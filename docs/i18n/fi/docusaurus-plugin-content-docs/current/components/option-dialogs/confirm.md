---
title: Confirm
sidebar_position: 5
_i18n_hash: 712808f446f16655074e93cda2231286
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` on modal-dialogi, joka on suunniteltu antamaan käyttäjän valita yhdestä korkeintaan kolme vaihtoehtoa. Dialogi estää sovelluksen suorituksen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

<!-- INTRO_END -->

## Käytöt {#usages}

`ConfirmDialog` tarjoaa tavan pyytää käyttäjiltä vahvistusta tai valitsemaan useista vaihtoehdoista, kuten `Kyllä/Ei` tai `OK/Peruuta`, varmistaen, että he tunnustavat ja vahvistavat toimintonsa.

<ComponentDemo
path='/webforj/confirmdialogconstructor'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java']}
height='350px'
/>

## Tyypit {#types}

### Vaihtoehtotyyppi {#option-type}

`ConfirmDialog` tukee seuraavia vaihtoehtotyyppejä, jotka määrittävät dialogissa näkyvät painikkeet:

1. **`OK`**: Näyttää `OK`-painikkeen.
2. **`OK_CANCEL`**: Näyttää `OK`- ja `Peruuta`-painikkeet.
3. **`ABORT_RETRY_IGNORE`**: Näyttää `Keskeytä`, `Yritä uudelleen` ja `Ohita` -painikkeet.
4. **`YES_NO_CANCEL`**: Näyttää `Kyllä`, `Ei` ja `Peruuta` -painikkeet.
5. **`YES_NO`**: Näyttää `Kyllä` ja `Ei` -painikkeet.
6. **`RETRY_CANCEL`**: Näyttää `Yritä uudelleen` ja `Peruuta` -painikkeet.
7. **`CUSTOM`**: Näyttää mukautettuja painikkeita, kuten on määritetty.

### Viestintyyppi {#message-type}

`ConfirmDialog` tukee seuraavia viestintyppejä. Kun määrität tyypin, dialogi näyttää kuvakkeen viestin vieressä ja dialogin teema päivittyy webforJ -suunnittelujärjestelmän sääntöjen mukaan.

1. `PLAIN`: Näyttää viestin ilman kuvaketta, käyttäen oletusteemaa.
2. `ERROR`: Näyttää virhekuvakkeen viestin vieressä virheteema käytössä.
3. `QUESTION`: Näyttää kysymysmerkkikuvakkeen viestin vieressä, käyttäen ensisijaista teemaa.
4. `WARNING`: Näyttää varoituskuvakkeen viestin vieressä varoitusteema käytössä.
5. `INFO`: Näyttää infoikuvakkeen viestin vieressä, käyttäen info-teemaa.

Seuraavassa esimerkissä koodi määrittää vahvistusdialogin tyypiksi `CUSTOM` mukautetulla otsikolla ja viestillä.

<ComponentDemo
path='/webforj/confirmdialogoptions'
files={['src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java']}
height='350px'
/>

## Tulos {#result}

`ConfirmDialog` palauttaa tuloksen käyttäjän vuorovaikutuksen perusteella dialogin kanssa. Tämä tulos ilmaisee, mikä painike käyttäjä napsautti tai suljettiinko dialogi aikakatkaisun vuoksi.

:::important
Tulos palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista, kuten alla on osoitettu. 
:::

`ConfirmDialog.Result` enum sisältää seuraavat mahdolliset tulokset:

1. **`OK`**: Käyttäjä napsautti `OK`-painiketta.
2. **`CANCEL`**: Käyttäjä napsautti `PERUUTA`-painiketta.
3. **`YES`**: Käyttäjä napsautti `KYLLÄ`-painiketta.
4. **`NO`**: Käyttäjä napsautti `EI`-painiketta.
5. **`ABORT`**: Käyttäjä napsautti `KESKEYTÄ`-painiketta.
6. **`RETRY`**: Käyttäjä napsautti `YRITÄ UUDELLEEN`-painiketta.
7. **`IGNORE`**: Käyttäjä napsautti `OHITA`-painiketta.
8. **`FIRST_CUSTOM_BUTTON`**: Käyttäjä napsautti ensimmäistä mukautettua painiketta
9. **`SECOND_CUSTOM_BUTTON`**: Käyttäjä napsautti toista mukautettua painiketta
10. **`THIRD_CUSTOM_BUTTON`**: Käyttäjä napsautti kolmatta mukautettua painiketta
11. **`TIMEOUT`**: Dialogin aikakatkaisuaika umpeutuu.
12. **`UNKNOWN`**: Tuntematon tulos, tyypillisesti käytetään oletus- tai virhetilana.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
  OptionDialog.showMessageDialog("Muutokset hylätty", "Hylätty", "Selvä");
} else {
  OptionDialog.showMessageDialog(
    "Muutokset tallennettu", "Tallennettu", "Selvä", MessageDialog.MessageType.INFO);
}
```

