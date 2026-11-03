---
sidebar_position: 15
title: Tri
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 7a910f5bdcaa8a9e053bcda6706808c2
---
Le tri permet aux utilisateurs d’organiser les données des colonnes selon un ordre donné, ce qui facilite leur lecture et leur analyse. Cette fonctionnalité est utile lorsque les utilisateurs doivent rapidement trouver les valeurs les plus élevées ou les plus faibles d’une colonne donnée.

:::tip Gestion et interrogation des données
Pour savoir comment utiliser le modèle `Repository` pour gérer et interroger des collections, consultez les [articles sur Repository](/docs/advanced/repository/overview).
:::

<ComponentDemo
path='/webforj/tablesorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Par défaut, une colonne ne peut pas être triée, sauf si cette fonctionnalité est activée explicitement. Pour autoriser le tri sur une colonne spécifique, utilisez la méthode `setSortable(true)` :

```java
table.getColumn("Age").setSortable(true);
```

## Tri sur plusieurs colonnes {#multi-sorting}

:::warning Le tri sur plusieurs colonnes est désactivé par défaut dans webforJ `25.00`
Avant webforJ `25.00`, le tri sur plusieurs colonnes était activé par défaut dans les tableaux. À partir de la version `25.00`, ce comportement a changé : les développeurs doivent désormais activer explicitement le tri sur plusieurs colonnes.
:::

Pour utiliser le tri sur plusieurs colonnes, il faut appliquer `setMultiSorting(true)` au tableau. Les utilisateurs peuvent ainsi trier plusieurs colonnes successivement :

```java
table.setMultiSorting(true);
```

Lorsque le tri sur plusieurs colonnes est activé, cliquer sur plusieurs en-têtes de colonne les trie successivement. La priorité de tri est indiquée visuellement dans l’interface du tableau.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Vous pouvez également définir par programmation la priorité de tri pour le tri côté serveur. Utilisez `setSortIndex()` sur les colonnes à trier, dans l’ordre de priorité :

```java
// Ordre de tri côté serveur
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info L’ordre des colonnes est important
Si `setSortIndex()` n’est pas utilisé, le tableau trie par défaut les colonnes dans l’ordre où elles sont déclarées.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortOrderView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sens du tri {#sort-direction}

Trois paramètres permettent de définir le sens du tri d’une colonne :

- `SortDirection.ASC` : trie la colonne par ordre croissant.
- `SortDirection.DESC` : trie la colonne par ordre décroissant.
- `SortDirection.NONE` : aucun tri n’est appliqué à la colonne.

Lorsque le tri est activé pour une colonne, des flèches verticales apparaissent en haut de celle-ci. Elles permettent à l’utilisateur de basculer entre les différents sens de tri.

Lorsque l’ordre croissant est sélectionné, un `^` s’affiche, tandis qu’un `v` s’affiche pour l’ordre décroissant.


## Tri côté client et côté serveur {#client-vs-server-side-sorting}

Le tri des données se divise généralement en deux approches principales : le **tri côté client** et le **tri côté serveur**.

### Tri côté client {#client-sorting}

Le tri côté client consiste à organiser et à afficher les données directement dans l’interface utilisateur de l’application cliente. Il s’agit du tri que les utilisateurs déclenchent en cliquant sur les en-têtes de colonne, ce qui modifie la représentation visuelle des données à l’écran.

Le développeur ne contrôle pas directement le tri côté client, qui est plutôt déterminé par le type de colonne défini en Java. Les types suivants sont actuellement pris en charge :

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Le tri côté client ne fonctionne pas lorsque seules certaines données sont disponibles sur le client.
:::

### Tri côté serveur {#server-sorting}

Contrairement au tri côté client, le tri côté serveur consiste à organiser les données sur le serveur avant de les transmettre au client. Cette approche est particulièrement utile pour les grands ensembles de données qu’il serait peu pratique de transférer intégralement au client.

Les développeurs ont davantage de contrôle sur la logique de tri côté serveur. Ils peuvent ainsi mettre en œuvre des algorithmes de tri complexes et des optimisations, ce qui convient aux situations impliquant de grandes quantités de données. Le client reçoit alors des données déjà triées, ce qui limite le traitement nécessaire de son côté.


:::info
Le tri côté serveur est une stratégie axée sur les performances pour gérer les ensembles de données qui dépassent les capacités de traitement efficace côté client. C’est également la méthode utilisée par défaut par `Table`.
:::

### Nom de propriété de la colonne {#column-property-name}

Par défaut, `Table` utilise l’ID d’une colonne comme nom de propriété lors de la création des critères de tri pour un référentiel backend. Lorsque l’ID affiché d’une colonne ne correspond pas à la propriété de données sous-jacente, ou lorsque la colonne affiche une valeur calculée, utilisez `setPropertyName()` pour indiquer explicitement à `Table` quelle propriété utiliser pour le tri.

```java
// L’ID de la colonne est « Full Name », mais la propriété backend est « fullName »
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Le nom de propriété est transmis à `OrderCriteria` lorsqu’un événement de tri se déclenche. Les référentiels backend, comme Spring Data JPA ou les adaptateurs REST, peuvent alors créer la clause `ORDER BY` appropriée.

:::warning
Sans `setPropertyName()`, `Table` utilise l’ID de la colonne. Si celui-ci ne correspond pas à une propriété backend valide, le tri échouera silencieusement ou renverra des données dans un ordre incorrect.
:::

Les chemins de propriétés imbriquées sont également pris en charge avec la notation par points :

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparateurs {#comparators}

Le composant `Column` permet aux développeurs d’utiliser les `Comparators` Java pour effectuer des tris dynamiques et personnalisés. Un `Comparator` est un mécanisme qui sert à ordonner deux objets de la même classe, même si celle-ci est définie par l’utilisateur. Cette fonctionnalité permet aux développeurs de personnaliser le tri des données et de mieux contrôler le comportement de tri par défaut, fondé sur l’ordre naturel.

Pour utiliser un tri avec `Comparator` dans une `Column`, vous pouvez recourir à la méthode `setComparator()`. Elle permet de définir une fonction `Comparator` personnalisée qui détermine la logique de tri.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Dans l’exemple ci-dessus, une fonction de comparaison personnalisée prend deux éléments (a et b) et définit l’ordre de tri en fonction des valeurs entières analysées de l’attribut `Number`.

L’utilisation de comparateurs pour trier les colonnes est particulièrement utile avec des valeurs non numériques. Ils sont également utiles pour mettre en œuvre des algorithmes de tri complexes.

:::info
Par défaut, `Table` effectue le tri côté serveur et trie les valeurs non primitives à l’aide de la méthode `toString()` de Object, en les convertissant en chaînes de caractères avant de les trier.
:::
