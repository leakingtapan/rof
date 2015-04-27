package com.amazon.mqa.datagen.rof;

import static com.google.common.base.Preconditions.checkNotNull;

import com.amazon.mqa.datagen.rof.typed.DefaultTypedObjectFactory;
import com.amazon.mqa.datagen.rof.typed.TypedObjectFactory;
import com.amazon.mqa.datagen.rof.spy.ClassSpy;
import com.amazon.mqa.datagen.rof.spy.DefaultClassSpy;
import com.google.common.collect.Lists;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Create any plain old java objects (POJO) other than primitive, interface, enum or abstract class.
 */
final class PojoFactory implements ObjectFactory {

    /** Get constructor within a class. */
    private final ClassSpy spy;

    /** Creates objects from its type. */
    private final TypedObjectFactory typedObjectFactory;

    /**
     * Creates a {@link PojoFactory}.
     *
     * @param objectFactory creates objects for class's constructor.
     * @return the default instance.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public static PojoFactory create(final ObjectFactory objectFactory) {
        checkNotNull(objectFactory, "objectFactory cannot be null");

        return new PojoFactory(
                new DefaultTypedObjectFactory(objectFactory),
                new DefaultClassSpy());
    }

    /**
     * Instantiates a new {@link PojoFactory}.
     *
     * @param spy finds constructor or setter of a class.
     * @param typedObjectFactory creates object from its type.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    PojoFactory(final TypedObjectFactory typedObjectFactory, final ClassSpy spy) {
        this.typedObjectFactory = checkNotNull(typedObjectFactory, "typeFactory cannot be null");
        this.spy = checkNotNull(spy, "spy cannot be null");
    }

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        if (!canCreate(clazz)) {
            return null;
        }

        final T object = createObject(clazz);

        return object == null ? null : populateFields(object);
    }

    /**
     * Check if the class can be created.
     *
     * @param clazz the class to check.
     * @return <code>true</code> if the class can be created.
     */
    private boolean canCreate(final Class<?> clazz) {
        assert clazz != null : "clazz cannot be null";

        return !clazz.isInterface() && !clazz.isEnum();
    }

    /**
     * Creates objects by calling the class's constructor with constructor argument properly created.
     *
     * @param clazz the class to create.
     * @param <T> the type of class.
     * @return the object, or <code>null</code> if failed to instantiate the object.
     */
    private <T> T createObject(final Class<T> clazz) {
        assert clazz != null : "clazz cannot be null";

        final Constructor<T> constructor = spy.findConstructor(clazz);

        final List<Object> constructorArgs = Lists.newArrayList();
        for (final Type genericType : constructor.getGenericParameterTypes()) {
            constructorArgs.add(typedObjectFactory.create(genericType));
        }

        final T object;
        try {
            object = constructor.newInstance(constructorArgs.toArray());
        } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }

        return object;
    }

    /**
     * Populating object fields by calling setters.
     *
     * @param object the object to set.
     * @param <T> the type of object.
     * @return the object with field being set.
     */
    private <T> T populateFields(final T object) {
        assert object != null : "object cannot be null";

        final List<Method> setters = spy.findMethods(object.getClass(), "set");
        for (final Method setter : setters) {
            final Type[] genericSetterParamTypes = setter.getGenericParameterTypes();
            // assumes setter only has one input argument
            if (genericSetterParamTypes.length == 1) {
                try {
                    setter.invoke(object, typedObjectFactory.create(genericSetterParamTypes[0]));
                } catch (final IllegalAccessException | InvocationTargetException e) {
                    // make setter invoke not fail on error
                    // intentionally ignored
                }
            }
        }

        return object;
    }

}
