---
sidebar_position: 12
title: Translation
sidebar_class_name: new-content
_i18n_hash: 4d6ff94e519d114cacbfcb325ba0598c
---
# Translation <DocChip chip='since' label='25.12' />

webforJ enthält ein eingebautes Übersetzungssystem, um lokalisierten Text anhand von Schlüsseln abzurufen. Das System besteht aus einem Übersetzungsresolver, der Schlüssel auf lokalisierten Text abbildet, einer `HasTranslation` Schnittstelle, die eine praktische `t()` Methode bereitstellt, `App.getTranslation()` für den direkten Zugriff überall, automatische Gebietsschilderkennung vom Browser und Unterstützung für benutzerdefinierte Übersetzungsquellen wie Datenbanken.

## Übersetzungsresolver {#translation-resolver}

Der Übersetzungsresolver ist das System, das lokalisierten Text für einen gegebenen Schlüssel und ein Gebietsschema sucht. webforJ bietet einen Standardresolver, `BundleTranslationResolver`, der Übersetzungen aus Java `ResourceBundle`-Eigenschaftsdateien im Klassenpfad lädt. Dies funktioniert sofort ohne zusätzliche Abhängigkeiten.

### Ressourcenbündel-Dateien

Platzieren Sie Ihre Übersetzungsdateien im Verzeichnis `src/main/resources`. Der Standardresolver sucht nach Dateien mit dem Namen `messages` und Gebietsschema-Suffixen gemäß der Standard-Java `ResourceBundle` Benennungskonvention:

```text
messages.properties        # Standard-/Fallback-Übersetzungen
messages_en.properties     # Englisch
messages_de.properties     # Deutsch
messages_fr_CA.properties  # Französisch (Kanada)
```

Jede Datei enthält Schlüssel-Wert-Paare. Schlüssel sind Bezeichner, die Sie im Code verwenden, und Werte sind die übersetzten Strings. Sie können [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) Platzhalter wie `{0}`, `{1}` für dynamische Werte einfügen:

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

Der Resolver delegiert an die Standard [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) Auflösungsreihe von Java, die die Übereinstimmung des Gebietsschemas und die Fallbacks automatisch behandelt.

### Unterstützte Gebietsstandards konfigurieren {#configuring-supported-locales}

Die Einstellung `supported-locales` teilt webforJ mit, welche Gebietsschemas Ihre App unterstützt. Diese Liste wird von der automatischen Erkennung verwendet, um die Gebietsschemata des Browsers des Benutzers mit verfügbaren Übersetzungen abzugleichen. Das erste Gebietsschema in der Liste wird als Standard-Fallback verwendet, wenn keine bessere Übereinstimmung gefunden wird. Der Eigenschaftsschlüssel ist `webforj.i18n.supported-locales` und akzeptiert eine Liste von [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) Sprachbezeichnern, wie z.B. `en, de`.

:::info Weitere Informationen
Siehe den Abschnitt [Konfiguration](/docs/configuration/properties), um zu erfahren, wie Sie Eigenschaften für verschiedene Umgebungen festlegen.
:::

## Die `t()` Methode {#the-t-method}

Komponenten, die die `HasTranslation` Schnittstelle implementieren, erhalten Zugriff auf die `t()` Methode zum Übersetzen von Text. Die Methode nimmt einen Übersetzungsschlüssel und gibt den lokalisierten String für das aktuelle App-Gebietsschema zurück:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Einfache Übersetzung
    String title = t("app.title");

    // Übersetzung mit MessageFormat-Parametern
    String greeting = t("greeting", userName, messageCount);

    // Übersetzung für ein bestimmtes Gebietsschema
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Sie können auch `App.getTranslation()` direkt überall verwenden, ohne die Schnittstelle zu implementieren:

```java
String title = App.getTranslation("app.title");
```

:::info Sanfter Fallback
Wenn ein Übersetzungsschlüssel nicht gefunden wird, gibt `t()` den Schlüssel selbst zurück, anstatt eine Ausnahme auszulösen. Das bedeutet, Ihre App funktioniert weiterhin, auch wenn eine Übersetzung fehlt. Der Schlüssel wird unverändert angezeigt, und eine Warnung wird protokolliert, damit Sie fehlende Übersetzungen während der Entwicklung verfolgen können.
:::

## Übersetzte Komponenten implementieren {#implementing-translated-components}

Eine übersetzte Komponente kombiniert typischerweise `HasTranslation` mit [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Verwenden Sie `t()`, wenn Sie UI-Elemente erstellen, um den anfänglichen übersetzten Text festzulegen. Um die Sprachanpassung zur Laufzeit zu unterstützen, implementieren Sie `LocaleObserver` und aktualisieren Sie denselben Text in `onLocaleChange()`.

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
Das Datenbindungssystem unterstützt übersetzte Validierungs- und Transformationsnachrichten unter Verwendung von `Supplier<String>` mit `t()`. Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynamische Transformatornachrichten](/docs/data-binding/transformation#dynamic-transformer-error-messages) und [gebietsbezogene Jakarta Validierung](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Benutzerdefinierte Übersetzungsresolver {#custom-translation-resolvers}

Der Standardresolver lädt Übersetzungen aus Java `ResourceBundle`-Eigenschaftsdateien. Um Übersetzungen aus einer anderen Quelle, wie z.B. einer Datenbank oder einem Remote-Service, zu laden, implementieren Sie `TranslationResolver`:

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

In einer normalen webforJ-App setzen Sie den Resolver, bevor die App startet, z.B. unter Verwendung eines [App-Lebenszykluslisteners](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

In einer Spring Boot-App stellen Sie den Resolver als Bean zur Verfügung:

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
Wenn kein benutzerdefinierter `TranslationResolver`-Bean definiert ist, stellt die Spring-Auto-Konfiguration einen Standard `BundleTranslationResolver` bereit, der mit den unterstützten Gebietsschemata aus `application.properties` konfiguriert ist.
:::
