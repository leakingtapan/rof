/**
 *
 */
package com.amazon.mtqa.testutil;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.easymock.EasyMock;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * <p>Holds all the mocks that are used in a test.  If you have a test that uses several mocks, it can be a 
 * nuisance to have to keep track of them.  There is also the danger that you will forget to put a mock in replay 
 * mode or forget to verify a mock after the test is over.  If you use a {@link MockObjectContainer} to hold all 
 * your mocks, you won't have to worry about either of these things.</p>
 *
 * <p>If you use this container, the only changes you need to make when writing tests are:
 * <ul>
 *   <li>create a {@link MockObjectContainer} in <code>setUp</code> and store it in a field.
 *   <li>call <code>container.createMock("mocked", MockMe.class)</code> instead of 
 *       <code>EasyMock.createMock("mocked", MockMe.class)</code>
 *   </li>
 *   <li>call <code>container.replayAll()</code> instead of <code>EasyMock.replay(x, y, z)</code></li>
 *   <li>call <code>container.verifyAll()</code> instead of <code>EasyMock.verify(x, y, z)</code></li>
 * </ul>
 * </p>
 */
public final class MockObjectContainer {

    /** The interface mocks. */
    private final Set<Object> mocks = Sets.newHashSet();

    /** The class mocks. */
    private final Set<Object> classMocks = Sets.newHashSet();

    /**
     * Creates a new mock.
     *
     * @param <T> the interface to mock
     * @param name the name to assign to the mock.  This name will appear in error messages to identify the mock.
     * @param toMock the interface to mock
     * @return a new mock
     * @throws IllegalArgumentException if an argument is <code>null</code>
     */
    public <T> T createMock(final String name, final Class<T> toMock) {
        Validate.notNull(name, "null mock name");
        Validate.notNull(toMock, "null class to mock");

        final T mock = EasyMock.createMock(name, toMock);
        mocks.add(mock);

        return mock;
    }

    /**
     * Creates several mocks.
     *
     * @param <T> the interface to mock
     * @param baseName the base name for each mock.  The actual name is a unique name based on this name. 
     * @param toMock the interface to mock
     * @param howMany the number of instances to create
     * @return the requested number of mocks
     * @throws IllegalArgumentException if an argument is <code>null</code> or the number of mocks to create is
     *          negative
     */
    public <T> List<T> createMocks(final String baseName, final Class<T> toMock, final int howMany) {
        Validate.notNull(baseName, "null name");
        Validate.notNull(toMock, "null class to mock");
        Validate.isTrue(howMany >= 0, "negative number of mocks to create");

        final List<T> result = Lists.newArrayList();
        for (int i = 0; i < howMany; i++) {
            result.add(createMock(baseName + "_" + i, toMock));
        }

        return result;
    }

    /**
     * Creates several mocks for a class.
     *
     * @param <T> the interface to mock
     * @param baseName the base name for each mock.  The actual name is a unique name based on this name. 
     * @param toMock the interface to mock
     * @param howMany the number of instances to create
     * @return the requested number of mocks
     * @throws IllegalArgumentException if an argument is <code>null</code> or the number of mocks to create is
     *          negative
     */
    public <T> List<T> createMocksForClass(final String baseName, final Class<T> toMock, final int howMany) {
        Validate.notNull(baseName, "null name");
        Validate.notNull(toMock, "null class to mock");
        Validate.isTrue(howMany >= 0, "negative number of mocks to create");

        final List<T> result = Lists.newArrayList();
        for (int i = 0; i < howMany; i++) {
            result.add(createMockForClass(baseName + "_" + i, toMock));
        }

        return result;
    }

    /**
     * Creates a new mock for a class.
     *
     * @param <T> the class to mock
     * @param name the name to assign to the mock.
     * @param toMock the class to mock
     * @return a new mock
     * @throws IllegalArgumentException if an argument is <code>null</code>
     */
    public <T> T createMockForClass(final String name, final Class<T> toMock) {
        Validate.notNull(name, "null mock name");
        Validate.notNull(toMock, "null class to mock");

        final T mock = org.easymock.classextension.EasyMock.createMock(name, toMock);
        classMocks.add(mock);

        return mock;
    }

    /**
     * Puts all of the mocks in 'replay' mode.
     */
    public void replayAll() {
        EasyMock.replay(mocks.toArray(new Object[mocks.size()]));
        org.easymock.classextension.EasyMock.replay(classMocks.toArray(new Object[classMocks.size()]));
    }

    /**
     * Verifies all of the mocks, failing the test if any expected method calls were not made.
     */
    public void verifyAll() {
        EasyMock.verify(mocks.toArray(new Object[mocks.size()]));
        org.easymock.classextension.EasyMock.verify(classMocks.toArray(new Object[classMocks.size()]));
    }

}
