---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Protect webforJ routes with declarative annotations and centralized
  authentication and authorization enforcement.
_i18n_hash: 850b9636996cb17a07a7aff25ac3cd0e
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
Tämä ominaisuus on julkisessa esikatselussa ja valmis tuotantokäyttöön. Esikatselukauden aikana API:ta voidaan hienosäätää kehittäjäyhteisön palautteen perusteella. Mahdolliset muutokset ilmoitetaan etukäteen julkaisumerkinnöissä, ja siirtymiseen liittyvät oppaat annetaan tarvittaessa.
:::

Nykyisissä web-sovelluksissa **turvallisuus** viittaa pääsyn hallintaan sovelluksen eri osiin käyttäjän identiteetin ja oikeuksien perusteella. webforJ:ssa turvallisuus tarjoaa kehyksen **reittikohtaiselle pääsynhallinnalle**, jossa voit suojata näkymiä, vaatia todennusta ja ottaa käyttöön roolipohjaisia oikeuksia.

<AISkillTip skill="webforj-securing-apps" />

## Perinteinen VS suojattu reititys {#traditional-vs-secured-routing}

Perinteisessä suojaamattomassa reitityksessä kaikki sovelluksen reitit ovat kaikkien käytettävissä, jotka tietävät URL-osoitteen. Tämä tarkoittaa, että käyttäjät voivat navigoida herkille sivuille, kuten ylläpitopaneeleihin tai käyttäjäpaneeleihin, ilman mitään todennusta tai valtuutustarkastuksia. Kehittäjien vastuulle jää muistaa tarkistaa oikeudet jokaisessa komponentissa manuaalisesti, mikä johtaa epätasaiseen turvallisuuden täytäntöönpanoon ja mahdollisiin haavoittuvuuksiin.

Tämä lähestymistapa tuo mukanaan useita ongelmia:

1. **Manuaaliset tarkastukset**: Kehittäjien on muistettava lisätä turvallisuuslogiikka jokaiseen suojattuun näkymään tai asetteluun.
2. **Epäyhtenäinen täytäntöönpano**: Turvallisuustarkastukset, jotka on hajautettu koko koodipohjaan, johtavat aukkoihin ja virheisiin.
3. **Ylläpidon lisäkuormitus**: Pääsääntöjen muuttaminen vaatii useiden tiedostojen päivittämistä.
4. **Ei keskitettyä hallintaa**: Ei ole yhtä paikkaa ymmärtää tai hallita sovelluksen turvallisuutta.

**Suojattu reititys** webforJ:ssa ratkaisee tämän mahdollistamalla pääsyn hallinnan suoraan reittitasolla. Turvallisuusjärjestelmä valvoo automaattisesti sääntöjä ennen kuin komponenttia renderöidään, tarjoamalla keskitetyn, deklaratiivisen lähestymistavan sovelluksen turvallisuuteen. Näin se toimii:

1. **Deklaratiiviset annotaatiot**: Merkitse reitit turvallisuusannotaatioilla määrittääksesi pääsyvaatimukset.
2. **Automaattinen täytäntöönpano**: Turvallisuusjärjestelmä tarkistaa oikeudet ennen minkään näkymän renderöintiä.
3. **Keskitetty konfiguraatio**: Määritä turvallisuuskäyttäytyminen yhdessä paikassa ja sovella se johdonmukaisesti.
4. **Joustavat toteutukset**: Valitse Spring Security -integraation tai mukautetun perus Java -toteutuksen välillä.

Tämä suunnittelu mahdollistaa **todennuksen** (käyttäjän identiteetin vahvistamisen) ja **valtuutuksen** (vahvistaa, mihin käyttäjä voi päästä), joten vain valtuutetuilla käyttäjillä on pääsy suojattuihin reitteihin. Valtuuttamattomat käyttäjät ohjataan automaattisesti tai heiltä evätään pääsy määritettyjen turvallisuus sääntöjen perusteella.

