package net.layin.lay.six;

import java.lang.reflect.InvocationTargetException;

public interface reflectBuilder {
    reflectObjectBuilder doMethod(String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    reflectObjectBuilder getField(String fieldName) throws NoSuchFieldException, IllegalAccessException;

    void setField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException;
}
