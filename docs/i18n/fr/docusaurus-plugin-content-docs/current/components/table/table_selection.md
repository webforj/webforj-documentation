---
sidebar_position: 10
title: Selection
slug: selection
description: >-
  Configure single, multi, or no-selection modes on the Table and respond to row
  selection events with appropriate listeners.
_i18n_hash: 3dc9f9e7462f97e260e1112a2966dc18
---
Le composant `Table` offre diverses capacités de sélection. Il existe des méthodes pour sélectionner un seul élément, plusieurs éléments, ou gérer les sélections de manière programmatique.

:::tip Gestion et requête des données
Pour des informations sur l'utilisation du modèle `Repository` pour gérer et interroger des collections, voir les [articles sur le Repository](/docs/advanced/repository/overview).
:::

## Mode de sélection {#selection-mode}

Le mode de sélection dans le tableau détermine comment les éléments peuvent être sélectionnés par l'utilisateur. Il fournit des options pour configurer le comportement de la sélection des éléments. La classe Table fournit une méthode pour définir le mode de sélection :

```java
setSelectionMode(SelectionMode selectionMode)
```

Les options de SelectionMode disponibles incluent :

>- `SINGLE` - (sélection unique)
>- `MULTI` - (sélection multiple)
>- `NONE` - (pas de sélection).

## Événement de sélection {#selection-event}

Le composant `Table` émet plusieurs événements liés à la sélection des lignes. Ces événements capturent les changements dans l'état de sélection des lignes du `Table`. Voici les principaux événements de sélection avec leurs descriptions :

>- `TableItemSelectEvent` - Émis lorsqu'un élément de tableau est sélectionné.
>- `TableItemDeselectEvent` - Émis lorsqu'un élément de tableau est désélectionné.
>- `TableItemSelectionChange` - Émis lorsque la sélection globale dans le tableau change, ou lorsqu'une sélection supplémentaire est choisie.

:::info
Les événements `TableItemSelectEvent` et `TableItemDeselectEvent` ne sont pas déclenchés lorsque le mode de sélection multiple est actif, et que la sélection est faite via la case à cocher d'en-tête. Dans ce cas, `TableItemSelectionChange` doit être utilisé à la place.
:::

Dans l'exemple ci-dessous, un événement `TableItemSelectEvent` sera déclenché chaque fois qu'un utilisateur sélectionne une ligne. L'événement peut être géré en ajoutant un écouteur au tableau en utilisant la méthode `onItemSelect()`.

<ComponentDemo
path='/webforj/tablesingleselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sélection par case à cocher {#checkbox-selection}

La sélection par case à cocher est activée lorsque le mode de sélection est `MULTI`, et permet aux utilisateurs de sélectionner facilement un ou plusieurs éléments à l'aide des cases à cocher associées à chaque ligne. Cette fonctionnalité est particulièrement utile pour les scénarios où les utilisateurs doivent effectuer des actions en masse sur des éléments sélectionnés. La classe Table fournit des méthodes pour activer et personnaliser la sélection par case à cocher.

En utilisant la méthode `setCheckboxSelection(boolean checkboxSelection)`, les cases à cocher peuvent être configurées pour être affichées à côté de chaque ligne, permettant aux utilisateurs de sélectionner des éléments. Le programme ci-dessous montre la sélection multiple et la sélection par case à cocher activées :

<ComponentDemo
path='/webforj/tablemultiselection'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sélection programmatique {#programatic-selection}

Le composant `Table` fournit des méthodes de sélection programmatiques, vous permettant de manipuler les éléments sélectionnés soit par leurs clés, soit par les éléments entiers.

### Sélection par clé {#select-by-key}

La méthode `selectKey(Object... keys)` vous permet de sélectionner programmeusement des éléments en utilisant leurs clés. Vous pouvez passer une ou plusieurs clés à cette méthode, et elle mettra à jour la sélection en conséquence.

### Sélection des éléments d'entrée {#selecting-entry-items}

Enfin, la méthode `select(T... items)` vous permet de sélectionner programmeusement des éléments en passant un ou plusieurs éléments eux-mêmes à cette méthode pour mettre à jour la sélection en conséquence.
