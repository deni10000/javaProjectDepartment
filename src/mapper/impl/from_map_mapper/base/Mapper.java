package mapper.impl.from_map_mapper.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Map;

public class Mapper<T> {

    protected Class<T> clazz;

    public Mapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T map(Map<String, Object> map) {
        try {
            // Получаем конструктор
            var constructor = (Constructor<T>) clazz.getDeclaredConstructors()[0];
            // Получаем параметры конструктора
            Parameter[] parameters = constructor.getParameters();
            // Создаем массив для аргументов конструктора
            Object[] constructorArgs = new Object[parameters.length];

            // Заполняем массив аргументов значениями из map
            for (int i = 0; i < parameters.length; i++) {
                String paramName = parameters[i].getName();
                constructorArgs[i] = map.getOrDefault(paramName, null);
            }

            // Создаем новый экземпляр через конструктор с аргументами
            return constructor.newInstance(constructorArgs);
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of " + clazz.getName(), e);
        }
    }
}
