---
title: TextArea
sidebar_position: 130
_i18n_hash: 5e61ae2b47786f23e6f1f6eba317ed54
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea`-komponentti tarjoaa monirivisen tekstikentän, johon käyttäjät voivat kirjoittaa ja muokata pidempiä tekstiblokkeja. Se tukee maksimimerkkirajoja, kappaleen rakennetta, rivinvaihtoa ja validaatiosääntöjä hallitakseen, miten syöte käsitellään.

<!-- INTRO_END -->

## Creating a `TextArea` {#creating-a-textarea}

Luo `TextArea` ennen syöttämällä sille etiketti konstruktorissa. Ominaisuuksia kuten paikkamerkkiteksti, merkkirajat ja rivinvaihtokäyttäytyminen voidaan määrittää asettamismenetelmien avulla.

<ComponentDemo
path='/webforj/textarea'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaView.java']}
height='300px'
/>

## Managing paragraphs {#managing-paragraphs}

`TextArea`-komponentti tarjoaa ominaisuuksia tekstikappaleiden käsittelyyn, mikä tekee siitä ihanteellisen sovelluksille, jotka vaativat asiakirjamarkkinointia tai rakennettua tekstisyöttöä.

Tässä on nopea esimerkki siitä, miten rakentaa ja manipuloida kappaletekstiä:

```java
TextArea textArea = new TextArea();

// Lisää kappale alussa
textArea.addParagraph(0, "Tämä on ensimmäinen kappale.");

// Liitä toinen kappale loppuun
textArea.addParagraph("Tässä on toinen kappale.");

// Liitä lisäsisältö ensimmäiseen kappaleeseen
textArea.appendToParagraph(0, " Tämä lause jatkaa ensimmäistä.");

// Poista toinen kappale
textArea.removeParagraph(1);

