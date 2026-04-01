---
title: Refresher
sidebar_position: 101
_i18n_hash: 763037d616f2274feb7a7ed24b9c91f0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh on yleinen malli mobiili- ja napinpainalluksille suunnatuissa käyttöliittymissä, ja `Refresher`-komponentti tuo sen vieritettävälle säiliöille webforJ:ssä. Kun käyttäjät pyyhkäisevät alaspäin määritellyn kynnyksen yli, se siirtyy visuaalisten tilojen läpi: `pull`, `release` ja `refreshing`, joista jokaisella on mukautettava ikoni ja paikallistettu teksti. Se toimii hyvin yhdessä [`InfiniteScroll`](../components/infinitescroll) kanssa, jotta sisällön lataaminen tai nollaaminen tapahtuu elepohjaisesti.

<!-- INTRO_END -->

## Instansointi ja kansainvälistäminen {#instantiation-and-internationalization}

Lisää `Refresher` instanssoimalla se ja rekisteröimällä päivityskuuntelija. Kun päivitystoimenpiteet on suoritettu, kutsu `finish()` nollataksesi komponentin lepotilaan.

:::info Kuinka aktivoida `Refresher`
Aktivoidaksesi `Refresher`, **napsauta ja vedä alaspäin** vieritettävällä alueen yläosasta. Vaikka tämä ele on tuttu mobiililaitteilla, se on harvinaisempi työpöydällä—varmista, että pidät ja vedät hiirellä.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Tätä lähestymistapaa käytetään usein sivutettujen luetteloiden päivittämiseen tai äärettömän vierityksen lataamisen aloittamiseen.

### Kansainvälistäminen {#internationalization}

Jokaisen tilan etiketti voidaan myös paikallistaa `RefresherI18n`-objektin avulla. Kolme tilaa ovat:

- Pull: Alkuperäisen eleen teksti (esim. "Vedä alaspäin päivittääksesi")
- Release: Kynnys saavutettu (esim. "Vapauta päivittääksesi")
- Refresh: Lataustila (esim. "Päivitetään")

Tämä mahdollistaa monikielisen tuen ja brändiä tarvittaessa säätämisen.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Ikonikustomointi {#icon-customization}

Voit vaihtaa [`Icons`](../components/icon) -kuvakkeita, joita käytetään `pull`/`release` ja `refreshing` -vaiheissa joko ennalta määritellyn [`Icon`](../components/icon) avulla tai [Ikoni-URL:n](../managing-resources/assets-protocols) avulla. Nämä ovat käyttökelpoisia, kun haluat soveltaa brändäystä tai mukautettua animaatiota.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Vedon käyttäytymisen konfigurointi {#pull-behavior-configuration}

### Kynnys {#threshold}

Aseta, kuinka pitkälle käyttäjän on vedettävä alaspäin (pikseleinä) ennen päivityksen laukaamista:

```java
refresher.setThreshold(80); // oletus: 80px
```

### Kynnysmaksimi {#threshold-maximum}

Määritä sallitun maksimivedon etäisyys käyttämällä `setThresholdMax()`-metodia:

```java
refresher.setThresholdMax(160);
```

Nämä kynnykset hallitsevat eleen herkkyyttä ja vastustuskäyrää.

## Tilahallinta {#state-management}

`Refresher`-komponentti ylläpitää omaa sisäistä tilaansa ja kommunikoi tilamuutoksista tapahtumien kautta. Kun käyttäjä vetää alaspäin määritellyn kynnyksen yli, `Refresher` laukaisee päivitystapahtuman, johon voit reagoida rekisteröimällä `onRefresh()`-kuuntelijan.

Tämän kuuntelijan sisällä odotetaan, että suoritat tarvittavat toimenpiteet—kuten uusien tietojen hakeminen tai luettelon nollaaminen—ja kutsut sitten nimenomaan:

```java
refresher.finish();
```
:::warning Puuttuva `finish()`
Jos unohdat kutsua `finish()`, refresher jää lataustilaan ikuisesti.
:::

Voit myös ohjelmallisesti poistaa `Refresher`-toiminnon käytöstä milloin tahansa estääksesi käyttäjää käynnistämästä päivitystoimintoa:

```java
refresher.setEnabled(false);
```

Tämä on hyödyllistä, kun päivityksiä tulisi tilapäisesti estää—esimerkiksi latausnäytön aikana tai kun toinen kriittinen prosessi on käynnissä.

## Tyylittely {#styling}

### Teemat {#themes}

`Refresher`-komponentti tukee useita teemoja, joilla voidaan visuaalisesti erottaa eri tilat tai sovittaa sovelluksesi tyyliin. Teemoja voidaan soveltaa käyttämällä `setTheme()`-metodia.

Alla oleva esimerkki kiertää kaikkia saatavilla olevia teemoja, kun vedät päivittääksesi, antaen sinulle live-esikatselun siitä, miltä `Refresher` näyttää eri teemoissa:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
