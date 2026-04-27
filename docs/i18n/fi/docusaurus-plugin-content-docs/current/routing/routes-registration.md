---
sidebar_position: 11
title: Routes Registration
_i18n_hash: def139d3db58322c269afef10acdf5fd
---
Lisäksi [reittien rekisteröimisestä `@Route`-annotaatioiden avulla](./defining-routes) on mahdollista rekisteröidä, päivittää tai poistaa reittejä dynaamisesti ajoitusvaiheessa sovelluksen logiikan, käyttäjäroolien tai muiden ehtojen perusteella. Tämä joustavuus mahdollistaa navigoinnin hallitsemisen dynaamisemmin, sen sijaan, että reitit määriteltäisiin staattisesti käännösvaiheessa.


## Dynaaminen reittien rekisteröinti {#registering-routes-dynamically}

Voit rekisteröidä reitin dynaamisesti `RouteRegistry`-luokan avulla, johon pääsee käsiksi `Router`in kautta. Tämä mahdollistaa uusien reittien lisäämisen ajoitusvaiheessa, mikä mahdollistaa joustavan navigoinnin.

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

## Ehtoinen reittien rekisteröinti {#conditional-route-registration}

Usein reittejä täytyy lisätä tai poistaa tiettyjen ehtojen, kuten käyttäjäroolien tai sovelluksen tilan perusteella. Dynaamisen reitityksen avulla voit rekisteröidä tai poistaa reittejä ehdollisesti ajoitusvaiheessa.

