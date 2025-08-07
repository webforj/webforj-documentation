---
sidebar_position: 30
title: Message
_i18n_hash: 575bdfd5364ffdbd911ac0ebe0628359
---
# Viestikeskustelu

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

`MessageDialog` on modaalinen keskusteluikkuna, joka on suunniteltu näyttämään viesti käyttäjälle OK-painikkeella, jolla keskusteluikkuna voidaan sulkea. Se estää sovelluksen suorituksen, kunnes käyttäjä vuorovaikuttaa sen kanssa tai se sulkeutuu aikakatkaisun vuoksi.

```java
OptionDialog.showMessageDialog("Hello World!");
```

## Käyttötapaukset {#usages}

Viestikeskustelu tarjoaa keinon näyttää tiedottavia ilmoituksia, kuten ilmoituksia, päivityksiä tai yksinkertaisia viestejä, jotka vaativat vain käyttäjän tunnustusta ilman, että heidän tarvitsee antaa mitään syötettä.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Viestityyppi {#message-type}

`MessageDialog` tukee seuraavia viestityyppejä. Kun määrität tyypin, keskusteluikkuna näyttää ikonin viestin vieressä ja keskusteluikkunan teema päivittyy webforJ-suunnitteluohjeiden mukaan.

1. `PLAIN`: Näyttää viestin ilman ikonia käyttäen oletusteemaa.
2. `ERROR`: Näyttää virheikon viestin vieressä, johon on sovellettu virheteema.
3. `QUESTION`: Näyttää kysymysmerkin ikonina viestin vieressä käyttäen ensisijaista teemaa.
4. `WARNING`: Näyttää varoitusikonin viestin vieressä, johon on sovellettu varoitusteema.
5. `INFO`: Näyttää info-ikonin viestin vieressä käyttäen info-teemaa.

Seuraavassa esimerkissä koodi määrittää varoitustyypin `WARNING` olevan viestikeskustelu, jossa on mukautettu otsikko ja viesti.

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Keskustelu & Painike Teema
Oletusarvoisesti keskusteluikkunan teema määritetään viestityypin perusteella. Voit mukauttaa keskusteluikkunan teemaa käyttämällä `setTheme(Theme theme)`-menetelmää ja säätää painikkeen teemaa itsenäisesti `setButtonTheme(ButtonTheme theme)`-menetelmällä, jolloin voit luoda erilaisia vaihteluita.
:::

## Painiketeksti {#button-text}

Voit määrittää keskusteluikkunan painikkeen tekstin käyttämällä `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hello World!", "Otsikko", "Sain sen");
```

## HTML-käsittely {#html-processing}

Oletusarvoisesti viestikeskustelu käsittelee ja renderöi HTML-sisältöä. Voit poistaa tämän ominaisuuden käytöstä määrittämällä sen näyttämään raakatekstiä sen sijaan.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Aikakatkaisu {#timeout}

`MessageDialog` sallii sinun määrittää aikakatkaisun, jonka jälkeen keskusteluikkuna sulkeutuu automaattisesti. Tämä ominaisuus on hyödyllinen kriittisille ilmoituksille tai tiedolle, joka ei vaadi käyttäjän välitöntä vuorovaikutusta.

Voit määrittää aikakatkaisun keskusteluikkunalle käyttämällä `setTimeout(int timeout)`-menetelmää. Aikakatkaisun kesto on sekunneissa. Jos määritetty aika kuluu ilman käyttäjän vuorovaikutusta, keskusteluikkuna sulkeutuu automaattisesti.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Tämä keskusteluikkuna sulkeutuu pian", "Aikakatkaisu");
dialog.setTimeout(2);
dialog.show();
```

## Parhaat käytännöt {#best-practices}

1. **Selkeät ja Tiivistetyt Viestit**: Pidä viestit lyhyinä ja ytimekkäinä, ja vältä teknistä jargonin käyttöä; käytä käyttäjäystävällistä kieltä.
2. **Sopivat Viestityypit**:
   - Käytä `ERROR` kriittisille ongelmille.
   - Käytä `WARNING` varoitusilmoituksille.
   - Käytä `INFO` yleiselle tiedolle.
3. **Johdonmukainen Teemointi**: Yhdistä keskustelu ja painikkeen teemat sovelluksesi suunnittelun mukaan.
4. **Käytä Aikakatkaisua Viisaasti**: Aseta aikakatkaisut ei-kriittisille ilmoituksille ja varmista, että käyttäjillä on riittävästi aikaa lukea viesti.
5. **Vähempi On Enemmän**: Käytä keskusteluja säästeliäästi käyttäjän turhautumisen estämiseksi ja varaa ne tärkeisiin viesteihin, jotka vaativat käyttäjän toimintaa tai tunnustamista.
