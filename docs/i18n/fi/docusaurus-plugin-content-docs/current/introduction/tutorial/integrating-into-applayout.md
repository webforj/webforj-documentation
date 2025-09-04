---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: c0ed4864dc99a4665aef3f4ff808bc9d
---
Tässä vaiheessa integroi aiemmissa vaiheissa toteutetut ominaisuudet, kuten reititys ja näkymät, yhtenäiseen sovelluksen asetteluun. Tämä rakenne tarjoaa yhtenäisen navigointijärjestelmän ja dynaamiset sisältöalueet.

## Sovelluksen asettelun tarkoitus {#purpose-of-the-app-layout}

`AppLayout` toimii perustana sovelluksesi kokonaisrakenteen ja -kulun hallintaan. Se tarjoaa:
- **Globaali navigointi**: Konsistentti tapa siirtyä keskeisten osioiden välillä.
- **Dynaaminen sisällön renderointi**: Keskitetty asettelu reititetyille näkymille.

## Käyttäen `AppNav` {#using-appnav}

`AppNav` komponenttia käytetään navigaatiovalikon luomiseen sovelluksen käyttöliittymässä. Tämä valikko tarjoaa linkkejä eri näkymiin sovelluksessasi, kuten `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Koontinäyttö", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

Esimerkissä:
- Navigaatiovalikko lisätään sovelluksen laatikkoon.
- Jokainen valikkokohde on `AppNavItem`, joka määrittelee:
  - Tunnisteen, esimerkiksi "Koontinäyttö."
  - Kohdenäkemyksen, esimerkiksi `DemoView`.
  - Valinnaisen ikonivaihtoehdon, esimerkiksi sarakkeiden ikoni.

## Asettelureitit ja ulostulot {#layout-routes-and-outlets}

Asettelut käyttävät reittejä ja ulostuloja dynaamisesti sisältöjen renderoimiseksi rakennetussa asettelussa. webforJ:ssa:
- **Reitit** määrittelevät, miten näkymät vastaavat tiettyjä polkuja.
- **Ulostulot** toimivat paikkamerkkeinä asetteluissa, joissa reititetyt näkymät näytetään.

### Esimerkki: Asettelureitin määrittäminen {#example-setting-up-a-layout-route}

`MainLayout`-luokassa `@Route`-annotaatio määrittelee sen perustasoksi, ja `DemoView` renderoidaan tämän asettelun ulostulon kautta:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

`@Route`-annotaatio `DemoView`lle määrittelee, että se käyttää `MainLayout`-asettelua ulostulona:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView logiikka
}
```

## Dynaamisen sisällön lisääminen `RouteOutlet`-komponentin avulla {#adding-dynamic-content-with-routeoutlet}

`RouteOutlet` näyttää dynaamisesti näkymiä aktiivisen reitin mukaan. Asettelussa näkymät kuten `DemoView` renderoidaan `RouteOutlet`in kautta. `RouteOutlet` käsitellään implisiittisesti reittiannotaatioiden ulostulomäärittelyssä.
