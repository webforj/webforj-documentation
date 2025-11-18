---
sidebar_position: 3
title: Security Annotations
_i18n_hash: af9997b8bee96bfa4005a65998fddaf5
---
Security annotations tarjoavat deklaratiivisen tavan hallita reittien käyttöoikeuksia webforJ-sovelluksessa. Lisäämällä annotaatiot reittikomponentteihisi määrität, kuka voi käyttää kutakin näkymää ilman manuaalista käyttöoikeustarkastusta. Turvajärjestelmä valvoo näitä sääntöjä automaattisesti ennen minkään komponentin renderöintiä.

:::info Toteutus huomautus
Tämä opas toimii minkä tahansa turvatoteutuksen kanssa. Esimerkit toimivat sekä Spring Securityn että mukautettujen toteutusten kanssa. Jos et käytä Spring Bootia, katso [Security Architecture guide](/docs/security/architecture/overview) ymmärtääksesi perustan ja toteuttaaksesi mukautetun turvallisuuden.
:::

## `@AnonymousAccess` - julkiset reitit {#anonymousaccess-public-routes}

`@AnonymousAccess` -annotaatio merkitsee reitin julkisesti saatavaksi. Käyttäjien ei tarvitse olla todennettuja päästäkseen näille reiteille. Tätä annotaatiota käytetään tyypillisesti kirjautumissivuilla, julkisilla laskeutumissivuilla tai muussa sisällössä, jonka tulisi olla kaikkien saatavilla.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // kirjautumisnäkymä
  }
}
```

Tässä esimerkissä:
- Mikä tahansa käyttäjä, todennettu tai ei, voi käyttää `/login`-reittiä.
- Kun `@AnonymousAccess` on läsnä, todennusta vailla olevat käyttäjät voivat käyttää tätä sivua ilman, että heitä ohjataan uudelleen.

:::tip Yleiset käyttötavat
Käytä `@AnonymousAccess` -annotaatiota kirjautumissivuilla, rekisteröintisivuilla, julkisilla etusivuilla, käyttöehdoissa, tietosuojakäytännöissä ja muussa sisällössä, jonka tulisi olla saatavilla ilman todennusta.
:::

## `@PermitAll` - todennetut reitit {#permitall-authenticated-routes}

`@PermitAll` -annotaatio vaatii käyttäjien olevan todennettuja, mutta se ei pakota mitään erityisiä roolivaatimuksia. Mikä tahansa sisäänkirjautunut käyttäjä voi käyttää näitä reittejä riippumatta heidän roolistaan tai käyttöoikeuksistaan.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {

  public InboxView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Inbox"));
  }
}
```

Tässä esimerkissä:
- Käyttäjien on oltava todennettuja päästäkseen saapuneet-sivulle.
- Mikä tahansa todennettu käyttäjä voi nähdä tämän sivun riippumatta roolistaan.
- Todentamattomat käyttäjät ohjataan kirjautumissivulle.

:::info Turvallinen oletustila
Kun turvallinen oletustila on käytössä, reitit, joilla ei ole mitään turvallisuusannotaatioita, käyttäytyvät samalla tavalla kuin `@PermitAll`—ne vaativat todentamisen. Katso [turvallinen oletustila -osio](#secure-by-default) lisätietoja varten.
:::

## `@RolesAllowed` - roolipohjaiset reitit {#rolesallowed-role-based-routes}

`@RolesAllowed` -annotaatio rajoittaa pääsyä käyttäjille, joilla on tietyt roolit. Voit määrittää yhden tai useamman roolin, ja käyttäjien on oltava vähintään yksi luetelluista rooleista päästäkseen reitille.

### Yhden roolin vaatimus {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {

  public TrashView() {
    FlexLayout layout = getBoundComponent();
    layout.setHeight("100%");
    layout.add(new Explore("Trash"));
  }
}
```

Tässä esimerkissä:
- Vain käyttäjillä, joilla on `ADMIN`-rooli, on pääsy roskakansinäkymään.
- Käyttäjät, joilla ei ole `ADMIN`-roolia, ohjataan käyttöoikeus kielletty -sivuille.

### Usean roolin vaatimukset {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {

  public SettingsView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Explore("Settings"));
  }
}
```

Tässä esimerkissä:
- Käyttäjät, joilla on joko `ADMIN`- tai `MANAGER`-rooli, voivat käyttää asetuksia.
- Käyttäjän tarvitsee ainoastaan yksi luetelluista rooleista, ei kaikkia.

