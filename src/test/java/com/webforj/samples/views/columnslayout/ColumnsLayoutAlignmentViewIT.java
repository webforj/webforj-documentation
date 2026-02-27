package com.webforj.samples.views.columnslayout;

import com.webforj.samples.views.BaseTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.webforj.samples.pages.columnslayout.ColumnsLayoutAlignmentPage;

public class ColumnsLayoutAlignmentViewIT extends BaseTest {

    private ColumnsLayoutAlignmentPage columnsLayoutAlignmentPage;

    @BeforeEach
    public void setupColumnsLayoutAlignment() {
        navigateToRoute(ColumnsLayoutAlignmentPage.getRoute());
        columnsLayoutAlignmentPage = new ColumnsLayoutAlignmentPage(page);
    }

    @Test
    public void testColumnsLayoutAlignment() {
        var firstNameBox = columnsLayoutAlignmentPage.getFirstName().boundingBox();
        var lastNameBox = columnsLayoutAlignmentPage.getLastName().boundingBox();

        assertNotNull(firstNameBox, "First name input element was not found");
        assertNotNull(lastNameBox, "Last name input element was not found");

        double yDifference = Math.abs(firstNameBox.y - lastNameBox.y);
        assertTrue(yDifference <= 1.0,
            String.format("Fields are not vertically aligned. Y-coordinate difference: %.2f pixels", yDifference));
    }
}
