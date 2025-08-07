---
title: Slider
sidebar_position: 101
_i18n_hash: 045c80d3d54048157d805ee64213f210
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`-komponentti webforJ:ssä tarjoaa interaktiivisen hallinnan, jonka avulla käyttäjät voivat valita arvon tietyllä alueella siirtämällä nuppia. Tämä ominaisuus on erityisen hyödyllinen sovelluksille, jotka vaativat tarkkaa tai intuitiivista syötettä, kuten äänenvoimakkuuden, prosenttien tai muiden säädettävien arvojen valinnassa.

## Perusteet {#basics}

`Slider` on suunniteltu toimimaan suoraan laatikosta, eikä se vaadi lisäasetuksia toimiakseen tehokkaasti. Oletusarvoisesti se kattaa alueen 0–100, alkusarvo on 50, mikä tekee siitä ihanteellisen nopeaan integrointiin mihin tahansa sovellukseen. Tarkempia käyttötapauksia varten `Slider`-komponenttia voidaan mukauttaa ominaisuuksilla, kuten suunta, tikkuviivat, etiketti ja työkaluvihjeet.

Tässä on esimerkki `Slider`-komponentista, joka sallii käyttäjien säätää äänenvoimakkuustasoja ennalta määritellyllä alueella:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-arvo {#slider-value}

`Slider`-arvo edustaa nuppien nykyistä sijaintia liukusäätimellä ja se määritellään kokonaislukuna `Slider`-alueella. Tämä arvo päivittyy dynaamisesti, kun käyttäjä vuorovaikuttaa liukusäätimen kanssa, mikä tekee siitä olennaisen ominaisuuden käyttäjän syötteen seuraamiseen.

:::tip Oletusarvoinen arvo
Oletusarvoisesti `Slider` alkaa arvosta 50, olettaen oletusalueen 0–100.
:::

### Arvon asettaminen ja saaminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-arvon alustusvaiheessa tai päivittää sen myöhemmin `setValue()`-menetelmällä. Nykyisen arvon saamiseksi käytä `getValue()`-menetelmää.

```java
Slider slider = new Slider();  
slider.setValue(25); // Asettaa liukusäätimen arvoon 25

