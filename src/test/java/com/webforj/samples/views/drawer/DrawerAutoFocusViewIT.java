package com.webforj.samples.views.drawer;

import com.webforj.samples.pages.drawer.DrawerAutoFocusPage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webforj.samples.views.BaseTest;

public class DrawerAutoFocusViewIT extends BaseTest {

    private DrawerAutoFocusPage drawerAutoFocusPage;

    @BeforeEach
    public void setupDrawerAutoFocus() {
        navigateToRoute(DrawerAutoFocusPage.getRoute());
        drawerAutoFocusPage = new DrawerAutoFocusPage(page);
    }

    @Test
    public void testDrawerAutoFocus() {
        assertThat(drawerAutoFocusPage.getEmailNotifications()).isFocused();
    }

}
