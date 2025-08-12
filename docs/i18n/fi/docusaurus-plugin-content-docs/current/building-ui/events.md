---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: 35a5057106e5fe7f270cbadaff74b924
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponentit, olivatpa ne mukautettuja tai osia kehyksestä, tukevat tapahtumien käsittelyä. Voit lisätä tapahtumakuuntelijoita erilaisten tapahtumien, kuten käyttäjäinteraktioiden, tilan muutosten tai muiden mukautettujen tapahtumien, kaappaamiseksi. Näitä tapahtumakuuntelijoita voidaan käyttää erityisten toimien tai käyttäytymisten laukaisevana vastauksena tapahtumiin.

Alla olevassa esimerkissä tapahtuma lisätään kolmella tuetulla menetelmällä: lambda-lausekkeilla, anonyymeillä luokilla ja metodiviittauksilla.
## Tapahtumien lisääminen {#adding-events}

Tapahtumakuuntelijan lisääminen on mahdollista käyttää yhtä seuraavista malleista, joissa:

- **`myComponent`** on komponentti, johon haluat liittää tapahtumakuuntelijan.

- **`addEventListener`** on korvattu tapahtumaan liittyvällä menetelmällä.

- **`EventListener`** on korvattu kuunneltavan tapahtuman tyypillä.

```java
myComponent.addEventListener(e -> {
  // Suoritetaan, kun tapahtuma laukaisee
});

// TAI

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    // Suoritetaan, kun tapahtuma laukaisee
  }
});

// TAI

myComponent.addEventListener(this::eventMethod);
```

Lisäsynnin koodimetodeja tai alias-nimet on lisätty vaihtoehtoista tapahtumien lisäämistä varten käyttäen `on`-etuliitettä yhdessä tapahtuman kanssa, kuten:

```java
myComponent.onEvent(e -> {
  // Suoritetaan, kun tapahtuma laukaisee
});
```

## Tapahtuman poistaminen {#removing-an-event}

Kun lisäät tapahtumakuuntelijan, palautetaan `ListenerRegistration`-objekti. Tämä voi olla käytettävissä muiden asioiden ohella poistamaan tapahtuman myöhemmin.

```java
// Tapahtuman lisääminen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
        // Suoritetaan, kun tapahtuma laukaisee
    });

// Tapahtuman poistaminen
listenerRegistration.remove();
```

## Tapahtuman palkin käyttäminen {#using-event-payload}

On tärkeää huomata, että tapahtumat tulevat usein palkin kanssa, joka sisältää lisätietoja liittyen tapahtumaan. Voit tehokkaasti hyödyntää tätä palkkia tapahtumankäsittelijässä päästäksesi käsiksi relevanteihin tietoihin ilman tarpeettomia pyörähdyksiä asiakkaan ja palvelimen välillä. Näin voit parantaa sovelluksesi suorituskykyä.

Seuraava koodipätkä kysyy komponentilta tietoja, jotka meidän esimerkkimme mukaisesti on jo sisällytetty tapahtuman palkkiin, mikä edustaa tehotonta koodia:

```java
myComponent.addEventListener(e -> {
  // Pääsy komponentin tietoihin
  String componentText = e.getComponent().getText();

  // TAI jos komponentti on käytettävissä funktion laajuudessa
  String componentText = myComponent.getText();

  // Käytä componentTextiä suorittaaksesi muita toimia.
});
```

Sen sijaan käyttämällä metodin palkkia, joka esimerkin vuoksi sisältää komponentin tekstin, vältytään pyörähdykseltä:

```java
myComponent.addEventListener(e -> {
  // Pääsy tapahtuman palkin tietoihin
  String componentText = e.getText();
  
  // Käytä componentTextiä suorittaaksesi muita toimia.
});
```

Tämä lähestymistapa minimoi tarpeen kysyä komponentilta tietoja, sillä tiedot ovat heti saatavilla tapahtuman palkissa. Noudattamalla tätä tehokasta tapahtumankäsittelykäytäntöä voit parantaa komponenttiesi suorituskykyä ja reagointinopeutta. Lisätietoja saat [Asiakas/Palvelin vuorovaikutus](../architecture/client-server).

### Esimerkki {#sample}

Alla on esittely, joka näyttää <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent" code="true">ButtonClickEvent</JavadocLink> lisäämisen [`Button`](#). Tämä [`Button`](#) käyttää myös tapahtuman palkin mukana tuomaa tietoa näyttääkseen tietoja näytöllä.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>

<!-- <EventTable base events={['drawerOpen', 'drawerClose']} /> -->
