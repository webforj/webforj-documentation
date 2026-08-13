---
sidebar_position: 12
title: Translation
description: >-
  Resolve localized strings from resource bundles or custom sources with the t()
  method, MessageFormat placeholders, and browser locale detection.
_i18n_hash: 565ad5b41fe9fa72e363b2db84809cbe
---
# Vertaling <DocChip chip='since' label='25.12' />

webforJ omvat een ingebouwd vertalingssysteem voor het opzoeken van gelokaliseerde strings op sleutel. Het systeem bestaat uit een vertaalresolver die sleutels toewijst aan gelokaliseerde teksten, een `HasTranslation` concerninterface die een handige `t()`-methode biedt, `App.getTranslation()` voor directe toegang overal, automatische lokale detectie vanuit de browser, en ondersteuning voor aangepaste vertaalketens zoals databases.

<AISkillTip skill="webforj-localizing-apps" />

## Vertaalresolver {#translation-resolver}

De vertaalresolver is het systeem dat gelokaliseerde strings voor een gegeven sleutel en lokale opzoekt. webforJ biedt een standaard resolver, `BundleTranslationResolver`, die vertalingen laadt uit Java `ResourceBundle`-eigenschappenbestanden op het classpath. Dit werkt direct uit de doos zonder extra afhankelijkheden.

### Hulpbronbundelbestanden {#resource-bundle-files}

Plaats uw vertaalbestanden in de map `src/main/resources`. De standaardresolver zoekt naar bestanden met de naam `messages` met lokale suffixen volgens de standaard Java `ResourceBundle` naamgevingsconventie:

```text
messages.properties        # Standaard/terugvalvertalingen
messages_en.properties     # Engels
messages_de.properties     # Duits
messages_fr_CA.properties  # Frans (Canada)
```

Elk bestand bevat sleutel-waarde paren. Sleutels zijn identificatoren die u in code gebruikt, en waarden zijn de vertaalde strings. U kunt [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) placeholders zoals `{0}`, `{1}` voor dynamische waarden opnemen:

```properties title="messages.properties"
app.title=Mailbox
menu.inbox=Inbox
menu.outbox=Outbox
greeting=Hallo {0}, je hebt {1} nieuwe berichten
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

De resolver delegeert aan Java's standaard [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) resolutiechain, die locale-matching en terugval automatisch afhandelt.

### Geconfigureerde ondersteunde locales {#configuring-supported-locales}

De instelling `supported-locales` geeft webforJ aan welke locales uw app ondersteunt. Deze lijst wordt gebruikt door autodetectie om de lokale van de browser van de gebruiker te matchen met beschikbare vertalingen. De eerste locale in de lijst wordt gebruikt als de standaard fallback wanneer er geen betere match wordt gevonden. De eigenschaps sleutel is `webforj.i18n.supported-locales` en accepteert een lijst van [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) taaltags, bijvoorbeeld `en, de`.

:::info Meer info
Zie de [Configuratie](/docs/configuration/properties) sectie om te leren hoe u eigenschappen voor verschillende omgevingen kunt instellen.
:::

## De `t()`-methode {#the-t-method}

Componenten die de `HasTranslation` concerninterface implementeren, krijgen toegang tot de `t()`-methode voor het vertalen van tekst. De methode neemt een vertaalsleutel en retourneert de gelokaliseerde string voor de huidige app-locale:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Eenvoudige vertaling
    String title = t("app.title");

    // Vertaling met MessageFormat-parameters
    String greeting = t("greeting", userName, messageCount);

    // Vertaling voor een specifieke locale
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

U kunt ook `App.getTranslation()` direct overal gebruiken zonder de interface te implementeren:

```java
String title = App.getTranslation("app.title");
```

:::info Soepele terugval
Als een vertaalsleutel niet wordt gevonden, retourneert `t()` de sleutel zelf in plaats van een uitzondering te gooien. Dit betekent dat uw app niet breekt als een vertaling ontbreekt. De sleutel wordt zoals deze is weergegeven en een waarschuwing wordt gelogd, zodat u ontbrekende vertalingen tijdens de ontwikkeling kunt volgen.
:::

## Geëntegreerde vertaalde componenten {#implementing-translated-components}

Een vertaald component combineert typisch `HasTranslation` met [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Gebruik `t()` wanneer u UI-elementen maakt om de aanvankelijke vertaalde tekst in te stellen. Om runtime-taalwisseling te ondersteunen, implementeer `LocaleObserver` en werk dezelfde tekst bij in `onLocaleChange()`.

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

:::tip Gegevensbinding
Het gegevensbindingsysteem ondersteunt vertaald validatie- en transformatieteksten met behulp van `Supplier<String>` met `t()`. Zie [dynamische validatieteksten](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynamische transformatormeldingen](/docs/data-binding/transformation#dynamic-transformer-error-messages), en [locale-bewuste Jakarta-validatie](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Aangepaste vertaalketens {#custom-translation-resolvers}

De standaardresolver laadt vertalingen uit Java `ResourceBundle`-eigenschappenbestanden. Om vertalingen vanuit een andere bron te laden, zoals een database of een externe service, implementeer `TranslationResolver`:

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

### Registreren van een aangepaste resolver {#registering-a-custom-resolver}

In een gewone webforJ-app, stel de resolver in voordat de app start, bijvoorbeeld met een [app lifecycle listener](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

In een Spring Boot-app, stel de resolver bloot als een bean:

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

:::info Standaardresolver in Spring Boot
Wanneer er geen aangepaste `TranslationResolver`-bean is gedefinieerd, biedt de Spring auto-configuratie een standaard `BundleTranslationResolver` die is geconfigureerd met de ondersteunde locales uit `application.properties`.
:::
