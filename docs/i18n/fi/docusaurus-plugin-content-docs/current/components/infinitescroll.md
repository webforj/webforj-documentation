---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: 3384cb35d5087561cc9be2c11b95c7e1
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll`-komponentti webforJ:ssä lataa automaattisesti enemmän sisältöä, kun käyttäjät vierittävät alas, poistaen tarpeen sivinavigoinnille. Tämä luo sujuvan kokemuksen listoille, syötteille ja dataraskaalle näkymälle lataamalla sisältöä vain tarpeen mukaan.

Kun käyttäjät saavuttavat vieritettävän sisällön alimman rajan, `InfiniteScroll` laukaisee tapahtuman lisäämään enemmän dataa. Uuden sisällön lataamisen aikana se näyttää [`Spinner`](../components/spinner), jossa on mukautettava teksti, joka ilmoittaa, että lisää kohteita on tulossa.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Tilan hallinta {#state-management}

`InfiniteScroll`-komponentti lähettää tapahtumia ja ylläpitää sisäistä tilaa auttaakseen hallitsemaan, kuinka ja milloin sisältöä ladataan.

Jotta voit hakea lisää dataa, kun käyttäjä vierittää, käytä `onScroll()` tai `addScrollListener()` -menetelmää rekisteröidäksesi kuuntelijan. Kuuntelijan sisällä lataat tyypillisesti lisäsisältöä ja kutsut `update()` päivittääksesi `InfiniteScroll`-tilan.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Ladattu kohde"));
    infiniteScroll.update();
});
```

Kun kaikki sisältö on ladattu, merkitse vieritys suoritetuksi estääksesi myöhemmät laukaisut. Kun olet asettanut tilan suoritetuksi, muista kutsua `update()` soveltaaksesi uutta tilaa:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Tämä estää lisäinfinite vierityksen toiminnan.

:::tip Nollaa latauslippu
Voit nollata tämän lipun käyttämällä `setCompleted(false)`, jos myöhemmin sallitaan käyttäjän ladata lisää sisältöä (esim. päivityksen jälkeen).
:::

## Latausindikaattorin mukauttaminen {#loading-indicator-customization}

Oletuksena `InfiniteScroll` näyttää sisäänrakennetun latausindikaattorin - pienen animaatio [`Spinner`](../components/spinner) yhdessä “Ladataan dataa” -tekstin kanssa. Voit vaihtaa näytettävää tekstiä välittämällä mukautetun viestin `InfiniteScroll`-konstruktoriin tai käyttämällä `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Haetaan lisää tietueita...");
infiniteScroll.setText("Ladataan lisää kohteita...");
```

Vastaavasti voit mukauttaa lataamisen aikana näytettävää [`Icon`](../components/icon) käyttämällä `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Täydellinen mukautus {#full-customization}

Jos haluat täysin korvata sekä [`Spinner`](../components/spinner) että tekstin omalla merkinnälläsi, voit lisätä sisältöä suoraan erityiseen sisältöpaikkaan käyttäen `addToContent()`.

Kun täytät sisältöpaikan, se korvataan kokonaan oletuslatauslayoutilla.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Tyylittely {#styling}

<TableBuilder name="InfiniteScroll" />
