---
sidebar_position: 1
title: Route Types
_i18n_hash: ff067ccd8461640c772c1f8fa0dcc856
---
Reitit jaotetaan kahteen päätyyppiin, **Näyttöreitit** ja **Asetusreitit**. Reittityypin valinta määrää, miten komponentit kartoitetaan URL-osoitteisiin ja miten ne vuorovaikuttavat sovelluksen muiden osien kanssa.

## Näyttöreitit {#view-routes}

Näyttöreitit kartoittavat suoraan URL-segmenttiin ja edustavat sovelluksesi tiettyjä sivuja. Nämä reitit heijastuvat selaimen URL-osoitteessa ja niitä käytetään tyypillisesti erillisiin näkymiin tai sivuihin.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Etusivun")); // "Home Page" translated to "Etusivu"
  }
}
```

- **URL**: `/home`
- **Renderöity komponentti**: `HomeView`

Tässä esimerkissä navigointi osoitteeseen `/home` renderöi `HomeView`-komponentin.

## Asetusreitit {#layout-routes}

Asetusreitit kääriivät lapsinäkymiä ilman, että ne vaikuttavat URL-osoitteeseen. Asetukset tarjoavat jaettuja käyttöliittymäelementtejä, kuten otsikoita tai sivupalkkeja, jotka ovat johdonmukaisia useilla näkymillä. Lapsireitit renderöidään asetuksen sisältöalueelle.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

Tässä tapauksessa `MainLayout` on asetussuunta, joka käärii lapsinäkymät. Se määrittelee yleisiä käyttöliittymäelementtejä, kuten otsikon ja laatikon. Tämän asetuksen kanssa liittyvät lapsireitit injektoidaan `AppLayout`-komponentin sisältöalueeseen.

## Reittityyppien automaattinen tunnistus {#auto-detection-of-route-types}

Oletusarvoisesti reittityyppi tunnistetaan automaattisesti siitä, onko reitti **näyttö** vai **asetus** luokan nimen perusteella:

- Luokat, jotka päättyvät `Layout`, käsitellään **asetussuunta** -reitteinä.
- Luokat, jotka päättyvät `View`, käsitellään **näyttöreitteinä**.

Vaihtoehtoisesti kehittäjät voivat manuaalisesti määrittää reittityypin asettamalla `Route.Type` `@Route`-annotaatiossa.

```java
// Tunnistettu automaattisesti Asetukseksi
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Tunnistettu automaattisesti Näytöksi
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Dashbordin sisältö")); // "Dashboard Content" translated to "Dashbordin sisältö"
  }
}
```
