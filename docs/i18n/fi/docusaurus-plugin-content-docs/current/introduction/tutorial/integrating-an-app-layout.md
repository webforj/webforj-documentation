---
title: Integrating an App Layout
sidebar_position: 7
description: Step 6 - Using the AppLayout and FlexLayout components.
_i18n_hash: e56d98e67ff6ee74a4dc1ee81346350d
---
Tässä vaiheessa yhdistät kaikki sovelluksesi osat yhtenäiseksi sovelluksen ulkoasuksi. Tämän vaiheen lopussa sovelluksesi rakenne muistuttaa läheisesti [SideMenu-arkkityyppiä](/docs/building-ui/archetypes/sidemenu), ja ymmärrät paremmin, kuinka seuraavat komponentit ja käsitteet toimivat:

- [`FlexLayout`](/docs/components/flex-layout)
- [Reittimuodot](/docs/routing/route-hierarchy/route-outlets)
- [`AppLayout`](/docs/components/app-layout)
- [`AppNav`](/docs/components/appnav)

## Sovelluksen suorittaminen {#running-the-app}

Sovellusta kehittäessäsi voit käyttää [6-integrating-an-app-layout](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry pääkansioon, joka sisältää `pom.xml`-tiedoston. Tämä on `6-integrating-an-app-layout`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa ajaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Uuden käytettävän komponentin luominen {#creating-a-reusable-component}

Aiemmassa vaiheessa, [Reititys ja yhdisteet](/docs/introduction/tutorial/routing-and-composites), loit kaksi yhdistettyä komponenttia, jotka sisälsivät asiakastaulukon ja asiakaslomakkeen sisällön.
Tässä vaiheessa luot pienemmän, käytettävän yhdisteen, joka näyttää sovelluksen nimen sivuvalikossa ja tietosivulla. Jos päätät muuttaa sovelluksen nimeä tulevaisuudessa, sinun tarvitsee vain päivittää se tässä komponentissa.

Luodaan `src/main/java/com/webforj/tutorial/components`-kansioon luokka nimeltä `AppTitle`. `AppTitle`:n sidottu komponentti on `FlexLayout`, konttikomponentti, jota käytetään tässä vaiheessa näyttämään, kuinka tehdä monimutkaisempia asetteluja.
Tässä `FlexLayout`:issa järjestät kohteiden suunnan ja välin kohteiden välillä. Tämä tehdään käyttämällä `setDirection()` ja `setSpacing()` -menetelmiä vastaavasti.

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

Käytä sitten standardeja HTML-elementtejä luodaksesi otsikon ja alaotsikon. Asettamalla otsikkoelementin alareunan marginaaliksi `0px` tuot elementit lähemmäs toisiaan, ja voit muotoilla alaotsikon käyttäen [DWC CSS -muuttujia](/docs/styling/css-variables).

```java title='AppTitle.java' {3-4,7-9,13}
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakashallinta");
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

### Valinnainen renderöinti {#optional-rendering}

Vaikka `AppTitle` on yksinkertainen, boolean-argumentin lisääminen konstruktorimenetelmään mahdollistaa sen hallinnan, milloin tietyt komponentin osat, kuten alaotsikko, renderöidään.

```java title='AppTitle.java'
// Lisää boolean-argumentti
public AppTitle(boolean showSubTitle) {

  self.setDirection(FlexDirection.COLUMN)
      .setSpacing("0px")

      // Lisää otsikko oletusarvoisesti
      .add(title);

  // Näytä tarvittaessa alaotsikko
  if (showSubTitle) {
    self.add(subTitle);
  }
}
```

### Valmis `AppTitle` {#completed-app-title}

Kaiken kaikkiaan käytettävän komponentin tulisi näyttää seuraavalta:

```java title='AppTitle.java'
public class AppTitle extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private H2 title = new H2("Asiakashallinta");
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

## Tietosivun luominen {#creating-an-about-page}

Ensimmäinen paikka, johon lisätään juuri luotu `AppTitle`-komponentti, on tietosivu. Tämä sivu sisältää kuvan ja `AppTitle`-komponentin, keskitettynä sivulla toisen `FlexLayout`:n avulla.

### Sisällön keskipistettä käyttämällä `FlexLayout` {#centering-content-using-a-flexlayout}

Tavoitteena on keskittää tietosivun sisältö `FlexLayout`:n avulla. `FlexLayout`-komponentti seuraa [CSS flexbox -asettelu mallia](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). `FlexLayout`:n menetelmät, kuten aiemmin käytetyt menetelmät, joita käytetään kohteiden suuntaamiseen pystysuoraan, ovat eri tapoja järjestää kohteita.

Kohteiden järjestämiseen `FlexLayout`:ssa käytetään suhteellista suuntausjärjestelmää. Sen sijaan, että ajattelisit vaakasuoria ja pystysuoria akselia, on parempi ajatella akselia, joka on kohteiden suuntaan rinnakkainen, pääakselina, ja akselia, joka on kohteiden suuntaan kohtisuora, poikittaisakselina.

Asettamalla sekä `FlexJustifyContent` että `FlexAlignment` -ominaisuudet `CENTER` keskität kohteet sekä pää- että poikittaisakselilla `FlexLayout`:ssa, ja tekemällä `FlexLayout`:n, joka vie koko vanhemman komponentin tilan, saat sen keskitettyä sivulle.

```java
private final FlexLayout layout = new FlexLayout();

// Täytä koko tila vanhemmasta elementistä
layout.setSize("100%", "100%");

// Aseta pääakseli pystysuoraksi
layout.setDirection(FlexDirection.COLUMN);

// Keskitä kohteet poikittaisakselilla
layout.setAlignment(FlexAlignment.CENTER);

// Keskitä kohteet pääakselilla
layout.setJustifyContent(FlexJustifyContent.CENTER);
```

Auttaaksesi visualisoimaan kuinka eri menetelmät toimivat, tarkista blogikirjoitus [FlexWrap your mind around webforJ's FlexLayout](/blog/2025/08/26/flexlayout-container).

### Resurssien lisääminen {#adding-resources}

Yksi elementti, joka tulee menemään keskitettyyn `FlexLayout`:iin, on kuva. Tämän oppaan osalta voit katsella ja ladata [tietosivun kuva](https://github.com/webforj/webforj-tutorial/tree/main/6-integrating-an-app-layout/src/main/resources/static/images/Files.svg) GitHubista.
Kun olet ladannut sen, lisää se projektisi staattiseen kansioon `src/main/resources/static/images` ja nimeä se `Files.svg`.

Kuvan sijoittaminen staattiseen kansioon mahdollistaa sen viittaamisen käyttämällä Webserver-protokollaa, kuten teit viitatessasi CSS-tiedostoon ensimmäisessä vaiheessa, [Yksinkertaisen sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app). Sen jälkeen voit käyttää sitä sovelluksessasi HTML-elementtinä seuraavasti:

```java
private Img fileImg = new Img("ws://images/Files.svg");
```

### `AboutView`-luominen {#creating-about-view}

Kuten kahdella olemassa olevalla sovellussivulla, tietosivu on reititettävä näkymä. Luodaan `src/main/java/com/webforj/tutorial/views`-kansioon luokka nimeltä `AboutView`. Käytä sidottuna komponenttina `FlexLayout`:ia, kuten teit `AppTitle`:n kanssa.

Koska olet nimennyt luokan `AboutView`, ei ole tarvetta antaa mukautettua arvoa URL-kartoitukselle; tämä sivu renderöidään `http://localhost:8080/about` -osoitteeseen oletusarvoisesti.

Tässä on, miltä näyttää, kun käytät aiempien vaiheiden käsitteitä ja juuri luotuja komponentteja uuden näkymän luomiseksi keskitetyllä sisällöllä:

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

## `Layout`-reitinvaiheen luominen {#creating-the-layout-route}

Tässä mainitaan lyhyesti [Reititys ja yhdisteet](/docs/introduction/tutorial/routing-and-composites) -vaiheessa, mutta on olemassa kaksi [reititystyyppiä](/docs/routing/route-hierarchy/route-types). `MainView`, `FormView` ja `AboutView` ovat kaikki `View` -reitityksiä, kun taas reititystyyppi, jota käytät luodaksesi sovelluksen sivuvalikon, on `Layout` -reititys.

Layout-reititykset kääriävät lapsinäkymiä ja sallivat tiettyjen käyttöliittymän osien pysyä voimassa näkymien yli, kuten sivuvalikko. Luodaan `src/main/java/com/webforj/tutorial/layouts`-kansioon luokka nimeltä `MainLayout`.

### Reititysreitit {#route-outlets}

Kuten näkymäreitityksissä, `MainLayout` tarvitsee `@Route` -annotaation. Koska sillä on `Layout` -päätteet, eikä layout-reititykset vaikuta URL-osoitteeseen, tämä annotaatio ei tarvitse lainkaan argumentteja.

```java title="MainLayout.java" {1}
@Route
public class MainLayout {

  public MainLayout() {

  }
}
```

Sovellus tietää, mitkä näkymät renderöidään `MainLayout`-sisällä, ilmoittamalla layout-luokan [reititysreitiksi](/docs/routing/route-hierarchy/route-outlets) jokaisessa näkymässä. Aiempien vaiheiden `@Route`-annotaatioissa on vain asetettu `value`-ominaisuus, joten nyt sinun täytyy nimenomaisesti ilmoittaa, mitä `value`- ja `outlet`-ominaisuudet ovat näkyluokille.

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
Tämä on viimeinen muutos vaadittu `FormView` ja `AboutView` -vaiheessa, joten muista päivittää `@Route`-annotaatio näille näkymille ennen kuin suoritat sovelluksesi.
:::

## `AppLayout`-komponentin käyttäminen {#using-the-app-layout-component}

Nyt, kun sovelluksesi renderöi näkymiä `MainLayout`:n sisällä, voit valita, minne komponentit renderöidään. Valitsemalla `AppLayout`:n sidotuksi komponentiksi `MainLayout`:lle voit tallentaa näkymät oletusarvoisesti pääsisältöalueelle, kunhan sinulla on erilaisia alueita, joihin voit lisätä elementtejä yläosaan ja sivuvalikkoon.

### Paikat {#slots}

Monissa webforJ-konteissa `add()`-menetelmien käyttö lisää käyttöliittymäkomponentteja pääsisältöalueelle. `AppLayout`-komponentissa on useita alueita käyttöliittymäkomponenttien lisäämiseen, jokainen erikseen. 
Merkitsemällä `MainLayout` layout-reititykseksi ja asettamalla sen sidotuksi komponentiksi `AppLayout`, näkymät renderöityvät automaattisesti pääsisältöpaikkaan.

Tässä vaiheessa käytät `drawer-title`- ja `drawer`-paikkoja luodaksesi sivuvalikon, ja `header`-paikka näyttää, millä sivulla käyttäjä on ja sisältää päävalikkopainikkeen.

### Sivuvalikon tekeminen {#making-a-side-menu}

Kun laitteessa on riittävästi näyttötilaa, `AppLayout`-komponentti näyttää laatikon. Tähän lisäät `AppTitle`:n uudelleen ja elementtejä, jotka sallivat käyttäjien navigoida sovelluksessa.

Oletusarvoisesti `AppLayout` ei näytä laatikon otsikkoa, mutta käyttämällä `setDrawerHeaderVisible()` -menetelmää voit näyttää elementtejä, jotka sijaitsevat `drawer-title`-paikassa, joka on `AppTitle` sen alaotsikon kanssa.

```java
private AppLayout appLayout = new AppLayout();

// Näytä laatikon otsikko
appLayout.setDrawerHeaderVisible(true);

// Lisää AppTitle laatikon otsikkoon alaotsikon kanssa
appLayout.addToDrawerTitle(new AppTitle(true));
```

`drawer`-paikan tulee sitten sisältää komponentit, jotka sallivat käyttäjien navigoida sovelluksessa. Käyttämällä [`AppNav`](/docs/components/appnav) -komponenttia, voit helposti luoda uusia navigointivaihtoehtoja. Jokaiselle linkille sinun tarvitsee vain luoda `AppNavItem`.
`AppNavItem`-komponentit tässä oppaassa käyttävät kolmea parametria:

- Linkin etiketti
- Kohdevuosi
- Valinnainen [`Icon`](/docs/components/icon) -komponentti, käyttämällä kuvia [Tablerista](https://tabler.io/icons)

Kaikkien laatikon asetusten kokoaminen `MainLayout`-luokassa näyttää seuraavalta:

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

    appNav.addItem(new AppNavItem("Koontinäyttö", MainView.class,
        TablerIcon.create("archive")));
    appNav.addItem(new AppNavItem("Tietoa", AboutView.class,
        TablerIcon.create("info-circle")));
    self.addToDrawer(appNav);
  }
```

### Ylätunnisteen tekeminen {#making-a-header}

`header`-paikan tulee sisältää kaksi elementtiä: vaihtokytkin sivuvalikon näyttämiselle tai piilottamiselle ja tapa näyttää kehysotsikko. Molemmat näistä elementeistä ovat [Toolbar](/docs/components/toolbar) -komponentin sisällä, joka on toinen tapa järjestää komponentteja.

Voit sisällyttää vaihtokytkimen `AppLayout` -laatikolle `AppDrawerToggle` -komponentilla. Tämä komponentti on jo tyylitelty yleisesti käytetyllä ikonilla piilotetuille valikkovaihtoehdoille, ja kohdistaa laatikon avattavaksi ja suljettavaksi.

```java
// Luo kontainerikomponentit
private AppLayout appLayout = new AppLayout();
private Toolbar toolbar = new Toolbar();

// Lisää Toolbar AppLayout -ylätunnisteeseen
appLayout.addToHeader(toolbar);

// Lisää AppDrawerToggle työkalupalkkiin
toolbar.addToStart(new AppDrawerToggle());
```

Ylätunniste voi myös näyttää kehysotsikon käyttämällä navigointitapahtumaa, jotta saadaan yksityiskohtia saapuvasta komponentista, ja tapahtumakuuntelijaa poistamaan rekisteröinnin estääkseen muistivuotot.

```java
// Luo H1-elementti ja navigointirekisteröinti
private H1 title = new H1("");
private ListenerRegistration<NavigateEvent> navigateRegistration;

// Rekisteröi tapahtuma navigointihetkellä
navigateRegistration = Router.getCurrent().onNavigate(this::onNavigate);

// Poista kuuntelijat ennen MainLayoutin tuhoamista
@Override
protected void onDidDestroy() {
  if (navigateRegistration != null) {
    navigateRegistration.remove();
  }
}

// Hae kehysotsikko saapuvasta näkymän luokasta
private void onNavigate(NavigateEvent ev) {
  Component component = ev.getContext().getComponent();
  if (component != null) {
    FrameTitle frameTitle = component.getClass().getAnnotation(FrameTitle.class);
    title.setText(frameTitle != null ? frameTitle.value() : "");
  }
}
```

## Valmis `MainLayout` {#completed-mainlayout}

Tässä on `MainLayout`, jossa on luodut sisällöt laatikkoon ja ylätunnisteeseen `AppLayout`:ssa:

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

      appNav.addItem(new AppNavItem("Koontinäyttö", MainView.class,
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

Kuten aiemmin mainittiin, ainoa muutos `FormView`:lle oli `@Route` -annotaatio.

```java
@Route(value = "customer/:id?<[0-9]+>", outlet = MainLayout.class)
```

## `MainView`:n päivittäminen {#updating-main-view}

`MainView`:lle muutat sidotun komponentin `Div`:stä `FlexLayout`:ksi. Tämä mahdollistaa taulukon keskittämisen, samalla kun siirrät tiettyjä komponentteja asetteluun. Käyttämällä `setItemAlignment()`-menetelmää voit valita komponentin asettelusta ja siirtää sen, jotta voit pitää taulukon keskitettynä samalla, kun ankkuroit asiakasta lisäävän painikkeen asettelun oikeaan yläkulmaan.

```java
// Muuta sidottu komponentti FlexLayout:ksi
private FlexLayout self = getBoundComponent();

// Kohdista painike poikittaisakselin päähän
self.setItemAlignment(FlexAlignment.END, addCustomer);
```

Toinen parannus, jonka voit tässä tehdä, on taulukon leveys. Kiinteän leveyden sijaan voit asettaa sen vastaamaan sen vanhempaa komponenttia, `FlexLayout`:ia. Sitten tuon `FlexLayout`:n on oltava enimmäisleveys, jotta se ei veny liikaa suurilla näytöillä.

```java
private FlexLayout self = getBoundComponent();
private Table<Customer> table = new Table<>();

self.setSize("100%", "100%");
self.setMaxWidth(2000);

table.setSize("100%", "294px");
```

Kun yhdistetään nämä yhdessä ja tehdään toinen menetelmä saadaksesi `FlexLayout`:n keskitettynä kuten aiemmissa, `MainView` näyttää seuraavalta korostetuin muutoksin:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java">
{`@Route(value = "/", outlet = MainLayout.class)
  @FrameTitle("Asiakastaulukko")
  // highlight-next-line
  public class MainView extends Composite<FlexLayout> {
    private final CustomerService customerService;
    // highlight-next-line
    private FlexLayout self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Lisää asiakasta", ButtonTheme.PRIMARY,
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