### Esimerkki: ehtoinen rekisteröinti käyttäjäroolin perusteella {#example-conditional-registration-based-on-user-role}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Tarkista käyttäjärooli ja rekisteröi sopivat reitit
if (user.hasRole("editor")) {
  registry.register("/editor/dashboard", EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  registry.register("/viewer/dashboard", ViewerDashboardView.class);
}

// Siirry sopivalle kojelaudalle
if (user.hasRole("editor")) {
  router.navigate(EditorDashboardView.class);
} else if (user.hasRole("viewer")) {
  router.navigate(ViewerDashboardView.class);
}
```

Tässä esimerkissä:
- `/editor/dashboard` tai `/viewer/dashboard` -reitti rekisteröidään dynaamisesti käyttäjän roolin perusteella.
- Sovellus navigoi sopivalle kojelaudalle käyttäjän käyttöoikeuksien mukaan.

## Reittien poistaminen {#removing-routes}

Samoin kuin reittejä voidaan lisätä dynaamisesti, niitä voidaan myös poistaa ajoitusvaiheessa, kun niitä ei enää tarvita tai kun sovelluksen konteksti muuttuu.

### Esimerkki: Rekisteröidyn reitin poistaminen {#example-removing-a-registered-route}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Poista reitti asetusten näkymälle
registry.unregister("/settings");

// Valinnaisesti, poista myös komponenttiluokan mukaan
registry.unregister(SettingsView.class);
```

Tässä esimerkissä `/settings`-reitti poistetaan dynaamisesti, kun sitä ei enää tarvita.

## Reittien rekisteröinti sovelluksen käynnistyksessä {#registering-routes-at-app-startup}

Voit rekisteröidä dynaamisia reittejä sovelluksen alussa, jolloin tietyt näkymät ovat saatavilla ympäristön tai konfiguraation perusteella käynnistyessä.

### Esimerkki: Reittien rekisteröinti sovelluksen käynnistyksessä {#example-registering-routes-during-app-startup}

```java
@Routify
public class Application extends App {

  @Override
  protected void onWillRun() {
    // Rekisteröi debug-näkymä vain kehitystilassa
    if (Environment.getCurrent().isDebug()) {
      Router router = Router.getCurrent();
      RouteRegistry registry = router.getRegistry();

      registry.register("/debug", DebugView.class);
    }
  }
}
```

Tässä esimerkissä:
- `DebugView` rekisteröidään dynaamisesti sovelluksen käynnistyksessä, mutta vain jos sovellus toimii kehitystilassa.

## `@Route`-annotoidun komponentin dynaaminen rekisteröinti {#registering-route-annotated-components-dynamically}

Lisäksi manuaalisesti määriteltyjen reittien ohella on mahdollista rekisteröidä dynaamisesti jo `@Route`-annotaatioilla merkittyjä komponentteja. Tämä on hyödyllistä, kun haluat hyödyntää etukäteen annotoitujen luokkien etuja, mutta rekisteröidä niitä dynaamisesti sovelluksen logiikan perusteella.

### Esimerkki: `@Route`-annotoidun komponentin rekisteröinti {#example-registering-an-route-annotated-component}

```java
@Route("profile")
public class ProfileView extends Composite<Div> {
  // Profiilin näkymän logiikka
}

Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi ProfileView dynaamisesti sen @Route-annotaation avulla
registry.register(ProfileView.class);

// Siirry ProfileViewiin
router.navigate(ProfileView.class);
```

Tässä esimerkissä:
- `ProfileView`-luokalla on `@Route("profile")`-annotaatio.
- Reitti rekisteröidään dynaamisesti ajoitusvaiheessa käyttäen `registry.register(ProfileView.class)`.

## Reittien rekisteröinti koko paketista {#registering-routes-from-an-entire-package}

Jos sovelluksellasi on suuri määrä reittejä, jotka on järjestetty pakettiin, voit rekisteröidä kaikki `@Route`-annotoitu komponentti dynaamisesti paketin sisältä.

### Esimerkki: Rekisteröi kaikki reitit paketista {#example-registering-all-routes-from-a-package}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi kaikki reitit "com.myapp.admin" -paketista
RouteRegistry.ofPackage(new String[] { "com.myapp.admin" }, registry);
```

Tässä esimerkissä:
- `ofPackage`-metodi skannaa `com.myapp.admin` -paketin ja rekisteröi kaikki @Route-annotoidut luokat.
- Tämä on erityisen hyödyllistä suurissa sovelluksissa, joissa on lukuisia reittejä, jotka on järjestetty pakettien mukaan.

:::info Mukautettu reittitiedon löytäminen
Versiosta 25.11 alkaen integrointikehykset voivat tarjota oman reittien löytämismekanisminsa `RouteRegistryProvider` SPI:n kautta. Tämä mahdollistaa kehyskohtaiset ominaisuudet, kuten riippuvuuden injektoinnin dynaamisesti rekisteröitäville reiteille. Katso [Reittirekisterin tarjoaja](/docs/advanced/route-registry-provider) lisätietoja varten.
:::

## Rekisteröityjen reittien hakeminen {#retrieving-registered-routes}

Voit hakea listan kaikista dynaamisesti rekisteröidyistä reiteistä käyttämällä `RouteRegistry`-luokkaa. Tämä on hyödyllistä, kun sinun tarvitsee hallita tai näyttää saatavilla olevia reittejä ohjelmallisesti.

### Esimerkki: Rekisteröityjen reittien hakeminen ja näyttäminen {#example-retrieving-and-displaying-all-registered-routes}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

List<RouteEntry> routes = registry.getAvailableRouteEntires();
routes.forEach(route -> console().log("Polku: " + route.getPath()));
```

Tässä esimerkissä sovellus hakee kaikki tällä hetkellä rekisteröidyt reitit ja tulostaa niiden polut.

## Reittialiasen dynaaminen hallinta {#managing-route-aliases-dynamically}

webforJ mahdollistaa useiden aliasten rekisteröimisen yhdelle näkymälle. Tämä tarkoittaa, että käyttäjät voivat käyttää samaa näkymää eri URL-polkujen kautta.

### Esimerkki: Reittialiasen dynaaminen rekisteröinti {#example-registering-route-aliases-dynamically}

```java
Router router = Router.getCurrent();
RouteRegistry registry = router.getRegistry();

// Rekisteröi ensisijainen reitti
registry.register("/contact", ContactView.class);

// Rekisteröi aliaksia yhteydenottanäkymälle
registry.register("/support", ContactView.class);
registry.register("/help", ContactView.class);
```

Tässä esimerkissä `ContactView` on käytettävissä kolmen eri polun kautta: `/contact`, `/support` ja `/help`.
