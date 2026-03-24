---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 19fe294c57ad6b7d105039c25aedab11
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La classe `Table` utilise des instances de colonne pour définir et personnaliser comment les données sont affichées. Les colonnes contrôlent quelles données sont affichées, comment elles apparaissent et comment les utilisateurs peuvent interagir avec elles. Cette page couvre l'identité de la colonne, la présentation, la dimension, les interactions utilisateur et les événements associés.

## Identité de la colonne {#column-identity}

L'identité d'une colonne définit comment elle est reconnue dans la `Table`. Cela inclut son étiquette, la valeur qu'elle fournit et si elle est visible ou navigable.

### Étiquette {#label}

L'étiquette d'une colonne est son identifiant public, aidant à clarifier les données affichées.

Utilisez `setLabel()` pour définir ou modifier l'étiquette.

:::tip
Par défaut, l'étiquette sera la même que l'ID de la colonne.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

### Fournisseurs de valeur {#value-providers}

Un fournisseur de valeur est une fonction responsable de la traduction des données brutes provenant du jeu de données sous-jacent dans un format approprié pour l'affichage dans une colonne spécifique. La fonction, que vous définissez, prend une instance du type de données de la ligne (T) et retourne la valeur à mettre en avant dans la colonne associée pour cette ligne particulière.

Pour définir un fournisseur de valeur sur une colonne, utilisez l'une des méthodes `addColumn()` du composant `Table`.

Dans l'extrait suivant, une colonne tentera d'accéder aux données d'un objet JSON, les rendant visibles seulement si les données ne sont pas nulles.

```java
List<String> columnsList = List.of("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");
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

Il est possible de définir la visibilité d'une colonne, déterminant si elle sera affichée ou non dans la `Table`. Cela peut être utile, entre autres, pour déterminer si des informations sensibles doivent être affichées.

```java
table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);
```

### Navigable {#navigable}

L'attribut navigable détermine si les utilisateurs peuvent interagir avec une colonne lors de la navigation. Fixer `setSuppressNavigable()` à true restreint l'interaction de l'utilisateur avec la colonne, offrant une expérience en lecture seule.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Mise en page et formatage {#layout-and-formatting}

Après avoir établi l'identité d'une colonne, l'étape suivante consiste à contrôler comment son contenu apparaît pour les utilisateurs. Les options de mise en page, telles que l'alignement et le pinning, déterminent où les données se trouvent et comment elles restent visibles pendant que vous travaillez avec une `Table`.

### Alignement {#alignment}

Définir l'alignement d'une colonne vous permet de créer des tables organisées, ce qui peut aider les utilisateurs à identifier les différentes sections dans la `Table`.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Le composant `Table` prend en charge trois options d'alignement :

- `Column.Alignment.LEFT` : Convient pour des données textuelles ou descriptives où le maintien d'un flux vers la gauche est intuitif. Utile lorsque l'on souhaite mettre en avant le point de départ du contenu.
- `Column.Alignment.CENTER` : Les colonnes centrées sont idéales pour des valeurs plus courtes, comme une clé de caractère, un statut, ou tout autre chose ayant une présentation équilibrée.
- `Column.Alignment.RIGHT` : Envisagez d'utiliser une colonne alignée à droite pour des valeurs numériques qu'il est utile de parcourir rapidement, telles que des dates, des montants ou des pourcentages.

Dans l'exemple précédent, la dernière colonne pour `Cost` a été alignée à droite pour fournir une distinction visuelle plus évidente.

### Pincement {#pinning}

Le pincement de colonne est une fonctionnalité qui permet aux utilisateurs de fixer ou "pincer" une colonne à un côté spécifique de la `Table`. Cela est utile lorsque certaines colonnes, comme des identifiants ou des informations essentielles, doivent rester visibles lors du défilement horizontal à travers une table.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Il existe trois directions disponibles pour le pincement d'une colonne :

- `PinDirection.LEFT` : Pince la colonne du côté gauche.
- `PinDirection.RIGHT` : Pince la colonne du côté droit.
- `PinDirection.AUTO` : La colonne apparaît en fonction de l'ordre d'insertion.

Le pincement peut être défini par programmation, vous permettant de changer la direction du pincement en fonction des interactions des utilisateurs ou de la logique de l'application.

## Dimensionnement de la colonne <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Largeur fixe {#fixed-width}

Définissez une largeur exacte pour une colonne en utilisant la méthode `setWidth()`, spécifiant la largeur souhaitée en pixels :

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propriété de largeur définit la largeur initiale souhaitée pour la colonne. La manière dont cette largeur est utilisée dépend d'autres propriétés et du type de colonne :

- **Colonnes régulières** : Avec seulement la largeur définie, la colonne s'affiche à la largeur spécifiée mais peut se réduire proportionnellement lorsque le conteneur est trop petit. La largeur d'origine sert de largeur souhaitée, mais sans contraintes minimales explicites, la colonne peut s'afficher plus petite que la largeur définie.
- [**Colonnes épinglées**](#pinning) : Maintiennent toujours leur largeur exacte, ne participant jamais à la réduction responsive.
- [**Colonnes flexibles**](#flex-sizing) : Définir une largeur est incompatible avec le flex. Utilisez soit la largeur (fixe) soit le flex (proportionnel), pas les deux.

Si non spécifié, la colonne utilisera sa largeur estimée basée sur l'analyse du contenu des quelques premières lignes.

```java
// Obtenir la largeur actuelle
float currentWidth = column.getWidth();
```

### Largeur minimale {#minimum-width}

La méthode `setMinWidth()` vous permet de définir la largeur minimale d'une colonne. Si la largeur minimale n'est pas fournie, la `Table` calculera la largeur minimale en fonction du contenu de la colonne.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100f);
```

