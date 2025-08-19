---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 9bf580ccd6532be7fc66e2825d083724
---
# Contenu riche et rendu côté client

Les tableaux dans webforJ sont également configurables en utilisant les outils suivants pour afficher du contenu riche dans les cellules. Cela inclut des composants interactifs ou des données formatées au sein des cellules du tableau.

Ces éléments sont rendus côté client, ce qui signifie que le processus de génération et d'affichage du contenu riche se fait directement dans le navigateur, en utilisant JavaScript uniquement si nécessaire, ce qui augmente les performances des applications utilisant le `Table`.

## Rendus Lodash {#lodash-renderers}

Les rendus offrent un mécanisme puissant pour personnaliser la manière dont les données sont affichées dans un `Table`. La classe principale, `Renderer`, est conçue pour être étendue afin de créer des rendus personnalisés basés sur des modèles Lodash, permettant un rendu de contenu dynamique et interactif.

Les modèles Lodash permettent l'insertion de HTML directement dans les cellules du tableau, les rendant très efficaces pour le rendu de données complexes dans un `Table`. Cette approche permet la génération dynamique de HTML basée sur les données des cellules, facilitant le contenu riche et interactif des cellules de tableau.

### Syntaxe Lodash {#lodash-syntax}

La section suivante décrit les bases de la syntaxe Lodash. Bien qu'il ne s'agisse pas d'une vue d'ensemble exhaustive ou complète, elle peut être utilisée pour commencer à utiliser Lodash au sein du composant `Table`.

#### Vue d'ensemble de la syntaxe pour les modèles Lodash : {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpole des valeurs, insérant le résultat du code JavaScript dans le modèle.
- `<% ... %>` - Exécute du code JavaScript, permettant des boucles, des conditionnels, et plus encore.
- `<%- ... %>` - Échappe le contenu HTML, garantissant que les données interpolées sont à l'abri des attaques par injection HTML.

#### Exemples utilisant les données de cellule : {#examples-using-cell-data}

**1. Interpolation simple de valeur** : Affichez directement la valeur de la cellule.

`<%= cell.value %>`

**2. Rendu conditionnel** : Utilisez la logique JavaScript pour rendre conditionnellement le contenu.

`<% if (cell.value > 100) { %> 'Élevé' <% } else { %> 'Normal' <% } %>`

**3. Combinaison de champs de données** : Rendu de contenu en utilisant plusieurs champs de données de la cellule.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Échappement du contenu HTML** : Rendu sécurisé du contenu généré par l'utilisateur.

Le renderer a accès aux propriétés détaillées de la cellule, de la ligne et de la colonne côté client :

**Propriétés de TableCell :**

|Propriété	|Type	|Description|
|-|-|-|
|column|`TableColumn`|L'objet colonne associé.|
|first|`boolean`|Indique si la cellule est la première de la ligne.|
|id|`String`|L'ID de la cellule.|
|index|`int`|L'index de la cellule dans sa ligne.|
|last|`boolean`|Indique si la cellule est la dernière de la ligne.|
|row|`TableRow`|L'objet ligne associé à la cellule.|
|value|`Object`|La valeur brute de la cellule, directement depuis la source de données.|

**Propriétés de TableRow :**

|Propriété|Type|Description|
|-|-|-|
|cells|`TableCell[]`|Les cellules dans la ligne.|
|data|`Object`|Les données fournies par l'application pour la ligne.|
|even|`boolean`|Indique si la ligne est un numéro pair (à des fins de style).|
|first|`boolean`|Indique si la ligne est la première dans le tableau.|
|id|`String`|ID unique pour la ligne.|
|index|`int`|L'index de la ligne.|
|last|`boolean`|Indique si la ligne est la dernière dans le tableau.|
|odd|`boolean`|Indique si la ligne est un numéro impair (à des fins de style).|

**Propriétés de TableColumn :**

|Propriété	|Type	|Description|
|-|-|-|
|align|ColumnAlignment|L'alignement de la colonne (gauche, centre, droite).|
|id|String|Le champ de l'objet ligne à partir duquel obtenir les données de la cellule.|
|label|String|Le nom à rendre dans l'en-tête de la colonne.|
|pinned|ColumnPinDirection|La direction du pin de la colonne (gauche, droite, automatique).|
|sortable|boolean|Si vrai, la colonne peut être triée.|
|sort|SortDirection|L'ordre de tri de la colonne.|
|type|ColumnType|Le type de la colonne (texte, nombre, booléen, etc.).|
|minWidth|number|La largeur minimale de la colonne en pixels.|

## Rendus disponibles {#available-renderers}

Bien que des rendus personnalisés puissent être créés, il existe plusieurs rendus préconfigurés disponibles pour une utilisation au sein d'un `Table`. Les suivants sont disponibles pour les développeurs à utiliser tels quels sans besoin de créer un renderer personnalisé :

>- `ButtonRenderer` - Renderer pour un bouton webforJ.
>- `NativeButtonRenderer` - Renderer pour un bouton HTML natif.
>- `ElementRenderer` - La classe de base pour tous les rendus qui rendent une balise HTML **avec** contenu.
>- `VoidElementRenderer` - La classe de base pour tous les rendus qui rendent un élément vide, ou une balise HTML **sans** contenu.
>- `IconRenderer` - Renderer pour une icône - **[voir ceci](../../components/icon)** article pour plus d'informations sur les icônes.

Les rendus permettent également d'écrire des événements personnalisés en étendant l'un des rendus de base pris en charge. Actuellement, les rendus sont accompagnés d'un `RendererClickEvent` disponible pour utilisation par les développeurs.

Voici un exemple d'un `Table` qui utilise des rendus pour afficher du contenu riche :

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
