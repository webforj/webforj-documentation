package com.webforj.samples.views.flexlayout;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.flexlayout.FlexBasisPage;
import com.webforj.samples.views.BaseTest;

public class FlexBasisViewIT extends BaseTest {

    private FlexBasisPage flexBasisPage;

    @BeforeEach
    public void setupFlexBasis() {
        navigateToRoute(FlexBasisPage.getRoute());
        flexBasisPage = new FlexBasisPage(page);
    }

    @Test
    public void testSetMinimumBasisValue() {
        flexBasisPage.getBox1().click();
        flexBasisPage.getNumberField().fill("75");
        flexBasisPage.getSetBasisButton().click();
        assertThat(flexBasisPage.getBox1()).hasAttribute("style", Pattern.compile(".*flex-basis:\\s*75px.*"));
    }

}
