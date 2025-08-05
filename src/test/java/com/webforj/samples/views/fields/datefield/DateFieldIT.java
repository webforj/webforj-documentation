package com.webforj.samples.views.fields.datefield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.fields.datefield.DateFieldPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class DateFieldIT extends BaseTest {

    private DateFieldPage dateFieldPage;
    LocalDate today = LocalDate.now();

    @BeforeEach
    public void setupDateField() {
        navigateToRoute(DateFieldPage.getRoute());
        dateFieldPage = new DateFieldPage(page);
    }

    @BrowserTest
    public void testValidDates() {
        assumeTrue(!browserType.equalsIgnoreCase("webkit"), "Skipping on WebKit");

        String departureDate = today.plusDays(3).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String returnDate = today.plusDays(6).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially(departureDate);

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getReturnInput().pressSequentially(returnDate);

        assertThat(dateFieldPage.getDepartureInput()).not().hasAttribute("invalid", "");
        assertThat(dateFieldPage.getReturnInput()).not().hasAttribute("invalid", "");

    }

    @BrowserTest
    public void testEarlierReturnDate() {
        assumeTrue(!browserType.equalsIgnoreCase("webkit"), "Skipping on WebKit - Manually Tested on Safari");
        assumeTrue(!browserType.equalsIgnoreCase("firefox"), "Skipping on Firefox - Manually Tested on Firefox");


        String departureDate = today.plusDays(6).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String returnDate = today.plusDays(3).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String correctedReturnDateISOFormat = today.plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE);

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially(departureDate);

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getReturnInput().pressSequentially(returnDate);

        assertThat(dateFieldPage.getDepartureInput().locator("input")).hasValue(correctedReturnDateISOFormat);

    }

    @BrowserTest
    public void testInvalidString() {
        assumeTrue(!browserType.equalsIgnoreCase("webkit"), "Skipping on WebKit");

        dateFieldPage.getDepartureInput().click();
        dateFieldPage.getDepartureInput().press("Control+A");
        dateFieldPage.getDepartureInput().pressSequentially("abcd");

        dateFieldPage.getReturnInput().click();
        dateFieldPage.getReturnInput().press("Control+A");
        dateFieldPage.getReturnInput().pressSequentially("abcd");

        assertThat(dateFieldPage.getDepartureInput().locator("input")).hasValue(today.toString());
        assertThat(dateFieldPage.getReturnInput().locator("input")).hasValue(today.toString());

    }

    @BrowserTest
    public void testExcessiveDate() {
        assumeTrue(!browserType.equalsIgnoreCase("webkit"), "Skipping on WebKit");

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially("12/12/10000");

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getReturnInput().pressSequentially("12/12/10000");

        assertThat(dateFieldPage.getDepartureInput()).hasAttribute("invalid", "");
    }

    @BrowserTest
    public void testSpecialChar() {
        // Webkit has issue, this is not related the feature. Skip the webkit.
        assumeTrue(!browserType.equalsIgnoreCase("webkit"), "Skipping on WebKit");

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially("#$%&");

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getReturnInput().pressSequentially("#$%&");

        assertThat(dateFieldPage.getDepartureInput().locator("input")).hasValue(today.toString());
        assertThat(dateFieldPage.getReturnInput().locator("input")).hasValue(today.toString());
    }

    @BrowserTest
    public void testInvalidZeroDate() {
        assumeTrue(!browserType.equalsIgnoreCase("webkit"), "Skipping on WebKit");

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially("00/00/0000");

        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getReturnInput().pressSequentially("00/00/0000");

        assertThat(dateFieldPage.getDepartureInput()).hasAttribute("invalid", "");
    }
}
