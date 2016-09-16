package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkNotNull;

import com.amazon.mqa.datagen.rof.ObjectFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of {@link TypedObjectFactory}.
 */
public final class DefaultTypedObjectFactory implements TypedObjectFactory {

    /** Creates objects. */
    private final ObjectFactory objectFactory;

    /** Creates collection fields. */
    private final CollectionFactory collectionFactory;

    /** Creates map object. */
    private final MapFactory mapFactory;

    /** Creates optional objects. */
    private final OptionalFactory optionalFactory;

    /**
     * Instantiates a new {@link DefaultTypedObjectFactory}.
     *
     * @param objectFactory creates objects.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public DefaultTypedObjectFactory(final ObjectFactory objectFactory) {
        checkNotNull(objectFactory, "objectFactory cannot be null");

        this.objectFactory = objectFactory;
        this.collectionFactory =  DefaultCollectionFactory.create(this);
        this.mapFactory = DefaultMapFactory.create(this);
        this.optionalFactory = new DefaultOptionalFactory(this);
    }

    /**
     * Instantiates a new {@link DefaultTypedObjectFactory}.
     *
     * @param objectFactory creates objects.
     * @param collectionFactory creates collections.
     * @param mapFactory creates maps.
     * @param optionalFactory creates optionals.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    DefaultTypedObjectFactory(final ObjectFactory objectFactory,
                              final CollectionFactory collectionFactory,
                              final MapFactory mapFactory,
                              final OptionalFactory optionalFactory) {
        this.objectFactory = checkNotNull(objectFactory, "objectFactory cannot be null");
        this.collectionFactory = checkNotNull(collectionFactory, "collectionFactory cannot be null");
        this.mapFactory = checkNotNull(mapFactory, "objectFactory cannot be null");
        this.optionalFactory = checkNotNull(optionalFactory, "optionalFactory cannot be null");
    }

    @Override
    public Object create(final Type type) {
        checkNotNull(type, "type cannot be null");

        if (type instanceof Class) {
            return objectFactory.create((Class) type);
        }

        if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            final Class rawType = (Class) parameterizedType.getRawType();
            final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            //TODO: add robust way to check if type can be handled
            if (rawType.equals(Map.class)) {
                return mapFactory.create(actualTypeArguments[0], actualTypeArguments[1]);
            } else if (rawType.equals(Optional.class)) {
                return optionalFactory.create(actualTypeArguments[0]);
            } else {
                return collectionFactory.create(rawType, actualTypeArguments[0]);
            }
        }

        return null;
    }
}
