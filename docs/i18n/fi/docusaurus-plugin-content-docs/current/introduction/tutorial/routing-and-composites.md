---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: b6d14f241d64208bfcfff527691bf8e9
---
Tähän asti tämä opas on ollut vain yksisivuinen sovellus. Tämä vaihe muuttaa sen.
Siirrät [Työskentely tiedon kanssa](/docs/introduction/tutorial/working-with-data) luomasi käyttöliittymän omalle sivulle ja luot toisen sivun uusien asiakkaiden lisäämistä varten.
Sitten yhdistät nämä sivut niin, että sovelluksesi voi navigoida niiden välillä näitä käsitteitä soveltamalla:

- [Reititys](/docs/routing/overview)
- [Komponenttien yhdistäminen](/docs/building-ui/composing-components)
- [`ColumnsLayout`](/docs/components/columns-layout) komponentti

Tämän vaiheen suorittaminen luo version [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Insert video here -->

## Sovelluksen suorittaminen {#running-the-app}

Sovellustasi kehittäessäsi voit käyttää [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) vertailukohtana. Näet sovelluksen käytössä:

1. Siirry ylimpään hakemistoon, jossa `pom.xml`-tiedosto sijaitsee; tämä on `3-routing-and-composites`, jos seuraat mukana olevaa versiota GitHubissa.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Reititettävät sovellukset {#routable-apps}

Aiemmin sovelluksesi oli yhden toiminnon varassa: olemassa olevan asiakastiedon näyttäminen taulukossa.
Tässä vaiheessa sovelluksesi voi myös muokata asiakastietoja lisäämällä uusia asiakkaita.
Käyttöliittymien erottaminen näyttöä ja muokkausta varten on hyödyllistä pitkän aikavälin ylläpidolle ja testaamiselle, joten lisäät tämän ominaisuuden erilliseksi sivuksi.
Teet sovelluksestasi [reititettävän](/docs/routing/overview), jolloin webforJ voi käyttää ja ladata kahta käyttöliittymää erikseen.

Reititettävä sovellus renderöi käyttöliittymän URL-osoitteen mukaan. Luokan, joka perii `App`-luokan, merkitseminen [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) avulla mahdollistaa reitityksen, ja `packages`-elementti kertoo webforJ:lle, mitkä paketit sisältävät käyttöliittymäkomponentteja.

Kun lisäät `@Routify`-annotaation `Application`-luokkaan, poista `run()`-metodi. Siirrät komponentit kyseisestä metodista luokkaan, jonka teet `com.webforj.tutorial.views`-pakettiin. Päivitetyn `Application.java`-tiedoston tulisi näyttää tältä:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

//Lisätty @Routify-annotaatio
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Poistettu ylitse kirjoitettu App.run()-metodi

}
```

:::tip Globaali CSS
`@StyleSheet`-annotaation pitäminen `Application`-luokassa soveltaa CSS:ää globaalisti.
:::

### Reittien luominen {#creating-routes}

`@Routify`-annotaation lisääminen tekee sovelluksestasi reititettävän. Kun se on reititettävä, sovellus etsii reittejä `com.webforj.tutorial.views`-paketista.
Sinun on luotava reitit käyttöliittymille ja määritettävä myös niiden [Reittityypit](/docs/routing/route-hierarchy/route-types). Reittityyppi määrittää, miten käyttöliittymän sisältö kartoitetaan URL-osoitteeseen.

Ensimmäinen reittityyppi on `View`. Tämäntyyppiset reitit kartoittavat suoraan tiettyyn URL-osaan sovelluksessasi. Taulukon käyttöliittymät ja uusi asiakaslomake ovat molemmat `View`-reittejä.

Toinen reittityyppi on `Layout`, joka sisältää käyttöliittymää, joka näkyy useilla sivuilla, kuten ylätunniste tai sivupalkki. Layout-reitit kehystävät myös lapsinäkymiä ilman, että ne vaikuttavat URL-osoitteeseen.

Luokan reittityypin määrittämiseksi lisää reittityyppi luokan nimen loppuun liitteenä.
Esimerkiksi `MainView` on `View`-reitityyppi.

Pitääksesi sovelluksen kaksi toimintoa erillään, sovelluksesi tarvitsee kartoittaa käyttöliittymät kahteen ainutlaatuiseen `View`-reittiin: yksi taulukolle ja toinen asiakaslomakkeelle. Luodaan `/src/main/java/com/webforj/tutorial/views`-hakemistoon kaksi luokkaa, joilla on `View`-liite:

- **`MainView`**: Tässä näkymässä on `Table`, joka oli aikaisemmin `Application`-luokassa.
- **`FormView`**: Tässä näkymässä on lomake uusien asiakkaiden lisäämiseksi.

### URL-osoitteiden kartoittaminen komponentteihin {#mapping-urls-to-components}

Sovelluksesi on reititettävä ja tietää etsiä kahta `View`-reittiä, `MainView` ja `FormView`, mutta sillä ei ole määritettyä URL-osoitetta niiden lataamista varten. Käyttämällä `@Route`-annotaatiota näkymäluokassa voit kertoa webforJ:lle, mistä se lataa sen annetun URL-osan perusteella. Esimerkiksi, käyttämällä `@Route("about")` -annotaatiota paikallisesti yhdistetään luokka osoitteeseen `http://localhost:8080/about`.

Nimen mukaisesti `MainView` on luokka, jonka haluat ladata ensin, kun sovellus alkaa. Saadaksesi tämän aikaan, lisää `@Route` -annotaatio, joka yhdistää `MainView` sovelluksesi juuriosoitteeseen:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

`FormView`-näkymälle määritä se niin, että se lataa käyttäjän siirtyessä osoitteeseen `http://localhost:8080/customer`:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Oletuskäyttäytyminen
Jos et nimenomaisesti määritä arvoa `@Route`-annotaatiolle, URL-osa on luokan nimi, joka on muunnettu pieniksi kirjaimiksi, ja `View`-liite poistettu.

- `MainView` kartoittuu `/main`
- `FormView` kartoittuu `/form`
:::

## Yhteiset ominaisuudet {#shared-characteristics}

Lisäksi, kun molemmat ovat näkymäreittejä, `MainView` ja `FormView` jakavat lisäominaisuuksia. Jotkut näistä jaetuista ominaisuuksista, kuten `Composite`-komponenttien käyttö, ovat perustavanlaatuisia webforJ-sovellusten käyttämiselle, kun taas muut helpottavat sovelluksesi hallintaa.

### `Composite`-komponenttien käyttäminen {#using-composite-components}

Kun sovellus oli yksisivuinen, säilytit komponentit `Frame`-sisällä. Jatkossa, monivisioisessa sovelluksessa, sinun tulee kääriä nämä käyttöliittymäkomponentit [`Composite`-komponentteihin](/docs/building-ui/composing-components).

`Composite`-komponentit ovat kehystimiä, jotka helpottavat uudelleenkäytettävien komponenttien luomista.
Luodaksesi `Composite`-komponentin, laajenna `Composite`-luokkaa määritellyllä sidotulla komponentilla, joka toimii luokan perustana, esim., `Composite<FlexLayout>`.

Tässä oppaassa käytetään `Div`-elementtejä sidottuina komponentteina, mutta ne voivat olla mitä tahansa komponentteja, kuten [`FlexLayout`](/docs/components/flex-layout) tai [`AppLayout`](/docs/components/app-layout). Käyttämällä `getBoundComponent()`-metodia voit viitata sidottuun komponenttiin ja saada pääsyn sen metodeihin. Tämä sallii sinun asettaa koon, lisätä CSS-luokan nimen, lisätä komponentteja, joita haluat näyttää `Composite`-komponentissa, ja käyttää komponenttikohtaisia metodeja.

`MainView`- ja `FormView`-näkymille laajenna `Composite` `Div`-sidotun komponentin kanssa. Viittaa sitten siihen sidottuun komponenttiin, jotta voit lisätä käyttöliittymiä myöhemmin. Molempien näkymien tulisi näyttää jokseenkin seuraavalta struktuurilta:

```java
// Laajenna Compositea sidotulla komponentilla
public class MainView extends Composite<Div> {

  // Viittaa sidottuun komponenttiin
  private Div self = getBoundComponent();

  // Luo komponentin käyttöliittymä
  private Button submit = new Button("Submit");

  public MainView() {

    // Lisää käyttöliittymäkomponentti sidottuun komponenttiin
    self.add(submit);
  }
}
```

### Kehyksen otsikon asettaminen {#setting-the-frame-tile}

Kun käyttäjällä on useita välilehtiä selaimessaan, ainutlaatuinen kehyksen otsikko auttaa heitä nopeammin tunnistamaan, mikä osa sovelluksesta on avattu.

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) -annotaatio määrittelee, mitä näkyy selaimen otsikossa tai sivun välilehdessä. Lisää molemmille näkymille kehyksen otsikko käyttämällä `@FrameTitle` -annotaatiota:

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Asiakkaat Taulukko")
  public class MainView extends Composite<Div> {

    private Div self = getBoundComponent();

    public MainView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
  ```java title="FormView.java" {2}
  @Route("customer")
  @FrameTitle("Asiakkaan Lomake")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### Yhteinen CSS {#shared-css}

Kun sinulla on sidottu komponentti, johon voit viitata `MainView`- ja `FormView`-näkymissä, voit tyylitellä sen CSS:llä.
Voit käyttää ensimmäisen vaiheen CSS:ää, [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), antaaksesi molemmille näkymille identtiset käyttöliittymäkontin tyylit.
Lisää CSS-luokka `card` sidottuun komponenttiin kummassakin näkymässä:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Asiakkaat Taulukko")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {9} title="FormView.java"
    @Route("customer")
    @FrameTitle("Asiakkaan Lomake")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### `CustomerService`-käytön {#using-customerservice}

Näkymille viimeinen yhteinen ominaisuus on `CustomerService`-luokan käyttö.
`Table` `MainView`-näkymässä näyttää jokaisen asiakkaan, kun taas `FormView` lisää uusia asiakkaita. Koska molemmat näkymät ovat vuorovaikutuksessa asiakastietojen kanssa, niiden on päästävä käsiksi sovelluksen liiketoimintalogiikkaan.

Näkymät saavat pääsyn Spring-palvelun kautta, joka luotiin [Työskentely tiedon kanssa](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Käyttääksesi Spring-palvelua jokaisessa näkymässä, tee `CustomerService` konstruktoriparametriksi:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Asiakkaat Taulukko")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
  <TabItem value="FormView" label="FormView">
    ```java {7-8} title="FormView.java"
    @Route("customer")
    @FrameTitle("Asiakkaan Lomake")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

## `MainView` luominen {#creating-mainview}

Kun olet tehnyt sovelluksestasi reititettävän, antanut näkymille `Composite`-komponentin kehykset ja sisällyttänyt `CustomerService`:n, voit rakentaa käyttöliittymät, jotka ovat ainutlaatuisia jokaiselle näkymälle. Kuten aiemmin mainittiin, `MainView` sisältää käyttöliittymäkomponentit, jotka alun perin olivat `Application`:ssa. Tälle luokalle tarvitaan myös tapa navigoida `FormView`:hen.

### Taulukon menetelmien ryhmittely {#grouping-the-table-methods}

Kun siirrät komponentteja `Application`-luokasta `MainView`-näkymään, on hyvä idea aloittaa sovelluksesi osittaen osia niin, että yksi mukautettu metodi voi tehdä muutoksia `Table`:lle kerralla. Kodin jäsentäminen nyt helpottaa sen hallintaa sovelluksen kasvaessa monimutkaiseksi.

Nyt `MainView`-konstruktorin tulisi kutsua vain yhtä `buildTable()`-metodia, joka lisää sarakkeet, asettaa koon ja viittaa varastoon:

```java
private void buildTable() {
  table.setSize("1000px", "294px");
  table.setMaxWidth("90vw");
  table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
  table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
  table.addColumn("company", Customer::getCompany).setLabel("Yritys");
  table.addColumn("country", Customer::getCountry).setLabel("Maa");
  table.setColumnsToAutoFit();
  table.getColumns().forEach(column -> column.setSortable(true));
  table.setRepository(customerService.getRepositoryAdapter());
}
```

### Navigointi `FormView`-näkymään {#navigating-to-formview}

Käyttäjillä on oltava tapa navigoida `MainView`-näkymästä `FormView`-näkymään käyttöliittymän avulla.

webforJ:ssä voit navigoida suoraan uuteen näkymään käyttämällä näkymän luokkaa. Reitittäminen luokan kautta URL-osan sijaan takaa, että webforJ löytää oikean polun ladatakseen näkymän.

Voit navigoida toiseen näkymään käyttämällä [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) -luokkaa saadaksesi nykyisen sijainnin `getCurrent()`-metodilla, ja sitten käytä `navigate()`-metodia näkymän luokka parametrina:

```java
Router.getCurrent().navigate(FormView.class);
```

Tämä koodi ohjaa käyttäjät ohjelmallisesti uuteen asiakaslomakkeeseen, mutta navigoinnin on oltava kytketty käyttäjän toimintaan.
Voit joko muokata tai korvata `Application`:n info-painiketta salliaksesi käyttäjien lisätä uuden asiakkaan. Painike voi navigoida `FormView`-luokkaan:

```java
private Button addCustomer = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Valmistunut `MainView` {#completed-mainview}

Kun navigointi `FormView`-näkymään ja taulukon metodit on ryhmitelty, tässä on miltä `MainView` tulisi näyttää ennen kuin siirrytään `FormView`-näkymän luomiseen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
  @FrameTitle("Asiakkaat Taulukko")
  public class MainView extends Composite<Div> {
    private final CustomerService customerService;
    private Div self = getBoundComponent();
    private Table<Customer> table = new Table<>();
    private Button addCustomer = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
        e -> Router.getCurrent().navigate(FormView.class));

    public MainView(CustomerService customerService) {
      this.customerService = customerService;
      addCustomer.setWidth(200);
      buildTable();
      self.setWidth("fit-content")
          .addClassName("card")
          .add(table, addCustomer);
    }

    private void buildTable() {
      table.setSize("1000px", "294px");
      table.setMaxWidth("90vw");
      table.addColumn("firstName", Customer::getFirstName).setLabel("Etunimi");
      table.addColumn("lastName", Customer::getLastName).setLabel("Sukunimi");
      table.addColumn("company", Customer::getCompany).setLabel("Yritys");
      table.addColumn("country", Customer::getCountry).setLabel("Maa");
      table.setColumnsToAutoFit();
      table.setColumnsToResizable(false);
      table.getColumns().forEach(column -> column.setSortable(true));
      table.setRepository(customerService.getRepositoryAdapter());
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## `FormView` luominen {#creating-formview}

`FormView` näyttää lomakkeen uusien asiakkaiden lisäämiseksi. Jokaiselle asiakasominaisuudelle `FormView` sisältää muokattavan komponentin, jonka avulla käyttäjät voivat olla vuorovaikutuksessa sen kanssa. Lisäksi siinä on nappi käyttäjien tietojen lähettämiseksi ja peruutusnappi sen hylkäämiseksi.

### `Customer`-instanssin luominen {#creating-a-customer-instance}

Kun käyttäjä muokkaa tietoja uudesta asiakkaasta, muutokset tulisi soveltaa varastoon vain, kun he ovat valmiita lähettämään lomakkeen. `Customer`-olion instanssin käyttö on kätevä tapa muokata ja ylläpitää uutta tietoa ilman, että muokataan varastoa suoraan. Luo uusi `Customer` `FormView`:ssä lomakkeelle käyttöä varten:

```java
private Customer customer = new Customer();
```

Jotta `Customer`-instanssi olisi muokattavissa, jokaiselle ominaisuudelle, paitsi `id`:lle, tulisi olla yhteys muokattavaan komponenttiin. Käyttäjän käyttöliittymässä tekemät muutokset tulisi heijastaa `Customer`-instanssissa.

### `TextField`-komponenttien lisääminen {#adding-textfield-components}

Ensimmäiset kolme muokattavaa ominaisuutta `Customer`:issa (`firstName`, `lastName` ja `company`) ovat kaikki `String`-arvoja, ja ne tulisi esittää yhdellä rivillä. [`TextField`](/docs/components/fields/textfield) -komponentit ovat loistava valinta näiden ominaisuuksien esittämiseen.

`TextField`-komponentin avulla voit lisätä etiketin ja tapahtumankuuntelijan, joka laukeaa aina, kun kentän arvo muuttuu. Jokaisen tapahtumankuuntelijan tulisi päivittää `Customer`-instanssia vastaavalla ominaisuudella.

Lisää kolme `TextField`-komponenttia, jotka päivittävät `Customer`-instanssia:

```java title="FormView.java" {6-8}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();

  private TextField firstName = new TextField("Etunimi", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Sukunimi", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Yritys", e -> customer.setCompany(e.getValue()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
  }
}
```

:::tip Yhteinen nimeämiskäytäntö
Komponenttien nimeäminen samaksi kuin ne ominaisuudet, joita ne edustavat `Customer`-oliossa, helpottaa tietojen sitomista tulevassa vaiheessa, [Tietojen validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data).
:::

### `ChoiceBox`-komponentin lisääminen {#adding-a-choicebox-component}

`country`-ominaisuuden esittämiseen `TextField`-komponentti ei olisi ihanteellinen, koska ominaisuuden on oltava yksi viidestä enum-arvosta: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY`, ja `USA`.

Parempi komponentti ennalta määritellyn vaihtoehtoluettelon valitsemiseksi on [`ChoiceBox`](/docs/components/lists/choicebox).

Jokainen vaihtoehto `ChoiceBox`-komponentille esitetään `ListItem`:na. Jokaisella `ListItem`:llä on kaksi arvoa, `Object`-avain ja `String`-teksti, joka näytetään käyttöliittymässä. Kaksi arvoa jokaiselle vaihtoehdolle mahdollistavat sen, että voit käsitellä `Object`-arvoa sisäisesti samalla, kun esität käyttöliittymässä käyttäjille luettavampia vaihtoehtoja.

Esimerkiksi, `Object`-avain voisi olla Kansainvälinen Standardikirjanumero (ISBN), kun taas `String`-teksti on kirjan otsikko, joka on käyttäjille helposti luettavissa.

```java
new ListItem(isbn, bookTitle);
```

Mutta tämä sovellus käsittelee maiden nimiä, ei kirjoja. Jokaisen `ListItem`:n osalta voit haluta `Object`:ksi `Customer.Country`-enum, kun taas tekstin voi olla sen `String`-esitys.

Lisätäksesi kaikki `country`-vaihtoehdot `ChoiceBox`-komponenttiin voit käyttää iteraattoria, joka luo `ListItem`:n jokaisesta `Customer.Country`-enmusta ja laittaa ne `ArrayList<ListItem>`-listaan. Sitten voit lisätä tuon `ArrayList<ListItem>`-listan `ChoiceBox`-komponenttiin:

```java
//Luo ChoiceBox-komponentti
private ChoiceBox country = new ChoiceBox("Maa");

//Luo ArrayList ListItem-objekteista
ArrayList<ListItem> listCountries = new ArrayList<>();

//Lisää iteraattori, joka luo ListItemin jokaiselle Customer.Country -vaihtoehdolle
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

//Lisää täytetty ArrayList ChoiceBox:iin
country.insert(listCountries);

//Aseta ensimmäinen `ListItem` oletukseksi lomakkeen latautuessa
country.selectIndex(0);
```

Kun käyttäjä valitsee vaihtoehdon `ChoiceBox`:ssa, `Customer`-instanssin tulisi päivittyä valitun kohteen avaimella, joka on `Customer.Country`-arvo.

```java
private ChoiceBox country = new ChoiceBox("Maa",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Pitäntöäsi siistinä varten iteraattori, joka luo `ArrayList<ListItem>`-listan ja lisää sen `ChoiceBox`-komponenttiin, tulisi olla erillisessä metodissa.
Kun lisäät `ChoiceBox`:n, joka mahdollistaa käyttäjän valita `country`-ominaisuuden, `FormView`-näkymän tulisi näyttää tältä:

```java title="FormView.java" {9-10,15,18-25}
public class FormView extends Composite<Div> {
  private final CustomerService customerService;
  private Customer customer = new Customer();
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("Etunimi", e -> customer.setFirstName(e.getValue()));
  private TextField lastName = new TextField("Sukunimi", e -> customer.setLastName(e.getValue()));
  private TextField company = new TextField("Yritys", e -> customer.setCompany(e.getValue()));

  private ChoiceBox country = new ChoiceBox("Maa",
      e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));

  public FormView(CustomerService customerService) {
    this.customerService = customerService;
    self.addClassName("card");
    fillCountries();
  }

  private void fillCountries() {
    ArrayList<ListItem> listCountries = new ArrayList<>();
    for (Country countryItem : Customer.Country.values()) {
      listCountries.add(new ListItem(countryItem, countryItem.toString()));
    }
    country.insert(listCountries);
    country.selectIndex(0);
  }

}
```

### `Button`-komponenttien lisääminen {#adding-button-components}

Käyttäjien tulisi voida tallentaa tai hylätä muutoksensa uuden asiakkaan lomakkeessa.
Luo kaksi `Button`-komponenttia tämän ominaisuuden toteuttamiseksi:

```java
private Button submit = new Button("Lähetä");
private Button cancel = new Button("Peruuta");
```

Sekä lähetys- että peruutuspainikkeet tulisi palata käyttäjät `MainView`:hin.
Tämä antaa käyttäjälle mahdollisuuden nähdä heti toimintansa tulokset, riippumatta siitä, näkyykö uusi asiakas taulukossa vai jääkö se ennalleen.
Koska useat syötteet `FormView`:ssä vievät käyttäjät `MainView`:hin, navigointi tulisi sijoittaa muistettavaksi metodiksi:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Peruuta-painike**

Lomakkeen muutosten hylkääminen ei vaadi mitään lisäkoodia tapahtumalle, vaan se palautuu `MainView`:iin. Koska peruutus ei ole ensisijainen toiminto, painikkeen teeman asettaminen ääriviivaksi antaa lähetys-painikkeelle enemmän painoarvoa.
`Button`-komponenttisivun [Teemat](/docs/components/button#themes) osiossa luetellaan kaikki saatavilla olevat teemat.

```java
private Button cancel = new Button("Peruuta", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Lähetä-painike**

Kun käyttäjä painaa lähetyspainiketta, `Customer`-instanssissa olevia arvoja tulisi käyttää uuden merkinnän luomiseen varastossa.

Käyttäen `CustomerService`:a, voit käyttää `Customer`-instanssia päivittääksesi H2-tietokannan. Kun tämä tapahtuu, uusi ja ainutlaatuinen `id` myönnetään tälle `Customer`:lle. Päivitettyäsi varaston voit ohjata käyttäjät `MainView`:iin, jossa he voivat nähdä uuden asiakkaan taulukossa.

```java
private Button submit = new Button("Lähetä", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### `ColumnsLayout`:n käyttäminen {#using-a-columnslayout}

Lisäämällä `TextField`, `ChoiceBox` ja `Button`-komponentit, sinulla on nyt kaikki lomakkeen vuorovaikutteiset osat. Viimeinen parannus `FormView`:lle tässä vaiheessa on järjestää kuusi komponenttia visuaalisesti.

Tämä lomake voi käyttää [`ColumnsLayout`](/docs/components/columns-layout) komponenttia, joka jakaa komponentit kahteen sarakkeeseen ilman, että sinun tarvitsee määrittää minkään vuorovaikutteisen komponentin leveyttä.
Luodaksesi `ColumnsLayout`:n, määritä jokainen komponentti, joka tulisi sijaitsemaan kehystyksessä:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Määrittääksesi `ColumnsLayout`:n sarakkeiden lukumäärän, käytä `Breakpoint`-objekteista koostuvaa `List`:aa. Jokainen `Breakpoint` kertoo `ColumnsLayout`:lle vähimmäisleveyden, jonka on oltava, jotta se voi käyttää määritettyä sarakkeiden määrää. Käyttämällä `ColumnsLayout`-komponenttia voit luoda lomakkeen, jossa on kaksi saraketta, mutta vain jos näyttö on riittävän leveä, jotta kaksi saraketta voidaan näyttää. Pienemmillä näytöillä komponentit näytetään yhdessä sarakkeessa.

[Breakpoints](/docs/components/columns-layout#breakpoints) -artikkelin osio selittää breakpointeista tarkemmin.

Pitääksesi koodin ylläpidettävänä, aseta breakpointit erillisessä metodissa. Tässä metodissa voit myös hallita komponenttien välistä vaakasuoraa ja pystysuoraa väliä `ColumnsLayout`-komponentin `setSpacing()`-metodin avulla.

```java
private void setColumnsLayout() {

  //Kaksi saraketta `ColumnsLayout`:issa, jos se on leveämpi kuin 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  //Lisää breakpoints-lista
  layout.setBreakpoints(breakpoints);

  //Aseta komponenttien väli käyttäen DWC CSS -muuttujaa
  layout.setSpacing("var(--dwc-space-l)")
}
```

Lopuksi voit lisätä juuri luodun `ColumnsLayout`:n `FormView`:n sidottuun komponenttiin samalla kun asetat maksimi-leveyden ja lisäät aikaisemmin käytetyn luokan nimen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Valmistunut `FormView` {#completed-formview}

Kun olet lisännyt `Customer`-instanssin, vuorovaikutteiset komponentit ja `ColumnsLayout`:n, `FormView`:n tulisi näyttää seuraavalta:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
  @FrameTitle("Asiakkaan Lomake")
  public class FormView extends Composite<Div> {
    private final CustomerService customerService;
    private Customer customer = new Customer();
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("Etunimi", e -> customer.setFirstName(e.getValue()));
    private TextField lastName = new TextField("Sukunimi", e -> customer.setLastName(e.getValue()));
    private TextField company = new TextField("Yritys", e -> customer.setCompany(e.getValue()));
    private ChoiceBox country = new ChoiceBox("Maa",
        e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
    private Button submit = new Button("Lähetä", ButtonTheme.PRIMARY, e -> submitCustomer());
    private Button cancel = new Button("Peruuta", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
    private ColumnsLayout layout = new ColumnsLayout(
        firstName, lastName,
        company, country,
        submit, cancel);

    public FormView(CustomerService customerService) {
      this.customerService = customerService;
      fillCountries();
      setColumnsLayout();
      self.setMaxWidth(600)
          .addClassName("card")
          .add(layout);
      submit.setStyle("margin-top", "var(--dwc-space-l)");
      cancel.setStyle("margin-top", "var(--dwc-space-l)");
    }

    private void setColumnsLayout() {
      List<Breakpoint> breakpoints = List.of(
          new Breakpoint(600, 2));
      layout.setSpacing("var(--dwc-space-l)")
          .setBreakpoints(breakpoints);
    }

    private void fillCountries() {
      ArrayList<ListItem> listCountries = new ArrayList<>();
      for (Country countryItem : Customer.Country.values()) {
        listCountries.add(new ListItem(countryItem, countryItem.toString()));
      }
      country.insert(listCountries);
      country.selectIndex(0);
    }

    private void submitCustomer() {
      customerService.createCustomer(customer);
      navigateToMain();
    }

    private void navigateToMain() {
      Router.getCurrent().navigate(MainView.class);
    }

  }
`}
</ExpandableCode>
<!-- vale on -->

## Seuraava vaihe {#next-step}

Koska käyttäjät voivat nyt lisätä asiakkaita, sovelluksesi tulisi voida muokata olemassa olevia asiakkaita käyttämällä samaa lomaketta. Seuraavassa vaiheessa, [Observerit ja reittiparametrit](/docs/introduction/tutorial/observers-and-route-parameters), sallit asiakastunnuksen `id` olla alkuperäinen parametri `FormView`:lle, jotta se voi täyttää lomakkeen kyseisen asiakkaan tiedoilla ja sallii käyttäjien muuttaa ominaisuuksia.
