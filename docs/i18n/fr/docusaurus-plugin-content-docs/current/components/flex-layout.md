---
title: FlexLayout
sidebar_position: 45
_i18n_hash: bd3f6177603a98c20d4958a9c40dd49f
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ fournit aux développeurs un moyen efficace et intuitif de mettre en page leurs diverses applications et composants - le Flex Layout. Cet ensemble d'outils permet d'afficher des éléments soit verticalement, soit horizontalement.

## Propriétés de mise en page flex {#flex-layout-properties}

Les propriétés de mise en page flex peuvent être regroupées en deux catégories : les propriétés qui s'appliquent aux éléments d'une mise en page et les propriétés qui s'appliquent à la mise en page elle-même. La mise en page flex, ou l'élément parent, est une boîte/conteneur qui peut contenir un ou plusieurs composants. Tout ce qui se trouve à l'intérieur d'un Flex Layout est appelé un élément ou un enfant. Le Flex Layout offre des capacités d'alignement robustes, qui peuvent être réalisées avec l'aide de propriétés de conteneur ou d'éléments.

:::tip
Le composant de mise en page de webforJ suit le modèle de [la mise en page flexbox CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Cependant, ces outils sont conçus pour être utilisés totalement en Java, et ne nécessitent pas l'application de CSS en dehors des méthodes API Java fournies.
:::

## Propriétés de conteneur {#container-properties}

Les propriétés de conteneur s'appliqueront à tous les composants d'un composant et non à la mise en page elle-même. Elles n'affecteront pas l'orientation ou le placement du parent - seulement les composants enfants.

### Direction {#direction}

Le Flex Layout ajoutera des composants les uns à côté des autres selon la direction choisie par le développeur - soit horizontale, soit verticale. Lors de l'utilisation du constructeur, utilisez soit les méthodes `horizontal()`, `horizontalReverse()`, `vertical()` ou `verticalReverse()` en appelant la méthode `create()` sur un objet `FlexLayout` pour configurer cette mise en page au moment de la création de l'objet.

Alternativement, utilisez la méthode `setDirection()`. Les options horizontales sont soit `FlexDirection.ROW` (de gauche à droite) soit `FlexDirection.ROW_REVERSE` (de droite à gauche), et les options verticales sont soit `FlexDirection.COLUMN` (de haut en bas) soit `FlexDirection.COLUMN_REVERSE` (de bas en haut). Cela se fait avec l'objet FlexLayout, contrairement au constructeur.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionnement {#positioning}

Les composants ajoutés horizontalement peuvent également être positionnés à la fois horizontalement et verticalement. Utilisez les méthodes `justify()`, `align()` et `contentAlign()` du Flex Layout Builder pour configurer le positionnement lors de la création d'un nouveau Flex Layout.

Alternativement, sur l'objet FlexLayout réel, vous pouvez utiliser la méthode `setJustifyContent()` pour positionner les éléments horizontalement, et la méthode `setAlignment()` pour configurer le positionnement vertical. Pour modifier la zone autour des composants le long de l'axe croisé (axe y pour les mises en page horizontales), utilisez la méthode `setAlignContent()`.

:::tip
La méthode `setAlignment()` configure comment les éléments s'afficheront le long de l'axe croisé dans son ensemble au sein du conteneur, et est efficace pour les mises en page à une seule ligne.

Les méthodes `setAlignContent()` configurent l'espace autour de l'axe croisé, et n'auront d'effet que lorsqu'une mise en page a plusieurs lignes.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Enveloppement {#wrapping}

Pour personnaliser davantage le composant Flex Layout, vous pouvez spécifier le comportement de la mise en page flex lorsque les composants ajoutés ne rentrent plus dans l'affichage. Pour configurer cela à l'aide du constructeur, utilisez les méthodes - `nowrap()` (par défaut), `wrap()` et `wrapReverse()` pour configurer l'enveloppement.

Alternativement, si votre mise en page existe déjà, utilisez la méthode `setWrap()` pour dicter comment les composants se comporteront une fois qu'ils ne pourront plus tenir sur une seule ligne.

### Espacement {#spacing}

Afin d'appliquer un espacement minimum entre les éléments, vous pouvez définir la propriété gap. Elle applique cet espacement uniquement entre les éléments, pas sur les bords extérieurs.

Le comportement de la propriété gap peut être considéré comme une distance minimale entre - cette propriété n'aura d'effet que si elle est l'espace calculé le plus important entre les éléments. Si l'espace entre les éléments serait autrement plus grand en raison d'une autre propriété calculée, par exemple en raison de `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, alors la propriété gap sera ignorée.

### Flux {#flow}

Le flux flex, qui est une combinaison des propriétés de direction et d'enveloppement, peut être défini à l'aide de la méthode `setFlow()` sur un objet Flex Layout.

:::info
Pour configurer cette propriété lors de la création de la mise en page, utilisez les méthodes directionnelles et d'enveloppement appropriées. Par exemple, pour créer un flux d'enveloppement en colonne, utilisez la combinaison `.vertical().wrap()`.
:::

### Constructeur de conteneur {#container-builder}

La démo suivante vous permet de construire un conteneur avec les propriétés flex souhaitées sélectionnées parmi les différents menus. Cet outil peut être utilisé non seulement pour créer un exemple visuel des différentes méthodes, mais aussi comme un outil pour créer vos propres mises en page avec les propriétés désirées. Pour utiliser une mise en page que vous personnalisez, il vous suffit de copier le code de sortie et d’ajouter vos éléments souhaités pour utilisation dans votre programme.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

## Propriétés d'élément {#item-properties}

Les propriétés d'élément n'affecteront aucun élément enfant au sein du Flex Layout, mais plutôt la mise en page elle-même. Cela est utile pour styliser un seul élément de Flex Layout qui est un enfant d'un élément Flex Layout plus grand, indépendamment des styles s'appliquant à tous les enfants.

### Ordre {#order}

La propriété `ItemOrder` détermine comment les composants sont affichés dans le Flex Layout, et lorsqu'elle est utilisée sur un Flex Layout, elle attribuera à un élément le numéro de commande spécifique de cette mise en page. Cela écrase l'ordre "source" par défaut qui est intégré à chaque élément (l'ordre dans lequel un composant est ajouté à son parent), et signifie qu'il sera affiché avant les éléments avec un ordre plus élevé, et après les éléments avec un ordre plus bas.

Cette propriété accepte une valeur entière sans unité qui spécifie l'ordre relatif de l'élément flex dans le conteneur. Plus la valeur est basse, plus l'élément apparaît tôt dans l'ordre. Par exemple, un élément avec une valeur d'ordre de 1 apparaîtra avant un élément avec une valeur d'ordre de 2.

:::caution
Il est important de noter que la propriété d'ordre n'affecte que l'ordre visuel des éléments dans le conteneur, pas leur position réelle dans le DOM. Cela signifie que les lecteurs d'écran et autres technologies d'assistance liront toujours les éléments dans l'ordre où ils apparaissent dans le code source, pas dans l'ordre visuel.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Auto-alignement {#self-alignment}

L'auto-alignement de Flex Layout fait référence à la façon dont un seul objet Flex Layout est aligné au sein de son conteneur flex parent le long de l'axe croisé, qui est perpendiculaire à l'axe principal. L'alignement sur l'axe croisé est contrôlé par la propriété `Alignment`.

La propriété align-self spécifie l'alignement d'un seul élément flex le long de l'axe croisé, écrasant l'alignement par défaut défini par la propriété `AlignContent` dans un objet Flex Layout. Cela vous permet d'aligner des objets Flex Layout individuels différemment des autres dans le conteneur.

:::info
L'auto-alignement utilise les mêmes valeurs que l'alignement de contenu.
:::

Cette propriété est particulièrement utile lorsque vous devez aligner un élément spécifique différemment des autres éléments du conteneur. Voir l'exemple ci-dessous pour un exemple d'alignement d'un seul élément :

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Base flex {#flex-basis}

`Item Basis` est une propriété qui est utilisée en conjonction avec la direction du Flex Layout pour définir la taille initiale d'un élément flex avant que tout l'espace restant ne soit distribué.

La propriété `Item Basis` spécifie la taille par défaut d'un élément flex le long de l'axe principal, qui est soit horizontal (pour une direction de ligne) soit vertical (pour une direction de colonne). Cette propriété définit la largeur ou la hauteur d'un élément flex en fonction de la valeur de la propriété de direction flex.

:::info
Par défaut, la propriété `Item Basis` est définie sur auto, ce qui signifie que la taille de l'élément est déterminée par son contenu. Cependant, vous pouvez également définir une taille spécifique pour l'élément en utilisant diverses unités telles que les pixels (px), les ems (em), les pourcentages (%) ou toute autre unité de longueur CSS.
:::

La démo suivante vous permet de sélectionner une ou plusieurs cases et de changer la `Item Basis` pour les éléments sélectionnés.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex grow / shrink {#flex-grow--shrink}

`Item Grow` et `Item Shrink` sont deux propriétés qui fonctionnent en conjonction l'une avec l'autre et avec la propriété `Item Basis` pour déterminer comment les éléments flex grandissent ou diminuent pour remplir l'espace disponible à l'intérieur d'un objet Flex Layout.

La propriété `Item Grow` spécifie dans quelle mesure l'élément flex peut croître par rapport aux autres éléments du conteneur. Elle prend une valeur sans unité qui représente une proportion de l'espace disponible qui doit être allouée à l'élément. Par exemple, si un élément a une valeur `Item Grow` de 1 et un autre a une valeur de 2, le second élément grandira deux fois plus que le premier élément.

La propriété `Item Shrink`, quant à elle, spécifie dans quelle mesure l'élément flex peut diminuer par rapport aux autres éléments du conteneur. Elle prend également une valeur sans unité qui représente une proportion de l'espace disponible qui doit être allouée à l'élément. Par exemple, si un élément a une valeur `Item Shrink` de 1 et un autre a une valeur de 2, le second élément rétrécira deux fois plus que le premier élément.

Lorsqu'un conteneur a plus d'espace que nécessaire pour accueillir son contenu, les éléments flex avec une valeur `Item Grow` supérieure à 0 s'étendront pour remplir l'espace disponible. La quantité d'espace que chaque élément obtient est déterminée par le rapport de sa valeur `Item Grow` à la valeur totale `Item Grow` de tous les éléments du conteneur.

De même, lorsque un conteneur n'a pas assez d'espace pour accueillir son contenu, les éléments flex avec une valeur `Item Shrink` supérieure à 0 rétréciront pour s'adapter à l'espace disponible. La quantité d'espace que chaque élément abandonne est déterminée par le rapport de sa valeur `Item Shrink` à la valeur totale `Item Shrink` de tous les éléments du conteneur.

## Formulaire d'exemple {#example-form}
Le formulaire ci-dessous démontre comment `FlexLayout` organise des champs de saisie en une mise en page structurée.

:::tip
Si vous préférez une structure basée sur des colonnes, consultez la version ColumnsLayout de ce formulaire dans l'article [`ColumnsLayout`](../components/columns-layout) pour voir comment cela se compare.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
