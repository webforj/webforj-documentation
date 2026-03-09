---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: c285de15c19063af40c61f15cebf4dc1
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->


Het `Repository`-patroon in webforJ biedt een gestandaardiseerde manier om collecties van entiteiten te beheren en op te vragen. Het fungeert als een abstractielaag tussen uw UI-componenten en gegevens, waardoor het eenvoudig is om met verschillende gegevensbronnen te werken terwijl het consistent gedrag behoudt.

## Waarom een repository gebruiken {#why-use-repository}

`Repository` elimineert handmatige updates terwijl uw originele gegevens intact blijven:

```java
// Zonder Repository - handmatige updates
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Toevoegen vereist volledige herlaad
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


## Collectie-repository {#collection-repository}

De <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> is de meest voorkomende implementatie en verpakt elke Java-collectie:

```java
// Van ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Van HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Van elke Collectie
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Gegevenssynchronisatie {#data-synchronization}

Het `Repository` fungeert als een brug tussen uw gegevens en UI-componenten. Wanneer gegevens veranderen, meldt u dit aan de repository via de `commit()`-methode:

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
- `commit()` - Zegt tegen de repository om alles te vernieuwen. Het activeert een `RepositoryCommitEvent` met alle huidige gegevens
- `commit(entity)` - Richt zich op een specifieke entiteit. De repository vindt deze entiteit aan de hand van de sleutel en werkt alleen de getroffen UI-elementen bij

:::important Committen van enkele entiteiten
Deze onderscheiding is belangrijk voor de prestaties. Wanneer u één veld bijwerkt in een tabel met 1000 rijen, werkt `commit(entity)` alleen die cel bij, terwijl `commit()` alle rijen zou vernieuwen.
:::

## Gegevens filteren {#filtering-data}

De filter van de repository beheert welke gegevens naar verbonden componenten stromen. Uw onderliggende collectie blijft ongewijzigd omdat de filter fungeert als een lens:

```java
// Filter op beschikbaarheid van voorraad
repository.setBaseFilter(product -> product.getStock() > 0);

// Filter op categorie
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Combineer meerdere voorwaarden
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Verwijder filter
repository.setBaseFilter(null);
```

Wanneer u een filter instelt, doet het `Repository`:
1. Past de predicate toe op elk item in uw collectie
2. Maakt een gefilterde stream van overeenkomende items
3. Meldt verbonden componenten om hun weergave bij te werken

De filter blijft bestaan totdat u deze verandert. Nieuwe items die aan de collectie worden toegevoegd, worden automatisch tegen de huidige filter getest.


## Werken met entiteitssleutels {#working-with-entity-keys}

De repository moet entiteiten uniek identificeren om operaties zoals `find()` en `commit(entity)` te ondersteunen. Er zijn twee manieren om te definiëren hoe entiteiten worden geïdentificeerd:

### Gebruik maken van het `HasEntityKey`-interface {#using-hasentitykey}

Implementeer <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> op uw entiteitsklasse:

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

### Gebruik maken van een aangepaste sleutelprovider <DocChip chip='since' label='25.10' /> {#using-custom-key-provider} 

Voor entiteiten waarbij u `HasEntityKey` niet kunt of wilt implementeren (zoals JPA-entiteiten), gebruik `setKeyProvider()`:

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // JPA-beheerde entiteit
}

// Configureer de repository om de getId()-methode te gebruiken
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Nu werkt zoeken met de ID
Optional<Product> product = repository.find(123L);
```

### Een aanpak kiezen {#choosing-approach}

Beide benaderingen werken, maar `setKeyProvider()` heeft de voorkeur wanneer:
- U werkt met JPA-entiteiten die `@Id`-velden hebben
- U de entiteitsklasse niet kunt aanpassen
- U verschillende sleutelsstrategieën voor verschillende repositories nodig hebt

Gebruik `HasEntityKey` wanneer:
- U controle heeft over de entiteitsklasse
- De sleutel-extractielogica complex is
- U wilt dat de entiteit zijn eigen identiteit definieert


## UI-integratie {#ui-integration}

`Repository` integreert met gegevensbewuste componenten:

```java
// Maak repository en tabel
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Naam", Customer::getName);
table.addColumn("E-mail", Customer::getEmail);

// Voeg gegevens toe - tabel wordt automatisch bijgewerkt
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```


## Volgende stappen {#next-steps}

<DocCardList className="topics-section" />
