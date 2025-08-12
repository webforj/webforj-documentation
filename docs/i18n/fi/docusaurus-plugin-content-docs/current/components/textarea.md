---
title: TextArea
sidebar_position: 130
_i18n_hash: f109f006fcd252bf81b6cccb83d38a50
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea`-komponentti webforJ:ssä tarjoaa ratkaisun moniriviseen teksin syöttöön. Loppukäyttäjät voivat vapaasti kirjoittaa ja muokata tekstiä, kun taas kehittäjät voivat asettaa kohtuullisia rajoja ominaisuuksilla, kuten enimmäismerkkirajoilla, kappalerakenteella ja validoimis säännöillä.

Tässä on esimerkki `TextArea`:sta monirivisen tekstin syöttämistä varten:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Kappaleiden hallinta {#managing-paragraphs}

`TextArea`-komponentti tarjoaa ominaisuuksia tekstikappaleiden käsittelemiseen, mikä tekee siitä ihanteellisen sovelluksille, jotka vaativat asiakirjojen muokkaamista tai jäsenneltyä tekstinsyöttöä.

Tässä on nopea esimerkki siitä, kuinka rakentaa ja manipuloida kappaleen sisältöä:

```java
TextArea textArea = new TextArea();

// Lisää kappale alussa
textArea.addParagraph(0, "Tämä on ensimmäinen kappale.");

// Liitä toinen kappale loppuun
textArea.addParagraph("Tässä on toinen kappale.");

// Liitä lisää sisältöä ensimmäiseen kappaleeseen
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

**Rakenteelliset rajoitteet** keskittyvät siihen, miten teksti on organisoitu ja visuaalisesti asetettu. Esimerkiksi:
- `setLineCountLimit(int maxLines)` rajoittaa sallitujen rivien määrää tekstialueella.
- `setParagraphLengthLimit(int maxCharsPerLine)` rajoittaa merkkien määrää kappaletta kohti (tai rivillä), auttaen säilyttämään luettavuutta tai muotoilustandardeja.

**Sisällön rajoitteet** puolestaan käsittelevät syötetyn tekstin kokonaismäärää riippumatta siitä, miten se on jaettu:
- `setMaxLength(int maxChars)` rajoittaa merkkien kokonaismäärää, joka on sallittu kaikissa kappaleissa.
- `setMinLength(int minChars)` pakottaa vähimmäispituuden, varmistaen että tarpeeksi sisältöä on annettu.

Seuraava demo sallii käyttäjien säätää validointirajoja—kuten enimmäismerkki- ja kappalemäärärajoja—reaaliaikaisesti ja nähdä, miten `TextArea` reagoi.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Sanat ja rivin kääntäminen {#word-wrap-and-line-wrapping}

Voit hallita, kääntyvätkö tekstit vai vieriivätkö ne vaakasuunnassa käyttämällä `setLineWrap()`. Kun kääntö on oikein pois käytöstä, rivit jatkavat vaakasuunnassa näkyvän alueen yli vaatiessaan vieritystä. Kun se on käytössä, teksti kääntyy automaattisesti seuraavalle riville, kun se saavuttaa komponentin reunan.

Kääntökäyttäytymisen tarkentamiseksi `setWrapStyle()` antaa sinun valita kahden tyylin välillä:
- `WORD_BOUNDARIES` kääntää tekstit kokonaisista sanoista, säilyttäen luonnollisen lukemisvirran.
- `CHARACTER_BOUNDARIES` kääntää yksittäisistä merkeistä, jolloin voit hallita tiukemmin asettelua, erityisesti kapeissa tai kiinteän levyisissä säilöissä.

Nämä kääntömahdollisuudet toimivat käsi kädessä rakenteellisten rajoitteiden, kuten rivimäärä- ja kappalerajoitusten, kanssa. Kun kääntö määrittää *kuinka* teksti virtaa saatavilla olevassa tilassa, rakenteelliset rajat määrittävät *kuinka paljon* tilaa tekstin on sallittu valloittaa. Yhdessä ne auttavat ylläpitämään sekä visuaalista rakennetta että käyttäjän syöttörakenteita.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Ennakoitu teksti {#predicted-text}

`TextArea`-komponentti tukee älykkäitä tekstiehdotuksia auttaakseen käyttäjiä kirjoittamaan nopeammin ja vähemmillä virheillä. Kun käyttäjät syöttävät tekstiä, ennakoivia ehdotuksia ilmestyy nykyisen syötteen perusteella, jolloin he voivat täydentää yleisiä tai odotettuja lauseita.

Ehdotukset voidaan hyväksyä painamalla `Tab`- tai `ArrowRight`-näppäintä, jolloin ehdotettu teksti lisätään syötteeseen saumatonta. Jos sopivaa ennustetta ei ole saatavilla tiettynä hetkenä, syöte pysyy muuttumattomana, ja käyttäjä voi jatkaa kirjoittamista keskeytyksettä—varmistamalla, että ominaisuus ei koskaan estä toimintaa.

Tämä ennakoiva käyttäytyminen parantaa sekä nopeutta että tarkkuutta, erityisesti toistuvissa syöttötapauksissa tai sovelluksissa, joissa ilmaisun johdonmukaisuus on tärkeää.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Tämä demo käyttää [Datamuse API](https://datamuse.com/) -sovellusliittymää sanaselitysten tarjoamiseen käyttäjän syötteen perusteella. Ehdotusten laatu ja relevanssi riippuvat kokonaan API:n datasta ja arviointimekanismista. Se ei käytä tekoälymalleja tai suuria kielimalleja (LLM); ehdotukset luodaan kevyestä, säännöksistä riippuvasta moottorista, joka keskittyy leksikaaliseen samankaltaisuuteen.
:::

## Vain luku ja Poistettu tila {#read-only-and-disabled-state}

`TextArea`-komponenttia voidaan asettaa joko vain luku- tai pois käytöstä käyttäjävuorovaikutuksen hallitsemiseksi.

**Vain luku** -tekstialue sallii käyttäjien tarkastella ja valita sisältöä, mutta ei muokata sitä. Tämä on hyödyllistä dynaamisen tai esitäytetyn tiedon näyttämiseksi, jonka tulisi pysyä muuttumattomana.

**Poistettu** tekstialue estää puolestaan kaiken vuorovaikutuksen—mukaan lukien keskittyminen ja tekstin valinta—ja sitä voidaan yleensä tyylitellä inaktiiviseksi tai harmaaksi.

Käytä vain luku -tilaa, kun sisältö on merkityksellistä, mutta muuttumatonta, ja pois käytöstä -tilaa, kun syöte ei tällä hetkellä ole sovellettavissa tai sen tulisi olla tilapäisesti inaktiivinen.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Tyylit {#styling}

<TableBuilder name="TextArea" />
