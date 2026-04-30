---
title: Slider
sidebar_position: 101
_i18n_hash: 77c71bf27e728d68c1e3381628b37a27
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`-komponentti antaa käyttäjille mahdollisuuden valita numeerinen arvo vetämällä nuppia radan yli minimirajan ja maksimirajan välillä. Askelväliä, viivaimia ja etikettejä voidaan säätää valinnan ohjaamiseksi.

<!-- INTRO_END -->

## Perusasiat {#basics}

`Slider` on suunniteltu toimimaan suoraan pakettiin, eikä se tarvitse lisäasetuksia toimiakseen tehokkaasti. Oletusarvoisesti se kattaa arvot 0 - 100, ja sen lähtöarvo on 50, mikä tekee siitä ihanteellisen nopeaan integroimiseen mihin tahansa sovellukseen. Tarkempia käyttötapauksia varten `Slider`-komponenttia voidaan mukauttaa ominaisuuksilla, kuten suunta, viivaimet, etiketit ja työkaluvihjeet.

Tässä on esimerkki `Slider`-komponentista, joka mahdollistaa käyttäjien äänenvoimakkuuden säätämisen ennalta määritellyn alueen sisällä:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height='100px'
/>

## `Slider`-arvo {#slider-value}

`Slider`-arvo edustaa nuppiasentoa liukusäätimessä ja se määritellään kokonaislukuna `Slider`-alueen rajoissa. Tämä arvo päivittyy dynaamisesti käyttäjän vuorovaikutuksessa liukusäätimen kanssa, joten se on olennainen ominaisuus käyttäjäsyötteen seuraamiseksi.

:::tip Oletusarvoinen arvo
Oletusarvoisesti `Slider` alkaa arvosta 50, ottaen oletusalueeksi 0 - 100.
:::

### Arvon asettaminen ja hakeminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-arvon alustuksessa tai päivittää sen myöhemmin `setValue()`-menetelmällä. Nykyisen arvon hakemiseksi käytä `getValue()`-menetelmää.

```java
Slider slider = new Slider();  
slider.setValue(25); // Asettaa liukusen arvoksi 25

Integer value = slider.getValue();  
System.out.println("Nykyinen liukusäätimen arvo: " + value);
```

## Minimivirheet ja maksimivirheet {#minimum-and-maximum-values}

Minimi- ja maksimivärit määrittävät sallitun alueen `Slider`-komponentille, määräten rajoja, joiden sisällä `Slider`-nuppi voi liikkua. Oletusarvoisesti alue on asetettu 0 - 100, mutta voit mukauttaa näitä arvoja tarpeidesi mukaan.

Liukusäätimen väli on oletusarvoisesti askeleeltaan 1, mikä tarkoittaa, että väliin määräytyy alueen mukaan. Esimerkiksi:
- Liukusäätimellä, jonka alue on 0 - 10, on 10 väliä.
- Liukusäätimellä, jonka alue on 0 - 100, on 100 väliä.

Nämä väli on tasaisesti jaettu liukusäätimen radalla, ja niiden välinen etäisyys riippuu `Slider`-komponentin mitoista.

Alla on esimerkki `Slider`-komponentista, jossa on mukautettu alue:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height='200px'
/>

## Viivaimen asetukset {#tick-configuration}

`Slider`-komponentti tarjoaa joustavan viivaimen asetuksen, jonka avulla voit mukauttaa viivaimien näyttöä ja liukusäätimen nuppia niiden kanssa. Tämä sisältää pää- ja pienviivaimien välisten etäisyyksien säätämisen, viivaimien näyttämisen/piilottamisen ja nuppiasennon lukitsemisen viivaimille tarkkoja käyttäjäsyötteitä varten.

### Pää- ja pienviivaimien välinen tila {#major-and-minor-tick-spacing}

Voit määrittää pää- ja pienviivaimien väliä, mikä määrää kuinka usein ne ilmestyvät `Slider`-radalla:

- Pääviivaimet ovat suurempia ja usein merkittyjä, jotta ne edustavat avainarvoja.
- Pienviivaimet ovat pienempiä ja ilmestyvät pääviivaiden väliin tarjoten hienompia välejä.

Aseta viivaimien väli käyttäen seuraavia `setMajorTickSpacing()` ja `setMinorTickSpacing()` -menetelmiä:
```java
slider.setMajorTickSpacing(10); // Pääviivaimet joka 10. yksikkö
slider.setMinorTickSpacing(2);  // Pienviivaimet joka 2. yksikkö
```

### Näytä tai piilota viivaimet {#show-or-hide-ticks}

Voit kytkeä viivaimien näkyvyyden päälle tai pois `setTicksVisible()`-menetelmällä. Oletusarvoisesti viivaimet ovat piilossa.

```java
slider.setTicksVisible(true); // Näytä viivaimet
slider.setTicksVisible(false); // Piilota viivaimet
```

### Nupin lukitus {#snapping}

Jotta varmistetaan, että `Slider`-nuppi on linjassa lähimmän viivaimen kanssa käyttäjän vuorovaikutuksen aikana, mahdollista nupin lukitus käyttäen `setSnapToTicks()`-menetelmää:

```java
slider.setSnapToTicks(true); // Ota käyttöön lukitus
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää pää- ja pienviivaimia yhdessä lukitusmahdollisuuden kanssa tarkkoja säätöjä varten:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height='350px'
/>

