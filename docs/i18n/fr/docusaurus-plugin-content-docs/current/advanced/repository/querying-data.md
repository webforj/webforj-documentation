---
title: Querying data
sidebar_position: 3
_i18n_hash: c5508e014de2ca1de7543b34e39731bc
---
<!-- vale off -->
# Interrogation de données <DocChip chip='since' label='25.02' />
<!-- vale on -->

L'interface <JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> étend `Repository` avec des requêtes avancées via <JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink>. Contrairement aux dépôts de base qui ne supportent que le filtrage simple, les dépôts interrogeables offrent des requêtes structurées avec des types de filtres personnalisés, du tri et de la pagination.

## Comprendre les types de filtres {#understanding-filter-types}

<JavadocLink type="data" location="com/webforj/data/repository/QueryableRepository" code="true">QueryableRepository</JavadocLink> introduit un deuxième paramètre générique pour le type de filtre : `QueryableRepository<T, F>` où `T` est votre type d'entité et `F` est votre type de filtre personnalisé.

Cette séparation existe parce que différentes sources de données parlent différentes langues de requête :

```java
// Filtres Predicate pour les collections en mémoire
QueryableRepository<Product, Predicate<Product>> inMemoryRepo = 
    new CollectionRepository<>(products);

// Objets de filtre personnalisés pour les API REST ou les bases de données  
QueryableRepository<User, UserFilter> apiRepo = 
    new DelegatingRepository<>(/* implémentation */);

// Requêtes de chaîne pour les moteurs de recherche
QueryableRepository<Document, String> searchRepo = 
    new DelegatingRepository<>(/* implémentation */);
```

`CollectionRepository` utilise `Predicate<Product>` car il filtre des objets Java en mémoire. Le dépôt API REST utilise `UserFilter` - une classe personnalisée avec des champs comme `department` et `status` qui correspondent aux paramètres de requête. Le dépôt de recherche utilise des chaînes simples pour des requêtes en texte intégral.

Les composants de l'UI ne se soucient pas de ces différences. Ils appellent `setBaseFilter()` avec n'importe quel type de filtre que le dépôt attend, et le dépôt gère la traduction.

## Créer des requêtes avec des critères de dépôt {#building-queries-with-repository-criteria}

<JavadocLink type="data" location="com/webforj/data/repository/RepositoryCriteria" code="true">RepositoryCriteria</JavadocLink> regroupe tous les paramètres de requête en un seul objet immuable. Au lieu d'appeler des méthodes séparées pour le filtre, le tri et la pagination, vous passez tout en une seule fois :

```java
// Requête complète avec tous les paramètres
RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>( 
        20,                                       // offset - ignorer les 20 premiers
        10,                                       // limite - prendre 10 éléments  
        orderCriteria,                           // règles de tri
        product -> product.getPrice() < 100.0    // condition de filtrage
    );

// Exécuter la requête
Stream<Product> results = repository.findBy(criteria);
int totalMatching = repository.size(criteria);
```

La méthode `findBy()` exécute la requête complète - elle applique le filtre, trie les résultats, ignore l'offset et prend la limite. La méthode `size()` compte tous les éléments correspondant au filtre, en ignorant la pagination.

Vous pouvez également créer des critères avec uniquement les parties dont vous avez besoin :

```java
// Filtre uniquement
RepositoryCriteria<Product, Predicate<Product>> filterOnly = 
    new RepositoryCriteria<>(product -> product.isActive());

// Pagination uniquement  
RepositoryCriteria<Product, Predicate<Product>> pageOnly = 
    new RepositoryCriteria<>(0, 25);
```

## Travailler avec différents types de filtres {#working-with-different-filter-types}

### Filtres Predicate {#predicate-filters}

Pour les collections en mémoire, utilisez `Predicate<T>` pour composer des filtres fonctionnels :

```java
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Construire des prédicats complexes
Predicate<Product> activeProducts = product -> product.isActive();
Predicate<Product> inStock = product -> product.getStock() > 0;
Predicate<Product> affordable = product -> product.getPrice() < 50.0;

// Combiner les conditions
repository.setBaseFilter(activeProducts.and(inStock).and(affordable));

// Filtrage dynamique
Predicate<Product> filter = product -> true;
if (categoryFilter != null) {
    filter = filter.and(p -> p.getCategory().equals(categoryFilter));
}
if (maxPrice != null) {
    filter = filter.and(p -> p.getPrice() <= maxPrice);
}
repository.setBaseFilter(filter);
```


