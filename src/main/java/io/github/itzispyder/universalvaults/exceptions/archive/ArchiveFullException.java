package io.github.itzispyder.universalvaults.exceptions.archive;

import io.github.itzispyder.universalvaults.exceptions.ArchiveException;
import io.github.itzispyder.universalvaults.server.plugin.misc.ItziSpyder;

/**
 * This exception is thrown when you attempt to add
 * a new item to a full archive.
 */
@ItziSpyder
public class ArchiveFullException extends ArchiveException {

    /**
     * Constructs a new ArchiveFullException
     */
    public ArchiveFullException() {
        super("Attempted to add an item to a full archive!");
    }

    /**
     * Constructs a new ArchiveFullException with a custom message
     * @param errorMessage error message
     */
    public ArchiveFullException(String errorMessage) {
        super(errorMessage);
    }
}
