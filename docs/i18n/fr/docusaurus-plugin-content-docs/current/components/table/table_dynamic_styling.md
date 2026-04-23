---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: 03c0fb81b4bcabef5db6dfb9785eef3d
---
<!-- vale off -->
# Styling dynamique <DocChip chip='since' label='25.00' />
<!-- vale on -->

Dans webforJ 25 et supérieur, il est possible de styliser des lignes et des cellules individuelles dans le tableau en utilisant des noms de parties personnalisés. Ces noms peuvent être assignés dynamiquement en fonction de la logique de votre application, vous donnant un contrôle précis sur l'apparence du tableau.

## Stylisation des lignes {#row-styling}

La méthode `setRowPartProvider()` assigne des noms de parties à des lignes entières en fonction de l’élément de données qu'elles contiennent. Cela vous permet de mettre en évidence des lignes complètes qui répondent à des conditions spécifiques, par exemple, des couleurs de fond alternées pour les lignes paires.

Ces noms de styles peuvent être ciblés à l'aide du sélecteur `::part()` dans votre CSS.

:::tip Parties d'ombre
Le sélecteur `::part()` est une fonctionnalité CSS spéciale qui vous permet de styliser des éléments à l'intérieur du DOM d'ombre d'un composant, tant que ces éléments exposent un attribut `part`. Cela est particulièrement utile pour styliser des parties internes des composants webforJ, comme des lignes ou des cellules dans un tableau.

Pour plus d'informations sur le fonctionnement des parties d'ombre et comment les définir et les cibler, consultez la section [Stylisation](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
cssURL='/css/table/table-row-styling-view.css'
height='300px'
/>

## Stylisation des cellules {#cell-styling}

La méthode `setCellPartProvider()` stylise des cellules individuelles en fonction de l’élément de données ainsi que de la colonne à laquelle elles appartiennent. Cela la rend idéale pour mettre en avant des valeurs spécifiques, comme signaler des âges dépassant un seuil ou des entrées invalides.

Comme les parties de lignes, les parties de cellules sont définies par un nom et ciblées à l'aide du sélecteur `::part()`.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
cssURL='/css/table/table-cell-styling-view.css'
height='300px'
/>

## Réaction aux mises à jour de données {#reacting-to-data-updates}

Si votre application modifie des données par programme, par exemple, en mettant à jour l’âge d’un utilisateur, le tableau réévaluera et réappliquera automatiquement tous les styles de lignes ou de cellules associés une fois que l’élément mis à jour est engagé dans le dépôt.

Dans cette démonstration, les cellules dans la colonne Âge sont stylisées en fonction d’un seuil : les âges supérieurs à 30 apparaissent en vert, tandis que les âges de 30 et moins apparaissent en rouge. Cliquer sur le bouton bascule l'âge d'Alice entre 28 et 31, déclenchant le `setCellPartProvider` pour réappliquer le style approprié lorsque les données sont engagées.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
cssURL='/css/table/table-dynamic-styling-view.css'
height='475px'
/>

## Lignes rayées {#striped-rows}

Activez des couleurs de fond alternées pour les lignes afin d'améliorer la lisibilité :

```java
// Appliquer le style de lignes rayées
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

La démo ci-dessous présente un moyen simple d'aligner l'apparence visuelle de votre `Table` avec le reste de votre application en utilisant `setStriped()` et `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Gestion et requêtes de données
Pour des informations sur la façon d'utiliser le modèle de `Repository` pour gérer et interroger des collections, consultez les [articles sur les dépôts](/docs/advanced/repository/overview).
:::