### Objets de filtre personnalisés {#custom-filter-objects}

Les sources de données externes ne peuvent pas exécuter de prédicats Java. Au lieu de cela, vous créez des classes de filtre qui représentent ce que votre backend peut rechercher :

```java
public class ProductFilter {
    private String category;
    private BigDecimal maxPrice;
    private Boolean inStock;
    
    // getters et setters...
}

// Utiliser avec le dépôt personnalisé
ProductFilter filter = new ProductFilter();
filter.setCategory("Électronique");
filter.setMaxPrice(new BigDecimal("99.99"));
filter.setInStock(true);

RepositoryCriteria<Product, ProductFilter> criteria = 
    new RepositoryCriteria<>(filter);

Stream<Product> results = customRepository.findBy(criteria);
```

À l'intérieur de la méthode `findBy()` de votre dépôt personnalisé, vous transféreriez cet objet filtre :
- Pour les API REST : Convertir en paramètres de requête comme `?category=Electronics&maxPrice=99.99&inStock=true`
- Pour SQL : Construire une clause where comme `WHERE category = ? AND price <= ? AND stock > 0`
- Pour GraphQL : Construire une requête avec les sélections de champs appropriées

L'implémentation `Repository` devrait gérer cette traduction, gardant ainsi votre code UI propre.

## Trier les données {#sorting-data}

<JavadocLink type="data" location="com/webforj/data/repository/OrderCriteria" code="true">OrderCriteria</JavadocLink> définit comment trier vos données. Chaque `OrderCriteria` a besoin d'un fournisseur de valeur (comment obtenir la valeur de votre entité) et d'une direction :

```java
// Tri sur un seul champ
OrderCriteria<Employee, String> byName = 
    new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC);

// Tri multi-niveaux - département d'abord, puis salaire, puis nom
OrderCriteriaList<Employee> sorting = new OrderCriteriaList<>();
sorting.add(new OrderCriteria<>(Employee::getDepartment, OrderCriteria.Direction.ASC));
sorting.add(new OrderCriteria<>(Employee::getSalary, OrderCriteria.Direction.DESC));  
sorting.add(new OrderCriteria<>(Employee::getName, OrderCriteria.Direction.ASC));

// Utiliser dans les critères
RepositoryCriteria<Employee, Predicate<Employee>> criteria = 
    new RepositoryCriteria<>(0, 50, sorting, employee -> employee.isActive());
```

Le fournisseur de valeur (`Employee::getName`) fonctionne pour le tri en mémoire. Mais les sources de données externes ne peuvent pas exécuter de fonctions Java. Pour ces cas, OrderCriteria accepte un nom de propriété :

```java
// Pour les dépôts externes - fournir à la fois le getter de valeur et le nom de propriété
OrderCriteria<Employee, String> byName = new OrderCriteria<>( 
    Employee::getName,           // Pour le tri en mémoire
    Direction.ASC,
    null,                       // Comparateur personnalisé (optionnel)
    "name"                      // Nom de propriété pour le tri en backend
);
```

`CollectionRepository` utilise le fournisseur de valeur pour trier les objets Java. Les implémentations de `DelegatingRepository` peuvent utiliser le nom de propriété pour construire des clauses de commande en SQL ou `sort=name:asc` dans les API REST.

## Contrôler la pagination {#controlling-pagination}

Définissez l'offset et la limite pour contrôler quelle tranche de données charger :

```java
// Pagination basée sur les pages
int page = 2;          // numéro de page basé sur zéro
int pageSize = 20;     // éléments par page
int offset = page * pageSize;

RepositoryCriteria<Product, Predicate<Product>> criteria = 
    new RepositoryCriteria<>(offset, pageSize, null, yourFilter);

// Chargement progressif - charger plus de données de manière incrémentielle  
int currentlyLoaded = 50;
int loadMore = 25;

repository.setOffset(0);
repository.setLimit(currentlyLoaded + loadMore);
```
