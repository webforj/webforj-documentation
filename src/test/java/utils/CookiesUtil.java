package utils;

import java.util.Map;

import com.microsoft.playwright.Page;

public class CookiesUtil {
    @SuppressWarnings("unchecked")
    public static Map<String, String> getLocalStorage(Page page) {
        return (Map<String, String>) page.evaluate("() => {" +
                "  const store = {};" +
                "  for (let i = 0; i < localStorage.length; i++) {" +
                "    const key = localStorage.key(i);" +
                "    store[key] = localStorage.getItem(key);" +
                "  }" +
                "  return store;" +
                "}");
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> getSessionStorage(Page page) {
        return (Map<String, String>) page.evaluate("() => {" +
                "  const store = {};" +
                "  for (let i = 0; i < sessionStorage.length; i++) {" +
                "    const key = sessionStorage.key(i);" +
                "    store[key] = sessionStorage.getItem(key);" +
                "  }" +
                "  return store;" +
                "}");
    }
}
