---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: a1c9e9a41325f2f1ffb75fd07204106a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Le composant `ColumnsLayout` organise les éléments dans une disposition en colonnes réactive qui s'ajuste en fonction de la largeur disponible. Les points de rupture et les alignements sont gérés automatiquement, de sorte que la création de formulaires multicolonnes et de grilles de contenu ne nécessite pas de logique réactive personnalisée.

<!-- INTRO_END -->

## Comportement par défaut {#default-behavior}

Par défaut, un `ColumnsLayout` organise les éléments en deux colonnes et occupe toute la largeur de son parent. L'affichage peut être ajusté davantage avec des points de rupture et des paramètres d'alignement, abordés dans les sections ci-dessous.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info Dispositions horizontales 
Cela peut être utilisé à la place, ou en combinaison avec, le composant [`FlexLayout`](./flex-layout) - un outil tout aussi puissant pour les dispositions horizontales.
:::

## Points de rupture {#breakpoints}

Au cœur, le `ColumnsLayout` est conçu pour fournir un système fluide de type grille qui s'adapte à la largeur de son conteneur parent. Contrairement aux systèmes de grille fixes traditionnels, cette disposition permet aux développeurs de spécifier un nombre de colonnes à une largeur donnée, et calcule dynamiquement le nombre de colonnes affichées en fonction des objets `Breakpoint` définis.

Cela permet à un `ColumnsLayout` de s'adapter en douceur d'un espace plus contraint sur les petits écrans à une zone plus large sur les grands écrans, offrant un design réactif à un développeur sans nécessiter d'implémentation personnalisée.

### Compréhension d'un `Breakpoint` {#understanding-a-breakpoint}

Un `Breakpoint` peut être spécifié à l'aide de la classe `Breakpoint`, qui prend trois paramètres :

1. **Nom (optionnel)** :
Nommer un point de rupture vous permet de le référencer dans les configurations futures.

2. **Largeur minimale** :
Chaque point de rupture a une plage spécifique qui détermine quand sa disposition est appliquée. La largeur minimale est définie explicitement, et le point de rupture suivant détermine la largeur maximale si elle existe. Vous pouvez utiliser un entier pour définir la largeur minimale en pixels ou utiliser une `String` pour spécifier d'autres unités telles que `vw`, `%` ou `em`.

3. **Nombre de colonnes** :
Spécifiez combien de colonnes un point de rupture doit avoir avec cet entier.

:::info Évaluation du `Breakpoint`
Les points de rupture sont évalués dans l'ordre croissant de la largeur, ce qui signifie que la disposition utilisera le premier point de rupture correspondant.
:::

### Application des points de rupture {#applying-breakpoints}

Les points de rupture sont appliqués à un `ColumnsLayout` de deux manières : lors de la construction, ou dans une `List` à l'aide de la méthode `setBreakpoints()` :

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

