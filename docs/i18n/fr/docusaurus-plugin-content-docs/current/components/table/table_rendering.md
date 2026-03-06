---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: e536c2f1d5c965ed0f8ee139e38ee0f7
---
# Contenu riche et rendu côté client

Les tableaux dans webforJ sont également configurables à l'aide des outils suivants pour afficher un contenu riche au sein des cellules. Cela inclut des composants interactifs ou des données formatées à l'intérieur des cellules du tableau.

Ces éléments sont rendus côté client, ce qui signifie que le processus de génération et d'affichage du contenu riche se fait directement dans le navigateur, en utilisant JavaScript uniquement lorsque cela est nécessaire, augmentant ainsi la performance des applications utilisant le `Table`.

## Rendu Lodash {#lodash-renderers}

Les renderers offrent un mécanisme puissant pour personnaliser la manière dont les données sont affichées au sein d'un `Table`. La classe principale, `Renderer`, est conçue pour être étendue afin de créer des renderers personnalisés basés sur des modèles Lodash, permettant un rendu de contenu dynamique et interactif.

Les modèles Lodash permettent l'insertion de HTML directement dans les cellules de tableau, les rendant très efficaces pour rendre des données de cellule complexes dans un `Table`. Cette approche permet la génération dynamique de HTML basée sur les données de cellule, facilitant ainsi un contenu riche et interactif dans les cellules de tableau.

### Syntaxe Lodash {#lodash-syntax}

La section suivante décrit les bases de la syntaxe Lodash. Bien qu'il ne s'agisse pas d'un aperçu exhaustif, cela peut aider à commencer à utiliser Lodash au sein du composant `Table`.

#### Aperçu de la syntaxe pour les modèles Lodash : {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpole des valeurs, insérant le résultat du code JavaScript dans le modèle.
- `<% ... %>` - Exécute du code JavaScript, permettant des boucles, des conditionnelles, et plus encore.
- `<%- ... %>` - Échappe le contenu HTML, garantissant que les données interpolées sont à l'abri des attaques par injection HTML.

#### Exemples utilisant des données de cellules : {#examples-using-cell-data}

**1. Interpolation de valeur simple** : afficher directement la valeur de la cellule.

`<%= cell.value %>`

**2. Rendu conditionnel** : utiliser la logique JavaScript pour rendre conditionnellement le contenu.

`<% if (cell.value > 100) { %> 'Élevé' <% } else { %> 'Normal' <% } %>`

**3. Combinaison de champs de données** : rendre le contenu en utilisant plusieurs champs de données de la cellule.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Échapper le contenu HTML** : rendre en toute sécurité le contenu généré par l'utilisateur.

Le renderer a accès à des propriétés détaillées de la cellule, de la ligne et de la colonne côté client :

**Propriétés TableCell :**

|Propriété	|Type	|Description|
|-|-|-|
|column|`TableColumn`|L'objet de colonne associé.|
|first|`boolean`|Indique si la cellule est la première de la ligne.|
|id|`String`|L'ID de la cellule.|
|index|`int`|L'index de la cellule au sein de sa ligne.|
|last|`boolean`|Indique si la cellule est la dernière de la ligne.|
|row|`TableRow`|L'objet de ligne associé à la cellule.|
|value|`Object`|La valeur brute de la cellule, directement depuis la source de données.|

**Propriétés TableRow :**

|Propriété|Type|Description|
|-|-|-|
|cells|`TableCell[]`|Les cellules au sein de la ligne.|
|data|`Object`|Les données fournies par l'application pour la ligne.|
|even|`boolean`|Indique si la ligne est de numéro pair (pour les purposes de style).|
|first|`boolean`|Indique si la ligne est la première du tableau.|
|id|`String`|ID unique pour la ligne.|
|index|`int`|L'index de la ligne.|
|last|`boolean`|Indique si la ligne est la dernière du tableau.|
|odd|`boolean`|Indique si la ligne est de numéro impair (pour les purposes de style).|

**Propriétés TableColumn :**

|Propriété	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|L'alignement de la colonne (gauche, centre, droite).|
|id|String|Le champ de l'objet de ligne pour obtenir les données de la cellule.|
|label|String|Le nom à rendre dans l'en-tête de la colonne.|
|pinned|ColumnPinDirection|La direction d'épinglage de la colonne (gauche, droite, auto).|
|sortable|boolean|Si vrai, la colonne peut être triée.|
|sort|SortDirection|L'ordre de tri de la colonne.|
|type|ColumnType|Le type de la colonne (texte, nombre, booléen, etc.).|
|minWidth|number|La largeur minimale de la colonne en pixels.|

## Renderers disponibles {#available-renderers}

Bien que des renderers personnalisés puissent être créés, il existe plusieurs renderers préconfigurés disponibles pour une utilisation dans un `Table`. Les suivants sont disponibles pour les développeurs sans avoir besoin de créer un renderer personnalisé :

>- `ButtonRenderer` - Renderer pour un bouton webforJ.
>- `NativeButtonRenderer` - Renderer pour un bouton HTML natif.
>- `ElementRenderer` - La classe de base pour tous les renderers qui rendent une balise HTML **avec** contenu.
>- `VoidElementRenderer` - La classe de base pour tous les renderers qui rendent un élément vide, ou une balise HTML **sans** contenu.
>- `IconRenderer` - Renderer pour une icône - **[voir ceci](../../components/icon)** article pour plus d'informations sur les icônes.

Les renderers permettent également d'écrire des événements personnalisés en étendant n'importe lequel des renderers de base pris en charge. Actuellement, les renderers viennent avec un `RendererClickEvent` disponible pour les développeurs.

Ci-dessous un exemple d'un `Table` qui utilise des renderers pour afficher du contenu riche :

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
