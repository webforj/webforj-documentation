---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 8e9f61685fbb3a7fb830463f1320e8cf
---
<!-- vale off -->
# Styling dynamique <DocChip chip='since' label='25.00' />
<!-- vale on -->

Dans webforJ 25 et versions supérieures, il est possible de styliser des lignes et des cellules individuelles dans le tableau en utilisant des noms de parties personnalisés. Ces noms peuvent être attribués dynamiquement en fonction de la logique de votre application, vous donnant un contrôle précis sur l'apparence du tableau.

## Stylisation des lignes {#row-styling}

La méthode `setRowPartProvider()` attribue des noms de parties à des lignes entières en fonction des éléments de données qu'elles contiennent. Cela vous permet de mettre en évidence des lignes complètes qui répondent à des conditions spécifiques, par exemple, des couleurs de fond alternées pour les lignes paires.

Ces noms de style peuvent être ciblés en utilisant le sélecteur `::part()` dans votre CSS.

:::tip Parties d'ombre
Le sélecteur `::part()` est une fonctionnalité CSS spéciale qui permet de styliser des éléments à l'intérieur du DOM d'ombre d'un composant, tant que ces éléments exposent un attribut `part`. Ceci est particulièrement utile pour le stylisation des parties internes des composants webforJ, comme les lignes ou les cellules dans un tableau.

Pour plus d'informations sur le fonctionnement des parties d'ombre et comment les définir et les cibler, consultez la section [Stylisation](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Stylisation des cellules {#cell-styling}

La méthode `setCellPartProvider()` stylise des cellules individuelles en fonction à la fois de l'élément de données et de la colonne à laquelle elles appartiennent. Cela le rend idéal pour mettre en évidence des valeurs spécifiques, comme signaler des âges dépassant un seuil ou des entrées invalides.

Comme pour les parties de ligne, les parties de cellule sont définies par un nom et ciblées à l'aide du sélecteur `::part()`.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Réaction aux mises à jour de données {#reacting-to-data-updates}

Si votre application modifie des données par programmation, comme mettre à jour l'âge d'un utilisateur, le tableau réévaluera automatiquement et réappliquera tous les styles de lignes ou de cellules associés une fois que l'élément mis à jour est engagé dans le référentiel.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Lignes rayées {#striped-rows}

Activez des couleurs de fond alternées pour les lignes afin d'améliorer la lisibilité :

```java
// Appliquer un style de ligne rayé
table.setStriped(true);
```

## Bordures {#borders}

Configurez quelles bordures sont affichées autour du `Table`, des colonnes et des lignes :

```java
// Activer toutes les bordures
table.setBordersVisible(EnumSet.of(Table.Border.AROUND, Table.Border.COLUMNS, Table.Border.ROWS));

// Supprimer toutes les bordures
table.setBordersVisible(EnumSet.noneOf(Table.Border.class));
```

La démo ci-dessous présente une manière simple d'aligner l'apparence visuelle de votre `Table` avec le reste de votre application en utilisant `setStriped()` et `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Gestion et interrogation des données
Pour des informations sur l'utilisation du modèle `Repository` pour gérer et interroger des collections, consultez les [articles sur le dépôt](/docs/advanced/repository/overview).
:::
