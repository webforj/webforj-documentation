---
title: Events and updates
sidebar_position: 5
_i18n_hash: 0f7a5576086e2922c0549eae23e66061
---
<!-- vale off -->
# Ereignisse und Aktualisierungen <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-Ereignisse ermöglichen es Ihnen, auf Datenänderungen zu reagieren. Über die automatischen UI-Aktualisierungen hinaus können Sie auf Änderungen hören, um benutzerdefinierte Logik auszulösen.

## `Repository`-Ereignislebenszyklus {#repository-event-lifecycle}

Jeder Aufruf von `commit()` löst ein <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink> aus. Dieses Ereignis enthält Informationen darüber, was sich geändert hat:

```java
repository.onCommit(event -> {
  // Alle bestätigten Entitäten abrufen
  List<Customer> commits = event.getCommits();
  
  // Überprüfen, ob es sich um eine Aktualisierung einer einzelnen Entität handelt
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Aktualisiert: " + updated.getName());
  }
});
```

Das Ereignis informiert Sie:
- `getCommits()` - Liste der Entitäten, die bestätigt wurden
- `isSingleCommit()` - Ob es sich um eine gezielte Aktualisierung einer einzigen Entität handelt
- `getFirstCommit()` - Hilfsmethode, um die erste (oder einzige) Entität abzurufen

Für `commit()` ohne Parameter enthält das Ereignis alle Entitäten, die sich derzeit im Repository nach der Filterung befinden.

## Aktualisierungsstrategien {#update-strategies}

Die beiden Commit-Signaturen dienen unterschiedlichen Zwecken:

```java
// Aktualisierung einer einzelnen Entität - effizient für individuelle Änderungen
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Massenaktualisierung - effizient für mehrere Änderungen
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Aktualisierungen einer einzelnen Entität sind chirurgisch - sie sagen den verbundenen Komponenten genau, welche Zeile sich geändert hat. Die [`Table`](../../components/table/overview) kann nur die Zellen dieser Zeile aktualisieren, ohne etwas anderes zu berühren.

Massencommits aktualisieren alles. Verwenden Sie sie, wenn:
- Mehrere Entitäten verändert wurden
- Sie Artikel hinzugefügt oder entfernt haben
- Sie sich nicht sicher sind, was sich geändert hat

## Reaktive UI-Muster {#reactive-ui-patterns}

`Repository`-Ereignisse ermöglichen es Ihnen, Zusammenfassungsanzeigen mit Ihren Daten synchron zu halten:

```java
// Automatisch aktualisierende Labels
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Gesamt: $%.2f", total));
  countLabel.setText("Verkäufe: " + sales.size());
});

// Live-Ergebniszahlen
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " Produkte gefunden");
});
```

Diese Listener werden bei jedem Commit aktiviert, ganz gleich, ob sie durch Benutzeraktionen, Datenimporte oder programmatische Aktualisierungen ausgelöst wurden. Das Ereignis gibt Ihnen Zugriff auf die bestätigten Entitäten, aber oft berechnen Sie erneut aus der Quellsammlung, um alle aktuellen Daten einzuschließen.

## Speicherverwaltung {#memory-management}

Ereignis-Listener halten Referenzen auf Ihre Komponenten. Wenn Sie diese nicht entfernen, behält das `Repository` Ihre Komponenten im Speicher, selbst wenn sie nicht mehr angezeigt werden:

```java
// Referenz behalten, um später zu entfernen
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Listener aufräumen, wenn die Komponente zerstört wird
if (registration != null) {
  registration.remove();
}
```

Die Methode `onCommit()` gibt eine `ListenerRegistration` zurück. Speichern Sie diese Referenz und rufen Sie `remove()` auf, wenn Ihre Komponente zerstört wird oder keine Aktualisierungen mehr benötigt. Dies verhindert Speicherlecks in lang laufenden Anwendungen.
