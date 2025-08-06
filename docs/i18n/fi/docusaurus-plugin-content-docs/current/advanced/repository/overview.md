---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: 8dfc90f24bba893de434f1a41d5776c6
---
<!-- vale off -->
# Repositorio <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-malli webforJ:ssä tarjoaa standardisoidun tavan hallita ja kysellä kokoelmia entiteettejä. Se toimii abstraktiokerroksena UI-komponenttienne ja datan välillä, mikä helpottaa erilaisten tietolähteiden käsittelyä samalla, kun käytös pysyy johdonmukaisena.

## Miksi käyttää repositoriota {#why-use-repository}

`Repository` poistaa manuaaliset päivitykset pitäen alkuperäiset tietosi ehjinä:

```java
// Ilman Repository - manuaaliset päivitykset
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Lisääminen vaatii täyden uudelleenlatauksen
customers.add(newCustomer);
table.setItems(customers); // Kaikki täytyy ladata uudelleen
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

## Kokoelmarepositorio {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> on yleisin toteutus ja se käärii minkä tahansa Java-kokoelman:

```java
// ArrayListista
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// HashSetista  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Mistä tahansa kokoelmasta
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## Datan synkronointi {#data-synchronization}

`Repository` toimii silta datasi ja UI-komponenttien välillä. Kun data muuttuu, ilmoitat repositorille `commit()`-metodin kautta:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Lisää uusi tuote
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Kaikki liitetyt komponentit päivittyvät

// Päivitä olemassa oleva tuote  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Päivittää vain tämän rivin

// Poista tuote
products.remove(2);
repository.commit(); // Päivittää näkymän
```

Commit-metodilla on kaksi allekirjoitusta:
- `commit()` - Käsketään repositorille päivittää kaikki. Se laukaisee `RepositoryCommitEvent`-tapahtuman kaikilla nykyisillä tiedoilla
- `commit(entity)` - Kohdistaa erityiseen entiteettiin. Repositorio löytää tämän entiteetin sen avaimen avulla ja päivittää vain vaikuttavat UI-elementit

:::important Yksittäisten entiteettien sitominen
Tämä ero on tärkeä suorituskyvyn kannalta. Kun päivität yhden kentän 1000 rivin taulukossa, `commit(entity)` päivittää vain sen solun, kun taas `commit()` päivittäisi kaikki rivit.
:::

## Datan suodatus {#filtering-data}

Repositorion suodatin ohjaa, mitä dataa virtaa liitettyihin komponentteihin. Peruskokoelmasi pysyy muuttumattomana, koska suodatin toimii linssinä:

```java
// Suodata varastotilan mukaan
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
1. Soveltaa predikaattia jokaiselle kokoelman kohteelle
2. Luo suodatetun virran vastaavista kohteista
3. Ilmoittaa liitetyille komponenteille päivittää näyttönsä

Suodatin pysyy voimassa, kunnes muutat sen. Uudet kokoelmaan lisätyt kohteet testataan automaattisesti nykyistä suodatinta vasten.

## Työskentely entiteettien avainarvojen kanssa {#working-with-entity-keys}

Kun entiteettisi toteuttavat <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>, repositorio voi löytää ja päivittää erityisiä kohteita niiden ID:n perusteella:

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

Ilman `HasEntityKey`:
- `repository.find("C001")` ei löydä asiakastasi, koska se etsii objektia, joka on yhtä kuin "C001"
- `repository.commit(entity)` toimii edelleen, mutta luottaa objektin yhtä suurista
- UI-komponentit eivät voi valita kohteita ID:n mukaan vaan vain objektiviittauksen perusteella

## UI-integraatio {#ui-integration}

`Repository` integroidaan datatietoisiin komponentteihin:

```java
// Luo repositorio ja taulukko
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nimi", Customer::getName);
table.addColumn("Sähköposti", Customer::getEmail);

// Lisää data - taulukko päivittyy automaattisesti
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Seuraavat vaiheet {#next-steps}

<DocCardList className="topics-section" />
