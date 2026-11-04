package com.webforj.samples;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Gives documentation, blog and cookbook URLs a correct HTTP answer.
 *
 * <p>The samples app serves every unmatched path, so a missing docs URL would otherwise return the
 * app shell with status 200, and search engines index it as an empty page. This filter answers
 * those URLs before the app does:
 *
 * <ul>
 *   <li>a trailing slash is redirected permanently to the URL without it
 *   <li>a folder with an overview page is redirected permanently to that page
 *   <li>a URL with no built page is answered with the 404 page and status 404
 * </ul>
 *
 * <p>Paths outside the content sections are left to the app. The filter stays inactive when the
 * Docusaurus build is absent, which is the case in development.
 */
public class ContentNotFoundFilter implements Filter {
  private static final Pattern CONTENT =
      Pattern.compile("^/(?:(es|de|fr|nl|fi|zh)/)?(?:docs|blog|cookbook)/.+");
  private static final String STATIC_ROOT = "static/";
  private static final String NOT_FOUND = "404.html";
  private static final String OVERVIEW = "/overview";

  private final Predicate<String> fileExists;

  /** What the filter does with a request. */
  public record Outcome(Kind kind, String target) {
    /** The action to take. */
    public enum Kind {
      PASS,
      REDIRECT,
      NOT_FOUND
    }

    /**
     * Lets the request continue to the app.
     *
     * @return the outcome
     */
    public static Outcome pass() {
      return new Outcome(Kind.PASS, null);
    }

    /**
     * Redirects permanently.
     *
     * @param location the path to redirect to
     * @return the outcome
     */
    public static Outcome redirect(String location) {
      return new Outcome(Kind.REDIRECT, location);
    }

    /**
     * Answers with a 404 page.
     *
     * @param page the 404 page to forward to
     * @return the outcome
     */
    public static Outcome notFound(String page) {
      return new Outcome(Kind.NOT_FOUND, page);
    }
  }

  /** Creates a filter that looks up built pages on the classpath. */
  public ContentNotFoundFilter() {
    this(
        file ->
            ContentNotFoundFilter.class.getClassLoader().getResource(STATIC_ROOT + file) != null);
  }

  /**
   * Creates a filter with a custom file lookup.
   *
   * @param fileExists tells whether a file exists, given its path relative to the static root
   */
  public ContentNotFoundFilter(Predicate<String> fileExists) {
    this.fileExists = fileExists;
  }

  /**
   * Tells whether the built documentation is present.
   *
   * @return true when the static root holds the 404 page
   */
  public boolean isActive() {
    return fileExists.test(NOT_FOUND);
  }

  /**
   * Decides what to do with a request path.
   *
   * @param path the decoded request path without the context path
   * @return the outcome for the path
   */
  public Outcome resolve(String path) {
    if (path == null || !isActive()) {
      return Outcome.pass();
    }

    var matcher = CONTENT.matcher(path);
    if (!matcher.matches()) {
      return Outcome.pass();
    }

    var canonical = path;
    while (canonical.endsWith("/")) {
      canonical = canonical.substring(0, canonical.length() - 1);
    }
    if (!canonical.equals(path)) {
      return Outcome.redirect(canonical);
    }

    var file = path.substring(1);
    if (!file.contains("..")) {
      if (isPage(file)) {
        return Outcome.pass();
      }
      if (fileExists.test(file + OVERVIEW + ".html")) {
        return Outcome.redirect(path + OVERVIEW);
      }
    }

    var locale = matcher.group(1);
    if (locale != null && fileExists.test(locale + "/" + NOT_FOUND)) {
      return Outcome.notFound("/" + locale + "/" + NOT_FOUND);
    }

    return Outcome.notFound("/" + NOT_FOUND);
  }

  private boolean isPage(String file) {
    var name = file.substring(file.lastIndexOf('/') + 1);
    return fileExists.test(file + ".html") || (name.contains(".") && fileExists.test(file));
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest http
        && response instanceof HttpServletResponse httpResponse
        && ("GET".equals(http.getMethod()) || "HEAD".equals(http.getMethod()))) {
      var contextPath = http.getContextPath();
      var rawPath = http.getRequestURI().substring(contextPath.length());
      var outcome = resolve(decode(rawPath));

      if (outcome.kind() == Outcome.Kind.REDIRECT) {
        var query = http.getQueryString();
        httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        httpResponse.setHeader(
            "Location",
            contextPath + redirectLocation(rawPath) + (query == null ? "" : "?" + query));
        return;
      }

      if (outcome.kind() == Outcome.Kind.NOT_FOUND) {
        httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        http.getRequestDispatcher(outcome.target()).forward(request, response);
        return;
      }
    }

    chain.doFilter(request, response);
  }

  /**
   * Builds the redirect target from the raw path so its percent-encoding is kept. It mirrors {@link
   * #resolve(String)}: a trailing slash is dropped, otherwise the overview page is appended.
   */
  private static String redirectLocation(String rawPath) {
    var canonical = rawPath;
    while (canonical.endsWith("/")) {
      canonical = canonical.substring(0, canonical.length() - 1);
    }
    return canonical.equals(rawPath) ? rawPath + OVERVIEW : canonical;
  }

  private static String decode(String rawPath) {
    try {
      return new URI(rawPath).getPath();
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
