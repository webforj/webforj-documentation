---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
draft: true
_i18n_hash: 505eca822769ef1f30adc7acca089fac
---
Diese Dokumentation dient als Leitfaden zum Upgrade von webforJ-Apps von 25.00 auf 26.00. 
Hier sind die erforderlichen Änderungen, damit vorhandene Apps weiterhin reibungslos funktionieren. 
Wie immer, siehe die [GitHub-Veröffentlichungsübersicht](https://github.com/webforj/webforj/releases) für eine umfassendere Liste von Änderungen zwischen den Versionen.

## Filter für `Repository`

Die `RetrievalCriteria`-Schnittstelle wurde in webforJ 26.00 entfernt, einschließlich der Methode `setFilter()`. Anstelle der generischen `Repository`-Schnittstelle verwenden Sie entweder `CollectionRepository` für einfache Filter oder [`QueryableRepository`](/docs/advanced/repository/querying-data) für komplexere Filtertypen, Sortierung und Seitenpagination.

### Vorher:
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Nachher:
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```
