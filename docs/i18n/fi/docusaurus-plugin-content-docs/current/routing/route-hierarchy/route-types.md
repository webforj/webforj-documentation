---
sidebar_position: 1
title: Route Types
_i18n_hash: d297703f4a165ebcfbb540c3256f825e
---
Reitit jaotellaan kahteen päätyyppiin, **Näkymäreitteihin** ja **Asettelureitteihin**. Reittityypin valinta määrää, miten komponentit kartoitetaan URL-osoitteisiin ja miten ne vuorovaikuttavat sovelluksesi muiden osien kanssa.

## Näkymäreitit {#view-routes}

Näkymäreitit kartoittavat suoraan URL-segmenttiin ja edustavat tiettyjä sivuja sovelluksessasi. Nämä reitit näkyvät selaimen URL-osoitteessa ja niitä käytetään tyypillisesti erillisille näkymille tai sivuille.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  public HomeView() {
    Div content = getBoundComponent();
    content.add(new H1("Etusivu"));
  }
}
```

- **URL**: `/home`
- **Renderoitu komponentti**: `HomeView`

Esimerkissä, siirtyminen osoitteeseen `/home` renderoi `HomeView`-komponentin.

## Asettelureitit {#layout-routes}

Asettelureitit kietovat lapsinäkymiä ilman, että ne vaikuttavat URL-osoitteeseen. Asettelut tarjoavat yhteisiä käyttöliittymäelementtejä, kuten yläosia tai sivupalkkeja, jotka ovat johdonmukaisia useissa näkymissä. Lapsireitit renderoidaan asettelun sisältöalueella.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

Tässä tapauksessa `MainLayout` on asettelureitti, joka kietoo lapsinäkymiä ympärilleen. Se määrittelee yleisiä UI-elementtejä, kuten yläosan ja piirongin. Tälle asettelulle liitetyt lapsireitit injektoidaan `AppLayout`-komponentin sisältöalueeseen.

## Reittityyppien auton tunnistaminen {#auto-detection-of-route-types}

Oletusarvoisesti reittityyppi tunnistetaan automaattisesti sen perusteella, onko reitti **näkymä** tai **asettelu**, perustuen luokan nimeen:

- Luokat, jotka päättyvät `Layout`:iin käsitellään **asettelureitteinä**.
- Luokat, jotka päättyvät `View`:iin käsitellään **näkymäreitteinä**.

Vaihtoehtoisesti kehittäjät voivat määrittää reittityypin manuaalisesti asettamalla `Route.Type` `@Route`-merkintään.

```java
// Tunnistetaan automaattisesti Asetteluksi
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Tunnistetaan automaattisesti Näkymäksi
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    Div content = getBoundComponent();
    content.add(new H1("Koontinäytön sisältö"));
  }
}
```
