package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkArgument;
import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.amazon.mtqa.testutil.DataProviders;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;

/** Unit test for {@link DefaultCollectionFactory}. */
public final class DefaultCollectionFactoryTest {

    /** Creates random numbers. */
    private final Supplier<Integer> integerFactory = new MinMaxIntegerSupplier(1, 10);

    /** Contains mocks. */
    private MockObjectContainer mocks;

    /** Mock typed object factory. */
    private TypedObjectFactory mockTypedObjectFactory;

    /** Mock size supplier. */
    private Supplier<Integer> mockSizeSupplier;

    /** Mock collection instance provider. */
    private CollectionInstanceProvider mockProvider;

    /** Instance under test. */
    private CollectionFactory collectionFactory;

    /** Set up. */
    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();

        mockTypedObjectFactory = mocks.createMock("mockTypedObjectFactory", TypedObjectFactory.class);
        mockSizeSupplier = mocks.createMock("mockSizeSupplier", Supplier.class);
        mockProvider = mocks.createMock("mockProvider", CollectionInstanceProvider.class);

        collectionFactory = new DefaultCollectionFactory(mockTypedObjectFactory, mockSizeSupplier, mockProvider);
    }

    /**
     * Tests randomizing collection object.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @SuppressWarnings("unchecked")
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testRandom(final int size) {
        checkArgument(size >= 0, "size cannot be negative");

        // set up
        final Class<List> collectionClazz = List.class;
        final Class<Integer> elementClazz = Integer.class;
        final List<Integer> expected = Lists.newArrayList();

        expect(mockProvider.provide(collectionClazz)).andReturn(Lists.newArrayList());
        expect(mockSizeSupplier.get()).andReturn(size);

        for (int i = 0; i < size; i++) {
            final int value = integerFactory.get();
            expect(mockTypedObjectFactory.create(elementClazz)).andReturn(value);
            expected.add(value);
        }

        mocks.replayAll();

        // exercise
        final Collection<Integer> actual = collectionFactory.create(collectionClazz, elementClazz);

        // verify
        assertEquals(actual, expected, "wrong create collection");
        mocks.verifyAll();
    }

    /**
     * Tests creating unsupported collection.
     */
    @Test
    public void testCreateUnsupportedCollection() {
        // set up
        final Class<List> collectionClazz = List.class;
        final Class<Integer> elementClazz = Integer.class;

        expect(mockProvider.provide(collectionClazz)).andReturn(null);

        mocks.replayAll();

        // exercise
        final List<Integer> actual = collectionFactory.create(collectionClazz, elementClazz);

        // verify
        assertNull(actual);
        mocks.verifyAll();
    }

}
