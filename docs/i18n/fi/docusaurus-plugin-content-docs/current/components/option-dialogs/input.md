---
sidebar_position: 25
title: Input Dialog
_i18n_hash: 60c8f92b63b241996eda4f5a08df8027
---
# Input Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` on on modaalinen dialogi, joka on suunniteltu pyytämään käyttäjältä syötettä. Dialogi estää sovelluksen suorituksen, kunnes käyttäjä antaa syötteen tai sulkee dialogin.

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Käytöt {#usages}

`InputDialog` tarjoaa tavan pyytää syötettä käyttäjiltä, ​​kuten tekstiä, numeroita tai muita tietoja, varmistaen, että he antavat tarvittavat tiedot ennen etenemistä.

## Tyypit {#types}

### Syöttötyypit {#input-types}

`InputDialog` tukee erilaisia syöttökenttätyyppejä, mikä mahdollistaa syöttötavan räätälöimisen erityisiin tarpeisiisi:

1. **TEKSTI**: Normaali yksirivinen tekstisyöttö.
2. **SALASANA**: Salasanasyöttökenttä, joka piilottaa käyttäjän syötteen.
3. **NUMERO**: Numero syöttökenttä.
4. **SÄHKÖPOSTI**: Syöttökenttä sähköpostiosoitteita varten.
5. **URL**: Syöttökenttä URL-osoitteita varten.
6. **HAKU**: Haku tekstisyöttökenttä.
7. **PÄIVÄ**: Syöttökenttä päivien valitsemiseksi.
8. **AIKA**: Syöttökenttä aikojen valitsemiseksi.
9. **PAIKALLINEN_AIKAMÄÄRÄ**: Syöttökenttä paikallisen päivämäärän ja ajan valitsemiseksi.
10. **VÄRI**: Syöttökenttä värin valitsemiseksi.

### Viestityyppi {#message-type}

`InputDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ-suunnittelujärjestelmän sääntöjen mukaisesti.

1. `YKSINKERTAINEN`: Näyttää viestin ilman ikonia käyttäen oletusteemaa.
2. `VIRHE`: Näyttää virheikoni viestin vieressä virheteemalla.
3. `KYSYMYS`: Näyttää kysymysmerkin ikonina viestin vieressä, käyttäen ensisijaisteemaa.
4. `VAROITUS`: Näyttää varoitusikoni viestin vieressä varoitusteemalla.
5. `TIETO`: Näyttää tietoikonin viestin vieressä, käyttäen tietoteemaa.

Alla olevassa esimerkissä käyttäjältä kysytään salasanansa syöttämistä päästäkseen sovellukseen. Jos kirjautuminen epäonnistuu, käyttäjältä kysytään uudelleen.

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Tulos {#result}

`InputDialog` palauttaa käyttäjän syötteen merkkijonona. Jos käyttäjä sulkee dialogin antamatta syötettä, tulos on `null`.

:::important
Palautettu merkkijono palautetaan `show()`-menetelmästä tai vastaavasta `OptionDialog`-menetelmästä, kuten alla on esitetty. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Ole hyvä ja syötä ikäsi:", "Ikäsyöttö", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("Annon: " + result, "Syöte vastaanotettu");
} else {
    OptionDialog.showMessageDialog("Ei syötettä vastaanotettu", "Syöte peruutettu");
}
```

## Oletusarvo {#default-value}

`InputDialog` mahdollistaa oletusarvon määrittämisen, joka näkyy syöttökentässä, kun dialogi näytetään. Tämä voi tarjota käyttäjille ehdotuksen tai aiemmin syötetyn arvon.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Ole hyvä ja syötä nimesi:", "Nimesyöttö", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Aikakatkaisu {#timeout}

`InputDialog` mahdollistaa aikakatkaisin kestoon asettamisen, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille syötepyynnöille tai toiminnoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää dialogin aikakatkaisun käyttämällä `setTimeout(int timeout)`-menetelmää. Aikakatkaisun kesto on sekunteina. Jos määritetty aika umpeutuu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Ole hyvä ja syötä nimesi:", "Nimesyöttö", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "Annon: " + result, "Syöte vastaanotettu", "OK", MessageDialog.MessageType.INFO);
```

## Best practices {#best-practices}

1. **Selkeät ja Ytimekkäät Kehotukset**: Varmista, että kehotusviesti selkeästi selittää, mitä tietoa käyttäjältä pyydetään.
2. **Sopivat Syöttötyypit**: Valitse syöttötyypit, jotka vastaavat vaadittavaa tietoa varmistaaksesi tarkka ja asiaankuuluva käyttäjäsyöte.
3. **Loogiset Oletusarvot**: Aseta oletusarvot, jotka tarjoavat hyödyllisiä ehdotuksia tai aiempia syötteitä käyttäjäsyötteen sujuvoittamiseksi.
4. **Aikakatkaisun Huolellinen Käyttö**: Aseta aikakatkaisuja ei-kriittisille syötepyynnöille varmistaen, että käyttäjillä on tarpeeksi aikaa antaa vaadittavat tiedot.
5. **Ylikäytön Vähentäminen**: Käytä syöttödialogeja säästeliäästi käyttäjien turhautumisen välttämiseksi. Varaudu niihin toimiin, jotka vaativat erityistä käyttäjäsyötettä.
