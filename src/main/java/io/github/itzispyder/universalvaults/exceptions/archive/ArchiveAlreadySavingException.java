package io.github.itzispyder.universalvaults.exceptions.archive;

import io.github.itzispyder.universalvaults.exceptions.ArchiveException;

/**
 * Thrown when the server tries to save the archive when it is already saving
 */
public class ArchiveAlreadySavingException extends ArchiveException {

    /**
     * Constructs a new ArchiveAlreadySaving
     */
    public ArchiveAlreadySavingException() {
        super("Cannot save the archive as it is already saving!");
    }

    /**
     * Constructs a new ArchiveAlreadySaving with a custom message
     * @param errorMessage error message
     */
    public ArchiveAlreadySavingException(String errorMessage) {
        super(errorMessage);
    }
}
