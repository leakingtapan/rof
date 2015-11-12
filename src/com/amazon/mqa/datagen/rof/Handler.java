package com.amazon.mqa.datagen.rof;

import com.amazon.mqa.datagen.rof.typed.TypedObjectFactory;
import com.google.common.base.Supplier;
import com.google.common.reflect.AbstractInvocationHandler;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/** Proxy handler. */
final class Handler extends AbstractInvocationHandler implements InvocationHandler {

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
