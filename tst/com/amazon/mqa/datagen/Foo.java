package com.amazon.mqa.datagen;

import static java.util.Objects.requireNonNull;

/**
 * Test interface.
 */
public interface Foo {

    /**
     * A class that presents a circular dependency on a type.
     */
    static final class CircularFoo implements Foo {

        /** The Foo reference. */
        private final Foo foo;

        /**
         * Instantiates a new {@link CircularFoo}.
         *
         * @param foo - the Foo reference.
         * @throws NullPointerException if an argument is <code>null</code>.
         */
        public CircularFoo(final Foo foo) {
            this.foo = requireNonNull(foo, "foo cannot be null");
        }

        /** @return the Foo reference. */
        public Foo getFoo() {
            return foo;
        }

    }

}
