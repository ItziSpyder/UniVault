package io.github.itzispyder.universalvaults.exceptions.command;

import org.bukkit.command.Command;

/**
 * Handles a command exception
 */
public class CmdExHandler {

    private Exception exception;
    private Command command;

    /**
     * Constructs the command exception
     * @param exception the exception caught
     * @param command the command run
     */
    public CmdExHandler(Exception exception, Command command) {
        this.exception = exception;
        this.command = command;
    }

    /**
     * Returns the error message
     * @return the error message
     */
    public String getErrorMessage() {
        String msg = "§cCommand Error: ";
        if (exception instanceof NullPointerException) msg += "Command contains a null value!";
        else if (exception instanceof IndexOutOfBoundsException) msg += "Unknown or incomplete command!";
        else msg += exception.getMessage();
        return msg +
                "\n§cCaused by: §7" + exception.getClass().getSimpleName() +
                "\n§cCorrect usage: §7" + command.getUsage();
    }

    public Exception getException() {
        return exception;
    }

    public Command getCommand() {
        return command;
    }
}