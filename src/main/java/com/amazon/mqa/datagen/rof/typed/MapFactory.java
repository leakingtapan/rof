package com.amazon.mqa.datagen.rof.typed;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Creates Map object.
 */
interface MapFactory {

    /**
     * Creates an Map.
     *
     * @param keyType the key type of map.
     * @param valueType the value type of map.
     * @return the map.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    Map create(Type keyType, Type valueType);

}
