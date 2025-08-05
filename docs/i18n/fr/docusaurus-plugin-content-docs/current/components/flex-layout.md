---
title: FlexLayout
sidebar_position: 45
_i18n_hash: df051e46de48f07807bf0dc3bcaa641a
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ fournit aux développeurs un moyen efficace et intuitif de disposer leurs différentes applications et composants - le Flex Layout. Cet ensemble d'outils permet d'afficher des éléments soit verticalement, soit horizontalement.

## Propriétés de la mise en page Flex {#flex-layout-properties}

Les propriétés de la mise en page Flex peuvent être regroupées en deux catégories : les propriétés qui s'appliquent aux éléments d'une mise en page, et les propriétés qui s'appliquent à la mise en page elle-même. La mise en page flex, ou l'élément parent, est une boîte/conteneur qui peut contenir un ou plusieurs composants. Tout ce qui se trouve à l'intérieur d'une mise en page Flex est appelé un élément ou un élément enfant. La mise en page Flex offre de robustes capacités d'alignement, qui peuvent être réalisées grâce aux propriétés de conteneur ou d'élément.

:::tip
Le composant de mise en page de webforJ suit le modèle de la [mise en page flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Cependant, ces outils sont conçus pour être entièrement utilisés en Java et ne nécessitent pas l'application de CSS en dehors des méthodes de l'API Java fournies.
:::

## Propriétés de conteneur {#container-properties}

Les propriétés de conteneur s'appliqueront à tous les composants au sein d'un composant et non à la mise en page elle-même. Elles n'affecteront pas l'orientation ou le placement du parent - seulement les composants enfants à l'intérieur.

### Direction {#direction}

La mise en page Flex ajoutera des composants les uns à côté des autres selon la direction choisie par le développeur - soit horizontale, soit verticale. En utilisant le builder, utilisez les méthodes `horizontal()`, `horizontalReverse()`, `vertical()` ou `verticalReverse()` lors de l'appel de la méthode `create()` sur un objet `FlexLayout` pour configurer cette mise en page à mesure que l'objet est créé.

Alternativement, utilisez la méthode `setDirection()`. Les options horizontales sont soit `FlexDirection.ROW` (de gauche à droite) ou `FlexDirection.ROW_REVERSE` (de droite à gauche), et les options verticales sont soit `FlexDirection.COLUMN` (de haut en bas) ou `FlexDirection.COLUMN_REVERSE` (de bas en haut). Cela se fait avec l'objet FlexLayout, par opposition au builder.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionnement {#positioning}

Les composants qui sont ajoutés horizontalement peuvent également être positionnés à la fois horizontalement et verticalement. Utilisez les méthodes `justify()`, `align()` et `contentAlign()` du Flex Layout Builder pour configurer le positionnement lors de la création d'une nouvelle mise en page Flex.

Alternativement, sur l'objet FlexLayout réel, vous pouvez utiliser la méthode `setJustifyContent()` pour positionner les éléments horizontalement et la méthode `setAlignment()` pour configurer le positionnement vertical. Pour modifier l'espace autour des composants le long de l'axe transversal (axe Y pour les mises en page horizontales), utilisez la méthode `setAlignContent()`.

:::tip
La méthode `setAlignment()` configure comment les éléments s'afficheront le long de l'axe transversal dans son ensemble au sein du conteneur, et est efficace pour les mises en page en ligne unique.

Les méthodes `setAlignContent()` configurent l'espace autour de l'axe transversal, et ne prendront effet que lorsqu'une mise en page a plusieurs lignes.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Enveloppement {#wrapping}

Pour personnaliser davantage le composant de mise en page Flex, vous pouvez spécifier le comportement de la mise en page flex lorsque les composants ajoutés ne tiennent plus dans l'affichage. Pour configurer cela en utilisant le builder, utilisez les méthodes `nowrap()` (par défaut), `wrap()` et `wrapReverse()` pour configurer l'enveloppement.

Alternativement, si votre mise en page existe déjà, utilisez la méthode `setWrap()` pour dicter comment les composants se comporteront une fois qu'ils ne pourront plus tenir sur une seule ligne.

### Espacement {#spacing}

Afin d'appliquer un espacement minimum entre les éléments, vous pouvez définir la propriété gap. Elle s'applique à cet espacement uniquement entre les éléments et non sur les bords extérieurs.

Le comportement de la propriété gap peut être considéré comme une distance minimale entre - cette propriété ne prendra effet que si c'est le plus grand espace calculé entre les éléments. Si l'espace entre les éléments serait autrement plus grand en raison d'une autre propriété calculée, par exemple en raison de `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, alors la propriété gap sera ignorée.

### Flux {#flow}

Le flux flex, qui est une combinaison des propriétés de direction et d'enveloppement, peut être défini à l'aide de la méthode `setFlow()` sur un objet Flex Layout.

:::info
Pour configurer cette propriété lors de la création de la mise en page, utilisez les méthodes directionnelles et d'enveloppement appropriées. Par exemple, pour créer un flux d'enveloppement en colonne, utilisez la combinaison `.vertical().wrap()`.
:::

### Constructeur de conteneur {#container-builder}

La démo suivante vous permet de construire un conteneur avec les propriétés flex souhaitées sélectionnées parmi les différents menus. Cet outil peut être utilisé non seulement pour créer un exemple visuel des différentes méthodes, mais également comme un outil pour créer vos propres mises en page avec les propriétés souhaitées. Pour utiliser une mise en page que vous avez personnalisée, il vous suffit de copier le code de sortie et d'ajouter vos éléments souhaités pour une utilisation dans votre programme.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

## Propriétés des éléments {#item-properties}

Les propriétés des éléments n'affecteront pas les éléments enfants au sein de la mise en page Flex, mais plutôt la mise en page réelle elle-même. Cela est utile pour styliser un élément Flex Layout unique qui est un enfant d'un élément Flex Layout plus grand indépendamment des styles s'appliquant à tous les enfants.

### Ordre {#order}

La propriété `ItemOrder` détermine comment les composants sont affichés au sein de la mise en page Flex, et lorsqu'elle est utilisée sur une mise en page Flex, attribuera un numéro d'ordre spécifique à cet élément de mise en page. Cela remplace le "source order" par défaut qui est intégré à chaque élément (l'ordre dans lequel un composant est ajouté à son parent), et signifie qu'il sera affiché avant les éléments avec un ordre plus élevé, et après les éléments avec un ordre plus bas.

Cette propriété accepte une valeur entière sans unité qui spécifie l'ordre relatif de l'élément flex au sein du conteneur. Plus la valeur est basse, plus l'élément apparaît tôt dans l'ordre. Par exemple, un élément avec une valeur d'ordre de 1 apparaîtra avant un élément avec une valeur d'ordre de 2.

:::caution
Il est important de noter que la propriété d'ordre n'affecte que l'ordre visuel des éléments au sein du conteneur, et non leur position réelle dans le DOM. Cela signifie que les lecteurs d'écran et autres technologies d'assistance liront toujours les éléments dans l'ordre dans lequel ils apparaissent dans le code source, et non dans l'ordre visuel.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto-Alignement {#self-alignment}

L'auto-alignement de Flex Layout se réfère à la façon dont un seul objet Flex Layout est aligné dans son conteneur parent selon l'axe transversal, qui est perpendiculaire à l'axe principal. L'alignement de l'axe transversal est contrôlé par la propriété `Alignment`.

La propriété align-self spécifie l'alignement d'un seul élément flex le long de l'axe transversal, remplaçant l'alignement par défaut défini par la propriété `AlignContent` dans un objet Flex Layout. Cela vous permet d'aligner des objets Flex Layout individuels différemment des autres dans le conteneur.

:::info
L'auto-alignement utilise les mêmes valeurs que l'alignement du contenu.
:::

Cette propriété est particulièrement utile lorsque vous devez aligner un élément spécifique différemment des autres éléments dans le conteneur. Voir l'exemple ci-dessous pour un exemple d'alignement d'un seul élément :

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Base Flex {#flex-basis}

`Item Basis` est une propriété utilisée en conjonction avec la direction de la mise en page Flex pour définir la taille initiale d'un élément flex avant que tout espace restant ne soit distribué.

La propriété `Item Basis` spécifie la taille par défaut d'un élément flex le long de l'axe principal, qui est soit horizontal (pour une direction Row) soit vertical (pour une direction Column). Cette propriété définit la largeur ou la hauteur d'un élément flex en fonction de la valeur de la propriété de direction flex.

:::info
Par défaut, la propriété `Item Basis` est définie sur auto, ce qui signifie que la taille de l'élément est déterminée par son contenu. Cependant, vous pouvez également définir une taille spécifique pour l'élément en utilisant diverses unités telles que les pixels (px), les ems (em), les pourcentages (%) ou toute autre unité de longueur CSS.
:::

La démo suivante vous permet de sélectionner une ou plusieurs cases et de changer la `Item Basis` pour les éléments sélectionnés.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex Grow / Shrink {#flex-grow--shrink}

`Item Grow` et `Item Shrink` sont deux propriétés qui fonctionnent ensemble et avec la propriété `Item Basis` pour déterminer comment les éléments flex grandissent ou rétrécissent pour remplir l'espace disponible au sein d'un objet Flex Layout.

La propriété `Item Grow` spécifie combien l'élément flex peut grandir par rapport aux autres éléments dans le conteneur. Elle prend une valeur sans unité qui représente une proportion de l'espace disponible qui devrait être alloué à l'élément. Par exemple, si un élément a une valeur `Item Grow` de 1 et qu'un autre a une valeur de 2, le deuxième élément grandira deux fois plus que le premier élément.

La propriété `Item Shrink`, en revanche, spécifie combien l'élément flex peut rétrécir par rapport aux autres éléments dans le conteneur. Elle prend également une valeur sans unité qui représente une proportion de l'espace disponible qui devrait être alloué à l'élément. Par exemple, si un élément a une valeur `Item Shrink` de 1 et qu'un autre a une valeur de 2, le deuxième élément rétrécira deux fois plus que le premier élément.

Lorsque le conteneur a plus d'espace que nécessaire pour accueillir son contenu, les éléments flex avec une valeur `Item Grow` supérieure à 0 s'étendront pour remplir l'espace disponible. La quantité d'espace que chaque élément obtient est déterminée par le rapport de sa valeur `Item Grow` à la valeur totale `Item Grow` de tous les éléments dans le conteneur.

De même, lorsque le conteneur n'a pas suffisamment d'espace pour accueillir son contenu, les éléments flex avec une valeur `Item Shrink` supérieure à 0 rétréciront pour s'adapter à l'espace disponible. La quantité d'espace que chaque élément abandonne est déterminée par le rapport de sa valeur `Item Shrink` à la valeur totale `Item Shrink` de tous les éléments dans le conteneur.

## Formulaire d'exemple {#example-form}
Le formulaire ci-dessous démontre comment `FlexLayout` organise les champs de saisie en une mise en page structurée.

:::tip
Si vous préférez une structure basée sur des colonnes, consultez la version ColumnsLayout de ce formulaire dans l'article [`ColumnsLayout`](../components/columns-layout) pour voir comment elle se compare.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
