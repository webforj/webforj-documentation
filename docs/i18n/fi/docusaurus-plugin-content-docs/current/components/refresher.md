---
title: Refresher
sidebar_position: 101
description: >-
  Enable pull-to-refresh on scrollable areas with the Refresher component, with
  pull, release, and refreshing states and i18n labels.
_i18n_hash: 9bb531347032e46ccbb9e7fa28c403f8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pyyhkäisy-päivitys on yleinen malli mobiili- ja kosketusystävällisissä käyttöliittymissä, ja `Refresher`-komponentti tuo sen vieritettävien säiliöiden käyttöön webforJ:ssä. Kun käyttäjät pyyhkäisevät alaspäin määritellyn kynnyksen yli, se siirtyy visuaalisten tilojen läpi: `pull`, `release` ja `refreshing`, joista jokaisella on mukautettavissa oleva kuvake ja lokalisoitu teksti. Se toimii hyvin yhdessä [`InfiniteScroll`](../components/infinitescroll) -komponentin kanssa, joka mahdollistaa sisällön lataamisen tai nollaamisen kosketusperusteisen syötteen avulla.

<!-- INTRO_END -->

## Instansointi ja kansainvälisyys {#instantiation-and-internationalization}

Lisää `Refresher` instansointamalla se ja rekisteröimällä päivityksen kuuntelija. Kun päivitystoiminnot on suoritettu, kutsu `finish()`, jotta komponentti palautuu lepotilaan.

:::info Kuinka aktivoida `Refresher`
Aktivoidaksesi `Refresher`, **napsauta ja vedä alaspäin** vieritettävän alueen yläosasta. Vaikka tämä ele on tuttu mobiililaitteissa, se ei ole yhtä yleinen työpöydällä — varmista, että pidät ja vedät hiirellä.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

Tätä lähestymistapaa käytetään yleisesti sivutettujen luetteloiden päivittämiseen tai äärettömän vierittämisen lataamisen uudelleenkäynnistämiseen.

### Kansainvälisyys {#internationalization}

Jokaisen tilan etiketti voidaan myös lokalisoida käyttämällä `RefresherI18n`-objektia. Kolme tilaa ovat:

- Pull: Alkuperäinen eleteksti (esim. "Pyyhkäise alaspäin päivittääksesi")
- Release: Kynnys saavutettu (esim. "Vapauta päivittääksesi")
- Refresh: Lataustila (esim. "Päivitän")

Tämä mahdollistaa monikielisen tuen ja brändäysmuutokset tarpeen mukaan.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Kuvake mukauttaminen {#icon-customization}

Voit vaihtaa [`Icons`](../components/icon)-kuvakkeita, joita käytetään `pull`/`release` ja `refreshing` vaiheissa käyttäen joko ennalta määritettyä [`Icon`](../components/icon) -kuvaketta tai [kuvaketta URL](../managing-resources/assets-protocols). Nämä ovat hyödyllisiä, kun haluat soveltaa brändäystä tai mukautettua animaatiota.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

## Vetokäyttäytymisen määrittäminen {#pull-behavior-configuration}

### Kynnys {#threshold}

Aseta, kuinka pitkälle käyttäjän on vedettävä alaspäin (pikseleinä) ennen päivityksen laukaamista:

```java
refresher.setThreshold(80); // oletusarvo: 80px
```

### Kynnysmaksimi {#threshold-maximum}

Määrittääksesi sallitun maksimi vetomatkan, käytä `setThresholdMax()`-metodia:

```java
refresher.setThresholdMax(160);
```

Nämä kynnykset hallitsevat eleen herkkyyttä ja vastuskaavaa.

## Tilanhallinta {#state-management}

`Refresher`-komponentti ylläpitää omaa sisäistä tilaansa ja kommunikoi tilamuutokset tapahtumien kautta. Kun käyttäjä vetää alaspäin määritellyn kynnyksen yli, `Refresher` lähettää päivitystapahtuman, johon voit reagoida rekisteröimällä `onRefresh()` -kuuntelijan.

Tässä kuuntelijassa odotetaan, että suoritat tarvittavan toiminnan — kuten uuden datan hakemisen tai luettelon nollaamisen — ja kutsut sitten eksplisiittisesti:

```java
refresher.finish();
```
:::warning Puuttuva `finish()`
Jos unohdat kutsua `finish()`, refresher pysyy lataustilassa ikuisesti.
:::

Voit myös ohjelmallisesti estää `Refresher`-toiminnon milloin tahansa estääksesi käyttäjää laukaiselemasta päivitys käyttäytymistä:

```java
refresher.setEnabled(false);
```

Tämä on hyödyllistä, kun päivitykset tulisi ajaksi estää — esimerkiksi latausnäytön aikana tai kun jokin muu kriittinen prosessi on käynnissä.

## Tyylittely {#styling}

### Teemat {#themes}

`Refresher`-komponentti tukee useita teemoja, jotta erilaiset tilat voidaan visuaalisesti erottaa tai sovittaa sovelluksesi ulkoasuun. Teemoja voidaan käyttää `setTheme()` -metodin avulla.

Seuraava esimerkki kierrättää kaikkia käytettävissä olevia teemoja jokaisella pyyhkäisyllä, jolloin saat elävän esikatselun siitä, miltä `Refresher` näyttää eri teemoissa:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/frontend/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
