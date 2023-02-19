package io.github.itzispyder.universalvaults.exceptions.archive;

import io.github.itzispyder.universalvaults.exceptions.ArchiveException;

/**
 * Thrown when an item duplicates in the archive
 */
public class ArchiveDuplicateException extends ArchiveException {

    /**
     * Constructs a new ArchiveDuplicateException
     */
    public ArchiveDuplicateException() {
        super("Attempted to add a duplicate item to an archive!");
    }

    /**
     * Constructs a new ArchiveDuplicateException with a custom message
     * @param errorMessage error message
     */
    public ArchiveDuplicateException(String errorMessage) {
        super(errorMessage);
    }
}
