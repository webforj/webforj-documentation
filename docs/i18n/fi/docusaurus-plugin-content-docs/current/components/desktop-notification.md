---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 79d5ddb69e13f8536439346d8d4ee85d
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

`DesktopNotification`-komponentti näyttää natiivin työpöytäilmoituksia selainikkunan ulkopuolella. Sitä voidaan käyttää käyttäjien ilmoittamiseen reaaliaikaisista tapahtumista, kuten uusista viesteistä, järjestelmäilmoituksista tai tilamuutoksista, kun he käyttävät sovellustasi.

<!-- INTRO_END -->

## Asennus ja vaatimukset {#setup-and-prerequisites}

:::warning kokeellinen ominaisuus
`DesktopNotification`-komponentti on edelleen kehittymässä, ja sen API saattaa kokea muutoksia kehityksen aikana. Aloittaaksesi tämän ominaisuuden käytön, varmista, että olet lisännyt seuraavan riippuvuuden pom.xml-tiedostoon.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

Ennen kuin integroi `DesktopNotification`-komponentin, varmista, että:

- Sovelluksesi toimii **turvallisessa ympäristössä** (HTTPS).
- Selain ei ole yksityisessä tai incognito-tilassa.
- Käyttäjä on vuorovaikutuksessa sovelluksen kanssa (esim. napsauttanut painiketta tai painanut näppäintä), koska ilmoitusten näyttäminen edellyttää käyttäjän toimintaa.
- Käyttäjä on myöntänyt ilmoituslupa ( tämä pyydetään automaattisesti tarvittaessa).

## Peruskäyttö {#basic-usage}

Ilmoituksen luomiseen ja näyttämiseen on useita tapoja. Useimmissa tapauksissa yksinkertaisin lähestymistapa on kutsua yhtä staattisista `show`-menetelmistä, jotka kapseloivat koko ilmoituksen elinkaaren.

### Esimerkki: Perusilmoituksen näyttäminen {#example-displaying-a-basic-notification}

```java
// Perusilmoitus, jossa on otsikko ja viesti
DesktopNotification.show("Päivitys saatavilla", "Latauksesi on valmis!");
```

Tämä yhden rivin komento luo ilmoituksen, jossa on otsikko ja sisältö, ja yrittää sitten näyttää sen.

## Ilmoituksen mukauttaminen {#customizing-the-notification}

Ilmoituksen ulkoasun ja toiminnan mukauttamiseen on useita vaihtoehtoja, riippuen sovelluksen tarpeista ja ilmoituksen tarkoituksesta.

### Mukautetun `Icon`-kuvakkeen asettaminen {#setting-a-custom-icon}

Oletusarvoisesti ilmoitus käyttää määriteltyä sovelluksen kuvaketta [icons-protokollan](../managing-resources/assets-protocols#the-icons-protocol) kautta. Voit asettaa mukautetun kuvakkeen `setIcon`-menetelmällä. Komponentti tukee erilaisia URL-skeemoja:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Ratkaistaan konteksti-URL:ksi, joka osoittaa sovelluksen resurssikansioon; kuva on base64-koodattu.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Ratkaistaan web-palvelimen URL:ksi, mikä antaa täysin määritellyn URL:n.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Ratkaistaan kuvake-URL:ksi.

**Esimerkki:**

```java
// Ilmoituksen luominen mukautetulla kuvakkeen URL:lla
DesktopNotification notification = new DesktopNotification(
  "Muistutus", "Palaveri alkaa 10 minuutin kuluttua."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Ilmoitus tapahtumat {#notification-events}

`DesktopNotification` tukee useita elinkaaritapahtumia, ja kuuntelijoita voidaan liittää käsittelemään tapahtumia, kuten ilmoituksen näyttämistä, sulkemista, napsauttamista tai virhetilanteita.

| Tapahtuma               | Kuvaus                                             | Milloin käyttää                                           |
|-----------------------------|---------------------------------------------------|---------------------------------------------------------|
| **Avaa** | Käynnistetään, kun ilmoitus näytetään.         | Kirjaa ilmoituksen näyttö, päivitä käyttöliittymä, seuraa sitoutumista. |
| **Sulje**| Käynnistetään, kun ilmoitus suljetaan.         | Siivoa resursseja, kirjaa sulkemiset, suorita jatkotoimia. |
| **Virhe**| Käynnistetään, kun ilmoituksessa tapahtuu virhe tai käyttäjä ei myöntänyt lupaa. | Käsittele virheitä sujuvasti, ilmoita käyttäjälle, käytä varatoimia. |
| **Napsauta**| Käynnistetään, kun käyttäjä napsauttaa ilmoitusta. | Navigoi tiettyyn osioon, kirjaa vuorovaikutuksia, tuo sovellus takaisin keskiöön. |


```java
DesktopNotification notification = new DesktopNotification("Ilmoitus", "Sinulla on uusi viesti!");

// Liitä tapahtumakuuntelija avaus tapahtumalle
notification.onOpen(event -> {
  System.out.println("Ilmoitus avattiin käyttäjän toimesta.");
});

// Kuuntele myös napsautustapahtumaa
notification.onClick(event -> {
  System.out.println("Ilmoitus napsautettu.");
});
```

:::warning Napsautuskäyttäytyminen
Selaimen suojauskäytännöt estävät ilmoituksen napsautustapahtuman automaattisesti tuomasta sovellusten ikkunaasi tai välilehteä keskiöön. Tämä käyttäytyminen on selaimen määrittämää eikä sitä voi ohittaa ohjelmallisesti. Jos sovelluksesi tarvitsee ikkunan olevan aktiivinen, sinun on ohjeistettava käyttäjiä napsauttamaan sovelluksessa ilmoituksen vuorovaikutuksen jälkeen.
:::

## Turvallisuus- ja yhteensopivuushavainnot {#security-and-compatibility-considerations}

Kun käytät **DesktopNotification**-komponenttia, pidä mielessä seuraavat asiat:

- **Turvallisuusympäristö:** Sovelluksesi on toimitettava HTTPS:n kautta, jotta ilmoitukset ovat sallittuja useimmissa moderneissa selaimissa.
- **Käyttäjän toimenpiteen vaatiminen:** Ilmoituksia näytetään vain käyttäjän aktivoiman toiminnan jälkeen. Pelkkä sivun lataaminen ei laukaise ilmoitusta.
- **Selaimen rajoitukset:** Kaikki selaimet eivät käsittele mukautettuja kuvakkeita tai keskiökäyttäytymistä samalla tavalla. Esimerkiksi mukautetut kuvakkeet eivät välttämättä toimi Safarissa, kun taas tapahtumakäyttäytyminen voi vaihdella muissa selaimissa.
- **Luvat:** Varmista aina, että sovelluksesi tarkistaa ja pyytää ilmoituslupia käyttäjältä sujuvasti.

## Käytön parhaat käytännöt {#usage-best-practices}

Pidä mielessä seuraavat parhaat käytännöt käytettäessä `DesktopNotification`-komponenttia sovelluksessasi:

- **Ilmoita käyttäjillesi:** Kerro käyttäjille, miksi ilmoituksia tarvitaan ja miten ne voivat olla heille hyödyksi.
- **Tarjoa varatoimia:** Koska jotkut selaimet saattavat rajoittaa ilmoituksia, harkitse vaihtoehtoisia tapoja ilmoittaa käyttäjille (esim. sovelluksen sisäiset viestit).
- **Virheiden käsittely:** Rekisteröi aina virhekuuntelija sujuvasti hallitaksesi tilanteita, joissa ilmoitukset eivät onnistu näyttämään.
