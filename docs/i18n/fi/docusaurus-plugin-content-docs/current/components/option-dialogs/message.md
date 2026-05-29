---
title: Message
sidebar_position: 30
_i18n_hash: b90d101884ed5ce8f6be2604ec637aee
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

`MessageDialog` on modaalinen dialogi, joka on suunniteltu näyttämään viestin käyttäjälle, jossa on `OK`-painike dialogin sulkemiseksi. Se estää sovelluksen suorittamisen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

<!-- INTRO_END -->

## Käyttötapaukset {#usages}

Käytä staattista `showMessageDialog`-metodia yksinkertaisen viestin näyttämiseen.

```java
OptionDialog.showMessageDialog("Hello World!");
```

Jos haluat enemmän hallintaa dialogin ulkoasusta ja toiminnasta, luo `MessageDialog`-instanssi suoraan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Viestityyppi {#message-type}

`MessageDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, dialogi näyttää ikonin viestin vieressä ja dialogin teema päivittyy webforJ:n suunnittelusysteemin sääntöjen mukaan.

1. `PLAIN`: Näyttää viestin ilman ikonia oletusteemalla.
2. `ERROR`: Näyttää virheikoni viestin vieressä, ja virheteema on käytössä.
3. `QUESTION`: Näyttää kysymysmerkin ikonin viestin vieressä, käyttäen ensisijaista teemaa.
4. `WARNING`: Näyttää varoitusikoni viestin vieressä varoitusteeman ollessa käytössä.
5. `INFO`: Näyttää info-ikoni viestin vieressä käyttäen info-teemaa.

Seuraavassa esimerkissä koodi määrittää varoitustyypin `WARNING` olevan viestidialogin, jossa on mukautettu otsikko ja viesti.

<ComponentDemo
path='/webforj/messagedialogtype'
files={['src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java']}
height='350px'
/>

:::tip Dialogi & Painikkeen Teema
Oletuksena dialogin teema määräytyy viestityypin mukaan. Voit mukauttaa dialogin teemaa käyttämällä `setTheme(Theme theme)`-metodia ja säätää painikkeen teemaa erikseen `setButtonTheme(ButtonTheme theme)`-metodilla luodaksesi erilaisia variaatioita.
:::

## Painiketeksti {#button-text}

Voit määrittää dialogin painikkeen tekstin käyttämällä `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hello World!", "Otsikko", "Selvä");
```

## HTML-käsittely {#html-processing}

Oletuksena viestidialogi käsittelee ja renderöi HTML-sisältöä. Voit kytkeä tämän ominaisuuden pois päältä määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`MessageDialog` sallii sinun asettaa aikakatkaisun keston, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen kriittisille ilmoituksille tai tiedoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää dialogin aikakatkaisun käyttämällä `setTimeout(int timeout)`-metodia. Aikakatkaisun kesto on sekunneissa. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Tämä dialogi sulkeutuu pian", "Aikakatkaisu");
dialog.setTimeout(2);
dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät viestit**: Pidä viestit lyhyinä ja ytimekkäinä, vältä teknistä sanastoa; käytä käyttäjäystävällistä kieltä.
2. **Sopivat viestityypit**:
   - Käytä `ERROR` kriittisille ongelmille.
   - Käytä `WARNING` varoitustiedotteille.
   - Käytä `INFO` yleiselle tiedolle.
3. **Johdonmukainen teema**: Yhdistele dialogin ja painikkeen teemat sovelluksesi suunnitteluun.
4. **Harkittu aikakatkaisun käyttö**: Aseta aikakatkaisuja ei-kriittisille ilmoituksille ja varmista, että käyttäjillä on tarpeeksi aikaa lukea viesti.
5. **Vältä liiallista käyttöä**: Käytä dialogeja säästeliäästi käyttäjätyytyväisyyden estämiseksi ja pidä ne tärkeissä viesteissä, jotka vaativat käyttäjän toimintaa tai vahvistusta.
