---
title: Confirm
sidebar_position: 5
_i18n_hash: d77902dcb6290597159d340941f5e8b7
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

`ConfirmDialog` on modaalinen dialogi, joka on suunniteltu antamaan käyttäjälle mahdollisuus valita enintään kolmen vaihtoehdon joukosta. Dialogi estää sovelluksen suorittamisen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

<!-- INTRO_END -->

## Käyttö {#usages}

`ConfirmDialog` tarjoaa tavan kysyä käyttäjiltä vahvistusta tai valita useiden vaihtoehtojen välillä, kuten `Kyllä/Ei` tai `OK/Peruuta`, varmistaen, että he tunnustavat ja vahvistavat toimintonsa.

<ComponentDemo 
path='/webforj/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Tyyppit {#types}

### Vaihtoehtotyyppi {#option-type}

`ConfirmDialog` tukee seuraavia vaihtoehtotyyppejä, jotka määrittävät dialogissa näytettävät painikkeet:

1. **`OK`**: Näyttää `OK`-painikkeen.
2. **`OK_CANCEL`**: Näyttää `OK`- ja `Peruuta`-painikkeet.
3. **`ABORT_RETRY_IGNORE`**: Näyttää `Keskeytä`, `Yritä uudelleen` ja `Ignoraa`-painikkeet.
4. **`YES_NO_CANCEL`**: Näyttää `Kyllä`, `Ei` ja `Peruuta`-painikkeet.
5. **`YES_NO`**: Näyttää `Kyllä` ja `Ei` -painikkeet.
6. **`RETRY_CANCEL`**: Näyttää `Yritä uudelleen` ja `Peruuta`-painikkeet.
7. **`CUSTOM`**: Näyttää mukautettuja painikkeita määriteltyjen mukaan.

### Viestityyppi {#message-type}

`ConfirmDialog` tukee seuraavia viestityyppejä. Kun määrität tyyppiä, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ:n suunnitelmasysteemin sääntöjen mukaisesti.

1. `PLAIN`: Näyttää viestin ilman ikonia, käyttäen oletusteemaa.
2. `ERROR`: Näyttää virheikon viestin vieressä virheteemalla.
3. `QUESTION`: Näyttää kysymysmerkki-ikonin viestin vieressä, käyttäen pääteemaa.
4. `WARNING`: Näyttää varoitus-ikonin viestin vieressä varoitusteemalla.
5. `INFO`: Näyttää info-ikonin viestin vieressä käyttäen info-teemaa.

Seuraavassa esimerkissä koodi määrittää `CUSTOM`-tyypin vahvistusdialogin, jossa on mukautettu otsikko ja viesti.

<ComponentDemo 
path='/webforj/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Tulos {#result}

`ConfirmDialog` palauttaa tuloksen käyttäjän vuorovaikutuksen perusteella dialogin kanssa. Tämä tulos osoittaa, mitä painiketta käyttäjä napsautti tai että dialogi suljettiin aikakatkaisun vuoksi.

:::important
Tulos palautetaan `show()`-metodista tai vastaavasta `OptionDialog`-metodista kuten alla on esitetty.
:::

`ConfirmDialog.Result`-enum sisältää seuraavat mahdolliset tulokset:

1. **`OK`**: Käyttäjä napsautti `OK`-painiketta.
2. **`CANCEL`**: Käyttäjä napsautti `CANCEL`-painiketta.
3. **`YES`**: Käyttäjä napsautti `YES`-painiketta.
4. **`NO`**: Käyttäjä napsautti `NO`-painiketta.
5. **`ABORT`**: Käyttäjä napsautti `ABORT`-painiketta.
6. **`RETRY`**: Käyttäjä napsautti `RETRY`-painiketta.
7. **`IGNORE`**: Käyttäjä napsautti `IGNORE`-painiketta.
8. **`FIRST_CUSTOM_BUTTON`**: Käyttäjä napsautti ensimmäistä mukautettua painiketta.
9. **`SECOND_CUSTOM_BUTTON`**: Käyttäjä napsautti toista mukautettua painiketta.
10. **`THIRD_CUSTOM_BUTTON`**: Käyttäjä napsautti kolmannetta mukautettua painiketta.
11. **`TIMEOUT`**: Dialogi aikakatkaistaan.
12. **`UNKNOWN`**: Tuntematon tulos, käytetään yleensä oletus- tai virhetilanteena.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Muutokset hylätty", "Hylätty", "Ymmärrän");
} else {
    OptionDialog.showMessageDialog(
        "Muutokset tallennettu", "Tallennettu", "Ymmärrän", MessageDialog.MessageType.INFO);
}
```

## Oletuspainike {#default-button}

`ConfirmDialog` antaa sinun määrittää oletuspainikkeen, joka on esivalittu, kun dialogi näytetään. Tämä parantaa käyttäjäkokemusta tarjoamalla ehdotetun toiminnon, jonka voi nopeasti vahvistaa painamalla <kbd>Enter</kbd>-näppäintä.

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

`ConfirmDialog` antaa sinun asettaa aikakatkaisuajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille vahvistuksille tai toiminnoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikakatkaisun dialogille käyttämällä `setTimeout(int timeout)`-metodia. Aikakatkaisuaika on sekunneissa. Jos määritetty aika kuluu ilman, että käyttäjä vuorovaikuttaa, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Oletko varma?", "Vahvista", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "Kesti liian kauan päättää", "Aikakatkaisu", "Ymmärrän",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "Napsautit Kyllä", "Kyllä", "Ymmärrän",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "Napsautit Ei", "Ei", "Ymmärrän",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät kysymykset**: Varmista, että kysymysviesti selkeästi selittää, mitä toimintoa käyttäjältä pyydetään vahvistamaan. Vältä epäselvyyksiä.
2. **Sopivat vaihtoehtotyypit**: Valitse vaihtoehtotyyppejä, jotka vastaavat toiminnan kontekstia. Yksinkertaisille kyllä/ei-päätöksille käytä suoraviivaisia vaihtoehtoja. Monimutkaisemmissa tilanteissa tarjoa lisäpainikkeita, kuten "Peruuta", jotta käyttäjät voivat peruuttaa ilman valintaa.
3. **Looginen oletuspainike**: Aseta oletuspainike, joka vastaa todennäköisintä tai suositeltavaa käyttäjätoimenpidettä päätöksenteon sujuvoittamiseksi.
4. **Johdonmukainen teema**: Yhdistä dialogin ja painikkeiden teemat sovelluksesi suunnittelun kanssa yhtenäisen käyttäjäkokemuksen saavuttamiseksi.
5. **Aikakatkaisuissa harkinta**: Aseta aikakatkaisuja ei-kriittisiin vahvistuksiin, varmistaen, että käyttäjillä on riittävästi aikaa lukea ja ymmärtää kysymys.
6. **Vähemmän on enemmän**: Käytä vahvistusdialogeja säästeliäästi käyttäjätyytyväisyyden välttämiseksi. Varaa ne kriittisiin toimintoihin, jotka vaativat käyttäjän nimenomaista vahvistusta.
