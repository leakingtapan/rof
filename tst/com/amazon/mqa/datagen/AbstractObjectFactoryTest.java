package com.amazon.mqa.datagen;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Test for abstract object factory.
 */
public final class AbstractObjectFactoryTest extends AbstractObjectFactory {

    /**
     * Instantiates a new {@link AbstractObjectFactoryTest}.
     */
    public AbstractObjectFactoryTest() {
        super(Config.createDefault().withSupplier(String.class, () -> "a"));
    }

    /**
     * Tests creating constant string.
     *
     * @throws Exception if any problem occurs.
     */
    @Test
    public void testCreate() throws Exception {
        // exercise
        final String s = create(String.class);

        // verify
        assertEquals(s, "a");
    }

}
