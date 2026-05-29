---
title: Spring Data JPA
sidebar_position: 20
_i18n_hash: 3fe8c744a49adaaa35e1e30c53b5c60f
---
Spring Data JPA est la norme de facto pour l'accès aux données dans les applications Spring, fournissant des abstractions de référentiel, des méthodes de requête et des spécifications pour des requêtes complexes. L'adaptateur webforJ `SpringDataRepository` fait le lien entre les référentiels Spring Data et les composants UI de webforJ, vous permettant de lier les entités JPA directement aux composants UI, d'implémenter un filtrage dynamique avec des spécifications JPA et de gérer la pagination.

L'adaptateur détecte les interfaces Spring Data que votre référentiel implémente - qu'il s'agisse de `CrudRepository`, `PagingAndSortingRepository` ou `JpaSpecificationExecutor` - et fournit automatiquement les fonctionnalités correspondantes dans votre UI. Cela signifie que vos référentiels Spring Data existants fonctionnent avec les composants webforJ sans modification, tout en maintenant la sécurité des types et en utilisant votre modèle de domaine existant.

:::tip[En savoir plus sur Spring Data JPA]
Pour une compréhension complète des fonctionnalités et des méthodes de requête de Spring Data JPA, consultez la [documentation Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/).
:::

## Utilisation de SpringDataRepository {#using-springdatarepository}

La classe `SpringDataRepository` établit un lien entre les référentiels Spring Data JPA et l'interface Repository de webforJ, les rendant compatibles avec des composants UI comme [`Table`](../../components/table/overview) tout en conservant toutes les fonctionnalités de Spring Data.

```java
// Votre référentiel Spring Data
@Autowired
private PersonRepository personRepository;

// Enveloppez-le avec SpringDataRepository
SpringDataRepository<Person, Long> adapter = new SpringDataRepository<>(personRepository);

// Utilisez avec la Table webforJ
Table<Person> table = new Table<>();
table.setRepository(adapter);
```

### Détection d'interface {#interface-detection}

Les référentiels Spring Data utilisent l'héritage d'interface pour ajouter des capacités. Vous commencez par des opérations CRUD de base et ajoutez des interfaces pour des fonctionnalités comme la pagination ou les spécifications :

```java
// CRUD de base seulement
public interface CustomerRepository extends CrudRepository<Customer, Long> {}

// CRUD + Pagination + Tri
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {}

// Référentiel complet
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {}
```

`SpringDataRepository` examine les interfaces que votre référentiel implémente et adapte son comportement en conséquence. Si votre référentiel prend en charge la pagination, l'adaptateur active les requêtes paginées. S'il implémente `JpaSpecificationExecutor`, vous pouvez utiliser un filtrage dynamique avec des spécifications.

### Capacités du référentiel {#repository-capabilities}

Chaque interface Spring Data ajoute des capacités spécifiques que `SpringDataRepository` peut utiliser :

- **CrudRepository** - Opérations de base : `save`, `delete`, `findById`, `findAll`
- **PagingAndSortingRepository** - Ajoute des requêtes paginées et un tri
- **JpaRepository** - Combine CRUD et pagination/tri avec des opérations par lot
- **JpaSpecificationExecutor** - Requêtes dynamiques utilisant des spécifications JPA

### Création d'un référentiel Spring Data {#creating-a-spring-data-repository}

Pour une compatibilité maximale avec les composants webforJ, créez des référentiels qui implémentent à la fois `JpaRepository` et `JpaSpecificationExecutor` :

```java title="PersonRepository.java"
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
  extends JpaRepository<Person, Long>,
      JpaSpecificationExecutor<Person> {
  // Les méthodes de requête personnalisées peuvent aller ici
}
```

Cette combinaison fournit :

- Opérations de recherche par ID
- Pagination avec performance optimale
- Capacités de tri
- Filtrage par spécification de l'API de persistance Java
- Opérations de comptage avec et sans filtres

## Travailler avec `Table` {#working-with-table}

L'exemple suivant utilise un `PersonRepository` qui étend `JpaRepository` et `JpaSpecificationExecutor`. Cette combinaison permet le tri via les en-têtes de colonnes et le filtrage dynamique avec des spécifications.

```java title="TableView.java"
@Route
public class TableView extends Composite<Div> {
  private SpringDataRepository<Person, Long> repository;
  private Table<Person> table = new Table<>();

  public TableView(PersonRepository personRepository) {
    // Enveloppez le référentiel Spring Data pour webforJ
    repository = new SpringDataRepository<>(personRepository);
    
    // Connectez-vous à la table
    table.setRepository(repository);
    
    // Définissez les colonnes
    table.addColumn("name", Person::getFullName)
          .setPropertyName("firstName"); // Triez par propriété JPA réelle
    table.addColumn("email", Person::getEmail);
    table.addColumn("age", person -> 
          person.getAge() != null ? person.getAge().toString() : "");
    table.addColumn("city", Person::getCity);
    table.addColumn("profession", Person::getProfession);
    
    // Activez le tri
    table.getColumns().forEach(column -> column.setSortable(true));
  }
}
```

La méthode `setPropertyName()` est importante pour le tri - elle indique à l'adaptateur quelle propriété JPA utiliser dans la clause `ORDER BY` lors du tri de cette colonne. Sans cela, le tri ne fonctionnera pas pour les propriétés calculées comme `getFullName()`.

## Filtrage avec des spécifications JPA {#filtering-with-jpa-specifications}

`SpringDataRepository` utilise des spécifications JPA pour des requêtes dynamiques et elles sont appliquées aux opérations `findBy` et `count` du référentiel.

:::tip[En savoir plus sur le filtrage]
Pour comprendre comment le filtrage fonctionne avec les référentiels webforJ, y compris les filtres de base et la composition de filtres, consultez la [documentation du référentiel](../../advanced/repository/overview).
::: 

```java
// Filtrer par ville
Specification<Person> cityFilter = (root, query, cb) -> 
  cb.equal(root.get("city"), "New York");
repository.setFilter(cityFilter);

// Conditions multiples
Specification<Person> complexFilter = (root, query, cb) -> 
  cb.and(
    cb.equal(root.get("profession"), "Engineer"),
    cb.greaterThanOrEqualTo(root.get("age"), 25)
  );
repository.setFilter(complexFilter);

// Effacer le filtre
repository.setFilter(null);
```
