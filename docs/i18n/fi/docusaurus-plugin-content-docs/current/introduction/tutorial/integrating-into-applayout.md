---
title: Integrating the AppLayout
sidebar_position: 7
draft: true
description: Step 6 - Use the AppLayout component.
_i18n_hash: fece87dce53e7e41102e122c740f6ea8
---
Tässä vaiheessa integroi käytettävissä olevat ominaisuudet, jotka on toteutettu aiemmissa vaiheissa, kuten reititys ja näkymät, yhtenäiseen sovelluksen ulkoasuun. Tämä rakenne tarjoaa yhtenäisen navigointijärjestelmän ja dynaamiset sisältöalueet.

## Sovelluksen suorittaminen {#running-the-app}

Kun kehität sovellustasi, voit käyttää [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry ylimpään hakemistoon, joka sisältää `pom.xml`-tiedoston, tämä on `6-integrating-an-app-layout`, jos seuraat versiota GitHubissa.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Sovelluksen ulkoasun tarkoitus {#purpose-of-the-app-layout}

`AppLayout` toimii perustana sovelluksesi yleisen rakenteen ja virtausten hallintaan. Se tarjoaa:
- **Kansainvälinen navigointi**: Yhtenäinen tapa siirtyä keskeisten osioiden välillä.
- **Dynaaminen sisällön renderointi**: Keskitetyllä ulkoasulla, joka näyttää reititetyt näkymät.

## Käyttäen `AppNav` {#using-appnav}

`AppNav`-komponenttia käytetään navigointivalikon luomiseen sovelluksen käyttöliittymässä. Tämä valikko tarjoaa linkkejä eri näkymiin sovelluksessasi, kuten `DemoView`:

```java title="MainLayout.java"
private void setDrawer() {
  AppLayout layout = getBoundComponent();

  AppNav appNav = new AppNav();
  appNav.addItem(new AppNavItem("Koontinäyttö", DemoView.class, FeatherIcon.MESSAGE_CIRCLE.create()));

  layout.addToDrawer(appNav);
}
```

Tässä esimerkissä:
- Navigointivalikko lisätään sovelluksen laatikkoon.
- Jokainen valikkokohta on `AppNavItem`, joka määrittää:
  - Nimen, esimerkiksi "Koontinäyttö."
  - Kohdevälin, esimerkiksi `DemoView`.
  - Valinnaisen kuvakkeen, esimerkiksi sarakkeet kuvake.

## Ulkoasu reitit ja ulostulot {#layout-routes-and-outlets}

Ulkoasu käyttää reittejä ja ulostuloja dynaamisesti renderöidäkseen sisältöä rakenteellisessa ulkoasussa. webforJ:ssä:
- **Reitit** määrittävät, miten näkymät kartoituvat tiettyihin polkuihin.
- **Ulostulot** toimivat paikkana ulkoasuissa, joissa reititetyt näkymät näytetään.

### Esimerkki: Ulkoasureitin määrittäminen {#example-setting-up-a-layout-route}

`MainLayout`-luokassa `@Route`-annotaatio määrittää sen perusulkoasuksi, ja `DemoView` renderöidään tämän ulkoasun kautta:

```java title="MainLayout.java"
@Route("/")
public class MainLayout extends Composite<AppLayout> {
    public MainLayout() {
        setHeader();
        setDrawer();
    }
}
```

`@Route`-annotaatio `DemoView`-luokassa määrittää, että se käyttää `MainLayout`-ulostuloa:

```java title="DemoView.java"
@Route(value = "/demo", outlet = MainLayout.class)
@FrameTitle("Demo")
public class DemoView extends Composite<Div> {
    // DemoView logiikka
}
```

## Dynaamisen sisällön lisääminen `RouteOutlet`-komponentilla {#adding-dynamic-content-with-routeoutlet}

`RouteOutlet` näyttää dynaamisesti näkymiä aktiivisen reitin perusteella. Ulkoasussa, näkymiä kuten `DemoView` renderöidään `RouteOutlet`-komponentin kautta. Vaikka `RouteOutlet` käsitellään epäsuorasti reittien annotaation ulostulospesifikaatiossa.
