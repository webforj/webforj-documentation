---
sidebar_position: 21
title: Dynamic Styling
slug: styling
_i18n_hash: ab74c802642742faeaa38ee9a2f6e8da
---
<!-- vale off -->
# Stylisation dynamique <DocChip chip='since' label='25.00' />
<!-- vale on -->

Dans webforJ 25 et plus, il est possible de styliser des lignes et des cellules individuelles dans le tableau en utilisant des noms de parties personnalisés. Ces noms peuvent être assignés dynamiquement en fonction de la logique de votre application, vous permettant de contrôler l'apparence du tableau de manière détaillée.

## Stylisation des lignes {#row-styling}

La méthode `setRowPartProvider()` assigne des noms de parties à des lignes entières en fonction de l’élément de données qu'elles contiennent. Cela vous permet de mettre en évidence des lignes entières qui répondent à des conditions spécifiques – par exemple, des couleurs de fond alternées pour les lignes paires.

Ces noms de style peuvent être ciblés en utilisant le sélecteur `::part()` dans votre CSS.

:::tip Parties d'ombre
Le sélecteur `::part()` est une fonctionnalité CSS spéciale qui vous permet de styliser des éléments à l’intérieur du shadow DOM d’un composant, tant que ces éléments exposent un attribut `part`. Cela est particulièrement utile pour styliser des parties internes des composants webforJ, comme des lignes ou des cellules dans un tableau.

Pour en savoir plus sur le fonctionnement des parties d'ombre et sur la manière de les définir et de les cibler, consultez la section [Stylisation](../../styling/shadow-parts).
:::

<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Stylisation des cellules {#cell-styling}

La méthode `setCellPartProvider()` stylise des cellules individuelles en fonction à la fois de l’élément de données et de la colonne à laquelle elles appartiennent. Cela le rend idéal pour mettre en évidence des valeurs spécifiques, comme faire ressortir des âges dépassant un seuil ou des entrées invalides.

Comme pour les parties de ligne, les parties de cellule sont définies par un nom et ciblées en utilisant le sélecteur `::part()`.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Réagir aux mises à jour de données {#reacting-to-data-updates}

Si votre application modifie des données de manière programmatique, comme la mise à jour de l'âge d'un utilisateur, le tableau réévaluera automatiquement et réappliquera tous les styles de ligne ou de cellule associés une fois que l'élément mis à jour est validé dans le dépôt.

Dans cette démo, les cellules de la colonne Âge sont stylisées en fonction d'un seuil : les âges supérieurs à 30 apparaissent en vert, tandis que les âges de 30 ans et moins apparaissent en rouge. Cliquer sur le bouton change l'âge d'Alice entre 28 et 31, déclenchant le `setCellPartProvider` pour réappliquer le style approprié lorsque les données sont validées.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Lignes rayées {#striped-rows}

Activez des couleurs de fond alternées pour les lignes afin d'améliorer la lisibilité :

```java
// Appliquer un style de ligne rayée
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

La démo ci-dessous présente une manière simple d’aligner l'apparence visuelle de votre `Table` avec le reste de votre application à l'aide de `setStriped()` et `setBordersVisible()`.

<ComponentDemo 
path='/webforj/tablelayoutstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableLayoutStylingView.java'
height='300px'
/>

:::tip Gestion et requête des données
Pour des informations sur la façon d'utiliser le modèle `Repository` pour gérer et interroger des collections, consultez les [articles sur le Repository](/docs/advanced/repository/overview).
:::
