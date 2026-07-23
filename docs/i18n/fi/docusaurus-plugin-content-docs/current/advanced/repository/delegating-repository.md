---
title: Custom data sources
sidebar_position: 4
description: >-
  Wire REST APIs, databases, or external services to webforJ components by
  supplying find, count, and key-lookup functions to DelegatingRepository.
_i18n_hash: 7d203b803816c64e9ca77d8b49bf34ed
---
<!-- vale off -->
# Mukautetut tietolähteet <DocChip chip='since' label='25.02' />
<!-- vale on -->

Kun tietosi sijaitsevat sovelluksesi ulkopuolella - REST-API:ssa, tietokannassa tai ulkoisessa palvelussa - sinun täytyy luoda mukautettu varaston toteutus. Luokka <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> tekee tämän yksinkertaiseksi antamalla sinun tarjota funktioita sen sijaan, että toteuttaisit koko luokan.

## Kuinka `DelegatingRepository` toimii {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> on konkreettinen luokka, joka laajentaa <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> -luokkaa. Sen sijaan, että toteuttaisit abstrakteja metodeja, tarjoat kolme funktiota konstruktorissa:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. Etsintäfunktio - palauttaa suodatetut/järjestetyt/sivutetut tiedot
  criteria -> userService.findUsers(criteria),

  // 2. Laskentafunktio - palauttaa suodattimen kokonaismäärän
  criteria -> userService.countUsers(criteria),

  // 3. Etsi avaimen avulla - palauttaa yksittäisen entiteetin ID:n perusteella
  userId -> userService.findById(userId)
);
```

Jokaisella funktiolla on oma erityinen tarkoitus:

**Etsintäfunktio** vastaanottaa `RepositoryCriteria` -objektin, joka sisältää:
- `getFilter()` - mukautettu suodatinobjektisi (tyyppi `F`)
- `getOffset()` ja `getLimit()` - sivutusta varten
- `getOrderCriteria()` - luettelo järjestyssäännöistä

Tämän funktion on palautettava `Stream<T>` entiteettejä, jotka vastaavat kriteerejä. Virta voi olla tyhjää, jos yhtään mätsäävää löydöstä ei ole.

**Laskentafunktio** vastaanottaa myös kriteerit, mutta käyttää tyypillisesti vain suodatinosaa. Se palauttaa vastaavien entiteettien kokonaismäärän, ottaen huomioon vain suodatuksen. Tätä käytetään käyttöliittymäkomponenteissa näyttämään kokonaisvarastot tai laskemaan sivuja.

**Etsi avaimen avulla** vastaanottaa entiteettiavaimen (yleensä ID) ja palauttaa `Optional<T>`. Palauta `Optional.empty()`, jos entiteettiä ei ole olemassa.

## REST API -esimerkki {#rest-api-example}

REST API:n kanssa integroiminen edellyttää, että muutat varaston kriteerit HTTP-pyyntöparametreiksi. Aloita määrittämällä suodatinluokka, joka vastaa API:si kyselymahdollisuuksia:

```java
public class UserFilter {
  private String department;
  private String status;
  // gettereitä ja settereitä...
}
```

Tämä suodatinluokka edustaa hakuparametrejä, joita API:si hyväksyy. Varasto välittää tämän luokan instanssit funktioihisi suodatuksen soveltamisen yhteydessä.

Luo varasto funktioilla, jotka kääntävät kriteerit API-kutsuiksi:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
  // Etsi käyttäjiä
  criteria -> {
    Map<String, String> params = buildParams(criteria);
    List<User> users = restClient.get("/users", params);
    return users.stream();
  },

  // Laske käyttäjät
  criteria -> {
    Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
    return restClient.getCount("/users/count", filterParams);
  },

  // Etsi ID:n mukaan
  userId -> restClient.getById("/users/" + userId)
);
```

`buildParams()`-menetelmä ekstraktiarvoittaa kriteereistä ja muuntaa ne kyselyparametreiksi kuten `?department=Sales&status=active&offset=20&limit=10`. REST-asiakkaasi suorittaa sitten varsinaisen HTTP-pyynnön ja deserialisoi vastauksen.

## Tietokantaesimerkki {#database-example}

Tietokanta-integrointi seuraa samankaltaista kaavaa, mutta muuntaa kriteerit SQL-kyselyiksi. Avainero on SQL-generaation ja parametrien sitomisen käsittely:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
  // Kysely suodattimella, järjestäminen, sivutus
  criteria -> {
    String sql = buildQuery(criteria);
    return jdbcTemplate.queryForStream(sql, rowMapper);
  },

  // Laske vastaavat tietueet
  criteria -> {
    String countSql = buildCountQuery(criteria.getFilter());
    return jdbcTemplate.queryForObject(countSql, Integer.class);
  },

  // Etsi pääavaimen mukaan
  customerId -> {
    String sql = "SELECT * FROM customers WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
  }
);
```

`buildQuery()`-menetelmä rakentaisi SQL:ää, kuten:
```sql
SELECT * FROM customers
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Suodatinobjektin ominaisuudet vastaavat `WHERE`-lauseen ehtoja, kun taas sivutus ja lajittelu käsitellään `LIMIT/OFFSET`- ja `ORDER BY` -lauseilla.

## Käyttö käyttöliittymäkomponenttien kanssa {#using-with-ui-components}

Varastomallin kauneus on siinä, että käyttöliittymäkomponentit eivät tiedä tai välitä, mistä data tulee. Onko se muistissa oleva kokoelma, REST-API vai tietokanta, käyttö on identtistä:

```java
// Luo ja määrittele varasto
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Kiinnitä taulukkoon
Table<User> table = new Table<>();
table.setRepository(repository);

// Taulukko näyttää automaattisesti suodatetut insinöörikäyttäjät
```

Kun käyttäjät vuorovaikuttavat [`Table`](../../components/table/overview) (lajittelevat sarakkeita, vaihtavat sivuja), `Table` kutsuu varastosi funktioita päivitettyjen kriteerien kanssa. Funktiot kääntävät nämä API-kutsuiksi tai SQL-kyselyiksi, ja taulukko päivittyy automaattisesti tulosten kanssa.

## Milloin laajentaa `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

Jos tarvitset mukautettuja metodeja tai monimutkaista alustamista, laajenna <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> suoraan:

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
  @Override
  public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
    // Toteutus
  }

  @Override
  public int size(RepositoryCriteria<User, UserFilter> criteria) {
    // Toteutus
  }

  @Override
  public Optional<User> find(Object key) {
    // Toteutus
  }

  // Lisää mukautettuja metodeja
  public List<User> findActiveManagers() {
    // Mukautettu kyselylogiikka
  }
}
```
