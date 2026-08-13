---
title: InfiniteScroll
sidebar_position: 60
description: >-
  Load more content as users scroll with the InfiniteScroll component, emitting
  scroll events and showing a customizable loading spinner.
_i18n_hash: f021168e8d6187e38da9107bd2f3ad65
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll`-komponentti webforJ:ssä lataa automaattisesti lisää sisältöä, kun käyttäjät selaavat alaspäin, mikä eliminoi sivutuksen tarpeen. Tämä luo sujuvan kokemuksen luetteloille, syötteille ja dataraskaille näkymille lataamalla sisältöä vain tarvittaessa.

Kun käyttäjät saavuttavat scrollattavan sisällön alaosan, `InfiniteScroll` laukaisee tapahtuman lisää tietoa ladatessa. Kun uusi sisältö latautuu, se näyttää [`Spinner`](../components/spinner) -komponentin, jossa on mukautettavaa tekstiä, joka osoittaa, että lisää kohteita on tulossa.

<!-- INTRO_END -->

## Tilan hallinta {#state-management}

`InfiniteScroll`-komponentti lähettää tapahtumia ja ylläpitää sisäistä tilaa auttaakseen hallitsemaan, miten ja milloin sisältöä ladataan.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

Jotta voit ladata lisää tietoa, kun käyttäjä selaa, käytä `onScroll()` tai `addScrollListener()` -menetelmää rekisteröidäksesi kuuntelijan. Kuuntelijassa lataat tyypillisesti lisäsisältöä ja kutsut `update()`-metodia päivittääksesi `InfiniteScroll`-tilan.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Ladattu kohde"));
  infiniteScroll.update();
});
```

Kun kaikki sisältö on ladattu, merkitse scrollaus valmiiksi, jotta estät lisälaatikoinnin. Kun olet asettanut valmiiksi, muista kutsua `update()` soveltaaksesi uutta tilaa:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Tämä estää lisäinfinite scroll -käyttäytymisen.

:::tip Nollaa latauslippu
Voit nollata tämän lipun käyttämällä `setCompleted(false)`, jos myöhemmin sallit käyttäjän ladata lisää sisältöä (esim. päivityksen jälkeen).
:::


## Latausilmoituksen mukauttaminen {#loading-indicator-customization}

Oletusarvoisesti `InfiniteScroll` näyttää sisäänrakennetun latausilmoituksen - pienen animaation [`Spinner`](../components/spinner) yhdistettynä "Ladataan tietoja" -tekstiin. Voit muuttaa näytettävää tekstiä välittämällä mukautetun viestin `InfiniteScroll`-konstruktoriin tai käyttämällä `setText()`-metodia.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Ladataan lisää tietueita...");
infiniteScroll.setText("Ladataan lisää kohteita...");
```

Vastaavasti voit mukauttaa lataamisen aikana näytettävää [`Ikonia`](../components/icon) käyttämällä `setIcon()`.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescroll.css',
]}
/>

### Täysi mukauttaminen {#full-customization}

Jos haluat täysin korvata sekä [`Spinner`](../components/spinner) että tekstin omalla merkinnälläsi,
voit lisätä sisältöä suoraan erityiseen sisältöpaikkaan käyttämällä `addToContent()`.

Kun täytät sisältöpaikan, se korvata oletusarvoisen latauslokakuvastruktuurin kokonaan.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/frontend/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Tyylittely {#styling}

<TableBuilder name="InfiniteScroll" />
