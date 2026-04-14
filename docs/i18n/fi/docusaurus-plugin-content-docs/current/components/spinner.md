---
title: Spinner
sidebar_position: 110
_i18n_hash: bb61c6f2d3cf7073ca2d1c6fc6e1c0c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner`-komponentti tarjoaa visuaalisen indikaattorin, joka osoittaa taustalla olevan prosessin tai lataamisen. Sitä käytetään usein näyttämään, että järjestelmä hakee tietoja tai kun prosessin viimeistely vie aikaa. `Spinner` tarjoaa käyttäjäpalautteen, merkitsemällä, että järjestelmä toimii aktiivisesti.

<!-- INTRO_END -->

## Perusteet {#basics}

Luoaksesi `Spinner`, voit määrittää teeman ja laajuuden. Perussyntaksi sisältää `Spinner`-instanssin luomisen ja sen ulkoasun sekä käyttäytymisen määrittämisen menetelmien, kuten `setTheme()` ja `setExpanse()`, kautta.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
height = '225px'
/>

## Nopeuden hallinta ja tauko {#managing-speed-and-pausing}

On mahdollista asettaa `Spinner`:n nopeus millisekunteina ja keskeyttää/jatkaa animaatiota helposti.

Käyttötapauksia nopeuden asettamiselle ovat erottaminen latausprosessien välillä. Esimerkiksi nopeammat `Spinnerit` sopivat pienille tehtäville, kun taas hitaammat `Spinnerit` ovat parempia suuremmille tehtäville. Taukoaminen on hyödyllistä, kun tarvitaan käyttäjän toimenpiteitä tai vahvistusta ennen prosessin jatkamista.

### Nopeuden säätäminen {#adjusting-speed}

Voit hallita kuinka nopeasti `Spinner` pyörii säätämällä sen nopeutta millisekunteina käyttäen `setSpeed()`-menetelmää. Alhaisempi arvo saa `Spinner`- komponentin pyörimään nopeammin, kun taas korkeammat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletuksena `Spinner` vie 1000 millisekuntia suorittaakseen yhden täydellisen kierroksen.
:::

### Taukoaminen ja jatkaminen {#pausing-and-resuming}

`Spinner`:n taukoaminen on hyödyllistä, kun ohjelma on tilapäisesti keskeytetty tai odottaa käyttäjän syötettä. Se ilmoittaa käyttäjille, että ohjelma on pysähdyksissä, eikä aktiivisesti suoritettuna, mikä parantaa selkeyttä monivaiheisissa prosesseissa.

Taukoamisen ja jatkamisen toteuttamiseksi käytä `setPaused()`-menetelmää. Tämä on erityisen hyödyllistä, kun on tarpeen tilapäisesti pysäyttää pyörivä animaatio.

```java
spinner.setPaused(true);  // Pysäytä spinneri
spinner.setPaused(false); // Jatka spinneriä
```

Tässä esimerkissä näytetään, kuinka asetetaan nopeus ja kuinka taukoaa/jatkaa `Spinner`:ä:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
height = '150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinner`:n suuntaa voidaan hallita, jotta se pyörii **myötäpäivään** tai **vastapäivään**. Voit määrittää tämän käyttäytymisen käyttäen `setClockwise()`-menetelmää.

```java
spinner.setClockwise(false);  // Pyörii vastapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto ilmaisee visuaalisesti erityistilan tai toimii ainutlaatuisena suunnitteluratkaisuna. Pyörimissuunnan muuttaminen voi auttaa erottamaan erilaisia prosesseja, kuten edistyminen vs. kääntäminen tai tarjoamaan erottuvan visuaalisen vihjeen tietyissä konteksteissa.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Tyylit {#styling}

### Teemat {#themes}

`Spinner`-komponentilla on useita sisäänrakennettuja teemoja, jotka mahdollistavat tyylin nopean soveltamisen ilman tarvetta mukautetulle CSS:lle. Nämä teemat muuttavat spinnerin visuaalista ulkoasua, tehden siitä sopivan erilaisiin käyttötapoihin ja konteksteihin. Näiden ennalta määriteltyjen teemojen käyttö varmistaa tyylin johdonmukaisuuden koko sovelluksessa.

Vaikka spinnerit palvelevat erilaisia tilanteita, tässä on joitakin esimerkkejä eri teemojen käyttötapauksista:

- **Pääteema**: Ihanteellinen korostamaan lataustilaa, joka on keskeinen osa käyttäjävirtaa, kuten lomakkeen lähettämisessä tai tärkeän toimenpiteen käsittelyssä.
  
- **Onnistuminen**: Hyödyllinen edustamaan onnistuneita taustaprosesseja, kuten silloin, kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin viimeiset vaiheet.
  
- **Vaara**: Käytä tätä riskialttiissa tai korkeiden panosten toiminnoissa, kuten tärkeiden tietojen poistamisessa tai peruuttamattomien muutosten tekemisessä, missä visuaalinen hälytys tai varoitus on tarpeen.
  
- **Varoitus**: Käytä tätä ilmoittaaksesi varoittavasta tai vähemmän kiireellisestä prosessista, kuten silloin, kun käyttäjä odottaa tietojen vahvistusta, mutta ei vaadi välitöntä toimintaa.

- **Harmaa**: Toimii hyvin hienovaraisissa taustaprosesseissa, kuten alhaisen prioriteetin tai passiivisten lataustehtävien, kuten lisätietojen hakemisen, yhteydessä, mikä ei suoraan vaikuta käyttäjäkokemukseen.
  
- **Info**: Sopii lataustilanteisiin, jolloin tarjoat käyttäjälle lisätietoja tai selvennystä, kuten spinnerin näyttäminen viestin rinnalla, joka selittää käynnissä olevaa prosessia.

Voit soveltaa näitä teemoja ohjelmallisesti spinneriin, tarjoten visuaalisia vihjeitä, jotka vastaavat toiminnan kontekstia ja tärkeyttä.

Voit määrittää tämän käyttäytymisen käyttäen `setTheme()`-menetelmää.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
height = '100px'
/>

### Laajuudet {#expanses}

Voit säätää spinnerin kokoa, joka tunnetaan nimellä **laajuus**, sopimaan tarvitsemaasi visuaaliseen tilaan. Spinner tukee erilaisia kokoja, mukaan lukien `Expanse.SMALL`, `Expanse.MEDIUM` ja `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
height = '100px'
/>

<TableBuilder name="Spinner" />
