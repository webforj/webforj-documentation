---
title: FlexLayout
sidebar_position: 45
_i18n_hash: ddb7d5ef1e583af6e3a7072d91329c7b
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

Le composant `FlexLayout` dispose les composants enfants en ligne ou en colonne en utilisant le modèle CSS Flexbox. Il vous donne le contrôle sur l'alignement, l'espacement, le wrapping et la façon dont les éléments se développent ou se rétrécissent pour remplir l'espace disponible.

<!-- INTRO_END -->

## Propriétés `FlexLayout` {#flex-layout-properties}

Les propriétés `FlexLayout` peuvent être regroupées en deux catégories : les propriétés qui s'appliquent aux éléments à l'intérieur d'un layout et celles qui s'appliquent au layout lui-même. Le `FlexLayout`, ou l'élément parent, est une boîte/conteneur qui peut contenir un ou plusieurs composants. Tout ce qui se trouve à l'intérieur d'un `FlexLayout` est appelé un élément ou un enfant. Le `FlexLayout` offre certaines capacités d'alignement, qui peuvent être réalisées avec l'aide des propriétés de conteneur ou d'élément.

:::tip
Le composant `FlexLayout` suit le modèle de [mise en page flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Cependant, le `FlexLayout` est conçu pour être utilisé entièrement en Java et ne nécessite pas l'utilisation de CSS en dehors des méthodes de l'API Java fournies.
:::

## Propriétés du conteneur {#container-properties}

Les propriétés du conteneur s'appliqueront à tous les composants au sein d'un composant et non au layout lui-même. Elles n'affecteront pas l'orientation ou le placement du parent, uniquement les composants enfants à l'intérieur.

### Direction {#direction}

Le `FlexLayout` ajoute des composants les uns à côté des autres selon sa direction, soit horizontale, soit verticale. Lorsque vous utilisez le créateur, chaînez les méthodes `horizontal()`, `horizontalReverse()`, `vertical()`, ou `verticalReverse()` avec la méthode `FlexLayout.create()` pour configurer le layout lors de la création de l'objet.

Pour définir la direction sur un objet `FlexLayout` existant, utilisez la méthode `setDirection()`. Les options horizontales sont `FlexDirection.ROW` (de gauche à droite) ou `FlexDirection.ROW_REVERSE` (de droite à gauche), et les options verticales sont `FlexDirection.COLUMN` (de haut en bas) ou `FlexDirection.COLUMN_REVERSE` (de bas en haut). 

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionnement {#positioning}

Les composants ajoutés horizontalement peuvent également être positionnés à la fois horizontalement et verticalement. Utilisez les méthodes `justify()`, `align()` et `contentAlign()` du Builder `FlexLayout` pour configurer le positionnement lors de la création d'un nouveau `FlexLayout`.

Alternativement, sur l'objet `FlexLayout` réel, vous pouvez utiliser la méthode `setJustifyContent()` pour positionner les éléments horizontalement, et la méthode `setAlignment()` pour configurer le positionnement vertical. Pour modifier l'espace autour des composants le long de l'axe transversal (axe y pour les mises en page horizontales), utilisez la méthode `setAlignContent()`.

:::tip
La méthode `setAlignment()` contrôle la façon dont les éléments seront affichés le long de l'axe transversal dans son ensemble au sein du conteneur, et est efficace pour les mises en page à ligne unique.

La méthode `setAlignContent()` contrôle l'espace autour de l'axe transversal, et prendra effet seulement lorsqu'un layout a plusieurs lignes.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Wrapping {#wrapping}

Pour personnaliser davantage le composant `FlexLayout`, vous pouvez spécifier son comportement lorsque des composants sont ajoutés qui ne tiennent plus dans l'affichage. Pour configurer cela en utilisant le créateur, utilisez les méthodes `nowrap()` (par défaut), `wrap()`, et `wrapReverse()` pour configurer le wrapping. Pour configurer cela sur un objet `FlexLayout` existant, utilisez la méthode `setWrap()`.

### Espacement {#spacing}

Afin d'appliquer un espacement minimal entre les éléments, vous pouvez définir la propriété `gap`. Elle applique cet espacement uniquement entre les éléments, pas sur les bords extérieurs. 

Le comportement de la propriété gap peut être considéré comme une distance minimale entre, il ne prendra donc effet que s'il s'agit de l'espace calculé le plus grand entre les éléments. Si l'espace entre les éléments serait autrement plus grand en raison d'une autre propriété calculée, telle que due à `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, alors la propriété gap sera ignorée.

### Flux {#flow}

Le flux flex, qui est une combinaison des propriétés de direction et de wrapping, peut être défini en utilisant la méthode `setFlow()` sur un objet `FlexLayout`.

:::info
Pour configurer cette propriété lors de la création du layout, utilisez les méthodes appropriées de direction et de wrapping. Par exemple, pour créer un flux de colonne wrap, utilisez la combinaison `.vertical().wrap()`.
:::

### Créateur de conteneur {#container-builder}

La démo suivante vous permet de construire un conteneur avec les propriétés flex souhaitées sélectionnées parmi les divers menus. Cet outil peut être utilisé non seulement pour créer un exemple visuel des différentes méthodes, mais aussi pour créer vos propres mises en page avec vos propriétés désirées. Pour utiliser un layout que vous avez personnalisé, il vous suffit de copier le code de sortie et d'ajouter vos éléments désirés pour utilisation dans votre programme.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propriétés des éléments {#item-properties}

Les propriétés des éléments n'affecteront aucun élément enfant à l'intérieur du `FlexLayout`, mais affectent le layout lui-même. Cela est utile pour styliser un unique élément `FlexLayout` qui est un enfant d'un plus grand élément `FlexLayout` indépendamment des styles s'appliquant à tous les enfants.

### Ordre {#order}

La propriété `ItemOrder` détermine l'ordre dans lequel les composants sont affichés au sein du `FlexLayout`, et lorsqu'elle est utilisée sur un `FlexLayout`, elle assignera un numéro d'ordre spécifique à cet élément dans le layout. Cela remplace l'"ordre source" par défaut intégré à chaque élément (l'ordre dans lequel un composant est ajouté à son parent), ce qui signifie qu'il sera affiché avant les éléments avec un ordre plus élevé et après les éléments avec un ordre plus bas.

Cette propriété accepte une valeur entière sans unité qui spécifie l'ordre relatif de l'élément flex au sein du conteneur. Plus la valeur est basse, plus l'élément apparaît tôt dans l'ordre. Par exemple, un élément avec une valeur d'ordre de 1 apparaîtra avant un élément avec une valeur d'ordre de 2.

:::caution
Il est important de noter que la propriété d'ordre n'affecte que l'ordre visuel des éléments au sein du conteneur, pas leur position réelle dans le DOM. Cela signifie que les lecteurs d'écran et autres technologies d'assistance liront toujours les éléments dans l'ordre dans lequel ils apparaissent dans le code source, et non dans l'ordre visuel.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto alignement {#self-alignment}

L'auto-alignment du `FlexLayout` fait référence à la façon dont un seul objet `FlexLayout` est aligné au sein de son conteneur flex parent le long de l'axe transversal, qui est perpendiculaire à l'axe principal. L'alignement de l'axe transversal est contrôlé par la propriété `Alignment`.

La propriété align-self spécifie l'alignement d'un élément flex unique le long de l'axe transversal, remplaçant l'alignement par défaut défini par la propriété `AlignContent` dans un objet `FlexLayout`. Cela vous permet d'aligner des objets `FlexLayout` individuels différemment des autres dans le conteneur.

:::info
L'auto-alignment utilise les mêmes valeurs que l'alignement de contenu.
:::

Cette propriété est particulièrement utile lorsque vous devez aligner un élément spécifique différemment des autres éléments dans le conteneur. Voir l'exemple ci-dessous pour un exemple d'alignement d'un élément unique :

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Basis flex {#flex-basis}

`Item Basis` est une propriété utilisée en conjonction avec la direction du `FlexLayout` pour définir la taille initiale d'un élément flex avant que tout espace restant ne soit distribué.

La propriété `Item Basis` spécifie la taille par défaut d'un élément flex le long de l'axe principal, qui est soit horizontal (pour une direction en ligne) soit vertical (pour une direction en colonne). Cette propriété définit la largeur ou la hauteur d'un élément flex en fonction de la valeur de la propriété de direction flex.

:::info
Par défaut, la propriété `Item Basis` est réglée sur `auto`, ce qui signifie que la taille de l'élément est déterminée par son contenu. Cependant, vous pouvez également définir une taille spécifique pour l'élément en utilisant diverses unités telles que des pixels (px), des ems (em), des pourcentages (%) ou toute autre unité de longueur CSS.
:::

La démo suivante vous permet de sélectionner une ou plusieurs cases et de changer le `Item Basis` pour les éléments sélectionnés.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex grow et shrink {#flex-grow--shrink}

`Item Grow` et `Item Shrink` sont deux propriétés qui fonctionnent en conjunction l'une avec l'autre et avec la propriété `Item Basis` pour déterminer comment les éléments flex grandissent ou rétrécissent pour remplir l'espace disponible au sein d'un objet `FlexLayout`.

La propriété `Item Grow` spécifie combien l'élément flex peut croître par rapport aux autres éléments dans le conteneur. Elle prend une valeur sans unité qui représente une proportion de l'espace disponible qui doit être attribuée à l'élément. Par exemple, si un élément a une valeur `Item Grow` de 1 et qu'un autre a une valeur de 2, le deuxième élément grandira deux fois plus que le premier.

La propriété `Item Shrink`, quant à elle, spécifie combien l'élément flex peut se rétracter par rapport aux autres éléments dans le conteneur. Elle prend également une valeur sans unité qui représente une proportion de l'espace disponible qui doit être attribuée à l'élément. Par exemple, si un élément a une valeur `Item Shrink` de 1 et un autre a une valeur de 2, le deuxième élément se rétrécira deux fois plus que le premier.

Lorsqu'un conteneur a plus d'espace que nécessaire pour accueillir son contenu, les éléments flex avec une valeur `Item Grow` supérieure à 0 s'élargiront pour remplir l'espace disponible. La quantité d'espace que chaque élément obtient est déterminée par le rapport de sa valeur `Item Grow` à la valeur totale `Item Grow` de tous les éléments dans le conteneur.

De même, lorsqu'un conteneur n'a pas suffisamment d'espace pour accueillir son contenu, les éléments flex avec une valeur `Item Shrink` supérieure à 0 se rétréciront pour s'adapter à l'espace disponible. La quantité d'espace que chaque élément laisse est déterminée par le rapport de sa valeur `Item Shrink` à la valeur totale `Item Shrink` de tous les éléments dans le conteneur.

## Formulaire d'exemple {#example-form}
Le formulaire ci-dessous démontre comment `FlexLayout` organise les champs de saisie dans un layout structuré. 

:::tip
Si vous préférez une structure basée sur les colonnes, jetez un œil à la version `ColumnsLayout` de ce formulaire dans l'article [`ColumnsLayout`](../components/columns-layout) pour voir comment elle se compare.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
