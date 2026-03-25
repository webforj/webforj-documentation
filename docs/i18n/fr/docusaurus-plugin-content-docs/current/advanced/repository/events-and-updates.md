---
title: Events and updates
sidebar_position: 5
_i18n_hash: 0f7a5576086e2922c0549eae23e66061
---
<!-- vale off -->
# Événements et mises à jour <DocChip chip='since' label='24.00' />
<!-- vale on -->

Les événements `Repository` vous permettent de réagir aux changements de données. Au-delà des mises à jour automatiques de l'interface utilisateur, vous pouvez écouter les changements pour déclencher une logique personnalisée.

## Cycle de vie des événements `Repository` {#repository-event-lifecycle}

Chaque appel de `commit()` déclenche un <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Cet événement contient des informations sur ce qui a changé :

```java
repository.onCommit(event -> {
  // Obtenez toutes les entités engagées
  List<Customer> commits = event.getCommits();
  
  // Vérifiez s'il s'agit d'une mise à jour d'entité unique
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Mis à jour : " + updated.getName());
  }
});
```

L'événement vous indique :
- `getCommits()` - Liste des entités qui ont été engagées
- `isSingleCommit()` - Indique s'il s'agissait d'une mise à jour ciblée d'une seule entité
- `getFirstCommit()` - Méthode pratique pour obtenir la première (ou unique) entité

Pour `commit()` sans paramètres, l'événement contient toutes les entités actuellement dans le référentiel après filtrage.

## Stratégies de mise à jour {#update-strategies}

Les deux signatures de commit servent des objectifs différents :

```java
// Mise à jour d'entité unique - efficace pour des modifications individuelles
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Mise à jour en masse - efficace pour plusieurs modifications
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Les commits d'entité unique sont chirurgicaux - ils indiquent aux composants connectés exactement quelle ligne a changé. Le [`Table`](../../components/table/overview) peut mettre à jour uniquement les cellules de cette ligne sans toucher à rien d'autre.

Les commits en masse actualisent tout. Utilisez-les lorsque :
- Plusieurs entités ont changé
- Vous avez ajouté ou supprimé des éléments
- Vous n'êtes pas sûr de ce qui a changé

## Modèles d'interface utilisateur réactive {#reactive-ui-patterns}

Les événements `Repository` vous permettent de garder les affichages de résumés synchronisés avec vos données :

```java
// Étiquettes se mettant à jour automatiquement
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Total : $%.2f", total));
  countLabel.setText("Ventes : " + sales.size());
});

// Comptage des résultats en direct
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " produits trouvés");
});
```

Ces écouteurs se déclenchent à chaque commit, que ce soit à partir d'actions utilisateur, d'importations de données ou de mises à jour programmatiques. L'événement vous donne accès aux entités engagées, mais souvent vous recalculerez à partir de la collection source pour inclure toutes les données actuelles.

## Gestion de la mémoire {#memory-management}

Les écouteurs d'événements conservent des références à vos composants. Si vous ne les supprimez pas, le `Repository` garde vos composants en mémoire même après qu'ils ne soient plus affichés :

```java
// Gardez la référence pour la supprimer plus tard
ListenerRegistration<RepositoryCommitEvent<Data>> registration = 
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Nettoyez l'écouteur lorsque le composant est détruit
if (registration != null) {
  registration.remove();
}
```

La méthode `onCommit()` renvoie une `ListenerRegistration`. Stockez cette référence et appelez `remove()` lorsque votre composant est détruit ou n'a plus besoin de mises à jour. Cela prévient les fuites de mémoire dans les applications de longue durée.
