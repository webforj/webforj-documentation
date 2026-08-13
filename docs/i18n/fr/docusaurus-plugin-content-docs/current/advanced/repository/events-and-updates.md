---
title: Events and updates
sidebar_position: 5
description: >-
  React to repository commits with RepositoryCommitEvent listeners, choose
  single-entity or bulk updates, and manage listener registrations.
_i18n_hash: 9ce2e7d25b38cd0d04acd30c80edffb3
---
<!-- vale off -->
# Événements et mises à jour <DocChip chip='since' label='24.00' />
<!-- vale on -->

Les événements de `Repository` vous permettent de réagir aux modifications de données. Au-delà des mises à jour automatiques de l'interface utilisateur, vous pouvez écouter les changements pour déclencher une logique personnalisée.

## Cycle de vie des événements `Repository` {#repository-event-lifecycle}

Chaque appel à `commit()` déclenche un <JavadocLink type="data" location="com/webforj/data/repository/event/RepositoryCommitEvent" code="true">RepositoryCommitEvent</JavadocLink>. Cet événement transmet des informations sur ce qui a changé :

```java
repository.onCommit(event -> {
  // Obtenir toutes les entités engagées
  List<Customer> commits = event.getCommits();

  // Vérifier s'il s'agit d'une mise à jour d'entité unique
  if (event.isSingleCommit()) {
    Customer updated = event.getFirstCommit();
    System.out.println("Mis à jour : " + updated.getName());
  }
});
```

L'événement vous indique :
- `getCommits()` - Liste des entités qui ont été engagées
- `isSingleCommit()` - Indique si c'était une mise à jour d'entité unique ciblée
- `getFirstCommit()` - Méthode pratique pour obtenir la première (ou unique) entité

Pour `commit()` sans paramètres, l'événement contient toutes les entités actuellement dans le dépôt après filtrage.

## Stratégies de mise à jour {#update-strategies}

Les deux signatures de commit servent des objectifs différents :

```java
// Mise à jour d'entité unique - efficace pour des changements individuels
Customer customer = customers.get(0);
customer.setStatus("VIP");
repository.commit(customer);

// Mise à jour en masse - efficace pour plusieurs changements
products.clear();
products.addAll(loadProductsFromCsv());
repository.commit();
```

Les commits d'entités uniques sont chirurgicaux - ils informent les composants connectés exactement quelle ligne a changé. Le [`Table`](../../components/table/overview) peut mettre à jour uniquement les cellules de cette ligne sans toucher à rien d'autre.

Les commits en masse mettent tout à jour. Utilisez-les lorsque :
- Plusieurs entités ont changé
- Vous avez ajouté ou supprimé des éléments
- Vous n'êtes pas sûr de ce qui a changé

## Modèles UI réactifs {#reactive-ui-patterns}

Les événements `Repository` vous permettent de garder les affichages de résumé synchronisés avec vos données :

```java
// Étiquettes auto-mises à jour
repository.onCommit(event -> {
  double total = sales.stream().mapToDouble(Sale::getAmount).sum();
  totalLabel.setText(String.format("Total : $%.2f", total));
  countLabel.setText("Ventes : " + sales.size());
});

// Comptages de résultats en direct
repository.onCommit(e -> {
  long count = repository.findAll().count();
  resultsLabel.setText(count + " produits trouvés");
});
```

Ces écouteurs se déclenchent à chaque commit, que ce soit à partir d'actions utilisateur, d'importations de données ou de mises à jour programmatiques. L'événement vous donne accès aux entités engagées, mais souvent vous recalculerez à partir de la collection source pour inclure toutes les données actuelles.

## Gestion de la mémoire {#memory-management}

Les écouteurs d'événements conservent des références à vos composants. Si vous ne les supprimez pas, le `Repository` garde vos composants en mémoire même après qu'ils ne sont plus affichés :

```java
// Conserver la référence pour supprimer plus tard
ListenerRegistration<RepositoryCommitEvent<Data>> registration =
  repository.onCommit(event -> {
    updateDisplay(event.getCommits());
  });

// Nettoyer l'écouteur lorsque le composant est détruit
if (registration != null) {
  registration.remove();
}
```

La méthode `onCommit()` renvoie une `ListenerRegistration`. Conservez cette référence et appelez `remove()` lorsque votre composant est détruit ou n'a plus besoin de mises à jour. Cela empêche les fuites de mémoire dans les applications de longue durée.
