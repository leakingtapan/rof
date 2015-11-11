package com.amazon.mqa.datagen.rof;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Thrown when failed to create object.
 */
class ObjectCreationException extends RuntimeException {

    /** Serial version ID. */
    private static final long serialVersionUID = 3194143595059914810L;

    /**
     * Instantiates a new {@link ObjectCreationException}.
     *
     * @param message the error message.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public ObjectCreationException(final String message) {
        super(checkNotNull(message, "message cannot be null"));
    }

    /**
     * Instantiates a new {@link ObjectCreationException}.
     *
     * @param message the error message.
     * @param cause the cause, may be <code>null</code>.
     * @throws NullPointerException if any argument is <code>null</code>.
     */
    public ObjectCreationException(final String message, final Throwable cause) {
        super(checkNotNull(message, "message cannot be null"), cause);
    }

}
