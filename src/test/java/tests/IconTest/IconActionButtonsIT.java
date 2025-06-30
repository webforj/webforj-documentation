package tests.IconTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import pages.IconPage.IconActionsButtonsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class IconActionButtonsIT extends BaseTest {

    private IconActionsButtonsPage iconActionsButtonsPage;

    private final String ARROW_NARROW_RIGHT_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/outline/arrow-narrow-right.svg";
    private final String FILTER_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/outline/filter.svg";

    @BeforeEach
    public void setupIconActionButtons() {
        navigateToRoute(IconActionsButtonsPage.getRoute());
        iconActionsButtonsPage = new IconActionsButtonsPage(page);
    }

    @BrowserTest
    public void testIconActionButtons() {
        assertThat(iconActionsButtonsPage.getNextIconSvg()).isVisible();
        assertThat(iconActionsButtonsPage.getFilterIconSvg()).isVisible();
    }

    @BrowserTest
    public void testSVG() throws IOException, InterruptedException {

        Map<String, String> svgIcons = Map.of(
                "arrow_narrow_right", ARROW_NARROW_RIGHT_URL,
                "filter", FILTER_ICON_URL);

        HttpClient client = HttpClient.newHttpClient();

        for (Map.Entry<String, String> icon : svgIcons.entrySet()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(icon.getValue()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("<svg"));
        }
    }
} 