:::tip Roolien nimeämiskäytännöt
Käytä suureita roolinimiä (kuten `ADMIN`, `USER`, `MANAGER`) johdonmukaisuuden vuoksi. Tämä vastaa yleisiä turvallisuuskehysten käytäntöjä ja tekee koodistasi luettavampaa.
:::

## `@DenyAll` - estetyt reitit {#denyall-blocked-routes}

`@DenyAll` -annotaatio estää pääsyn reitille kaikilta käyttäjiltä, riippumatta todennustilasta tai rooleista. Tämä on hyödyllistä reittien tilapäiseen poistamiseen käytöstä huollon aikana tai reiteille, jotka ovat kehitysvaiheessa.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {

  public MaintenanceView() {
    FlexLayout layout = getBoundComponent();
    layout.add(new Paragraph("Tämä sivu on huollossa."));
  }
}
```

Tässä esimerkissä:
- Kukaan käyttäjä ei voi käyttää tätä reittiä, ei edes ylläpitäjät.
- Kaikki pääsyyritykset johtavat käyttöoikeus kielletty -sivulle.

:::warning Väliaikainen käyttö
`@DenyAll` -annotaatiota käytetään tyypillisesti tilapäisesti kehityksen tai huollon aikana. Tuotantokäyttöön harkitse reitin poistamista kokonaan tai käyttöoikeusrajoitusten käyttämistä sen sijaan.
:::

## Mitä tapahtuu, kun pääsy on kielletty {#what-happens-when-access-is-denied}

Kun käyttäjä yrittää päästä reitille, jota hänellä ei ole oikeutta nähdä, turvajärjestelmä käsittelee kiellon automaattisesti:

1. **Todentamattomat käyttäjät**: Ohjataan kirjautumissivulle, joka on määritetty turvasetupissani.
2. **Todennetut käyttäjät ilman tarvittavia rooleja**: Ohjataan käyttöoikeus kielletty -sivulle.
3. **Kaikki käyttäjät `@DenyAll`-reiteillä**: Ohjataan käyttöoikeus kielletty -sivulle.

Voit mukauttaa näitä uudelleenohjauspaikkoja vastaamaan sovelluksesi navigointirakennetta, jotta pääsyn kiellot ja todennuspyynnöt johtavat tarkoitetuille sivuille. Katso [Määritä Spring Security](/docs/security/getting-started#configure-spring-security) kokoonpanotietoja varten.

## Turvallinen oletustila {#secure-by-default}

Turvallinen oletustila on kokoonpanovaihtoehto, joka määrittää, miten reittejä, joilla ei ole minkäänlaista turvallisuusannotaatiota, käsitellään. Kun se on käytössä, kaikki reitit vaativat todennusta oletuksena, ellei niitä nimenomaan merkitä `@AnonymousAccess` -annotaatiolla.

### Käytössä (suositellaan tuotantokäyttöön) {#enabled-recommended-for-production}

Lisää tämä `application.properties`-tiedostoon:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Kun turvallinen oletustila on käytössä:
- Reitit, joilla ei ole annotaatioita, vaativat todennuksen (sama kuin `@PermitAll`).
- Vain `@AnonymousAccess` -reitit ovat julkisesti saatavilla.
- Sinun on nimenomaan merkittävä julkiset reitit, mikä vähentää riskiä vahingossa suojatun sisällön altistamisesta.

```java
// Vaatii todennuksen (ilman annotaatiota)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Julkinen pääsy (nimenomaan merkitty)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Poistettu käytöstä (sallittu oletuksena) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Kun turvallinen oletustila on poistettu käytöstä:
- Reitit, joilla ei ole annotaatioita, ovat julkisesti saatavilla.
- Sinun on nimenomaan lisättävä `@PermitAll` tai `@RolesAllowed` suojataksesi reittejä.
- Helpompi kehitykselle, mutta riskialttiimpi tuotannolle.

```java
// Julkinen pääsy (ilman annotaatiota)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Vaatii todennuksen (nimenomaan merkitty)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Parhaat käytännöt
Ota käyttöön `secure-by-default` tuotantosovelluksille. Tällä asetuksella suojattuja reittejä ei paljasteta, ellet nimenomaan merkitse niitä julkisiksi, mikä vähentää riskiä vahingossa altistaa puutteellisten annotaatioiden vuoksi. Poista tämä käytöstä vain alkuperäisessä kehityksessä, jos koet ylimääräiset annotaatiot hankaliksi.
:::
