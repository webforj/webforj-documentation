---
sidebar_position: 21
title: Dynamic Styling
slug: styling
description: >-
  Apply data-driven CSS to Table rows and cells using setRowPartProvider and
  setCellPartProvider with shadow part selectors.
_i18n_hash: 28911597e1e885b0531bb27aa3c8e1a7
---
<!-- vale off -->
# Stylisation dynamique <DocChip chip='since' label='25.00' />
<!-- vale on -->

Dans webforJ 25 et supérieur, il est possible de styliser des lignes et des cellules individuelles dans le tableau en utilisant des noms de parties personnalisés. Ces noms peuvent être attribués dynamiquement en fonction de la logique de votre application, vous donnant un contrôle précis sur l'apparence du tableau.

## Stylisation des lignes {#row-styling}

La méthode `setRowPartProvider()` attribue des noms de parties à des lignes entières en fonction de l'élément de données qu'elles contiennent. Cela vous permet de mettre en surbrillance des lignes entières qui répondent à des conditions spécifiques, par exemple, des couleurs de fond alternées pour les lignes paires.

Ces noms de style peuvent être ciblés en utilisant le sélecteur `::part()` dans votre CSS.

:::tip Parties d'ombre
Le sélecteur `::part()` est une fonctionnalité CSS spéciale qui vous permet de styliser des éléments à l'intérieur du DOM d'ombre d'un composant, tant que ces éléments exposent un attribut `part`. C'est particulièrement utile pour styliser des parties internes des composants webforJ, comme les lignes ou les cellules d'un tableau.

Pour plus d'informations sur le fonctionnement des parties d'ombre et sur la façon de les définir et de les cibler, consultez la section [Styling](../../styling/shadow-parts).
:::


<ComponentDemo
path='/webforj/tablerowstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableRowStylingView.java',
  'src/main/frontend/css/table/table-row-styling-view.css',
]}
height='300px'
/>

## Stylisation des cellules {#cell-styling}

La méthode `setCellPartProvider()` stylise des cellules individuelles en fonction de l'élément de données et de la colonne à laquelle elles appartiennent. Cela la rend idéale pour mettre en évidence des valeurs spécifiques, comme signaler des âges au-dessus d'un seuil ou des entrées invalides.

Tout comme pour les parties de lignes, les parties de cellules sont définies par un nom et ciblées en utilisant le sélecteur `::part()`.

<ComponentDemo
path='/webforj/tablecellstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/frontend/css/table/table-cell-styling-view.css',
]}
height='300px'
/>

## Réagir aux mises à jour des données {#reacting-to-data-updates}

Si votre application modifie les données de manière programmatique, comme la mise à jour de l'âge d'un utilisateur, le tableau réévaluera automatiquement et réappliquera tous les styles de ligne ou de cellule associés une fois que l'élément mis à jour est validé dans le dépôt.

Dans cette démo, les cellules de la colonne Âge sont stylisées en fonction d'un seuil : les âges supérieurs à 30 apparaissent en vert, tandis que les âges de 30 ans et moins apparaissent en rouge. En cliquant sur le bouton, l'âge d'Alice bascule entre 28 et 31, déclenchant la `setCellPartProvider` pour réappliquer le style approprié lorsque les données sont validées.

<ComponentDemo
path='/webforj/tabledynamicstyling'
files={[
  'src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java',
  'src/main/frontend/css/table/table-dynamic-styling-view.css',
]}
height='475px'
/>

## Lignes rayées {#striped-rows}

Activez des couleurs de fond alternées pour les lignes afin d'améliorer la lisibilité :

```java
// Appliquer le style de ligne rayée
table.setStriped(true);
```

## Bordures {#borders}

Configurez quelles bordures sont affichées autour du `Table`, des colonnes et des lignes :

```java
// Activer toutes les bordures
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Retirer toutes les bordures
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

La démo ci-dessous présente une manière simple d'aligner l'apparence visuelle de votre `Table` avec le reste de votre application en utilisant `setStriped()` et `setBordersVisible()`.

<ComponentDemo
path='/webforj/tablelayoutstyling'
files={['src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java']}
height='300px'
/>

:::tip Gestion et interrogation des données
Pour des informations sur l'utilisation du modèle `Repository` pour gérer et interroger des collections, consultez les [articles sur le Repository](/docs/advanced/repository/overview).
:::
