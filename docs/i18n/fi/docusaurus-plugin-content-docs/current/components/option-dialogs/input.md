---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 96ced3bbe3c9ec87ebf19010833b62c5
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` on modaalinen dialogi, joka on suunniteltu pyytämään käyttäjältä syötettä. Dialogi estää sovelluksen suorituksen, kunnes käyttäjä antaa syötteen tai sulkee dialogin.

<!-- INTRO_END -->

## Käytöt {#usages}

`InputDialog` pyytää käyttäjiltä syötettä, kuten tekstiä, numeroita tai muuta tietoa. Koska dialogi on modaalinen, sovellus odottaa käyttäjän vastausta ennen kuin jatkaa:

<ComponentDemo
path='/webforj/inputdialogbasic'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java']}
height='500px'
/>

## Tyypit {#types}

### Syöttötyypit {#input-types}

`InputDialog` tukee erilaisia syöttökenttätyyppejä, mikä mahdollistaa syöttötavan räätälöinnin tarpeidesi mukaan:

1. **TEKSTI**: Tavanomainen yksirivinen tekstisyöttö.
2. **SALASANA**: Salasanasyöttökenttä, joka piilottaa käyttäjän syötteen.
3. **NUMERO**: Numeraalinen syöttökenttä.
4. **SÄHKÖPOSTI**: Syöttökenttä sähköpostiosoitteille.
5. **URL**: Syöttökenttä URL-osoitteille.
6. **HAKU**: Hakutekstisyöttökenttä.
7. **PÄIVÄ**: Syöttökenttä päivämäärän valitsemiseksi.
8. **AIKA**: Syöttökenttä ajan valitsemiseksi.
9. **PAIKALLINEN_PÄIVÄ_AIKA**: Syöttökenttä paikallisen päivämäärän ja ajan valitsemiseksi.
10. **VÄRI**: Syöttökenttä värin valitsemiseksi.

### Viestityyppi {#message-type}

`InputDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ-suunnittelu järjestelmän sääntöjen mukaisesti.

1. `YKSINKERTAINEN`: Näyttää viestin ilman ikonia käyttäen oletusteemaa.
2. `VIRHE`: Näyttää virheikon viestin vieressä virheteema käytössä.
3. `KYSYMYS`: Näyttää kysymysmerkin ikonin viestin vieressä pääteeman mukaisesti.
4. `VAROITUS`: Näyttää varoitusikonin viestin vieressä varoitusteema käytössä.
5. `TIETOA`: Näyttää infoikonin viestin vieressä käyttäen info teemaa.

Seuraavassa esimerkissä käyttäjältä kysytään salasanaa sovelluksen käyttöön. Jos kirjautuminen epäonnistuu, käyttäjältä kysytään uudelleen.

<ComponentDemo
path='/webforj/inputdialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java']}
height='350px'
/>

## Tulos {#result}

`InputDialog` palauttaa käyttäjän syötteen merkkijonona. Jos käyttäjä sulkee dialogin ilman syötettä, tulos on `null`.

:::important
Palautettu merkkijono palautuu `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
  "Ole hyvä ja syötä ikäsi:", "Ikäsyöttö", "", InputDialog.InputType.NUMBER);

if (result != null) {
  OptionDialog.showMessageDialog("Syötit: " + result, "Syöte vastaanotettu");
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

## Aikakatkaisu {#timeout}

`InputDialog` sallii sinun asettaa aikakatkaisua, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisissä syöttöpyynnöissä tai toimissa, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikakatkaisun dialogille käyttämällä `setTimeout(int timeout)` -metodia. Aikakatkaisu kestää sekunteina. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
InputDialog dialog = new InputDialog(
  "Ole hyvä ja syötä nimesi:", "Nimisyöttö", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
  "Syötit: " + result, "Syöte vastaanotettu", "OK", MessageDialog.MessageType.INFO);
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehotteet**: Varmista, että kehotteessa selvästi kerrotaan, mitä tietoa käyttäjältä pyydetään.
2. **Sopivat syöttötyypit**: Valitse syöttotyypit, jotka vastaavat vaadittua tietoa varmistaaksesi tarkan ja merkityksellisen käyttäjän syötteen.
3. **Loogiset oletusarvot**: Aseta oletusarvot, jotka antavat hyödyllisiä ehdotuksia tai aiempia syötteitä käyttäjän syötteen sujuvoittamiseksi.
4. **Aikakatkaisun harkittu käyttö**: Aseta aikakatkaisut ei-kriittisille syöttöpyynnöille, varmistaen että käyttäjillä on tarpeeksi aikaa antaa tarvittavat tiedot.
5. **Ylivoimaisuuden minimointi**: Käytä syöttödialegia säästeliäästi käyttäjän turhautumisen välttämiseksi. Varaa niitä toiminnoille, jotka vaativat erityistä käyttäjän syötettä.
