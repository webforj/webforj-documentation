---
title: Custom data sources
sidebar_position: 4
description: >-
  Wire REST APIs, databases, or external services to webforJ components by
  supplying find, count, and key-lookup functions to DelegatingRepository.
_i18n_hash: 7d203b803816c64e9ca77d8b49bf34ed
---
<!-- vale off -->
# Aangepaste gegevensbronnen <DocChip chip='since' label='25.02' />
<!-- vale on -->

Wanneer uw gegevens zich buiten uw app bevinden - in een REST API, database of externe service - moet u een aangepaste repository-implementatie maken. De <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> klasse maakt dit eenvoudig door u in staat te stellen functies te bieden in plaats van een volledige klasse te implementeren.

## Hoe `DelegatingRepository` werkt {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> is een concrete klasse die <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> uitbreidt. In plaats van abstracte methoden te implementeren, biedt u drie functies in de constructor:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. Zoekfunctie - retourneert gefilterde/sorteerde/gevuld gegevens
  criteria -> userService.findUsers(criteria),

  // 2. Tel functie - retourneert totaal aantal voor de filter
  criteria -> userService.countUsers(criteria),

  // 3. Zoek op sleutel functie - retourneert enkele entiteit op ID
  userId -> userService.findById(userId)
);
```

Elke functie heeft een specifieke functie:

**Zoekfunctie** ontvangt een `RepositoryCriteria` object dat bevat:
- `getFilter()` - uw aangepaste filterobject (de `F` typeparameter)
- `getOffset()` en `getLimit()` - voor paginering
- `getOrderCriteria()` - lijst met sorteerregels

Deze functie moet een `Stream<T>` van entiteiten retourneren die voldoen aan de criteria. De stroom kan leeg zijn als er geen overeenkomsten worden gevonden.

**Tel functie** ontvangt ook de criteria, maar gebruikt meestal alleen het filtergedeelte. Het retourneert het totale aantal bijpassende entiteiten, waarbij paginering wordt genegeerd. Dit wordt door UI-componenten gebruikt om totale resultaten weer te geven of pagina's te berekenen.

**Zoek op sleutel functie** ontvangt een entiteitssleutel (meestal een ID) en retourneert een `Optional<T>`. Geef `Optional.empty()` terug als de entiteit niet bestaat.

## REST API voorbeeld {#rest-api-example}

Bij integratie met een REST API moet u de repositorycriteria omzetten in HTTP-verzoekparameters. Begin met het definiëren van een filterklasse die overeenkomt met de zoekmogelijkheden van uw API:

```java
public class UserFilter {
  private String department;
  private String status;
  // getters en setters...
}
```

Deze filterklasse vertegenwoordigt de zoekparameters die uw API accepteert. De repository zal instanties van deze klasse doorgeven aan uw functies wanneer filtering wordt toegepast.

Maak de repository met functies die criteria vertalen naar API-aanroepen:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
  // Zoek gebruikers
  criteria -> {
    Map<String, String> params = buildParams(criteria);
    List<User> users = restClient.get("/users", params);
    return users.stream();
  },

  // Tel gebruikers
  criteria -> {
    Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
    return restClient.getCount("/users/count", filterParams);
  },

  // Zoek op ID
  userId -> restClient.getById("/users/" + userId)
);
```

De `buildParams()` methode zou waarden uit de criteria extraheren en deze omzetten in queryparameters zoals `?department=Sales&status=active&offset=20&limit=10`. Uw REST-client doet vervolgens de daadwerkelijke HTTP-aanroep en deserializeert de respons.

## Database voorbeeld {#database-example}

Database-integratie volgt een vergelijkbaar patroon, maar zet criteria om in SQL-query's. Het belangrijkste verschil is het omgaan met SQL-generatie en parameterbinding:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
  // Query met filter, sorteren, paginering
  criteria -> {
    String sql = buildQuery(criteria);
    return jdbcTemplate.queryForStream(sql, rowMapper);
  },

  // Tel bijpassende records
  criteria -> {
    String countSql = buildCountQuery(criteria.getFilter());
    return jdbcTemplate.queryForObject(countSql, Integer.class);
  },

  // Zoek op primaire sleutel
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

De eigenschappen van uw filterobject worden gekoppeld aan voorwaarden in de `WHERE` clausule, terwijl paginering en sortering worden afgehandeld via `LIMIT/OFFSET` en `ORDER BY` clausules.

## Gebruik met UI-componenten {#using-with-ui-components}

De schoonheid van het repository-patroon is dat UI-componenten niet weten of geven om waar de gegevens vandaan komen. Of het nu een in-memory collectie, REST API of database is, het gebruik is identiek:

```java
// Maak en configureer repository
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Koppel aan tabel
Table<User> table = new Table<>();
table.setRepository(repository);

// Tabel geeft automatisch gefilterde engineering gebruikers weer
```

Wanneer gebruikers interactie hebben met de [`Table`](../../components/table/overview) (kolommen sorteren, pagina's wijzigen), roept de `Table` uw repository-functies aan met bijgewerkte criteria. Uw functies vertalen deze naar API-aanroepen of SQL-query's, en de tabel wordt automatisch bijgewerkt met de resultaten.

## Wanneer `AbstractQueryableRepository` uit te breiden {#when-to-extend-abstractqueryablerepository}

Als u aangepaste methoden of complexe initialisatie nodig heeft, breidt u <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> direct uit:

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
    // Aangepaste querylogica
  }
}
```
