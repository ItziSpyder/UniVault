package io.github.itzispyder.universalvaults.exceptions.archive;

import io.github.itzispyder.universalvaults.exceptions.ArchiveException;

/**
 * Thrown when the server tries to load the archive when it is already loading
 */
public class ArchiveAlreadyLoadingException extends ArchiveException {

    /**
     * Constructs a new ArchiveAlreadyLoading
     */
    public ArchiveAlreadyLoadingException() {
        super("Cannot load/reload the archive as it is already loading/reloading!");
    }

    /**
     * Constructs a new ArchiveAlreadyLoading with a custom message
     * @param errorMessage error message
     */
    public ArchiveAlreadyLoadingException(String errorMessage) {
        super(errorMessage);
    }
}
