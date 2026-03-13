---
title: Slider
sidebar_position: 101
_i18n_hash: 56140362edd92adde8d6114a8e6652c9
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider` -komponentti antaa käyttäjille mahdollisuuden valita numeerinen arvo vetämällä nuppia radalla minimirajan ja maksimin välillä. Askeleen väli, tikkuviivat ja etiketit voidaan määrittää ohjaamaan valintaa.

<!-- INTRO_END -->

## Perusteet {#basics}

`Slider` on suunniteltu toimimaan heti käyttöönottovaiheessa, eikä se vaadi lisäasetuksia toimiakseen tehokkaasti. Oletuksena se kattaa alueen 0–100 aloitusarvolla 50, mikä tekee siitä ihanteellisen nopeaa integrointia mihin tahansa sovellukseen. Tarkempia käyttötapauksia varten `Slider` voidaan mukauttaa ominaisuuksilla, kuten suunta, tikkuviivat, etiketit ja työkaluvihjeet.

Tässä on esimerkki `Slider`-komponentista, joka mahdollistaa käyttäjien säätää äänenvoimakkuutta ennalta määritellyllä alueella:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height='100px'
/>

## `Slider`-arvo {#slider-value}

`Slider`-arvo edustaa nuppin nykyistä sijaintia liukusäätimessä, ja se määritellään kokonaislukuna `Slider`-arvovälin sisällä. Tämä arvo päivittyy dynaamisesti, kun käyttäjä vuorovaikuttaa liukusäätimen kanssa, mikä tekee siitä ensisijaisen ominaisuuden käyttäjän syötteen seuraamista varten.

:::tip Oletusarvo
Oletusarvoisesti `Slider` aloittaa arvolla 50, ottaen oletusalueen huomioon, joka on 0–100.
:::

### Arvon asettaminen ja hakeminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-arvon alustusvaiheessa tai päivittää sen myöhemmin käyttäen `setValue()`-menetelmää. Nykyisen arvon hakemiseen käytä `getValue()`-menetelmää.

```java
Slider slider = new Slider();  
slider.setValue(25); // Asettaa liukusäätimen arvoon 25

Integer value = slider.getValue();  
System.out.println("Nykyinen Slider-arvo: " + value);
```

## Minimiväli ja maksimiarvot {#minimum-and-maximum-values}

Minimi- ja maksimiarvot määrittävät `Slider`-komponentin sallitun alueen, mikä määrää rajat, joilla `Slider`-nuppi voi liikkua. Oletuksena alue on asetettu väliin 0–100, mutta voit mukauttaa näitä arvoja tarpeidesi mukaan.

`Slider`-komponentin askelväli on oletuksena 1, mikä tarkoittaa, että välin määrä määräytyy alueen mukaan. Esimerkiksi:
- Liukusäädin, jonka alue on 0–10, sisältää 10 askelta.
- Liukusäädin, jonka alue on 0–100, sisältää 100 askelta.

Nämä askeleet jakautuvat tasaisesti liukusäätimen radalle, ja niiden välinen etäisyys riippuu `Slider`-komponentin dimensioista.

Alla on esimerkki `Slider`-komponentista, jossa on mukautettu alue:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height='200px'
/>

## Tiku-asetukset {#tick-configuration}

`Slider`-komponentti tarjoaa joustavat tikku-asetukset, mikä mahdollistaa tikkuviivojen näyttämisen mukauttamisen ja sen, miten liukusäätimen nuppi toimii niiden kanssa. Tämä sisältää pää- ja väliaskelten välin säätämisen, tikkuviivojen näyttämisen/piilottamisen ja tikkuviivoihin tarttumisen mahdollistamisen tarkkoja käyttäjän syötteitä varten.

### Pää- ja väliaskelten väli {#major-and-minor-tick-spacing}

Voit määrittää pää- ja väliaskelviivojen välin, mikä määrää, kuinka usein ne näkyvät `Slider`-radalla:

- Pääaskelviivat ovat suurempia ja usein merkittyjä avainarvoille.
- Väliaskelviivat ovat pienempiä ja näkyvät pääaskeliin väliin tarjoten hienompia väliä.

Aseta askelväli seuraavien `setMajorTickSpacing()`- ja `setMinorTickSpacing()`-menetelmien avulla:
```java
slider.setMajorTickSpacing(10); // Pääaskelviivat joka 10. yksikölle
slider.setMinorTickSpacing(2);  // Väliaskelviivat joka 2. yksikölle
```

### Tikkien näyttäminen tai piilottaminen {#show-or-hide-ticks}

Voit vaihtaa tikkuviivojen näkyvyyden käyttämällä `setTicksVisible()`-menetelmää. Oletuksena tikkuviivat ovat piilossa.

```java
slider.setTicksVisible(true); // Näytä tikkuviivat
slider.setTicksVisible(false); // Piilota tikkuviivat
```

### Tarttuminen {#snapping}

Jotta `Slider`-nuppi linjautuu lähimmän tikkuviivan kanssa käyttäjän vuorovaikutuksen aikana, ota käyttöön tarttuminen käyttäen `setSnapToTicks()`-menetelmää:

```java
slider.setSnapToTicks(true); // Ota tarttuminen käyttöön
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää pää- ja väliaskelasetuksia sekä tarttumiskykyä tarkkoja sääntöjä varten:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height='350px'
/>

