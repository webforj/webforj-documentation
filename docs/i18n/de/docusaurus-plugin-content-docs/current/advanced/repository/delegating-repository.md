---
title: Custom data sources
sidebar_position: 4
_i18n_hash: 7ead4a1af63b9c20d81dc2fd9b67380f
---
<!-- vale off -->
# Benutzerdefinierte Datenquellen <DocChip chip='since' label='25.02' />
<!-- vale on -->

Wenn Ihre Daten außerhalb Ihrer Anwendung - in einer REST-API, einer Datenbank oder einem externen Dienst - liegen, müssen Sie eine benutzerdefinierte Repository-Implementierung erstellen. Die <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink>-Klasse macht dies einfach, indem sie Ihnen erlaubt, Funktionen anzubieten, anstatt eine vollständige Klasse zu implementieren.

## Wie `DelegatingRepository` funktioniert {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> ist eine konkrete Klasse, die <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> erweitert. Anstelle abstrakter Methoden zu implementieren, geben Sie im Konstruktor drei Funktionen an:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. Suchfunktion - gibt gefilterte, sortierte, paginierte Daten zurück
  criteria -> userService.findUsers(criteria),
  
  // 2. Zählfunktion - gibt die Gesamtzahl für den Filter zurück
  criteria -> userService.countUsers(criteria),
  
  // 3. Suche nach Schlüssel - gibt eine einzelne Entität nach ID zurück
  userId -> userService.findById(userId)
);
```

Jede Funktion erfüllt einen bestimmten Zweck:

**Suchfunktion** erhält ein `RepositoryCriteria`-Objekt, das enthält:
- `getFilter()` - Ihr benutzerdefiniertes Filterobjekt (der `F`-Typparameter)
- `getOffset()` und `getLimit()` - für die Paginierung
- `getOrderCriteria()` - Liste der Sortierregeln

Diese Funktion muss einen `Stream<T>` von Entitäten zurückgeben, die den Kriterien entsprechen. Der Stream kann leer sein, wenn keine Übereinstimmungen gefunden werden.

**Zählfunktion** erhält ebenfalls die Kriterien, verwendet jedoch typischerweise nur den Filterteil. Sie gibt die Gesamtzahl der übereinstimmenden Entitäten zurück, ignoriert die Paginierung. Dies wird von UI-Komponenten verwendet, um die Gesamtzahl der Ergebnisse anzuzeigen oder Seiten zu berechnen.

**Suche nach Schlüssel** erhält einen Entitätsschlüssel (normalerweise eine ID) und gibt ein `Optional<T>` zurück. Geben Sie `Optional.empty()` zurück, wenn die Entität nicht existiert.

## REST API Beispiel {#rest-api-example}

Bei der Integration mit einer REST-API müssen Sie die Repository-Kriterien in HTTP-Anforderungsparameter umwandeln. Beginnen Sie mit der Definition einer Filterklasse, die mit den Abfragefähigkeiten Ihrer API übereinstimmt:

```java
public class UserFilter {
  private String department;
  private String status;
  // Getter und Setter...
}
```

Diese Filterklasse repräsentiert die Suchparameter, die Ihre API akzeptiert. Das Repository wird Instanzen dieser Klasse an Ihre Funktionen übergeben, wenn der Filter angewendet wird.

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
  
  // Suche nach ID
  userId -> restClient.getById("/users/" + userId)
);
```

Die `buildParams()`-Methode würde Werte aus den Kriterien extrahieren und in Abfrageparameter wie `?department=Sales&status=active&offset=20&limit=10` umwandeln. Ihr REST-Client führt dann die tatsächliche HTTP-Anforderung durch und deserialisiert die Antwort.

## Datenbankbeispiel {#database-example}

Die Datenbankintegration folgt einem ähnlichen Muster, wandelt jedoch Kriterien in SQL-Abfragen um. Der wesentliche Unterschied besteht darin, wie SQL-Generierung und Parameterbindung gehandhabt werden:

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
  
  // Suche nach Primärschlüssel
  customerId -> {
    String sql = "SELECT * FROM customers WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
  }
);
```

Die `buildQuery()`-Methode würde SQL konstruieren wie:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Die Eigenschaften Ihres Filterobjekts entsprechen den Bedingungen der `WHERE`-Klausel, während Paginierung und Sortierung durch `LIMIT/OFFSET` und `ORDER BY`-Klauseln behandelt werden.

## Verwendung mit UI-Komponenten {#using-with-ui-components}

Die Schönheit des Repository-Musters besteht darin, dass UI-Komponenten nicht wissen oder sich darum kümmern, woher die Daten kommen. Ob es sich um eine in-memory Sammlung, eine REST-API oder eine Datenbank handelt, die Verwendung ist identisch:

```java
// Erstellen und Konfigurieren des Repositories
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// An die Tabelle anhängen
Table<User> table = new Table<>();
table.setRepository(repository);

// Die Tabelle zeigt automatisch gefilterte Ingenieure an
```

Wenn Benutzer mit der [`Table`](../../components/table/overview) (Sortierung der Spalten, Seitenwechsel) interagieren, ruft die `Table` Ihre Repository-Funktionen mit aktualisierten Kriterien auf. Ihre Funktionen übersetzen diese in API-Aufrufe oder SQL-Abfragen, und die Tabelle aktualisiert sich automatisch mit den Ergebnissen.

## Wann `AbstractQueryableRepository` erweitern {#when-to-extend-abstractqueryablerepository}

Wenn Sie benutzerdefinierte Methoden oder komplexe Initialisierungen benötigen, erweitern Sie <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> direkt:

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
  
  // Benutzerdefinierte Methoden hinzufügen
  public List<User> findActiveManagers() {
    // Logik für benutzerdefinierte Abfragen
  }
}
```
