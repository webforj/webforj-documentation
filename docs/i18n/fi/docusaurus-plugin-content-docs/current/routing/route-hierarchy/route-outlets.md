---
sidebar_position: 3
title: Route Outlets
_i18n_hash: bab7ef02dabbb653741f7c8176913213
---
**Outlet** on nimetty komponentti, joko [reittiasettelu](./route-types#layout-routes) tai [reittinäkymä](./route-types#view-routes), jossa lapsireittejä renderöidään dynaamisesti. Se määrittelee, minne lapsireitin sisältö ilmestyy vanhemman reitin sisällä. Outletit ovat perusasioita moduulisten, sisäkkäisten käyttöliittymien ja joustavien navigointirakenteiden luomisessa.

## Outletin määrittäminen {#defining-an-outlet}

Outletit toteutetaan tyypillisesti säilytys-komponenteilla, jotka voivat pitää ja hallita lapsisisältöä. webforJ:ssa mikä tahansa komponentti, joka toteuttaa `HasComponents`-rajapinnan tai sen yhdistelmän, voi toimia outletina. Esimerkiksi [`FlexLayout`](../../components/flex-layout) toteuttaa `HasComponents`-rajapinnan, joten se on voimassa oleva outlet lapsireiteille.

Jos reitille ei ole määritelty erikseen outletia, sovelluksen ensimmäistä `Frame`:tä käytetään oletusoutletina. Tämä käyttäytyminen varmistaa, että jokaisella lapsireitillä on paikka renderöityä.

:::tip Kehysjohtaminen
Sovelluksissa, joissa on useita kehyksiä, voit määrittää, mikä kehys käytetään outletina lapsireiteille asettamalla `frame`-attribuutin `@Route`-annotaatiossa. `frame`-attribuutti hyväksyy sen kehysnimen, jota käytetään renderöimiseen.
:::

### Esimerkki: {#example}

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  public MainLayout() {
    setHeader();
    setDrawer();
  }
}

@Route(outlet = MainLayout.class)
public class DashboardView extends Composite<Div> {
  public DashboardView() {
    getBoundComponent().add(new H1("Dashboard Content"));
  }
}
```

Tässä esimerkissä:

- `MainLayout` toimii asettelusäilönä, mutta koska erityistä outletia ei ole määritelty, sovelluksen oletus `Frame` käytetään.
- `DashboardView` renderöidään `MainLayout`-sisällä käyttäen `AppLayout`:n oletusoutletia (sisältöaluetta).

Näin ollen `MainLayout`:n lapsireitit renderöidään automaattisesti `AppLayout`:n sisältöpaikkaan, ellet määritä toista outletia tai kehystä.

## Outletin elinkaari {#outlet-lifecycle}

Outletit ovat tiiviisti sidoksissa reittien elinkaareen. Kun aktiivinen reitti muuttuu, outlet päivittää sisältönsä dynaamisesti injektoimalla sopivan lapsikomponentin ja poistamalla kaikki komponentit, joita ei enää tarvita. Tämä varmistaa, että vain asiaankuuluvat näkymät renderöidään millä tahansa hetkellä.

- **Luominen**: Outletit alustetaan ennen lapsikomponenttien luomista.
- **Sisällön injektointi**: Kun lapsireitti osuu, sen komponentti injektoidaan outletiin.
- **Päivitys**: Reittejä vaihdettaessa outlet päivittää sisältönsä, injektoimalla uuden lapsikomponentin ja poistamalla vanhentuneet komponentit.

## Mukautetut outletit {#custom-outlets}

`RouteOutlet`-rajapinta on vastuussa reittikomponenttien elinkaaren hallinnasta, määrittäen, miten komponentteja renderöidään ja poistetaan. Mikä tahansa komponentti, joka toteuttaa tämän rajapinnan, voi toimia outletina muille komponenteille.

### Tärkeät metodit `RouteOutlet`:ssa {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Vastaa annetun komponentin renderöimisestä outletissa. Tätä kutsutaan, kun reitit ovat yhteensopivia ja lapsikomponentti on näytettävä.
- **`removeRouteContent(Component component)`**: Huolehtii komponentin poistamisesta outletista, tyypillisesti kutsutaan, kun siirrytään pois nykyiseltä reitiltä.

Toteuttamalla `RouteOutlet`:in kehittäjät voivat hallita, miten reittejä injektoidaan sovelluksen tiettyihin alueisiin. Esimerkiksi:

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {

  @Override
  public void showRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    AppLayout layout = getBoundComponent();
    layout.remove(component);
  }
}
```

