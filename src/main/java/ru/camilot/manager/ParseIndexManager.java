package ru.camilot.manager;

import ru.camilot.annotation.ParseIndex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseIndexManager {

    private static final String separator = ",";

    private final Map<Class, Map<Integer, Method>> map = new HashMap<>();

    public boolean registerClass(String header, Class<?> clazz) {

        String[] headers = header.split(separator);
        for (Field field: clazz.getDeclaredFields()) {
            ParseIndex annotation = field.getAnnotation(ParseIndex.class);
            if (annotation != null) {
                String setterName = "set"
                        + field.getName().substring(0, 1).toUpperCase()
                        + field.getName().substring(1);
                try {
                    Method setter = clazz.getDeclaredMethod(setterName, field.getType());

                    String headerName = annotation.headerName();
                    int headerIndex = Arrays.asList(headers).indexOf(headerName);

                    Map<Integer, Method> setters = map.getOrDefault(clazz, new HashMap<>());
                    if (setters.containsKey(headerIndex)) return false;
                    setters.put(headerIndex, setter);
                    map.put(clazz, setters);
                }
                catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public <T> T parseObject(String line, Class<T> clazz) {
        T instance = null;
        try {
            Constructor<T> constructor = clazz.getConstructor();
            instance = constructor.newInstance();
            String[] values = line.split(separator);
            Map<Integer, Method> integerMethodMap = map.get(clazz);

            for(Map.Entry<Integer, Method> entry : integerMethodMap.entrySet()) {
                int key = entry.getKey();
                entry.getValue().invoke(instance, values[key]);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

}
