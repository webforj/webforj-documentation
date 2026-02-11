---
sidebar_position: 12
title: Translation
sidebar_class_name: new-content
---

# Translation <DocChip chip='since' label='25.12' />

webforJ includes a built-in translation system for looking up localized strings by key. The system consists of a translation resolver that maps keys to localized text, a `HasTranslation` concern interface that provides a convenient `t()` method, `App.getTranslation()` for direct access anywhere, automatic locale detection from the browser, and support for custom translation sources such as databases.

## Translation resolver {#translation-resolver}

The translation resolver is the system that looks up localized strings for a given key and locale. webforJ provides a default resolver, `BundleTranslationResolver`, that loads translations from Java `ResourceBundle` property files on the classpath. This works out of the box with no additional dependencies.

### Resource bundle files

Place your translation files in the `src/main/resources` directory. The default resolver looks for files named `messages` with locale suffixes following the standard Java `ResourceBundle` naming convention:

```text
messages.properties        # Default/fallback translations
messages_en.properties     # English
messages_de.properties     # German
messages_fr_CA.properties  # French (Canada)
```

Each file contains key-value pairs. Keys are identifiers you use in code, and values are the translated strings. You can include [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) placeholders like `{0}`, `{1}` for dynamic values:

```properties title="messages.properties"
app.title=Mailbox
menu.inbox=Inbox
menu.outbox=Outbox
greeting=Hello {0}, you have {1} new messages
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

The resolver delegates to Java's standard [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) resolution chain, which handles locale matching and fallback automatically.

### Configuring supported locales

The `supported-locales` setting tells webforJ which locales your app supports. This list is used by auto-detection to match the user's browser locale against available translations. The first locale in the list is used as the default fallback when no better match is found. The property key is `webforj.i18n.supported-locales` and accepts a list of [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) language tags, for example `en, de`.

See the [Configuration](/docs/configuration/properties) section to learn how to set properties for different environments.

## The `t()` method {#the-t-method}

Components that implement the `HasTranslation` concern interface gain access to the `t()` method for translating text. The method takes a translation key and returns the localized string for the current app locale:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Simple translation
    String title = t("app.title");

    // Translation with MessageFormat parameters
    String greeting = t("greeting", userName, messageCount);

    // Translation for a specific locale
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

You can also use `App.getTranslation()` directly anywhere without implementing the interface:

```java
String title = App.getTranslation("app.title");
```

:::info Graceful fallback
If a translation key isn't found, `t()` returns the key itself rather than throwing an exception. This means your app won't break if a translation is missing. The key is displayed as-is, and a warning is logged so you can track missing translations during development.
:::

## Implementing translated components {#implementing-translated-components}

A translated component typically combines `HasTranslation` with [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Use `t()` when creating UI elements to set the initial translated text. To support runtime language switching, implement `LocaleObserver` and update the same text in `onLocaleChange()`.

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private AppLayout self = getBoundComponent();
  private AppNavItem inboxItem;
  private AppNavItem outboxItem;

  public MainLayout() {
    inboxItem = new AppNavItem(t("menu.inbox"), InboxView.class, TablerIcon.create("inbox"));
    outboxItem = new AppNavItem(t("menu.outbox"), OutboxView.class, TablerIcon.create("send-2"));

    AppNav appNav = new AppNav();
    appNav.addItem(inboxItem);
    appNav.addItem(outboxItem);

    self.addToDrawer(appNav);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    inboxItem.setText(t("menu.inbox"));
    outboxItem.setText(t("menu.outbox"));
  }
}
```

:::tip Data binding
The data binding system supports translated validation and transformation messages using `Supplier<String>` with `t()`. See [dynamic validation messages](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynamic transformer messages](/docs/data-binding/transformation#dynamic-transformer-error-messages), and [locale-aware Jakarta Validation](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Custom translation resolvers {#custom-translation-resolvers}

The default resolver loads translations from Java `ResourceBundle` property files. To load translations from a different source, such as a database or a remote service, implement `TranslationResolver`:

```java title="DatabaseTranslationResolver.java"
public class DatabaseTranslationResolver implements TranslationResolver {
  private final TranslationRepository repository;
  private final List<Locale> supportedLocales;

  public DatabaseTranslationResolver(TranslationRepository repository,
      List<Locale> supportedLocales) {
    this.repository = repository;
    this.supportedLocales = List.copyOf(supportedLocales);
  }

  @Override
  public String resolve(String key, Locale locale, Object... args) {
    String value = repository
        .findByKeyAndLocale(key, locale.getLanguage())
        .map(Translation::getValue)
        .orElse(key);

    if (args != null && args.length > 0) {
      value = new MessageFormat(value, locale).format(args);
    }

    return value;
  }

  @Override
  public List<Locale> getSupportedLocales() {
    return supportedLocales;
  }
}
```

### Registering a custom resolver {#registering-a-custom-resolver}

In a plain webforJ app, set the resolver before the app starts, for example using an [app lifecycle listener](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

In a Spring Boot app, expose the resolver as a bean:

```java title="MessageSourceConfig.java"
@Configuration
public class MessageSourceConfig {

  @Bean
  TranslationResolver translationResolver(TranslationRepository repository,
      SpringConfigurationProperties properties) {
    List<Locale> supportedLocales = properties.getI18n().getSupportedLocales().stream()
        .map(Locale::forLanguageTag)
        .toList();
    return new DatabaseTranslationResolver(repository, supportedLocales);
  }
}
```

:::info Default resolver in Spring Boot
When no custom `TranslationResolver` bean is defined, Spring auto-configuration provides a default `BundleTranslationResolver` configured with the supported locales from `application.properties`.
:::
