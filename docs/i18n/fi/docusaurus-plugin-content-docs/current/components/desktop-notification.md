---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 16e95136d6067cafa258ef513f9e757f
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

WebforJ-version 25.00 ja uudemmat, **DesktopNotification**-komponentti tarjoaa yksinkertaisen käyttöliittymän työpöytäilmoitusten luomiseen, näyttämiseen ja hallintaan. Keskityttäessä minimaaliseen konfigurointiin ja sisäänrakennettuun tapahtumankäsittelyyn, komponenttia voidaan käyttää, kun käyttäjille on tarpeen ilmoittaa reaaliaikaisista tapahtumista (kuten uusista viesteistä, ilmoituksista tai järjestelmäntapahtumista) samalla kun he selaavat sovellustasi.

:::warning kokeellinen ominaisuus
`DesktopNotification`-komponentti on vielä kehittymässä, ja sen API voi kokea muutoksia kehittyessään. Aloittaaksesi tämän ominaisuuden käytön, varmista, että lisäät seuraavan riippuvuuden pom.xml-tiedostoon.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Esivaatimukset
Ennen `DesktopNotification`-komponentin integroimista varmista, että:

- Sovelluksesi toimii **turvallisessa kontekstissa** (HTTPS).
- Selain ei ole yksityisessä tai incognito-tilassa.
- Käyttäjä on ollut vuorovaikutuksessa sovelluksen kanssa (esim. painanut painiketta tai näppäintä), sillä ilmoitukset vaativat käyttäjän eleen näyttämiseksi.
- Käyttäjä on myöntänyt ilmoitusluvat (tämä pyydetään automaattisesti, jos tarpeen).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Peruskäyttö {#basic-usage}

Ilmoituksen luomiseen ja näyttämiseen on useita tapoja. Useimmissa tilanteissa yksinkertaisin lähestymistapa on kutsua yhtä staattisista `show`-menetelmistä, jotka kapseloivat koko ilmoituksen elinkaaren.

### Esimerkki: Perusilmoituksen näyttäminen {#example-displaying-a-basic-notification}

```java
// Perusilmoitus, jossa on otsikko ja viesti
DesktopNotification.show("Päivitys saatavilla", "Latauksesi on valmis!");
```

Tämä yksirivinen koodi luo ilmoituksen, jossa on otsikko ja sisältö, ja yrittää sitten näyttää sen.

## Ilmoituksen mukauttaminen {#customizing-the-notification}

Ilmoituksen ulkonäön ja tunteen mukauttamiseen on useita vaihtoehtoja, riippuen sovelluksen tarpeista ja ilmoituksen tarkoituksesta.

### Mukautetun `Icon`-asetuksen asettaminen {#setting-a-custom-icon}

Oletusarvoisesti ilmoitus käyttää määrittelemääsi sovellusikonia [ikoni-protokollan](../managing-resources/assets-protocols#the-icons-protocol) kautta. Voit asettaa mukautetun ikonin käyttämällä `setIcon`-menetelmää. Komponentti tukee eri URL-protokollia:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Ratkaisee kontekstin URL:ksi, joka osoittaa sovelluksen resurssikansioon; kuva on base64-koodattu.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Ratkaisee verkkopalvelimen URL:ksi, joka antaa täysin määritellyn URL-osoitteen.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Ratkaisee ikonien URL-osoitteeksi.

**Esimerkki:**

```java
// Luodaan ilmoitus mukautetulla ikonu URL-osoitteella
DesktopNotification notification = new DesktopNotification(
  "Muistutus", "Kokous alkaa 10 minuutin kuluttua."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Ilmoitustapahtumat {#notification-events}

`DesktopNotification` tukee useita elinkaaritapahtumia, ja kuuntelijoita voidaan liittää käsittelemään tapahtumia, kuten kun ilmoitus näytetään, suljetaan, klikataan tai syntyy virhe.

| Tapahtuma              | Kuvaus                                               | Milloin käyttää                                            |
|------------------------|------------------------------------------------------|----------------------------------------------------------|
| **Avaa**               | Käynnistyy, kun ilmoitus näytetään.                 | Ilmoituksen näyttämisen lokittaminen, käyttöliittymän päivittäminen, sitoutumisen seuraaminen. |
| **Sulje**              | Käynnistyy, kun ilmoitus suljetaan.                 | Resurssien puhdistaminen, hylkäysten lokittaminen, seurantatoimien suorittaminen. |
| **Virhe**              | Käynnistyy, kun ilmoituksessa syntyy virhe tai käyttäjä ei myöntänyt lupia. | Virheiden käsitteleminen sujuvasti, käyttäjän ilmoittaminen, varajärjestelmien soveltaminen. |
| **Klikkaus**           | Käynnistyy, kun käyttäjä klikkaa ilmoitusta.        | Siirtyminen tiettyyn osioon, vuorovaikutusten lokittaminen, sovelluksen fokusoiminen. |


```java
DesktopNotification notification = new DesktopNotification("Ilmoitus", "Sinulla on uusi viesti!")

// Liitetään tapahtumakuuntelija avautumisen tapahtumalle
notification.onOpen(event -> {
  System.out.println("Ilmoitus avattiin käyttäjän toimesta.");
});

// Vastaavasti, kuunnellaan klikkaustapahtumaa
notification.onClick(event -> {
  System.out.println("Ilmoitus klikattu.");
});
```

:::warning Klikkauskäyttäytyminen
Selaimen turvallisuuspolitiikat estävät ilmoituksen klikkaustapahtuman automaattisesti tuomasta sovelluksesi ikkunaa tai välilehteä fokukseen. Tämä käyttäytyminen on selaimen täytäntöönpanema eikä sitä voida ohittaa ohjelmallisesti. Jos sovelluksesi vaatii ikkunan fokusoimista, sinun on kehotettava käyttäjiä klikkaamaan sovelluksessa ilmoituksen vuorovaikutuksen jälkeen.
:::

## Turvallisuus- ja yhteensopivuusnäkökohtia {#security-and-compatibility-considerations}

Käyttäessäsi **DesktopNotification**-komponenttia, pidä mielessä seuraavat seikat:

- **Turvallisuuskonteksti:** Sovelluksesi on tarjottava HTTPS:n kautta, jotta ilmoitukset ovat sallittuja useimmissa moderneissa selaimissa.
- **Käyttäjän elevaatimus:** Ilmoitukset näytetään vain käyttäjän aiheuttaman toiminnan jälkeen. Pelkkä sivun lataaminen ei laukaise ilmoitusta.
- **Selaimen rajoitukset:** Kaikki selaimet eivät käsittele mukautettuja ikoneita tai fokusoimiskäyttäytymistä samalla tavalla. Esimerkiksi mukautetut ikonit eivät välttämättä toimi Safarissa, kun taas tapahtumakäyttäytyminen voi vaihdella muissa selaimissa.
- **Luvat:** Varmista aina, että sovelluksesi tarkistaa ja pyytää ilmoituslupia käyttäjältä sujuvasti.

## Käyttöparhaat käytännöt {#usage-best-practices}

Pidä mielessä seuraavat parhaat käytännöt käyttäessäsi `DesktopNotification`-komponenttia sovelluksessasi:

- **Tiedota käyttäjiä:** Kerro käyttäjille, miksi ilmoituksia tarvitaan ja miten niistä voi olla hyötyä.
- **Tarjoa varajärjestelmiä:** Koska jotkut selaimet saattavat rajoittaa ilmoituksia, harkitse vaihtoehtoisia tapoja varoittaa käyttäjiä (esimerkiksi sovelluksen sisäiset viestit).
- **Virheiden käsittely:** Rekisteröi aina virhekuuntelija hallitaksesi sujuvasti tilanteita, joissa ilmoitusten näyttäminen epäonnistuu.
