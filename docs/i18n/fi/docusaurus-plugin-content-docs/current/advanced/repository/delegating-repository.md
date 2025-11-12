---
title: Mukautetut tietolähteet
sidebar_position: 4
_i18n_hash: 44f087c7c2308fc7a0c3b8c4c4246531
---
<!-- vale off -->
# Mukautetut tietolähteet <DocChip chip='since' label='25.02' />
<!-- vale on -->

Kun tietosi sijaitsevat sovelluksesi ulkopuolella - REST API:ssa, tietokannassa tai ulkoisessa palvelussa - sinun on luotava mukautettu varaston toteutus. <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> -luokka tekee tämän suoraviivaiseksi antamalla sinun määrittää funktioita sen sijaan, että toteuttaisit koko luokan.

## Kuinka `DelegatingRepository` toimii {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> on konkreettinen luokka, joka laajentaa <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> -luokkaa. Sen sijaan, että toteuttaisit abstraktit metodit, määrität kolme funktiota konstruktorissa:

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Etsi-funktio - palauttaa suodatetut/lajitellut/sivuttuneet tiedot
    criteria -> userService.findUsers(criteria),
    
    // 2. Laske-funktio - palauttaa suodattimen kokonaismäärän
    criteria -> userService.countUsers(criteria),
    
    // 3. Etsi avaimen mukaan - palauttaa yksittäisen entiteetin ID:n mukaan
    userId -> userService.findById(userId)
);
```

Jokaisella funktiolla on erityinen tarkoitus:

**Etsi-funktio** vastaanottaa `RepositoryCriteria` -olion, joka sisältää:
- `getFilter()` - mukautettu suodatinobjekti (tyyppi `F`)
- `getOffset()` ja `getLimit()` - sivutusta varten
- `getOrderCriteria()` - lajittelusääntöjen lista

Tämän funktion on palautettava `Stream<T>` entiteeteistä, jotka täyttävät kriteerit. Virta voi olla tyhjää, jos yhtään vastaavuutta ei löydy.

**Laske-funktio** vastaanottaa myös kriteerit, mutta käyttää yleensä vain suodatinosaa. Se palauttaa vastaavien entiteettien kokonaismäärän, jättäen huomiotta sivutuksen. Tämä on käytössä käyttöliittymän komponenteissa, jotka näyttävät kokonaismäärän tai laskevat sivuja.

**Etsi avaimen mukaan** -funktio vastaanottaa entiteetin avaimen (yleensä ID:n) ja palauttaa `Optional<T>`. Palauta `Optional.empty()`, jos entiteettiä ei ole olemassa.

## REST API -esimerkki {#rest-api-example}

Päivittäessäsi REST API:n kanssa, sinun on muunnettava varastokriteerit HTTP-pyyntöparametreiksi. Aloita määrittämällä suodatinluokka, joka vastaa API:si kyselymahdollisuuksia:

```java
public class UserFilter {
    private String department;
    private String status;
    // getterit ja setterit...
}
```

Tämä suodatinluokka edustaa hakuperusteita, jotka API:si hyväksyy. Varasto välittää tämän luokan instansseja funktioihisi suodattamista varten.

Luo varasto funktioilla, jotka kääntävät kriteerit API-kutsuksi:

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
    // Etsi käyttäjiä
    criteria -> {
        Map<String, String> params = buildParams(criteria);
        List<User> users = restClient.get("/users", params);
        return users.stream();
    },
    
    // Laske käyttäjiä
    criteria -> {
        Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
        return restClient.getCount("/users/count", filterParams);
    },
    
    // Etsi ID:n mukaan
    userId -> restClient.getById("/users/" + userId)
);
```

`buildParams()`-metodi poimii arvoja kriteereistä ja muuntaa ne kyselyparametreiksi kuten `?department=Sales&status=active&offset=20&limit=10`. REST-asiakas tekee sitten varsinaisen HTTP-pyynnön ja deserialisoi vastauksen.

## Tietokantaesimerkki {#database-example}

Tietokannan integrointi seuraa samankaltaista kaavaa, mutta muuntaa kriteerit SQL-kyselyiksi. Tärkein ero on SQL:n luomisen ja parametrin sitomisen käsittely:

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
    // Kysely, jossa suodatin, lajittelu, sivutus
    criteria -> {
        String sql = buildQuery(criteria);
        return jdbcTemplate.queryForStream(sql, rowMapper);
    },
    
    // Laske vastaavat rekisterit
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

`buildQuery()`-metodi rakentaisi SQL:n kuten:
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Suodatinobjektin ominaisuudet kartoittuvat `WHERE`-ehdon ehtoihin, kun taas sivutus ja lajittelu käsitellään `LIMIT/OFFSET` ja `ORDER BY` -lauseiden kautta.

## Käyttö käyttöliittymäkomponenttien kanssa {#using-with-ui-components}

Varastomallin kauneus on siinä, että käyttöliittymäkomponentit eivät tiedä tai välitä, mistä tiedot tulevat. Olipa se muistissa oleva kokoelma, REST API tai tietokanta, käyttö on identtistä:

```java
// Luo ja määritä varasto
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Liitä tauluun
Table<User> table = new Table<>();
table.setRepository(repository);

// Taulu näyttää automaattisesti suodatetut insinöörikäyttäjät
```

Kun käyttäjät vuorovaikuttavat [`Table`](../../components/table/overview) komponentin kanssa (sarakkeiden lajittelu, sivujen muuttaminen), taulu kutsuu varastofunktioitasi päivitettyjen kriteerien kanssa. Funktiosi kääntävät nämä API-kutsuksi tai SQL-kyselyiksi, ja taulu päivittyy automaattisesti tulosten kanssa.

## Milloin laajentaa `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

Jos tarvitset mukautettuja metodeja tai monimutkaista alustusta, laajenna <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> -luokkaa suoraan:

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
