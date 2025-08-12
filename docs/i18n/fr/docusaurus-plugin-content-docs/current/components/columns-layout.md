---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: fb5b6ef5a20567d8a86d04c022a0449e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

Le composant `ColumnsLayout` dans webforJ permet aux développeurs de créer des mises en page en utilisant une mise en page verticale flexible et réactive. Cette mise en page fournit des colonnes dynamiques qui s'ajustent en fonction de la largeur disponible. Ce composant simplifie la création de mises en page à colonnes multiples en gérant automatiquement les points de rupture et les alignements.

:::info Mises en page horizontales
Cela peut être utilisé à la place de, ou en combinaison avec, le composant [`FlexLayout`](./flex-layout) - un outil tout aussi puissant pour les mises en page horizontales.
:::

## Bases {#basics}

Lors de son instanciation initiale, le `ColumnsLayout` utilise deux colonnes pour afficher les éléments ajoutés à la mise en page. Par défaut, il occupe la totalité de la largeur de ses éléments parents et s'agrandit au besoin pour s'adapter au contenu supplémentaire. L'affichage des éléments ajoutés peut être calibré davantage à l'aide des réglages [`Breakpoint`](./columns-layout#breakpoints) et [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments), qui sont discutés en détail dans les sections suivantes.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Points de rupture {#breakpoints}

Au cœur, le `ColumnsLayout` est conçu pour fournir un système fluide de type grille qui s'adapte à la largeur de son conteneur parent. Contrairement aux systèmes de grille fixes traditionnels, cette mise en page permet aux développeurs de spécifier un nombre de colonnes à une certaine largeur et calcule dynamiquement le nombre de colonnes affichées en fonction des objets `Breakpoint` définis.

Cela permet à un `ColumnsLayout` de s'adapter en douceur d'un espace plus restreint sur les petits écrans à une zone plus large sur les plus grands écrans, offrant un design réactif à un développeur sans nécessiter d'implémentation personnalisée.

### Comprendre un `Breakpoint` {#understanding-a-breakpoint}

Un `Breakpoint` peut être spécifié à l'aide de la classe `Breakpoint`, qui prend trois paramètres :

1. **Nom (facultatif)** :
Nommer un point de rupture vous permet de le référencer dans les configurations futures.

2. **Largeur minimale** :
Chaque point de rupture a une plage spécifique qui détermine quand sa mise en page est appliquée. La largeur minimale est définie explicitement, et le point de rupture suivant détermine la largeur maximale si elle existe. Vous pouvez utiliser un entier pour définir la largeur minimale en pixels ou utiliser une `String` pour spécifier d'autres unités comme `vw`, `%`, ou `em`.

3. **Nombre de colonnes** :
Spécifiez combien de colonnes un point de rupture doit avoir avec cet entier.

:::info Évaluation des `Breakpoint`
Les points de rupture sont évalués par ordre croissant de la largeur, ce qui signifie que la mise en page utilisera le premier point de rupture correspondant.
:::

### Application des points de rupture {#applying-breakpoints}

Les points de rupture sont appliqués à un `ColumnsLayout` de deux manières : lors de la construction, ou en utilisant la méthode `addBreakpoint(Breakpoint)` comme montré ci-dessous.

```java
ColumnsLayout layout = new ColumnsLayout()
    // Une colonne à des largeurs >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Deux colonnes à des largeurs >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Quatre colonnes à des largeurs >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

La démonstration ci-dessous montre un exemple de définition de plusieurs points de rupture lors de la construction, utilisant des points de rupture pour configurer le [`Span`](#column-span-and-spans-per-breakpoint) d'un composant, et démontre les capacités de redimensionnement du `ColumnsLayout` lorsque l'application est redimensionnée :

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Span de colonne et spans par point de rupture {#column-span-and-spans-per-breakpoint}

Les spans de colonne dans `ColumnsLayout` vous permettent de contrôler combien de colonnes un élément occupe, vous donnant plus de contrôle sur l'apparence de votre mise en page à des largeurs variables. Ceci est particulièrement utile lorsque certains éléments doivent occuper plus ou moins d'espace selon la taille de l'écran ou les exigences de conception.

### Gestion des spans de colonnes {#managing-column-spans}

Par défaut, chaque élément dans le `ColumnsLayout` occupe exactement une colonne. Cependant, vous pouvez personnaliser ce comportement en définissant le span de colonne pour des éléments individuels. Un span spécifie le nombre de colonnes qu'un élément doit occuper.

```java
Button button = new Button("Cliquez ici");
layout.addComponent(button);
// L'élément s'étend sur deux colonnes
layout.setSpan(button, 2);
```

Dans l'exemple ci-dessus, le bouton occupe deux colonnes au lieu de la colonne par défaut. La méthode `setSpan()` vous permet de spécifier combien de colonnes un composant doit s'étendre dans la mise en page.

### Ajustement des spans de colonnes avec des points de rupture {#adjusting-column-spans-with-breakpoints}

Vous pouvez également ajuster les spans de colonnes dynamiquement en fonction des points de rupture. Cette fonctionnalité est utile lorsque vous souhaitez qu'un élément s'étende sur différents nombres de colonnes selon la taille de l'écran. Par exemple, vous pouvez vouloir qu'un élément occupe une seule colonne sur des appareils mobiles mais s'étende sur plusieurs colonnes sur des écrans plus grands.

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
// Le champ email s'étendra sur deux colonnes lorsque le point de rupture moyen est actif
columnsLayout.setSpan(email, "medium", 2);
//...
```

Ce niveau de personnalisation garantit que votre mise en page reste réactive et correctement structurée sur différents appareils.

## Placement des éléments dans les colonnes {#placing-items-within-columns}

`ColumnsLayout` offre la possibilité de placer des éléments dans des colonnes spécifiques, offrant un plus grand contrôle sur l'arrangement des éléments. Vous pouvez spécifier manuellement où un élément doit apparaître dans la mise en page, en veillant à ce que les composants importants s'affichent comme prévu.

### Placement de colonne de base {#basic-column-placement}

Par défaut, les éléments sont placés dans la prochaine colonne disponible, remplissant de gauche à droite. Cependant, vous pouvez remplacer ce comportement et spécifier la colonne exacte dans laquelle un élément doit être placé. Pour placer un élément dans une colonne spécifique, utilisez la méthode `setColumn()`. Dans cet exemple, le bouton est placé dans la deuxième colonne de la mise en page, quel que soit l'ordre dans lequel il a été ajouté par rapport aux autres composants :

```java
Button button = new Button("Soumettre");
layout.addComponent(button);
// Placer l'élément dans la deuxième colonne
layout.setColumn(button, 2);  
```

### Ajustement du placement par point de rupture {#adjusting-placement-per-breakpoint}

Tout comme avec les spans de colonnes, vous utilisez des points de rupture pour ajuster le placement des éléments en fonction de la taille de l'écran. Cela est utile pour réorganiser ou relocaliser des éléments dans la mise en page lorsque la fenêtre de visualisation change.

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
// Le champ email apparaîtra dans la deuxième colonne lorsque le point de rupture moyen est actif
columnsLayout.setColumn(email, "medium", 2); 
//...
```

Dans la démonstration suivante, notez que lorsque le point de rupture `"medium"` est déclenché, le champ `email` s'étend sur les deux colonnes, et le champ `confirmPassword` est placé dans la première colonne, plutôt que sa place par défaut dans la deuxième colonne :

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Évitez les collisions
Lorsque plusieurs éléments sont placés dans une mise en page avec des spans et/ou des attributions de colonnes différents, assurez-vous que les spans et les placements combinés des éléments dans une ligne ne se chevauchent pas. La mise en page tente de gérer automatiquement l'espacement, mais une conception soigneuse des spans et des points de rupture permet d'éviter l'affichage inattendu des éléments.
:::

## Alignements d'éléments verticaux et horizontaux {#vertical-and-horizontal-item-alignments}

Chaque élément dans le `ColumnsLayout` peut être aligné à la fois horizontalement et verticalement dans sa colonne, donnant le contrôle sur la façon dont le contenu est positionné à l'intérieur de la mise en page.

**L'alignement horizontal** d'un élément est contrôlé à l'aide de la méthode `setHorizontalAlignment()`. Cette propriété détermine comment un élément s'aligne dans sa colonne le long de l'axe horizontal.

**L'alignement vertical** spécifie comment un élément est positionné dans sa colonne le long de l'axe vertical. Cela est utile lorsque les colonnes ont des hauteurs variées et que vous souhaitez contrôler la distribution verticale des éléments.

Les options d'`Alignment` disponibles incluent :

- `START` : Aligne l'élément à gauche de la colonne (par défaut).
- `CENTER` : Centre l'élément horizontalement dans la colonne.
- `END` : Aligne l'élément à droite de la colonne.
- `STRETCH` : Étire le composant pour remplir la mise en page.
- `BASELINE` : Aligne en fonction du texte ou du contenu à l'intérieur de la colonne, alignant les éléments sur la ligne de base du texte plutôt que sur d'autres options d'alignement.
- `AUTO` : Alignement automatique.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

Dans la démo ci-dessus, le bouton `Soumettre` a été donné `ColumnsLayout.Alignment.END` pour s'assurer qu'il apparaît à la fin, ou dans ce cas à droite, de sa colonne.

## Espacement des éléments {#item-spacing}

Contrôler l'espace entre les colonnes dans le `ColumnsLayout` entre les colonnes (espacement horizontal) et entre les lignes (espacement vertical) aide les développeurs à affiner la mise en page.

Pour définir l'espacement horizontal de la mise en page, utilisez la méthode `setHorizontalSpacing()` :

```java
// Définir un espace de 20px entre les colonnes
layout.setHorizontalSpacing(20);  
```

De même, utilisez la méthode `setVerticalSpacing()` pour configurer l'espace entre les lignes de la mise en page :

```java
// Définir un espace de 15px entre les lignes
layout.setVerticalSpacing(15);  
```

:::tip Unités CSS
Vous pouvez utiliser un entier pour définir la largeur minimale en pixels ou utiliser une `String` pour spécifier d'autres unités comme `vw`, `%`, ou `em`.
:::

## Mises en page horizontales et verticales {#horizontal-and-vertical-layouts}

Construire des mises en page réactives et attrayantes est possible en utilisant à la fois le composant [`FlexLayout`](./flex-layout) et le composant `ColumnsLayout`, ainsi qu'une combinaison des deux. Ci-dessous se trouve un exemple de l'[formulaire créé dans l'article FlexLayout](./flex-layout#example-form), mais en utilisant un schéma `ColumnLayout` à la place :

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Style {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
