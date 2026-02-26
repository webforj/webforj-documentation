---
sidebar_position: 12
title: Translation
sidebar_class_name: new-content
_i18n_hash: 57626c2969592f2378a55eff0dd01d48
---
# Vertaling <DocChip chip='since' label='25.12' />

webforJ bevat een ingebouwd vertalingsysteem voor het opzoeken van gelokaliseerde strings op basis van sleutels. Het systeem bestaat uit een vertalingsresolver die sleutels koppelt aan gelokaliseerde tekst, een `HasTranslation` concern interface die een handige `t()` methode biedt, `App.getTranslation()` voor directe toegang overal, automatische locale-detectie vanuit de browser en ondersteuning voor aangepaste vertalingsbronnen zoals databases.

## Vertalingsresolver {#translation-resolver}

De vertalingsresolver is het systeem dat gelokaliseerde strings opzoekt voor een gegeven sleutel en locale. webforJ biedt een standaard resolver, `BundleTranslationResolver`, die vertalingen laadt vanuit Java `ResourceBundle` property-bestanden op het classpath. Dit werkt direct zonder extra afhankelijkheden.

### Resource bundle bestanden

Plaats uw vertaalbestanden in de map `src/main/resources`. De standaard resolver zoekt naar bestanden met de naam `messages` met locale-suffixen die de standaard Java `ResourceBundle` naamgevingsconventie volgen:

```text
messages.properties        # Standaard/fallback vertalingen
messages_en.properties     # Engels
messages_de.properties     # Duits
messages_fr_CA.properties  # Frans (Canada)
```

Elk bestand bevat sleutel-waardeparen. Sleutels zijn identificeerbare elementen die u in de code gebruikt, en waarden zijn de vertaalde strings. U kunt [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) placeholders zoals `{0}`, `{1}` gebruiken voor dynamische waarden:

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

De resolver geeft de standaard [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) resolutieketen door, die locale-matching en fallback automatisch afhandelt.

### Ondersteunde locales configureren {#configuring-supported-locales}

De instelling `supported-locales` vertelt webforJ welke locales uw app ondersteunt. Deze lijst wordt gebruikt door autodetectie om de locale van de browser van de gebruiker te vergelijken met beschikbare vertalingen. De eerste locale in de lijst wordt gebruikt als de standaard fallback wanneer er geen betere overeenstemming wordt gevonden. De eigenschapssleutel is `webforj.i18n.supported-locales` en accepteert een lijst van [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) taal-tags, bijvoorbeeld `en, de`.

:::info Meer info
Zie de [Configuratie](/docs/configuration/properties) sectie om te leren hoe u eigenschappen voor verschillende omgevingen kunt instellen.
:::

## De `t()` methode {#the-t-method}

Componenten die de `HasTranslation` concern interface implementeren, krijgen toegang tot de `t()` methode voor het vertalen van tekst. De methode neemt een vertaal sleutel en retourneert de gelokaliseerde string voor de huidige app-locale:

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

U kunt ook `App.getTranslation()` rechtstreeks overal gebruiken zonder de interface te implementeren:

```java
String title = App.getTranslation("app.title");
```

:::info Graceful fallback
Als een vertaal sleutel niet wordt gevonden, retourneert `t()` de sleutel zelf in plaats van een uitzondering te gooien. Dit betekent dat uw app niet crasht als er een vertaling ontbreekt. De sleutel wordt weergegeven zoals deze is, en er wordt een waarschuwing gelogd zodat u ontbrekende vertalingen tijdens de ontwikkeling kunt volgen.
:::

## Geëigende vertaalde componenten implementeren {#implementing-translated-components}

Een vertaalde component combineert doorgaans `HasTranslation` met [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Gebruik `t()` bij het maken van UI-elementen om de initiële vertaalde tekst in te stellen. Om runtime-taalwisseling te ondersteunen, implementeer `LocaleObserver` en werk dezelfde tekst bij in `onLocaleChange()`.

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

:::tip Gegevensbinding
Het gegevensbindingsysteem ondersteunt vertaalde validatie- en transformatieberichten met `Supplier<String>` met `t()`. Zie [dynamische validatieberichten](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynamische transformatieberichten](/docs/data-binding/transformation#dynamic-transformer-error-messages), en [locale-gevoelige Jakarta Validatie](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Aangepaste vertalingsresolvers {#custom-translation-resolvers}

De standaard resolver laadt vertalingen vanuit Java `ResourceBundle` property-bestanden. Om vertalingen vanuit een andere bron te laden, zoals een database of een externe service, implementeer `TranslationResolver`:

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

In een gewone webforJ-app, stel de resolver in voordat de app start, bijvoorbeeld met een [app-lifecycle listener](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

In een Spring Boot-app, exposeer de resolver als een bean:

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

:::info Standaard resolver in Spring Boot
Wanneer er geen aangepaste `TranslationResolver` bean is gedefinieerd, biedt Spring auto-configuratie een standaard `BundleTranslationResolver` die is geconfigureerd met de ondersteunde locales vanuit `application.properties`.
:::
