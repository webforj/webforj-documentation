---
title: "Java web app internationalization: translate a webforJ app"
description: "Internationalize a Java web app with webforJ: message bundles, t() in components, browser locale detection, a language switcher and locale-aware formatting."
slug: java-i18n
date: 2026-09-10
authors: Matthew Hawkins
tags: [tutorial, spring, layout]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-10-java-i18n/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-10-java-i18n/cover.jpg)

Java web app internationalization in webforJ takes three pieces: a `messages.properties` bundle per language, the `t()` method on your components, and a list of supported locales in your configuration. webforJ resolves keys with `BundleTranslationResolver`, can pick the user's language from the browser, and updates the UI at runtime when you call `App.setLocale()`.

This guide walks through the whole path: bundles, translated components, locale detection, a language switcher, date and number formatting, and Spring Boot setup.

<!-- truncate -->

## How internationalization works in a webforJ app {#how-it-works}

webforJ ships a translation system built from four parts. A translation resolver maps a key to localized text. The `HasTranslation` interface gives a component the `t()` method. `App.getTranslation()` does the same lookup anywhere in your code. `LocaleObserver` notifies a component when the app locale changes.

The default resolver, `BundleTranslationResolver`, reads standard Java `ResourceBundle` files from the classpath. You need no extra dependency and no template language. Your UI is Java, so a translated label is a method call.

Translation support arrived in [webforJ 25.12](/blog/whats-new-v25.12). The [Translation](/docs/advanced/i18n-localization) page covers the full reference.

## Create message bundles {#message-bundles}

Put the files in `src/main/resources`. The resolver looks for files named `messages` with a locale suffix:

```text
messages.properties        # fallback
messages_en.properties     # English
messages_de.properties     # German
messages_fr_CA.properties  # French (Canada)
```

Keys are the identifiers you use in code and values are the translations:

```properties title="messages.properties"
menu.inbox=Inbox
menu.outbox=Outbox
order.summary=Shipped on {0}, total {1}
```

```properties title="messages_de.properties"
menu.inbox=Posteingang
menu.outbox=Postausgang
order.summary=Versendet am {0}, Summe {1}
```

Values can contain [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) placeholders such as `{0}` and `{1}`. Locale matching and fallback follow Java's standard [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) chain, so a missing `messages_fr_CA` key falls back to `messages_fr`, then to `messages.properties`.

## Configure supported locales {#supported-locales}

