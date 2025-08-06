---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 3896ad417b6309ffbfbc46b2f893589b
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponentit, olivatpa ne mukautettuja tai osa kehystä, tukevat tapahtumien käsittelyä. Voit lisätä tapahtumakuuntelijoita eri tapahtumien, kuten käyttäjäinteraktioiden, tilamuutosten tai muiden mukautettujen tapahtumien, kaappaamiseen. Näitä tapahtumakuuntelijoita voidaan käyttää erityisten toimintojen tai käyttäytymisten käynnistämiseen tapahtumien myötä.

Alla olevassa esimerkissä tapahtuma lisätään kolmella tuetulla menetelmällä: lambda-lausekkeilla, nimettömillä luokilla ja metodiviittauksilla.
## Tapahtumien lisääminen {#adding-events}

Tapahtumakuuntelijan lisääminen on mahdollista käyttää yhtä seuraavista malleista, joissa:

- **`myComponent`** on komponentti, johon haluat liittää tapahtumakuuntelijan.

- **`addEventListener`** korvataan tapahtumaspesifisellä menetelmällä.

- **`EventListener`** korvataan kuunneltavan tapahtumatyyppillä.

```java
myComponent.addEventListener(e -> {
  //Suoritetaan, kun tapahtuma laukeaa
});

//TAI

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    //Suoritetaan, kun tapahtuma laukeaa
  }
});

//TAI

myComponent.addEventListener(this::eventMethod);
```

Lisäksi on lisätty syntaktisia sokerimenetelmiä tai aliaksia, jotka mahdollistavat tapahtumien vaihtoehtoisen lisäämisen `on`-etuliitteen avulla, jota seuraa tapahtuma, kuten:

```java
myComponent.onEvent(e -> {
  //Suoritetaan, kun tapahtuma laukeaa
});
```

## Tapahtuman poistaminen {#removing-an-event}

Kun lisätään tapahtumakuuntelija, `ListenerRegistration`-objekti palautetaan. Sitä voidaan käyttää muun muassa tapahtuman poistamiseen myöhemmin.

```java
//Tapahtuman lisääminen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        //Suoritetaan, kun tapahtuma laukeaa
    });

//Tapahtuman poistaminen
listenerRegistration.remove();
```

## Tapahtumakuorman käyttö {#using-event-payload}

On tärkeää huomioida, että tapahtumilla on usein kuorma, joka sisältää lisätietoja liittyen tapahtumaan. Voit tehokkaasti hyödyntää tätä kuormaa tapahtumakäsittelijässä päästäksesi relevantteihin tietoihin ilman tarpeettomia pyynnöitä asiakkaan ja palvelimen välillä. Tällä tavoin voit parantaa sovelluksesi suorituskykyä.

Seuraava koodinpätkä kysyy komponentilta tietoja, jotka meidän esimerkkitarkoituksiamme varten on jo sisällytetty tapahtumakuormaan, mikä edustaa tehotonta koodia:

```java
myComponent.addEventListener(e -> {
  // Pääsy komponentin tietoihin
  String componentText = e.getComponent().getText();

  //TAI jos komponentti on saatavilla funktion alueella
  String componentText = myComponent.getText();

  // Käytä componentTextiä muiden toimintojen suorittamiseen.
});
```

Sen sijaan, hyödyntämällä metodin kuormaa, joka esimerkin vuoksi sisältää komponentin tekstin, vältetään pyynnön tekeminen:

```java
myComponent.addEventListener(e -> {
  // Pääsy tapahtumakuorman tietoihin
  String componentText = e.getText();
  
  // Käytä componentTextiä muiden toimintojen suorittamiseen.
});
```

Tämä lähestymistapa minimoi tarpeen kysyä tietoja komponentilta, koska tiedot ovat välittömästi saatavilla tapahtumakuormassa. Noudattamalla tätä tehokasta tapahtumankäsittelykäytäntöä voit parantaa komponenttien suorituskykyä ja reagointikykyä. Lisätietoja varten voit viitata [Asiakaspalvelinvuorovaikutus](../architecture/client-server).

### Esimerkki {#sample}

Alla on esittely, joka osoittaa <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink>:n lisäämisen [`Button`](#)-komponenttiin. Tämä [`Button`](#) käyttää myös tapahtuman kuormasta tulevia tietoja näyttääkseen tietoja näytöllä.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->
