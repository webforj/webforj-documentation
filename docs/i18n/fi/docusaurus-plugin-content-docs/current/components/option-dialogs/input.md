---
title: Input Dialog
sidebar_position: 25
description: >-
  Prompt users for text, numbers, dates, colors, or other typed values with the
  modal InputDialog and message-type styling.
_i18n_hash: b797a58a2e413b1be6d2cfd814d74efa
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` on modaalinen dialogi, joka on suunniteltu pyytämään käyttäjältä syötettä. Dialogi estää sovelluksen suorituksen, kunnes käyttäjä antaa syötteen tai sulkee dialogin.

<!-- INTRO_END -->

## Käytöt {#usages}

`InputDialog` pyytää käyttäjiltä syötettä, kuten tekstiä, numeroita tai muita tietoja. Koska dialogi on modaalinen, sovellus odottaa käyttäjän vastausta ennen kuin jatkaa:

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Tyyppit {#types}

### Syöttötyypit {#input-types}

`InputDialog` tukee erilaisia syöttökenttätyyppejä, jotka sallivat syöttötavan muokkaamisen tarpeidesi mukaan:

1. **TEKSTI**: Vakio yksirivinen tekstisyöttö.
2. **SALASANA**: Salasanasyöttökenttä, joka piilottaa käyttäjän syötteen.
3. **NUMERO**: Numero syöttökenttä.
4. **SÄHKÖPOSTI**: Syöttökenttä sähköpostiosoitteille.
5. **URL**: Syöttökenttä URL-osoitteille.
6. **HAKU**: Hakutekstisyöttökenttä.
7. **PÄIVÄMÄÄRÄ**: Syöttökenttä päivämäärien valitsemiseksi.
8. **AIKA**: Syöttökenttä ajan valitsemiseksi.
9. **PAIKALLINEN_PÄIVÄMÄRÄ-AIKA**: Syöttökenttä paikallisen päivämäärän ja ajan valitsemiseksi.
10. **VÄRI**: Syöttökenttä värin valitsemiseksi.

### Viestityyppi {#message-type}

`InputDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ-suunnittelujärjestelmän sääntöjen mukaan.

1. `YKSINKERTAINEN`: Näyttää viestin ilman ikonia käyttäen oletusteemaa.
2. `VIRHE`: Näyttää virheikoni viestin vieressä virheteema käytössä.
3. `KYSYMYS`: Näyttää kysymysmerkin ikonin viestin vieressä käyttäen ensisijaista teemaa.
4. `VAROITUS`: Näyttää varoitusikonin viestin vieressä varoitusteema käytössä.
5. `TIETO`: Näyttää info-ikonin viestin vieressä käyttäen info-teemaa.

Seuraavassa esimerkissä käyttäjältä pyydetään syöttämään salasanansa päästäkseen sovellukseen. Jos kirjautuminen epäonnistuu, käyttäjältä kysytään uudelleen.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Tulokset {#result}

`InputDialog` palauttaa käyttäjän syötteen merkkijonona. Jos käyttäjä sulkee dialogin antamatta syötettä, tulos on `null`.

:::important
Palautettava merkkijono palautetaan `show()`-menetelmästä tai vastaavasta `OptionDialog`-menetelmästä, kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Ole hyvä ja syötä ikäsi:", "Ikäsyöttö", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Syötit: " + result, "Syöte vastaanotettu");
} else {
  OptionDialog.showMessageDialog("Ei syötettä vastaanotettu", "Syöte peruutettu");
}
```

## Oletusarvo {#default-value}

`InputDialog` sallii sinun määrittää oletusarvon, joka näkyy syöttökentässä, kun dialogi näytetään. Tämä voi antaa käyttäjille ehdotuksen tai aikaisemmin syötetyn arvon.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Ole hyvä ja syötä nimesi:", "Nimisyöttö", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Aika raja {#timeout}

`InputDialog` sallii sinun asettaa aikarajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille syötepyynnöille tai toiminnoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikarajan dialogille käyttämällä `setTimeout(int timeout)`-menetelmää. Aikarajan kesto on sekunteina. Jos määritetty aika umpeutuu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Ole hyvä ja syötä nimesi:", "Nimisyöttö", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Syötit: " + result, "Syöte vastaanotettu", "OK", MessageDialog.MessageType.INFO);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehut**: Varmista, että kehotusviesti selkeästi selittää, mitä tietoa käyttäjältä pyydetään.
2. **Sopivat syöttötyypit**: Valitse syöttötyyppejä, jotka vastaavat vaadittua tietoa varmistaaksesi tarkkoja ja relevantteja käyttäjän syötteitä.
3. **Loogiset oletusarvot**: Aseta oletusarvoja, jotka tarjoavat hyödyllisiä ehdotuksia tai aikaisempia merkintöjä käyttäjän syötteen sujuvoittamiseksi.
5. **Aikarajojen harkittu käyttö**: Aseta aikarajoja ei-kriittisille syötepyynnöille varmistaen, että käyttäjillä on riittävästi aikaa antaa vaadittua tietoa.
6. **Ylivoimaisen käytön minimointi**: Käytä syöttödialokkeja säästeliäästi käyttäjän turhautumisen välttämiseksi. Varaa ne toiminnoille, jotka vaativat erityistä käyttäjäsyötettä.
