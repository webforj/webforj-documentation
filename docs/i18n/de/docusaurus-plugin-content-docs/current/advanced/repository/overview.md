---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: 8dfc90f24bba893de434f1a41d5776c6
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

Das `Repository`-Muster in webforJ bietet eine standardisierte Möglichkeit, Sammlungen von Entitäten zu verwalten und abzufragen. Es fungiert als Abstraktionsschicht zwischen Ihren UI-Komponenten und Daten, wodurch die Arbeit mit verschiedenen Datenquellen erleichtert wird, während das Verhalten konsistent bleibt.

## Warum ein Repository verwenden {#why-use-repository}

`Repository` eliminiert manuelle Aktualisierungen, während Ihre ursprünglichen Daten intakt bleiben:

```java
// Ohne Repository - manuelle Updates
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

## Sammlung Repository {#collection-repository}

Der <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> ist die gängigste Implementierung und umschließt jede Java-Sammlung:

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

## Daten Synchronisierung {#data-synchronization}

Das `Repository` fungiert als Verbindung zwischen Ihren Daten und UI-Komponenten. Wenn sich die Daten ändern, benachrichtigen Sie das Repository über die `commit()`-Methode:

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

Die commit-Methode hat zwei Signaturen:
- `commit()` - Sagt dem Repository, dass alles aktualisiert werden soll. Es löst ein `RepositoryCommitEvent` mit allen aktuellen Daten aus.
- `commit(entity)` - Zielt auf eine spezifische Entität ab. Das Repository findet diese Entität anhand ihres Schlüssels und aktualisiert nur die betroffenen UI-Elemente.

:::important Einzelne Entitäten committen
Diese Unterscheidung ist wichtig für die Leistung. Wenn Sie ein Feld in einer Tabelle mit 1000 Zeilen aktualisieren, aktualisiert `commit(entity)` nur diese Zelle, während `commit()` alle Zeilen aktualisieren würde.
:::

## Daten filtern {#filtering-data}

Der Filter des Repositories steuert, welche Daten an die verbundenen Komponenten fließen. Ihre zugrunde liegende Sammlung bleibt unverändert, da der Filter als Linse fungiert:

```java
// Filtern nach Verfügbarkeit im Lager
repository.setBaseFilter(product -> product.getStock() > 0);

// Filtern nach Kategorie
repository.setBaseFilter(product -> "Electronics".equals(product.getCategory()));

// Kombinieren mehrerer Bedingungen
repository.setBaseFilter(product -> 
    product.getCategory().equals("Electronics") && 
    product.getStock() > 0 && 
    product.getPrice() < 100.0
);

// Filter zurücksetzen
repository.setBaseFilter(null);
```

Wenn Sie einen Filter setzen, führt das `Repository`:
1. Trägt das Prädikat auf jedes Element in Ihrer Sammlung an.
2. Erstellt einen gefilterten Stream von übereinstimmenden Elementen.
3. Benachrichtigt die verbundenen Komponenten, ihre Anzeige zu aktualisieren.

Der Filter bleibt bestehen, bis Sie ihn ändern. Neue Elemente, die zur Sammlung hinzugefügt werden, werden automatisch gegen den aktuellen Filter getestet.

## Arbeiten mit Entitätsschlüsseln {#working-with-entity-keys}

Wenn Ihre Entitäten <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> implementieren, kann das Repository spezifische Elemente anhand ihrer ID finden und aktualisieren:

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
    repository.commit(c); // Nur diese Zeile des Kunden wird aktualisiert
});
```

Ohne `HasEntityKey`:
- `repository.find("C001")` findet Ihren Kunden nicht, weil es nach einem Objekt sucht, das gleich "C001" ist.
- `repository.commit(entity)` funktioniert weiterhin, beruht jedoch auf der Objektgleichheit.
- UI-Komponenten können Elemente nicht nach ID auswählen, sondern nur nach Objektverweis.

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
