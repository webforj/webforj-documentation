---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: dd158594bca6d722983b03ecf8321f90
---
Sovelluksesi [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) voi käyttää `FormView`-komponenttia muokataksesi olemassa olevia asiakastietoja. Tämä vaihe hyödyntää [Data binding](/docs/data-binding/overview), joka yhdistää käyttöliittymäkomponentit suoraan datamalliin automaattista arvon synkronointia varten. Tämä vähentää boilerplate-koodia sovelluksessasi ja antaa sinun lisätä validoimisvaatimuksia Spring-entiteetille `Customer`, mikä saa käyttäjäsi antamaan täydellisiä ja tarkkoja tietoja lomakkeita täyttäessään. Tämä vaihe kattaa seuraavat käsitteet:

- [Jakarta validation](https://beanvalidation.org)
- [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) -luokan käyttäminen

Tämän vaiheen lopettaminen luo version [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Sovelluksen ajaminen {#running-the-app}

Sovelluksesi kehittämisen aikana voit käyttää [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) -versiota vertailua varten. Näet sovelluksen toiminnassa:

1. Siirry ylimmän tason hakemistoon, joka sisältää `pom.xml`-tiedoston; tämä on `5-validating-and-binding-data`, jos seuraat GitHubissa olevaa versiota.

2. Käytä seuraavaa Maven-komentoa ajaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen ajaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Validointisääntöjen määrittäminen {#defining-validation-rules}

Muokattavilla tiedoilla varustetun sovelluksen kehittämisen tulisi sisältää validointia. Validointitarkastukset auttavat ylläpitämään merkityksellisiä ja tarkkoja käyttäjän syöttämiä tietoja. Jos niitä ei tarkisteta, se voi johtaa ongelmiin, joten on tärkeää saada kiinni virheistä, joita käyttäjät voivat tehdä lomaketta täyttäessään reaaliajassa.

Koska se, mikä lasketaan voimassa olevaksi, voi vaihdella ominaisuuksien välillä, sinun täytyy määritellä, mitä kunkin ominaisuuden tekeminen voimassa olevaksi tarkoittaa ja tiedottaa käyttäjää, jos jokin ei ole voimassa. Onneksi voit tehdä tämän helposti [Jakarta Validation](https://beanvalidation.org) -kirjaston avulla. Jakarta-validointi sallii sinun lisätä rajoituksia ominaisuuksiin annotaatioiden avulla.

Tämä opas käyttää kahta Jakarta-annotaatiota, `@NotEmpty` ja `@Pattern`. `@NotEmpty` tarkistaa, onko arvo null tai tyhjää merkkijonoa, kun taas `@Pattern` tarkistaa, vastaako ominaisuus asettamaasi säännöllistä lauseketta. Molemmat annotaatiot sallivat sinun lisätä viestin, joka näytetään, kun ominaisuus muuttuu virheelliseksi.

Vaatimuksena on, että sekä etunimeä että sukunimeä on pakollinen ja niiden on sisällettävä vain kirjaimia, kun taas yrityksen nimi on valinnainen ja sallii kirjaimet, numerot ja välilyönnit. Käytä seuraavia annotaatioita `Customer`-entiteetillä:

<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>
{`@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // highlight-next-line
    @NotEmpty(message = "Asiakkaan etunimi on pakollinen")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Virheellisiä merkkejä")
    private String firstName = "";

  // highlight-next-line
    @NotEmpty(message = "Asiakkaan sukunimi on pakollinen")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Virheellisiä merkkejä")
    private String lastName = "";

  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Virheellisiä merkkejä")
    private String company = "";

    private Country country = Country.UNKNOWN;

    public enum Country {
      UNKNOWN,
      GERMANY,
      ENGLAND,
      ITALY,
      USA
    }

    public Customer(String firstName, String lastName, String company, Country country) {
      setFirstName(firstName);
      setLastName(lastName);
      setCompany(company);
      setCountry(country);
    }

    public Customer(String firstName, String lastName, String company) {
      this(firstName, lastName, company, Country.UNKNOWN);
    }

    public Customer(String firstName, String lastName) {
      this(firstName, lastName, "");
    }

    public Customer(String firstName) {
      this(firstName, "");
    }

    public Customer() {
    }

    public void setFirstName(String newName) {
      firstName = newName;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setLastName(String newName) {
      lastName = newName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setCompany(String newCompany) {
      company = newCompany;
    }

    public String getCompany() {
      return company;
    }

    public void setCountry(Country newCountry) {
      country = newCountry;
    }

    public Country getCountry() {
      return country;
    }

    public Long getId() {
      return id;
    }

  }
`}
</ExpandableCode>

Katso [Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) rajoitusten täyttämiseksi tai lue lisää [webforJ Jakarta Validation -artikkelista](/docs/data-binding/validation/jakarta-validation).

## Kenttien sitominen {#binding-the-fields}

Käytä `Customer`-luokan validoimisvaatimuksia käyttöliittymässä `FormView`-komponentissa luomalla `BindingContext` datan sitomista varten. Ennen datan sitomista jokainen kenttä `FormView`-komponentissa vaati tapahtumakuuntelijan synkronoimaan sen Spring-entiteettiin `Customer` manuaalisesti. `BindingContext`-luokan luominen `FormView`-komponentissa sitoo ja synkronoi `Customer`-datan automaattisesti käyttöliittymäkomponenttien kanssa.

### `BindingContext`-instanssin luominen {#creating-a-bindingcontext}

`BindingContext`-instanssi tarvitsee Spring-beanin, jonka kanssa sitomiset synkronoidaan. `FormView`-komponentissa julista `BindingContext` käyttäen `Customer`-entiteettiä:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Sitten, sitoaaksesi käyttöliittymäkomponentit automaattisesti bean-ominaisuuksiin niiden nimien perusteella, käytä `BindingContext.of()` seuraavilla parametreilla:

- **`this`** : Aiemmin julistit `context`-muuttujan `BindingContext`:ksi. Ensimmäinen parametri määrittää, mikä objekti sisältää sidottavat komponentit.
- **`Customer.class`** : Toinen parametri on luokan bean, jota käytetään sitomiseen.
- **`true`** : Kolmas parametri mahdollistaa Jakarta-validoinnin, mikä sallii kontekstin käyttää `Customer`:lle asettamiasi validoimisvaatimuksia. Tämä muuttaa virheellisten komponenttien tyyliä ja näyttää asetetut viestit.

Yhteenvetona se näyttää seuraavalta koodiriviltä:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Lomakkeen responsiivisuuden tekeminen {#making-the-form-responsive}

Datan sitomisen avulla sovelluksesi suorittaa nyt automaattisesti validoimisvaatimuksia. Lisäämällä tapahtumakuuntelijan tarkastuksiin voit estää käyttäjiä lähettämästä virheellistä lomaketta. Lisää seuraava koodi, jotta lähetyspainike on aktiivinen vain, kun lomake on voimassa:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Tapahtumakuuntelijoiden poistaminen komponenteilta {#removing-event-listeners-for-components}

Jokainen käyttöliittymän muutos synkronoituu nyt automaattisesti `BindingContext`:in kanssa. Tämä tarkoittaa, että voit nyt poistaa tapahtumakuuntelijat jokaiselta kentältä:

**Ennen**
```java title="FormView.java"
// Ilman datan sitomista
TextField firstName = new TextField("Etunimi", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Sukunimi", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Yritys", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Maa", e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Jälkeen**
```java title="FormView.java"
// Datan sitomisen kanssa
TextField firstName = new TextField("Etunimi");
TextField lastName = new TextField("Sukunimi");
TextField company = new TextField("Yritys");
ChoiceBox country = new ChoiceBox("Maa");
```

### Sitominen ominaisuuden nimien mukaan {#binding-by-property-names}

Koska jokaisen komponentin nimi vastaa datamallia, webforJ sovelsi [Automaattista sitomista](/docs/data-binding/automatic-binding). Jos nimet eivät vastanneet, voit käyttää `@UseProperty` -annotaatiota karttaaksesi ne.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Etunimi");
```

### Datan lukeminen `fillForm()`-metodissa {#reading-data-in-the-fillForm()-method}

Aiemmin `fillForm()`-metodissa aloitit jokaisen komponentin arvon asettamisen manuaalisesti noutamalla tiedot `Customer`-kopiosta. Nyt, koska käytät `BindingContext`:ia, voit käyttää `read()`-metodia. Tämä metodi täyttää jokaisen sidotun komponentin tietyn ominaisuuden avulla datasta `Customer`-kopiosta.

`fillForm()`-metodissa korvaa `setValue()`-metodit `read()`-metodilla:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Poistettu jokainen setValue() -metodi käyttöliittymäkomponenteilta
    
    context.read(customer);
  }
```

### Validoinnin lisääminen `submitCustomer()`-metodiin {#adding-validation-to-submitcustomer}

Viimeinen muutos `FormView`:ssa tälle vaiheelle on lisätä turvatoimi `submitCustomer()`-metodiin. Ennen muutosten sitomista H2-tietokantaan sovellus suorittaa lopullisen validoinnin sidotun kontekstin tulosten perusteella `write()`-metodilla.

`write()`-metodi päivittää beanin ominaisuudet käyttäen `BindingContext`:issa sidottuja käyttöliittymäkomponentteja ja palauttaa `ValidationResult`-olion.

Käytä `write()`-metodia kirjoittaaksesi `Customer`:in kopioon sidottujen komponenttien avulla `FormView`:ssa. Jos palautettu `ValidationResult` on voimassa, päivitä H2-tietokanta kirjoitetuilla tiedoilla.

```java title="FormView.java" {2-3}
private void submitCustomer() {
  ValidationResult results = context.write(customer);
  if (results.isValid()) {
    if (customerService.doesCustomerExist(customerId)) {
      customerService.updateCustomer(customer);
    } else {
      customerService.createCustomer(customer);
    }
    navigateToMain();
  }
}
```

### Valmis `FormView`

Näiden muutosten myötä `FormView` näyttää tältä. Sovelluksesi tukee nyt datan sitomista ja validoimista käyttämällä Spring Bootia ja webforJ:ta. Lomakkeen syötteet synkronoidaan automaattisesti mallin kanssa ja tarkastetaan validointisääntöjä vastaan.

<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer/:id?<[0-9]+>")
  @FrameTitle("Asiakastietolomake")
  public class FormView extends Composite<Div> implements WillEnterObserver {
    private final CustomerService customerService;
    private BindingContext<Customer> context;
    private Customer customer = new Customer();
    private Long customerId = 0L;
    private Div self = getBoundComponent();
    private TextField firstName = new TextField("Etunimi");
    private TextField lastName = new TextField("Sukunimi");
    private TextField company = new TextField("Yritys");
    private ChoiceBox country = new ChoiceBox("Maa");
    private Button submit = new Button("Lähetä", ButtonTheme.PRIMARY, e -> submitCustomer());
    private Button cancel = new Button("Peruuta", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
    private ColumnsLayout layout = new ColumnsLayout(
        firstName, lastName,
        company, country,
        submit, cancel);

    public FormView(CustomerService customerService) {
      this.customerService = customerService;
      context = BindingContext.of(this, Customer.class, true);
      context.onValidate(e -> submit.setEnabled(e.isValid()));
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
      ValidationResult results = context.write(customer);
      if (results.isValid()) {
        if (customerService.doesCustomerExist(customerId)) {
          customerService.updateCustomer(customer);
        } else {
          customerService.createCustomer(customer);
        }
        navigateToMain();
      }
    }

    private void navigateToMain() {
      Router.getCurrent().navigate(MainView.class);
    }

    @Override
    public void onWillEnter(WillEnterEvent event, ParametersBag parameters) {
      parameters.getInt("id").ifPresentOrElse(id -> {
        customerId = Long.valueOf(id);
        if (customerService.doesCustomerExist(customerId)) {
          event.accept();
          fillForm(customerId);
        } else {
          event.reject();
          navigateToMain();
        }

      }, () -> event.accept());
    }

    public void fillForm(Long customerId) {
      customer = customerService.getCustomerByKey(customerId);
      context.read(customer);
    }
  }
`}
</ExpandableCode>

:::info Seuraavat askeleet
Etsitkö lisää tapoja parantaa sovellustasi tästä oppaasta? Voit kokeilla käyttää [`AppLayout`](/docs/components/app-layout) -komponenttia kehikkona asiakastietotaulukkosi lisäämiseksi ja uusien ominaisuuksien lisäämiseksi.
:::
