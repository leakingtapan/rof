package com.amazon.mqa.datagen.rof;

import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;

import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/** Unit test for {@link DefaultObjectFactory}. */
public final class DefaultObjectFactoryTest {

    /** Creates random numbers. */
    private final Supplier<Integer> integerFactory = new MinMaxIntegerSupplier(1, 10);

    /** Contains mocks. */
    private MockObjectContainer mocks;

    /** Mock factory. */
    private ObjectFactory mockFactory1;

    /** Mock factory. */
    private ObjectFactory mockFactory2;

    /** Instance under test. */
    private ObjectFactory factory;

    /** Set up. */
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();

        mockFactory1 = mocks.createMock("mockFactory1", ObjectFactory.class);
        mockFactory2 = mocks.createMock("mockFactory1", ObjectFactory.class);

        factory = new DefaultObjectFactory(Lists.newArrayList(mockFactory1, mockFactory2));
    }

    /**
     * Tests creating object.
     */
    @Test
    public void testCreate() {
        // set up
        final Integer expected = integerFactory.get();
        final Class<Integer> clazz = Integer.class;

        expect(mockFactory1.create(clazz)).andReturn(null);
        expect(mockFactory2.create(clazz)).andReturn(expected);

        mocks.replayAll();

        // exercise
        final Integer actual = factory.create(clazz);

        // verify
        assertEquals(actual, expected, "wrong object");
        mocks.verifyAll();
    }

    /**
     * Tests creating unhandled object.
     */
    @Test(expectedExceptions = ObjectCreationException.class)
    public void testCreateUnhandledClass() {
        // set up
        final Class<Integer> clazz = Integer.class;

        expect(mockFactory1.create(clazz)).andReturn(null);
        expect(mockFactory2.create(clazz)).andReturn(null);

        mocks.replayAll();

        // exercise
        factory.create(clazz);
    }

}