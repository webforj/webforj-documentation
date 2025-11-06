---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: b6707cb6491075a82ac19fb808840245
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# Turvallisuus <DocChip chip='since' label='25.10' />

:::note Julkinen esikatselu
Tämä ominaisuus on julkisessa esikatselussa ja valmis tuotantokäyttöön. Esikatseluaikana API:ita voidaan tarkistaa kehittäjäyhteisön palautteen perusteella. Kaikki muutokset ilmoitetaan etukäteen julkaisutiedoissa, ja siirtymäohjeet annetaan tarvittaessa.
:::

Nykyaikaisissa verkkosovelluksissa **turvallisuus** tarkoittaa pääsyn hallintaa sovelluksesi eri osiin käyttäjäidentiteetin ja -oikeuksien perusteella. webforJ:ssä turvallisuus tarjoaa kehyksen **reittikohtaiselle pääsynhallinnalle**, jossa voit suojata näkymiä, vaatia todennusta ja pakottaa rooliin perustuvat oikeudet.

## Perinteinen VERSUS suojattu reititys {#traditional-vs-secured-routing}

Perinteisessä suojaamattomassa reitituksessa kaikki sovelluksesi reitit ovat kenen tahansa saatavilla, joka tietää URL-osoitteen. Tämä tarkoittaa sitä, että käyttäjät voivat navigoida herkille sivuille, kuten ylläpito-paneeleihin tai käyttäjätauluihin, ilman mitään todennus- tai valtuutustarkistuksia. Kehittäjien on muistettava manuaalisesti tarkistaa oikeudet jokaisessa komponentissa, mikä johtaa epäjohdonmukaiseen turvallisuuden toteuttamiseen ja mahdollisiin haavoittuvuuksiin.

Tämä lähestymistapa tuo mukanaan useita ongelmia:

1. **Manuaaliset tarkistukset**: Kehittäjien on muistettava lisätä turvallisuuslogiikka jokaiseen suojattuun näkymään tai asetteluun.
2. **Epäjohdonmukainen toteuttaminen**: Turvallisuustarkistusten hajaantuminen koodipohjaan johtaa aukkoihin ja virheisiin.
3. **Ylläpitokustannukset**: Pääsääntöjen muuttaminen vaatii useiden tiedostojen päivittämistä.
4. **Ei keskitettyä hallintoa**: Ei ole yhtä paikkaa, josta ymmärtää tai hallita sovelluksen turvallisuutta.

**Suojattu reititys** webforJ:ssä ratkaisee tämän mahdollistamalla pääsynhallinnan suoraan reittitasolla. Turvallisuusjärjestelmä pakottaa säännöt automaattisesti ennen kuin mitään komponenttia piirretään, tarjoten keskitetyn, deklaratiivisen lähestymistavan sovelluksen turvallisuuteen. Näin se toimii:

1. **Deklaratiiviset lisäykset**: Merkitse reitit turvallisuuslisäyksillä päivitysvaatimusten määrittämiseksi.
2. **Automaattinen toteutus**: Turvallisuusjärjestelmä tarkistaa oikeudet ennen minkään näkymän näyttämistä.
3. **Keskitetty rakenne**: Määritä turvallisuuskäyttäytyminen yhdessä paikassa ja sovella sitä johdonmukaisesti.
4. **Joustavat toteutukset**: Valitse Spring Security -integraation tai mukautetun plain Java -ratkaisun välillä.

Tämä suunnittelu mahdollistaa **todennuksen** (käyttäjän henkilöllisyyden vahvistaminen) ja **valtuutuksen** (vahvistaa, mihin käyttäjä pääsee), joten vain valtuutetuilla käyttäjillä on pääsy suojattuihin reitteihin. Valtuuttamattomat käyttäjät ohjataan automaattisesti tai heiltä evätään pääsy määritettyjen turvallisuus sääntöjen mukaan.

## Esimerkki suojatusta reitityksestä webforJ:ssä {#example-of-secured-routing-in-webforj}

Tässä on esimerkki, joka näyttää erilaiset turvallisuustasot webforJ-sovelluksessa:

```java title="LoginView.java"
// Julkinen kirjautumissivu - kuka tahansa voi käyttää
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {  
    self.onSubmit(e -> {
      handleLogin(e.getUsername(), e.getPassword());
    });

    whenAttached().thenAccept(c -> {
      self.open();
    });
  }
}
```

