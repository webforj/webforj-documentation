---
sidebar_position: 5
title: Confirm
_i18n_hash: 1a5d5c10371b3d751853eb3c3bcbe66f
---
# Vahvista-dialogi

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` on modaalinen dialogi, joka on suunniteltu sallimaan käyttäjän valita yhdestä enintään 3 vaihtoehdosta. Dialogi estää sovelluksen suorituksen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Vahvistatko?",
    "Vahvistus",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## Käytöt {#usages}

`ConfirmDialog` tarjoaa tavan kysyä käyttäjiltä vahvistusta tai valita useista vaihtoehdoista, kuten `Kyllä/Ei` tai `OK/Peruuta`, varmistaen, että he tunnustavat ja vahvistavat toimintonsa.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Tyypit {#types}

### Vaihtoehtotyyppi {#option-type}

`ConfirmDialog` tukee seuraavia vaihtoehtotyyppejä, jotka määrittävät dialogissa näkyvät painikkeet:

1. **`OK`**: Näyttää `OK`-painikkeen.
2. **`OK_CANCEL`**: Näyttää `OK`- ja `Peruuta`-painikkeet.
3. **`ABORT_RETRY_IGNORE`**: Näyttää `Keskeytä`, `Uudelleen` ja `Ohita` -painikkeet.
4. **`YES_NO_CANCEL`**: Näyttää `Kyllä`, `Ei` ja `Peruuta` -painikkeet.
5. **`YES_NO`**: Näyttää `Kyllä` ja `Ei` -painikkeet.
6. **`RETRY_CANCEL`**: Näyttää `Uudelleen` ja `Peruuta` -painikkeet.
7. **`CUSTOM`**: Näyttää mukautettuja painikkeita kuten määritelty.

### Viestintyyppi {#message-type}

`ConfirmDialog` tukee seuraavia viestintyyppisiä. Kun määrität tyypin, dialogi näyttää kuvakkeen viestin vieressä, ja dialogin teema päivittyy webforJ:n suunnittelujärjestelmän sääntöjen mukaan.

1. `PLAIN`: Näyttää viestin ilman kuvaketta, käyttäen oletusteemaa.
2. `ERROR`: Näyttää virhekukkeen viestin vieressä virheteeman kanssa.
3. `QUESTION`: Näyttää kysymysmerkin kuvakkeen viestin vieressä, käyttäen pääteemaa.
4. `WARNING`: Näyttää varoituskuvakkeen viestin vieressä varoitusteeman kanssa.
5. `INFO`: Näyttää infokuvakkeen viestin vieressä, käyttäen infotyyppiä.

Seuraavassa esimerkissä koodi määrittää vahvistusdialogin tyypiksi `CUSTOM` mukautetun otsikon ja viestin kanssa.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Tulos {#result}

`ConfirmDialog` palauttaa tuloksen käyttäjän vuorovaikutuksen perusteella dialogin kanssa. Tämä tulos osoittaa, mitä painiketta käyttäjä napsautti tai sulkeutuiko dialogi aikakatkaisun vuoksi.

:::important
Tulos palautuu `show()`-metodista tai vastaavasta `OptionDialog`-metodista kuten alla on esitetty. 
:::

`ConfirmDialog.Result` enum sisältää seuraavat mahdolliset tulokset:

1. **`OK`**: Käyttäjä napsautti `OK`-painiketta.
2. **`CANCEL`**: Käyttäjä napsautti `CANCEL`-painiketta.
3. **`YES`**: Käyttäjä napsautti `Kyllä`-painiketta.
4. **`NO`**: Käyttäjä napsautti `Ei`-painiketta.
5. **`ABORT`**: Käyttäjä napsautti `Keskeytä`-painiketta.
6. **`RETRY`**: Käyttäjä napsautti `Uudelleen`-painiketta.
7. **`IGNORE`**: Käyttäjä napsautti `Ohita`-painiketta.
8. **`FIRST_CUSTOM_BUTTON`**: Käyttäjä napsautti ensimmäistä mukautettua painiketta.
9. **`SECOND_CUSTOM_BUTTON`**: Käyttäjä napsautti toista mukautettua painiketta.
10. **`THIRD_CUSTOM_BUTTON`**: Käyttäjä napsautti kolmatta mukautettua painiketta.
11. **`TIMEOUT`**: Dialogi aikakatkaistuu.
12. **`UNKNOWN`**: Tuntematon tulos, käytetään tyypillisesti oletus- tai virhetilanteessa.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Muutokset hylätty", "Hylätty", "Selvä");
} else {
    OptionDialog.showMessageDialog(
        "Muutokset tallennettu", "Tallennettu", "Selvä", MessageDialog.MessageType.INFO);
}
```

## Oletuspainike {#default-button}

`ConfirmDialog` mahdollistaa oletuspainikkeen määrittämisen, joka on esivalittu, kun dialogi näytetään. Tämä parantaa käyttäjäkokemusta tarjoamalla ehdotetun toiminnon, joka voidaan nopeasti vahvistaa painamalla <kbd>Enter</kbd> -näppäintä.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // toinen painike
dialog.show();
```

## Painiketeksti {#buttons-text}

Voit määrittää painikkeiden tekstit käyttämällä `setButtonText(ConfirmDialog.Button button, String text)` -metodia.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Ehdottomasti");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Ei");
dialog.show();
```

## HTML-käsittely {#html-processing}

Oletusarvoisesti vahvistusdialogi käsittelee ja renderoi HTML-sisältöä. Voit poistaa tämän ominaisuuden käytöstä määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Oletko varma?</b>", "Vahvista",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`ConfirmDialog` mahdollistaa aikakatkaisuajan asettamisen, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille vahvistuksille tai toiminnoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää dialogin aikakatkaisun käyttämällä `setTimeout(int timeout)` -metodia. Aikakatkaisuaika on sekunteina. Jos määritetty aika kulkee ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Kesti liian kauan päättää", "Aikakatkaisu", "Selvä",
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

1. **Selkeät ja ytimekkäät kehotteet**: Varmista, että kehoteviesti selittää selkeästi, mitä toimintoa käyttäjältä kysytään vahvistettavaksi. Vältä epäselvyyksiä.
2. **Sopivat vaihtoehtotyypit**: Valitse vaihtoehtotyypit, jotka vastaavat toiminnan kontekstia. Yksinkertaisille kyllä/ei-päätöksille käytä suoraviivaisia vaihtoehtoja. Monimutkaisemmissa skenaarioissa tarjoa lisäpainikkeita, kuten "Peruuta", jotta käyttäjät voivat perääntyä ilman valintaa.
3. **Looginen oletuspainike**: Aseta oletuspainike, joka vastaa todennäköisintä tai suositeltua käyttäjän toimintoa päätöksenteon sujuvoittamiseksi.
4. **Johdonmukainen teema**: Yhdistele dialogin ja painikkeiden teemat sovelluksesi muotoilun kanssa yhtenäisen käyttäjäkokemuksen saavuttamiseksi.
5. **Aikakatkaisun järkevä käyttö**: Aseta aikakatkaisuja ei-kriittisille vahvistuksille, varmistaen, että käyttäjillä on riittävästi aikaa lukea ja ymmärtää kehote.
6. **Vähennä liiallista käytön**: Käytä vahvistusdialogeja säästeliäästi käyttäjäturhautumisen välttämiseksi. Varaa niitä kriittisille toimille, jotka vaativat eksplisiittistä käyttäjän vahvistusta.
