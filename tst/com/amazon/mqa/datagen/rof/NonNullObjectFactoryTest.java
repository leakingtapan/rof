package com.amazon.mqa.datagen.rof;

import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.expectThrows;

import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.amazon.mtqa.testutil.MockObjectContainer;

/**
 * Unit tests for {@link NonNullObjectFactory}.
 */
public final class NonNullObjectFactoryTest {

    /** Container for mock objects. */
    private MockObjectContainer mocks;

    /** The object under test. */
    private ObjectFactory factory;

    /** Mock factory. */
    private ObjectFactory mockFactory;

    /** Required test set up. */
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();

        mockFactory = mocks.createMock("mockFactory", ObjectFactory.class);

        factory = new NonNullObjectFactory(mockFactory);
    }

    /**
     * Tests creating an object.
     */
    @Test
    public void create() {
        // setup
        final String expected = UUID.randomUUID().toString();

        expect(mockFactory.create(String.class)).andReturn(expected);

        mocks.replayAll();

        // exercise
        final String actual = factory.create(String.class);

        // verify
        assertEquals(actual, expected);

        mocks.verifyAll();
    }

    /**
     * Tests that an exception is thrown if <code>null</code> is returned.
     */
    @Test
    public void createNull() {
        // setup
        expect(mockFactory.create(String.class)).andReturn(null);

        mocks.replayAll();

        // exercise
        expectThrows(ObjectCreationException.class, () -> factory.create(String.class));

        // verify
        mocks.verifyAll();
    }

}
