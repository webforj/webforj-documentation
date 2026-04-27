---
title: Slider
sidebar_position: 101
_i18n_hash: 16e62c6e021597448e33a04ebfd6f201
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`-komponentti tarjoaa käyttäjille mahdollisuuden valita numeerinen arvo vetämällä nuppia radalla minimi- ja maksimirajan välillä. Askelväli, viivamarkkerit ja tarrat voidaan määrittää valinnan ohjaamiseksi.

<!-- INTRO_END -->

## Perusteet {#basics}

`Slider` on suunniteltu toimimaan suoraan laatikosta ilman lisäasetuksia. Oletuksena se kattaa alueen 0-100, ja lähtöarvo on 50, mikä tekee siitä ihanteellisen kiireelliseen integroimiseen mihin tahansa sovellukseen. Tarkempia käyttötapauksia varten `Slider`-komponenttia voidaan mukauttaa ominaisuuksilla, kuten suunta, viivamarkkerit, tarrat ja työkaluvinkit.

Tässä on esimerkki `Slider`-komponentista, joka mahdollistaa käyttäjien säätää äänenvoimakkuuden tasoja ennalta määritellyssä alueessa:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-arvo {#slider-value}

`Slider`-arvo edustaa nuppien nykyistä sijaintia liukusäätimessä ja se määritellään kokonaislukuna `Slider`-aluetta sisällä. Tämä arvo päivittyy dynaamisesti käyttäjän vuorovaikutuksen aikana liukusäätimen kanssa, mikä tekee siitä tärkeän ominaisuuden käyttäjän syötteen seuraamiseksi.

:::tip Oletusarvo
Oletuksena `Slider` alkaa arvolla 50, olettaen oletusalueen 0-100.
:::

### Arvon asettaminen ja saaminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-arvon alustamisen aikana tai päivittää sen myöhemmin `setValue()`-menetelmällä. Nykyisen arvon hakemiseen käytetään `getValue()`-menetelmää.

```java
Slider slider = new Slider();  
slider.setValue(25); // Asettaa liukusäätimen arvoksi 25

