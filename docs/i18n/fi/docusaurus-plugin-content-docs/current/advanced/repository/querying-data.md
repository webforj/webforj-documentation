---
title: Querying data
sidebar_position: 3
_i18n_hash: 745f13099f9adff4b07124e4c8230600
---
<!-- vale off -->
# Tietojen kysely <DocChip chip='since' label='25.02' />
<!-- vale on -->

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> -rajapinta laajentaa `Repository` -toiminnallisuutta kehittyneellä kyselyllä <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> -kautta. Toisin kuin perusvarastot, jotka tukevat vain yksinkertaista suodatusta, kyseltävät varastot tarjoavat rakenteellista kyselyä mukautetuilla suodatustyypeillä, lajittelulla ja sivutuksella.

## Suodatustyyppien ymmärtäminen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> esittelee toisen geneerisen parametrin suodatustyypille: `QueryableRepository<T, F>`, jossa `T` on entiteettityyppi ja `F` on mukautettu suodatustyyppi.

Tämä erottelu on tarpeen, koska erilaiset tietolähteet käyttävät erilaisia kyselykieliä:

```java
// Predikaattisuodattimet muistissa oleville kokoelmille
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
  new CollectionRepository<>(products);

// Mukautetut suodatusobjektit REST API:lle tai tietokannoille  
QueryableRepository<User, UserFilter> apiRepo = 
  new DelegatingRepository<>(/* toteutus */);

// Merkkijonokyselyt hakukoneille
QueryableRepository<Document, String> searchRepo = 
  new DelegatingRepository<>(/* toteutus */);
```

`CollectionRepository` käyttää `Predicate<Product>` -tiheyttä, koska se suodattaa Java-objekteja muistissa. REST API -varasto käyttää `UserFilter` -luokkaa, jossa on kenttiä kuten `department` ja `status`, jotka vastaavat kyselyparametreja. Hakuväline käyttää tavallisia merkkijonoja täydentäviä kyselyitä.

Käyttöliittymäkomponentit eivät välitä näistä eroista. Ne kutsuvat `setBaseFilter()`-metodia sen suodatustyypin kanssa, jota varasto odottaa, ja varasto hoitaa käännöksen.

## Kyselyjen rakentaminen varastokriteerien avulla {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> kokoaa kaikki kyselyparametrit yhteen muuttumattomaan objektiin. Sen sijaan, että käyttäisit erillisiä metodeja suodattamiseen, lajitteluun ja sivutukseen, voit välittää kaiken kerralla:

```java
// Täydellinen kysely kaikilla parametreilla
RepositoryCriteria<Product, Predicate<Product>> criteria = 
  new RepositoryCriteria<>(
    20,                                       // offset - ohita ensimmäiset 20
    10,                                       // limit - ota 10 kohdetta  
    orderCriteria,                           // lajittelusäännöt
    product -> product.getPrice() < 100.0    // suodatuskriteeri
  );

// Suorita kysely
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

`findBy()`-metodi suorittaa täydellisen kyselyn - se soveltaa suodatusta, lajittelee tulokset, ohittaa siirron ja ottaa rajoituksen. `size()`-metodi laskee kaikki suodatusta vastaavat kohteet ottaen huomioon vuorottelun.

Voit myös luoda kriteerejä vain tarvittavilla osilla:

```java
// Vain suodatus
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
  new RepositoryCriteria<>(product -> product.isActive());

// Vain sivutus  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
  new RepositoryCriteria<>(0, 25);
```

## Erilaisten suodatustyyppien kanssa työskentely {#working-with-different-filter-types}

### Predikaattisuodattimet {#predicate-filters}

Muistissa oleville kokoelmille käytä `Predicate<T>`-ilmausta rakentamaan funktionaalisia suodattimia:

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

### Mukautetut suodatusobjektit {#custom-filter-objects}

Ulkoiset tietolähteet eivät voi suorittaa Java-predikaatteja. Sen sijaan luot suodatusluokkia, jotka edustavat sitä, mitä taustajärjestelmäsi voi etsiä:

```java
public class ProductFilter {
  private String category;
  private BigDecimal maxPrice;
  private Boolean inStock;
  
  // getterit ja setterit...
}

// Käytä mukautetun varaston kanssa
ProductFilter filter = new ProductFilter();
filter.setCategory("Electronics");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
  new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Muiden mukautettujen varastojen `findBy()`-metodissa sinun tulisi kääntää tämä suodatusobjekti:
- REST API:lle: Muunna kyselyparametreiksi kuten `?category=Electronics&maxPrice=99.99&inStock=true`
- SQL:lle: Laadi `where`-lause, kuten `WHERE category = ? AND price <= ? AND stock > 0`
- GraphQL:lle: Rakenna kysely asianmukaisilla kenttävalinnoilla

`Repository`-toteutuksen tulisi hoitaa tämä käännös pitääkseen käyttöliittymäkoodisi siistinä.

## Datan lajittelu {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> määrittelee, kuinka lajitella datasi. Jokainen `OrderCriteria` tarvitsee arvonantajan (kuinka saada arvo entiteetiltäsi) ja suunnan:

```java
// Yksittäisen kentän lajittelu
OrderCriteria<Employee, String> byName = 
  new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Monitason lajittelu - osasto ensin, sitten palkka, sitten nimi
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Käytä kriteereissä
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
  new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Arvonantaja (`Employee::getName`) toimii muistissa lajittelussa. Mutta ulkoiset tietolähteet eivät voi suorittaa Java-funktioita. Näille tapauksille `OrderCriteria` hyväksyy kentän nimen:

```java
// Ulkoisia varastoja varten - tarjoa sekä arvon saaja että kentän nimi
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
  Employee::getName,           // Muistissa lajittelua varten
  Direction.ASC,
  null,                       // Mukautettu vertailija (valinnainen)
  "name"                      // Kentän nimi taustalla lajittelua varten
);
```

`CollectionRepository` käyttää arvonantajaa Java-objektien lajitteluun. `DelegatingRepository`-toteutukset voivat käyttää kentän nimeä rakennettaessa järjestyslausekkeita SQL:ssä tai `sort=name:asc` REST API:ssa.

## Sivutuksen hallinta {#controlling-pagination}

Aseta siirto ja rajoitus hallitaksesi, minkä datan osan lataat:

```java
// Sivupohjainen sivutus
int page = 2;          // nollasta alkava sivunumero
int pageSize = 20;     // kohteita per sivu
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
  new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressiivinen lataaminen - lataa enemmän dataa vähitellen  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
