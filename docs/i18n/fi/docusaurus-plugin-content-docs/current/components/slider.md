---
title: Slider
sidebar_position: 101
description: >-
  Let users pick a numeric value with the Slider component, with configurable
  range, step, tick marks, labels, and orientation.
_i18n_hash: 06f08c2c7500c5fb8d50a1dcfd8488da
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

`Slider`-komponentti antaa käyttäjille mahdollisuuden valita numeerinen arvo vetämällä nuppia radalla minimirajan ja maksimiarvon välillä. Askeleet, viiva merkit ja etiketit voidaan määrittää valinnan ohjaamiseksi.

<!-- INTRO_END -->

## Perusteet {#basics}

`Slider` on suunniteltu toimimaan heti laatikosta, eikä se vaadi lisäasetuksia toimiakseen tehokkaasti. Oletusarvoisesti se kattaa arvot 0–100, ja sen aloitusarvo on 50, mikä tekee siitä ihanteellisen nopeaa integrointia varten mihin tahansa sovellukseen. Erityisempiin käyttötapauksiin `Slider`-komponenttia voidaan mukauttaa ominaisuuksilla, kuten suunta, viivamerkit, etiketit ja työkaluvihjeet.

Tässä esimerkki `Slider`-komponentista, joka mahdollistaa käyttäjien äänenvoimakkuuden säätämisen ennalta määritetyllä alueella:

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider`-arvo {#slider-value}

`Slider`-arvo edustaa nuppia nykyistä sijaintia liukusäätimessä ja se määritellään kokonaislukuna `Slider`-alueen sisällä. Tämä arvo päivittyy dynaamisesti käyttäjän vuorovaikutuksen myötä liukusäätimen kanssa, mikä tekee siitä olennaisen ominaisuuden käyttäjän syötteen seuraamiseksi.

:::tip Oletusarvo
Oletusarvoisesti `Slider`-komponentti alkaa arvolla 50, olettaen oletusarvoisen alueen 0–100.
:::

### Arvon asettaminen ja hakeminen {#setting-and-getting-the-value}

Voit asettaa `Slider`-arvon alustusvaiheessa tai päivittää sen myöhemmin käyttämällä `setValue()`-metodia. Nykyisen arvon hakemiseksi käytä `getValue()`-metodia.

```java
Slider slider = new Slider();
slider.setValue(25); // Asettaa liukusäätimen arvoksi 25

