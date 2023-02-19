package io.github.itzispyder.universalvaults.exceptions;

/**
 * This exception is thrown when you attempt to add
 * a new item to a full archive.
 */
public class InvalidInventoryPresetException extends Exception {

    /**
     * Constructs a new InvalidInventoryPreset
     */
    public InvalidInventoryPresetException() {
        super("Attempted to apply an invalid inventory preset to an inventory!");
    }

    /**
     * Constructs a new InvalidInventoryPreset with a custom message
     * @param errorMessage error message
     */
    public InvalidInventoryPresetException(String errorMessage) {
        super(errorMessage);
    }
}
