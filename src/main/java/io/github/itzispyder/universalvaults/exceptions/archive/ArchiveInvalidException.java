package io.github.itzispyder.universalvaults.exceptions.archive;

import io.github.itzispyder.universalvaults.exceptions.ArchiveException;

/**
 * Thrown when an archive is null or invalid
 */
public class ArchiveInvalidException extends ArchiveException {

    /**
     * Constructs a new ArchiveInvalidException
     */
    public ArchiveInvalidException() {
        super("Attempted to load an invalid archive!");
    }

    /**
     * Constructs a new ArchiveInvalidException with a custom message
     * @param errorMessage error message
     */
    public ArchiveInvalidException(String errorMessage) {
        super(errorMessage);
    }
}
