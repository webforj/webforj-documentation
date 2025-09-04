---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: dc6f7bbfe82d68565cbe8da6436f080c
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->


Het `Repository`-patroon in webforJ biedt een gestandaardiseerde manier om collecties van entiteiten te beheren en te raadplegen. Het fungeert als een abstractielaag tussen je UI-componenten en gegevens, waardoor het eenvoudig is om met verschillende gegevensbronnen te werken terwijl je een consistente gedraging behoudt.

## Waarom een repository gebruiken {#why-use-repository}

`Repository` voorkomt handmatige updates en behoudt je originele gegevens intact:

```java
// Zonder Repository - handmatige updates
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Toevoegen vereist volledige herlaad
customers.add(newCustomer);
table.setItems(customers); // Alles moet opnieuw worden geladen
```

```java
// Met Repository - automatische synchronisatie
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Toevoegen synchroniseert automatisch
customers.add(newCustomer);
repository.commit(newCustomer); // Alleen wat is veranderd wordt bijgewerkt
```


## Collectie repository {#collection-repository}

De <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> is de meest voorkomende implementatie en wikkelt elke Java-collectie:

```java
// Van ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Van HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Van elke collectie
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Gegevenssynchronisatie {#data-synchronization}

De `Repository` fungeert als een brug tussen je gegevens en UI-componenten. Wanneer gegevens veranderen, meld je dit aan de repository via de `commit()`-methode:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Nieuwe product toevoegen
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Alle verbonden componenten worden bijgewerkt

// Bestaand product bijwerken  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Alleen deze specifieke rij wordt bijgewerkt

// Product verwijderen
products.remove(2);
repository.commit(); // Vernieuwt de weergave
```

De commit-methode heeft twee handtekeningen:
- `commit()` - Geeft de repository opdracht om alles te vernieuwen. Het activeert een `RepositoryCommitEvent` met alle huidige gegevens
- `commit(entity)` - Richt zich op een specifieke entiteit. De repository vindt deze entiteit op basis van zijn sleutel en werkt alleen de aangetaste UI-elementen bij

:::important Committen van enkele entiteiten
Dit onderscheid is belangrijk voor de prestaties. Wanneer je één veld bijwerkt in een tabel met 1000 rijen, werkt `commit(entity)` alleen die cel bij, terwijl `commit()` alle rijen vernieuwt.
:::

## Gegevens filteren {#filtering-data}

De filter van de repository controleert welke gegevens naar verbonden componenten stromen. Je onderliggende collectie blijft onveranderd omdat de filter als een lens fungeert:

```java
// Filteren op voorraadbeschikbaarheid
repository.setBaseFilter(product -> product.getStock() > 0);

// Filteren op categorie
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Meerdere voorwaarden combineren
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Filter wissen
repository.setBaseFilter(null);
```

Wanneer je een filter instelt, doet de `Repository`:
1. Past de predicate toe op elk item in je collectie
2. Maakt een gefilterde stroom van bijpassende items
3. Meldt verbonden componenten om hun weergave bij te werken

De filter blijft bestaan totdat je deze verandert. Nieuwe items die aan de collectie worden toegevoegd, worden automatisch getest tegen de huidige filter.


## Werken met entiteitssleutels {#working-with-entity-keys}

Wanneer je entiteiten <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> implementeren, kan de repository specifieke items vinden en bijwerken op basis van hun ID:

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

// Vinden op sleutel
Optional<Customer> customer = repository.find("C001");

// Specifieke klant bijwerken
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Alleen deze klant rij wordt bijgewerkt
});
```

Zonder `HasEntityKey`:
- `repository.find("C001")` vindt je klant niet omdat het zoekt naar een object dat gelijk is aan "C001"
- `repository.commit(entity)` werkt nog steeds, maar is afhankelijk van objectgelijkheid
- UI-componenten kunnen items niet selecteren op ID, alleen op objectreferentie


## UI-integratie {#ui-integration}

`Repository` integreert met gegevensbewuste componenten:

```java
// Repository en tabel maken
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Naam", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// Gegevens toevoegen - tabel wordt automatisch bijgewerkt
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```


## Volgende stappen {#next-steps}

<DocCardList className="topics-section" />
