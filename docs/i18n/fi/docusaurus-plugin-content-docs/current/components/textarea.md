---
title: TextArea
sidebar_position: 130
_i18n_hash: c25007720c315e5b0b26197e1fdfff61
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea`-komponentti tarjoaa monirivisen tekstikentän, johon käyttäjät voivat kirjoittaa ja muokata pidempiä tekstikappaleita. Se tukee maksimimerkkirajoja, kappalerakennetta, rivin katkeamista ja validointisääntöjä syötteen käsittelyn hallinnaksi.

<!-- INTRO_END -->

## Luominen `TextArea` {#creating-a-textarea}

Luo `TextArea` välittämällä etiketti sen konstruktorille. Ominaisuuksia, kuten paikkamerkki, merkkirajat ja rivin katkeaminen, voidaan konfiguroida asetusmenetelmien avulla.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Kappaleiden hallinta {#managing-paragraphs}

`TextArea`-komponentti tarjoaa ominaisuuksia tekstikappaleiden käsittelemiseen, mikä tekee siitä ihanteellisen sovelluksille, jotka vaativat asiakirjojen muokkaamista tai jäsenneltyä tekstisyöttöä.

Tässä on nopea esimerkki siitä, miten kappalekohtainen sisältö voidaan rakentaa ja manipuloida:

```java
TextArea textArea = new TextArea();

// Lisää kappale alussa
textArea.addParagraph(0, "Tämä on ensimmäinen kappale.");

// Liitä toinen kappale loppuun
textArea.addParagraph("Tässä on toinen kappale.");

// Liitä lisäsisältöä ensimmäiseen kappaleeseen
textArea.appendToParagraph(0, " Tämä lause jatkaa ensimmäistä.");

// Poista toinen kappale
textArea.removeParagraph(1);

// Hae ja tulosta kaikki nykyiset kappaleet
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Kappale " + i + ": " + paragraphs.get(i));
}
```

## Validointi {#validation}

`TextArea`-komponentti tukee kahta täydentävää validointityyppiä: rakenteelliset rajoitteet ja sisällön rajoitteet.

**Rakenteelliset rajoitteet** keskittyvät siihen, miten teksti on järjestetty ja visuaalisesti asetettu. Esimerkiksi:
- `setLineCountLimit(int maxLines)` rajoittaa sallitun rivien määrää tekstikentässä.
- `setParagraphLengthLimit(int maxCharsPerLine)` rajoittaa merkkien määrää per kappale (tai rivi), auttaen ylläpitämään luettavuutta tai muotoilustandardeja.

**Sisällön rajoitteet** sen sijaan käsittelevät syötetyn tekstin kokonaismäärää riippumatta siitä, miten se on jaettu:
- `setMaxLength(int maxChars)` rajoittaa kaikkien kappaleiden yhteenlasketun merkkimäärän.
- `setMinLength(int minChars)` asettaa vähimmäispituuden, varmistaen, että riittävästi sisältöä annetaan.

Seuraava demo sallii käyttäjien säätää validointirajoja—kuten maksimimerkkimäärää, kappaleen pituutta ja rivimäärää—reaaliaikaisesti ja nähdä, miten `TextArea` reagoi.
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Sanojen kääntö ja rivin katkeaminen {#word-wrap-and-line-wrapping}

Voit hallita, kääriikö teksti vai vierittääkö se vaakasuunnassa käyttämällä `setLineWrap()`. Kun kääntö on pois päältä, rivit jatkuvat vaakasuunnassa näkyvän alueen yli, jolloin vierittäminen on tarpeen. Kun se on päällä, teksti kääntyy automaattisesti seuraavalle riville, kun se saavuttaa komponentin reunan.

Kääntökäyttäytymisen hienosäätämiseksi `setWrapStyle()` antaa sinulle mahdollisuuden valita kahden tyylin välillä:
- `WORD_BOUNDARIES` kääntää tekstiä kokonaisilla sanoilla säilyttäen luonnollisen lukemisvirran.
- `CHARACTER_BOUNDARIES` kääntää yksittäisillä merkeillä, mikä mahdollistaa tiukemman hallinnan asettelussa, erityisesti kapeissa tai kiinteän levyisissä säiliöissä.

Nämä kääntövaihtoehdot toimivat yhdessä rakenteellisten rajoitteiden, kuten rivien ja kappaleiden pituusrajojen, kanssa. Vaikka kääntö määrittää *kuinka* teksti virtaa käytettävissä olevassa tilassa, rakenteelliset rajat määrittävät *kuinka paljon* tilaa tekstille sallitaan. Yhdessä ne auttavat ylläpitämään sekä visuaalista rakennetta että käyttäjän syöterajoja.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Ennakoitu teksti {#predicted-text}

`TextArea`-komponentti tukee älykkäitä tekstiehdotuksia auttaen käyttäjiä kirjoittamaan nopeammin ja vähemmillä virheillä. Kun käyttäjät syöttävät tekstiä, ennakoivia ehdotuksia ilmestyy nykyisen syötteen perusteella, mahdollistaen yleisten tai odotettavien ilmausten täydentämisen.

Ehdotuksia voidaan hyväksyä painamalla `Tab`- tai `ArrowRight`-näppäintä, jolloin ehdotettu teksti lisätään syötteeseen vaivattomasti. Jos mitään sopivaa ennakoitavaa ei ole saatavilla tiettynä hetkenä, syöte pysyy muuttumattomana, ja käyttäjä voi jatkaa kirjoittamista keskeytyksettä—varmistamalla, että ominaisuus ei koskaan häiritse.

Tämä ennakoiva käyttäytyminen parantaa sekä nopeutta että tarkkuutta, erityisesti toistuvissa syöttöskenaarioissa tai sovelluksissa, joissa ilmausten johdonmukaisuus on tärkeää.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Tämä demo käyttää [Datamuse API:ta](https://datamuse.com/) tarjoamaan sanasuosituksia käyttäjän syötteen perusteella. Ennusteiden laatu ja merkityksellisyys riippuvat täysin API:n tietojoukosta ja arviointimekanismista. Se ei käytä tekoälymalleja tai suuria kielimalleja (LLMs); ehdotukset tuotetaan kevyestä, sääntöihin perustuvasta moottorista, joka keskittyy lexikaaliseen samankaltaisuuteen.
:::

## Vain luku- ja pois päältä -tila {#read-only-and-disabled-state}

`TextArea`-komponenttia voidaan asettaa vain luku- tai pois päältä -tilaan käyttäjävuorovaikutuksen hallitsemiseksi.

**Vain luku** -tekstikenttä sallii käyttäjien katsella ja valita sisältöä, mutta ei muokata sitä. Tämä on hyödyllistä dynaamisen tai esitäytetyn tiedon näyttämiseksi, jota ei pitäisi muuttaa.

**Pois päältä** -tekstikenttä puolestaan estää kaiken vuorovaikutuksen—mukaan lukien keskittymisen ja tekstin valinnan—ja se on yleensä tyylitelty inaktiiviseksi tai harmaaksi.

Käytä vain luku -tilaa, kun sisältö on merkityksellistä mutta muuttumatonta, ja pois päältä -tilaa, kun syöte ei ole tällä hetkellä sovellettavaa tai sen pitäisi olla tilapäisesti inaktiivinen.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Tyylittely {#styling}

<TableBuilder name="TextArea" />
