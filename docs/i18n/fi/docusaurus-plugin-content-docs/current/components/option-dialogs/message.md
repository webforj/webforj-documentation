---
title: Message
sidebar_position: 30
_i18n_hash: 1925f377637c75ea99d29272f31258ff
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

`MessageDialog` on modaalinen dialogi, joka on suunniteltu näyttämään viesti käyttäjälle `OK`-painikkeella dialogin sulkemiseksi. Se estää sovelluksen suorittamisen, kunnes käyttäjä on vuorovaikutuksessa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

<!-- INTRO_END -->

## Käyttöesimerkit {#usages}

Käytä staattista `showMessageDialog`-metodia näyttämään perusviesti.

```java
OptionDialog.showMessageDialog("Hello World!");
```

Saadaksesi enemmän hallintaa dialogin ulkonäöstä ja käyttäytymisestä, luo `MessageDialog`-instanssi suoraan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Viestityyppi {#message-type}

`MessageDialog` tukee seuraavia viestityyppejä. Kun määrität tyyppin, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ-suunnittelujärjestelmän sääntöjen mukaan.

1. `PLAIN`: Näyttää viestin ilman ikonia käyttäen oletusteemaa.
2. `ERROR`: Näyttää virheikon viestin vieressä virheteema käytössä.
3. `QUESTION`: Näyttää kysymysmerkin ikonin viestin vieressä, käyttäen ensisijaista teemaa.
4. `WARNING`: Näyttää varoitusikonin viestin vieressä varoitusteema käytössä.
5. `INFO`: Näyttää infoikonin viestin vieressä käyttäen infoteemaa.

Seuraavassa esimerkissä koodi määrittää varoitusyksikön tyyppiä `WARNING` mukautetulla otsikolla ja viestillä.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialogi & Painikkeen teema
Oletusarvoisesti dialogin teema määräytyy viestityypin mukaan. Voit mukauttaa dialogin teemaa käyttämällä `setTheme(Theme theme)`-metodia ja säätää painiketeemaa erikseen `setButtonTheme(ButtonTheme theme)`-metodin avulla luodaksesi erilaisia versioita.
:::

## Painiketeksti {#button-text}

Voit määrittää dialogin painikkeen tekstin käyttämällä `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hello World!", "Otsikko", "Sain sen");
```

## HTML-käsittely {#html-processing}

Oletusarvoisesti viestidialogi käsittelee ja renderöi HTML-sisältöä. Voit poistaa tämän ominaisuuden käytöstä määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`MessageDialog` antaa sinun asettaa aikakatkaisuajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille ilmoituksille tai tiedoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikakatkaisun dialogille käyttämällä `setTimeout(int timeout)`-metodia. Aikakatkaisu on sekunneissa. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Tämä dialogi sulkeutuu pian", "Aikakatkaisu");
dialog.setTimeout(2);
dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät viestit**: Pidä viestit lyhyinä ja ytimekkäinä ja vältä teknistä jargonia; käytä käyttäjäystävällistä kieltä.
2. **Sopivat viestityypit**:
   - Käytä `ERROR`-tyyppiä kriittisille ongelmille.
   - Käytä `WARNING`-tyyppiä varoituksille.
   - Käytä `INFO`-tyyppiä yleiselle tiedolle.
3. **Yhtenäinen teema**: Riveä dialogin ja painikkeen teemat sovelluksesi suunnittelun mukaisiksi.
4. **Käytä aikakatkaisua harkiten**: Aseta aikakatkaisu ei-kriittisille ilmoituksille ja varmista, että käyttäjillä on riittävästi aikaa lukea viesti.
5. **Vältä liiallista käyttöä**: Käytä dialogeja säästeliäästi estämään käyttäjän turhautumista ja säästä niitä tärkeitä viestejä varten, jotka vaativat käyttäjän toimintaa tai vahvistusta.
