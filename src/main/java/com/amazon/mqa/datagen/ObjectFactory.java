package com.amazon.mqa.datagen;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates objects, populated with arbitrary data.
 */
public interface ObjectFactory {
    /**
     * Creates a populated object. Returns <code>null</code> if the object can't be created.
     *
     * @param clazz the class to create
     * @param <T> the type of the class.
     * @return an instance of the class, or <code>null</code> if failed to create object from the class.
     * @throws NullPointerException if the argument is <code>null</code>.
     */
    <T> T create(Class<T> clazz);

    /**
     * Creates a list of populated objects with the default size (implementation dependent).
     *
     * @param clazz the class of object to create.
     * @param <T> the type of object to create.
     * @return a list of objects.
     * @throws IllegalArgumentException if the number of objects is negative.
     *
     * @see ReflectionObjectFactory#setOf(Class, int)
     */
    <T> List<T> listOf(Class<T> clazz);

    /**
     * Creates a list of populated objects.
     *
     * @param clazz the class of object to create.
     * @param howMany the number of objects to create.
     * @param <T> the type of object to create.
     * @return the indicated number of objects, populated with arbitrary data.
     * @throws NullPointerException if an argument is <code>null</code>.
     * @throws IllegalArgumentException if the number of objects is negative.
     *
     * @see ReflectionObjectFactory#listOf(Class)
     */
    <T> List<T> listOf(Class<T> clazz, int howMany);

    /**
     * Creates a set of populated objects with the default size (implementation dependent).
     *
     * @param clazz the class of object to create.
     * @param <T> the type of object to create.
     * @return a set of objects.
     * @throws NullPointerException if an argument is <code>null</code>.
     *
     * @see ReflectionObjectFactory#setOf(Class, int)
     */
    <T> Set<T> setOf(Class<T> clazz);

    /**
     * <p>Creates a set of populated objects.</p>
     *
     * <p>
     * The actual number of objects created may occasionally be smaller than requested if it is hard to create
     * the requested number of unique objects. For instance, it isn't possible to create a set of more than
     * two Booleans. This method will attempt to create the desired number of objects, but it may not always
     * succeed.
     * </p>
     *
     * @param clazz the class of object to create.
     * @param howMany the number of objects to create.
     * @param <T> the type of object to create.
     * @return the indicated number of objects, populated with arbitrary data.
     * @throws NullPointerException if an argument is <code>null</code>.
     * @throws IllegalArgumentException if the number of objects is negative.
     *
     * @see ReflectionObjectFactory#setOf(Class)
     */
    <T> Set<T> setOf(Class<T> clazz, int howMany);

    /**
     * <p>Creates a map of populated objects with the default size (implementation dependent).</p>
     *
     * @param keyClass the class of map's key.
     * @param valueClass the class of map's value.
     * @param <K> the type of key.
     * @param <V> the type of value.
     * @return a map of object.
     * @throws NullPointerException if any argument is <code>null</code>.
     *
     * @see ReflectionObjectFactory#mapOf(Class, Class, int)
     */
    <K, V> Map<K, V> mapOf(Class<K> keyClass, Class<V> valueClass);

    /**
     * <p>Creates a map of populated objects.</p>
     *
     * @param keyClass the class of map's key.
     * @param valueClass the class of map's value.
     * @param howMany the number of map entries to create.
     * @param <K> the type of key.
     * @param <V> the type of value.
     * @return a map of object.
     * @throws IllegalArgumentException if the number of objects is negative.
     * @throws NullPointerException if any argument is <code>null</code>.
     *
     * @see ReflectionObjectFactory#mapOf(Class, Class)
     */
    <K, V> Map<K, V> mapOf(Class<K> keyClass, Class<V> valueClass, int howMany);

}
