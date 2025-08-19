---
title: Repository
sidebar_position: 1
sidebar_class_name: new-content
_i18n_hash: dc6f7bbfe82d68565cbe8da6436f080c
---
<!-- vale off -->
# Repository <DocChip chip='since' label='24.00' />
<!-- vale on -->

Le modèle `Repository` dans webforJ fournit un moyen standardisé de gérer et d'interroger des collections d'entités. Il agit comme une couche d'abstraction entre vos composants d'interface utilisateur et les données, facilitant ainsi le travail avec différentes sources de données tout en maintenant un comportement cohérent.

## Pourquoi utiliser un repository {#why-use-repository}

`Repository` élimine les mises à jour manuelles tout en gardant vos données d'origine intactes :

```java
// Sans Repository - mises à jour manuelles
List<Customer> customers = loadCustomers();
Table<Customer> table = new Table<>();
table.setItems(customers);

// L'ajout nécessite un rechargement complet
customers.add(newCustomer);
table.setItems(customers); // Doit tout recharger
```

```java
// Avec Repository - synchronisation automatique
List<Customer> customers = loadCustomers();
CollectionRepository<Customer> repository = new CollectionRepository<>(customers);
Table<Customer> table = new Table<>();
table.setRepository(repository);

// L'ajout se synchronise automatiquement
customers.add(newCustomer);
repository.commit(newCustomer); // Ne met à jour que ce qui a changé
```

## Repository de collection {#collection-repository}

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

Le `Repository` agit comme un pont entre vos données et les composants d'interface utilisateur. Lorsque les données changent, vous informez le repository par la méthode `commit()` :

```java
List<Product> products = new ArrayList<>();
CollectionRepository<Product> repository = new CollectionRepository<>(products);

// Ajouter un nouveau produit
Product newProduct = new Product("P4", "Gizmo", 79.99, 15);
products.add(newProduct);
repository.commit(); // Tous les composants connectés se mettent à jour

// Mettre à jour un produit existant  
products.get(0).setPrice(89.99);
repository.commit(products.get(0)); // Ne met à jour que cette ligne spécifique

// Supprimer un produit
products.remove(2);
repository.commit(); // Actualise la vue
```

La méthode commit a deux signatures :
- `commit()` - Indique au repository de tout rafraîchir. Elle déclenche un `RepositoryCommitEvent` avec toutes les données actuelles
- `commit(entity)` - Cible une entité spécifique. Le repository trouve cette entité par sa clé et met à jour uniquement les éléments d'UI affectés

:::important Engagement d'entités uniques
Cette distinction est importante pour les performances. Lorsque vous mettez à jour un champ dans un tableau de 1000 lignes, `commit(entity)` met à jour juste cette cellule tandis que `commit()` rafraîchirait toutes les lignes.
:::

## Filtrage des données {#filtering-data}

Le filtre du repository contrôle quelles données circulent vers les composants connectés. Votre collection sous-jacente reste inchangée car le filtre agit comme une lentille :

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
3. Notifie les composants connectés pour mettre à jour leur affichage

Le filtre persiste jusqu'à ce que vous le changiez. Les nouveaux éléments ajoutés à la collection sont automatiquement testés par rapport au filtre actuel.

## Travail avec les clés d'entité {#working-with-entity-keys}

Lorsque vos entités implémentent <JavadocLink type="data" location="com/webforj/data/HasEntityKey" code="true">HasEntityKey</JavadocLink>, le repository peut trouver et mettre à jour des éléments spécifiques par leur ID :

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

Sans `HasEntityKey` :
- `repository.find("C001")` ne trouvera pas votre client car il recherche un objet qui est égal à "C001"
- `repository.commit(entity)` fonctionne toujours, mais repose sur l'égalité des objets
- Les composants d'UI ne peuvent pas sélectionner des éléments par ID, seulement par référence d'objet

## Intégration UI {#ui-integration}

`Repository` s'intègre avec des composants sensibles aux données :

```java
// Créer le repository et la table
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

## Étapes suivantes {#next-steps}

<DocCardList className="topics-section" />
