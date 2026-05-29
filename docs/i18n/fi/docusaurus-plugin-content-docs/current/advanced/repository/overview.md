---
title: Repository
sidebar_position: 1
_i18n_hash: 455b667132d3c9693257eb74671412c5
---
<!-- vale off -->
# Repositorio <DocChip chip='since' label='24.00' />
<!-- vale on -->


`Repository`-malli webforJ:ssä tarjoaa standardoidun tavan hallita ja kysyä kokoelmaa entiteettejä. Se toimii abstraktiokerroksena UI-komponenttien ja datan välillä, mikä helpottaa työskentelyä eri tietolähteiden kanssa säilyttäen johdonmukaisen käyttäytymisen.

## Miksi käyttää repositorya {#why-use-repository}

`Repository` poistaa manuaaliset päivitykset ja pitää alkuperäiset tiedot ehjinä:

```java
// Ilman Repositorya - manuaaliset päivitykset
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Lisääminen vaatii kokonaisuuden lataamista
customers.add(newCustomer);
table.setItems(customers); // Kaikki on ladattava uudelleen
```

```java
// Repositoryn kanssa - automaattinen synkronointi
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Lisääminen synkronoi automaattisesti
customers.add(newCustomer);
repository.commit(newCustomer); // Päivittää vain muutoksen
```


## Kokoelma-repository {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> on yleisin toteutus, ja se käärii minkä tahansa Java-kokoelman:

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

`Repository` toimii silta datasi ja UI-komponenttien välillä. Kun data muuttuu, ilmoitat repositorylle `commit()`-metodin kautta:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Lisää uusi tuote
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Kaikki liitetyt komponentit päivitetään

// Päivitä olemassa oleva tuote  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Päivittää vain tämän tietyn rivin

// Poista tuote
products.remove(2);
repository.commit(); // Päivittää näkymän
```

Commit-metodilla on kaksi allekirjoitusta:
- `commit()` - Kehottaa repositorya päivittämään kaiken. Se laukaisee `RepositoryCommitEvent`-tapahtuman kaikilla nykyisillä tiedoilla
- `commit(entity)` - Kohdentaa tiettyyn entiteettiin. Repository löytää tämän entiteetin sen avaimen kautta ja päivittää vain vaikutuksen saaneet UI-elementit

:::important Yksittäisten entiteettien sitouttaminen
Tällä erolla on merkitystä suorituskyvyn kannalta. Kun päivität yhden kentän 1000-rivisessä taulukossa, `commit(entity)` päivittää vain sen solun, kun taas `commit()` päivittäisi kaikki rivit.
:::

## Datatietojen suodatus {#filtering-data}

Repositoryn suodatin hallitsee, mitä dataa virtaa liitettyihin komponentteihin. Perusteellinen kokoelmasi pysyy muuttumattomana, koska suodatin toimii linssinä:

```java
// Suodata varastotilanteen mukaan
repository.setBaseFilter(product -> product.getStock() > 0);

// Suodata kategorian mukaan
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Yhdistää useita ehtoja
repository.setBaseFilter(product -> 
  product.getCategory().equals("Electronics") && 
  product.getStock() > 0 && 
  product.getPrice() < 100.0
);

// Tyhjennä suodatin
repository.setBaseFilter(null);
```

Kun asetat suodattimen, `Repository`:
1. Soveltaa ennustetta jokaiselle kokoelmasi kohteelle
2. Luo suodatetun virran vastaavista kohteista
3. Ilmoittaa liitetyille komponenteille, että niiden näyttö on päivitettävä

Suodatin pysyy voimassa, kunnes muutat sen. Uudet kohteet, jotka lisätään kokoelmaan, testataan automaattisesti nykyisen suodattimen mukaan.


## Työskentely entiteettiavainten kanssa {#working-with-entity-keys}

Repository tarvitsee tunnistaa entiteetit yksilöllisesti tukeakseen toimintoja kuten `find()` ja `commit(entity)`. On kaksi tapaa määrittää, miten entiteetit tunnistetaan:

### Käyttämällä `HasEntityKey`-rajapintaa {#using-hasentitykey}

Toteuta <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> entiteettisi luokassa:

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
  repository.commit(c); // Vain tämän asiakkaan rivi päivitetään
});
```

### Käyttämällä räätälöityä avaimen tarjoajaa <DocChip chip='since' label='25.10' /> {#using-custom-key-provider} 

Entiteeteille, joissa et voi tai et halua toteuttaa `HasEntityKey` (kuten JPA-entiteetit), käytä `setKeyProvider()`:

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

// Nyt löytö toimii ID:llä
Optional<Product> product = repository.find(123L);
```

### Lähestymistavan valitseminen {#choosing-approach}

Molemmat lähestymistavat toimivat, mutta `setKeyProvider()` on suositeltavaa, kun:
- Työskentelet JPA-entiteettien kanssa, joilla on `@Id`-kenttiä
- Et voi muokata entiteettien luokkaa
- Tarvitset erilaisia avainstrategioita eri repositorioille

Käytä `HasEntityKey` kun:
- Hallitset entiteettien luokkaa
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
