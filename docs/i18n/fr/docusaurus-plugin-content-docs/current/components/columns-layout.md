---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 27b0727ced855ad047db6be3e142801f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Le composant `ColumnsLayout` arrange les éléments dans une mise en page réactive basée sur des colonnes qui s'ajuste en fonction de la largeur disponible. Les points d'arrêt et les alignements sont gérés automatiquement, de sorte que la construction de formulaires multicolonnes et de grilles de contenu ne nécessite pas de logique réactive personnalisée.

<!-- INTRO_END -->

## Comportement par défaut {#default-behavior}

Par défaut, un `ColumnsLayout` arrange les éléments en deux colonnes et occupe toute la largeur de son parent. L'affichage peut être davantage ajusté avec des points d'arrêt et des réglages d'alignement, couverts dans les sections ci-dessous.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Dispositions horizontales 
Cela peut être utilisé à la place, ou en combinaison avec, le composant [`FlexLayout`](./flex-layout) - un outil tout aussi puissant pour les mises en page horizontales.
:::

## Points d'arrêt {#breakpoints}

À sa base, le `ColumnsLayout` est conçu pour fournir un système fluide, semblable à une grille qui s'adapte à la largeur de son conteneur parent. Contrairement aux systèmes de grille fixes traditionnels, cette mise en page permet aux développeurs de spécifier un nombre de colonnes à une largeur donnée, et calcule dynamiquement le nombre de colonnes affichées en fonction des objets `Breakpoint` définis.

Cela permet à un `ColumnsLayout` de s'adapter en douceur d'un espace plus contraint sur les petits écrans à une zone plus large sur les grands écrans, offrant un design réactif à un développeur sans nécessiter d'implémentation personnalisée.

### Comprendre un `Breakpoint` {#understanding-a-breakpoint}

Un `Breakpoint` peut être spécifié en utilisant la classe `Breakpoint`, qui prend trois paramètres :

1. **Nom (facultatif)** :
Nommer un point d'arrêt vous permet de le référencer dans de futures configurations.

2. **Largeur minimale** :
Chaque point d'arrêt a une plage spécifique qui détermine quand sa mise en page est appliquée. La largeur minimale est définie explicitement, et le point d'arrêt suivant détermine la largeur maximale si elle existe. Vous pouvez utiliser un entier pour définir la largeur minimale en pixels ou utiliser une `String` pour spécifier d'autres unités telles que `vw`, `%` ou `em`.

3. **Nombre de colonnes** :
Indiquez combien de colonnes un point d'arrêt doit avoir avec cet entier.

:::info Évaluation du `Breakpoint`
Les points d'arrêt sont évalués dans l'ordre croissant de la largeur, ce qui signifie que la mise en page utilisera le premier point d'arrêt correspondant.
:::


### Application des points d'arrêt {#applying-breakpoints}

Les points d'arrêt sont appliqués à un `ColumnsLayout` de deux manières : pendant la construction ou dans une `List` en utilisant la méthode `setBreakpoints()` : 

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Une colonne à des largeurs >= 0px
  new Breakpoint(0, 1),
  // Deux colonnes à des largeurs >= 600px
  new Breakpoint(600, 2),
  // Quatre colonnes à des largeurs >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

