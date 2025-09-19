---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 3c9156ad5da204816bd4ce783003cbf7
---
Le tri permet aux utilisateurs d'organiser les données dans des colonnes par ordre, rendant l'information plus facile à lire et à analyser. Cela est utile lorsque les utilisateurs ont besoin de trouver rapidement les valeurs les plus élevées ou les plus basses dans une colonne particulière.

:::tip Gestion et interrogation des données
Pour des informations sur la façon d'utiliser le modèle `Repository` pour gérer et interroger des collections, voir les [articles de Repository](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Par défaut, une colonne n'est pas triable à moins d'être explicitement activée. Pour permettre le tri sur une colonne spécifique, utilisez la méthode `setSortable(true)` :

```java 
table.getColumn("Age").setSortable(true);
```

## Tri multiple {#multi-sorting}

:::warning Tri de plusieurs colonnes désactivé par défaut dans webforJ `25.00`
Avant webforj `25.00`, les tableaux prenaient en charge le tri de plusieurs colonnes par défaut. À partir de la version `25.00`, ce comportement a changé—les développeurs doivent maintenant activer explicitement le tri de plusieurs colonnes.
:::

Si le tri multiple est nécessaire, `setMultiSorting(true)` doit être appliqué au tableau. Cela permet aux utilisateurs de trier plusieurs colonnes en séquence :

```java
table.setMultiSorting(true);
```

Avec le tri multiple activé, cliquer sur plusieurs en-têtes de colonne les triera séquentiellement. La priorité de tri est visuellement indiquée dans l'interface utilisateur du tableau.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Vous pouvez également définir la priorité de tri de manière programmatique pour le tri côté serveur. Utilisez `setSortOrder()` sur les colonnes que vous souhaitez trier, dans l'ordre de priorité :

```java
// Ordre de tri côté serveur
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info L'ordre des colonnes est important
À moins que `setSortOrder()` ne soit utilisé, le tableau se base sur l'ordre dans lequel les colonnes sont déclarées pour le tri par défaut.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Direction de tri {#sort-direction}

Il y a trois réglages disponibles pour la direction dans laquelle une colonne peut être triée :

- `SortDirection.ASC` : Trie la colonne par ordre croissant.
- `SortDirection.DESC` : Trie la colonne par ordre décroissant.
- `SortDirection.NONE` : Aucun tri appliqué à la colonne.

Lorsqu'une colonne a le tri activé, vous verrez un ensemble d'indicateurs de flèches verticales apparaître en haut de la colonne triée. Ces flèches permettent à l'utilisateur de basculer entre les différentes directions de tri.

Lorsqu'un ordre croissant est sélectionné, un `^` sera affiché, tandis qu'un ordre décroissant affichera un `v`.

## Tri côté client vs côté serveur {#client-vs-server-side-sorting}

Le tri des données peut être largement catégorisé en deux approches principales : **Tri côté client** et **Tri côté serveur**.

### Tri côté client {#client-sorting}

Le tri côté client implique l'organisation et l'affichage des données directement dans l'interface utilisateur de l'application cliente. C'est le tri avec lequel les utilisateurs interagissent lorsqu'ils cliquent sur les en-têtes de colonne, influençant la représentation visuelle des données à l'écran.

Le développeur n'a pas de contrôle direct sur le tri côté client, mais cela est plutôt déterminé par le type de colonne fourni en Java. Les types suivants sont actuellement pris en charge :

- TEXTE
- NOMBRE
- BOOLEEN
- DATE
- DATETIME
- HEURE

:::info
Le tri côté client ne fonctionne pas lorsque seule une partie des données est disponible dans le client.
:::

### Tri côté serveur {#server-sorting}

Contrairement au tri côté client, le tri côté serveur implique l'organisation et le classement des données sur le serveur avant de les transmettre au client. Cette approche est particulièrement bénéfique lorsque vous traitez de grands ensembles de données qui pourraient être impraticables à transférer entièrement au client.

Les développeurs ont plus de contrôle sur la logique du tri côté serveur. Cela permet la mise en œuvre d'algorithmes de tri complexes et d'optimisations, ce qui le rend adapté aux scénarios avec de nombreuses données. Cela garantit ainsi que le client reçoit des données pré-triées, minimisant le besoin d'un traitement côté client étendu.

:::info
Le tri côté serveur est une stratégie axée sur la performance pour traiter des ensembles de données qui dépassent les capacités d'un traitement efficace côté client et est la méthode par défaut utilisée par le `Table`.
:::

#### Comparateurs {#comparators}

Le composant `Column` permet aux développeurs d'utiliser des `Comparators` Java pour un tri dynamique et personnalisé. Un `Comparator` est un mécanisme utilisé pour ordonner deux objets de la même classe, même si cette classe est définie par l'utilisateur. Cette fonctionnalité donne aux développeurs la flexibilité de personnaliser la façon dont les données sont triées, offrant un meilleur contrôle sur le comportement de tri par défaut basé sur l'ordre naturel.

Pour tirer parti du tri par `Comparator` dans une `Column`, vous pouvez utiliser la méthode `setComparator()`. Cette méthode vous permet de définir une fonction `Comparator` personnalisée qui dicte la logique de tri.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Dans l'exemple ci-dessus, un comparateur personnalisé est spécifié, qui prend deux éléments (a et b) et définit l'ordre de tri basé sur les valeurs entières analysées de l'attribut `Number`.

Utiliser des comparateurs pour le tri des colonnes est particulièrement utile lors de la gestion de valeurs non numériques. Ils sont également utiles pour mettre en œuvre des algorithmes de tri complexes.

:::info
Par défaut, le `Table` utilise le tri côté serveur et trie les valeurs non primitives en utilisant la méthode `toString()` de l'Object, les convertissant en leurs valeurs de chaîne, puis les triant.
:::