## Oletuspainike {#default-button}

`ConfirmDialog` sallii sinun määrittää oletuspainikkeen, joka on esivalittu, kun dialogi näytetään. Tämä parantaa käyttäjäkokemusta tarjoamalla ehdotetun toiminnan, joka voidaan nopeasti vahvistaa painamalla <kbd>Enter</kbd> näppäintä.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // toinen painike
dialog.show();
```

## Painikkeiden teksti {#buttons-text}

Voit määrittää painikkeiden tekstin käyttämällä `setButtonText(ConfirmDialog.Button button, String text)`-metodia.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Ehdottomasti");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Ei");
dialog.show();
```

## HTML-käsittely {#html-processing}

Oletuksena vahvistusdialogi käsittelee ja renderoi HTML-sisältöä. Voit poistaa tämän ominaisuuden käytöstä määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
  "<b>Oletko varma?</b>", "Vahvista",
  ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`ConfirmDialog` sallii sinun määrittää aikakatkaisun, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille vahvistuksille tai toimille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää dialogin aikakatkaisun käyttämällä `setTimeout(int timeout)`-metodia. Aikakatkaisuaika on sekunneissa. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Käytit liikaa aikaa päätöksentekoon", "Aikakatkaisu", "Selvä",
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

1. **Selkeät ja ytimekkäät kehykset**: Varmista, että kehysviesti selvästi selittää, mitä toimintoa käyttäjältä pyydetään vahvistamaan. Vältä epäselvyyksiä.
2. **Sopivat vaihtoehtotyypit**: Valitse vaihtoehtotyypit, jotka vastaavat toiminnan kontekstia. Yksinkertaisille kyllä/ei-päätöksille, käytä yksinkertaisia vaihtoehtoja. Monimutkaisemmissa tilanteissa tarjoa lisäpainikkeita, kuten "Peruuta", jotta käyttäjät voivat peruuttaa ilman, että heidän täytyy tehdä valinta.
3. **Looginen oletuspainike**: Aseta oletuspainike, joka vastaa todennäköisintä tai suositeltua käyttäjätoimintoa päätöksenteon sujuvoittamiseksi.
4. **Johdonmukainen teema**: Yhdistele dialogin ja painikkeiden teemat sovelluksesi suunnittelun kanssa yhtenäisen käyttäjäkokemuksen saavuttamiseksi.
5. **Sijoita aikakatkaisua harkiten**: Määritä aikakatkaisuja ei-kriittisille vahvistuksille, varmistaen, että käyttäjillä on riittävästi aikaa lukea ja ymmärtää kehotteet.
6. **Vältä liiallista käyttöä**: Käytä vahvistusdialogeja säästeliäästi käyttäjien turhautumisen välttämiseksi. Varaudu niitä kriittisiin toimiin, jotka vaativat käyttäjän nimenomaista vahvistusta.
