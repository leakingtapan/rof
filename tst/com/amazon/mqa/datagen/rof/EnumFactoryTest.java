package com.amazon.mqa.datagen.rof;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

/**
 * Unit test for {@link EnumFactory}.
 */
public final class EnumFactoryTest {

    /** Instance under test. */
    private final ObjectFactory factory = new EnumFactory();

    /**
     * Test empty enum.
     */
    private static enum EmptyEnum { }

    /**
     * Test enum.
     */
    private static enum TestEnum { ENUM1, ENUM2 }

    /**
     * Tests creating empty enum.
     */
    @Test
    public void testCreateEmptyEnum() {
        // exercise
        final EmptyEnum object = factory.create(EmptyEnum.class);

        // verify
        assertNull(object, "object is not null");
    }

    /**
     * Tests creating a random enum.
     */
    @Test
    public void testCreateEnum() {
        // exercise
        final TestEnum testEnum = factory.create(TestEnum.class);

        // verify
        assertNotNull(testEnum);
    }

    /**
     * Tests creating non-enum class.
     */
    @Test
    public void testCreateNonEnum() {
        // exercise
        final Integer integer = factory.create(Integer.class);

        // verify
        assertNull(integer);
    }

}