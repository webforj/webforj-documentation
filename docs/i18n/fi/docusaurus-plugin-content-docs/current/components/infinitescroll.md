---
title: InfiniteScroll
sidebar_position: 60
_i18n_hash: afeb43fb31ce58db2860ceddd8e8527c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-infinite-scroll" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="infinite-scroll" location="com/webforj/component/infinitescroll/InfiniteScroll" top='true'/>

`InfiniteScroll`-komponentti webforJ:ssä lataa automaattisesti lisää sisältöä, kun käyttäjät vierittävät alaspäin, poistaen tarpeen sivutukselle. Tämä luo sujuvan käyttökokemuksen listoille, syötteille ja tiedostoryhmille lataamalla sisältöä vain tarpeen tullen.

Kun käyttäjät saavuttavat vieritettävän sisällön alareunan, `InfiniteScroll` käynnistää tapahtuman lisäämään lisää tietoa. Kun uusi sisältö latautuu, se näyttää [`Spinner`](../components/spinner) -animaation mukautettavalla tekstillä, joka ilmoittaa, että lisää kohteita on tulossa.

<AppLayoutViewer
path='/webforj/infinitescroll?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

## Tilan hallinta {#state-management}

`InfiniteScroll`-komponentti lähettää tapahtumia ja ylläpitää sisäistä tilaa auttaakseen hallitsemaan, miten ja milloin sisältö ladataan.

Lataa lisää tietoa, kun käyttäjä vierittää, käyttämällä `onScroll()` tai `addScrollListener()` -metodia rekisteröidäksesi kuuntelijan. Kuuntelijassa ladataan tyypillisesti lisäsisältöä ja kutsutaan `update()` päivittämään `InfiniteScroll`-tilan.

```java
infiniteScroll.onScroll(event -> {
    infiniteScroll.add(new Paragraph("Ladattu kohde"));
    infiniteScroll.update();
});
```

Kun kaikki sisältö on ladattu, merkitse vieritys suoritetuksi estääksesi lisäkäynnistykset. Kun olet asettanut suoritetuksi, muista kutsua `update()` soveltaaksesi uutta muutosta:

```java
infiniteScroll.setCompleted(true);
infiniteScroll.update();
```
Tämä poistaa lisä-rajatonta vierittämistä käytöstä.

:::tip Nollaa latauslippu
Voit nollata tämän lipun käyttämällä `setCompleted(false)`, jos myöhemmin sallit käyttäjän ladata lisää sisältöä (esim. päivityksen jälkeen).
:::

## Latausilmoittimen mukauttaminen {#loading-indicator-customization}

Oletusarvoisesti `InfiniteScroll` näyttää sisäänrakennetun latausilmoittimen - pienen animaatiossa olevan [`Spinner`](../components/spinner) -elementin yhdessä "Ladataan tietoja" tekstin kanssa. Voit muuttaa näytettävää tekstiä siirtämällä mukautetun viestin `InfiniteScroll`-konstruktoriin tai käyttämällä `setText()`.

```java
InfiniteScroll infiniteScroll = new InfiniteScroll("Noutaa lisää tietueita...");
infiniteScroll.setText("Ladataan lisää kohteita...");
```

Vastaavasti voit mukauttaa lataamisen aikana näytettävää [`Icon`](../components/icon) -elementtiä käyttämällä `setIcon()`.

<AppLayoutViewer
path='/webforj/infinitescrollloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollLoadingView.java'
cssURL='/css/infinitescroll/infinitescroll.css'
height = '400px'
mobile='true'
/>

### Täydellinen mukauttaminen {#full-customization}

Jos haluat korvata sekä [`Spinner`](../components/spinner) että tekstin täysin omalla merkinnälläsi, voit lisätä sisältöä suoraan erikoissisältöpaikkaan käyttäen `addToContent()`.

Kun täytät sisältöpaikan, se korvää oletusarvoisen latausjärjestelyn kokonaan.

<AppLayoutViewer
path='/webforj/infinitescrollcustomloading?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/infinitescroll/InfiniteScrollCustomLoadingView.java'
cssURL='/css/infinitescroll/infinitescrollcustom.css'
height = '400px'
mobile='true'
/>

## Tyylit {#styling}

<TableBuilder name="InfiniteScroll" />
