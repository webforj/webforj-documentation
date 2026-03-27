---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: c285de15c19063af40c61f15cebf4dc1
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-malli webforJ:ssä tarjoaa standardoidun tavan hallita ja kysyä kokoelmia entiteeteistä. Se toimii abstraktiokerroksena UI-komponenttiesi ja datan välillä, mikä tekee erilaisten tietolähteiden käytöstä helppoa samalla kun käyttäytyminen pysyy johdonmukaisena.

## Miksi käyttää repositorya {#why-use-repository}

`Repository` eliminoi manuaaliset päivitykset pitäen alkuperäiset datat ehjänä:

```java
// Ilman Repositorya - manuaaliset päivitykset
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Lisäys vaatii täydellisen latauksen
customers.add(newCustomer);
table.setItems(customers); // Täytyy ladata kaikki uudelleen
```

```java
// Repositoryn kanssa - automaattinen synkronointi
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Lisäys synkronoituu automaattisesti
customers.add(newCustomer);
repository.commit(newCustomer); // Päivittää vain muuttuneet
```

## Kokoelman repository {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> on yleisin toteutus ja se käärii minkä tahansa Java Collectionin:

```java
// ArrayListista
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// HashSetistä  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Minkä tahansa kokoelman kautta
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## Datan synkronointi {#data-synchronization}

`Repository` toimii siltana datasi ja UI-komponenttien välillä. Kun data muuttuu, ilmoitat repositorylle `commit()`-metodin kautta:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Lisää uusi tuote
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Kaikki liitetyt komponentit päivittyvät

// Päivitä olemassa oleva tuote  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Päivittää vain tämän tietyn rivin

// Poista tuote
products.remove(2);
repository.commit(); // Päivittää näkymän
```

Commit-metodilla on kaksi allekirjoitusta:
- `commit()` - Ilmoittaa repositorylle, että kaikkea on päivitettävä. Se laukaisee `RepositoryCommitEvent`:in kaikkien nykyisten tietojen kanssa
- `commit(entity)` - Tavoittaa tietyn entiteetin. Repository löytää tämän entiteetin sen avaimen perusteella ja päivittää vain vaikuttavat UI-elementit

:::important Yksittäisten entiteettien sitominen
Tämä ero on tärkeä suorituskyvyn kannalta. Kun päivität yhden kentän 1000 rivin taulukossa, `commit(entity)` päivittää vain sen solun, kun taas `commit()` päivittää kaikki rivit.
:::

## Datan suodatus {#filtering-data}

Repositoryn suodatin hallitsee sitä, mitä dataa välitetään liitettyihin komponentteihin. Alkuperäinen kokoelmasi pysyy muuttumattomana, koska suodatin toimii linssinä:

```java
// Suodata varaston saatavuuden mukaan
repository.setBaseFilter(product -> product.getStock() > 0);

// Suodata kategoriakohtaisesti
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
1. Soveltaa ehdon jokaiselle kohteelle kokoelmassasi
2. Luo suodatetun streamin vastaavista kohteista
3. Ilmoittaa liitetyille komponenteille, että niiden näyttö on päivitettävä

Suodatin pysyy voimassa, kunnes muutat sen. Uudet kokoelmaan lisätyt kohteet testataan automaattisesti nykyisen suodattimen perusteella.

## Työskentely entiteettiavainten kanssa {#working-with-entity-keys}

Repository tarvitsee pystyäkseen tunnistamaan entiteetit ainutlaatuisesti tukeakseen toimintoja kuten `find()` ja `commit(entity)`. On kaksi tapaa määrittää, miten entiteettejä tunnistetaan:

### Käyttämällä `HasEntityKey`-rajapintaa {#using-hasentitykey}

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

    // Konstruktori ja gettterit/setterit...
}

// Etsi avaimen mukaan
Optional<Customer> customer = repository.find("C001");

// Päivitä tietty asiakas
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Vain tämän asiakkaan rivi päivittyy
});
```

### Käyttämällä mukautettua avainhallintaa <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Entiteeteille, joissa et voi tai et halua toteuttaa `HasEntityKey`:ta (kuten JPA-entiteetit), käytä `setKeyProvider()`:

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // JPA-hallittu entiteetti
}

// Määritä repository käyttämään getId()-metodia
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Nyt haku toimii ID:llä
Optional<Product> product = repository.find(123L);
```

### Lähestymistavan valinta {#choosing-approach}

Molemmat lähestymistavat toimivat, mutta `setKeyProvider()` on suositeltavampi silloin, kun:
- Työskentelet JPA-entiteettien kanssa, joilla on `@Id`-kenttiä
- Et voi muuttaa entiteettiklassia
- Tarvitset erilaisia avainstrategioita eri repositorioille

Käytä `HasEntityKey`a silloin, kun:
- Hallitset entiteettiklassia
- Avaimen poimintalogiikka on monimutkainen
- Haluat entiteetin määrittävän oman identiteettinsä

## UI-integraatio {#ui-integration}

`Repository` integroituu datatietoisiin komponentteihin:

```java
// Luo repository ja taulukko
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

## Seuraavat askeleet {#next-steps}

<DocCardList className="topics-section" />
