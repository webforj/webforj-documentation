---
title: Refresher
sidebar_position: 101
_i18n_hash: de00fad980f74bdd18544409408de0b8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-refresher" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="refresher" location="com/webforj/component/refresher/Refresher" top='true'/>

`Refresher`-komponentti webforJ:ssa mahdollistaa vetämällä virkistystoiminnan vieritettävissä konteissa—erittäin hyvä dynaamisen datan lataamiseen mobiili- tai kosketusystävällisissä käyttöliittymissä. Kun käyttäjät pyyhkäisevät alaspäin yli määritetyn raja-arvon, virkistin siirtyy visuaalisten tilojen läpi: `pull`, `release` ja `refreshing`. Jokainen tila esittelee mukautettavan ikonin ja lokalisoidun tekstin selkeän palautteen viestimiseksi.

Voit käyttää `Refresher`-komponenttia yhdessä komponenttien, kuten [`InfiniteScroll`](../components/infinitescroll) kanssa, ladataksesi sisältöä tai palauttaaksesi tilan yksinkertaisella elepohjaisella syötteellä. Komponentti on täysin mukautettavissa vuorovaikutuskäyttäytymisen, ulkoasun, lokalisaation ja integraation osalta muun käyttöliittymäsi kanssa.

## Instansointi ja kansainvälistäminen {#instantiation-and-internationalization}

Lisää `Refresher` instansioimalla se ja rekisteröimällä virkistyskuuntelija. Kun virkistysohjelmat ovat valmiit, kutsu `finish()` palauttaaksesi komponentti sen lepotilaan.

:::info Kuinka aktivoida `Refresher`
Aktivoidaksesi `Refresher`, **napsauta ja vedä alaspäin** vieritettävän alueen yläosasta. Vaikka tämä ele on tuttu mobiilissa, se on vähemmän yleinen työpöydällä—muista pitää ja vetää hiirellä.
:::

<AppLayoutViewer
path='/webforj/refresher?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

Tätä lähestymistapaa käytetään yleisesti sivutettujen luetteloiden virkistämiseen tai äärettömän vierityksen latauksen uudelleenkäynnistämiseen.

### Kansainvälistäminen {#internationalization}

Jokaisen tilan etiketti voidaan myös lokalisoida käyttämällä `RefresherI18n`-objektia. Kolme tilaa ovat:

- Pull: Alkuperäisen eleen teksti (esim. "Vedä alas virkistääksesi")
- Release: Käynnistyksen raja saavutettu (esim. "Päästä virkistääksesi")
- Refresh: Lataustila (esim. "Virittäminen")

Tämä mahdollistaa monikielisen tuen ja brändäyssäätöjen tarpeen mukaan.

<AppLayoutViewer 
path='/webforj/refresheri18n?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherI18nView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Ikoni mukauttaminen {#icon-customization}

Voit muuttaa [`Icons`](../components/icon) -ikoneita, joita käytetään `pull`/`release` ja `refreshing` vaiheissa joko esik Defined [`Icon`](../components/icon) tai [Ikoni URL](../managing-resources/assets-protocols) avulla. Nämä ovat hyödyllisiä, kun haluat soveltaa brändäystä tai mukautettua animaatiota.

<AppLayoutViewer 
path='/webforj/refreshericon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherIconView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

## Vetokäyttäytymisen asetukset {#pull-behavior-configuration}

### Raja-arvo {#threshold}

Määritä, kuinka pitkälle käyttäjän on vedettävä alaspäin (pikseleinä) ennen virkistyksen laukaamista:

```java
refresher.setThreshold(80); // oletus: 80px
```

### Raja-arvon maksimi {#threshold-maximum}

Määrittääksesi sallitun suurimman vedetyttöä etäisyyden, käytä `setThresholdMax()`-metodia:

```java
refresher.setThresholdMax(160);
```

Nämä rajat säätelevät eleen herkkyyttä ja vastustuskäyrää.

## Tilan hallinta {#state-management}

`Refresher`-komponentti ylläpitää omaa sisäistä tilaansa ja kommunikoi tilan muutokset tapahtumien kautta. Kun käyttäjä vetää alaspäin määritetyn raja-arvon ohi, `Refresher` lähettää virkistystapahtuman, johon voit vastata rekisteröimällä `onRefresh()`-kuuntelijan.

Tässä kuuntelijassa odotetaan, että suoritat tarvittavat toimenpiteet—kuten uusien tietojen hakemisen tai luettelon palauttamisen—ja kutsut sitten nimenomaisesti:

```java
refresher.finish();
```
:::warning Puuttuva `finish()`
Jos unohtat kutsua `finish()`, virkistin pysyy lataustilassa määräänsä ikuisesti.
:::

Voit myös ohjelmallisesti poistaa `Refresher` käytöstä milloin tahansa estääksesi käyttäjän laukaisevan virkistyskäyttäytymistä:

```java
refresher.setEnabled(false);
```

Tämä on hyödyllistä, kun virkistyksiä tulisi tilapäisesti estää—esimerkiksi latausnäytön aikana tai kun jokin muu kriittinen prosessi on käynnissä.

## Tyylittely {#styling}

### Teemat {#themes}

`Refresher`-komponentti tukee useita teemoja, joilla voidaan visuaalisesti erottaa eri tilat tai sovittaa sovelluksesi ilmeeseen ja tunne. Teemoja voidaan soveltaa käyttämällä `setTheme()`-metodia.

Seuraava malli kiertää kaikki käytettävissä olevat teemat joka kerta kun vedät virkistääksesi, antaen sinulle live-esikatsauksen siitä, miltä `Refresher` näyttää eri teemoissa:

<AppLayoutViewer 
path='/webforj/refresherthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/refresher/RefresherThemesView.java'
cssURL='/css/refresher/refresher.css'
height = '400px'
mobile='true'
/>

<TableBuilder name="Refresher" />