La démonstration ci-dessous montre un exemple de configuration de plusieurs points de rupture lors de la construction, utilisant des points de rupture pour configurer le [`Span`](#column-span-and-spans-per-breakpoint) d'un composant, et démontre les capacités de redimensionnement du `ColumnsLayout` lorsque l'application est redimensionnée :

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Colonne `Span` et spans par `Breakpoint` {#column-span-and-spans-per-breakpoint}

Les spans des colonnes dans `ColumnsLayout` permettent de contrôler combien de colonnes un élément occupe, vous donnant plus de contrôle sur l'apparence de votre disposition à des largeurs variables. Cela est particulièrement utile lorsque vous avez besoin que certains éléments occupent plus ou moins d'espace en fonction de la taille de l'écran ou des exigences de conception.

### Gestion des spans de colonnes {#managing-column-spans}

Par défaut, chaque élément dans le `ColumnsLayout` occupe exactement une colonne. Cependant, vous pouvez personnaliser ce comportement en définissant le span de colonne pour des éléments individuels. Un span spécifie le nombre de colonnes qu'un élément doit occuper.

```java
Button button = new Button("Cliquez ici");
layout.addComponent(button);
// L'élément s'étend sur deux colonnes
layout.setSpan(button, 2);
```

Dans l'exemple ci-dessus, le bouton occupe deux colonnes au lieu de la colonne par défaut. La méthode `setSpan()` vous permet de spécifier combien de colonnes un composant doit s'étendre dans la disposition.

### Ajustement des spans de colonnes avec des points de rupture {#adjusting-column-spans-with-breakpoints}

Vous pouvez également ajuster les spans de colonnes dynamiquement en fonction des points de rupture. Cette fonctionnalité est utile lorsque vous souhaitez qu'un élément s'étende sur différents nombres de colonnes selon la taille de l'écran. Par exemple, vous pouvez vouloir qu'un élément occupe une seule colonne sur les appareils mobiles mais s'étende sur plusieurs colonnes sur les plus grands écrans.

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
//le champ email s'étendra sur deux colonnes lorsque le point de rupture moyen est actif
columnsLayout.setSpan(email, "medium", 2);
//...
```

Ce niveau de personnalisation garantit que votre disposition reste réactive et correctement structurée sur différents appareils.

## Placement d'éléments dans les colonnes {#placing-items-within-columns}

`ColumnsLayout` offre la possibilité de placer des éléments dans des colonnes spécifiques, donnant un meilleur contrôle sur l'arrangement des éléments. Vous pouvez spécifier manuellement où un élément doit apparaître dans la disposition, garantissant que les composants importants s'affichent comme prévu.

### Placement de colonnes de base {#basic-column-placement}

Par défaut, les éléments sont placés dans la prochaine colonne disponible, remplissant de gauche à droite. Cependant, vous pouvez annuler ce comportement et spécifier la colonne exacte dans laquelle un élément doit être placé. Pour placer un élément dans une colonne spécifique, utilisez la méthode `setColumn()`. Dans cet exemple, le bouton est placé dans la deuxième colonne de la disposition, indépendamment de l'ordre dans lequel il a été ajouté par rapport aux autres composants :

```java
Button button = new Button("Soumettre");
layout.addComponent(button);
// Placer l'élément dans la deuxième colonne
layout.setColumn(button, 2);  
```

### Ajustement du placement par point de rupture {#adjusting-placement-per-breakpoint}

Tout comme avec les spans de colonnes, vous utilisez des points de rupture pour ajuster le placement des éléments en fonction de la taille de l'écran. Cela est utile pour réorganiser ou relocaliser des éléments dans la disposition au fur et à mesure que la fenêtre change.

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
//le champ email apparaîtra dans la deuxième colonne lorsque le point de rupture moyen est actif
columnsLayout.setColumn(email, "medium", 2); 
//...
```

Dans la démonstration suivante, remarquez que lorsque le point de rupture `"medium"` est déclenché, le champ `email` s'étend sur les deux colonnes, et le champ `confirmPassword` est placé dans la première colonne, plutôt que dans son placement par défaut dans la deuxième colonne :

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Évitez les collisions
Lorsque plusieurs éléments sont placés dans une disposition avec des spans et/ou des assignments de colonnes différents, assurez-vous que les spans et les placements combinés des éléments dans une rangée ne se chevauchent pas. La disposition tente de gérer l'espacement automatiquement avec soin, mais une bonne conception des spans et des points de rupture empêche l'affichage involontaire des éléments.
:::

## Alignements verticaux et horizontaux des éléments {#vertical-and-horizontal-item-alignments}

Chaque élément dans le `ColumnsLayout` peut être aligné à la fois horizontalement et verticalement dans sa colonne, donnant un contrôle sur la façon dont le contenu est positionné à l'intérieur de la disposition.

**L'alignement horizontal** d'un élément est contrôlé à l'aide de la méthode `setHorizontalAlignment()`. Cette propriété détermine comment un élément s'aligne dans sa colonne le long de l'axe horizontal.

**L'alignement vertical** spécifie comment un élément est positionné dans sa colonne le long de l'axe vertical. Cela est utile lorsque les colonnes ont des hauteurs variables et que vous souhaitez contrôler comment les éléments sont répartis verticalement.

Les options d'`Alignment` disponibles comprennent :

- `START`: Aligne l'élément à gauche de la colonne (par défaut).
- `CENTER`: Centre l'élément horizontalement dans la colonne.
- `END`: Aligne l'élément à droite de la colonne.
- `STRETCH`: Étend le composant pour remplir la disposition.
- `BASELINE`: Aligne en fonction du texte ou du contenu à l'intérieur de la colonne, alignant les éléments selon la ligne de base du texte plutôt qu'avec d'autres options d'alignement.
- `AUTO`: Alignement automatique.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

Dans la démo ci-dessus, le bouton `Soumettre` a été donné `ColumnsLayout.Alignment.END` pour s'assurer qu'il apparaisse à la fin, ou dans ce cas à droite, de sa colonne.

## Espacement des éléments {#item-spacing}

Contrôler l'espace entre les colonnes dans le `ColumnsLayout` entre les colonnes (espacement horizontal) et entre les rangées (espacement vertical) aide les développeurs à peaufiner la disposition.

Pour définir l'espacement horizontal de la disposition, utilisez la méthode `setHorizontalSpacing()` :

```java
// Définir un espace de 20px entre les colonnes
layout.setHorizontalSpacing(20);  
```

De même, utilisez la méthode `setVerticalSpacing()` pour configurer l'espace entre les rangées de la disposition :

```java
// Définir un espace de 15px entre les rangées
layout.setVerticalSpacing(15);  
```

:::tip Unités CSS
Vous pouvez utiliser un entier pour définir la largeur minimale en pixels ou utiliser une `String` pour spécifier d'autres unités telles que `vw`, `%` ou `em`.
:::

## Dispositions horizontales et verticales {#horizontal-and-vertical-layouts}

Construire des dispositions réactives et attrayantes est possible en utilisant à la fois le composant [`FlexLayout`](./flex-layout) et le composant `ColumnsLayout`, ainsi qu'une combinaison des deux. Ci-dessous se trouve un exemple du [formulaire créé dans l'article FlexLayout](./flex-layout#example-form), mais utilisant un schéma `ColumnLayout` à la place :

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Style {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
