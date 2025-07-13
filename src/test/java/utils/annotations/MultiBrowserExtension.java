package utils.annotations;

import config.RunConfig;

import org.junit.jupiter.api.extension.*;
import utils.LoggerUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Stream;

/**
 * JUnit 5 extension for running tests against multiple browsers
 */
public class MultiBrowserExtension implements TestTemplateInvocationContextProvider {
    // Semaphore to limit concurrent browser instances to 8
    private static final Semaphore BROWSER_SEMAPHORE = new Semaphore(8, true);

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        boolean directAnnotation = context.getTestMethod()
                .map(method -> method.isAnnotationPresent(BrowserTest.class))
                .orElse(false);

        if (directAnnotation) {
            return true;
        }

        return context.getTestClass()
                .map(clazz -> clazz.isAnnotationPresent(BrowserTest.class))
                .orElse(false);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        LoggerUtil.info("Setting up multi-browser test contexts");

        BrowserTest browserTest = context.getRequiredTestMethod().getAnnotation(BrowserTest.class);
        List<String> browsers = getBrowsers(browserTest);

        return browsers.stream()
                .map(BrowserTestInvocationContext::new);
    }

    private List<String> getBrowsers(BrowserTest browserTest) {
        if (browserTest.value().length > 0) {
            return Arrays.asList(browserTest.value());
        }
        return RunConfig.getBrowsers();
    }

    private static class BrowserTestInvocationContext implements TestTemplateInvocationContext {
        private final String browser;

        public BrowserTestInvocationContext(String browser) {
            this.browser = browser;
        }

        @Override
        public String getDisplayName(int invocation) {
            return String.format("Browser: %s", browser);
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return Arrays.asList(
                    new BrowserParameterResolver(browser),
                    new BrowserTestExecutionHandler(),
                    new BrowserSemaphoreExtension()
            );
        }

        private static class BrowserParameterResolver implements ParameterResolver {
            private final String browser;

            public BrowserParameterResolver(String browser) {
                this.browser = browser;
            }

            @Override
            public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                return parameterContext.getParameter().getType() == String.class &&
                        parameterContext.getParameter().getName().equals("browserType");
            }

            @Override
            public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
                return browser;
            }
        }

        private static class BrowserSemaphoreExtension implements BeforeEachCallback, AfterEachCallback {
            @Override
            public void beforeEach(ExtensionContext context) throws Exception {
                BROWSER_SEMAPHORE.acquire();
            }

            @Override
            public void afterEach(ExtensionContext context) {
                BROWSER_SEMAPHORE.release();
            }
        }
    }
}