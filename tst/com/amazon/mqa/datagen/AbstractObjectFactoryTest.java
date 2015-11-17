package com.amazon.mqa.datagen;

import com.amazon.mtqa.testutil.DataProviders;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static org.testng.Assert.assertEquals;

/**
 * Test for abstract object factory.
 */
public final class AbstractObjectFactoryTest extends AbstractObjectFactory {

    /**
     * Instantiates a new {@link AbstractObjectFactoryTest}.
     */
    public AbstractObjectFactoryTest() {
        super(Config.createDefault().withSupplier(String.class, () -> "str"));
    }

    /**
     * Instantiates a new {@link AbstractObjectFactoryTest}.
     *
     * @param size the array size.
     */
    public AbstractObjectFactoryTest(final int size) {
        super(Config.createDefault().withArraySizeSupplier(() -> size));
    }

    /**
     * Tests creating constant string.
     */
    @Test
    public void testCreate() {
        // exercise
        final String actual = create(String.class);

        // verify
        assertEquals(actual, "str");
    }

    /**
     * Tests creating list of constant string.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testListOfDefaultSize(final int size) {
        checkArgument(size >= 0, "size cannot be negative");
        // exercise
        final List<String> actual = new AbstractObjectFactoryTest(size).listOf(String.class);

        // verify
        assertEquals(actual.size(), size);
    }

    /**
     * Tests creating list of constant string.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testListOfExplicitSize(final int size) {
        checkArgument(size >= 0, "size cannot be negative");

        // exercise
        final List<String> actual = listOf(String.class, size);

        // verify
        assertEquals(actual.size(), size);
    }

    /**
     * Tests creating set of random integers.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testSetOfDefaultSize(final int size) {
        checkArgument(size >= 0, "size cannot be negative");

        // exercise
        final Set<Integer> actual = new AbstractObjectFactoryTest(size).setOf(int.class, size);

        // verify
        assertEquals(actual.size(), size);
    }

    /**
     * Tests creating set of random integers.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testSetOfExplicitSize(final int size) {
        checkArgument(size >= 0, "size cannot be negative");

        // exercise
        final Set<Integer> actual = setOf(int.class, size);

        // verify
        assertEquals(actual.size(), size);
    }

    /**
     * Tests creating map of random integer to string.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testMapOfDefaultSize(final int size) {
        checkArgument(size >= 0, "size cannot be negative");

        // exercise
        final Map<Integer, String> actual = new AbstractObjectFactoryTest(size)
                .mapOf(int.class, String.class, size);

        // verify
        assertEquals(actual.size(), size);
    }

    /**
     * Tests creating map of random integer to string.
     *
     * @param size the desired size.
     * @throws IllegalArgumentException if the argument is negative.
     */
    @Test(dataProvider = "collectionSizes", dataProviderClass = DataProviders.class)
    public void testMapOfExplicitSize(final int size) {
        checkArgument(size >= 0, "size cannot be negative");

        // exercise
        final Map<Integer, String> actual = mapOf(int.class, String.class, size);

        // verify
        assertEquals(actual.size(), size);
    }
}
