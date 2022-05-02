package test.rsingh.schoolmessenger.config;

import test.rsingh.schoolmessenger.model.Email;

import java.util.LinkedList;
import java.util.List;

/**
 * A class that contains all global constants, not able to create of instance by any chance.
 */
public class Constants {
    /**
     * A unique constructor that prevent instantiating of this class.
     */
    private Constants() { throw new IllegalStateException("Instantiation of constants class is prohibited!"); }

    /**
     * A constant of the base path for every API
     */
    public static final String BASE_PATH = "/api/v1/sm";
    /**
     * A constant of the linked list used as a in-memory storage for email logs.
     */
    public static final List<Email> EMAIL_HISTORY = new LinkedList<>();
}