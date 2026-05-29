---
sidebar_position: 12
title: Translation
_i18n_hash: 276130dcd9ff26441b844042d4cdc5dd
---
# Vertaling <DocChip chip='since' label='25.12' />

webforJ bevat een ingebouwd vertaalsysteem voor het opzoeken van gelokaliseerde strings op sleutel. Het systeem bestaat uit een vertaalresolver die sleutels aan gelokaliseerde tekst koppelt, een `HasTranslation` concern interface dat een handige `t()` methode biedt, `App.getTranslation()` voor directe toegang overal, automatische provinciale detectie vanuit de browser en ondersteuning voor aangepaste vertaale bronnen zoals databases.

<AISkillTip skill="webforj-localizing-apps" />

## Vertaalresolver {#translation-resolver}

De vertaalresolver is het systeem dat gelokaliseerde strings voor een gegeven sleutel en provincie opzoekt. webforJ biedt een standaard resolver, `BundleTranslationResolver`, die vertalingen laadt vanuit Java `ResourceBundle` eigenschapsbestanden op het classpath. Dit werkt direct uit de doos zonder extra afhankelijkheden.

### Hulpbundelbestanden

Plaats uw vertaalbestanden in de `src/main/resources` directory. De standaardresolver zoekt naar bestanden met de naam `messages` met provincie suffixen die de standaard Java `ResourceBundle` naamgevingsconventie volgen:

```text
messages.properties        # Standaard/noodgevallen vertalingen
messages_en.properties     # Engels
messages_de.properties     # Duits
messages_fr_CA.properties  # Frans (Canada)
```

Elk bestand bevat sleutel-waarde paren. Sleutels zijn identificatoren die u in de code gebruikt, en waarden zijn de vertaalde strings. U kunt [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) plaatsen zoals `{0}`, `{1}` voor dynamische waarden:

```properties title="messages.properties"
app.title=Mailbox
menu.inbox=Inbox
menu.outbox=Verzonden
greeting=Hallo {0}, u heeft {1} nieuwe berichten
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

De resolver delegeert aan Java's standaard [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) resolutie keten, die automatisch provincie matching en fallback behandelt.

### Geconfigureerde ondersteunde provincies {#configuring-supported-locales}

De instelling `supported-locales` vertelt webforJ welke provincies uw app ondersteunt. Deze lijst wordt gebruikt door autodetectie om de provinciale voorkeur van de gebruiker te vergelijken met beschikbare vertalingen. De eerste provincie in de lijst wordt gebruikt als de standaard fallback wanneer er geen betere match wordt gevonden. De eigenschaps sleutel is `webforj.i18n.supported-locales` en accepteert een lijst van [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) taal tags, bijvoorbeeld `en, de`.

:::info Meer info
Zie de [Configuratie](/docs/configuration/properties) sectie om te leren hoe u eigenschappen voor verschillende omgevingen kunt instellen.
:::

## De `t()` methode {#the-t-method}

Componenten die de `HasTranslation` concern interface implementeren, krijgen toegang tot de `t()` methode voor het vertalen van tekst. De methode neemt een vertaal sleutel en retourneert de gelokaliseerde string voor de huidige app provincie:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Eenvoudige vertaling
    String title = t("app.title");

    // Vertaling met MessageFormat parameters
    String greeting = t("greeting", userName, messageCount);

    // Vertaling voor een specifieke provincie
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

U kunt ook `App.getTranslation()` rechtstreeks overal gebruiken zonder de interface te implementeren:

```java
String title = App.getTranslation("app.title");
```

:::info Eerlijke fallback
Als een vertaal sleutel niet wordt gevonden, retourneert `t()` de sleutel zelf in plaats van een uitzondering te genereren. Dit betekent dat uw app niet crasht als er een vertaling ontbreekt. De sleutel wordt zoals is weergegeven, en er wordt een waarschuwing gelogd zodat u ontbrekende vertalingen tijdens de ontwikkeling kunt bijhouden.
:::

## Geïmplementeerde vertaalde componenten {#implementing-translated-components}

Een vertaald component combineert doorgaans `HasTranslation` met [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Gebruik `t()` wanneer u UI-elementen creëert om de initiële vertaalde tekst in te stellen. Om runtime taalwisseling te ondersteunen, implementeer `LocaleObserver` en werk dezelfde tekst bij in `onLocaleChange()`.

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
Het gegevensbindingsysteem ondersteunt vertaalde validatie- en transformatieberichten met behulp van `Supplier<String>` met `t()`. Zie [dynamische validatieberichten](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynamische transformeringsberichten](/docs/data-binding/transformation#dynamic-transformer-error-messages), en [provincie-bewuste Jakarta Validatie](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Aangepaste vertaal resolvers {#custom-translation-resolvers}

De standaard resolver laadt vertalingen vanuit Java `ResourceBundle` eigenschapsbestanden. Om vertalingen vanuit een andere bron te laden, zoals een database of een externe dienst, implementeer `TranslationResolver`:

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

In een eenvoudige webforJ app, stel de resolver in voordat de app start, bijvoorbeeld met een [app levenscyclus luisteraar](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

In een Spring Boot app, expose de resolver als een bean:

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
Wanneer geen aangepaste `TranslationResolver` bean is gedefinieerd, biedt de Spring auto-configuratie een standaard `BundleTranslationResolver` geconfigureerd met de ondersteunde provincies vanuit `application.properties`.
:::
