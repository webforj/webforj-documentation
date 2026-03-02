---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: 066d555fcf006756c6ae0e542d409d77
---
# Paikallinen hallinta <DocChip chip='since' label='25.10' />

webforJ tarjoaa sisäänrakennetun tuen sovelluksen paikallisen käytön hallintaan. Paikallinen asetus määrittää, mikä kieli ja alueellinen muotoilu käytetään koko sovelluksessa. Komponentit voivat reagoida paikallisiin muutoksiin `LocaleObserver`-rajapinnan kautta, jolloin käyttöliittymä päivittyy heti, kun käyttäjä vaihtaa kieltä.

## Oletuspaikallisen asetuksen määrittäminen {#setting-the-default-locale}

Sovelluksen paikka voidaan määrittää käyttämällä `webforj.locale`-ominaisuutta. Tämä asettaa paikallisen asetuksen, jota sovellus käyttää käynnistyksessä, vaikuttaen kaikkiin paikallisiin muotoiluihin ja tekstiin. Kun `webforj.locale` ei ole määritetty, sovellus käyttää palvelimen JVM-osoitetta. Voit lukea nykyisen paikallisen asetuksen milloin tahansa kutsumalla `App.getLocale()`.

Katso [Configuration](/docs/configuration/properties) -osio oppiaksesi, kuinka määrittää ominaisuuksia eri ympäristöille.

## Paikallisen asetuksen muuttaminen {#changing-the-locale}

Muuta paikallista asetusta ajonaikaisesti kutsumalla `App.setLocale()`. Tämä päivittää paikallisen asetuksen koko sovelluksessa ja ilmoittaa kaikille komponenteille, jotka toteuttavat `LocaleObserver`, mahdollistaen käyttöliittymän päivittymisen ilman sivun uudelleenlatausta.

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## Selaimen paikallisen havaitseminen <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

Kun automaattinen havaitseminen on käytössä, webforJ lukee selaimen suosimat kielet käynnistyksen yhteydessä ja asettaa sovelluksen paikallisen asetuksen parhaiten vastaavasta määritetystä tuetusta paikasta. Jos vastaavuutta ei löydy, ensimmäistä tuettua paikallista käytetään oletuksena.

Ota automaattinen havaitseminen käyttöön asettamalla `webforj.i18n.auto-detect` arvoksi `true` ja määrittämällä `webforj.i18n.supported-locales` sovelluksesi tukemat paikalliset asetukset. Katso [Configuration](/docs/configuration/properties) -osio oppiaksesi, kuinka määrittää ominaisuuksia eri ympäristöille.

:::info Tuetut paikalliset asetukset vaaditaan
Automaattinen havaitseminen edellyttää, että `supported-locales` on määritetty. Jos luettelo on tyhjentynyt, automaattinen havaitseminen ei vaikuta ja sovellus käyttää oletuspaikallista asetusta `webforj.locale`-mukaisesti.
:::

## `LocaleObserver`-rajapinta {#the-localeobserver-interface}

Komponenttien, jotka tarvitsevat sisällön päivittämistä paikallisen muutoksen yhteydessä, tulee toteuttaa `LocaleObserver`-rajapinta. webforJ rekisteröi ja poistaa automaattisesti havaitsijat, kun komponentteja luodaan ja poistetaan.

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

Kun paikallinen asetus muuttuu, `onLocaleChange` kutsutaan uudella paikallisella asetuksella. Tässä menetelmässä päivitä kaikki paikallisesti herkkä teksti tai muotoilu:

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

:::tip Sisäänrakennettu käännöstuki
Versiosta 25.12 alkaen webforJ tarjoaa sisäänrakennetun [käännösjärjestelmän](/docs/advanced/i18n-localization), joka tukee resurssikokoelmia, mukautettuja ratkaisijoita, automaattista selaimen paikallisen havaitsemista ja paikallisesti herkkiä tietosidoksia.
:::

### `LocaleEvent` {#localeevent}

`LocaleEvent`, joka välitetään `onLocaleChange()`-menetelmälle, tarjoaa uuden paikallisen asetuksen ja komponentin, joka sai tapahtuman:

| Metodi | Palauttaa | Kuvaus |
|--------|---------|-------------|
| `getLocale()` | `Locale` | Asetettu uusi paikallinen asetus |
| `getSource()` | `Object` | Komponentti, joka sai tapahtuman |

## Manuaaliset paikalliset päivitykset {#manual-locale-updates}

Kaikki komponentit eivät reagoi paikallisiin muutoksiin automaattisesti. Jotkut komponentit, kuten [Masked Fields](/docs/components/fields/masked/overview), lukevat `App.getLocale()` kerran luomisen aikana määrittääkseen paikallisesti herkän muotoilun, mutta eivät toteuta `LocaleObserver`. Kun paikallinen asetus muuttuu ajonaikaisesti, nämä on päivitettävä erikseen `onLocaleChange()`-käsittelijässä:

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
`BindingContext` tukee paikallisesti herkkiä vahvistus- ja muunnosviestejä. Katso [dynaamiset vahvistusviestit](/docs/data-binding/validation/validators#dynamic-validation-messages) ja [paikallisesti herkät Jakarta- vahvistukset](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages).
:::
