package com.amazon.mqa.datagen.supplier;

import static org.testng.Assert.assertTrue;

import com.google.common.base.Supplier;
import org.testng.annotations.Test;

/**
 * Unit test for {@link MinMaxIntegerSupplier}.
 */
public final class MinMaxIntegerSupplierTest {

    /**
     * Tests create int.
     */
    @Test
    public void testRandomInt() {
        // set up
        final int min = -1000;
        final int max = 1000;

        final Supplier<Integer> supplier = new MinMaxIntegerSupplier(min, max);
        
        // exercise
        final Integer integer = supplier.get();

        // verify
        assertTrue(integer >= min);
        assertTrue(integer < max);
    }

}