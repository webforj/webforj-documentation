package com.webforj.samples.views.fields.timefield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.fields.timefield.TimeFieldPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TimeFieldViewIT extends BaseTest {

    private TimeFieldPage timeFieldPage;

    @BeforeEach
    public void setupTimeField() {
        navigateToRoute(TimeFieldPage.getRoute());
        timeFieldPage = new TimeFieldPage(page);
    }

    @Test
    public void testValidTimeEntered() {
        timeFieldPage.getReminder().fill("10:00");
        assertThat(timeFieldPage.getReminder()).hasValue("10:00");
    }

    
}
