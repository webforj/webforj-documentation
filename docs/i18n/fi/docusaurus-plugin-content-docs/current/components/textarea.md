---
title: TextArea
sidebar_position: 130
_i18n_hash: 423b70520e8f64a463d2c7b1d0e35ddc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Komponentti `TextArea` tarjoaa monirivisen tekstikentän, johon käyttäjät voivat kirjoittaa ja muokata pidempiä tekstilohkoja. Se tukee enimmäismerkkirajoja, kappalerakennetta, rivinvaihtoa ja validoimis sääntöjä, joilla hallitaan syötteen käsittelyä.

<!-- INTRO_END -->

## Luodaan `TextArea` {#creating-a-textarea}

Luo `TextArea` välittämällä etiketti sen konstruktorille. Ominaisuuksia kuten paikkausteksti, merkkirajat ja rivinvaihtokäyttäytyminen voidaan säätää asettamismenetelmien avulla.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Kappaleiden hallinta {#managing-paragraphs}

Komponentti `TextArea` tarjoaa ominaisuuksia tekstikappaleiden käsittelyyn, mikä tekee siitä ihanteellisen sovelluksiin, jotka vaativat asiakirjojen muokkaamista tai jäsenneltyä tekstisyöttöä.

Tässä on nopea esimerkki siitä, kuinka rakentaa ja manipuloida kappaletekstiä:

```java
TextArea textArea = new TextArea();

// Lisää kappale alusta
textArea.addParagraph(0, "Tämä on ensimmäinen kappale.");

// Liitä toinen kappale loppuun
textArea.addParagraph("Tässä on toinen kappale.");

// Lisää lisäsisältö ensimmäiseen kappaleeseen
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

Komponentti `TextArea` tukee kahta toisiaan täydentävää validoimintyyppiä: rakenteellisia rajoituksia ja sisällön rajoituksia.

**Rakenteelliset rajoitukset** keskittyvät siihen, miten teksti on järjestetty ja visuaalisesti asetettu. Esimerkiksi:
- `setLineCountLimit(int maxLines)` rajoittaa sallitut rivien määrä tekstikentässä.
- `setParagraphLengthLimit(int maxCharsPerLine)` rajoittaa merkkien määrää per kappale (tai rivi), auttaen noudattamaan luettavuutta tai muotoilustandardeja.

**Sisällön rajoitukset** puolestaan koskevat kokonaismäärää syötettynä tekstinä, riippumatta siitä, miten se jakautuu:
- `setMaxLength(int maxChars)` rajoittaa kaikkien kappaleiden yhteenlaskettua maksimimerkkimäärää.
- `setMinLength(int minChars)` varmistaa minimipituuden, mikä tarkoittaa, että riittävästi sisältöä on annettava.

Seuraava demo antaa käyttäjille mahdollisuuden säätää validoimisen rajoja—kuten enimmäismerkkimäärä, kappaleen pituus ja rivimäärä—reaaliajassa ja nähdä, miten `TextArea` reagoi.
	
<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Sanat ja rivinvaihto {#word-wrap-and-line-wrapping}

Voit hallita, tapahtuuko tekstin rivinvaihto vai vieritys vaakasuunnassa käyttäen `setLineWrap()`. Kun rivinvaihto on poistettu käytöstä, rivit jatkuvat horisontaalisesti näkyvän alueen ulkopuolelle, vaaditen vierittämistä. Kun se on käytössä, teksti menee automaattisesti seuraavalle riville, kun se saavuttaa komponentin reunan.

Rivinvaihdon käyttäytymisen hienosäätämiseksi `setWrapStyle()` antaa sinun valita kahden tyylin välillä:
- `WORD_BOUNDARIES` rajoittaa tekstin koko sanoilla, säilyttäen luonnollisen lukemisen kulun.
- `CHARACTER_BOUNDARIES` rajoittaa yksittäisillä merkeillä, mikä mahdollistaa tiukemman hallinnan asettelusta, erityisesti kapeissa tai kiinteän leveissä säiliöissä.

Nämä rivinvaihto vaihtoehdot toimivat yhdessä rakenteellisten rajoitusten, kuten rivien lukumäärän ja kappaleen pituusrajojen kanssa. Vaikka rivinvaihto määrittää *kuinka* teksti virtaa käytettävissä olevassa tilassa, rakenteelliset rajoitukset määrittävät *kuinka paljon* tilaa tekstille annetaan. Yhdessä ne auttavat ylläpitämään sekä visuaalista rakennetta että käyttäjän syöttämälle asetettua rajaa.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Ennakoitu teksti {#predicted-text}

Komponentti `TextArea` tukee älykkäitä tekstiehdotuksia, jotka auttavat käyttäjiä kirjoittamaan nopeammin ja vähemmillä virheillä. Kun käyttäjät syöttävät tekstiä, ennakoivat ehdotukset näkyvät nykyisen syötteen perusteella, jolloin he voivat täydentää yleisiä tai odotettuja lauseita.

Ehdotuksia voi hyväksyä painamalla `Tab`- tai `ArrowRight`-näppäintä, jolloin ehdotettu teksti lisätään syötteeseen saumattomasti. Jos tiettyä ehdotusta ei ole saatavilla kyseisellä hetkellä, syöte pysyy muuttumattomana, ja käyttäjä voi jatkaa kirjoittamista keskeytyksettä—varmistamalla, että toiminto ei koskaan häiritse.

Tämä ennakoiva käyttäytyminen parantaa sekä nopeutta että tarkkuutta, erityisesti toistuvissa syöttötilanteissa tai sovelluksissa, joissa lauseiden johdonmukaisuus on tärkeää.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Tämä demo käyttää [Datamuse API:ta](https://datamuse.com/) tarjoamaan sanatahdotuksia käyttäjän syötteen perusteella. Ehdotusten laatu ja relevanssi riippuvat täysin API:n tietokannasta ja pisteytys mekanismista. Se ei käytä tekoälymalleja tai suuria kielimalleja (LLM); ehdotukset tuotetaan kevyeltä, säännönmukaiselta moottorilta, joka keskittyy leksikaaliseen samankaltaisuuteen.
:::

## Vain luku- ja poistotila {#read-only-and-disabled-state}

Komponentti `TextArea` voidaan asettaa joko vain luku- tai poistotilaan käyttäjät vuorovaikutuksen hallitsemiseksi.

**Vain luku** tekstikenttä sallii käyttäjien nähdä ja valita sisällön, mutta ei muokata sitä. Tämä on hyödyllistä dynaamisen tai ennalta täytetyn tiedon näyttämiseen, joka tulisi pysyä muuttumattomana.

**Poistotila** puolestaan estää kaiken vuorovaikutuksen—mukaan lukien fokuksen ja tekstin valinnan—ja se on tyypillisesti muotoiltu inaktiiviseksi tai harmaaksi.

Käytä vain luku -tilaa, kun sisältö on relevanttia mutta muuttumatonta, ja poistotilaa, kun syötteelle ei ole tällä hetkellä sovellettavaa sisältöä tai sen pitäisi olla väliaikaisesti inaktiivista.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Tyylittely {#styling}

<TableBuilder name="TextArea" />
