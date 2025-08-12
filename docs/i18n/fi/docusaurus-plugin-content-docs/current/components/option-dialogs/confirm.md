---
sidebar_position: 5
title: Confirm
_i18n_hash: 99babacee9e77d9376b00554e47d7ca3
---
# Vahvistusdialogi

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjän valita yhdestä enintään 3 vaihtoehdosta. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Vahvistatko?",
    "Vahvistus",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## Käytännöt {#usages}

`ConfirmDialog` tarjoaa tavan kysyä käyttäjiltä vahvistusta tai valita useiden vaihtoehtojen välillä, kuten `Kyllä/Ei` tai `OK/Peruuta`, varmistaen, että he tunnustavat ja vahvistavat toimintonsa.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Tyypit {#types}

### Vaihtoehtotyyppi {#option-type}

`ConfirmDialog` tukee seuraavia vaihtoehtotyyppejä, jotka määrittävät dialogissa näytettävät painikkeet:

1. **`OK`**: Näyttää `OK`-painikkeen.
2. **`OK_CANCEL`**: Näyttää `OK`- ja `Peruuta`-painikkeet.
3. **`ABORT_RETRY_IGNORE`**: Näyttää `Keskeytä`, `Yritä uudelleen` ja `Ignoroi` -painikkeet.
4. **`YES_NO_CANCEL`**: Näyttää `Kyllä`, `Ei` ja `Peruuta` -painikkeet.
5. **`YES_NO`**: Näyttää `Kyllä` ja `Ei` -painikkeet.
6. **`RETRY_CANCEL`**: Näyttää `Yritä uudelleen` ja `Peruuta` -painikkeet.
7. **`CUSTOM`**: Näyttää mukautettuja painikkeita määritellyn mukaisesti.

### Viestintyyppi {#message-type}

`ConfirmDialog` tukee seuraavia viestintyyppejä. Kun määrität tyyppiä, dialogi näyttää kuvakkeen viestin vieressä, ja dialogin teema päivittyy webforJ:n muotoilujärjestelmän sääntöjen mukaisesti.

1. `PLAIN`: Näyttää viestin ilman kuvaketta, käyttäen oletusteemaa.
2. `ERROR`: Näyttää virhekuvakkeen viestin vieressä virheteeman ollessa aktiivinen.
3. `QUESTION`: Näyttää kysymysmerkin kuvakkeen viestin vieressä, käyttäen ensisijaista teemaa.
4. `WARNING`: Näyttää varoituskuvakkeen viestin vieressä varoitusteeman ollessa aktiivinen.
5. `INFO`: Näyttää infokuvakkeen viestin vieressä, käyttäen info-teemaa.

Seuraavassa esimerkissä koodi määrittää vahvistusdialogin tyyppiä `CUSTOM`, jossa on mukautettu otsikko ja viesti.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Tulos {#result}

`ConfirmDialog` palauttaa tuloksen käyttäjän vuorovaikutuksen perusteella dialogin kanssa. Tämä tulos osoittaa, mikä painike käyttäjää napsautettiin tai suljettiinko dialogi aikakatkaisun vuoksi.

:::important
Tulos palautetaan `show()`-menetelmällä tai vastaavalla `OptionDialog`-menetelmällä, kuten alla on esitetty. 
:::

`ConfirmDialog.Result`-enum sisältää seuraavat mahdolliset tulokset:

1. **`OK`**: Käyttäjä napsautti `OK`-painiketta.
2. **`CANCEL`**: Käyttäjä napsautti `CANCEL`-painiketta.
3. **`YES`**: Käyttäjä napsautti `Kyllä`-painiketta.
4. **`NO`**: Käyttäjä napsautti `Ei`-painiketta.
5. **`ABORT`**: Käyttäjä napsautti `Keskeytä`-painiketta.
6. **`RETRY`**: Käyttäjä napsautti `Yritä uudelleen`-painiketta.
7. **`IGNORE`**: Käyttäjä napsautti `Ignoroi`-painiketta.
8. **`FIRST_CUSTOM_BUTTON`**: Käyttäjä napsautti ensimmäistä mukautettua painiketta.
9. **`SECOND_CUSTOM_BUTTON`**: Käyttäjä napsautti toista mukautettua painiketta.
10. **`THIRD_CUSTOM_BUTTON`**: Käyttäjä napsautti kolmatta mukautettua painiketta.
11. **`TIMEOUT`**: Dialogin aikakatkaisu.
12. **`UNKNOWN`**: Tuntematon tulos, jota käytetään tyypillisesti oletus- tai virhetilassa.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Muutokset hylättiin", "Hylätty", "Selvä");
} else {
    OptionDialog.showMessageDialog(
        "Muutokset tallennettiin", "Tallennettu", "Selvä", MessageDialog.MessageType.INFO);
}
```

## Oletuspainike {#default-button}

`ConfirmDialog` antaa sinun määrittää oletuspainikkeen, joka on esivalittu, kun dialogi näytetään. Tämä parantaa käyttäjäkokemusta tarjoamalla ehdotetun toiminnan, jonka voi nopeasti vahvistaa painamalla <kbd>Enter</kbd>-näppäintä.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // toinen painike
dialog.show();
```

## Painikkeiden teksti {#buttons-text}

Voit määrittää painikkeiden tekstin käyttämällä `setButtonText(ConfirmDialog.Button button, String text)` -menetelmää.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Ehdottomasti");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Ei");
dialog.show();
```

## HTML-käsittely {#html-processing}

Oletuksena vahvistusdialogi käsittelee ja renderoi HTML-sisältöä. Voit sammuttaa tämän ominaisuuden määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Oletko varma?</b>", "Vahvista",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`ConfirmDialog` antaa sinun määrittää aikakatkaisua ajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille vahvistuksille tai toimille, jotka eivät vaadi käyttäjän heti vuorovaikutusta.

Voit määrittää aikakatkaisun dialogille käyttämällä `setTimeout(int timeout)` -menetelmää. Aikakatkaisuaika on sekunneissa. Jos määritetty aika kuluu ilman, että käyttäjä vuorovaikuttaa, dialogi sulkeutuu automaattisesti.

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

1. **Selkeät ja ytimekkäät kehut**: Varmista, että kehupäivämäärä selkeästi selittää, mitä toimintoa käyttäjältä kysytään vahvistettavaksi. Vältä epäselvyyksiä.
2. **Sopivat vaihtoehtotyypit**: Valitse vaihtoehtotyypit, jotka vastaavat toiminnan kontekstia. Yksinkertaisiin kyllä/ei-päätöksiin käytä suoraviivaisia vaihtoehtoja. Monimutkaisemmissa tilanteissa tarjoa lisäpainikkeita, kuten "Peruuta", jotta käyttäjät voivat peruuttaa valinnat tekemättä päätöstä.
3. **Looginen oletuspainike**: Aseta oletuspainike, joka vastaa todennäköisintä tai suositeltua käyttäjän toimintaa päätöksenteon sujuvoittamiseksi.
4. **Johdonmukainen teema**: Yhdistele dialogin ja painikkeiden teemat sovelluksesi muotoilun kanssa, jotta saavutetaan yhtenäinen käyttäjäkokemus.
5. **Käytä aikakatkaisuja harkiten**: Aseta aikakatkaisuja ei-kriittisille vahvistuksille varmistaen, että käyttäjillä on riittävästi aikaa lukea ja ymmärtää kehupäivämäärät.
6. **Vähennä käyttöä**: Käytä vahvistusdialogeja säästeliäästi käyttäjätyytyväisyyden välttämiseksi. Varaudu niitä kriittisiin toimintoihin, jotka vaativat käyttäjän nimenomaista vahvistusta.
