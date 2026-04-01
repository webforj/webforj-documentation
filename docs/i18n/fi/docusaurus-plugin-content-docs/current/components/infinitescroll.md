---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: b41c992436f501c03ae93b1dfc2c254b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll`-komponentti webforJ:ssa lataa automaattisesti lisää sisältöä, kun käyttäjät vierittävät alaspäin, mikä eliminoi sivutuksen tarpeen. Tämä luo sujuvan kokemuksen luetteloille, syötteille ja tietopitoisille näkymille lataamalla sisältöä vain tarvittaessa.

Kun käyttäjät saavuttavat vieritettävän sisällön alareunan, `InfiniteScroll` laukaisee tapahtuman lisäämään lisää tietoja. Kun uutta sisältöä ladataan, se näyttää [`Spinner`](../components/spinner) -elementin, jossa on mukautettavissa teksti, joka kertoo, että lisää kohteita on matkalla.

<!-- INTRO_END -->

## Tilanhallinta {#state-management}

`InfiniteScroll`-komponentti lähettää tapahtumia ja ylläpitää sisäistä tilaa auttaakseen hallitsemaan, kuinka ja milloin sisältöä ladataan.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

Lataa lisää tietoja, kun käyttäjä vierittää, käyttämällä `onScroll()` tai `addScrollListener()` -metodia kuuntelijan rekisteröimiseksi. Kuuntelijassa lataat tyypillisesti lisäsisältöä ja kutsut `update()`-metodia `InfiniteScroll`-tilan päivittämiseksi.

```java
infiniteScroll.onScroll(event -> {
  infiniteScroll.add(new Paragraph("Ladattu kohde"));
  infiniteScroll.update();
});
```

Kun kaikki sisältö on ladattu, merkitse vieritys täydelliseksi estääksesi lisälauskautumiset. Kun asetat täydelliseksi, muista kutsua `update()` ottaaksesi käyttöön uuden tilan:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Tämä estää lisäinfinite-vieritystoiminnon.

:::tip Nollaa latauslippu
Voit nollata tämän lipun käyttämällä `setCompleted(false)`, jos myöhemmin sallitaan käyttäjän ladata lisää sisältöä (esim. päivityksen jälkeen).
:::


## Latausindikaattorin mukauttaminen {#loading-indicator-customization}

Oletusarvoisesti `InfiniteScroll` näyttää sisäänrakennetun latausindikaattorin - pienen animaatio [`Spinner`](../components/spinner) -elementin yhdessä “Ladataan tietoja” -tekstin kanssa. Voit muuttaa näytettävää tekstiä välittämällä mukautetun viestin `InfiniteScroll`-konstruktoriin tai käyttämällä `setText()`-metodia.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Haetaan lisää tietueita...");
infiniteScroll.setText("Ladataan lisää kohteita...");
```

Samalla tavalla voit mukauttaa lataamisen aikana näkyvää [`Icon`](../components/icon) -elementtiä käyttämällä `setIcon()`-metodia.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Täydellinen mukauttaminen {#full-customization}

Jos haluat täysin korvata sekä [`Spinner`](../components/spinner) -elementin että tekstin omalla markupillasi, voit lisätä sisältöä suoraan erityiseen sisältölokeroon käyttämällä `addToContent()`.

Kun täytät sisältölokeron, se korvataan kokonaan oletusarvoinen latauslayout.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Tyylitys {#styling}

<TableBuilder name="InfiniteScroll" />
