---
sidebar_position: 25
title: Input Dialog
_i18n_hash: ba46203db1b4c35878c6509a514a70e5
---
# Syöttödialogi

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` on modaalinen dialogi, joka on suunniteltu pyytämään käyttäjältä syötettä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä antaa syötteen tai sulkee dialogin.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Käytöt {#usages}

`InputDialog` tarjoaa tavan pyytää syötettä käyttäjiltä, kuten tekstiä, numeroita tai muuta tietoa, varmistaen, että he antavat tarvittavat tiedot ennen kuin jatkavat.

## Tyypit {#types}

### Syöttötyypit {#input-types}

`InputDialog` tukee eri tyyppisiä syöttökenttiä, jotka sallivat sinun mukauttaa syöttötapaa erityistarpeidesi mukaan:

1. **TEKSTI**: Vakiota yhden rivin tekstisyöttö.
2. **SALASANA**: Salasanasyöttökenttä, joka piilottaa käyttäjän syötteen.
3. **NUMERO**: Numero-syöttökenttä.
4. **SÄHKÖPOSTI**: Syöttökenttä sähköpostiosoitteille.
5. **URL**: Syöttökenttä URL-osoitteille.
6. **HAKU**: Hakutekstisyöttökenttä.
7. **PÄIVÄ**: Syöttökenttä päivämäärän valitsemiseksi.
8. **AIKA**: Syöttökenttä ajan valitsemiseksi.
9. **MUISTI_LOCAL**: Syöttökenttä paikallisen päivämäärän ja ajan valitsemiseksi.
10. **VÄRI**: Syöttökenttä värin valitsemiseksi.

### Viestityyppi {#message-type}

`InputDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ-suunnittelujärjestelmän sääntöjen mukaisesti.

1. `LAINEN`: Näyttää viestin ilman ikonia, käyttäen oletusteemaa.
2. `VIRHE`: Näyttää virhekuvakkeen viestin vieressä virheteemalla.
3. `KYSYMYS`: Näyttää kysymysmerkin ikonina viestin vieressä, käyttäen ensisijaista teemaa.
4. `VAROITUS`: Näyttää varoituskuvakkeen viestin vieressä varoitusteemalla.
5. `TIETO`: Näyttää infoikonin viestin vieressä, käyttäen info teemaa.

Seuraavassa esimerkissä käyttäjä pyytää syöttämään salasanansa päästäkseen sovellukseen. Jos kirjautuminen epäonnistuu, käyttäjältä pyydetään uudelleen.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Tulos {#result}

`InputDialog` palauttaa käyttäjän syötteen merkkijonona. Jos käyttäjä sulkee dialogin antamatta syötettä, tulos on `null`.

:::important
Tuloksena oleva merkkijono palautuu `show()`-menetelmästä tai vastaavasta `OptionDialog`-menetelmästä, kuten alla on esitetty. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Ole hyvä ja syötä ikäsi:", "Ikäsyöttö", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("Annoit: " + result, "Syöttö vastaanotettu");
} else {
    OptionDialog.showMessageDialog("Ei syötettä vastaanotettu", "Syöttö peruutettu");
}
```

## Oletusarvo {#default-value}

`InputDialog` sallii sinun määrittää oletusarvon, joka näkyy syöttökentässä, kun dialogi näytetään. Tämä voi tarjota käyttäjille ehdotuksen tai aiemmin syötetyn arvon.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Ole hyvä ja syötä nimesi:", "Nimisyöttö", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Aikaraja {#timeout}

`InputDialog` sallii sinun asettaa aikarajan, jonka kuluttua dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisissä syötepyynnöissä tai toimissa, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikarajan dialogille käyttämällä `setTimeout(int timeout)`-menetelmää. Aikaraja on sekunteina. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Ole hyvä ja syötä nimesi:", "Nimisyöttö", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Annoit: " + result, "Syöttö vastaanotettu", "OK", MessageDialog.MessageType.INFO);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehotteet**: Varmista, että kehoteviesti selittää selvästi, mitä tietoa käyttäjältä pyydetään.
2. **Sopivat syöttötyypit**: Valitse syöttötyypit, jotka vastaavat vaadittua tietoa varmistaaksesi tarkan ja relevantin käyttäjäsyötteen.
3. **Loogiset oletusarvot**: Aseta oletusarvot, jotka tarjoavat hyödyllisiä ehdotuksia tai aikaisempia syötteitä käyttäjäsyötteen virtaviivaistamiseksi.
4. **Käytä aikarajoja harkiten**: Aseta aikarajoja ei-kriittisille syötepyynnöille varmistaen, että käyttäjillä on tarpeeksi aikaa antaa tarvittavat tiedot.
5. **Vältä liiallista käyttöä**: Käytä syöttödialogeita säästeliäästi käyttäjätyytyväisyyden välttämiseksi. Varreserve ne toimiin, jotka vaativat erityistä käyttäjäsyötettä.
