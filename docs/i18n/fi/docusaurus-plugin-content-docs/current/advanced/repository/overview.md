---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: b9a08226a8ace111beea4ab2a03ff79f
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-malli webforJ:ssä tarjoaa standardoidun tavan hallita ja kysyä entiteettikokoelmia. Se toimii abstraktiokerroksena käyttöliittymäkomponenttiesi ja datan välillä, mikä helpottaa erilaisten tietolähteiden kanssa työskentelyä säilyttäen samalla johdonmukaisen käyttäytymisen.

## Miksi käyttää repositorya {#why-use-repository}

`Repository` eliminoi manuaaliset päivitykset samalla pitäen alkuperäisen datan muuttumattomana:

```java
// Ilman Repositorya - manuaaliset päivitykset
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Lisääminen vaatii täydellisen lataamisen
customers.add(newCustomer);
table.setItems(customers); // Kaikki täytyy ladata uudelleen
```

```java
// Repositoryn kanssa - automaattinen synkronointi
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Lisääminen synkronoi automaattisesti
customers.add(newCustomer);
repository.commit(newCustomer); // Vain mitä on muuttunut
```

## Kokoelmarepository {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> on yleisin toteutus, ja se käärii minkä tahansa Java-kokoelman:

```java
// ArrayListista
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// HashSetistä  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Mistä tahansa kokoelmasta
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## Datan synkronointi {#data-synchronization}

`Repository` toimii siltoina datasi ja käyttöliittymäkomponenttiesi välillä. Kun data muuttuu, ilmoitat repositorylle `commit()`-menetelmää käyttämällä:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Lisää uusi tuote
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Kaikki liitetyt komponentit päivittyvät

// Päivitä olemassa oleva tuote  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Vain tämä tietty rivi päivittyy

// Poista tuote
products.remove(2);
repository.commit(); // Päivittää näkymän
```

Commit-menetelmällä on kaksi allekirjoitusta:
- `commit()` - Kertoo repositorylle, että kaikessa tulee päivittää. Se laukaisee `RepositoryCommitEvent`:n kaikella nykyisellä datalla
- `commit(entity)` - Kohdistaa tiettyyn entiteettiin. Repository löytää tämän entiteetin sen avaimen mukaan ja päivittää vain vaikuttavat käyttöliittymäelementit

:::important Yksittäisten entiteettien vahvistaminen
Tämä erottelu on tärkeä suorituskyvyn kannalta. Kun päivität yhden kentän 1000 rivin taulukossa, `commit(entity)` päivittää vain sen solun, kun taas `commit()` päivittäisi kaikki rivit.
:::

## Datan suodattaminen {#filtering-data}

Repositoryn suodatin hallitsee, mitä dataa siirtyy liitettyihin komponentteihin. Alkuperäinen kokoelmasi pysyy muuttumattomana, koska suodatin toimii objektiivina:

```java
// Suodata varastontilanteen mukaan
repository.setBaseFilter(product -> product.getStock() > 0);

// Suodata kategorian mukaan
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Yhdistä useita ehtoja
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Poista suodatin
repository.setBaseFilter(null);
```

Kun asetat suotimen, `Repository`:
1. Soveltaa ennustetta jokaiseen kokoelman kohteeseen
2. Luo suodatetun virran vastaavista kohteista
3. Ilmoittaa liitetyille komponenteille, että niiden näyttö tulee päivittää

Suodatin pysyy voimassa, kunnes muutat sen. Uudet kohteet, jotka lisätään kokoelmaan, testataan automaattisesti nykyistä suodatinta vastaan.


## Työskentely entiteettiavaimilla {#working-with-entity-keys}

Repository tarvitsee tunnistaa entiteetit ainutlaatuisesti tukemaan toimintoja, kuten `find()` ja `commit(entity)`. On kaksi tapaa määrittää, miten entiteettejä identifioidaan:

### HasEntityKey-rajapinnan käyttäminen {#using-hasentitykey}

Toteuta <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> entiteettiklassissasi:

```java
public class Customer implements HasEntityKey {
    private String customerId;
    private String name;
    private String email;

    @Override
    public Object getEntityKey() {
        return customerId;
    }

    // Konstruktori ja getterit/setterit...
}

// Etsi avaimen mukaan
Optional<Customer> customer = repository.find("C001");

// Päivitä tietty asiakas
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Vain tämän asiakkaan rivi päivittyy
});
```

### Mukautetun avaimen tarjoajan käyttö <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Entiteeteille, joissa et voi tai et halua toteuttaa `HasEntityKey`:a (kuten JPA-entiteetit), käytä `setKeyProvider()`:

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // JPA-hallittu entiteetti
}

// Määrittele repository käyttämään getId()-metodia
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Nyt haku toimii ID:llä
Optional<Product> product = repository.find(123L);
```

### Lähestymistavan valitseminen {#choosing-approach}

Molemmat lähestymistavat toimivat, mutta `setKeyProvider()` on suositeltava, kun:
- Työskentelet JPA-entiteettien kanssa, joilla on `@Id`-kenttiä
- Et voi muuttaa entiteettiklassia
- Tarvitset erilaisia avainestrategioita eri repositorien varten

Käytä `HasEntityKey`:a, kun:
- Hallitset entiteettiklassia
- Avaimen erottelulogiikka on monimutkaista
- Halut, että entiteetti määrittelee oman identiteettinsä


## Käyttöliittymän integrointi {#ui-integration}

`Repository` integroidaan datatietoisiksi komponentteiksi:

```java
// Luo repository ja taulukko
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nimi", Customer::getName);
table.addColumn("Sähköposti", Customer::getEmail);

// Lisää dataa - taulukko päivittyy automaattisesti
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Seuraavat askeleet {#next-steps}

<DocCardList className="topics-section" />
