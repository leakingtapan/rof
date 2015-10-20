package com.amazon.mqa.datagen;

import com.amazon.mqa.datagen.supplier.RandomIntegerSupplier;
import org.testng.annotations.Test;

/**
 * Unit test for {@link ObjectFactoryBuilder}.
 */
public final class ObjectFactoryBuilderTest {

    /**
     * Tests build object factory.
     */
    @Test
    public void testBuild() {
        // exercise
        new ObjectFactoryBuilder()
                .withSupplier(int.class, new RandomIntegerSupplier())
                .build();
    }

}