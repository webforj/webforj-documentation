---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 620635097d0620cc0cd4a639b0d29d97
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponentit, jotka ovat joko mukautettuja tai osa kehystä, tukevat tapahtumien käsittelyä. Voit lisätä tapahtumakuuntelijoita, jotka tallentavat erilaisia tapahtumia, kuten käyttäjän vuorovaikutuksia, tilan muutoksia tai muita mukautettuja tapahtumia. Näitä tapahtumakuuntelijoita voidaan käyttää erityisten toimintojen tai käyttäytymisten laukaisemiseen vastauksena tapahtumiin.

Alla olevassa esimerkissä tapahtuma lisätään käyttäen kolmea tuettua menetelmää: lambda-lausekkeita, nimettömiä luokkia ja menetelviittauksia.
## Tapahtumien lisääminen {#adding-events}

Tapahtumakuuntelijan lisääminen on mahdollista käyttää yhden seuraavista malleista, joissa:

- **`myComponent`** on komponentti, johon haluat liittää tapahtumakuuntelijan.

- **`addEventListener`** korvataan tapahtumakohtaisella menetelmällä.

- **`EventListener`** korvataan kuunneltavan tapahtuman tyypillä.

```java
myComponent.addEventListener(e -> {
  //Suoritetaan, kun tapahtuma käynnistyy
});

//TAI

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Suoritetaan, kun tapahtuma käynnistyy
  }
});

//TAI

myComponent.addEventListener(this::eventMethod);
```

Lisäksi on lisätty synnin sokeria tai alias-menetelmiä vaihtoehtoisia tapahtumien lisäämistä varten käyttämällä `on`-etuliitettä, jota seuraa tapahtuma, kuten:

```java
myComponent.onEvent(e -> {
  //Suoritetaan, kun tapahtuma käynnistyy
});
```

## Tapahtuman poistaminen {#removing-an-event}

Kun lisäät tapahtumakuuntelijan, palautetaan `ListenerRegistration`-objekti. Tätä voidaan käyttää muiden asioiden ohella tapahtuman poistamiseen myöhemmin.

```java
//Tapahtuman lisääminen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        //Suoritetaan, kun tapahtuma käynnistyy
    });

//Tapahtuman poistaminen
listenerRegistration.remove();
```

## Tapahtuman hyödyntäminen {#using-event-payload}

On tärkeää huomata, että tapahtumilla tulee usein mukana kuormitus, joka sisältää lisätietoja tapahtumasta. Voit tehokkaasti käyttää tätä kuormitusta tapahtumankäsittelijässä päästäksesi käsiksi relevantteihin tietoihin ilman tarpeettomia matkustuksia asiakas- ja palvelinpuolien välillä. Näin voit parantaa sovelluksesi suorituskykyä.

Seuraavassa koodiesimerkissä kysytään komponentilta tietoa, joka, esimerkkimme tarkoitusten vuoksi, on jo mukana tapahtumakuormituksessa, mikä edustaa tehottomaa koodia:

```java
myComponent.addEventListener(e -> {
  // Pääsy dataan komponentista
  String componentText = e.getComponent().getText();

  //TAI jos komponentti on käytettävissä funktion laajuudessa
  String componentText = myComponent.getText();

  // Käytä componentTextiä muiden toimintojen suorittamiseen.
});
```

Sen sijaan kuormituksen hyödyntäminen, joka esimerkin vuoksi sisältää komponentin tekstin, välttää matkustuksen:

```java
myComponent.addEventListener(e -> {
  // Pääsy dataan tapahtumakuormituksesta
  String componentText = e.getText();
  
  // Käytä componentTextiä muiden toimintojen suorittamiseen.
});
```

Tämä lähestymistapa minimoi tarpeen kysyä komponentilta tietoja, sillä tiedot ovat helposti saatavilla tapahtumakuormituksessa. Noudattamalla tätä tehokasta tapahtumankäsittelykäytäntöä voit parantaa komponenttisi suorituskykyä ja reagointikykyä. Lisätietoja saat [Asiakas/Palvelin vuorovaikutus](../architecture/client-server).

### Esimerkki {#sample}

Alla on esitys, joka näyttää <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink> lisäämisen [`Button`](#). Tämä [`Button`](#) käyttää myös tapahtuman kuormituksessa mukana tulevia tietoja näyttääkseen tietoa näytöllä.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
