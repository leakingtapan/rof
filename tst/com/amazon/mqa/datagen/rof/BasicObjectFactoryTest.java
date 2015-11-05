package com.amazon.mqa.datagen.rof;

import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import com.amazon.mqa.datagen.supplier.AlphanumericStringSupplier;
import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Unit test for {@link BasicObjectFactory}.
 */
public final class BasicObjectFactoryTest {

    /** Creates random numbers. */
    private final Supplier<Integer> integerFactory = new MinMaxIntegerSupplier(1, 10);

    /** Creates random string. */
    private final Supplier<String> stringFactory = new AlphanumericStringSupplier();

    /** Contains mocks. */
    private MockObjectContainer mocks;

    /** Mock integer supplier. */
    private Supplier<Integer> mockIntegerSupplier;

    /** Mock string supplier. */
    private Supplier<String> mockStringSupplier;

    /** Instance under test. */
    private ObjectFactory factory;

    /** Set up. */
    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();
        mockIntegerSupplier = mocks.createMock("mockIntegerSupplier", Supplier.class);
        mockStringSupplier = mocks.createMock("mockStringSupplier", Supplier.class);

        final Map<Class<?>, Supplier> suppliers = new ImmutableMap.Builder<Class<?>, Supplier>()
                .put(int.class, mockIntegerSupplier)
                .put(String.class, mockStringSupplier)
                .build();

        factory = new BasicObjectFactory(suppliers);
    }

    /**
     * Tests creating int.
     */
    @Test
    public void testCreateInt() {
        // set up
        final int expectedInteger = integerFactory.get();

        expect(mockIntegerSupplier.get()).andReturn(expectedInteger);

        mocks.replayAll();

        // exercise
        final int actualInteger = factory.create(int.class);

        // verify
        assertEquals(actualInteger, expectedInteger, "wrong integer");
        mocks.verifyAll();
    }

    /**
     * Tests creating string.
     */
    @Test
    public void testCreateString() {
        // set up
        final String expectedString = stringFactory.get();

        expect(mockStringSupplier.get()).andReturn(expectedString);

        mocks.replayAll();

        // exercise
        final String actualString = factory.create(String.class);

        // verify
        assertEquals(actualString, expectedString, "wrong string");
        mocks.verifyAll();
    }

    /**
     * Tests creating class that is not in the map.
     */
    @Test
    public void testCreateUnSupportedClass() {
        // exercise
        final Double actualDouble = factory.create(Double.class);

        // verify
        assertNull(actualDouble);
    }

}