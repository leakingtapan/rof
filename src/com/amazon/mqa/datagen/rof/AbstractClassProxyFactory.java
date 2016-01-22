package com.amazon.mqa.datagen.rof;

import com.amazon.mqa.datagen.rof.spy.DefaultClassSpy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates proxy object for abstract class.
 */
final class AbstractClassProxyFactory implements ObjectFactory {

    /** Method invocation handler. */
    private final InvocationHandler handler;

    /**
     * Instantiates a new {@link AbstractClassProxyFactory}.
     *
     * @param handler handles
     */
    AbstractClassProxyFactory(final InvocationHandler handler) {
        this.handler = checkNotNull(handler, "handler cannot be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        if (!isAbstract(clazz)) {
            return null;
        }

        checkIfSupported(clazz);

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(handler);

        return (T) enhancer.create();
    }

    /**
     * @param clazz the clazz to check.
     * @return <code>true</code> if the class is abstract class.
     */
    private boolean isAbstract(final Class<?> clazz) {
        assert clazz != null : "clazz cannot be null";

        return  Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * Checks if the abstract class is supported.
     * Currently only supports abstract class with no argument constructor.
     *
     * @param clazz the class to check.
     * @throws ObjectCreationException if the class is not supported.
     */
    private void checkIfSupported(final Class clazz) {
        assert clazz != null : "clazz cannot be null";
      
        final Constructor constructor = new DefaultClassSpy().findConstructor(clazz);
        if (constructor.getParameterCount() != 0) {
            throw new ObjectCreationException(clazz + " doesn't have constructor with no arguments");
        }
    }
}
