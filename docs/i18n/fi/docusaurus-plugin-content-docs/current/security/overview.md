---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: fe28b9f0c456b9880785afcc5d4d5f23
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
Tämä ominaisuus on julkisessa esikatselussa ja valmis tuotantokäyttöön. Esikatseluaikana API:ita voidaan täydentää kehittäjätuotannon palautteen perusteella. Kaikki muutokset ilmoitetaan etukäteen julkaisutiedoissa ja siirtymäoppaissa, jos tarpeen.
:::

Nykyisissä verkkosovelluksissa **turvallisuus** tarkoittaa pääsyn hallintaa sovelluksesi eri osiin käyttäjän henkilöllisyyden ja käyttöoikeuksien perusteella. webforJ:ssä turvallisuus tarjoaa kehyksen **reittitason pääsynhallinnalle**, jossa voit suojata näkymiä, vaatia todennusta ja valvoa roolipohjaisia käyttöoikeuksia.

<AISkillTip skill="webforj-securing-apps" />

## Perinteinen VS suojattu reititys {#traditional-vs-secured-routing}

Perinteisessä suojaamattomassa reitityksessä kaikki sovelluksen reitit ovat kaikkien saatavilla, jotka tuntevat URL-osoitteen. Tämä tarkoittaa, että käyttäjät voivat navigoida arkaluonteisiin sivuihin, kuten järjestelmänvalvojan paneeliin tai käyttäjäkoontinäyttöihin ilman mitään todennus- tai valtuutustarkistuksia. Taakka jää kehittäjille, jotka joutuvat manuaalisesti tarkistamaan käyttöoikeudet jokaisessa komponentissa, mikä johtaa epäjohdonmukaiseen turvallisuuden valvontaan ja mahdollisiin haavoittuvuuksiin.

Tämä lähestymistapa tuo mukanaan useita ongelmia:

1. **Manuaaliset tarkistukset**: Kehittäjien on muistettava lisätä turvallisuuslogiikka jokaiseen suojattuun näkymään tai asetteluun.
2. **Epäjohdonmukainen valvonta**: Turvallisuustarkastukset, jotka ovat hajallaan kooditietokannassa, johtavat aukkoihin ja virheisiin.
3. **Ylläpitokustannukset**: Pääsäännösten muuttaminen vaatii useiden tiedostojen päivittämistä.
4. **Ei keskitettyä hallintaa**: Ei ole yksittäistä paikkaa, jossa ymmärtää tai hallita sovelluksen turvallisuutta.

**Suojattu reititys** webforJ:ssä ratkaisee tämän mahdollistamalla pääsynhallinnan suoraan reittitasolla. Turvallisuusjärjestelmä valvoo sääntöjä automaattisesti ennen kuin mitään komponenttia renderöidään, jolloin saadaan keskitetty, deklaratiivinen lähestymistapa sovelluksen turvallisuuteen. Tässä on kuinka se toimii:

1. **Deklaratiiviset annotaatiot**: Merkitse reitit turvallisuusannotaatioilla määritelläksesi pääsyvaatimukset.
2. **Automaattinen valvonta**: Turvallisuusjärjestelmä tarkistaa käyttöoikeudet ennen minkään näkymän renderöimistä.
3. **Keskitetty konfiguraatio**: Määrittele turvallisuuskäyttäytyminen yhdessä paikassa ja sovella se johdonmukaisesti.
4. **Joustavat toteutukset**: Valitse Spring Security -integraation tai mukautetun Java-toteutuksen välillä.

Tämä suunnittelu mahdollistaa **todennuksen** (käyttäjän henkilöllisyyden tarkistaminen) ja **valtuutuksen** (tarkistaa, mitä käyttäjä voi käyttää), jotta vain valtuutetuille käyttäjille myönnetään pääsy suojattuihin reitteihin. Valtuuttamattomat käyttäjät ohjataan automaattisesti tai heiltä evätään pääsy määritettyjen turvallisuussääntöjen perusteella.

## Esimerkki suojatusta reitityksestä webforJ:ssä {#example-of-secured-routing-in-webforj}

