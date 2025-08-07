---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 8f6b7b85dd246adc8d98c8a5bf994a39
---
Lisäksi [reittien rekisteröimisestä `@Route`-annotaatioiden avulla](./defining-routes) on mahdollista rekisteröidä, päivittää tai poistaa reittejä dynaamisesti suoritusaikana sovelluksen logiikan, käyttäjäroolien tai muiden ehtojen perusteella. Tämä joustavuus mahdollistaa navigoinnin hallinnan dynaamisemmin sen sijaan, että reitit määritellään staattisesti käännösaikana.

## Reittien dynaaminen rekisteröinti {#registering-routes-dynamically}

Voit rekisteröidä reitin dynaamisesti käyttäen `RouteRegistry`-luokkaa, joka on saatavilla `Routerin` kautta. Tämä mahdollistaa uusien reittien lisäämisen suoritusaikana, mikä mahdollistaa joustavamman navigoinnin.

### Esimerkki: Dynaamisen reitin rekisteröinti {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi asetukset-reitti dynaamisesti
registry.register("/settings", SettingsView.class);

// Siirry asetukset-näkymään
router.navigate(SettingsView.class);
```

Tässä esimerkissä `/settings`-reitti rekisteröidään dynaamisesti ja sovellus siirtyy juuri rekisteröityyn näkymään.

## Ehdollinen reittien rekisteröinti {#conditional-route-registration}

Usein reittejä on tarpeen lisätä tai poistaa tiettyjen ehtojen, kuten käyttäjäroolien tai sovelluksen tilan perusteella. Dynaamisen reitityksen avulla voit rekisteröidä tai peruuttaa reittejä ehdollisesti suoritusaikana.

### Esimerkki: Ehdollinen rekisteröinti käyttäjäroolin mukaan {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Tarkista käyttäjärooli ja rekisteröi sopivat reitit
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Siirry sopivaan dashboardiin
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

Tässä esimerkissä:
- `/editor/dashboard`- tai `/viewer/dashboard`-reitti rekisteröidään dynaamisesti käyttäjän roolin mukaan.
- Sovellus siirtyy sopivaan dashboardiin käyttäjän pääsyoikeuksien perusteella.

## Reittien poistaminen {#removing-routes}

Samoin kuin reittejä voidaan lisätä dynaamisesti, niitä voidaan myös poistaa suoritusaikana, kun niitä ei enää tarvita tai kun sovelluksen konteksti muuttuu.

### Esimerkki: Rekisteröidyn reitin poistaminen {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Poista asetukset-näkymän reitti
registry.unregister("/settings");

// Valinnaisesti, poista komponenttiluokan mukaan
registry.unregister(SettingsView.class);
```

Tässä esimerkissä `/settings`-reitti poistetaan dynaamisesti, kun sitä ei enää tarvita.

## Reittien rekisteröinti sovelluksen käynnistyksessä {#registering-routes-at-app-startup}

Voit rekisteröidä dynaamisia reittejä sovelluksen alustuksen aikana, jolloin tietyt näkymät ovat saatavilla ympäristön tai kokoonpanon perusteella käynnistyksessä.

### Esimerkki: Reittien rekisteröinti sovelluksen käynnistyksen aikana {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Rekisteröi debug-näkymä vain kehitysmallissa
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

Tässä esimerkissä:
- `DebugView` rekisteröidään dynaamisesti sovelluksen käynnistyksen aikana, mutta vain jos sovellus on käynnissä kehitysmallissa.

## Dynaamisesti rekisteröidyt `@Route`-annotoitu komponentit {#registering-route-annotated-components-dynamically}

Manuaalisten reittien määrittämisen lisäksi on mahdollista rekisteröidä dynaamisesti `@Route`-annotaatiolla jo varustettuja komponentteja. Tämä on kätevää, kun haluat hyödyntää esimerkit-annotoituja luokkia mutta rekisteröidä ne dynaamisesti sovelluksen logiikan perusteella.

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

// Siirry ProfileView-näkymään
router.navigate(ProfileView.class);
```

Tässä esimerkissä:
- `ProfileView`-luokka on annotoitu `@Route("profile")`.
- Reitti rekisteröidään dynaamisesti suoritusaikana käyttäen `registry.register(ProfileView.class)`.

## Reittien rekisteröinti koko paketista {#registering-routes-from-an-entire-package}

Jos sovelluksessasi on suuri määrä reittejä, jotka on järjestetty pakettiin, voit rekisteröidä kaikki `@Route`-annotoituja komponentteja paketista dynaamisesti.

### Esimerkki: Kaikkien reittien rekisteröinti paketista {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi kaikki reitit "com.myapp.admin" -paketista
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

Tässä esimerkissä:
- `ofPackage`-menetelmä skannaa `com.myapp.admin`-paketin ja rekisteröi kaikki `@Route`-annotoitu luokat.
- Tämä on erityisen hyödyllistä suurille sovelluksille, joissa on lukuisia reittejä, jotka on järjestetty paketteihin.

## Rekisteröityjen reittien hakeminen {#retrieving-registered-routes}

Voit hakea luettelon kaikista dynaamisesti rekisteröidyistä reiteistä käyttämällä `RouteRegistry`-luokkaa. Tämä on hyödyllistä, kun sinun tarvitsee hallita tai näyttää ohjelmallisesti saatavilla olevia reittejä.

### Esimerkki: Kaikkien rekisteröityjen reittien hakeminen ja näyttäminen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Polku: " + route.getPath()));
```

Tässä esimerkissä sovellus hakee kaikki tällä hetkellä rekisteröidyt reitit ja tulostaa niiden polut.

## Reitti Alias:ien hallinta dynaamisesti {#managing-route-aliases-dynamically}

webforJ mahdollistaa useiden alias-nimien rekisteröimisen yhdelle näkymälle. Tämä tarkoittaa, että käyttäjät voivat käyttää samaa näkymää eri URL-polkujen kautta.

### Esimerkki: Reitti Alias:ien dynaaminen rekisteröinti {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi ensisijainen reitti
registry.register("/contact", ContactView.class);

// Rekisteröi alias-nimiä kontaktinäkymälle
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

Tässä esimerkissä `ContactView` on saavutettavissa kolmella eri polulla: `/contact`, `/support` ja `/help`.
