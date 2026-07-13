---
title: Confirm
sidebar_position: 5
description: >-
  Show a blocking ConfirmDialog with up to three options, configurable button
  sets, message types, and timeout behavior.
_i18n_hash: 0d5432d42be98a19b1a6938b306b0442
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` on modaalinen dialogi, joka on suunniteltu antamaan käyttäjän valita yhdestä enintään 3 vaihtoehdosta. Dialogi estää sovelluksen suorituksen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

<!-- INTRO_END -->

## Käyttö {#usages}

`ConfirmDialog` tarjoaa tavan pyytää käyttäjiltä vahvistusta tai valita useiden vaihtoehtojen välillä, kuten `Kyllä/Ei` tai `OK/Sulje`, varmistaen, että he tunnustavat ja vahvistavat toimintonsa.

<ComponentDemo
path='/webforj/confirmdialogconstructor'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java']}
height='350px'
/>

## Tyypit {#types}

### Vaihtoehtotyyppi {#option-type}

`ConfirmDialog` tukee seuraavia vaihtoehtotyyppejä, jotka määrittävät dialogissa näytettävät painikkeet:

1. **`OK`**: Näyttää `OK`-painikkeen.
2. **`OK_CANCEL`**: Näyttää `OK`- ja `Peruuta`-painikkeet.
3. **`ABORT_RETRY_IGNORE`**: Näyttää `Keskeytä`, `Uudelleen` ja `Ohita` -painikkeet.
4. **`YES_NO_CANCEL`**: Näyttää `Kyllä`, `Ei` ja `Peruuta` -painikkeet.
5. **`YES_NO`**: Näyttää `Kyllä` ja `Ei` -painikkeet.
6. **`RETRY_CANCEL`**: Näyttää `Uudelleen` ja `Peruuta` -painikkeet.
7. **`CUSTOM`**: Näyttää mukautetut painikkeet määriteltyjen mukaan.

### Viestintyyppi {#message-type}

`ConfirmDialog` tukee seuraavia viestintyyppiä. Kun konfiguroit tyyppiä, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ-muotoilun sääntöjen mukaisesti.

1. `PLAIN`: Näyttää viestin ilman ikonia, käyttäen oletusteemaa.
2. `ERROR`: Näyttää virheikoni viestin vieressä virheteema käytössä.
3. `QUESTION`: Näyttää kysymysmerkin ikonin viestin vieressä, käyttäen päätoteemaa.
4. `WARNING`: Näyttää varoitusikoni viestin vieressä varoitusteema käytössä.
5. `INFO`: Näyttää info-ikonin viestin vieressä, käyttäen info-teemaa.

Seuraavassa esimerkissä koodi konfiguroi vahvistusdialogin tyypiksi `CUSTOM`, jossa on mukautettu otsikko ja viesti.

<ComponentDemo
path='/webforj/confirmdialogoptions'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java']}
height='350px'
/>

## Tulos {#result}

`ConfirmDialog` palauttaa tuloksen käyttäjän vuorovaikutuksen perusteella dialogin kanssa. Tämä tulos osoittaa, mikä painike käyttäjä klikattiin tai suljettiinko dialogi aikakatkaisun vuoksi.

:::important
Tulos palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on esitetty.
:::

`ConfirmDialog.Result` enum sisältää seuraavat mahdolliset tulokset:

1. **`OK`**: Käyttäjä napsautti `OK`-painiketta.
2. **`CANCEL`**: Käyttäjä napsautti `PERUUTA`-painiketta.
3. **`YES`**: Käyttäjä napsautti `KYLLÄ`-painiketta.
4. **`NO`**: Käyttäjä napsautti `EI`-painiketta.
5. **`ABORT`**: Käyttäjä napsautti `KESKEYTÄ`-painiketta.
6. **`RETRY`**: Käyttäjä napsautti `UUDEN`-painiketta.
7. **`IGNORE`**: Käyttäjä napsautti `OHITA`-painiketta.
8. **`FIRST_CUSTOM_BUTTON`**: Käyttäjä napsautti ensimmäistä mukautettua painiketta.
9. **`SECOND_CUSTOM_BUTTON`**: Käyttäjä napsautti toista mukautettua painiketta.
10. **`THIRD_CUSTOM_BUTTON`**: Käyttäjä napsautti kolmatta mukautettua painiketta.
11. **`TIMEOUT`**: Dialogi aikakatkaisee.
12. **`UNKNOWN`**: Tuntematon tulos, jota käytetään tyypillisesti oletus- tai virhetilassa.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Muutokset hylättiin", "Hylätty", "Selvä");
} else {
  OptionDialog.showMessageDialog(
    "Muutokset tallennettu", "Tallennettu", "Selvä", MessageDialog.MessageType.INFO);
}
```

## Oletuspainike {#default-button}

`ConfirmDialog` sallii sinun määrittää oletuspainikkeen, joka on esivalittu, kun dialogi näytetään. Tämä parantaa käyttäjäkokemusta tarjoamalla suositellun toiminnan, joka voidaan nopeasti vahvistaa painamalla <kbd>Enter</kbd>-näppäintä.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // toinen painike
dialog.show();
```

## Painikkeiden teksti {#buttons-text}

Voit konfiguroida painikkeiden tekstiä käyttämällä `setButtonText(ConfirmDialog.Button button, String text)`-metodia.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Ehdottomasti");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Ei");
dialog.show();
```

## HTML-käsittely {#html-processing}

Oletuksena `ConfirmDialog` käsittelee ja renderoi HTML-sisältöä. Voit poistaa tämän ominaisuuden käytöstä määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Oletko varma?</b>", "Vahvista",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`ConfirmDialog` sallii sinun asettaa aikakatkaisuajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille vahvistuksille tai toiminnoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää dialogin aikakatkaisun käyttämällä `setTimeout(int timeout)`-metodia. Aikakatkaisu on sekunteina. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Käytit liikaa aikaa päättääksesi", "Aikakatkaisu", "Selvä",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Klikkasit Kyllä", "Kyllä", "Selvä",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Klikkasit Ei", "Ei", "Selvä",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kehotteet**: Varmista, että kehotteessa on selvästi ilmoitettu, mitä toimintoa käyttäjältä pyydetään vahvistamaan. Vältä epäselvyyksiä.
2. **Sopivat vaihtoehtotyypit**: Valitse vaihtoehtotyypit, jotka vastaavat toiminnan kontekstia. Yksinkertaisille kyllä/ei-päätöksille käytä suoraviivaisia vaihtoehtoja. Monimutkaisemmissa tilanteissa tarjoa lisäpainikkeita, kuten "Peruuta", jotta käyttäjät voivat perääntyä ilman päätöksentekoa.
3. **Looginen oletuspainike**: Aseta oletuspainike, joka vastaa todennäköisintä tai suositeltua käyttäjän toimintoa päätöksenteon yksinkertaistamiseksi.
4. **Johdonmukainen teema**: Yhdistele dialogin ja painikkeiden teemat sovelluksesi muotoilun kanssa yhtenäisen käyttäjäkokemuksen varmistamiseksi.
5. **Viisaasti käytetty aikakatkaisu**: Aseta aikakatkaisuja ei-kriittisille vahvistuksille varmistaaksesi, että käyttäjillä on tarpeeksi aikaa lukea ja ymmärtää kehotteet.
6. **Ylihyödyntämisen minimointi**: Käytä vahvistusdialogeja säästeliäästi käyttäjätyytyväisyyden välttämiseksi. Varaa niitä kriittisille toimille, jotka vaativat erillistä käyttäjän vahvistusta.
