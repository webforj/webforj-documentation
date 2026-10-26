---
title: Tietojen validointi ja sidonta
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: 5b2523a6cc740389f43f68bfd55a1675
---
Sovelluksesi [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters) voi käyttää `FormView`-komponenttia muokataksesi olemassa olevia asiakastietoja. Tämä vaihe hyödyntää [Data binding](/docs/data-binding/overview) -menetelmää, joka yhdistää käyttöliittymäkomponentit suoraan tietomalliin automaattista arvon synkronointia varten. Tämä vähentää boilerplate-koodia sovelluksessasi ja antaa sinun lisätä validointitarkistuksia Spring-entiteetille `Customer`, jolloin käyttäjät antavat täydellisiä ja tarkkoja tietoja täyttäessään lomakkeita. Tämä vaihe kattaa seuraavat käsitteet:

- [Jakarta validation](https://beanvalidation.org)
- Käyttämällä [`BindingContext`](https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html) -luokkaa

Tämän vaiheen suorittaminen luo version [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Sovelluksen suorittaminen {#running-the-app}

Sovellustasi kehittäessäsi voit käyttää [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) -versiona vertailukohtana. Näet sovelluksen toiminnassa:

1. Siirry ykkösluokan hakemistoon, joka sisältää `pom.xml`-tiedoston, tämä on `5-validating-and-binding-data`, jos seuraat GitHubin versiota.

2. Käytä seuraavaa Maven-komentoa suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selaimen osoitteessa `http://localhost:8080`.

## Validointisääntöjen määrittäminen {#defining-validation-rules}

Muokattavalla datalla olevan sovelluksen kehittämiseen tulisi sisältyä validointi. Validointitarkistukset auttavat ylläpitämään merkityksellisiä ja tarkkoja käyttäjien lähettämiä tietoja. Jos niitä ei tarkisteta, se voi johtaa ongelmiin, joten on tärkeää saada kiinni niiden virheiden tyypit, joita käyttäjät voivat tehdä täyttäessään lomaketta reaaliajassa.

Koska se, mikä katsotaan voimassa olevaksi, voi vaihdella ominaisuuksien välillä, sinun on määritettävä, mikä tekee jokaisesta ominaisuudesta voimassa olevan ja ilmoitettava käyttäjälle, jos jokin asia on virheellinen. Onneksi voit tehdä tämän helposti [Jakarta Validation](https://beanvalidation.org) -työkalulla. Jakarta validointi mahdollistaa rajoitusten lisäämisen ominaisuuksiin annotaatioina.

Tässä oppaassa käytetään kahta Jakarta-annotaatiota: `@NotEmpty` ja `@Pattern`. `@NotEmpty` tarkistaa null- ja tyhjät merkkijonot, kun taas `@Pattern` tarkistaa, vastaako ominaisuus asetettua säännöllistä ilmausta. Molemmat annotaatiot antavat sinun lisätä viestin näytettäväksi, kun ominaisuus muuttuu virheelliseksi.

Vaatiaksesi, että sekä etu- että sukunimet ovat pakollisia ja sisältävät vain kirjaimia, kun taas yritysnimi on valinnainen ja sallii kirjaimet, numerot ja välilyönnit, lisää seuraavat annotaatiot `Customer`-entiteettiin:

```java
@Entity
@Table(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Asiakkaan etunimi on pakollinen")
  @Pattern(regexp = "[a-zA-Z]*", message = "Virheelliset merkit")
  private String firstName = "";

  @NotEmpty(message = "Asiakkaan sukunimi on pakollinen")
  @Pattern(regexp = "[a-zA-Z]*", message = "Virheelliset merkit")
  private String lastName = "";

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
```

Tutustu [Jakarta Bean Validation -rajoitteiden viittaukseen](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) saadaksesi täydellisen listan validoitavista, tai opi lisää [webforJ Jakarta Validation -artikkelista](/docs/data-binding/validation/jakarta-validation).

## Kenttien sitominen {#binding-the-fields}

Käyttääksesi validointitarkistuksia `Customer`-luokassa käyttöliittymässä `FormView`-komponentissa, sinun täytyy luoda `BindingContext` tietojen sitomista varten. Ennen tietojen sitomista jokainen `FormView`-kenttä tarvitsi tapahtumakuuntelijan synkronoimaan manuaalisesti Spring-entiteetin `Customer` kanssa. `BindingContext`-luokan luominen `FormView`:ssä sitoo ja synkronoi automaattisesti `Customer`-tietomallin käyttöliittymäkomponenttien kanssa.

### `BindingContext`-luokan luominen {#creating-a-bindingcontext}

`BindingContext`-instanssi tarvitsee Spring-beanin, jonka kanssa sidonta synkronoidaan. `FormView`:ssä määrittele `BindingContext`, käyttäen `Customer`-entiteettiä:

```java title="FormView.java" {4}
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Sitten, jotta käyttöliittymän komponentit sitoutuvat automaattisesti bean-ominaisuuksiin nimien perusteella, käytä `BindingContext.of()` seuraavilla parametreilla:

- **`this`** : Aiemmin määrittelit `context`-muuttujan `BindingContext`-luokkana. Ensimmäinen parametri määrittää, mikä objekti sisältää sidottavat komponentit.
- **`Customer.class`** : Toinen parametri on sidottavan beanin luokka.
- **`true`** : Kolmas parametri aktivoi Jakarta validoinnin, jolloin konteksti voi käyttää määrittämiäsi validointeja `Customer`-luokassa. Tämän tekeminen muuttaa virheellisten komponenttien tyyliä ja näyttää asetetut viestit.

Kaikki yhdessä näyttää seuraavalta koodiriviltä:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Lomakkeen responsiivisuuden lisääminen {#making-the-form-responsive}

Tietojen sitomisen ansiosta sovelluksesi suorittaa nyt automaattisesti validointitarkistuksia. Lisäämällä tapahtumakuuntelijan tarkistuksiin voit estää käyttäjiä lähettämästä virheellistä lomaketta. Lisää seuraava koodi, jotta lähetä-nappi on aktiivinen vain, kun lomake on voimassa:

```java {2}
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Tapahtumakuuntelijoiden poistaminen komponenteista {#removing-event-listeners-for-components}

Jokainen käyttöliittymän muutos synkronoidaan nyt automaattisesti `BindingContext`-luokan kanssa. Tämä tarkoittaa, että voit nyt poistaa tapahtumakuuntelijat jokaiselta kentältä:

**Ennen**
```java title="FormView.java"
// Ilman tietojen sitomista
TextField firstName = new TextField("Etunimi", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Sukunimi", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Yritys", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Maa",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Jälkeen**
```java title="FormView.java"
// Tietojen sitomisen kanssa
TextField firstName = new TextField("Etunimi");
TextField lastName = new TextField("Sukunimi");
TextField company = new TextField("Yritys");
ChoiceBox country = new ChoiceBox("Maa");
```

### Sitominen ominaisuuden nimien mukaan {#binding-by-property-names}

Koska jokaisen komponentin nimi vastaa tietomallia, webforJ sovelsi [Automatic Binding](/docs/data-binding/automatic-binding). Jos nimet eivät vastanneet, voit käyttää `@UseProperty`-annotaatiota kartuttaaksesi niitä.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Etunimi");
```

### Datan lukeminen `fillForm()`-metodissa {#reading-data-in-the-fillForm()-method}

Aiemmin `fillForm()`-metodissa alustit jokaisen komponentin arvon manuaalisesti hakemalla tietoja `Customer`-kopiosta. Mutta nyt, kun käytät `BindingContext`-luokkaa, voit käyttää `read()`-metodia. Tämä metodi täyttää jokaisen sidotun komponentin siihen liittyvällä ominaisuudella `Customer`-kopiosta.

`fillForm()`-metodissa voit korvata `setValue()`-metodit `read()`-metodilla:

```java title="FormView.java" {6}
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

    context.read(customer);
  }
```

### Validoinnin lisääminen `submitCustomer()` {#adding-validation-to-submitcustomer}

Viimeinen muutos `FormView`-komponentille tässä vaiheessa on lisätä suojaus `submitCustomer()`-metodiin. Ennen muutosten sitomista H2-tietokantaan, sovellus suorittaa lopullisen validoinnin sidotun kontekstiin palautettavien tulosten käyttämiseksi `write()`-metodilla.

`write()`-metodi päivittää beanin ominaisuuksia käyttäen sidottuja käyttöliittymäkomponentteja `BindingContext`-luokassa ja palauttaa `ValidationResult`-arvon.

Käytä `write()`-metodia kirjoittaaksesi `Customer`-kopioon käyttäen `FormView`:ssä sidottuja komponentteja. Jos palautettu `ValidationResult` on voimassa, päivitä H2-tietokanta kirjoitetuilla tiedoilla.

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

Näiden muutosten myötä tässä on miltä `FormView` näyttää. Sovelluksesi tukee nyt tietojen sitomista ja validointia käyttäen Spring Bootia ja webforJ:tä. Lomakekentät on automaattisesti synkronoitu mallin kanssa ja tarkistettu validointisääntöjen mukaan.

## Seuraava vaihe {#next-step}

Seuraavassa vaiheessa, [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout), keskitytään käyttämään `AppLayout`-komponenttia, joka lisää sivuvalikon, joka on käytettävissä asiakas-taulukko- ja asiakas-lomakesivuilla. Opit myös toisesta asettelutyökalusta, `FlexLayout`-komponentista.
