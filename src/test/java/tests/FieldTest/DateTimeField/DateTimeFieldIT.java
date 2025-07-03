package tests.FieldTest.DateTimeField;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.FieldPages.DateTimeFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DateTimeFieldIT extends BaseTest {

    private DateTimeFieldPage dateTimeFieldPage;

    @BeforeEach
    public void setupDateTimeField() {
        navigateToRoute(DateTimeFieldPage.getRoute());
        dateTimeFieldPage = new DateTimeFieldPage(page);
    }

    @BrowserTest
    public void testVisibility() {
        assertThat(dateTimeFieldPage.getDepartureInput()).isVisible();
    }
}