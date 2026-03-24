---
title: Message
sidebar_position: 30
_i18n_hash: 4540b0f4317acc598d4970d0f16ae757
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

`MessageDialog` on modaalinen dialogi, joka on suunniteltu esittämään viesti käyttäjälle OK-painikkeella dialogin sulkemiseksi. Se estää sovelluksen suorittamisen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

<!-- INTRO_END -->

## Käytöt {#usages}

Käytä staattista `showMessageDialog`-menetelmää perustason viestin näyttämiseen.

```java
OptionDialog.showMessageDialog("Hello World!");
```

Jos haluat enemmän hallintaa dialogin ulkonäön ja käytöksen suhteen, voit luoda `MessageDialog`-instanssin suoraan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Viestityyppi {#message-type}

`MessageDialog` tukee seuraavia viestityyppejä. Kun konfiguroit tyyppin, dialogi näyttää ikonin viestin vieressä, ja dialogin teema päivittyy webforJ:n suunnittelujärjestelmän sääntöjen mukaan.

1. `PLAIN`: Näyttää viestin ilman ikonia, käyttäen oletusteemaa.
2. `ERROR`: Näyttää virheikon viestin vieressä virheteema käytössä.
3. `QUESTION`: Näyttää kysymysmerkin ikonin viestin vieressä, käyttäen ensisijaista teemaa.
4. `WARNING`: Näyttää varoitusikonin viestin vieressä varoitusteema käytössä.
5. `INFO`: Näyttää infoikonin viestin vieressä, käyttäen infotema.

Seuraavassa esimerkissä koodi konfiguroi varoitustyypin `WARNING` viestidialogin mukautetulla otsikolla ja viestillä.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialogi & Painike Teema
Oletuksena dialogin teema määräytyy viestityypin mukaan. Voit mukauttaa dialogin teemaa käyttämällä `setTheme(Theme theme)`-menetelmää ja säätää painiketeemaa itsenäisesti `setButtonTheme(ButtonTheme theme)`-menetelmällä luodaksesi erilaisia ​​vaihtoehtoja.
:::

## Painiketeksti {#button-text}

Voit konfiguroida dialogin painikkeen tekstin käyttämällä `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hello World!", "Otsikko", "Ymmärrän");
```

## HTML-käsittely {#html-processing}

Oletuksena viestidialogi käsittelee ja renderöi HTML-sisältöä. Voit poistaa tämän ominaisuuden käytöstä konfiguroimalla sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`MessageDialog` sallii sinun asettaa aikakatkaisuajan, jonka jälkeen dialogi sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen ei-kriittisille ilmoituksille tai tiedoille, jotka eivät vaadi käyttäjän välitöntä vuorovaikutusta.

Voit konfiguroida dialogin aikakatkaisuajan käyttämällä `setTimeout(int timeout)`-menetelmää. Aikakatkaisu on sekunneissa. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, dialogi sulkeutuu automaattisesti.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Tämä dialogi sulkeutuu pian", "Aikakatkaisu");
dialog.setTimeout(2);
dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja ytimekkäät viestit**: Pidä viestit lyhyinä ja ytimekkäinä sekä vältä teknistä sanastoa; käytä käyttäjäystävällistä kieltä.
2. **Sopivat viestityypit**:
   - Käytä `ERROR` kriittisille ongelmille.
   - Käytä `WARNING` varoitusilmoituksille.
   - Käytä `INFO` yleiselle tiedolle.
3. **Johdonmukainen teema**: Yhdistelee dialogin ja painikkeiden teemat sovelluksesi suunnittelun kanssa.
4. **Harkintaa aikakatkaisun käytössä**: Aseta aikakatkaisuja ei-kriittisille ilmoituksille ja varmista, että käyttäjillä on riittävästi aikaa lukea viesti.
5. **Vältä ylikäyttöä**: Käytä dialogeja kohtuudella käyttäjätyytymättömyyden estämiseksi ja pidä ne tärkeitä viestejä varten, jotka vaativat käyttäjän toimintaa tai tunnustusta.