Integer value = slider.getValue();  
System.out.println("Nykyinen liukusäätimen arvo: " + value);
```

## Minimialueet ja maksimaalalueet {#minimum-and-maximum-values}

Minimi- ja maksimiarvot määrittävät `Slider`-säädin hyväksyttävän alueen, joka määrittää rajat, joissa liukusäätimen nuppi voi liikkua. Oletuksena alue asetetaan väliin 0-100, mutta voit mukauttaa nämä arvot tarpeidesi mukaan.

`Slider`-komponentin väli on oletusarvoisesti 1, mikä tarkoittaa, että väliin kuuluvien askelten määrä määräytyy alueen mukaan. Esimerkiksi:
- Liukusäädin, jonka alue on 0-10, sisältää 10 väliä.
- Liukusäädin, jonka alue on 0-100, sisältää 100 väliä.

Nämä väli on jaettu tasaisesti liukusäätimen radan varrella, ja niiden välinen etäisyys riippuu `Slider`-komponentin mitoista.

Alla on esimerkki `Slider`-komponentin luomisesta mukautetulla alueella:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Viivamarkkerin asetukset {#tick-configuration}

`Slider`-komponentti tarjoaa joustavat viivamarkkerin asetukset, jolloin voit mukauttaa sitä, kuinka viivamarkkerit esitetään ja miten liukusäätimen nuppi vuorovaikuttaa niiden kanssa. Tämä sisältää suurten ja pienten viivamarkkerien välin säätämisen, viivamarkkereiden näyttämisen/piilottamisen sekä napsautuksen aktivoimisen viivamarkkereille tarkkaa käyttäjän syötettä varten.

### Suurten ja pienten viivamarkkerien väli {#major-and-minor-tick-spacing}

Voit määrittää suurten ja pienten viivamarkkerien välit, mikä määrittää kuinka usein ne ilmestyvät `Slider`-radalle:

- Suuret viivamarkkerit ovat suurempia ja usein merkittyjen arvojen edustamiseksi.
- Pienet viivamarkkerit ovat pienempiä ja ilmestyvät suurten viivamarkkerien väliin tarjoten hienompia väliä.

Aseta viivamarkkerivälit käyttämällä seuraavia `setMajorTickSpacing()` ja `setMinorTickSpacing()` -menetelmiä:
```java
slider.setMajorTickSpacing(10); // Suuret viivamarkkerit 10 yksikön välein
slider.setMinorTickSpacing(2);  // Pienet viivamarkkerit 2 yksikön välein
```

### Viivamarkkereiden näyttäminen tai piilottaminen {#show-or-hide-ticks}

Voit kytkeä viivamarkkereiden näkyvyyden päälle tai pois päältä käyttämällä `setTicksVisible()` -menetelmää. Oletuksena viivamarkkerit ovat piilotettuja.

```java
slider.setTicksVisible(true); // Näytä viivamarkkerit
slider.setTicksVisible(false); // Piilota viivamarkkerit
```

### Napsahdus {#snapping}

Varmistaaksesi, että `Slider`-nuppi kohdistuu lähimpään viivamarkkeriin käyttäjän vuorovaikutuksen aikana, ota napsautus käyttöön käyttämällä `setSnapToTicks()` -menetelmää:

```java
slider.setSnapToTicks(true); // Ota napsautus käyttöön
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää suurten ja pienten viivamarkkerien asetukset sekä napsautusmahdollisuuden tarkkoja säätöjä varten:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Suunta ja kääntö {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaaka (oletus) ja pystysuora. Voit muuttaa suuntaa sovelluksesi käyttöliittymän ja vaatimusten mukaisesti.

Suuntauden lisäksi `Slider` voidaan myös kääntää. Oletuksena:

- Vaakasuora `Slider` kulkee minimiarvosta (vasen) maksimiarvoon (oikea).
- Pystysuora `Slider` kulkee minimiarvosta (alas) maksimiarvoon (ylös).

Kun se käännetään, tämä suunta muuttuu. Käytä menetelmää `setInverted(true)` ottaaksesi käännön käyttöön.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Tarrat {#labels}

`Slider`-komponentti tukee tarroja viivamarkkereilla auttaakseen käyttäjiä tulkitsemaan arvoja helpommin. Voit käyttää oletusarvoisia numeerisia tarroja tai antaa mukautettuja, ja voit kytkeä niiden näkyvyyden päälle tai pois päältä tarpeen mukaan.

### Oletustarrat {#default-labels}

Oletuksena liukusäätimessä voi näkyä numeerisia tarroja suurilla viivamarkkereilla. Nämä arvot määräytyvät asetuksen `setMajorTickSpacing()` mukaan. Ota oletustarrat käyttöön:

```java
slider.setLabelsVisible(true);
```

### Mukautetut tarrat {#custom-labels}

Voit korvata oletusarvoiset numeeriset tarrat mukautetulla tekstillä käyttämällä `setLabels()` -menetelmää. Tämä on hyödyllistä, kun haluat näyttää merkityksellisimpiä arvoja (esim. lämpötila, valuutta tai kategoriat).

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

### Tarran näkyvyyden kytkeminen {#toggling-label-visibility}

Olitpa käyttämässä oletustarroja tai mukautettuja, voit hallita niiden näkyvyyttä `setLabelsVisible(true)` -menetelmällä tai piilottaa ne käyttämällä `setLabelsVisible(false)` -menetelmää.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Työkaluvinkit {#tooltips}

Työkaluvinkit parantavat käytettävyyttä näyttämällä `Slider`-arvon suoraan nuppien ylä- tai alapuolella, mikä auttaa käyttäjiä tekemään tarkempia säätöjä. Voit määrittää työkaluvinkkien käyttäytymistä, näkyvyyttä ja formaattia tarpeidesi mukaan.

Ota työkaluvinkit käyttöön käyttämällä `setTooltipVisible()` -menetelmää. Oletuksena työkaluvinkit ovat pois käytöstä:

```java
slider.setTooltipVisible(true); // Ota työkaluvinkit käyttöön
slider.setTooltipVisible(false); // Poista työkaluvinkit käytöstä
```

Työkaluvinkit voidaan myös määrittää näkyväksi vain, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Käytä `setTooltipVisibleOnSlideOnly()` -menetelmää tämän käyttäytymisen mahdollistamiseksi. Tämä on erityisen hyödyllistä visuaalisen hälyn vähentämiseksi samalla kun tarjotaan hyödyllistä palautetta vuorovaikutuksessa.

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista työkaluvinkkien kanssa:

### Työkaluvinkkien mukauttaminen {#tooltip-customization}

Oletuksena `Slider` näyttää työkaluvinkin sen nykyisellä arvolla. Jos haluat mukauttaa tätä tekstiä, käytä `setTooltipText()` -menetelmää. Tämä on hyödyllistä, kun haluat työkaluvinkin näyttävän staattista tai kuvailevaa tekstiä elävän arvon sijasta.

Voit myös käyttää JavaScript-lauseketta työkaluvinkkien dynaamiseen muotoiluun. Jos lausekkeesi sisältää `return`-avaimen, sitä käytetään sellaisenaan. Jos ei, se kääritään automaattisesti `return`-sanan ja `;`-merkinnän ympärille muodostaaksesi kelvollisen funktion. Esimerkiksi:

```java
// Näyttää arvon, jonka jälkeen tulee dollari
slider.setTooltipText("return x + '$'"); 
```

Tai yksinkertaisesti:

```java
// Interpretoidaan: return x + ' yksikköä';
slider.setTooltipText("x + ' yksikköä'"); 
```

## Tyylittely {#styling}

### Teemat {#themes}

`Slider` tarjoaa 6 sisäänrakennettua teemaa nopeaa tyylittelyä varten ilman CSS:ää. Teemaa tuetaan sisäänrakennetun enum-luokan avulla.
Alla on liukusäätimiä jokaisella tuetulla teemalla:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