// Hae ja tulosta kaikki nykyiset kappaleet
List<String> paragraphs = textArea.getParagraphs();
for (int i = 0; i < paragraphs.size(); i++) {
  System.out.println("Kappale " + i + ": " + paragraphs.get(i));
}
```

## Validation {#validation}

`TextArea`-komponentti tukee kahta täydentävää validaatiotyyppiä: rakenteellisia rajoitteita ja sisältörajoitteita.

**Rakenteelliset rajoitteet** keskittyvät siihen, miten teksti on järjestetty ja visuaalisesti esitetty. Esimerkiksi:
- `setLineCountLimit(int maxLines)` rajoittaa tekstialueeseen sallittujen rivien määrää.
- `setParagraphLengthLimit(int maxCharsPerLine)` rajoittaa merkkien määrää per kappale (tai rivi), mikä auttaa ylläpitämään luettavuutta tai muotoilustandardeja.

**Sisältörajoitteet** puolestaan käsittelevät syötettävän tekstin kokonaismäärää riippumatta siitä, miten se on jaettu:
- `setMaxLength(int maxChars)` rajoittaa kaikkien kappaleiden sallittujen merkkien kokonaismäärää.
- `setMinLength(int minChars)` asettaa vähimmäispituuden varmistaakseen, että riittävästi sisältöä annetaan.

Seuraava demo antaa käyttäjille mahdollisuuden säätää validaatiorajoja—kuten maksimimerkkimäärä, kappaleen pituus ja rivimäärä—reaaliaikaisesti ja nähdä, miten `TextArea` reagoi.

<ComponentDemo
path='/webforj/textareavalidation'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java']}
height='550px'
/>

## Word wrap and Line wrapping {#word-wrap-and-line-wrapping}

Voit hallita, kääriikö teksti vai vierittääkö se vaakasuunnassa käyttäen `setLineWrap()`. Kun kääntäminen on pois päältä, rivit jatkuvat vaakasuunnassa näkyvän alueen yli, mikä vaatii vierittämistä. Kun se on päällä, teksti kääntyy automaattisesti seuraavalle riville, kun se saavuttaa komponentin reunan.

Käyttäytymisen tarkentamiseksi `setWrapStyle()` antaa sinun valita kahden tyylin välillä:
- `WORD_BOUNDARIES` käärii tekstin kokonaisilla sanoilla, säilyttäen luonnollisen lukemisjärjestyksen.
- `CHARACTER_BOUNDARIES` käärii yksittäisillä merkeillä, joka mahdollistaa tiukemman hallinnan asettelusta, erityisesti kapeissa tai kiinteän levyisissä säilyttimissä.

Nämä käännösvaihtoehdot toimivat käsi kädessä rakenteellisten rajoitteiden, kuten rivimäärän ja kappaleen pituuden rajoitusten kanssa. Kun kääntyminen määrittää *kuinka* teksti virtaa käytettävissä olevassa tilassa, rakenteelliset rajoitukset määrittävät *kuinka paljon* tilaa tekstin sallitaan valloittaa. Yhdessä ne auttavat säilyttämään sekä visuaalisen rakenteen että käyttäjän syöttörajoja.

<ComponentDemo
path='/webforj/textareawrap'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java']}
height='400px'
/>

## Predicted text {#predicted-text}

`TextArea`-komponentti tukee älykkäitä tekstiehdotuksia auttaakseen käyttäjiä kirjoittamaan nopeammin ja vähemmillä virheillä. Kun käyttäjät syöttävät tekstiä, ennakoivat ehdotukset näkyvät nykyisen syötteen perusteella, mikä mahdollistaa heidän täydentävän yleisiä tai odotettuja lauseita.

Ehdotuksia voidaan hyväksyä painamalla `Tab`- tai `ArrowRight`-näppäintä, mikä lisää ehdotetun tekstin syötteeseen ongelmattomasti. Jos sopivia ennusteita ei ole mihin aikaan tahansa, syöte pysyy muuttumattomana, ja käyttäjä voi jatkaa kirjoittamista keskeytyksettä—varmistaen, että ominaisuus ei koskaan estä.

Tämä ennakoiva käyttäytyminen parantaa sekä nopeutta että tarkkuutta, erityisesti toistuvissa syöttötilanteissa tai sovelluksissa, joissa lauserakenteen johdonmukaisuus on tärkeää.

<ComponentDemo
path='/webforj/textareapredictedtext'
files={[
  'src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java',
  'src/main/resources/static/css/textarea/text-area-predicted-text-view.css',
]}
height='400px'
/>

:::info
Tämä demo käyttää [Datamuse API](https://datamuse.com/) tarjoamaan sanasuosituksia käyttäjän syötteen perusteella. Ehdotusten laatu ja olennaisuus riippuvat täysin API:n tietojoukosta ja pisteytysmenetelmästä. Se ei käytä tekoälymalleja tai suuria kielimalleja (LLM); ehdotukset tuotetaan kevyeltä, sääntöperusteiselta moottorilta, joka keskittyy sanalliseen samankaltaisuuteen.
:::

## Read-Only and Disabled state {#read-only-and-disabled-state}

`TextArea`-komponentti voidaan asettaa joko vain luku- tai poistettavaksi, jotta käyttäjävuorovaikutusta kontrolloidaan.

**Vain luku** -tekstikenttä sallii käyttäjien tarkastella ja valita sisältöä, mutta ei muokata sitä. Tämä on hyödyllinen dynaamisen tai esitäytetyn tiedon esittämiseen, joka tulisi pitää muuttumattomana.

**Poistettu** tekstikenttä, toisaalta, estää kaikki vuorovaikutukset—mukaan lukien keskittyminen ja tekstin valinta—ja se tyypillisesti tyylitellään passiiviseksi tai harmaaksi.

Käytä vain luku -tilaa, kun sisältö on olennaista mutta muuttumatonta, ja poistettu -tilaa, kun syöttö ei ole tällä hetkellä sovellettavissa tai sen tulisi olla väliaikaisesti passiivinen.

<ComponentDemo
path='/webforj/textareastates'
files={['src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java']}
height='300px'
/>

## Styling {#styling}

<TableBuilder name="TextArea" />
