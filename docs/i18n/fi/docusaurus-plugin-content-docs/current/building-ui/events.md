---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 6c1d6fc7f2d8e0027320e0323b107dca
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponentit, olivatpa ne mukautettuja tai osana kehystä, tukevat tapahtumien käsittelyä. Voit lisätä tapahtumakuuntelijoita kaapataksesi erilaisia tapahtumia, kuten käyttäjäinteraktioita, tilan muutoksia tai muita mukautettuja tapahtumia. Näitä tapahtumakuuntelijoita voidaan käyttää erityisten toimintojen tai käyttäytymisten käynnistämiseen vastauksena tapahtumiin.

Alla olevassa esimerkissä tapahtuma lisätään käyttämällä jokaista kolmea tuettua menetelmää: lambda-ilmaisut, anonyymit luokat ja metodiviittaukset.
## Tapahtumien lisääminen {#adding-events}

Tapahtumakuuntelijan lisääminen on mahdollista käyttää yhtä seuraavista malleista, joissa:

- **`myComponent`** on komponentti, johon haluat liittää tapahtumakuuntelijan.

- **`addEventListener`** korvataan tapahtumakohtaisella metodilla.

- **`EventListener`** korvataan kuunneltavan tapahtuman tyypillä.

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

Lisäsyntaktiset sokerimenetelmät tai aliasit on lisätty mahdollistamaan vaihtoehtoinen tapahtumien lisääminen käyttämällä `on`-etuliitettä seurattuna tapahtumalla, kuten:

```java
myComponent.onEvent(e -> {
  //Suoritetaan, kun tapahtuma laukaisee
});
```

## Tapahtuman poistaminen {#removing-an-event}

Kun lisätään tapahtumakuuntelija, palautetaan `ListenerRegistration`-olio. Tätä voidaan käyttää muun muassa tapahtuman poistamiseen myöhemmin.

```java
//Lisätään tapahtuma
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    //Suoritetaan, kun tapahtuma laukaisee
  });

//Poistetaan tapahtuma
listenerRegistration.remove();
```

## Tapahtuman kuormituksen käyttäminen {#using-event-payload}

On tärkeää huomata, että tapahtumat tulevat usein kuormituksen kanssa, joka sisältää lisätietoja tapahtumasta. Voit hyödyntää tätä kuormitusta tehokkaasti tapahtumankäsittelijässä päästäksesi käsiksi olennaisiin tietoihin ilman turhaa liikennettä asiakkaan ja palvelimen välillä. Näin voit parantaa sovelluksesi suorituskykyä.

Seuraava koodinpätkä kysyy komponentilta tietoja, jotka esimerkin tarkoituksia varten on jo sisällytetty tapahtuman kuormitukseen, edustaen tehotonta koodia:

```java
myComponent.addEventListener(e -> {
  // Pääsy tietoihin komponentista
  String componentText = e.getComponent().getText();

  //TAI jos komponentti on käytettävissä funktion laajuudessa
  String componentText = myComponent.getText();

  // Käytä componentTextiä suorittaaksesi muita toimintoja.
});
```

Sen sijaan hyödyntäen metodin kuormitusta, joka esimerkin vuoksi sisältää komponentin tekstin, vältetään ylimääräinen kysely:

```java
myComponent.addEventListener(e -> {
  // Pääsy tietoihin tapahtuman kuormituksesta
  String componentText = e.getText();
  
  // Käytä componentTextiä suorittaaksesi muita toimintoja.
});
```

Tämä lähestymistapa minimoi tarpeen kysyä komponentilta tietoja, sillä tiedot ovat heti saatavilla tapahtuman kuormituksessa. Noudattamalla tätä tehokasta tapahtumien käsittelykäytäntöä voit parantaa komponenttiesi suorituskykyä ja reagointikykyä. Lisätietoja varten voit viitata [Asiakas/Palvelin vuorovaikutukseen](../architecture/client-server).

### Esimerkki {#sample}

Alla on esittely, joka näyttää <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEventin</JavadocLink> lisäämisen [`Button`](#). Tämä [`Button`](#) käyttää myös tapahtuman kuormituksessa tulevia tietoja näyttääkseen tietoa näytöllä.

<ComponentDemo
path='/webforj/buttonevent'
files={['src/main/java/com/webforj/samples/views/button/ButtonEventView.java']}
height='100px'
/>
