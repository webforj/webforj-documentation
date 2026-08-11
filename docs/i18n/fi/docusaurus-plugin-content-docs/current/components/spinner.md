---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: bd35c3da6c5fc265d0bb249bbde86215
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Komponentti `Spinner` tarjoaa visuaalisen indikaattorin, joka osoittaa meneillään olevaa käsittelyä tai lataamista taustalla. Sitä käytetään usein näyttämään, että järjestelmä hakee tietoja tai kun prosessi vie aikaa valmistuakseen. `Spinner` tarjoaa käyttäjäpalautetta, viestien, että järjestelmä toimii aktiivisesti.

<!-- INTRO_END -->

## Perusteet {#basics}

Voit luoda `Spinnerin` määrittämällä teeman ja laajuuden. Perussyntaksi sisältää `Spinner`-instanssin luomisen ja sen ulkonäön ja käyttäytymisen määrittämisen menetelmiä kuten `setTheme()` ja `setExpanse()` käyttäen.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Nopeuden hallinta ja keskeyttäminen {#managing-speed-and-pausing}

On mahdollista asettaa nopeus millisekunteina `Spinnerille` ja keskeyttää/jatkaa animaatiota helposti.

Käyttötapauksia nopeuden asettamiselle on muun muassa latausprosessien erottaminen toisistaan. Esimerkiksi nopeammat `Spinnerit` sopivat pienille tehtäville, kun taas hitaammat `Spinnerit` ovat parempia suuremmille tehtäville. Keskeyttäminen on hyödyllistä, kun käyttäjätoimintaa tai vahvistusta vaaditaan ennen prosessin jatkamista.

### Nopeuden säätäminen {#adjusting-speed}

Voit hallita kuinka nopeasti `Spinner` pyörii säätämällä sen nopeutta millisekunteina käyttäen `setSpeed()`-menetelmää. Alhaisempi arvo saa `Spinnerin` pyörimään nopeammin, kun taas korkeammat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletuksena `Spinner` vie 1000 millisekuntia yhden täyden pyörähdyksen suorittamiseen.
:::

### Keskeyttäminen ja jatkaminen {#pausing-and-resuming}

`Spinnerin` keskeyttäminen on hyödyllistä, kun ohjelma on väliaikaisesti keskeytetty tai odottaa käyttäjän syötettä. Se antaa käyttäjille tietoa siitä, että ohjelma on tauolla, eikä toimi aktiivisesti, mikä parantaa selkeyttä monivaiheisissa prosesseissa.

Keskeyttääksesi ja jatkaaksesi Spinneriä, käytä `setPaused()`-menetelmää. Tämä on erityisen hyödyllistä, kun tarvitset väliaikaisesti pysäyttää pyörivän animaation.

```java
spinner.setPaused(true);  // Keskeyttää spinnerin
spinner.setPaused(false); // Jatkaa spinneriä
```

Tässä esimerkissä näytetään, kuinka asettaa nopeus ja kuinka keskeyttää/jatkaa `Spinneriä`:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinnerin` suuntaa voidaan hallita pyörimään **myötäpäivään** tai **vastapäivään**. Voit määrittää tämän käyttäytymisen käyttämällä `setClockwise()`-menetelmää.

```java
spinner.setClockwise(false);  // Pyörii vastapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto viestii visuaalisesti erityisestä tilasta tai toimii ainutlaatuisena suunnitteluratkaisuna. Pyörimissuunnan muuttaminen voi auttaa erottamaan prosessityyppejä, kuten edistyminen vs. kumoaminen, tai antaa erityisiä visuaalisia vihjeitä tietyissä konteksteissa.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Tyylittely {#styling}

### Teemat {#themes}

`Spinner`-komponentti tulee useiden esiasetettujen teemojen kanssa, jotka antavat sinun käyttää tyylejä nopeasti ilman, että sinun tarvitsee muokata CSS:ää. Nämä teemat muuttavat spinnereiden visuaalista ilmettä, tehden niistä sopivia eri käyttötarkoituksiin ja konteksteihin. Näiden ennalta määritettyjen teemojen käyttäminen varmistaa yhdenmukaisuuden tyylittelyssä koko sovelluksessasi.

Vaikka spinnereillä on erilaisia sovelluksia, tässä on joitakin esimerkkejä eri teemojen käyttötapauksista:

- **Ensisijainen**: Ihanteellinen korostamaan lataustilaa, joka on keskeinen osa käyttäjäprosessia, kuten lomakkeen lähettämisen tai tärkeän toiminnan käsittelyn aikana.

- **Onnistuminen**: Hyödyllinen edustamaan onnistuneita taustaprosesseja, kuten silloin, kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin loppuvaiheita.

- **Vaara**: Käytä tätä riskialttiiden tai suurten operaatioiden, kuten tärkeiden tietojen poistamisen tai peruuttamattomien muutosten tekemisen, yhteydessä, jossa visuaalinen indikaattori kiireellisyydestä tai varovaisuudesta on tarpeen.

- **Varoitus**: Käytä tätä osoittamaan varovaisuuteen tai vähemmän kiireelliseen prosessiin, kuten silloin, kun käyttäjä odottaa tietojen validoimista, mutta ei vaadi välitöntä toimintaa.

- **Harmaa**: Sopii hyvin hienovaraisiin taustaprosesseihin, kuten matalan prioriteetin tai passiivisiin lataustehtäviin, kuten täydentävien tietojen hakemiseen, joka ei suoraan vaikuta käyttäjäkokemukseen.

- **Tieto**: Sopii lataustilanteisiin, joissa tarjoat käyttäjälle lisätietoja tai selvennyksiä, esimerkiksi näyttämällä spinnerin viestin rinnalla, joka selittää meneillään olevaa prosessia.

Voit soveltaa näitä teemoja ohjelmallisesti spinnerille, tarjoten visuaalisia vihjeitä, jotka vastaavat toimintojen kontekstia ja tärkeyttä.

Voit määrittää tämän käyttäytymisen käyttämällä `setTheme()`-menetelmää.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Laajuudet {#expanses}

Voit säätää spinnerin kokoa, jota kutsutaan **laajuudeksi**, sopimaan tarvittavaan visuaaliseen tilaan. Spinneri tukee erilaisia kokoja, mukaan lukien `Expanse.SMALL`, `Expanse.MEDIUM` ja `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
