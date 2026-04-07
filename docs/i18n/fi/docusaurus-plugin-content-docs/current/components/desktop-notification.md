---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: b7e4651594dee824d6bcdf1ac32e1998
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

`DesktopNotification`-komponentti näyttää natiivin työpöydän ilmoituksia selainikkunan ulkopuolella. Sitä voidaan käyttää käyttäjien hälyttämiseen reaaliaikaisista tapahtumista, kuten uusista viesteistä, järjestelmäilmoituksista tai tilan muutoksista, kun he käyttävät sovellustasi.

<!-- INTRO_END -->

## Asennus ja ennakkoedellytykset {#setup-and-prerequisites}

<ExperimentalWarning />

Aloita tämän ominaisuuden käyttäminen lisäämällä seuraava riippuvuus pom.xml-tiedostoosi:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

Ennen `DesktopNotification`-komponentin integroimista varmista, että:

- Sovelluksesi toimii **turvallisessa kontekstissa** (HTTPS).
- Selain ei ole incognito- tai yksityisessä selaustilassa.
- Käyttäjä on vuorovaikuttanut sovelluksen kanssa (esim. napsauttamalla painiketta tai painamalla näppäintä), koska ilmoitukset vaativat käyttäjän eleen näyttääkseen.
- Käyttäjä on myöntänyt ilmoitusoikeudet (tämä pyydetään automaattisesti, jos tarpeen).

## Peruskäyttö {#basic-usage}

On useita tapoja luoda ja näyttää ilmoitus. Useimmissa skenaarioissa yksinkertaisin lähestymistapa on kutsua yhtä staattisista `show`-menetelmistä, jotka kapseloivat koko ilmoituselinkaaren.

### Esimerkki: Perusilmoituksen näyttäminen {#example-displaying-a-basic-notification}

```java
// Perusilmoitus, jossa on otsikko ja viesti
DesktopNotification.show("Päivitys saatavilla", "Latauksesi on valmis!");
```

Tämä yksirivinen komento luo ilmoituksen, jossa on otsikko ja sisältö, ja yrittää sitten näyttää sen.

## Ilmoituksen mukauttaminen {#customizing-the-notification}

On olemassa useita vaihtoehtoja ilmoituksen ulkoasun ja tuntuman mukauttamiseen sovelluksen tarpeiden ja ilmoituksen tarkoituksen mukaan.

### Mukautetun `Icon`-asetuksen asettaminen {#setting-a-custom-icon}

Oletuksena ilmoitus käyttää määriteltyä sovelluskuvaketta [ikoni-protokollan](../managing-resources/assets-protocols#the-icons-protocol) kautta. Voit asettaa mukautetun kuvakkeen käyttämällä `setIcon`-menetelmää. Komponentti tukee erilaisia URL-skeemoja:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Ratkaistaan konteksti-URL:ksi, joka osoittaa sovelluksen resurssikansioon; kuva on base64-koodattu.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Ratkaistaan verkkopalvelimen URL-osoitteeksi, mikä antaa täysin kelvollisen URL:n.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Ratkaistaan ikoni-URL:ksi.

**Esimerkki:**

```java
// Luodaan ilmoitus mukautetulla kuvake-URL:lla
DesktopNotification notification = new DesktopNotification(
  "Muistutus", "Kokous alkaa 10 minuutin kuluttua."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Ilmoitustapahtumat {#notification-events}

`DesktopNotification` tukee useita elinkaaren tapahtumia, ja kuuntelijoita voidaan liittää käsittelemään tapahtumia, kuten kun ilmoitus on näytetty, suljettu, klikattu tai kun siihen liittyy virhe.

| Tapahtuma      | Kuvaus                                            | Milloin käyttää                                           |
|-----------------|---------------------------------------------------|----------------------------------------------------------|
| **Avaa** | Käynnistyy, kun ilmoitus on näytetty.            | Kirjaa ilmoituksen näyttämisen, päivitä UI, seuraa sitoutumista. |
| **Sulje**| Käynnistyy, kun ilmoitus on suljettu.           | Siivoa resursseja, kirjaa hylkäämiset, suorita seurantatoimenpiteitä. |
| **Virhe**| Käynnistyy, kun virhe tapahtuu ilmoituksessa tai käyttäjä ei myöntänyt lupaa.| Käsittele virheitä sujuvasti, ilmoita käyttäjälle, käytä varatoimenpiteitä.  |
| **Napsauta**| Käynnistyy, kun käyttäjä napsauttaa ilmoitusta. | Siirry tiettyyn osioon, kirjaa vuorovaikutukset, keskity sovellukseen. |

```java
DesktopNotification notification = new DesktopNotification("Ilmoitus", "Sinulla on uusi viesti!")

// Liitä tapahtumakuuntelija avaus-tapahtumalle
notification.onOpen(event -> {
  System.out.println("Käyttäjä avasi ilmoituksen.");
});

// Vastaavasti, kuuntele napsautustapahtumaa
notification.onClick(event -> {
  System.out.println("Ilmoitus klikattu.");
});
```

:::warning Napsautuskäyttäytyminen
Selain turvallisuuspolitiikat estävät ilmoituksen napsautustapahtuman automaattisesti tuomasta sovellusikkunaasi tai -välilehteä näkyviin. Tämä käyttäytyminen on selaimen pakottamaa eikä sitä voida ohittaa ohjelmallisesti. Jos sovelluksesi tarvitsee ikkunan keskittymistä, sinun on ohjeistettava käyttäjiä napsauttamaan sovelluksessa ilmoituksen vuorovaikutuksen jälkeen.
:::

## Tietoturva- ja yhteensopivuusharkinnat {#security-and-compatibility-considerations}

Käyttäessäsi **DesktopNotification**-komponenttia pidä seuraavat seikat mielessä:

- **Turvallisuuskonteksti:** Sovelluksesi on toimitettava HTTPS:n kautta varmistaaksesi, että ilmoitukset sallitaan useimmissa moderneissa selaimissa.
- **Käyttäjän eleen vaatimus:** Ilmoitukset näytetään vain käyttäjän käynnistämän toiminnan jälkeen. Pelkkä sivun lataaminen ei laukaise ilmoitusta.
- **Selaimen rajoitukset:** Kaikki selaimet eivät käsittele mukautettuja kuvakkeita tai keskittymiskäyttäytymistä samalla tavalla. Esimerkiksi mukautetut kuvakkeet eivät välttämättä toimi Safarissa, kun taas tapahtumakäyttäytyminen saattaa vaihdella muissa selaimissa.
- **Luvat:** Varmista, että sovelluksesi tarkistaa ja pyytää ilmoitusoikeudet käyttäjältä sujuvasti.

## Käytön parhaat käytännöt {#usage-best-practices}

Pidä seuraavat parhaat käytännöt mielessä käyttäessäsi `DesktopNotification`-komponenttia sovelluksessasi:

- **Tietäkö käyttäjäsi:** Kerro käyttäjille, miksi ilmoituksia tarvitaan ja miten ne voivat hyödyttää heitä.
- **Tarjoa varatoimenpiteitä:** Koska jotkin selaimet saattavat rajoittaa ilmoituksia, harkitse vaihtoehtoisia tapoja hälyttää käyttäjiä (esim. sisäiset viestit).
- **Virheiden käsittely:** Rekisteröi aina virhekuuntelija käsittelemään sujuvasti tilanteita, joissa ilmoitukset eivät näy.
