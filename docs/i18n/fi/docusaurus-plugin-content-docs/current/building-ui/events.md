---
sidebar_position: 10
title: Events
slug: events
draft: false
_i18n_hash: c5c07ac4ca0f8d88ea6ef86afd5bb408
---
<JavadocLink type="foundation" location="com/webforj/component/event/Event" top='true'/>

Komponentit, olivatpa ne mukautettuja tai osa kehystä, tukevat tapahtumien käsittelyä. Voit lisätä tapahtumakuuntelijoita eri tyyppisten tapahtumien, kuten käyttäjävuorovaikutusten, tilamuutosten tai muiden mukautettujen tapahtumien, sieppaamiseksi. Näitä tapahtumakuuntelijoita voidaan käyttää erityisten toimintojen tai käyttäytymisten käynnistämiseen vastauksena tapahtumiin.

Alla olevassa esimerkissä lisätään tapahtuma jokaisella kolmella tuetulla menetelmällä: lambda-lausekkeilla, nimettömillä luokilla ja menetelmäviittauksilla.
## Tapahtumien lisääminen {#adding-events}

Tapahtumakuuntelijan lisääminen on mahdollista käyttää yhtä seuraavista malleista, joissa:

- **`myComponent`** on komponentti, johon haluat liittää tapahtumakuuntelijan.

- **`addEventListener`** korvataan tapahtumasidonnaisella menetelmällä.

- **`EventListener`** korvataan tapahtumatyypillä, jota kuunnellaan.

```java
myComponent.addEventListener(e -> {
  // Suoritetaan, kun tapahtuma laukeaa
});

// TAi

myComponent.addEventListener(new ComponentEventListener<EventListener>() {
  @Override
  public void onComponentEvent(ComponentEvent e){
    // Suoritetaan, kun tapahtuma laukeaa
  }
});

// TAi

myComponent.addEventListener(this::eventMethod);
```

Lisäksi on lisätty syntaktisia makeisia tai alias-nimiä, jotka mahdollistavat vaihtoehtoisen tapaamisen tapahtumien lisäämiseksi käyttämällä `on`-etuliitettä, jota seuraa tapahtuma, kuten:

```java
myComponent.onEvent(e -> {
  // Suoritetaan, kun tapahtuma laukeaa
});
```

## Tapahtuman poistaminen {#removing-an-event}

Kun lisäät tapahtumakuuntelijan, `ListenerRegistration`-objekti palautuu. Tätä voidaan käyttää, muiden asioiden ohella, tapahtuman poistamiseen myöhemmin.

```java
// Tapahtuman lisääminen
ListenerRegistration listenerRegistration = myComponent.addEventListener(e -> {
    // Suoritetaan, kun tapahtuma laukeaa
  });

// Tapahtuman poistaminen
listenerRegistration.remove();
```

## Tapahtuman kuormituksen käyttö {#using-event-payload}

On tärkeää huomata, että tapahtumat tulevat usein kuormituksen kanssa, joka sisältää lisätietoja tapahtumasta. Voit käyttää tätä kuormitusta tehokkaasti tapahtumakäsittelijässä pääsyoikeuden saamiseksi asiaankuuluviin tietoon ilman tarpeettomia ylimääräisiä matkoja asiakkaan ja palvelimen välillä. Tällä tavoin voit parantaa sovelluksesi suorituskykyä.

Seuraavassa koodinpätkässä kysytään komponentilta tietoa, joka, meidän demonstraatiomme tarkoituksena, on jo mukana tapahtuman kuormituksessa, mikä edustaa tehotonta koodia:

```java
myComponent.addEventListener(e -> {
  // Pääsy komponentin tietoihin
  String componentText = e.getComponent().getText();

  // TAi jos komponentti on saatavilla funktion alueella
  String componentText = myComponent.getText();

  // Käytä componentTextiä muiden toimintojen suorittamiseen.
});
```

Sen sijaan, hyödyntäen metodin kuormitusta, joka esimerkin vuoksi sisältää komponentin tekstin, ylimääräinen matka vältetään:

```java
myComponent.addEventListener(e -> {
  // Pääsy tietoon tapahtuman kuormituksesta
  String componentText = e.getText();
  
  // Käytä componentTextiä muiden toimintojen suorittamiseen.
});
```

Tämä lähestymistapa minimoi tarpeen kysyä komponentilta tietoja, koska tiedot ovat heti käytettävissä tapahtuman kuormituksessa. Noudattamalla tätä tehokasta tapahtumakäsittelykäytäntöä voit parantaa komponenttisi suorituskykyä ja reagointikykyä. Lisätietoja varten voit viitata [Asiakas/Palvelin Vuorovaikutus](../architecture/client-server).

### Esimerkki {#sample}

Alla on demonstraatio, joka osoittaa <JavadocLink type="foundation" location="com/webforj/component/button/event/ButtonClickEvent"  code="true">ButtonClickEvent</JavadocLink>n lisäämistä [`Button`](#). Tämä [`Button`](#) käyttää myös tietoa, joka tulee tapahtuman kuormituksesta näyttääkseen tietoja näytöllä.

<ComponentDemo 
path='/webforj/buttonevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonEventView.java'
height='100px'
/>
