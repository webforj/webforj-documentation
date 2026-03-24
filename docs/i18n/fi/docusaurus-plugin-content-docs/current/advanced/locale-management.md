---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: d3dcb4b1ded50923232cb33225364239
---
# Kieli hallinta <DocChip chip='since' label='25.10' />

webforJ tarjoaa sisäänrakennettua tukea sovelluksen kielen hallintaan. Kieli määrittää, mitä kieltä ja alueellista muotoilua käytetään koko sovelluksessa. Komponentit voivat reagoida kielimuutoksiin `LocaleObserver`-rajapinnan kautta, joka mahdollistaa käyttöliittymän päivityksen heti, kun käyttäjä vaihtaa kieltä.

## Oletuskielen asettaminen {#setting-the-default-locale}

Sovelluksen kieltä voidaan konfiguroida `webforj.locale`-ominaisuudella. Tämä määrittää kielen, jota sovellus käyttää käynnistyksessä, vaikuttaen kaikkiin kielellisesti herkkään muotoiluun ja tekstiin. Kun `webforj.locale` ei ole määritetty, sovellus palautuu palvelimen JVM:n oletuskieleen. Voit lukea nykyisen kielen milloin tahansa käyttämällä `App.getLocale()`.

Katso [Configuration](/docs/configuration/properties) -osiota oppiaksesi, miten määrittää ominaisuuksia eri ympäristöille.

## Kielen muuttaminen {#changing-the-locale}

Muuta kieltä ajonaikaisesti kutsumalla `App.setLocale()`. Tämä päivittää kielen koko sovelluksessa ja ilmoittaa kaikille komponenteille, jotka toteuttavat `LocaleObserver`, mahdollistaen käyttöliittymän päivityksen ilman sivun uudelleenlatausta.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Selaimen kielen tunnistus <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Kun automaattinen tunnistus on käytössä, webforJ lukee selaimen suosimat kielet käynnistyksessä ja asettaa sovelluksen kielen parhaaksi vastaavaksi määritellyistä tuetuista kielistä. Jos vastaavuuksia ei löydy, käytetään ensimmäistä tukemista kieltä oletuksena.

Ota automaattinen tunnistus käyttöön asettamalla `webforj.i18n.auto-detect` arvoon `true` ja määrittämällä `webforj.i18n.supported-locales` sovelluksesi tukemille kielille. Katso [Configuration](/docs/configuration/properties) -osiota oppiaksesi, miten määrittää ominaisuuksia eri ympäristöille.

:::info Vaatimukset tuetuille kielille
Automaattinen tunnistus vaatii `supported-locales` -määrittelyn. Jos luettelo on tyhjällä, automaattinen tunnistus ei vaikuta ja sovellus käyttää `webforj.locale`:n oletuskieltä.
:::

## `LocaleObserver`-rajapinta {#the-localeobserver-interface}

Komponenttien, jotka tarvitsevat sisällön päivittämistä kielen muuttuessa, tulisi toteuttaa `LocaleObserver`-rajapinta. webforJ rekisteröi ja poistaa automaattisesti rekisteröitymiset, kun komponentteja luodaan ja tuhotaan.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
  void onLocaleChange(LocaleEvent event);
}
```

Kun kieli muuttuu, `onLocaleChange` kutsutaan uudella kielellä. Tässä metodissa päivitä kaikki kielellisesti herkät tekstit tai muotoilut:

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

:::tip Sisäänrakennettu käännöstuki
Version 25.12 alkaen webforJ tarjoaa sisäänrakennetun [käännösjärjestelmän](/docs/advanced/i18n-localization), joka tukee resurssipaketteja, mukautettuja ratkaisuja, automaattista selaimen kielen tunnistusta ja kielitietoista tietosidontaa.
:::

### `LocaleEvent` {#localeevent}

`LocaleEvent`, joka välitetään `onLocaleChange()`-metodiin, tarjoaa uuden kielen ja komponentin, joka sai tapahtuman:

| Metodi | Palauttaa | Kuvaus |
|--------|-----------|--------|
| `getLocale()` | `Locale` | Uusi kieli, joka on asetettu |
| `getSource()` | `Object` | Komponentti, joka sai tapahtuman |

## Manuaaliset kielipäivitykset {#manual-locale-updates}

Kaikki komponentit eivät reagoi kielen muutoksiin automaattisesti. Jotkin komponentit, kuten [Masked Fields](/docs/components/fields/masked/overview), lukevat `App.getLocale()` kerran luontivaiheessa konfiguroidakseen kielellisesti herkkää muotoilua, mutta eivät toteuta `LocaleObserver`-rajapintaa. Kun kieli muuttuu ajonaikaisesti, nämä on päivitettävä nimenomaan `onLocaleChange()`-käsittelijässäsi:

```java
public class OrderForm extends Composite<FlexLayout> implements LocaleObserver {
  private MaskedDateField dateField = new MaskedDateField("Date");
  private MaskedTimeField timeField = new MaskedTimeField("Time");

  @Override
  public void onLocaleChange(LocaleEvent event) {
    Locale newLocale = event.getLocale();
    dateField.setLocale(newLocale);
    timeField.setLocale(newLocale);
  }
}
```

:::tip Tietosidonta
`BindingContext` tukee kielellisesti tietoisia validointi- ja muuntoviestejä. Katso [dynaamiset validointiviestit](/docs/data-binding/validation/validators#dynamic-validation-messages) ja [kielellisesti tietoinen Jakarta Validation](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
