package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkArgument;
import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;

import com.amazon.mqa.datagen.supplier.AlphanumericStringSupplier;
import com.amazon.mqa.datagen.supplier.RandomIntegerSupplier;
import com.amazon.mtqa.testutil.DataProviders;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Unit test for {@link DefaultMapFactory}.
 */
public final class DefaultMapFactoryTest {

    /** Creates random string. */
    private final Supplier<String> stringFactory = new AlphanumericStringSupplier();

    /** Creates random integers. */
    private final Supplier<Integer> integerFactory = new RandomIntegerSupplier();

    /** Contains mocks. */
    private MockObjectContainer mocks;

    /** Mock size supplier. */
    private Supplier<Integer> mockSizeSupplier;

    /** Mock typed object factory. */
    private TypedObjectFactory mockTypedObjectFactory;

    /** Class under test. */
    private MapFactory factory;

    /** Set up. */
    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();

        mockSizeSupplier = mocks.createMock("mockSizeSupplier", Supplier.class);
        mockTypedObjectFactory = mocks.createMock("mockTypedObjectFactory", TypedObjectFactory.class);

        factory = new DefaultMapFactory(mockTypedObjectFactory, mockSizeSupplier);
    }

    /**
     * Tests creating Map.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testCreateMap(final int size) {
        checkArgument(size >= 0, "size cannot be negative");

        // set up
        final Class<Integer> keyClazz = Integer.class;
        final Class<String> valueClazz = String.class;
        final Map<Integer, String> expected = Maps.newHashMap();

        expect(mockSizeSupplier.get()).andReturn(size);

        for (int i = 0; i < size; i++) {
            final Integer key = integerFactory.get();
            final String value = stringFactory.get();

            expect(mockTypedObjectFactory.create(keyClazz)).andReturn(key);
            expect(mockTypedObjectFactory.create(valueClazz)).andReturn(value);

            expected.put(key, value);
        }

        mocks.replayAll();

        // exercise
        final Map actual = factory.create(keyClazz, valueClazz);

        // verify
        assertEquals(actual.size(), size, "wrong map size");
        assertEquals(actual, expected, "wrong map");

        mocks.verifyAll();
    }

}