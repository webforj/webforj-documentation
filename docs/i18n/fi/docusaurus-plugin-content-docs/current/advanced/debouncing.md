---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: be654f5efb68050d8632a27166954583
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debonssointi on tekniikka, joka viivästyttää toiminnan suorittamista, kunnes tietty aika on kulunut viimeisestä kutsusta. Jokainen uusi kutsu nollaa ajastimen. Tämä on hyödyllistä skenaarioissa, kuten kirjoittamalla hakua, jossa haluat odottaa, kunnes käyttäjä lopettaa kirjoittamisen ennen kuin suoritat hakukyselyn.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerDemoView.java'
height='265px'
/>

## Peruskäyttö {#basic-usage}

`Debouncer`-luokka tarjoaa yksinkertaisen tavan debonsoida toimintoja. Luo `Debouncer` viiveellä sekunneissa ja kutsu sitten `run()` -menetelmää sen toiminnan kanssa, jonka haluat debonsoida:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

Tässä esimerkissä `search()`-metodia kutsutaan vain sen jälkeen, kun käyttäjä on lopettanut kirjoittamisen 300 millisekunnin ajan. Jokainen näppäinkosketus nollaa ajastimen `onModify`-tapahtuman kautta, joten nopea kirjoittaminen ei laukaise useita hakuja.

## Miten se toimii {#how-it-works}

Kun kutsut `run()`-menetelmää toiminnalla:

1. Jos mitään toimintoa ei ole odottamassa, `Debouncer` aikatauluttaa toiminnan suoritettavaksi viiveen jälkeen
2. Jos toiminta on jo odottamassa, edellinen toiminta peruutetaan ja ajastin käynnistetään uudelleen uudella toiminnalla
3. Kun viive on kulunut ilman toista kutsua, toiminta suoritetaan

`Debouncer` toimii UI-säikeessä käyttäen webforJ:n [`Interval`](/docs/advanced/interval) mekanismia, joten sinun ei tarvitse kääriä UI-päivityksiä `Environment.runLater()`-menetelmään.

:::tip Viiveyksiköt
Viiveparametri käyttää sekunteja yksikkönä, ei millisekunteja. Käytä `0.3f` 300 ms:lle tai `1.5f` 1.5 sekunnille.
:::

## Suorittamisen hallinta {#controlling-execution}

Seuraavia menetelmiä voidaan käyttää tarkempaan toiminnan hallintaan ja `Debouncer`-käyttöön:

### Odottavan toiminnan peruuttaminen {#cancelling-a-pending-action}

Käytä `cancel()`-menetelmää lopettaaksesi odottavan toiminnan suorittamisen:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Käyttäjä siirtyy muualle ennen tallennuksen suorittamista
debounce.cancel();
```

:::tip Odottavien debonsointien peruuttaminen
Kuten aikavälit, on hyvä käytäntö peruuttaa odottavat debonsoidut toiminnot, kun komponentti tuhotaan. Tämä estää muistivuodot ja vältää virheitä toiminnan suorittamisessa tuhotuissa komponenteissa:

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

### Pakottaminen välittömään suorittamiseen {#forcing-immediate-execution}

Käytä `flush()`-menetelmää suorittaaksesi odottavan toiminnan heti:

```java
Debouncer debounce = new Debouncer(0.5f);

textField.onModify(e -> {
  debounce.run(() -> validateInput(textField.getText()));
});

// Pakota validoiminen ennen lomakkeen lähettämistä
submitButton.onClick(e -> {
  debounce.flush();
  if (isValid()) {
    submitForm();
  }
});
```

### Odottavan tilan tarkistaminen {#checking-pending-status}

Käytä `isPending()`-menetelmää tarkistaaksesi, onko toiminta odottamassa suorittamista:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Käsitellään...");
}
```

## Tapahtumatason debonsointi vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ tarjoaa kaksi lähestymistapaa debonsointiin:

| Ominaisuus | `Debouncer` | `ElementEventOptions.setDebounce()` |
|------------|-------------|-------------------------------------|
| Laajuus    | Mikä tahansa toiminta | Elementtien tapahtumat vain |
| Sijainti   | Palvelinpuoli | Asiakaspuoli |
| Yksikkö    | Sekunnit (float) | Millisekunnit (int) |
| Joustavuus | Täysi hallinta peruutus/flush | Automaattinen tapahtuman kanssa |

Käytä `Debouncer`-luokkaa, kun tarvitset ohjelmallista hallintaa debonsoinnista, kuten odottavien toimien peruuttamista tai tyhjentämistä. Käytä `ElementEventOptions`-luokkaa, kun haluat yksinkertaista asiakaspuolen debonsointia elementtien tapahtumille ilman lisäpalvelinpyyntöjä.

```java
// Käyttäen ElementEventOptions asiakaspuolen debonsointiin
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Tämä käsittelijä on debonsoitu asiakkaan puolella
}, options);
```
