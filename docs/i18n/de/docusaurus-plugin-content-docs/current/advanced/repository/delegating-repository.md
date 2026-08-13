---
title: Custom data sources
sidebar_position: 4
description: >-
  Wire REST APIs, databases, or external services to webforJ components by
  supplying find, count, and key-lookup functions to DelegatingRepository.
_i18n_hash: 7d203b803816c64e9ca77d8b49bf34ed
---
<!-- vale off -->
# Benutzerdefinierte Datenquellen <DocChip chip='since' label='25.02' />
<!-- vale on -->

Wenn Ihre Daten außerhalb Ihrer App liegen - in einer REST-API, Datenbank oder einem externen Dienst - müssen Sie eine benutzerdefinierte Repository-Implementierung erstellen. Die <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink>-Klasse macht dies einfach, indem Sie Funktionen bereitstellen, anstatt eine vollständige Klasse zu implementieren.

## Wie `DelegatingRepository` funktioniert {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> ist eine konkrete Klasse, die die <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> erweitert. Anstelle der Implementierung abstrakter Methoden geben Sie drei Funktionen im Konstruktor an:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. Find-Funktion - gibt gefilterte/sortierte/paginierte Daten zurück
  criteria -> userService.findUsers(criteria),

  // 2. Count-Funktion - gibt die Gesamtanzahl für den Filter zurück
  criteria -> userService.countUsers(criteria),

  // 3. Find-by-Key-Funktion - gibt eine einzelne Entität nach ID zurück
  userId -> userService.findById(userId)
);
```

Jede Funktion hat einen bestimmten Zweck:

**Find-Funktion** erhält ein `RepositoryCriteria`-Objekt, das Folgendes enthält:
- `getFilter()` - Ihr benutzerdefiniertes Filterobjekt (den `F`-Typen-Parameter)
- `getOffset()` und `getLimit()` - für die Paginierung
- `getOrderCriteria()` - Liste von Sortierregeln

Diese Funktion muss einen `Stream<T>` von Entitäten zurückgeben, die den Kriterien entsprechen. Der Stream kann leer sein, wenn keine Übereinstimmungen gefunden werden.

**Count-Funktion** erhält ebenfalls die Kriterien, verwendet jedoch typischerweise nur den Filterteil. Sie gibt die Gesamtanzahl der übereinstimmenden Entitäten zurück, ignoriert die Paginierung. Dies wird von UI-Komponenten verwendet, um die Gesamtergebnisse anzuzeigen oder Seiten zu berechnen.

**Find-by-Key-Funktion** erhält einen Entitätsschlüssel (normalerweise eine ID) und gibt ein `Optional<T>` zurück. Geben Sie `Optional.empty()` zurück, wenn die Entität nicht existiert.

## REST-API-Beispiel {#rest-api-example}

Bei der Integration mit einer REST-API müssen Sie die Repository-Kriterien in HTTP-Anforderungsparameter umwandeln. Beginnen Sie mit der Definition einer Filterklasse, die mit den Abfragefähigkeiten Ihrer API übereinstimmt:

```java
public class UserFilter {
  private String department;
  private String status;
  // Getter und Setter...
}
```

Diese Filterklasse repräsentiert die Suchparameter, die Ihre API akzeptiert. Das Repository übergibt Instanzen dieser Klasse an Ihre Funktionen, wenn Filter angewendet werden.

Erstellen Sie das Repository mit Funktionen, die Kriterien in API-Aufrufe übersetzen:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
  // Benutzer finden
  criteria -> {
    Map<String, String> params = buildParams(criteria);
    List<User> users = restClient.get("/users", params);
    return users.stream();
  },

  // Benutzer zählen
  criteria -> {
    Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
    return restClient.getCount("/users/count", filterParams);
  },

  // Nach ID finden
  userId -> restClient.getById("/users/" + userId)
);
```

Die Methode `buildParams()` würde Werte aus den Kriterien extrahieren und sie in Abfrageparameter wie `?department=Sales&status=active&offset=20&limit=10` umwandeln. Ihr REST-Client führt dann die tatsächliche HTTP-Anforderung durch und deserialisiert die Antwort.

## Datenbankbeispiel {#database-example}

Die Datenbankintegration folgt einem ähnlichen Muster, wandelt jedoch Kriterien in SQL-Abfragen um. Der Hauptunterschied besteht im Umgang mit SQL-Generierung und Parameterbindung:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
  // Abfrage mit Filter, Sortierung, Paginierung
  criteria -> {
    String sql = buildQuery(criteria);
    return jdbcTemplate.queryForStream(sql, rowMapper);
  },

  // Übereinstimmende Datensätze zählen
  criteria -> {
    String countSql = buildCountQuery(criteria.getFilter());
    return jdbcTemplate.queryForObject(countSql, Integer.class);
  },

  // Nach Primärschlüssel suchen
  customerId -> {
    String sql = "SELECT * FROM customers WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
  }
);
```

Die Methode `buildQuery()` würde SQL wie folgt konstruieren:
```sql
SELECT * FROM customers
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Die Eigenschaften Ihres Filterobjekts werden den Bedingungen der `WHERE`-Klausel zugeordnet, während Paginierung und Sortierung über `LIMIT/OFFSET` und `ORDER BY`-Klauseln gehandhabt werden.

## Verwendung mit UI-Komponenten {#using-with-ui-components}

Die Schönheit des Repository-Musters besteht darin, dass UI-Komponenten nicht wissen oder sich darum kümmern, woher die Daten stammen. Ob es sich um eine In-Memory-Sammlung, eine REST-API oder eine Datenbank handelt, die Verwendung ist identisch:

```java
// Repository erstellen und konfigurieren
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// An Tabelle anhängen
Table<User> table = new Table<>();
table.setRepository(repository);

// Die Tabelle zeigt automatisch gefilterte Benutzer aus der Engineering-Abteilung an
```

Wenn Benutzer mit der [`Table`](../../components/table/overview) (Sortieren von Spalten, Ändern von Seiten) interagieren, ruft die `Table` Ihre Repository-Funktionen mit aktualisierten Kriterien auf. Ihre Funktionen übersetzen diese in API-Aufrufe oder SQL-Abfragen, und die Tabelle wird automatisch mit den Ergebnissen aktualisiert.

## Wann `AbstractQueryableRepository` erweitern {#when-to-extend-abstractqueryablerepository}

Wenn Sie benutzerdefinierte Methoden oder komplexe Initialisierungen benötigen, erweitern Sie die <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> direkt:

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
  @Override
  public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
    // Implementierung
  }

  @Override
  public int size(RepositoryCriteria<User, UserFilter> criteria) {
    // Implementierung
  }

  @Override
  public Optional<User> find(Object key) {
    // Implementierung
  }

  // Fügen Sie benutzerdefinierte Methoden hinzu
  public List<User> findActiveManagers() {
    // Benutzerdefinierte Abfragelogik
  }
}
```
