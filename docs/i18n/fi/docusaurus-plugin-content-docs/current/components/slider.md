---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 88cace5ce1650eaaf33dfc4535125dc0
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`-komponentti tarjoaa käyttäjille tavan valita numeerinen arvo vetämällä nuppia radalla minimi- ja maksimiarvojen välillä. Askelväliä, tikkumerkkejä ja etikettejä voidaan konfiguroida ohjaamaan valintaa.

<!-- INTRO_END -->

Uusi `Slider` kattaa alueen 0-100, ja sen lähtöarvo on 50, joten se toimii ilman mitään asetuksia. Ominaisuudet, kuten suunta, tikkumerkit, etiketit ja työkaluvihjeet käsittelevät tarkempia tapauksia, kuten alla olevaa äänenvoimakkuuden säätöä.

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider`-arvo {#slider-value}

`Slider`-arvo edustaa nuppia nykyistä sijaintia sliderissa ja se määritellään kokonaislukuna `Slider`-arvojen alueella. Tämä arvo päivittyy dynaamisesti käyttäjän vuorovaikutuksen aikana, mikä tekee siitä olennaisen ominaisuuden käyttäjän syötteen seuraamiseen.

:::tip Oletusarvo
Oletuksena `Slider` alkaa arvosta 50, olettaen oletusarvoisen alueen 0-100.
:::

### Arvon asettaminen ja saaminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-arvon alustusvaiheessa tai päivittää sen myöhemmin käyttämällä `setValue()`-metodia. Jotta saisit nykyisen arvon, käytä `getValue()`-metodia.

```java
Slider slider = new Slider();
slider.setValue(25); // Asettaa sliderin arvoon 25

Integer value = slider.getValue();
System.out.println("Nykyinen Slider-arvo: " + value);
```

## Minimiyksiköt ja maksimiarvot {#minimum-and-maximum-values}

Minimi- ja maksimiarvot määrittelevät sallitun alueen `Slider`-yhteydessä, jolloin määritellään rajat, joiden sisällä `Slider`-nuppi voi liikkua. Oletuksena alue on asetettu 0-100, mutta voit mukauttaa nämä arvot tarpeidesi mukaan.

`Slider`-askelväli on oletuksena 1, mikä tarkoittaa, että välin määrä määräytyy alueen mukaan. Esimerkiksi:
- Slider, jonka alue on 0-10, sisältää 10 väliä.
- Slider, jonka alue on 0-100, sisältää 100 väliä.

Nämä väli on tasaisesti jaettu slittiradalla, ja niiden väli riippuu `Slider`-mitoista.

Alla on esimerkki `Slider`-komponentista, jossa on mukautettu alue:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Tikkukonfiguraatio {#tick-configuration}

`Slider`-komponentti tarjoaa joustavan tikkukonfiguraation, joka mahdollistaa tikkumerkkien näyttämisen ja sen, miten slider-nuppi vuorovaikuttaa niihin. Tämä sisältää suurten ja pienten tikkujen väliasettelun säätämisen, tikkujen näyttämisen/piilottamisen ja tikkujen kiinnittämisen mahdollistamisen tarkkaa käyttäjäinputia varten.

### Suurten ja pienten tikkujen väli {#major-and-minor-tick-spacing}

Voit määrittää suuret ja pienet tikkumerkit, mikä määrää kuinka usein ne esiintyvät `Slider`-radalla:

- Suuret tikut ovat suurempia ja usein merkittyjä edustamaan avainarvoja.
- Pienet tikut ovat pienempiä ja näkyvät suurten tikkujen väliin tarjoamaan hienompia välejä.

Aseta tikkujen väli käyttäen seuraavia `setMajorTickSpacing()` ja `setMinorTickSpacing()` -metodeja:
```java
slider.setMajorTickSpacing(10); // Suuret tikut joka 10. yksikössä
slider.setMinorTickSpacing(2);  // Pienet tikut joka 2. yksikössä
```

### Tikkujen näyttäminen tai piilottaminen {#show-or-hide-ticks}

Voit kytkeä tikkujen näkyvyyden päälle tai pois käyttämällä `setTicksVisible()`-metodia. Oletuksena tikut ovat piilossa.

```java
slider.setTicksVisible(true); // Näytä tikut
slider.setTicksVisible(false); // Piilota tikut
```

### Kiinnittäminen {#snapping}

Varmistaaksesi, että `Slider`-nuppi kohdistuu lähimpään tikkumerkkiin käyttäjän vuorovaikutuksen aikana, mahdollista kiinnitys käyttämällä `setSnapToTicks()`-metodia:

```java
slider.setSnapToTicks(true); // Ota kiinnitys käyttöön
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää suurten ja pienten tikkuasetusten lisäksi kiinnitysominaisuuden tarkkoja säätöjä varten:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Suunta ja kääntäminen {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaakasuuntaista (oletus) ja pystysuuntaista. Voit vaihtaa suuntaa vastaamaan käyttöliittymäsi asettelu- ja sovellusvaatimuksia.

Suuntaamisen lisäksi `Slider` voidaan myös kääntää. Oletuksena:

- Vaakasuuntainen `Slider` kulkee minimistä (vasen) maksimiksi (oikea).
- Pystysuuntainen `Slider` kulkee minimistä (alempi) maksimiksi (ylempi).

Käännettäessä tämä suunta muuttuu. Ota kääntäminen käyttöön käyttämällä `setInverted(true)`-metodia.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Etiketit {#labels}

`Slider`-komponentti tukee etikettejä tikkujen kohdalla, jotta käyttäjät voivat tulkita arvoja helpommin. Voit käyttää oletusnumerollisia etikettejä tai tarjota mukautettuja, ja voit kytkeä niiden näkyvyyden tarpeen mukaan.

### Oletusetiketit {#default-labels}

Oletuksena slider voi näyttää numeerisia etikettejä suurilla tikkumerkeillä. Nämä arvot määrätään `setMajorTickSpacing()`-asetuksen mukaan. Ota oletusetiketit käyttöön seuraavasti:

```java
slider.setLabelsVisible(true);
```

### Mukautetut etiketit {#custom-labels}

Voit korvata oletusnumerolliset etiketti mukautetuilla teksteillä käyttämällä `setLabels()`-metodia. Tämä on hyödyllistä, kun haluat näyttää merkityksellisempiä arvoja (esim. lämpötila, valuutta tai kategoriat).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Kylmä",
  30, "Viileä",
  50, "Kohtalainen",
  80, "Lämmin",
  100, "Kuuma"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Etiketin näkyvyyden kytkeminen {#toggling-label-visibility}

Olitpa käyttämässä oletus- tai mukautettuja etikettejä, voit hallita niiden näkyvyyttä käyttämällä `setLabelsVisible(true)` tai piilottaa ne `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Työkaluvihjeet {#tooltips}

Työkaluvihjeet parantavat käytettävyyttä näyttämällä `Slider`-arvon suoraan nuppia ylle tai alle, auttaen käyttäjiä tekemään tarkempia säätöjä. Voit määrittää työkaluvihjeen käyttäytymistä, näkyvyyttä ja muotoa tarpeidesi mukaan.

Ota työkaluvihjeet käyttöön käyttämällä `setTooltipVisible()`-metodia. Oletuksena työkaluvihjeet ovat pois päältä:

```java
slider.setTooltipVisible(true); // Ota työkaluvihjeet käyttöön
slider.setTooltipVisible(false); // Poista työkaluvihjeet käytöstä
```

Työkaluvihjeitä voidaan myös määrittää näkymään vain, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Käytä `setTooltipVisibleOnSlideOnly()`-metodia ottaaksesi tämän käyttäytymisen käyttöön. Tämä on erityisen hyödyllistä visuaalisten häiriöiden vähentämiseen samalla, kun se tarjoaa hyödyllistä palautetta vuorovaikutuksen aikana.

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, jossa on työkaluvihjeet:


### Työkaluvihjeen mukauttaminen {#tooltip-customization}

Oletuksena `Slider` näyttää työkaluvihjeen nykyisellä arvolla. Jos haluat mukauttaa tätä tekstiä, käytä `setTooltipText()`-metodia. Tämä on hyödyllistä, kun haluat, että työkaluvihje näyttää staattista tai kuvailevaa tekstiä sen sijaan, että se näyttäisi reaaliaikaisen arvon.

Voit myös käyttää JavaScript-lauseketta työkaluvihjeen muotoiluun dynaamisesti. Jos lauseke sisältää `return`-avaimen, sitä käytetään sellaisenaan. Jos ei, se kehystetään automaattisesti `return`- ja `;`-merkeillä muodostaakseen kelvollisen funktion. Esimerkiksi:

```java
// Näyttää arvon, jota seuraa dollari
slider.setTooltipText("return x + '$'");
```

Tai yksinkertaisesti:

```java
// Tulkitse: return x + ' yksikköä';
slider.setTooltipText("x + ' yksikköä'");
```

## Tyylitys {#styling}

### Teemat {#themes}

`Slider` tarjoaa kuusi valmista teemaa nopeaa tyylittelyä varten ilman CSS:n käyttöä. Teemaa tuetaan sisäänrakennetun enum-luokan avulla.
Alla on esimerkkejä slidereista, joissa jokin tuetuista teemoista on käytössä:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
