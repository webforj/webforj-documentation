---
title: Events and updates
sidebar_position: 5
description: >-
  React to repository commits with RepositoryCommitEvent listeners, choose
  single-entity or bulk updates, and manage listener registrations.
_i18n_hash: 9ce2e7d25b38cd0d04acd30c80edffb3
---
<!-- vale off -->
# Evenementen en updates <DocChip chip='since' label='24.00' />
<!-- vale on -->

`Repository`-evenementen stellen je in staat te reageren op dataveranderingen. Naast de automatische UI-updates, kun je luisteren naar wijzigingen om aangepaste logica te activeren.

## `Repository` evenement levenscyclus {#repository-event-lifecycle}

Elke `commit()` aanroep genereert een <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Dit evenement bevat informatie over wat er is veranderd:

```java
repository.onCommit(event -> {
  // Haal alle gecommitteerde entiteiten op
  List<Customer> commits = event.getCommits();

  // Controleer of het een update van een enkele entiteit is
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Bijgewerkt: " + updated.getName());
  }
});
```

Het evenement vertelt je:
- `getCommits()` - Lijst van entiteiten die zijn gecommitteerd
- `isSingleCommit()` - Of dit een gerichte update van een enkele entiteit was
- `getFirstCommit()` - Handige methode om de eerste (of enige) entiteit op te halen

Voor `commit()` zonder parameters bevat het evenement alle entiteiten die momenteel in het repository staan na filtering.

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

Updates van enkele entiteiten zijn chirurgisch - ze vertellen verbonden componenten exact welke rij is gewijzigd. De [`Table`](../../components/table/overview) kan alleen de cellen van die rij bijwerken zonder iets anders aan te raken.

Bulkcommits vernieuwen alles. Gebruik ze wanneer:
- Meerdere entiteiten zijn gewijzigd
- Je items hebt toegevoegd of verwijderd
- Je niet zeker weet wat is gewijzigd

## Reactieve UI-patronen {#reactive-ui-patterns}

`Repository`-evenementen laten je toe om samenvattende weergaven in sync te houden met je gegevens:

```java
// Auto-updating labels
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Totaal: $%.2f", total));
  countLabel.setText("Verkoop: " + sales.size());
});

// Live resultaat tellingen
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " producten gevonden");
});
```

Deze listeners worden geactiveerd bij elke commit, of deze nu voortkomen uit gebruikersacties, gegevensimport of programmat updates. Het evenement geeft je toegang tot de gecommitteerde entiteiten, maar vaak zul je opnieuw berekenen vanaf de broncollectie om alle huidige gegevens op te nemen.

## Geheugenbeheer {#memory-management}

Evenementlisteners houden verwijzingen naar je componenten. Als je ze niet verwijdert, houdt de `Repository` je componenten in het geheugen, zelfs nadat ze niet meer worden weergegeven:

```java
// Houd verwijzing vast om later te verwijderen
ListenerRegistration<RepositoryCommitEvent<Data>> registration =
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Ruim listener op wanneer component wordt vernietigd
if (registration != null) {
  registration.remove();
}
```

De `onCommit()` methode retourneert een `ListenerRegistration`. Bewaar deze verwijzing en roep `remove()` aan wanneer je component wordt vernietigd of geen updates meer nodig heeft. Dit voorkomt geheugenlekken in langdurige applicaties.
