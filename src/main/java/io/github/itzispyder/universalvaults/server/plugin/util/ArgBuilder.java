package io.github.itzispyder.universalvaults.server.plugin.util;

import java.util.List;
import java.util.Set;

/**
 * Represents Argument builder
 */
public class ArgBuilder {

    private String result;

    /**
     * Constructs an argument builder.
     */
    public ArgBuilder() {
        this.result = " ";
    }

    /**
     * Constructs an argument builder with a string.
     * @param begin the beginner string
     */
    public ArgBuilder(String begin) {
        this.result = begin + " ";
    }

    /**
     * Appends a string
     * @param string string
     * @return this class
     */
    public ArgBuilder append(String string) {
        this.result += string + " ";
        return this;
    }

    /**
     * Appends a string array
     * @param args string array
     * @return this class
     */
    public ArgBuilder append(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) builder.append(arg).append(" ");
        this.result += builder.toString();
        return this;
    }

    /**
     * Appends a string array
     * @param args string array
     * @return this class
     */
    public ArgBuilder append(String[] args, int begin, int end) {
        StringBuilder builder = new StringBuilder();
        for (int i = begin; i < end; i ++)
            builder.append(args[i]).append(" ");
        this.result += builder.toString();
        return this;
    }

    /**
     * Appends a string list
     * @param args string list
     * @return this class
     */
    public ArgBuilder append(List<String> args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) builder.append(arg).append(" ");
        this.result += builder.toString();
        return this;
    }

    /**
     * Appends a string set
     * @param args string set
     * @return this class
     */
    public ArgBuilder append(Set<String> args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) builder.append(arg).append(" ");
        this.result += builder.toString();
        return this;
    }

    /**
     * Returns this class as a string
     * @return this class as a string
     */
    public String build() {
        return this.toString().trim();
    }

    @Override
    public String toString() {
        return result;
    }
}
