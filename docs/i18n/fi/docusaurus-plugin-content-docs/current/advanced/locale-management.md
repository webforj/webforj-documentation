---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: cfcad51aaedd77b781334fc048b0a4f1
---
# Paikallinen hallinta <DocChip chip='since' label='25.10' />

webforJ tarjoaa sisäänrakennetun tuen sovelluksen alueen hallitsemiseen. Alue määrittää, mitä kieltä ja alueellista muotoilua käytetään koko sovelluksessa. Komponentit voivat reagoida alueen muutoksiin `LocaleObserver`-rajapinnan kautta, mikä sallii UI:n päivittämisen heti, kun käyttäjä vaihtaa kieliä.

## Oletusalueen asettaminen {#setting-the-default-locale}

Sovelluksen aluetta voidaan konfiguroida käyttämällä ominaisuutta `webforj.locale`. Tämä asettaa alueen, jota sovellus käyttää käynnistyksestä lähtien, vaikuttaen kaikkiin alueherkkiin muotoiluihin ja teksteihin. Kun `webforj.locale` ei ole määritelty, sovellus palautuu palvelimen JVM: n oletusalueeseen. Voit lukea nykyisen alueen milloin tahansa käyttämällä `App.getLocale()`.

Katso [Konfigurointi](/docs/configuration/properties) -osio saadaksesi lisätietoja ominaisuuksien asettamisesta eri ympäristöille.

## Alueen muuttaminen {#changing-the-locale}

Muuttaaaksesi aluetta ajon aikana, kutsu `App.setLocale()`. Tämä päivittää alueen koko sovelluksessa ja ilmoittaa kaikille komponenteille, jotka toteuttavat `LocaleObserver`-rajapinnan, jolloin UI voi päivittää ilman sivun uudelleenlatausta.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Selainalueen tunnistus <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Kun automaattitunnistus on käytössä, webforJ lukee selaimen suosimat kielet käynnistyksen aikana ja asettaa sovelluksen alueen parhaimpaan vastaavuuteen konfiguroiduista tuetuista alueista. Jos vastaavuutta ei löydy, ensimmäinen tuettu alue käytetään oletuksena.

Ota automaattitunnistus käyttöön asettamalla `webforj.i18n.auto-detect` arvoon `true` ja määrittelemällä `webforj.i18n.supported-locales` alueilla, joita sovelluksesi tukee. Katso [Konfigurointi](/docs/configuration/properties) -osio saadaksesi lisätietoja ominaisuuksien asettamisesta eri ympäristöille.

:::info Edellyttää tuettuja alueita
Automaattitunnistus edellyttää `supported-locales`-asetusta. Jos lista on tyhjää, automaattitunnistuksella ei ole vaikutusta ja sovellus käyttää oletusaluetta `webforj.locale`-määritelmästä.
:::

## `LocaleObserver`-rajapinta {#the-localeobserver-interface}

Komponenttien, jotka tarvitsevat sisällön päivittämistä alueen muuttuessa, tulisi toteuttaa `LocaleObserver`-rajapinta. webforJ rekisteröi ja poistaa automaattisesti havaitsijat, kun komponentteja luodaan ja tuhotaan.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Kun alue muuttuu, `onLocaleChange` kutsutaan uuden alueen kanssa. Tämän metodin sisällä päivitä kaikki alueherkät tekstit tai muotoilut:

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
Alkaen versiosta 25.12, webforJ tarjoaa sisäänrakennetun [käännösjärjestelmän](/docs/advanced/i18n-localization), joka tukee resurssikokoelmia, mukautettuja ratkaisuja, automaattista selaimen alueen tunnistusta ja alueherkkää tietosidontaa.
:::

### `LocaleEvent` {#localeevent}

`LocaleEvent`, joka siirretään `onLocaleChange()`-metodille, antaa uuden alueen ja komponentin, joka sai tapahtuman:

| Metodi | Palauttaa | Kuuvaus |
|--------|-----------|---------|
| `getLocale()` | `Locale` | Uusi asetettu alue |
| `getSource()` | `Object` | Komponentti, joka sai tapahtuman |

## Manuaaliset aluepäivitykset {#manual-locale-updates}

Kaikki komponentit eivät reagoi automaattisesti alueen muutoksiin. Jotkut komponentit, kuten [Masked Fields](/docs/components/fields/masked/overview), lukevat `App.getLocale()` vain kerran luomisen aikana määrittääkseen alueherkän muotoilun, mutta eivät toteuta `LocaleObserver`-rajapintaa. Kun alue muuttuu ajon aikana, nämä on päivitettävä erikseen `onLocaleChange()`-käsittelijässäsi:

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
`BindingContext` tukee alueherkkiä validoinnin ja muunnoksen viestejä. Katso [dynaamiset validointiviestit](/docs/data-binding/validation/validators#dynamic-validation-messages) ja [alueherkkä Jakarta-validointi](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
