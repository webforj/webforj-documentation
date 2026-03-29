---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 3c045d4085b917bd2f338916cc61d276
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` on modaalinen dialogi, joka on suunniteltu pyytämään käyttäjältä syötettä. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä antaa syötteen tai sulkee dialogin.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

`InputDialog` pyytää käyttäjiltä syötettä, kuten tekstiä, numeroita tai muuta dataa. Koska dialogi on modaalinen, sovellus odottaa käyttäjän vastausta ennen kuin jatkaa:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Tyypit {#types}

### Syöttötyypit {#input-types}

`InputDialog` tukee erilaisia syöttökenttätyyppejä, jolloin voit mukauttaa syöttötavan tarpeidesi mukaan:

1. **TEKSTI**: Vakio yksirivinen tekstisyöttö.
2. **SALASANA**: Salasanasyöttökenttä, joka piilottaa käyttäjän syötön.
3. **NUMERO**: Numeraalinen syöttökenttä.
4. **SÄHKEPOSTI**: Syöttökenttä sähköpostiosoitteita varten.
5. **URL**: Syöttökenttä URL-osoitteita varten.
6. **HAKU**: Hakutekstisyöttökenttä.
7. **PÄIVÄMÄÄRÄ**: Syöttökenttä päivämäärien valitsemiseksi.
8. **AIKA**: Syöttökenttä ajan valitsemiseksi.
9. **PAIKALLINEN_PÄIVÄMÄÄRÄ_AIKA**: Syöttökenttä paikallisen päivämäärän ja ajan valitsemiseksi.
10. **VÄRI**: Syöttökenttä värin valitsemiseksi.

### Viestityyppi {#message-type}

`InputDialog` tukee seuraavia viestityyppejä. Kun määrität tyyppiä, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ:n suunnittelujärjestelmän sääntöjen mukaan.

1. `YKSINKERTAINEN`: Näyttää viestin ilman ikonia, käyttäen oletusteemaa.
2. `VIRHE`: Näyttää virheikon viestin vieressä virheteemalla.
3. `KYSYMYS`: Näyttää kysymysmerkin ikonin viestin vieressä, käyttäen ensisijaista teemaa.
4. `VAROITUS`: Näyttää varoitusikon viestin vieressä varoitusteemalla.
5. `TIETO`: Näyttää infoikon viestin vieressä, käyttäen infoteemaa.

Seuraavassa esimerkissä käyttäjältä pyydetään syöttämään salasana päästäkseen sovellukseen. Jos kirjautuminen epäonnistuu, käyttäjälle pyydetään syöttämään se uudelleen.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Tulos {#result}

`InputDialog` palauttaa käyttäjän syötteen merkkijonona. Jos käyttäjä sulkee dialogin antamatta syötettä, tulos on `null`.

:::important
Palautettava merkkijono palautuu `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Ole hyvä ja syötä ikäsi:", "Ikäsyöte", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Syötit: " + result, "Syöte vastaanotettu");
} else {
  OptionDialog.showMessageDialog("Syötettä ei vastaanotettu", "Syöte peruutettu");
}
```

## Oletusarvo {#default-value}

`InputDialog` sallii sinun määrittää oletusarvon, joka näkyy syöttökentässä, kun dialogi avataan. Tämä voi antaa käyttäjille ehdotuksen tai aikaisemmin syötetyn arvon.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Ole hyvä ja syötä nimesi:", "Nimi-syöte", "Matti Meikäläinen", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Aikakatkaisu {#timeout}

`InputDialog` sallii sinun asettaa aikakatkaisuajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille syöttöpyynnöille tai toimille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikakatkaisun dialogille käyttämällä `setTimeout(int timeout)`-metodia. Aikakatkaisu on sekunneissa. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Ole hyvä ja syötä nimesi:", "Nimi-syöte", "Matti Meikäläinen");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Syötit: " + result, "Syöte vastaanotettu", "OK", MessageDialog.MessageType.INFO);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja tiiviit kysymykset**: Varmista, että kysymys selkeästi selittää, mitä tietoa käyttäjältä pyydetään.
2. **Sopivat syöttötyypit**: Valitse syöttötyypit, jotka vastaavat vaadittua dataa varmistaen tarkka ja relevantti käyttäjäsyöte.
3. **Loogiset oletusarvot**: Aseta oletusarvot, jotka tarjoavat hyödyllisiä ehdotuksia tai aikaisempia merkintöjä käyttäjän syötteen virtaviivaistamiseksi.
4. **Ajan käytön harkinta**: Aseta aikakatkaisuja ei-kriittisille syöttöpyynnöille varmistaen, että käyttäjillä on tarpeeksi aikaa antaa vaadittu tieto.
5. **Vähemmän on enemmän**: Käytä syöttödialekteja säästeliäästi, jotta vältät käyttäjien turhautumisen. Varaa niitä toimille, jotka vaativat erityistä käyttäjäsyötettä.
