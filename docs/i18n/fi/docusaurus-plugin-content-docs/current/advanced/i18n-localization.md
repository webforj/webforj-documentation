---
sidebar_position: 12
title: Translation
_i18n_hash: 276130dcd9ff26441b844042d4cdc5dd
---
# Käännös <DocChip chip='since' label='25.12' />

webforJ sisältää sisäänrakennetun käännösjärjestelmän lokalisoitujen merkkijonojen etsimistä varten avaimen avulla. Järjestelmä koostuu käännösresolversista, joka yhdistää avaimet lokalisoituihin teksteihin, `HasTranslation` -huolenaiherajapinnasta, joka tarjoaa kätevän `t()`-metodin, `App.getTranslation()`-pääsyn mihin tahansa, automaattisesta paikallisen havaitsemisesta selaimesta ja tuesta mukautetuille käännöslähteille, kuten tietokannoille.

<AISkillTip skill="webforj-localizing-apps" />

## Käännösresolveri {#translation-resolver}

Käännösresolveri on järjestelmä, joka etsii lokalisoituja merkkijonoja tietyn avaimen ja paikallisen mukaan. webforJ tarjoaa oletusresolverin, `BundleTranslationResolver`, joka lataa käännöksiä Java `ResourceBundle` -ominaisuus tiedostoista luokkapolussa. Tämä toimii heti ilman lisäriippuvuuksia.

### Ominaisuuspaketit

Sijoita käännöstiedostosi `src/main/resources` -hakemistoon. Oletusresolveri etsii `messages`-nimisiä tiedostoja, joissa on paikallisliitteet noudattaen Java `ResourceBundle` -nimikointikäytäntöä:

```text
messages.properties        # Oletus/varavalmisteet
messages_en.properties     # Englanti
messages_de.properties     # Saksa
messages_fr_CA.properties  # Ranska (Kanada)
```

Jokainen tiedosto sisältää avain-arvo-parit. Avaimet ovat tunnisteita, joita käytät koodissa, ja arvot ovat käännettyjä merkkijonoja. Voit sisällyttää [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) -paikkoja, kuten `{0}`, `{1}` dynaamisille arvoille:

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

Resolveri delegoi Java:n standardin [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) ratkaisuputkeen, joka käsittelee paikallisten vastaavuuksien ja varatäytön automaattisesti.

### Tuettujen lokaliteettien määrittäminen {#configuring-supported-locales}

`supported-locales` -asetuksella kerrotaan webforJ:lle, mitkä paikalliset asetukset sovelluksesi tukee. Tämä luetteloa käytetään automaattisessa tunnistamisessa käyttäjän selaimen localea vastaan saatavilla olevien käännösten osalta. Luettelon ensimmäistä localea käytetään oletusvaratäytteenä, kun parempaa vastaavuutta ei löydy. Ominaisuusavain on `webforj.i18n.supported-locales` ja se hyväksyy luettelon [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) kielitunnisteista, esimerkiksi `en, de`.

:::info Lisätietoja
Katso [Konfigurointi](/docs/configuration/properties) -osio oppiaksesi, kuinka asettaa ominaisuuksia eri ympäristöille.
:::

## `t()`-metodi {#the-t-method}

Komponentit, jotka toteuttavat `HasTranslation` -huolenaiherajapinnan, saavat pääsyn `t()`-metodiin tekstin kääntämistä varten. Metodi ottaa käännösavaimen ja palauttaa lokalisoidun merkkijonon nykyisen sovelluksen paikallisen mukaan:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Yksinkertainen käännös
    String title = t("app.title");

    // Käännös MessageFormat -parametreilla
    String greeting = t("greeting", userName, messageCount);

    // Käännös tietylle paikalliselle
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Voit myös käyttää `App.getTranslation()` suoraan missä tahansa ilman rajapinnan toteuttamista:

```java
String title = App.getTranslation("app.title");
```

:::info Sujuva varatäyttö
Jos käännösavainta ei löydy, `t()` palauttaa avaimen itsessään sen sijaan, että heittäisi poikkeuksen. Tämä tarkoittaa, että sovelluksesi ei katkea, jos käännös puuttuu. Avain näytetään sellaisenaan ja varoitus tallennetaan, jotta voit seurata puuttuvia käännöksiä kehityksen aikana.
:::

## Käännettyjen komponenttien toteuttaminen {#implementing-translated-components}

Käännetty komponentti yhdistää tyypillisesti `HasTranslation` ja [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Käytä `t()` metodia, kun luot UI-elementtejä asetaksesi alkuperäisen käännetyn tekstin. Tukeaksesi aikarajan kielellistä vaihtamista, toteuta `LocaleObserver` ja päivitä sama teksti `onLocaleChange()` -metodissa.

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

:::tip Datan sitominen
Datan sitomusjärjestelmä tukee käännettyjä validoitus- ja muunnosviestejä käyttäen `Supplier<String>` yhdistettynä `t()`-metodiin. Katso [dynaamiset validoitusviestit](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynaamiset muuntajaviestit](/docs/data-binding/transformation#dynamic-transformer-error-messages) ja [paikallisesti tietoisa Jakarta Validointi](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Mukautetut käännösresolverit {#custom-translation-resolvers}

Oletusresolveri lataa käännöksiä Java `ResourceBundle` -ominaistiedostoista. Ladataaksesi käännöksiä erilaisista lähteistä, kuten tietokannasta tai etäpalvelusta, toteuta `TranslationResolver`:

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

### Mukautetun resolverin rekisteröinti {#registering-a-custom-resolver}

Yksinkertaisessa webforJ-sovelluksessa aseta resolveri ennen sovelluksen käynnistymistä, esimerkiksi käyttämällä [sovelluksen elinkaarikuuntelijaa](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

Spring Boot -sovelluksessa, tarjoa resolveri beanina:

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

:::info Oletusresolveri Spring Bootissa
Kun mukautettua `TranslationResolver` -beania ei ole määritelty, Springin automaattinen konfigurointi tarjoaa oletus `BundleTranslationResolver` -resolverin, joka on määritetty tuettujen lokaliteettien mukaan `application.properties`-tiedostossa.
:::