Tässä on esimerkki, joka näyttää eri turvallisuustasot webforJ-sovelluksessa:

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
// Laskut - vaatii ACCOUNTANT-roolin
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // laskujen näkymä
  }
}
```

Tässä asetuksessa:

- `LoginView` on merkitty `@AnonymousAccess`-annoitolla, mikä sallii todennusta vaatimattomien käyttäjien pääsyn siihen.
- `ProductsView` ei sisällä turvallisuusannotaatiota, mikä tarkoittaa, että se vaatii todennuksen oletuksena (kun `secure-by-default` -tila on käytössä).
- `InvoicesView` vaatii `ACCOUNTANT`-roolin, joten vain käyttäjillä, joilla on kirjanpitovalmiudet, on pääsy laskuihin.

## Kuinka turvallisuus toimii {#how-security-works}

Kun käyttäjä yrittää navigoida reitille, turvallisuusjärjestelmä seuraa tätä prosessia:

1. **Navigointi aloitetaan**: Käyttäjä napsauttaa linkkiä tai syöttää URL-osoitteen.
2. **Turvallisuustarkistus**: Ennen komponentin renderöimistä järjestelmä arvioi turvallisuusannotaatioita ja sääntöjä.
3. **Päätös**: Käyttäjän todennustilan ja roolien perusteella:
   - **Salli**: Salli navigointi ja renderöi komponentti.
   - **Estä**: Estä navigointi ja ohjaa kirjautumissivulle tai pääsy evätty -sivulle.
4. **Renderöi tai ohjaa**: Joko pyydetty komponentti näkyy, tai käyttäjä ohjataan asianmukaisesti.

Automaattisella valvonnalla turvallisuussäännöt sovelletaan johdonmukaisesti koko sovelluksesi laajuudessa, joten pääsynhallinta hoidetaan ennen kuin mitään komponenttia renderöidään ja kehittäjien ei tarvitse lisätä manuaalisia tarkistuksia jokaiseen näkymään.

## Todennus VS valtuutus {#authentication-vs-authorization}

Jotta voit toteuttaa turvallisuuden sovelluksessasi oikein, on tärkeää tietää eroa näiden kahden käsitteen välillä:

- **Todennus**: Tarkistetaan, kuka käyttäjä on. Tämä tapahtuu yleensä kirjautumisen aikana, kun käyttäjä antaa kirjautumistiedot (käyttäjätunnus ja salasana). Kun käyttäjä on todennettu, hänen henkilöllisyytensä tallennetaan istuntoon tai turvallisuuskontekstiin.

- **Valtuutus**: Tarkistetaan, mihin todennetulla käyttäjällä on oikeus. Tämä tarkoittaa käyttöoikeuksien tai roolien tarkistamista, joita käyttäjällä on tarvittava päästäkseen tietylle reitille. Valtuutus tapahtuu joka kerta, kun käyttäjä navigoi suojatulle reitille.

webforJ:n turvallisuusjärjestelmä hoitaa molemmat näkökohdat:

- Annotaatiot kuten `@PermitAll` hoitavat todennusvaatimukset.
- Annotaatiot kuten `@RolesAllowed` hoitavat valtuutusvaatimukset.

## Aloittaminen {#getting-started}

Tämä oppaatus olettaa, että käytät **Spring Bootia yhdessä Spring Securityn kanssa**, mikä on suositeltu lähestymistapa useimmille webforJ-sovelluksille. Spring Security tarjoaa alan standardin mukaisen todennuksen ja valtuutuksen automaattisella konfiguroinnilla Spring Bootin kautta.

Tämä dokumentaatio oppii sinua suojaamaan reittejäsi Spring Securityn kanssa, perusasetuksista edistyneisiin ominaisuuksiin. Jos et käytä Spring Bootia tai tarvitset mukautetun turvallisuustoteutuksen, tutustu [Turvallisuusarkkitehtuuri -oppaaseen](/docs/security/architecture/overview) oppiaksesi, kuinka järjestelmä toimii ja kuinka toteuttaa mukautettu turvallisuus.

## Aiheet {#topics}

<DocCardList className="topics-section" />
