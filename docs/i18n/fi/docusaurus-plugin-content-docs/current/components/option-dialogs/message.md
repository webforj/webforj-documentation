---
sidebar_position: 30
title: Message
_i18n_hash: 633e8c1297144da8b39cfd7ca2e77e5c
---
# Viestidiakoni

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

`MessageDialog` on modaalidiakoni, joka on suunniteltu näyttämään viesti käyttäjälle, ja siinä on `OK`-painike, jolla dialogi voidaan sulkea. Se estää sovelluksen suorituksen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu ajan loputtua.

```java
OptionDialog.showMessageDialog("Hello World!");
```

## Käyttö {#usages}

Viestidiakoni tarjoaa tavan näyttää informatiivisia hälytyksiä, kuten ilmoituksia, päivityksiä tai yksinkertaisia viestejä, jotka vaativat vain käyttäjän tunnustavan ne ilman, että syöttöä tarvitaan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Viestityyppi {#message-type}

`MessageDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivitetään webforJ-suunnittelujärjestelmän sääntöjen mukaisesti.

1. `PLAIN`: Näyttää viestin ilman ikonia käyttäen oletusteemaa.
2. `ERROR`: Näyttää virheikoni viestin vieressä soveltaen virheteemaa.
3. `QUESTION`: Näyttää kysymysmerkin ikonin viestin vieressä käyttäen ensisijaista teemaa.
4. `WARNING`: Näyttää varoitusikonin viestin vieressä soveltaen varoitusteemaa.
5. `INFO`: Näyttää infoikoni viestin vieressä käyttäen info-teemaa.

Seuraavassa esimerkissä koodi määrittää varoitustyypin `WARNING` viestidiakoniin mukautetulla otsikolla ja viestillä.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialogi & Paineteema
Oletuksena dialogin teema määräytyy viestityypin mukaan. Voit mukauttaa dialogin teemaa käyttämällä `setTheme(Theme theme)` metodia ja säätää paineteeman erikseen `setButtonTheme(ButtonTheme theme)` metodilla luodaksesi erilaisia versioita.
:::

## Paineteksti {#button-text}

Voit määrittää dialogin painikkeen tekstin käyttämällä `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hello World!", "Otsikko", "Ymmärrän");
```

## HTML-käsittely {#html-processing}

Oletuksena viestidiakoni käsittelee ja renderoi HTML-sisältöä. Voit poistaa tämän ominaisuuden käytöstä määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`MessageDialog` sallii sinun asettaa aikakatkaisun keston, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille ilmoituksille tai tiedoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää dialogin aikakatkaisun käyttämällä `setTimeout(int timeout)` metodia. Aikakatkaisun kesto on sekunneissa. Jos määritetty aika kuluu ilman mitään käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Tämä dialogi sulkeutuu pian", "Aikakatkaisu");
dialog.setTimeout(2);
dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät viestit**: Pidä viestit lyhyinä ja ytimekkäinä, ja vältä teknistä jargonia; käytä käyttäjäystävällistä kieltä.
2. **Sopivat viestityypit**:
   - Käytä `ERROR` kriittisille ongelmille.
   - Käytä `WARNING` varoitusilmoituksille.
   - Käytä `INFO` yleiselle tiedolle.
3. **Johdonmukainen teema**: Yhdistele dialogin ja painikkeiden teemat sovelluksesi suunnittelun mukaisiksi.
4. **Aikakatkaisun harkittu käyttö**: Aseta aikakatkaisuja ei-kriittisille ilmoituksille ja varmista, että käyttäjillä on riittävästi aikaa lukea viesti.
5. **Vältä liiallista käyttöä**: Käytä dialogeja säästeliäästi estämiseksi käyttäjän turhautumista ja säästä niitä tärkeille viesteille, jotka vaativat käyttäjän toimintaa tai tunnustusta.