La démonstration ci-dessous montre un exemple de définition de plusieurs points d'arrêt à la construction, utilisant les points d'arrêt pour configurer le [`Span`](#column-span-and-spans-per-breakpoint) d'un composant, et démontre les capacités de redimensionnement du `ColumnsLayout` lorsque l'application est redimensionnée :

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## `Span` de colonne et spans par `Breakpoint` {#column-span-and-spans-per-breakpoint}

Les spans de colonne dans `ColumnsLayout` vous permettent de contrôler combien de colonnes un élément occupe, vous donnant plus de contrôle sur l'apparence de votre mise en page à différentes largeurs. Cela est particulièrement utile lorsque vous avez besoin que certains éléments prennent plus ou moins d'espace en fonction de la taille de l'écran ou des exigences de design.

### Gestion des spans de colonne {#managing-column-spans}

Par défaut, chaque élément dans le `ColumnsLayout` occupe exactement une colonne. Cependant, vous pouvez personnaliser ce comportement en définissant le span de colonne pour des éléments individuels. Un span spécifie le nombre de colonnes qu'un élément doit occuper.

```java
Button button = new Button("Cliquez Moi");
layout.addComponent(button);
// L'élément s'étend sur deux colonnes
layout.setSpan(button, 2);
```

Dans l'exemple ci-dessus, le bouton occupe deux colonnes au lieu de la colonne par défaut. La méthode `setSpan()` vous permet de spécifier combien de colonnes un composant doit s'étendre dans la mise en page.

### Ajustement des spans de colonne avec des points d'arrêt {#adjusting-column-spans-with-breakpoints}

Vous pouvez également ajuster les spans de colonnes dynamiquement en fonction des points d'arrêt. Cette fonctionnalité est utile lorsque vous voulez qu'un élément occupe un nombre différent de colonnes en fonction de la taille de l'écran. Par exemple, vous pouvez vouloir qu'un élément occupe une seule colonne sur les appareils mobiles mais s'étende sur plusieurs colonnes sur les grands écrans.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
// Le champ email s'étendra sur deux colonnes lorsque le point d'arrêt moyen est actif
columnsLayout.setSpan(email, "medium", 2);
//...
```

Ce niveau de personnalisation garantit que votre mise en page reste réactive et correctement structurée sur différents appareils.

## Placement des éléments dans les colonnes {#placing-items-within-columns}

`ColumnsLayout` offre la possibilité de placer des éléments dans des colonnes spécifiques, offrant plus de contrôle sur l'arrangement des éléments. Vous pouvez spécifier manuellement où un élément doit apparaître dans la mise en page, garantissant que les composants importants s'affichent comme prévu.

### Placement de colonne de base {#basic-column-placement}

Par défaut, les éléments sont placés dans la colonne suivante disponible, remplissant de gauche à droite. Cependant, vous pouvez remplacer ce comportement et spécifier la colonne exacte où un élément doit être placé. Pour placer un élément dans une colonne spécifique, utilisez la méthode `setColumn()`. Dans cet exemple, le bouton est placé dans la deuxième colonne de la mise en page, quel que soit l'ordre dans lequel il a été ajouté par rapport aux autres composants :

```java
Button button = new Button("Soumettre");
layout.addComponent(button);
// Placez l'élément dans la deuxième colonne
layout.setColumn(button, 2);  
```

### Ajustement du placement par point d'arrêt {#adjusting-placement-per-breakpoint}

Tout comme avec les spans de colonne, vous utilisez des points d'arrêt pour ajuster le placement des éléments en fonction de la taille de l'écran. Cela est utile pour réorganiser ou déplacer des éléments dans la mise en page à mesure que la fenêtre de visualisation change.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
// Le champ email apparaîtra dans la deuxième colonne lorsque le point d'arrêt moyen est actif
columnsLayout.setColumn(email, "medium", 2); 
//...
```

Dans la démonstration suivante, remarquez que lorsque le point d'arrêt `"medium"` est déclenché, le champ `email` s'étend sur les deux colonnes, et le champ `confirmPassword` est placé dans la première colonne, plutôt que dans son placement par défaut dans la deuxième colonne :

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Évitez les collisions
Lorsque plusieurs éléments sont placés dans une mise en page avec des spans et/ou des affectations de colonnes différents, assurez-vous que les spans et les placements combinés des éléments dans une rangée ne se chevauchent pas. La mise en page tente de gérer l'espacement automatiquement, mais un design soigneux des spans et des points d'arrêt prévient l'affichage involontaire d'éléments.
:::

## Alignements des éléments verticaux et horizontaux {#vertical-and-horizontal-item-alignments}

Chaque élément dans le `ColumnsLayout` peut être aligné à la fois horizontalement et verticalement dans sa colonne, donnant le contrôle sur la façon dont le contenu est positionné à l'intérieur de la mise en page.

**L'alignement horizontal** d'un élément est contrôlé à l'aide de la méthode `setHorizontalAlignment()`. Cette propriété détermine comment un élément s'aligne dans sa colonne le long de l'axe horizontal.

**L'alignement vertical** spécifie comment un élément est positionné dans sa colonne le long de l'axe vertical. Ceci est utile lorsque les colonnes ont des hauteurs variables et que vous souhaitez contrôler comment les éléments sont distribués verticalement. 

Les options d'`Alignment` disponibles incluent :

- `START` : Aligne l'élément à gauche de la colonne (par défaut).
- `CENTER`: Centre l'élément horizontalement dans la colonne.
- `END` : Aligne l'élément à droite de la colonne.
- `STRETCH` : Étire le composant pour remplir la mise en page.
- `BASELINE` : Aligne en fonction du texte ou du contenu à l'intérieur de la colonne, alignant les éléments sur la ligne de base du texte plutôt que sur d'autres options d'alignement.
- `AUTO` : Alignement automatique.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

Dans la démo ci-dessus, le bouton `Soumettre` a été donné `ColumnsLayout.Alignment.END` pour s'assurer qu'il apparaît à la fin, ou dans ce cas à droite, de sa colonne.

## Espacement des éléments {#item-spacing}

Contrôler l'espace entre les colonnes dans le `ColumnsLayout` entre les colonnes (espacement horizontal) et entre les rangées (espacement vertical) aide les développeurs à peaufiner la mise en page.

Pour définir l'espacement horizontal de la mise en page, utilisez la méthode `setHorizontalSpacing()` :

```java
// Définir un espace de 20px entre les colonnes
layout.setHorizontalSpacing(20);  
```

De même, utilisez la méthode `setVerticalSpacing()` pour configurer l'espace entre les rangées de la mise en page :

```java
// Définir un espace de 15px entre les rangées
layout.setVerticalSpacing(15);  
```

:::tip Unités CSS
Vous pouvez utiliser un entier pour définir la largeur minimale en pixels ou utiliser une `String` pour spécifier d'autres unités telles que `vw`, `%` ou `em`.
:::

## Mises en page horizontales et verticales {#horizontal-and-vertical-layouts}

Construire des mises en page réactives et attrayantes est possible en utilisant à la fois le composant [`FlexLayout`](./flex-layout) et le composant `ColumnsLayout`, ainsi qu'une combinaison des deux. Ci-dessous se trouve un échantillon du [formulaire créé dans l'article FlexLayout](./flex-layout#example-form), mais en utilisant un schéma de `ColumnLayout` à la place :

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Style {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
