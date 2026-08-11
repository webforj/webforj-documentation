---
title: Repository
sidebar_position: 1
description: >-
  Bridge data collections to UI components with CollectionRepository, commit
  changes for automatic sync, and apply base filters.
_i18n_hash: 77548d09e4d712ce8f508917d3b953ae
---
<!-- vale off -->
# Varasto <DocChip chip='since' label='24.00' />
<!-- vale on -->


`Varasto`-malli webforJ:ssa tarjoaa standardoidun tavan hallita ja kysyä olioita. Se toimii abstraktiokerroksena käyttöliittymäkomponenttiesi ja datan välillä, mikä helpottaa työskentelyä erilaisten datalähteiden kanssa säilyttäen samalla johdonmukaisen käyttäytymisen.

## Miksi käyttää varastoa {#why-use-repository}

`Varasto` poistaa manuaaliset päivitykset samalla kun alkuperäinen data pysyy muuttamattomana:

```java
// Ilman varastoa - manuaaliset päivitykset
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Lisääminen vaatii täydellisen latauksen
customers.add(newCustomer);
table.setItems(customers); // Pakko ladata kaikki uudelleen
```

```java
// Varaston avulla - automaattinen synkronointi
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Lisääminen synkronoi automaattisesti
customers.add(newCustomer);
repository.commit(newCustomer); // Päivittää vain muutetut
```


## Kokoelman varasto {#collection-repository}

<JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">Kokoelman varasto</JavadocLink> on yleisin toteutus, ja se käärii minkä tahansa Java-kokoelman:

```java
// ArrayListin osalta
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// HashSetin osalta
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Mistä tahansa kokoelmasta
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Datan synkronointi {#data-synchronization}

`Varasto` toimii sillan tavoin datasi ja käyttöliittymäkomponenttien välillä. Kun data muuttuu, ilmoitat varastolle `commit()`-menetelmällä:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Lisää uusi tuote
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Kaikki liitetyt komponentit päivittyvät

// Päivitä olemassa olevaa tuotetta
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Päivittää vain tämän tietyn rivin

// Poista tuote
products.remove(2);
repository.commit(); // Päivittää näkymän
```

Commit-metodilla on kaksi allekirjoitusta:
- `commit()` - Kertoo varastolle, että koko tämänhetkinen data on päivitettävä. Se laukaisee `RepositoryCommitEvent`-tapahtuman kaikella nykyisellä datalla
- `commit(entity)` - Kohdistaa tiettyyn entiteettiin. Varasto löytää tämän entiteetin sen avaimen avulla ja päivittää vain vaikuttavat käyttöliittymä-elementit

:::important Yksittäisten entiteettien sitominen
Tämä ero on merkityksellinen suorituskyvyn kannalta. Kun päivität yhden kentän 1000-rivisessä taulussa, `commit(entity)` päivittää vain sen solun, kun taas `commit()` päivittäisi kaikki rivit.
:::

## Datan suodatus {#filtering-data}

Varaston suodatin säätelee, mitä dataa kulkee liitettyihin komponentteihin. Taustalla oleva kokoelmasi pysyy muuttumattomana, koska suodatin toimii linssinä:

```java
// Suodata varastotilanteen mukaan
repository.setBaseFilter(product -> product.getStock() > 0);

// Suodata kategorian mukaan
repository.setBaseFilter(product -> "Sähkölaitteet".equals(product.getCategory()));

// Yhdistä useita ehtoja
repository.setBaseFilter(product ->
  product.getCategory().equals("Sähkölaitteet") &&
  product.getStock() > 0 &&
  product.getPrice() < 100.0
);

// Tyhjennä suodatin
repository.setBaseFilter(null);
```

Kun asetat suodattimen, `varasto`:
1. Soveltaa ehtoa jokaiselle kohteelle kokoelmassasi
2. Luo suodatetun streamin vastaavista kohteista
3. Ilmoittaa liitetyille komponenteille päivityksestä

Suodatin pysyy voimassa, kunnes vaihdat sen. Uudet kohteet, jotka lisätään kokoelmaan, testataan automaattisesti nykyistä suodatinta vastaan.


## Työskentely entiteettiavainten kanssa {#working-with-entity-keys}

Varaston on pystyttävä tunnistamaan entiteetit ainutlaatuisesti, jotta se tukee toimintoja kuten `find()` ja `commit(entity)`. On kaksi tapaa määrittää, miten entiteettejä tunnistetaan:

### Käyttäen `HasEntityKey`-rajaa {#using-hasentitykey}

Toteuta <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> entiteettiluokassasi:

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

// Löydä avaimen mukaan
Optional<Customer> customer = repository.find("C001");

// Päivitä tietty asiakas
customer.ifPresent(c -> {
  c.setEmail("newemail@example.com");
  repository.commit(c); // Vain tämän asiakkaan rivi päivittyy
});
```

### Käyttäen mukautettua avaimen tarjoajaa <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Entiteeteille, joille et voi tai et halua toteuttaa `HasEntityKey` (kuten JPA-entiteetit), käytä `setKeyProvider()`:

```java
@Entity
public class Product {
  @Id
  private Long id;
  private String name;
  private double price;

  // JPA-hallittu entiteetti
}

// Määritä varasto käyttämään getId()-metodia
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Nyt haku toimii ID:n kanssa
Optional<Product> product = repository.find(123L);
```

### Lähestymistavan valitseminen {#choosing-approach}

Molemmat lähestymistavat toimivat, mutta `setKeyProvider()` on suosittavampi, kun:
- Työskentely JPA-entiteettien kanssa, joilla on `@Id`-kenttiä
- Et voi muuttaa entiteettiluokkaa
- Tarvitset erilaisia avainstrategioita eri varastoille

Käytä `HasEntityKey` kun:
- Hallitset entiteettiluokkaa
- Avaimen poimintalogiikka on monimutkainen
- Haluat entiteetin määrittelevän oman identiteettinsä


## Käyttöliittymän integraatio {#ui-integration}

`Varasto` integroituu dataa tunteviin komponentteihin:

```java
// Luo varasto ja taulu
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nimi", Customer::getName);
table.addColumn("Sähköposti", Customer::getEmail);

// Lisää dataa - taulu päivittyy automaattisesti
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```


## Seuraavat askeleet {#next-steps}

<DocCardList className="topics-section" />
