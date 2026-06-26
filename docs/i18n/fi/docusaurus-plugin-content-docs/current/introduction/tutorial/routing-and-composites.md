---
title: Routing and Composites
sidebar_position: 4
description: Step 3 - Make your app navigable.
_i18n_hash: 1ffb9e9bf7ba8d863dad3bc0c42c11d7
---
 tähän asti tämä opetusohjelma on ollut vain yhden sivun sovellus. Tämä vaihe muuttaa sen. Siirrät luomasi käyttöliittymän [Työskentely tietojen kanssa](/docs/introduction/tutorial/working-with-data) omalle sivulle ja luot toisen sivun uusien asiakkaiden lisäämiseksi. Sitten yhdistät nämä sivut niin, että sovelluksesi pystyy navigoimaan niiden välillä hyödyntämällä näitä käsitteitä:

- [Reititys](/docs/routing/overview)
- [Komponenttien kokoaminen](/docs/building-ui/composing-components)
- [`ColumnsLayout`](/docs/components/columns-layout) komponentti

Tämän vaiheen loppuun saattaminen luo version [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites).

<!-- Insert video here -->

## Sovelluksen ajaminen {#running-the-app}

Kun kehität sovellustasi, voit käyttää [3-routing-and-composites](https://github.com/webforj/webforj-tutorial/tree/main/3-routing-and-composites) vertailuna. Näet sovelluksen toiminnassa:

1. Siirry pääkansioon, joka sisältää `pom.xml`-tiedoston; tämä on `3-routing-and-composites`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa ajaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen ajaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Reititettävät sovellukset {#routable-apps}

Aikaisemmin sovelluksesi toimi yhdellä tavalla: näyttämällä olemassa olevien asiakastietojen taulukon. Tässä vaiheessa sovelluksesi pystyy myös muuttamaan asiakastietoja lisäämällä uusia asiakkaita. Näyttö- ja muokkauskäyttöliittymien erottaminen on hyödyllistä pitkäaikaiselle ylläpidolle ja testaukselle, joten lisäät tämän ominaisuuden erilliselle sivulle. Teet sovelluksestasi [reititettävän](/docs/routing/overview), jotta webforJ voi käyttää ja ladata kahta käyttöliittymää erikseen.

Reititettävä sovellus renderöi käyttöliittymän URL-osoitteen perusteella. Luokkaa, joka laajentaa `App`-luokkaa, anotetaan [`@Routify`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/Routify.html) -anotaatiolla, joka mahdollistaa reitityksen, ja `packages`-elementti kertoo webforJ:lle, mitkä paketit sisältävät käyttöliittymän komponentteja.

Kun lisäät `@Routify`-annotaation `Application`-luokkaan, poista `run()`-metodi. Siirrät sen metodin komponentit luokkaan, jonka teet `com.webforj.tutorial.views`-pakettiin. Päivitetyn `Application.java` -tiedostosi tulisi näyttää tältä:

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

// Poistettu ylikirjoitettu App.run() -metodi

}
```

:::tip Globaalit CSS-tyylit
`@StyleSheet`-annotaation pitäminen `Application`-luokassa soveltaa CSS-tyylejä globaalisti.
:::

### Reittien luominen {#creating-routes}

`@Routify`-annotaation lisääminen tekee sovelluksestasi reititettävän. Kun se on reititettävä, sovelluksesi etsii `com.webforj.tutorial.views` -paketista reittejä. Sinun on luotava reitit käyttöliittymillesi ja määritettävä myös niiden [Reittityypit](/docs/routing/route-hierarchy/route-types). Reittityyppi määrittää, kuinka kartoitat käyttöliittymän sisällön URL-osoitteeseen.

Ensimmäinen reittityyppi on `View`. Tällaiset reitit kartoittavat suoraan tiettyyn URL-osaan sovelluksessasi. Taulukon ja uuden asiakaslomakkeen käyttöliittymät ovat molemmat `View`-reitityyppejä.

Toinen reittityyppi on `Layout`, joka sisältää käyttöliittymiä, jotka näkyvät useilla sivuilla, kuten otsikko tai sivupalkki. Layout-reitit kääriytyvät myös lapsinäkymiin ilman, että ne vaikuttavat URL-osoitteeseen.

Määrittääksesi luokan reittityypin, lisää reittityyppi luokan nimen loppuun liitteenä. Esimerkiksi `MainView` on `View`-reitityyppi.

Pitääksesi sovelluksen kaksi toimintoa erillisinä, sovelluksesi on kartoitettava käyttöliittymät kahteen ainutlaatuiseen `View`-reititykseen: yksi taulukolle ja yksi asiakaslomakkeelle. Luo `/src/main/java/com/webforj/tutorial/views`-kansioon kaksi luokkaa, joilla on `View`-liite:

- **`MainView`**: Tämä näkymä sisältää `Application`-luokassa olevan taulukon.
- **`FormView`**: Tämä näkymä sisältää lomakkeen uusien asiakkaiden lisäämiseksi.

### URL-osoitteiden kartoittaminen komponentteihin {#mapping-urls-to-components}

Sovelluksesi on reititettävä ja tietää etsivänsä kahta `View`-reititystä, `MainView` ja `FormView`, mutta sillä ei ole tiettyä URL-osoitetta niiden lataamisesta. Käyttämällä `@Route`-annotaatiota näkyluokassa voit kertoa webforJ:lle, mihin ladata se annetun URL-osan perusteella. Esimerkiksi, käyttämällä `@Route("about")` -anotaatiota, näkyluokka kartoitetaan paikallisesti osoitteeseen `http://localhost:8080/about`.

Nimi `MainView` on luokka, jonka haluat lataa aluksi, kun sovellus käynnistyy. Saavuttaaksesi tämän, lisää `@Route` -anotaatiota, joka kartoi `MainView` juur URL-osoitteeseesi:

```java title="MainView.java" {1}
@Route("/")
public class MainView {

  public MainView() {
  }

}
```

`FormView`-luokan osalta kartoita näkymä niin, että se ladataan, kun käyttäjä siirtyy osoitteeseen `http://localhost:8080/customer`:

```java title="FormView.java" {1}
@Route("customer")
public class FormView {

  public FormView() {
  }

}
```

:::tip Oletuskäyttäytyminen
Jos et määritä arvoa nimenomaisesti `@Route` -annotaatiolle, URL-segmentti on luokan nimi muunnettuna pieniksi kirjaimiksi, ja `View`-liite poistetaan.

- `MainView` kartoittaisi `/main`
- `FormView` kartoittaisi `/form`
:::

## Yhteiset ominaisuudet {#shared-characteristics}

Lisäksi siitä, että molemmat ovat näkymäreittejä, `MainView` ja `FormView` jakavat muitakin ominaisuuksia. Jotkut näistä yhteisistä piirteistä, kuten `Composite`-komponenttien käyttö, ovat perustavanlaatuisia webforJ-sovellusten käytölle, kun taas toiset helpottavat sovelluksesi hallintaa.

### `Composite`-komponenttien käyttö {#using-composite-components}

Kun sovellus oli yhden sivun sovellus, säilytit komponentit `Frame`-komponentissa. Jatkossa, kun sovelluksessa on useita näkymiä, sinun on käärittävä nämä käyttöliittymäkomponentit [`Composite`-komponentteihin](/docs/building-ui/composing-components).

`Composite`-komponentit ovat kääreitä, jotka helpottavat uudelleenkäytettävien komponenttien luomista. Voit luoda `Composite`-komponentin laajentamalla `Composite`-luokkaa määritellyllä sitoutuneella komponentilla, joka toimii luokan perustana, esim. `Composite<FlexLayout>`. 

Tässä oppaassa käytetään `Div`-elementtejä sitoutuneina komponentteina, mutta ne voivat olla mitä tahansa komponentteja, kuten [`FlexLayout`](/docs/components/flex-layout) tai [`AppLayout`](/docs/components/app-layout). `getBoundComponent()` -menetelmää käyttämällä voit viitata sitoutuneeseen komponenttiin ja saada pääsyn sen menetelmiin. Tämä mahdollistaa koon asettamisen, CSS-luokan nimen lisäämisen, haluamiesi komponenttien lisäämisen `Composite`-komponenttiin ja komponenttiin liittyvien menetelmien käyttämisen.

`MainView` ja `FormView` laajentavat `Composite`-komponenttia `Div`:nä sitoutuneena komponenttina. Viittaa sitten tuohon sitoutuneeseen komponenttiin, jotta voit lisätä käyttöliittymiä myöhemmin. Molempien näkymien pitäisi näyttää seuraavanlaisilta:

```java
// Laajenna Composite sitoutuneella komponentilla
public class MainView extends Composite<Div> {

  // Pääsy sitoutuneeseen komponenttiin
  private Div self = getBoundComponent();

  // Luo komponentin käyttöliittymä
  private Button submit = new Button("Lähetä");

  public MainView() {

    // Lisää käyttöliittymäkomponentti sitoutuneeseen komponenttiin
    self.add(submit);
  }
}
```

### Kehysotsikon asettaminen {#setting-the-frame-tile}

Kun käyttäjällä on useita välilehtiä selaimessaan, ainutlaatuinen kehysotsikko auttaa heitä nopeasti tunnistamaan, mikä osa sovelluksesta on avoinna.

[`@FrameTitle`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/annotation/FrameTitle.html) -annotaatio määrittelee, mitä näytetään selaimen otsikossa tai sivun välilehdessä. Lisää molemmille näkymille kehysotsikko käyttäen `@FrameTitle` -annotaatiota:

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

### Jaettu CSS {#shared-css}

Kun sinulla on sitoutunut komponentti, johon voit viitata `MainView`- ja `FormView`-näkymissä, voit muotoilla sen CSS:llä. Voit käyttää ensimmäisen vaiheen CSS:ää, [Perus sovelluksen luominen](/docs/introduction/tutorial/creating-a-basic-app#referencing-a-css-file), antaaksesi molemmille näkymille identtiset käyttöliittymäkontti-tyylit. Lisää CSS-luokan nimi `card` jokaisen näkymän sitoutuneeseen komponenttiin:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {9} title="MainView.java"
    @Route("/")
    @FrameTitle("Asiakastaulukko")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView() {

        self.addClassName("kortti");
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

        self.addClassName("kortti");
      }
    }
    ```
  </TabItem>
</Tabs>

### `CustomerService`-käyttäminen {#using-customerservice}

Näkyjen viimeinen yhteinen piirre on `CustomerService`-luokan käyttö. `Taulukko` `MainView`-luokassa näyttää jokaisen asiakkaan, kun taas `FormView` lisää uusia asiakkaita. Koska molemmat näkymät ovat vuorovaikutuksessa asiakastietojen kanssa, niiden on päästävä sovelluksen liiketoimintalogiikkaan. 

Näkymät saavat pääsyn siihen Spring-palvelun kautta, joka on luotu [Työskentely tietojen kanssa](/docs/introduction/tutorial/working-with-data#creating-a-service), `CustomerService`. Osaakseen käyttää Spring-palvelua jokaisessa näkymässä, tee `CustomerService` konstruktoriparametriksi:

<Tabs>
  <TabItem value="MainView" label="MainView">
    ```java {7-8} title="MainView.java"
    @Route("/")
    @FrameTitle("Asiakastaulukko")
    public class MainView extends Composite<Div> {

      private Div self = getBoundComponent();

      public MainView(CustomerService customerService) {
        this.customerService = customerService;
        self.addClassName("kortti");
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
        self.addClassName("kortti");
      }
    }
    ```
  </TabItem>
</Tabs>

## `MainView`-luokan luominen {#creating-mainview}

Kun olet tehnyt sovelluksestasi reititettävän, antanut näkymille `Composite`-komponenttien kääreitä ja sisällyttänyt `CustomerService`:n, olet valmis rakentamaan kunkin näkymän yksilölliset käyttöliittymät. Kuten mainittiin aiemmin, `MainView` sisältää käyttöliittymäkomponentit, jotka alunperin olivat `Application`-luokassa. Tälle luokalle on myös tarjottava tapa navigoida `FormView`-näkymään.

### `Table`-metodien ryhmittely {#grouping-the-table-methods}

Kun siirrät komponentteja `Application`-luokasta `MainView`-luokkaan, on hyvä idea alkaa jakaa sovelluksesi osiin, jotta voi yksi mukautettu metodi voisi tehdä muutoksia `Table`-komponenttiin kerralla. Koodin jakaminen nyt tekee siitä helpommin hallittavaa sovelluksen kasvaessa monimutkaiseksi.

Nyt `MainView`-konstruktorisi tulisi vain kutsua yhtä `buildTable()`-metodia, joka lisää sarakkeet, asettaa koot ja viittaa varastoon:

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

Käyttäjillä on oltava tapa siirtyä `MainView`-näkymästä `FormView`-näkymään käyttöliittymän kautta.

webforJ:ssä voit siirtyä suoraan uuteen näkymään käyttämällä näkymän luokkaa. Reititys luokan kautta sen sijaan, että URL-segmentin kautta, takaa, että webforJ ottaa oikean reitin ladatakseen näkymän. 

Siirtyäksesi toiseen näkymään, käytä [`Router`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/router/Router.html) -luokkaa saadaksesi nykyisen sijainnin `getCurrent()`-menetelmällä, ja käytä sitten `navigate()`-menetelmää, jonka parametrina on näkymän luokka: 

```java
Router.getCurrent().navigate(FormView.class);
```

Tämä koodi lähettää käyttäjän ohjelmallisesti uuteen asiakaslomakkeeseen, mutta navigoinnin on oltava kytketty käyttäjän toimintaan. Jotta käyttäjät voivat lisätä uuden asiakkaan, voit joko muokata tai korvata tiedonpainiketta `Application`-luokasta. Sen sijaan, että avaat viestidialogin, painike voi navigoida `FormView`-luokkaan:

```java
private Button addCustomer = new Button("Lisää asiakas", ButtonTheme.PRIMARY,
    e -> Router.getCurrent().navigate(FormView.class));
```

## Valmis `MainView` {#completed-mainview}

Siirtymisen `FormView`-näkymään ja ryhmitettyjen taulukkometodien kanssa tässä on miltä `MainView` pitäisi näyttää ennen kuin siirrytään `FormView`-näkymän luomiseen:

<!-- vale off -->
<ExpandableCode title="MainView.java" language="java" startLine={1} endLine={15}>
{`@Route("/")
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
          .addClassName("kortti")
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

## `FormView`-luokan luominen {#creating-formview}

`FormView` näyttää lomakkeen uusien asiakkaiden lisäämiseksi. Jokaiselle asiakastiedolle `FormView` sisältää muokattavan komponentin, jonka avulla käyttäjät voivat olla vuorovaikutuksessa. Lisäksi siinä on painike, jonka avulla käyttäjät voivat lähettää tiedot ja peruuttaa painike, jolla tiedot voidaan hylätä.

### `Customer`-instanssin luominen {#creating-a-customer-instance}

Kun käyttäjä muokkaa tietoja uudelle asiakkaalle, muutokset tulisi pitää vain varastossa, kunnes he ovat valmiita lähettämään lomakkeen. Uuden `Customer`-olion käyttäminen on kätevä tapa muokata ja ylläpitää uusia tietoja ilman, että varastoa muokataan suoraan. Luo uusi `Customer`-instanssi `FormView`:ssä lomaketta varten:

```java
private Customer customer = new Customer();
```

Jotta `Customer`-instanssia voitaisiin muokata, jokainen ominaisuus, lukuun ottamatta `id`:tä, tulisi liittää muokattavaan komponenttiin. Käyttäjän käyttöliittymässä tekemät muutokset tulisi peilata `Customer`-instanssiin.

### `TextField`-komponenttien lisääminen {#adding-textfield-components}

Ensimmäiset kolme muokattavaa ominaisuutta `Customer`-luokassa (`firstName`, `lastName` ja `company`) ovat kaikki `String`-arvoja ja ne tulisi esittää yhden rivin tekstieditorilla. [`TextField`](/docs/components/fields/textfield) -komponentit ovat loistava valinta näiden ominaisuuksien esittämiselle.

`TextField`-komponentin avulla voit lisätä etiketin ja tapahtumakuuntelijan, joka laukaisee aina, kun kentän arvo muuttuu. Kukin tapahtumakuuntelija tulisi päivittää `Customer`-instanssia vastaavalla ominaisuudella.

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
    self.addClassName("kortti");
  }
}
```

:::tip Jaettu nimikäytönto
Komponenttien nimeäminen samaksi kuin ominaisuudet, joita ne edustavat `Customer`-entiteetissä, helpottaa tietojen sitomista tulevassa vaiheessa, [Tiedon validoiminen ja sitominen](/docs/introduction/tutorial/validating-and-binding-data).
:::

### `ChoiceBox`-komponentin lisääminen {#adding-a-choicebox-component}

`country`-ominaisuuden käyttämisessä `TextField`-komponenttia ei olisi ihanteellista, koska ominaisuus voi olla vain yksi viidestä enum-arvosta: `UNKNOWN`, `GERMANY`, `ENGLAND`, `ITALY` ja `USA`.

Parempi komponentti etukäteen määriteltyjen vaihtoehtojen valintaan on [`ChoiceBox`](/docs/components/lists/choicebox).

Jokainen `ChoiceBox`-komponentin vaihtoehto on esitetty `ListItem`:inä. Jokaisella `ListItem`:illä on kaksi arvoa, `Object`-avain ja `String`-teksti, joka näytetään käyttöliittymässä. Kahdella arvolla jokaiselle vaihtoehdolle voit käsitellä `Object`-arvoa sisäisesti samalla kun esität ihmisten luettavampaa vaihtoehtoa käyttöliittymässä.

Esimerkiksi `Object`-avain voisi olla kansainvälinen standardikustannusnumero (ISBN), kun taas `String`-teksti on kirjan nimi, joka on käyttäjille helpommin luettavissa.

```java
new ListItem(isbn, bookTitle);
```

Mutta tämä sovellus käsittelee joukkoa maan nimiä, ei kirjoja. Jokaiselle `ListItem`:ille haluat `Object`-arvoksi olevan `Customer.Country` -enum, kun taas tekstin pitäisi olla sen `String`-esitys.

Lisätäksesi kaikki `country`-vaihtoehdot `ChoiceBox`:iin, voit käyttää iteraattoria luodaksesi `ListItem`:n jokaiselle `Customer.Country` -enumeration ja laittaa ne `ArrayList<ListItem>`-kansioon. Tämän jälkeen voit lisätä tuon `ArrayList<ListItem>`-kansio `ChoiceBox`-komponenttiin:

```java
//Luo ChoiceBox-komponentti
private ChoiceBox country = new ChoiceBox("Maa");

