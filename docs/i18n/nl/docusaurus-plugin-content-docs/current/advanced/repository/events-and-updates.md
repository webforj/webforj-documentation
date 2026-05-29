---
title: Events and updates
sidebar_position: 5
_i18n_hash: 0f7a5576086e2922c0549eae23e66061
---
<!-- vale off -->
# Evenementen en updates <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-evenementen stellen je in staat om te reageren op gegevenswijzigingen. Naast de automatische UI-updates, kun je luisteren naar veranderingen om aangepaste logica te activeren.

## `Repository` evenement levenscyclus {#repository-event-lifecycle}

Elke `commit()` aanroep genereert een <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Dit evenement bevat informatie over wat is veranderd:

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
- `getCommits()` - Lijst van entiteiten die zijn gecommitteerd
- `isSingleCommit()` - Of dit een gerichte update van een enkele entiteit was
- `getFirstCommit()` - Handige methode om de eerste (of enige) entiteit te krijgen

Voor `commit()` zonder parameters, bevat het evenement alle entiteiten die momenteel in het repository zijn na filtering.

## Update-strategieën {#update-strategies}

De twee commit-handtekeningen dienen verschillende doelen:

```java
// Single-entity update - efficiënt voor individuele wijzigingen
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Bulk update - efficiënt voor meerdere wijzigingen
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Single-entity commits zijn chirurgisch - ze vertellen verbonden componenten precies welke rij is veranderd. De [`Table`](../../components/table/overview) kan alleen de cellen van die rij bijwerken zonder andere elementen aan te raken.

Bulk commits vernieuwen alles. Gebruik ze wanneer:
- Meerdere entiteiten zijn veranderd
- Je items hebt toegevoegd of verwijderd
- Je niet zeker weet wat er is veranderd

## Reactieve UI-patronen {#reactive-ui-patterns}

`Repository`-evenementen stellen je in staat om samenvattende weergaven in sync te houden met je gegevens:

```java
// Auto-updating labels
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Totaal: $%.2f", total));
  countLabel.setText("Verkopen: " + sales.size());
});

// Live resultaat aantallen
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " producten gevonden");
});
```

Deze listeners worden geactiveerd bij elke commit, of het nu gaat om gebruikersacties, gegevensimporten of programmatische updates. Het evenement geeft je toegang tot de gecommitteerde entiteiten, maar vaak zul je opnieuw berekenen vanaf de broncollectie om alle huidige gegevens op te nemen.

## Geheugenbeheer {#memory-management}

Evenementlisteners houden referenties naar je componenten. Als je ze niet verwijdert, houdt de `Repository` je componenten in het geheugen, zelfs als ze niet langer worden weergegeven:

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

De `onCommit()`-methode retourneert een `ListenerRegistration`. Bewaar deze referentie en roep `remove()` aan wanneer je component wordt vernietigd of niet langer updates nodig heeft. Dit voorkomt geheugenlekken in langdurige toepassingen.
