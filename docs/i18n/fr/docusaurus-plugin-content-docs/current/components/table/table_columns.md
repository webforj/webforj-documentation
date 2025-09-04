---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 6be8663364f0b321c603eb8b870cc104
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La classe `Table` utilise des instances de colonne pour définir et personnaliser la manière dont les données sont affichées. Les colonnes contrôlent quelles données sont montrées, leur apparence et comment les utilisateurs peuvent interagir avec elles. Cette page couvre l'identité de la colonne, la présentation, la taille, les interactions utilisateur et les événements associés.

## Identité de la colonne {#column-identity}

L'identité d'une colonne définit comment elle est reconnue dans la `Table`. Cela inclut son label, la valeur qu'elle fournit et si elle est visible ou navigable.

### Label {#label}

Le label d'une colonne est son identifiant public, aidant à clarifier les données affichées.

Utilisez `setLabel()` pour définir ou modifier le label.

:::tip
Par défaut, le label sera le même que l'ID de la colonne.
:::

```java
table.addColumn("ID du produit", Product::getProductId).setLabel("ID");
```

### Fournisseurs de valeur {#value-providers}

Un fournisseur de valeur est une fonction responsable de traduire les données brutes de l'ensemble de données sous-jacent en un format approprié pour l'affichage dans une colonne spécifique. La fonction, que vous définissez, prend une instance du type de données de la ligne (T) et retourne la valeur à être présentée dans la colonne associée pour cette ligne particulière.

Pour définir un fournisseur de valeur sur une colonne, utilisez l'une des méthodes `addColumn()` du composant `Table`.

Dans l'extrait suivant, une colonne tentera d'accéder à des données à partir d'un objet JSON, les rendant visibles seulement si les données ne sont pas nulles.

