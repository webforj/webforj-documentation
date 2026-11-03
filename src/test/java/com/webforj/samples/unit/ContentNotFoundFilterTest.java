package com.webforj.samples.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webforj.samples.ContentNotFoundFilter;
import com.webforj.samples.ContentNotFoundFilter.Outcome;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ContentNotFoundFilterTest {

  private static final Set<String> FILES =
      Set.of(
          "404.html",
          "de/404.html",
          "docs/components/table/overview.html",
          "docs/components/button.html",
          "blog/route-security.html",
          "blog/route-security/cover.png",
          "blog/2025/08/07/using-textfield-in-forms.html",
          "blog/tags/mvc.html",
          "de/docs/components/button.html",
          "de/docs/components/table/overview.html",
          "cookbook/theming/dark-mode.html",
          "img/logo.svg");

  private final ContentNotFoundFilter filter = new ContentNotFoundFilter(FILES::contains);

  private static Outcome pass() {
    return Outcome.pass();
  }

  private static Outcome redirect(String location) {
    return Outcome.redirect(location);
  }

  private static Outcome notFound(String page) {
    return Outcome.notFound(page);
  }

  @Nested
  class Resolve {
    @Test
    void shouldPassExistingPages() {
      assertEquals(pass(), filter.resolve("/docs/components/button"));
      assertEquals(pass(), filter.resolve("/docs/components/table/overview"));
      assertEquals(pass(), filter.resolve("/blog/route-security"));
      assertEquals(pass(), filter.resolve("/blog/2025/08/07/using-textfield-in-forms"));
      assertEquals(pass(), filter.resolve("/blog/tags/mvc"));
      assertEquals(pass(), filter.resolve("/de/docs/components/button"));
      assertEquals(pass(), filter.resolve("/cookbook/theming/dark-mode"));
    }

    @Test
    void shouldPassExistingFilesWithAnExtension() {
      assertEquals(pass(), filter.resolve("/blog/route-security/cover.png"));
    }

    @Test
    void shouldRedirectTrailingSlashesToTheCanonicalUrl() {
      assertEquals(redirect("/docs/components/button"), filter.resolve("/docs/components/button/"));
      assertEquals(redirect("/blog/route-security"), filter.resolve("/blog/route-security//"));
      assertEquals(redirect("/de/docs/nope"), filter.resolve("/de/docs/nope/"));
    }

    @Test
    void shouldRedirectFoldersToTheirOverviewPage() {
      assertEquals(
          redirect("/docs/components/table/overview"), filter.resolve("/docs/components/table"));
      assertEquals(
          redirect("/de/docs/components/table/overview"),
          filter.resolve("/de/docs/components/table"));
    }

    @Test
    void shouldReturnTheDefaultNotFoundPageForMissingContent() {
      assertEquals(notFound("/404.html"), filter.resolve("/docs/nope"));
      assertEquals(notFound("/404.html"), filter.resolve("/blog/old-post"));
      assertEquals(notFound("/404.html"), filter.resolve("/cookbook/a/b/c"));
      assertEquals(notFound("/404.html"), filter.resolve("/docs/components"));
    }

    @Test
    void shouldReturnTheLocalizedNotFoundPageForMissingLocalizedContent() {
      assertEquals(notFound("/de/404.html"), filter.resolve("/de/docs/nope"));
    }

    @Test
    void shouldFallBackToTheDefaultNotFoundPageWhenTheLocaleHasNone() {
      assertEquals(notFound("/404.html"), filter.resolve("/fr/blog/nope"));
    }

    @Test
    void shouldPassSectionRootsHandledByTheRewriteRules() {
      assertEquals(pass(), filter.resolve("/docs"));
      assertEquals(pass(), filter.resolve("/blog"));
      assertEquals(pass(), filter.resolve("/cookbook"));
      assertEquals(pass(), filter.resolve("/de/docs"));
      assertEquals(pass(), filter.resolve("/de/blog"));
    }

    @Test
    void shouldPassPathsOutsideTheContentSections() {
      assertEquals(pass(), filter.resolve("/"));
      assertEquals(pass(), filter.resolve("/accordionbasic"));
      assertEquals(pass(), filter.resolve("/webforj/accordionbasic"));
      assertEquals(pass(), filter.resolve("/webforj/accordionbasic/"));
      assertEquals(pass(), filter.resolve("/webapp/_lib/webapp/webapp.min.js"));
      assertEquals(pass(), filter.resolve("/img/logo.svg"));
      assertEquals(pass(), filter.resolve("/xx/docs/nope"));
      assertEquals(pass(), filter.resolve("/docsearch"));
      assertEquals(pass(), filter.resolve(null));
    }

    @Test
    void shouldTreatTraversalAttemptsAsMissing() {
      assertEquals(notFound("/404.html"), filter.resolve("/docs/../WEB-INF/web"));
    }
  }

  @Nested
  class Activation {
    @Test
    void shouldStayInactiveWithoutBuiltDocs() {
      var withoutDocs = new ContentNotFoundFilter(Set.<String>of()::contains);
      assertFalse(withoutDocs.isActive());
      assertEquals(pass(), withoutDocs.resolve("/docs/nope"));
      assertEquals(pass(), withoutDocs.resolve("/docs/nope/"));
    }

    @Test
    void shouldBeActiveWithBuiltDocs() {
      assertTrue(filter.isActive());
    }

    @Test
    void shouldLookUpFilesOnTheClasspathByDefault() {
      var built = getClass().getClassLoader().getResource("static/404.html") != null;
      assertEquals(built, new ContentNotFoundFilter().isActive());
    }
  }

  @Nested
  class DoFilter {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final FilterChain chain = mock(FilterChain.class);

    private void get(String uri) {
      when(request.getMethod()).thenReturn("GET");
      when(request.getRequestURI()).thenReturn(uri);
      when(request.getContextPath()).thenReturn("");
    }

    @Test
    void shouldForwardMissingContentToThe404PageWithA404Status() throws Exception {
      var dispatcher = mock(RequestDispatcher.class);
      get("/de/docs/nope");
      when(request.getRequestDispatcher("/de/404.html")).thenReturn(dispatcher);

      filter.doFilter(request, response, chain);

      verify(response).setStatus(404);
      verify(dispatcher).forward(request, response);
      verify(chain, never()).doFilter(request, response);
    }

    @Test
    void shouldRedirectPermanentlyAndKeepTheQueryString() throws Exception {
      get("/docs/components/button/");
      when(request.getQueryString()).thenReturn("tab=java");

      filter.doFilter(request, response, chain);

      verify(response).setStatus(301);
      verify(response).setHeader("Location", "/docs/components/button?tab=java");
      verify(chain, never()).doFilter(request, response);
    }

    @Test
    void shouldKeepThePercentEncodingInTheRedirectLocation() throws Exception {
      get("/blog/route%2Dsecurity%20x%3F/");

      filter.doFilter(request, response, chain);

      verify(response).setStatus(301);
      verify(response).setHeader("Location", "/blog/route%2Dsecurity%20x%3F");
    }

    @Test
    void shouldKeepThePercentEncodingInTheOverviewRedirect() throws Exception {
      get("/docs/components/t%61ble");

      filter.doFilter(request, response, chain);

      verify(response).setStatus(301);
      verify(response).setHeader("Location", "/docs/components/t%61ble/overview");
    }

    @Test
    void shouldRedirectWithinTheContextPath() throws Exception {
      when(request.getMethod()).thenReturn("GET");
      when(request.getRequestURI()).thenReturn("/ctx/docs/components/table");
      when(request.getContextPath()).thenReturn("/ctx");

      filter.doFilter(request, response, chain);

      verify(response).setStatus(301);
      verify(response).setHeader("Location", "/ctx/docs/components/table/overview");
    }

    @Test
    void shouldPassExistingContentThrough() throws Exception {
      get("/docs/components/button");

      filter.doFilter(request, response, chain);

      verify(chain).doFilter(request, response);
      verify(response, never()).setStatus(404);
    }

    @Test
    void shouldDecodeTheRequestPathBeforeTheLookup() throws Exception {
      get("/blog/route%2Dsecurity");

      filter.doFilter(request, response, chain);

      verify(chain).doFilter(request, response);
    }

    @Test
    void shouldPassMalformedPathsThrough() throws Exception {
      get("/docs/bad path%");

      filter.doFilter(request, response, chain);

      verify(chain).doFilter(request, response);
    }

    @Test
    void shouldHandleHeadRequests() throws Exception {
      var dispatcher = mock(RequestDispatcher.class);
      get("/blog/nope");
      when(request.getMethod()).thenReturn("HEAD");
      when(request.getRequestDispatcher("/404.html")).thenReturn(dispatcher);

      filter.doFilter(request, response, chain);

      verify(response).setStatus(404);
      verify(dispatcher).forward(request, response);
    }

    @Test
    void shouldPassNonReadRequestsThrough() throws Exception {
      when(request.getMethod()).thenReturn("POST");
      when(request.getRequestURI()).thenReturn("/docs/nope");

      filter.doFilter(request, response, chain);

      verify(chain).doFilter(request, response);
      verify(request, never()).getRequestDispatcher(anyString());
    }

    @Test
    void shouldPassNonHttpRequestsThrough() throws Exception {
      var plainRequest = mock(ServletRequest.class);
      var plainResponse = mock(ServletResponse.class);

      filter.doFilter(plainRequest, plainResponse, chain);

      verify(chain).doFilter(plainRequest, plainResponse);
    }
  }
}
