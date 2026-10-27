---
sidebar_position: 21
title: Debouncing
slug: debouncing
description: >-
  Delay actions until activity settles using the Debouncer class for
  search-as-you-type, autosave, and other rate-limited UI work.
_i18n_hash: fd81dccbd2aeb6e50922c2d09de536de
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debouncing on tekniikka, joka viivyttää toiminnon suorittamista, kunnes määritetty aika on kulunut viimeisestä kutsusta. Jokainen uusi kutsu nollaa ajastimen. Tämä on hyödyllistä tilanteissa, kuten hakeminen kirjoittaessa, joissa haluat odottaa, kunnes käyttäjä lopettaa kirjoittamisen ennen haun suorittamista.

<!-- INTRO_END -->

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

Luo `Debouncer` viiveellä sekunneissa, ja kutsu sitten `run()` haluamallasi toiminnolla:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

Tässä esimerkissä `search()`-metodia kutsutaan vain, kun käyttäjä on lopettanut kirjoittamisen 300 millisekunnin ajan. Jokainen näppäinpainallus nollaa ajastimen `onModify`-tapahtuman avulla, joten nopea kirjoittaminen ei laukaise useita hakuja.

## Miten se toimii {#how-it-works}

Kun kutsut `run()` toimintoa:

1. Jos ei ole odottavaa toimintoa, `Debouncer` aikatauluttaa toiminnon suoritettavaksi viiveen jälkeen
2. Jos toiminto on jo odottamassa, aikaisempi toiminto peruutetaan ja ajastin käynnistyy uudelleen uudella toiminnolla
3. Kun viive on kulunut ilman uutta kutsua, toiminto suoritetaan

`Debouncer` käyttää käyttöliittymän säiettä hyödyntäen webforJ:n [`Interval`](/docs/advanced/interval) mekanismia, joten sinun ei tarvitse kääriä käyttöliittymän päivityksiä `Environment.runLater()`-kutsun ympärille.

:::tip Viiveen yksiköt
Viiveparametri käyttää sekunteja yksikkönä, ei millisekunteja. Käytä `0.3f` 300 ms:lle tai `1.5f` 1,5 sekunnille.
:::

## Suorittamisen hallinta {#controlling-execution}

Seuraavia metodeja voidaan käyttää tarkempaan hallintaan `Debouncer`in käyttöön:

### Odottavan toiminnon peruuttaminen {#cancelling-a-pending-action}

Käytä `cancel()` estääksesi odottavan toiminnon suorittamisen:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Käyttäjä navigoi pois ennen kuin tallenus suoritetaan
debounce.cancel();
```

:::tip Odottavien viivästysten peruuttaminen
Kuten aikaväleissä, on hyvä käytäntö peruuttaa odottavat viivästyneet toiminnot, kun komponenttia tuhotaan. Tämä estää muistivuotoja ja virheitä, jotka johtuvat toimintojen suorittamisesta tuhoittuissa komponenteissa:

```java
public class SearchPanel extends Composite<Div> {
  private final Debouncer debounce = new Debouncer(0.3f);

  @Override
  protected void onDidDestroy() {
    debounce.cancel();
  }
}
```
:::

### Välitön suorittaminen {#forcing-immediate-execution}

Käytä `flush()` suorittaaksesi odottavan toiminnon heti:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Pakota validoimaan ennen lomakkeen lähettämistä
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Odottavan tilan tarkistaminen {#checking-pending-status}

Käytä `isPending()` tarkistaaksesi, onko toiminto odottamassa suorittamista:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Käsitellään...");
}
```

## Tapahtumatason viivästys vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ tarjoaa kaksi lähestymistapaa viivästykseen:

| Ominaisuus | `Debouncer` | `ElementEventOptions.setDebounce()` |
|------------|-------------|--------------------------------------|
| Laajuus    | Mikä tahansa toiminto | Vain elementtitapahtumat |
| Sijainti   | Palvelinpuolella | Asiakaspuolella |
| Yksikkö    | Sekunnit (float) | Millisekunnit (int) |
| Joustavuus | Täysi hallinta peruutuksen/flushin avulla | Automaattinen tapahtumalla |

Käytä `Debouncer`-toimintoa, kun tarvitset ohjelmallista hallintaa viivästykseen, kuten odottavien toimintojen peruuttamista tai tyhjentämistä. Käytä `ElementEventOptions`-toimintoa, kun haluat yksinkertaista asiakaspuolen viivästystä elementtitapahtumille ilman lisäpalvelinkierroksia.

```java
// Käyttäen ElementEventOptionsia asiakaspuolen viivästykseen
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Tämä käsittelijä on viivästytetty asiakkaalla
}, options);
```
