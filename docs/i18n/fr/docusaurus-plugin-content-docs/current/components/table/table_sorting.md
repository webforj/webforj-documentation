---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 94345d1313939015f02cf46ecaa5af99
---
Le tri permet aux utilisateurs d'organiser les données dans les colonnes par ordre, rendant l'information plus facile à lire et à analyser. Cela est utile lorsque les utilisateurs doivent rapidement trouver les valeurs les plus élevées ou les plus basses dans une colonne particulière.

:::tip Gestion et requête des données
Pour des informations sur l'utilisation du modèle `Repository` pour gérer et interroger des collections, consultez les [articles sur le Repository](/docs/advanced/repository/overview).
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

Par défaut, une colonne n'est pas triable à moins d'être explicitement activée. Pour autoriser le tri sur une colonne spécifique, utilisez la méthode `setSortable(true)` :

```java
table.getColumn("Age").setSortable(true);
```

## Tri multiple {#multi-sorting}

:::warning Tri de plusieurs colonnes désactivé par défaut dans webforJ `25.00`
Avant webforj `25.00`, les tableaux prenaient en charge le tri de plusieurs colonnes par défaut. À partir de la version `25.00`, ce comportement a changé : les développeurs doivent désormais activer explicitement le tri de plusieurs colonnes.
:::

Si un tri multiple est nécessaire, `setMultiSorting(true)` doit être appliqué au tableau. Cela permet aux utilisateurs de trier plusieurs colonnes successivement :

```java
table.setMultiSorting(true);
```

Avec le tri multiple activé, cliquer sur plusieurs en-têtes de colonne les triera séquentiellement. La priorité de tri est indiquée visuellement dans l'interface utilisateur du tableau.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Vous pouvez également définir la priorité de tri de manière programmatique pour le tri côté serveur. Utilisez `setSortIndex()` sur les colonnes que vous souhaitez trier, par ordre de priorité :

```java
// Ordre de tri côté serveur
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info L'ordre des colonnes a son importance
À moins que `setSortIndex()` ne soit utilisé, le tableau par défaut trie dans l'ordre dans lequel les colonnes sont déclarées.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Direction de tri {#sort-direction}

Il existe trois paramètres disponibles pour la direction dans laquelle une colonne peut être triée :

- `SortDirection.ASC` : Trie la colonne par ordre croissant.
- `SortDirection.DESC` : Trie la colonne par ordre décroissant.
- `SortDirection.NONE` : Aucun tri appliqué à la colonne.

Lorsqu'une colonne a le tri activé, vous verrez un ensemble d'indicateurs fléchés verticaux apparaître en haut de la colonne en question. Ces flèches permettent à l'utilisateur de basculer entre les différentes directions de tri.

Lorsque l'ordre croissant est sélectionné, un `^` sera affiché, tandis que l'ordre décroissant affichera un `v`.


## Tri côté client vs. côté serveur {#client-vs-server-side-sorting}

Le tri des données peut être largement catégorisé en deux approches principales : **Tri Client** et **Tri Serveur**.

### Tri client {#client-sorting}

Le tri client implique l'organisation et l'affichage des données directement dans l'interface utilisateur de l'application cliente. C'est le tri avec lequel les utilisateurs interagissent lorsqu'ils cliquent sur les en-têtes de colonne, influençant la représentation visuelle des données à l'écran.

Le développeur n'a aucun contrôle direct sur le tri côté client, mais il est plutôt déterminé par le type de colonne fourni en Java. Les types suivants sont actuellement pris en charge :

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Le tri côté client ne fonctionne pas lorsque seule une partie des données est disponible dans le client.
:::

### Tri serveur {#server-sorting}

Contrairement au tri côté client, le tri serveur consiste à organiser et à ranger les données sur le serveur avant de les transmettre au client. Cette approche est particulièrement bénéfique lorsqu'il s'agit de grands ensembles de données qui pourraient être peu pratiques à transférer entièrement au client.

Les développeurs ont plus de contrôle sur la logique du tri serveur. Cela permet la mise en œuvre d'algorithmes de tri complexes et d'optimisations, rendant cela adapté aux scénarios avec des données volumineuses. Cela garantit ensuite que le client reçoit des données déjà triées, minimisant le besoin d'un traitement côté client extensif.

:::info
Le tri serveur est une stratégie axée sur la performance pour traiter des ensembles de données qui dépassent les capacités d'un traitement efficace côté client, et est la méthode par défaut utilisée par le `Table`.
:::

### Nom de propriété de colonne {#column-property-name}

Par défaut, le `Table` utilise l'ID d'une colonne comme nom de propriété lors de la construction des critères de tri pour un dépôt backend. Lorsque l'ID d'affichage d'une colonne ne correspond pas à la propriété de données sous-jacente, ou lorsque la colonne affiche une valeur calculée, utilisez `setPropertyName()` pour indiquer explicitement au `Table` quelle propriété trier.

```java
// L'ID de la colonne est "Nom complet", mais la propriété backend est "fullName"
table.addColumn("Nom complet", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Le nom de propriété est transmis à l'`OrderCriteria` lorsqu'un événement de tri est déclenché, permettant aux dépôts backend tels que Spring Data JPA ou les adaptateurs REST de construire la clause `ORDER BY` correcte.

:::warning
Sans `setPropertyName()`, le `Table` revient à l'ID de colonne. Si cela ne correspond pas à une propriété backend valide, le tri échouera silencieusement ou renverra des données mal ordonnées.
:::

Les chemins de propriété imbriqués sont également pris en charge en utilisant la notation par points :

```java
table.addColumn("Ville", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Compareurs {#comparators}

Le composant `Column` permet aux développeurs d'utiliser des `Comparators` Java pour un tri dynamique et personnalisé. Un `Comparator` est un mécanisme utilisé pour ordonner deux objets de la même classe, même si cette classe est définie par l'utilisateur. Cette fonctionnalité offre aux développeurs la flexibilité de personnaliser la façon dont les données sont triées, offrant un contrôle accru sur le comportement de tri par défaut basé sur l'ordre naturel.

Pour tirer parti du tri par `Comparator` dans une `Column`, vous pouvez utiliser la méthode `setComparator()`. Cette méthode vous permet de définir une fonction `Comparator` personnalisée qui dicte la logique de tri.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Dans l'exemple ci-dessus, une fonction de comparateur personnalisée est spécifiée, qui prend deux éléments (a et b), et définit l'ordre de tri en fonction des valeurs entières analysées de l'attribut `Number`.

Utiliser des compareurs pour le tri de colonnes est particulièrement utile lors du traitement de valeurs non numériques. Ils sont également utiles pour mettre en œuvre des algorithmes de tri complexes.

:::info
Par défaut, le `Table` utilise le tri côté serveur et trie les valeurs non primitives en utilisant la méthode `toString()` de l'objet, les convertissant en leurs valeurs de chaîne puis les triant.
:::
