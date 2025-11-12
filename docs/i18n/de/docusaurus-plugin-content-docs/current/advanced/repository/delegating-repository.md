---
title: Benutzerdefinierte Datenquellen
sidebar_position: 4
_i18n_hash: 44f087c7c2308fc7a0c3b8c4c4246531
---
<!-- vale off -->
# Benutzerdefinierte Datenquellen <DocChip chip='since' label='25.02' />
<!-- vale on -->

Wenn Ihre Daten außerhalb Ihrer App - in einer REST-API, Datenbank oder externen Dienst - leben, müssen Sie eine benutzerdefinierte Repository-Implementierung erstellen. Die <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> Klasse macht dies einfach, indem sie es Ihnen ermöglicht, Funktionen bereitzustellen, anstatt eine vollständige Klasse zu implementieren.

## Wie `DelegatingRepository` funktioniert {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> ist eine konkrete Klasse, die <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> erweitert. Anstatt abstrakte Methoden zu implementieren, stellen Sie im Konstruktor drei Funktionen bereit:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Find-Funktion - gibt gefilterte/sortierte/seitengerechte Daten zurück
    criteria -> userService.findUsers(criteria),
    
    // 2. Count-Funktion - gibt die Gesamtanzahl für den Filter zurück
    criteria -> userService.countUsers(criteria),
    
    // 3. Find by key-Funktion - gibt eine einzelne Entität nach ID zurück
    userId -> userService.findById(userId)
);
```

Jede Funktion hat einen spezifischen Zweck:

**Find-Funktion** erhält ein `RepositoryCriteria`-Objekt, das enthält:
- `getFilter()` - Ihr benutzerdefiniertes Filterobjekt (der `F` Typparameter)
- `getOffset()` und `getLimit()` - für die Paginierung
- `getOrderCriteria()` - Liste von Sortierregeln

Diese Funktion muss einen `Stream<T>` von Entitäten zurückgeben, die den Kriterien entsprechen. Der Stream kann leer sein, wenn keine Übereinstimmungen gefunden werden.

**Count-Funktion** erhält ebenfalls die Kriterien, verwendet aber typischerweise nur den Filterteil. Sie gibt die Gesamtanzahl der übereinstimmenden Entitäten zurück und ignoriert die Paginierung. Dies wird von UI-Komponenten verwendet, um die Gesamtzahl der Ergebnisse anzuzeigen oder Seiten zu berechnen.

**Find by key-Funktion** erhält einen Entitätsschlüssel (in der Regel eine ID) und gibt ein `Optional<T>` zurück. Geben Sie `Optional.empty()` zurück, wenn die Entität nicht existiert.

## REST API-Beispiel {#rest-api-example}

Bei der Integration mit einer REST-API müssen Sie die Repository-Kriterien in HTTP-Anforderungsparameter umwandeln. Beginnen Sie mit der Definition einer Filterklasse, die zu den Abfragemöglichkeiten Ihrer API passt:

```java
public class UserFilter {
    private String department;
    private String status;
    // Getter und Setter...
}
```

Diese Filterklasse repräsentiert die Suchparameter, die Ihre API akzeptiert. Das Repository wird Instanzen dieser Klasse an Ihre Funktionen übergeben, wenn das Filtern angewendet wird.

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
    
    // Nach ID suchen
    userId -> restClient.getById("/users/" + userId)
);
```

Die Methode `buildParams()` würde Werte aus den Kriterien extrahieren und in Abfrageparameter wie `?department=Sales&status=active&offset=20&limit=10` umwandeln. Ihr REST-Client führt dann die tatsächliche HTTP-Anforderung aus und deserialisiert die Antwort.

## Datenbankbeispiel {#database-example}

Die Datenbankintegration folgt einem ähnlichen Muster, wandelt jedoch Kriterien in SQL-Abfragen um. Der wesentliche Unterschied besteht in der Handhabung der SQL-Generierung und der Parameterbindung:

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

Die Eigenschaften Ihres Filterobjektsmap entsprechen den Bedingungen der `WHERE`-Klausel, während Paginierung und Sortierung über `LIMIT/OFFSET` und `ORDER BY`-Klauseln gehandhabt werden.

## Verwendung mit UI-Komponenten {#using-with-ui-components}

Die Schönheit des Repository-Musters besteht darin, dass UI-Komponenten nicht wissen oder sich darum kümmern, woher die Daten stammen. Ob es sich um eine In-Memory-Sammlung, REST-API oder Datenbank handelt, die Nutzung ist identisch:

```java
// Repository erstellen und konfigurieren
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// An Tabelle anhängen
Table<User> table = new Table<>();
table.setRepository(repository);

// Tabelle zeigt automatisch gefilterte Benutzer der Ingenieurabteilung an
```

Wenn Benutzer mit der [`Table`](../../components/table/overview) (Sortierung der Spalten, Wechseln der Seiten) interagieren, ruft die `Table` Ihre Repository-Funktionen mit aktualisierten Kriterien auf. Ihre Funktionen übersetzen diese in API-Aufrufe oder SQL-Abfragen, und die Tabelle wird automatisch mit den Ergebnissen aktualisiert.

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
    
    // Fügen Sie benutzerdefinierte Methoden hinzu
    public List<User> findActiveManagers() {
        // Logik für benutzerdefinierte Abfragen
    }
}
```