//Luo ArrayList ListItem-olioista
ArrayList<ListItem> listCountries = new ArrayList<>();

//Lisää iteraattori, joka luo ListItem:n jokaiselle Customer.Country -vaihtoehdolle
for (Country countryItem : Customer.Country.values()) {
  listCountries.add(new ListItem(countryItem, countryItem.toString()));
}

//Lisää täytetty ArrayList ChoiceBoxiin
country.insert(listCountries);

//Aseta ensimmäinen `ListItem` oletukseksi, kun lomake latautuu
country.selectIndex(0);
```

Sitten, kun käyttäjä valitsee vaihtoehdon `ChoiceBox`:issa, `Customer`-instanssin tulisi päivittää valitun kohteen avaimella, joka on `Customer.Country`-arvo.

```java
private ChoiceBox country = new ChoiceBox("Maa",
    e -> customer.setCountry((Customer.Country) e.getSelectedItem().getKey()));
```

Koodin pitämiseksi siistinä iteraattorin, joka luo `ArrayList<ListItem>` ja lisää sen `ChoiceBox`:iin, tulisi olla erillisessä metodissa. Sen jälkeen, kun olet lisännyt `ChoiceBox`:in, joka sallii käyttäjän valita `country`-ominaisuuden, `FormView` pitäisi näyttää tältä:

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
    self.addClassName("kortti");
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

Kun käytät uutta asiakaslomaketta, käyttäjien tulisi voida joko tallentaa tai hylätä muutoksensa. Luo kaksi `Button`-komponenttia tämän ominaisuuden toteuttamiseksi:

```java
private Button submit = new Button("Lähetä");
private Button cancel = new Button("Peruuta");
```

Sekä lähetys- että peruutuspainikkeet tulisi ohjata käyttäjät takaisin `MainView`-näkymään. Tämä sallii käyttäjän heti nähdä toimintojensa tulokset, olivatpa ne uusia asiakkaita taulukkossa tai pysyivätkö ne muuttumattomina. Koska useat syötteet `FormView`:ssa vievät käyttäjät `MainView`:hen, navigoinnin tulisi olla kytkettynä kutsuttavaan metodiin:

```java
private void navigateToMain(){
  Router.getCurrent().navigate(MainView.class);
}
```

**Peruutuspainike**

Lomakkeen tietojen hylkääminen ei vaadi muuta koodia kuin paluu `MainView`:hen. Koska peruutus ei ole ensisijainen toiminto, painikkeen teeman asettaminen ääriviivattomaksi antaa enemmän painoarvoa lähetyspainikkeelle. `Button`-komponentin sivuilla [Teemat](/docs/components/button#themes) luetteloi kaikki saatavilla olevat teemat.

```java
private Button cancel = new Button("Peruuta", ButtonTheme.OUTLINED_PRIMARY,
    e -> navigateToMain());
