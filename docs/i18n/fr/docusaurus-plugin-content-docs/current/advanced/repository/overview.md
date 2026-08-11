---
title: Repository
sidebar_position: 1
description: >-
  Bridge data collections to UI components with CollectionRepository, commit
  changes for automatic sync, and apply base filters.
_i18n_hash: 77548d09e4d712ce8f508917d3b953ae
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

Le modèle `Repository` dans webforJ fournit un moyen standardisé de gérer et de requêter des collections d'entités. Il agit comme une couche d'abstraction entre vos composants UI et les données, facilitant le travail avec différentes sources de données tout en maintenant un comportement cohérent.

## Pourquoi utiliser un repository {#why-use-repository}

`Repository` élimine les mises à jour manuelles tout en conservant vos données d'origine intactes :

```java
// Sans Repository - mises à jour manuelles
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Ajouter nécessite un rechargement complet
customers.add(newCustomer);
table.setItems(customers); // Doit tout recharger
```

```java
// Avec Repository - synchronisation automatique
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Ajouter synchronise automatiquement
customers.add(newCustomer);
repository.commit(newCustomer); // Met à jour uniquement ce qui a changé
```

## Repository de collection {#collection-repository}

Le <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> est l'implémentation la plus courante et enveloppe toute Collection Java :

```java
// À partir d'ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// À partir de HashSet
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// À partir de n'importe quelle Collection
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## Synchronisation des données {#data-synchronization}

Le `Repository` agit comme un pont entre vos données et les composants UI. Lorsque les données changent, vous notifiez le repository via la méthode `commit()` :

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Ajouter un nouveau produit
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Tous les composants connectés se mettent à jour

// Mettre à jour un produit existant
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Met à jour uniquement cette ligne spécifique

// Supprimer un produit
products.remove(2);
repository.commit(); // Rafraîchit la vue
```

La méthode commit a deux signatures :
- `commit()` - Indique au repository de rafraîchir tout. Cela déclenche un `RepositoryCommitEvent` avec toutes les données actuelles
- `commit(entity)` - Cible une entité spécifique. Le repository trouve cette entité par sa clé et met à jour uniquement les éléments UI affectés

:::important Engagement de certaines entités
Cette distinction est importante pour la performance. Lorsque vous mettez à jour un champ dans un tableau de 1000 lignes, `commit(entity)` met à jour uniquement cette cellule tandis que `commit()` rafraîchirait toutes les lignes.
:::

## Filtrage des données {#filtering-data}

Le filtre du repository contrôle quelles données sont envoyées aux composants connectés. Votre collection sous-jacente reste inchangée car le filtre agit comme une lentille :

```java
// Filtrer par disponibilité en stock
repository.setBaseFilter(product -> product.getStock() > 0);

// Filtrer par catégorie
repository.setBaseFilter(product -> "Électronique".equals(product.getCategory()));

// Combiner plusieurs conditions
repository.setBaseFilter(product ->
  product.getCategory().equals("Électronique") &&
  product.getStock() > 0 &&
  product.getPrice() < 100.0
);

// Effacer le filtre
repository.setBaseFilter(null);
```

Lorsque vous définissez un filtre, le `Repository` :
1. Applique le prédicat à chaque élément de votre collection
2. Crée un flux filtré d'éléments correspondants
3. Notifie les composants connectés de mettre à jour leur affichage

Le filtre persiste jusqu'à ce que vous le changiez. Les nouveaux éléments ajoutés à la collection sont automatiquement testés contre le filtre actuel.

## Travailler avec des clés d'entité {#working-with-entity-keys}

Le repository doit identifier les entités de manière unique pour prendre en charge des opérations telles que `find()` et `commit(entity)`. Il existe deux manières de définir comment les entités sont identifiées :

### Utiliser l'interface `HasEntityKey` {#using-hasentitykey}

Implémentez <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink> sur votre classe d'entité :

```java
public class Customer implements HasEntityKey {
  private String customerId;
  private String name;
  private String email;

  @Override
  public Object getEntityKey() {
    return customerId;
  }

  // Constructeur et getters/setters...
}

// Trouver par clé
Optional<Customer> customer = repository.find("C001");

// Mettre à jour un client spécifique
customer.ifPresent(c -> {
  c.setEmail("newemail@example.com");
  repository.commit(c); // Seule la ligne de ce client se met à jour
});
```

### Utiliser un fournisseur de clé personnalisé <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Pour les entités pour lesquelles vous ne pouvez pas ou ne voulez pas implémenter `HasEntityKey` (comme les entités JPA), utilisez `setKeyProvider()` :

```java
@Entity
public class Product {
  @Id
  private Long id;
  private String name;
  private double price;

  // Entité gérée par JPA
}

// Configurer le repository pour utiliser la méthode getId()
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Maintenant, la recherche fonctionne avec l'ID
Optional<Product> product = repository.find(123L);
```

### Choisir une approche {#choosing-approach}

Les deux approches fonctionnent, mais `setKeyProvider()` est préféré lorsque :
- Vous travaillez avec des entités JPA qui ont des champs `@Id`
- Vous ne pouvez pas modifier la classe d'entité
- Vous avez besoin de stratégies de clé différentes pour différents repositories

Utilisez `HasEntityKey` quand :
- Vous contrôlez la classe d'entité
- La logique d'extraction de clé est complexe
- Vous voulez que l'entité définisse sa propre identité

## Intégration UI {#ui-integration}

`Repository` s'intègre avec des composants sensibles aux données :

```java
// Créer un repository et une table
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nom", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// Ajouter des données - la table se met à jour automatiquement
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Prochaines étapes {#next-steps}

<DocCardList className="topics-section" />
