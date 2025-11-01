package com.webforj.samples.views.element;
import com.webforj.samples.pages.element.ElementInputTextPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ElementInputTextViewIT extends BaseTest {

    private ElementInputTextPage elementInputTextViewPage;

    @BeforeEach
    public void setupElementInputTextView() {
        navigateToRoute(ElementInputTextPage.getRoute());
        elementInputTextViewPage = new ElementInputTextPage(page);
    }

    @Test
    public void testInputFieldHasTextSet() {
        assertThat(elementInputTextViewPage.getInputField()).hasValue("Here is the set text");
    }
}
