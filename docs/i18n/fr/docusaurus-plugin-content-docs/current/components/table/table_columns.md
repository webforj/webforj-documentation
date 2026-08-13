---
sidebar_position: 5
title: Columns
slug: columns
description: >-
  Define Table columns with labels, value providers, visibility, navigability,
  sizing, and user-interaction events.
_i18n_hash: 5ca9d9959c258db42780e52dad8c463d
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La classe `Table` utilise des instances de colonnes pour définir et personnaliser l'affichage des données. Les colonnes contrôlent quelles données sont affichées, leur apparence et comment les utilisateurs peuvent interagir avec elles. Cette page traite de l'identité des colonnes, de leur présentation, de leur dimensionnement, des interactions utilisateur et des événements associés.

## Identité de la colonne {#column-identity}

L'identité d'une colonne définit comment elle est reconnue dans la `Table`. Cela inclut son étiquette, la valeur qu'elle fournit et si elle est visible ou navigable.

### Étiquette {#label}

L'étiquette d'une colonne est son identifiant public, aidant à clarifier les données affichées.

Utilisez `setLabel()` pour définir ou modifier l'étiquette.

:::tip
Par défaut, l'étiquette sera identique à l'ID de la colonne.
:::

```java
table.addColumn("ID produit", Product::getProductId).setLabel("ID");
```

### Fournisseurs de valeur {#value-providers}

Un fournisseur de valeur est une fonction responsable de traduire les données brutes de l'ensemble de données sous-jacent en un format adapté à l'affichage dans une colonne spécifique. La fonction, que vous définissez, prend une instance du type de données de ligne (T) et renvoie la valeur à présenter dans la colonne associée pour cette ligne particulière.

Pour définir un fournisseur de valeur sur une colonne, utilisez l'une des méthodes `addColumn()` du composant `Table`.

Dans l'extrait suivant, une colonne tentera d'accéder aux données d'un objet JSON, ne les rendant que si les données ne sont pas nulles.

```java
List<String> columnsList = List.of("athlète", "âge", "pays", "année", "sport", "or", "argent", "bronze", "total");
for (String column : columnsList) {
  table.addColumn(column, (JsonObject person) -> {
    JsonElement element = person.get(column);
    if (!element.isJsonNull()) {
      return element.getAsString();
    }
    return "";
  });
}
```

### Visibilité {#visibility}

Il est possible de définir la visibilité d'une colonne, déterminant si elle sera affichée ou non dans la `Table`. Cela peut être utile, entre autres, pour déterminer s'il faut afficher des informations sensibles.

```java
table.addColumn("Carte de crédit", Customer::getCreditCardNumber).setHidden(true);
```

### Navigable {#navigable}

L'attribut navigable détermine si les utilisateurs peuvent interagir avec une colonne lors de la navigation. En définissant `setSuppressNavigable()` sur true, vous restreignez l'interaction des utilisateurs avec la colonne, fournissant une expérience en lecture seule.

```java
table.addColumn("Colonne en lecture seule", Product::getDescription).setSuppressNavigable(true);
```

## Mise en page et formatage {#layout-and-formatting}

Après avoir établi l'identité d'une colonne, la prochaine étape consiste à contrôler l'apparence de son contenu pour les utilisateurs. Les options de mise en page telles que l'alignement et le pinning déterminent où se trouvent les données et comment elles restent visibles pendant que vous travaillez avec une `Table`.

### Alignement {#alignment}

Définir l'alignement d'une colonne vous permet de créer des tables organisées, ce qui peut aider les utilisateurs à identifier les différentes sections de la `Table`.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnalignment'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Le composant `Table` prend en charge trois options d'alignement :

- `Column.Alignment.LEFT`: Convient pour des données textuelles ou descriptives où maintenir un flux vers la gauche est intuitif. Utile pour souligner le point de départ du contenu.
- `Column.Alignment.CENTER`: Les colonnes centrées sont idéales pour des valeurs plus courtes, comme une clé de caractère, un statut, ou tout autre élément ayant une présentation équilibrée.
- `Column.Alignment.RIGHT`: Envisagez d'utiliser une colonne alignée à droite pour des valeurs numériques qui sont utiles pour une consultation rapide, telles que des dates, des montants et des pourcentages.

Dans l'exemple précédent, la colonne finale pour `Coût` a été alignée à droite pour fournir une distinction visuelle plus évidente.

### Pinned {#pinning}

Le pinning des colonnes est une fonctionnalité qui permet aux utilisateurs de fixer une colonne à un côté spécifique de la `Table`. Cela est utile lorsque certaines colonnes, telles que les identifiants ou des informations essentielles, doivent rester visibles lors du défilement horizontal dans une table.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnpinning'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Il existe trois directions disponibles pour le pinning d'une colonne :

- `PinDirection.LEFT`: Fixe la colonne sur le côté gauche.
- `PinDirection.RIGHT`: Fixe la colonne sur le côté droit.
- `PinDirection.AUTO`: La colonne apparaît en fonction de l'ordre d'insertion.

