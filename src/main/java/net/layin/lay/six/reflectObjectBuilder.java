package net.layin.lay.six;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 可直接调用反射获得的实例方法的对象
 */
public class reflectObjectBuilder implements reflectBuilder {
    private Class<?> cla;
    private Object obj;

    /**
     * 由对象获取可直接使用反射方法对象
     *
     * @param obj 对象
     */
    public reflectObjectBuilder(Object obj) {
        this.obj = obj;
        this.cla = obj.getClass();
    }

    /**
     * 由对象获取可直接使用反射方法对象
     *
     * @param obj 对象
     */
    public static reflectObjectBuilder fromObject(Object obj) {
        return new reflectObjectBuilder(obj);
    }

    /**
     * 由反射对象恢复普通对象
     */
    public Object toObject() {
        return this.obj;
    }

    /**
     * 使用实例方法
     *
     * @param methodName 方法名
     * @param args       参数
     * @return 返回值可反射对象
     */
    @Override
    public reflectObjectBuilder doMethod(String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] objArgs = args.clone();
        Class<?>[] claArgs = new Class[args.length];
        //处理参数
        for (int i = 0; i < objArgs.length; i++) {
            if (objArgs[i] instanceof reflectBuilder) {
                if (objArgs[i] instanceof reflectClassBuilder) {
                    objArgs[i] = ((reflectClassBuilder) objArgs[i]).toClass();
                } else if (objArgs[i] instanceof reflectObjectBuilder) {
                    objArgs[i] = ((reflectObjectBuilder) objArgs[i]).toObject();//将两种Builder转换为正常类型
                }
            }
        }
        for (int i = 0; i < objArgs.length; i++) {
            claArgs[i] = objArgs[i].getClass();
        }
        Method method;
        try {
            method = this.cla.getMethod(methodName, claArgs);
        } catch (NoSuchMethodException e) {
            method = this.cla.getDeclaredMethod(methodName, claArgs);
            method.setAccessible(true);
        }
        return new reflectObjectBuilder(method.invoke(this.obj, objArgs));
    }

    /**
     * 获取实例变量
     *
     * @param fieldName 变量名
     * @return 实例方法的返回值
     */
    @Override
    public reflectObjectBuilder getField(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = this.cla.getField(fieldName);
        } catch (NoSuchFieldException e) {
            field = this.cla.getDeclaredField(fieldName);
            field.setAccessible(true);
        }
        return new reflectObjectBuilder(field.get(this.obj));
    }

    /**
     * 设置实例变量
     *
     * @param fieldName 变量名
     * @param value     设置値
     */
    @Override
    public void setField(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        try {
            field = this.cla.getField(fieldName);
        } catch (NoSuchFieldException e) {
            field = this.cla.getDeclaredField(fieldName);
            field.setAccessible(true);
        }
        field.set(this.obj, value);
    }
}
