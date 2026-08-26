---
title: Reititys ja Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: f32a8552d85a9c85b565fe6f026c93bb
---
Tähän asti tämä opetusohjelma on ollut vain yhden sivun sovellus. Tämä vaihe muuttaa sen. Siirrä käyttöliittymä, jonka loit [Työskentely datan kanssa](/docs/introduction/tutorial/working-with-data), omalle sivulleen ja luo toinen sivu uusien asiakkaiden lisäämiselle. Sitten yhdistät nämä sivut, jotta sovelluksesi pystyy navigoimaan niiden välillä soveltamalla näitä käsitteitä:

- [Reititys](/docs/routing/overview)
- [Komponenttien yhdistäminen](/docs/building-ui/composing-components)
- [`ColumnsLayout`](/docs/components/columns-layout) -komponentti

Tämän vaiheen suorittaminen luo version [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Lisää video tähän -->

## Sovelluksen suorittaminen {#running-the-app}

Sovelluksesi kehittämisen aikana voit käyttää [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry yllätystasolle, joka sisältää `pom.xml` -tiedoston; tämä on `3-routing-and-composites`, jos seuraat GitHubin versiota.

2. Suorita seuraava Maven-komento Spring Boot -sovelluksen suorittamiseksi paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Reititettävät sovellukset {#routable-apps}

Aikaisemmin sovelluksesi oli yhden toiminnon omaava: olemassa olevan asiakastiedon taulukon näyttäminen. Tässä vaiheessa sovelluksesi pystyy myös muokkaamaan asiakastietoja lisäämällä uusia asiakkaita. Käyttöliittymien erottaminen näyttöä ja muokkaamista varten on hyödyllistä pitkän aikavälin ylläpidon ja testauksen kannalta, joten lisäät tämän ominaisuuden erillisenä sivuna. Teet sovelluksestasi [reititettävän](/docs/routing/overview), jotta webforJ voi käyttää ja ladata kahta käyttöliittymää erikseen.

Reititettävä sovellus renderöi käyttöliittymän URL-osoitteen perusteella. Luokan, joka laajentaa `App` -luokkaa, merkitseminen [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) -annotaatiolla mahdollistaa reitityksen, ja `packages`-elementti kertoo webforJ:lle, mitkä paketit sisältävät käyttöliittymäkomponentteja.

Kun lisäät `@Routify` -annotaation `Application`-luokkaan, poista `run()`-metodi. Siirrä komponentit tuosta metodista luokkaan, jonka teet `com.webforj.tutorial.views` -pakettiin. Päivitetyn `Application.java` -tiedoston tulisi näyttää tältä:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@BundleEntry("css/card.css")
@AppTheme("system")

// Lisätty @Routify -annotaatio
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Poistettu ylikirjoitettu App.run() -metodi

}
```

:::tip Globaalit CSS-tyylit
`@BundleEntry` -annotaation pitäminen `Application`-luokassa lisää CSS-tiedoston sovellustason frontend-pakettiin, joten tyylit pysyvät käytettävissä reititetyissä näkymissä.
:::

### Reittien luominen {#creating-routes}

`@Routify` -annotaation lisääminen tekee sovelluksestasi reititettävän. Kun se on reititettävä, sovellus etsii `com.webforj.tutorial.views` -paketista reittejä. Sinun tulee luoda reitit käyttöliittymistäsi ja myös määrittää niiden [Reittityypit](/docs/routing/route-hierarchy/route-types). Reittityyppi määrittää, kuinka käyttöliittymäsisältö kartoitetaan URL-osoitteeseen.

Ensimmäinen reittityyppi on `View`. Tällaiset reitit kartoitetaan suoraan tiettyyn URL-segmenttiin sovelluksessasi. Taulukon ja uuden asiakaslomakkeen käyttöliittymät ovat molemmat `View` -reitityyppejä.

Toinen reittityyppi on `Layout`, joka sisältää käyttöliittymän, joka näkyy useilla sivuilla, kuten otsikko tai sivupalkki. Layout-reitit myös ympäröivät lapsinäkymiä ilman, että niillä on vaikutusta URL-osoitteeseen.

Luokan reittityypin määrittämiseksi lisää reittityyppi luokan nimen loppuun liitteenä. Esimerkiksi `MainView` on `View` -reitityyppi.

Pitääksesi sovelluksen kaksi toimintoa erillään, sovelluksesi tarvitsee kartoittaa käyttöliittymät kahteen ainutlaatuiseen `View` -reittiin: yksi taulukolle ja yksi asiakaslomakkeelle. Luo `/src/main/java/com/webforj/tutorial/views` -hakemistoon kaksi luokkaa, joilla on `View` -liite:

- **`MainView`**: Tässä näkymässä on `Application`-luokassa aiemmin ollut `Table`.
- **`FormView`**: Tässä näkymässä on lomake uusien asiakkaiden lisäämiseksi.

### URL-osoitteiden kartoittaminen komponentteihin {#mapping-urls-to-components}

Sovelluksesi on reititettävä ja tietää, että sen on etsittävä kaksi `View` -reittiä, `MainView` ja `FormView`, mutta sillä ei ole erityistä URL-osoitetta niiden lataamista varten. Voit käyttää `@Route` -annotaatiota näkyluokassa, jotta voit kertoa webforJ:lle, mihin sen tulisi ladata sen tietyn URL-segmentin perusteella. Esimerkiksi käyttämällä `@Route("about")` näkymässä kartoitetaan luokka paikallisesti osoitteeseen `http://localhost:8080/about`.

Nimi tarkoittaa, että `MainView` on luokka, jonka haluat ladata aluksi, kun sovellus käynnistyy. Saavuttaaksesi tämän, lisää `@Route` -annotaatio, joka kartoittaa `MainView`:n sovelluksesi juurisivulle:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

`FormView`:lle, kartoita näkymä niin, että se ladataan, kun käyttäjä siirtyy osoitteeseen `http://localhost:8080/customer`:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Oletuskäyttäytyminen
Jos et nimenomaan määritä arvoa `@Route` -annotaatiolle, URL-segmentti on luokan nimi muunnettuna pieniksi kirjaimiksi, `View` -liite poistettuna.

- `MainView` kartoitettaisiin `/main`
- `FormView` kartoitettaisiin `/form`
:::

## Yhteiset ominaisuudet {#shared-characteristics}

Lisäksi kummankin ollessa näkymäreittejä, `MainView` ja `FormView` jakavat lisäominaisuuksia. Jotkut näistä jaetuista ominaisuuksista, kuten `Composite` -komponenttien käyttö, ovat perusvaatimuksia webforJ-sovelluksille, kun taas toiset helpottavat sovelluksesi hallintaa.

### `Composite` -komponenttien käyttäminen {#using-composite-components}

Kun sovellus oli yhden sivun, säilytit komponentit `Frame` -sisällä. Jatkossa, kun sovelluksessa on useita näkymiä, sinun on käärittävä käyttöliittymäkomponentit [`Composite` -komponentteihin](/docs/building-ui/composing-components).

`Composite` -komponentit ovat kääreitä, jotka helpottavat uudelleenkäytettävien komponenttien luomista. Luodaksesi `Composite` -komponentin, laajenna `Composite` -luokkaa määritellyllä sidotulla komponentilla, joka toimii luokan perustana, esim. `Composite<FlexLayout>`.

Tässä opetusohjelmassa käytetään `Div` -elementtejä sidottuina komponenteina, mutta ne voivat olla mitä tahansa komponentteja, kuten [`FlexLayout`](/docs/components/flex-layout) tai [`AppLayout`](/docs/components/app-layout). Käyttämällä `getBoundComponent()` -metodia voit viitata sidottuun komponenttiin ja päästä sen metodeihin käsiksi. Tämä mahdollistaa koon asettamisen, CSS-luokan nimen lisäämisen, lisättävien komponenttien asettamisen `Composite` -komponenttiin ja komponenttiperusteisten metodoiden käyttämisen.

Sekä `MainView` että `FormView` laajentavat `Composite` -komponenttia `Div`:lla sidottuna komponenttina. Viittaa sitten siihen sidottuun komponenttiin, jotta voit lisätä käyttöliittymät myöhemmin. Molemmat näkymät tulisi näyttää seuraavan kaltaisilta:

```java
// Laajentaa Compositea sidotulla komponentilla
public class MainView extends Composite<Div> {

  // Pääsy sidottuun komponenttiin
  private Div self = getBoundComponent();

  // Luo komponentin käyttöliittymä
  private Button submit = new Button("Submit");

  public MainView() {

    // Lisää käyttöliittymäkomponentti sidottuun komponenttiin
    self.add(submit);
  }
}
```

### Kehysten otsikon asettaminen {#setting-the-frame-tile}

Kun käyttäjällä on useita välilehtiä selaimessaan, ainutlaatuinen kehysten otsikko auttaa heitä nopeasti tunnistamaan, mikä osa sovelluksesta on avoinna.

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) -annotaatio määrittää, mitä selain otsikossa tai sivun välilehdessä näkyy. Lisää molemmille näkymille kehysten otsikko käyttämällä `@FrameTitle` -annotaatiota:

<Tabs>
  <TabItem value="MainView" label="MainView">
  ```java title="MainView.java" {2}
  @Route("/")
  @FrameTitle("Asiakastaulukko")
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
  @FrameTitle("Asiakaslomake")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### Yhteiset CSS-tyylit {#shared-css}

Sidottua komponenttia, jota voit viitata `MainView` ja `FormView`, voit tyylitellä CSS:llä. Voit käyttää ensimmäisen vaiheen CSS:ää, [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), antaaksesi molemmille näkymille samanlaiset käyttöliittymäkontekstityylit. Lisää CSS-luokka `card` sidottuun komponenttiin kummassakin näkymässä:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Asiakastaulukko")
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
    @FrameTitle("Asiakaslomake")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### `CustomerService` -palvelun käyttäminen {#using-customerservice}

Viimeinen jaettu piirre näkymille on `CustomerService` -luokan käyttö. `Table` `MainView`:ssä näyttää jokaisen asiakkaan, kun taas `FormView` lisää uusia asiakkaita. Koska kummatkin näkymät ovat vuorovaikutuksessa asiakastietojen kanssa, niiden on pääsy sovelluksen liiketoimintalogiikkaan.

Näkymät saavat pääsyn Spring-palvelun kautta, joka luotiin [Työskentely datan kanssa](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Käyttääksesi Spring-palvelua kummassakin näkymässä, tee `CustomerService` konstruktorin parametriksi:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Asiakastaulukko")
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
    @FrameTitle("Asiakaslomake")
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

## `MainView` -luokan luominen {#creating-mainview}

Sovelluksesi reititettäväksi, antaaksesi näkymille `Composite` -komponenttikääreet ja sisällyttääksesi `CustomerService`, olet valmis rakentamaan jokaiselle näkymälle ainutlaatuiset käyttöliittymät. Kuten aiemmin mainittiin, `MainView` sisältää käyttöliittymäkomponentit, jotka alun perin olivat `Application`-luokassa. Tälle luokalle tarvitaan myös tapa navigoida `FormView`:hen.

### `Table`-metodien ryhmittely {#grouping-the-table-methods}

Kun siirrät komponentteja `Application`-luokasta `MainView`-luokkaan, on hyvä ajatus alkaa jakaa osia sovelluksestasi, jotta yksi mukautettu metodi voi tehdä muutoksia `Table`:aan kerralla. Koodin jakaminen nyt tekee siitä helpompaa hallita, kun sovellus muuttuu monimutkaisemmaksi.

Nyt `MainView`-konstruktori tulisi vain kutsua yhtä `buildTable()`-metodia, joka lisää sarakkeet, asettaa koon ja viittaa tietovarastoon:

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

### Navigointi `FormView`:aan {#navigating-to-formview}

Käyttäjien tarvitsee olla tapa navigoida `MainView`:sta `FormView`:hen käyttöliittymän kautta.

webforJ:ssä voit navigoida suoraan uuteen näkymään käyttämällä näkymän luokkaa. Reitittäminen luokan avulla URL-segmentin sijasta takaa, että webforJ ottaa oikean reitin ladatakseen näkymän.

Navigoidaksesi toiseen näkymään, käytä [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) -luokkaa saadaksesi nykyisen sijainnin `getCurrent()` -metodilla, ja käytä sitten `navigate()` -metodia näkymän luokka argumenttina:

```java
Router.getCurrent().navigate(FormView.class);
```

Tämä koodi ohjaa käyttäjät ohjelmallisesti uuden asiakaslomakkeen. Navigoinnin on kuitenkin yhdistettävä käyttäjän toimintaan. Antaaksesi käyttäjien lisätä uuden asiakkaan, voit muokata tai korvata `Application`-luokasta peräisin olevan infosivun painikkeen. Painikkeen sijaan, että avaat viestikehotteen, se voi navigoida `FormView`-luokkaan:

```java
private Button addCustomer = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Valmis `MainView` {#completed-mainview}

Johdanto `FormView`:lle ja taulukkometodien ryhmittely, tässä on miltä `MainView` pitäisi näyttää ennen siirtymistä `FormView`:n luomiseen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>

```java
@Route("/")
@FrameTitle("Asiakastaulukko")
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
```

</ExpandableCode>
<!-- vale on -->

## `FormView` -luokan luominen {#creating-formview}

`FormView` näyttää lomakkeen uusien asiakkaiden lisäämiseksi. Jokaiselle asiakasominaisuudelle `FormView` sisältää muokattavan komponentin, jotta käyttäjät voivat vuorovaikuttaa sen kanssa. Lisäksi siinä on painike käyttäjien tiedon lähettämiseksi ja peruutuspainike muutosten hylkäämiseksi.

### `Customer`-instanssin luominen {#creating-a-customer-instance}

Kun käyttäjä muokkaa uuden asiakkaan tietoja, muutoksia tulisi soveltaa tietovarastoon vain kun he ovat valmiita lähettämään lomakkeen. `Customer` -objektin instanssin käyttäminen on kätevä tapa muokata ja ylläpitää uutta tietoa ilman, että muokkaat suoraan tietovarastoa. Luo uusi `Customer` `FormView`:ssä käytettäväksi lomakkeessa:

```java
private Customer customer = new Customer();
```

Jotta `Customer` -instanssi olisi muokattavissa, jokaiselle ominaisuudelle, paitsi `id`:lle, tulisi liittää muokattava komponentti. Käyttäjän tekemät muutokset käyttöliittymässä tulisi heijastua `Customer` -instanssiin.

### `TextField` -komponenttien lisääminen {#adding-textfield-components}

Ensimmäiset kolme muokattavaa ominaisuutta `Customer`:issa (`firstName`, `lastName` ja `company`) ovat kaikki `String`-arvoja, ja niiden tulisi olla edustettuna yksirivisellä tekstieditorilla. [`TextField`](/docs/components/fields/textfield) -komponentit ovat loistava valinta näiden ominaisuuksien edustamiseen.

`TextField` -komponentin avulla voit lisätä etiketin ja tapahtumakuuntelijan, joka laukeaa, aina kun kentän arvo muuttuu. Jokaisen tapahtumakuuntelijan tulisi päivittää `Customer` -instanssia vastaavalla ominaisuudella.

Lisää kolme `TextField` -komponenttia, jotka päivittävät `Customer` -instanssia:

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

:::tip Jaettu nimeämiskäytäntö
Nimeämiä komponentteja samalla tavalla kuin ne ominaisuudet, joita ne edustavat `Customer` -entiteetille, helpottaa tiedon sitomista tulevassa vaiheessa, [Tietojen validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data).
:::

### `ChoiceBox` -komponentin lisääminen {#adding-a-choicebox-component}

`country` -ominaisuuden edustaminen `TextField`:llä ei olisi ihanteellista, koska ominaisuuden on oltava vain yksi viidestä enum-arvosta: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` ja `USA`.

Parempi komponentti ennalta määrätyn vaihtoehtoluettelon valitsemiseen on [`ChoiceBox`](/docs/components/lists/choicebox).

Jokainen vaihtoehto `ChoiceBox`-komponentille on edustettuna `ListItem`:inä. Jokaisella `ListItem`:illä on kaksi arvoa, `Object`avain ja `String` -teksti, joka näytetään käyttöliittymässä. Kahden arvon avulla jokaiselle vaihtoehdolle voit käsitellä `Object` sisäisesti samalla kun esität käyttäjille luettavamman vaihtoehdon käyttöliittymässä.

Esimerkiksi `Object`-avain voisi olla kansainvälinen standardikirjanumero (ISBN), kun taas `String`-teksti on kirjan otsikko, joka on helpompi luettavissa.

```java
new ListItem(isbn, bookTitle);
```

Kuitenkin tämä sovellus käsittelee maanimiä, ei kirjoja. Jokaisen `ListItem`:n tapauksessa haluat, että `Object` on `Customer.Country` enum, kun taas teksti voi olla sen `String` -edustus.

Lisätäksesi kaikki `country` -vaihtoehdot `ChoiceBox`:iin, voit käyttää iteraattoria luodaksesi `ListItem` jokaista `Customer.Country` enumia varten ja laittaa ne `ArrayList<ListItem>`:iin. Sitten voit lisätä tämän `ArrayList<ListItem>`:n `ChoiceBox`-komponenttiin:

```java
// Luo ChoiceBox-komponentti
private ChoiceBox country = new ChoiceBox("Maa");

// Luo ArrayList ListItem-objekteista
ArrayList<ListItem> listCountries = new ArrayList<>();

// Lisää iteraattori, joka luo ListItem jokaiselle Customer.Country -vaihtoehdolle
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Lisää täytetty ArrayList ChoiceBox:iin
country.insert(listCountries);

// Tee ensimmäisestä ListItem:stä oletus, kun lomake latautuu
country.selectIndex(0);
```

Kun käyttäjä valitsee vaihtoehdon `ChoiceBox`:issä, `Customer` -instanssin tulisi päivittyä valitun kohteen avaimella, joka on `Customer.Country` -arvo.

```java
private ChoiceBox country = new ChoiceBox("Maa",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Pitääkseen koodin siistinä, iteraattorin, joka luo `ArrayList<ListItem>` ja lisää sen `ChoiceBox`:iin, tulisi olla erillisessä metodissa. Kun olet lisännyt `ChoiceBox`:n, joka sallii käyttäjän valita `country` -ominaisuuden, `FormView` tulisi näyttää tältä:

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

### `Button` -komponenttien lisääminen {#adding-button-components}

Kun käytetään uutta asiakaslomaketta, käyttäjien pitäisi pystyä joko tallentamaan tai hylkäämään muutokset. Luo kaksi `Button` -komponenttia tämän ominaisuuden toteuttamiseksi:

```java
private Button submit = new Button("Lähetä");
private Button cancel = new Button("Peruuta");
```

Sekä lähetä- että peruutuspainikkeen tulisi palauttaa käyttäjä `MainView`:lle. Tämä sallii käyttäjän nähdä välittömästi toimintansa tulokset, olivatpa he uudella asiakkaalla taulukossa tai ei mitään muutoksia. Koska useat syötteet `FormView`:ssa vievät käyttäjiä `MainView`:lle, navigoinnin tulisi olla palautettavissa olevaan metodiin:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Peruuta-painike**

Lomakkeen hylkääminen ei vaadi mitään lisäkoodia tapahtumalle, joka siirtää takaisin `MainView`:lle. Koska peruuttaminen ei ole ensisijainen toiminto, asettaminen painikkeen teeman kehykseen antaa enemmän näkyvyyttä lähetyspainikkeelle. [Teemat](/docs/components/button#themes) -osio painikkekomponentin sivulle luettelee kaikki saatavilla olevat teemat.

```java
private Button cancel = new Button("Peruuta", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Lähetä-painike**

Kun käyttäjä napsauttaa lähetä-painiketta, `Customer` -instanssissa olevia arvoja tulisi käyttää uuden tietueen luomiseen tietovarastoon.

Käyttämällä `CustomerService`:a voit ottaa `Customer` -instanssin ja päivittää H2-tietokannan. Kun tämä tapahtuu, uusi ja ainutlaatuinen `id` myönnetään tälle `Customer`:lle. Tietovaraston päivittämisen jälkeen, voit ohjata käyttäjät `MainView`:lle, jossa he näkevät uuden asiakkaan taulukossa.

```java
private Button submit = new Button("Lähetä", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### `ColumnsLayout` -käyttö {#using-a-columnslayout}

Lisäämällä `TextField`, `ChoiceBox` ja `Button` komponentteja, sinulla on nyt kaikki lomakkeen vuorovaikutteiset osat. Viimeinen parannus `FormView`-luokassa tässä vaiheessa on visuaalisesti järjestää kuusi komponenttia.

Tämä lomake voi käyttää [`ColumnsLayout`](/docs/components/columns-layout) -komponenttia erottamaan komponentit kahteen sarakkeeseen ilman, että sinun tarvitsee asettaa yksittäisten interaktiivisten komponenttien leveyksiä. Luodaksesi `ColumnsLayout`, määritä jokainen komponentti, joka tulisi olla asettamisen sisällä:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Asettaaksesi `ColumnsLayout`:lle sarakkeiden määrän, käytä `List` -objekteja `Breakpoint`. Jokainen `Breakpoint` kertoo `ColumnsLayout`:lle minimileveyden, jonka sen on oltava, jotta se voi soveltaa tiettyä sarakkeiden määrää. Käytettäessä `ColumnsLayout` -komponenttia, voit tehdä lomakkeen, jossa on kaksi saraketta, mutta vain jos näyttö on riittävän leveä näyttämään kaksi saraketta. Pienemmillä näytöillä komponentit näytetään yhdessä sarakkeessa.

[Breakpoints](/docs/components/columns-layout#breakpoints) -osion artikkelissa kerrotaan enemmän katkoksista.

Pitääkseen koodin ylläpidettävänä, aseta katkoskohdat erillisessä metodissa. Tässä metodissa voit myös hallita komponenttien välistä vaakasuoraa ja pystysuoraa etäisyyttä `ColumnsLayout`:ssä `setSpacing()`-metodin avulla.

```java
private void setColumnsLayout() {

  // Kahden sarakkeen lukumäärä, jos se on leveämpi kuin 600 px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Lisää katkoskohteet
  layout.setBreakpoints(breakpoints);

  // Aseta komponenttien välinen etäisyys käyttämällä DWC CSS -muuttujaa
  layout.setSpacing("var(--dwc-space-l)");
}
```

Lopuksi voit lisätä juuri luodun `ColumnsLayout`:n `FormView`-luokan sidottuun komponenttiin, samalla asettaen myös maksimi leveyden ja lisäämällä aiemman luokan nimen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Valmis `FormView` {#completed-formview}

Lisättyäsi `Customer`-instanssin, vuorovaikutteiset komponentit ja `ColumnsLayout`:n, `FormView`-luokkasi tulisi näyttää seuraavalta:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer")
@FrameTitle("Asiakaslomake")
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
```

</ExpandableCode>
<!-- vale on -->

## Seuraava vaihe {#next-step}

Koska käyttäjät voivat nyt lisätä asiakkaita, sovelluksesi tulisi pystyä muokkaamaan olemassa olevia asiakkaita samalla lomakkeella. Seuraavassa vaiheessa, [Kuvaajat ja reittiparametrit](/docs/introduction/tutorial/observers-and-route-parameters), sallit asiakas `id`:n toimivan aloitusparametrinä `FormView`:lle niin, että se voi täyttää lomakkeen kyseisen asiakkaan tiedoilla ja antaa käyttäjille mahdollisuuden muuttaa ominaisuuksia.
