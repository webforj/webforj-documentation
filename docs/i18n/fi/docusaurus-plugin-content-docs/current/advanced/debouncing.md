---
sidebar_position: 21
title: Debouncing
slug: debouncing
_i18n_hash: 2096c774627674739fd237aed9a4f79e
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debounceaminen on tekniikka, joka viivästyttää toiminnan suorittamista, kunnes tietty aika on kulunut viimeisestä kutsusta. Jokainen uusi kutsu nollaa ajastimen. Tämä on hyödyllistä skenaarioissa, kuten kirjoittaessasi hakua, jossa haluat odottaa, että käyttäjä lopettaa kirjoittamisen ennen hakukyselyn suorittamista.

<ComponentDemo
path='/webforj/debouncer'
files={['src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java']}
height='265px'
/>

## Peruskäyttö {#basic-usage}

`Debouncer`-luokka tarjoaa yksinkertaisen tavan debounce-toimintoja. Luo `Debouncer`, jolla on viive sekunteina, ja kutsu sitten `run()` haluamasi debounce-toiminnon kanssa:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

Tässä esimerkissä `search()`-metodia kutsutaan vain sen jälkeen, kun käyttäjä on lopettanut kirjoittamisen 300 millisekunnin ajan. Jokainen näppäinpainallus nollaa ajastimen `onModify`-tapahtuman kautta, joten nopea kirjoittaminen ei käynnistä useita hakuja.

## Kuinka se toimii {#how-it-works}

Kun kutsut `run()` toiminnoilla:

1. Jos ei ole odottavaa toimintoa, `Debouncer` aikatauluttaa toiminnan suoritettavaksi viiveen jälkeen
2. Jos toiminto on jo odottavana, edellinen toiminto peruutetaan ja ajastin käynnistyy uudelleen uuden toiminnon kanssa
3. Kun viive on kulunut ilman uutta kutsua, toiminto suoritetaan

`Debouncer` toimii käyttöliittymän säikeessä käyttäen webforJ:n [`Interval`](/docs/advanced/interval) mekanismia, joten sinun ei tarvitse kääriä käyttöliittymän päivityksiä `Environment.runLater()`-muotoon.

:::tip Viiveyksiköt
Viiveparametri käyttää sekunteja yksikkönä, ei millisekunteja. Käytä `0.3f` 300 ms:lle tai `1.5f` 1,5 sekunnille.
:::

## Suorittamisen hallinta {#controlling-execution}

Seuraavia metodeja voidaan käyttää tarkempaan suorituksen käsittelyyn ja `Debouncerin` käyttöön:

### Odottavan toiminnan peruuttaminen {#cancelling-a-pending-action}

Käytä `cancel()` pysäyttääksesi odottavan toiminnan suorittamisen:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Käyttäjä siirtyy pois ennen tallennuksen suorittamista
debounce.cancel();
```

:::tip Odottavien debouncejen peruuttaminen
Kuten aikaväleissä, on hyvä käytäntö peruuttaa odottavat debounce-toiminnat, kun komponentti tuhotaan. Tämä estää muistivuodot ja virheet tuhoutuneilla komponenteilla suoritettavissa toiminnoissa:

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

### Pakotettu välitön suorittaminen {#forcing-immediate-execution}

Käytä `flush()` suorittaaksesi odottavan toiminnan heti:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Pakota validointi ennen lomakkeen lähettämistä
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Odottavan tilan tarkistaminen {#checking-pending-status}

Käytä `isPending()` varmistaaksesi, onko toiminta odottamassa suoritusta:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Käsitellään...");
}
```

## Tapahtumatason debounce vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ tarjoaa kaksi lähestymistapaa debounceamiseen:

| Ominaisuus | `Debouncer` | `ElementEventOptions.setDebounce()` |
|------------|-------------|-------------------------------------|
| Laajuus    | Mikä tahansa toiminto | Elementtien tapahtumat vain |
| Sijainti   | Palvelinpuoleinen | Asiakaspuoleinen |
| Yksikkö    | Sekunnit (float) | Millisekunnit (int) |
| Joustavuus | Täydellinen hallinta peruutus/pakota | Automaattinen tapahtuman kanssa |

Käytä `Debouncer`-luokkaa, kun tarvitset ohjelmallista hallintaa debounceamisessa, kuten odottavien toimintojen peruuttamista tai pakottamista. Käytä `ElementEventOptions`-luokkaa, kun haluat yksinkertaista asiakaspuoleista debounceamista elementtien tapahtumille ilman ylimääräisiä palvelinkierroksia.

```java
// Käyttämällä ElementEventOptions-objektia asiakaspuoleisessa debounceamisessa
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Tämä käsittelijä on debounce asiakaspuolella
}, options);
```
