package com.webforj.samples.views.flexlayout;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.flexlayout.FlexLayoutPage;
import com.webforj.samples.views.BaseTest;

public class FlexLayoutViewIT extends BaseTest {

    private FlexLayoutPage flexLayoutPage;

    @BeforeEach
    public void setupFlexLayout() {
        navigateToRoute(FlexLayoutPage.getRoute());
        flexLayoutPage = new FlexLayoutPage(page);
    }

    @Test
    public void testItemBasisIsSet() {
        assertThat(flexLayoutPage.getCityField()).hasCSS("flex-basis", "40%");
        assertThat(flexLayoutPage.getZipField()).hasCSS("flex-basis", "40%");
    }

}
