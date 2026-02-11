package com.webforj.samples.pages.checkbox;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class CheckboxHorizontalTextPage {

    private static final String ROUTE = "checkboxhorizontaltext";

    private final Locator dailyCheckbox;

    public CheckboxHorizontalTextPage(Page page) {
        this.dailyCheckbox = page.getByText("Daily").nth(1);
    }

        public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getDailyCheckbox() {
        return dailyCheckbox;
    }
}
