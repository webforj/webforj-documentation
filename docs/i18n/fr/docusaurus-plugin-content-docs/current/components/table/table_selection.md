---
sidebar_position: 10
title: Selection
slug: selection
_i18n_hash: 91df1121dac6d410883e3b43ddf767d5
---
Le composant `Table` fournit diverses capacités de sélection. Il existe des méthodes pour sélectionner un seul élément, plusieurs éléments ou gérer les sélections par programmation.

## Mode de sélection {#selection-mode}

Le mode de sélection dans la table détermine comment les éléments peuvent être sélectionnés par l'utilisateur. Il offre des options pour configurer le comportement de la sélection des éléments. La classe Table fournit une méthode pour définir le mode de sélection :

```java
setSelectionMode(SelectionMode selectionMode)
```

Les options disponibles pour SelectionMode incluent :

>- `SINGLE` - (sélection unique) 
>- `MULTI` - (sélection multiple)
>- `NONE` - (pas de sélection).

## Événement de sélection {#selection-event}

Le composant `Table` émet plusieurs événements liés à la sélection de lignes. Ces événements capturent les changements dans l'état de sélection des lignes du `Table`. Voici les principaux événements de sélection avec leurs descriptions :

>- `TableItemSelectEvent` - Émis lorsqu'un élément de la table est sélectionné.
>- `TableItemDeselectEvent` - Émis lorsqu'un élément de la table est désélectionné.
>- `TableItemSelectionChange` - Émis lorsque la sélection globale dans la table change, ou lorsqu'une sélection supplémentaire est choisie.

:::info
Les événements `TableItemSelectEvent` et `TableItemDeselectEvent` ne sont pas déclenchés lorsque le mode de sélection multiple est actif et que la sélection est effectuée via la case à cocher de l'en-tête. Dans ce cas, `TableItemSelectionChange` doit être utilisé à la place.
:::

Dans l'exemple ci-dessous, un événement `TableItemSelectEvent` sera déclenché chaque fois qu'un utilisateur sélectionne une ligne. L'événement peut être géré en ajoutant un écouteur à la table en utilisant la méthode `onItemSelect()`.

<ComponentDemo 
path='/webforj/tablesingleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSingleSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sélection par case à cocher {#checkbox-selection}

La sélection par case à cocher est activée lorsque le mode de sélection est `MULTI`, et permet aux utilisateurs de sélectionner facilement un ou plusieurs éléments à l'aide de cases à cocher associées à chaque ligne. Cette fonctionnalité est particulièrement utile pour les scénarios où les utilisateurs doivent effectuer des actions en masse sur les éléments sélectionnés. La classe Table fournit des méthodes pour activer et personnaliser la sélection par case à cocher.

En utilisant la méthode `setCheckboxSelection(boolean checkboxSelection)`, les cases à cocher peuvent être configurées pour s'afficher à côté de chaque ligne, permettant ainsi aux utilisateurs de sélectionner des éléments. Le programme ci-dessous montre la sélection multiple et la sélection par case à cocher activées :

<ComponentDemo 
path='/webforj/tablemultiselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSelectionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sélection programmatique {#programatic-selection}

Le composant `Table` fournit des méthodes de sélection programmatique, permettant de manipuler les éléments sélectionnés soit par leurs clés soit par les éléments eux-mêmes. 

### Sélection par clé {#select-by-key}

La méthode `selectKey(Object... keys)` vous permet de sélectionner des éléments par programmation en utilisant leurs clés. Vous pouvez passer une ou plusieurs clés à cette méthode, et cela mettra à jour la sélection en conséquence.

### Sélection par index {#select-by-index}

Utiliser la méthode `selectIndex(int... indices)` vous permet de passer un ou plusieurs indices à la méthode et met à jour les éléments sélectionnés en conséquence.

### Sélection d'éléments entiers {#selecting-entire-items}

Enfin, la méthode `select(T... items)` vous permet de sélectionner programmatique des éléments en passant un ou plusieurs éléments eux-mêmes à cette méthode pour mettre à jour la sélection en conséquence.
