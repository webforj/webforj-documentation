---
sidebar_position: 12
title: Translation
sidebar_class_name: new-content
_i18n_hash: 57626c2969592f2378a55eff0dd01d48
---
# Käännös <DocChip chip='since' label='25.12' />

webforJ sisältää sisäänrakennetun käännöksen, joka etsii paikallisia merkkijonoja avaimen mukaan. Järjestelmä koostuu käännösresoluutiosta, joka yhdistää avaimet paikallisiin teksteihin, `HasTranslation` huolen interface, joka tarjoaa kätevän `t()`-menetelmän, `App.getTranslation()` suoran käyttöön missä tahansa, automaattisesta paikallisuuden havaitsemisesta selaimesta ja tuesta mukautetuille käännöslähteille, kuten tietokannoille.

## Käännösresoluutio {#translation-resolver}

Käännösresoluutio on järjestelmä, joka etsii paikallisia merkkijonoja tietyllä avaimella ja paikallisuudella. webforJ tarjoaa oletusresoluution, `BundleTranslationResolver`, joka lataa käännöksiä Java `ResourceBundle` -ominaisuus tiedostoista luokkahakualueella. Tämä toimii suoraan ilman lisäriippuvuuksia.

### Resurssikokoelmat

Aseta käännöstiedostosi `src/main/resources`-hakemistoon. Oletusresoluutiot etsii tiedostoja, jotka on nimetty `messages` paikallisuusliitteillä, jotka seuraavat Java `ResourceBundle` -nimistön standardia:

```text
messages.properties        # Oletus/takaisin käännökset
messages_en.properties     # Englanti
messages_de.properties     # Saksa
messages_fr_CA.properties  # Ranska (Kanada)
```

Jokainen tiedosto sisältää avain-arvo-pareja. Avaimet ovat tunnisteita, joita käytät koodissa, ja arvot ovat käännettyjä merkkijonoja. Voit sisällyttää [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) paikkamerkkejä, kuten `{0}`, `{1}` dynaamisille arvoille:

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

Resoluutio delegoi Java:n standardille [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) resoluutioketjulle, joka hoitaa paikallisuuden vastaavuuden ja varat automaattisesti.

### Tuettujen paikallisuuksien määrittäminen {#configuring-supported-locales}

`supported-locales`-asetus kertoo webforJ:lle, mitkä paikallisuudet sovelluksesi tukee. Tätä luetteloa käytetään automaattisessa havaitsemisessa, jotta käyttäjän selaimen paikallisuus voidaan korvata käytettävissä olevilla käännöksillä. Ensimmäistä luettelon paikallisuutta käytetään oletusvarana, kun parempaa vastaavuutta ei löydy. Ominaisuusavain on `webforj.i18n.supported-locales` ja se hyväksyy luettelon [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) kielitunnisteista, esim. `en, de`.

:::info Lisätietoja
Katso [Määritykset](/docs/configuration/properties) -osio oppiaksesi, kuinka määrittää ominaisuuksia eri ympäristöille.
:::

## `t()`-menetelmä {#the-t-method}

Komponentit, jotka toteuttavat `HasTranslation` huolen interface:n, saavat pääsyn `t()`-menetelmään tekstin kääntämiseen. Menetelmä ottaa käännösavaimen ja palauttaa paikallistetun merkkijonon nykyiselle sovelluksen paikallisuudelle:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Yksinkertainen käännös
    String title = t("app.title");

    // Käännös MessageFormat-parametreilla
    String greeting = t("greeting", userName, messageCount);

    // Käännös tietyssä paikallisuudessa
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Voit käyttää myös `App.getTranslation()` suoraan missä tahansa ilman, että toteutat rajapintaa:

```java
String title = App.getTranslation("app.title");
```

:::info Sujuva varma
Jos käännösavain ei löydy, `t()` palauttaa avaimen itsessään sen sijaan, että heittäisi poikkeusta. Tämä tarkoittaa, ettei sovelluksesi jumitu, jos käännös puuttuu. Avainta näytetään sellaisenaan, ja varoitus kirjataan, jotta voit seurata puuttuvia käännöksiä kehitysvaiheessa.
:::

## Käännettyjen komponenttien toteuttaminen {#implementing-translated-components}

Käännetty komponentti yhdistää tyypillisesti `HasTranslation` ja [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Käytä `t()` UI-elementtien luomisessa, jotta asetat alkuperäiset käännetyt tekstit. Tuen tarjoamiseksi käyttöaikaiselle kielenvaihdolle, toteuta `LocaleObserver` ja päivitä sama teksti `onLocaleChange()` -menetelmässä.

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

:::tip Databindaus
Databindausjärjestelmä tukee käännettyjä validointi- ja muunnosviestejä käyttämällä `Supplier<String>` ja `t()`. Katso [dynaamiset validointiviestit](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynaamiset muuntoviestit](/docs/data-binding/transformation#dynamic-transformer-error-messages) ja [paikallisuustietoisen Jakarta Validoinnin](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Mukautetut käännösresoluutorit {#custom-translation-resolvers}

Oletusresoluutiolataa käännöksiä Java `ResourceBundle` -ominaisuus tiedostoista. Ladata käännöksiä eri lähteistä, kuten tietokannasta tai etäpalvelusta, toteuta `TranslationResolver`:

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

Yksinkertaisessa webforJ-sovelluksessa aseta resolver ennen sovelluksen käynnistämistä, esimerkiksi käyttäen [sovelluksen elinkaarikuuntelijaa](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

Spring Boot -sovelluksessa tuo resolver beanina:

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

:::info Oletusresoluutio Spring Bootissa
Kun mukautettua `TranslationResolver` -beania ei ole määritelty, Springin automaattinen konfigurointi tarjoaa oletus `BundleTranslationResolver` -resoluution, joka on määritetty käytettävissä olevilla paikallisuuksilla `application.properties` -tiedostosta.
:::
