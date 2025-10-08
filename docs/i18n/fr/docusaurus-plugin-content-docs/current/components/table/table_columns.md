---
sidebar_position: 5
title: Columns
slug: columns
sidebar_class_name: new-content
_i18n_hash: 280a70bb65c45d3b200157f3462d7b10
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La classe `Table` utilise des instances de colonnes pour définir et personnaliser l'affichage des données. Les colonnes contrôlent quelles données sont affichées, leur apparence et comment les utilisateurs peuvent interagir avec elles. Cette page aborde l'identité des colonnes, la présentation, le dimensionnement, les interactions utilisateur, et les événements associés.

## Identité de la colonne {#column-identity}

L'identité d'une colonne définit comment elle est reconnue dans la `Table`. Cela inclut son label, la valeur qu'elle fournit, et si elle est visible ou navigable.

### Label {#label}

Le label d'une colonne est son identifiant public, aidant à clarifier les données affichées.  

Utilisez `setLabel()` pour définir ou modifier le label.

:::tip
Par défaut, le label sera le même que l'ID de la colonne.
:::

```java
table.addColumn("ID produit", Product::getProductId).setLabel("ID");
```

### Fournisseurs de valeur {#value-providers}

Un fournisseur de valeur est une fonction responsable de la traduction des données brutes de l'ensemble de données sous-jacent dans un format adapté à l'affichage dans une colonne spécifique. La fonction, que vous définissez, prend une instance du type de données de ligne (T) et renvoie la valeur à afficher dans la colonne associée pour cette ligne particulière.

Pour définir un fournisseur de valeur sur une colonne, utilisez l'une des méthodes `addColumn()` du composant `Table`.

Dans l'extrait suivant, une colonne tentera d'accéder à des données d'un objet JSON, ne les rendant que si les données ne sont pas nulles.

