---
title: Querying data
sidebar_position: 3
description: >-
  Build typed filters, sorting, and pagination with QueryableRepository and
  RepositoryCriteria for in-memory collections or external sources.
_i18n_hash: 4bb2af4f510fd31035042c2f1e3a24c7
---
<!-- vale off -->
# Tietojen kysely <DocChip chip='since' label='25.02' />
<!-- vale on -->

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> -rajapinta laajentaa `Repository` -toimintoa tarjoamalla edistyneitä kyselymahdollisuuksia <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> -luokan kautta. Toisin kuin perusrekisterit, jotka tukevat vain yksinkertaista suodatusta, kyselyrekisterit tarjoavat jäsenneltyjä kyselyitä mukautettujen suodatin tyyppien, lajittelun ja sivutuksen avulla.

## Suodatin tyyppien ymmärtäminen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> esittelee toisen geneerisen parametrin suodatin tyyppille: `QueryableRepository<T, F>` missä `T` on entiteettisi tyyppi ja `F` on mukautettu suodatin tyyppi.

Tämä erottelu on olemassa, koska eri tietolähteet käyttävät erilaisia kyselykieliä:

```java
// Ennustepohjaiset suodattimet muistissa oleville kokoelmille
QueryableRepository<Product, Predicate<Product>> inMemoryRepo =
  new CollectionRepository<>(products);

// Mukautetut suodatinobjektit REST-rajapinnoille tai tietokannoille
QueryableRepository<User, UserFilter> apiRepo =
  new DelegatingRepository<>(/* toteutus */);

// Merkkijonokyselyt hakukoneille
QueryableRepository<Document, String> searchRepo =
  new DelegatingRepository<>(/* toteutus */);
```

`CollectionRepository` käyttää `Predicate<Product>` koska se suodattaa Java-objekteja muistissa. REST API -rekisteri käyttää `UserFilter` -luokkaa, jossa on kenttiä kuten `department` ja `status`, jotka vastaavat kyselyparametreja. Hakurepository käyttää yksinkertaisia merkkijonoja täysimittaisten kyselyjen suorittamiseen.

Käyttöliittymäkomponentit eivät välitä näistä eroista. Ne kutsuvat `setBaseFilter()` -menetelmää millä tahansa suotimen tyypillä, jota rekisteri odottaa, ja rekisteri käsittelee käännöksen.

## Kyselyiden rakentaminen rekisterikriteerien avulla {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> kokoaa kaikki kyselyparametrit yhdeksi muuttumattomaksi objektiksi. Sen sijaan, että kutsuisit erillisiä metodeja suodatin, lajittelu ja sivutus, annat kaiken kerralla:

```java
// Täydellinen kysely kaikilla parametreilla
RepositoryCriteria<Product, Predicate<Product>> criteria =
  new RepositoryCriteria<>(
    20,                                       // offset - ohita ensimmäiset 20
    10,                                       // limit - ota 10 kohdetta
    orderCriteria,                           // lajittelusäännöt
    product -> product.getPrice() < 100.0    // suodatin ehto
  );

// Suorita kysely
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

`findBy()` -metodi suorittaa täydellisen kyselyn - se soveltaa suodatinta, lajittelee tulokset, ohittaa offsetin ja ottaa limitin. `size()` -metodi laskee kaikki kohteet, jotka vastaavat suodatinta, unohtaen sivutuksen.

Voit myös luoda kriteerejä vain niillä osilla, joita tarvitset:

```java
// Vain suodatin
RepositoryCriteria<Product, Predicate<Product>> filterOnly =
  new RepositoryCriteria<>(product -> product.isActive());

// Vain sivutus
RepositoryCriteria<Product, Predicate<Product>> pageOnly =
  new RepositoryCriteria<>(0, 25);
```

## Eri suodatin tyyppien kanssa työskentely {#working-with-different-filter-types}

### Ennustepohjaiset suodattimet {#predicate-filters}

Muistissa oleville kokoelmille käytä `Predicate<T>` -menetelmää koostamaan toiminnallisia suodattimia:

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Rakenna monimutkaisia ennusteita
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// Yhdistä ehdot
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// Dynaaminen suodatus
Predicate<Product> filter = product -> true;
if (categoryFilter != null) {
  filter = filter.and(p -> p.getCategory().equals(categoryFilter));
}
if (maxPrice != null) {
  filter = filter.and(p -> p.getPrice() <= maxPrice);
}
repository.setBaseFilter(filter);
```


### Mukautetut suodatinobjektit {#custom-filter-objects}

Ulkopuoliset tietolähteet eivät voi suorittaa Java-ennusteita. Sen sijaan luot suodatinluokkia, jotka edustavat sitä, mitä taustajärjestelmäsi voi etsiä:

```java
public class ProductFilter {
  private String category;
  private BigDecimal maxPrice;
  private Boolean inStock;

  // getterit ja setterit...
}

// käytetään mukautetun rekisterin kanssa
ProductFilter filter = new ProductFilter();
filter.setCategory("Elektroniikka");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria =
  new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Mukautetun rekisterin `findBy()` -metodissa kääntelet tämän suodatinobjektin:
- REST-rajapinnoille: Muunna kyselyparametreiksi kuten `?category=Elektroniikka&maxPrice=99.99&inStock=true`
- SQL: Luo where-ehto kuten `WHERE category = ? AND price <= ? AND stock > 0`
- GraphQL: Rakenna kysely asianmukaisilla kenttävalinnoilla

`Repository` -toteutuksen tulisi käsitellä tämä käännös, pitäen käyttöliittymäkoodisi puhtaana.

## Tietojen lajittelu {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> määrittelee, kuinka lajittelet tietosi. Jokaisella `OrderCriteria` -tavalla on arvotoimittaja (kuinka saada arvo entiteetistäsi) ja suunta:

```java
// Yksittäisen kentän lajittelu
OrderCriteria<Employee, String> byName =
  new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Monitasoinen lajittelu - ensin osasto, sitten palkka, sitten nimi
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Käytä kriteereissä
RepositoryCriteria<Employee, Predicate<Employee>> criteria =
  new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Arvotoimittaja (`Employee::getName`) toimii muistissa lajittelussa. Mutta ulkoiset tietolähteet eivät voi suorittaa Java-funktioita. Näissä tapauksissa OrderCriteria hyväksyy ominaisuuden nimen:

```java
// Ulkoisia rekisterejä varten - tarjoa sekä arvon hakija että ominaisuuden nimi
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
  Employee::getName,           // Muistissa lajittelua varten
  Direction.ASC,
  null,                       // Mukautettu vertailija (valinnainen)
  "name"                      // Ominaisuuden nimi taustajärjestelmää varten
);
```

`CollectionRepository` käyttää arvotoimittajaa lajittelemassa Java-objekteja. `DelegatingRepository` -toteutukset voivat käyttää ominaisuuden nimeä rakennettaessa järjestyslausekkeita SQL:ssä tai `sort=name:asc` REST-rajapinnoissa.

## Sivutuksen hallinta {#controlling-pagination}

Aseta offset ja limit hallitaksesi, mikä tietojen osa ladataan:

```java
// Sivupohjainen sivutus
int page = 2;          // nollapohjainen sivunumero
int pageSize = 20;     // kohteet per sivu
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria =
  new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressiivinen lataaminen - lataa lisää tietoa asteittain
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
