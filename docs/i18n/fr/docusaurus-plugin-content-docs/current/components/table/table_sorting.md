---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 16c35fa416c4ebba3b680deb2d8925ef
---
Le tri permet aux utilisateurs d'organiser les données dans des colonnes par ordre, rendant l'information plus facile à lire et à analyser. Cela est utile lorsque les utilisateurs doivent rapidement trouver les valeurs les plus élevées ou les plus basses dans une colonne particulière.

:::tip Gestion et requête des données
Pour des informations sur la façon d'utiliser le modèle `Repository` pour gérer et interroger des collections, consultez les [articles sur le Repository](/docs/advanced/repository/overview).
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

Par défaut, une colonne n'est pas triable à moins d'être explicitement activée. Pour permettre le tri sur une colonne spécifique, utilisez la méthode `setSortable(true)` :

```java 
table.getColumn("Age").setSortable(true);
```

## Tri multiple {#multi-sorting}

:::warning Tri de plusieurs colonnes désactivé par défaut dans webforJ `25.00`
Avant webforj `25.00`, les tableaux prenaient en charge le tri de plusieurs colonnes par défaut. À partir de la version `25.00`, ce comportement a changé : les développeurs doivent maintenant activer explicitement le tri de plusieurs colonnes.
:::

Si un tri multiple est nécessaire, `setMultiSorting(true)` doit être appliqué au tableau. Cela permet aux utilisateurs de trier plusieurs colonnes en séquence :

```java
table.setMultiSorting(true);
```

Avec le tri multiple activé, cliquer sur plusieurs en-têtes de colonne les triera de manière séquentielle. La priorité de tri est visuellement indiquée dans l'interface utilisateur du tableau.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Vous pouvez également définir la priorité de tri de manière programmatique pour le tri côté serveur. Utilisez `setSortOrder()` sur les colonnes que vous souhaitez trier, dans l'ordre de priorité :

```java
// Ordre de tri côté serveur
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info L'ordre des colonnes est important
Sauf si `setSortOrder()` est utilisé, le tableau par défaut trie selon l'ordre dans lequel les colonnes sont déclarées.
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

Lorsqu'une colonne a le tri activé, vous verrez apparaître un ensemble d'indicateurs fléchés verticaux en haut de la colonne concernée. Ces flèches permettent à l'utilisateur de basculer entre les différentes directions de tri.

Lorsque l'ordre croissant est sélectionné, un `^` sera affiché, tandis que l'ordre décroissant affichera un `v`.


## Tri côté client vs. côté serveur {#client-vs-server-side-sorting}

Le tri des données peut être largement catégorisé en deux approches principales : **Tri côté client** et **Tri côté serveur**.

### Tri côté client {#client-sorting}

Le tri côté client consiste à organiser et afficher les données directement dans l'interface utilisateur de l'application cliente. C'est le tri avec lequel les utilisateurs interagissent lorsqu'ils cliquent sur les en-têtes de colonne, influençant la représentation visuelle des données à l'écran.

Le développeur n'a pas de contrôle direct sur le tri côté client, mais celui-ci est déterminé par le type de colonne fourni en Java. Les types suivants sont actuellement pris en charge :

- TEXTE
- NOMBRE
- BOOLEAN
- DATE
- DATETIME
- HEURE

:::info
Le tri côté client ne fonctionne pas lorsque seule une partie des données est disponible dans le client.
:::

### Tri côté serveur {#server-sorting}

Contrairement au tri côté client, le tri côté serveur consiste à organiser et classer les données sur le serveur avant de les transmettre au client. Cette approche est particulièrement bénéfique lorsqu'il s'agit de grands ensembles de données qui pourraient être impraticables à transférer entièrement au client.

Les développeurs ont plus de contrôle sur la logique du tri côté serveur. Cela permet la mise en œuvre d'algorithmes de tri complexes et d'optimisations, rendant cela adapté aux scénarios avec des données étendues. Cela garantit alors que le client reçoit des données déjà triées, minimisant le besoin d'un traitement intensif côté client.


:::info
Le tri côté serveur est une stratégie orientée performance pour traiter des ensembles de données qui dépassent les capacités d'un traitement efficace côté client, et est la méthode par défaut utilisée par le `Table`.
:::

### Nom de propriété de colonne {#column-property-name}

Par défaut, le `Table` utilise l'ID d'une colonne comme nom de propriété lors de la construction des critères de tri pour un dépôt backend. Lorsque l'ID d'affichage de la colonne ne correspond pas à la propriété de données sous-jacente, ou lorsque la colonne affiche une valeur calculée, utilisez `setPropertyName()` pour indiquer explicitement au `Table` quelle propriété trier.

```java
// L'ID de colonne est "Full Name", mais la propriété backend est "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Le nom de propriété est transmis à l'`OrderCriteria` lorsqu'un événement de tri se déclenche, permettant aux dépôts backend tels que Spring Data JPA ou les adaptateurs REST de construire la bonne clause `ORDER BY`.

:::warning
Sans `setPropertyName()`, le `Table` utilise l'ID de colonne par défaut. Si cela ne correspond pas à une propriété backend valide, le tri échouera silencieusement ou renverra des données mal triées.
:::

Les chemins de propriété imbriqués sont également pris en charge en utilisant la notation par points :

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparateurs {#comparators}

Le composant `Column` permet aux développeurs d'utiliser des `Comparators` Java pour un tri dynamique et personnalisé. Un `Comparator` est un mécanisme utilisé pour ordonner deux objets de la même classe, même si cette classe est définie par l'utilisateur. Cette fonctionnalité offre aux développeurs la flexibilité de personnaliser la manière dont les données sont triées, fournissant un contrôle accru sur le comportement de tri par défaut basé sur l'ordre naturel.

Pour tirer parti du tri avec `Comparator` dans une `Column`, vous pouvez utiliser la méthode `setComparator()`. Cette méthode vous permet de définir une fonction de `Comparator` personnalisée qui dicte la logique de tri.

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

Utiliser des comparateurs pour le tri des colonnes est particulièrement utile lors de la gestion de valeurs non numériques. Ils sont également utiles pour la mise en œuvre d'algorithmes de tri complexes.

:::info
Par défaut, le `Table` utilise le tri côté serveur, et trie les valeurs non primitives en utilisant la méthode `toString()` de l'objet, les convertissant en leurs valeurs de chaîne et les triant ensuite.
:::
