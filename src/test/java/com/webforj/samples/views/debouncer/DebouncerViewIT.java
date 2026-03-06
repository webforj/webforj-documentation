package com.webforj.samples.views.debouncer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.debouncer.DebouncerPage;
import com.webforj.samples.views.BaseTest;

public class DebouncerViewIT extends BaseTest {

    private DebouncerPage debouncerPage;

    @BeforeEach
    public void setupDebouncer() {
        navigateToRoute(DebouncerPage.getRoute());
        debouncerPage = new DebouncerPage(page);
    }

    @Test
    public void testTypingUpdatesHelperText() {
        assertThat(debouncerPage.getInputHelperText()).hasText("Key events: 0");

        debouncerPage.getInput().pressSequentially("ab");
        assertThat(debouncerPage.getInputHelperText()).hasText("Key events: 2");
        debouncerPage.getInput().fill("b");
        assertThat(debouncerPage.getInputHelperText()).hasText("Key events: 3");
    }

    @Test
    public void testDebouncerUpdatesOutputAndResetsHelper() {
        debouncerPage.getInput().pressSequentially("world");

        assertThat(debouncerPage.getOutput()).hasValue("");
        assertThat(debouncerPage.getOutput()).hasValue("world\n");

        assertThat(debouncerPage.getInputHelperText()).hasText("Key events: 0");
    }

}
