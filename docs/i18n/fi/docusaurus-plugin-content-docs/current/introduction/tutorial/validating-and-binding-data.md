---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: bb0d88755455ff4e639e598c104b6d68
---
Sovelluksesi [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) voi käyttää `FormView`-komponenttia olemassa olevien asiakastietojen muokkaamiseen. Tämä vaihe hyödyntää [Data binding](/docs/data-binding/overview) -toimintoa, joka yhdistää käyttöliittymän komponentit suoraan tietomalliin automaattista arvon synkronointia varten. Tämä vähentää boilerplate-koodia sovelluksessasi ja mahdollistaa validointitarkistusten lisäämisen Spring-entiteettiin `Customer`, pakottaen käyttäjät antamaan täydellisiä ja tarkkoja tietoja lomakkeita täyttäessään. Tämä vaihe kattaa seuraavat käsitteet:

- [Jakarta validation](https://beanvalidation.org)
- [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) -luokan käyttäminen

Tämän vaiheen suorittaminen luo version [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Sovelluksen ajaminen {#running-the-app}

Sovelluksesi kehittämisen aikana voit käyttää [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) -esimerkkiä vertailukohtana. Näet sovelluksen toiminnassa seuraavasti:

1. Siirry ylimmän tason hakemistoon, joka sisältää `pom.xml`-tiedoston, tämä on `5-validating-and-binding-data`, jos seuraat GitHub-version ohjeita.

2. Käytä seuraavaa Maven-komentoa ajaaksesi Spring Boot -sovellusta paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen ajaminen avaa automaattisesti uuden selaimen osoitteeseen `http://localhost:8080`.

## Validointisääntöjen määrittäminen {#defining-validation-rules}

Muokattavaa dataa sisältävän sovelluksen kehittäminen tulisi sisältää validointia. Validointitarkistukset auttavat ylläpitämään merkityksellisiä ja tarkkoja käyttäjiltä lähetettyjä tietoja. Jos tarkistuksia ei tehdä, se voi johtaa ongelmiin, joten on tärkeää havaita, millaisia virheitä käyttäjät voivat tehdä lomaketta täyttäessään reaaliajassa.

Koska se, mikä on voimassa olevaa, voi vaihdella ominaisuuksien välillä, sinun on määritettävä, mikä tekee kustakin ominaisuudesta voimassa olevan, ja ilmoitettava käyttäjälle, jos jokin on virheellistä. Onneksi voit tehdä tämän helposti [Jakarta Validation](https://beanvalidation.org) avulla. Jakarta validationin avulla voit lisätä rajoituksia ominaisuuksiin annotaatioina.

Tässä oppaassa käytetään kahta Jakarta-annotaatiota, `@NotEmpty` ja `@Pattern`. `@NotEmpty` tarkistaa null- ja tyhjät merkkijonot, kun taas `@Pattern` tarkistaa, vastaako ominaisuus asetettua säännöllistä lauseketta. Molemmat annotaatiot mahdollistavat viestin lisäämisen näytettäväksi, kun ominaisuudesta tulee virheellinen.

Edellyttää, että sekä etu- että sukunimet ovat pakollisia ja sisältävät vain kirjaimia, samalla kun yritysnimi on valinnainen ja sallii kirjaimet, numerot ja väliotsikot, lisää seuraavat annotaatiot `Customer`-entiteettiin:

<!-- vale off -->
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
    @Pattern(regexp = "[a-zA-Z]*", message = "Virheelliset merkit")
    private String firstName = "";

  // highlight-next-line
    @NotEmpty(message = "Asiakkaan sukunimi on pakollinen")
  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z]*", message = "Virheelliset merkit")
    private String lastName = "";

  // highlight-next-line
    @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Virheelliset merkit")
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
<!-- vale on -->

Katso [Jakarta Bean Validation -rajoitusviitteestä](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) täydellinen lista validoitavista rajoituksista tai opi lisää [webforJ Jakarta Validation -artikkelista](/docs/data-binding/validation/jakarta-validation).

## Kenttien sitominen {#binding-the-fields}

Käyttääksesi `Customer`-validointitarkistuksia käyttöliittymässä `FormView`, sinun on luotava `BindingContext` datan sitomista varten. Ennen datan sitomista jokaiselle kentälle `FormView`:ssä tarvittiin tapahtumakuuntelija synkronoimaan se manuaalisesti Spring-entiteetin `Customer` kanssa. `BindingContext`-luokan luominen `FormView`:ssä sitoo ja synkronoi automaattisesti `Customer`-datan mallin käyttöliittymän komponentteihin.

### `BindingContext`in luominen {#creating-a-bindingcontext}

`BindingContext`-instanssi tarvitsee Spring-beanin, jonka kanssa sitoumukset synkronoidaan. `FormView`:ssä määrittele `BindingContext` käyttämällä `Customer`-entiteettiä:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Käytä sitten automaattisesti sitomaan käyttöliittymäkomponentit ominaisuuksien nimiä käyttäen `BindingContext.of()` seuraavilla parametreilla:

- **`this`** : Aikaisemmin määrittelit `context`-muuttujan `BindingContext`:ksi. Ensimmäinen parametri asettaa, mikä objekti sisältää sidottavat komponentit.
- **`Customer.class`** : Toinen parametri on käytettävän beanin luokka sitomista varten.
- **`true`** : Kolmas parametri ottaa käyttöön Jakarta-validoinnin, jolloin konteksti voi käyttää asettamiasi validoijia `Customer`-luokalle. Tämä muuttaa virheellisten komponenttien tyyliä ja näyttää asetetut viestit.

Yhteenvetona se näyttää seuraavalta koodiriviltä:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Lomakkeen responsiiviseksi tekeminen {#making-the-form-responsive}

Datan sitomisen avulla sovelluksesi suorittaa nyt automaattisesti validointitarkistuksia. Lisäämällä tapahtumakuuntelijan tarkistuksiin voit estää käyttäjiä lähettämästä virheellistä lomaketta. Lisää seuraavat kooditiedot, jotta lähetä-painike on aktiivinen vain, kun lomake on voimassa:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Tapahtumakuuntelijoiden poistaminen komponenteista {#removing-event-listeners-for-components}

Jokainen käyttöliittymän muutos on nyt automaattisesti synkronoitu `BindingContext`in kanssa. Tämä tarkoittaa, että voit nyt poistaa tapahtumakuuntelijat jokaiselta kentältä:

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
// Datansitomisen kanssa
TextField firstName = new TextField("Etunimi");
TextField lastName = new TextField("Sukunimi");
TextField company = new TextField("Yritys");
ChoiceBox country = new ChoiceBox("Maa");
```

### Sitominen ominaisuuksien nimillä {#binding-by-property-names}

Koska jokaisen komponentin nimi vastasi datamallia, webforJ käytti [Automattista sitomista](/docs/data-binding/automatic-binding). Jos nimet eivät vastanneet, voit käyttää `@UseProperty`-annotaatiota niiden kartoittamiseksi.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Etunimi");
```

### Datan lukeminen `fillForm()`-metodissa {#reading-data-in-the-fillForm()-method}

Aiemmin `fillForm()`-metodissa aloitit jokaisen komponentin arvon asettamalla sen manuaalisesti `Customer`-kopiosta. Mutta nyt, kun käytät `BindingContext`-luokkaa, voit käyttää `read()`-metodia. Tämä metodi täyttää jokaisen sidotun komponentin datassa olevan `Customer`-kopion mukaisella ominaisuudella.

`fillForm()`-metodissa vaihda `setValue()`-metodit `read()`-metodiin:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);
  
  // Poistettu jokainen setValue()-metodi käyttöliittymän komponenteille

    context.read(customer);
  }
```

### Validoinnin lisääminen `submitCustomer()`-metodiin {#adding-validation-to-submitcustomer}

Viimeinen muutos `FormView`:ssä tämän vaiheen aikana on lisätä turvatoimi `submitCustomer()`-metodiin. Ennen kuin muutokset sitoutuvat H2-tietokantaan, sovellus suorittaa viimeisen validoinnin sidotulle kontekstille `write()`-metodia käyttäen.

`write()`-metodi päivittää beanin ominaisuudet `BindingContext`:ssä käytettävien sidottujen käyttöliittymän komponenttien avulla ja palauttaa `ValidationResult`-objektin.

Käytä `write()`-metodia kirjoittaaksesi `Customer`-kopioon käyttäen sidottuja komponentteja `FormView`:ssä. Sitten, jos palautettu `ValidationResult` on voimassa, päivitä H2-tietokanta kirjoitetuilla tiedoilla.

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

Näiden muutosten myötä `FormView` näyttää tältä. Sovellus tukee nyt datan sitomista ja validointia käyttäen Spring Bootia ja webforJ:ta. Lomakkeen syötteet synkronoidaan automaattisesti mallin kanssa ja tarkistetaan validointisääntöjen mukaan.

<!-- vale off -->
<ExpandableCode title="FormView.java" language="java" startLine={1} endLine={15}>
{`@Route("customer/:id?<[0-9]+>")
  @FrameTitle("Asiakaslomake")
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
<!-- vale on -->

## Seuraava askel {#next-step}

Seuraava vaihe, [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout), keskittyy `AppLayout`:n käyttöön, jolla lisätään sivuvalikko, joka on saatavilla asiakkaita käsittelevillä tauluilla ja asiakaslomakesivuilla. Opit myös lisää yhdestä asettelutyökalusta, `FlexLayout`-komponentista.
