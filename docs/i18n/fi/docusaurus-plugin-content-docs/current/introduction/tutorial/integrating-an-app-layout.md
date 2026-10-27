---
title: AppLayoutin integrointi
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: 3a2148bdfb680284a597a17c263609da
---
Tässä vaiheessa yhdistät kaikki sovelluksesi osat yhtenäiseksi sovellussarakkeeksi. Tämän vaiheen lopussa sovelluksesi rakenne muistuttaa läheisesti [SideMenu-mallia](/docs/building-ui/archetypes/sidemenu), ja ymmärrät paremmin, miten seuraavat komponentit ja käsitteet toimivat:

- [`FlexLayout`](/docs/components/flex-layout)
- [Reittilähtö](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Sovelluksen käynnistäminen {#running-the-app}

Sovellustasi kehittäessäsi voit käyttää [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) vertailuna. Näet sovelluksen toiminnassa:

1. Siirry ylin taso hakemistoon, joka sisältää `pom.xml`-tiedoston. Tämä on `6-integrating-an-app-layout`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa ajaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen käynnistäminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Uuden käytettävän komponentin luominen {#creating-a-reusable-component}

Aiemmassa vaiheessa, [Reititys ja Yhdistelmät](/docs/introduction/tutorial/routing-and-composites), loit kaksi yhdistelmäkomponenttia, jotka sisälsivät asiakastaulukon ja asiakaslomakkeen sisällön. Tässä vaiheessa luot pienemmän, uudelleenkäytettävän yhdistelmäkomponentin, joka näyttää sovelluksen nimen sivupalkissa sekä tietoa-sivulla. Jos päätät muuttaa sovelluksen nimeä tulevaisuudessa, sinun tarvitsee vain päivittää se tähän komponenttiin.

Hakemistossa `src/main/java/com/webforj/tutorial/components` luo luokka nimeltä `AppTitle`. `AppTitle`-komponentti tulee olemaan `FlexLayout`, joka on konttikomponentti, jota käytetään tämän vaiheen aikana näyttämään, kuinka tehdä monimutkaisempia asetteluja. Tällä `FlexLayout`-komponentilla järjestät kohteiden suunnan ja niiden väliset välihaut. Tämä tehdään käyttämällä `setDirection()` ja `setSpacing()` menetelmiä.

```java title='AppTitle.java'
// Tee sidottu komponentti FlexLayoutiksi
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Järjestä kohteet pystysuuntaan
    self.setDirection(FlexDirection.COLUMN);

    // Aseta välihauta kohteiden välillä
    self.setSpacing("0px");
  }
}
```

Käytä sitten standardeja HTML-elementtejä luodaksesi otsikon ja alaotsikon. Asettaessasi otsikko-elementin alaosan marginaalin `0px`:ksi, tulet lähemmäksi toisiaan, ja voit muotoilla alaotsikon käyttämällä [DWC CSS -muuttujia](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakas Hallinta");
  private Paragraph subTitle = new Paragraph("Yksinkertainen rekisteröintijärjestelmä");

  public AppTitle() {
    title.setStyle("margin-bottom", "0px");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title, subTitle);
  }
}
```

### Valinnainen renderointi {#optional-rendering}

Vaikka `AppTitle` on yksinkertainen, boolean-argumentin lisääminen konstruktorimenetelmään sallii sinun hallita, milloin renderoida komponentin tiettyjä osia, kuten alaotsikon.

```java title='AppTitle.java'
// Lisää boolean-argumentti
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Lisää otsikko oletuksena
      .add(title);

  // Valinnaisesti näytä alaotsikko
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Valmis `AppTitle` {#completed-app-title}

Kaikki yhdessä uudelleenkäytettävä komponentti näyttää seuraavalta:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakas Hallinta");
  private Paragraph subTitle = new Paragraph("Yksinkertainen rekisteröintijärjestelmä");

  public AppTitle(boolean showSubTitle) {
    title.setStyle("margin-bottom", "0");
    subTitle.setStyle("color", "var(--dwc-color-gray-50)");
    subTitle.setStyle("font-size", "var(--dwc-font-size-m)");

    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("0px")
        .add(title);

    if (showSubTitle) {
      self.add(subTitle);
    }
  }
}
```

## Tietoa-sivun luominen {#creating-an-about-page}

Ensimmäinen paikka, johon lisätään uusi `AppTitle`-komponentti, on tietoa-sivu. Tämä sivu sisältää kuvan ja `AppTitle`-komponentin, joka on keskitetty sivulle toisen `FlexLayout`-komponentin avulla.

### Sisällön keskittäminen `FlexLayoutilla` {#centering-content-using-a-flexlayout}

Tavoitteena on keskittää tietoa-sivun sisältö käyttämällä `FlexLayout`-komponenttia. `FlexLayout`-komponentti noudattaa [CSS flexbox -asettelu mallia](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Menetelmät `FlexLayout`-komponentille, kuten aikaisemmin käytetyt, sisältävät kohteiden suuntaamista pystysuoraan.

Kohteiden järjestämiseen `FlexLayout`:ssa käytetään suhteellista suuntausjärjestelmää. Sen sijaan, että ajattelisit vaakasuoria ja pystysuoria akselia, on parempi ajatella akselia, joka on rinnakkain kohteiden kanssa pääakselina, ja akselia, joka on kohtisuorassa kohteisiin, poikkiakselina.

Asettamalla sekä `FlexJustifyContent` että `FlexAlignment` ominaisuudet arvoon `CENTER` keskittää kohteet sekä pää- että poikkiakseliin `FlexLayout`:issa, ja tekemällä `FlexLayout`:sta vanhemman kontin täydellinen, tekee sen keskitettynä sivulla.

```java
private final FlexLayout layout = new FlexLayout();

// Täytä koko tila pääelementistä
layout.setSize("100%", "100%");

// Tee pääakselista pystysuora
layout.setDirection(FlexDirection.COLUMN);

// Keskitä kohteet poikkiakselilla
layout.setAlignment(FlexAlignment.CENTER);

// Keskitä kohteet pääakselilla
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Auttaaksesi visualisoimaan, miten eri menetelmät toimivat, katso blogikirjoitusta [FlexWrap your mind around webforJ:n FlexLayout](/blog/2025/08/26/flexlayout-container).

### Resurssien lisääminen {#adding-resources}

Yksi keskitettävästä `FlexLayout`:sta olevista kohteista on kuva. Tällä oppaalla voit katsella ja ladata [tietoa-sivun kuvaa](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) GitHubista. Lataamisen jälkeen lisää se projektisi staattiseen hakemistoon `src/main/resources/static/images` ja nimeä se `Files.svg`.

Tämän kuvan asettaminen staattiseen hakemistoon mahdollistaa sen viittaamisen [Webpalvelin -protokollan](/docs/managing-resources/assets-protocols#the-webserver-protocol) avulla. Sitten voit käyttää sitä sovelluksessasi HTML-elementtinä, kuten näin:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### `AboutView` -luominen {#creating-about-view}

Kuten kahdella aiemmalla sovellussivulla, tietoa-sivu tulee olemaan reittikohde. Hakemistossa `src/main/java/com/webforj/tutorial/views` lisää luokka nimeltä `AboutView`. Käytä sidottuna komponenttina `FlexLayout`-komponenttia, kuten teit `AppTitle`:ssa.

Koska olet nimennyt luokan `AboutView`, URL-kartoitukselle ei tarvitse antaa mukautettua arvoa; tämä sivu renderöityy oletuksena osoitteessa `http://localhost:8080/about`.

Tässä on, miltä näyttää, kun käytät aikaisemman vaiheen käsitteitä yhdessä juuri luotujen komponenttien kanssa luodaksesi uuden näkymän, jolla on keskitetty sisältö:

```java title='AboutView.java'
@Route()
@FrameTitle("Tietoa")
public class AboutView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Img fileImg = new Img("ws://images/Files.svg");

  public AboutView() {
    fileImg.setWidth(250);
    self.setSize("100%", "100%")
        .setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(fileImg, new AppTitle(false));
  }
}
```

## `Layout`-reitin luominen {#creating-the-layout-route}

Kaikkia aiemmissa vaiheissa mainittuja reittityyppejä, `Layout`-reittejä käytetään lapsinäkymien ympäröimiseen, jolloin tietyt käyttöliittymän osat voivat pysyä jatkuvina näkymien välillä, kuten sivupalkki. Hakemistossa `src/main/java/com/webforj/tutorial/layouts` luo luokka nimeltä `MainLayout`.

### Reittilähtö {#route-outlets}

Kuten näkymäreiteissä, myös `MainLayout` tarvitsee `@Route`-annotaation. Koska sillä on `Layout`-pääte ja layout-reitit eivät vaikuta URL-osoitteeseen, tätä annotaatiota ei tarvitse varustaa argumenteilla.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

Sovellus tietää, mitä näkymiä renderöidään `MainLayout`:n sisällä julistamalla layout-luokan reittilähtöksi (/docs/routing/route-hierarchy/route-outlets) jokaisessa näkymässä. Aiemmissa vaiheissa on vain `value`-ominaisuus asetettuna `@Route`-annotaatioissa, joten nyt sinun täytyy eksplisiittisesti ilmoittaa, mitkä `value` ja `outlet` -ominaisuudet ovat näkyluokille.

<!-- vale Google.Quotes = NO -->
<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java
  @Route(value = "/", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```
  </TabItem>
  <TabItem value="AboutView" label="AboutView">
  ```java
  @Route(outlet = MainLayout.class)
  ```
  </TabItem>
</Tabs>
<!-- vale Google.Quotes = YES -->

:::note Viimeiset viimeistelyt
Tämä on viimeinen muutos, joka vaaditaan `FormView` ja `AboutView` -reittien kohdistamiseen tässä vaiheessa, joten muista päivittää `@Route`-annotaatiot näille näkymille ennen sovelluksen suorittamista.
:::

## `AppLayout`-komponentin käyttäminen {#using-the-app-layout-component}

Nyt kun sovelluksesi renderöi näkymät `MainLayout`:ssa, voit valita, mihin nuo komponentit renderöidään. Valitsemalla `AppLayout` sidottuna komponenttina `MainLayout`:lle, voit säilyttää näkymät oletuksena pääsisältöalueella, mutta myös antaa eri alueita lisätä asioita yläpalkkiin ja sivupalkkiin.

### Slotit {#slots}

Monissa webforJ-konttareissa `add()`-menetelmien käyttö lisää käyttöliittymäkomponentteja pääsisältöalueeseen. `AppLayout`-komponentissa on useita alueita käyttöliittymäkomponenttien lisäämiseen, jokainen erillisessä slotissa. Merkitsemällä `MainLayout` layout-reitiksi ja asettamalla sen sidotuksi komponentiksi `AppLayout`, näkymät renderöityvät automaattisesti pääsisältöslotissa.

Tässä vaiheessa käytät `drawer-title` ja `drawer` slotteja luodaksesi sivupalkin, sekä `header` slotin näyttääksesi, missä sivussa käyttäjä on ja kytkimen sivupalkkiin.

### Sivupalkin luominen {#making-a-side-menu}

Kun laitteessa on riittävästi näyttötilaa, `AppLayout`-komponentti näyttää laatikon. Täällä lisäät `AppTitle`-komponentin jälleen ja kohteita, jotka mahdollistavat käyttäjien navigoida sovelluksessa.

Oletuksena `AppLayout` ei näytä laatikon otsikkoa, mutta käyttämällä `setDrawerHeaderVisible()`-menetelmää voit näyttää kohteita, jotka ovat `drawer-title` slotissa, ja jotka ovat `AppTitle` sen alaotsikko näkyvissä.

```java
private AppLayout appLayout = new AppLayout();

// Näytä Laatikkohäntä
appLayout.setDrawerHeaderVisible(true);

// Lisää AppTitle Laukkohäntään sen alaotsikon kanssa
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer` slotin tulisi sitten sisältää komponentit, jotka mahdollistavat käyttäjien navigoida sovelluksessa. Käyttämällä [`AppNav`](/docs/components/appnav) komponenttia on helppo luoda uusia navigointivaihtoehtoja. Jokaiselle linkille sinun tarvitsee vain luoda `AppNavItem`. 
`AppNavItem` komponentit tämän oppaan aikana käyttävät kolmea parametria:

- Linkin etiketti
- Kohteen näkymä
- Valinnainen [`Icon`](/docs/components/icon) komponentti, joka käyttää kuvia [Tabler](https://tabler.io/icons)

Ryhmittäminen kaikki laatikon asetukset `MainLayout`:ssa näyttää seuraavalta:

```java title="MainLayout"
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setDrawer();
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("Dashboard", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("About", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
}
```

### Yläpalkin luominen {#making-a-header}

`header` slotin tulisi sisältää kaksi kohdetta: kytkin, joka näyttää tai piilottaa sivupalkin, ja tapa näyttää kehyksen otsikko. Molemmat kohteet ovat [Työkalupalkki](/docs/components/toolbar) komponentin sisällä, joka on toinen tapa järjestää komponentteja.

Voit sisällyttää kytkimen `AppLayout`-laatikon tuomaan `AppDrawerToggle`-komponentin. Tämä komponentti on jo muotoiltu yleisesti käytetyllä ikonilla piilotettuihin valikko vaihtoehtoihin, ja kohdistaa laatikon avautumaan ja sulkeutumaan.

```java
// Luo säilökomponentit
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Lisää Työkalupalkki AppLayoutin yläpalkkiin
appLayout.addToHeader(toolbar);

// Lisää AppDrawerToggle työkalupalkkiin
toolbar.addToStart(new AppDrawerToggle());
```

Yläpalkki voi myös näyttää kehyksen otsikon käyttämällä navigointitapahtumaa saadaksesi tietoja sisään tulevasta komponentista ja tapahtuman kuuntelijaa tilauspoiston estämiseksi muistivuodoilta.

```java
// Luo H1-elementti ja navigointitilauksen
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Rekisteröi tapahtuma navigoitaessa
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Poista kuuntelijat ennen MainLayoutin tuhoamista
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Hae kehyksen otsikko saapuvasta näkymäluokasta
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Valmis `MainLayout` {#completed-mainlayout}

Tässä on `MainLayout`, jossa on luodut sisällöt laatikolle ja yläpalkille `AppLayout`-sisällä:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">

```java
@Route
public class MainLayout extends Composite<AppLayout> {
  private AppLayout self = getBoundComponent();
  private H1 title = new H1("");
  private ListenerRegistration<NavigateEvent> navigateRegistration;
  private Toolbar toolbar = new Toolbar();
  private AppNav appNav = new AppNav();

  public MainLayout() {
    setHeader();
    setDrawer();
    navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);
  }

  private void setHeader() {
    self.addToHeader(toolbar);

    toolbar.addToStart(new AppDrawerToggle());
    toolbar.addToTitle(title);
  }

  private void setDrawer() {
    self.setDrawerHeaderVisible(true)
        .addToDrawerTitle(new AppTitle(true));

    appNav.addItem(new AppNavItem("Dashboard", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("About", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }

  @Override
  protected void onDidDestroy() {
    if (navigateRegistration != null) {
      navigateRegistration.remove();
    }
  }

  private void onNavigate(NavigateEvent ev) {
    Component component = ev.getContext().getComponent();
    if (component != null) {
      FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
      title.setText(frameTitle != null ? frameTitle.value() : "");
    }
  }

}
```

</ExpandableCode>
<!-- vale on -->

## `FormView` -päivitys {#updating-form-view}

Kuten aiemmin mainittiin, ainoa muutos `FormView`-näkymän osalta oli `@Route`-annotaatiossa.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## `MainView` -päivitys {#updating-main-view}

`MainView`:lle vaihdat sidotun komponentin `Div`:stä `FlexLayout`:iin. Tämä mahdollistaa taulukon keskittämisen, ja voit myös siirtää tiettyjä komponentteja asettelun sisällä. Käyttämällä `setItemAlignment()`-menetelmää voit valita komponentin asettelusta ja siirtää sitä, jotta voit säilyttää taulukon keskitettynä, samalla kiinnittäen asiakaslisäyspainikkeen yläoikeaan asettelun kulmaan.

```java
// Muuta sidottu komponentti FlexLayoutiksi
private FlexLayout self = getBoundComponent();

// Kohdistaa painikkeen poikkiaxisnan loppupäähän
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Toinen parannus, jonka voit tehdä tässä, on taulukon leveys. Kiinteän leveuden sijaan voit asettaa sen vastaamaan vanhempaan konttiin, `FlexLayout`:iin. Sitten tuo `FlexLayout` voi olla enimmäisleveys, jotta se ei venyisi liian suureksi suuremmilla näytöillä.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Yhdistämällä nämä ja luomalla toinen menetelmä saadaksesi `FlexLayout` keskitettynä, näyttää `MainView` seuraavilta korostetuilla muutoksilla:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">

```java
@Route(value = "/", outlet = MainLayout.class)
@FrameTitle("Asiakas Taulukko")
// highlight-next-line
public class MainView extends Composite<FlexLayout> {
  private final CustomerService customerService;
  // highlight-next-line
  private FlexLayout self = getBoundComponent();
  private Table<Customer> table = new Table<>();
  private Button addCustomer = new Button("Lisää Asiakas", ButtonTheme.PRIMARY,
      e -> Router.getCurrent().navigate(FormView.class));

  public MainView(CustomerService customerService) {
    this.customerService = customerService;
    addCustomer.setWidth(200);
    buildTable();
    // highlight-next-line
    setFlexLayout();
    // highlight-next-line
    self.add(addCustomer, table);
    // highlight-next-line
    self.setItemAlignment(FlexAlignment.END, addCustomer);
  }

  private void buildTable() {
    // highlight-next-line
    table.setSize("100%", "294px");
    table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
    table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
    table.addColumn("company", Customer::getCompany).setLabel("Yritys");
    table.addColumn("country", Customer::getCountry).setLabel("Maa");
    table.setColumnsToAutoFit();
    table.setColumnsToResizable(false);
    table.getColumns().forEach(column -> column.setSortable(true));
    table.setRepository(customerService.getRepositoryAdapter());
    table.setKeyProvider(Customer::getId);
    table.addItemClickListener(this::editCustomer);
  }

  // highlight-next-line
  private void setFlexLayout() {
    // highlight-next-line
    self.setSize("100%", "100%")
        // highlight-next-line
        .setMargin("auto")
        // highlight-next-line
        .setMaxWidth(2000)
        // highlight-next-line
        .setDirection(FlexDirection.COLUMN)
        // highlight-next-line
        .setAlignment(FlexAlignment.CENTER);
        // highlight-next-line
  }

  private void editCustomer(TableItemClickEvent<Customer> e) {
    Router.getCurrent().navigate(FormView.class,
        ParametersBag.of("id=" + e.getItemKey()));
  }
}
```

</ExpandableCode>
<!-- vale on -->
