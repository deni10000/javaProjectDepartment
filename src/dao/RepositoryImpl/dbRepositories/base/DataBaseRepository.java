package dao.RepositoryImpl.dbRepositories.base;

import connection_pool.ConnectionPool;
import dao.Repository;
import models.entity.Entity;
import object_manager.ObjectManager;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static utils.Utils.entityToMap;
import static utils.Utils.toSnakeCase;

public abstract class DataBaseRepository<T extends Entity<K>, K> implements Repository<T, K> {

    ConnectionPool connectionPool = ObjectManager.get(ConnectionPool.class);

    protected abstract Map<String, ?> idToMap(K id);

    protected abstract String getTableName();

    protected abstract Class<T> getEntityClass();

    protected T mapResultSetToEntity(ResultSet resultSet) {
        try {
            Class<T> entityClass = getEntityClass();
            Field[] fields = entityClass.getDeclaredFields();
            Object[] args = new Object[fields.length];

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String columnName = toSnakeCase(field.getName());
                args[i] = resultSet.getObject(columnName, field.getType());
            }
            return entityClass.getDeclaredConstructor(Arrays.stream(fields)
                            .map(Field::getType)
                            .toArray(Class[]::new))
                    .newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map result set to entity", e);
        }
    }

    @Override
    public boolean delete(K id) {
        Map<String, ?> idMap = idToMap(id);
        StringBuilder sql = new StringBuilder("DELETE FROM " + getTableName() + " WHERE ");
        buildWhereClause(sql, idMap);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            setParameters(statement, idMap);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete model with id " + id, e);
        }
    }

    @Override
    public T save(T model) {
        Map<String, Object> fieldMap = entityToMap(model);
        StringBuilder sql = new StringBuilder("INSERT INTO " + getTableName() + " (");
        StringBuilder placeholders = new StringBuilder("VALUES (");
        buildInsertStatement(sql, placeholders, fieldMap);
        sql.append(") ").append(placeholders).append(") RETURNING *");

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            setParameters(statement, fieldMap);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return mapResultSetToEntity(resultSet);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save model", e);
        }
    }

    @Override
    public Optional<T> findById(K id) {
        List<T> entity = findAllWithParameter(idToMap(id));
        if (entity.size() > 1) {
            throw new RuntimeException("Unexpected multiple objects found for a single key");
        }
        return entity.isEmpty() ? Optional.empty() : Optional.of(entity.get(0));
    }

    @Override
    public List<T> findAll() {
        return findAllWithParameter(Map.of());
    }

    public List<T> findAll(Integer pageNumber, Integer pageSize) {
        return findAllWithParameter(Map.of(), pageNumber, pageSize);
    }

    @Override
    public boolean update(T model) {
        K id = model.getId();
        Map<String, ?> idMap = idToMap(id);
        if (findById(id).isEmpty()) {
            return false;
        }

        Map<String, Object> fieldMap = entityToMap(model);
        StringBuilder sql = new StringBuilder("UPDATE " + getTableName() + " SET ");
        buildUpdateStatement(sql, fieldMap);
        sql.append(" WHERE ");
        buildWhereClause(sql, idMap);

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            setParameters(statement, fieldMap);
            setParameters(statement, idMap, fieldMap.size() + 1);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update model with id " + id, e);
        }
    }

    @Override
    public List<T> findAllWithParameter(Map<String, ?> parameters) {
        return findAllWithParameter(parameters, null, null);
    }

    public List<T> findAllWithParameter(Map<String, ?> parameters, Integer pageNumber, Integer pageSize) {
        if (parameters == null) {
            throw new RuntimeException("parameters is null");
        }
        StringBuilder sql = new StringBuilder("SELECT * FROM " + getTableName());
        if (!parameters.isEmpty()) {
            sql.append(" WHERE ");
            buildWhereClause(sql, parameters);
        }
        if (pageNumber != null && pageSize != null) {
            sql.append(" LIMIT ").append(pageSize).append(" OFFSET ").append((pageNumber - 1) * pageSize);
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            setParameters(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }
            return entities;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM " + getTableName();
        return countRecords(sql);
    }

    public int countWithParameter(Map<String, ?> parameters) {
        if (parameters == null) {
            throw new RuntimeException("parameters is null");
        }

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM " + getTableName());
        if (!parameters.isEmpty()) {
            sql.append(" WHERE ");
            buildWhereClause(sql, parameters);
        }

        return countRecords(sql.toString(), parameters.values().toArray());
    }

    private void buildInsertStatement(StringBuilder sql, StringBuilder placeholders, Map<String, Object> fieldMap) {
        int fieldCount = fieldMap.size();
        int index = 0;

        for (String fieldName : fieldMap.keySet()) {
            sql.append(toSnakeCase(fieldName));
            placeholders.append("?");
            if (index < fieldCount - 1) {
                sql.append(", ");
                placeholders.append(", ");
            }
            index++;
        }
    }

    private void buildUpdateStatement(StringBuilder sql, Map<String, Object> fieldMap) {
        int index = 0;
        for (String fieldName : fieldMap.keySet()) {
            sql.append(toSnakeCase(fieldName)).append(" = ?");
            if (index < fieldMap.size() - 1) {
                sql.append(", ");
            }
            index++;
        }
    }

    private void buildWhereClause(StringBuilder sql, Map<String, ?> parameters) {
        int paramCount = parameters.size();
        int index = 0;
        for (String key : parameters.keySet()) {
            sql.append(toSnakeCase(key)).append(" = ?");
            if (index < paramCount - 1) {
                sql.append(" AND ");
            }
            index++;
        }
    }

    private void setParameters(PreparedStatement statement, Map<String, ?> parameters) throws Exception {
        setParameters(statement, parameters, 1);
    }

    private void setParameters(PreparedStatement statement, Map<String, ?> parameters, int start) throws Exception {
        int index = start;
        for (Object value : parameters.values()) {
            statement.setObject(index, value);
            index++;
        }
    }

    private int countRecords(String sql, Object... parameters) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
