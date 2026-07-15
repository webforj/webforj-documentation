---
title: FlexLayout
sidebar_position: 45
description: >-
  Arrange children in rows or columns with the FlexLayout component, controlling
  direction, justification, alignment, wrapping, and growth.
_i18n_hash: cd16392e244062d863d403e50cc56ddd
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

Le composant `FlexLayout` organise les composants enfants en ligne ou en colonne en utilisant le modèle CSS Flexbox. Il vous permet de contrôler l'alignement, l'espacement, le passage à la ligne et comment les éléments grandissent ou rétrécissent pour remplir l'espace disponible.

<!-- INTRO_END -->

## Propriétés de `FlexLayout` {#flex-layout-properties}

Les propriétés de `FlexLayout` peuvent être regroupées en deux catégories : les propriétés qui s'appliquent aux éléments à l'intérieur d'une mise en page et les propriétés qui s'appliquent à la mise en page elle-même. Le `FlexLayout`, ou l'élément parent, est une boîte/conteneur qui peut contenir un ou plusieurs composants. Tout ce qui est à l'intérieur d'un `FlexLayout` est appelé un élément ou un enfant. Le `FlexLayout` fournit certaines capacités d'alignement, qui peuvent être réalisées avec l'aide des propriétés de conteneur ou d'élément.

