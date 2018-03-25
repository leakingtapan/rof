package com.amazon.mqa.datagen.rof;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.Assert.assertNotNull;

import com.amazon.mqa.datagen.Config;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Functional test for {@link PojoFactory}.
 */
public final class PojoFactoryFunctionalTest {

    /**
     * A class that is consisted of primitive field.
     */
    private static class ClassOnlyHasPrimitive {

        /** String field. */
        private final String str;

        /** Number field. */
        private final int number;

        /**
         * Instantiates a new {@link ClassOnlyHasPrimitive}.
         *
         * @param str string field.
         * @param number int field.
         * @throws NullPointerException if any argument is <code>null</code>.
         */
        ClassOnlyHasPrimitive(final String str, final int number) {
            checkNotNull(str, "str cannot be null");

            this.str = str;
            this.number = number;
        }
    }

    /**
     * Class has primitive list.
     */
    private static class ClassWithPrimitiveCollection {
        /** Double list. */
        private final List<Double> doubles;
        
        /**
         * Instantiates a new {@link ClassWithPrimitiveCollection}.
         *
         * @param doubles double list.
         * @throws NullPointerException if any argument is <code>null</code>.
         */
        ClassWithPrimitiveCollection(final List<Double> doubles) {
            this.doubles = checkNotNull(doubles, "doubles cannot be null");
        }
    }

    /** Instance under test. */
    private final ObjectFactory factory = PojoFactory.create(
            new BasicObjectFactory(Config.createDefault().getSuppliers()));

    /**
     * Test data.
     *
     * @return the data.
     */
    @DataProvider
    private Object[][] data() {
        return new Object[][] {
                {ClassOnlyHasPrimitive.class},
                {ClassWithPrimitiveCollection.class}
        };
    }

    /**
     * Tests creating class which only has primitive field.
     *
     * @param clazz the class to create.
     * @param <T> the type of class.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "data")
    public <T> void testCreateObject(final Class<T> clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        // exercise
        final T object = factory.create(clazz);

        // verify
        assertNotNull(object);
    }

}