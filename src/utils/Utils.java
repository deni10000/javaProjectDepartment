package utils;

import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;

public final class Utils {
    public static String toSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    public static <T> Map<String, Object> entityToMap(T entity) {
        Map<String, Object> recordMap = new HashMap<>();
        RecordComponent[] components = entity.getClass().getRecordComponents();

        for (RecordComponent component : components) {
            try {
                Object value = component.getAccessor().invoke(entity);
                if (value != null) {
                    recordMap.put(component.getName(), value);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert entity to map", e);
            }
        }
        return recordMap;
    }

}