```

**Lähetyspainike**

Kun käyttäjä painaa lähetyspainiketta, `Customer`-instanssissa olevat arvot tulisi käyttää uuden merkinnän luomiseen varastoon.

Käyttämällä `CustomerService`:ia voit ottaa `Customer`-instanssin päivittääksesi H2-tietokannan. Tämän mukana uuden ja ainutlaatuisen `id`:n myöntäminen sille `Customer`:lle. Varaston päivittämisen jälkeen voit ohjata käyttäjiä `MainView`-näkymään, jossa he voivat nähdä uuden asiakkaan taulukossa.

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

Lisäämällä `TextField`, `ChoiceBox` ja `Button` komponentit, sinulla on nyt kaikki lomakkeen vuorovaikutteiset osat. Viimeinen parannus `FormView`-näkymässä tässä vaiheessa on visuaalisesti järjestää kuusi komponenttia.

Tätä lomaketta voidaan käyttää [`ColumnsLayout`](/docs/components/columns-layout) -komponentilla erottamaan komponentit kahteen sarakkeeseen ilman, että tarvitsee asettaa minkään vuorovaikutteisen komponentin leveyttä. Luodaksesi `ColumnsLayout`:n, määrittele jokainen komponentti, joka tulisi olla asettuneena layouttiin:

```java
private ColumnsLayout layout = new ColumnsLayout(
  firstName, lastName,
  company, country,
  submit, cancel);
