package com.amazon.mtqa.testutil;

import org.testng.annotations.DataProvider;

/**
 * Provides some TestNG {@link DataProvider}s for common situations.
 */
public final class DataProviders {
    /** The largest size collection to use. */
    static final int MAX_COLLECTION_SIZE = 10;

    /**
     * Provides strings to test.
     *
     * @return strings to test.
     */
    @DataProvider(name = "strings")
    public String[][] strings() {
        return new String[][] {
                { "" },
                { "abc" },
                { "abc123" },
                { " \t\n" },
        };
    }

    /**
     * Provides both Boolean values.
     *
     * @return both Boolean values
     */
    @DataProvider(name = "booleans")
    public static Boolean[][] booleans() {
        return new Boolean[][] {
                { false },
                { true }
        };
    }

    /**
     * Provides some common collection sizes to test, including an empty collection, a 1-element collection, 
     * and a few multi-element collections.
     *
     * @return some sizes you might want to use when testing something that operates on collections
     */
    @DataProvider(name = "collectionSizes")
    public static Integer[][] collectionSizes() {
        return new Integer[][] {
                { 0  },
                { 1  },
                { 2  },
                { MAX_COLLECTION_SIZE },
        };
    }

    /**
     * Provides some common collection sizes to test, excluding the empty collection.
     *
     * @return some sizes you might want to use when testing something that operates on collections
     */
    @DataProvider(name = "nonEmptycollectionSizes")
    public static Integer[][] nonEmptyCollectionSizes() {
        return new Integer[][] {
                { 1  },
                { 2  },
                { MAX_COLLECTION_SIZE },
        };
    }

    /**
     * Provides non-positive integers.
     *
     * @return some non-positive integers
     */
    @DataProvider(name = "nonPositiveIntegers")
    public static Integer[][] nonPositiveIntegers() {
        // CHECKSTYLE:SUPPRESS:Magic Number
        return new Integer[][] {
                { 0  },
                { -1 },
                { -2 },
        };
        // CHECKSTYLE:UNSUPPRESS:Magic Number
    }

    /**
     * No instantiations needed.
     */
    private DataProviders() {
    }
}
