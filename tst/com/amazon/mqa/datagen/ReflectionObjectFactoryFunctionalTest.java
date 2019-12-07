package com.amazon.mqa.datagen;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amazon.mqa.datagen.Foo.CircularFoo;
import com.amazon.mqa.datagen.rof.ObjectCreationException;
import com.amazon.mqa.datagen.supplier.MinMaxIntegerSupplier;

/**
 * Functional test for {@link ReflectionObjectFactory}.
 */
public final class ReflectionObjectFactoryFunctionalTest {

    /**
     * Test primitive object classes.
     *
     * @return the classes.
     */
    @DataProvider
    private Object[][] primitiveObjectClasses() {
        return new Object[][] {
            {Boolean.class},
            {Byte.class},
            {Character.class},
            {Double.class},
            {Float.class},
            {Integer.class},
            {Long.class},
            {Short.class},
            {String.class}
        };
    }

    /**
     * Tests creating primitive object.
     *
     * @param clazz class of primitive object.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "primitiveObjectClasses")
    public void testCreatePrimitiveObject(final Class clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        // exercise
        final Object object = ReflectionObjectFactory.createObject(clazz);

        // verify
        assertNotNull(object);
    }

    /**
     * Test primitive classes.
     *
     * @return the classes.
     */
    @DataProvider
    private Object[][] primitiveClasses() {
        return new Object[][] {
                {boolean.class},
                {byte.class},
                {char.class},
                {double.class},
                {float.class},
                {int.class},
                {long.class},
                {short.class}
        };
    }

    /**
     * Tests creating create primitives.
     *
     * @param clazz class of primitive.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "primitiveClasses")
    public void testCreatePrimitive(final Class clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        // exercise
        final Object object = ReflectionObjectFactory.createObject(clazz);

        // verify
        assertNotNull(object, "object is null");
    }

    /**
     * Test primitive array classes.
     *
     * @return the classes.
     */
    @DataProvider
    private Object[][] primitiveArray() {
        return new Object[][] {
                {boolean[].class},
                {Boolean[].class},
                {byte[].class},
                {Byte[].class},
                {char[].class},
                {Character[].class},
                {double[].class},
                {Double[].class},
                {float[].class},
                {Float[].class},
                {int[].class},
                {Integer[].class},
                {long[].class},
                {Long[].class},
                {short[].class},
                {Short[].class},
                {int[][][].class},
                {String[].class}
        };
    }

    /**
     * Tests creating array.
     *
     * @param clazz class of primitive array.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "primitiveArray")
    public void testRandomArray(final Class clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        // exercise
        final Object object = ReflectionObjectFactory.createObject(clazz);

        // verify
        assertEquals(object.getClass(), clazz, "wrong class");
    }

    /**
     * Test basic object classes.
     *
     * @return the classes.
     */
    @DataProvider
    private Object[][] basicObjectClasses() {
        return new Object[][] {
                {Date.class},
                {BigInteger.class},
                {BigDecimal.class}
        };
    }

    /**
     * Tests creating basic object.
     *
     * @param clazz class of primitive object.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "basicObjectClasses")
    public void testCreateBasicObject(final Class clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        // exercise
        final Object object = ReflectionObjectFactory.createObject(clazz);

        // verify
        assertNotNull(object);
    }

    /**
     * Test enum classes.
     *
     * @return the classes.
     */
    @DataProvider
    private Object[][] enumClasses() {
        return new Object[][] {
                {EnumClass.class},
                {EnumClass[].class},
                {EnumClass[][].class}
        };
    }

    /**
     * Tests creating enumeration class.
     *
     * @param clazz the enum class.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "enumClasses")
    public void testRandomEnum(final Class clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        // exercise
        final Object object = ReflectionObjectFactory.createObject(clazz);

        // verify
        assertEquals(object.getClass(), clazz, "wrong class");
    }

    /**
     * Test object classes.
     *
     * @return the classes.
     */
    @DataProvider
    private Object[][] objectClasses() {
        return new Object[][] {
                {TestClassA.class},
                {TestClassA[].class},
                {TestClassB.class},
                {TestClassB[].class},
        };
    }

