---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
draft: true
_i18n_hash: 505eca822769ef1f30adc7acca089fac
---
Deze documentatie dient als een gids voor het upgraden van webforJ-apps van 25.00 naar 26.00. Hier zijn de benodigde wijzigingen zodat bestaande apps soepel blijven draaien. Zoals altijd, zie de [GitHub release-overzicht](https://github.com/webforj/webforj/releases) voor een uitgebreidere lijst van wijzigingen tussen releases.

## Filters voor `Repository`

De `RetrievalCriteria` interface is verwijderd in webforJ 26.00, inclusief de `setFilter()` methode. In plaats van de generieke `Repository` interface te gebruiken, gebruik je ofwel `CollectionRepository` voor eenvoudige filters, of [`QueryableRepository`](/docs/advanced/repository/querying-data) voor meer geavanceerde filtertypes, sorteren en paginering.

### Voor:
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Na:
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```