Tell webforJ which languages your app supports with `webforj.i18n.supported-locales`. It takes [language tags](https://en.wikipedia.org/wiki/IETF_language_tag) such as `en` or `fr-CA`. The first entry is the default when nothing better matches.

```ini title="webforj.conf"
webforj.i18n.supported-locales = ["en", "de"]
webforj.i18n.auto-detect = true
```

If you use Spring Boot, set the same keys in `application.properties`. The [property reference](/docs/configuration/properties) lists every option and how to set them per environment.

## Translate text in components {#translate-components}

Implement `HasTranslation` and call `t()` with a key. Extra arguments fill the `MessageFormat` placeholders:

```java
String title = t("menu.inbox");
String summary = t("order.summary", date, amount);
String german = t(Locale.GERMAN, "menu.inbox");
```

Outside a component, call `App.getTranslation("menu.inbox")`.

A missing key doesn't throw. `t()` returns the key itself and logs a warning, so a gap in one language shows up as `menu.inbox` on screen and in the log instead of breaking the page.

## Detect the browser language {#detect-browser-locale}

With `webforj.i18n.auto-detect` set to `true`, webforJ reads the browser's preferred languages on startup and sets the app locale to the closest match from your supported list. If nothing matches, the first supported locale wins.

Auto-detection needs `supported-locales`. With an empty list it does nothing and the app uses `webforj.locale`, which is the property for a fixed default locale. When that property isn't set, the app uses the server's JVM default locale. Read the active locale any time with `App.getLocale()`.

## Build a runtime language switcher {#language-switcher}

`App.setLocale()` changes the locale for the whole app and notifies every component that implements `LocaleObserver`. No page reload is needed. A component reacts by setting its text again in `onLocaleChange()`:

```java
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private final AppNavItem inbox =
      new AppNavItem(t("menu.inbox"), InboxView.class, TablerIcon.create("inbox"));
  private final AppNavItem outbox =
      new AppNavItem(t("menu.outbox"), OutboxView.class, TablerIcon.create("send-2"));
  private final Button english = new Button("EN", e -> App.setLocale(Locale.ENGLISH));
  private final Button german = new Button("DE", e -> App.setLocale(Locale.GERMAN));

  public MainLayout() {
    AppNav nav = new AppNav();
    nav.addItem(inbox);
    nav.addItem(outbox);

    getBoundComponent().addToDrawer(nav);
    getBoundComponent().addToHeader(new AppDrawerToggle(), english, german);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    inbox.setText(t("menu.inbox"));
    outbox.setText(t("menu.outbox"));
  }
}
```

The two buttons call `App.setLocale()`. The layout observes the change and re-translates the navigation. webforJ registers and unregisters observers as components are created and destroyed, so you never manage listeners yourself.

`LocaleEvent` gives you `getLocale()` for the new locale and `getSource()` for the component that received the event.

## Format dates and numbers per locale {#format-dates-numbers}

Translated strings are half of localization. Dates and numbers need the same locale. Pass the locale to the standard Java formatters and hand the results to `t()` as arguments:

```java
public class OrderSummary extends Composite<FlexLayout>
    implements HasTranslation, LocaleObserver {

  private final LocalDate shipped = LocalDate.of(2026, 9, 10);
  private final double total = 1249.5;
  private final Paragraph line = new Paragraph();

  public OrderSummary() {
    getBoundComponent().add(line);
    render(App.getLocale());
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    render(event.getLocale());
  }

  private void render(Locale locale) {
    String date = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(locale).format(shipped);
    String amount = NumberFormat.getCurrencyInstance(locale).format(total);
    line.setText(t(locale, "order.summary", date, amount));
  }
}
```

In English this renders a month-first date, in German a day-first one, and each language gets its own decimal separator. The same `render` method runs on startup and on every switch.

Some components read the locale once when they're created. [Masked fields](/docs/components/fields/masked/overview) are an example. Update those yourself in `onLocaleChange()` with their `setLocale()` method, as shown on the [locale management](/docs/advanced/locale-management) page.

## Set up internationalization with Spring Boot {#spring-boot}

In a Spring Boot app you put the same keys in `application.properties`:

```properties title="application.properties"
webforj.i18n.supported-locales=en,de
webforj.i18n.auto-detect=true
```

When you define no `TranslationResolver` bean, Spring auto-configuration provides a `BundleTranslationResolver` that uses those supported locales. Your bundles stay in `src/main/resources` exactly as before.

## Load translations from a database {#custom-resolver}

Bundles are the default, not the limit. Implement `TranslationResolver` with `resolve(key, locale, args)` and `getSupportedLocales()` to read translations from a database or a remote service. In a plain app, register it with `App.setTranslationResolver()` before the app starts. In Spring Boot, expose it as a bean. The [Translation](/docs/advanced/i18n-localization#custom-translation-resolvers) page includes a complete example.

## Translate validation messages {#validation-messages}

Forms need translated errors too. The data binding system accepts a `Supplier<String>` that calls `t()`, so validator and transformer messages follow the active locale. Jakarta Validation messages are locale-aware as well. See the [dynamic validation messages](/docs/data-binding/validation/validators#dynamic-validation-messages) docs.

## Start translating your app {#next-step}

Add a `messages.properties` and one more language file, list the locales in your configuration, and replace your hard-coded labels with `t()`. Generate a project with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started). If you want the wider picture, the [full stack framework overview](/blog/java-full-stack-framework) shows where it fits in the larger app.

## Frequently asked questions {#faq}

### How do I internationalize a Java web app? {#faq-how}

Create `messages.properties` files per language, list your locales in `webforj.i18n.supported-locales`, and call `t("key")` from components that implement `HasTranslation`. Call `App.setLocale()` to switch language at runtime.

### Can webforJ detect the user's language automatically? {#faq-detect}

Yes. Set `webforj.i18n.auto-detect` to `true` and list your supported locales. On startup webforJ matches the browser's preferred languages against that list and falls back to the first supported locale.

### What happens when a translation key is missing? {#faq-missing}

`t()` returns the key itself and logs a warning. Nothing throws, so you can find missing translations during development without a broken page.

### Does it work with Spring Boot? {#faq-spring}

Yes. Set the i18n properties in `application.properties`. Without a custom `TranslationResolver` bean, Spring auto-configuration supplies a `BundleTranslationResolver` with your supported locales.
