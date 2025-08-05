package com.webforj.samples.views.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.icon.IconVariationsPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class IconVariationsIT extends BaseTest {

    private final String CALENDAR_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/outline/calendar.svg";
    private final String FILLED_CALENDAR_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/filled/calendar.svg";
    private final String ENVELOPE_ICON_URL = "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@latest/svgs/regular/envelope.svg";
    private final String SOLID_ENVELOPE_ICON_URL = "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@latest/svgs/solid/envelope.svg";
    private final String INSTAGRAM_ICON_URL = "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@latest/svgs/brands/instagram.svg";

    @BeforeEach
    public void setupIconVariations() {
        navigateToRoute(IconVariationsPage.getRoute());
    }

    @BrowserTest
    public void testSVG() throws IOException, InterruptedException {

        Map<String, String> svgIcons = Map.of(
                "calendar", CALENDAR_ICON_URL,
                "filled_calendar", FILLED_CALENDAR_ICON_URL,
                "envelope", ENVELOPE_ICON_URL,
                "solid_envelope", SOLID_ENVELOPE_ICON_URL,
                "instagram", INSTAGRAM_ICON_URL);

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
