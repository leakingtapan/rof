package com.amazon.mqa.datagen.rof.typed;

import static org.easymock.EasyMock.expect;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import com.amazon.mqa.datagen.rof.ObjectFactory;
import com.amazon.mqa.datagen.supplier.AlphanumericStringSupplier;
import com.amazon.mtqa.testutil.MockObjectContainer;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Unit test for {@link DefaultTypedObjectFactory}.
 */
public final class DefaultTypedObjectFactoryTest {

    /** Creates random string. */
    private final Supplier<String> stringFactory = new AlphanumericStringSupplier();

    /** Contains mocks. */
    private MockObjectContainer mocks;

    /** Mock object factory. */
    private ObjectFactory mockObjectFactory;

    /** Mock collection factory. */
    private CollectionFactory mockCollectionFactory;

    /** Mock map factory. */
    private MapFactory mockMapFactory;

    /** Mock optional factory. */
    private OptionalFactory mockOptionalFactory;

    /** Object under test. */
    private TypedObjectFactory factory;

    /** Set up. */
    @BeforeMethod
    public void setUp() {
        mocks = new MockObjectContainer();

        mockObjectFactory = mocks.createMock("mockObjectFactory", ObjectFactory.class);
        mockCollectionFactory = mocks.createMock("mockCollectionFactory", CollectionFactory.class);
        mockMapFactory = mocks.createMock("mockMapFactory", MapFactory.class);
        mockOptionalFactory = mocks.createMock("mockOptionalFactory", OptionalFactory.class);

        factory = new DefaultTypedObjectFactory(mockObjectFactory, mockCollectionFactory,
                mockMapFactory, mockOptionalFactory);
    }

    /**
     * Tests creating regular object from its type.
     */
    @Test
    public void testCreateClass() {
        // set up
        final Class<String> clazz = String.class;
        final String expected = stringFactory.get();

        expect(mockObjectFactory.create(clazz)).andReturn(expected);

        mocks.replayAll();

        // exercise
        final Object actual = factory.create(clazz);

        // verify
        assertEquals(actual, expected, "wrong object");
        mocks.verifyAll();
    }

    /**
     * Tests creating collection.
     */
    @Test
    public void testCreateCollection() {
        // set up
        final List<Integer> list = new ArrayList<>();
        final List<Integer> expected = Lists.newArrayList(1, 2, 3);

        expect(mockCollectionFactory.create(EasyMock.eq(List.class), EasyMock.isA(TypeVariable.class)))
                .andReturn(expected);

        mocks.replayAll();

        // exercise
        final Object actual = factory.create(list.getClass().getGenericInterfaces()[0]);

        // verify
        assertEquals(actual, expected);

        mocks.verifyAll();
    }

    /**
     * Tests creating map.
     */
    @Test
    public void testCreateMap() {
        // set up
        final Map<Integer, String> map = Maps.newHashMap();
        final Map<Integer, String> expected = Maps.newHashMap();

        expect(mockMapFactory.create(EasyMock.isA(TypeVariable.class), EasyMock.isA(TypeVariable.class)))
                .andReturn(expected);

        mocks.replayAll();

        // exercise
        final Object actual = factory.create(map.getClass().getGenericInterfaces()[0]);

        // verify
        assertEquals(actual, expected, "wrong map");
        mocks.verifyAll();
    }

    /**
     * Tests creating optional.
     *
     * @throws Exception for any failure
     */
    @Test
    public void testCreateOptional() throws Exception {
        // set up
        final Optional<Integer> expected = Optional.of(1);
        final Object container = new Object() {
            Optional<Integer> optional;
        };

        expect(mockOptionalFactory.create(Integer.class)).andReturn(expected);

        mocks.replayAll();

        // exercise
        final Object actual = factory.create(container.getClass().getDeclaredField("optional").getGenericType());

        // verify
        assertEquals(actual, expected, "wrong optional");
        mocks.verifyAll();
    }

    /**
     * Tests creating unsupported type.
     */
    @Test
    public void testCreateUnsupportedType() {
        // set up
        final TypeVariable mockType = mocks.createMock("typeVariable", TypeVariable.class);

        // exercise
        final Object actual = factory.create(mockType);

        // verify
        assertNull(actual);
    }
}