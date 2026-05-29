---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 8a64cd917fe9f1de3f37ee01254e80e7
---
An **outlet** on määritelty komponentti, joko [reittinäkymä](./route-types#layout-routes) tai [reittinäkymä](./route-types#view-routes), jossa lapsireitit renderoidaan dynaamisesti. Se määrittää, mihin lapsireitin sisältö ilmestyy vanhempi-reitillä. Outletit ovat keskeisiä modulaaristen, sisäkkäisten käyttöliittymien ja joustavien navigointirakenteiden luomisessa.

## Outletin määrittäminen {#defining-an-outlet}

Outletit toteutetaan tyypillisesti säiliökomponenteilla, jotka voivat pitää ja hallita lapsisisältöä. webforJ:ssä mikä tahansa komponentti, joka toteuttaa `HasComponents`-rajapinnan, tai tällaisista komponenteista koostuva yhdistelmä, voi toimia outletina. Esimerkiksi [`FlexLayout`](../../components/flex-layout) toteuttaa `HasComponents`-rajapinnan, joten se on kelvollinen outlet lapsireiteille.

Jos reitille ei ole nimenomaan määritetty outletia, sovelluksen ensimmäistä `Framea` käytetään oletusoutletina. Tämä käytäntö varmistaa, että jokaisella lapsireitillä on paikka renderoida.

:::tip Frame-hallinta
Moniframesovelluksissa voit määrittää, mikä frame käytetään outletina lapsireiteille asettamalla `frame`-attribuutin `@Route`-annotaatiossa. `frame`-attribuutti hyväksyy renderöitäväksi nimen framea.
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
  private final Div self = getBoundComponent();

  public DashboardView() {
    self.add(new H1("Dashboard Content"));
  }
}
```

Tässä esimerkissä:

- `MainLayout` toimii asettelusäiliönä, mutta koska erityistä outletia ei ole määritetty, sovelluksen oletus `Frame` on käytössä.
- `DashboardView` renderoidaan `MainLayout`-elementissä käyttämällä `AppLayout`:n oletusoutletia (sisältöalue).

Näin ollen `MainLayout`-lapsireitit renderoidaan automaattisesti `AppLayout`:n sisältöpaikkaan, ellei erilaista outletia tai framea ole määritetty.

## Outletin elinkaari {#outlet-lifecycle}

Outletit ovat tiiviisti sidottuja reittien elinkaareen. Kun aktiivinen reitti muuttuu, outlet päivittää sisältönsä dynaamisesti injektoimalla sopivan lapsikomponentin ja poistamalla kaikki komponentit, joita ei enää tarvita. Tämä varmistaa, että vain asianmukaiset näkymät renderoidaan kussakin hetkessä.

- **Luonti**: Outletit alustetaan ennen kuin lapsikomponentit luodaan.
- **Sisällön injektointi**: Kun lapsireitti on kohdattu, sen komponentti injektoidaan outletiin.
- **Päivitys**: Reittien välillä navigoitaessa outlet päivittää sisältönsä injektoimalla uuden lapsikomponentin ja poistamalla kaikki vanhentuneet komponentit.

## Mukautetut outletit {#custom-outlets}

`RouteOutlet`-rajapinta on vastuussa reittikomponenttien elinkaaren hallinnasta, määrittäen, miten komponentteja renderoidaan ja poistetaan. Mikä tahansa komponentti, joka toteuttaa tämän rajapinnan, voi toimia outletina muille komponenteille.

### Tärkeimmät metodit `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Vastaa toimitetun komponentin renderoimisesta outletissa. Tätä kutsutaan, kun reititin kohtaa reitin, ja lapsikomponentin on näytettävä.
- **`removeRouteContent(Component component)`**: Käsittelee komponentin poistamista outletista, tyypillisesti kutsutaan, kun navigoidaan pois nykyiseltä reitiltä.

Toteuttamalla `RouteOutlet` kehittäjät voivat hallita, kuinka reitit injektoidaan sovelluksen tiettyihin alueisiin. esimerkiksi

