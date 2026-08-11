---
sidebar_position: 12
title: Translation
description: >-
  Resolve localized strings from resource bundles or custom sources with the t()
  method, MessageFormat placeholders, and browser locale detection.
_i18n_hash: 565ad5b41fe9fa72e363b2db84809cbe
---
# Käännös <DocChip chip='since' label='25.12' />

webforJ sisältää sisäänrakennetun käännösjärjestelmän, jolla voidaan etsiä lokalisoituja merkkijonoja avaimen mukaan. Järjestelmä koostuu käännöksen ratkaisemisesta, joka kartoittaa avaimet lokalisoituun tekstiin, `HasTranslation` -välittämisliittymästä, joka tarjoaa kätevän `t()`-metodin, `App.getTranslation()` suorasta pääsystä missä tahansa, automaattisesta kielentunnistuksesta selaimelta ja tuesta mukautetuille käännöslähteille, kuten tietokannoille.

<AISkillTip skill="webforj-localizing-apps" />

## Käännöksen ratkaiseminen {#translation-resolver}

Käännöksen ratkaiseminen on järjestelmä, joka etsii lokalisoituja merkkijonoja annetulle avaimelle ja paikalle. webforJ tarjoaa oletusarvoisen ratkaisimisen, `BundleTranslationResolver`, joka lataa käännöksiä Java `ResourceBundle` -ominaisuustiedostoista luokka-polussa. Tämä toimii heti ilman lisäriippuvuuksia.

### Ominaisuuspakettitiedostot {#resource-bundle-files}

Sijoita käännöstiedostosi hakemistoon `src/main/resources`. Oletusarvoinen ratkaisija etsii tiedostoja, joiden nimet ovat `messages` paikalliskatseilla, jotka noudattavat Java `ResourceBundle` -nimikettä:

```text
messages.properties        # Oletus/varakohtaiset käännökset
messages_en.properties     # Englanti
messages_de.properties     # Saksa
messages_fr_CA.properties  # Ranska (Kanada)
```

Jokainen tiedosto sisältää avain-arvo-pareja. Avaimet ovat tunnisteita, joita käytät koodissa, ja arvot ovat käännetyt merkkijonot. Voit sisällyttää [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) -paikkamerkkejä kuten `{0}`, `{1}` dynaamisille arvoille:

```properties title="messages.properties"
app.title=Postilaatikko
menu.inbox=Saapuneet
menu.outbox=Lähetetyt
greeting=Hei {0}, sinulla on {1} uutta viestiä
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

Ratkaisija delegoi Java:n standardin [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) ratkaisu ketjulle, joka käsittelee paikantamis ja varakohtaus automaattisesti.

### Tuettujen paikojen määrittäminen {#configuring-supported-locales}

`supported-locales` -asetuksella kerrotaan webforJ:lle, mitkä paikalliset kielet sovelluksesi tukee. Tätä luetteloa käytetään automaattisesti tunnistamiseen käyttäjän selaimen kielen yhteensattumien osalta saatavilla olevia käännöksiä vastaan. Luettelon ensimmäistä paikallista käytetään oletusvarakohtana, kun parempaa vastaavuutta ei löydy. Ominaisuuden avain on `webforj.i18n.supported-locales` ja se hyväksyy luettelon [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) kielitunnuksista, esimerkiksi `en, de`.

:::info Lisätietoja
Katso [Konfigurointi](/docs/configuration/properties) -osiosta, kuinka määrittää ominaisuuksia eri ympäristöille.
:::

## `t()`-metodi {#the-t-method}

Komponentit, jotka toteuttavat `HasTranslation` -välittämisliittymän, saavat käyttöönsä `t()`-metodin tekstin kääntämiseen. Metodi ottaa vastaan käännöksen avaimen ja palauttaa lokalisoidun merkkijonon nykyiselle sovelluskielelle:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Yksinkertainen käännös
    String title = t("app.title");

    // Käännös MessageFormat-parametrien kanssa
    String greeting = t("greeting", userName, messageCount);

    // Käännös tietylle kielelle
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Voit myös käyttää `App.getTranslation()` suoraan missä tahansa ilman, että sinun tarvitsee toteuttaa liittymää:

```java
String title = App.getTranslation("app.title");
```

:::info Sujuva varakohta
Jos käännöksen avainta ei löydy, `t()` palauttaa avaimen itsessään eikä heitä poikkeusta. Tämä tarkoittaa, että sovelluksesi ei riko, jos käännös puuttuu. Avainta näytetään sellaisenaan, ja varoitus kirjataan, jotta voit seurata puuttuvia käännöksiä kehityksen aikana.
:::

## Käännettyjen komponenttien toteuttaminen {#implementing-translated-components}

Käännetty komponentti yhdistää tyypillisesti `HasTranslation` ja [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Käytä `t()` luodessasi UI-elementtejä asettaaksesi alkuperäinen käännetty teksti. Tukeaksesi aikarajan kielen vaihtamista, toteuta `LocaleObserver` ja päivitä sama teksti `onLocaleChange()`-menetelmässä.

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

:::tip Tietojen sitominen
Tietosidontajärjestelmä tukee käännettyjä validointiviestejä ja muunnosviestejä käyttämällä `Supplier<String>` ja `t()`. Katso [dynaamiset validointiviestit](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynaamiset muunnosviestit](/docs/data-binding/transformation#dynamic-transformer-error-messages) ja [kielelle tietoiset Jakarta Validointi](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Mukautetut käännöksen ratkaisijat {#custom-translation-resolvers}

Oletusratkaisija lataa käännökset Java `ResourceBundle` -ominaisuustiedostoista. Kuvitellaksesi käännöksiä eri lähteistä, kuten tietokannasta tai etäpalvelusta, toteuta `TranslationResolver`:

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

### Mukautetun ratkaisijan rekisteröinti {#registering-a-custom-resolver}

Varsinaisessa webforJ-sovelluksessa aseta ratkaisija ennen sovelluksen käynnistämistä, esimerkiksi käyttämällä [sovelluksen elinkaaren kuuntelijaa](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

Spring Boot -sovelluksessa, tuo ratkaisija beaniksi:

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

:::info Oletusratkaisija Spring Bootissa
Kun mukautettua `TranslationResolver` -beania ei määritetä, Springin automaattinen konfigurointi tarjoaa oletus `BundleTranslationResolver` -ratkaisijan, joka on määritetty tuetuilla kielillä `application.properties` -tiedostosta.
:::
