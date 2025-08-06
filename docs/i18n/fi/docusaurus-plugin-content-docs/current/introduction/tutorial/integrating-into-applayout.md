---
title: Integrating the AppLayout
sidebar_position: 6
draft: true
_i18n_hash: fd2f3844bbcb102adf05ae01b07ff8d8
---
Tässä vaiheessa integroi aiemmissa vaiheissa toteutetut ominaisuudet, kuten reititys ja näkymät, yhtenäiseksi sovellusrakenteeksi. Tämä rakenne tarjoaa yhtenäisen navigointijärjestelmän ja dynaamiset sisältöalueet.

## Sovellusrakenteen tarkoitus {#purpose-of-the-app-layout}

`AppLayout` toimii perustana sovelluksesi kokonaisrakenteen ja -virran hallintaan. Se tarjoaa:
- **Globaali navigointi**: Johdonmukainen tapa siirtyä tärkeiden osioiden välillä.
- **Dynaaminen sisällön renderöinti**: Keskitetty asettelu reititettyjen näkymien näyttämiseen.

## `AppNav` käyttö {#using-appnav}

`AppNav` komponenttia käytetään navigointivalikon luomiseen sovelluksen käyttöliittymässä. Tämä valikko tarjoaa linkkejä erilaisiin näkymiin sovelluksessasi, kuten `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Koontinäyttö", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

Tästä esimerkistä:
- Navigointivalikko lisätään sovelluksen laatikkoon.
- Jokainen valikkokohde on `AppNavItem`, joka määrittää:
  - Nimen, esimerkiksi "Koontinäyttö."
  - Kohdenäkymän esimerkiksi `DemoView`.
  - Valinnaisen kuvakkeen, esimerkiksi sarakkeiden kuvake.

## Asettelureitit ja -paikat {#layout-routes-and-outlets}

Asettelu käyttää reittejä ja paikkoja dynaamisen sisällön renderöimiseen rakenteellisessa asettelussa. webforJ:ssa:
- **Reitit** määrittävät, miten näkymät vastaavat tiettyjä polkuja.
- **Paikat** toimivat paikkamerkkeinä asetteluissa, joissa reititetyt näkymät näytetään.

### Esimerkki: Asettelu reitin määrittäminen {#example-setting-up-a-layout-route}

`MainLayout` luokassa `@Route` -annotaatio määrittää sen perustasanteeksi, ja `DemoView` renderöidään tämän asettelun kautta paikassa:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

`@Route` -annotaatio `DemoView` -luokassa määrittää, että se käyttää `MainLayout`-asetusta:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView logiikka
}
```

## Dynaamisen sisällön lisääminen `RouteOutlet`-komponentilla {#adding-dynamic-content-with-routeoutlet}

`RouteOutlet` näyttää dynaamisesti näkymiä aktiivisen reitin perusteella. Asettelussa näkymät, kuten `DemoView`, renderöidään `RouteOutlet`-komponentin kautta. `RouteOutlet` käsitellään implisiittisesti reittiannotaatioissa määritellyn paikan spesifikaation kautta.