## Esimerkki suojatusta reitityksestä webforJ:ssa {#example-of-secured-routing-in-webforj}

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

- `LoginView` on merkitty `@AnonymousAccess`, mikä sallii tarkistamattomien käyttäjien käyttää sitä.
- `ProductsView` ei sisällä turvallisuusannotaatiota, mikä tarkoittaa, että se vaatii todennuksen oletusarvoisesti (kun `secure-by-default` -tila on käytössä).
- `InvoicesView` vaatii `ACCOUNTANT` -roolin, joten vain käyttäjät, joilla on kirjanpito-oikeudet, voivat käyttää laskuja.

## Miten turvallisuus toimii {#how-security-works}

Kun käyttäjä yrittää navigoida reitille, turvallisuusjärjestelmä seuraa tätä prosessia:

1. **Navigointi aloitetaan**: Käyttäjä klikkaa linkkiä tai syöttää URL-osoitteen.
2. **Turvallisuuden tarkistus**: Ennen komponentin renderöintiä järjestelmä arvioi turvallisuusannotaatioita ja sääntöjä.
3. **Päätös**: Käyttäjän todennusstatus ja roolit huomioiden:
   - **Myönnä**: Salli navigointi ja renderöi komponentti.
   - **Hylkää**: Estä navigointi ja ohjaa kirjautumissivulle tai pääsy kielletty -sivulle.
4. **Renderöinti tai uudelleenohjaus**: Joko pyydetty komponentti näytetään tai käyttäjä ohjataan asianmukaisesti.

Automaattisen täytäntöönpanon avulla turvallisuussäännöt otetaan käyttöön johdonmukaisesti koko sovelluksessa, joten pääsyhallinta hoidetaan ennen minkään komponentin renderöintiä, eikä kehittäjien tarvitse lisätä manuaalisia tarkistuksia mihinkään näkymään.

## Todennus VS valtuutus {#authentication-vs-authorization}

Jotta voit toteuttaa turvallisuuden sovelluksessasi oikein, on tärkeää ymmärtää näiden kahden käsitteen ero:

- **Todennus**: Vahvistaa, kuka käyttäjä on. Tämä tapahtuu tyypillisesti kirjautumisen aikana, kun käyttäjä antaa henkilötiedot (käyttäjätunnus ja salasana). Kun käyttäjä on todennettu, hänen identiteettinsä tallennetaan istuntoon tai turvallisuuskontekstiin.

- **Valtuutus**: Vahvistaa, mihin todennettu käyttäjä voi päästä. Tämä tarkoittaa tarkistamista, onko käyttäjällä vaadittavat roolit tai oikeudet päästä tiettyyn reittiin. Valtuutus tapahtuu aina, kun käyttäjä navigoi suojatulle reitille.

webforJ:n turvallisuusjärjestelmä käsittelee molemmat näkökohdat:

- Annotations kuten `@PermitAll` käsittelevät todennusvaatimuksia.
- Annotations kuten `@RolesAllowed` käsittelevät valtuutusvaatimuksia.

## Aloittaminen {#getting-started}

Tämä opas olettaa, että käytät **Spring Bootia yhdessä Spring Securityn kanssa**, mikä on suositeltu lähestymistapa useimmille webforJ-sovelluksille. Spring Security tarjoaa alan standardin mukaisen todennuksen ja valtuutuksen automaattisen konfiguroinnin avulla Spring Bootin kautta.

Tämän dokumentaation loppuosa käynnistää sinut reittiesi suojaamisen Spring Securityn avulla, perusasetuksesta kehittyneisiin ominaisuuksiin. Jos et käytä Spring Bootia tai tarvitset mukautetun turvallisuustoteutuksen, katso [Turvallisuusarkkitehtuurin opas](/docs/security/architecture/overview) oppiaksesi, miten järjestelmä toimii ja kuinka toteuttaa mukautettu turvallisuus.

## Aiheet {#topics}

<DocCardList className="topics-section" />