:::tip
Le composant `FlexLayout` suit le modèle de mise en page à flexbox de [CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Cependant, `FlexLayout` est conçu pour être utilisé entièrement en Java et ne nécessite pas l'utilisation de CSS en dehors des méthodes API Java fournies.
:::

## Propriétés du conteneur {#container-properties}

Les propriétés du conteneur s'appliqueront à tous les composants d'un composant et non à la mise en page elle-même. Elles n'affecteront pas l'orientation ou le placement du parent, seulement les composants enfants à l'intérieur.

### Direction {#direction}

Le `FlexLayout` ajoute des composants les uns à côté des autres selon sa direction, soit horizontale, soit verticale. Lors de l'utilisation du constructeur, enchaînez les méthodes `horizontal()`, `horizontalReverse()`, `vertical()`, ou `verticalReverse()` avec la méthode `FlexLayout.create()` pour configurer la mise en page lorsque l'objet est créé.

Pour définir la direction sur un objet `FlexLayout` existant, utilisez la méthode `setDirection()`. Les options horizontales sont `FlexDirection.ROW` (de gauche à droite) ou `FlexDirection.ROW_REVERSE` (de droite à gauche), et les options verticales sont `FlexDirection.COLUMN` (de haut en bas) ou `FlexDirection.COLUMN_REVERSE` (de bas en haut).

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Positionnement {#positioning}

Les composants ajoutés horizontalement peuvent également être positionnés à la fois horizontalement et verticalement. Utilisez les méthodes `justify()`, `align()` et `contentAlign()` du constructeur `FlexLayout` pour configurer le positionnement lors de la création d'un nouveau `FlexLayout`.

Alternativement, sur l'objet `FlexLayout` réel, vous pouvez utiliser la méthode `setJustifyContent()` pour positionner les éléments horizontalement, et la méthode `setAlignment()` pour configurer le positionnement vertical. Pour modifier l'espace autour des composants le long de l'axe transversal (l'axe y pour les mises en page horizontales), utilisez la méthode `setAlignContent()`.

:::tip
La méthode `setAlignment()` contrôle comment les éléments s'affichent le long de l'axe transversal dans son ensemble au sein du conteneur, et est efficace pour les mises en page à une seule ligne.

La méthode `setAlignContent()` contrôle l'espace autour de l'axe transversal, et n'a d'effet que lorsqu'une mise en page a plusieurs lignes.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Enveloppement {#wrapping}

Pour personnaliser davantage le composant `FlexLayout`, vous pouvez spécifier son comportement lorsque des composants sont ajoutés et ne s'intègrent plus dans l'affichage. Pour configurer cela en utilisant le constructeur, utilisez les méthodes `nowrap()` (par défaut), `wrap()`, et `wrapReverse()` pour configurer l'enveloppement. Pour configurer cela sur un objet `FlexLayout` existant, utilisez la méthode `setWrap()`.

### Espacement {#spacing}

Afin d'appliquer un espacement minimum entre les éléments, vous pouvez définir la propriété `gap`. Cela applique cet espacement uniquement entre les éléments, pas sur les bords extérieurs.

Le comportement de la propriété gap peut être considéré comme une distance minimale entre les éléments, elle n'aura donc d'effet que si elle est la plus grande distance calculée entre les éléments. Si l'espace entre les éléments serait autrement plus grand en raison d'une autre propriété calculée, comme `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, alors la propriété gap sera ignorée.

### Flux {#flow}

Le flux flex, qui est une combinaison des propriétés direction et enveloppe, peut être défini en utilisant la méthode `setFlow()` sur un objet `FlexLayout`.

:::info
Pour configurer cette propriété lors de la création de la mise en page, utilisez les méthodes de direction et d'enveloppe appropriées. Par exemple, pour créer un flux d'enveloppement en colonne, utilisez la combinaison `.vertical().wrap()`.
:::

### Constructeur de conteneur {#container-builder}

La démo suivante vous permet de construire un conteneur avec les propriétés flex souhaitées sélectionnées dans divers menus. Cet outil peut être utilisé non seulement pour créer un exemple visuel des différentes méthodes, mais aussi pour créer vos propres mises en page avec vos propriétés désirées. Pour utiliser une mise en page que vous personnalisez, il suffit de copier le code de sortie et d'ajouter vos éléments désirés pour les utiliser dans votre programme.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Propriétés des éléments {#item-properties}

Les propriétés des éléments n'affecteront aucun élément enfant au sein du `FlexLayout`, mais affectent la mise en page elle-même. Cela est utile pour styliser un seul élément `FlexLayout` qui est un enfant d'un élément `FlexLayout` plus grand, indépendamment des styles appliqués à tous les enfants.

### Ordre {#order}

La propriété `ItemOrder` détermine l'ordre dans lequel les composants sont affichés au sein du `FlexLayout`, et lorsqu'elle est utilisée sur un `FlexLayout`, elle attribuera un numéro d'ordre spécifique à cet élément de mise en page. Cela remplace l'ordre "source" par défaut intégré dans chaque élément (l'ordre dans lequel un composant est ajouté à son parent), et signifie qu'il sera affiché avant les éléments avec un ordre plus élevé et après les éléments avec un ordre plus bas.

Cette propriété accepte une valeur entière sans unité qui spécifie l'ordre relatif de l'élément flex au sein du conteneur. Plus la valeur est basse, plus l'élément apparaît tôt dans l'ordre. Par exemple, un élément avec une valeur d'ordre de 1 apparaîtra avant un élément avec une valeur d'ordre de 2.

:::caution
Il est important de noter que la propriété d'ordre n'affecte que l'ordre visuel des éléments au sein du conteneur, et non leur position réelle dans le DOM. Cela signifie que les lecteurs d'écran et autres technologies d'assistance liront toujours les éléments dans l'ordre dans lequel ils apparaissent dans le code source, et non dans l'ordre visuel.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Auto-alignement {#self-alignment}

L'auto-alignement de `FlexLayout` fait référence à la façon dont un seul objet `FlexLayout` est aligné au sein de son conteneur flex parent le long de l'axe transversal, qui est perpendiculaire à l'axe principal. L'alignement de l'axe transversal est contrôlé par la propriété `Alignment`.

La propriété align-self spécifie l'alignement d'un seul élément flex le long de l'axe transversal, remplaçant l'alignement par défaut défini par la propriété `AlignContent` dans un objet `FlexLayout`. Cela vous permet d'aligner des objets `FlexLayout` individuels différemment des autres dans le conteneur.

:::info
L'auto-alignement utilise les mêmes valeurs que l'alignement de contenu.
:::

Cette propriété est particulièrement utile lorsque vous devez aligner un élément spécifique différemment des autres éléments dans le conteneur. Voir l'exemple ci-dessous pour un exemple d'alignement d'un seul élément :

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Base flex {#flex-basis}

`Item Basis` est une propriété utilisée en conjonction avec la direction de `FlexLayout` pour définir la taille initiale d'un élément flex avant que tout l'espace restant soit distribué.

La propriété `Item Basis` spécifie la taille par défaut d'un élément flex le long de l'axe principal, qui est soit horizontal (pour une direction de ligne) ou vertical (pour une direction de colonne). Cette propriété définit la largeur ou la hauteur d'un élément flex en fonction de la valeur de la propriété de direction flex.

:::info
Par défaut, la propriété `Item Basis` est définie sur `auto`, ce qui signifie que la taille de l'élément est déterminée par son contenu. Cependant, vous pouvez également définir une taille spécifique pour l'élément en utilisant diverses unités comme pixels (px), ems (em), pourcentages (%), ou toute autre unité de longueur CSS.
:::

La démo suivante vous permet de sélectionner une ou plusieurs cases et de changer la `Base Item` pour les éléments sélectionnés.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Croissance et rétrécissement flex {#flex-grow--shrink}

`Item Grow` et `Item Shrink` sont deux propriétés qui fonctionnent en conjonction l'une avec l'autre et avec la propriété `Item Basis` pour déterminer comment les éléments flex grandissent ou rétrécissent pour remplir l'espace disponible dans un objet `FlexLayout`.

La propriété `Item Grow` spécifie combien l'élément flex peut grandir par rapport aux autres éléments dans le conteneur. Elle prend une valeur sans unité qui représente une proportion de l'espace disponible qui devrait être attribuée à l'élément. Par exemple, si un élément a une valeur `Item Grow` de 1 et un autre une valeur de 2, le deuxième élément grandira deux fois plus que le premier élément.

La propriété `Item Shrink`, quant à elle, spécifie combien l'élément flex peut rétrécir par rapport aux autres éléments dans le conteneur. Elle prend également une valeur sans unité qui représente une proportion de l'espace disponible qui devrait être attribuée à l'élément. Par exemple, si un élément a une valeur `Item Shrink` de 1 et un autre une valeur de 2, le deuxième élément rétrécira deux fois plus que le premier élément.

Lorsqu'un conteneur dispose de plus d'espace que nécessaire pour accueillir son contenu, les éléments flex avec une valeur `Item Grow` supérieure à 0 s'élargiront pour remplir l'espace disponible. La quantité d'espace que chaque élément obtient est déterminée par le rapport entre sa valeur `Item Grow` et la valeur totale `Item Grow` de tous les éléments dans le conteneur.

De même, lorsqu'un conteneur n'a pas suffisamment d'espace pour accueillir son contenu, les éléments flex avec une valeur `Item Shrink` supérieure à 0 rétréciront pour s'adapter à l'espace disponible. La quantité d'espace que chaque élément abandonne est déterminée par le rapport entre sa valeur `Item Shrink` et la valeur totale `Item Shrink` de tous les éléments dans le conteneur.

## Formulaire d'exemple {#example-form}
Le formulaire ci-dessous démontre comment `FlexLayout` organise les champs de saisie en une mise en page structurée.

:::tip
Si vous préférez une structure basée sur les colonnes, jetez un œil à la version `ColumnsLayout` de ce formulaire dans l'article [`ColumnsLayout`](../components/columns-layout) pour voir comment elle se compare.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/frontend/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
