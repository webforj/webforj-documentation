---
title: Spinner
sidebar_position: 110
_i18n_hash: d93d5704fff2acc975910f1a10e34d0b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner`-komponentti tarjoaa visuaalisen indikaattorin, joka osoittaa taustalla tapahtuvaa prosessointia tai latausta. Sitä käytetään usein näyttämään, että järjestelmä hakee tietoja tai kun prosessi vie aikaa. `Spinner` tarjoaa käyttäjälle palautetta, mikä signaloi, että järjestelmä työskentelee aktiivisesti.

<!-- INTRO_END -->

## Perusteet {#basics}

Luoaksesi `Spinner`, voit määrittää teeman ja laajuuden. Perussyntaksi sisältää `Spinner`-instanssin luomisen ja sen ulkoasun ja käyttäytymisen määrittämisen metodien, kuten `setTheme()` ja `setExpanse()`, avulla.

<ComponentDemo
path='/webforj/spinnerdemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java']}
height='225px'
/>

## Nopeuden hallinta ja keskeyttäminen {#managing-speed-and-pausing}

On mahdollista määrittää nopeus millisekunteina `Spinner`-komponentille ja keskeyttää/jatkaa animaatiota helposti.

Käyttötapauksia nopeuden määrittämiselle ovat esimerkiksi latausprosessien erottaminen. Esimerkiksi nopeammat `Spinners` sopivat pienille tehtäville, kun taas hitaammat `Spinners` ovat parempia suuremmille tehtäville. Keskeyttäminen on hyödyllistä, kun käyttäjän toimintaa tai vahvistusta vaaditaan ennen prosessin jatkamista.

### Nopea säätäminen {#adjusting-speed}

Voit hallita, kuinka nopeasti `Spinner` pyörii säätämällä sen nopeutta millisekunteina `setSpeed()`-metodin avulla. Pienempi arvo saa `Spinner`-komponentin pyörimään nopeammin, kun taas suuremmat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletuksena `Spinner` vie 1000 millisekuntia yhden täydellisen kierroksen suorittamiseen.
:::

### Keskeyttäminen ja jatkaminen {#pausing-and-resuming}

`Spinner`-komponentin keskeyttäminen on hyödyllistä, kun ohjelma on tilapäisesti pysähdyksissä tai odottaa käyttäjän syötettä. Se antaa käyttäjille tietää, että ohjelma on pysähdyksissä eikä aktiivisesti käynnissä, mikä parantaa selkeyttä monivaiheisissa prosesseissa.

Keskeyttämiseen ja jatkamiseen käytetään `setPaused()`-metodia. Tämä on erityisen hyödyllistä, kun sinun on tilapäisesti pysäytettävä pyörivä animaatio.

```java
spinner.setPaused(true);  // Keskeyttää spinnerin
spinner.setPaused(false); // Jatkaa spinnerin pyörimistä
```

Tämä esimerkki näyttää, kuinka nopeus asetetaan ja kuinka `Spinner` voidaan keskeyttää/jatkaa:

<ComponentDemo
path='/webforj/spinnerspeeddemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java']}
height='150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinner`-komponentin suuntaa voidaan hallita, jotta se pyörii **myötäpäivään** tai **vastapäivään**. Voit määrittää tämän käyttäytymisen `setClockwise()`-metodin avulla.

```java
spinner.setClockwise(false);  // Pyörii vastapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto osoittaa visuaalisesti erityistä tilaa tai toimii ainutlaatuisena muotoilupäätöksenä. Pyörimissuunnan muuttaminen voi auttaa erottamaan prosessityyppejä, kuten edistyminen vs. peruutus, tai antaa erottuvan visuaalisen vihjeen tietyissä konteksteissa.

<ComponentDemo
path='/webforj/spinnerdirectiondemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java']}
height='150px'
/>

## Tyylittely {#styling}

### Teemat {#themes}

`Spinner`-komponentti sisältää useita sisäänrakennettuja teemoja, jotka mahdollistavat tyylien nopean soveltamisen ilman mukautettua CSS:ää. Nämä teemat muuttavat spinnereiden visuaalista ulkoasua, mikä tekee niistä sopivia erilaisiin käyttötilanteisiin ja -yhteyksiin. Näiden ennalta määriteltyjen teemojen käyttö varmistaa tyylin yhdenmukaisuuden koko sovelluksessa.

Vaikka spinnereitä käytetään erilaisissa tilanteissa, tässä on esimerkkejä eri teemojen käyttötilanteista:

- **Pääteema**: Ihanteellinen korostamaan lataustilaa, joka on keskeinen osa käyttäjäprosessia, kuten lomakkeen lähettämisen tai tärkeän toiminnan käsittelyn aikana.
  
- **Onnistuminen**: Käytetään kuvaamaan onnistuneita taustaprosesseja, kuten kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin viimeiset vaiheet.
  
- **Vaarallinen**: Käytä tätä riskialttiille tai korkeisiin panoksiin liittyville toiminnoille, kuten tärkeiden tietojen poistamiselle tai peruuttamattomille muutoksille, joissa on tarpeen osoittaa kiireellisyys tai varovaisuus.
  
- **Varoitus**: Käytä tätä varoitus- tai vähemmän kiireellisen prosessin ilmoittamiseen, kuten kun käyttäjä odottaa tietojen validoimista, mutta ei vaadi välitöntä toimintaa.

- **Harmaa**: Toimii hyvin hienovaraisille taustaprosesseille, kuten matalalle prioriteettitasolle tai passiivisille lataustehtäville, kuten lisätietojen hakemiselle, jotka eivät suoraan vaikuta käyttäjäkokemukseen.
  
- **Tieto**: Sopii lataustilanteisiin, joissa tarjoat lisätietoja tai selvennyksiä käyttäjälle, kuten näyttämällä spinneri viestin rinnalla, joka selittää meneillään olevaa prosessia.

Voit soveltaa näitä teemoja ohjelmallisesti spinnereihin, tarjoten visuaalisia vihjeitä, jotka vastaavat toiminnan kontekstia ja tärkeyttä.

Voit määrittää tämän käyttäytymisen `setTheme()`-metodin avulla.

<ComponentDemo
path='/webforj/spinnerthemedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java']}
height='100px'
/>

### Laajuudet {#expanses}

Voit säätää spinnerin kokoa, jota kutsutaan **laajuudeksi**, vastaamaan tarvittavaa visuaalista tilaa. Spinner tukee erilaisia kokoja, mukaan lukien `Expanse.SMALL`, `Expanse.MEDIUM` ja `Expanse.LARGE`.

<ComponentDemo
path='/webforj/spinnerexpansedemo'
files={['src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java']}
height='100px'
/>

<TableBuilder name="Spinner" />
