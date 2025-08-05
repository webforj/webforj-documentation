---
title: Custom data sources
sidebar_position: 4
_i18n_hash: dbcaaa420987ee45f54d3f4059e98d92
---
<!-- vale off -->
# Aangepaste gegevensbronnen <DocChip chip='since' label='25.02' />
<!-- vale on -->

Wanneer uw gegevens buiten uw app leven - in een REST API, database of externe service - moet u een aangepaste repository-implementatie creëren. De <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink>-klasse maakt dit eenvoudig door u in staat te stellen functies te bieden in plaats van een volledige klasse te implementeren.

## Hoe `DelegatingRepository` werkt {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> is een concrete klasse die <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> uitbreidt. In plaats van abstracte methoden te implementeren, biedt u drie functies in de constructor aan:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Zoekfunctie - retourneert gefilterde/geordende/gepaginate gegevens
    criteria -> userService.findUsers(criteria),
    
    // 2. Aantal functie - retourneert totaal aantal voor de filter
    criteria -> userService.countUsers(criteria),
    
    // 3. Vind per sleutel functie - retourneert enkelentiteit per ID
    userId -> userService.findById(userId)
);
```

Elke functie heeft een specifieke bedoeling:

**Zoekfunctie** ontvangt een `RepositoryCriteria`-object met:
- `getFilter()` - uw aangepaste filterobject (de `F` typeparameter)
- `getOffset()` en `getLimit()` - voor paginering
- `getOrderCriteria()` - lijst met sorteerrichtlijnen

Deze functie moet een `Stream<T>` van entiteiten retourneren die aan de criteria voldoen. De stream kan leeg zijn als er geen overeenkomsten worden gevonden.

**Aantal functie** ontvangt ook de criteria, maar gebruikt doorgaans slechts het filtergedeelte. Het retourneert het totale aantal overeenkomende entiteiten, waarbij paginering wordt genegeerd. Dit wordt gebruikt door UI-componenten om het totale aantal resultaten weer te geven of pagina's te berekenen.

**Vind per sleutel functie** ontvangt een entiteitssleutel (meestal een ID) en retourneert een `Optional<T>`. Retourneer `Optional.empty()` als de entiteit niet bestaat.

## REST API voorbeeld {#rest-api-example}

Bij integratie met een REST API, moet u de repositorycriteria omzetten in HTTP-verzoekparameters. Begin met het definiëren van een filterklasse die overeenkomt met de querymogelijkheden van uw API:

```java
public class UserFilter {
    private String department;
    private String status;
    // getters en setters...
}
```

Deze filterklasse vertegenwoordigt de zoekparameters die uw API accepteert. De repository zal instanties van deze klasse doorgeven aan uw functies wanneer filtratie wordt toegepast.

Maak de repository met functies die criteria naar API-aanroepen vertalen:

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
    
    // Vind per ID
    userId -> restClient.getById("/users/" + userId)
);
```

De `buildParams()`-methode zou waarden uit de criteria extraheren en omzetten naar queryparameters zoals `?department=Sales&status=active&offset=20&limit=10`. Uw REST-client maakt vervolgens het daadwerkelijke HTTP-verzoek en deserialiseert de respons.

## Database voorbeeld {#database-example}

Database-integratie volgt een vergelijkbaar patroon, maar converteert criteria naar SQL-query's. Het belangrijke verschil is de verwerking van SQL-generatie en parameterbinding:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // Query met filter, sorteren, paginering
    criteria -> {
        String sql = buildQuery(criteria);
        return jdbcTemplate.queryForStream(sql, rowMapper);
    },
    
    // Tel overeenkomende records
    criteria -> {
        String countSql = buildCountQuery(criteria.getFilter());
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    },
    
    // Vind per primaire sleutel
    customerId -> {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
    }
);
```

De `buildQuery()`-methode zou SQL construeren zoals:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Uw filterobjecteigenschappen worden gekoppeld aan `WHERE`-clausules, terwijl paginering en sorteren worden afgehandeld door middel van `LIMIT/OFFSET` en `ORDER BY`-clausules.

## Gebruik met UI-componenten {#using-with-ui-components}

De schoonheid van het repositorypatroon is dat UI-componenten niet weten of zich zorgen hoeven te maken over waar de gegevens vandaan komen. Of het nu een in-memory collectie, REST API of database is, het gebruik is identiek:

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

Wanneer gebruikers interactie hebben met de [`Table`](../../components/table/overview) (kolommen sorteren, pagina's wijzigen), roept de `Table` uw repositoryfuncties aan met bijgewerkte criteria. Uw functies vertalen deze naar API-aanroepen of SQL-query's, en de tabel wordt automatisch bijgewerkt met de resultaten.

## Wanneer `AbstractQueryableRepository` uit te breiden {#when-to-extend-abstractqueryablerepository}

Als u aangepaste methoden of complexe initialisatie nodig heeft, breid dan <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> direct uit:

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
        // Logica voor aangepaste query
    }
}
```
