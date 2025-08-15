package com.webforj.samples.views.fields.datefield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.fields.datefield.DateFieldPage;
import com.webforj.samples.views.BaseTest;

public class DateFieldIT extends BaseTest {

    private DateFieldPage dateFieldPage;
    LocalDate today = LocalDate.now();

    @BeforeEach
    public void setupDateField() {
        navigateToRoute(DateFieldPage.getRoute());
        dateFieldPage = new DateFieldPage(page);
    }

    @Test
    public void testValidDates() {
        String departureDate = today.plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String returnDate = today.plusDays(6).format(DateTimeFormatter.ISO_LOCAL_DATE);

        dateFieldPage.getDepartureInput().fill(departureDate);
        dateFieldPage.getReturnInput().fill(returnDate);

        assertThat(dateFieldPage.getDepartureInput()).not().hasAttribute("invalid", "");
        assertThat(dateFieldPage.getReturnInput()).not().hasAttribute("invalid", "");
    }

    @Test
    public void testEarlierReturnDate() {
        String departureDate = today.plusDays(6).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String returnDate = today.plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String correctedReturnDateISOFormat = today.plusDays(6).format(DateTimeFormatter.ISO_LOCAL_DATE);

        dateFieldPage.getDepartureInput().fill(departureDate);
        dateFieldPage.getReturnInput().fill(returnDate);

        assertThat(dateFieldPage.getDepartureInput()).hasValue(correctedReturnDateISOFormat);
    }

    @Test
    public void testInvalidString() {
        dateFieldPage.getDepartureInput().click();
        dateFieldPage.getDepartureInput().press("Control+A");
        dateFieldPage.getDepartureInput().pressSequentially("abcd");

        assertThat(dateFieldPage.getDepartureInput()).hasValue(today.toString());

    }

    @Test
    public void testExcessiveDate() {
        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially("121210000");

        assertThat(dateFieldPage.getDepartureInputHost()).hasAttribute("invalid", "");
    }

    @Test
    public void testSpecialChar() {
        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially("#$%&");

        assertThat(dateFieldPage.getDepartureInput()).hasValue(today.toString());
    }

    @Test
    public void testInvalidZeroDate() {
        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureInput().pressSequentially("00/00/0000");

        assertThat(dateFieldPage.getDepartureInputHost()).hasAttribute("invalid", "");
    }
}
