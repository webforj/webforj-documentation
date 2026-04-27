---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
sidebar_class_name: new-content
_i18n_hash: 66c364d83c3b6d574acaca5156bbb018
---
Tässä vaiheessa keräät kaikki sovelluksesi osat yhteen johdonmukaiseen sovelluslayoutiin. Tämän vaiheen lopussa sovelluksesi rakenne muistuttaa läheisesti [SideMenu-arkkityyppiä](/docs/building-ui/archetypes/sidemenu), ja sinulla on parempi ymmärrys siitä, miten seuraavat komponentit ja käsitteet toimivat:

- [`FlexLayout`](/docs/components/flex-layout)
- [Reittiulostulot](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Sovelluksen suorittaminen {#running-the-app}

Sovellusta kehittäessäsi voit käyttää [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) vertailupohjana. Näet sovelluksen käytössä:

1. Siirry pääkansioon, joka sisältää `pom.xml`-tiedoston, joka on `6-integrating-an-app-layout`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Uuden käytettävän komponentin luominen {#creating-a-reusable-component}

Aiemmassa vaiheessa, [Routing and Composites](/docs/introduction/tutorial/routing-and-composites), loit kaksi komposiittikomponenttia, jotka sisälsivät asiakastaulukon sisällön ja asiakaslomakkeen. Tämän vaiheen osana luot pienemmän, uudelleen käytettävän komposiittikomponentin, joka näyttää sovelluksen nimen sivuvalikossa ja "Tietoa"-sivulla. Jos päätät muuttaa sovelluksen nimeä tulevaisuudessa, sinun tarvitsee päivittää se vain tässä komponentissa.

Hakemistossa `src/main/java/com/webforj/tutorial/components` luo luokka nimeltä `AppTitle`. `AppTitle`:n sidottu komponentti on `FlexLayout`, säilytyskomponentti, jota käytetään tämän vaiheen aikana näyttämään, kuinka tehdä monimutkaisempia asetteluja. Tälle `FlexLayout`:lle järjestät kohteiden suunnan ja välin kohteiden välillä. Tämä tehdään käyttämällä `setDirection()` ja `setSpacing()` -metodeja.

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

Sen jälkeen käytä standardeja HTML-elementtejä luodaksesi otsikon ja alaotsikon. Header-elementin alaosan marginaalin asettaminen `0px` tuo elementit lähemmäksi toisiaan, ja voit tyylitellä alaotsikon käyttämällä [DWC CSS -muuttujia](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakkaan hallinta");
  private Paragraph subTitle = new Paragraph("Yksinkertainen rekisterijärjestelmä");

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

Vaikka `AppTitle` on yksinkertainen, lisätty boolean-parametri konstruktoriin antaa sinun hallita, milloin renderöidä tietyt osat komponentista, kuten alaotsikon.

```java title='AppTitle.java'
// Lisää boolean-parametri
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

Kaikkiaan uudelleen käytettävä komponentti näyttää tältä:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakkaan hallinta");
  private Paragraph subTitle = new Paragraph("Yksinkertainen rekisterijärjestelmä");

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

## Tietosivun luominen {#creating-an-about-page}

Ensimmäinen paikka, johon lisäät juuri luodun `AppTitle`-komponentin, on tietosivu. Tämä sivu sisältää kuvan ja `AppTitle`-komponentin, keskitettynä sivulle toisen `FlexLayout`:n avulla.

### Sisällön keskittäminen `FlexLayout`:lla {#centering-content-using-a-flexlayout}

Tavoitteena on keskittyä tietosivun sisältöön `FlexLayout`:n avulla. `FlexLayout` komponentti seuraa [CSS flexbox -asettelumallia](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). `FlexLayout`:n metodit, kuten aiemmin käytetyt menetelmät kohteiden suuntaamiseksi pystysuoraan, ovat erilaisia tapoja järjestää kohteet.

Kohteiden järjestämisen metodit `FlexLayout`:ssa käyttävät suhteellista suuntajärjestelmää. Sen sijaan, että ajattelisit vaakasuoria ja pystysuoria akselia, on parempi ajatella, että akseli, joka on kohtisuorassa kohteiden kanssa, on pääakseli ja akseli, joka on kohteita vastaan kohtisuorassa, on poikkisysteemi.

Kun asetat sekä `FlexJustifyContent` että `FlexAlignment` -ominaisuudet `CENTER`:iksi, se keskittää kohteet sekä pää- että poikkisysteemissä `FlexLayout`:ssa, ja kun teet `FlexLayout`:n käyttämään koko vanhempaa säiliötä, se keskittää sen sivulle.

```java
private final FlexLayout layout = new FlexLayout();

// Täytä koko tila vanhemman elementin
layout.setSize("100%", "100%");

// Aseta pääakselin suunta pystysuoraksi
layout.setDirection(FlexDirection.COLUMN);

// Keskitä kohteet poikkisysteemissä
layout.setAlignment(FlexAlignment.CENTER);

// Keskitä kohteet pääakselissa
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Auttaaksesi visualisoimaan, kuinka eri menetelmät toimivat, voit katsoa blogiviestiä [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Resurssien lisääminen {#adding-resources}

Yksi kohteista, joka menee keskitettyyn `FlexLayout`:iin, on kuva. Tämän oppaan vuoksi voit katsella ja ladata [tietosivun kuvaa](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) GitHubista. Lataa se ja lisää se projektisi staattiseen kansioon `src/main/resources/static/images` ja nimeä se `Files.svg`.

Tämän kuvan laittaminen staattiseen kansioon mahdollistaa sen käyttämisen viittauksena verkkopalveluprotokollan avulla, kuten teit, kun viittasit CSS-tiedostoon ensimmäisessä vaiheessa, [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app). Voit sitten käyttää sitä sovelluksessasi HTML-elementtinä seuraavasti:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### `AboutView`:n luominen {#creating-about-view}

Kuten kahdella olemassa olevalla sovellussivulla, tietosivusta tulee reititettävä näkymä. Hakemistossa `src/main/java/com/webforj/tutorial/views` lisää luokka nimeltä `AboutView`. Käytä sidottuna komponenttina `FlexLayout`:a, kuten teit `AppTitle`:n kanssa.

Koska olet nimennyt luokan `AboutView`:ksi, URL-kartoitukselle ei tarvitse antaa erityisarvoa; tämä sivu renderöityy oletusarvoisesti osoitteeseen `http://localhost:8080/about`.

Tässä on miltä se näyttää, kun käytät aikaisempien vaiheiden käsitteitä ja juuri luotuja komponentteja uuden näkymän luomiseksi keskitettyyn sisältöön:

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

## `Layout`-reititin luominen {#creating-the-layout-route}

Se mainitaan lyhyesti [Routing and Composites](/docs/introduction/tutorial/routing-and-composites) vaiheessa, mutta on olemassa kaksi [reitityyppiä](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` ja `AboutView` ovat kaikki `View`-reittejä, kun taas se reitityyppi, jota käytät sovelluksen sivuvalikon luomiseen, on `Layout`-reititin.

Layout-reitit paketoivat lapsinäkymiä ja mahdollistavat tiettyjen UI-osien pysymisen näkymien välillä, kuten sivuvalikon. Hakemistossa `src/main/java/com/webforj/tutorial/layouts` luo luokka nimeltä `MainLayout`.

### Reittiulostulot {#route-outlets}

Kuten näkymäreiteillä, `MainLayout` tarvitsee `@Route`-annotaation. Koska sillä on `Layout`-lisäyksena, eikä layout-reitit vaikuta URL-osoitteeseen, näissä annotaatioissa ei tarvita argumentteja.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

Sovellus tietää, mitkä näkymät renderöidään `MainLayout`:ssa, ilmoittamalla layout-luokan [reittiulostimena](/docs/routing/route-hierarchy/route-outlets) jokaisessa näkymässä. Edellisissä vaiheissa on vain `value`-ominaisuus asetettuna `@Route`-annotaatiossaan, joten nyt sinun on ilmoitettava selkeästi, mitkä `value` ja `outlet` -ominaisuudet ovat näkyluokille.

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

:::note Viimeiset silaukset
Tämä on viimeinen muutos, joka tarvitaan `FormView` ja `AboutView` tämän vaiheen osalta, joten muista päivittää `@Route`-annotaatio näille näkymille ennen kuin suoritat sovelluksesi.
:::

## `AppLayout`-komponentin käyttäminen {#using-the-app-layout-component}

Nyt kun sovelluksesi renderöi näkymät `MainLayout`:ssa, voit valita, mihin komponentit renderöityvät. Valitsemalla `AppLayout`:n sidotuksi komponentiksi `MainLayout`:lle voit tallentaa näkymät oletusarvoisesti pääsisältöalueeseen, samalla kun sinulla on erilaisia alueita, joihin voit lisätä kohteita osoitteelle.

### Slotit {#slots}

Monissa webforJ-säilöissä käyttämällä `add()`-menetelmiä lisätään UI-komponentteja pääsisältöalueeseen. `AppLayout`-komponentissa on useita alueita UI-komponenttien lisäämistä varten, kukin erikseen slotissa. Merkitsemällä `MainLayout`:n layout-reitiksi ja asettamalla sen sidotuksi komponentiksi `AppLayout`, näkymät renderöityvät automaattisesti pääsisältöslotissa.

Tässä vaiheessa käytät `drawer-title` ja `drawer` slotteja luodaksesi sivuvalikon sekä `header` slotin näyttääksesi, millä sivulla käyttäjä on, ja se on sivuvalikon kytkin.

### Sivuvalikon tekeminen {#making-a-side-menu}

Kun laitteella on riittävästi näyttötilaa, `AppLayout`-komponentti näyttää laatikon. Tähän lisäät `AppTitle`-komponentin uudelleen ja kohteita, jotka mahdollistavat käyttäjien navigoinnin sovelluksessa.

Oletuksena `AppLayout` ei näytä laatikon otsikkoa, mutta käyttämällä `setDrawerHeaderVisible()`-menetelmää voit näyttää `drawer-title` slotin sisällä olevia kohteita, jotka ovat `AppTitle` otsikolla alaotsikon kera.

```java
private AppLayout appLayout = new AppLayout();

// Näytä Laatikon Otsikko
appLayout.setDrawerHeaderVisible(true);

// Lisää AppTitle Laatikon Otsikkoon alaotsikon kanssa
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer` slotin tulee sitten sisältää komponentteja, jotka mahdollistavat käyttäjien navigoinnin sovelluksessa. Käyttämällä [`AppNav`](/docs/components/appnav) -komponenttia on helppo luoda uusia navigointivaihtoehtoja. Jokaiselle linkille tarvitset vain luoda `AppNavItem`. `AppNavItem`-komponentit tässä oppaassa käyttävät kolmea parametria:

- Linkin etiketti
- Kohde-näkymä
- Valinnainen [`Icon`](/docs/components/icon) -komponentti, joka käyttää kuvia [Tabler](https://tabler.io/icons)

Koko laatikkoasetusten ryhmittely `MainLayout`:ssa näyttää seuraavalta:

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

    appNav.addItem(new AppNavItem("Dashbord", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("Tietoja", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
```

### Headerin tekeminen {#making-a-header}

`header` slotin tulee sisältää kaksi kohdetta: kytkin näyttämään tai piilottamaan sivuvalikon ja tapa näyttää kehysotsikko. Molemmat nämä kohteet ovat osa [Toolbar](/docs/components/toolbar) -komponenttia, toinen tapa järjestää komponentteja.

Voit sisällyttää kytkimen `AppLayout` laatikolle komponentilla `AppDrawerToggle`. Tämä komponentti on jo tyylitelty yleisesti käytetyn näkymävaihtoehtojen kuvakkeella, ja se avaa ja sulkee laatikkoa.

```java
// Luo säiliökomponentit
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Lisää Työkalupalkki AppLayoutin yläosaan
appLayout.addToHeader(toolbar);

// Lisää AppDrawerToggle työkalupalkkiin
toolbar.addToStart(new AppDrawerToggle());
```

Header voi myös näyttää kehysotsikon rekisteröimällä navigointitapahtuman ja hakemalla tietoja saapuvasta komponentista, ja se on tapahtumakuuntelija, joka poistaa rekisteröinnin estämään muistivuotoja.

```java
// Luo H1-elementti ja navigointirekisteröinti
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

// Hae kehysotsikko saapuvasta näkyluokasta
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Valmis `MainLayout`

Tässä on `MainLayout`, jossa on luotua sisältöä laatikon ja headerin sisällä `AppLayout`:issa:

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

      appNav.addItem(new AppNavItem("Dashbord", MainView.class,
          TablerIcon.create("archive")));
      appNav.addItem(new AppNavItem("Tietoja", AboutView.class,
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

Kuten aiemmin mainittiin, ainoa muutos `FormView`:lle oli `@Route`-annotaatiossa.

  ```java
  @Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
  ```

## `MainView`:n päivittäminen {#updating-main-view}

`MainView`:ssa vaihdat sidotun komponentin `Div`:stä `FlexLayout`:iin. Tämä mahdollistaa taulukon keskittymisen, samalla siirtäen tiettyjä komponentteja asettelun sisälle. `setItemAlignment()`-metodin käyttö antaa sinun valita komponentin asettelusta ja siirtää sen, joten voit pitää taulukon keskitettynä, kun taas asiakasta lisäävä painike voidaan kiinnittää asetelun yläkulmaan.

```java
// Vaihda sidottu komponentti FlexLayout:iksi
private FlexLayout self = getBoundComponent();

// Kohdistaa painikkeen poikkisysteemin loppuun
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Toinen parannus, jonka voit tehdä tässä, on taulukon leveys. Sen sijaan, että se olisi kiinteä leveys, voit asettaa sen vastaamaan vanhempaa säilöä, `FlexLayout`:ia. Sitten tuolle `FlexLayout`:lle voidaan asettaa enimmäisleveys, jotta se ei veny liikaa suuremmilla näytöillä.

```java 
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Yhdistämällä nämä yhteen ja tekemällä toisen metodin saadaksesi `FlexLayout` keskittyneenä kuten aiemmissa, `MainView` näyttää seuraavalta, jossa on korostetut muutokset:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Asiakkaan taulukko")
  // highlight-next-line
  public class MainView extends Composite<FlexLayout> {
    private final CustomerService customerService;
    // highlight-next-line
    private FlexLayout self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
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
