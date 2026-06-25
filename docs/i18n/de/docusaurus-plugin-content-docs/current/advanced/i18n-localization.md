---
sidebar_position: 12
title: Translation
_i18n_hash: 276130dcd9ff26441b844042d4cdc5dd
---
# Übersetzung <DocChip chip='since' label='25.12' />

webforJ enthält ein integriertes Übersetzungssystem zum Abrufen lokalisierter Zeichenfolgen über Schlüssel. Das System besteht aus einem Übersetzungsresolver, der Schlüssel auf lokalisierte Texte abbildet, einem `HasTranslation` Concern-Interface, das eine bequeme `t()`-Methode bereitstellt, `App.getTranslation()` für den direkten Zugriff überall, automatischer Erkennung der Regionaleinstellung aus dem Browser und Unterstützung für benutzerdefinierte Übersetzungsquellen wie Datenbanken.

<AISkillTip skill="webforj-localizing-apps" />

## Übersetzungsresolver {#translation-resolver}

Der Übersetzungsresolver ist das System, das lokalisierte Zeichenfolgen für einen gegebenen Schlüssel und eine Regionaleinstellung nachschlägt. webforJ bietet einen Standardresolver, `BundleTranslationResolver`, der Übersetzungen aus Java `ResourceBundle`-Eigenschaftsdateien im Klassenpfad lädt. Dies funktioniert sofort ohne zusätzliche Abhängigkeiten.

### Ressourcenbündeldateien

Platzieren Sie Ihre Übersetzungsdateien im Verzeichnis `src/main/resources`. Der Standardresolver sucht nach Dateien mit dem Namen `messages`, die den Standardbenennungsrichtlinien für Java `ResourceBundle` folgen:

```text
messages.properties        # Standard-/Fallback-Übersetzungen
messages_en.properties     # Englisch
messages_de.properties     # Deutsch
messages_fr_CA.properties  # Französisch (Kanada)
```

Jede Datei enthält Schlüssel-Wert-Paare. Schlüssel sind Bezeichner, die Sie im Code verwenden, und Werte sind die übersetzten Zeichenfolgen. Sie können [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) Platzhalter wie `{0}`, `{1}` für dynamische Werte einfügen:

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

Der Resolver delegiert an die standardmäßige [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) Auflösungskette von Java, die die Sprachübereinstimmung und den Fallback automatisch behandelt.

### Unterstützte Regionen einstellen {#configuring-supported-locales}

Die Einstellung `supported-locales` sagt webforJ, welche Regionen Ihre App unterstützt. Diese Liste wird von der automatischen Erkennung verwendet, um die Regionaleinstellung des Browsers des Benutzers mit den verfügbaren Übersetzungen abzugleichen. Die erste Region in der Liste wird als Standardfallback verwendet, wenn keine bessere Übereinstimmung gefunden wird. Der Eigenschaftsschlüssel ist `webforj.i18n.supported-locales` und akzeptiert eine Liste von [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) Sprach-Tags, zum Beispiel `en, de`.

:::info Mehr Informationen
Siehe den Abschnitt [Konfiguration](/docs/configuration/properties), um zu erfahren, wie Eigenschaften für verschiedene Umgebungen festgelegt werden.
:::

## Die `t()`-Methode {#the-t-method}

Komponenten, die das `HasTranslation` Concern-Interface implementieren, erhalten Zugriff auf die `t()`-Methode zur Übersetzung von Texten. Die Methode nimmt einen Übersetzungsschlüssel und gibt die lokalisierte Zeichenfolge für die aktuelle App-Regionaleinstellung zurück:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Einfache Übersetzung
    String title = t("app.title");

    // Übersetzung mit MessageFormat-Parametern
    String greeting = t("greeting", userName, messageCount);

    // Übersetzung für eine bestimmte Region
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Sie können auch `App.getTranslation()` direkt überall verwenden, ohne das Interface zu implementieren:

```java
String title = App.getTranslation("app.title");
```

:::info Sanfter Fallback
Wenn ein Übersetzungsschlüssel nicht gefunden wird, gibt `t()` den Schlüssel selbst zurück, anstatt eine Ausnahme auszulösen. Das bedeutet, dass Ihre App nicht abstürzt, wenn eine Übersetzung fehlt. Der Schlüssel wird so angezeigt, wie er ist, und eine Warnung wird protokolliert, damit Sie fehlende Übersetzungen während der Entwicklung verfolgen können.
:::

## Implementierung übersetzter Komponenten {#implementing-translated-components}

Ein übersetzter Bestandteil kombiniert typischerweise `HasTranslation` mit [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Verwenden Sie `t()`, wenn Sie UI-Elemente erstellen, um den initialen übersetzten Text festzulegen. Um die Sprachen zur Laufzeit zu wechseln, implementieren Sie `LocaleObserver` und aktualisieren denselben Text in `onLocaleChange()`.

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
Das Datenbindungssystem unterstützt übersetzte Validierungs- und Transformationsnachrichten mit `Supplier<String>` zusammen mit `t()`. Siehe [dynamische Validierungsnachrichten](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynamische Transformatornachrichten](/docs/data-binding/transformation#dynamic-transformer-error-messages) und [locale-bewusste Jakarta-Validierung](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Benutzerdefinierte Übersetzungsresolver {#custom-translation-resolvers}

Der Standardresolver lädt Übersetzungen aus Java `ResourceBundle`-Eigenschaftsdateien. Um Übersetzungen aus einer anderen Quelle zu laden, wie z. B. einer Datenbank oder einem Remote-Dienst, implementieren Sie `TranslationResolver`:

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

### Registrierung eines benutzerdefinierten Resolvers {#registering-a-custom-resolver}

In einer einfachen webforJ-App setzen Sie den Resolver vor dem Start der App, beispielsweise mit einem [App-Lebenszyklus-Listener](/docs/advanced/lifecycle-listeners):

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
Wenn keine benutzerdefinierte `TranslationResolver`-Bean definiert ist, stellt die Spring-Auto-Konfiguration einen Standard-`BundleTranslationResolver` bereit, der mit den unterstützten Regionen aus `application.properties` konfiguriert ist.
:::
