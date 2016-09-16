package com.amazon.mqa.datagen.rof.typed;

import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;

import com.amazon.mqa.datagen.supplier.RandomIntegerSupplier;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

/**
 * Unit test for {@link DefaultOptionalFactory}.
 */
public final class DefaultOptionalFactoryTest {

    /** Creates random integers. */
    private final Supplier<Integer> integerFactory = new RandomIntegerSupplier();

    /** Contains mocks. */
    private MockObjectContainer mocks;

    /** Mock typed object factory. */
    private TypedObjectFactory mockTypedObjectFactory;

    /** Class under test. */
    private OptionalFactory factory;

    /**
     * Required test set up.
     */
    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();

        mockTypedObjectFactory = mocks.createMock("mockTypedObjectFactory", TypedObjectFactory.class);

        factory = new DefaultOptionalFactory(mockTypedObjectFactory);
    }

    /**
     * Tests creating Optional.
     *
     * @throws Exception for any failure.
     */
    @Test
    public void testCreateOptional() throws Exception {

        // set up
        final Class<Integer> clazz = Integer.class;
        final Integer expected = integerFactory.get();

        expect(mockTypedObjectFactory.create(clazz)).andReturn(expected);

        mocks.replayAll();

        // exercise
        final Optional actual = factory.create(clazz);

        // verify
        assertEquals(actual, Optional.of(expected), "wrong optional");
    }
}
