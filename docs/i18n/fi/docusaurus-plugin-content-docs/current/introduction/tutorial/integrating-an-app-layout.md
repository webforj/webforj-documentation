---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: ddf62eb6d62a711c38f9ddaf9caeabad
---
Tässä vaiheessa yhdistät kaikki sovelluksesi osat yhtenäiseksi sovellusrakenteeksi. Tämän vaiheen lopussa sovelluksesi rakenne muistuttaa läheisesti [SideMenu-mallia](/docs/building-ui/archetypes/sidemenu), ja ymmärrät paremmin, miten seuraavat komponentit ja käsitteet toimivat:

- [`FlexLayout`](/docs/components/flex-layout)
- [Reittilähtökohtia](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Sovelluksen suorittaminen {#running-the-app}

Kun kehität sovellustasi, voit käyttää [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) vertailukohtana. Nähdäksesi sovelluksen toiminnassa:

1. Siirry ylimpään hakemistoon, joka sisältää `pom.xml` -tiedoston, tämä on `6-integrating-an-app-layout`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selainikkunan osoitteeseen `http://localhost:8080`.

## Uuden komponentin luominen {#creating-a-reusable-component}

Aiemmassa vaiheessa, [Reititys ja koostumukset](/docs/introduction/tutorial/routing-and-composites), loit kaksi koottua komponenttia, jotka sisälsivät asiakastietotaulukon ja asiakaslomakkeen sisällön. Osana tätä vaihetta luot pienemmän, uudelleenkäytettävän koottavan komponentin, joka näyttää sovelluksen nimen sivuvalikossa ja tieto-sivulla. Jos päätät muuttaa sovelluksen nimeä tulevaisuudessa, sinun tarvitsee vain päivittää se tässä komponentissa.

Hakemistossa `src/main/java/com/webforj/tutorial/components` luo luokka nimeltä `AppTitle`. `AppTitle`:n sidottu komponentti on `FlexLayout`, säilytyskomponentti, jota käytetään tämän vaiheen aikana näyttämään, miten tehdä monimutkaisempia asetteluja. Tämän `FlexLayout`:n avulla voit järjestää kohteiden suunnan ja välin kohteiden välillä. Se tapahtuu käyttämällä `setDirection()` ja `setSpacing()` menetelmiä.

```java title='AppTitle.java'
// Tee sidotusta komponentista FlexLayout
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public AppTitle() {

    // Järjestä kohteet pystysuoraan
    self.setDirection(FlexDirection.COLUMN);

    // Aseta väli kohteiden väliin
    self.setSpacing("0px");
  }
}
```

Käytä sitten tavallisia HTML-elementtejä luodaksesi otsikon ja alaotsikon. Asettamalla otsikkoelementin alareunan marginaaliin `0px`, saat elementit lähempänä toisiaan, ja voit tyylitellä alaotsikon käyttäen [DWC CSS -muuttujia](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakashallinta");
  private Paragraph subTitle = new Paragraph("Yksinkertainen tietojärjestelmä");

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

### Valinnainen renderöinti {#optional-rendering}

Vaikka `AppTitle` on yksinkertainen, boolean-argumentin lisääminen konstruktorimenetelmään antaa sinulle mahdollisuuden hallita, milloin tietyt osat komponentista, kuten alaotsikko, renderoidaan.

```java title='AppTitle.java'
// Lisää boolean-argumentti
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Lisää otsikko oletuksena
      .add(title);

  // Näytä alaotsikko vaihtoehtoisesti
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Valmis `AppTitle` {#completed-app-title}

Kaiken kaikkiaan, uudelleenkäytettävän komponentin tulisi näyttää seuraavalta:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakashallinta");
  private Paragraph subTitle = new Paragraph("Yksinkertainen tietojärjestelmä");

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

## Tieto-sivun luominen {#creating-an-about-page}

Ensimmäinen paikka, johon lisätään juuri luotu `AppTitle` komponentti, on tieto-sivu. Tämä sivu sisältää kuvan ja `AppTitle` komponentin, keskitettynä sivulle toisen `FlexLayout` komponentin avulla.

### Sisällön keskittäminen käyttämällä `FlexLayout`:a {#centering-content-using-a-flexlayout}

Tavoitteena on keskittää tieto-sivun sisältö käyttämällä `FlexLayout`:ia. `FlexLayout` komponentti noudattaa [CSS flexbox -asettelu mallia](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). `FlexLayout`:n menetelmät, kuten aiemmin käytettyjen kohteiden järjestäminen pystysuoraan, ovat erilaisia tapoja järjestää kohteita.

Menetelmät kohteiden järjestämiseksi `FlexLayout`:issa käyttävät suhteellista suuntaista järjestelmää. Sen sijaan että ajattelisit vaakasuoria ja pystysuoria akselia, on parempi ajatella akselia, joka on rinnakkainen kohteiden kanssa pääakselina, ja akselia, joka on kohtisuorassa kohteiden kanssa, ristiaskeleena.

Asettamalla sekä `FlexJustifyContent` että `FlexAlignment` ominaisuudet `CENTER` auttaa keskittämään kohteet pitkin sekä pää- että ristiaspäässuunnat `FlexLayout`:issä, ja tekemällä `FlexLayout`:ista koko vanhemman säilöä täyttävän tekee sen keskitetyn sivulle.

```java
private final FlexLayout layout = new FlexLayout();

// Täytä koko vanhemman elementin tila
layout.setSize("100%", "100%");

// Tee pääakselista pystysuora
layout.setDirection(FlexDirection.COLUMN);

// Keskity kohteet ristiaspaineaksella
layout.setAlignment(FlexAlignment.CENTER);

// Keskity kohteet pääakselilla
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Auttaaksesi visualisoimaan, miten eri menetelmät toimivat, katso blogikirjoitusta [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Resurssien lisääminen {#adding-resources}

Yksi asioista, jotka menevät keskitettyyn `FlexLayout`:iin, on kuva. Tämän oppaan vuoksi voit katsella ja ladata [tieto-sivun kuvaa](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) GitHubista. Kun olet ladannut sen, lisää se projektisi staattiseen kansioon osoitteeseen `src/main/resources/static/images` ja nimeä se `Files.svg`.

Kuvan laittaminen staattiseen kansioon antaa mahdollisuuden viitata siihen käyttämällä [Verkkopalvelimen protokollaa](/docs/managing-resources/assets-protocols#the-webserver-protocol). Voit sitten käyttää sitä sovelluksessasi HTML-elementtinä, kuten näin:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### `AboutView` luominen {#creating-about-view}

Kuten kaksi olemassa olevaa sovellussivua, tieto-sivu on reittiävä näkymä. Hakemistossa `src/main/java/com/webforj/tutorial/views` lisää luokka nimeltä `AboutView`. Käytä sidottuna komponenttina `FlexLayout`:ia, kuten teit `AppTitle`:lle.

Koska olet nimennyt luokan `AboutView`, ei ole tarpeen antaa mukautettua arvoa URL-mappingille; tämä sivu renderöityy osoitteessa `http://localhost:8080/about` oletuksena.

Tältä näyttää, kun käytät aiemmista vaiheista saadun käsitteitä yhdessä juuri luotujen komponenttien kanssa luodaksesi uuden näkymän, jonka sisältö on keskitetty:

```java title='AboutView.java'
@Route()
@FrameTitle("Tietoja")
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

## `Layout` reitin luominen {#creating-the-layout-route}

Se mainitaan lyhyesti [Reititys ja koostumukset](/docs/introduction/tutorial/routing-and-composites) vaiheessa, mutta on olemassa kaksi [reitityyppiä](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` ja `AboutView` ovat kaikki `View` reittejä, kun taas reitityyppi, jota käytät luodaksesi sovelluksen sivuvalikon, on `Layout` reitti.

Layout-reitit kääriä lapsinäkymiä ja mahdollistavat tiettyjen UI-osien säilyttämisen näkymien välillä, kuten sivuvalikon. Hakemistossa `src/main/java/com/webforj/tutorial/layouts` luo luokka nimeltä `MainLayout`.

### Reittilähtöpaikat {#route-outlets}

Kuten näkymäreiteillä, `MainLayout` tarvitsee `@Route` -annotaation. Kuitenkin, koska sillä on `Layout` liitteenä ja layout-reitit eivät vaikuta URL-osoitteeseen, tämän annotaation ei tarvitse sisältää argumentteja.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

Sovellus tietää, mitä näkymiä renderoida `MainLayout`:in sisällä, ilmoittamalla layout-luokan [reititysleikkien](/docs/routing/route-hierarchy/route-outlets) jokaisessa näkymässä. Aiemmissa vaiheissa on vain `value`-ominaisuus asetettuna `@Route` annotaatioihin, joten nyt sinun on ilmoitettava, mitkä `value` ja `outlet` ominaisuudet ovat näkyluokilla.

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

:::note Viimeiset säädöt
Tämä on viimeinen muutos, joka tarvitaan `FormView` ja `AboutView` kohdalla tässä vaiheessa, joten muista päivittää `@Route` annotaatio näissä näkymissä ennen kuin suoritat sovelluksesi.
:::

## `AppLayout` komponentin käyttäminen {#using-the-app-layout-component}

Nyt kun sovelluksesi renderoi näkymiä `MainLayout` sisällä, voit valita, mihin nuo komponentit renderoidaan. Valitsemalla `AppLayout`:in sidottuna komponenttina `MainLayout`:lle, voit tallentaa näkymät oletuksena pääsisältöalueelle, samalla kun saat eri alueita, joihin voit lisätä elementtejä ylä- ja sivuvalikkoa varten.

### Paikat {#slots}

Monille webforJ säilöille, `add()` menetelmien käyttö lisää UI-komponentteja pääsisältöalueelle. `AppLayout` komponentissa on useita alueita UI-komponenttien lisäämiseen, jokainen erillisessä paikassa. Merkitsemällä `MainLayout`:n layout-reitiksi ja asettamalla sen sidotuksi komponentiksi `AppLayout`, näkymät renderoituvat automaattisesti pääsisältöpaikkaan.

Tässä vaiheessa käytät `drawer-title` ja `drawer` paikkoja luodaksesi sivuvalikon, ja `header` paikkaa näyttääksesi, millä sivulla käyttäjä on ja kytkimen sivuvalikolle.

### Sivuvalikon tekeminen {#making-a-side-menu}

Kun laitteessa on tarpeeksi näyttötilaa, `AppLayout` komponentti näyttää laatikon. Tähän lisäät `AppTitle`:n uudelleen ja elementtejä, jotka mahdollistavat käyttäjien navigoida sovelluksessa.

Oletuksena `AppLayout` ei näytä laatikon ykköshaaraa, mutta käyttämällä `setDrawerHeaderVisible()` menetelmää pystyt näyttämään elementtejä, jotka ovat `drawer-title` paikassa, mikä on `AppTitle` alaotsikkonsa kanssa.

```java
private AppLayout appLayout = new AppLayout();

// Näytä laatikon ykköshaara
appLayout.setDrawerHeaderVisible(true);

// Lisää AppTitle laatikon ykköhaaraan alaotsikkonsa kanssa
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer` paikassa pitäisi sitten sisältää komponentit, jotka mahdollistavat käyttäjien navigoida sovelluksessa. Käyttäminen [`AppNav`](/docs/components/appnav) komponenttia helpottaa uusien navigointivaihtoehtojen luomista. Jokaiselle linkille tarvitset vain luoda `AppNavItem`. `AppNavItem` komponentit tässä oppaassa käyttävät kolmea parametria:

- Linkin etiketti
- Kohde-näkymä
- Valinnainen [`Icon`](/docs/components/icon) komponentti, joka käyttää kuvia [Tablerista](https://tabler.io/icons)

Kaikkien laatikon asetusten ryhmittäminen `MainLayout` näyttäisi seuraavalta:

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

    appNav.addItem(new AppNavItem("Koontinäkymä", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("Tietoa", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
```

### Ylätason tekeminen {#making-a-header}

`header` paikassa tulisi olla kaksi elementtiä: kytkin näyttämään tai piilottamaan sivuvalikon ja tapa näyttää kehysotsikko. Molemmat nämä elementit ovat [Työkalupakki](/docs/components/toolbar) komponentissa, toinen tapa organisoida komponentteja.

Voit lisätä `AppLayout` laatikon kytkimen `AppDrawerToggle` komponentilla. Tämä komponentti on jo tyylitelty yleisesti käytetyllä ikonilla piilotetuille valikkovaihtoehdoille ja kohdistaa laatikon avaamaan ja sulkemaan sen.

```java
// Luo säilöntäkomponentit
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Lisää Työkalupakki AppLayout yläosaan
appLayout.addToHeader(toolbar);

// Lisää AppDrawerToggle työkalupakkiin
toolbar.addToStart(new AppDrawerToggle());
```

Ylätaso voi myös näyttää kehysotsikon käyttämällä navigointitapahtumaa, jotta saat tietoa saapuvasta komponentista, jolloin sinulla on tapahtumakuuntelija, joka poistaa rekisteröinnin estääksesi muistivuodot.

```java
// Luo H1-elementti ja navigointirekisteröinti
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Rekisteröi tapahtuma navigoinnin aikana
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Poista kuuntelijat ennen MainLayoutin tuhoamista
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Hanki kehysotsikko saapuvan reitin luokasta
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Valmis `MainLayout` {#completed-mainlayout}

Tässä on `MainLayout`, jossa on luotu sisältö laatikolle ja ylätasolle `AppLayout`:n sisällä:

<!-- vale off -->
<ExpandableCode title="MainLayout.java" language="java">
{`@Route
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

      appNav.addItem(new AppNavItem("Koontinäkymä", MainView.class,
          TablerIcon.create("archive")));
      appNav.addItem(new AppNavItem("Tietoa", AboutView.class,
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
`}
</ExpandableCode>
<!-- vale on -->

## `FormView`:n päivittäminen {#updating-form-view}

Kuten aiemmin mainittiin, ainoa muutos `FormView`:lle oli `@Route` annotaatiossa.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## `MainView`:n päivittäminen {#updating-main-view}

`MainView`:lle vaihdat sidotun komponentin `Div`:stä `FlexLayout`:iin. Tämä mahdollistaa taulukon keskittämisen sekä joidenkin erikseen tiettyjen komponenttien siirtämisen asettelussa. Käyttämällä `setItemAlignment()` menetelmää voit valita komponentin asettelusta ja siirtää sen, jotta voit pitää taulukon keskitettynä, samalla kun sitoutat asiakkaan lisäämispainikkeen asettelun oikeaan yläkulmaan.

```java
// Vaihda sidottu komponentti FlexLayout:ksi
private FlexLayout self = getBoundComponent();

// Kohdistaa painikkeen ristiaskeleen loppupisteeseen
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Toinen parannus, jonka voit tehdä, on taulukon leveys. Kiinteän leveyden sijasta voit asettaa sen vastaamaan vanhemman säilön, `FlexLayout`:in, leveyttä. Sitten tämä `FlexLayout` voi saada maksimaalisen leveyden, jotta se ei veny liian pitkälle suuremmilla näytöillä.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Yhdistämällä nämä yhteen ja tekemällä toisen menetelmän saadaksesi `FlexLayout` keskitettynä, kuten aiemmissa, tekee `MainView`:sta, jossa on korostetut muutokset:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Asiakkaat Taulukko")
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
`}
</ExpandableCode>
<!-- vale on -->
