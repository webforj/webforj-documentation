---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 22812c9195f148410b746c3547a0f118
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner`-komponentti tarjoaa visuaalisen indikaattorin, joka osoittaa käynnissä olevaa prosessia tai latausta taustalla. Sitä käytetään usein osoittamaan, että järjestelmä hakee tietoja tai kun prosessi vie aikaa valmistuakseen. `Spinner` tarjoaa käyttäjäpalautetta, merkitsemällä, että järjestelmä toimii aktiivisesti.

<!-- INTRO_END -->

Luo `Spinner`-instanssi ja määritä sen ulkonäkö ja käyttäytyminen metodeilla, kuten `setTheme()` ja `setExpanse()`.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Nopeuden ja tauon hallinta {#managing-speed-and-pausing}

On mahdollista asettaa nopeus millisekunteina `Spinner`-komponentille ja keskeyttää/jatkaa animaatiota helposti.

Käyttötapojasi nopeuden säätämiselle on erilaisten latausprosessien erottaminen. Esimerkiksi nopeammat `Spinnerit` sopivat pienempiin tehtäviin, kun taas hitaammat `Spinnerit` ovat parempia suurempiin tehtäviin. Taukoaminen on hyödyllistä, kun käyttäjältä tarvitaan toiminto tai vahvistus ennen prosessin jatkamista.

### Nopeuden säätäminen {#adjusting-speed}

Voit säätää `Spinner`-komponentin pyörimisnopeutta muuttamalla sen nopeutta millisekunteina `setSpeed()`-metodin avulla. Alhaisempi arvo tekee `Spinneristä` pyörivämmän, kun taas korkeammat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletuksena `Spinner` vie 1000 millisekuntia yhden täydellisen pyörimisen suorittamiseen.
:::

### Keskeyttäminen ja jatkaminen {#pausing-and-resuming}

`Spinnerin` keskeyttäminen on hyödyllistä, kun ohjelma on väliaikaisesti pysähtynyt tai odottaa käyttäjän syöttöä. Se kertoo käyttäjille, että ohjelma on tauolla eikä aktiivisesti toimi, mikä parantaa selkeyttä monivaiheisissa prosesseissa.

Keskeyttämiseksi ja jatkamiseksi käytä `setPaused()`-metodia. Tämä on erityisen hyödyllistä, kun sinun on väliaikaisesti pysäytettävä pyörivä animaatio.

```java
spinner.setPaused(true);  // Keskeytä spinner
spinner.setPaused(false); // Jatka spinner
```

Tässä esimerkissä näytetään, miten nopeus asetetaan ja miten `Spinner` keskeytetään/aloitetaan uudelleen:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinnerin` suuntaa voidaan hallita pyörimään **myötäpäivään** tai **vastapäivään**. Voit määrittää tämän käyttäytymisen `setClockwise()`-metodilla.

```java
spinner.setClockwise(false);  // Pyörii vastapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto ilmoittaa visuaalisesti erityisestä tilasta tai toimii ainutlaatuisena suunnittelun valintana. Pyörimissuunnan muuttaminen voi auttaa erottamaan prosessityypit, kuten edistys tai käänne, tai antaa erottuvan visuaalisen vihjeen tietyissä konteksteissa.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Tyylittely {#styling}

### Teemat {#themes}

`Spinner`-komponentti sisältää useita sisäänrakennettuja teemoja, jotka mahdollistavat tyylien nopean soveltamisen ilman tarvetta mukautetulle CSS:lle. Nämä teemat muuttavat spinnauksen visuaalista ulkonäköä, tehden siitä sopivan erilaisiin käyttötarkoituksiin ja konteksteihin. Näiden ennalta määriteltyjen teemojen käyttäminen varmistaa tyylin johdonmukaisuuden koko sovelluksessa.

Vaikka spinnereitä käytetään erilaisiin tilanteisiin, tässä on joitakin esimerkkikäyttötapauksia eri teemoille:

- **Pääteema**: Ihanteellinen korostamaan lataustilaa, joka on keskeinen osa käyttäjän polkua, kuten lomakkeen lähettämisen tai tärkeän toiminnon käsittelyn aikana.

- **Onnistuminen**: Hyödyllinen edustamaan onnistuneita taustaprosesseja, kuten silloin, kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin viimeisiä vaiheita.

- **Vaara**: Käytä tätä riskialttiisiin tai korkean panoksen operaatioihin, kuten tärkeiden tietojen poistamiseen tai palautumattomien muutosten tekemiseen, joissa tarvitaan visuaalista kiireellisyyden tai varoituksen indikaattoria.

- **Varoitus**: Käytä tätä varoittavaan tai vähemmän kiireelliseen prosessiin, kuten silloin, kun käyttäjä odottaa tietojen vahvistusta, mutta ei vaadi välitöntä toimintaa.

- **Harmaa**: Toimii hyvin hienovaraisissa taustaprosesseissa, kuten vähäprioriteettisissa tai passiivisissa lataustehtävissä, kuten lisätietojen hakemisessa, jotka eivät suoraan vaikuta käyttäjäkokemukseen.

- **Info**: Sopii lataustilanteisiin, joissa tarjoat käyttäjälle lisätietoa tai selvennystä, kuten näyttäessäsi spinnauksen viestin, joka selittää meneillään olevaa prosessia.

Voit soveltaa näitä teemoja ohjelmallisesti spinnereihin, tarjoten visuaalisia vihjeitä, jotka vastaavat toiminnan kontekstia ja merkitystä.

Voit määrittää tämän käyttäytymisen `setTheme()`-metodilla.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Laajuudet {#expanses}

Voit säätää spinnauksen kokoa, joka tunnetaan nimellä **laajuus**, sopimaan tarvittavaan visuaaliseen tilaan. Spinner tukee erilaisia kokoja, mukaan lukien `Expanse.SMALL`, `Expanse.MEDIUM` ja `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
