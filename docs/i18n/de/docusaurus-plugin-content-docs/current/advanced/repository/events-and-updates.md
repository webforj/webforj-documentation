---
title: Events and updates
sidebar_position: 5
_i18n_hash: 45321f5a931bc6efb56376b53662be32
---
<!-- vale off -->
# Ereignisse und Aktualisierungen <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-Ereignisse ermöglichen es Ihnen, auf Datenänderungen zu reagieren. Neben den automatischen UI-Aktualisierungen können Sie auf Änderungen hören, um benutzerdefinierte Logik auszulösen.

## `Repository`-Ereignislebenszyklus {#repository-event-lifecycle}

Jeder Aufruf von `commit()` löst ein <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink> aus. Dieses Ereignis enthält Informationen darüber, was sich geändert hat:

```java
repository.onCommit(event -> {
    // Alle getätigten Entitäten abrufen
    List<Customer> commits = event.getCommits();
    
    // Überprüfen, ob es sich um ein Update einer einzelnen Entität handelt
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Aktualisiert: " + updated.getName());
    }
});
```

Das Ereignis informiert Sie über:
- `getCommits()` - Liste der entitäten, die committed wurden
- `isSingleCommit()` - Ob es sich um ein gezieltes Update einer einzelnen Entität handelt
- `getFirstCommit()` - Bequemlichkeitsmethode zum Abrufen der ersten (oder einzigen) Entität

Für `commit()` ohne Parameter enthält das Ereignis alle derzeit im Repository enthaltenen Entitäten nach Filterung.

## Aktualisierungsstrategien {#update-strategies}

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

Commits für einzelne Entitäten sind chirurgisch - sie informieren verbundene Komponenten genau, welche Zeile sich geändert hat. Die [`Table`](../../components/table/overview) kann nur die Zellen dieser Zeile aktualisieren, ohne etwas anderes zu berühren.

Bulk-Commits aktualisieren alles. Verwenden Sie sie, wenn:
- Mehrere Entitäten geändert wurden
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

// Live-Ergebnisanzahlen
repository.onCommit(e -> {
    long count = repository.findAll().count();
    resultsLabel.setText(count + " Produkte gefunden");
});
```

Diese Listener werden bei jedem Commit ausgelöst, unabhängig davon, ob es sich um Benutzeraktionen, Datenimporte oder programmatische Updates handelt. Das Ereignis gibt Ihnen Zugriff auf die committed Entitäten, aber oft berechnen Sie erneut aus der Quellsammlung, um alle aktuellen Daten einzuschließen.

## Speicherverwaltung {#memory-management}

Ereignislistener halten Referenzen auf Ihre Komponenten. Wenn Sie sie nicht entfernen, behält das `Repository` Ihre Komponenten im Speicher, auch nachdem sie nicht mehr angezeigt werden:

```java
// Referenz behalten, um sie später zu entfernen
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// Listener aufräumen, wenn die Komponente zerstört wird
if (registration != null) {
    registration.remove();
}
```

Die Methode `onCommit()` gibt eine `ListenerRegistration` zurück. Speichern Sie diese Referenz und rufen Sie `remove()` auf, wenn Ihre Komponente zerstört wird oder keine Updates mehr benötigt. Dies verhindert Speicherlecks in lang laufenden Anwendungen.
