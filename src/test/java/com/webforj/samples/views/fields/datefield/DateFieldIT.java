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

        dateFieldPage.getDepartureDate().fill(departureDate);
        dateFieldPage.getReturnDate().fill(returnDate);

        assertThat(dateFieldPage.getDepartureDate()).not().hasAttribute("invalid", "");
        assertThat(dateFieldPage.getReturnDate()).not().hasAttribute("invalid", "");
    }

    @Test
    public void testEarlierReturnDate() {
        String departureDate = today.plusDays(6).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String returnDate = today.plusDays(3).format(DateTimeFormatter.ISO_LOCAL_DATE);
        String correctedReturnDateISOFormat = today.plusDays(6).format(DateTimeFormatter.ISO_LOCAL_DATE);

        dateFieldPage.getDepartureDate().fill(departureDate);
        dateFieldPage.getReturnDate().fill(returnDate);

        assertThat(dateFieldPage.getDepartureDate()).hasValue(correctedReturnDateISOFormat);
    }

    @Test
    public void testInvalidString() {
        dateFieldPage.getDepartureDate().click();
        dateFieldPage.getDepartureDate().press("Control+A");
        dateFieldPage.getDepartureDate().pressSequentially("abcd");

        assertThat(dateFieldPage.getDepartureDate()).hasValue(today.toString());
    }

    @Test
    public void testSpecialChar() {
        page.keyboard().press("Tab");
        page.keyboard().press("Tab");
        dateFieldPage.getDepartureDate().pressSequentially("#$%&");

        assertThat(dateFieldPage.getDepartureDate()).hasValue(today.toString());
    }
}
