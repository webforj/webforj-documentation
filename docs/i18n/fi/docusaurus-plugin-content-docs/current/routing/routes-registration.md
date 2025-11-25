---
sidebar_position: 11
title: Routes Registration
_i18n_hash: 0fadade88e7248bc679d489ed50b537d
---
Lisäksi [reittien rekisteröinti `@Route`-annotaatioiden avulla](./defining-routes) mahdollistaa reittien dynaamisen rekisteröinnin, päivittämisen tai poistamisen ajonaikaisesti sovelluksen logiikan, käyttäjäroolien tai muiden ehtojen perusteella. Tämä joustavuus mahdollistaa navigoinnin hallinnan dynaamisemmin sen sijaan, että reitit määriteltäisiin staattisesti käännösaikana.

## Reittien dynaaminen rekisteröinti {#registering-routes-dynamically}

Voit rekisteröidä reitin dynaamisesti `RouteRegistry`-luokan avulla, joka on saatavilla `Routerin` kautta. Tämä mahdollistaa uusien reittien lisäämisen ajonaikaisesti, jolloin navigointi on joustavampaa.

### Esimerkki: Dynaamisen reitin rekisteröinti {#example-registering-a-dynamic-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi asetukset-reitti dynaamisesti
registry.register("/settings", SettingsView.class);

// Siirry asetukset-näkymään
router.navigate(SettingsView.class);
```

Tässä esimerkissä `/settings`-reitti rekisteröidään dynaamisesti, ja sovellus siirtyy juuri rekisteröityyn näkymään.

## Ehdollinen reittien rekisteröinti {#conditional-route-registration}

Usein reittejä on tarpeen lisätä tai poistaa tiettyjen ehtojen, kuten käyttäjäroolien tai sovelluksen tilan, perusteella. Dynaamisen reitityksen avulla voit rekisteröidä tai poistaa reittejä ehdollisesti ajonaikaisesti.

### Esimerkki: Ehdollinen rekisteröinti käyttäjäroolin perusteella {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Tarkista käyttäjärooli ja rekisteröi sopivat reitit
if (user.hasRole("editor")) {
    registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Siirry sopivaan koontinäyttöön
if (user.hasRole("editor")) {
    router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
    router.navigate(ViewerDashboardView.class);
}
```

Tässä esimerkissä:
- `/editor/dashboard` tai `/viewer/dashboard`-reitti rekisteröidään dynaamisesti käyttäjän roolin perusteella.
- Sovellus siirtyy sopivaan koontinäyttöön käyttäjän pääsyoikeuksien mukaan.

## Reittien poistaminen {#removing-routes}

Samoin kuin reittejä voidaan lisätä dynaamisesti, niitä voidaan myös poistaa ajonaikaisesti, kun niitä ei enää tarvita tai kun sovelluksen konteksti muuttuu.

### Esimerkki: Rekisteröidyn reitin poistaminen {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Poista reitti asetukset-näkymälle
registry.unregister("/settings");

// Valinnaisesti, poista komponenttimallin mukaan
registry.unregister(SettingsView.class);
```

Tässä esimerkissä `/settings`-reitti poistetaan dynaamisesti, kun sitä ei enää tarvita.

## Reittien rekisteröinti sovelluksen käynnistyksen aikana {#registering-routes-at-app-startup}

Voit rekisteröidä dynaamisia reittejä sovelluksen alustamisen aikana, jolloin tietyt näkymät ovat käytettävissä ympäristön tai konfiguraation perusteella käynnistyksen yhteydessä.

### Esimerkki: Reittien rekisteröinti sovelluksen käynnistyksen aikana {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Rekisteröi virhesietotila vain kehitystilassa
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

Tässä esimerkissä:
- `DebugView` rekisteröidään dynaamisesti sovelluksen käynnistyksen aikana, mutta vain jos sovellus toimii kehitystilassa.

## Dynaamisesti rekisteröidyt `@Route`-annotoitu komponentit {#registering-route-annotated-components-dynamically}

Manuaalisten reittien määrittämisen lisäksi on mahdollista rekisteröidä komponentteja, jotka on jo annotoitu `@Route`:illa. Tämä on hyödyllistä, kun haluat hyödyntää etukäteen annotoituja luokkia, mutta rekisteröidä ne dynaamisesti sovelluslogiikan perusteella.

### Esimerkki: `@Route`-annotoitu komponentti rekisteröinti {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
    // Profiilinäkymän logiikka
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi ProfileView dynaamisesti sen @Route-annotaatiolla
registry.register(ProfileView.class);

// Siirry ProfileView-näkymään
router.navigate(ProfileView.class);
```

Tässä esimerkissä:
- `ProfileView`-luokka on annotoitu `@Route("profile")`.
- Reitti rekisteröidään dynaamisesti ajonaikana `registry.register(ProfileView.class)` avulla.

## Reittien rekisteröinti koko paketista {#registering-routes-from-an-entire-package}

Jos sovelluksellasi on suuri määrä reittejä järjestettynä paketissa, voit rekisteröidä kaikki `@Route`-annotoidut komponentit paketista dynaamisesti.

### Esimerkki: Rekisteröidään kaikki reitit paketista {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi kaikki reitit "com.myapp.admin" paketista
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

Tässä esimerkissä:
- `ofPackage`-menetelmä skannaa `com.myapp.admin` paketin ja rekisteröi kaikki luokat, jotka on annotoitu `@Route`.
- Tämä on erityisen hyödyllistä suurille sovelluksille, joissa on paljon reittejä järjestettynä paketteihin.

:::info Mukautettu reittien löytö
Versiosta 25.11 alkaen integraatiokehykset voivat tarjota oman reittien löytömekanismin `RouteRegistryProvider` SPI:n kautta. Tämä mahdollistaa kehyskohtaiset ominaisuudet, kuten riippuvuuksien injektoinnin dynaamisesti rekisteröidyille reiteille. Katso lisätietoja [Rekisteröintirekisteristä](/docs/advanced/route-registry-provider).
:::

## Rekisteröityjen reittien hakeminen {#retrieving-registered-routes}

Voit hakea luettelon kaikista dynaamisesti rekisteröidyistä reiteistä käyttämällä `RouteRegistry`-luokkaa. Tämä on hyödyllistä, kun sinun on hallittava tai näytettävä käytettävissä olevia reittejä ohjelmallisesti.

### Esimerkki: Kaikkien rekisteröityjen reittien hakeminen ja näyttäminen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Polku: " + route.getPath()));
```

Tässä esimerkissä sovellus hakee kaikki tällä hetkellä rekisteröidyt reitit ja tulostaa niiden polut.

## Reittialiasien hallinta dynaamisesti {#managing-route-aliases-dynamically}

webforJ:ssä voit rekisteröidä useita aliasia yhdelle näkymälle. Tämä tarkoittaa, että käyttäjät voivat käyttää samaa näkymää eri URL-polkujen kautta.

### Esimerkki: Reittialiasien dynaaminen rekisteröinti {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi ensisijainen reitti
registry.register("/contact", ContactView.class);

// Rekisteröi aliasit contact-näkymälle
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

Tässä esimerkissä `ContactView` on käytettävissä kolmen eri polun kautta: `/contact`, `/support`, ja `/help`.
