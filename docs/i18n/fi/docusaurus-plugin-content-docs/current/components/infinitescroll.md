---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 8c7fc66f78d6508466b5fb9b5dfc3a68
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll`-komponentti webforJ:ssä lataa automaattisesti lisää sisältöä, kun käyttäjät vierittävät alaspäin, jolloin sivutusta ei tarvita. Tämä luo sujuvan kokemuksen luetteloille, syötteille ja dataraskaalle näkymille lataamalla sisältöä vain tarvittaessa.

Kun käyttäjät saavuttavat vieritettävän sisällön alaosan, `InfiniteScroll` laukaisee tapahtuman lisäämään dataa. Kun uusi sisältö latautuu, näytetään [`Spinner`](../components/spinner) mukautettavalla tekstillä, joka ilmoittaa, että lisää kohteita on tulossa.

<!-- INTRO_END -->

## Tilan hallinta {#state-management}

`InfiniteScroll`-komponentti lähettää tapahtumia ja ylläpitää sisäistä tilaa auttaakseen hallitsemaan, miten ja milloin sisältöä ladataan.

<ComponentDemo
path='/webforj/infinitescroll'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

Jos haluat ladata lisää dataa, kun käyttäjä vierittää, käytä `onScroll()`- tai `addScrollListener()`-menetelmää rekisteröidäksesi kuuntelijan. Kuuntelijassa ladataan tyypillisesti lisäsisältö ja kutsutaan `update()` päivittämään `InfiniteScroll`-tila.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Ladattu kohde"));
  infiniteScroll.update();
});
```

Kun kaikki sisältö on ladattu, merkitse vieritys suoritetuksi estääksesi lisälaukaisuja. Kun olet asettanut suoritetuksi, muista kutsua `update()` soveltaaksesi uutta tilaa:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Tämä estää lisäinfiniittisen vierityksen käytöstä.

:::tip Nollaa Latauslipuke
Voit nollata tämän lipukkeen käyttämällä `setCompleted(false)`, jos päätät myöhemmin sallia käyttäjän ladata lisää sisältöä (esim. päivityksen jälkeen).
:::


## Latausindikaattorin mukauttaminen {#loading-indicator-customization}

Oletuksena `InfiniteScroll` näyttää sisäänrakennetun latausindikaattorin - pienen animaatioitu [`Spinner`](../components/spinner) yhdessä “Ladataan dataa” -tekstin kanssa. Voit muuttaa näytettävää tekstiä antamalla mukautetun viestin `InfiniteScroll`-rakentajalle tai käyttämällä `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Haetaan lisää tietueita...");
infiniteScroll.setText("Ladataan lisää kohteita...");
```

Vastaavasti voit mukauttaa lataamisen aikana näytettävää [`Icon`](../components/icon) käyttämällä `setIcon()`.

<ComponentDemo
path='/webforj/infinitescrollloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescroll.css',
]}
/>

### Täysi mukauttaminen {#full-customization}

Jos haluat täysin korvata sekä [`Spinner`](../components/spinner) että tekstin omalla markupillasi, voit lisätä sisältöä suoraan erityiseen sisältöpaikkaan käyttämällä `addToContent()`.

Kun täytät sisältöpaikan, se korvaa oletuslatauslayoutin täysin.

<ComponentDemo
path='/webforj/infinitescrollcustomloading'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java',
  'src/main/resources/static/css/infinitescroll/infinitescrollcustom.css',
]}
/>

## Tyylit {#styling}

<TableBuilder name="InfiniteScroll" />
