package com.amazon.mqa.datagen.rof.spy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Finds the constructor and methods for class.
 */
public interface ClassSpy {

    /**
     * Finds an arbitrary constructor in the class.
     * In the case of multiple constructor, the way to choose depends on implementation.
     *
     * @param clazz the class to check.
     * @param <T> a type of the class.
     * @return the constructor.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    <T> Constructor<T> findConstructor(Class<T> clazz);

    /**
     * Finds all methods in the class, by the prefix of the method name.
     *
     * @param clazz the class to check.
     * @param <T> the type of the class.
     * @param namePrefix the prefix of the method name.
     * @return the methods.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    <T> List<Method> findMethods(Class<T> clazz, String namePrefix);

}
