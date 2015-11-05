package com.amazon.mqa.datagen.rof.typed;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Unit test for {@link CollectionInstanceProvider}.
 */
public final class CollectionInstanceProviderTest {

    /** Instance under test. */
    private final CollectionInstanceProvider provider = DefaultCollectionInstanceProvider.INSTANCE;

    /**
     * Test data.
     *
     * @return the data.
     */
    @DataProvider
    private Object[][] data() {
        return new Object[][] {
                {List.class, ArrayList.class},
                {Set.class, HashSet.class},
                {Collection.class, ArrayList.class}
        };
    }

    /**
     * Tests providing instance of collection.
     *
     * @param inputClass input class.
     * @param expectedClass expected class.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "data")
    public void testProvide(final Class<? extends Collection> inputClass, final Class expectedClass) {
        checkNotNull(inputClass, "inputClass cannot be null");
        checkNotNull(expectedClass, "expectedClass cannot be null");

        // exercise
        final Collection actual = provider.provide(inputClass);

        // verify
        assertNotNull(actual);
        assertEquals(actual.getClass(), expectedClass, "wrong collection instance");
    }

    /**
     * Tests providing unsupported of collection.
     *
     * @throws Exception if any problem occurs.
     */
    @Test
    public void testProvideUnSupportedCollectionType() throws Exception {
        // exercise
        final Collection actual = provider.provide(LinkedList.class);

        // verify
        assertNull(actual);
    }
}