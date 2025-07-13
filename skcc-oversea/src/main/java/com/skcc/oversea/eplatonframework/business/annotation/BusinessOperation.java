package com.skcc.oversea.eplatonframework.business.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Business Operation Annotation for SKCC Oversea
 * 
 * Marks methods as business operations and provides metadata for logging,
 * monitoring, and documentation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessOperation {

    /**
     * Operation name
     */
    String value() default "";

    /**
     * Operation description
     */
    String description() default "";

    /**
     * Operation category
     */
    String category() default "";

    /**
     * Whether this operation requires transaction
     */
    boolean transactional() default true;

    /**
     * Whether this operation is read-only
     */
    boolean readOnly() default false;

    /**
     * Timeout in seconds
     */
    int timeout() default 300;

    /**
     * Whether to log this operation
     */
    boolean loggable() default true;

    /**
     * Whether to audit this operation
     */
    boolean auditable() default true;

    /**
     * Required permissions
     */
    String[] permissions() default {};

    /**
     * Error codes that can be thrown
     */
    String[] errorCodes() default {};
}