```

Aseta `ColumnsLayout`:lle sarakkeiden määrä käyttämällä `Breakpoint`-objektien listaa. Jokainen `Breakpoint` kertoo `ColumnsLayout`:lle minimileveyden, jonka sen on oltava, jotta määritetty määrä sarakkeita voidaan soveltaa. Käyttämällä `ColumnsLayout`:ia voit tehdä lomakkeen, jossa on kaksi saraketta, mutta vain jos näyttö on riittävän leveä esitelläkseen kaksi saraketta. Pienemmillä näytöillä komponentit näkyvät yhdessä sarakkeessa.

[Breakpoints](/docs/components/columns-layout#breakpoints) -osiossa `ColumnsLayout`:n artikkelissa selitetään breakpointeista tarkemmin.

Koodin pitämiseksi ylläpidettävänä aseta breakpointeja erillisessä metodissa. Tässä metodissa voit myös hallita komponenttien välistä vaaka- ja pystysuoraa väliä `ColumnsLayout`:issa `setSpacing()`-menetelmällä.

```java
private void setColumnsLayout() {

  //Tenimin sarakkeiden määrä, ColumnsLayout on leveämpi kuin 600px
  List<Breakpoint> breakpoints = List.of(
    new Breakpoint(600, 2));

  //Lisää lista breakpointeista
  layout.setBreakpoints(breakpoints);

  //Aseta komponenttien väli käyttämällä DWC CSS -muuttujaa
  layout.setSpacing("var(--dwc-space-l)")
}
```

Lopuksi voit lisätä juuri luodun `ColumnsLayout`:in `FormView`:n sitoutuneeseen komponenttiin, asettamalla samalla maksimi leveyden ja lisäämällä aikaisemmin mainitun luokan nimen:

```java
self.setMaxWidth(600)
  .addClassName("kortti")
  .add(layout);
```

## Valmis `FormView` {#completed-formview}

Lisättyäsi `Customer`-instanssin, vuorovaikutteiset komponentit ja `ColumnsLayout`:in, `FormView`:n tulisi näyttää seuraavalta:

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer")
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
          .addClassName("kortti")
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

Koska käyttäjät voivat nyt lisätä asiakkaita, sovelluksesi pitäisi pystyä muokkaamaan olemassa olevia asiakkaita samassa lomakkeessa. Seuraavassa vaiheessa, [Observerit ja reittiparametrit](/docs/introduction/tutorial/observers-and-route-parameters), mahdollistat asiakas `id`:n olevan alkuperäinen parametri `FormView`:lle, jotta voi täyttää lomakkeen sen asiakkaan tiedoilla ja sallia käyttäjien muuttaa ominaisuuksia.
