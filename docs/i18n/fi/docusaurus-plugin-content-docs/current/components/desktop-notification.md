---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 6bc148e8bcb06161115d0509601b516b
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

Webforj 25.00 ja uudemmissa **DesktopNotification** -komponentti tarjoaa yksinkertaisen käyttöliittymän työpöytäilmoitusten luomiseen, näyttämiseen ja hallintaan. Painopiste on minimaalisen konfiguroinnin ja sisäänrakennetun tapahtumakäsittelyn avulla, komponenttia voidaan käyttää, kun halutaan ilmoittaa käyttäjille reaaliaikaisista tapahtumista (kuten uusista viesteistä, hälytyksistä tai järjestelmätilanteista) heidän selatessaan sovellustasi.

:::warning kokeellinen ominaisuus
`DesktopNotification` -komponentti on edelleen kehittymässä, ja sen API saattaa kokea muutoksia kehittyessään. Aloittaaksesi tämän ominaisuuden käytön, varmista että lisäät seuraavan riippuvuuden pom.xml-tiedostoon.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Ehdot
Ennen kuin integroit `DesktopNotification` -komponentin, varmista että:

- Sovelluksesi toimii **turvallisessa ympäristössä** (HTTPS).
- Selain ei ole inkognito- tai yksityisessä selaustilassa.
- Käyttäjä on vuorovaikuttanut sovelluksen kanssa (esim. napsauttanut painiketta tai painanut näppäintä), koska ilmoitukset vaativat käyttäjän eleen näyttääkseen.
- Käyttäjä on myöntänyt luvan ilmoituksille (tätä pyydetään automaattisesti tarvittaessa).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Peruskäyttö {#basic-usage}

Ilmoituksen luomiseen ja näyttämiseen on useita tapoja. Useimmissa tapauksissa yksinkertaisinta on kutsua jotain statista `show`-menetelmää, joka kapseloi koko ilmoituselämänsyklin.

### Esimerkki: Perusilmoituksen näyttäminen {#example-displaying-a-basic-notification}

```java
// Perusilmoitus, jossa otsikko ja viesti
DesktopNotification.show("Päivitys saatavilla", "Latauksesi on valmis!");
```

Tämä yksirivinen koodi luo ilmoituksen, jossa on otsikko ja sisältö, ja yrittää sitten näyttää sen.

## Ilmoituksen mukauttaminen {#customizing-the-notification}

Näytettävän ilmoituksen ulkoasun ja tuntuman mukauttamiseen on erilaisia vaihtoehtoja, riippuen sovelluksen tarpeista ja ilmoituksen tarkoituksesta.

### Mukautetun `Icon`-kuvakkeen asettaminen {#setting-a-custom-icon}

Oletuksena ilmoitus käyttää määrittelemääsi sovelluksen kuvaketta [icons-protokollan](../managing-resources/assets-protocols#the-icons-protocol) kautta. Voit asettaa mukautetun kuvakkeen käyttämällä `setIcon`-menetelmää. Komponentti tukee erilaisia URL-skeemoja:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Ratkaistaan konteksti-URL:ksi, joka viittaa sovelluksen resurssikansioon; kuva on base64-koodattu.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Ratkaistaan web-palvelimen URL:ksi, antaen täydellisen URL-osoitteen.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Ratkaistaan kuvakkeiden URL:ksi.

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

`DesktopNotification` tukee useita elinkaaritapahtumia, ja kuuntelijoita voidaan liittää käsittelemään tapahtumia, kuten silloin, kun ilmoitus näytetään, suljetaan, sitä napsautetaan tai jos ilmenee virhe.

| Tapahtuma                  | Kuvaus                                           | Milloin käytetään                                               |
|-----------------------------|--------------------------------------------------|---------------------------------------------------------------|
| **Avaa** | Käynnistyy, kun ilmoitus näytetään.       | Kirjaa ilmoituksen näyttämisen, päivitä käyttöliittymää, seuraa osallistumista.    |
| **Sulje**| Käynnistyy, kun ilmoitus suljetaan.         | Puhdista resursseja, kirjaa hylkäämiset, suorita jatkotoimia.|
| **Virhe**| Käynnistyy, kun ilmoituksessa tai käyttäjän luvassa ilmenee virhe.| Käsittele virheet hienovaraisesti, ilmoita käyttäjälle, sovella varatoimia.  |
| **Napsauta**| Käynnistyy, kun käyttäjä napsauttaa ilmoitusta. | Siirry tiettyyn osioon, kirjaa vuorovaikutuksia, keskity sovellukseen. |

```java
DesktopNotification notification = new DesktopNotification("Ilmoitus", "Sinulla on uusi viesti!")

// Liitä tapahtumakuuntelija avautumisanturille
notification.onOpen(event -> {
  System.out.println("Ilmoitus avattiin käyttäjän toimesta.");
});

// Kuuntele myös napsautustapahtumaa
notification.onClick(event -> {
  System.out.println("Ilmoitus napsautettu.");
});
```

:::warning Napsautuskäyttäytyminen
Selaimen turvallisuuspolitiikat estävät ilmoituksen napsautustapahtumaa tuomasta sovelluksen ikkunaasi tai välilehteä keskiöön automaattisesti. Tämä käyttäytyminen on selaimen pakottama, eikä sitä voida ohittaa ohjelmallisesti. Jos sovelluksesi vaatii ikkunan keskittymistä, sinun on ohjeistettava käyttäjiä napsauttamaan sovelluksessa vuorovaikutuksen jälkeen ilmoituksen kanssa.
:::

## Turvallisuus- ja yhteensopivuusseikat {#security-and-compatibility-considerations}

Käytettäessä **DesktopNotification** -komponenttia, pidä mielessä seuraavat asiat:

- **Turvallisuusympäristö:** Sovelluksesi on toimitettava HTTPS:n kautta, jotta ilmoitukset ovat sallittuja useimmissa moderneissa selaimissa.
- **Käyttäjän eleen vaatimus:** Ilmoituksia näytetään vain käyttäjän aloittaman toiminnan jälkeen. Pelkkä sivun lataaminen ei laukaise ilmoitusta.
- **Selaimen rajoitukset:** Ei kaikki selaimet käsittele mukautettuja kuvakkeita tai keskittymiskäyttäytymistä samalla tavalla. Esimerkiksi, mukautetut kuvakkeet eivät välttämättä toimi Safarissa, kun taas tapahtumakäyttäytyminen voi vaihdella muissa selaimissa.
- **Luvat:** Varmista aina, että sovelluksesi tarkistaa ja pyytää ilmoituslupia käyttäjältä hienovaraisesti.

## Käyttökäytännöt {#usage-best-practices}

Pidä seuraavat parhaat käytännöt mielessä käytettäessä `DesktopNotification` -komponenttia sovelluksessasi:

- **Ilmoita käyttäjillesi:** Kerro käyttäjille, miksi ilmoituksia tarvitaan ja miten niistä on hyötyä.
- **Tarjoa varatoimia:** Koska jotkin selaimet saattavat rajoittaa ilmoituksia, harkitse vaihtoehtoisia tapoja varoittaa käyttäjiä (esim. sovellusviestit).
- **Virheiden käsittely:** Rekisteröi aina virhekuuntelija hallitaksesi hienovaraisesti tilanteita, joissa ilmoitukset eivät näy.
