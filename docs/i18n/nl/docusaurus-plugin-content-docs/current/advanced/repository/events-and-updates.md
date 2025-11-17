---
title: Evenementen en updates
sidebar_position: 5
_i18n_hash: b2973e75abc879992ab1e235ba5d8b5e
---
<!-- vale off -->
# Evenementen en updates <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-evenementen stellen je in staat om te reageren op gegevenswijzigingen. Buiten de automatische UI-updates kun je luisteren naar wijzigingen om aangepaste logica te triggeren.

## `Repository` evenement levenscyclus {#repository-event-lifecycle}

Elke aanroep van `commit()` genereert een <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Dit evenement bevat informatie over wat er is veranderd:

```java
repository.onCommit(event -> {
    // Verkrijg alle gecommitteerde entiteiten
    List<Customer> commits = event.getCommits();
    
    // Controleer of het een enkele entiteit update is
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Bijgewerkt: " + updated.getName());
    }
});
```

Het evenement vertelt je:
- `getCommits()` - Lijst van entiteiten die zijn gecommit
- `isSingleCommit()` - Of dit een gerichte update van een enkele entiteit was
- `getFirstCommit()` - Handige methode om de eerste (of enige) entiteit te verkrijgen

Voor `commit()` zonder parameters bevat het evenement alle entiteiten die momenteel in de repository staan na filtering.

## Update strategieën {#update-strategies}

De twee commit-handtekeningen dienen verschillende doelen:

```java
// Update van een enkele entiteit - efficiënt voor individuele wijzigingen
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Bulkupdate - efficiënt voor meerdere wijzigingen
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Commits van een enkele entiteit zijn chirurgisch - ze vertellen verbonden componenten precies welke rij is veranderd. De [`Table`](../../components/table/overview) kan alleen de cellen van die rij bijwerken zonder iets anders aan te raken.

Bulkcommits verfrissen alles. Gebruik ze wanneer:
- Meerdere entiteiten zijn veranderd
- Je items hebt toegevoegd of verwijderd
- Je niet zeker weet wat er is veranderd

## Reactieve UI patronen {#reactive-ui-patterns}

`Repository`-evenementen stellen je in staat om samenvattingsweergaven synchroon te houden met je gegevens:

```java
// Auto-updating labels
repository.onCommit(event -> {
    double total = sales.stream().mapToDouble(Sale::getAmount).sum();
    totalLabel.setText(String.format("Totaal: $%.2f", total));
    countLabel.setText("Verkopen: " + sales.size());
});

// Live resultaat tellingen
repository.onCommit(e -> {
    long count = repository.findAll().count();
    resultsLabel.setText(count + " producten gevonden");
});
```

Deze luisteraars worden geactiveerd bij elke commit, of deze nu komt van gebruikeracties, gegevensimport of programmatische updates. Het evenement geeft je toegang tot de gecommitteerde entiteiten, maar vaak zul je opnieuw berekenen vanuit de bronverzameling om alle huidige gegevens op te nemen.

## Geheugenbeheer {#memory-management}

Evenementluisteraars houden verwijzingen naar je componenten vast. Als je ze niet verwijdert, houdt de `Repository` je componenten in geheugen, zelfs als ze niet meer worden weergegeven:

```java
// Houd verwijzing om later te verwijderen
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// Maak de luisteraar schoon wanneer de component wordt vernietigd
if (registration != null) {
    registration.remove();
}
```

De methode `onCommit()` retourneert een `ListenerRegistration`. Bewaar deze referentie en roep `remove()` aan wanneer je component wordt vernietigd of geen updates meer nodig heeft. Dit voorkomt geheugenlekken in langdurige toepassingen.
