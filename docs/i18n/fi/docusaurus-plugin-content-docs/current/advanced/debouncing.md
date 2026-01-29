---
sidebar_position: 21
title: Debouncing
slug: debouncing
sidebar_class_name: new-content
_i18n_hash: 89cdcc39e4954963d7e19cb0e5665ca4
---
<DocChip chip='since' label='25.11' />
<JavadocLink type="foundation" location="com/webforj/Debouncer" top='true'/>

Debonssi on tekniikka, joka viivästyttää toiminnon suorittamista, kunnes määritetty aika viimeisestä kutsusta on kulunut. Jokainen uusi kutsu nollaa ajastimen. Tämä on hyödyllistä skenaarioissa, kuten etsiessä kirjoittaessa, jossa haluat odottaa, kunnes käyttäjä lopettaa kirjoittamisen, ennen kuin suoritat hakukyselyn.

<ComponentDemo
path='/webforj/debouncer?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/debouncer/DebouncerView.java'
height='265px'
/>

## Peruskäyttö {#basic-usage}

`Debouncer`-luokka tarjoaa yksinkertaisen tavan debonsoida toimintoja. Luo `Debouncer` viiveellä sekunneissa, ja kutsu sitten `run()` toimintoa varten, jota haluat debonsoida:

```java
Debouncer debounce = new Debouncer(0.3f);

textField.onModify(e -> {
  debounce.run(() -> search(textField.getText()));
});
```

Tässä esimerkissä `search()`-metodia kutsutaan vain, kun käyttäjä lopettaa kirjoittamisen 300 millisekunnin ajaksi. Jokainen näppäinpainallus nollaa ajastimen `onModify`-tapahtuman kautta, joten nopea kirjoittaminen ei laukaise useita hakuja.

## Kuinka se toimii {#how-it-works}

Kun kutsut `run()` toimintoa:

1. Jos toimintoa ei ole odottamassa, `Debouncer` aikatauluttaa toiminnon suoritettavaksi viiveen jälkeen
2. Jos toiminto on jo odottamassa, aikaisempi toiminto peruutetaan ja ajastin alkaa uudelleen uuden toiminnon kanssa
3. Kun viive on kulunut ilman uutta kutsua, toiminto suoritetaan

`Debouncer` toimii UI-säikeessä käyttäen webforJ:n [`Interval`](/docs/advanced/interval) mekanismia, joten sinun ei tarvitse kääriä UI-päivityksiä `Environment.runLater()`-kutsun ympärille.

:::tip Viiveen yksiköt
Viiveparametri käyttää sekunteja yksikkönä, ei millisekunteja. Käytä `0.3f` 300 ms:lle tai `1.5f` 1.5 sekunnille.
:::

## Suorittamisen hallinta {#controlling-execution}

Seuraavia metodeja voidaan käyttää tarkemmin hallitsemaan `Debouncer`in suorittamista ja käyttöä:

### Odottavan toiminnon peruuttaminen {#cancelling-a-pending-action}

Käytä `cancel()`-metodia estääksesi odottavan toiminnon suorittamisen:

```java
Debouncer debounce = new Debouncer(1f);

debounce.run(() -> saveDocument());

// Käyttäjä navigoi pois ennen kuin tallennus suoritetaan
debounce.cancel();
```

:::tip Odottavien debonsointien peruuttaminen
Kuten intervallit, on hyvä käytäntö peruuttaa odottavat debonsoidut toiminnot, kun komponentti tuhotaan. Tämä estää muistivuotoja ja virheitä tuotehdolla olevan komponentin suorittamiselta:

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

Käytä `flush()`-metodia suorittaaksesi odottava toiminto välittömästi:

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

Käytä `isPending()`-metodia tarkistaaksesi, onko toiminto odottamassa suorittamista:

```java
Debouncer debounce = new Debouncer(0.3f);

if (debounce.isPending()) {
  statusLabel.setText("Käsittely...");
}
```

## Tapahtumatason debonsointi vs `Debouncer` {#event-level-debouncing-vs-debouncer}

webforJ tarjoaa kaksi lähestymistapaa debonsoimiseen:

| Ominaisuus | `Debouncer` | `ElementEventOptions.setDebounce()` |
|------------|-------------|-------------------------------------|
| Laajuus    | Mikä tahansa toiminto | Vain elementtitapahtumat |
| Sijainti   | Palvelinpuoli | Asiakaspäässä |
| Yksikkö    | Sekunteja (float) | Millisekunteja (int) |
| Joustavuus | Täydellinen hallinta peruuttamiseen/tyhjennykseen | Automaattinen tapahtumalla |

Käytä `Debouncer`ia, kun tarvitset ohjelmallista hallintaa debonsoimiseen, kuten odottavien toimintojen peruuttamiseen tai tyhjentämiseen. Käytä `ElementEventOptions`-asetusta, kun haluat yksinkertaista asiakaspään debonsointia elementtitapahtumille ilman lisäpalvelinpyyntöjä.

```java
// Käyttäen ElementEventOptions asiakaspään debonsoimiseen
ElementEventOptions options = new ElementEventOptions();
options.setDebounce(300);

element.addEventListener("input", e -> {
  // Tämä käsittelijä debonsoidaan asiakkaalla
}, options);
```