```java
    List<String> columnsList = Arrays.asList("athlète", "âge", "pays", "année", "sport", "or", "argent", "bronze", "total");

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

Il est possible de définir la visibilité d'une colonne, déterminant si elle sera affichée ou non dans la `Table`. Cela peut être utile lorsqu'il s'agit, entre autres, de décider s'il faut afficher des informations sensibles.

```java
table.addColumn("Carte de Crédit", Customer::getCreditCardNumber).setHidden(true);
```

### Navigable {#navigable}

L'attribut navigable détermine si les utilisateurs peuvent interagir avec une colonne pendant la navigation. Définir `setSuppressNavigable()` sur true restreint l'interaction de l'utilisateur avec la colonne, offrant une expérience en lecture seule.

```java
table.addColumn("Colonne en lecture seule", Product::getDescription).setSuppressNavigable(true);
```

## Mise en page et formatage {#layout-and-formatting}

Après avoir établi l'identité d'une colonne, l'étape suivante consiste à contrôler comment son contenu apparaît aux utilisateurs. Les options de mise en page telles que l'alignement et le "pinning" déterminent où les données se trouvent et comment elles restent visibles pendant que vous travaillez avec une `Table`.

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

- `Column.Alignment.LEFT` : Convient pour des données textuelles ou descriptives où le maintien d'un flux vers la gauche est intuitif. Utile pour souligner le point de départ du contenu.
- `Column.Alignment.CENTER` : Les colonnes centrées sont idéales pour des valeurs plus courtes, comme une clé de caractère, un statut ou toute autre chose qui a une présentation équilibrée.
- `Column.Alignment.RIGHT` : Envisagez d'utiliser une colonne alignée à droite pour des valeurs numériques qui sont utiles à parcourir rapidement, comme des dates, des montants et des pourcentages.

Dans l'exemple précédent, la dernière colonne pour le `Coût` a été alignée à droite pour fournir une distinction visuelle plus évidente.

### Pinning {#pinning}

Le "pinning" de colonnes est une caractéristique qui permet aux utilisateurs de fixer ou "pincer" une colonne d'un côté spécifique de la `Table`. Cela est utile lorsque certaines colonnes, telles que les identifiants ou les informations essentielles, doivent rester visibles lors du défilement horizontal à travers une table.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Il existe trois directions disponibles pour pincer une colonne :

- `PinDirection.LEFT` : Pince la colonne à gauche.
- `PinDirection.RIGHT` : Pince la colonne à droite.
- `PinDirection.AUTO` : La colonne apparaît selon l'ordre d'insertion.

Le "pinning" peut être défini programmétiquement, vous permettant de changer la direction de pin selon les interactions des utilisateurs ou par la logique de l'application.

## Taille de la colonne <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Largeur fixe {#fixed-width}

Définissez une largeur exacte pour une colonne en utilisant la méthode `setWidth()`, spécifiant la largeur désirée en pixels :

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propriété de largeur définit la largeur initiale désirée pour la colonne. Comment cette largeur est utilisée dépend d'autres propriétés et du type de colonne :

- **Colonnes régulières** : Avec seulement la largeur définie, la colonne s'affiche à la largeur spécifiée mais peut rétrécir proportionnellement lorsque le conteneur est trop petit. La largeur d'origine sert de largeur désirée, mais sans contraintes minimales explicites, la colonne peut s'afficher plus petite que la largeur définie.
- [**Colonnes épinglées**](#pinning) : Maintiennent toujours leur largeur exacte, ne participant jamais au rétrécissement réactif.
- [**Colonnes flexibles**](#flex-sizing) : La définition de la largeur est incompatible avec le flex. Utilisez soit la largeur (fixe) ou le flex (proportionnel), pas les deux.

Si non spécifiée, la colonne utilisera sa largeur estimée basée sur l'analyse du contenu des premières lignes.

```java
// Obtenez la largeur actuelle
float currentWidth = column.getWidth();
```

### Largeur minimale {#minimum-width}

La méthode `setMinWidth()` vous permet de définir la largeur minimale d'une colonne. Si la largeur minimale n'est pas fournie, la `Table` calculera la largeur minimale basée sur le contenu de la colonne.

```java
table.addColumn("Prix", Product::getPrice).setMinWidth(100f);
```

La valeur passée représente la largeur minimale en pixels.

La propriété de largeur minimale contrôle la plus petite largeur qu'une colonne peut avoir :

- **Colonnes régulières** : Avec seulement la largeur minimale définie, la colonne utilise la largeur minimale comme largeur désirée et minimale. Avec la largeur + la largeur minimale, la colonne peut rétrécir de la largeur jusqu'à la largeur minimale mais pas plus.
- [**Colonnes épinglées**](#pinning) : Si seule la largeur minimale est définie (pas de largeur), elle devient la largeur fixe.
- [**Colonnes flexibles**](#flex-sizing) : Empêche la colonne de rétrécir en dessous de cette largeur même lorsque l'espace du conteneur est limité.

```java
// Obtenez la largeur minimale actuelle
float minWidth = column.getMinWidth();
```

### Largeur maximale {#maximum-width}

La méthode `setMaxWidth()` limite combien une colonne peut grandir, empêchant les colonnes avec des contenus longs de devenir trop larges et d'affecter la lisibilité :

```java
table.addColumn("Description", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

La propriété `maxWidth` limite la croissance des colonnes pour tous les types de colonnes et ne sera jamais dépassée peu importe le contenu, la taille du conteneur ou les paramètres de flex.

```java
// Obtenez la largeur maximale actuelle
float maxWidth = column.getMaxWidth();
```

### Taille flexible {#flex-sizing}

La méthode `setFlex()` permet une taille proportionnelle des colonnes, faisant partager aux colonnes l'espace disponible après l'allocation des colonnes à largeur fixe :

```java
// La colonne Titre obtient deux fois l'espace de la colonne Artiste
table.addColumn("Titre", Product::getTitle).setFlex(2f);
table.addColumn("Artiste", Product::getArtist).setFlex(1f);
```

Comportements clés du flex :

- **Valeur de flex** : Détermine la proportion de l'espace disponible. Une colonne avec flex=2 obtient deux fois l'espace d'une colonne avec flex=1.
- **Incompatible avec la largeur** : Ne peut pas être utilisée avec la propriété de largeur. Lorsque flex est supérieur à zéro, il prend effet sur la définition de la largeur.
- **Respecte les contraintes** : Fonctionne avec les contraintes de largeur minimale/largeur maximale. Sans largeur minimale, les colonnes flexibles peuvent rétrécir à 0.

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
Les propriétés de largeur et de flex sont mutuellement exclusives. Définir l'un annule automatiquement l'autre. Utilisez la largeur pour un contrôle précis ou le flex pour un comportement réactif.
:::

### Taille automatique {#automatic-sizing}

Au-delà des réglages manuels de largeur et de flex, les colonnes peuvent également être dimensionnées automatiquement. La taille automatique permet à la `Table` de déterminer les largeurs optimales soit en analysant le contenu soit en distribuant l'espace proportionnellement.

#### Taille automatique basée sur le contenu {#content-based-auto-sizing}

Dimensionner automatiquement les colonnes en fonction de leur contenu. La `Table` analyse les données dans chaque colonne et calcule la largeur optimale pour afficher le contenu sans troncature.

```java
// Auto-dimensionner toutes les colonnes pour s'adapter au contenu
table.setColumnsToAutoSize().thenAccept(c -> {
    // Dimensionnement terminé - les colonnes s'adaptent maintenant à leur contenu
});

// Auto-dimensionner une colonne spécifique
table.setColumnToAutoSize("description");
```

#### Ajustement automatique proportionnel {#proportional-auto-fit}

Distribuer toutes les colonnes proportionnellement au largeurs disponibles de la `Table`. Cette opération règle chaque colonne à flex=1, les faisant partager la largeur totale de la `Table` également, peu importe la longueur de leur contenu. Les colonnes vont s'étendre ou se rétracter pour remplir exactement les dimensions de la `Table` sans espace restant.

```java
// Ajuster les colonnes à la largeur de la table (équivalent à réglage flex=1 sur toutes)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Toutes les colonnes partagent maintenant l'espace également
});
```

:::info Opérations Asynchrones
Les méthodes de dimensionnement automatique retournent `PendingResult<Void>` car elles nécessitent des calculs côté client. Utilisez `thenAccept()` pour exécuter le code après que le dimensionnement soit terminé. Si vous ne devez pas attendre la fin, vous pouvez appeler les méthodes sans `thenAccept()`
:::

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnautosizing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAutoSizingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='550px'
/>
<!-- vale on -->

## Interactions utilisateur <DocChip chip='since' label='25.03' /> {#user-interactions}

### Redimensionnement des colonnes {#column-resizing}

Le redimensionnement des colonnes donne aux utilisateurs le contrôle sur l'espace que chaque colonne occupe en faisant glisser les bordures des colonnes.

Vous pouvez contrôler le comportement de redimensionnement sur des colonnes individuelles lors de la construction de votre table :

```java
// Activer le redimensionnement par l'utilisateur pour cette colonne
table.addColumn("Titre", Product::getTitle).setResizable(true);

// Désactiver le redimensionnement
table.addColumn("ID", Product::getId).setResizable(false);

// Vérifiez l'état actuel
boolean canResize = column.isResizable();
```

Pour les tables où vous souhaitez un comportement cohérent sur plusieurs colonnes, utilisez les méthodes de configuration en masse :

```java
// Rendre toutes les colonnes existantes redimensionnables
table.setColumnsToResizable(true);

// Verrouiller toutes les colonnes existantes contre le redimensionnement
table.setColumnsToResizable(false);
```

### Réorganisation des colonnes {#column-reordering}

La réorganisation des colonnes permet aux utilisateurs de glisser-déposer les colonnes dans leur ordre préféré, personnalisant la mise en page de la `Table` selon leur flux de travail.

Configurez les permissions de mouvement des colonnes lors de la configuration de votre table :

```java
// Permettre aux utilisateurs de déplacer cette colonne
table.addColumn("Titre", Product::getTitle).setMovable(true);

// Empêcher le mouvement des colonnes (utile pour les colonnes ID ou d'action)
table.addColumn("ID", Product::getId).setMovable(false);

// Vérifiez l'état actuel
boolean canMove = column.isMovable();
```

Appliquez les paramètres de mouvement à plusieurs colonnes simultanément :

```java
// Activer la réorganisation pour toutes les colonnes existantes
table.setColumnsToMovable(true);

// Désactiver la réorganisation pour toutes les colonnes existantes  
table.setColumnsToMovable(false);
```

:::note Opérations en masse
Les méthodes `setColumnsToResizable()` et `setColumnsToMovable()` n'affectent que les colonnes existantes au moment de l'invocation. Elles ne définissent pas de valeurs par défaut pour les colonnes futures.
:::

### Mouvement de colonne programmatique {#programmatic-column-movement} 

En plus du glisser-déposer, vous pouvez également repositionner les colonnes de manière programmatique par index ou ID. Gardez à l'esprit que l'index est basé uniquement sur les colonnes visibles ; les colonnes cachées sont ignorées lors du calcul des positions.

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

Le composant `Table` émet des événements lorsque les utilisateurs interagissent avec les colonnes, vous permettant de réagir aux changements de mise en page et de sauvegarder les préférences des utilisateurs.

Événements pris en charge :

- `TableColumnResizeEvent` : Déclenché lorsqu'un utilisateur redimensionne une colonne en faisant glisser sa bordure.
- `TableColumnMoveEvent` : Déclenché lorsqu'un utilisateur réorganise une colonne en faisant glisser son en-tête.

Vous pouvez attacher des écouteurs au `Table` pour répondre lorsque les utilisateurs modifient la mise en page de la table.

```java
Table<Product> table = new Table<>();

table.onColumnResize(event -> {
  // Gérer l'événement de redimensionnement de colonne
  // Accéder : event.getColumn(), event.getOldWidth(), event.getNewWidth()
});

table.onColumnMove(event -> {
  // Gérer l'événement de mouvement de colonne  
  // Accéder : event.getColumn(), event.getOldIndex(), event.getNewIndex()
});
```
