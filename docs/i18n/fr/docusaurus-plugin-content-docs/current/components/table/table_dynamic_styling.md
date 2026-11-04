---
sidebar_position: 21
title: Styles dynamiques
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 6d82241fb67ffa1ce5a1e8c974a96dea
---
<!-- vale off -->
# Styles dynamiques <DocChip chip='since' label='25.00' />
<!-- vale on -->

Dans webforJ 25 et les versions ultérieures, il est possible de styliser des lignes et des cellules individuelles de la table à l’aide de noms de parties personnalisés. Ces noms peuvent être attribués dynamiquement en fonction de la logique de votre application, ce qui vous donne un contrôle précis sur l’apparence de la table.

## Style des lignes {#row-styling}

La méthode `setRowPartProvider()` attribue des noms de parties à des lignes entières en fonction de l’élément de données qu’elles contiennent. Vous pouvez ainsi mettre en évidence les lignes complètes qui répondent à des conditions spécifiques, par exemple en alternant les couleurs d’arrière-plan des lignes paires.

Ces noms de style peuvent être ciblés à l’aide du sélecteur `::part()` en CSS.

:::tip Parties d’ombre
Le sélecteur `::part()` est une fonctionnalité CSS spéciale qui permet de styliser des éléments à l’intérieur du DOM fantôme d’un composant, à condition que ces éléments exposent un attribut `part`. Cette fonctionnalité est particulièrement utile pour styliser les parties internes des composants webforJ, comme les lignes ou les cellules d’une table.

Pour en savoir plus sur le fonctionnement des parties d’ombre et sur la façon de les définir et de les cibler, consultez la section [Styles](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Style des cellules {#cell-styling}

La méthode `setCellPartProvider()` stylise des cellules individuelles en fonction à la fois de l’élément de données et de la colonne à laquelle elles appartiennent. Elle est donc idéale pour mettre en évidence des valeurs spécifiques, par exemple pour signaler les âges dépassant un seuil ou les entrées non valides.

Comme pour les parties des lignes, les parties des cellules sont définies par un nom et ciblées à l’aide du sélecteur `::part()`.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableCellStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Réagir aux mises à jour des données {#reacting-to-data-updates}

Si votre application modifie les données par programmation, par exemple en mettant à jour l’âge d’un utilisateur, la table réévalue et réapplique automatiquement les styles associés aux lignes ou aux cellules lorsque l’élément mis à jour est validé dans le dépôt.

Dans cette démonstration, les cellules de la colonne Age sont stylisées selon un seuil : les âges supérieurs à 30 apparaissent en vert, tandis que les âges inférieurs ou égaux à 30 apparaissent en rouge. Un clic sur le bouton fait alterner l’âge d’Alice entre 28 et 31, déclenchant la réapplication du style approprié par `setCellPartProvider` lorsque les données sont validées.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Lignes alternées {#striped-rows}

Activez l’alternance des couleurs d’arrière-plan des lignes pour améliorer la lisibilité :

```java
// Appliquer un style avec des lignes alternées
table.setStriped(true);
```

## Bordures {#borders}

Configurez les bordures affichées autour de la `Table`, des colonnes et des lignes :

```java
// Activer toutes les bordures
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Supprimer toutes les bordures
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

La démonstration ci-dessous présente une façon simple d’harmoniser l’apparence de votre `Table` avec le reste de votre application à l’aide de `setStriped()` et `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java',
  'src/main/java/com/webforj/samples/views/table/Person.java',
]}
height='300px'
/>

:::tip Gérer et interroger les données
Pour savoir comment utiliser le modèle `Repository` afin de gérer et d’interroger des collections, consultez les [articles sur Repository](/docs/advanced/repository/overview).
:::
