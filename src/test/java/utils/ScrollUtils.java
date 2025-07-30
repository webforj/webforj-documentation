package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.Map;

/**
 * Helper utility for scrolling-related operations
 */
public class ScrollUtils {
    private final Page page;

    public ScrollUtils(Page page) {
        this.page = page;
    }

    public static ScrollUtils forPage(Page page) {
        return new ScrollUtils(page);
    }

    public boolean isPageVerticallyScrollable() {
        return (Boolean) page.evaluate("() => document.documentElement.scrollHeight > document.documentElement.clientHeight");
    }

    public boolean isPageHorizontallyScrollable() {
        return (Boolean) page.evaluate("() => document.documentElement.scrollWidth > document.documentElement.clientWidth");
    }

    public void scrollToElement(String selector) {
        page.evaluate("(selector) => document.querySelector(selector).scrollIntoView()", selector);
    }

    public void scrollToElement(Locator element) {
        page.evaluate("(element) => element.scrollIntoView()", element);
    }

    public void scrollToBottom() {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToBottom(Page page) {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTop() {
        page.evaluate("() => window.scrollTo(0, 0)");
    }

    public void scrollPageBy(int x, int y) {
        page.evaluate("({x, y}) => window.scrollBy(x, y)", Map.of("x", x, "y", y));
    }


    public static void scrollPageBy(Page page, int x, int y) {
        page.evaluate("({x, y}) => window.scrollBy(x, y)", Map.of("x", x, "y", y));
    }


    public void scrollElementToBottom(String selector) {
        page.evaluate("(selector) => document.querySelector(selector).scrollTop = document.querySelector(selector).scrollHeight", selector);
    }


    public static void scrollElementToBottom(Page page, String selector) {
        page.evaluate("(selector) => document.querySelector(selector).scrollTop = document.querySelector(selector).scrollHeight", selector);
    }


    public boolean isElementScrollable(String selector) {
        return (Boolean) page.evaluate("(selector) => {\n" +
                "    const element = document.querySelector(selector);\n" +
                "    return element.scrollWidth > element.clientWidth || element.scrollHeight > element.clientHeight;\n" +
                "}", selector);
    }
}