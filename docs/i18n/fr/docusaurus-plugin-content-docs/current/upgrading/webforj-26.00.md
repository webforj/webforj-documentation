---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
draft: true
_i18n_hash: 505eca822769ef1f30adc7acca089fac
---
Cette documentation sert de guide pour mettre à niveau les applications webforJ de la version 25.00 à 26.00. Voici les changements nécessaires pour que les applications existantes continuent de fonctionner sans problème. Comme toujours, consultez la [vue d'ensemble des versions sur GitHub](https://github.com/webforj/webforj/releases) pour une liste plus complète des changements entre les versions.

## Filtres pour `Repository`

L'interface `RetrievalCriteria` est supprimée dans webforJ 26.00, y compris la méthode `setFilter()`. Au lieu d'utiliser l'interface générique `Repository`, utilisez soit `CollectionRepository` pour des filtres simples, soit [`QueryableRepository`](/docs/advanced/repository/querying-data) pour des types de filtrage plus avancés, le tri et la pagination.

### Avant :
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Après :
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```
