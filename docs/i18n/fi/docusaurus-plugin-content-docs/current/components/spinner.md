---
title: Spinner
sidebar_position: 110
_i18n_hash: 8ab95efdcfcc1e42df56c372da27cc81
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

`Spinner`-komponentti tarjoaa visuaalisen indikaattorin, joka osoittaa taustalla käynnissä olevan prosessin tai latauksen. Sitä käytetään usein näyttämään, että järjestelmä hakee tietoja tai kun prosessi vie aikaa valmistuakseen. Spinner tarjoaa käyttäjille palautetta, viestien, että järjestelmä on aktiivisesti töissä.

## Perusteet {#basics}

`Spinner`:in luomiseen voit määrittää teeman ja laajuuden. Perus syntaksi sisältää `Spinner`-instanssin luomisen ja sen ulkonäön ja käyttäytymisen määrittämisen menetelmien, kuten `setTheme()` ja `setExpanse()`, avulla.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Nopeuden ja tauon hallinta {#managing-speed-and-pausing}

Spinnerin nopeutta voidaan asettaa millisekunneissa, ja animaation pysäyttäminen/käynnistäminen on helppoa.

Käytön tapauksia nopeuden asettamiselle ovat erilaisten latausprosessien erottaminen. Esimerkiksi nopeammat `Spinnerit` sopivat pienempiin tehtäviin, kun taas hitaammat `Spinnerit` ovat parempia suuremmille tehtäville. Tauko on hyödyllinen silloin, kun käyttäjän toiminta tai vahvistus on tarpeen ennen prosessin jatkamista.

### Nopeuden säätäminen {#adjusting-speed}

Voit hallita, kuinka nopeasti `Spinner` pyörii säätämällä sen nopeutta millisekunneissa `setSpeed()`-menetelmällä. Alhaisempi arvo saa `Spinner`:in pyörimään nopeammin, kun taas korkeammat arvot hidastavat sitä.

```java
spinner.setSpeed(500); // Pyörii nopeammin
```

:::info Oletusnopeus
Oletuksena `Spinner` vie 1000 millisekuntia yhden täyden kierroksen suorittamiseen.
:::

### Taukoaminen ja jatkaminen {#pausing-and-resuming}

`Spinner`:in pysäyttäminen on hyödyllistä, kun ohjelma on tilapäisesti pysähtynyt tai odottaa käyttäjän syöttöä. Se kertoo käyttäjille, että ohjelma on tauolla, eikä aktiivisesti käynnissä, mikä parantaa selkeyttä monivaiheisissa prosesseissa.

Taukoamisen ja jatkamisen osalta käytetään `setPaused()`-menetelmää. Tämä on erityisen hyödyllistä, kun haluat tilapäisesti pysäyttää pyörivän animaation.

```java
spinner.setPaused(true);  // Pysäytä spinner
spinner.setPaused(false); // Jatka spinneriä
```

Tämä esimerkki näyttää, miten asettaa nopeus ja miten tauottaa/jatkaa `Spinner`-komponenttia:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Pyörimissuunta {#spin-direction}

`Spinner`:in suuntaa voidaan säätää pyörimään **myötäpäivään** tai **vastaapäivään**. Voit määrittää tämän käyttäytymisen `setClockwise()`-menetelmän avulla.

```java
spinner.setClockwise(false);  // Pyörii vastaapäivään
spinner.setClockwise(true);   // Pyörii myötäpäivään
```

Tämä vaihtoehto voi visuaalisesti osoittaa erityistä tilaa tai toimia ainutlaatuisena suunnitteluvaihtoehtona. Pyörimissuunnan muuttaminen voi auttaa erottamaan prosessityyppejä, kuten edistymisen ja käänteisen, tai antaa erityisen visuaalisen vihjeen tietyissä konteksteissa.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Tyylit {#styling}

### Teemat {#themes}

`Spinner`-komponentti tulee useiden sisäänrakennettujen teemojen kanssa, jotka mahdollistavat tyyliin soveltamisen ilman tarpeettomia mukautettuja CSS:ää. Nämä teemat muuttavat spinnerin visuaalista ilmenemistä, mikä tekee siitä sopivan eri käytännöille ja konteksteille. Näiden ennalta määrättyjen teemojen käyttäminen takaa johdonmukaisuuden tyylissä koko sovelluksessa.

Vaikka spinnerit palvelevat erilaisia tilanteita, tässä on joitakin esimerkkitapauksia eri teemoille:

- **Ensisijainen**: Ihanteellinen korostamaan lataustilaa, joka on keskeinen osa käyttäjäkulkuja, kuten lomakkeen lähettämisen tai tärkeän toimenpiteen käsittelyn aikana.
  
- **Onnistuminen**: Käytetään kuvaamaan onnistuneita taustaprosesseja, kuten kun käyttäjä lähettää lomakkeen ja sovellus suorittaa prosessin viimeiset vaiheet.
  
- **Vaara**: Käytä tätä riskialttiille tai suurten panosten toiminnoille, kuten tärkeiden tietojen poistamiselle tai peruuttamattomien muutosten tekemiselle, joissa visuaalinen kiireen tai varovaisuuden indikaattori on tarpeen.
  
- **Varoitus**: Käytä tätä varoittavien tai vähemmän kiireellisten prosessien merkitsemiseen, kuten silloin, kun käyttäjä odottaa tietojen vahvistusta, mutta ei vaadi välitöntä toimintaa.

- **Harmaa**: Sopii hyvin hienovaraisille taustaprosesseille, kuten matalan prioriteetin tai passiivisten lataustehtävien, kuten lisätietojen hakemisen, jotka eivät vaikuta suoraan käyttäjäkokemukseen.
  
- **Tietoa**: Sopii latausskenaarioihin, joissa tarjoat lisätietoa tai selvennystä käyttäjälle, kuten näyttäessäsi spinneriä viestin rinnalla, joka selittää käynnissä olevan prosessin.

Voit soveltaa näitä teemoja ohjelmallisesti spinneriin, tarjoten visuaalisia vihjeitä, jotka ovat sopusoinnussa toiminnan kontekstin ja tärkeyden kanssa.

Voit määrittää tämän käyttäytymisen käyttämällä `setTheme()`-menetelmää.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Laajuudet {#expanses}

Voit säätää spinnerin kokoa, jota kutsutaan **laajuudeksi**, sopimaan tarvitsemaasi visuaaliseen tilaan. Spinner tukee erilaisia kokoja, kuten `Expanse.SMALL`, `Expanse.MEDIUM` ja `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