La valeur passée représente la largeur minimale en pixels.

La propriété de largeur minimale contrôle la plus petite largeur qu'une colonne peut avoir :

- **Colonnes régulières** : Avec seulement la largeur minimale définie, la colonne utilise la largeur minimale comme largeur souhaitée et minimale. Avec la largeur + largeur minimale, la colonne peut se rétrécir de la largeur jusqu'à la largeur minimale mais pas davantage.
- [**Colonnes épinglées**](#pinning) : Si seule la largeur minimale est définie (pas de largeur), elle devient la largeur fixe.
- [**Colonnes flexibles**](#flex-sizing) : Empêche la colonne de se rétrécir en dessous de cette largeur, même lorsque l'espace du conteneur est limité.

```java
// Obtenir la largeur minimale actuelle
float minWidth = column.getMinWidth();
```

### Largeur maximale {#maximum-width}

La méthode `setMaxWidth()` limite la largeur à laquelle une colonne peut croître, empêchant les colonnes avec un contenu long de devenir trop larges et de nuire à la lisibilité :

```java
table.addColumn("Description", Product::getDescription)
  .setMinWidth(100f)
  .setMaxWidth(300f);
```

La propriété `maxWidth` limite la croissance de la colonne pour tous les types de colonnes et ne sera jamais dépassée indépendamment du contenu, de la taille du conteneur ou des paramètres flex.

```java
// Obtenir la largeur maximale actuelle
float maxWidth = column.getMaxWidth();
```

### Dimensionnement flex {#flex-sizing}

La méthode `setFlex()` permet un dimensionnement proportionnel des colonnes, faisant en sorte que les colonnes partagent l'espace disponible après que les colonnes à largeur fixe ont été allouées :

```java
// La colonne de titre obtient deux fois l'espace de la colonne d'artiste
table.addColumn("Title", Product::getTitle).setFlex(2f);
table.addColumn("Artist", Product::getArtist).setFlex(1f);
```

Comportements clés du flex :

- **Valeur du flex** : Détermine la proportion de l'espace disponible. Une colonne avec flex=2 obtient deux fois l'espace d'une colonne avec flex=1.
- **Incompatible avec la largeur** : Ne peut pas être utilisée en même temps que la propriété de largeur. Lorsque le flex est supérieur à zéro, il prend effet sur le paramètre de largeur.
- **Respecte les contraintes** : Fonctionne avec des contraintes de largeur minimale/largeur maximale. Sans largeur minimale, les colonnes flex peuvent se réduire à 0.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnflexsizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnFlexSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

:::info Largeur vs Flex
Les propriétés de largeur et de flex sont mutuellement exclusives. Définir l'une efface automatiquement l'autre. Utilisez la largeur pour un contrôle précis ou le flex pour un comportement réactif.
:::

### Dimensionnement automatique {#automatic-sizing}

Au-delà des paramètres manuels de largeur et de flex, les colonnes peuvent également être dimensionnées automatiquement. Le dimensionnement automatique permet à la `Table` de déterminer les largeurs optimales soit en analysant le contenu, soit en distribuant l'espace proportionnellement.

#### Dimensionnement automatique basé sur le contenu {#content-based-auto-sizing}

Dimensionnez automatiquement les colonnes en fonction de leur contenu. La `Table` analyse les données dans chaque colonne et calcule la largeur optimale pour afficher le contenu sans troncature.

```java
// Auto-dimensionner toutes les colonnes pour s'adapter au contenu
table.setColumnsToAutoSize().thenAccept(c -> {
  // Dimensionnement terminé - les colonnes s'adaptent maintenant à leur contenu
});

// Auto-dimensionner une colonne spécifique
table.setColumnToAutoSize("description");
```

#### Ajustement proportionnel automatique {#proportional-auto-fit}

Distribuez toutes les colonnes proportionnellement sur la largeur disponible de la `Table`. Cette opération définit chaque colonne à flex=1, leur permettant de partager la largeur totale de la `Table` de manière égale, quelle que soit la longueur de leur contenu. Les colonnes s'étendront ou se contracteront pour remplir exactement les dimensions de la `Table` sans espace restant.

```java
// Ajuster les colonnes à la largeur de la table (équivalent à définir flex=1 sur toutes)
table.setColumnsToAutoFit().thenAccept(ignored -> {
  // Toutes les colonnes partagent maintenant l'espace de manière égale
});
```

:::info Opérations Asynchrones
Les méthodes de dimensionnement automatique retournent `PendingResult<Void>` car elles nécessitent des calculs côté client. Utilisez `thenAccept()` pour exécuter du code après la fin du dimensionnement. Si vous n'avez pas besoin d'attendre la fin, vous pouvez appeler les méthodes sans `thenAccept()`.
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## Interactions utilisateur <DocChip chip='since' label='25.03' /> {#user-interactions}

### Redimensionnement de colonne {#column-resizing}

Le redimensionnement de colonne donne aux utilisateurs le contrôle sur la quantité d'espace que chaque colonne occupe en faisant glisser les bordures des colonnes.

Vous pouvez contrôler le comportement de redimensionnement des colonnes individuelles lors de la construction de votre table :

```java
// Activer le redimensionnement par l'utilisateur pour cette colonne
table.addColumn("Title", Product::getTitle).setResizable(true);

// Désactiver le redimensionnement
table.addColumn("ID", Product::getId).setResizable(false);

// Vérifier l'état actuel
boolean canResize = column.isResizable();
```

Pour les tables où vous souhaitez un comportement cohérent sur plusieurs colonnes, utilisez les méthodes de configuration en bloc :

```java
// Rendre toutes les colonnes existantes redimensionnables
table.setColumnsToResizable(true);

// Verrouiller toutes les colonnes existantes contre le redimensionnement
table.setColumnsToResizable(false);
```

### Réorganisation de colonnes {#column-reordering}

La réorganisation des colonnes permet aux utilisateurs de faire glisser et de déposer des colonnes dans l'ordre qu'ils préfèrent, personnalisant ainsi la mise en page de la `Table` pour leur flux de travail.

Configurez les autorisations de mouvement des colonnes lors de la création de votre table :

```java
// Autoriser les utilisateurs à déplacer cette colonne
table.addColumn("Title", Product::getTitle).setMovable(true);

// Empêcher le mouvement de la colonne (utile pour les colonnes d'ID ou d'action)
table.addColumn("ID", Product::getId).setMovable(false);

// Vérifier l'état actuel
boolean canMove = column.isMovable();
```

Appliquez les paramètres de mouvement à plusieurs colonnes simultanément :

```java
// Activer la réorganisation pour toutes les colonnes existantes
table.setColumnsToMovable(true);

// Désactiver la réorganisation pour toutes les colonnes existantes  
table.setColumnsToMovable(false);
```

:::note Opérations en bloc
Les méthodes `setColumnsToResizable()` et `setColumnsToMovable()` n'affectent que les colonnes existantes au moment de l'invocation. Elles ne définissent pas de valeurs par défaut pour les colonnes futures.
:::

### Mouvement de colonne programmatique {#programmatic-column-movement} 

En plus du glisser-déposer, vous pouvez également repositionner les colonnes de manière programmatique par index ou ID. Gardez à l'esprit que l'index est basé uniquement sur les colonnes visibles ; toute colonne masquée est ignorée lors du calcul des positions.

```java
// Déplacer la colonne à la première position
table.moveColumn("title", 0);

// Déplacer la colonne à la dernière position
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Mouvement asynchrone avec rappel
table.moveColumn("description", 2).thenAccept(c -> {
  // Colonne déplacée avec succès
});
```

## Gestion des événements {#event-handling}

Le composant `Table` émet des événements lorsque les utilisateurs interagissent avec les colonnes, vous permettant de répondre aux changements de mise en page et de sauvegarder les préférences utilisateur.

Événements pris en charge :

- `TableColumnResizeEvent` : Déclenché lorsqu'un utilisateur redimensionne une colonne en faisant glisser sa bordure.
- `TableColumnMoveEvent` : Déclenché lorsqu'un utilisateur réorganise une colonne en faisant glisser son en-tête.

Vous pouvez attacher des écouteurs au `Table` pour répondre lorsque les utilisateurs modifient la mise en page de la table.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Traiter l'événement de redimensionnement de colonne
  // Accéder à : event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Traiter l'événement de mouvement de colonne  
  // Accéder à : event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
