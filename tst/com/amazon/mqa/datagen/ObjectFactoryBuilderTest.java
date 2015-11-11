package com.amazon.mqa.datagen;

import com.amazon.mqa.datagen.supplier.RandomIntegerSupplier;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

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

    /**
     * Tests build object factory to create interface proxy.
     *
     * @throws Exception if any problem occurs.
     */
    @Test
    public void testBuildForInterface() throws Exception {
        // set up
        final int constant = new ReflectionObjectFactory().create(int.class);

        // exercise
        final InterfaceA obj = new ObjectFactoryBuilder()
                .withSupplier("getInt", () -> constant)
                .build()
                .create(InterfaceA.class);

        // verify
        assertEquals(obj.getInt(), constant);
    }

}