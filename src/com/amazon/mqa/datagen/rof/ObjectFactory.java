package com.amazon.mqa.datagen.rof;

/** Factory that can create object from class. */
public interface ObjectFactory {

    /**
     * Creates an object.
     *
     * If called when the class can not be created, <code>null</code> will be returned.
     *
     * @param clazz the class.
     * @param <T> the type.
     * @return an instance of the class, or <code>null</code> if failed to create object from the class.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    <T> T create(Class<T> clazz);

}
