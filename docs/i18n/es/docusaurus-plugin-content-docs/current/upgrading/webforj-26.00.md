---
title: Upgrade to 26.00
description: Upgrade from 25.00 to 26.00
pagination_next: null
draft: true
_i18n_hash: 505eca822769ef1f30adc7acca089fac
---
Esta documentación sirve como guía para actualizar aplicaciones de webforJ de la versión 25.00 a la 26.00.  
Aquí están los cambios necesarios para que las aplicaciones existentes continúen funcionando sin problemas.  
Como siempre, consulte la [visión general de lanzamientos de GitHub](https://github.com/webforj/webforj/releases) para obtener una lista más completa de cambios entre versiones.

## Filtros para `Repository`

La interfaz `RetrievalCriteria` se ha eliminado en webforJ 26.00, incluyendo el método `setFilter()`. En lugar de utilizar la interfaz genérica `Repository`, use `CollectionRepository` para filtros simples, o [`QueryableRepository`](/docs/advanced/repository/querying-data) para tipos de filtrado más avanzados, ordenamiento y paginación.

### Antes:
```java
private String searchTerm = "";

Repository<CustomerRecord> repository = new Repository<>();

repository.setFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```

### Después:
```java {3,5}
private String searchTerm = "";

CollectionRepository<CustomerRecord> repository = new CollectionRepository<>();

repository.setBaseFilter((CustomerRecord r) -> {
  String title = r.getTitle();
  return title.toLowerCase().contains(this.searchTerm);
  });
```