Integer value = slider.getValue();
System.out.println("Nykyinen liukusäätimen arvo: " + value);
```

## Minimialue ja maksimaalalue {#minimum-and-maximum-values}

Minimi- ja maksimiarvot määrittävät `Slider`-liukusäätimen sallitun alueen, määräten rajat, joiden sisällä liukusäätimen nuppi voi liikku. Oletusarvoisesti alue on asetettu 0–100, mutta voit mukauttaa näitä arvoja tarpeidesi mukaan.

Liukusäätimessä on oletusarvoinen askel 1, mikä tarkoittaa, että välisten askelten määrä määräytyy alueen mukaan. Esimerkiksi:
- Liukusäätimessä, jonka alue on 0–10, on 10 askelta.
- Liukusäätimessä, jonka alue on 0–100, on 100 askelta.

Nämä askeleet jakautuvat tasaisesti liukusäätimen radalle, ja niiden väli riippuu `Slider`-komponentin mitoista.

Alla on esimerkki `Slider`-komponentin luomisesta mukautetulla alueella:

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Viiva konfigurointi {#tick-configuration}

`Slider`-komponentti tarjoaa joustavan viiva konfiguroinnin, jonka avulla voit mukauttaa, miten viivamerkit näytetään ja miten liukusäätimen nuppi vuorovaikuttaa niiden kanssa. Tämä sisältää pää- ja pienaskelten välin määrittämisen, viivamerkkien näyttämisen/piilottamisen sekä nuppien sulauttamisen viivamerkkeihin tarkkaa käyttäjäsyötettä varten.

### Pää- ja pienaskelten väli {#major-and-minor-tick-spacing}

Voit määrittää pää- ja pienaskelten välin, joka määrää, kuinka usein ne ilmestyvät `Slider`-radalla:

- Pääaskelmerkki on suurempi ja usein merkitty key-arvoilla.
- Pienaskelmerkki on pienempi ja ilmestyy pääaskelten väliin tarjotakseen hienompia välejä.

Aseta askelväli käyttämällä `setMajorTickSpacing()` ja `setMinorTickSpacing()` menetelmiä:
```java
slider.setMajorTickSpacing(10); // Pääaskelmerkki joka 10 yksikköä
slider.setMinorTickSpacing(2);  // Pienaskelmerkki joka 2 yksikköä
```

### Näytä tai piilota askelmerkit {#show-or-hide-ticks}

Voit vaihtaa askelmerkkien näkyvyyttä käyttämällä `setTicksVisible()`-metodia. Oletusarvoisesti askelmerkit ovat piilotettu.

```java
slider.setTicksVisible(true); // Näytä askelmerkit
slider.setTicksVisible(false); // Piilota askelmerkit
```

### Kohdistus {#snapping}

Jotta `Slider`-nuppi kohdistuisi lähimpään viivamerkkiin käyttäjän vuorovaikutuksen aikana, voit ottaa kohdistuksen käyttöön käyttämällä `setSnapToTicks()`-metodia:

```java
slider.setSnapToTicks(true); // Ota kohdistus käyttöön
```

Tässä on esimerkki täysin konfiguroidusta `Slider`-komponentista, joka näyttää pää- ja pienaskel asetus yhdessä kohdistusmahdollisuuden kanssa tarkkoja säätöjä varten:

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Suunta ja käänteisyys {#orientation-and-inversion}

`Slider`-komponentti tukee kahta suuntaa: vaakasuunta (oletusarvo) ja pystysuunta. Voit vaihtaa suuntaa sovelluksesi käyttöliittymän ja vaatimusten mukaisesti.

Suuntaisuuden lisäksi `Slider` voidaan myös kääntää. Oletusarvoisesti:

- Vaakasuuntainen `Slider` kulkee minimistä (vasemmasta) maksimiin (oikealle).
- Pystysuuntainen `Slider` kulkee minimistä (alhaalta) maksimiin (ylöspäin).

Kun se on käännetty, tämä suunta kääntyy. Käytä `setInverted(true)`-metodia ottaaksesi käänteisyyden käyttöön.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Etiketit {#labels}

`Slider`-komponentti tukee etikettejä viivamerkeissä auttaakseen käyttäjiä tulkitsemaan arvoja helpommin. Voit käyttää oletusarvoisia numeerisia etikettejä tai antaa mukautettuja, ja voit muuttaa niiden näkyvyyttä tarpeen mukaan.

### Oletuseteiketit {#default-labels}

Oletusarvoisesti liukusäätimessä voidaan näyttää numeeriset etiketit pääaskelmerkeissä. Nämä arvot määräytyvät `setMajorTickSpacing()`-asetuksen mukaan. Oletusetikettien ottamiseksi käyttöön käytä:

```java
slider.setLabelsVisible(true);
```

### Mukautetut etiketit {#custom-labels}

Voit korvata oletusnumeraaliset etiketit mukautetulla tekstillä käyttämällä `setLabels()`-metodia. Tämä on hyödyllistä, kun haluat näyttää merkityksellisempiä arvoja (esim. lämpötila, valuutta tai kategoriat).

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

### Etikettien näkyvyyden hallinta {#toggling-label-visibility}

Riippumatta siitä, käytätkö oletusarvoisia vai mukautettuja etikettejä, voit hallita niiden näkyvyyttä `setLabelsVisible(true)` tai piilottaa ne `setLabelsVisible(false)`-metodilla.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Työkaluvihjeet {#tooltips}

Työkaluvihjeet parantavat käytettävyyttä näyttämällä `Slider`-komponentin arvon suoraan nuppia ylä- tai alapuolella, auttaen käyttäjiä tekemään tarkempia säätöjä. Voit konfiguroida työkaluvihjeen käyttäytymisen, näkyvyyden ja muodon tarpeidesi mukaan.

Ota työkaluvihjeet käyttöön käyttämällä `setTooltipVisible()`-metodia. Oletusarvoisesti työkaluvihjeet ovat pois käytöstä:

```java
slider.setTooltipVisible(true); // Ota työkaluvihjeet käyttöön
slider.setTooltipVisible(false); // Poista työkaluvihjeet käytöstä
```

Työkaluvihjeet voidaan myös konfiguroida näkyviksi vain, kun käyttäjä vuorovaikuttaa `Slider`-komponentin kanssa. Käytä `setTooltipVisibleOnSlideOnly()`-metodia ottaaksesi tämän käytön käyttöön. Tämä on erityisen hyödyllistä visuaalisen hälyn vähentämiseksi samalla, kun se tarjoaa hyödyllistä palautetta vuorovaikutuksen aikana.

Tässä esimerkki täysin konfiguroidusta `Slider`-komponentista, jossa on työkaluvihjeet:


### Työkaluvihjeen muokkaus {#tooltip-customization}

Oletusarvoisesti `Slider` näyttää työkaluvihjeen nykyisellä arvollaan. Jos haluat muokata tätä tekstiä, käytä `setTooltipText()`-metodia. Tämä on hyödyllistä, kun haluat, että työkaluvihje näyttää staattista tai kuvailevaa tekstiä elävän arvon sijaan.

Voit myös käyttää JavaScript-lauseketta työkaluvihjeen dynaamiseen muotoiluun. Jos lausekkeesi sisältää `return`-avainsanan, sitä käytetään sellaisenaan. Jos ei, se kääritään automaattisesti `return`- ja `;`-avainsanojen kanssa muodostaakseen voimassa olevan funktion. Esimerkiksi:

```java
// Näyttää arvon, jota seuraa dollarimerkki
slider.setTooltipText("return x + '$'");
```

Tai yksinkertaisesti:

```java
// Tulkitsee: return x + ' units';
slider.setTooltipText("x + ' units'");
```


## Tyylit {#styling}

### Teemat {#themes}

`Slider`-komponentissa on 6 sisäänrakennettua teemaa nopeaa tyylittelyä varten ilman CSS:n käyttöä. Teeman tuki on mahdollista käyttämällä sisäänrakennettua enum-luokkaa.
Alla on liukusäätimiä, joissa on kukin tuettu teema:

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
