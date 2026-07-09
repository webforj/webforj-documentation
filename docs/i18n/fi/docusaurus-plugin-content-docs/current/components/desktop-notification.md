---
title: DesktopNotification
sidebar_position: 29
description: >-
  Send native OS notifications outside the browser window with the
  DesktopNotification component for real-time messages, alerts, and status
  changes.
_i18n_hash: 529ae2fce596f744b423574be0a95dc0
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

`DesktopNotification`-komponentti näyttää natiivin työpöytäilmoituksia selaimen ikkunan ulkopuolella. Sitä voidaan käyttää varoittamaan käyttäjiä reaaliaikaisista tapahtumista, kuten uusista viesteistä, järjestelmävaroista tai tilamuutoksista, kun he käyttävät sovellustasi.

<!-- INTRO_END -->

## Asennus ja vaatimukset {#setup-and-prerequisites}

<ExperimentalWarning />

Aloittaaksesi tämän ominaisuuden käytön, lisää seuraava riippuvuus pom.xml-tiedostoon:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Ennen kuin integroi `DesktopNotification`-komponentin, varmista, että:

- Sovelluksesi toimii **turvallisessa ympäristössä** (HTTPS).
- Selainta ei käytetä incognito- tai yksityisessä selaustilassa.
- Käyttäjä on ollut vuorovaikutuksessa sovelluksen kanssa (esim. napsauttamalla painiketta tai painamalla näppäintä), sillä ilmoitukset vaativat käyttäjäeleen näytettäväksi.
- Käyttäjä on myöntänyt ilmoituslupia (tätä pyydetään automaattisesti tarvittaessa).

## Peruskäyttö {#basic-usage}

Ilmoituksen luomiseen ja näyttämiseen on useita tapoja. Useimmissa tilanteissa yksinkertaisin tapa on kutsua yhtä staattisista `show`-menetelmistä, jotka kapseloivat koko ilmoituksen elinkaaren.

### Esimerkki: Perusilmoituksen näyttäminen {#example-displaying-a-basic-notification}

```java
// Perusilmoitus, jossa on otsikko ja viesti
DesktopNotification.show("Päivitys saatavilla", "Latauksesi on valmis!");
```

Tämä yksi rivi luo ilmoituksen, jossa on otsikko ja sisältö, ja yrittää sitten näyttää sen.

## Ilmoituksen mukauttaminen {#customizing-the-notification}

Näytettävän ilmoituksen ulkoasun ja tuntuman mukauttamiseen on erilaisia vaihtoehtoja, riippuen sovelluksen tarpeista ja ilmoituksen tarkoituksesta.

### Mukautetun `Icon`-kuvakkeen asettaminen {#setting-a-custom-icon}

Oletusarvoisesti ilmoitus käyttää määriteltyä sovelluksen kuvaketta [icons-protokollan](../managing-resources/assets-protocols#the-icons-protocol) kautta. Voit asettaa mukautetun kuvakkeen käyttämällä `setIcon`-menetelmää. Komponentti tukee erilaisia URL-skeemoja:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Ratkaistaan kontekstURL-osoitteeksi, joka osoittaa sovelluksen resurssikansioon; kuva on base64-koodattu.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Ratkaistaan verkkopalvelimen URL-osoitteeksi, jolloin saadaan täydellinen URL-osoite.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Ratkaistaan kuvakkeiden URL-osoitteeksi.

**Esimerkki:**

```java
// Luodaan ilmoitus mukautetulla kuvakkeen URL-osoitteella
DesktopNotification notification = new DesktopNotification(
  "Muistutus", "Kokous alkaa 10 minuutin kuluttua."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Ilmoitustapahtumat {#notification-events}

`DesktopNotification` tukee useita elinkaaritapahtumia, ja kuuntelijoita voidaan liittää käsittelemään tapahtumia, kuten silloin, kun ilmoitus näytetään, suljetaan, napsautetaan tai ilmenee virhe.

| Tapahtuma                  | Kuvaus                                           | Milloin käyttää                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Avaa** | Käynnistyy, kun ilmoitus näytetään.       | Kirjaa ilmoituksen näyttämisen, päivitä käyttöliittymä, seuraa sitoutumista.    |
| **Sulje**| Käynnistyy, kun ilmoitus suljetaan.         | Siivoa resurssit, kirjaa hylkäykset, suorita seuranta.|
| **Virhe**| Käynnistyy, kun ilmoituksessa tapahtuu virhe tai käyttäjä ei myöntänyt lupaa.| Käsittele virheitä sujuvasti, ilmoita käyttäjälle, käytä vaihtoehtoja.  |
| **Napsauta**| Käynnistyy, kun käyttäjä napsauttaa ilmoitusta. | Siirry tiettyyn osioon, kirjaa vuorovaikutuksia, keskity sovellukseen. |

```java
DesktopNotification notification = new DesktopNotification("Hälytys", "Sinulla on uusi viesti!");

// Liitä tapahtumakuuntelija avaa tapahtumalle
notification.onOpen(event -> {
  System.out.println("Ilmoitus avattiin käyttäjän toimesta.");
});

// Samoin, kuuntele napsautustapahtumaa
notification.onClick(event -> {
  System.out.println("Ilmoitusta napsautettiin.");
});
```

:::warning Napsautuskäyttäytyminen
Selaimen turvallisuuspolitiikat estävät ilmoituksen napsautustapahtuman automaattisesti tuomasta sovelluksen ikkunaasi tai välilehteäsi etualalle. Tämä käyttäytyminen on selaimen määräämää, eikä sitä voi ohittaa ohjelmallisesti. Jos sovelluksesi vaatii ikkunan fokusta, sinun on ohjeistettava käyttäjiä napsauttamaan sovelluksen sisällä ilmoituksen vuorovaikutuksen jälkeen.
:::

## Turvallisuus- ja yhteensopivuusharkinnat {#security-and-compatibility-considerations}

Kun käytät **DesktopNotification**-komponenttia, pidä mielessä seuraavat asiat:

- **Turvallisuusympäristö:** Sovelluksesi on toimitettava HTTPS:n yli varmistaaksesi, että ilmoitukset ovat sallittuja useimmissa moderneissa selaimissa.
- **Käyttäjäeleen vaatimukset:** Ilmoituksia näytetään vain käyttäjän aiheuttaman toiminnan jälkeen. Pelkkä sivun lataaminen ei käynnistä ilmoitusta.
- **Selaimen rajoitukset:** Ei kaikki selaimet käsittele mukautettuja kuvakkeita tai fokuskäyttäytymistä samalla tavalla. Esimerkiksi mukautetut kuvakkeet eivät ehkä toimi Safarissa, samalla kun tapahtumakäyttäytyminen voi vaihdella muissa selaimissa.
- **Luvat:** Tarkista aina, että sovelluksesi tarkistaa ja pyytää käyttäjältä ilmoituslupia sujuvasti.

## Käyttö parhaat käytännöt {#usage-best-practices}

Pidä seuraavat parhaat käytännöt mielessä käytettäessä `DesktopNotification`-komponenttia sovelluksessasi:

- **Tiedota käyttäjiäsi:** Kerro käyttäjille, miksi ilmoituksia tarvitaan ja kuinka he voivat hyötyä niistä.
- **Tarjoa vaihtoehtoja:** Koska jotkut selaimet saattavat rajoittaa ilmoituksia, harkitse vaihtoehtoisia tapoja varoittaa käyttäjiä (esim. sovellusviestit).
- **Virheiden käsittely:** Rekisteröi aina virhekuuntelija, jotta voit sujuvasti hallita tilanteita, joissa ilmoitukset epäonnistuvat näkymään.
