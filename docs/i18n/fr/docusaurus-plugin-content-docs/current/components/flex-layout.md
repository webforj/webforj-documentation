---
title: FlexLayout
sidebar_position: 45
_i18n_hash: 5c12042a5890f07259e77e0d2111a5c6
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

Le composant `FlexLayout` organise les composants enfants en une rangée ou une colonne en utilisant le modèle CSS Flexbox. Il vous permet de contrôler l'alignement, l'espacement, le retour à la ligne, et comment les éléments grandissent ou rétrécissent pour remplir l'espace disponible.

<!-- INTRO_END -->

## Propriétés de la mise en page flexible {#flex-layout-properties}

Les propriétés de la mise en page flexible peuvent être regroupées en deux catégories : les propriétés qui s'appliquent aux éléments au sein d'une mise en page, et les propriétés qui s'appliquent à la mise en page elle-même. La mise en page flexible, ou l'élément parent, est une boîte/conteneur qui peut contenir un ou plusieurs composants. Tout ce qui se trouve à l'intérieur d'un Flex Layout est appelé un élément ou un élément enfant. Le Flex Layout offre des capacités d'alignement robustes, qui peuvent être atteintes grâce à l'aide des propriétés de conteneur ou d'élément.

:::tip
Le composant de mise en page de webforJ suit le modèle de la [mise en page flexbox de CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Cependant, ces outils sont conçus pour être utilisés pleinement en Java, et ne nécessitent pas l'application de CSS en dehors des méthodes API Java fournies.
:::

## Propriétés de conteneur {#container-properties}

Les propriétés de conteneur s'appliqueront à tous les composants au sein d'un composant et non à la mise en page elle-même. Elles n'affecteront pas l'orientation ou le placement du parent - seulement les composants enfants à l'intérieur.

### Direction {#direction}

Le Flex Layout ajoutera des composants les uns à côté des autres selon la direction choisie par le développeur - soit horizontale soit verticale. Lors de l'utilisation du constructeur, utilisez soit les méthodes `horizontal()`, `horizontalReverse()`, `vertical()` ou `verticalReverse()` lors de l'appel de la méthode `create()` sur un objet `FlexLayout` pour configurer cette mise en page lors de la création de l'objet.

Alternativement, utilisez la méthode `setDirection()`. Les options horizontales sont soit `FlexDirection.ROW` (de gauche à droite) ou `FlexDirection.ROW_REVERSE` (de droite à gauche), et les options verticales sont soit `FlexDirection.COLUMN` (de haut en bas) ou `FlexDirection.COLUMN_REVERSE` (de bas en haut). Cela se fait avec l'objet FlexLayout, contrairement au constructeur.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionnement {#positioning}

Les composants qui sont ajoutés horizontalement peuvent également être positionnés à la fois horizontalement et verticalement. Utilisez les méthodes `justify()`, `align()` et `contentAlign()` du constructeur Flex Layout pour configurer le positionnement lors de la création d'un nouveau Flex Layout.

Alternativement, sur l'objet FlexLayout lui-même, vous pouvez utiliser la méthode `setJustifyContent()` pour positionner les éléments horizontalement, et la méthode `setAlignment()` pour configurer le positionnement vertical. Pour modifier l'espace autour des composants le long de l'axe croisé (axe y pour les mises en page horizontales), utilisez la méthode `setAlignContent()`.

:::tip
La méthode `setAlignment()` détermine comment les éléments s'affichent le long de l'axe croisé dans son ensemble au sein du conteneur, et est efficace pour les mises en page à ligne unique.

Les méthodes `setAlignContent()` déterminent l'espace autour de l'axe croisé, et ne prendront effet que lorsqu'une mise en page a plusieurs lignes.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Retour à la ligne {#wrapping}

Pour personnaliser davantage le composant Flex Layout, vous pouvez spécifier le comportement de la mise en page flexible lorsque des composants ajoutés ne s'adaptent plus à l'affichage. Pour configurer cela à l'aide du constructeur, utilisez les méthodes - `nowrap()` (par défaut), `wrap()` et `wrapReverse()` pour configurer le retour à la ligne.

Alternativement, si votre mise en page existe déjà, utilisez la méthode `setWrap()` pour dicter comment les composants se comporteront une fois qu'ils ne seront plus en mesure de s'adapter à une seule ligne.

### Espacement {#spacing}

Afin d'appliquer un espacement minimum entre les éléments, vous pouvez définir la propriété gap. Elle applique cet espacement uniquement entre les éléments et non sur les bords extérieurs. 

Le comportement de la propriété gap peut être considéré comme une distance minimale entre - cette propriété ne prendra effet que si c'est l'espace calculé le plus grand entre les éléments. Si l'espace entre les éléments serait autrement plus grand en raison d'une autre propriété calculée, telle que `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, alors la propriété gap sera ignorée.

### Flux {#flow}

Le flux flexible, qui est une combinaison des propriétés de direction et de retour à la ligne, peut être défini à l'aide de la méthode `setFlow()` sur un objet Flex Layout. 

:::info
Pour configurer cette propriété lors de la création de la mise en page, utilisez les méthodes directionnelles et de retour appropriées. Par exemple, pour créer un flux de retour à la ligne de colonnes, utilisez la combinaison `.vertical().wrap()`.
:::

### Constructeur de conteneur {#container-builder}

La démo suivante vous permet de construire un conteneur avec les propriétés flex souhaitées sélectionnées à partir des différents menus. Cet outil peut être utilisé non seulement pour créer un exemple visuel des différentes méthodes, mais aussi comme outil pour créer vos propres mises en page avec vos propriétés désirées. Pour utiliser une mise en page que vous personnalisez, il vous suffit de copier le code de sortie et d'ajouter vos éléments souhaités pour une utilisation dans votre programme.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propriétés des éléments {#item-properties}

Les propriétés des éléments n'affecteront aucun élément enfant dans le Flex Layout, mais plutôt le Flex Layout lui-même. Cela est utile pour styliser un seul élément Flex Layout qui est un enfant d'un élément Flex Layout plus grand indépendamment des styles s'appliquant à tous les enfants.

### Ordre {#order}

La propriété `ItemOrder` détermine comment les composants sont affichés dans le Flex Layout, et lorsqu'elle est utilisée sur un Flex Layout, elle assignera à un élément le numéro d'ordre spécifique de cette mise en page. Cela remplace l' "ordre source" par défaut qui est intégré à chaque élément (l'ordre dans lequel un composant est ajouté à son parent), et signifie qu'il sera affiché avant des éléments avec un ordre plus élevé, et après des éléments avec un ordre plus faible.

Cette propriété accepte une valeur entière sans unité qui spécifie l'ordre relatif de l'élément flexible dans le conteneur. Plus la valeur est basse, plus l'élément apparaît tôt dans l'ordre. Par exemple, un élément avec une valeur d'ordre de 1 apparaîtra avant un élément avec une valeur d'ordre de 2.

:::caution
Il est important de noter que la propriété d'ordre n'affecte que l'ordre visuel des éléments dans le conteneur, pas leur position réelle dans le DOM. Cela signifie que les lecteurs d'écran et autres technologies d'assistance liront toujours les éléments dans l'ordre dans lequel ils apparaissent dans le code source, et non dans l'ordre visuel.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto-Alignement {#self-alignment}

L'auto-alignement du Flex Layout fait référence à la façon dont un objet Flex Layout est aligné dans son conteneur parent flexible le long de l'axe croisé, qui est perpendiculaire à l'axe principal. L'alignement de l'axe croisé est contrôlé par la propriété `Alignment`.

La propriété align-self spécifie l'alignement d'un seul élément flexible le long de l'axe croisé, remplaçant l'alignement par défaut défini par la propriété `AlignContent` dans un objet Flex Layout. Cela vous permet d'aligner des objets Flex Layout individuels différemment des autres dans le conteneur.

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

### Base flexible {#flex-basis}

La `Base d'élément` est une propriété qui est utilisée en conjonction avec la direction de Flex Layout pour définir la taille initiale d'un élément flexible avant que tout l'espace restant ne soit distribué.

La propriété `Base d'élément` spécifie la taille par défaut d'un élément flexible le long de l'axe principal, qui est soit horizontal (pour une direction de rangée) ou vertical (pour une direction de colonne). Cette propriété définit la largeur ou la hauteur d'un élément flexible selon la valeur de la propriété de direction flexible.

:::info
Par défaut, la propriété `Base d'élément` est définie sur auto, ce qui signifie que la taille de l'élément est déterminée par son contenu. Cependant, vous pouvez également définir une taille spécifique pour l'élément en utilisant diverses unités telles que des pixels (px), des ems (em), des pourcentages (%) ou toute autre unité de longueur CSS.
:::

La démo suivante vous permet de sélectionner une ou plusieurs boîtes et de changer la `Base d'élément` pour les éléments sélectionnés.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Croître / Rétrécir flexible {#flex-grow--shrink}

La `Croissance d'élément` et la `Réduction d'élément` sont deux propriétés qui fonctionnent en conjonction l'une avec l'autre et avec la propriété `Base d'élément` pour déterminer comment les éléments flexibles grandissent ou rétrécissent pour remplir l'espace disponible dans un objet Flex Layout.

La propriété `Croissance d'élément` spécifie combien l'élément flexible peut croître par rapport aux autres éléments dans le conteneur. Elle prend une valeur sans unité qui représente une proportion de l'espace disponible qui doit être allouée à l'élément. Par exemple, si un élément a une valeur de `Croissance d'élément` de 1 et un autre a une valeur de 2, le second élément grandira deux fois plus que le premier élément.

La propriété `Réduction d'élément`, quant à elle, spécifie combien l'élément flexible peut rétrécir par rapport aux autres éléments dans le conteneur. Elle prend également une valeur sans unité qui représente une proportion de l'espace disponible qui doit être allouée à l'élément. Par exemple, si un élément a une valeur de `Réduction d'élément` de 1 et un autre a une valeur de 2, le second élément rétrécira deux fois plus que le premier élément.

Lorsque un conteneur a plus d'espace que ce qui est nécessaire pour accueillir son contenu, les éléments flexibles avec une valeur de `Croissance d'élément` supérieure à 0 s'étendront pour remplir l'espace disponible. La quantité d'espace que chaque élément obtient est déterminée par le ratio de sa valeur de `Croissance d'élément` par rapport à la valeur totale de `Croissance d'élément` de tous les éléments dans le conteneur.

De même, lorsqu'un conteneur n'a pas assez d'espace pour accueillir son contenu, les éléments flexibles avec une valeur de `Réduction d'élément` supérieure à 0 rétréciront pour s'adapter à l'espace disponible. La quantité d'espace que chaque élément abandonne est déterminée par le ratio de sa valeur de `Réduction d'élément` par rapport à la valeur totale de `Réduction d'élément` de tous les éléments dans le conteneur.

## Exemple de formulaire {#example-form}
Le formulaire ci-dessous démontre comment `FlexLayout` organise les champs de saisie dans une mise en page structurée. 

:::tip
Si vous préférez une structure basée sur des colonnes, consultez la version ColumnsLayout de ce formulaire dans l'article [`ColumnsLayout`](../components/columns-layout) pour voir comment elle se compare.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
