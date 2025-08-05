---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: 8dfc90f24bba893de434f1a41d5776c6
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

Het `Repository`-patroon in webforJ biedt een gestandaardiseerde manier om collecties van entiteiten te beheren en op te vragen. Het fungeert als een abstractielaag tussen uw UI-componenten en gegevens, waardoor het gemakkelijk is om met verschillende gegevensbronnen te werken terwijl consistent gedrag behouden blijft.

## Waarom een repository gebruiken {#why-use-repository}

`Repository` elimineert handmatige updates en houdt uw oorspronkelijke gegevens intact:

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
repository.commit(newCustomer); // Updateert alleen wat veranderd is
```

## Collectie repository {#collection-repository}

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

De `Repository` fungeert als een brug tussen uw gegevens en UI-componenten. Wanneer gegevens veranderen, stelt u de repository op de hoogte via de `commit()`-methode:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Nieuwe product toevoegen
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Alle verbonden componenten worden bijgewerkt

// Bestaand product bijwerken  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Updateert alleen deze specifieke rij

// Product verwijderen
products.remove(2);
repository.commit(); // Vernieuwt de weergave
```

De commit-methode heeft twee handtekeningen:
- `commit()` - Vertelt de repository om alles te vernieuwen. Het activeert een `RepositoryCommitEvent` met alle huidige gegevens
- `commit(entity)` - Richt zich op een specifieke entiteit. De repository vindt deze entiteit op basis van zijn sleutel en werkt alleen de aangetaste UI-elementen bij

:::important Het indienen van enkele entiteiten
Deze differentiatie is belangrijk voor de prestaties. Wanneer u één veld bijwerkt in een tabel met 1000 rijen, werkt `commit(entity)` alleen die cel bij, terwijl `commit()` alle rijen zou vernieuwen.
:::

## Gegevens filteren {#filtering-data}

De filter van de repository controleert welke gegevens naar verbonden componenten stromen. Uw onderliggende collectie blijft onveranderd, omdat de filter fungeert als een lens:

```java
// Filter op voorraadbeschikbaarheid
repository.setBaseFilter(product -> product.getStock() > 0);

// Filter op categorie
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

Wanneer u een filter instelt, doet de `Repository`:
1. Past de voorwaarde toe op elk item in uw collectie
2. Creeert een gefilterde stroom van overeenkomstige items
3. Meldt de verbonden componenten om hun weergave bij te werken

De filter blijft bestaan totdat u deze wijzigt. Nieuwe items die aan de collectie worden toegevoegd, worden automatisch getest tegen de huidige filter.

## Werken met entiteit sleutels {#working-with-entity-keys}

Wanneer uw entiteiten <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> implementeren, kan de repository specifieke items vinden en bijwerken op basis van hun ID:

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

// Vind op sleutel
Optional<Customer> customer = repository.find("C001");

// Specifieke klant bijwerken
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Alleen deze klant rij wordt bijgewerkt
});
```

Zonder `HasEntityKey`:
- `repository.find("C001")` vindt uw klant niet omdat het zoekt naar een object dat gelijk is aan "C001"
- `repository.commit(entity)` werkt nog steeds, maar vertrouwt op objectgelijkheid
- UI-componenten kunnen geen items op ID selecteren, alleen op objectreferentie

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

// Gegevens toevoegen - tabel wordt automatisch bijgewerkt
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Volgende stappen {#next-steps}

<DocCardList className="topics-section" />
