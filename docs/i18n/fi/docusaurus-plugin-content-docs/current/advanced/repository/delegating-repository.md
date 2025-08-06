---
title: Custom data sources
sidebar_position: 4
_i18n_hash: dbcaaa420987ee45f54d3f4059e98d92
---
<!-- vale off -->
# Mukautetut tietolähteet <DocChip chip='since' label='25.02' />
<!-- vale on -->

Kun tietosi sijaitsevat sovelluksesi ulkopuolella - REST API:ssa, tietokannassa tai ulkoisessa palvelussa - sinun on luotava mukautettu varastointitoteutus. <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> -luokka tekee tästä suoraviivaista sallimalla funktioiden tarjoamisen täydellisen luokan toteuttamisen sijasta.

## Miten `DelegatingRepository` toimii {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> on konkreettinen luokka, joka laajentaa <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> -luokkaa. Sen sijaan, että toteuttaisit abstrakteja metodeja, annat konstruktorissa kolme funktiota:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Etsi-funktio - palauttaa suodatetut/lajitellut/sivutetut tiedot
    criteria -> userService.findUsers(criteria),
    
    // 2. Laske-funktio - palauttaa suodattimen kokonaismäärän
    criteria -> userService.countUsers(criteria),
    
    // 3. Etsi avaimen mukaan - palauttaa yksittäisen entiteetin ID:n perusteella
    userId -> userService.findById(userId)
);
```

Jokaisella funktionilla on erityinen tarkoitus:

**Etsi-funktio** vastaanottaa `RepositoryCriteria` -objektin, joka sisältää:
- `getFilter()` - oma mukautettu suodatinobjekti (tyyppi `F`)
- `getOffset()` ja `getLimit()` - sivutukseen
- `getOrderCriteria()` - lajittelusääntöjen lista

Tämän funktion on palautettava `Stream<T>` entiteeteistä, jotka täyttävät kriteerit. Suoritus voi olla tyhjää, jos osumia ei löydy.

**Laske-funktio** vastaanottaa myös kriteerit, mutta käyttää yleensä vain suodatinosiota. Se palauttaa vastaavien entiteettien kokonaismäärän, jättämättä huomiota sivutusta. Tätä käytetään UI-komponenteissa kaikkien tulosten näyttämiseksi tai sivujen laskemiseksi.

**Etsi avaimen mukaan** -funktio vastaanottaa entiteetin avaimen (yleensä ID:n) ja palauttaa `Optional<T>`. Palauta `Optional.empty()`, jos entiteettiä ei ole olemassa.

## REST API esimerkki {#rest-api-example}

Kun integroituu REST API:hin, sinun on muutettava varastokriteerit HTTP-pyyntöparametreiksi. Aloita määrittämällä suodatinluokka, joka vastaa API:si kyselykykyjä:

```java
public class UserFilter {
    private String department;
    private String status;
    // getterit ja setterit...
}
```

Tämä suodatinluokka edustaa hakuparametreja, joita API:si hyväksyy. Varasto välittää tätä luokkaa instansseja funktioihisi, kun suodatin käytetään.

Luo varasto funktioilla, jotka kääntävät kriteerit API-kutsuihin:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
    // Etsi käyttäjät
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
    
    // Etsi ID:llä
    userId -> restClient.getById("/users/" + userId)
);
```

`buildParams()` -metodi poimii arvot kriteereistä ja muuntaa ne kyselyparametreiksi, kuten `?department=Sales&status=active&offset=20&limit=10`. REST-asiakas sitten tekee varsinaisen HTTP-pyynnön ja deserialisoi vastauksen.

## Tietokantaesimerkki {#database-example}

Tietokanta-integraatio seuraa samanlaista kaavaa, mutta muuntaa kriteerit SQL-kyselyiksi. Keskeinen ero on SQL-generaation ja parametrin sitomisen käsittelyssä:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // Kysely suodattimella, lajittelu, sivutus
    criteria -> {
        String sql = buildQuery(criteria);
        return jdbcTemplate.queryForStream(sql, rowMapper);
    },
    
    // Laske vastaavat tietueet
    criteria -> {
        String countSql = buildCountQuery(criteria.getFilter());
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    },
    
    // Etsi ensisijaisella avaimella
    customerId -> {
        String sql = "SELECT * FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
    }
);
```

`buildQuery()` -metodi rakentaisi SQL:n seuraavasti:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Suodatinobjektin ominaisuudet karttuvat `WHERE`-ehdon olosuhteisiin, kun taas sivutus ja lajittelu hoidetaan `LIMIT/OFFSET` ja `ORDER BY` -ehdoilla.

## Käyttö käyttöliittymäkomponenteissa {#using-with-ui-components}

Varastomallin kauneus on siinä, että käyttöliittymäkomponentit eivät tiedä tai välitä, mistä tiedot tulevat. Olipa se sitten muistissa oleva kokoelma, REST API tai tietokanta, käyttö on identtistä:

```java
// Luo ja määrittele varasto
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Liitä tauluun
Table<User> table = new Table<>();
table.setRepository(repository);

// Taulu näyttää automaattisesti suodatetut engineering-käyttäjät
```

Kun käyttäjät vuorovaikuttavat [`Table`](../../components/table/overview) kanssa (lajitellessaan sarakkeita, vaihtaessaan sivuja), `Table` kutsuu varastosi funktioita päivittääkseen kriteerit. Funktiot kääntävät nämä API-pyynnöiksi tai SQL-kyselyiksi, ja taulu päivittyy automaattisesti tulosten kanssa.

## Milloin laajentaa `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

Jos tarvitset mukautettuja metodeja tai monimutkaista alustusta, laajenna <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> suoraan:

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
    
    // Lisää mukautetut metodit
    public List<User> findActiveManagers() {
        // Mukautettu kyselylogiikka
    }
}
```
