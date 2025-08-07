---
sidebar_position: 3
title: Route Outlets
_i18n_hash: 1871c92c77115c99444f1d7a0c20aed9
---
**Outlet** on määritellyt komponentti, joko [reittimuoto](./route-types#layout-routes) tai [reittinäkymä](./route-types#view-routes), jossa lapsireittejä renderoidaan dynaamisesti. Se määrittää, mihin lapsireitin sisältö ilmestyy vanhemman reitin sisällä. Outletit ovat olennaisia modulaaristen, sisäkkäisten käyttöliittymien ja joustavien navigaatiorakenteiden luomisessa.

## Outletin määrittäminen {#defining-an-outlet}

Outletit toteutetaan tyypillisesti käyttämällä säilökomponentteja, jotka voivat pitää ja hallita lapsisisältöä. webforJ:ssä mikä tahansa komponentti, joka toteuttaa `HasComponents`-rajapinnan tai sen komponenttien yhdistelmä, voi toimia outletina. Esimerkiksi [`FlexLayout`](../../components/flex-layout) toteuttaa `HasComponents`-rajapinnan, joten se on voimassa oleva outlet lapsireiteille.

Jos reitille ei ole selvästi määritelty outletia, sovelluksen ensimmäistä `Frame`-komponenttia käytetään oletusoutlettina. Tämä käytös varmistaa, että jokaisella lapsireitillä on paikka renderöityä.

:::tip Kehys Hallinta
Monikehyksisissä sovelluksissa voit määrittää, mikä kehys käytetään outletina lapsireiteille asettamalla `frame`-attribuutin `@Route`-annotaatiossa. `frame`-attribuutti hyväksyy kehysnimen, jota käytetään renderöinnissä.
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
- `DashboardView` renderöidään `MainLayout`:in sisälle käyttäen `AppLayout`:in oletusoutlettia (sisältöalue).

Näin ollen `MainLayout`:in lapsireitit renderöidään automaattisesti `AppLayout`:in sisältöslotissa, ellei muuta outletia tai kehystä ole määritelty.

## Outletin elinkaari {#outlet-lifecycle}

Outletit ovat tiiviisti sidoksissa reittien elinkaareen. Kun aktiivinen reitti muuttuu, outlet päivittää sisältönsä dynaamisesti injektoimalla oikean lapsikomponentin ja poistamalla kaikki komponentit, joita ei enää tarvita. Tämä varmistaa, että vain asiaankuuluvat näkymät renderoidaan kokeiden aikana.

- **Luonti**: Outletit alustetaan ennen kuin lapsikomponentteja luodaan.
- **Sisältöinjektio**: Kun lapsireitti on osunut, sen komponentti injektoidaan outletiin.
- **Päivitys**: Kun liikkuvat reittien välillä, outlet päivittää sisältönsä, injektoimalla uuden lapsikomponentin ja poistamalla kaikki vanhentuneet komponentit.

## Mukautetut outletit {#custom-outlets}

`RouteOutlet`-rajapinta on vastuussa reittikomponenttien elinkaaren hallinnasta, määrittäen, miten komponentit renderöidään ja poistetaan. Mikä tahansa komponentti, joka toteuttaa tämän rajapinnan, voi toimia outletina muille komponenteille.

### Tärkeät metodit `RouteOutlet`: {#key-methods-in-routeoutlet}

- **`showRouteContent(Component component)`**: Vastaa annetun komponentin renderöinnistä outletissa. Tätä kutsutaan, kun reititin osuu reittiin ja lapsikomponentti on näytettävä.
- **`removeRouteContent(Component component)`**: Hallitsee komponentin poistamista outletista, tyypillisesti kutsuttu, kun navigoidaan pois nykyiseltä reitiltä.

Toteuttamalla `RouteOutlet`, kehittäjät voivat hallita, kuinka reitit injektoidaan sovelluksen tiettyihin alueisiin. Esimerkiksi

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

Tässä esimerkissä `MainLayout`-luokka toteuttaa `RouteOutlet`-rajapinnan, jolloin komponentteja voidaan lisätä tai poistaa `AppLayout`:n laatikosta dynaamisesti reittinavigaation perusteella sen sijaan, että käytettäisiin oletus sisältöaluetta, joka on määritelty `AppLayout`:n komponentissa.

## Outlet-komponenttien välimuisti {#caching-outlet-components}

Oletusarvoisesti outletit lisäävät ja poistavat dynaamisesti komponentteja siirryttäessä reiteille ja pois niiltä. Tietyissä tapauksissa—erityisesti monimutkaisilla komponenteilla varustetuissa näkymissä— voi olla parempi vaihtaa komponenttien näkyvyyttä sen sijaan, että poistetaan ne kokonaan DOMista. Tässä `PersistentRouteOutlet` tulee kuvaan, jolloin komponentit pysyvät muistissa ja niitä yksinkertaisesti piilotetaan tai näytetään, sen sijaan että ne tuhotaan ja luodaan uudelleen.

`PersistentRouteOutlet` välimuisti renderoituja komponentteja, pitäen niitä muistissa, kun käyttäjä navigoi pois. Tämä parantaa suorituskykyä välttämällä tarpeettoman komponentin tuhoamisen ja uudelleenluomisen, mikä on erityisen hyödyllistä sovelluksissa, joissa käyttäjät vaihtavat usein näkymien välillä.

### Kuinka `PersistentRouteOutlet` toimii: {#how-persistentrouteoutlet-works}

- **Komponenttien välimuisti**: Se pitää muistissa välimuistin kaikista komponenteista, jotka on renderöity outletissa.
- **Näkyvyyden vaihto**: Sen sijaan, että poistetut komponentit DOMista, se piilottaa ne reittiä kohden navigoidessa.
- **Komponentin palautus**: Kun käyttäjä navigoi takaisin aiemmin välimuistissa olevaan reittiin, komponentti näytetään yksinkertaisesti uudelleen ilman uudelleenluontitarvetta.

Tämä käyttäytyminen on erityisen hyödyllistä monimutkaisille käyttöliittymille, joissa komponenttien jatkuva uusintarenderointi voi heikentää suorituskykyä. Kuitenkin tämän näkyvyyden vaihtamisen toimimiseksi hallittavien komponenttien on toteutettava `HasVisibility`-rajapinta, joka mahdollistaa `PersistentRouteOutlet`:in hallita niiden näkyvyyttä.

:::tip Milloin käyttää `PersistentRouteOutlet`
Käytä `PersistentRouteOutlet` kun komponenttien luominen ja tuhoaminen johtaa usein suorituskykyongelmiin sovelluksessasi. On yleensä suositeltavaa sallia oletuskäyttäytyminen komponenttien luomiseen ja tuhoamiseen reittisiirtymien aikana, koska tämä auttaa välttämään mahdollisia virheitä ja ongelmia, jotka liittyvät johdonmukaisen tilan ylläpitämiseen. Kuitenkin tilanteissa, joissa suorituskyky on kriittistä ja komponentit ovat monimutkaisia tai kalliita luoda uudelleen, `PersistentRouteOutlet` voi tarjota merkittäviä parannuksia välimuistitusten ja näkyvyyden hallinnan avulla.
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

Tässä esimerkissä `MainLayout` käyttää `PersistentRouteOutlet`:ia hallitakseen lapsireittejään. Kun navigoidaan reittien välillä, komponentteja ei poisteta DOMista, vaan ne piilotetaan, varmistamalla, että ne pysyvät saatavilla nopeaa uudelleenrenderointia varten, kun käyttäjä navigoi takaisin. Tämä lähestymistapa parantaa suorituskykyä huomattavasti, erityisesti näkymissä, joilla on monimutkaista sisältöä tai raskasta resurssin käyttöä.
