---
title: Custom data sources
sidebar_position: 4
description: >-
  Wire REST APIs, databases, or external services to webforJ components by
  supplying find, count, and key-lookup functions to DelegatingRepository.
_i18n_hash: 7d203b803816c64e9ca77d8b49bf34ed
---
<!-- vale off -->
# Sources de données personnalisées <DocChip chip='since' label='25.02' />
<!-- vale on -->

Lorsque vos données se trouvent en dehors de votre application - dans une API REST, une base de données ou un service externe - vous devez créer une implémentation de référentiel personnalisé. La classe <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> facilite cela en vous permettant de fournir des fonctions plutôt que d'implémenter une classe complète.

## Comment fonctionne `DelegatingRepository` {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> est une classe concrète qui étend <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>. Au lieu d’implémenter des méthodes abstraites, vous fournissez trois fonctions dans le constructeur :

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. Fonction de recherche - renvoie des données filtrées/triestées/paginées
  criteria -> userService.findUsers(criteria),

  // 2. Fonction de comptage - renvoie le total pour le filtre
  criteria -> userService.countUsers(criteria),

  // 3. Fonction de recherche par clé - renvoie une seule entité par ID
  userId -> userService.findById(userId)
);
```

Chaque fonction a un but spécifique :

**La fonction de recherche** reçoit un objet `RepositoryCriteria` contenant :
- `getFilter()` - votre objet filtrant personnalisé (le paramètre de type `F`)
- `getOffset()` et `getLimit()` - pour la pagination
- `getOrderCriteria()` - liste des règles de tri

Cette fonction doit renvoyer un `Stream<T>` d'entités qui correspondent aux critères. Le stream peut être vide si aucune correspondance n'est trouvée.

**La fonction de comptage** reçoit également les critères mais utilise généralement seulement la partie filtre. Elle renvoie le total des entités correspondant, en ignorant la pagination. Ceci est utilisé par les composants UI pour afficher le nombre total de résultats ou calculer des pages.

**La fonction de recherche par clé** reçoit une clé d'entité (généralement un ID) et renvoie un `Optional<T>`. Retournez `Optional.empty()` si l'entité n'existe pas.

## Exemple d'API REST {#rest-api-example}

Lors de l'intégration avec une API REST, vous devez convertir les critères de référentiel en paramètres de requête HTTP. Commencez par définir une classe de filtre qui correspond aux capacités de requête de votre API :

```java
public class UserFilter {
  private String department;
  private String status;
  // getters et setters...
}
```

Cette classe de filtre représente les paramètres de recherche que votre API accepte. Le référentiel passera des instances de cette classe à vos fonctions lorsqu'un filtrage est appliqué.

Créez le référentiel avec des fonctions qui traduisent les critères en appels API :

```java
DelegatingRepository<User, UserFilter> apiRepository = new DelegatingRepository<>(
  // Trouver des utilisateurs
  criteria -> {
    Map<String, String> params = buildParams(criteria);
    List<User> users = restClient.get("/users", params);
    return users.stream();
  },

  // Compter les utilisateurs
  criteria -> {
    Map<String, String> filterParams = buildFilterParams(criteria.getFilter());
    return restClient.getCount("/users/count", filterParams);
  },

  // Trouver par ID
  userId -> restClient.getById("/users/" + userId)
);
```

La méthode `buildParams()` extraira les valeurs des critères et les convertira en paramètres de requête, comme `?department=Sales&status=active&offset=20&limit=10`. Votre client REST effectuera ensuite la requête HTTP réelle et désérialisera la réponse.

## Exemple de base de données {#database-example}

L'intégration avec la base de données suit un modèle similaire mais convertit les critères en requêtes SQL. La principale différence est la gestion de la génération SQL et de la liaison des paramètres :

```java
DelegatingRepository<Customer, CustomerFilter> dbRepository = new DelegatingRepository<>(
  // Requête avec filtre, tri, pagination
  criteria -> {
    String sql = buildQuery(criteria);
    return jdbcTemplate.queryForStream(sql, rowMapper);
  },

  // Compter les enregistrements correspondants
  criteria -> {
    String countSql = buildCountQuery(criteria.getFilter());
    return jdbcTemplate.queryForObject(countSql, Integer.class);
  },

  // Trouver par clé primaire
  customerId -> {
    String sql = "SELECT * FROM customers WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, rowMapper, customerId);
  }
);
```

La méthode `buildQuery()` construirait du SQL comme :
```sql
SELECT * FROM customers
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Les propriétés de votre objet de filtre se mappent aux conditions de la clause `WHERE`, tandis que la pagination et le tri sont gérés par les clauses `LIMIT/OFFSET` et `ORDER BY`.

## Utilisation avec les composants UI {#using-with-ui-components}

La beauté du modèle de référentiel est que les composants UI ne savent pas et ne se soucient pas d'où proviennent les données. Que ce soit d'une collection en mémoire, d'une API REST ou d'une base de données, l'utilisation est identique :

```java
// Créer et configurer le référentiel
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Attacher à la table
Table<User> table = new Table<>();
table.setRepository(repository);

// La table affiche automatiquement les utilisateurs d'ingénierie filtrés
```

Lorsque les utilisateurs interagissent avec la [`Table`](../../components/table/overview) (tri des colonnes, changement de pages), la `Table` appelle vos fonctions de référentiel avec des critères mis à jour. Vos fonctions traduisent cela en appels API ou en requêtes SQL, et la table se met à jour automatiquement avec les résultats.

## Quand étendre `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

Si vous avez besoin de méthodes personnalisées ou d'une initialisation complexe, étendez <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> directement :

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
  @Override
  public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
    // Implementation
  }

  @Override
  public int size(RepositoryCriteria<User, UserFilter> criteria) {
    // Implementation
  }

  @Override
  public Optional<User> find(Object key) {
    // Implementation
  }

  // Ajouter des méthodes personnalisées
  public List<User> findActiveManagers() {
    // Logique de requête personnalisée
  }
}
```
