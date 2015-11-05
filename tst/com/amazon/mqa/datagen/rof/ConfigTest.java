package com.amazon.mqa.datagen.rof;

import static org.testng.Assert.assertEquals;

import com.amazon.mqa.datagen.Config;
import com.amazon.mqa.datagen.TestClassA;
import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * Unit test for {@link Config}.
 */
public final class ConfigTest {

    /** Contains mocks. */
    private MockObjectContainer mocks;

    /** Set up. */
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();
    }

    /**
     * Tests creating default config.
     */
    @Test
    public void testCreateDefaultConfig() {
        // set up
        final Config config = Config.createDefault();
        final int expectedSuppliersSize = 20;

        // exercise
        final Map<Class<?>, Supplier> suppliers = config.getSuppliers();

        // verify
        assertEquals(suppliers.size(), expectedSuppliersSize, "wrong size of supplier");
    }

    /**
     * Tests adding a new supplier.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testWithSupplier() {
        // set up
        final Supplier mockSupplier = mocks.createMock("mockSupplier", Supplier.class);
        final Config config = Config.createDefault();
        final int expectedSuppliersSize = 21;

        // exercise
        final Config actualConfig = config.withSupplier(TestClassA.class, mockSupplier);

        // verify
        final Map<Class<?>, Supplier> actualSuppliers = actualConfig.getSuppliers();
        assertEquals(actualSuppliers.size(), expectedSuppliersSize, "wrong suppliers size");
        assertEquals(actualSuppliers.get(TestClassA.class), mockSupplier, "wrong supplier");
    }

    /**
     * Tests overriding integer supplier.
     */
    @Test
    public void testWithIntSupplier() {
        // set up
        final Config config = Config.createDefault();
        final MinMaxIntegerSupplier integerSupplier = MinMaxIntegerSupplier.create();
        final int expectedSuppliersSize = 20;

        // exercise
        final Config actualConfig = config.withSupplier(Integer.class, integerSupplier);

        // verify
        final Map<Class<?>, Supplier> actualSuppliers = actualConfig.getSuppliers();
        assertEquals(actualSuppliers.size(), expectedSuppliersSize, "wrong suppliers size");
        assertEquals(actualSuppliers.get(Integer.class), integerSupplier, "wrong supplier");
    }
}