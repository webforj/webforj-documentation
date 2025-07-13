package tests.GoogleChartsTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import com.microsoft.playwright.Locator;

import pages.GoogleChartsPages.ChartGalleryViewPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ChartGalleryViewIT extends BaseTest {

    private HttpClient httpClient;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";
    private ChartGalleryViewPage chartGalleryViewPage;

    @BeforeEach
    public void setupToChartGallery() {
        navigateToRoute(ChartGalleryViewPage.getRoute());
        httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        chartGalleryViewPage = new ChartGalleryViewPage(page);
    }

    @BrowserTest
    public void testChartGalleryPage() throws InterruptedException, IOException {
        WaitUtil.waitForVisible(chartGalleryViewPage.getGanttChart(), 30000);

        Locator links = chartGalleryViewPage.getLinks();
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