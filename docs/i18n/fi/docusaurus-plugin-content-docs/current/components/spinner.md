---
title: Spinner
sidebar_position: 110
description: >-
  Indicate background activity with the Spinner component, configuring theme,
  expanse, rotation speed, and pause or resume.
_i18n_hash: 6a667297edb572a6990b58bee72c8b1b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner`-komponentti tarjoaa visuaalisen indikaattorin, joka osoittaa taustalla käynnissä olevaa prosessia tai latausta. Sitä käytetään usein näyttämään, että järjestelmä hakemassa tietoja tai kun prosessi vie aikaa valmistuakseen. `Spinner` tarjoaa käyttäjäpalautetta, ilmoittaen, että järjestelmä toimii aktiivisesti.

<!-- INTRO_END -->

Luo `Spinner`-instanssi, sitten määritä sen ulkoasu ja käyttäytyminen menetelmien kuten `setTheme()` ja `setExpanse()` avulla.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Nopeuden ja pysäyttämisen hallinta {#managing-speed-and-pausing}

On mahdollista asettaa nopeus millisekunteina `Spinnerille` ja keskeyttää/ottaa animaatio uudelleen käyttöön helposti.

Käyttötapauksia nopeuden asettamiselle ovat erilaisten latausprosessien erottaminen. Esimerkiksi nopeammat `Spinners` sopivat pienempiin tehtäviin, kun taas hitaammat `Spinners` ovat parempia suuremmissa tehtävissä. Pysäyttäminen on hyödyllistä, kun käyttäjän toimintaa tai vahvistusta vaaditaan ennen prosessin jatkamista.

### Nopeuden säätäminen {#adjusting-speed}

Voit hallita, kuinka nopeasti `Spinner` pyörii säätämällä sen nopeutta millisekunteina käyttäen `setSpeed()`-metodia. Alhaisempi arvo saa `Spinnerin` pyörimään nopeammin, kun taas korkeammat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletuksena `Spinner` vie 1000 millisekuntia yhden täyden kierroksen suorittamiseen.
:::

### Pysäyttäminen ja jatkaminen {#pausing-and-resuming}

`Spinnerin` pysäyttäminen on hyödyllistä, kun ohjelma on tilapäisesti pysähtynyt tai odottaa käyttäjän syöttöä. Se ilmoittaa käyttäjille, että ohjelma on tauolla eikä aktiivisesti käynnissä, mikä lisää selkeyttä monivaiheisissa prosesseissa.

Pysäyttämiseen ja jatkamiseen `Spinnerissä` käytetään `setPaused()`-metodia. Tämä on erityisen hyödyllistä, kun sinun on tilapäisesti pysäytettävä pyörivä animaatio.

```java
spinner.setPaused(true);  // Pysäyttää spinnerin
spinner.setPaused(false); // Jatkaa spinneriä
```

Tässä esimerkissä näytetään, kuinka asettaa nopeus ja kuinka pysäyttää/jatkaa `Spinner`:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinnerin` suuntaa voidaan hallita pyörimään **myötäpäivään** tai **vastapäivään**. Voit määrittää tämän käyttäytymisen käyttämällä `setClockwise()`-metodia.

```java
spinner.setClockwise(false);  // Pyörii vastapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto viestittää visuaalisesti erityisestä tilasta tai toimii ainutlaatuisena suunnitteluratkaisuna. Pyörimissuunnan muuttaminen voi auttaa erottamaan prosessityyppejä, kuten edistymisen ja käänteisen, tai antaa erottuvan visuaalisen vihjeen tietyissä konteksteissa.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Tyylittely {#styling}

### Teemat {#themes}

`Spinner`-komponentti tulee useiden valmiiksi määriteltyjen teemojen kera, jotka mahdollistavat tyylien nopean soveltamisen ilman tarvetta mukautetulle CSS:lle. Nämä teemat muuttavat spinnerin visuaalista ulkoasua, mikä tekee siitä sopivan erilaisiin käyttötilanteisiin ja konteksteihin. Näiden esimääritettyjen teemojen käyttäminen varmistaa tyylin johdonmukaisuuden sovelluksessasi.

Vaikka spinnerit palvelevat erilaisia tilanteita, tässä on joitain esimerkkejä erilaisten teemojen käyttötapauksista:

- **Pääteema**: Ihanteellinen korostamaan lataustilaa, joka on tärkeä osa käyttäjän kulkua, esimerkiksi lomakkeen lähettämisen tai tärkeän toiminnan käsittelyn aikana.

- **Onnistuminen**: Hyödyllinen edustamaan onnistuneita taustaprosesseja, kuten kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin viimeisiä vaiheita.

- **Vaarallinen**: Käytä tätä riskialttiissa tai korkeasti arvokkaissa operaatioissa, kuten tärkeiden tietojen poistamisessa tai peruuttamattomien muutosten tekemisessä, joissa visuaalinen indikaattori kiireellisyydestä tai varovaisuudesta on tarpeen.

- **Varoitus**: Käytä tätä varoittamaan varovaisesta tai vähemmän kiireellisestä prosessista, kuten kun käyttäjä odottaa tietojen vahvistamista, mutta ei vaadi välitöntä toimintaa.

- **Harmaa**: Toimii hyvin hienovaraisissa taustaprosesseissa, kuten alhaisen prioriteetin tai passiivisten lataustehtävien, kuten lisätietojen hakemisen, yhteydessä, mikä ei suoraan vaikuta käyttäjäkokemukseen.

- **Tietoa**: Sopii lataustilanteisiin, joissa tarjoat käyttäjälle lisätietoa tai selvennystä, kuten näyttämällä spinnerin viestin rinnalla, joka selittää käynnissä olevaa prosessia.

Voit soveltaa näitä teemoja ohjelmallisesti spinnerille, tarjoamalla visuaalisia vihjeitä, jotka vastaavat toiminnan kontekstia ja tärkeyttä.

Voit määrittää tämän käyttäytymisen käyttämällä `setTheme()`-metodia.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Laajuudet {#expanses}

Voit säätää spinnerin kokoa, jota kutsutaan **laajuudeksi**, käyttämällä `setExpanse()`-metodia `SpinnerExpanse`-enumerin kanssa. Saatavilla on yhdeksän kokoa: `XXXSMALL`, `XXSMALL`, `XSMALL`, `SMALL`, `MEDIUM`, `LARGE`, `XLARGE`, `XXLARGE` ja `XXXLARGE`. Oletusarvo on `SpinnerExpanse.NONE`.

```java
spinner.setExpanse(SpinnerExpanse.LARGE);
```

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