```java title="ProductsView.java"
// Tuotteet - vaatii todennuksen
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // tuotteiden näkymä
  }
}
```

```java title="InvoicesView.java"
// Laskut - vaatii ACCOUNTANT-roolia
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // laskujen näkymä
  }
}
```

Tässä kokoonpanossa:

- `LoginView` on merkitty `@AnonymousAccess`:lla, mikä mahdollistaa tunnistamattomien käyttäjien pääsyn siihen.
- `ProductsView` ei sisällä turvallisuuslisäystä, mikä tarkoittaa, että se vaatii todennuksen oletuksena (kun `secure-by-default` -tila on käytössä).
- `InvoicesView` vaatii `ACCOUNTANT`-roolia, joten vain käyttäjillä, joilla on kirjanpito-oikeudet, on pääsy laskuihin.

## Kuinka turvallisuus toimii {#how-security-works}

Kun käyttäjä yrittää navigoida reitille, turvallisuusjärjestelmä seuraa tätä prosessia:

1. **Navigointi aloitetaan**: Käyttäjä napsauttaa linkkiä tai syöttää URL-osoitteen.
2. **Turvallisuuden varmennus**: Ennen komponentin näyttämistä järjestelmä arvioi turvallisuuslisäykset ja -säännöt.
3. **Päätös**: Käyttäjän todennusstatus ja roolit huomioon ottaen:
   - **Myönnä**: Salli navigointi ja piirrä komponentti.
   - **Hylkäys**: Estä navigointi ja ohjaa kirjautumissivulle tai pääsy evätty -sivulle.
4. **Piirrä tai ohjaa**: Joko pyydetty komponentti näytetään tai käyttäjä ohjataan sopivasti.

Automaattisen valvonnan avulla turvallisuussäännöt sovelletaan johdonmukaisesti koko sovelluksesi läpi, joten pääsynhallinta tapahtuu ennen kuin mitään komponenttia piirretään, eikä kehittäjien tarvitse lisätä manuaalisia tarkistuksia jokaiseen näkymään.

## Todennus VERSUS valtuutus {#authentication-vs-authorization}

Jotta voit toteuttaa turvallisuuden sovelluksessasi oikein, on tärkeää tietää näiden kahden käsitteen ero:

- **Todennus**: Kenestä käyttäjä on kyse. Tämä tapahtuu tyypillisesti kirjautumisen aikana, jolloin käyttäjä antaa tunnistetiedot (käyttäjänimi ja salasana). Kun käyttäjä on todennettu, hänen henkilöllisyytensä tallennetaan sessioon tai turvallisuuskontekstiin.

- **Valtuutus**: Vahvistamalla, mihin todennettu käyttäjä pääsee. Tämä sisältää tarkistamisen, onko käyttäjällä tarvittavat roolit tai oikeudet pääsyyn tiettyyn reittiin. Valtuutus tapahtuu joka kerta, kun käyttäjä navigoi suojatulle reitille.

webforJ:n turvallisuusjärjestelmä hallitsee molempia näkökohtia:

- Lisäykset kuten `@PermitAll` käsittelevät todennustarpeita.
- Lisäykset kuten `@RolesAllowed` käsittelevät valtuutustarpeita.

## Aloittaminen {#getting-started}

Tämä opas olettaa, että käytät **Spring Bootia Spring Securityn kanssa**, mikä on suositeltava lähestymistapa useimmille webforJ-sovelluksille. Spring Security tarjoaa alan standardin todennuksen ja valtuutuksen automaattisella konfiguroinnilla Spring Bootin kautta.

Tämä dokumentaatio opastaa sinua reittiesi suojaamisessa Spring Securityn avulla, perusasetuksista edistyneisiin ominaisuuksiin. Jos et käytä Spring Bootia tai tarvitset mukautetun turvallisuusratkaisun, katso [Turvallisuusarkkitehtuurin opas](/docs/security/architecture/overview) saadaksesi tietoa siitä, miten järjestelmä toimii ja miten voit toteuttaa mukautettua turvallisuutta.

## Aiheet {#topics}

<DocCardList className="topics-section" />
