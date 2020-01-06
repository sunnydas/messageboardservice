package com.sunny.service.messageboard.exception;

/**
 * This class represents Exception messages being used.
 * This can be externalized to message bundles in the future.
 */
public class ExceptionMessages {

    public final static String BUSINESS_CONSTRAINTS_VIOLATED = "Business constraints violated,check for unique,primary " +
            "or foreign key violations";

    public final static String VALIDATION_ERROR_BASE_TEXT = "Validation error %s";

    public final static String RESOURCE_NOT_FOUND = "Resource not found %s";

}
