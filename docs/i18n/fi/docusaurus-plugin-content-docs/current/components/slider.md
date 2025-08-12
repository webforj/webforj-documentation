---
title: Slider
sidebar_position: 101
_i18n_hash: 47e9254faad15097b580eb4099968fbc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`-komponentti webforJ:ssä tarjoaa vuorovaikutteisen hallinnan, joka sallii käyttäjien valita arvon tietyllä alueella liikuttamalla nuppia. Tämä ominaisuus on erityisen hyödyllinen sovelluksille, jotka vaativat tarkkaa tai intuitiivista syöttöä, kuten äänenvoimakkuuden, prosenttien tai muiden säädettävien arvojen valitseminen.

## Perusteet {#basics}

`Slider` on suunniteltu toimimaan heti laatikosta, eikä se vaadi lisäasetuksia toimiakseen tehokkaasti. Oletuksena se kattaa alueen 0–100 ja sen aloitusarvo on 50, mikä tekee siitä ihanteellisen nopeaan integrointiin mihin tahansa sovellukseen. Tarkempia käyttötapauksia varten `Slider`-komponenttia voidaan mukauttaa ominaisuuksilla, kuten suunta, välihuomautukset, etiketit ja työkaluvihjeet.

Tässä on esimerkki `Slider`-komponentista, joka sallii käyttäjien säätää äänenvoimakkuustasoja ennalta määritellyllä alueella:

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider`-arvo {#slider-value}

`Slider`-arvo edustaa nuppia `Slider`-komponentissa ja se määritellään kokonaislukuna `Slider`-alueella. Tämä arvo päivittyy käyttäjän vuorovaikutuksen myötä, mikä tekee siitä olennaisen ominaisuuden käyttäjäsyötteen seuraamiseksi.

:::tip Oletusarvo
Oletuksena `Slider` alkaa arvosta 50, olettaen oletusarvoisen alueen 0–100.
:::

### Arvon asettaminen ja hakeminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-komponentin arvon alkuasetuksessa tai päivittää sen myöhemmin käyttämällä `setValue()`-metodia. Hae nykyinen arvo `getValue()`-metodilla.

```java
Slider slider = new Slider();  
slider.setValue(25); // Asettaa slidervalueksi 25

Integer value = slider.getValue();  
System.out.println("Nykyinen Slider-arvo: " + value);
```

## Miniminsuurimmat arvot {#minimum-and-maximum-values}

Minimi- ja maksimiarvot määrittävät sallitun `Slider`-arvon alueen, määrittäen rajoja, joissa `Slider`-nuppi voi liikkua. Oletuksena alue on asetettu 0–100, mutta voit mukauttaa näitä arvoja tarpeidesi mukaan.

Välin etäisyydet `Slider`-komponentilla ovat oletusarvoisesti 1, mikä tarkoittaa, että välin määrä määräytyy alueen mukaan. Esimerkiksi:
- Slider alueella 0–10 sisältää 10 väliä.
- Slider alueella 0–100 sisältää 100 väliä.

Nämä välin ovat tasaisesti jakautuneet liukumaton radalla, ja niiden väli riippuu `Slider`-komponentin mitoista.

Alla on esimerkki `Slider`-komponentista, joka on luotu mukautetulla alueella:

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Välihuomautusten konfigurointi {#tick-configuration}

`Slider`-komponentti tarjoaa joustavan välihuomautusten konfiguroinnin, joka sallii sinun muokata, kuinka välihuomautuksia näytetään ja kuinka `Slider`-nuppi vuorovaikuttaa niiden kanssa. Tämä sisältää suurten ja pienten välihuomautusten välin säätämisen, välihuomautusten näyttämisen/piilottamisen ja "napauttamisen" mahdollistamisen välihuomautuksiin tarkkaa käyttäjäsyöttöä varten.

### Suurten ja pienten välihuomautusten väli {#major-and-minor-tick-spacing}

Voit määrittää suurten ja pienten välihuomautusten välin, joka määrittää, kuinka usein ne näkyvät `Slider`-radalla:

- Suuret välihuomautukset ovat suurempia ja usein merkittyjä avainarvojen esittelyyn.
- Pienet välihuomautukset ovat pienempiä ja näkyvät suurten välihuomautusten välissä tarjoamaan hienompia välejä.

Aseta välihuomautusten väli käyttämällä seuraavia `setMajorTickSpacing()` ja `setMinorTickSpacing()`-metodeja:
```java
slider.setMajorTickSpacing(10); // Suuret välihuomautukset joka 10. yksikölle
slider.setMinorTickSpacing(2);  // Pienet välihuomautukset joka 2. yksikölle
```

### Näytä tai piilota välihuomautukset {#show-or-hide-ticks}

Voit kytkeä välihuomautusten näkyvyyden käyttämällä `setTicksVisible()`-metodia. Oletuksena välihuomautukset ovat piilotettuina.

```java
slider.setTicksVisible(true); // Näytä välihuomautukset
slider.setTicksVisible(false); // Piilota välihuomautukset
```

### Napauttaminen {#snapping}

Varmistaaksesi, että `Slider`-nuppi kohdistuu lähimpään välihuomautukseen käyttäjän vuorovaikutuksen aikana, mahdollista napauttaminen käyttämällä `setSnapToTicks()`-metodia:

```java
slider.setSnapToTicks(true); // Ota napauttaminen käyttöön
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää suuria ja pieniä välihuomautusten asetuksia sekä napautusominaisuuden tarkkoja säätöjä varten:

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Suunta ja kääntäminen {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaakasuora (oletus) ja pystysuora. Voit muuttaa suuntaa sovelluksesi käyttöliittymän asettelun ja tarpeiden mukaan.

Suuntaamisen lisäksi `Slider`-komponenttia voidaan myös kääntää. Oletuksena:

- Vaakasuora `Slider` kulkee vähimmäisestä (vasen) maksimiin (oikea).
- Pystysuora `Slider` kulkee vähimmäisestä (ala) maksimiin (ylä).

Kun suunta on käännetty, suunta käännetään. Käytä `setInverted(true)`-metodia ottaaksesi kääntäminen käyttöön.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Etiketit {#labels}

`Slider`-komponentti tukee välihuomautuksissa näkyviä etikettejä, jotka auttavat käyttäjiä tulkitsemaan arvoja helpommin. Voit käyttää oletusnumero-etikettejä tai antaa mukautettuja etikettejä, ja voit kytkeä niiden näkyvyyttä tarpeen mukaan.

### Oletusetiketit {#default-labels}

Oletuksena slidervalue voi näyttää numeerisia etikettejä suurilla välihuomautuksilla. Nämä arvot määräytyvät `setMajorTickSpacing()`-asetuksen mukaan. Ota käyttöön oletusetiketit käyttämällä:

```java
slider.setLabelsVisible(true);
```

### Mukautetut etiketit {#custom-labels}

Voit korvata oletusnumeraaliset etiketit mukautetulla tekstillä käyttämällä `setLabels()`-metodia. Tämä on hyödyllistä, kun haluat näyttää merkityksellisempia arvoja (esim. lämpötila, valuutta tai kategoriat).

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

Olipa kyseessä oletus- tai mukautetut etiketit, voit hallita niiden näkyvyyttä `setLabelsVisible(true)` tai piilottaa ne käyttämällä `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Työkaluvihjeet {#tooltips}

Työkaluvihjeet parantavat käytettävyyttä näyttämällä `Slider`-arvon suoraan nuppia ylä- tai alapuolella, auttaen käyttäjiä tekemään tarkempia sääntöjä. Voit määrittää työkaluvihjeiden käyttäytymisen, näkyvyyden ja muodon tarpeidesi mukaan.

Ota käyttöön työkaluvihjeet käyttämällä `setTooltipVisible()`-metodia. Oletuksena työkaluvihjeet ovat pois käytöstä:

```java
slider.setTooltipVisible(true); // Ota työkaluvihjeet käyttöön
slider.setTooltipVisible(false); // Poista työkaluvihjeet käytöstä
```

Työkaluvihjeet voidaan myös määrittää näkymään vain silloin, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Käytä `setTooltipVisibleOnSlideOnly()`-metodia ottaaksesi tämän käyttäytymisen käyttöön. Tämä on erityisen hyödyllistä visuaalisen häiriön vähentämiseksi samalla, kun annetaan hyödyllistä palautetta vuorovaikutuksen aikana.

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, jossa on työkaluvihjeet:


### Työkaluvihjeiden mukauttaminen {#tooltip-customization}

Oletuksena `Slider` näyttää työkaluvihjeen sen nykyisestä arvosta. Jos haluat mukauttaa tätä tekstiä, käytä `setTooltipText()`-metodia. Tämä on hyödyllistä, kun haluat, että työkaluvihje näyttää staattista tai kuvaavaa tekstiä elävän arvon sijaan.

Voit myös käyttää JavaScript-lauseketta muotoilla työkaluvihje dynaamisesti. Jos lausekkeesi sisältää `return`-avaimen, sitä käytetään sellaisenaan. Jos ei, se pakataan automaattisesti `return`- ja `;`-lauselmiin, jotta se muodostaa kelvollisen funktion. Esimerkiksi:

```java
// Näyttää arvon, jota seuraa dollari
slider.setTooltipText("return x + '$'"); 
```

Tai yksinkertaisesti:

```java
// Tulkitsee: return x + ' yksikköä';
slider.setTooltipText("x + ' yksikköä'"); 
```

## Tyylittely {#styling}

### Teemat {#themes}

`Slider`-komponentissa on 6 teema, jotka on rakennettu nopeaa tyylittelyä varten ilman CSS:n käyttöä. Teemoja tuetaan sisäänrakennetun enum-luokan avulla. Alla on esimerkkejä liukumista, joihin kukin tuettu teema on sovellettu:

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
