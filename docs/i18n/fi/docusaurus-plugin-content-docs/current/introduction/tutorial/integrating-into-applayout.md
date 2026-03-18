---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: 14b409262af6d7a8a25a67278f687250
---
Tässä vaiheessa yhdistät aiemmissa vaiheissa toteutetut ominaisuudet, kuten reitityksen ja näkymät, yhtenäiseksi sovellusrakenneksi. Tämä rakenne tarjoaa yhdenmukaisen navigointijärjestelmän ja dynaamiset sisältöalueet.

## Sovelluksen suorittaminen {#running-the-app}

Sovellustasi kehitettäessä voit käyttää [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry ylin taso hakemistoon, joka sisältää `pom.xml` -tiedoston, tämä on `6-integrating-an-app-layout`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Sovellusrakenteen tarkoitus {#purpose-of-the-app-layout}

`AppLayout` toimii perustana sovelluksesi kokonaisrakenteen ja -kulun hallinnalle. Se tarjoaa:
- **Globaali navigointi**: Johdonmukainen tapa siirtyä tärkeiden osioiden välillä.
- **Dynaaminen sisällön renderointi**: Keskitetty rakenne reititettyjen näkymien näyttämiseen.

## `AppNav` käyttämisestä {#using-appnav}

`AppNav`-komponenttia käytetään navigointivalikon luomiseen sovelluksen käyttöliittymässä. Tämä valikko tarjoaa linkkejä eri näkymiin sovelluksessasi, kuten `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Dashboard", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

Tässä esimerkissä:
- Navigointivalikko lisätään sovelluksen laatikkoon.
- Jokainen valikoelementti on `AppNavItem`, joka määrittelee:
  - Tunnisteen, esimerkiksi "Dashboard."
  - Kohdenäkymän, esimerkiksi `DemoView`.
  - Valinnaisen kuvakkeen, esimerkiksi sarakkeet-kuvakkeen.

## Rakennereitit ja ulostulot {#layout-routes-and-outlets}

Rakenne käyttää reittejä ja ulostuloja dynaamisesti renderöimään sisältöä jäsennellyssä rakenteessa. webforJ:ssä:
- **Reitit** määrittävät, miten näkymät kartoituvat tiettyihin polkuihin.
- **Ulostulot** toimivat paikkamerkkeinä rakenteissa, joihin reititetyt näkymät näytetään.

### Esimerkki: Rakennereitin määrittäminen {#example-setting-up-a-layout-route}

`MainLayout`-luokassa `@Route`-annotaatio määrittelee sen perusteena oleksi rakenteeksi, ja `DemoView` renderöidään tämän rakenteen ulostulon kautta:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}
```

`@Route`-annotaatio `DemoView`-luokassa määrittää, että se käyttää `MainLayout`-rakennetta ulostulonaan:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
  // DemoView logiikka
}
```

## Dynaamisen sisällön lisääminen `RouteOutlet`-komponentilla {#adding-dynamic-content-with-routeoutlet}

`RouteOutlet` näyttää dynaamisesti näkymiä aktiivisen reitin perusteella. Rakenne renderöi näkymiä, kuten `DemoView`, `RouteOutlet`-komponentin kautta. `RouteOutlet` käsitellään implisiittisesti reitin annotaatioissa olevien ulostulospesifikaatioiden kautta.
