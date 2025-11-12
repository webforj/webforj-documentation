---
title: Tietojen kysely
sidebar_position: 3
_i18n_hash: 96551b4f47c7019b8bdd43b57f716c88
---
<!-- vale off -->
# Tietojen kysely <DocChip chip='since' label='25.02' />
<!-- vale on -->

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> -rajapinta laajentaa `Repository` -rajapintaa edistyneillä kyselyominaisuuksilla <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>:n kautta. Toisin kuin perusrepositoriot, jotka tukevat vain yksinkertaista suodattamista, kyselyrepositoriot tarjoavat rakenteellista kyselyä mukautettujen suodatin tyyppien, lajittelun ja sivutuksen avulla.

## Suodatin tyyppien ymmärtäminen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> esittelee toisen geneerisen parametrin suodatin tyypille: `QueryableRepository<T, F>`, missä `T` on entiteettityyppisi ja `F` on mukautettu suodatin tyyppisi.

Tämä erottelu on olemassa, koska eri tietolähteet käyttävät erilaisia kyselykieliä:

```java
// Predikaatti suodattimet muistissa oleville kokoelmille
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Mukautetut suodatin objektit REST API:lle tai tietokannoille  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* implementation */);

// Merkkijono kyselyt hakukoneille
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* implementation */);
```

`CollectionRepository` käyttää `Predicate<Product>` koska se suodattaa Java-objekteja muistissa. REST API -repository käyttää `UserFilter` -nimistä mukautettua luokkaa, jonka kentät ovat kuten `department` ja `status`, jotka vastaavat kyselyparametreja. Hakurepositorio käyttää tavallisia merkkijonoja täysimittaisiin kyselyihin.

UI-komponentit eivät välitä näistä eroista. Ne kutsuvat `setBaseFilter()` -menetelmää haluamallaan suodatin tyypillä, ja repositorio hoitaa käännöksen.

## Kyselyjen rakentaminen repositorion kriteereillä {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> kokoaa kaikki kyselyparametrit yhteen muutettuun objektiin. Sen sijaan, että kutsuisit erillisiä menetelmiä suodattamiseen, lajitteluun ja sivutukseen, voit välittää kaiken kerralla:

```java
// Kattava kysely kaikilla parametreilla
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

`findBy()`-menetelmä suorittaa koko kyselyn - se soveltaa suodatinta, lajittelee tulokset, ohittaa offsetin ja ottaa rajan. `size()`-menetelmä laskee kaikki kohteet, jotka vastaavat suodatinta, unohtaen sivutuksen.

Voit myös luoda kriteerejä vain tarvittavia osia käyttäen:

```java
// Vain suodatus
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Vain sivutus  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Eri suodatin tyyppien kanssa työskentely {#working-with-different-filter-types}

### Predikaatti suodattimet {#predicate-filters}

Muistissa oleville kokoelmille käytä `Predicate<T>` -tyyppiä rakentaaksesi funktionaalisia suodattimia:

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Rakenna monimutkaisia predikaatteja
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

### Mukautetut suodatin objektit {#custom-filter-objects}

Ulkoiset tietolähteet eivät voi suorittaa Java-predikaatteja. Sen sijaan luot suodatinluokkia, jotka edustavat mitä taustajärjestelmäsi voi etsiä:

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // getterit ja setterit...
}

// Käytä mukautetun repositorion kanssa
ProductFilter filter = new ProductFilter();
filter.setCategory("Elektroniikka");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Mukautetun repositoryn `findBy()`-menetelmässä sinun tulisi kääntää tämä suodatin objekti:
- REST API:lle: Muunna kyselyparametreiksi kuten `?category=Elektroniikka&maxPrice=99.99&inStock=true`
- SQL:lle: Rakenna where-lause kuten `WHERE category = ? AND price <= ? AND stock > 0`
- GraphQL:lle: Rakenna kysely oikeilla kenttävalinnoilla

`Repository`-implementaation tulisi hoitaa tämä käännös, pitäen UI-koodisi puhtaana.

## Tietojen lajittelu {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> määrittelee, kuinka lajittelet tietosi. Jokainen `OrderCriteria` tarvitsee arvon tuottajan (kuinka saada arvo entiteetistäsi) ja suunnan:

```java
// Yhden kentän lajittelu
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Monitasoinen lajittelu - osasto ensin, sitten palkka, sitten nimi
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Käytä kriteereissä
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Arvon tuottaja (`Employee::getName`) toimii muistissa lajittelussa. Mutta ulkoiset tietolähteet eivät voi suorittaa Java-funktioita. Näissä tapauksissa `OrderCriteria` hyväksyy ominaisuuden nimen:

```java
// Ulkoisia repositorioita varten - anna sekä arvon hakija että ominaisuuden nimi
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
    Employee::getName,           // Muistissa lajittelu
    Direction.ASC,
    null,                       // Mukautettu vertailija (valinnainen)
    "name"                      // Ominaisuuden nimi taustajärjestelmälle
);
```

`CollectionRepository` käyttää arvon tuottajaa lajittaakseen Java-objekteja. `DelegatingRepository`-implementaatiot voivat käyttää ominaisuuden nimeä rakentamaan järjestykseen liittyviä lausekkeita SQL:ssä tai `sort=name:asc` REST API:ssa.

## Sivutuksen hallinta {#controlling-pagination}

Aseta offset ja limit hallitaksesi, mikä tietolohko ladataan:

```java
// Sivupohjainen sivutus
int page = 2;          // nollapohjainen sivun numero
int pageSize = 20;     // kohteita sivua kohti
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressiivinen lataus - lataa lisää tietoja asteittain  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