```java
    List<String> columnsList = Arrays.asList("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total");

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

Il est possible de définir la visibilité d'une colonne, déterminant si elle sera affichée dans la `Table`. Cela peut être utile, entre autres, pour décider d'afficher ou non des informations sensibles. 

```java
table.addColumn("Numéro de carte de crédit", Customer::getCreditCardNumber).setHidden(true);
```

### Navigable {#navigable}

L'attribut navigable détermine si les utilisateurs peuvent interagir avec une colonne pendant la navigation. En définissant `setSuppressNavigable()` sur true, vous restreignez l'interaction des utilisateurs avec la colonne, offrant une expérience en lecture seule.

```java
table.addColumn("Colonne en lecture seule", Product::getDescription).setSuppressNavigable(true);
```

## Mise en page et formatage {#layout-and-formatting}

Après avoir établi l'identité d'une colonne, l'étape suivante consiste à contrôler l'apparence de son contenu pour les utilisateurs. Les options de mise en page, telles que l'alignement et l'épinglage, déterminent où les données se trouvent et comment elles restent visibles en travaillant avec une `Table`.

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

- `Column.Alignment.LEFT` : Convient aux données textuelles ou descriptives où maintenir un flux vers la gauche est intuitif. Utile pour mettre en avant le point de départ du contenu.
- `Column.Alignment.CENTER` : Les colonnes centrées sont idéales pour les valeurs plus courtes, comme une clé de caractère, un statut, ou toute autre chose ayant une présentation équilibrée.
- `Column.Alignment.RIGHT` : Envisagez d'utiliser une colonne à droite pour des valeurs numériques qui sont utiles pour un aperçu rapide, telles que des dates, des montants et des pourcentages.

Dans l'exemple précédent, la dernière colonne pour le `Coût` a été alignée à droite pour fournir une distinction visuelle plus évidente.

### Épinglage {#pinning}

L’épinglage des colonnes est une fonctionnalité qui permet aux utilisateurs de fixer ou "épingler" une colonne à un côté spécifique de la `Table`. Cela est utile lorsque certaines colonnes, comme des identifiants ou des informations essentielles, doivent rester visibles lors du défilement horizontal dans une table.

<!-- vale off -->
<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

Il existe trois directions disponibles pour l'épinglage d'une colonne :

- `PinDirection.LEFT` : Épingle la colonne à gauche.
- `PinDirection.RIGHT` : Épingle la colonne à droite.
- `PinDirection.AUTO` : La colonne apparaît en fonction de l'ordre d'insertion.

L'épinglage peut être défini par programme, vous permettant de changer la direction de l'épinglage en fonction des interactions utilisateur ou de la logique de l'application.

## Dimensionnement des colonnes <DocChip chip='since' label='25.03' /> {#column-sizing} 

### Largeur fixe {#fixed-width}

Définissez une largeur exacte pour une colonne à l'aide de la méthode `setWidth()`, en spécifiant la largeur souhaitée en pixels :

```java
table.addColumn("ID", Product::getId).setWidth(80f);
```

La propriété de largeur définit la largeur initiale souhaitée pour la colonne. L'utilisation de cette largeur dépend des autres propriétés et du type de colonne :

- **Colonnes régulières** : Avec seulement la largeur définie, la colonne s'affiche à la largeur spécifiée mais peut se rétrécir proportionnellement lorsque le conteneur est trop petit. La largeur d'origine sert de largeur souhaitée, mais sans contraintes minimales explicites, la colonne peut s'afficher plus petite que la largeur fixée.
- [**Colonnes épinglées**](#pinning) : Maintiennent toujours leur largeur exacte, ne participant jamais à la réduction responsive.
- [**Colonnes flexibles**](#flex-sizing) : Définir la largeur est incompatible avec le flex. Utilisez soit la largeur (fixe), soit le flex (proportionnel), pas les deux.

Si non spécifiée, la colonne utilisera sa largeur estimée en fonction de l'analyse du contenu des premières lignes.

```java
// Obtenir la largeur actuelle
float currentWidth = column.getWidth();
```

### Largeur minimale {#minimum-width}

La méthode `setMinWidth()` vous permet de définir la largeur minimale d'une colonne. Si la largeur minimale n'est pas fournie, la `Table` calcule la largeur minimale en fonction du contenu de la colonne.

```java
table.addColumn("Prix", Product::getPrice).setMinWidth(100f);
```

La valeur passée représente la largeur minimale en pixels.

La propriété de largeur minimale contrôle la plus petite largeur qu'une colonne peut avoir :

- **Colonnes régulières** : Avec seulement la largeur minimale définie, la colonne utilise la largeur minimale comme souhaitée et minimale. Avec largeur + largeur minimale, la colonne peut se rétrécir de la largeur à la largeur minimale mais pas plus loin.
- [**Colonnes épinglées**](#pinning) : Si seule la largeur minimale est définie (pas de largeur), celle-ci devient la largeur fixe.
- [**Colonnes flexibles**](#flex-sizing) : Empêche la colonne de se rétrécir en dessous de cette largeur même lorsque l'espace dans le conteneur est limité.

```java
// Obtenir la largeur minimale actuelle
float minWidth = column.getMinWidth();
```

### Largeur maximale {#maximum-width}

La méthode `setMaxWidth()` limite la largeur que peut atteindre une colonne, empêchant les colonnes avec un contenu long de devenir trop larges et d'affecter la lisibilité :

```java
table.addColumn("Description", Product::getDescription)
    .setMinWidth(100f)
    .setMaxWidth(300f);
