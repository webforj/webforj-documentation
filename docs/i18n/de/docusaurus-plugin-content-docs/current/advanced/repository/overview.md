---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: c285de15c19063af40c61f15cebf4dc1
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

Das `Repository`-Muster in webforJ bietet eine standardisierte Methode, um Sammlungen von Entitäten zu verwalten und abzufragen. Es fungiert als Abstraktionsschicht zwischen Ihren UI-Komponenten und Daten, wodurch es einfach ist, mit verschiedenen Datenquellen zu arbeiten und dabei ein konsistentes Verhalten beizubehalten.

## Warum Repository verwenden {#why-use-repository}

`Repository` beseitigt manuelle Aktualisierungen und hält Ihre ursprünglichen Daten intakt:

```java
// Ohne Repository - manuelle Updates
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Hinzufügen erfordert ein vollständiges Neuladen
customers.add(newCustomer);
table.setItems(customers); // Muss alles neu laden
```

```java
// Mit Repository - automatische Synchronisation
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Hinzufügen synchronisiert automatisch
customers.add(newCustomer);
repository.commit(newCustomer); // Aktualisiert nur, was sich geändert hat
```


## Sammlung-Repository {#collection-repository}

Das <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> ist die gebräuchlichste Implementierung und umschließt jede Java-Sammlung:

```java
// Aus ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Aus HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Aus jeder Collection
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Datensynchronisation {#data-synchronization}

Das `Repository` fungiert als Brücke zwischen Ihren Daten und den UI-Komponenten. Wenn sich Daten ändern, benachrichtigen Sie das Repository über die `commit()`-Methode:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Neues Produkt hinzufügen
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Alle verbundenen Komponenten aktualisieren

// Vorhandenes Produkt aktualisieren  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Aktualisiert nur diese spezifische Zeile

// Produkt entfernen
products.remove(2);
repository.commit(); // Aktualisiert die Ansicht
```

Die Commit-Methode hat zwei Signaturen:
- `commit()` - Sagt dem Repository, dass alles aktualisiert werden soll. Es löst ein `RepositoryCommitEvent` mit allen aktuellen Daten aus.
- `commit(entity)` - Ziel eine spezifische Entität. Das Repository findet diese Entität anhand ihres Schlüssels und aktualisiert nur die betroffenen UI-Elemente.

:::important Einzelne Entitäten committen
Diese Unterscheidung ist wichtig für die Leistung. Wenn Sie ein Feld in einer Tabelle mit 1000 Zeilen aktualisieren, aktualisiert `commit(entity)` nur diese Zelle, während `commit()` alle Zeilen aktualisieren würde.
:::

## Daten filtern {#filtering-data}

Der Filter des Repositories steuert, welche Daten an verbundene Komponenten weitergeleitet werden. Ihre zugrunde liegende Sammlung bleibt unverändert, da der Filter als Linse wirkt:

```java
// Nach Verfügbarkeit auf Lager filtern
repository.setBaseFilter(product -> product.getStock() > 0);

// Nach Kategorie filtern
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Mehrere Bedingungen kombinieren
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Filter löschen
repository.setBaseFilter(null);
```

Wenn Sie einen Filter festlegen, führt das `Repository` Folgendes durch:
1. Wendet das Prädikat auf jedes Element in Ihrer Sammlung an
2. Erstellt einen gefilterten Stream von übereinstimmenden Elementen
3. Benachrichtigt verbundene Komponenten, um ihre Anzeige zu aktualisieren

Der Filter bleibt bestehen, bis Sie ihn ändern. Neue Elemente, die der Sammlung hinzugefügt werden, werden automatisch gegen den aktuellen Filter getestet.


## Arbeiten mit Entitätsschlüsseln {#working-with-entity-keys}

Das Repository muss Entitäten eindeutig identifizieren, um Operationen wie `find()` und `commit(entity)` zu unterstützen. Es gibt zwei Möglichkeiten, wie Entitäten identifiziert werden können:

### Verwendung des `HasEntityKey`-Interfaces {#using-hasentitykey}

Implementieren Sie <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> in Ihrer Entitätsklasse:

```java
public class Customer implements HasEntityKey {
    private String customerId;
    private String name;
    private String email;

    @Override
    public Object getEntityKey() {
        return customerId;
    }

    // Konstruktor und Getter/Setter...
}

// Nach Schlüssel suchen
Optional<Customer> customer = repository.find("C001");

// Spezifischen Kunden aktualisieren
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Nur die Zeile dieses Kunden wird aktualisiert
});
```

### Verwendung eines benutzerdefinierten Schlüsselanbieters <DocChip chip='since' label='25.10' /> {#using-custom-key-provider} 

Für Entitäten, bei denen Sie `HasEntityKey` nicht implementieren können oder möchten (z. B. JPA-Entitäten), verwenden Sie `setKeyProvider()`:

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // JPA-gemanagte Entität
}

// Repository so konfigurieren, dass die Methode getId() verwendet wird
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Jetzt funktioniert die Suche mit der ID
Optional<Product> product = repository.find(123L);
```

### Auswahl eines Ansatzes {#choosing-approach}

Beide Ansätze funktionieren, aber `setKeyProvider()` wird bevorzugt, wenn:
- Mit JPA-Entitäten, die `@Id`-Felder haben, gearbeitet wird
- Sie die Entitätsklasse nicht ändern können
- Sie für verschiedene Repositories unterschiedliche Schlüsselstrategien benötigen

Verwenden Sie `HasEntityKey`, wenn:
- Sie die Entitätsklasse kontrollieren
- Die Logik zur Extraktion des Schlüssels komplex ist
- Sie möchten, dass die Entität ihre eigene Identität definiert


## UI-Integration {#ui-integration}

`Repository` integriert sich mit datensensitiven Komponenten:

```java
// Repository und Tabelle erstellen
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// Daten hinzufügen - Tabelle aktualisiert sich automatisch
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```


## Nächste Schritte {#next-steps}

<DocCardList className="topics-section" />
