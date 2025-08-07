---
title: Querying data
sidebar_position: 3
_i18n_hash: c5508e014de2ca1de7543b34e39731bc
---
<!-- vale off -->
# Tietojen kysely <DocChip chip='since' label='25.02' />
<!-- vale on -->

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink>-rajapinta laajentaa `Repository`-rajapintaa edistetyllä kyselyllä <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> avulla. Toisin kuin perusvarastot, jotka tukevat vain yksinkertaista suodattamista, kyseltävät varastot tarjoavat rakenteellista kyselyä mukautetuilla suodatintyypeillä, lajittelulla ja sivutuksella.

## Suodatintyypin ymmärtäminen {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> esittelee toisen geneerisen parametrin suodatintyypille: `QueryableRepository<T, F>` missä `T` on entiteettisi tyyppi ja `F` on mukautettu suodatintyypi.

Tämä erottelu on olemassa, koska eri tietolähteet puhuvat eri kyselykieliä:

```java
// Predikaattisuodattimet muistissa oleville kokoelmille
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Mukautetut suodatinobjektit REST-API:lle tai tietokannoille  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* toteutus */);

// Merkkikyselyt hakukoneille
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* toteutus */);
```

`CollectionRepository` käyttää `Predicate<Product>`:tä, koska se suodattaa Java-objekteja muistissa. REST API -varasto käyttää `UserFilter`:iä - mukautettua luokkaa, jossa on kenttiä kuten `department` ja `status`, jotka vastaavat kyselyparametreja. Hakusäilytys käyttää tavallisia merkkijonoja täydellisiin kyselyihin.

Käyttöliittymäkomponentit eivät välitä näistä eroista. Ne kutsuvat `setBaseFilter()`-metodia mihin tahansa suodatintyypiin, jota varasto odottaa, ja varasto käsittelee käännöksen.

## Kyselyjen rakentaminen varastokriteerien avulla {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> kokoaa kaikki kyselyparametrit yhteen muuttumattomaan objektiin. Sen sijaan, että kutsuisit erillisiä metodeja suodattamiseen, lajitteluun ja sivutukseen, voit siirtää kaiken kerralla:

```java
// Täydellinen kysely kaikilla parametreilla
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>( 
        20,                                       // offset - jätä ensimmäiset 20 pois
        10,                                       // limit - ota 10 kohdetta 
        orderCriteria,                           // lajittelusäännöt
        product -> product.getPrice() < 100.0    // suodatuskonditionaalitus
    );

// Suorita kysely
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

`findBy()`-metodi suorittaa koko kyselyn - se soveltaa suodatusta, lajittelee tuloksia, jättää offsetin huomiotta ja ottaa rajoituksen. `size()`-metodi laskee kaikki kohteet, jotka vastaavat suodatinta, sivutuksesta huolimatta.

Voit myös luoda kriteereitä vain tarvitsemillasi osilla:

```java
// Vain suodatin
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Vain sivutus  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Eri suodatintyypeillä työskentely {#working-with-different-filter-types}

### Predikaattisuodattimet {#predicate-filters}

Muistissa oleville kokoelmille käytä `Predicate<T>`:tä rakentaaksesi funktionaalisia suodattimia:

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

### Mukautetut suodatinobjektit {#custom-filter-objects}

Ulkoiset tietolähteet eivät voi suorittaa Java-predikaatteja. Sen sijaan luot suodatinluokkia, jotka kuvaavat, mitä taustajärjestelmäsi voi etsiä:

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // getterit ja setterit...
}

// Käytä mukautetussa varastossa
ProductFilter filter = new ProductFilter();
filter.setCategory("Elektroniikka");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

Mukautetun varaston `findBy()`-metodissa sinun tulee kääntää tämä suodatinobjekti:
- REST API:lle: Muunna kyselyparametreiksi kuten `?category=Elektroniikka&maxPrice=99.99&inStock=true`
- SQL:lle: Rakenna WHERE-lause kuten `WHERE category = ? AND price <= ? AND stock > 0`
- GraphQL:lle: Rakenna kysely asianmukaisilla kenttävalinnoilla

`Repository`-toteutuksen tulisi käsitellä tämä käännös, pitäen käyttöliittymäkoodisi siistinä.

## Datan lajittelu {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> määrittää, kuinka lajittelet tietosi. Jokaisella `OrderCriteria`:lla on arvojen hankintapalvelija (kuinka saada arvo entiteetistä) ja suunta:

```java
// Yhden kentän lajittelu
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Monitasoinen lajittelu - osasto ensin, sitten palkka, sitten nimi
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Käytä kriteerissä
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Arvon hankintapalvelija (`Employee::getName`) toimii muistissa lajittelussa. Mutta ulkoiset tietolähteet eivät voi suorittaa Java-funktioita. Näissä tapauksissa `OrderCriteria` hyväksyy ominaisuuden nimen:

```java
// Ulkoisia varastoja varten - tarjoa sekä arvojen hankkija että ominaisuuden nimi
OrderCriteria<Employee, String> byName = new OrderCriteria<>(
    Employee::getName,           // Muistissa lajittelua varten
    Direction.ASC,
    null,                       // Mukautettu vertailija (valinnainen)
    "name"                      // Ominaisuuden nimi taustan lajittelua varten
);
```

`CollectionRepository` käyttää arvojen hankintapalvelijaa lajittelemiseen Java-objekteissa. `DelegatingRepository`-toteutukset voivat käyttää ominaisuuden nimeä rakentaakseen järjestyslausekkeita SQL:ssä tai `sort=name:asc` REST-API:ssä.

## Sivutuksen hallinta {#controlling-pagination}

Aseta offset ja limit hallitaksesi, mikä datan osa ladataan:

```java
// Sivupohjainen sivutus
int page = 2;          // nollapohjainen sivunumero
int pageSize = 20;     // kohteita per sivu
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Progressiivinen lataus - lataa lisää dataa vähitellen  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
