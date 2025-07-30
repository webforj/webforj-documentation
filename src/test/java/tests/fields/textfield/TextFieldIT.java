package tests.fields.textfield;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import pages.fields.textfield.TextFieldPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TextFieldIT extends BaseTest {

    private TextFieldPage textFieldPage;

    @BeforeEach
    public void setupTextField() {
        navigateToRoute(TextFieldPage.getRoute());
        textFieldPage = new TextFieldPage(page);
    }

    @BrowserTest
    public void testAllInputFields() {
        assertThat(textFieldPage.getUsernameInput()).hasValue("John Doe");
        assertThat(textFieldPage.getEmailInput()).hasValue("example@email.com");
        assertThat(textFieldPage.getPhoneInput()).hasValue("(123) 456-7890");
        assertThat(textFieldPage.getUrlInput()).hasValue("https://www.example.com");
        assertThat(textFieldPage.getSearchInput()).hasValue("Search...");

        textFieldPage.getUsernameInput().clear();
        textFieldPage.getUsernameInput().fill("Jane Doe");
        textFieldPage.getEmailInput().clear();
        textFieldPage.getEmailInput().fill("jane.doe@email.com");
        textFieldPage.getPhoneInput().clear();
        textFieldPage.getPhoneInput().fill("0987543210");
        textFieldPage.getUrlInput().clear();
        textFieldPage.getUrlInput().fill("https://www.test.com");
        textFieldPage.getSearchInput().clear();
        textFieldPage.getSearchInput().fill("Search something...");

        assertThat(textFieldPage.getUsernameInput()).hasValue("Jane Doe");
        assertThat(textFieldPage.getEmailInput()).hasValue("jane.doe@email.com");
        assertThat(textFieldPage.getPhoneInput()).hasValue("0987543210");
        assertThat(textFieldPage.getUrlInput()).hasValue("https://www.test.com");
        assertThat(textFieldPage.getSearchInput()).hasValue("Search something...");
    }

    @BrowserTest
    public void testValidEmail() {
        List<String> validEmails = List.of(
                "user@example.com",
                "user@subdomain.example.com",
                "user+tag@example.com",
                "user_email@example.com",
                "user@example.co.de",
                "user@123.123.123.123",
                "!#$%&'*+-/=?^_{}~@example.org",
                "user@xn--bcher-kva.de");

        for (String email : validEmails) {
            textFieldPage.getEmailInput().clear();
            textFieldPage.getEmailInput().fill(email);
            assertThat(textFieldPage.getAlertPopover()).not().isVisible();
        }
    }

    @BrowserTest
    public void testInvalidEmail() {
        List<String> invalidEmails = List.of(
                "user@",
                "@example.com",
                "user",
                "user@@example.com",
                "user@.com",
                "user@com.",
                "user@example..com",
                "user@domain,com",
                "user@example#domain.com",
                "user<>@example.com",
                "user@ example.com",
                "user@-example.com",
                "user@domain-.com",
                "username'example.com");

        for (String email : invalidEmails) {
            textFieldPage.getEmailInput().clear();
            textFieldPage.getEmailInput().fill(email);
            assertThat(textFieldPage.getAlertPopover()).isVisible();
        }
    }

    @BrowserTest
    public void testValidURL() {
        List<String> validURLs = List.of(
                "http://example.com",
                "https://example.com",
                "http://www.example.com",
                "https://subdomain.example.com",
                "http://example.com/path",
                "http://example.com/path/to/resource",
                "https://example.com?query=123",
                "http://example.com#section",
                "https://example.com:8080",
                "ftp://example.com",
                "https://example.co.uk",
                "https://example.com/path?query=123#section",
                "http://192.168.1.1",
                "http://[2001:db8::1]");

        for (String url : validURLs) {
            textFieldPage.getUrlInput().clear();
            textFieldPage.getUrlInput().fill(url);
            assertThat(textFieldPage.getAlertPopover()).not().isVisible();
        }
    }

    @BrowserTest
    public void testInvalidURL() {
        List<String> invalidURLs = List.of(
                "example.com",
                "http//example.com",
                "://example.com",
                "http://example.com:abc",
                "ftp://",
                "http://1234.1234.1234.1234",
                "http://[2001:db8::12345]");

        for (String url : invalidURLs) {
            textFieldPage.getUrlInput().clear();
            textFieldPage.getUrlInput().fill(url);
            assertThat(textFieldPage.getAlertPopover()).isVisible();
        }
    }
}