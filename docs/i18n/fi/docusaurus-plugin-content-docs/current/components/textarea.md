---
title: TextArea
sidebar_position: 130
_i18n_hash: 0ca8e9c1163e55bb86adf44931de139a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-textarea" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextArea" top='true'/>

`TextArea` -komponentti webforJ:ssä tarjoaa ratkaisun moniriviseen tekstisyöttöön. Loppukäyttäjät voivat vapaasti kirjoittaa ja muokata tekstiä, samalla kun kehittäjät voivat asettaa järkeviä rajoja kuten maksimimerkkirajat, kappalerakenne ja validointisäännöt.

Tässä on esimerkki `TextArea`:sta monirivisen tekstin syöttämiseen:

<ComponentDemo 
path='/webforj/textarea?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaView.java'
height = '300px'
/>

## Kappaleiden hallinta {#managing-paragraphs}

`TextArea` -komponentti tarjoaa ominaisuuksia tekstikappaleiden käsittelemiseen, mikä tekee siitä ihanteellisen sovelluksille, jotka vaativat asiakirjojen muokkaamista tai jäsenneltyä tekstisyöttöä.

Tässä on nopea esimerkki siitä, kuinka rakentaa ja manipuloida kappaletta:

```java
TextArea textArea = new TextArea();

// Lisää kappale alussa
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

## Validointi {#validation}

`TextArea` -komponentti tukee kahta toisiaan täydentävää validointityyppiä: rakenteellisia rajoitteita ja sisällön rajoitteita.

**Rakenteelliset rajoitteet** keskittyvät tekstin organisointiin ja visuaaliseen asettamiseen. Esimerkiksi:
- `setLineCountLimit(int maxLines)` rajoittaa sallitut rivimäärät tekstialueessa.
- `setParagraphLengthLimit(int maxCharsPerLine)` rajoittaa merkkimäärää per kappale (tai rivi), auttaen ylläpitämään luettavuutta tai muotoilustandardeja.

**Sisällön rajoitteet** puolestaan käsittelevät syötetyn tekstin kokonaismäärää riippumatta siitä, kuinka se on jakautunut:
- `setMaxLength(int maxChars)` rajoittaa kaikkien kappaleiden yhteenlaskettua merkkimäärää.
- `setMinLength(int minChars)` vaatii minimipituuden, varmistaen että riittävästi sisältöä annetaan.

Seuraava demo sallii käyttäjien säätää validointirajoja—kuten maksimimerkkimäärä, kappaleen pituus ja rivimäärä—reaaliajassa ja nähdä, kuinka `TextArea` reagoi.

<ComponentDemo 
path='/webforj/textareavalidation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaValidationView.java'
height = '550px'
/>

## Sanan kääntäminen ja rivin kääntäminen {#word-wrap-and-line-wrapping}

Voit määrittää, kääntyykö teksti vai vierittääkö se vaaka-suunnassa käyttäen `setLineWrap()`. Kun kääntäminen on pois käytöstä, rivit jatkuvat vaaka-suunnassa näkyvän alueen ohi, mikä vaatii vierittämistä. Kun se on käytössä, teksti kääntyy automaattisesti seuraavalle riville, kun se saavuttaa komponentin reunan.

Jotta voit edelleen hienosäätää, kuinka kääntäminen käyttäytyy, `setWrapStyle()` antaa sinun valita kahden tyylin välillä:
- `WORD_BOUNDARIES` kääntää tekstiä kokonaisilla sanoilla, säilyttäen luonnollisen luettavuuden.
- `CHARACTER_BOUNDARIES` kääntää yksittäisillä merkeillä, mahdollistaen tiukemman hallinnan asettelussa, erityisesti kapeissa tai kiinteissä leveissä konteissa.

Nämä kääntöoptioita toimivat yhdessä rakenteellisten rajoitteiden, kuten rivimäärän ja kappaleen pituuden rajoitusten kanssa. Kun kääntäminen määrittää *kuinka* teksti virtaa käytettävässä tilassa, rakenteelliset rajat määrittelevät *kuinka paljon* tilaa teksti saa käyttää. Yhdessä ne auttavat säilyttämään sekä visuaalisen rakenteen että käyttäjän syöttörajoitukset.

<ComponentDemo 
path='/webforj/textareawrap?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaWrapView.java'
height = '400px'
/>

## Ennustettu teksti {#predicted-text}

`TextArea` -komponentti tukee älykkäitä tekstiehdotuksia, jotka auttavat käyttäjiä kirjoittamaan nopeammin ja vähemmillä virheillä. Kun käyttäjät syöttävät tekstiä, ennakoivia ehdotuksia ilmestyy nykyisen syötteen perusteella, mikä mahdollistaa yleisten tai odotettujen lauseiden täydentämisen.

Ennustuksia voidaan hyväksyä painamalla `Tab` tai `ArrowRight` -näppäintä, jolloin ehdotettu teksti lisätään syötteeseen saumattomasti. Jos ei ole sopivaa ennustusta saatavilla tietyllä hetkellä, syöte pysyy muuttumattomana ja käyttäjä voi jatkaa kirjoittamista keskeyttämättä—varmistaa, että toiminto ei koskaan häiritse.

Tämä ennakoiva käyttäytyminen parantaa sekä nopeutta että tarkkuutta, erityisesti toistuvissa syöttötilanteissa tai sovelluksissa, joissa lauserakenteiden johdonmukaisuus on tärkeää.

<ComponentDemo 
path='/webforj/textareapredictedtext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaPredictedTextView.java'
height = '400px'
/>

:::info
Tämä demo käyttää [Datamuse API:a](https://datamuse.com/) tarjotakseen sanavihjeitä käyttäjän syötteen perusteella. Ennusteiden laatu ja relevanssi riippuvat täysin API:n tietokannasta ja arviointimekanismista. Se ei käytä AI-malleja tai suuria kielimalleja (LLM); ehdotukset luodaan kevyestä, sääntöihin perustuvasta moottorista, joka keskittyy leksikaaliseen samankaltaisuuteen.
:::

## Vain luku ja pois käytöstä {#read-only-and-disabled-state}

`TextArea` -komponentti voidaan asettaa joko vain luku- tai pois käytöstä -tilaan käyttäjävuorovaikutuksen hallitsemiseksi.

**Vain luku** -tekstialue sallii käyttäjien nähdä ja valita sisällön, mutta ei muokata sitä. Tämä on hyödyllistä dynaamisen tai esitäytetyn tiedon näyttämiseksi, jonka tulisi pysyä muuttumattomana.

**Poissa käytöstä** -tekstialue puolestaan estää kaiken vuorovaikutuksen—mukaan lukien fokusoiminen ja tekstin valinta—ja sitä tyypillisesti muotoillaan epäaktiiviseksi tai harmaaksi.

Käytä vain luku -tilaa, kun sisältö on merkityksellistä mutta muuttumatonta, ja pois käytöstä -tilaa silloin, kun syöttö ei ole tällä hetkellä sovellettavissa tai sen pitäisi olla väliaikaisesti epäaktiivinen.

<ComponentDemo 
path='/webforj/textareastates?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/textarea/TextAreaStatesView.java'
height = '300px'
/>

## Tyylit {#styling}

<TableBuilder name="TextArea" />
