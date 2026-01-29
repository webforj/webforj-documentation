package com.webforj.samples.views.icon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.webforj.samples.pages.SupportedLanguage;

import com.webforj.samples.pages.icon.IconVariationsPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class IconVariationsViewIT extends BaseTest {

    private final String CALENDAR_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/outline/calendar.svg";
    private final String FILLED_CALENDAR_ICON_URL = "https://cdn.jsdelivr.net/npm/@tabler/icons@latest/icons/filled/calendar.svg";
    private final String ENVELOPE_ICON_URL = "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@latest/svgs/regular/envelope.svg";
    private final String SOLID_ENVELOPE_ICON_URL = "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@latest/svgs/solid/envelope.svg";
    private final String INSTAGRAM_ICON_URL = "https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@latest/svgs/brands/instagram.svg";

    public void setupIconVariations(SupportedLanguage language) {
        navigateToRoute(IconVariationsPage.getRoute(language));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSVGsLoaded(SupportedLanguage language) throws IOException, InterruptedException {
      setupIconVariations(language);

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
