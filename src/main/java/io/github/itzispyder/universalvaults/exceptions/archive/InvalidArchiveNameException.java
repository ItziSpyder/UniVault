package io.github.itzispyder.universalvaults.exceptions.archive;

import io.github.itzispyder.universalvaults.exceptions.ArchiveException;

/**
 * Thrown when an archive is null or invalid
 */
public class InvalidArchiveNameException extends ArchiveException {

    /**
     * Constructs a new InvalidArchiveNameException
     */
    public InvalidArchiveNameException() {
        super("Do not contain any non-alphabet or capital characters!");
    }

    /**
     * Constructs a new InvalidArchiveNameException with a custom message
     * @param errorMessage error message
     */
    public InvalidArchiveNameException(String errorMessage) {
        super(errorMessage);
    }
}
