---
sidebar_position: 11
title: Localization
sidebar_class_name: new-content
_i18n_hash: 91f5af285113e5e76d50a201a2fbf88f
---
# Lokalisaatio <DocChip chip='since' label='25.10' />

Komponentit, jotka toteuttavat `LocaleObserver`-rajapinnan, saavat automaattisia ilmoituksia, kun paikallisuus muuttuu. Tämä mahdollistaa käyttöliittymäelementtien päivityksen tekstinsä, muotoilunsa ja muiden paikalliseen sisältöön liittyvien asioiden osalta ilman manuaalista koordinointia.

## `LocaleObserver`-rajapinta {#the-localeobserver-interface}

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver extends Serializable {
    void onLocaleChange(LocaleEvent event);
}
```

Kun komponentti toteuttaa tämän rajapinnan, webforJ rekisteröi automaattisesti:
- Komponentin luotaessa niin, että se saa paikallisuuden muutostapahtumat
- Poistaa komponentin rekisteristä tuhottaessa sen
- Kutsuu `onLocaleChange()`, kun paikallisuus muuttuu

Tämä rekisteröinti tapahtuu komponentin elinkaaren aikana.

## Käännösten käsittely {#handling-translations}

Kun `onLocaleChange()` kutsutaan, komponentit saavat uuden paikallisuuden. Miten ne lataa ja soveltaa käännöksiä, on kehittäjän päätettävissä. Yleisiä lähestymistapoja ovat:

- Java `ResourceBundle` ominaisuustiedostoilla
- Tietokantakyselyt käännöksille
- Mukautetut käännöspalveluntarjoajat
- Kovan koodin kartat yksinkertaisille tapauksille

Tässä esimerkissä käytetään `ResourceBundle`-luokkaa, joka tallentaa käännökset ominaisuustiedostoihin:

```
messages.properties        # Varaudu/yleinen
messages_en.properties     # Englanti
messages_de.properties     # Saksa
```

Ominaisuustiedostot sisältävät avain-arvo pareja:

```properties title="messages_en.properties"
app.title=Mailbox
menu.inbox=Inbox
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
```

## Paikallisuuden muuttaminen {#changing-the-locale}

Käytä `App.setLocale()` muuttamaan sovelluksen paikallisuus. Tämä laukaisee ilmoitukset kaikille rekisteröidyille havainnoijille:

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

Tyypillinen toteutus voisi käyttää avattavaa valikkoa tai valintakomponenttia:

```java
ChoiceBox languageSelector = new ChoiceBox();
languageSelector.add("en", "English");
languageSelector.add("de", "Deutsch");
languageSelector.add("fr", "Français");

languageSelector.onSelect(e -> {
  String lang = (String) e.getSelectedItem().getKey();
  Locale newLocale = Locale.forLanguageTag(lang);

  App.setLocale(newLocale);
});
```

Kun käyttäjä valitsee kielen, `App.setLocale()` laukaisee tapahtuman, ja kaikki komponentit, jotka toteuttavat `LocaleObserver`, saavat päivityksen.

## Havainnoijien toteuttaminen {#implementing-observers}

Kun komponentti toteuttaa `LocaleObserver`, sen on käsiteltävä kahta skenaariota: alkuperäinen renderöinti nykyisellä paikallisuudella ja päivitykset, kun paikallisuus muuttuu. Seuraava esimerkki havainnollistaa tätä kaavaa komponentilla, joka näyttää lokalisointitekstiä ja linkkejä.

Komponentti tallentaa viittauksia elementteihin, jotka tarvitsevat käännöspäivityksiä. Rakennettaessa se lataa nykyisen paikallisuuden käännökset. Kun paikallisuus muuttuu, `onLocaleChange()` kutsutaan, jolloin komponentti voi ladata käännökset uudelleen ja päivittää näyttämänsä tekstin.

```java title="TranslationService.java"
import com.webforj.App;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
  private final MessageSource messageSource;

  public TranslationService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String get(String key) {
    return messageSource.getMessage(key, null, App.getLocale());
  }
}
```

```java title="Explore.java"
public class Explore extends Composite<FlexLayout> implements LocaleObserver {
  private final TranslationService i18n;
  private FlexLayout self = getBoundComponent();
  private H3 titleElement;
  private Anchor anchor;
  private String titleKey;

  public Explore(TranslationService i18n, String titleKey) {
    this.i18n = i18n;
    this.titleKey = titleKey;

    self.addClassName("explore-component");
    self.setStyle("margin", "1em auto");
    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.setMaxWidth(300);
    self.setSpacing(".3em");

    Img img = new Img(String.format("ws://explore/%s.svg", titleKey), "mailbox");
    img.setMaxWidth(250);

    String translatedTitle = i18n.get("menu." + titleKey.toLowerCase());
    titleElement = new H3(translatedTitle);

    anchor = new Anchor("https://docs.webforj.com/docs/components/overview", i18n.get("explore.link"));
    anchor.setTarget("_blank");

    self.add(img, titleElement, anchor);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    titleElement.setText(i18n.get("menu." + titleKey.toLowerCase()));
    anchor.setText(i18n.get("explore.link"));
  }
}
```

Komponentti tallentaa viittauksia elementteihin, jotka näyttävät käännettyä sisältöä (`titleElement` ja `anchor`). Käännökset ladataan konstruktorissa nykyisen paikallisuuden avulla. Kun paikallisuus muuttuu, `onLocaleChange()` päivittää vain sen tekstin, joka tarvitsee käännöksen.

## Elinkaaren hallinta {#lifecycle-management}

Kehys käsittelee havainnoijien rekisteröinnin automaattisesti komponentin elinkaaren koukkujen kautta:

- **Luotaessa**: Komponentit, jotka toteuttavat `LocaleObserver`, rekisteröidään `LocaleObserverRegistry`-tietueeseen
- **Tuhoamisen yhteydessä**: Komponentit poistetaan rekisteristä muistivuotojen estämiseksi

Jokainen sovellusinstanssi ylläpitää omaa havainnoijarekisteriään. Tämä automaattinen hallinta tarkoittaa:

- Ei manuaalisia rekisteröinti-/poistokutsuja
- Ei muistivuotoja tuhotuista komponenteista
- Lankaturvalliset samanaikaiset ilmoitukset

:::info Per-sovellusrekisteri
Jokainen sovellusinstanssi ylläpitää omaa havainnoijarekisteriään. Yhden sovelluksen rekisteröidyt havainnoijat eivät saa ilmoituksia muilta sovelluksilta, jotka toimivat samassa JVM:ssä.
:::

## `LocaleEvent` {#localeevent}

`LocaleEvent`, joka siirretään `onLocaleChange()`-metodiin, tarjoilee:

| Metodi | Palauttaa | Kuvaus |
|--------|---------|-------------|
| `getLocale()` | `Locale` | Uuden paikallisuuden, joka asetettiin |
| `getSource()` | `Object` | Komponentin, joka vastaanotti tapahtuman |

```java
@Override
public void onLocaleChange(LocaleEvent event) {
  Locale newLocale = event.getLocale();
  Object source = event.getSource();

  // Päivitä komponentti uudella paikallisuudella
  ResourceBundle bundle = ResourceBundle.getBundle("messages", newLocale);
  updateUI(bundle);
}
```
