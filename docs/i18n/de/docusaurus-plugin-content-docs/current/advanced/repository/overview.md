---
title: Repository
sidebar_position: 1
_i18n_hash: 455b667132d3c9693257eb74671412c5
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->


Das `Repository`-Muster in webforJ bietet einen standardisierten Ansatz zur Verwaltung und Abfrage von Sammlungen von Entitäten. Es fungiert als Abstraktionsschicht zwischen Ihren UI-Komponenten und Daten und erleichtert das Arbeiten mit verschiedenen Datenquellen, während ein konsistentes Verhalten beibehalten wird.

## Warum Repository verwenden {#why-use-repository}

`Repository` eliminiert manuelle Aktualisierungen, während Ihre ursprünglichen Daten intakt bleiben:

```java
// Ohne Repository - manuelle Aktualisierungen
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Hinzufügen erfordert vollständiges Neu Laden
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


## Sammlung Repository {#collection-repository}

Das <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> ist die häufigste Implementierung und umschließt jede Java-Sammlung:

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

Das `Repository` fungiert als Brücke zwischen Ihren Daten und den UI-Komponenten. Wenn sich Daten ändern, benachrichtigen Sie das Repository über die `commit()`-Methode:

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Neues Produkt hinzufügen
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Alle verbundenen Komponenten werden aktualisiert

// Bestehendes Produkt aktualisieren  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Aktualisiert nur diese spezifische Zeile

// Produkt entfernen
products.remove(2);
repository.commit(); // Aktualisiert die Ansicht
```

Die Commit-Methode hat zwei Signaturen:
- `commit()` - Sagt dem Repository, dass alles aktualisiert werden soll. Dies löst ein `RepositoryCommitEvent` mit allen aktuellen Daten aus.
- `commit(entity)` - Zielt auf eine spezifische Entität ab. Das Repository findet diese Entität anhand ihres Schlüssels und aktualisiert nur die betroffenen UI-Elemente.

:::important Einzelne Entitäten committen
Diese Unterscheidung ist wichtig für die Leistung. Wenn Sie ein Feld in einer Tabelle mit 1000 Zeilen aktualisieren, aktualisiert `commit(entity)` nur diese Zelle, während `commit()` alle Zeilen neu laden würde.
:::

## Daten filtern {#filtering-data}

Der Filter des Repositorys steuert, welche Daten zu den verbundenen Komponenten fließen. Ihre zugrunde liegende Sammlung bleibt unverändert, da der Filter als Linse fungiert:

```java
// Filtern nach Verfügbarkeit auf Lager
repository.setBaseFilter(product -> product.getStock() > 0);

// Filtern nach Kategorie
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Kombinieren mehrerer Bedingungen
repository.setBaseFilter(product -> 
  product.getCategory().equals("Electronics") && 
  product.getStock() > 0 && 
  product.getPrice() < 100.0
);

// Filter löschen
repository.setBaseFilter(null);
```

Wenn Sie einen Filter festlegen, bewirkt das `Repository`:
1. Es wendet das Prädikat auf jedes Element in Ihrer Sammlung an
2. Es erstellt einen gefilterten Stream mit passenden Elementen
3. Es benachrichtigt die verbundenen Komponenten, ihre Anzeige zu aktualisieren

Der Filter bleibt bestehen, bis Sie ihn ändern. Neu hinzugefügte Elemente in der Sammlung werden automatisch gegen den aktuellen Filter getestet.


## Arbeiten mit Entitätsschlüsseln {#working-with-entity-keys}

Das Repository muss Entitäten eindeutig identifizieren, um Operationen wie `find()` und `commit(entity)` zu unterstützen. Es gibt zwei Möglichkeiten, wie Entitäten identifiziert werden:

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

// Bestimmten Kunden aktualisieren
customer.ifPresent(c -> {
  c.setEmail("newemail@example.com");
  repository.commit(c); // Nur diese Zeile des Kunden wird aktualisiert
});
```

### Verwendung eines benutzerdefinierten Schlüsselproviders <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Für Entitäten, bei denen Sie `HasEntityKey` nicht implementieren können oder wollen (wie JPA-Entitäten), verwenden Sie `setKeyProvider()`:

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
- Sie mit JPA-Entitäten arbeiten, die `@Id`-Felder haben
- Sie die Entitätsklasse nicht ändern können
- Sie unterschiedliche Schlüsselstrategien für verschiedene Repositories benötigen

Verwenden Sie `HasEntityKey`, wenn:
- Sie die Entitätsklasse kontrollieren
- Die Schlüsselauszuglogik komplex ist
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
