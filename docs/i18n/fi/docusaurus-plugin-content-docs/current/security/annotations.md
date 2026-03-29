---
sidebar_position: 3
title: Security Annotations
_i18n_hash: 564a7d991d26edb972bc2c7c99366f37
---
Turvallisuusannotaatiot tarjoavat deklaratiivisen tavan hallita pääsyä reitteihin webforJ-sovelluksessa. Lisäämällä annotaatioita reittikomponentteihisi määrittelet, kuka voi käyttää kutakin näkymää ilman manuaalisten käyttöoikeustarkistusten kirjoittamista. Turvallisuusjärjestelmä valvoo näitä sääntöjä automaattisesti ennen kuin mitään komponenttia renderöidään.

:::info Toteutus huomautus
Tämä opas toimii minkä tahansa turvallisuustoteutuksen kanssa. Esimerkit toimivat sekä Spring Securityn että mukautettujen toteutusten kanssa. Jos et käytä Spring Bootia, katso [Turvallisuusarkkitehtuurin opas](/docs/security/architecture/overview) ymmärtääksesi perustan ja toteuttaaksesi mukautetun turvallisuuden.
:::

## `@AnonymousAccess` - julkiset reitit {#anonymousaccess-public-routes}

`@AnonymousAccess`-annotaatio merkitsee reitin julkisesti saatavaksi. Käyttäjien ei tarvitse olla todennettuja voidakseen käyttää näitä reittejä. Tätä annotaatiota käytetään tyypillisesti kirjautumissivuilla, julkisilla lähtöpagesilla tai muussa sisällössä, joka tulisi olla kaikkien saatavilla.

```java title="LoginView.java"
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {

  public LoginView() {
    // kirjautumisnäkymä
  }
}
```

Esimerkissä:
- Kaikki käyttäjät, todennetut tai ei, voivat käyttää `/login`-reittiä.
- Kun `@AnonymousAccess` on läsnä, todennusta vaatimattomat käyttäjät saavat käyttää tätä sivua ilman, että heitä ohjataan toiseen paikkaan.

:::tip Yleiset käyttötapaukset
Käytä `@AnonymousAccess`-annotaatiota kirjautumissivuilla, rekisteröintisivuilla, julkisilla etusivuilla, käyttöehdoissa, tietosuojakäytännöissä ja muussa sisällössä, joka pitäisi olla saatavilla ilman todennusta.
:::

## `@PermitAll` - todennetut reitit {#permitall-authenticated-routes}

`@PermitAll`-annotaatio edellyttää, että käyttäjien on oltava todennettuja, mutta se ei pakota mitään erityisiä roolivaatimuksia. Mikä tahansa sisäänkirjautunut käyttäjä voi käyttää näitä reittejä rooleistaan tai oikeuksistaan riippumatta.

```java title="InboxView.java"
@Route(value = "/", outlet = MainLayout.class)
@PermitAll
public class InboxView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public InboxView() {
    self.setHeight("100%");
    self.add(new Explore("Saapuneet"));
  }
}
```

Esimerkissä:
- Käyttäjien on oltava todennettuja päästäkseen saapuneisiin.
- Mikä tahansa todennettu käyttäjä voi tarkastella tätä sivua roolistaan riippumatta.
- Todennusta vaatimattomat käyttäjät ohjataan kirjautumissivulle.

