package net.layin.lay.six;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 可直接调用反射获得的静态方法的类
 */
public class reflectClassBuilder implements reflectBuilder {
    private Class<?> cla;

    /**
     * 通过Class获取类
     */
    public reflectClassBuilder(Class<?> cla) {
        this.cla = cla;
    }

    /**
     * 通过类名获取类
     */
    public reflectClassBuilder(String name) throws ClassNotFoundException {
        this.cla = Class.forName(name);
    }

    /**
     * 通过类名获取类
     */
    public static reflectClassBuilder forName(String name) throws ClassNotFoundException {
        return new reflectClassBuilder(name);
    }

    /**
     * 通过转化成普通的Class
     */
    public Class<?> toClass() {
        return this.cla;
    }

    /**
     * 执行静态方法
     *
     * @param methodName 方法名
     * @param args       参数
     * @return 返回值, 是reflectObjectBuilder
     */
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
        return new reflectObjectBuilder(method.invoke(null, objArgs));

    }

    /**
     * 获取静态变量
     *
     * @param fieldName 变量名
     * @return 返回值, reflectObjectBuilder
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
        return new reflectObjectBuilder(field.get(null));
    }

    /**
     * 设置静态变量
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
        field.set(null, value);
    }

    /**
     * 调用构造方法创建实例
     *
     * @param args 参数
     * @return 创建的实例, 以reflectObjectBuilder形式返回
     */
    public reflectObjectBuilder newInstance(Object... args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
        Constructor<?> constructor;
        try {
            constructor = this.cla.getConstructor(claArgs);//先假设是public
        } catch (NoSuchMethodException e) {
            constructor = this.cla.getDeclaredConstructor(claArgs);
            constructor.setAccessible(true);//作为最后的尝试假设是私有权限
        }
        return new reflectObjectBuilder(constructor.newInstance(objArgs));
    }
}
