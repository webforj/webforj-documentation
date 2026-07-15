---
sidebar_position: 10
title: Events
description: >-
  Attach event listeners to webforJ components with lambdas, anonymous classes,
  or method references and reuse the event payload server-side.
slug: events
draft: false
_i18n_hash: e965d354159ccc38ad417700fc3686eb
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponentit, olivatpa ne mukautettuja tai osia keh Frameworkista, tukevat tapahtumankäsittelyä. Voit lisätä tapahtumakuuntelijoita eri tyyppisten tapahtumien, kuten käyttäjävuorovaikutusten, tilamuutosten tai muiden mukautettujen tapahtumien, tallentamiseksi. Näitä tapahtumakuuntelijoita voidaan käyttää erityisten toimintojen tai käyttäytymisten laukaisemiseen tapahtumien yhteydessä.

Alla olevassa esimerkissä tapahtuma lisätään kolmen tuetun menetelmän avulla: lambda-lauseet, anonyymit luokat ja metodiviittaukset.
## Tapahtumien lisääminen {#adding-events}

Tapahtumakuuntelijan lisääminen on mahdollista käyttää yhtä seuraavista malleista, joissa:

- **`myComponent`** on komponentti, johon haluat liittää tapahtumakuuntelijan.

- **`addEventListener`** korvataan tapahtumaspecific-grahamella.

- **`EventListener`** korvataan tapahtuman tyypillä, jota kuunnellaan.

```java
myComponent.addEventListener(e -> {
  //Suoritetaan, kun tapahtuma laukaisee
});

//TAI

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Suoritetaan, kun tapahtuma laukaisee
  }
});

//TAI

myComponent.addEventListener(this::eventMethod);
```

Lisäksi on lisätty synnynnäisiä sokerimenetelmiä tai alias-nimiä, jotka mahdollistavat tapahtumien vaihtoehtoisen lisäämisen käyttämällä `on`-etuliitettä, jota seuraa tapahtuma, kuten:

```java
myComponent.onEvent(e -> {
  //Suoritetaan, kun tapahtuma laukaisee
});
```

## Tapahtuman poistaminen {#removing-an-event}

Kun tapahtumakuuntelija lisätään, `ListenerRegistration`-objekti palautetaan. Sitä voidaan käyttää muun muassa tapahtuman poistamiseen myöhemmin.

```java
//Tapahtuman lisääminen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //Suoritetaan, kun tapahtuma laukaisee
  });

//Tapahtuman poistaminen
listenerRegistration.remove();
```

## Tapahtumakuorman käyttäminen {#using-event-payload}

On tärkeää huomata, että tapahtumat usein tulevat kuormituksen kanssa, joka sisältää lisätietoja, jotka liittyvät tapahtumaan. Voit hyödyntää tätä kuormaa tehokkaasti tapahtumankäsittelijässä saadaksesi käyttökelpoiset tiedot ilman tarpeettomia pyynnön toimittamista asiakkaan ja palvelimen välillä. Näin parannat sovelluksesi suorituskykyä.

Seuraava koodinpätkä kysyy komponentilta tietoja, jotka, tämän demonstroinnin tarkoituksia varten, on jo sisällytetty tapahtuman kuormaan, ja se edustaa tehotonta koodia:

```java
myComponent.addEventListener(e -> {
  // Joka saa tietoja komponentista
  String componentText = e.getComponent().getText();

  //TAI jos komponentti on käytettävissä funktion laajuudessa
  String componentText = myComponent.getText();

  // Käytä componentTextiä suorittaaksesi muita toimintoja.
});
```

Sen sijaan voit käyttää metodin kuormaa, jonka esimerkki sisältää komponentin tekstin, jolloin kierrosavoimaa vältetään:

```java
myComponent.addEventListener(e -> {
  // Joka saa tietoja tapahtumakuormasta
  String componentText = e.getText();

  // Käytä componentTextiä suorittaaksesi muita toimintoja.
});
```

Tämä lähestymistapa minimoi tarpeen kysyä komponentilta tietoja, koska tiedot ovat helposti saatavilla tapahtuman kuormassa. Noudattamalla tätä tehokasta tapahtumankäsittelykäytäntöä voit parantaa komponenttisi suorituskykyä ja reagointikykyä. Lisätietoja löytyy [Asiakas/PALVELINSUHTEET](../architecture/client-server).

### Esimerkki {#sample}

Alla on esittely, joka osoittaa <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> lisäämisen [`Button`](#) komponenttiin. Tämä [`Button`](#) käyttää myös tapahtuman kuorman mukana tulevaa tietoa näyttääkseen tietoja näytöllä.

<ComponentDemo
path='/webforj/buttonevent'
files={['src/main/java/com/webforj/samples/views/button/ButtonEventView.java']}
height='100px'
/>
