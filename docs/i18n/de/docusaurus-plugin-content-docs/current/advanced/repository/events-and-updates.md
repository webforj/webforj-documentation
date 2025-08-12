---
title: Events and updates
sidebar_position: 5
_i18n_hash: b2973e75abc879992ab1e235ba5d8b5e
---
<!-- vale off -->
# Ereignisse und Updates <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-Ereignisse ermöglichen es Ihnen, auf Datenänderungen zu reagieren. Neben den automatischen UI-Updates können Sie Änderungen überwachen, um benutzerdefinierte Logik auszulösen.

## `Repository`-Ereignislebenszyklus {#repository-event-lifecycle}

Jeder `commit()`-Aufruf löst ein <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink> aus. Dieses Ereignis trägt Informationen darüber, was sich geändert hat:

```java
repository.onCommit(event -> {
    // Alle eingereichten Entitäten abrufen
    List<Customer> commits = event.getCommits();
    
    // Überprüfen, ob es sich um ein Update einer einzelnen Entität handelt
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Aktualisiert: " + updated.getName());
    }
});
```

Das Ereignis teilt Ihnen mit:
- `getCommits()` - Liste der eingereichten Entitäten
- `isSingleCommit()` - Ob es sich um ein gezieltes Update einer einzelnen Entität handelt
- `getFirstCommit()` - Bequemlichkeitsmethode, um die erste (oder einzige) Entität abzurufen

Für `commit()` ohne Parameter enthält das Ereignis alle Entitäten, die sich nach der Filterung derzeit im Repository befinden.

## Update-Strategien {#update-strategies}

Die beiden Commit-Signaturen dienen unterschiedlichen Zwecken:

```java
// Update einer einzelnen Entität - effizient für individuelle Änderungen
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Bulk-Update - effizient für mehrere Änderungen
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Commits für einzelne Entitäten sind chirurgisch - sie teilen den verbundenen Komponenten genau mit, welche Zeile sich geändert hat. Der [`Table`](../../components/table/overview) kann nur die Zellen dieser Zeile aktualisieren, ohne etwas anderes zu berühren.

Bulk-Commits aktualisieren alles. Verwenden Sie sie, wenn:
- Mehrere Entitäten sich geändert haben
- Sie Elemente hinzugefügt oder entfernt haben
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

// Aktuelle Ergebniszahlen
repository.onCommit(e -> {
    long count = repository.findAll().count();
    resultsLabel.setText(count + " Produkte gefunden");
});
```

Diese Listener werden bei jedem Commit ausgelöst, unabhängig davon, ob es sich um Benutzeraktionen, Datenimporte oder programmatische Updates handelt. Das Ereignis gibt Ihnen Zugriff auf die eingereichten Entitäten, aber oft werden Sie die Berechnung aus der Quellsammlung neu durchführen, um alle aktuellen Daten einzubeziehen.

## Speichermanagement {#memory-management}

Ereignis-Listener halten Referenzen auf Ihre Komponenten. Wenn Sie sie nicht entfernen, behält das `Repository` Ihre Komponenten im Speicher, selbst nachdem sie nicht mehr angezeigt werden:

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

Die Methode `onCommit()` gibt eine `ListenerRegistration` zurück. Bewahren Sie diese Referenz auf und rufen Sie `remove()` auf, wenn Ihre Komponente zerstört wird oder keine Updates mehr benötigt. Dies verhindert Speicherlecks in langfristig laufenden Anwendungen.
