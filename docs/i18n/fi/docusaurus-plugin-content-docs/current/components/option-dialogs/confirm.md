---
title: Confirm
sidebar_position: 5
_i18n_hash: f55c50a799ee979b4bd4dfd24ba56a19
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` on modaalinen dialogi, joka on suunniteltu mahdollistamaan käyttäjän valita yhden enintään kolmesta vaihtoehdosta. Dialogi estää ohjelman suorittamisen siihen asti, kun käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

<!-- INTRO_END -->

## Käyttötarkoitukset {#usages}

`ConfirmDialog` tarjoaa tavan kysyä käyttäjiltä vahvistusta tai valita useiden vaihtoehtojen, kuten `Kyllä/Ei` tai `OK/Peru`, välillä, varmistaen että he tunnustavat ja vahvistavat toimintansa.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Tyypit {#types}

### Vaihtoehtotyyppi {#option-type}

`ConfirmDialog` tukee seuraavia vaihtoehtotyyppejä, jotka määrittävät dialogissa näytettävät painikkeet:

1. **`OK`**: Näyttää `OK`-painikkeen.
2. **`OK_CANCEL`**: Näyttää `OK`- ja `Peru`-painikkeet.
3. **`ABORT_RETRY_IGNORE`**: Näyttää `Peru`, `Yritä uudelleen` ja `Ignoroi` -painikkeet.
4. **`YES_NO_CANCEL`**: Näyttää `Kyllä`, `Ei` ja `Peru` -painikkeet.
5. **`YES_NO`**: Näyttää `Kyllä` ja `Ei` -painikkeet.
6. **`RETRY_CANCEL`**: Näyttää `Yritä uudelleen` ja `Peru` -painikkeet.
7. **`CUSTOM`**: Näyttää mukautettuja painikkeita määriteltyjen mukaan.

### Viestityyppi {#message-type}

`ConfirmDialog` tukee seuraavia viestityyppejä. Kun määrität tyyppiä, dialogi näyttää kuvakkeen viestin vieressä, ja dialogin teema päivitetään webforJ:n suunnittelujärjestelmän sääntöjen mukaan.

1. `PLAIN`: Näyttää viestin ilman kuvaketta, käyttäen oletusteemaa.
2. `ERROR`: Näyttää virhekuvakkeen viestin vieressä virheteema käytössä.
3. `QUESTION`: Näyttää kysymysmerkin kuvakkeen viestin vieressä, käyttäen ensisijateemaa.
4. `WARNING`: Näyttää varoituskuvakkeen viestin vieressä varoitusteema käytössä.
5. `INFO`: Näyttää infokuvakkeen viestin vieressä, käyttäen infoteemaa.

Seuraavassa esimerkissä koodi määrittää vahvistusdialogin tyypiksi `CUSTOM` mukautetulla otsikolla ja viestillä.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Tulokset {#result}

`ConfirmDialog` palauttaa tuloksen käyttäjän vuorovaikutuksen perusteella dialogin kanssa. Tämä tulos osoittaa, minkä painikkeen käyttäjä napsautti tai sulkeutuiko dialogi aikakatkaisun vuoksi.

:::important
Tulos palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty. 
:::

`ConfirmDialog.Result` enum sisältää seuraavat mahdolliset tulokset:

1. **`OK`**: Käyttäjä napsautti `OK`-painiketta.
2. **`PERU`**: Käyttäjä napsautti `PERU`-painiketta.
3. **`KYLLÄ`**: Käyttäjä napsautti `KYLLÄ`-painiketta.
4. **`EI`**: Käyttäjä napsautti `EI`-painiketta.
5. **`PERU`**: Käyttäjä napsautti `PERU`-painiketta.
6. **`Yritä uudelleen`**: Käyttäjä napsautti `Yritä uudelleen` -painiketta.
7. **`Ignoroi`**: Käyttäjä napsautti `Ignoroi` -painiketta.
8. **`ENSIMMÄINEN_MUKAUTETTU_PAINIKKE`**: Käyttäjä napsautti ensimmäistä mukautettua painiketta.
9. **`TOINEN_MUKAUTETTU_PAINIKKE`**: Käyttäjä napsautti toista mukautettua painiketta.
10. **`KOLMAS_MUKAUTETTU_PAINIKKE`**: Käyttäjä napsautti kolmatta mukautettua painiketta.
11. **`AIKAKATKAISU`**: Dialogi aikakatkaistaan.
12. **`TUNNISTAMATON`**: Tunnistamaton tulos, käytetään yleensä oletus- tai virhetilanteena.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Muutokset hylätty", "Hylätty", "Selvä");
} else {
  OptionDialog.showMessageDialog(
    "Muutokset tallennettu", "Tallennettu", "Selvä", MessageDialog.MessageType.INFO);
}
```

## Oletuspainike {#default-button}

`ConfirmDialog` mahdollistaa oletuspainikkeen määrittämisen, joka on esivalittu, kun dialogi näytetään. Tämä parantaa käyttäjäkokemusta tarjoamalla suositellun toiminnon, jonka voi nopeasti vahvistaa painamalla <kbd>Enter</kbd> -näppäintä.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // toinen painike
dialog.show();
```

## Painikkeiden teksti {#buttons-text}

Voit määrittää painikkeiden tekstin käyttämällä `setButtonText(ConfirmDialog.Button button, String text)` -metodia.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Ehdottomasti");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Ei");
dialog.show();
```

## HTML-käsittely {#html-processing}

Oletusarvoisesti vahvistusdialogi käsittelee ja renderoi HTML-sisältöä. Voit sulkea tämän ominaisuuden pois määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Oletko varma?</b>", "Vahvista",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`ConfirmDialog` mahdollistaa aikakatkaisuedellytyksen asettamisen, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille vahvistuksille tai toimille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikakatkaisun dialogille käyttämällä `setTimeout(int timeout)` -metodia. Aikakatkaisuaika on sekunneissa. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Käytit liian kauan päättämiseen", "Aikakatkaisu", "Selvä",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Napsautit Kyllä", "Kyllä", "Selvä",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Napsautit Ei", "Ei", "Selvä",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kysymykset**: Varmista, että kysymysviesti selvästi selittää, mitä toimintoa käyttäjältä kysytään vahvistettavaksi. Vältä epäselvyyksiä.
2. **Sopivat vaihtoehtotyypit**: Valitse vaihtoehtotyypit, jotka vastaavat toiminnan kontekstia. Yksinkertaisille kyllä/ei-päätöksille käytä suoraviivaisia vaihtoehtoja. Monimutkaisemmissa tilanteissa tarjoa lisäpainikkeita, kuten "Peru", jotta käyttäjät voivat peruuttaa ilman valintaa.
3. **Looginen oletuspainike**: Aseta oletuspainike, joka vastaa todennäköisintä tai suositeltua käyttäjän toimintoa päätöksenteon tehostamiseksi.
4. **Johdonmukainen teema**: Yhdistele dialogi- ja painikketeemat sovelluksesi suunnitteluun yhtenäisen käyttäjäkokemuksen saavuttamiseksi.
5. **Aikakatkaisujen järkevä käyttö**: Aseta aikakatkaisuja ei-kriittisille vahvistuksille varmistaen, että käyttäjillä on riittävästi aikaa lukea ja ymmärtää kysymys.
6. **Vähennä ylisuorittamista**: Käytä vahvistusdialogeja säästeliäästi käyttäjäpettymysten välttämiseksi. Varaudu niihin kriittisissä toiminnoissa, jotka vaativat käyttäjän nimenomaista vahvistusta.
