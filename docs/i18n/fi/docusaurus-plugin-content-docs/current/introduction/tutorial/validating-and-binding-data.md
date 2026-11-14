---
title: Tietojen validointi ja sidonta
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 97f423188eb353ac27b8e9179e93b224
---
Sovelluksesi, jonka loit vaiheessa [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters), voi käyttää `FormView`-näkymää olemassa olevien asiakastietojen muokkaamiseen. Tässä vaiheessa käytetään [tietojen sidontaa](/docs/data-binding/overview), joka yhdistää käyttöliittymän komponentit suoraan tietomalliin ja synkronoi arvot automaattisesti. Tämä vähentää sovelluksesi toistuvaa koodia ja mahdollistaa validointitarkistusten lisäämisen Spring-entiteettiin `Customer`, jolloin käyttäjät antavat lomakkeita täyttäessään täydelliset ja oikeat tiedot. Tässä vaiheessa käsitellään seuraavia käsitteitä:

- [Jakarta-validointi](https://beanvalidation.org)
- [`BindingContext`-luokan](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) käyttö

Tämän vaiheen suorittamalla luot version [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)-projektista.

<!-- <VideoPlayer video="tutorial/validating-and-binding-data" width={1920} height={1080} title="Validating and binding data" /> -->

## Sovelluksen suorittaminen {#running-the-app}

Sovellusta kehittäessäsi voit käyttää vertailukohtana [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data)-projektia. Näet sovelluksen toiminnassa seuraavasti:

1. Siirry ylimmän tason hakemistoon, jossa `pom.xml`-tiedosto sijaitsee. Jos seuraat GitHub-version ohjeita, kyseessä on hakemisto `5-validating-and-binding-data`.

2. Suorita Spring Boot -sovellus paikallisesti seuraavalla Maven-komennolla:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Validointisääntöjen määrittäminen {#defining-validation-rules}

Muokattavia tietoja käsittelevän sovelluksen kehittämiseen kuuluu validointi. Validointitarkistukset auttavat säilyttämään käyttäjien lähettämät tiedot mielekkäinä ja oikeina. Tarkistamatta jättäminen voi johtaa ongelmiin, joten on tärkeää havaita reaaliaikaisesti virheet, joita käyttäjät voivat tehdä lomaketta täyttäessään.

Koska ominaisuuden hyväksyttävät arvot voivat vaihdella, sinun on määritettävä kullekin ominaisuudelle kelvolliset arvot ja ilmoitettava käyttäjälle, jos jokin arvo ei kelpaa. Onneksi tämän voi tehdä helposti [Jakarta Validationin](https://beanvalidation.org) avulla. Jakarta Validationin avulla voit lisätä ominaisuuksille rajoitteita annotaatioina.

Tässä tutoriaalissa käytetään kahta Jakarta-annotaatiota: `@NotEmpty` ja `@Pattern`. `@NotEmpty` tarkistaa, ettei arvo ole null tai tyhjä merkkijono, kun taas `@Pattern` tarkistaa, vastaako ominaisuuden arvo määrittämääsi säännöllistä lauseketta. Molempien annotaatioiden avulla voit lisätä viestin, joka näytetään, kun ominaisuuden arvo ei kelpaa.

Jos haluat vaatia, että sekä etu- että sukunimi ovat pakollisia ja sisältävät vain kirjaimia, mutta yrityksen nimi on valinnainen ja voi sisältää kirjaimia, numeroita ja välilyöntejä, lisää seuraavat annotaatiot `Customer`-entiteettiin:

<!-- vale off -->
<ExpandableCode title="Customer.java" language="java" startLine={8} endLine={28}>

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

// highlight-next-line
  @NotEmpty(message = "Customer first name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";

// highlight-next-line
  @NotEmpty(message = "Customer last name is required")
// highlight-next-line
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String lastName = "";

// highlight-next-line
  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
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
```

</ExpandableCode>
<!-- vale on -->

Katso kaikki validoinnit [Jakarta Bean Validationin rajoitteiden viiteoppaasta](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) tai lue lisää [webforJ:n Jakarta Validation -artikkelista](/docs/data-binding/validation/jakarta-validation).

## Kenttien sitominen {#binding-the-fields}

Jotta voit käyttää `Customer`-luokan validointitarkistuksia `FormView`-näkymän käyttöliittymässä, luot tietojen sidontaa varten `BindingContext`-olion. Ennen tietojen sidontaa jokainen `FormView`-näkymän kenttä vaati tapahtumakuuntelijan, joka synkronoi kentän arvon manuaalisesti Spring-entiteetin `Customer` kanssa. Kun luot `FormView`-näkymään `BindingContext`-olion, `Customer`-tietomalli sidotaan käyttöliittymän komponentteihin ja synkronoidaan niiden kanssa automaattisesti.

### `BindingContext`-olion luominen {#creating-a-bindingcontext}

`BindingContext`-olion luomiseen tarvitaan Spring-bean, jonka kanssa sidonnat synkronoidaan. Määritä `FormView`-näkymässä `BindingContext`, joka käyttää `Customer`-entiteettiä:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Voit sitoa käyttöliittymän komponentit automaattisesti beanin ominaisuuksiin niiden nimien perusteella käyttämällä `BindingContext.of()`-metodia seuraavin parametrein:

- **`this`** : Aiemmin määritit `context`-muuttujan `BindingContext`-olioksi. Ensimmäinen parametri määrittää olion, joka sisältää sidottavat komponentit.
- **`Customer.class`** : Toinen parametri on sidonnassa käytettävän beanin luokka.
- **`true`** : Kolmas parametri ottaa käyttöön Jakarta-validoinnin, jolloin konteksti voi käyttää `Customer`-luokalle määrittämiäsi validointeja. Tämä muuttaa virheellisten komponenttien tyyliä ja näyttää määritetyt viestit.

Kokonaisuudessaan koodirivi näyttää tältä:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Lomakkeen responsiivisuuden määrittäminen {#making-the-form-responsive}

Tietojen sidonnan ansiosta sovelluksesi suorittaa nyt validointitarkistukset automaattisesti. Lisäämällä tarkistuksiin tapahtumakuuntelijan voit estää käyttäjiä lähettämästä virheellistä lomaketta. Lisää seuraava koodi, jotta lähetyspainike on aktiivinen vain lomakkeen ollessa kelvollinen:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Komponenttien tapahtumakuuntelijoiden poistaminen {#removing-event-listeners-for-components}

Kaikki käyttöliittymän muutokset synkronoidaan nyt automaattisesti `BindingContext`-olion kanssa. Voit siis poistaa kunkin kentän tapahtumakuuntelijat:

**Ennen**
```java title="FormView.java"
// Without data binding
TextField firstName = new TextField("First Name", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Last Name", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Company", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Country",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Jälkeen**
```java title="FormView.java"
// With data binding
TextField firstName = new TextField("First Name");
TextField lastName = new TextField("Last Name");
TextField company = new TextField("Company");
ChoiceBox country = new ChoiceBox("Country");
```

### Sitominen ominaisuuksien nimien perusteella {#binding-by-property-names}

Koska kunkin komponentin nimi vastasi tietomallin ominaisuuden nimeä, webforJ käytti [automaattista sidontaa](/docs/data-binding/automatic-binding). Jos nimet eivät vastaisi toisiaan, voisit yhdistää ne `@UseProperty`-annotaation avulla.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("First Name");
```

### Tietojen lukeminen `fillForm()`-metodissa {#reading-data-in-the-fillForm()-method}

Aiemmin alustusit `fillForm()`-metodissa kunkin komponentin arvon hakemalla tiedot manuaalisesti `Customer`-kopiosta. Nyt kun käytät `BindingContext`-oliota, voit käyttää `read()`-metodia. Tämä metodi täyttää kunkin sidotun komponentin arvolla, joka vastaa `Customer`-kopion tietoja.

Korvaa `fillForm()`-metodissa `setValue()`-metodit `read()`-metodilla:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Removed each setValue() method for the UI components

    context.read(customer);
  }
```

### Validoinnin lisääminen `submitCustomer()`-metodiin {#adding-validation-to-submitcustomer}

Tässä vaiheessa viimeinen `FormView`-näkymään tehtävä muutos on suojauksen lisääminen `submitCustomer()`-metodiin. Ennen muutosten tallentamista H2-tietokantaan sovellus suorittaa sidotun kontekstin tuloksille lopullisen validoinnin `write()`-metodilla.

`write()`-metodi päivittää beanin ominaisuudet käyttämällä `BindingContext`-olioon sidottuja käyttöliittymäkomponentteja ja palauttaa `ValidationResult`-olion.

Kirjoita `write()`-metodilla `Customer`-kopioon `FormView`-näkymään sidottujen komponenttien arvot. Jos palautettu `ValidationResult` on kelvollinen, päivitä H2-tietokanta kirjoitetuilla tiedoilla.

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

### Valmis `FormView` {#completed-formview}

Näiden muutosten jälkeen `FormView` näyttää tältä. Sovellus tukee nyt tietojen sidontaa ja validointia Spring Bootin ja webforJ:n avulla. Lomakkeen syötteet synkronoidaan automaattisesti mallin kanssa ja tarkistetaan validointisääntöjä vasten.

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>

```java
@Route("customer/:id?<[0-9]+>")
@FrameTitle("Customer Form")
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;
  private BindingContext<Customer> context;
  private Customer customer = new Customer();
  private Long customerId = 0L;
  private Div self = getBoundComponent();
  private TextField firstName = new TextField("First Name");
  private TextField lastName = new TextField("Last Name");
  private TextField company = new TextField("Company");
  private ChoiceBox country = new ChoiceBox("Country");
  private Button submit = new Button("Submit", ButtonTheme.PRIMARY, e -> submitCustomer());
  private Button cancel = new Button("Cancel", ButtonTheme.OUTLINED_PRIMARY, e -> navigateToMain());
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
```

</ExpandableCode>
<!-- vale on -->

## Seuraava vaihe {#next-step}

Seuraavassa vaiheessa [sovelluksen asettelun integrointi](/docs/introduction/tutorial/integrating-an-app-layout) käsitellään `AppLayout`-komponentin käyttöä sivuvalikon lisäämiseen. Valikko on käyttäjien käytettävissä sekä asiakastaulukko- että asiakaslomakesivulla. Opit myös käyttämään toista asettelutyökalua, `FlexLayout`-komponenttia.
