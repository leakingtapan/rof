package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkArgument;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.testng.Assert.assertSame;

import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.amazon.mtqa.testutil.DataProviders;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Unit tests for the {@link ReflectionObjectFactory} class.
 */
public final class ReflectionObjectFactoryTest {

    /** Creates random numbers. */
    private final Supplier<Integer> integerFactory = new MinMaxIntegerSupplier(1, 10);

    /** Holds the mocks. */
    private MockObjectContainer mocks;

    /** Mock object factory for single objects. */
    private com.amazon.mqa.datagen.rof.ObjectFactory mockObjectFactory;

    /** Mock collection size supplier. */
    private Supplier<Integer> collectionSizeSupplier;

    /** Object factory to test. */
    private ObjectFactory reflectionObjectFactory;

    /**
     * Test set up.
     */
    @SuppressWarnings("unchecked")
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();

        mockObjectFactory = mocks.createMock("mockObjectFactory", com.amazon.mqa.datagen.rof.ObjectFactory.class);
        collectionSizeSupplier = mocks.createMock("collectionSizeSupplier", Supplier.class);

        reflectionObjectFactory = new ReflectionObjectFactory(collectionSizeSupplier, mockObjectFactory);
    }

    /**
     * Tests creating a single object.
     */
    @Test
    public void testCreate() {
        // set up
        final UUID expected = expectCreateObject();
        mocks.replayAll();

        // exercise
        final UUID actual = reflectionObjectFactory.create(UUID.class);

        // verify
        assertSame(actual, expected);
        mocks.verifyAll();
    }

    /**
     * Tests creating a list of objects when providing a size.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testListOfExplicitSize(final int size) {
        checkArgument(size >= 0, "size can't be negative");

        // set up
        final List<UUID> expected = expectCreateObjects(size);
        mocks.replayAll();

        // exercise
        final List<UUID> actual = reflectionObjectFactory.listOf(UUID.class, size);

        // verify
        assertThat(actual, equalTo(expected));
        mocks.verifyAll();
    }

    /**
     * Tests creating a list of objects using the default size.
     *
     * @param size the size to test.
     * @throws NullPointerException if the argument is <code>null</code>.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testListOfDefaultSize(final int size) {
        checkArgument(size >= 0, "size can't be negative");

        // set up
        expect(collectionSizeSupplier.get()).andReturn(size);
        final List<UUID> expected = expectCreateObjects(size);
        mocks.replayAll();

        // exercise
        final List<UUID> actual = reflectionObjectFactory.listOf(UUID.class);

        // verify
        assertThat(actual, equalTo(expected));
        mocks.verifyAll();
    }

    /**
     * Tests creating a set of objects when explicitly providing the size.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testSetOfExplicitSize(final int size) {
        checkArgument(size >= 0, "size can't be negative");

        // set up
        final Set<UUID> expected = ImmutableSet.copyOf(expectCreateObjects(size));
        mocks.replayAll();

        // exercise
        final Set<UUID> actual = reflectionObjectFactory.setOf(UUID.class, size);

        // verify
        assertThat(actual, equalTo(expected));
        mocks.verifyAll();
    }

    /**
     * Tests creating a set of objects with the default size.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testSetOfDefaultSize(final int size) {
        checkArgument(size >= 0, "size can't be negative");

        // set up
        expect(collectionSizeSupplier.get()).andReturn(size);
        final Set<UUID> expected = ImmutableSet.copyOf(expectCreateObjects(size));
        mocks.replayAll();

        // exercise
        final Set<UUID> actual = reflectionObjectFactory.setOf(UUID.class);

        // verify
        assertThat(actual, equalTo(expected));
        mocks.verifyAll();
    }

    /**
     * Tests creating a map of objects when explicitly providing the size.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testMapOfExplicitSize(final int size) {
        checkArgument(size >= 0, "size can't be negative");

        // set up
        final Map<UUID, Integer> expected = Maps.newHashMap();
        for (int i = 0; i < size; i++) {
            final UUID key = UUID.randomUUID();
            final int value = integerFactory.get();

            expect(reflectionObjectFactory.create(UUID.class)).andReturn(key);
            expect(reflectionObjectFactory.create(Integer.class)).andReturn(value);

            expected.put(key, value);
        }

        mocks.replayAll();

        // exercise
        final Map<UUID, Integer> actual = reflectionObjectFactory.mapOf(UUID.class, Integer.class, size);

        // verify
        assertThat(actual, equalTo(expected));
        mocks.verifyAll();
    }

    /**
     * Tests creating a map of objects with the default size.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testMapOfDefaultSize(final int size) {
        checkArgument(size >= 0, "size can't be negative");

        // set up
        expect(collectionSizeSupplier.get()).andReturn(size);
        final Map<UUID, Integer> expected = Maps.newHashMap();
        for (int i = 0; i < size; i++) {
            final UUID key = UUID.randomUUID();
            final int value = integerFactory.get();

            expect(reflectionObjectFactory.create(UUID.class)).andReturn(key);
            expect(reflectionObjectFactory.create(Integer.class)).andReturn(value);

            expected.put(key, value);
        }

        mocks.replayAll();

        // exercise
        final Map<UUID, Integer> actual = reflectionObjectFactory.mapOf(UUID.class, Integer.class);

        // verify
        assertThat(actual, equalTo(expected));
        mocks.verifyAll();
    }

    /**
     * Tests the static <code>createObject</code> method.
     */
    @Test
    public void testCreateObject() {
        // exercise
        final String actual = ReflectionObjectFactory.createObject(String.class);

        // verify
        assertThat("string", actual, not(nullValue()));
        assertThat("string", actual.length(), greaterThan(1));
    }

    /**
     * Tests that the no-argument constructor doesn't throw an exception.
     */
    @Test
    public void testNoArgConstructor() {
        new ReflectionObjectFactory();
    }

    /**
     * Configures the mock object factory to expect to create a single object.
     *
     * @return the expected object.
     */
    private UUID expectCreateObject() {
        final UUID expected = UUID.randomUUID();
        expect(mockObjectFactory.create(UUID.class)).andReturn(expected);

        return expected;
    }

    /**
     * Configures the mock object factory to expect to create several objects.
     *
     * @param numObjects the number of objects to expect.
     * @return the expected objects.
     */
    private List<UUID> expectCreateObjects(final int numObjects) {
        assert numObjects >= 0 : "negative number of objects";

        final List<UUID> expected = Lists.newArrayList();
        for (int i = 0; i < numObjects; i++) {
            expected.add(expectCreateObject());
        }

        return expected;
    }
}