Le pinning peut être défini par programme, vous permettant de modifier la direction du pin en fonction des interactions utilisateur ou de la logique de l'application.

## Dimensionnement de la colonne <DocChip chip='since' label='25.03' /> {#column-sizing}

### Largeur fixe {#fixed-width}

Définissez une largeur exacte pour une colonne à l'aide de la méthode `setWidth()`, en spécifiant la largeur souhaitée en pixels :

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propriété width définit la largeur initiale souhaitée pour la colonne. La façon dont cette largeur est utilisée dépend d'autres propriétés et du type de colonne :

- **Colonnes régulières** : Avec uniquement la largeur définie, la colonne s'affiche à la largeur spécifiée mais peut rétrécir proportionnellement lorsque le conteneur est trop petit. La largeur originale sert de largeur souhaitée, mais sans contraintes minimum explicites, la colonne peut s'afficher plus petite que la largeur définie.
- [**Colonnes épinglées**](#pinning) : Conservent toujours leur largeur exacte, ne participant jamais au rétrécissement réactif.
- [**Colonnes flex**](#flex-sizing) : Définir la largeur est incompatible avec flex. Utilisez soit la largeur (fixe) soit le flex (proportionnel), pas les deux.

Si elle n'est pas spécifiée, la colonne utilisera sa largeur estimée en fonction de l'analyse du contenu des premières lignes.

```java
// Obtenir la largeur actuelle
float currentWidth = column.getWidth();
```

### Largeur minimum {#minimum-width}

La méthode `setMinWidth()` vous permet de définir la largeur minimale d'une colonne. Si la largeur minimale n'est pas fournie, la `Table` calculera la largeur minimale en fonction du contenu de la colonne.

```java
table.addColumn("Prix", Product::getPrice).setMinWidth(100f);
```

La valeur passée représente la largeur minimale en pixels.

La propriété de largeur minimale contrôle la plus petite largeur qu'une colonne peut avoir :

- **Colonnes régulières** : Avec uniquement la largeur minimale définie, la colonne utilise la largeur minimale comme largeur désirée et minimale. Avec largeur + largeur minimale, la colonne peut rétrécir de la largeur jusqu'à la largeur minimale mais pas plus.
- [**Colonnes épinglées**](#pinning) : Si seule la largeur minimale est définie (sans largeur), elle devient la largeur fixe.
- [**Colonnes flex**](#flex-sizing) : Empêche la colonne de rétrécir en dessous de cette largeur même lorsque l'espace dans le conteneur est limité.

```java
// Obtenir la largeur minimale actuelle
float minWidth = column.getMinWidth();
```

### Largeur maximum {#maximum-width}

La méthode `setMaxWidth()` limite la largeur maximale d'une colonne, empêchant les colonnes avec un long contenu de devenir trop larges et d'affecter la lisibilité :

```java
table.addColumn("Description", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

La propriété `maxWidth` limite la croissance de la colonne pour tous les types de colonnes et ne sera jamais dépassée, quel que soit le contenu, la taille du conteneur ou les réglages de flex.

```java
// Obtenir la largeur maximum actuelle
float maxWidth = column.getMaxWidth();
```

### Dimensionnement flexible {#flex-sizing}

La méthode `setFlex()` permet de dimensionner des colonnes de manière proportionnelle, faisant partager aux colonnes l'espace disponible après que les colonnes à largeur fixe aient été allouées :

```java
// La colonne Titre obtient deux fois l'espace de la colonne Artiste
table.addColumn("Titre", Product::getTitle).setFlex(2f);
table.addColumn("Artiste", Product::getArtist).setFlex(1f);
```

Comportements clés du flex :

- **Valeur de flex** : Détermine la proportion de l'espace disponible. Une colonne avec flex=2 obtient deux fois l'espace d'une colonne avec flex=1.
- **Incompatible avec la largeur** : Ne peut pas être utilisée en même temps que la propriété width. Lorsque flex est supérieur à zéro, il prend effet sur le réglage de la largeur.
- **Respecte les contraintes** : Fonctionne avec les contraintes de largeur minimale/maximum. Sans largeur minimale, les colonnes flex peuvent rétrécir jusqu'à 0.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnflexsizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

:::info Largeur vs Flex
Les propriétés de largeur et de flex sont mutuellement exclusives. En définissant l'une, l'autre est automatiquement effacée. Utilisez la largeur pour un contrôle précis ou le flex pour un comportement réactif.
:::

### Dimensionnement automatique {#automatic-sizing}

Au-delà des réglages manuels de largeur et de flex, les colonnes peuvent également être dimensionnées automatiquement. Le dimensionnement automatique permet à la `Table` de déterminer les largeurs optimales soit en analysant le contenu, soit en répartissant l'espace de manière proportionnelle.

#### Dimensionnement automatique basé sur le contenu {#content-based-auto-sizing}

Dimensionnez automatiquement les colonnes en fonction de leur contenu. La `Table` analyse les données de chaque colonne et calcule la largeur optimale pour afficher le contenu sans troncature.

```java
// Dimensionner automatiquement toutes les colonnes pour s'adapter au contenu
table.setColumnsToAutoSize().thenAccept(c -> {
  // Dimensionnement terminé - les colonnes s'adaptent maintenant à leur contenu
});

// Dimensionner automatiquement une colonne spécifique
table.setColumnToAutoSize("description");
```

#### Ajustement proportionnel automatique {#proportional-auto-fit}

Répartissez toutes les colonnes de manière proportionnelle sur la largeur disponible de la `Table`. Cette opération définit chaque colonne sur flex=1, les faisant partager uniformément la largeur totale de la `Table`, quelle que soit la longueur de leur contenu. Les colonnes s'étendront ou se contracteront pour remplir exactement les dimensions de la `Table` sans espace restant.

```java
// Ajuster les colonnes à la largeur de la table (équivalent à définir flex=1 sur toutes)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Toutes les colonnes partagent maintenant l'espace également
});
```

:::info Opérations asynchrones
Les méthodes de dimensionnement automatique renvoient `PendingResult<Void>` car elles nécessitent des calculs côté client. Utilisez `thenAccept()` pour exécuter du code après l'achèvement du dimensionnement. Si vous n'avez pas besoin d'attendre la fin, vous pouvez appeler les méthodes sans `thenAccept()`.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumnautosizing'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='550px'
/>
<!-- vale on -->

## Interactions utilisateur <DocChip chip='since' label='25.03' /> {#user-interactions}

### Redimensionnement des colonnes {#column-resizing}

Le redimensionnement des colonnes donne aux utilisateurs le contrôle sur l'espace que chaque colonne occupe en faisant glisser les bords de la colonne.

Vous pouvez contrôler le comportement de redimensionnement sur des colonnes individuelles lors de la construction de votre table :

```java
// Activer le redimensionnement par l'utilisateur pour cette colonne
table.addColumn("Titre", Product::getTitle).setResizable(true);

// Désactiver le redimensionnement
table.addColumn("ID", Product::getId).setResizable(false);

// Vérifier l'état actuel
boolean canResize = column.isResizable();
```

Pour des tables où vous souhaitez un comportement cohérent sur plusieurs colonnes, utilisez les méthodes de configuration groupée :

```java
// Rendre toutes les colonnes existantes redimensionnables
table.setColumnsToResizable(true);

// Bloquer toutes les colonnes existantes du redimensionnement
table.setColumnsToResizable(false);
```

### Réorganisation des colonnes {#column-reordering}

La réorganisation des colonnes permet aux utilisateurs de glisser-déposer les colonnes dans leur ordre préféré, personnalisant la mise en page de la `Table` pour leur flux de travail.

Configurez les autorisations de mouvement des colonnes lors de la configuration de votre table :

```java
// Autoriser les utilisateurs à déplacer cette colonne
table.addColumn("Titre", Product::getTitle).setMovable(true);

// Empêcher le mouvement de la colonne (utile pour les colonnes ID ou action)
table.addColumn("ID", Product::getId).setMovable(false);

// Vérifier l'état actuel
boolean canMove = column.isMovable();
```

Appliquez les réglages de mouvement à plusieurs colonnes simultanément :

```java
// Activer la réorganisation pour toutes les colonnes existantes
table.setColumnsToMovable(true);

// Désactiver la réorganisation pour toutes les colonnes existantes
table.setColumnsToMovable(false);
```

:::note Opérations groupées
Les méthodes `setColumnsToResizable()` et `setColumnsToMovable()` n'affectent que les colonnes existantes au moment de l'invocation. Elles ne définissent pas de valeurs par défaut pour les colonnes futures.
:::

### Mouvement de colonne programmatique {#programmatic-column-movement}

En plus du glisser-déposer, vous pouvez également repositionner des colonnes par programme en utilisant l'index ou l'ID. Gardez à l'esprit que l'index est basé uniquement sur les colonnes visibles ; toutes les colonnes cachées sont ignorées lors du calcul des positions.

```java
// Déplacer la colonne à la première position
table.moveColumn("titre", 0);

// Déplacer la colonne à la dernière position
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Mouvement asynchrone avec rappel
table.moveColumn("description", 2).thenAccept(c -> {
  // Colonne déplacée avec succès
});
```

## Gestion des événements {#event-handling}

Le composant `Table` émet des événements lorsque les utilisateurs interagissent avec des colonnes, vous permettant de répondre aux modifications de mise en page et de sauvegarder les préférences des utilisateurs.

Événements pris en charge :

- `TableColumnResizeEvent` : Émis lorsqu'un utilisateur redimensionne une colonne en faisant glisser sa bordure.
- `TableColumnMoveEvent` : Émis lorsqu'un utilisateur réorganise une colonne en faisant glisser son en-tête.

Vous pouvez attacher des écouteurs au `Table` pour répondre lorsque les utilisateurs modifient la mise en page de la table.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Gérer l'événement de redimensionnement de colonne
  // Accéder à : event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Gérer l'événement de mouvement de colonne
  // Accéder à : event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
