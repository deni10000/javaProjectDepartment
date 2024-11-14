package connection_pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private final BlockingQueue<Connection> connections = new ArrayBlockingQueue<>(PoolConfiguration.POOL_SIZE);

    public ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < PoolConfiguration.POOL_SIZE; i++) {
            createConnection();
        }
    }

    private void createConnection() {
        try {
            connections.add(DriverManager.getConnection(PoolConfiguration.URL, PoolConfiguration.USER, PoolConfiguration.PASSWORD));
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось создать подключение к БД", e);
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return createProxyConnection(connection);
    }

    private void returnConnection(Connection connection) {
        connections.add(connection);
    }

    private Connection createProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                new ConnectionProxyHandler(connection, this)
        );
    }

    private record ConnectionProxyHandler(Connection connection, ConnectionPool pool) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                if (method.getName().equals("close")) {
                    pool.returnConnection(connection);
                    return null;
                } else {
                    return method.invoke(connection, args);
                }
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при выполнении метода соединения", e);
            }
        }
    }
}