```java
import com.webforj.router.RouteOutlet;

public class MainLayout extends Composite<AppLayout> implements RouteOutlet {
  private final AppLayout self = getBoundComponent();

  @Override
  public void showRouteContent(Component component) {
    self.addToDrawer(component);
  }

  @Override
  public void removeRouteContent(Component component) {
    self.remove(component);
  }
}
```

Tässä esimerkissä `MainLayout`-luokka toteuttaa `RouteOutlet`-rajapinnan, jolloin komponentteja voidaan lisätä tai poistaa AppLayoutin piirustuksesta dynaamisesti reitin navigoinnin perusteella, sen sijaan että käytettäisiin oletussisältöaluetta, joka on määritelty `AppLayout`-komponentissa.

## Outlet-komponenttien välimuisti {#caching-outlet-components}

Oletusarvoisesti outletit lisäävät ja poistavat komponentteja dynaamisesti navigoidessaan reitiltä pois ja takaisin. Tietyissä tapauksissa—erityisesti monimutkaisilla näkymillä—voi olla suositeltavaa vaihtaa komponenttien näkyvyyttä sen sijaan, että ne poistetaan kokonaan DOMista. Tässä kohtaa `PersistentRouteOutlet` tulee kuvaan, jolloin komponentit voivat pysyä muistis käytössä ja vain piiloutua tai ilmestyä, sen sijaan että ne tuhotaan ja luodaan uudelleen.

`PersistentRouteOutlet` välimuisti renderoituja komponentteja, pitäen niitä muistissa, kun käyttäjä navigoi pois. Tämä parantaa suorituskykyä välttämällä tarpeettomia komponenttien tuhoamista ja uudelleen luomista, mikä on erityisen hyödyllistä sovelluksille, joissa käyttäjät vaihtavat usein näkymien välillä.

### Miten `PersistentRouteOutlet` toimii: {#how-persistentrouteoutlet-works}

- **Komponentin välimuisti**: Se pitää muistissa välimuistin kaikista komponentista, jotka on renderoitu outletissa.
- **Näkyvyyden vaihto**: Sen sijaan, että komponentteja poistettaisiin DOMista, ne piilotetaan, kun reitiltä navigoidaan pois.
- **Komponentin palauttaminen**: Kun käyttäjä navigoi takaisin aiemmin välimuistissa olleeseen reittiin, komponentti vain näytetään uudelleen ilman uudelleen luomistarvetta.

Tämä käyttäytyminen on erityisen hyödyllistä monimutkaisissa käyttöliittymissä, joissa jatkuva uusintarenderointi voi heikentää suorituskykyä. Kuitenkin, jotta tämä näkyvyyden vaihto toimii, hallittavien komponenttien on toteutettava `HasVisibility`-rajapinta, jonka avulla `PersistentRouteOutlet` voi hallita niiden näkyvyyttä.

:::tip Milloin käyttää `PersistentRouteOutlet`
Käytä `PersistentRouteOutlet`-komponenttia, kun komponenttien usein luominen ja tuhoaminen johtaa suorituskykyongelmiin sovelluksessasi. On yleensä suositeltavaa sallia oletuskäyttäytymisen luoda ja tuhota komponentteja reitin siirtymien aikana, sillä tämä auttaa välttämään mahdollisia virheitä ja ongelmia, jotka liittyvät tilan ylläpitoon. Kuitenkin, skenaariot, joissa suorituskyky on kriittistä ja komponentit ovat monimutkaisia tai kalliita luoda uudelleen, `PersistentRouteOutlet` voi tarjota merkittäviä parannuksia välimuistin avulla ja hallita komponenttien näkyvyyttä.
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

Tässä esimerkissä `MainLayout` käyttää `PersistentRouteOutlet`-komponenttia hallitakseen lapsireittejään. Kun navigoidaan reittien välillä, komponentteja ei poisteta DOMista, vaan piilotetaan, varmistaen, että ne ovat nopeasti saatavilla, kun käyttäjä navigoi takaisin. Tämä lähestymistapa parantaa suorituskykyä merkittävästi, erityisesti näkymille, joissa on monimutkaista sisältöä tai voimakasta resurssien käyttöä.
