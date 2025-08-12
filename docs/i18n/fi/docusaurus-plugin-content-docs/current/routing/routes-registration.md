---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 5075588c497508fc77d7f76b1b412cf4
---
Rekisteröinnin lisäksi [reittiä @Route -annotaatioiden avulla](./defining-routes) on mahdollista rekisteröidä, päivittää tai poistaa reittejä dynaamisesti suoritusajon aikana sovelluksen logiikan, käyttäjäroolien tai muiden ehtojen perusteella. Tämä joustavuus mahdollistaa navigoinnin hallinnan dynaamisemmin, sen sijaan että reitit määritellään staattisesti käännösaikana.

## Reittien dynaaminen rekisteröinti {#registering-routes-dynamically}

Voit rekisteröidä reitin dynaamisesti käyttämällä `RouteRegistry`-luokkaa, joka on saatavilla `Routerin` kautta. Tämä mahdollistaa uusien reittien lisäämisen suoritusaikana, mikä mahdollistaa joustavan navigoinnin.

### Esimerkki: Dynaamisen reitin rekisteröinti {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi asetusten reitti dynaamisesti
registry.register("/settings", SettingsView.class);

// Siirry asetusten näkymään
router.navigate(SettingsView.class);
```

Tässä esimerkissä `/settings`-reitti rekisteröidään dynaamisesti, ja sovellus navigoi juuri rekisteröityyn näkymään.

## Ehdollinen reittien rekisteröinti {#conditional-route-registration}

Usein reittejä on lisättävä tai poistettava tietyistä ehdoista, kuten käyttäjärooleista tai sovelluksen tilasta. Dynaamisen reitityksen avulla voit rekisteröidä tai poistaa reittejä ehdollisesti suoritusaikana.

### Esimerkki: Ehdollinen rekisteröinti käyttäjäroolen perusteella {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Tarkista käyttäjärooli ja rekisteröi sopivat reitit
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Siirry sopivaan hallintapaneeliin
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

Tässä esimerkissä:
- `/editor/dashboard` tai `/viewer/dashboard` reitti rekisteröidään dynaamisesti käyttäjän roolin perusteella.
- Sovellus navigoi sopivaan hallintapaneeliin käyttäjän käyttöoikeuksien mukaan.

## Reittien poistaminen {#removing-routes}

Juuri kuten reittejä voidaan lisätä dynaamisesti, niitä voidaan myös poistaa suoritusaikana, kun niitä ei enää tarvita tai kun sovelluksen konteksti muuttuu.

### Esimerkki: Rekisteröidyn reitin poistaminen {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Poista reitti asetusten näkymälle
registry.unregister("/settings");

// Vaihtoehtoisesti, poista komponenttaluokan mukaan
registry.unregister(SettingsView.class);
```

Tässä esimerkissä `/settings`-reitti poistuu dynaamisesti, kun sitä ei enää tarvita.

## Reittien rekisteröinti sovelluksen käynnistyksessä {#registering-routes-at-app-startup}

Voit rekisteröidä dynaamisia reittejä sovelluksen alustusvaiheessa, jolloin tietyt näkymät ovat saatavilla ympäristöstä tai asetuksista riippuen käynnistyksen aikana.

### Esimerkki: Reittien rekisteröinti sovelluksen käynnistyksen aikana {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Rekisteröi debug-näkö vain kehitysmoodissa
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

Tässä esimerkissä:
- `DebugView` rekisteröidään dynaamisesti sovelluksen käynnistyessä, mutta vain jos sovellus toimii kehitysmoodissa.

## `@Route`-annotoidun komponentin dynaaminen rekisteröinti {#registering-route-annotated-components-dynamically}

Lisäksi manuaalisten reittien määrittämisen ohella on mahdollista rekisteröidä dynaamisesti komponentteja, jotka on jo merkitty `@Route`-annotaatiolla. Tämä on hyödyllistä, kun haluat käyttää etukäteen merkittyjä luokkia, mutta rekisteröidä ne dynaamisesti sovelluksen logiikan perusteella.

### Esimerkki: `@Route`-annotoidun komponentin rekisteröinti {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Profiilinäkymän logiikka
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi ProfileView dynaamisesti sen @Route-annotaation kanssa
registry.register(ProfileView.class);

// Siirry ProfileVieween
router.navigate(ProfileView.class);
```

Tässä esimerkissä:
- `ProfileView`-luokka on merkitty `@Route("profile")`.
- Reitti rekisteröidään dynaamisesti suoritusaikana käyttäen `registry.register(ProfileView.class)`.

## Reittien rekisteröinti kokonaisesta paketista {#registering-routes-from-an-entire-package}

Jos sovelluksessasi on suuri määrä reittejä, jotka on järjestetty pakettiin, voit rekisteröidä kaikki `@Route`-annotoidut komponentit paketin dynaamisesti.

### Esimerkki: Kaikkien reittien rekisteröinti paketista {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi kaikki reitit "com.myapp.admin" paketista
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

Tässä esimerkissä:
- `ofPackage`-metodi skannaa `com.myapp.admin`-paketin ja rekisteröi kaikki `@Route`-annotaatiolla varustetut luokat.
- Tämä on erityisen hyödyllistä suurissa sovelluksissa, joissa on lukuisia reittejä, jotka on järjestetty paketteihin.

## Rekisteröityjen reittien hakeminen {#retrieving-registered-routes}

Voit käyttää `RouteRegistry`-luokkaa saadaksesi listan kaikista dynaamisesti rekisteröidyistä reiteistä. Tämä on hyödyllistä, kun sinun on hallittava tai näytettävä saatavilla olevia reittejä ohjelmallisesti.

### Esimerkki: Rekisteröityjen reittien hakeminen ja näyttäminen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Polku: " + route.getPath()));
```

Tässä esimerkissä sovellus saa kaikki tällä hetkellä rekisteröidyt reitit ja tulostaa niiden polut.

## Reitti-aliasien hallinta dynaamisesti {#managing-route-aliases-dynamically}

webforJ mahdollistaa useiden aliasien rekisteröinnin yhdelle näkymälle. Tämä tarkoittaa, että käyttäjät voivat käyttää samaa näkymää eri URL-reittien kautta.

### Esimerkki: Reitti-aliasien rekisteröinti dynaamisesti {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi pääreitti
registry.register("/contact", ContactView.class);

// Rekisteröi aliasit contact-näkymälle
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

Tässä esimerkissä `ContactView` on saavutettavissa kolmen eri polun kautta: `/contact`, `/support` ja `/help`.
