---
sidebar_position: 1
title: Route Types
_i18n_hash: 75cb67715544b94ca99fc81c736ebcc7
---
Reitit jaotellaan kahteen päätyyppiin, **Näyttöreitit** ja **Asettelureitit**. Reittityypin valinta määrää, miten komponentit kytkeytyvät URL-osoitteisiin ja miten ne vuorovaikuttavat sovelluksen muiden osien kanssa.

## Näyttöreitit {#view-routes}

Näyttöreitit kartoittavat suoraan URL-segmenttiin ja edustavat tiettyjä sivuja sovelluksessasi. Nämä reitit näkyvät selaimen URL-osoitteessa ja niitä käytetään tyypillisesti erillisille näkymille tai sivuille.

```java
@Route(value = "home")
public class HomeView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public HomeView() {
    self.add(new H1("Etusivu"));
  }
}
```

- **URL**: `/home`
- **Renderöity komponentti**: `HomeView`

Tässä esimerkissä navigointi osoitteeseen `/home` renderöi `HomeView`-komponentin.

## Asettelureitit {#layout-routes}

Asettelureitit kääriivät lapsinäyttöjä ilman, että ne vaikuttavat URL-osoitteeseen. Asettelu tarjoaa jaettuja käyttöliittymän elementtejä, kuten otsikoita tai sivupalkkeja, jotka ovat johdonmukaisia useilla näkymillä. Lapsireitit renderöidään asetelun sisältöalueelle.

```java
@Route(type = Route.Type.LAYOUT)
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

Tässä tapauksessa `MainLayout` on asetelureitti, joka kääriytyy lapsinäyttöjen ympärille. Se määrittelee yhteiset käyttöliittymäelementit, kuten otsikon ja laatikon. Tämän asetelman kanssa liitetyt lapsireitit injektoidaan `AppLayout`-komponentin sisältöalueelle.

## Reittityyppien automaattinen tunnistus {#auto-detection-of-route-types}

Oletuksena reittityyppi tunnistetaan automaattisesti, olipa reitti **näyttö** tai **asettelu** luokan nimen perusteella:

- Luokat, jotka päättyvät `Layout`, käsitellään **asettelureitteinä**.
- Luokat, jotka päättyvät `View`, käsitellään **näyttöreitteinä**.

Vaihtoehtoisesti kehittäjät voivat manuaalisesti määrittää reittityypin asettamalla `Route.Type` `@Route`-annotaatiossa.

```java
// Automattisesti tunnistettu Asetteluksi
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

```java
// Automattisesti tunnistettu Näyttöksi
@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Koontinäytön sisältö"));
  }
}
```
