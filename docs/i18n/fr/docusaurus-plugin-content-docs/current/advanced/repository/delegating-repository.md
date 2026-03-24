---
title: Custom data sources
sidebar_position: 4
_i18n_hash: 7ead4a1af63b9c20d81dc2fd9b67380f
---
<!-- vale off -->
# Sources de données personnalisées <DocChip chip='since' label='25.02' />
<!-- vale on -->

Lorsque vos données résident en dehors de votre application - dans une API REST, une base de données ou un service externe - vous devez créer une implémentation de dépôt personnalisée. La classe <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> rend cela simple en vous permettant de fournir des fonctions au lieu d’implémenter une classe complète.

## Comment fonctionne `DelegatingRepository` {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> est une classe concrète qui étend <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>. Au lieu d'implémenter des méthodes abstraites, vous fournissez trois fonctions dans le constructeur :

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
  // 1. Fonction de recherche - renvoie des données filtrées/tris/paginées
  criteria -> userService.findUsers(criteria),
  
  // 2. Fonction de comptage - renvoie le total pour le filtre
  criteria -> userService.countUsers(criteria),
  
  // 3. Fonction de recherche par clé - renvoie une entité unique par ID
  userId -> userService.findById(userId)
);
```

Chaque fonction a un but spécifique :

**Fonction de recherche** reçoit un objet `RepositoryCriteria` contenant :
- `getFilter()` - votre objet filtre personnalisé (le paramètre de type `F`)
- `getOffset()` et `getLimit()` - pour la pagination
- `getOrderCriteria()` - liste des règles de tri

Cette fonction doit retourner un `Stream<T>` d'entités qui correspondent aux critères. Le flux peut être vide si aucune correspondance n'est trouvée.

**Fonction de comptage** reçoit également les critères mais n'utilise généralement que la partie filtre. Elle renvoie le nombre total d'entités correspondantes, en ignorant la pagination. Cela est utilisé par les composants de l'interface utilisateur pour afficher les résultats totaux ou calculer les pages.

**Fonction de recherche par clé** reçoit une clé d'entité (généralement un ID) et renvoie un `Optional<T>`. Retournez `Optional.empty()` si l'entité n'existe pas.

## Exemple d'API REST {#rest-api-example}

Lors de l'intégration avec une API REST, vous devez convertir les critères de dépôt en paramètres de requête HTTP. Commencez par définir une classe de filtre qui correspond aux capacités de requête de votre API :

```java
public class UserFilter {
  private String department;
  private String status;
  // getters and setters...
}
```

Cette classe de filtre représente les paramètres de recherche que votre API accepte. Le dépôt passera des instances de cette classe à vos fonctions lors de l'application des filtres.

Créez le dépôt avec des fonctions qui traduisent les critères en appels API :

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

La méthode `buildParams()` extraira des valeurs des critères et les convertira en paramètres de requête comme `?department=Sales&status=active&offset=20&limit=10`. Votre client REST fera ensuite la requête HTTP réelle et désérialisera la réponse.

## Exemple de base de données {#database-example}

L'intégration de bases de données suit un modèle similaire mais convertit les critères en requêtes SQL. La principale différence est la gestion de la génération SQL et du binding des paramètres :

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

Les propriétés de votre objet filtre se cartographient aux conditions de la clause `WHERE`, tandis que la pagination et le tri sont gérés par les clauses `LIMIT/OFFSET` et `ORDER BY`.

## Utilisation avec des composants d'interface utilisateur {#using-with-ui-components}

La beauté du modèle de dépôt est que les composants d’interface utilisateur n'ont pas besoin de savoir ou de se soucier d'où proviennent les données. Que ce soit une collection en mémoire, une API REST ou une base de données, l'utilisation est identique :

```java
// Créer et configurer le dépôt
Repository<User> repository = createApiRepository();
UserFilter filter = new UserFilter();
filter.setDepartment("Engineering");
repository.setBaseFilter(filter);

// Attacher à la table
Table<User> table = new Table<>();
table.setRepository(repository);

// La table affiche automatiquement les utilisateurs d'ingénierie filtrés
```

Lorsque les utilisateurs interagissent avec la [`Table`](../../components/table/overview) (tri des colonnes, changement de pages), la `Table` appelle vos fonctions de dépôt avec des critères mis à jour. Vos fonctions traduisent ces appels en appels API ou en requêtes SQL, et la table se met à jour automatiquement avec les résultats.

## Quand étendre `AbstractQueryableRepository` {#when-to-extend-abstractqueryablerepository}

Si vous avez besoin de méthodes personnalisées ou d'une initialisation complexe, étendez <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink> directement :

```java
public class CustomUserRepository extends AbstractQueryableRepository<User, UserFilter> {
  @Override
  public Stream<User> findBy(RepositoryCriteria<User, UserFilter> criteria) {
    // Implémentation
  }
  
  @Override
  public int size(RepositoryCriteria<User, UserFilter> criteria) {
    // Implémentation
  }
  
  @Override
  public Optional<User> find(Object key) {
    // Implémentation
  }
  
  // Ajouter des méthodes personnalisées
  public List<User> findActiveManagers() {
    // Logique de requête personnalisée
  }
}
```
