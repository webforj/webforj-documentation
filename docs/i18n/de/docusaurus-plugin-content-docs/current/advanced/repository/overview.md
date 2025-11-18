---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: b9a08226a8ace111beea4ab2a03ff79f
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->


Das `Repository`-Muster in webforJ bietet eine standardisierte Möglichkeit, Sammlungen von Entitäten zu verwalten und abzufragen. Es fungiert als Abstraktionsschicht zwischen Ihren UI-Komponenten und Daten, sodass Sie problemlos mit verschiedenen Datenquellen arbeiten können, während Sie ein konsistentes Verhalten beibehalten.

## Warum Repository verwenden {#why-use-repository}

`Repository` beseitigt manuelle Aktualisierungen und erhält Ihre ursprünglichen Daten intakt:

```java
// Ohne Repository - manuelle Aktualisierungen
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Hinzufügen erfordert vollständiges Neuladen
customers.add(newCustomer);
table.setItems(customers); // Muss alles neu laden
```

```java
// Mit Repository - automatische Synchronisierung
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Hinzufügen synchronisiert automatisch
customers.add(newCustomer);
repository.commit(newCustomer); // Aktualisiert nur, was sich geändert hat
```


## Collections-Repository {#collection-repository}

Das <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> ist die gängigste Implementierung und wickelt jede Java-Sammlung ein:

```java
// Von ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Von HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Von jeder Sammlung
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```


## Daten-Synchronisierung {#data-synchronization}

Das `Repository` fungiert als Brücke zwischen Ihren Daten und UI-Komponenten. Wenn sich Daten ändern, benachrichtigen Sie das Repository über die Methode `commit()`:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Neues Produkt hinzufügen
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Alle verbundenen Komponenten aktualisieren

// Bestehendes Produkt aktualisieren  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Aktualisiert nur diese spezielle Zeile

// Produkt entfernen
products.remove(2);
repository.commit(); // Aktualisiert die Ansicht
```

Die Methode commit hat zwei Signaturen:
- `commit()` - Sagt dem Repository, dass alles aktualisiert werden soll. Es löst ein `RepositoryCommitEvent` mit allen aktuellen Daten aus
- `commit(entity)` - Zielt auf eine bestimmte Entität ab. Das Repository findet diese Entität anhand ihres Schlüssels und aktualisiert nur die betroffenen UI-Elemente

:::important Einzelne Entitäten committen
Diese Unterscheidung ist wichtig für die Leistung. Wenn Sie ein Feld in einer Tabelle mit 1000 Zeilen aktualisieren, aktualisiert `commit(entity)` nur diese Zelle, während `commit()` alle Zeilen aktualisieren würde.
:::

## Daten filtern {#filtering-data}

Der Filter des Repositories steuert, welche Daten zu verbundenen Komponenten fließen. Ihre zugrunde liegende Sammlung bleibt unverändert, da der Filter als Linse fungiert:

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

Wenn Sie einen Filter festlegen, führt das `Repository` Folgendes aus:
1. Wendet das Prädikat auf jedes Element in Ihrer Sammlung an
2. Erstellt einen gefilterten Stream von passenden Elementen
3. Benachrichtigt verbundene Komponenten, dass sie ihre Anzeige aktualisieren sollen

Der Filter bleibt bestehen, bis Sie ihn ändern. Neue Elemente, die zur Sammlung hinzugefügt werden, werden automatisch gegen den aktuellen Filter getestet.


## Arbeiten mit Entitätsschlüsseln {#working-with-entity-keys}

Das Repository muss Entitäten eindeutig identifizieren, um Operationen wie `find()` und `commit(entity)` zu unterstützen. Es gibt zwei Möglichkeiten, wie Entitäten identifiziert werden:

### Verwendung des HasEntityKey-Interfaces {#using-hasentitykey}

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

// Bestimmten Kunden aktualisieren
customer.ifPresent(c -> {
    c.setEmail("newemail@example.com");
    repository.commit(c); // Aktualisiert nur diese Zeile des Kunden
});
```

### Verwendung eines benutzerdefinierten Schlüssel-Providers <DocChip chip='since' label='25.10' /> {#using-custom-key-provider} 

Für Entitäten, bei denen Sie `HasEntityKey` nicht implementieren können oder wollen (wie bei JPA-Entitäten), verwenden Sie `setKeyProvider()`:

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // Von JPA verwaltete Entität
}

// Repository so konfigurieren, dass die Methode getId() verwendet wird
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Jetzt funktioniert das Finden mit der ID
Optional<Product> product = repository.find(123L);
```

### Auswahl eines Ansatzes {#choosing-approach}

Beide Ansätze funktionieren, aber `setKeyProvider()` wird bevorzugt, wenn:
- Sie mit JPA-Entitäten arbeiten, die `@Id`-Felder haben
- Sie die Entitätsklasse nicht ändern können
- Sie unterschiedliche Schlüsselstrategien für verschiedene Repositories benötigen

Verwenden Sie `HasEntityKey`, wenn:
- Sie die Entitätsklasse kontrollieren
- Die Logik zur Schlüsselerfassung komplex ist
- Sie möchten, dass die Entität ihre eigene Identität definiert


## UI-Integration {#ui-integration}

`Repository` integriert sich mit datenbewussten Komponenten:

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
