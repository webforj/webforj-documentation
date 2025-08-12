---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: dc6f7bbfe82d68565cbe8da6436f080c
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-malli webforJ:ssa tarjoaa standardoidun tavan hallita ja kysyä kokoelmia entiteeteistä. Se toimii abstraktiokerroksena käyttöliittymäkomponenttiesi ja tietojen välillä, jolloin eri tietolähteiden kanssa työskentely on helppoa pitäen samalla johdonmukaisen käyttäytymisen.

## Miksi käyttää repositorya {#why-use-repository}

`Repository` poistaa manuaaliset päivitykset säilyttäen alkuperäiset tietosi ehjinä:

```java
// Ilman Repositorya - manuaaliset päivitykset
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Lisääminen edellyttää täyden uudelleenlatauksen
customers.add(newCustomer);
table.setItems(customers); // On ladattava kaikki uudelleen
```

```java
// Repositoryn kanssa - automaattinen synkronointi
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Lisääminen synkronoituu automaattisesti
customers.add(newCustomer);
repository.commit(newCustomer); // Päivittää vain muutetut
```

## Kokoelma repository {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> on yleisin toteutus ja se kääritsee minkä tahansa Java-kokoelman:

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

`Repository` toimii sillan tavoin tietosi ja käyttöliittymäkomponenttien välillä. Kun tiedot muuttuvat, ilmoitat repositorylle `commit()`-metodin kautta:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Lisää uusi tuote
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Kaikki kytketyt komponentit päivittävät

// Päivitä olemassa oleva tuote  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Päivittää vain tämän erityisen rivin

// Poista tuote
products.remove(2);
repository.commit(); // Uudistaa näkymän
```

Commit-metodilla on kaksi allekirjoitusta:
- `commit()` - Kehottaa repositoryä päivittämään kaiken. Se laukaisee `RepositoryCommitEvent`:in kaikilla nykyisillä tiedoilla.
- `commit(entity)` - Kohdistaa erityiseen entiteettiin. Repository löytää tämän entiteetin sen avaimen perusteella ja päivittää vain vaikuttavat käyttöliittymäelementit.

:::important Yksittäisten entiteettien sitominen
Tämä erottelu on tärkeä suorituskyvyn kannalta. Kun päivität yhden kentän 1000 rivin taulukossa, `commit(entity)` päivittää vain tämän solun, kun taas `commit()` päivittäisi kaikki rivit.
:::

## Datan suodatus {#filtering-data}

Repositoryn suodatin kontrolloi, mitkä tiedot kulkevat kytkettyihin komponentteihin. Taustalla oleva kokoelma pysyy muuttumattomana, koska suodatin toimii linssinä:

```java
// Suodata varastotilanteen mukaan
repository.setBaseFilter(product -> product.getStock() > 0);

// Suodata kategorian mukaan
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Yhdistä useita ehtoja
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Tyhjennä suodatin
repository.setBaseFilter(null);
```

Kun asetat suodattimen, `Repository`:
1. Soveltaa ehtoa jokaiseen kohteeseen kokoelmassasi
2. Luo suodatetun virran vastaavista kohteista
3. Ilmoittaa kytkettyille komponenteille, että niiden näyttö on päivitettävä

Suodatin pysyy voimassa, kunnes muutat sitä. Uudet kokoelmaan lisätyt kohteet testataan automaattisesti nykyistä suodatinta vastaan.

## Työskentely entiteettiavainten kanssa {#working-with-entity-keys}

Kun entiteettisi toteuttavat <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>, repository voi löytää ja päivittää erityisiä kohteita niiden ID:n perusteella:

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

// Päivitä erityinen asiakas
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Vain tämän asiakkaan rivi päivittyy
});
```

Ilman `HasEntityKey`:ia:
- `repository.find("C001")` ei löydä asiakastasi, koska se etsii objektia, joka on yhtä kuin "C001"
- `repository.commit(entity)` toimii silti, mutta riippuu objektin tasa-arvosta
- Käyttöliittymäkomponentit eivät voi valita kohteita ID:n mukaan, vain objektiviittauksen perusteella

## Käyttöliittymän integrointi {#ui-integration}

`Repository` integroituu datasta tietoisiin komponentteihin:

```java
// Luo repository ja taulukko
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nimi", Customer::getName);
table.addColumn("Sähköposti", Customer::getEmail);

// Lisää tietoja - taulukko päivittyy automaattisesti
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Seuraavat vaiheet {#next-steps}

<DocCardList className="topics-section" />
