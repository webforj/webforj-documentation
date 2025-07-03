package tests.FieldTest.TimeField;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.TimeFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TimeFieldIT extends BaseTest {

    private TimeFieldPage timeFieldPage;

    @BeforeEach
    public void setupTimeField() {
        navigateToRoute(TimeFieldPage.getRoute());
        timeFieldPage = new TimeFieldPage(page);
    }

    @BrowserTest
    public void testVisibility() {
        assertThat(timeFieldPage.getReminderField()).isVisible();
    }
}