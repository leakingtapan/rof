package com.amazon.mqa.datagen.rof;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;

/**
 * {@link ObjectFactory} that attempts to create instances of interfaces that have a concrete implementation on the
 * classpath that is supported. This supports interface types using the Java Immutables library for POJO
 * generation.
 */
public final class InterfaceFactory implements ObjectFactory {

    /** The object factory to delegate to. */
    private final ObjectFactory factory;

    /** Classes that are currently attempting to be created (used to avoid circular type references). */
    private final Set<Class<?>> classesInProgress = Collections.synchronizedSet(new HashSet<>());

    /**
     * Instantiates a new {@link InterfaceFactory}.
     *
     * @param factory - the object factory to delegate to.
     * @throws NullPointerException if an argument is <code>null</code>.
     */
    public InterfaceFactory(final ObjectFactory factory) {
        this.factory = requireNonNull(factory, "factory cannot be null");
    }

    @Override
    public <T> T create(final Class<T> clazz) {
        requireNonNull(clazz, "clazz cannot be null");

        if (!clazz.isInterface() || classesInProgress.contains(clazz)) {
            return null;
        }

        classesInProgress.add(clazz);

        try {
            return createInternal(clazz);
        } finally {
            classesInProgress.remove(clazz);
        }
    }

    /**
     * Attempts to create an instance of the specified class.
     *
     * @param clazz - the class.
     * @param <T> - the type.
     * @return the instance, or <code>null</code> if it cannot be created.
     */
    private <T> T createInternal(final Class<T> clazz) {
        assert clazz != null : "clazz cannot be null";

        final Reflections reflections = new Reflections(clazz.getPackage().getName());
        final Set<Class<? extends T>> allTypes = reflections.getSubTypesOf(clazz);

        final Comparator<? super Class<? extends T>> publicClassesFirst = (one, two) ->
                (two.getModifiers() & Modifier.PUBLIC) - (one.getModifiers() & Modifier.PUBLIC);

        return allTypes.stream()
                .filter(e -> !e.isAnnotationPresent(Deprecated.class))
                .sorted(publicClassesFirst)
                .map(factory::create)
                .filter(e -> e != null)
                .findFirst()
                .orElse(null);
    }

}
