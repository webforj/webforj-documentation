---
sidebar_position: 12
title: Translation
description: >-
  Resolve localized strings from resource bundles or custom sources with the t()
  method, MessageFormat placeholders, and browser locale detection.
_i18n_hash: 565ad5b41fe9fa72e363b2db84809cbe
---
# Übersetzung <DocChip chip='since' label='25.12' />

webforJ verfügt über ein integriertes Übersetzungssystem, um lokalisierte Strings nach Schlüssel zu suchen. Das System besteht aus einem Übersetzungsresolver, der Schlüssel auf lokalisierte Texte abbildet, einem `HasTranslation`-Interface, das eine bequeme `t()`-Methode bereitstellt, `App.getTranslation()` für den direkten Zugriff von überall, automatischer Lokalerkennung aus dem Browser und Unterstützung für benutzerdefinierte Übersetzungsquellen wie Datenbanken.

<AISkillTip skill="webforj-localizing-apps" />

## Übersetzungsresolver {#translation-resolver}

Der Übersetzungsresolver ist das System, das lokalisierte Strings für einen gegebenen Schlüssel und eine gegebenen Locale sucht. webforJ bietet einen Standardresolver, `BundleTranslationResolver`, der Übersetzungen aus Java `ResourceBundle`-Eigenschaftsdateien im Klassenpfad lädt. Dies funktioniert sofort ohne zusätzliche Abhängigkeiten.

### Ressourcenbündeldateien {#resource-bundle-files}

Platzieren Sie Ihre Übersetzungsdateien im Verzeichnis `src/main/resources`. Der Standardresolver sucht nach Dateien mit dem Namen `messages` und Locale-Suffixen, die der Standardbenennungskonvention von Java `ResourceBundle` folgen:

```text
messages.properties        # Standard-/Fallback-Übersetzungen
messages_en.properties     # Englisch
messages_de.properties     # Deutsch
messages_fr_CA.properties  # Französisch (Kanada)
```

Jede Datei enthält Schlüssel-Wert-Paare. Schlüssel sind Identifikatoren, die Sie im Code verwenden, und Werte sind die übersetzten Strings. Sie können [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) Platzhalter wie `{0}`, `{1}` für dynamische Werte einfügen:

```properties title="messages.properties"
app.title=Mailbox
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

Der Resolver delegiert an die Standardauflösungskette von Java [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html), die die Lokalanpassung und Fallbacks automatisch behandelt.

### Konfigurieren unterstützter Sprachen {#configuring-supported-locales}

Die Einstellung `supported-locales` teilt webforJ mit, welche Sprachen Ihre App unterstützt. Diese Liste wird von der automatischen Erkennung verwendet, um die Locale des Browsers des Benutzers mit den verfügbaren Übersetzungen abzugleichen. Die erste Sprache in der Liste wird als Standard-Fallback verwendet, wenn kein besserer Treffer gefunden wird. Der Eigenschaftsschlüssel lautet `webforj.i18n.supported-locales` und akzeptiert eine Liste von [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) Sprach-Tags, zum Beispiel `en, de`.

:::info Weitere Informationen
Siehe den Abschnitt [Konfiguration](/docs/configuration/properties), um zu erfahren, wie Eigenschaften für verschiedene Umgebungen gesetzt werden.
:::

## Die `t()`-Methode {#the-t-method}

Komponenten, die das `HasTranslation`-Interface implementieren, erhalten Zugriff auf die `t()`-Methode zum Übersetzen von Text. Die Methode nimmt einen Übersetzungsschlüssel entgegen und gibt den lokalisierten String für die aktuelle Sprache der App zurück:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Einfache Übersetzung
    String title = t("app.title");

    // Übersetzung mit MessageFormat-Parametern
    String greeting = t("greeting", userName, messageCount);

    // Übersetzung für eine spezifische Sprache
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Sie können auch `App.getTranslation()` direkt überall verwenden, ohne das Interface zu implementieren:

```java
String title = App.getTranslation("app.title");
```

:::info Sanfter Fallback
Wenn ein Übersetzungsschlüssel nicht gefunden wird, gibt `t()` den Schlüssel selbst zurück, anstatt eine Ausnahme auszulösen. Das bedeutet, dass Ihre App nicht abstürzt, wenn eine Übersetzung fehlt. Der Schlüssel wird unverändert angezeigt, und eine Warnung wird protokolliert, damit Sie fehlende Übersetzungen während der Entwicklung verfolgen können.
:::

## Implementierung übersetzter Komponenten {#implementing-translated-components}

Eine übersetzte Komponente kombiniert typischerweise `HasTranslation` mit [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Verwenden Sie `t()`, wenn Sie UI-Elemente erstellen, um den anfänglichen übersetzten Text festzulegen. Um die Sprachumschaltung zur Laufzeit zu unterstützen, implementieren Sie `LocaleObserver` und aktualisieren Sie denselben Text in `onLocaleChange()`.

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private final AppLayout self = getBoundComponent();
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

:::tip Datenbindung
Das Datenbindungssystem unterstützt übersetzte Validierungs- und Transformationsnachrichten mit `Supplier<String>` mit `t()`. Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynamische Transformationsnachrichten](/docs/data-binding/transformation#dynamic-transformer-error-messages) und [locale-aware Jakarta Validation](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Benutzerdefinierte Übersetzungsresolver {#custom-translation-resolvers}

Der Standardresolver lädt Übersetzungen aus Java `ResourceBundle`-Eigenschaftsdateien. Um Übersetzungen aus einer anderen Quelle zu laden, beispielsweise einer Datenbank oder einem Remote-Dienst, implementieren Sie `TranslationResolver`:

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

### Registrieren eines benutzerdefinierten Resolvers {#registering-a-custom-resolver}

In einer einfachen webforJ-App legen Sie den Resolver fest, bevor die App startet, zum Beispiel mithilfe eines [App-Lebenszyklus-Listeners](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

In einer Spring Boot-App exponieren Sie den Resolver als Bean:

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

:::info Standardresolver in Spring Boot
Wenn kein benutzerdefinierter `TranslationResolver`-Bean definiert ist, stellt die automatische Konfiguration von Spring einen Standard-`BundleTranslationResolver` bereit, der mit den unterstützten Sprachen aus `application.properties` konfiguriert ist.
:::