## Suunta ja kääntö {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaakasuora (oletus) ja pystysuora. Voit vaihtaa suuntaa sovelluksesi käyttöliittymäasettelun ja vaatimusten mukaan.

Lisäksi `Slider`-komponentti voidaan myös kääntää. Oletusarvoisesti:

- Vaakasuora `Slider` menee minimistä (vasen) maksimiksi (oikea).
- Pystysuora `Slider` menee minimistä (alakautta) maksimiksi (yläpuolelle).

Kun se on käännetty, tämä suunta on käännetty. Käytä `setInverted(true)`-menetelmää kääntämisen mahdollistamiseksi.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height='440px'
/>

## Etiketit {#labels}

`Slider`-komponentti tukee etikettejä viivaimilla, jotta käyttäjät voivat tulkita arvoja helpommin. Voit käyttää oletusarvoisia numeerisia etikettejä tai tarjota mukautettuja, ja voit kytkeä niiden näkyvyyden päälle tai pois tarpeen mukaan.

### Oletusarvoiset etiketit {#default-labels}

Oletusarvoisesti liukusäätimellä voi olla numeerisia etikettejä pääviivaimilla. Nämä arvot määräytyvät `setMajorTickSpacing()`-asetuksen mukaan. Ota oletusarvoiset etiketit käyttöön seuraavasti:

```java
slider.setLabelsVisible(true);
```

### Mukautetut etiketit {#custom-labels}

Voit korvata oletusarvoiset numeeriset etikettit mukautetuilla teksteillä `setLabels()`-menetelmän avulla. Tämä on hyödyllistä, kun haluat näyttää merkityksellisempiä arvoja (esim. lämpötila, valuutta tai kategoriat).

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

### Etikettien näkyvyyden kytkeminen {#toggling-label-visibility}

Käytitpä sitten oletus- tai mukautettuja etikettejä, voit hallita niiden näkyvyyttä `setLabelsVisible(true)` tai piilottaa ne `setLabelsVisible(false)`-menetelmällä.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height='150px'
/>

## Työkaluvihjeet {#tooltips}

Työkaluvihjeet parantavat käytettävyyttä näyttämällä `Slider`-arvon suoraan nuppia ylä- tai alapuolella, mikä auttaa käyttäjiä tekemään tarkempia säätöjä. Voit määrittää työkaluvihjeen käyttäytymistä, näkyvyyttä ja muotoa tarpeidesi mukaan.

Ota käyttöön työkaluvihjeet `setTooltipVisible()`-menetelmällä. Oletusarvoisesti työkaluvihjeet ovat pois päältä:

```java
slider.setTooltipVisible(true); // Ota käyttöön työkaluvihjeet
slider.setTooltipVisible(false); // Poista työkaluvihjeet käytöstä
```

Työkaluvihjeet voidaan myös määrittää näkyviin vain silloin, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Käytä `setTooltipVisibleOnSlideOnly()`-menetelmää ottaaksesi tämän toiminnallisuuden käyttöön. Tämä on erityisen hyödyllistä vähentämään visuaalista hälyä samalla kun tarjotaan hyödyllistä palautetta vuorovaikutuksen aikana.

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista työkaluvihjeiden kanssa:

### Työkaluvihjeiden mukauttaminen {#tooltip-customization}

Oletusarvoisesti `Slider` näyttää työkaluvihjeen sen nykyisellä arvolla. Jos haluat mukauttaa tämän tekstin, käytä `setTooltipText()`-menetelmää. Tämä on hyödyllistä, kun haluat, että työkaluvihje näyttää kiinteää tai kuvailevaa tekstiä elävän arvon sijaan.

Voit myös käyttää JavaScript-lauseketta työkaluvihjeen muotoilemiseksi dynaamisesti. Jos lausekkeesi sisältää `return`-avaimen, sitä käytetään sellaisenaan. Muutoin se kääritään automaattisesti `return`- ja `;`-lausekkeiden kanssa muodostaakseen pätevän funktion. Esimerkiksi:

```java
// Näyttää arvon, jota seuraa dollari-symboli
slider.setTooltipText("return x + '$'"); 
```

Tai yksinkertaisesti:

```java
// Tulkitsee: return x + ' yksikköä';
slider.setTooltipText("x + ' yksikköä'"); 
```

## Tyylit {#styling}

### Teemat {#themes}

`Slider`-komponentti sisältää 6 teemoja, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:n käyttöä. Teemojen tukea ylläpitää sisäänrakennettu enum-luokka.
Alla näkyvät liukusäätimet jokaiselle tuetulle teemalle:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height='460px'
/>

<TableBuilder name="Slider" />