    /**
     * Tests creating object.
     *
     * @param clazz the object class.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    @Test(dataProvider = "objectClasses")
    public void testCreateObject(final Class clazz) {
        checkNotNull(clazz, "clazz cannot be null");

        // exercise
        final Object object = ReflectionObjectFactory.createObject(clazz);

        //verify
        assertEquals(object.getClass(), clazz, "wrong class");
    }

    /**
     * Tests creating array with configured array size.
     */
    @Test
    public void testCreateWithConfiguredArraySize() {
        // set up
        final int minSize = 100;
        final int maxSize = 105;
        final Config config = Config.createDefault()
                .withArraySizeSupplier(new MinMaxIntegerSupplier(minSize, maxSize));
        final ObjectFactory withArraySize = new ReflectionObjectFactory(config);

        // exercise
        final String[] randomStrings = withArraySize.create(String[].class);

        // verify
        assertTrue(randomStrings.length >= minSize);
        assertTrue(randomStrings.length <  maxSize);
    }

    /**
     * Tests creating proxy object for interface.
     *
     * @throws Exception if any problem occurs.
     */
    @Test
    public void testCreateForInterface() throws Exception {
        // exercise
        final InterfaceA obj = new ReflectionObjectFactory().create(InterfaceA.class);

        // verify
        assertNotNull(obj);
        assertNotNull(obj.getString());
        assertNotNull(obj.getInt());
        assertNotNull(obj.getTestClassA());
    }

    /**
     * Tests creating proxy object with fixed return value for proxy method.
     *
     * @throws Exception if any problem occurs.
     */
    @Test
    public void testCreateForInterfaceWithFixValue() throws Exception {
        // set up
        final int constant = 100;
        final ObjectFactory factory =
                new ReflectionObjectFactory(
                        Config.createDefault()
                                .withSupplier("getInt", () -> constant)
                );

        // exercise
        final InterfaceA object = factory.create(InterfaceA.class);

        // verify
        assertEquals(object.getInt(), constant);
    }

    //CHECKSTYLE:SUPPRESS:IllegalType
    /**
     * Tests creating proxy object for abstract class.
     *
     * @throws Exception if any problem occurs.
     */
    @Test
    public void testCreateForAbstractClass() throws Exception {
        // exercise
        final AbstractClassA obj = new ReflectionObjectFactory().create(AbstractClassA.class);

        // verify
        assertNotNull(obj.getAbstractDouble());
    }

    /**
     * Tests creating proxy object with fixed return value for abstract class.
     *
     * @throws Exception if any problem occurs.
     */
    @Test
    public void testCreateForAbstractClassWithFixValue() throws Exception {
        // set up
        final double constant = 1.0;
        final ObjectFactory factory =
                new ReflectionObjectFactory(
                        Config.createDefault()
                                .withSupplier("getAbstractDouble", () -> constant)
                );

        // exercise
        final AbstractClassA obj = factory.create(AbstractClassA.class);

        // verify
        assertEquals(obj.getAbstractDouble(), constant);
        assertEquals(obj.getString(), "abstract class A");
    }


    /**
     * Tests creating proxy object for abstract class which doesn't have no argument constructor.
     *
     * @throws Exception if any problem occurs.
     */
    @Test(expectedExceptions = ObjectCreationException.class)
    public void testCreateForAbstractClassB() throws Exception {
        // exercise
        final AbstractClassB obj = ReflectionObjectFactory.createObject(AbstractClassB.class);

        // verify
        assertNotNull(obj);
    }
    //CHECKSTYLE:UNSUPPRESS:IllegalType

    /**
     * Creates creating an interface that uses generic types through extension.
     */
    @Test
    public void testInterfaceTypeWithGenerics() {
        // exercise
        final Baz actual = ReflectionObjectFactory.createObject(Baz.class);

        // verify
        assertNotNull(actual);
        assertTrue(actual.getType() instanceof String);
    }

    /**
     * Tests creating a type with a circular type reference.
     */
    @Test
    public void testCircularInterfaceType() {
        // exercise
        final Foo foo = ReflectionObjectFactory.createObject(Foo.class);
        final CircularFoo circularFoo = ReflectionObjectFactory.createObject(Foo.CircularFoo.class);

        // verify
        assertNotNull(foo, "foo");
        assertNotNull(circularFoo, "circular foo");
        assertNotNull(circularFoo.getFoo(), "circular foo foo");

    }

}
