---
title: Events and updates
sidebar_position: 5
_i18n_hash: 45321f5a931bc6efb56376b53662be32
---
<!-- vale off -->
# Evenementen en updates <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-evenementen laten je reageren op dataveranderingen. Naast de automatische UI-updates kun je luisteren naar wijzigingen om aangepaste logica te activeren.

## `Repository` evenement levenscyclus {#repository-event-lifecycle}

Elke aanroep van `commit()` activeert een <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Dit evenement bevat informatie over wat er is veranderd:

```java
repository.onCommit(event -> {
    // Verkrijg alle gecommitteerde entiteiten
    List<Customer> commits = event.getCommits();
    
    // Controleer of het een enkele entiteit update betreft
    if (event.isSingleCommit()) {
        Customer updated = event.getFirstCommit();
        System.out.println("Bijgewerkt: " + updated.getName());
    }
});
```

Het evenement vertelt je:
- `getCommits()` - Lijst van entiteiten die zijn gecommitteerd
- `isSingleCommit()` - Of dit een gerichte update van een enkele entiteit was
- `getFirstCommit()` - Handige methode om de eerste (of enige) entiteit te verkrijgen

Voor `commit()` zonder parameters bevat het evenement alle entiteiten die momenteel in de repository aanwezig zijn na filtering.

## Update strategieën {#update-strategies}

De twee commit-handtekeningen dienen verschillende doeleinden:

```java
// Update van enkele entiteit - efficiënt voor individuele wijzigingen
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Bulk update - efficiënt voor meerdere wijzigingen
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Single-entity commits zijn chirurgisch - ze vertellen verbonden componenten precies welke rij is veranderd. De [`Tabel`](../../components/table/overview) kan alleen die rijcellen bijwerken zonder verder iets aan te raken.

Bulk commits vernieuwen alles. Gebruik ze wanneer:
- Meerdere entiteiten zijn veranderd
- Je items hebt toegevoegd of verwijderd
- Je niet zeker weet wat er veranderd is

## Reactieve UI patronen {#reactive-ui-patterns}

`Repository`-evenementen laten je samenvattingsschermen in sync houden met je gegevens:

```java
// Automatisch bijwerkende labels
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

Deze listeners worden geactiveerd bij elke commit, of deze nu afkomstig is van gebruikersacties, gegevensimporten of programmaticale updates. Het evenement geeft je toegang tot de gecommitteerde entiteiten, maar vaak zul je opnieuw berekenen vanaf de bronsverzameling om alle huidige gegevens op te nemen.

## Geheugenbeheer {#memory-management}

Evenementlisteners houden verwijzingen naar je componenten vast. Als je ze niet verwijdert, houdt de `Repository` je componenten in het geheugen, zelfs nadat ze niet langer worden weergegeven:

```java
// Houd referentie om later te verwijderen
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
    repository.onCommit(event -> {
        updateDisplay(event.getCommits());
    });

// Maak listener schoon wanneer component wordt vernietigd
if (registration != null) {
    registration.remove();
}
```

De methode `onCommit()` retourneert een `ListenerRegistration`. Bewaar deze referentie en roep `remove()` aan wanneer je component wordt vernietigd of niet langer updates nodig heeft. Dit voorkomt geheugenlekken in langlopende toepassingen.