## Suunta ja kääntö {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaakasuora (oletus) ja pystysuora. Voit muuttaa suuntaa käyttöösi sopivaksi käyttöliittymäasettelussa ja sovellusvaatimuksissa.

Lisäksi `Slider` voidaan myös kääntää. Oletuksena:

- Vaakasuora `Slider` kulkee minimistä (vasen) maksimiin (oikea).
- Pystysuora `Slider` kulkee minimistä (alhaalla) maksimiin (ylhäällä).

Kun se on käännetty, tämä suunta muuttuu päinvastaiseksi. Ota kääntö käyttöön `setInverted(true)`-menetelmällä.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height='420px'
/>

## Etiketit {#labels}

`Slider`-komponentti tukee etikettejä tikkuviivoilla auttaakseen käyttäjiä tulkitsemaan arvoja helpommin. Voit käyttää oletusnumerisia etikettejä tai tarjota mukautettuja etikettejä, ja voit säätää niiden näkyvyyttä tarpeen mukaan.

### Oletusetiketit {#default-labels}

Oletuksena liukusäädin voi näyttää numerisia etikettejä pääaskelviivoilla. Nämä arvot määräytyvät `setMajorTickSpacing()`-asetuksen mukaan. Ota oletusetiketit käyttöön seuraavasti:

```java
slider.setLabelsVisible(true);
```

### Mukautetut etikettit {#custom-labels}

Voit korvata oletusnumeriset etikettit mukautetulla tekstillä `setLabels()`-menetelmän avulla. Tämä on hyödyllistä, kun haluat näyttää merkityksellisempiä arvoja (esim. lämpötila, valuutta tai kategoriat).

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

### Etikettien näkyvyyden säätäminen {#toggling-label-visibility}

Olitpa käyttänyt oletus- tai mukautettuja etikettejä, voit hallita niiden näkyvyyttä `setLabelsVisible(true)`-menetelmällä tai piilottaa ne `setLabelsVisible(false)`-menetelmällä.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height='150px'
/>

## Työkaluvihjeet {#tooltips}

Työkaluvihjeet parantavat käytettävyyttä näyttämällä `Slider`-arvon suoraan nuppia ylhäällä tai alhaalla, auttaen käyttäjiä tekemään tarkempia säätöjä. Voit määrittää työkaluvihjeiden toimintaa, näkyvyyttä ja muotoa tarpeidesi mukaan.

Ota työkaluvihjeet käyttöön käyttämällä `setTooltipVisible()`-menetelmää. Oletuksena työkaluvihjeet ovat pois käytöstä:

```java
slider.setTooltipVisible(true); // Ota käyttöön työkaluvihjeet
slider.setTooltipVisible(false); // Poista työkaluvihjeet käytöstä
```

Työkaluvihjeet voidaan myös määrittää näkyviksi vain, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Ota tämä toiminta käyttöön `setTooltipVisibleOnSlideOnly()`-menetelmällä. Tämä on erityisen hyödyllistä visuaalisen häiriön vähentämiseksi samalla kun tarjotaan hyödyllistä palautetta vuorovaikutuksen aikana.

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, jossa on työkaluvihjeitä:

### Työkaluvihjeiden mukauttaminen {#tooltip-customization}

Oletuksena `Slider` näyttää työkaluvihjeen sen nykyisellä arvolla. Jos haluat mukauttaa tämän tekstin, käytä `setTooltipText()`-menetelmää. Tämä on hyödyllistä, kun haluat, että työkaluvihje näyttää staattista tai kuvaavaa tekstiä eikä elävää arvoa.

Voit myös käyttää JavaScript-lauseketta muotoillaksesi työkaluvihjeen dynaamisesti. Jos lausekkeesi sisältää `return`-avainsanan, se käytetään sellaisenaan. Jos ei, se kääritään automaattisesti `return`-sanan ja `;`-merkin sisään muodostaen kelvollisen funktion. Esimerkiksi:

```java
// Näyttää arvon dollarimerkin perässä
slider.setTooltipText("return x + '$'"); 
```

Tai yksinkertaisesti:

```java
// Tulkitsee seuraavasti: return x + ' yksikköä';
slider.setTooltipText("x + ' yksikköä'"); 
```


## Tyylit {#styling}

### Teemat {#themes}

`Slider`-komponentti sisältää 6 teemoja, joita voidaan käyttää nopeasti tyylittämiseen ilman CSS:n käyttöä. Teemojen käyttö tapahtuu sisäänrakennetun enum-luokan avulla.
Alla on liukusäätimiä, joihin on sovellettu kutakin tuettua teemaa:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height='460px'
/>

<TableBuilder name="Slider" />
