package com.webforj.samples.utils.annotations;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for running tests against multiple browsers
 * Use this annotation on test methods to run them in all configured browsers
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(MultiBrowserExtension.class)

public @interface BrowserTest {
    String[] value() default {};
}