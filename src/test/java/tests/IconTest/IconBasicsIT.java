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

import pages.IconPage.IconBasicsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class IconBasicsIT extends BaseTest {

    private IconBasicsPage iconBasicsPage;

    private final String MESSAGE_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/outline/message.svg";
    private final String EDIT_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/outline/edit.svg";
    private final String TRASH_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/outline/trash.svg";

    @BeforeEach
    public void setupIconBasics() {
        navigateToRoute(IconBasicsPage.getRoute());
        iconBasicsPage = new IconBasicsPage(page);
    }

    @BrowserTest
    public void testIconsRender() {
        assertThat(iconBasicsPage.getMessageIconSvg()).isVisible();
        assertThat(iconBasicsPage.getEditIconSvg()).isVisible();
        assertThat(iconBasicsPage.getTrashIconSvg()).isVisible();
    }

    @BrowserTest
    public void testSVG() throws IOException, InterruptedException {

        Map<String, String> svgIcons = Map.of(
                "message", MESSAGE_ICON_URL,
                "edit", EDIT_ICON_URL,
                "trash", TRASH_ICON_URL);

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