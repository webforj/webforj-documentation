---
sidebar_position: 5
title: Columns
slug: columns
_i18n_hash: b3545c590336bb6574bf196fbd417349
---
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Column" top='true'/>

La classe `Table` utilise les classes `Column` pour gérer la création de colonnes de données à l'intérieur. Cette classe possède une large gamme de fonctionnalités qui permettent à un utilisateur de personnaliser en profondeur ce qui est affiché dans chacune des colonnes. 
Pour ajouter une `Column` à une `Table`, utilisez l'une des méthodes de fabrique `addColumn`.

## Fournisseurs de valeur {#value-providers}

Un fournisseur de valeur est une fonction responsable de la traduction des données brutes du jeu de données sous-jacent en un format approprié pour l'affichage dans une colonne spécifique. La fonction, définie par l'utilisateur, prend une instance du type de données de la ligne (T) et renvoie la valeur à mettre en avant dans la colonne associée pour cette ligne particulière.

Pour définir un fournisseur de valeur sur une colonne, utilisez l'une des méthodes de fabrique `addColumn` qui acceptent un fournisseur comme argument :

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

Dans cet exemple, une colonne tentera d'accéder à des données d'un objet JSON, ne les affichant que si les données ne sont pas nulles.

## Direction de fixation {#pin-direction}

Le « pinning » des colonnes est une fonctionnalité qui permet aux utilisateurs d'ancrer ou de « pin » une colonne à un côté spécifique de la table, améliorant la visibilité et l'accessibilité. Cela est utile lorsque certaines colonnes, comme des identifiants ou des informations essentielles, doivent rester visibles lors du défilement horizontal à travers une table.

<ComponentDemo 
path='/webforj/tablecolumnpinning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnPinningView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Il existe trois directions disponibles pour le « pinning » d'une colonne :

- `PinDirection.LEFT` : Fixe la colonne sur le côté gauche.
- `PinDirection.RIGHT` : Fixe la colonne sur le côté droit.
- `PinDirection.AUTO` : La colonne apparaît en fonction de l'ordre d'insertion.

Le « pinning » peut être défini de manière programmatique, permettant aux utilisateurs de changer la direction de fixation en fonction des interactions utilisateur ou de la logique de l'application.

## Alignement {#alignment}

L'alignement de la colonne définit le positionnement horizontal des données à l'intérieur d'une colonne. Cela influence la manière dont les valeurs des données sont affichées, fournissant un guide visuel aux utilisateurs sur la nature de l'information.

<ComponentDemo 
path='/webforj/tablecolumnalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnAlignmentView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Le composant Table prend en charge trois principales options d'alignement :

- `Column.Alignment.LEFT` : Convient pour des données textuelles ou descriptives où maintenir un flux vers la gauche est intuitif. Utile lorsqu'il s'agit d'accentuer le point de départ du contenu.
- `Column.Alignment.CENTER` : Idéal pour des données numériques ou catégorielles où une présentation équilibrée est souhaitée. Crée un affichage visuellement centré.
- `Column.Alignment.RIGHT` : Couramment utilisé pour les données numériques, surtout lorsque la magnitude ou la précision des nombres est significative. Aligne les données vers la droite pour un flux de lecture naturel.

Dans l'exemple ci-dessus, la colonne finale pour `Cost` a été alignée à droite pour fournir une distinction visuelle plus évidente.

## Visibilité {#visibility}

Il est possible de définir la visibilité d'une `Column`, déterminant si elle sera affichée ou non à l'intérieur de la table. Cela peut être utile, entre autres, pour déterminer si oui ou non afficher des informations sensibles.

Utilisez la méthode `setHidden()`, comme indiqué ci-dessous, pour définir cette propriété sur une `Column` :

`table.addColumn("Credit Card", Customer::getCreditCardNumber).setHidden(true);`

## Navigable {#navigable}

L'attribut navigable détermine si les utilisateurs peuvent interagir avec une colonne lors de la navigation. En définissant `setSuppressNavigable()` sur true, l'interaction utilisateur avec la colonne est restreinte, offrant une expérience en lecture seule.

```java
table.addColumn("ReadOnly Column", Product::getDescription).setSuppressNavigable(true);
```

## Étiquette {#label}

L'étiquette d'une colonne est son identifiant public, contribuant à la clarté et à la compréhension des données affichées. Utilisez setLabel pour définir ou modifier l'étiquette associée à une colonne.

:::tip
Par défaut, l'étiquette sera la même que l'ID de la colonne.
:::

```java
table.addColumn("Product ID", Product::getProductId).setLabel("ID");
```

## Largeur minimale {#minimum-width}

La méthode `setMinWidth()` vous permet de définir la largeur minimale d'une colonne, garantissant un agencement cohérent et esthétiquement agréable.

Si la largeur minimale n'est pas fournie, la table calculera la largeur minimale en fonction du contenu de la colonne.

```java
table.addColumn("Price", Product::getPrice).setMinWidth(100.0);
```

:::info
La valeur passée représente la largeur minimale souhaitée en pixels.
:::