:::info Oletusarvoisesti turvallinen tila
Kun oletusarvoisesti turvallinen tila on käytössä, reitit, joilla ei ole mitään turvallisuusannotaatiota, toimivat samalla tavalla kuin `@PermitAll`—ne edellyttävät todennusta. Katso [oletusarvoisesti turvallinen osio](#secure-by-default) lisätietoja varten.
:::

## `@RolesAllowed` - rooli-pohjaiset reitit {#rolesallowed-role-based-routes}

`@RolesAllowed`-annotaatio rajoittaa pääsyä käyttäjille, joilla on erityisiä rooleja. Voit määrittää yhden tai useamman roolin, ja käyttäjillä on oltava ainakin yksi luetelluista rooleista päästäkseen reitille.

### Yhden roolin vaatimus {#single-role-requirement}

```java title="TrashView.java"
@Route(value = "/trash", outlet = MainLayout.class)
@RolesAllowed("ADMIN")
public class TrashView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TrashView() {
    self.setHeight("100%");
    self.add(new Explore("Roski"));
  }
}
```

Esimerkissä:
- Vain käyttäjät, joilla on `ADMIN`-rooli, voivat käyttää roskanäkymää.
- Käyttäjät, joilla ei ole `ADMIN`-roolia, ohjataan pääsy estetty -sivulle.

### Usean roolin vaatimukset {#multiple-role-requirements}

```java title="SettingsView.java"
@Route(value = "/settings", outlet = MainLayout.class)
@RolesAllowed({"ADMIN", "MANAGER"})
public class SettingsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public SettingsView() {
    self.add(new Explore("Asetukset"));
  }
}
```

Esimerkissä:
- Käyttäjät, joilla on joko `ADMIN`- tai `MANAGER`-rooli, voivat käyttää asetuksia.
- Käyttäjän on vain oltava yksi luetelluista rooleista, ei kaikkia.

:::tip Roolin nimeämiskäytännöt
Käytä isoja roolin nimiä (kuten `ADMIN`, `USER`, `MANAGER`) johdonmukaisuuden vuoksi. Tämä vastaa yleisiä turvallisuuskehysten käytäntöjä ja tekee koodistasi luettavampaa.
:::

## `@DenyAll` - estetyt reitit {#denyall-blocked-routes}

`@DenyAll`-annotaatio estää pääsyn reitille kaikilta käyttäjiltä riippumatta todennustilasta tai rooleista. Tämä on hyödyllistä reittien tilapäisessä poistamisessa käytöstä huoltotöiden aikana tai reitille, joka on kehitysvaiheessa.

```java title="MaintenanceView.java"
@Route(value = "/maintenance", outlet = MainLayout.class)
@DenyAll
public class MaintenanceView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public MaintenanceView() {
    self.add(new Paragraph("Tämä sivu on huollossa."));
  }
}
```

Esimerkissä:
- Kukaan käyttäjä ei voi käyttää tätä reittiä, edes ylläpitäjät.
- Kaikki pääsyyritykset johtavat pääsy estetty -sivulle uudelleenohjaukseen.

:::warning Tilapäinen käyttö
`@DenyAll` on tyypillisesti käytössä tilapäisesti kehityksen tai huollon aikana. Tuotantosovelluksille harkitse reitin poistamista kokonaan tai asianmukaisen roolirajoituksen käyttämistä sen sijaan.
:::

## Mitä tapahtuu, kun pääsy evätään {#what-happens-when-access-is-denied}

Kun käyttäjä yrittää käyttää reittiä, johon hänellä ei ole lupaa, turvallisuusjärjestelmä käsittelee evämisen automaattisesti:

1. **Todennusta vaatimattomat käyttäjät**: Ohjataan kirjautumissivulle, joka on määritetty turvallisuusasetusasiakirjassasi.
2. **Todennetut käyttäjät, joilta puuttuvat vaaditut roolit**: Ohjataan pääsy estetty -sivulle.
3. **Kaikki käyttäjät `@DenyAll` -reiteillä**: Ohjataan pääsy estetty -sivulle.

Voit mukauttaa näitä uudelleenohjauspaikkoja vastaamaan sovelluksesi navigointirakennetta niin, että pääsydenialit ja todennuspäivityspyynnöt johtavat haluttuihin sivuihin. Katso [Määritä Spring Security](/docs/security/getting-started#configure-spring-security) konfiguraatiotietoja varten.

## Oletusarvoisesti turvallinen {#secure-by-default}

Oletusarvoisesti turvallinen on konfiguraatio-ominaisuus, joka määrittelee, miten reittejä, joilla ei ole mitään turvallisuusannotaatiota, käsitellään. Kun se on käytössä, kaikki reitit vaativat todennusta oletusarvoisesti, ellei niitä nimenomaan merkitä `@AnonymousAccess`-annotaatiolla.

### Käytössä (suositeltavaa tuotannossa) {#enabled-recommended-for-production}

Lisää tämä `application.properties`-tiedostoon:

```properties title="application.properties"
webforj.security.secure-by-default=true
```

Kun oletusarvoisesti turvallinen on käytössä:
- Reitit, joilla ei ole annotaatioita, vaativat todennusta (sama kuin `@PermitAll`).
- Vain `@AnonymousAccess`-reitit ovat julkisesti saatavilla.
- Sinun on nimenomaisesti merkittävä julkiset reitit, jolloin suojatun sisällön vahingossa altistumisen riski vähenee.

```java
// Vaatii todennusta (ilman annotaatiota)
@Route("/dashboard")
public class DashboardView extends Composite<Div> { }

// Julkinen pääsy (nimenomaisesti merkitty)
@Route("/about")
@AnonymousAccess
public class AboutView extends Composite<Div> { }
```

### Pois käytöstä (salli oletusarvoisesti) {#disabled-allow-by-default}

```properties title="application.properties"
webforj.security.secure-by-default=false
```

Kun oletusarvoisesti turvallinen on pois käytöstä:
- Reitit, joilla ei ole annotaatioita, ovat julkisesti saatavilla.
- Sinun on nimenomaisesti lisättävä `@PermitAll` tai `@RolesAllowed` suojatakseen reittejä.
- Helpompi kehityksessä, mutta riskialttiimpi tuotannossa.

```java
// Julkinen pääsy (ilman annotaatiota)
@Route("/about")
public class AboutView extends Composite<Div> { }

// Vaatii todennusta (nimenomaisesti merkitty)
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> { }
```

:::tip Paras käytäntö
Ota `secure-by-default` käyttöön tuotantosovelluksille. Tällä asetuksella suojattuja reittejä ei altisteta, ellei niitä ole nimenomaisesti merkitty julkisiksi, mikä vähentää vahingossa altistumisen riskiä puuttuvien annotaatioiden vuoksi. Kytke se pois päältä vain varhaisessa kehityksessä, jos toimit liian paljon annotaatioita hankalana.
:::
