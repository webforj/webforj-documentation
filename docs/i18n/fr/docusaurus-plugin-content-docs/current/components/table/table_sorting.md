---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: a51ea10e855e94a24cb6e74d8f774abe
---
Le tri permet aux utilisateurs d'organiser les données dans les colonnes par ordre, facilitant ainsi la lecture et l'analyse des informations. Cela est utile lorsque les utilisateurs ont besoin de trouver rapidement les valeurs les plus élevées ou les plus basses dans une colonne particulière.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Par défaut, une colonne n'est pas triable à moins qu'elle ne soit explicitement activée. Pour permettre le tri sur une colonne spécifique, utilisez la méthode `setSortable(true)` :

```java 
table.getColumn("Age").setSortable(true);
```

## Tri multiple {#multi-sorting}

:::warning Le tri de plusieurs colonnes est désactivé par défaut dans webforJ `25.00`
Avant webforj `25.00`, les tables prêtaient support au tri de plusieurs colonnes par défaut. À partir de la version `25.00`, ce comportement a changé : les développeurs doivent désormais activer explicitement le tri de plusieurs colonnes.
:::

Si un tri multiple est nécessaire, `setMultiSorting(true)` doit être appliqué à la table. Cela permet aux utilisateurs de trier plusieurs colonnes successivement :

```java
table.setMultiSorting(true);
```

Avec le tri multiple activé, cliquer sur plusieurs en-têtes de colonnes les triera successivement. La priorité de tri est visuellement indiquée dans l'interface utilisateur de la table.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Vous pouvez également définir la priorité de tri programmatique pour le tri côté serveur. Utilisez `setSortOrder()` sur les colonnes que vous souhaitez trier, par ordre de priorité :

```java
// Ordre de tri côté serveur
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info L'ordre des colonnes est important
À moins que `setSortOrder()` ne soit utilisé, la table par défaut trie dans l'ordre dans lequel les colonnes sont déclarées.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Direction de tri {#sort-direction}

Il existe trois paramètres disponibles pour la direction dans laquelle une colonne peut être triée :

- `SortDirection.ASC` : Trie la colonne dans l'ordre croissant.
- `SortDirection.DESC` : Trie la colonne dans l'ordre décroissant.
- `SortDirection.NONE` : Aucun tri appliqué à la colonne.

Lorsque le tri est activé sur une colonne, un ensemble d'indicateurs en forme de flèche verticale apparaîtra en haut de la colonne en question. Ces flèches permettent à l'utilisateur de basculer entre les différentes directions de tri.

Lorsque l'ordre croissant est sélectionné, un `^` sera affiché, tandis que l'ordre décroissant affichera un `v`.


## Tri côté client vs côté serveur {#client-vs-server-side-sorting}

Le tri des données peut être largement catégorisé en deux approches principales : **Tri Côté Client** et **Tri Côté Serveur**.

### Tri côté client {#client-sorting}

Le tri côté client implique d'organiser et d'afficher les données directement dans l'interface utilisateur de l'application cliente. C'est le tri avec lequel les utilisateurs interagissent lorsqu'ils cliquent sur les en-têtes des colonnes, influençant ainsi la représentation visuelle des données sur l'écran.

Le développeur n'a pas de contrôle direct sur le tri côté client, mais celui-ci est plutôt déterminé par le type de colonne fourni en Java. Les types suivants sont actuellement pris en charge :

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Le tri côté client ne fonctionne pas lorsque seule une partie des données est disponible dans le client.
:::

### Tri côté serveur {#server-sorting}

Contrairement au tri côté client, le tri côté serveur consiste à organiser et à trier les données sur le serveur avant de les transmettre au client. Cette approche est particulièrement bénéfique lorsqu'il s'agit de grands ensembles de données qui pourraient être peu pratiques à transférer intégralement au client.

Les développeurs ont un meilleur contrôle sur la logique du tri côté serveur. Cela permet la mise en œuvre d'algorithmes de tri complexes et d'optimisations, rendant cette méthode adaptée aux scénarios avec des données étendues. Cela garantit alors que le client reçoit des données déjà triées, minimisant ainsi le besoin d'un traitement client intensif.


:::info
Le tri côté serveur est une stratégie axée sur la performance pour traiter des ensembles de données qui dépassent les capacités de traitement efficace côté client, et est la méthode par défaut utilisée par le `Table`.
:::

#### Comparateurs {#comparators}

Le composant `Column` permet aux développeurs d'utiliser les `Comparators` Java pour un tri dynamique et personnalisé. Un `Comparator` est un mécanisme utilisé pour ordonner deux objets de la même classe, même si cette classe est définie par l'utilisateur. Cette fonctionnalité offre aux développeurs la flexibilité de personnaliser la manière dont les données sont triées, fournissant un contrôle supérieur sur le comportement de tri par défaut basé sur l'ordre naturel.

Pour tirer parti du tri par `Comparator` dans une `Column`, vous pouvez utiliser la méthode `setComparator()`. Cette méthode vous permet de définir une fonction `Comparator` personnalisée qui dicte la logique de tri.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Dans l'exemple ci-dessus, une fonction de comparateur personnalisée est spécifiée, qui prend deux éléments (a et b), et définit l'ordre de tri en fonction des valeurs entières analysées de l'attribut `Number`.

Utiliser des Comparators pour le tri des colonnes est particulièrement utile lors de la manipulation de valeurs non numériques. Ils sont également utiles pour mettre en œuvre des algorithmes de tri complexes.

:::info
Par défaut, le `Table` utilise le tri côté serveur, et trie les valeurs non primitives en utilisant la méthode `toString()` de l'objet, les convertissant en leurs valeurs de chaîne, puis les triant.
:::
