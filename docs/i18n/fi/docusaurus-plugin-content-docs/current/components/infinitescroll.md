---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: b6795a86cf03a60d9ef9e7d89749c9ab
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll`-komponentti webforJ:ssä lataa automaattisesti lisää sisältöä käyttäjien vierittäessä alaspäin, mikä poistaa tarpeen sivutukselle. Tämä luo sujuvan kokemuksen luetteloille, syötteille ja dataraskaalle näkymille lataamalla sisältöä vain tarpeen mukaan.

Kun käyttäjät saavuttavat vieritettävän sisällön alaosan, `InfiniteScroll` laukaisee tapahtuman lisäämään lisää dataa. Kun uusi sisältö latautuu, se näyttää [`Spinner`](../components/spinner) -komponentin, jossa on mukautettavissa oleva teksti, joka ilmoittaa, että lisää kohteita on tulossa.

<!-- INTRO_END -->

## Tilanhallinta {#state-management}

`InfiniteScroll`-komponentti lähettää tapahtumia ja ylläpitää sisäistä tilaa auttaakseen hallitsemaan, miten ja milloin sisältöä ladataan.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

Lataaksesi lisää dataa, kun käyttäjä vierittää, käytä `onScroll()` tai `addScrollListener()` -metodia rekisteröidäksesi kuuntelijan. Kuuntelijassa lataat tyypillisesti lisäsisältöä ja kutsut `update()`-metodia päivittääksesi `InfiniteScroll`-tilan.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Ladattu kohde"));
    infiniteScroll.update();
});
```

Kun kaikki sisältö on ladattu, merkitse vieritys suoritetuksi estääksesi lisälaikkurit. Kun olet asettanut suoritettu-tilan, muista kutsua `update()` soveltaaksesi uutta tilaa:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Tämä estää lisäinfiniittisen vierityskäyttäytymisen.

:::tip Nollaa latauslippu
Voit nollata tämän lipun käyttämällä `setCompleted(false)`, jos myöhemmin sallii käyttäjän ladata lisää sisältöä (esim. päivityksen jälkeen).
:::

## Latausindikaattorin mukauttaminen {#loading-indicator-customization}

Oletusarvoisesti `InfiniteScroll` näyttää sisäänrakennetun latausindikaattorin - pienen animaatio[`Spinner`](../components/spinner)in yhdessä "Ladataan dataa" -tekstin kanssa. Voit muuttaa näytettävää tekstiä antamalla mukautetun viestin `InfiniteScroll`-rakentajalle tai käyttämällä `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Ladataan lisää tietueita...");
infiniteScroll.setText("Ladataan lisää kohteita...");
```

Samoin voit mukauttaa [`Icon`](../components/icon), joka näytetään lataamisen aikana käyttämällä `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Täydellinen mukauttaminen {#full-customization}

Jos haluat täysin korvata sekä [`Spinner`](../components/spinner)in että tekstin omalla merkinnälläsi, voit lisätä sisältöä suoraan erityiseen sisältöaukkoon käyttämällä `addToContent()`.

Kun täytät sisältöaukkoa, se korvataan kokonaan oletuslatauslayoutilla.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Tyylittely {#styling}

<TableBuilder name="InfiniteScroll" />
