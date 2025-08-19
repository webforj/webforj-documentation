---
title: Spinner
sidebar_position: 110
_i18n_hash: b1137c43133bce5c5a16df51c0aa82e3
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner`-komponentti tarjoaa visuaalisen indikaattorin, joka osoittaa käynnissä olevaa käsittelyä tai latausta taustalla. Sitä käytetään usein näyttämään, että järjestelmä hakee tietoja tai kun prosessi vie aikaa loppuun. Spinner antaa käyttäjille palautetta, signaloiden, että järjestelmä työskentelee aktiivisesti.

## Perusasiat {#basics}

`Spinner`-komponentin luomiseksi voit määrittää teeman ja koon. Perussyntaksi sisältää `Spinner`-instanssin luomisen ja sen ulkoasun sekä käytöksen määrittämisen metodeilla kuten `setTheme()` ja `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Nopeuden hallinta ja pysäyttäminen {#managing-speed-and-pausing}

On mahdollista asettaa nopeus millisekunteina `Spinner`-komponentille ja pysäyttää/jatkaa animaatiota vaivatta. 

Sovelluskohteet nopeuden säätämiseksi sisältävät latausprosessien erottamisen. Esimerkiksi nopeammat `Spinnerit` sopivat pienempiin tehtäviin, kun taas hitaammat `Spinnerit` ovat parempia suuremmille tehtäville. Pysäyttäminen on hyödyllistä, kun käyttäjän toimintaa tai vahvistusta tarvitaan ennen kuin prosessi jatkuu.

### Nopeuden säätäminen {#adjusting-speed}

Voit hallita, kuinka nopeasti `Spinner` pyörii säätämällä sen nopeutta millisekunteina `setSpeed()`-metodin avulla. Alhaisempi arvo tekee `Spinneristä` nopeamman, kun taas korkeammat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletusarvoisesti `Spinner` vie 1000 millisekuntia yhden täydellisen kierroksen suorittamiseen.
:::

### Pysäyttäminen ja jatkaminen {#pausing-and-resuming}

`Spinnerin` pysäyttäminen on hyödyllistä, kun ohjelma on tilapäisesti pysähtynyt tai odottaa käyttäjän syötettä. Se ilmoittaa käyttäjille, että ohjelma on tauolla, eikä aktiivisesti käynnissä, mikä parantaa selkeyttä monivaiheisissa prosesseissa.

Voit pysäyttää ja jatkaa Spinneriä käyttämällä `setPaused()`-metodia. Tämä on erityisen hyödyllistä, kun tarvitset tilapäisesti pysäyttää pyörivän animaation.

```java
spinner.setPaused(true);  // Pysäytä spinner
spinner.setPaused(false); // Jatka spinner
```

Tässä esimerkissä näytetään, kuinka nopeus asetetaan ja kuinka `Spinner` pysäytetään/jatketaan:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinnerin` suuntaa voidaan hallita pyörimään **myötäpäivään** tai **vastapäivään**. Voit määrittää tämän käyttäytymisen `setClockwise()`-metodin avulla.

```java
spinner.setClockwise(false);  // Pyörii vastapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto ilmoittaa visuaalisesti erityisestä tilasta tai toimii ainutlaatuisena muotoiluvaihtoehtona. Pyörimissuunnan muuttaminen voi auttaa erottamaan prosessityypit, kuten edistyminen vs. käänteinen, tai antaa erottuvan visuaalisen vihjeen tietyissä yhteyksissä.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Muotoilu {#styling}

### Teemat {#themes}

`Spinner`-komponentissa on useita sisäänrakennettuja teemoja, jotka mahdollistavat tyylin nopean soveltamisen ilman tarvetta muokata CSS:ää. Nämä teemat muuttavat spinnerin visuaalista ulkoasua, mikä tekee siitä sopivan erilaisiin käyttötarkoituksiin ja konteksteihin. Näiden ennalta määriteltyjen teemojen käyttö varmistaa tyylin johdonmukaisuuden sovelluksessasi.

Vaikka spinnerit palvelevat erilaisia tilanteita, tässä on joitakin esimerkkitapauksia eri teemoille:

- **Pääteema**: Ihanteellinen korostamaan lataustilaa, joka on keskeinen osa käyttäjän kulkua, kuten lomakkeen lähettämisen tai tärkeän toiminnon käsittelyn aikana.
  
- **Onnistuminen**: Hyödyllinen edustamaan onnistuneita taustaprosesseja, kuten silloin, kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin viimeisiä vaiheita.
  
- **Vaara**: Käytä tätä riskialttiille tai suureen panokseen liittyville toiminnoille, kuten tärkeiden tietojen poistamiselle tai peruuttamattomien muutosten tekemiselle, jolloin visuaalinen indikaattori kiireellisyydestä tai varovaisuudesta on tarpeen.
  
- **Varoitus**: Käytä tätä varovaisen tai vähemmän kiireellisen prosessin ilmoittamiseen, kuten kun käyttäjä odottaa tietojen validointia, mutta ei tarvitse välitöntä toimintaa.

- **Harmaa**: Toimii hyvin hienovaraisille taustaprosesseille, kuten matalan prioriteetin tai passiivisille lataustehtäville, kuten täydentävien tietojen hakemiseen, jotka eivät vaikuta suoraan käyttäjäkokemukseen.
  
- **Tieto**: Sopii lataustilanteisiin, joissa tarjoat lisätietoja tai selvennystä käyttäjälle, kuten näyttämällä spinnerin viestin yhteydessä, joka selittää käynnissä olevaa prosessia.

Voit soveltaa näitä teemoja ohjelmallisesti spinneriin, mikä antaa visuaalisia vihjeitä, jotka vastaavat toiminnan kontekstia ja tärkeyttä.

Voit määrittää tämän käyttäytymisen käyttämällä `setTheme()`-metodia.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Koot {#expanses}

Voit säätää spinnerin kokoa, jota kutsutaan **kooksi**, sopimaan visuaaliseen tilaan, jota tarvitset. Spinner tukee useita kokoja, mukaan lukien `Expanse.SMALL`, `Expanse.MEDIUM` ja `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
