---
title: Refresher
sidebar_position: 101
_i18n_hash: 99793e9f95d4c5a052014f677aa8a6cb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

Pull-to-refresh on yleinen malli mobiili- ja napinpainamisystävällisissä käyttöliittymissä, ja `Refresher`-komponentti tuo sen rolattaviin säiliöihin webforJ:ssä. Kun käyttäjät pyyhkäisevät alaspäin määritellyn kynnyksen yli, se siirtyy visuaalisten tilojen läpi: `pull`, `release` ja `refreshing`, jokaisessa on mukautettava kuvake ja lokalisoitu teksti. Se sopii hyvin yhteen [`InfiniteScroll`](../components/infinitescroll) kanssa sisällön lataamista tai nollaamista varten elepohjaisella syötteellä.

<!-- INTRO_END -->

## Instansiointi ja kansainvälistäminen {#instantiation-and-internationalization}

Lisää `Refresher` instansioimalla se ja rekisteröimällä päivityskuuntelija. Kun päivitystoiminnot ovat valmiit, kutsu `finish()` palauttaaksesi komponentti lepotilaan.

:::info Kuinka aktivoida `Refresher`
Aktivoidaksesi `Refresher`, **napsauta ja vedä alaspäin** rullattavan alueen yläreunasta. Vaikka tämä ele on tuttu mobiililaitteilla, se ei ole yhtä yleinen työpöydällä—muista pitää ja vetää hiirellä.
:::

<ComponentDemo
path='/webforj/refresher'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

Tätä lähestymistapaa käytetään yleisesti uusiin sivuisiin luetteloihin päivittämisessä tai äärettömän vierityksen lataamisen aloittamisessa.

### Kansainvälistäminen {#internationalization}

Jokaisen tilan etiketti voidaan myös lokalisoida `RefresherI18n`-objektin avulla. Kolme tilaa ovat:

- Pull: Alkuperäisen eleen teksti (esim. "Vedä alas päivittääksesi")
- Release: Kynnysarvo saavutettu (esim. "Vapauta päivittääksesi")
- Refresh: Lataustila (esim. "Päivitetään")

Tämä mahdollistaa monikielisen tuen ja brändäyksen säädöt tarpeen mukaan.

<ComponentDemo
path='/webforj/refresheri18n'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Kuvake mukauttaminen {#icon-customization}

Voit vaihtaa [`Icons`](../components/icon), joita käytetään `pull`/`release` ja `refreshing` vaiheissa, käyttäen joko ennalta määriteltyä [`Icon`](../components/icon) tai [Kuvake URLia](../managing-resources/assets-protocols). Nämä ovat hyödyllisiä, kun haluat soveltaa brändäystä tai mukautettua animaatiota.

<ComponentDemo
path='/webforj/refreshericon'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

## Vedon käyttäytymisen konfigurointi {#pull-behavior-configuration}

### Kynnysarvo {#threshold}

Aseta kuinka kauas käyttäjän täytyy vetää alaspäin (pikseleinä) ennen kuin päivittämistä laukaistaan:

```java
refresher.setThreshold(80); // oletus: 80px
```

### Kynnyksen maksimi {#threshold-maximum}

Määritä sallitun vetomatkan maksimiarseno käyttämällä `setThresholdMax()`-metodia:

```java
refresher.setThresholdMax(160);
```

Nämä kynnykset hallitsevat eleen herkkyyttä ja vastuskäyrää.

## Tilanhallinta {#state-management}

`Refresher`-komponentti ylläpitää omaa sisäistä tilaansa ja kommunikoi tilamuutoksia tapahtumien kautta. Kun käyttäjä vetää alaspäin määritetyn kynnyksen yli, `Refresher` lähettää päivitystapahtuman, johon voit reagoida rekisteröimällä `onRefresh()`-kuuntelijan.

Tämän kuuntelijan sisällä sinun odotetaan toteuttavan tarvittavat toiminnot—esimerkiksi uusien tietojen hakemisen tai luettelon nollaamisen—ja sitten nimenomaan kutsuvan:

```java
refresher.finish();
```
:::warning Puuttuva `finish()`
Jos unohdat kutsua `finish()`, refresher pysyy lataustilassa ikuisesti.
:::

Voit myös ohjelmallisesti estää `Refresher`-toiminnan milloin tahansa estääksesi käyttäjää laukaamassa päivitys käyttäytymistä:

```java
refresher.setEnabled(false);
```

Tämä on hyödyllistä, kun päivitykset pitäisi estää tilapäisesti—esimerkiksi latausnäytön aikana tai kun toinen kriittinen prosessi on käynnissä.

## Tyylit {#styling}

### Teemat {#themes}

`Refresher`-komponentti tukee useita teemoja erottaakseen visuaalisesti erilaisia tiloja tai vastatakseen sovelluksesi ilmeen ja tunnelman. Teemoja voidaan soveltaa käyttämällä `setTheme()`-metodia.

Seuraava esimerkki kierrättää kaikkia saatavilla olevia teemoja aina kun vedät päivitystä varten, antaen sinulle live-esikatselun siitä, miltä `Refresher` näyttää eri teemoissa:

<ComponentDemo
path='/webforj/refresherthemes'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java',
  'src/main/resources/static/css/refresher/refresher.css',
]}
/>

<TableBuilder name="Refresher" />
