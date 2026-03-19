---
sidebar_position: 12
title: Translation
sidebar_class_name: new-content
_i18n_hash: 4d6ff94e519d114cacbfcb325ba0598c
---
# Käännös <DocChip chip='since' label='25.12' />

webforJ sisältää sisäänrakennetun käännösten hallintajärjestelmän, joka etsii lokalisointeja avaimen mukaan. Järjestelmä koostuu käännösten ratkaisemisesta, joka yhdistää avaimet lokalisointiteksteihin, `HasTranslation` -interfacesta, joka tarjoaa kätevän `t()`-menetelmän, `App.getTranslation()` -menetelmät suoran pääsyn mahdollistamiseksi, automaattisesta alueen tunnistamisesta selaimesta sekä tuesta mukautetuille käännösvaroille, kuten tietokannoille.

## Käännösten ratkaiseminen {#translation-resolver}

Käännösten ratkaiseminen on järjestelmä, joka etsii lokalisointeja annetulle avaimelle ja alueelle. webforJ tarjoaa oletusratkaisijan, `BundleTranslationResolver`, joka lataa käännöksiä Java `ResourceBundle` -ominaisuusfileistä luokkapolussa. Tämä toimii suoraan ilman lisäriippuvuuksia.

### Resurssipakettitiedostot

Aseta käännöstiedostosi kansioon `src/main/resources`. Oletusratkaisija etsii tiedostoja, joiden nimet ovat `messages` ja jotka sisältävät alueen päätteitä noudattaen Java `ResourceBundle` -nimikonventiota:

```text
messages.properties        # Oletus/varakäännökset
messages_en.properties     # Englanti
messages_de.properties     # Saksa
messages_fr_CA.properties  # Ranska (Kanada)
```

Jokainen tiedosto sisältää avain-arvo-parit. Avaimet ovat tunnistajia, joita käytät koodissa, ja arvot ovat käännetyt merkkijonot. Voit sisällyttää [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) -paikkamerkkejä kuten `{0}`, `{1}` dynaamisille arvoille:

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

Ratkaisija siirtää Java:n standardin [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) ratkaisuputkeen, joka käsittelee alueen vastaavuuden ja varavalinnat automaattisesti.

### Tuettujen alueiden määrittäminen {#configuring-supported-locales}

`supported-locales` asetus kertoo webforJ:lle, mitä alueita sovelluksesi tukee. Tätä luetteloa käytetään automaattiseen tunnistukseen käyttäjän selaimen alueen vertaamiseksi saatavilla oleviin käännöksiin. Luettelon ensimmäistä aluetta käytetään oletusvaraversiona, kun parempaa vaihtoehtoa ei löydy. Ominaisuuden avain on `webforj.i18n.supported-locales` ja se hyväksyy luettelon [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) kieltä tunnuksia, esimerkiksi `en, de`.

:::info Lisätietoa
Katso [Konfiguraatio](/docs/configuration/properties) -osio ymmärtääksesi, kuinka määrittää ominaisuuksia eri ympäristöille.
:::

## `t()`-menetelmä {#the-t-method}

Komponentit, jotka toteuttavat `HasTranslation` -interfacen, saavat pääsyn `t()`-menetelmään tekstin kääntämiseksi. Menetelmä ottaa käännöksen avaimen ja palauttaa lokalisoidun merkkijonon nykyiselle sovellusalueelle:

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // Yksinkertainen käännös
    String title = t("app.title");

    // Käännös MessageFormat-parametreilla
    String greeting = t("greeting", userName, messageCount);

    // Käännös tietyllä alueella
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

Voit myös käyttää `App.getTranslation()` suoraan missä tahansa ilman, että sinun tarvitsee toteuttaa rajapintaa:

```java
String title = App.getTranslation("app.title");
```

:::info Kaunis varaus
Jos käännöksen avainta ei löydy, `t()` palauttaa avaimen sellaisenaan sen sijaan, että heittäisi poikkeuksen. Tämä tarkoittaa, että sovelluksesi ei romahda, jos käännöstä ei ole. Avainta näytetään sellaisenaan, ja varoitus kirjataan, jotta voit seurata puuttuvia käännöksiä kehityksen aikana.
:::

## Käännettyjen komponenttien toteutus {#implementing-translated-components}

Käännetty komponentti yhdistää tyypillisesti `HasTranslation` ja [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface). Käytä `t()` UI-elementtien luomisessa aloittaaksesi käännetyn tekstin asettamisen. Tukeaksesi runtime-kielen vaihtamista, toteuta `LocaleObserver` ja päivitä sama teksti `onLocaleChange()`-metodissa.

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
Datavalinnan järjestelmä tukee käännettyjä validoivia ja muuntamissanomia käyttäen `Supplier<String>` -oliossa `t()`-menetelmää. Katso [dynaamiset validointiviestit](/docs/data-binding/validation/validators#dynamic-validation-messages), [dynaamiset muuntamissanomat](/docs/data-binding/transformation#dynamic-transformer-error-messages), ja [aluekoskeva Jakarta-validointi](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::

## Mukautetut käännösten ratkaisut {#custom-translation-resolvers}

Oletusratkaisija lataa käännöksiä Java `ResourceBundle` -ominaisuusfileistä. Jos haluat ladata käännöksiä muista lähteistä, kuten tietokannasta tai etäpalvelusta, toteuta `TranslationResolver`:

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

Yksinkertaisessa webforJ-sovelluksessa, määritä ratkaisija ennen sovelluksen käynnistämistä, esimerkiksi käyttämällä [sovelluselinkaarikuuntujaa](/docs/advanced/lifecycle-listeners):

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

Spring Boot -sovelluksessa, tuo ratkaisija beanina:

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
Kun ei ole määritelty mukautettua `TranslationResolver`-beania, Springin automaattinen konfigurointi tarjoaa oletus `BundleTranslationResolver`-ratkaisijan, joka on määritetty tuettujen alueiden mukaisesti `application.properties`-tiedostosta.
:::
