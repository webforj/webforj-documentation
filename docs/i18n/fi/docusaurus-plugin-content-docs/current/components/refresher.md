---
title: Refresher
sidebar_position: 101
_i18n_hash: 77c3e72a5a59a55d61a7dba79efb7324
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

`Refresher`-komponentti webforJ:ssä mahdollistaa pull-to-refresh -vuorovaikutuksen vieritettävissä astioissa – täydellinen dynaamisen datan lataamiseen mobiililaitteissa tai kosketusystävällisissä käyttöliittymissä. Kun käyttäjät pyyhkäisevät alaspäin konfiguroitavan kynnyksen yli, refresher siirtyy visuaalisiin tiloihin: `pull`, `release`, ja `refreshing`. Jokainen tila esittää mukautettavan ikonin ja lokalisoidun tekstin, joka selvästi viestii palautetta.

Voit käyttää `Refresher`-komponenttia yhdessä komponenttien, kuten [`InfiniteScroll`](../components/infinitescroll), kanssa ladata sisältöä tai nollata tilan yksinkertaisella gestuuriin perustuvalla syötteellä. Komponentti on täysin konfiguroitavissa vuorovaikutuskäyttäytymisen, ulkoasun, lokalisaation ja integroinnin osalta muun UI:si kanssa.

## Instansiointi ja kansainvälistäminen {#instantiation-and-internationalization}

Lisää `Refresher` instansioimalla se ja rekisteröimällä päivityskuuntelija. Kun päivitystoiminnot on saatu päätökseen, kutsu `finish()` nollataksesi komponentin lepotilaan.

:::info Kuinka aktivoida `Refresher`
Aktivoidaksesi `Refresher`, **napsauta ja vedä alaspäin** vieritettävän alueen yläosasta. Vaikka tämä ele on tuttu mobiililaitteilla, se ei ole niin yleinen työpöydällä – varmista, että pidät ja vedät hiirelläsi.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Tätä lähestymistapaa käytetään usein sivuittain laskettavien listojen päivittämiseen tai äärettömän vierityksen lataamisen aloittamiseen.

### Kansainvälistäminen {#internationalization}

Jokaisen tilan etiketti voidaan myös lokalisoida käyttämällä `RefresherI18n` -objektia. Kolme tilaa ovat:

- Pull: Alkuperäinen eleteksti (esim. "Vedä alas päivittääksesi")
- Release: Käynnistyskynnys saavutettu (esim. "Päästäksesi päivittääksesi")
- Refresh: Lataustila (esim. "Päivitetään")

Tämä mahdollistaa monikielisen tuen ja brändin säädöt tarpeen mukaan.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Ikonien mukauttaminen {#icon-customization}

Voit muuttaa [`Icons`](../components/icon) -ikoneita, joita käytetään `pull`/`release` ja `refreshing` -vaiheissa joko ennaltamäärätyn [`Icon`](../components/icon) tai [Ikoni-URL:n](../managing-resources/assets-protocols) avulla. Nämä ovat hyödyllisiä, kun haluat soveltaa brändäystä tai mukautettua animaatiota.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Vetokäyttäytymisen konfigurointi {#pull-behavior-configuration}

### Kynnysarvo {#threshold}

Määritä kuinka kauas käyttäjän on vedettävä alaspäin (pikseleinä) ennen päivityksen käynnistämistä:

```java
refresher.setThreshold(80); // oletusarvo: 80px
```

### Kynnysarvon maksimi {#threshold-maximum}

Määritä sallittu maksimi vedon etäisyys käyttämällä `setThresholdMax()` -metodia:

```java
refresher.setThresholdMax(160);
```

Nämä kynnykset hallitsevat eleen herkkyyttä ja vastuskäyrää.

## Tilanhallinta {#state-management}

`Refresher`-komponentti ylläpitää omaa sisäistä tilaansa ja viestii tilamuutoksista tapahtumien kautta. Kun käyttäjä vetää alaspäin määritellyn kynnyksen ohi, `Refresher` lähettää päivitystapahtuman, johon voit reagoida rekisteröimällä `onRefresh()` -kuuntelijan.

Tämän kuuntelijan sisällä olet odotettu suorittamaan tarvittava toimenpide – kuten uusien tietojen hakeminen tai luettelon nollaaminen – ja sitten kutsumaan selvästi:

```java
refresher.finish();
```
:::warning Puuttuva `finish()`
Jos unohtat kutsua `finish()`, refresher pysyy lataustilassa ikuisesti.
:::

Voit myös ohjelmallisesti poistaa `Refresher`-komponentin käytöstä milloin tahansa estääksesi käyttäjää laukaiselemasta päivitystoimintaa:

```java
refresher.setEnabled(false);
```

Tämä on hyödyllistä, kun päivityksiä tulisi väliaikaisesti estää – esimerkiksi latausnäytön aikana tai kun toinen kriittinen prosessi on käynnissä.

## Tyylittely {#styling}

### Teemat {#themes}

`Refresher`-komponentti tukee useita teemoja eri tilojen visuaaliseksi erottamiseksi tai sovittamiseksi sovelluksesi ilmeeseen. Teemat voidaan soveltaa käyttämällä `setTheme()` -metodia.

Seuraava esimerkki kiertää kaikki saatavilla olevat teemat joka kerta, kun vedät päivittääksesi, antaen sinulle live-esikatselun siitä, miltä `Refresher` näyttää eri teemoissa:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
