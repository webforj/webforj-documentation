---
title: Slider
sidebar_position: 101
_i18n_hash: 490cb925a92ffd4860f74b00491402e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider` -komponentti antaa käyttäjille mahdollisuuden valita numeerinen arvo vetämällä nuppia raidan yli, joka on määritelty minimirajan ja maksimirajan välillä. Askelväli, viivamerkit ja etiketit voidaan määrittää ohjaamaan valintaa.

<!-- INTRO_END -->

## Perusteet {#basics}

`Slider` on suunniteltu toimimaan suoraan laatikosta ilman lisäasetuksia, jotta se toimii tehokkaasti. Oletusarvoisesti se kattaa alueen 0-100, ja sen alkuarvo on 50, mikä tekee siitä ihanteellisen nopeaan integroimiseen mihin tahansa sovellukseen. Tarkempia käyttötarkoituksia varten `Slider`-komponenttia voidaan mukauttaa ominaisuuksilla, kuten suunta, viivamerkit, etiketit ja työkaluvinkit.

Tässä on esimerkki `Slider`-komponentista, joka mahdollistaa käyttäjien säätää äänenvoimakkuutta ennalta määritellyn alueen sisällä:

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` arvo {#slider-value}

`Slider`-arvo edustaa nuppien nykyistä sijaintia sliderilla ja se on määritelty kokonaislukuna `Slider`-komponentin alueella. Tämä arvo päivittyy dynaamisesti, kun käyttäjä vuorovaikuttaa sliderin kanssa, mikä tekee siitä olennaisen ominaisuuden käyttäjän syötteen seuraamiseksi.

:::tip Oletusarvoinen arvo
Oletusarvoisesti `Slider` aloittaa arvolla 50, olettaen oletusalueen 0-100.
:::

### Arvon asettaminen ja saaminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-arvon alkuperäisen asennuksen aikana tai päivittää sen myöhemmin käyttäen `setValue()`-metodia. Nykyisen arvon hakemiseksi käytä `getValue()`-metodia.

```java
Slider slider = new Slider();  
slider.setValue(25); // Aseta slider 25:een

Integer value = slider.getValue();  
System.out.println("Nykyinen Slider Arvo: " + value);
```

## Minimimäärät ja maksimit {#minimum-and-maximum-values}

Minimi- ja maksimiarvot määrittelevät `Slider`-komponentin sallitun alueen, joka määrittää rajat, joiden sisällä `Slider`-nuppi voi liikkua. Oletusarvoisesti alue on asetettu 0-100, mutta voit mukauttaa näitä arvoja tarpeidesi mukaan.

`Slider`-komponentin askelväli on oletusarvoisesti 1, mikä tarkoittaa, että välin määrä määräytyy alueen mukaan. Esimerkiksi:
- `Slider`, jonka alue on 0-10, sisältää 10 väliä.
- `Slider`, jonka alue on 0-100, sisältää 100 väliä.

Nämä väliä on jaettu tasaisesti sliderin radalla, ja niiden väli määräytyy `Slider`-komponentin mittojen mukaan.

Alla on esimerkki `Slider`-komponentista, jonka alue on määritelty mukautetusti:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Viivamerkkien määrittäminen {#tick-configuration}

`Slider`-komponentti tarjoaa joustavan viivamerkkien määrittämisen, jonka avulla voit mukauttaa, miten viivamerkit näytetään ja miten slider-nuppi vuorovaikuttaa niiden kanssa. Tämä sisältää pää- ja alaviivamerkkien välin säätämisen, viivamerkkien näyttämisen/piilottamisen ja viivamerkkien mukaantuloan mahdollistamisen tarkkoja käyttäjäsyötteitä varten.

### Pää- ja alaviivamerkkien väli {#major-and-minor-tick-spacing}

Voit määrittää pää- ja alaviivamerkkien välin, mikä määrittää, kuinka usein ne näkyvät `Slider`-radalla:

- Pääviivamerkit ovat suurempia ja usein merkittyjä edustamaan avainarvoja.
- Alaviivamerkit ovat pienempiä ja näkyvät pääviivamerkkien välissä tarjoten hienompia välejä.

Määritä viivamerkkiväli käyttäen seuraavia `setMajorTickSpacing()` ja `setMinorTickSpacing()` metodeja:
```java
slider.setMajorTickSpacing(10); // Pääviivamerkit joka 10 yksikkö
slider.setMinorTickSpacing(2);  // Alaviivamerkit joka 2 yksikkö
```

### Näytä tai piilota viivamerkit {#show-or-hide-ticks}

Voit kytkeä viivamerkkien näkyvyyden päälle tai pois päältä käyttäen `setTicksVisible()`-metodia. Oletusarvoisesti viivamerkit ovat piilotettu.

```java
slider.setTicksVisible(true); // Näytä viivamerkit
slider.setTicksVisible(false); // Piilota viivamerkit
```

### Tarttuminen {#snapping}

Jotta `Slider`-nuppi olisi linjassa lähimmän viivamerkin kanssa käyttäjän vuorovaikutuksessa, ota käyttöön tarttuminen käyttäen `setSnapToTicks()`-metodia:

```java
slider.setSnapToTicks(true); // Ota käyttöön tarttuminen
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää pää- ja alaviivamerkkien asetukset sekä tarttumiskyvyn tarkkoja säätöjä varten:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Suunta ja kääntö {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaakasuora (oletus) ja pystysuora. Voit muuttaa suuntaa mukauttaaksesi sen käyttöliittymän asettelua ja sovelluksen vaatimuksia.

Lisäksi `Slider`-komponentti voidaan myös kääntää. Oletusarvoisesti:

- Vaakasuora `Slider` kulkee minimistä (vasen) maksimiin (oikea).
- Pystysuora `Slider` kulkee minimistä (alas) maksimiin (ylös).

Kun komponenteille on asetettu käännös, tämä suunta käännetään. Ota käyttöön kääntö käyttäen `setInverted(true)`-metodia.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Etiketit {#labels}

`Slider`-komponentti tukee etikettejä viivamerkeillä, jotta käyttäjät voivat tulkita arvot helpommin. Voit käyttää oletusarvoisia numeerisia etikettejä tai tarjota mukautettuja, ja voit kytkeä niiden näkyvyyden päälle tai pois tarpeen mukaan.

### Oletusarvoiset etiketit {#default-labels}

Oletusarvoisesti slider voi näyttää numeerisia etikettejä pääviivamerkeillä. Nämä arvot määräytyvät `setMajorTickSpacing()`-asetuksen mukaan. Ottaaksesi käyttöön oletusarvoiset etiketit, käytä:

```java
slider.setLabelsVisible(true);
```

### Mukautetut etiketit {#custom-labels}

Voit korvata oletusarvoiset numeeriset etiketit mukautetuilla teksteillä käyttäen `setLabels()`-metodia. Tämä on hyödyllistä, kun haluat näyttää merkityksellisempiä arvoja (esim. lämpötila, valuutta tai kategorioita).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Kylmä",
  30, "Viileä",
  50, "Kohtuullinen",
  80, "Lämmin",
  100, "Kuuma"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Etiketin näkyvyyden kytkeminen {#toggling-label-visibility}

Olitpa sitten käyttämässä oletusarvoisia tai mukautettuja etikettejä, voit hallita niiden näkyvyyttä `setLabelsVisible(true)`-metodilla tai piilottaa ne käyttäen `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Työkaluvinkit {#tooltips}

Työkaluvinkit parantavat käytettävyyttä näyttämällä `Slider`-arvon suoraan nuppien yläpuolella tai alapuolella, auttaen käyttäjiä tekemään tarkkoja säätöjä. Voit konfiguroida työkaluvinkkien käyttäytymistä, näkyvyyttä ja muotoa tarpeidesi mukaan.

Ottaaksesi työkaluvinkit käyttöön, käytä `setTooltipVisible()`-metodia. Oletusarvoisesti työkaluvinkit ovat pois päältä:

```java
slider.setTooltipVisible(true); // Ota käyttöön työkaluvinkit
slider.setTooltipVisible(false); // Poista työkaluvinkit käytöstä
```

Työkaluvinkkejä voidaan myös konfiguroida näkyviksi vain silloin, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Käytä `setTooltipVisibleOnSlideOnly()`-metodia ottaaksesi tämän käyttäytymisen käyttöön. Tämä on erityisen hyödyllistä visuaalisen hälyn vähentämiseksi samalla, kun tarjotaan hyödyllistä palautetta vuorovaikutuksen aikana.

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista työkaluvinkkien kanssa:

### Työkaluvinkkien mukauttaminen {#tooltip-customization}

Oletusarvoisesti `Slider` näyttää työkaluvinkin sen nykyisellä arvolla. Jos haluat mukauttaa tätä tekstiä, käytä `setTooltipText()`-metodia. Tämä on hyödyllistä, kun haluat, että työkaluvinkit näyttävät staattista tai kuvailevaa tekstiä elävän arvon sijaan.

Voit myös käyttää JavaScript-lauseketta työkaluvinkkien muotoilemiseksi dynaamisesti. Jos lausekkeesi sisältää avainsanan `return`, se käytetään sellaisenaan. Muuten se kääritään automaattisesti `return`- ja `;`-merkkien väliin muodostaen kelvollisen funktion. Esimerkiksi:

```java
// Näyttää arvon, jota seuraa dollarimerkki
slider.setTooltipText("return x + '$'"); 
```

Tai yksinkertaisesti:

```java
// Interpretoituu: return x + ' yksikköä'; 
slider.setTooltipText("x + ' yksikköä'"); 
```

## Muotoilu {#styling}

### Teemat {#themes}

`Slider`-komponentti tulee kuuden teeman kanssa valmiina nopeaa muotoilua varten ilman CSS:n käyttöä. Teeman tukeminen tapahtuu käyttöönottamalla valmiiksi rakennettu enum-luokka. Alla on esitelty sliders, joihin on sovellettu kutakin tuettua teemaa:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
