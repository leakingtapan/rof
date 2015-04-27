package com.amazon.mqa.datagen.rof.spy;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Lists;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/** Default implementation of {@link ClassSpy}. */
public final class DefaultClassSpy implements ClassSpy {

    /**
     * Finds the constructor with least number of parameters.
     *
     * {@inheritDoc}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> Constructor<T> findConstructor(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        int minNumberOfParameter = Integer.MAX_VALUE;
        Constructor<T> result = null;
        for (final Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length < minNumberOfParameter) {
                minNumberOfParameter = constructor.getParameterTypes().length;
                result = (Constructor<T>) constructor;
            }
        }

        return result;
    }

    @Override
    public <T> List<Method> findMethods(final Class<T> clazz, final String namePrefix) {
        checkNotNull(clazz, "clazz cannot be null");
        checkNotNull(namePrefix, "namePrefix cannot be null");

        // Gets inherited methods too
        final Method[] methods = clazz.getMethods();
        final List<Method> methodsWithPrefix = Lists.newArrayList();
        for (final Method method : methods) {
            if (method.getName().startsWith(namePrefix)) {
                methodsWithPrefix.add(method);
            }
        }

        return methodsWithPrefix;
    }
}