Integer value = slider.getValue();  
System.out.println("Nykyinen Slider-arvo: " + value);
```

## Minimialue ja maksimaalinen alue {#minimum-and-maximum-values}

Minimialue ja maksimaalinen alue määrittävät sallitun alueen `Slider`-komponentissa, määritellen ne rajat, joiden puitteissa `Slider`-nuppi voi liikkua. Oletusarvoisesti alue on asetettu 0–100, mutta voit mukauttaa nämä arvot tarpeidesi mukaan.

`Slider`-komponentin väliin jäävä askel on oletusarvoisesti 1, mikä tarkoittaa, että välin määrä määräytyy alueen mukaan. Esimerkiksi:
- Liukusäädin, jonka alue on 0–10, sisältää 10 väliaskelta.
- Liukusäädin, jonka alue on 0–100, sisältää 100 väliaskelta.

Nämä väliaskelmat jakautuvat tasaisesti liukusäätimen radalla, ja niiden väli riippuu `Slider`-komponentin mitoista.

Alla on esimerkki siitä, kuinka luodaan `Slider`-komponentti mukautetulla alueella:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Tikkujen konfigurointi {#tick-configuration}

`Slider`-komponentti tarjoaa joustavaa tikkujen konfigurointia, jolloin voit mukauttaa sitä, miten tikkuviivat näytetään ja miten liukusäätimen nuppi vuorovaikuttaa niiden kanssa. Tämä sisältää pää- ja alitikkupisteiden välin säätämisen, tikkuviivojen näyttämisen/piilottamisen ja tikkuviivoihin tarttumisen tarkan käyttäjäsyötteen varmistamiseksi.

### Pää- ja alitikkupisteiden väli {#major-and-minor-tick-spacing}

Voit määrittää pää- ja alitikkupisteiden välin, mikä määrää, kuinka usein ne ilmestyvät `Slider`-radalla:

- Päätikkuviivat ovat suurempia ja usein nimettyjä edustamaan keskeisiä arvoja.
- Alitikkuviivat ovat pienempiä ja esiintyvät päätikkuviivojen väleissä tarjoten hienompia väliaskelia.

Aseta tikkujen väli seuraavilla `setMajorTickSpacing()` ja `setMinorTickSpacing()` -menetelmillä:
```java
slider.setMajorTickSpacing(10); // Päätikkuviivat 10 välein
slider.setMinorTickSpacing(2);  // Alitikkuviivat 2 välein
```

### Näytä tai piilota tikkuviivat {#show-or-hide-ticks}

Voit kytkeä tikkuviivojen näkyvyyden päälle tai pois `setTicksVisible()`-menetelmällä. Oletusarvoisesti tikkuviivat ovat piilossa.

```java
slider.setTicksVisible(true); // Näytä tikkuviivat
slider.setTicksVisible(false); // Piilota tikkuviivat
```

### Tikkupisteisiin tarttuminen {#snapping}

Jotta `Slider`-nuppi linjautuisi lähimmän tikkupisteen kanssa käyttäjän vuorovaikutuksen aikana, ota käyttöön tarttuminen käyttämällä `setSnapToTicks()`-menetelmää:

```java
slider.setSnapToTicks(true); // Ota käyttöön tarttuminen
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää pää- ja alitikkupisteiden asetukset yhdessä tarttumiskyvyn kanssa tarkkoja sääntöjä varten:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Suunta ja kääntäminen {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaaka (oletusarvo) ja pystysuora. Voit vaihtaa suuntaa sovelluksesi käyttöliittymän asettelun ja tarpeiden mukaisesti.

Suuntausten lisäksi `Slider` voidaan myös kääntää. Oletusarvoisesti:

- Vaaka `Slider` kulkee minimistä (vasemmalle) maksimiseen (oikealle).
- Pystysuora `Slider` kulkee minimistä (alhaalta) maksimiseen (ylöspäin).

Kun suunta käännetään, tämä suunta muuttuu. Käytä `setInverted(true)`-menetelmää käännön mahdollistamiseksi.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Etiketit {#labels}

`Slider`-komponentti tukee etikettejä tikkuviivojen kohdalla auttaakseen käyttäjiä tulkitsemaan arvoja helpommin. Voit käyttää oletusarvoisia numeerisia etikettejä tai tarjota mukautettuja, ja voit säätää niiden näkyvyyttä tarpeen mukaan.

### Oletusetiketit {#default-labels}

Oletusarvoisesti liukusäädin voi näyttää numeerisia etikettejä päätikkuviivoilla. Nämä arvot määräytyvät `setMajorTickSpacing()`-asetuksen mukaan. Oletusetikettien aktivoimiseksi käytä:

```java
slider.setLabelsVisible(true);
```

### Mukautetut etiketit {#custom-labels}

Voit korvata oletusarvoiset numeeriset etiketit mukautetulla tekstillä käyttämällä `setLabels()`-menetelmää. Tämä on hyödyllistä, kun haluat näyttää merkityksellisempiä arvoja (esim. lämpötila, valuutta tai kategoriat).

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

Oletetut tai mukautetut etiketit käyttäessäsi voit hallita niiden näkyvyyttä `setLabelsVisible(true)` kytkemällä näkyviin tai piilottamalla `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Työkaluvihjeet {#tooltips}

Työkaluvihjeet parantavat käytettävyyttä näyttämällä `Slider`-arvon suoraan nuppien ylä- tai alapuolella, auttaen käyttäjiä tekemään tarkempia säätöjä. Voit konfiguroida työkaluvihjeiden käytäntöä, näkyvyyttä ja formaattia tarpeidesi mukaan.

Ota työkaluvihjeet käyttöön käyttämällä `setTooltipVisible()`-menetelmää. Oletusarvoisesti työkaluvihjeet ovat pois käytöstä:

```java
slider.setTooltipVisible(true); // Ota käyttöön työkaluvihjeet
slider.setTooltipVisible(false); // Poista työkaluvihjeet käytöstä
```

Työkaluvihjeitä voidaan myös konfiguroida näkyviin vain, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Käytä `setTooltipVisibleOnSlideOnly()`-menetelmää tämän käyttäytymisen mahdollistamiseksi. Tämä on erityisen hyödyllistä visuaalisen hälyn vähentämiseksi, samalla tarjoten hyödyllistä palautetta vuorovaikutuksen aikana.

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista työkaluvihjeiden kanssa:


### Työkaluvihjeiden mukauttaminen {#tooltip-customization}

Oletusarvoisesti `Slider` näyttää työkaluvihjeen nykyisellä arvolla. Jos haluat mukauttaa tätä tekstiä, käytä `setTooltipText()`-menetelmää. Tämä on hyödyllistä, kun haluat, että työkaluvihje näyttää staattista tai kuvailevaa tekstiä sen sijaan, että se vain näyttäisi livearvon.

Voit myös käyttää JavaScript-ilmaisua muodostaaksesi työkaluvihjeen dynaamisesti. Jos ilmaisusisi sisältää `return`-avainsanan, sitä käytetään sellaisenaan. Jos ei, se kääritään automaattisesti `return`- ja `;`-merkkien sisään muodostaen kelvollisen funktion. Esimerkiksi:

```java
// Näyttää arvon dollarimerkillä
slider.setTooltipText("return x + '$'"); 
```

Tai yksinkertaisesti:

```java
// Interprettoituu: return x + ' yksikköä';
slider.setTooltipText("x + ' yksikköä'"); 
```


## Tyylitys {#styling}

### Teemat {#themes}

`Slider`-komponentissa on 6 sisäänrakennettua teemaa nopeaa tyylittelyä varten ilman CSS:n käyttöä. Teemojen tukea tarjoaa sisäänrakennettu enum-luokka.
Alla on liukusäätimiä jokaisella tuetulla teemalla:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
