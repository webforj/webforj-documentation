package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.Map;

/**
 * Helper utility for scrolling-related operations
 */
public class ScrollUtils {
    private final Page page;

    /**
     * Constructor
     * 
     * @param page The Playwright page
     */
    public ScrollUtils(Page page) {
        this.page = page;
    }

    /**
     * Static method to create a ScrollUtils instance
     * 
     * @param page The Playwright page
     * @return ScrollUtils instance
     */
    public static ScrollUtils forPage(Page page) {
        return new ScrollUtils(page);
    }

    /**
     * Check if the page is scrollable vertically.
     * 
     * @return boolean - Returns true if vertical scrolling is possible.
     */
    public boolean isPageVerticallyScrollable() {
        return (Boolean) page.evaluate("() => document.documentElement.scrollHeight > document.documentElement.clientHeight");
    }

    /**
     * Check if the page is scrollable horizontally.
     * 
     * @return boolean - Returns true if horizontal scrolling is possible.
     */
    public boolean isPageHorizontallyScrollable() {
        return (Boolean) page.evaluate("() => document.documentElement.scrollWidth > document.documentElement.clientWidth");
    }
    
    /**
     * Scroll to a specific element
     * 
     * @param selector CSS selector for the element to scroll to
     */
    public void scrollToElement(String selector) {
        page.evaluate("(selector) => document.querySelector(selector).scrollIntoView()", selector);
    }
    
    /**
     * Scroll to a specific element
     * 
     * @param element Locator for the element to scroll to
     */
    public void scrollToElement(Locator element) {
        page.evaluate("(element) => element.scrollIntoView()", element);
    }
    
    /**
     * Scroll to the bottom of the page
     */
    public void scrollToBottom() {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
    }

    /**
     * Static helper to scroll to the bottom of the page
     * 
     * @param page The Playwright page
     */
    public static void scrollToBottom(Page page) {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
    }
    
    /**
     * Scroll to the top of the page
     */
    public void scrollToTop() {
        page.evaluate("() => window.scrollTo(0, 0)");
    }

    /**
     * Scroll the page by a specific amount
     * 
     * @param x Horizontal scroll amount in pixels
     * @param y Vertical scroll amount in pixels
     */
    public void scrollPageBy(int x, int y) {
        page.evaluate("({x, y}) => window.scrollBy(x, y)", Map.of("x", x, "y", y));
    }

    /**
     * Static helper to scroll the page by a specific amount
     * 
     * @param page The Playwright page
     * @param x Horizontal scroll amount in pixels
     * @param y Vertical scroll amount in pixels
     */
    public static void scrollPageBy(Page page, int x, int y) {
        page.evaluate("({x, y}) => window.scrollBy(x, y)", Map.of("x", x, "y", y));
    }

    /**
     * Scroll an element to its bottom
     * 
     * @param selector CSS selector for the element to scroll
     */
    public void scrollElementToBottom(String selector) {
        page.evaluate("(selector) => document.querySelector(selector).scrollTop = document.querySelector(selector).scrollHeight", selector);
    }

    /**
     * Static helper to scroll an element to its bottom
     * 
     * @param page The Playwright page
     * @param selector CSS selector for the element to scroll
     */
    public static void scrollElementToBottom(Page page, String selector) {
        page.evaluate("(selector) => document.querySelector(selector).scrollTop = document.querySelector(selector).scrollHeight", selector);
    }

    /**
     * Check if an element is scrollable
     * 
     * @param selector CSS selector for the element to check
     * @return boolean - Returns true if the element is scrollable
     */
    public boolean isElementScrollable(String selector) {
        return (Boolean) page.evaluate("(selector) => {\n" +
                "    const element = document.querySelector(selector);\n" +
                "    return element.scrollWidth > element.clientWidth || element.scrollHeight > element.clientHeight;\n" +
                "}", selector);
    }
}