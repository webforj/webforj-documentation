package utils.annotations;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking tests that should be retried on failure
 * Use this annotation on test methods to enable automatic retry
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@TestTemplate
@ExtendWith(RetryTestExtension.class)
public @interface RetryTest {
    /**
     * Number of retry attempts
     * Defaults to the value in FrameworkConfig
     */
    int value() default 0;

    int maxRetries();
}