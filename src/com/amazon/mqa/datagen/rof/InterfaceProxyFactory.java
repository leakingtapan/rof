package com.amazon.mqa.datagen.rof;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a random object which conforms the contract of an interface class
 * by dynamic proxy. Each methods in the interface is recursively proxied.
 */
final class InterfaceProxyFactory implements ObjectFactory {

    /** Method invocation handler. */
    private final InvocationHandler handler;

    /**
     * Instantiates a new {@link InterfaceProxyFactory}.
     *
     * @param handler invocation handler.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    InterfaceProxyFactory(final InvocationHandler handler) {
        this.handler = checkNotNull(handler, "handler cannot be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        if (!clazz.isInterface()) {
            return null;
        }

        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                handler);
    }


}
