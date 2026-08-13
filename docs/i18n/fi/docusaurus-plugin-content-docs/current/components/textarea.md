---
title: TextArea
sidebar_position: 130
description: >-
  Capture multi-line input with the TextArea component, including paragraph
  management, character limits, wrapping, and validation.
_i18n_hash: f9863352a124e1af3575a849204b97ed
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea`-komponentti tarjoaa monirivisen tekstiinput-kentän, johon käyttäjät voivat kirjoittaa ja muokata pidempiä tekstikappaleita. Se tukee enimmäismerkki rajoja, kappalerakennetta, rivin kelausta ja tarkastus sääntöjä, jotka hallitsevat syötteen käsittelyä.

<!-- INTRO_END -->

## Luodaan `TextArea` {#creating-a-textarea}

Luo `TextArea` välittämällä etiketti sen konstruktorille. Ominaisuuksia, kuten paikkamerkki, merkkirajat ja kelauskäyttäytyminen, voidaan määrittää asetusmenetelmien avulla.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Kappaleiden hallinta {#managing-paragraphs}

`TextArea`-komponentti tarjoaa ominaisuuksia tekstikappaleiden käsittelyyn, mikä tekee siitä ihanteellisen sovelluksille, jotka vaativat asiakirjojen muokkausta tai rakenteellista tekstisyötettä.

Tässä on nopea esimerkki siitä, miten rakentaa ja manipuloida kappaletekstiä:

```java
TextArea textArea = new TextArea();

// Lisää kappale alkuun
textArea.addParagraph(0, "Tämä on ensimmäinen kappale.");

// Liitä toinen kappale loppuun
textArea.addParagraph("Tässä on toinen kappale.");

// Lisää lisäsisältöä ensimmäiseen kappaleeseen
textArea.appendToParagraph(0, " Tämä lause jatkaa ensimmäistä.");

// Poista toinen kappale
textArea.removeParagraph(1);

// Hae ja tulosta kaikki nykyiset kappaleet
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Kappale " + i + ": " + paragraphs.get(i));
}
```

## Tarkastus {#validation}

`TextArea`-komponentti tukee kahta täydentävää tarkastus tyyppiä: rakenteellisia rajoja ja sisältörajoja.

**Rakenteelliset rajoitukset** keskittyvät siihen, miten teksti on järjestetty ja visuaalisesti asetettu. Esimerkiksi:
- `setLineCountLimit(int maxLines)` rajoittaa sallittujen rivien määrää tekstialueella.
- `setParagraphLengthLimit(int maxCharsPerLine)` rajoittaa merkkien määrää kappaletta kohden (tai rivi), auttaen säilyttämään luettavuus- tai muotoilustandardeja.

**Sisältörajoitukset** puolestaan käsittelevät syötettyjen merkkien kokonaismäärää, riippumatta siitä, miten ne on jaettu:
- `setMaxLength(int maxChars)` asettaa enimmäismerkkimäärän kaikille kappaleille.
- `setMinLength(int minChars)` pakottaa vähimmäispituuden, varmistaen riittävän sisällön.

Seuraava demo mahdollistaa käyttäjille tarkastusrajojen säätämisen—kuten enimmäismerkkimäärä, kappaleen pituus ja rivimäärä—reaalimaailmassa ja näyttää, kuinka `TextArea` reagoi.

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Sanan kelaus ja rivin kelaus {#word-wrap-and-line-wrapping}

Voit hallita, kelaako teksti vai vierittääkö se vaaka suuntaan käyttämällä `setLineWrap()`. Kun kelaus on pois päältä, rivit jatkuvat vaaka suuntaan näkyvän alueen ulkopuolelle, vaatimalla vierittämistä. Kun se on päällä, teksti kelaa automaattisesti seuraavalle riville, kun se saavuttaa komponentin reunan.

Kelauskäyttäytymisen hienosäädön tueksi, `setWrapStyle()` antaa sinulle mahdollisuuden valita kahden tyylin välillä:
- `WORD_BOUNDARIES` kelataan tekstiä kokonaisilla sanoilla, säilyttäen luonnollisen lukemisen rytmin.
- `CHARACTER_BOUNDARIES` kelataan yksittäisillä merkeillä, mikä mahdollistaa tiukemman hallinnan asettelussa, erityisesti kapeissa tai kiinteäleveyksisissä säilöissä.

Nämä kelausvaihtoehdot toimivat yhdessä rakenteellisten rajoitusten kanssa, kuten rivimäärä- ja kappale pituusrajat. Vaikka kelaus määrittää *miten* teksti virtaa käytettävissä olevassa tilassa, rakenteelliset rajat määrittelevät *kuinka paljon* tilaa tekstin on sallittu vallata. Yhdessä ne auttavat ylläpitämään sekä visuaalista rakennetta että käyttäjän syöttörajoja.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Ennakoitu teksti {#predicted-text}

`TextArea`-komponentti tukee älykkäitä tekstiehdotuksia auttaakseen käyttäjiä kirjoittamaan nopeammin ja vähemmillä virheillä. Kun käyttäjät syöttävät tekstiä, ennakoivat ehdotukset ilmestyvät nykyisen syötteen perusteella, mikä mahdollistaa heidän täydentävän yhteisiä tai odotettuja lauseita.

Ehdotuksia voi hyväksyä painamalla `Tab`- tai `ArrowRight`-näppäintä, mikä liittää ehdotetun tekstin syöttöön saumattomasti. Jos sopivaa arviointia ei ole saatavilla tiettynä hetkenä, syöte pysyy muuttumattomana, ja käyttäjä voi jatkaa kirjoittamista ilman keskeytyksiä—varmistaen, että ominaisuus ei koskaan ole tiellä.

Tämä ennakoiva käyttäytyminen parantaa sekä nopeutta että tarkkuutta, erityisesti toistuvissa syöttötapauksissa tai sovelluksissa, joissa lauseiden johdonmukaisuus on tärkeää.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/frontend/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Tämä demo käyttää [Datamuse API:a](https://datamuse.com/) sanojen ehdottamiseen käyttäjän syötteen perusteella. Ehdotusten laatu ja merkityksellisyys riippuvat täysin API:n tietopaketista ja pisteytys mekanismista. Se ei käytä tekoälymalleja tai suuria kielimalleja (LLM); ehdotukset luodaan kevyestä, sääntöperustaisesta moottorista, joka keskittyy lexikaaliseen samankaltaisuuteen.
:::

## Vain luku ja pois päältä oleva tila {#read-only-and-disabled-state}

`TextArea`-komponentti voidaan asettaa joko vain luku- tai pois päältä-tilaan käyttäjän vuorovaikutuksen hallitsemiseksi.

**Vain luku** -tekstialue sallii käyttäjien tarkastella ja valita sisältöä, mutta ei muokata sitä. Tämä on hyödyllinen dynaamisen tai esitäytetyn tietojen näyttämiseksi, joka tulisi pysyä muuttumattomana.

**Pois päältä** -tekstialue puolestaan estää kaiken vuorovaikutuksen—mukaan lukien kohdistus ja tekstin valinta—ja se on tyypillisesti tyyliltään inaktiivinen tai harmaantunut.

Käytä vain luku-tilaa, kun sisältö on merkityksellistä mutta muuttumatonta, ja pois päältä-tilaa, kun syöte ei ole tällä hetkellä sovellettavissa tai sen pitäisi olla tilapäisesti inaktiivinen.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Tyylittely {#styling}

<TableBuilder name="TextArea" />
