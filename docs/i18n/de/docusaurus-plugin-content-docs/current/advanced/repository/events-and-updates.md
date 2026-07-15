---
title: Events and updates
sidebar_position: 5
description: >-
  React to repository commits with RepositoryCommitEvent listeners, choose
  single-entity or bulk updates, and manage listener registrations.
_i18n_hash: 9ce2e7d25b38cd0d04acd30c80edffb3
---
<!-- vale off -->
# Ereignisse und Updates <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-Ereignisse ermöglichen es Ihnen, auf Datenänderungen zu reagieren. Neben den automatischen UI-Updates können Sie auf Änderungen hören, um benutzerdefinierte Logik auszulösen.

## `Repository`-Ereignislebenszyklus {#repository-event-lifecycle}

Jeder `commit()`-Aufruf löst ein <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink> aus. Dieses Ereignis enthält Informationen darüber, was sich geändert hat:

```java
repository.onCommit(event -> {
  // Alle kommittierten Entitäten abrufen
  List<Customer> commits = event.getCommits();

  // Überprüfen, ob es sich um ein Update einer einzelnen Entität handelt
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Aktualisiert: " + updated.getName());
  }
});
```

Das Ereignis gibt Ihnen folgende Informationen:
- `getCommits()` - Liste der Entitäten, die kommittiert wurden
- `isSingleCommit()` - Ob dies ein gezieltes Update einer einzelnen Entität war
- `getFirstCommit()` - Hilfsmethode, um die erste (oder einzige) Entität zu erhalten

Für `commit()` ohne Parameter enthält das Ereignis alle Entitäten, die sich nach der Filterung derzeit im Repository befinden.

## Aktualisierungsstrategien {#update-strategies}

Die beiden Commit-Signaturen dienen unterschiedlichen Zwecken:

```java
// Update einer einzelnen Entität - effizient für individuelle Änderungen
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Massenupdate - effizient für mehrere Änderungen
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Commits von einzelnen Entitäten sind gezielt - sie sagen den verbundenen Komponenten genau, welche Zeile sich geändert hat. Der [`Table`](../../components/table/overview) kann nur die Zellen dieser Zeile aktualisieren, ohne andere zu berühren.

Massencommits aktualisieren alles. Verwenden Sie sie, wenn:
- Mehrere Entitäten geändert wurden
- Sie Elemente hinzugefügt oder entfernt haben
- Sie sich nicht sicher sind, was sich geändert hat

## Reaktive UI-Muster {#reactive-ui-patterns}

`Repository`-Ereignisse ermöglichen es Ihnen, Zusammenfassungen mit Ihren Daten synchron zu halten:

```java
// Automatisch aktualisierende Labels
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Gesamt: $%.2f", total));
  countLabel.setText("Verkäufe: " + sales.size());
});

// Live-Ergebnisanzahlen
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " Produkte gefunden");
});
```

Diese Listener werden bei jedem Commit ausgelöst, egal ob durch Benutzeraktionen, Datenimporte oder programmatische Updates. Das Ereignis gibt Ihnen Zugriff auf die kommittierten Entitäten, aber oft berechnen Sie erneut aus der Quellsammlung, um alle aktuellen Daten einzuschließen.

## Speicherverwaltung {#memory-management}

Ereignis-Listener halten Referenzen zu Ihren Komponenten. Wenn Sie diese nicht entfernen, behält das `Repository` Ihre Komponenten im Speicher, auch nachdem sie nicht mehr angezeigt werden:

```java
// Referenz behalten, um sie später zu entfernen
ListenerRegistration<RepositoryCommitEvent<Data>> registration =
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Listener bereinigen, wenn die Komponente zerstört wird
if (registration != null) {
  registration.remove();
}
```

Die Methode `onCommit()` gibt eine `ListenerRegistration` zurück. Bewahren Sie diese Referenz auf und rufen Sie `remove()` auf, wenn Ihre Komponente zerstört wird oder keine Updates mehr benötigt. Dies verhindert Speicherlecks in langlebigen Anwendungen.
