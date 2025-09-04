---
sidebar_position: 21
title: Dynamic Styling
slug: styling
sidebar_class_name: updated-content
_i18n_hash: 38ec04cdf27a7de8a9e51ad24cf66009
---
<!-- vale off -->
# Stylisation dynamique <DocChip chip='since' label='25.00' />
<!-- vale on -->

Dans webforJ 25 et versions ultérieures, il est possible de styliser des lignes et des cellules individuelles dans la Table en utilisant des noms de parties personnalisés. Ces noms peuvent être attribués dynamiquement en fonction de la logique de votre application, vous offrant un contrôle détaillé sur l'apparence de la table.

## Stylisation des lignes {#row-styling}

La méthode `setRowPartProvider()` attribue des noms de parties à des lignes entières en fonction des éléments de données qu'elles contiennent. Cela vous permet de mettre en valeur des lignes complètes qui répondent à des conditions spécifiques, par exemple, des couleurs de fond alternées pour les lignes paires.

Ces noms de style peuvent être ciblés à l'aide du sélecteur `::part()` dans votre CSS.

:::tip Parties d'ombre
Le sélecteur `::part()` est une fonctionnalité CSS spéciale qui vous permet de styliser des éléments à l'intérieur du DOM d'ombre d'un composant, tant que ces éléments exposent un attribut `part`. Cela est particulièrement utile pour styliser les parties internes des composants webforJ, comme les lignes ou les cellules d'une table.
 
Pour en savoir plus sur le fonctionnement des parties d'ombre et sur la façon de les définir et de les cibler, consultez la section [Styling](../../styling/shadow-parts).
:::


<ComponentDemo 
path='/webforj/tablerowstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRowStylingView.java'
height='300px'
/>

## Stylisation des cellules {#cell-styling}

La méthode `setCellPartProvider()` stylise des cellules individuelles en fonction à la fois de l’élément de données et de la colonne à laquelle elles appartiennent. Cela le rend idéal pour mettre en valeur des valeurs spécifiques, comme signaler des âges dépassant un seuil ou des entrées invalides.

Comme pour les parties de lignes, les parties de cellules sont définies par un nom et ciblées à l'aide du sélecteur `::part()`.

<ComponentDemo 
path='/webforj/tablecellstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
height='300px'
/>

## Réagir aux mises à jour de données {#reacting-to-data-updates}

Si votre application modifie des données par programme, comme mettre à jour l'âge d'un utilisateur, la table réévaluera automatiquement et réappliquera tous les styles de lignes ou de cellules associés une fois que l’élément mis à jour est validé dans le référentiel.

<ComponentDemo 
path='/webforj/tabledynamicstyling?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableDynamicStylingView.java'
height='475px'
/>

## Lignes à rayures {#striped-rows}

Activez des couleurs de fond alternées pour les lignes afin d'améliorer la lisibilité :

```java
// Appliquer un style de lignes à rayures
table.setStriped(true);
```

## Bordures {#borders}

Configurez les bordures qui sont affichées autour de la `Table`, des colonnes et des lignes :

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
