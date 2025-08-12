---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: 5ebe30c35548db6d3b603b8a72ed4c2a
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La classe `Table` utilise les classes `Column` pour gérer la création de colonnes de données à l'intérieur. Cette classe dispose d'une large gamme de fonctionnalités qui permet à un utilisateur de personnaliser complètement ce qui est affiché dans chacune des colonnes. 
Pour ajouter une `Column` à une `Table`, utilisez l'une des méthodes de fabrique `addColumn`.

## Fournisseurs de valeur {#value-providers}

Un fournisseur de valeur est une fonction responsable de la traduction des données brutes de l'ensemble de données sous-jacent en un format approprié pour l'affichage dans une colonne spécifique. La fonction, définie par l'utilisateur, prend une instance du type de données de ligne (T) et renvoie la valeur à mettre en avant dans la colonne associée pour cette ligne particulière.

Pour définir un fournisseur de valeur sur une colonne, utilisez l'une des méthodes de fabrique `addColumn` qui acceptent un fournisseur comme argument :

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

Dans cet exemple, une colonne tentera d'accéder aux données d'un objet JSON, les rendant visibles uniquement si les données ne sont pas nulles.

## Direction de fixation {#pin-direction}

La fixation de colonne est une fonctionnalité qui permet aux utilisateurs d'attacher ou de "fixer" une colonne à un côté spécifique du tableau, améliorant la visibilité et l'accessibilité. Cela est utile lorsque certaines colonnes, comme les identifiants ou les informations essentielles, doivent rester visibles tout en défilant horizontalement à travers un tableau.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Il existe trois directions disponibles pour fixer une colonne :

- `PinDirection.LEFT`: Fixe la colonne à gauche.
- `PinDirection.RIGHT`: Fixe la colonne à droite.
- `PinDirection.AUTO`: La colonne apparaît en fonction de l'ordre d'insertion.

La fixation peut être définie par programmation, permettant aux utilisateurs de changer la direction de fixation en fonction des interactions des utilisateurs ou de la logique de l'application.

## Alignement {#alignment}

L'alignement de la colonne définit le positionnement horizontal des données au sein d'une colonne. Il influence la manière dont les valeurs de données sont affichées, fournissant un guide visuel aux utilisateurs sur la nature des informations.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Le composant Table prend en charge trois options d'alignement principales :

- `Column.Alignment.LEFT`: Adapté pour les données textuelles ou descriptives où il est intuitif de maintenir un flux vers la gauche. Utile pour souligner le point de départ du contenu.
- `Column.Alignment.CENTER`: Idéal pour les données numériques ou catégorielles où une présentation équilibrée est souhaitée. Crée un affichage centré visuellement.
- `Column.Alignment.RIGHT`: Communément utilisé pour les données numériques, notamment lorsque la magnitude ou la précision des chiffres est significative. Aligne les données vers la droite pour un flux de lecture naturel.

Dans l'exemple ci-dessus, la dernière colonne pour `Coût` a été alignée à droite pour fournir une distinction visuelle plus évidente.

## Visibilité {#visibility}

Il est possible de définir la visibilité d'une `Column`, déterminant si elle sera affichée ou non dans le tableau. Cela peut être utile lorsque, parmi d'autres choses, il s'agit de déterminer s'il faut ou non afficher des informations sensibles.

Utilisez la méthode `setHidden()` comme indiqué ci-dessous pour définir cette propriété sur une `Column` :

`table.addColumn("Carte de Crédit", Customer::getCreditCardNumber).setHidden(true);`

## Navigable {#navigable}

L'attribut navigable détermine si les utilisateurs peuvent interagir avec une colonne pendant la navigation. En définissant `setSuppressNavigable()` sur true, on limite l'interaction des utilisateurs avec la colonne, offrant une expérience en lecture seule.

```java
table.addColumn("Colonne en Lecture Seule", Product::getDescription).setSuppressNavigable(true);
```

## Étiquette {#label}

L'étiquette d'une colonne est son identifiant public, contribuant à la clarté et à la compréhension des données affichées. Utilisez setLabel pour définir ou modifier l'étiquette associée à une colonne.

:::tip
Par défaut, l'étiquette sera la même que l'ID de la colonne
:::

```java
table.addColumn("ID Produit", Product::getProductId).setLabel("ID");
```

## Largeur minimale {#minimum-width}

La méthode `setMinWidth()` vous permet de définir la largeur minimale d'une colonne, garantissant une mise en page cohérente et esthétiquement agréable.

Si la largeur minimale n'est pas fournie, le tableau calculera la largeur minimale basée sur le contenu de la colonne.

```java
table.addColumn("Prix", Product::getPrice).setMinWidth(100.0);
```

:::info
La valeur passée représente la largeur minimale souhaitée en pixels.  
:::
