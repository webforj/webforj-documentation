package tests.GoogleChartsTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ChartGalleryTestsIT extends BaseTest {

    private HttpClient httpClient;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";

    @BeforeEach
    public void setupToChartGallery() {
        page.navigate("https://docs.webforj.com/webforj/chartgallery?");
        httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    @BrowserTest
    public void testChartGalleryPage() throws InterruptedException, IOException {
        WaitUtil.waitForVisible(page.locator("div[dwc-id=\"92\"]"), 30000);

        Locator links = page.locator("div[dwc-id=\"10\"].chart-gallery a");
        int count = links.count();

        for (int i = 0; i < count; i++) {
            String href = links.nth(i).getAttribute("href");
            if (href == null || href.isEmpty())
                continue;
            if (!href.startsWith("http"))
                href = page.url() + href;

            String requestUrl = href;
            if (href.contains("github.com") && href.contains("/blob/")) {
                requestUrl = href.replace("https://github.com/", "https://raw.githubusercontent.com/")
                        .replace("/blob/", "/");
            }

            System.out.println("Checking URL: " + href);
            System.out.println("Requesting URL: " + requestUrl);

            int maxRetries = 3;
            int retryCount = 0;
            int backoffMs = 2000;
            int statusCode;

            while (true) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(requestUrl))
                        .method("HEAD", HttpRequest.BodyPublishers.noBody())
                        .header("User-Agent", USER_AGENT)
                        .build();

                HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                statusCode = response.statusCode();

                if (statusCode == 200) {
                    break;
                } else if (statusCode == 429 && retryCount < maxRetries) {
                    Thread.sleep(backoffMs);
                    backoffMs *= 2;
                    retryCount++;
                } else {
                    break;
                }
            }

            assertTrue(statusCode == 200,
                    String.format("Link failed: %s returned %d after %d retries", href, statusCode, retryCount));
        }
    }
} 