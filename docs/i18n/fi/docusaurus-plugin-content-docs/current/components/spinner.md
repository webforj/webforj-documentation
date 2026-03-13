---
title: Spinner
sidebar_position: 110
_i18n_hash: c60e7d3c3604a39de7f659f169d973a6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner`-komponentti tarjoaa visuaalisen indikaattorin, joka osoittaa käynnissä olevaa prosessia tai ladattavaa taustalla. Sitä käytetään usein osoittamaan, että järjestelmä noutaa tietoja tai kun prosessi vie aikaa valmistua. `Spinner` antaa käyttäjälle palautetta, mikä merkitsee, että järjestelmä työskentelee aktiivisesti.

<!-- INTRO_END -->

## Perusteet {#basics}

Luodaksesi `Spinner`, voit määrittää teeman ja koon. Perussyntaksi sisältää `Spinner`-instanssin luomisen ja sen ulkonäön ja käyttäytymisen määrittämisen menetelmien, kuten `setTheme()` ja `setExpanse()`, avulla.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Nopeuden ja tauon hallinta {#managing-speed-and-pausing}

On mahdollista asettaa nopeus millisekunteina `Spinner`-komponentille sekä keskeyttää/aloittaa animointi helposti. 

Käyttötapauksia nopeuden asettamiseen ovat esimerkiksi latausprosessien erottaminen. Esimerkiksi nopeammat `Spinners` soveltuvat pienille tehtäville, kun taas hitaammat `Spinners` ovat parempia suuremmille tehtäville. Taukoaminen on hyödyllistä, kun käyttäjän toiminta tai vahvistus on tarpeen prosessin jatkamiseksi.

### Nopeuden säätäminen {#adjusting-speed}

Voit säädellä, kuinka nopeasti `Spinner` pyörii säätämällä sen nopeutta millisekunneissa käyttäen `setSpeed()`-menetelmää. Alhaisempi arvo saa `Spinner`-komponentin pyörimään nopeammin, kun taas korkeammat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletuksena `Spinner` vie 1000 millisekuntia yhden täydellisen kierroksen suorittamiseen.
:::

### Taukoaminen ja jatkaminen {#pausing-and-resuming}

`Spinner`-komponentin taukoaminen on hyödyllistä, kun ohjelma on tilapäisesti keskeytetty tai odottaa käyttäjän syötettä. Se ilmoittaa käyttäjille, että ohjelma on pidätetty, eikä aktiivisesti toimi, mikä parantaa selkeyttä monivaiheisissa prosesseissa.

Keskeyttääksesi ja jatkaaksesi `Spinner`-komponenttia, käytä `setPaused()`-menetelmää. Tämä on erityisen hyödyllistä, kun tarvitset tilapäisesti pysäyttää pyörimisanimoinnin.      

```java
spinner.setPaused(true);  // Keskeytä spinner
spinner.setPaused(false); // Jatka spinner
```

Tämä esimerkki näyttää kuinka asettaa nopeus ja kuinka keskeyttää/jatkaa `Spinneriä`:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinner`-komponentin suuntaa voidaan ohjata pyörimään **myötäpäivään** tai **vastapäivään**. Voit määrittää tämän käyttäytymisen `setClockwise()`-menetelmällä.

```java
spinner.setClockwise(false);  // Pyörii vastapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto osoittaa visuaalisesti erityistä tilaa tai toimii ainutlaatuisena muotoiluvaihtoehtona. Pyörimissuunnan muuttaminen voi auttaa erottamaan prosessityyppejä, kuten edistymisen ja kääntämisen, tai tarjota selkeän visuaalisen vihjeen tietyissä konteksteissa.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Muotoilu {#styling}

### Teemat {#themes}

`Spinner`-komponentti sisältää useita sisäänrakennettuja teemoja, jotka mahdollistavat tyylien nopean soveltamisen ilman räätälöityä CSS:ää. Nämä teemat muuttavat spinnin visuaalista ilmettä, jolloin se on sopiva eri käyttötarkoituksiin ja konteksteihin. Näiden ennalta määrättyjen teemojen käyttäminen varmistaa johdonmukaisuuden tyylissä sovelluksesi koko laajuudessa.

Vaikka spinnereitä käytetään erilaisissa tilanteissa, tässä on joitakin esimerkkikäyttötilanteita eri teemoille:

- **Pääteema**: Ihanteellinen korostamaan lataustilaa, joka on keskeinen osa käyttäjäprosessia, kuten lomakkeen lähettämisen tai tärkeän toiminnan käsittelyn aikana.
  
- **Onnistuminen**: Käytännöllinen esittämään onnistuneita taustaprosesseja, kuten kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin viimeisiä vaiheita.
  
- **Vaara**: Käytä tätä riskialttiissa tai tärkeissä operaatioissa, kuten tärkeän datan poistamisessa tai peruuttamattomien muutosten tekemisessä, joissa visuaalinen kiireellisyyden tai varovaisuuden indikaattori on tarpeen.
  
- **Varoitus**: Käytä tätä varoittamaan varovaisesta tai vähemmän kiireellisestä prosessista, kuten kun käyttäjä odottaa tietojen vahvistamista, mutta ei vaadi välitöntä toimintaa.

- **Harmaa**: Toimii hyvin hienovaraisille taustaprosesseille, kuten vähäprioriteettisille tai passiivisille lataustehtäville, kuten lisätietojen hakemiselle, joka ei suoraan vaikuta käyttäjäkokemukseen.
  
- **Tietoa**: Sopii lataustilanteisiin, joissa tarjoat käyttäjälle lisätietoa tai selvennystä, kuten näyttää spinnin yhdessä viestin kanssa, joka selittää meneillään olevaa prosessia.

Voit soveltaa näitä teemoja ohjelmallisesti spinnereille, tarjoten visuaalisia vihjeitä, jotka vastaavat toiminnan kontekstia ja tärkeyttä.

Voit määrittää tämän käyttäytymisen käyttäen `setTheme()`-menetelmää.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Koot {#expanses}

Voit säätää spinnin kokoa, jota kutsutaan **kooksi**, jotta se sopisi tarvittavaan visuaaliseen tilaan. Spinner tukee useita eri kokoja, mukaan lukien `Expanse.SMALL`, `Expanse.MEDIUM` ja `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