```

La propriété `maxWidth` limite la croissance des colonnes pour tous les types de colonnes et ne sera jamais dépassée, peu importe le contenu, la taille du conteneur ou les réglages de flex.

```java
// Obtenir la largeur maximale actuelle
float maxWidth = column.getMaxWidth();
```

### Dimensionnement flexible {#flex-sizing}

La méthode `setFlex()` permet un dimensionnement proportionnel des colonnes, faisant en sorte que les colonnes partagent l'espace disponible après que les colonnes de largeur fixe aient été allouées :

```java
// La colonne titre obtient deux fois l'espace de la colonne Artiste
table.addColumn("Titre", Product::getTitle).setFlex(2f);
table.addColumn("Artiste", Product::getArtist).setFlex(1f);
```

Comportements clés du flex :

- **Valeur flex** : Détermine la proportion de l'espace disponible. Une colonne avec flex=2 obtient deux fois l'espace d'une colonne avec flex=1.
- **Incompatible avec la largeur** : Ne peut pas être utilisé avec la propriété de largeur. Lorsque flex est supérieur à zéro, il prend effet sur la configuration de la largeur.
- **Respecte les contraintes** : Fonctionne avec les contraintes de largeur minimale/largeur maximale. Sans largeur minimale, les colonnes flexibles peuvent se rétrécir à 0.

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
Les propriétés de largeur et de flex sont mutuellement exclusives. En définissant l'une, l'autre est automatiquement effacée. Utilisez la largeur pour un contrôle précis ou le flex pour un comportement réactif.
:::

### Dimensionnement automatique {#automatic-sizing}

Au-delà des réglages manuels de largeur et de flex, les colonnes peuvent également être dimensionnées automatiquement. Le dimensionnement automatique permet à la `Table` de déterminer les largeurs optimales soit en analysant le contenu, soit en répartissant l'espace proportionnellement.

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

Distribuez toutes les colonnes proportionnellement à travers la largeur disponible de la `Table`. Cette opération définit chaque colonne sur flex=1, les faisant partager la largeur totale de la `Table` de manière égale, quelle que soit la longueur de leur contenu. Les colonnes s'étendront ou se rétréciront pour remplir les dimensions exactes de la `Table` sans espace restant.

```java
// Ajuster les colonnes à la largeur de la table (équivalent à définir flex=1 sur toutes)
table.setColumnsToAutoFit().thenAccept(ignored -> {
    // Toutes les colonnes partagent maintenant l'espace également
});
```

:::info Opérations asynchrones
Les méthodes de dimensionnement automatique retournent `PendingResult<Void>` car elles nécessitent des calculs côté client. Utilisez `thenAccept()` pour exécuter du code une fois le dimensionnement terminé. Si vous n'avez pas besoin d'attendre la fin, vous pouvez appeler les méthodes sans `thenAccept()`
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

Le redimensionnement des colonnes permet aux utilisateurs de contrôler l'espace occupé par chaque colonne en faisant glisser les bordures des colonnes.

Vous pouvez contrôler le comportement de redimensionnement pour des colonnes individuelles lors de la construction de votre table :

```java
// Activer le redimensionnement utilisateur pour cette colonne
table.addColumn("Titre", Product::getTitle).setResizable(true);

// Désactiver le redimensionnement
table.addColumn("ID", Product::getId).setResizable(false);

// Vérifier l'état actuel
boolean canResize = column.isResizable();
```

Pour les tables où vous souhaitez un comportement cohérent à travers plusieurs colonnes, utilisez les méthodes de configuration en masse :

```java
// Rendre toutes les colonnes existantes redimensionnables
table.setColumnsToResizable(true);

// Bloquer toutes les colonnes existantes de se redimensionner
table.setColumnsToResizable(false);
```

### Réorganisation des colonnes {#column-reordering}

La réorganisation des colonnes permet aux utilisateurs de faire glisser et déposer des colonnes dans l'ordre qu'ils préfèrent, personnalisant ainsi la mise en page de la `Table` pour leur workflow.

Configurez les permissions de mouvement de colonne lors de la configuration de votre table :

```java
// Permettre aux utilisateurs de déplacer cette colonne
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

:::note Opérations en masse
Les méthodes `setColumnsToResizable()` et `setColumnsToMovable()` n'affectent que les colonnes existantes au moment de l'invocation. Elles ne définissent pas de défauts pour les colonnes futures.
:::

### Mouvement programmatique des colonnes {#programmatic-column-movement} 

En plus du glisser-déposer, vous pouvez également repositionner les colonnes par programme en utilisant l'index ou l'ID. Gardez à l'esprit que l'index est basé uniquement sur les colonnes visibles ; toutes les colonnes cachées sont ignorées lors du calcul des positions.

```java
// Déplacer la colonne à la première position
table.moveColumn("titre", 0);

// Déplacer la colonne à la dernière position
table.moveColumn(titleColumn, table.getColumns().size() - 1);

// Mouvement asynchrone avec callback
table.moveColumn("description", 2).thenAccept(c -> {
    // Colonne déplacée avec succès
});
```

## Gestion des événements {#event-handling}

Le composant `Table` émet des événements lorsque les utilisateurs interagissent avec les colonnes, vous permettant de répondre aux changements de mise en page et de sauvegarder les préférences des utilisateurs.

Événements pris en charge :

- `TableColumnResizeEvent` : Déclenché lorsqu'un utilisateur redimensionne une colonne en faisant glisser sa bordure.
- `TableColumnMoveEvent` : Déclenché lorsqu'un utilisateur réorganise une colonne en faisant glisser son en-tête.

Vous pouvez attacher des écouteurs à la `Table` pour répondre lorsque les utilisateurs modifient la mise en page de la table.

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