Tässä esimerkissä `MainLayout`-luokka toteuttaa `RouteOutlet`-rajapinnan, mikä mahdollistaa komponenttien lisäämisen tai poistamisen AppLayoutin laatikosta dynaamisesti reittinavigoinnin perusteella sen sijaan, että käytettäisiin AppLayout-komponentissa määritettyä oletussisältöaluetta.

## Outlet-komponenttien välimuisti {#caching-outlet-components}

Oletuksena outletit lisäävät ja poistavat dynaamisesti komponentteja, kun siirrytään reiteille ja sieltä pois. Kuitenkin tietyissä tapauksissa—erityisesti monimutkaisilla komponenteilla varustetuissa näkymissä—saattaa olla parempi kytkeä komponenttien näkyvyys päälle ja pois sen sijaan, että ne poistetaan kokonaan DOM:sta. Tässä vaiheessa `PersistentRouteOutlet` tulee peliin, jolloin komponentit pysyvät muistissa ja yksinkertaisesti piilotetaan tai näytetään, sen sijaan että ne tuhottaisiin ja luotaisiin uudestaan.

`PersistentRouteOutlet` välimuistittaa renderöidyt komponentit, pitäen ne muistissa, kun käyttäjä siirtyy pois. Tämä parantaa suorituskykyä välttämällä tarpeetonta komponenttien tuhoamista ja uudelleenluontia, mikä on erityisen hyödyllistä sovelluksille, joissa käyttäjät siirtyvät usein näkymien välillä.

### Miten `PersistentRouteOutlet` toimii: {#how-persistentrouteoutlet-works}

- **Komponenttien välimuisti**: Se ylläpitää muistissa välimuistia kaikista komponenteista, jotka on renderöity outletissa.
- **Näkyvyyden kytkentä**: Komponentteja ei poisteta DOM:sta, vaan ne piilotetaan, kun siirrytään pois reitiltä.
- **Komponentin palauttaminen**: Kun käyttäjä siirtyy takaisin aiemmin välimuistitetulle reitille, komponentti näytetään taas ilman, että sitä tarvitsee luoda uudestaan.

Tämä käyttäytyminen on erityisen hyödyllinen monimutkaisille käyttöliittymille, joissa komponenttien jatkuva uudelleen renderöinti voi heikentää suorituskykyä. Kuitenkin, jotta tämän näkyvyyden kytkeminen toimisi, hallittavien komponenttien on toteutettava `HasVisibility`-rajapinta, jonka avulla `PersistentRouteOutlet` voi hallita niiden näkyvyyttä.

:::tip Milloin käyttää `PersistentRouteOutlet`
Käytä `PersistentRouteOutlet`:ia, kun komponenttien luominen ja tuhoaminen johtaa usein suorituskykyongelmiin sovelluksessasi. Yleisesti ottaen on suositeltavaa sallia oletuskäyttäytyminen komponenttien luomisessa ja tuhoamisessa reittisiirtymien aikana, koska tämä auttaa välttämään mahdollisia virheitä ja ongelmia, jotka liittyvät johdonmukaisen tilan ylläpitoon. Kuitenkin, tilanteissa, joissa suorituskyky on kriittinen ja komponentit ovat monimutkaisia tai kalliita luoda uudelleen, `PersistentRouteOutlet` voi tarjota merkittäviä parannuksia välimuistittamalla komponentteja ja hallitsemalla niiden näkyvyyttä.
:::

### Esimerkki `PersistentRouteOutlet`-toteutuksesta: {#example-of-persistentrouteoutlet-implementation}

```java
@Route
public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  PersistentRouteOutlet outlet = new PersistentRouteOutlet(this);

  public MainLayout() {
    setHeader();
    setDrawer();
  }

  @Override
  public void removeRouteContent(Component component) {
    outlet.removeRouteContent(component);
  }

  @Override
  public void showRouteContent(Component component) {
    outlet.showRouteContent(component);
  }
}
```

Tässä esimerkissä `MainLayout` käyttää `PersistentRouteOutlet`-ratkaisua hallita lapsireittejään. Reittejä vaihdettaessa komponentteja ei poisteta DOM:sta, vaan ne piilotetaan, varmistaen, että ne pysyvät käytettävissä nopeaa uudelleen renderöintiä varten, kun käyttäjä palaa takaisin. Tämä lähestymistapa parantaa merkittävästi suorituskykyä, erityisesti monimutkaisille sisällöille tai raskasta resurssin käyttöä vaativille näkymille.
