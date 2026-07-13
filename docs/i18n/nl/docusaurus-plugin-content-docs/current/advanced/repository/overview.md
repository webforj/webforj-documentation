---
title: Repository
sidebar_position: 1
description: >-
  Bridge data collections to UI components with CollectionRepository, commit
  changes for automatic sync, and apply base filters.
_i18n_hash: 77548d09e4d712ce8f508917d3b953ae
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->


Het `Repository`-patroon in webforJ biedt een gestandaardiseerde manier om collecties van entiteiten te beheren en op te vragen. Het fungeert als een abstractielaag tussen je UI-componenten en gegevens, waardoor het gemakkelijk is om met verschillende gegevensbronnen te werken terwijl je consistent gedrag behoudt.

## Waarom een repository gebruiken {#why-use-repository}

`Repository` elimineert handmatige updates terwijl je originele gegevens intact blijven:

```java
// Zonder Repository - handmatige updates
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Toevoegen vereist volledige herlading
customers.add(newCustomer);
table.setItems(customers); // Moet alles opnieuw laden
```

```java
// Met Repository - automatische synchronisatie
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Toevoegen synchroniseert automatisch
customers.add(newCustomer);
repository.commit(newCustomer); // Update alleen wat veranderd is
```


## Collectie repository {#collection-repository}

De <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> is de meest voorkomende implementatie en wikkelt elke Java Collection:

```java
// Van ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Van HashSet
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Van elke Collection
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Gegevenssynchronisatie {#data-synchronization}

De `Repository` fungeert als een brug tussen jouw gegevens en UI-componenten. Wanneer gegevens veranderen, informeer je de repository via de `commit()`-methode:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Voeg nieuw product toe
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Alle verbonden componenten worden bijgewerkt

// Update bestaand product
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Update alleen deze specifieke rij

// Verwijder product
products.remove(2);
repository.commit(); // Vernieuwt de weergave
```

De commit-methode heeft twee handtekeningen:
- `commit()` - Vertelt de repository om alles te vernieuwen. Het activeert een `RepositoryCommitEvent` met alle huidige gegevens.
- `commit(entity)` - Richt zich op een specifieke entiteit. De repository vindt deze entiteit aan de hand van zijn sleutel en werkt alleen de aangetaste UI-elementen bij.

:::important Enkelvoudige entiteiten committen
Deze onderscheiding is belangrijk voor de prestaties. Wanneer je één veld in een 1000-rijen tabel bijwerkt, werkt `commit(entity)` alleen die cel bij terwijl `commit()` alle rijen zou vernieuwen.
:::

## Filteren van gegevens {#filtering-data}

Het filter van de repository bepaalt welke gegevens naar verbonden componenten stromen. Je onderliggende collectie blijft ongewijzigd omdat het filter fungeert als een lens:

```java
// Filter op beschikbaarheid in voorraad
repository.setBaseFilter(product -> product.getStock() > 0);

// Filter op categorie
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Combineer meerdere voorwaarden
repository.setBaseFilter(product ->
  product.getCategory().equals("Electronics") &&
  product.getStock() > 0 &&
  product.getPrice() < 100.0
);

// Wis filter
repository.setBaseFilter(null);
```

Wanneer je een filter instelt, doet de `Repository`:
1. Past de predicaat toe op elk item in je collectie.
2. Creëert een gefilterde stroom van overeenkomende items.
3. Laat verbonden componenten weten dat ze hun weergave moeten bijwerken.

Het filter blijft bestaan totdat je het verandert. Nieuwe items die aan de collectie worden toegevoegd, worden automatisch getest tegen het huidige filter.


## Werken met entiteitssleutels {#working-with-entity-keys}

De repository moet entiteiten uniek kunnen identificeren om bewerkingen zoals `find()` en `commit(entity)` te ondersteunen. Er zijn twee manieren om te definiëren hoe entiteiten worden geïdentificeerd:

### Gebruik `HasEntityKey` interface {#using-hasentitykey}

Implementeer <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> in je entiteitsklasse:

```java
public class Customer implements HasEntityKey {
  private String customerId;
  private String name;
  private String email;

  @Override
  public Object getEntityKey() {
    return customerId;
  }

  // Constructor en getters/setters...
}

// Zoek op sleutel
Optional<Customer> customer = repository.find("C001");

// Update specifieke klant
customer.ifPresent(c -> {
  c.setEmail("newemail@example.com");
  repository.commit(c); // Alleen deze klant rij wordt bijgewerkt
});
```

### Gebruik aangepaste sleutelprovider <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Voor entiteiten waarvoor je `HasEntityKey` niet kunt of wilt implementeren (zoals JPA-entiteiten), gebruik `setKeyProvider()`:

```java
@Entity
public class Product {
  @Id
  private Long id;
  private String name;
  private double price;

  // JPA-beheerde entiteit
}

// Configureer repository om de getId() methode te gebruiken
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Nu werkt zoeken met de ID
Optional<Product> product = repository.find(123L);
```

### Kiezen van een aanpak {#choosing-approach}

Beide benaderingen werken, maar `setKeyProvider()` heeft de voorkeur wanneer:
- Je werkt met JPA-entiteiten die `@Id`-velden hebben.
- Je de entiteitsklasse niet kunt wijzigen.
- Je verschillende sleutelstrategieën voor verschillende repositories nodig hebt.

Gebruik `HasEntityKey` wanneer:
- Je controle hebt over de entiteitsklasse.
- De sleutelextractielogica complex is.
- Je wilt dat de entiteit zijn eigen identiteit definieert.


## UI-integratie {#ui-integration}

`Repository` integreert met data-bewuste componenten:

```java
// Maak repository en tabel
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Naam", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// Voeg gegevens toe - tabel wordt automatisch bijgewerkt
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```


## Volgende stappen {#next-steps}

<DocCardList className="topics-section" />
