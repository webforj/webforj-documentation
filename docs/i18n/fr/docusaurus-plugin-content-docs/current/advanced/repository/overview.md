---
title: Repository
sidebar_position: 1
sidebar_class_name: has-new-content
_i18n_hash: c285de15c19063af40c61f15cebf4dc1
---
<!-- vale off -->
# Référentiel <DocChip chip='since' label='24.00' />
<!-- vale on -->

Le modèle `Repository` dans webforJ fournit une manière standardisée de gérer et d'interroger des collections d'entités. Il agit comme une couche d'abstraction entre vos composants UI et les données, facilitant le travail avec différentes sources de données tout en maintenant un comportement cohérent.

## Pourquoi utiliser le référentiel {#why-use-repository}

`Repository` élimine les mises à jour manuelles tout en gardant vos données originales intactes :

```java
// Sans Repository - mises à jour manuelles
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// Ajouter nécessite un rechargement complet
customers.add(newCustomer);
table.setItems(customers); // Doit recharger tout
```

```java
// Avec Repository - synchronisation automatique
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// Ajout synchronisé automatiquement
customers.add(newCustomer);
repository.commit(newCustomer); // Met à jour uniquement ce qui a changé
```

## Référentiel de collection {#collection-repository}

Le <JavadocLink type="data" location="com/webforj/data/repository/CollectionRepository" code="true">CollectionRepository</JavadocLink> est l'implémentation la plus courante et enveloppe n'importe quelle Collection Java :

```java
// Depuis ArrayList
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> customerRepo = new CollectionRepository<>(customers);

// Depuis HashSet  
Set<String> tags = new HashSet<>();
CollectionRepository<String> tagRepo = new CollectionRepository<>(tags);

// Depuis n'importe quelle Collection
Collection<Employee> employees = getEmployeesFromHR();
CollectionRepository<Employee> employeeRepo = new CollectionRepository<>(employees);
```

## Synchronisation des données {#data-synchronization}

Le `Repository` agit comme un pont entre vos données et les composants UI. Lorsque des données changent, vous notifiez le référentiel via la méthode `commit()` :

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
repository.commit(); // Actualise la vue
```

La méthode commit a deux signatures :
- `commit()` - Indique au référentiel de tout rafraîchir. Elle déclenche un `RepositoryCommitEvent` avec toutes les données actuelles
- `commit(entity)` - Cible une entité spécifique. Le référentiel trouve cette entité par sa clé et met à jour uniquement les éléments UI affectés

:::important Engagement d'entités uniques
Cette distinction est importante pour la performance. Lorsque vous mettez à jour un champ dans un tableau de 1000 lignes, `commit(entity)` met à jour uniquement cette cellule tandis que `commit()` rafraîchirait toutes les lignes.
:::

## Filtrage des données {#filtering-data}

Le filtre du référentiel contrôle quelles données circulent vers les composants connectés. Votre collection sous-jacente reste inchangée car le filtre agit comme une lentille :

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

Le filtre persiste jusqu'à ce que vous le changiez. Les nouveaux éléments ajoutés à la collection sont automatiquement testés par rapport au filtre actuel.

## Travailler avec des clés d'entité {#working-with-entity-keys}

Le référentiel doit identifier les entités de manière unique pour prendre en charge des opérations comme `find()` et `commit(entity)`. Il existe deux façons de définir comment les entités sont identifiées :

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
    repository.commit(c); // Seule la ligne de ce client est mise à jour
});
```

### Utiliser un fournisseur de clé personnalisé <DocChip chip='since' label='25.10' /> {#using-custom-key-provider}

Pour les entités où vous ne pouvez pas ou ne voulez pas implémenter `HasEntityKey` (comme les entités JPA), utilisez `setKeyProvider()` :

```java
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private double price;

    // Entité gérée par JPA
}

// Configurer le référentiel pour utiliser la méthode getId()
CollectionRepository<Product> repository = new CollectionRepository<>(products);
repository.setKeyProvider(Product::getId);

// Maintenant, la recherche fonctionne avec l'ID
Optional<Product> product = repository.find(123L);
```

### Choisissez une approche {#choosing-approach}

Les deux approches fonctionnent, mais `setKeyProvider()` est préféré lorsque :
- Vous travaillez avec des entités JPA qui ont des champs `@Id`
- Vous ne pouvez pas modifier la classe d'entité
- Vous avez besoin de différentes stratégies de clé pour différents référentiels

Utilisez `HasEntityKey` lorsque :
- Vous contrôlez la classe d'entité
- La logique d'extraction de clé est complexe
- Vous souhaitez que l'entité définisse sa propre identité

## Intégration UI {#ui-integration}

`Repository` s'intègre avec des composants conscients des données :

```java
// Créer le référentiel et le tableau
List<Customer> customers = new ArrayList<>();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);

Table<Customer> table = new Table<>();
table.setRepository(repository);
table.addColumn("ID", Customer::getId);
table.addColumn("Nom", Customer::getName);
table.addColumn("Email", Customer::getEmail);

// Ajouter des données - le tableau se met à jour automatiquement
customers.add(new Customer("C001", "Alice Johnson", "alice@example.com"));
repository.commit();
```

## Prochaines étapes {#next-steps}

<DocCardList className="topics-section" />
