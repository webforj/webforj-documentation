---
title: Validating and Binding Data
sidebar_position: 6
pagination_next: null
description: Step 5 - Add validation checks and bind data to the UI.
_i18n_hash: b99d289f94de614d85524e9846bdcd92
---
Sovelluksesi, joka perustuu [Observers and Route Parameters](/docs/introduction/tutorial/observers-and-route-parameters), voi käyttää `FormView`-komponenttia olemassa olevan asiakastiedon muokkaamiseen. Tämä vaihe käyttää [Data binding](/docs/data-binding/overview) -toimintoa, joka yhdistää käyttöliittymäkomponentit suoraan tietomalliin automaattista arvon synkronointia varten. Tämä vähentää ylimääräistä koodia sovelluksessasi ja mahdollistaa validointitarkistusten lisäämisen Spring-entiteettiin `Customer`, mikä pakottaa käyttäjät antamaan täydellisiä ja tarkkoja tietoja lomakkeita täyttäessään. Tämä vaihe kattaa seuraavat käsitteet:

- [Jakarta validation](https://beanvalidation.org)
- `BindingContext`-luokan käyttö [https://javadoc.io/doc/com.webforj/webforj-data/latest/com/webforj/data/binding/BindingContext.html]

Tämän vaiheen suorittaminen luo version [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data).

## Sovelluksen suorittaminen {#running-the-app}

Sovellustasi kehitettäessä voit käyttää [5-validating-and-binding-data](https://github.com/webforj/webforj-tutorial/tree/main/5-validating-and-binding-data) -projektia vertailuna. Näet sovelluksen toiminnassa:

1. Siirry ylimpään hakemistoon, joka sisältää `pom.xml`-tiedoston. Tämä on `5-validating-and-binding-data`, jos seuraat GitHubissa olevaa versiota.

2. Suorita seuraava Maven-komento suorittaaksesi Spring Boot -sovelluksen paikallisesti:
    ```bash
    mvn
    ```

Sovelluksen suorittaminen avaa automaattisesti uuden selainikkunan osoitteessa `http://localhost:8080`.

## Validointisääntöjen määrittäminen {#defining-validation-rules}

Muokattavilla tiedoilla varustetun sovelluksen kehittämiseen tulisi sisältyä validointia. Validointitarkistukset auttavat ylläpitämään merkityksellisiä ja tarkkoja käyttäjien syöttämiä tietoja. Jos tarkistuksia ei tehdä, se voi johtaa ongelmiin, joten on tärkeää havaita ne virheiden tyypit, joita käyttäjät voivat tehdä lomakkeen täyttämisen aikana reaaliaikaisesti.

Koska se, mikä on voimassa, voi vaihdella ominaisuuksien välillä, sinun on määritettävä, mikä tekee kustakin ominaisuudesta kelvollisen ja ilmoitettava käyttäjälle, jos jotain on virheellistä. Onneksi voit tehdä tämän helposti [Jakarta Validation](https://beanvalidation.org) -toiminnolla. Jakarta validointi mahdollistaa rajoitteiden lisäämisen ominaisuuksiin annotaatioiden avulla.

Tässä oppaassa käytetään kahta Jakarta-annotaatioita, `@NotEmpty` ja `@Pattern`. `@NotEmpty` tarkistaa, onko arvot null tai tyhjä merkkijono, kun taas `@Pattern` tarkistaa, vastaako ominaisuus määrittämääsi säännöllistä ilmaisua. Molemmat annotaatiot antavat sinun lisätä viestin, joka näytetään, kun ominaisuus tulee virheelliseksi.

Vaatiaksesi, että sekä etunimet että sukunimet ovat pakollisia ja sisältävät vain kirjaimia, samalla kun yritysnimi on valinnainen ja sallii kirjaimet, numerot ja välilyönnit, käytä seuraavia annotaatioita `Customer`-entiteetille:

```java
@Entity
  @Table(name = "customers")
  public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Asiakkaan etunimi on pakollinen")
    @Pattern(regexp = "[a-zA-Z]*", message = "Virheellisiä merkkejä")
    private String firstName = "";

    @NotEmpty(message = "Asiakkaan sukunimi on pakollinen")
    @Pattern(regexp = "[a-zA-Z]*", message = "Virheellisiä merkkejä")
    private String lastName = "";

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
```

Katso [Jakarta Bean Validation constraints reference](https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary.html) saadaksesi täydellinen luettelo validointisäännöistä, tai lue lisää [webforJ Jakarta Validation -artikkelista](/docs/data-binding/validation/jakarta-validation).

## Kenttien sitominen {#binding-the-fields}

Käyttääksesi `Customer`-validointitarkistuksia käyttöliittymässä `FormView`, sinun on luotava `BindingContext` tietosidontaa varten. Ennen tietosidontaa jokaiselle `FormView`:n kentälle tarvittiin tapahtumakuuntelija synkronoimiseen Spring-entiteetti `Customer`:in kanssa manuaalisesti. `BindingContext` luominen `FormView`:ssa sitoo ja synkronoi automaattisesti `Customer`-tietomallin käyttöliittymäkomponentteihin.

### `BindingContext`-instanssin luominen {#creating-a-bindingcontext}

`BindingContext`-instanssi tarvitsee Spring-beanin, jonka kanssa sitomiset synkronoidaan. `FormView`:ssa määrittele `BindingContext`, käyttäen `Customer`-entiteettiä:

```java
public class FormView extends Composite<Div> implements WillEnterObserver {
  private final CustomerService customerService;

  private BindingContext<Customer> context;

  Customer customer = new Customer();
```

Käytä sitten automaattisesti sitomaan käyttöliittymäkomponentit bean-ominaisuuksiin niiden nimien mukaan käyttämällä `BindingContext.of()` seuraavilla parametreilla:

- **`this`** : Aiemmin määrittelit `context`-muuttujan `BindingContext`:ina. Ensimmäinen parametri asettaa, mikä objekti sisältää sidottavat komponentit.
- **`Customer.class`** : Toinen parametri on bindattavan beanin luokka.
- **`true`** : Kolmas parametri mahdollistaa Jakarta-validoinnin, mikä sallii kontekstin käyttää asettamiasi validointeja `Customer`:lle. Tämä muuttaa virheellisten komponenttien tyyliä ja näyttää asetetut viestit.

Kaiken kaikkiaan se näyttää seuraavalta koodiriviltä:

```java
context = BindingContext.of(this, Customer.class, true);
```

### Lomakkeen responsiivisuuden tekeminen {#making-the-form-responsive}

Tietosidonnan avulla sovelluksesi suorittaa nyt automaattisesti validointitarkistuksia. Lisäämällä tapahtumakuuntelijan tarkistuksiin voit estää käyttäjiä lähettämästä virheellistä lomaketta. Lisää seuraava, jotta "lähetä" -painike on aktiivinen vain, kun lomakkeen tiedot ovat voimassa:

```java
context = BindingContext.of(this, Customer.class, true);
context.onValidate(e -> submit.setEnabled(e.isValid()));
```

### Tapahtumakuuntelijoiden poistaminen komponenteista {#removing-event-listeners-for-components}

Jokainen käyttöliittymän muutos synkronoidaan nyt automaattisesti `BindingContext`:in kanssa. Tämä tarkoittaa, että voit nyt poistaa tapahtumakuuntelijat jokaiselta kentältä:

**Ennen**
```java
// Ilman tietosidontaa
TextField firstName = new TextField("Etunimi", e -> customer.setFirstName(e.getValue()));
TextField lastName = new TextField("Sukunimi", e -> customer.setLastName(e.getValue()));
TextField company = new TextField("Yritys", e -> customer.setCompany(e.getValue()));
ChoiceBox country = new ChoiceBox("Maa",
    e -> customer.setCountry(Country.valueOf(e.getSelectedItem().getText())));
```

**Jälkeen**
```java
// Tietosidonnalla
TextField firstName = new TextField("Etunimi");
TextField lastName = new TextField("Sukunimi");
TextField company = new TextField("Yritys");
ChoiceBox country = new ChoiceBox("Maa");
```

### Sitominen ominaisuuden nimien mukaan {#binding-by-property-names}

Koska jokaisen komponentin nimi vastasi tietomallia, webforJ sovelsi [Automaattista sitomista](/docs/data-binding/automatic-binding). Jos nimet eivät olisi kohdanneet, voisit käyttää `@UseProperty`-annotaatiota yhdistääksesi ne.

```java
@UseProperty("firstName")
TextField firstNameField = new TextField("Etunimi");
```

### Datan lukeminen `fillForm()`-metodissa {#reading-data-in-the-fillForm()-method}

Aiemmin `fillForm()`-metodissa aloitit jokaisen komponentin arvon alustamisen hakemalla tiedot manuaalisesti `Customer`-kopiosta. Nyt, koska käytät `BindingContext`:ia, voit käyttää `read()`-metodia. Tämä metodi täyttää jokaisen sidotun komponentin tietojen mukaisella ominaisuudella `Customer`-kopiosta.

`fillForm()`-metodissa vaihda `setValue()`-metodit `read()`-metodiksi:

```java
public void fillForm(Long customerId) {
  customer = customerService.getCustomerByKey(customerId);

  // Poistettu jokainen setValue() -metodi käyttöliittymäkomponenteilta

    context.read(customer);
  }
```

### Validoinnin lisääminen `submitCustomer()`-metodiin {#adding-validation-to-submitcustomer}

Viimeinen muutos `FormView`:lle tässä vaiheessa on lisätä turva `submitCustomer()`-metodiin. Ennen tietojen tallentamista H2-tietokantaan sovellus suorittaa lopullisen validoinnin sidotuille tuloksille `write()`-metodia käyttäen.

`write()`-metodi päivittää beanin ominaisuudet käyttäen käyttöliittymäkomponentteihin sidottuja tietoja `BindingContext`:issa ja palauttaa `ValidationResult`-tuloksen.

Käytä `write()`-metodia kirjoittaaksesi tiedot `Customer`-kopioon käyttäen sidottuja komponentteja `FormView`:ssä. Sitten, jos palautettu `ValidationResult` on voimassa, päivitä H2-tietokanta kirjoitetuilla tiedoilla.

```java
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

Näiden muutosten myötä `FormView` näyttää tältä. Sovellus tukee nyt tietosidontaa ja validointia Spring Bootin ja webforJ:n avulla. Lomakkeen syötteet synkronoidaan automaattisesti mallin kanssa ja tarkistetaan validointisäännöillä.

## Seuraava vaihe {#next-step}

Seuraava vaihe, [Integrating an App Layout](/docs/introduction/tutorial/integrating-an-app-layout), keskittyy käyttämään `AppLayout`:ia lisätäkseen sivuvalikon, joka on käyttäjien käytettävissä sekä asiakas-taulukon että asiakaslomakesivujen osalta. Opit myös toisesta asettelutyökalusta, `FlexLayout`-komponentista.
