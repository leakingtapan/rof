package com.amazon.mqa.datagen.rof;

import com.amazon.mqa.datagen.rof.typed.DefaultTypedObjectFactory;
import com.amazon.mqa.datagen.rof.typed.TypedObjectFactory;
import com.google.common.base.Supplier;
import com.google.common.reflect.AbstractInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

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

        /** Suppliers for proxy method. */
        private final Map<String, Supplier> pmSuppliers;

        /**
         * Instantiate a new {@link Handler}.
         *
         * @param factory create random object.
         * @param pmSuppliers suppliers for proxy method.
         * @throws NullPointerException if any argument is <code>null</code>.
         */
        Handler(final TypedObjectFactory factory, final Map<String, Supplier> pmSuppliers) {
            this.factory = checkNotNull(factory, "factory cannot be null");
            this.pmSuppliers = checkNotNull(pmSuppliers, "pmSuppliers cannot be null");
        }

        //CHECKSTYLE:SUPPRESS:IllegalThrows
        @Override
        protected Object handleInvocation(final Object proxy,
                                          final Method method,
                                          final Object[] args) throws Throwable {
            checkNotNull(proxy, "proxy cannot be null");
            checkNotNull(method, "method cannot be null");
            checkNotNull(args, "args cannot be null");

            if (pmSuppliers.containsKey(method.getName())) {
                return pmSuppliers.get(method.getName()).get();
            }

            return factory.create(method.getReturnType());
        }
        //CHECKSTYLE:UNSUPPRESS:IllegalThrows
    }

    /**
     * creates a new {@link ProxyObjectFactory}.
     *
     * @param factory the inner factory.
     * @param pmSuppliers suppliers for proxy method.
     * @return the instance.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    static ProxyObjectFactory create(final ObjectFactory factory, final Map<String, Supplier> pmSuppliers) {
        checkNotNull(factory, "factory cannot be null");
        checkNotNull(pmSuppliers, "pmSuppliers cannot be null");

        return new ProxyObjectFactory(
                new Handler(
                        new DefaultTypedObjectFactory(factory),
                        pmSuppliers)
        );
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
