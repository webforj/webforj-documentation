---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 1dbd6d7664b01a9c3282ff4f3df65ea8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` on modaalinen dialogi, joka on suunniteltu pyytämään käyttäjältä syötteitä. Dialogi estää sovelluksen suorituksen, kunnes käyttäjä antaa syötteen tai sulkee dialogin.

<!-- INTRO_END -->

## Käyttöesimerkit {#usages}

`InputDialog` pyytää käyttäjiä antamaan syötteen, kuten tekstiä, numeroita tai muuta tietoa. Koska dialogi on modaalinen, sovellus odottaa käyttäjän vastausta ennen kuin jatkaa:

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Tyyppit {#types}

### Syöttötyypit {#input-types}

`InputDialog` tukee erilaisia syöttökenttätyyppejä, mikä mahdollistaa syöttötavan mukauttamisen erityistarpeiden mukaan:

1. **TEKSTI**: Normaalin yhden rivin tekstisyöte.
2. **SALASANA**: Salasanasyöttökenttä, joka piilottaa käyttäjän syötteen.
3. **NUMERO**: Numerosyöttökenttä.
4. **SÄHKÖPOSTI**: Syöttökenttä sähköpostiosoitteille.
5. **URL**: Syöttökenttä URL-osoitteille.
6. **HAKU**: Hakutekstisyöttökenttä.
7. **PÄIVÄ**: Syöttökenttä päivämäärän valitsemiseksi.
8. **AIKA**: Syöttökenttä ajan valitsemiseksi.
9. **PAIKALLINEN_PÄIVÄ_AIKA**: Syöttökenttä paikallisen päivämäärän ja ajan valitsemiseksi.
10. **VÄRI**: Syöttökenttä värin valitsemiseksi.

### Viestityyppi {#message-type}

`InputDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, dialogi näyttää kuvakkeen viestin vieressä, ja dialogin teema päivittyy webforJ:n suunnittelujärjestelmän sääntöjen mukaisesti.

1. `YKSINKERTAINEN`: Näyttää viestin ilman kuvaketta, käyttäen oletusteemaa.
2. `VIRHE`: Näyttää virhekuvakkeen viestin vieressä virheteeman kanssa.
3. `KYSYMYS`: Näyttää kysymysmerkin kuvakkeen viestin vieressä, käyttäen ensisijaista teemaa.
4. `VAROITUS`: Näyttää varoituskuvakkeen viestin vieressä varoitusteeman kanssa.
5. `TIETO`: Näyttää tietoikuvakkeen viestin vieressä käyttäen tietoteemaa.

Seuraavassa esimerkissä käyttäjältä pyydetään syöttämään salasana päästäkseen sovellukseen. Jos kirjautuminen epäonnistuu, käyttäjältä kysytään jälleen.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Tulos {#result}

`InputDialog` palauttaa käyttäjän syötteen merkkijonona. Jos käyttäjä sulkee dialogin antamatta syötettä, tulos on `null`.

:::important
Tuloksena oleva merkkijono palautuu `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Ole hyvä ja syötä ikäsi:", "Ikäsyöte", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("Annoit: " + result, "Syöte Vastaanotettu");
} else {
    OptionDialog.showMessageDialog("Ei syötettä saatu", "Syöttö Peruutettu");
}
```

## Oletusarvo {#default-value}

`InputDialog` sallii sinun määritellä oletusarvon, joka näkyy syöttökentässä, kun dialogi näytetään. Tämä voi antaa käyttäjille ehdotuksen tai aiemmin syötetyn arvon.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Ole hyvä ja syötä nimesi:", "Nimi Syöte", "Matti Meikäläinen", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Aikaikkuna {#timeout}

`InputDialog` sallii sinun asettaa aikarajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisissä syöttöpyynnöissä tai toiminnoissa, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikarajan dialogille käyttämällä `setTimeout(int timeout)`-metodia. Aikaraja on sekunteina. Jos määrätty aika kulkee ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Ole hyvä ja syötä nimesi:", "Nimi Syöte", "Matti Meikäläinen");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Annoit: " + result, "Syöte Vastaanotettu", "OK", MessageDialog.MessageType.INFO);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät Kehotukset**: Varmista, että kehotusviesti selvästi selittää, mitä tietoa käyttäjältä pyydetään.
2. **Sopivat Syöttötyypit**: Valitse syöttötyypit, jotka vastaavat vaadittua tietoa varmistaaksesi tarkka ja olennaista käyttäjän syöte.
3. **Loogiset Oletusarvot**: Aseta oletusarvoja, jotka antavat hyödyllisiä ehdotuksia tai aikaisempia merkintöjä käyttäjän syötteen helpottamiseksi.
5. **Kohtuullinen Aikarajan Käyttö**: Aseta aikarajoja ei-kriittisille syöttöpyynnöille varmistaen, että käyttäjillä on riittävästi aikaa antaa tarvittavat tiedot.
6. **Ylihyödyntämisen Vähentäminen**: Käytä syöttödialegia säästeliäästi, jotta vältetään käyttäjän turhautuminen. Varaudu niihin toimiin, jotka vaativat erityistä käyttäjän syötettä.
