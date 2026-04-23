---
title: TextArea
sidebar_position: 130
_i18n_hash: e8956f1a5bf39eab9a42244ff8d5ff21
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

Komponentti `TextArea` tarjoaa monirivisen tekstikentän, johon käyttäjät voivat kirjoittaa ja muokata pidempiä tekstikappaleita. Se tukee enimmäismerkki rajoja, kappalerakennetta, rivin kääntämistä ja validointisääntöjä, jotta syöttöä voidaan hallita.

<!-- INTRO_END -->

## Tekeminen `TextArea` {#creating-a-textarea}

Luo `TextArea` välittämällä sille label-kenttä konstruktoriin. Ominaisuuksia, kuten paikkamerkki, merkkirajat ja kääntö käyttäytyminen, voidaan konfiguroida setter-metodien avulla.

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Kappaleiden hallinta {#managing-paragraphs}

Komponentti `TextArea` tarjoaa ominaisuuksia tekstikappaleiden käsittelyyn, mikä tekee siitä ihanteellisen sovelluksille, jotka vaativat asiakirjojen muokkausta tai jäsenneltyä tekstisyöttöä.

Tässä on nopea esimerkki siitä, kuinka rakentaa ja manipuloida kappaleen sisältöä:

```java
TextArea textArea = new TextArea();

// Lisää kappale alussa
textArea.addParagraph(0, "Tämä on ensimmäinen kappale.");

// Lisää toinen kappale loppuun
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

## Validointi {#validation}

Komponentti `TextArea` tukee kahta täydentävää validointityyppiä: rakenteellisia rajoituksia ja sisällön rajoituksia.

**Rakenteelliset rajoitukset** keskittyvät siihen, kuinka teksti on järjestetty ja visuaalisesti asetettu. Esimerkiksi:
- `setLineCountLimit(int maxLines)` rajoittaa sallittujen rivien määrää tekstialueella.
- `setParagraphLengthLimit(int maxCharsPerLine)` rajoittaa merkkien määrää per kappale (tai rivi), mikä auttaa voimaan luettavuutta tai muotoilustandardeja.

**Sisällön rajoitukset** puolestaan liittyvät syötetyn tekstin kokonaissummaan riippumatta siitä, miten se on jakautunut:
- `setMaxLength(int maxChars)` rajoittaa sallittujen merkkien kokonaismäärää kaikissa kappaleissa.
- `setMinLength(int minChars)` asettaa vähimmäispituuden varmistaen, että riittävästi sisältöä on annettu.

Seuraava demo mahdollistaa käyttäjien säätää validointirajoja—kuten enimmäismerkkimäärä, kappaleen pituus ja rivien määrä—reaaliajassa ja nähdä, miten `TextArea` reagoi.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Sanan kääntäminen ja rivin kääntäminen {#word-wrap-and-line-wrapping}

Voit hallita, kääntääkö teksti vai vierittääkö se vaaka-suunnassa käyttämällä `setLineWrap()`. Kun kääntäminen on poistettu käytöstä, rivit jatkuvat vaaka-suunnassa näkyvän alueen ulkopuolelle, jolloin vierittäminen on tarpeen. Kun se on käytössä, teksti kääntyy automaattisesti seuraavalle riville, kun se saavuttaa komponentin reunan.

Kääntämisen käyttäytymistä voi edelleen hienosäätää `setWrapStyle()` avulla, jolloin voit valita kahden tyylin välillä:
- `WORD_BOUNDARIES` kääntää tekstiä koko sanojen kohdalla, säilyttäen luonnollisen lukemisen rytmin.
- `CHARACTER_BOUNDARIES` kääntää yksittäisissä merkeissä, jolloin saadaan tarkempi kontrolli asettelusta, erityisesti kapeissa tai kiinteän leveyden konteissa.

Nämä kääntö vaihtoehdot toimivat käsi kädessä rakenteellisten rajoitusten, kuten rivin määrä ja kappaleen pituusrajojen kanssa. Kun kääntäminen määrittää *miten* teksti virtaa käytettävissä olevassa tilassa, rakenteelliset rajat määrittävät *kuinka paljon* tilaa tekstin on sallittua viedä. Yhdessä ne auttavat ylläpitämään sekä visuaalista rakennetta että käyttäjän syöttörajoja.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Ennustettu teksti {#predicted-text}

Komponentti `TextArea` tukee älykkäitä tekstiehdotuksia auttaakseen käyttäjiä kirjoittamaan nopeammin ja vähemmillä virheillä. Kun käyttäjät syöttävät tekstiä, ennakoivia ehdotuksia näkyy nykyisen syötteen perusteella, jolloin he voivat täydentää yleisiä tai odotettuja lauseita.

Ehdotuksen voi hyväksyä painamalla `Tab` tai `ArrowRight` -näppäintä, jolloin ehdotettu teksti lisätään syötteeseen saumattomasti. Jos sopivaa ehdotusta ei ole saatavilla tietyllä hetkellä, syöte pysyy muuttumattomana, ja käyttäjä voi jatkaa kirjoittamista keskeytyksettä—varmistamalla, että toiminto ei koskaan ole tiellä.

Tämä ennakoiva käyttäytyminen parantaa sekä nopeutta että tarkkuutta, erityisesti toistuvissa syöttötilanteissa tai sovelluksissa, joissa lauserakenteen johdonmukaisuus on tärkeää.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
cssURL='/css/textarea/text-area-predicted-text-view.css'
height = '400px'
/>

:::info
Tämä demo käyttää [Datamuse API](https://datamuse.com/) tarjotakseen sanan ehdotuksia käyttäjän syötteen perusteella. Ehdotusten laatu ja merkitys riippuvat täysin API:n tietokannasta ja arviointimekanismista. Se ei käytä AI-malleja tai suuria kielimalleja (LLM); ehdotukset luodaan kevyestä, sääntöihin perustuvasta moottorista, joka keskittyy leksikaaliseen samankaltaisuuteen.
:::

## Luku ja Poistaminen tilassa {#read-only-and-disabled-state}

Komponentti `TextArea` voidaan asettaa joko vain luku- tai poistustilaan käyttäjä vuorovaikutuksen hallitsemiseksi.

**Vain luku** tekstialue sallii käyttäjien nähdä ja valita sisältöä, mutta ei muokata sitä. Tämä on hyödyllistä dynaamisten tai esitäytettyjen tietojen esittämisessä, jotka eivät saa muuttua.

**Poistettu** tekstialue estää kaikki vuorovaikutukset—mukaan lukien fokus ja tekstin valinta—ja on tyypillisesti tyylitelty inaktiiviseksi tai harmaaksi.

Käytä vain luku -tilaa, kun sisältö on relevanttia, mutta muuttumatonta, ja poistettu -tilaa, kun syöttö ei ole tällä hetkellä soveltuvaa tai sen pitäisi olla tilapäisesti pois käytöstä.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Tyylittely {#styling}

<TableBuilder name="TextArea" />
