package com.amazon.mqa.datagen.rof.spy;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.Assert.assertEquals;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/** Unit test for {@link DefaultClassSpy}. */
public final class DefaultClassSpyTest {

    /**
     * A class with out constructor.
     */
    private static final class ClassWithoutConstructor { }

    /**
     * A class with a default constructor.
     */
    private static final class ClassWithNoArgumentConstructor {

        /**
         * Instantiates a new {@link ClassWithNoArgumentConstructor}.
         */
        ClassWithNoArgumentConstructor() { }

        /**
         * Test method.
         */
        public void ab() { }

        /**
         * Test method.
         */
        public void abc() { }
    }

    /**
     * A class with two constructors.
     */
    private static final class ClassWithTwoConstructor {

        /**
         * Instantiates a new {@link ClassWithTwoConstructor}.
         *
         * @param num dummy field.
         */
        ClassWithTwoConstructor(final int num) { }

        /**
         * Instantiates a new {@link ClassWithTwoConstructor}.
         *
         * @param num1 dummy field 1.
         * @param num2 dummy field 2.
         */
        ClassWithTwoConstructor(final int num1, final int num2) { }
    }

    /** Instance under test. */
    private final ClassSpy spy = new DefaultClassSpy();

    /**
     * Test data.
     *
     * @return the data.
     */
    @DataProvider
    private Object[][] classWithConstructor() {
        return new Object[][] {
                {ClassWithoutConstructor.class,
                    "private com.amazon.mqa.datagen.rof.spy.DefaultClassSpyTest$ClassWithoutConstructor()"},
                {ClassWithNoArgumentConstructor.class,
                    "com.amazon.mqa.datagen.rof.spy.DefaultClassSpyTest$ClassWithNoArgumentConstructor()"},
                {ClassWithTwoConstructor.class,
                    "com.amazon.mqa.datagen.rof.spy.DefaultClassSpyTest$ClassWithTwoConstructor(int)"}
        };
    }

    /**
     * Tests finding constructor for class with at least one constructor.
     *
     * @param clazz the class to test.
     * @param expectedSignature the expected constructor signature.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "classWithConstructor")
    public void testFindConstructor(final Class clazz, final String expectedSignature) {
        checkNotNull(clazz, "clazz cannot be null");
        checkNotNull(expectedSignature, "expectedSignature cannot be null");

        // exercise
        final Constructor constructor = spy.findConstructor(clazz);

        // verify
        assertEquals(constructor.toGenericString(), expectedSignature, "wrong signature");
    }

    /**
     * Test data for find method.
     *
     * @return test data.
     */
    @DataProvider
    private Object[][] methodPrefix() {
        return new Object[][] {
                {"ab", ImmutableSet.of("ab", "abc")},
                {"abc", ImmutableSet.of("abc")}
        };
    }

    /**
     * Tests finding methods with prefix.
     *
     * @param prefix method prefix.
     * @param methodNames expected method names.
     */
    @Test(dataProvider = "methodPrefix")
    public void testFindMethodsWithPrefix(final String prefix, final Set<String> methodNames) {
        checkNotNull(prefix, "prefix cannot be null");
        checkNotNull(methodNames, "methodNames cannot be null");

        // exercise
        final List<Method> methods = spy.findMethods(ClassWithNoArgumentConstructor.class, prefix);

        // verify
        assertEquals(methods.size(), methodNames.size(), "wrong methods size");

        final Set<String> actualMethodNames = Sets.newHashSet();
        for (final Method method : methods) {
            actualMethodNames.add(method.getName());
        }

        assertEquals(actualMethodNames, methodNames, "wrong method names");
    }

}
