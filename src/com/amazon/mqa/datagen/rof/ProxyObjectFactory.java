package com.amazon.mqa.datagen.rof;

import com.amazon.mqa.datagen.rof.typed.DefaultTypedObjectFactory;
import com.amazon.mqa.datagen.rof.typed.TypedObjectFactory;
import com.google.common.reflect.AbstractInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates a random object which conforms the contract of an interface/abstract/non-final class
 * by dynamic proxy. Each methods in the interface is recursively proxied.
 */
final class ProxyObjectFactory implements ObjectFactory {

    /** Proxy handler. */
    private static class Handler extends AbstractInvocationHandler {

        /** Create random object. */
        private final TypedObjectFactory factory;

        /**
         * Instantiate a new {@link Handler}.
         *
         * @param factory create random object.
         * @throws NullPointerException if any argument is <code>null</code>.
         */
        Handler(final TypedObjectFactory factory) {
            this.factory = checkNotNull(factory, "factory cannot be null");
        }

        @Override
        protected Object handleInvocation(final Object proxy,
                                          final Method method,
                                          final Object[] args) throws Throwable {
            checkNotNull(proxy, "proxy cannot be null");
            checkNotNull(method, "method cannot be null");
            checkNotNull(args, "args cannot be null");

            return factory.create(method.getReturnType());
        }
    }

    /**
     * creates a new {@link ProxyObjectFactory}.
     *
     * @param factory the inner factory.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    static ProxyObjectFactory create(final ObjectFactory factory) {
        checkNotNull(factory, "factory cannot be null");

        return new ProxyObjectFactory(new Handler(new DefaultTypedObjectFactory(factory)));
    }

    /** Method invocation handler. */
    private final InvocationHandler handler;

    /**
     * Instantiates a new {@link ProxyObjectFactory}.
     *
     * @param handler invocation handler.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    ProxyObjectFactory(final InvocationHandler handler) {
        this.handler = checkNotNull(handler, "handler cannot be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T create(final Class<T> clazz) {
        checkArgument(clazz.isInterface(), "clazz is not interface");

        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                handler);
    }
}
