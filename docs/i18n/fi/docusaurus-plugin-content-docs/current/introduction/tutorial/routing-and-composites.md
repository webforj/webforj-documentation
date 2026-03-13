---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 673861b579764a7f9b81512fc0b1e576
---
F до tähän mennessä tässä opetusohjelmassa on ollut vain yhden sivun sovellus. Tämä vaihe muuttaa sen. 
Siirrät [Työskentely tiedon kanssa](/docs/introduction/tutorial/working-with-data) luomasi käyttöliittymän omalle sivulle ja luot toisen sivun uusien asiakkaiden lisäämiseksi.
Sitten yhdistät nämä sivut siten, että sovelluksesi pystyy navigoimaan niiden välillä soveltamalla näitä käsitteitä:

- [Reititys](/docs/routing/overview)
- [Yhdistelmärakenteet](/docs/building-ui/composite-components)
- [`ColumnsLayout`](/docs/components/columns-layout) komponentti

Tämän vaiheen suorittaminen luo version [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Insert video here -->

## Sovelluksen ajaminen {#running-the-app}

Kun kehität sovellustasi, voit käyttää [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry ylimmälle hakemistolle, joka sisältää `pom.xml` -tiedoston; tämä on `3-routing-and-composites`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa ajaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen ajaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Reititettävät sovellukset {#routable-apps}

Aikaisemmin sovelluksellasi oli yksi funktio: näyttää olemassaolevien asiakastietojen taulukko. 
Tässä vaiheessa sovelluksesi pystyy myös muokkaamaan asiakastietoja lisäämällä uusia asiakkaita.
Käyttöliittymien erottaminen näyttöä ja muokkausta varten hyödyttää pitkäaikaista ylläpitoa ja testausta, joten lisäät tämän ominaisuuden erilliselle sivulle.
Teet sovelluksestasi [reititettävän](/docs/routing/overview), jotta webforJ voi käyttää ja ladata kahta käyttöliittymää erikseen.

Reititettävä sovellus näyttää käyttöliittymän URL-osoitteen perusteella. Luokka, joka laajentaa `App`-luokkaa, anotetaan [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) avulla, mikä mahdollistaa reitityksen, ja `packages`-elementti kertoo webforJ:lle, mitkä paketit sisältävät käyttöliittymäkomponentteja.

Kun lisäät `@Routify`-anotaation `Application`-luokkaan, poista `run()`-metodi. Siirrät komponentit tästä metodista luokkaan, jonka teet `com.webforj.tutorial.views` -pakettiin. Päivitetyn `Application.java` -tiedoston tulisi näyttää tältä:

```java title="Application.java" {5-6,15}
@SpringBootApplication
@StyleSheet("ws://css/card.css")
@AppTheme("system")

//Lisätty @Routify-anotaatio
@Routify(packages = "com.webforj.tutorial.views")

@AppProfile(name = "CustomerApplication", shortName = "CustomerApplication")
public class Application extends App {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

// Poistettu ylikirjoitettu App.run() -metodi

}
```

:::tip Global CSS
`@StyleSheet`-anotaation pitäminen `Application`-luokassa soveltaa sitä CSS:ää globaalisti.
:::

### Reittien luominen {#creating-routes}

`@Routify`-anotaation lisääminen tekee sovelluksestasi reititettävän. Kun se on reititettävä, sovelluksesi etsii reittejä `com.webforj.tutorial.views` -paketista. 
Sinun on luotava reitit käyttöliittymillesi ja määritettävä myös niiden [Reittityypit](/docs/routing/route-hierarchy/route-types). Reittityyppi määrittää, miten käyttöliittymäin sisältö kartoitetaan URL-osoitteeseen.

Ensimmäinen reittityyppi on `View`. Tällaiset reitit kartoittavat suoraan tiettyä URL-segmenttiä sovelluksessasi. Taulukon ja uuden asiakaslomakkeen käyttöliittymät ovat molemmat `View`-reitityyppejä.

Toinen reittityyppi on `Layout`, johon sisältyy käyttöliittymä, joka näkyy useilla sivuilla, kuten otsikko tai sivupalkki. Layout-reitit myös kääriivät lapsinäkymiä vaikuttamatta URL:iin.

Reittityypin määrittämiseksi luokan nimen loppuun lisätään reittityyppi suffiksina.
Esimerkiksi `MainView` on `View`-reitityyppi.

Sovelluksen kahden toiminnon erottamiseksi sovelluksesi tarvitsee kartoittaa käyttöliittymät kahteen ainutlaatuiseen `View`-reitityyppiin: yksi taulukolle ja yksi asiakaslomakkeelle. Luota `/src/main/java/com/webforj/tutorial/views` -hakemistoon kaksi luokkaa, joilla on `View`-suffiksi:

- **`MainView`**: Tämä näkymä sisältää `Application`-luokassa aikaisemmin olleen `Table`-komponentin.
- **`FormView`**: Tämä näkymä pitää lomakkeen uusien asiakkaiden lisäämistä varten.

### URL:ien kartoittaminen komponentteihin {#mapping-urls-to-components}

Sovelluksesi on reititettävä ja tietää etsiä kahta `View`-reittiä, `MainView` ja `FormView`, mutta sillä ei ole erityistä URL-osoitetta niiden lataamiseen. Käyttämällä `@Route`-anotaatiota näkyluokassa voit kertoa webforJ:lle, mistä se lataa sen tietyn URL-segmentin perusteella. Esimerkiksi käyttämällä `@Route("about")` näkyluokassa kartoittaa luokan paikallisesti osoitteeseen `http://localhost:8080/about`.

Nimen mukaisesti `MainView` on luokka, jonka haluat ladata ensisijaisesti sovelluksen käynnistyessä. Saavuttaaksesi tämän, lisää `@Route`-anotaatio, joka kartoittaa `MainView` sovelluksesi juuriosoitteeseen:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

`FormView`-luokalle kartoita näkymä niin, että se ladataan, kun käyttäjä menee osoitteeseen `http://localhost:8080/customer`:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Oletuskäyttäytyminen
Jos et määrittele arvoa eksplisiittisesti `@Route`-anotaatiolle, URL-segmentti on luokan nimi muunnettuna pieniksi kirjaimiksi, `View`-suffiksi poistettuna.

- `MainView` kartoitetaan `/main`
- `FormView` kartoitetaan `/form`
:::

## Yhteiset ominaisuudet {#shared-characteristics}

Molempien, `MainView` ja `FormView`, lisäksi molemmat ovat näkynä reittejä, jakavat myös muita ominaisuuksia. Jotkut näistä jaetuista ominaisuuksista, kuten `Composite`-komponenttien käyttö, ovat olennaisia webforJ-sovellusten käytössä, kun taas toiset tekevät sovelluksen hallinnoimisesta helpompaa.

### `Composite`-komponenttien käyttö {#using-composite-components}

Kun sovellus oli yhden sivun, säilytit komponentit sisällä `Frame`. Edelleen, monitahtisessa sovelluksessa sinun on pakko kääriä nämä käyttöliittymäkomponentit [`Composite`-komponentteihin](/docs/building-ui/composite-components).

`Composite`-komponentit ovat kääreitä, jotka helpottavat uudelleenkäytettävien komponenttien luomista. 
Luodaksesi `Composite`-komponentin, laajenna `Composite`-luokkaa määritellyllä sidotulla komponentilla, joka toimii luokan perustana, esim. `Composite<FlexLayout>`. 

Tässä opetusohjelmassa käytetään `Div`-elementtejä sidottuina komponenteina, mutta ne voivat olla mitä tahansa komponenttia, kuten [`FlexLayout`](/docs/components/flex-layout) tai [`AppLayout`](/docs/components/app-layout). Käyttämällä `getBoundComponent()`-metodia, voit viitata sidottuun komponenttiin ja saada käyttöön sen metodit. Tämä antaa sinun asettaa koon, lisätä CSS-luokan nimen, lisätä komponentteja, joita haluat näyttää `Composite`-komponentissa, ja käyttää komponentille ominaisia metodeja.

Molemmissa, `MainView` ja `FormView`, laajenna `Composite` `Div`:lla sidottuna komponenttina. Sitten, viittaa tuohon sidottuun komponenttiin, jotta voit lisätä käyttöliittymiä myöhemmin. Molempien näkymien tulisi näyttää samankaltaisilta seuraavan rakenteen mukaan:

```java
// Laajenna Composite sidotulla komponentilla
public class MainView extends Composite<Div> {

  // Viittaa sidottuun komponenttiin
  private Div self = getBoundComponent();

  // Luo komponentti käyttöliittymä
  private Button submit = new Button("Send");

  public MainView() {

    // Lisää käyttöliittymäkomponentti sidottuun komponenttiin
    self.add(submit);
  }
}
```

### Kehyksen otsikon asettaminen {#setting-the-frame-tile}

Kun käyttäjällä on useita välilehtiä selaimessaan, ainutlaatuinen kehysotsikko auttaa heitä nopeasti tunnistamaan minkä osan sovelluksesta he ovat avanneet.

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) -anotaatio määrittelee, mitä näkyy selaimen otsikossa tai sivun välilehdessä. Molemmille näkymille lisää kehysotsikko käyttämällä `@FrameTitle` -anotaatiota:

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
  @FrameTitle("Asiakkaat Lomake")
  public class FormView extends Composite<Div> {

    private Div self = getBoundComponent();

    public FormView(CustomerService customerService) {
    }
  }
  ```
  </TabItem>
</Tabs>

### Yhteinen CSS {#shared-css}

Kun sinulla on sidottu komponentti, johon voit viitata `MainView` ja `FormView`, voit tyylitellä sitä CSS:llä. 
Voit käyttää ensimmäisen vaiheen CSS:ää, [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), antaaksesi molemmille näkymille identtiset käyttöliittymän säiliötyylit. 
Lisää CSS-luokan nimi `card` sidottuun komponenttiin jokaisessa näkymässä:

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
    @FrameTitle("Asiakkaat Lomake")
    public class FormView extends Composite<Div> {

      private Div self = getBoundComponent();

      public FormView() {

        self.addClassName("card");
      }
    }
    ```
  </TabItem>
</Tabs>

### `CustomerService`:n käyttö {#using-customerservice}

Viimeinen jaettu ominaisuus näkymille on `CustomerService`-luokan käyttö.
`Table` sisällä `MainView` näyttää jokaisen asiakkaan, kun taas `FormView` lisää uusia asiakkaita. Koska molemmat näkymät vuorovaikuttavat asiakastietojen kanssa, niiden on päästävä sovelluksen liiketoimintalogikkaan. 

Näkymät saavat pääsyn sen kautta, mikä on luotu Spring-palveluksi [Työskentely tiedon kanssa](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Käyttääksesi Spring-palvelua jokaisessa näkymässä, tee `CustomerService` konstruktoriparametriksi:

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
    @FrameTitle("Asiakkaat Lomake")
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

Kun olet tehnyt sovelluksestasi reititettävän, antanut näkymille `Composite`-komponentin kääreen ja ottanut mukaan `CustomerService`-komponentin, olet valmis rakentamaan jokaisen näkymän ainutlaatuiset käyttöliittymät. Kuten mainittiin aikaisemmin, `MainView` sisältää käyttöliittymäkomponentit, jotka alun perin olivat `Application`-luokassa. Tälle luokalle on myös lisättävä tapa navigoida `FormView`:hen.

### Taulukon metodien ryhmittely {#grouping-the-table-methods}

Kun siirrät komponentteja `Application`-luokasta `MainView`:hen, on hyvä idea aloittaa sovelluksen osien jakaminen, jotta yksi mukautettu metodi voi tehdä muutoksia `Table`:hen kerralla. Koodin jakaminen nyt helpottaa hallintaa, kun sovellus kasvaa monimutkaiseksi.

Nyt `MainView`-konstruktorisi tulisi vain kutsua yhtä `buildTable()`-metodia, joka lisää sarakkeet, asettaa koon ja viittaa varastoon:

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

### Navigointi `FormView`{#navigating-to-formview}

Käyttäjillä tulee olla tapa navigoida `MainView`:stä `FormView`:hen käyttöliittymän avulla.

WebforJ:ssä voit navigoida suoraan uuteen näkymään käyttämällä näkymän luokkaa. Reititys luokkaa kohti URL-segmentin sijaan varmistaa, että webforJ ottaa oikean polun ladatakseen näkymän. 

Navigoidaksesi toiseen näkymään, käytä [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) -luokkaa saadaksesi nykyisen sijainnin `getCurrent()`-metodilla, ja käytä sitten `navigate()`-metodia, jonka parametrina on näkymän luokka: 

```java
Router.getCurrent().navigate(FormView.class);
```

Tämä koodi ohjaa käyttäjät ohjelmallisesti uuteen asiakaslomakkeeseen, mutta navigoinnin on oltava yhteydessä käyttäjän toimintaan.
Salliaksesi käyttäjien lisätä uusi asiakas, voit joko muuttaa tai korvata `Application`-luokasta peräisin olevaa info-nappia. Sen sijaan, että napin avaisi viestikeskustelun, nappi voi navigoida `FormView`-luokkaan:

```java
private Button addCustomer = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Valmis `MainView` {#completed-mainview}

Navigoinnin `FormView`-luokkaan ja taulukkometodien ryhmittelyn jälkeen `MainView`-luokan tulisi näyttää tältä ennen siirtymistä `FormView`-luokan luomiseen:

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

## `FormView`:n luominen {#creating-formview}

`FormView` näyttää lomakkeen uusien asiakkaiden lisäämistä varten. Jokaiselle asiakastiedolle `FormView` sisältää muokattavan komponentin, jonka kautta käyttäjät voivat vuorovaikuttaa. Lisäksi siinä on painike, jonka avulla käyttäjät voivat lähettää tiedot, ja peruutuspainike, jonka avulla he voivat hylätä ne.

### `Customer`-instanssin luominen {#creating-a-customer-instance}

Kun käyttäjä muokkaa tietoja uudelle asiakkaalle, muutokset tulisi soveltaa varastoon vain silloin, kun he ovat valmiita lähettämään lomakkeen. `Customer`-objektin instanssin käyttäminen on kätevä tapa muokata ja ylläpitää uusia tietoja ilman, että varastoa editoidaan suoraan. Luo uusi `Customer` `FormView`:ssä käytettäväksi lomakkeessa:

```java
private Customer customer = new Customer();
```

Jotta `Customer`-instanssi olisi muokattavissa, jokaisen ominaisuuden, paitsi `id`, tulisi olla yhteydessä muokattavaan komponenttiin. Käyttäjän tekemät muutokset käyttöliittymässä tulisi heijastaa `Customer`-instanssissa.

### `TextField`-komponenttien lisääminen {#adding-textfield-components}

Ensimmäiset kolme muokattavaa ominaisuutta `Customer`-instanssissa (`firstName`, `lastName` ja `company`) ovat kaikki `String`-arvoja, ja ne tulisi esittää yksirivisen tekstieditorin avulla. [`TextField`](/docs/components/fields/textfield) -komponentit ovat loistava valinta näiden ominaisuuksien esittämiseen.

`TextField`-komponentilla voit lisätä tunnisteen ja tapahtumakuuntelijan, joka aktivoituu aina, kun kentän arvo muuttuu. Jokaisen tapahtumakuuntelijan tulisi päivittää `Customer`-instanssi vastaavalla ominaisuudella.

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

:::tip Jaettu nimeämiskäytäntö
Nimeä komponentit samoin kuin ne ominaisuudet, joita ne edustavat `Customer`-entiteetissä, mikä helpottaa tietojen sitomista tulevassa vaiheessa, [Tietojen validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data).
:::

### `ChoiceBox`-komponentin lisääminen {#adding-a-choicebox-component}

`country`-ominaisuuden osalta `TextField`:n käyttö ei olisi ihanteellista, koska ominaisuus voi olla vain yksi viidestä enum-arvosta: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` ja `USA`.

Parempi komponentti ennalta määritetyn valintalistan valitsemiseksi on [`ChoiceBox`](/docs/components/lists/choicebox).

Jokainen `ChoiceBox`-komponentin vaihtoehto on edustettu `ListItem`-luokalla. Jokaisella `ListItem`-luokalla on kaksi arvoa, `Object`-avain ja `String`-teksti, joka näkyy käyttöliittymässä. Kahden arvon olemassaolo jokaiselle vaihtoehdolle mahdollistaa avaimen käsittelyn sisäisesti, samalla kun se esittää käyttäjille luettavamman vaihtoehdon käyttöliittymässä.

Esimerkiksi `Object`-avain voisi olla kansainvälinen standardikirjakoodi (ISBN), kun taas `String`-teksti on kirjan otsikko, joka on helposti ymmärrettävä.

```java
new ListItem(isbn, bookTitle);
```

Tässä sovelluksessa käytetään siis maanimityksiä, ei kirjoja. Jokaiselle `ListItem`:lle haluat, että `Object` on `Customer.Country` enum, kun taas teksti voi olla sen `String`-edustaja.

Lisätäksesi kaikki `country`-vaihtoehdot `ChoiceBox`-komponenttiin, voit käyttää iteraattoria luodaksesi `ListItem`:n jokaiselle `Customer.Country` enumille ja sijoittaa ne `ArrayList<ListItem>`-muuttujaan. Sitten voit sijoittaa sen `ArrayList<ListItem>` `ChoiceBox`-komponenttiin:

```java
// Luo ChoiceBox-komponentti
private ChoiceBox country = new ChoiceBox("Maa");

// Luo ArrayList ListItem-objekteista
ArrayList<ListItem> listCountries = new ArrayList<>();

// Lisää iteraattori, joka luo ListItemin jokaiselle Customer.Country-vaihtoehdolle
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

// Lisää täytetty ArrayList ChoiceBoxiin
country.insert(listCountries);

// Aseta ensimmäinen ListItem oletukseksi, kun lomake ladataan
country.selectIndex(0);
```

Kun käyttäjä valitsee vaihtoehdon `ChoiceBox`:issa, `Customer`-instanssin tulisi päivittyä valitun kohteen avaimen, joka on `Customer.Country` -arvo.

```java
private ChoiceBox country = new ChoiceBox("Maa",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Pitääksesi koodin siistinä, iteraattori, joka luo `ArrayList<ListItem>` ja lisää sen `ChoiceBox`-komponenttiin, tulisi olla erillisessä metodissa. 
Kun olet lisännyt `ChoiceBox`-komponentin, joka antaa käyttäjän valita `country`-ominaisuuden, `FormView`-luokan tulisi näyttää tältä:

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

Käyttäjän tulisi voida joko tallentaa tai hylätä muutoksensa käyttäessään uuden asiakkaan lomaketta.
Luo kaksi `Button`-komponenttia tämän ominaisuuden toteuttamiseksi:

```java
private Button submit = new Button("Lähetä");
private Button cancel = new Button("Peruuta");
```

Sekä lähetys- että peruutuspainikkeen tulisi palauttaa käyttäjä `MainView`:hen.
Tämä mahdollistaa käyttäjän nähdä välittömästi toimintansa tulokset, olipa kyseessä uuden asiakkaan näkyminen taulukossa tai sen pysyminen muuttumattomana. 
Koska useat syötteet `FormView`:ssä vievät käyttäjät `MainView`:hen, navigointi tulisi sijoittaa muistettavaksi metodiksi:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Peruuta-painike**

Lomakkeen muutosten hylkääminen ei vaadi muita lisäkoodin lisäksi, kuin siirtyminen `MainView`:een. Koska peruutus ei ole ensisijainen toiminto, painikkeen teeman asettaminen ääriviivaiseksi tekee lähetyspainikkeesta huomattavammaksi. 
`Button`-komponenttisivun [Teemat](/docs/components/button#themes) osiossa luetellaan kaikki saatavilla olevat teemat.


```java
private Button cancel = new Button("Peruuta", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Lähetä-painike**

Kun käyttäjä painaa lähetyspainiketta, `Customer`-instanssissa olevia arvoja tulisi käyttää luotaaksesi uusi merkintä varastoon.

Käyttämällä `CustomerService`-luokkaa voit viedä `Customer`-instanssin päivittääksesi H2-tietokannan. Kun näin tapahtuu, uusi ja ainutlaatuinen `id` osoitetaan kyseiselle `Customer`:lle. Tietovaraston päivityksen jälkeen voit ohjata käyttäjät `MainView`:ään, missä he voivat nähdä uuden asiakkaan taulukossa.

```java
private Button submit = new Button("Lähetä", ButtonTheme.PRIMARY,
    e -> submitCustomer());

//...

private void submitCustomer() {
  customerService.createCustomer(customer);
  navigateToMain();
}
```

### `ColumnsLayout`:n käyttö {#using-a-columnslayout}

Lisätessäsi `TextField`, `ChoiceBox` ja `Button` -komponentit, sinulla on nyt kaikki lomakkeen interaktiiviset osat. Viimeinen parannus `FormView`:ssä tässä vaiheessa on visuaalisesti järjestää kuusi komponenttia.

Tämä lomake voi käyttää [`ColumnsLayout`](/docs/components/columns-layout) -komponenttia erottamaan komponentit kahteen sarakkeeseen ilman tarvetta asettaa minkään interaktiivisen komponentin leveyttä.
Luo `ColumnsLayout` määrittelemällä jokainen komponentti, joka tulee olemaan asennettuna layoutiin:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Asettaaksesi sarakkeiden määrän `ColumnsLayout`:ille, käytä `List<Breakpoint>`-objekteja. Jokainen `Breakpoint` kertoo `ColumnsLayout`:lle minimileveyden, jota sen on oltava, jotta se voi soveltaa määriteltyä sarakemäärää. `ColumnsLayout`:n käyttö mahdollistaa lomakkeen luomisen kahdelle sarakkeelle, mutta vain jos näyttö on tarpeeksi leveä näyttämään kaksi saraketta. Pienemmillä näytöillä komponentit näytetään yhdessä sarakkeessa.

`ColumnsLayout`-artikkelin [Katkot](/docs/components/columns-layout#breakpoints) osiossa selitetään katkokset yksityiskohtaisemmin.

Pitäksesi koodin helposti ylläpidettävänä, määritä katkokset erillisessä metodissa. Tässä metodissa voit myös hallita komponenttien välistä vaaka- ja pystysuoraa väliä `setSpacing()`-metodin avulla.

```java
private void setColumnsLayout() {

  // Mahdollistaa kaksi saraketta ColumnsLayout:ssa, jos se on leveämpi kuin 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  // Lisää Breakpoint-tyytyväiset
  layout.setBreakpoints(breakpoints);

  // Aseta komponenttien välinen etäisyys DWC CSS -muuttujan avulla
  layout.setSpacing("var(--dwc-space-l)")
}
```

Lopuksi, voit lisätä juuri luodun `ColumnsLayout`:in `FormView`:n sidottuun komponenttiin, samalla asettaen maksimi leveyden ja lisäämällä aiemmin määritellyn luokan nimen:

```java
self.setMaxWidth(600)
  .addClassName("card")
  .add(layout);
```

## Valmis `FormView` {#completed-formview}

Lisättyäsi `Customer`-instanssin, interaktiiviset komponentit ja `ColumnsLayout`:n, `FormView`:n tulisi näyttää seuraavalta:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
  @FrameTitle("Asiakkaat Lomake")
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

Koska käyttäjät voivat nyt lisätä asiakkaita, sovelluksesi pitäisi pystyä myös muokkaamaan olemassa olevia asiakkaita käyttämällä samaa lomaketta. Seuraavassa vaiheessa, [Ilmoitukset ja reittiparametrit](/docs/introduction/tutorial/observers-and-route-parameters), sallitaan asiakasta `id`:n olevan alkuperäinen parametrina `FormView`:lle, jotta se voi täyttää lomakkeen kyseisen asiakkaan tiedoilla ja sallia käyttäjien muuttaa ominaisuuksia.
