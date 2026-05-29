---
title: Custom data sources
sidebar_position: 4
_i18n_hash: 7ead4a1af63b9c20d81dc2fd9b67380f
---
<!-- vale off -->
# Mukautetut tietolähteet <DocChip chip='since' label='25.02' />
<!-- vale on -->

Kun tietosi sijaitsevat sovelluksesi ulkopuolella - REST API:ssa, tietokannassa tai ulkoisessa palvelussa - sinun on luotava mukautettu varaston toteutus. <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> -luokka tekee tästä suoraviivaista antamalla sinun määrittää funktioita sen sijaan, että toteuttaisit kokonaisen luokan.

## Kuinka `DelegatingRepository` toimii {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> on konkreettinen luokka, joka laajentaa <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> -luokkaa. Sen sijaan, että toteuttaisit abstrakteja menetelmiä, määrität rakentajassa kolme toimintoa:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. Haku toiminto - palauttaa suodatetut/lajitellut/sivutetu tiedot
  criteria -> userService.findUsers(criteria),
  
  // 2. Laskentatoiminto - palauttaa suodattimen kokonaismäärän
  criteria -> userService.countUsers(criteria),
  
  // 3. Haku avaimella - palauttaa yksittäisen olion ID:n mukaan
  userId -> userService.findById(userId)
);
```

Jokaisella funktiolla on erityinen tarkoitus:

**Haku toiminto** vastaanottaa `RepositoryCriteria` -objektin, joka sisältää:
- `getFilter()` - mukautettu suodatinobjekti (F-tyyppiparametri)
- `getOffset()` ja `getLimit()` - sivutusta varten
- `getOrderCriteria()` - lajittelusääntöjen lista

Tämän toiminnon on palautettava `Stream<T>` -olioita, jotka vastaavat kriteerejä. Virta voi olla tyhjää, jos vastaavuuksia ei löydy.

**Laskentatoiminto** vastaanottaa myös kriteerit, mutta käyttää tyypillisesti vain suodatinosaa. Se palauttaa vastaavien olioiden kokonaismäärän ohi sivutuksen. Tätä käytetään käyttöliittymäkomponenteissa näyttämään kokonais tulokset tai laskemaan sivuja.

**Haku avaimella** vastaanottaa olion avaimen (yleensä ID) ja palauttaa `Optional<T>`. Palauta `Optional.empty()`, jos olio ei ole olemassa.

## REST API esimerkki {#rest-api-example}

REST API:n integroiminen vaatii, että muunnat varaston kriteerit HTTP-pyyntöparametreiksi. Aloita määrittämällä suodatinluokka, joka vastaa API:n kyselymahdollisuuksia:

```java
public class UserFilter {
  private String department;
  private String status;
  // getterit ja setterit...
}
```

Tämä suodatinluokka edustaa hakuparametreja, jotka API hyväksyy. Varasto välittää tämän luokan instanssit funktioihisi suodattamista varten.

Luo varasto, jonka funktiot kääntävät kriteerit API-kutsuksi:

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

`buildParams()` -menetelmä purkaa arvoja kriteereistä ja muuntaa ne kyselyparametreiksi, kuten `?department=Sales&status=active&offset=20&limit=10`. REST-asiakas tekee sitten varsinaisen HTTP-kutsun ja deserializeraa vastauksen.

## Tietokanta esimerkki {#database-example}

Tietokannan integrointi seuraa samanlaista kaavaa, mutta muuntaa kriteerit SQL-kyselyiksi. Avainero on SQL:n generoinnin ja parametrien sitomisen käsittely:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
  // Kysely suodatin, lajittelu, sivutus
  criteria -> {
    String sql = buildQuery(criteria);
    return jdbcTemplate.queryForStream(sql, rowMapper);
  },
  
  // Laske vastaavat tietueet
  criteria -> {
    String countSql = buildCountQuery(criteria.getFilter());
    return jdbcTemplate.queryForObject(countSql, Integer.class);
  },
  
  // Etsi ensisijaisen avaimen mukaan
  customerId -> {
    String sql = "SELECT * FROM customers WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
  }
);
```

`buildQuery()` -menetelmä rakentaisi SQL:n, kuten:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Suodatinobjektin ominaisuudet vastaavat `WHERE`-lausekkeiden ehtoja, kun taas sivutus ja lajittelu käsitellään `LIMIT/OFFSET` ja `ORDER BY` lausekkeiden kautta.

## Käyttäminen käyttöliittymäkomponenttien kanssa {#using-with-ui-components}

Varastomallin kauneus on se, että käyttöliittymäkomponentit eivät tiedä tai piittaa siitä, mistä tiedot tulevat. Olipa se sitten muistiin perustuva kokoelma, REST API tai tietokanta, käyttö on identtistä:

```java
// Luo ja määritä varasto
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Liitä taulukkoon
Table<User> table = new Table<>();
table.setRepository(repository);

// Taulukko näyttää automaattisesti suodatetut insinöörikäyttäjät
```

Kun käyttäjät vuorovaikuttavat [`Table`](../../components/table/overview) (lajittelemalla sarakkeita, vaihtaen sivuja), taulukko kutsuu varaston toimintoja päivitettyjen kriteerien kanssa. Toimintosi kääntävät nämä API-kutsuksi tai SQL-kyselyiksi, ja taulukko päivittää automaattisesti tulosten kanssa.

## Milloin laajentaa `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

Jos tarvitset mukautettuja menetelmiä tai monimutkaista alustusta, laajenna <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> -luokkaa suoraan:

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
  
  // Lisää mukautettuja menetelmiä
  public List<User> findActiveManagers() {
    // Mukautettu kyselylogiikka
  }
}
```
