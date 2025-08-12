---
title: Custom data sources
sidebar_position: 4
_i18n_hash: 44f087c7c2308fc7a0c3b8c4c4246531
---
<!-- vale off -->
# Aangepaste gegevensbronnen <DocChip chip='since' label='25.02' />
<!-- vale on -->

Wanneer je gegevens buiten je app worden opgeslagen - in een REST API, database of externe service - moet je een aangepaste repository-implementatie maken. De <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> klasse maakt dit eenvoudig door je in staat te stellen functies aan te bieden in plaats van een volledige klasse te implementeren.

## Hoe `DelegatingRepository` werkt {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> is een concrete klasse die <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> uitbreidt. In plaats van abstracte methoden te implementeren, geef je drie functies op in de constructor:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Vindfunctie - retourneert gefilterde/gesorteerde/gepaginateerde gegevens
    criteria -> userService.findUsers(criteria),
    
    // 2. Aantalfunctie - retourneert totaal aantal voor de filter
    criteria -> userService.countUsers(criteria),
    
    // 3. Vindfunctie op sleutel - retourneert enkele entiteit op ID
    userId -> userService.findById(userId)
);
```

Elke functie heeft een specifieke doel:

**Vindfunctie** ontvangt een `RepositoryCriteria` object met:
- `getFilter()` - jouw aangepaste filterobject (de `F` typeparameter)
- `getOffset()` en `getLimit()` - voor paginering
- `getOrderCriteria()` - lijst van sorteerregels

Deze functie moet een `Stream<T>` van entiteiten retourneren die aan de criteria voldoen. De stream kan leeg zijn als er geen overeenkomsten zijn gevonden.

**Aantalfunctie** ontvangt ook de criteria, maar gebruikt meestal alleen het filterdeel. Het retourneert het totale aantal van overeenkomende entiteiten, waarbij paginering wordt genegeerd. Dit wordt gebruikt door UI-componenten om het totale aantal resultaten weer te geven of pagina's te berekenen.

**Vindfunctie op sleutel** ontvangt een entiteit sleutel (meestal een ID) en retourneert een `Optional<T>`. Retourneer `Optional.empty()` als de entiteit niet bestaat.

## REST API voorbeeld {#rest-api-example}

Bij integratie met een REST API moet je de repositorycriteria omzetten naar HTTP-verzoekparameters. Begin met het definiëren van een filterklasse die overeenkomt met de query-mogelijkheden van je API:

```java
public class UserFilter {
    private String department;
    private String status;
    // getters en setters...
}
```

Deze filterklasse representeert de zoekparameters die je API accepteert. De repository zal instanties van deze klasse doorgeven aan jouw functies wanneer filtering wordt toegepast.

Maak de repository met functies die criteria vertalen naar API-aanroepen:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
    // Vind gebruikers
    criteria -> {
        Map<String, String> params = buildParams(criteria);
        List<User> users = restClient.get("/users", params);
        return users.stream();
    },
    
    // Aantal gebruikers
    criteria -> {
        Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
        return restClient.getCount("/users/count", filterParams);
    },
    
    // Vind op ID
    userId -> restClient.getById("/users/" + userId)
);
```

De `buildParams()` methode zou waarden uit de criteria extraheren en omzetten naar queryparameters zoals `?department=Sales&status=active&offset=20&limit=10`. Jouw REST-client doet vervolgens de daadwerkelijke HTTP-aanroep en deserialiseert de reactie.

## Database voorbeeld {#database-example}

Database-integratie volgt een vergelijkbaar patroon, maar zet criteria om naar SQL-query's. Het belangrijke verschil is het omgaan met SQL-generatie en parameterbinding:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // Query met filter, sorteren, paginering
    criteria -> {
        String sql = buildQuery(criteria);
        return jdbcTemplate.queryForStream(sql, rowMapper);
    },
    
    // Aantal overeenkomende records
    criteria -> {
        String countSql = buildCountQuery(criteria.getFilter());
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    },
    
    // Vind op primaire sleutel
    customerId -> {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
    }
);
```

De `buildQuery()` methode zou SQL construeren zoals:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

De eigenschappen van jouw filterobject worden gemapt naar de `WHERE`-clausulecondities, terwijl paginering en sortering worden afgehandeld via `LIMIT/OFFSET` en `ORDER BY` clausules.

## Gebruik met UI-componenten {#using-with-ui-components}

De schoonheid van het repositorypatroon is dat UI-componenten niet weten of zich bekommeren waar de gegevens vandaan komen. Of het nu een in-memory collectie, REST API of database is, het gebruik is identiek:

```java
// Maak en configureer repository
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Koppel aan tabel
Table<User> table = new Table<>();
table.setRepository(repository);

// Tabel toont automatisch gefilterde engineering gebruikers
```

Wanneer gebruikers interactie hebben met de [`Table`](../../components/table/overview) (kolommen sorteren, pagina's wijzigen), roept de `Table` jouw repositoryfuncties aan met bijgewerkte criteria. Jouw functies vertalen deze naar API-aanroepen of SQL-query's, en de tabel werkt automatisch bij met de resultaten.

## Wanneer `AbstractQueryableRepository` uit te breiden {#when-to-extend-abstractqueryablerepository}

Als je aangepaste methoden of complexe initialisatie nodig hebt, breid dan <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> rechtstreeks uit:

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
    @Override
    public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
        // Implementatie
    }
    
    @Override
    public int size(RepositoryCriteria<User, UserFilter> criteria) {
        // Implementatie
    }
    
    @Override
    public Optional<User> find(Object key) {
        // Implementatie
    }
    
    // Voeg aangepaste methoden toe
    public List<User> findActiveManagers() {
        // Aangepaste query-logica
    }
}
```
