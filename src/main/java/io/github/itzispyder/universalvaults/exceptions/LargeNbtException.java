package io.github.itzispyder.universalvaults.exceptions;

/**
 * Thrown when attempting to save a large nbt
 */
public class LargeNbtException extends Exception {

    /**
     * Constructs a new LargeNbtException
     */
    public LargeNbtException() {
        super("Nbt input too large!");
    }

    /**
     * Constructs a new LargeNbtException with a custom message.
     * @param errorMessage error message
     */
    public LargeNbtException(String errorMessage) {
        super(errorMessage);
    }
}
