package com.amazon.mqa.datagen.rof;

import static org.testng.Assert.assertTrue;

import com.amazon.mqa.datagen.Config;
import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import org.testng.annotations.Test;

/**
 * Functional test for {@link ObjectArrayFactory}.
 */
public final class ObjectArrayFactoryFunctionalTest {

    /** Minimum array size. */
    private static final int MIN_ARRAY_SIZE = 1;

    /** Maximum array size. */
    private static final int MAX_ARRAY_SIZE = 21;

    /** Instance under test. */
    private ObjectFactory factory = new ObjectArrayFactory(
            new BasicObjectFactory(Config.createDefault().getSuppliers()),
            new MinMaxIntegerSupplier(MIN_ARRAY_SIZE, MAX_ARRAY_SIZE));

    /**
     * Tests creating array with primitives.
     */
    @Test
    public void testCreateArray() {
        // exercise
        final int[] intArray = factory.create(int[].class);

        // verify
        assertTrue(intArray.length < MAX_ARRAY_SIZE && intArray.length >= MIN_ARRAY_SIZE, "wrong array size");
    }

}