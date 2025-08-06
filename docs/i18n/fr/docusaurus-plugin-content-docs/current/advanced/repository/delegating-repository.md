---
title: Custom data sources
sidebar_position: 4
_i18n_hash: dbcaaa420987ee45f54d3f4059e98d92
---
<!-- vale off -->
# Sources de données personnalisées <DocChip chip='since' label='25.02' />
<!-- vale on -->

Lorsque vos données résident en dehors de votre application - dans une API REST, une base de données ou un service externe - vous devez créer une implémentation de dépôt personnalisé. La classe <JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> facilite cela en vous permettant de fournir des fonctions au lieu d'implémenter une classe complète.

## Comment fonctionne `DelegatingRepository` {#how-delegatingrepository-works}

<JavadocLink type="data" location="com/webforj/data/repository/DelegatingRepository" code="true">DelegatingRepository</JavadocLink> est une classe concrète qui étend <JavadocLink type="data" location="com/webforj/data/repository/AbstractQueryableRepository" code="true">AbstractQueryableRepository</JavadocLink>. Au lieu d’implémenter des méthodes abstraites, vous fournissez trois fonctions dans le constructeur :

```java
DelegatingRepository<User, UserFilter> repository = new DelegatingRepository<>(
    // 1. Fonction de recherche - renvoie des données filtrées/triées/pagées
    criteria -> userService.findUsers(criteria),
    
    // 2. Fonction de comptage - renvoie le nombre total pour le filtre
    criteria -> userService.countUsers(criteria),
    
    // 3. Fonction de recherche par clé - renvoie une seule entité par ID
    userId -> userService.findById(userId)
);
```

Chaque fonction a un but spécifique :

**Fonction de recherche** reçoit un objet `RepositoryCriteria` contenant :
- `getFilter()` - votre objet filtre personnalisé (le paramètre de type `F`)
- `getOffset()` et `getLimit()` - pour la pagination
- `getOrderCriteria()` - liste des règles de tri

Cette fonction doit renvoyer un `Stream<T>` d'entités qui correspondent aux critères. Le flux peut être vide si aucune correspondance n'est trouvée.

**Fonction de comptage** reçoit également les critères, mais utilise généralement uniquement la partie filtre. Elle renvoie le nombre total d'entités correspondantes, en ignorant la pagination. Cela est utilisé par les composants d'interface utilisateur pour afficher le total des résultats ou calculer des pages.

**Fonction de recherche par clé** reçoit une clé d'entité (généralement un ID) et renvoie un `Optional<T>`. Retournez `Optional.empty()` si l'entité n'existe pas.

## Exemple d'API REST {#rest-api-example}

Lors de l'intégration avec une API REST, vous devez convertir les critères du dépôt en paramètres de requête HTTP. Commencez par définir une classe de filtre qui correspond aux capacités de requête de votre API :

```java
public class UserFilter {
    private String department;
    private String status;
    // getters et setters...
}
```

Cette classe de filtre représente les paramètres de recherche que votre API accepte. Le dépôt passera des instances de cette classe à vos fonctions lors de l'application des filtres.

Créez le dépôt avec des fonctions qui traduisent les critères en appels d'API :

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

La méthode `buildParams()` extraire les valeurs des critères et les convertit en paramètres de requête comme `?department=Sales&status=active&offset=20&limit=10`. Votre client REST effectue ensuite la véritable requête HTTP et désérialise la réponse.

## Exemple de base de données {#database-example}

L'intégration de la base de données suit un modèle similaire mais convertit les critères en requêtes SQL. La principale différence réside dans la génération SQL et le liaison des paramètres :

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

La méthode `buildQuery()` construirait une SQL comme :
```sql
SELECT * FROM customers 
WHERE status = ? AND region = ?
ORDER BY created_date DESC, name ASC
LIMIT ? OFFSET ?
```

Les propriétés de votre objet filtre se mappent aux conditions de la clause `WHERE`, tandis que la pagination et le tri sont gérés par les clauses `LIMIT/OFFSET` et `ORDER BY`.

## Utilisation avec des composants d'interface utilisateur {#using-with-ui-components}

La beauté du modèle de dépôt est que les composants d'interface utilisateur ne savent pas et ne se soucient pas de l'origine des données. Que ce soit une collection en mémoire, une API REST ou une base de données, l'utilisation est identique :

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

Lorsque les utilisateurs interagissent avec le [`Table`](../../components/table/overview) (trier les colonnes, changer de pages), le `Table` appelle vos fonctions de dépôt avec des critères mis à jour. Vos fonctions traduisent celles-ci en appels d’API ou en requêtes SQL, et la table se met à jour automatiquement avec les résultats.

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
