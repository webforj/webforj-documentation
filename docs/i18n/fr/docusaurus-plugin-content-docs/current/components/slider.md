---
title: Slider
sidebar_position: 101
_i18n_hash: 490cb925a92ffd4860f74b00491402e5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Le composant `Slider` offre aux utilisateurs un moyen de sélectionner une valeur numérique en faisant glisser un bouton le long d'une piste entre une limite minimale et maximale. Les intervalles d'étape, les marques de graduation et les étiquettes peuvent être configurés pour guider la sélection.

<!-- INTRO_END -->

## Basics {#basics}

Le `Slider` est conçu pour fonctionner immédiatement sans configuration supplémentaire. Par défaut, il couvre une plage de 0 à 100 avec une valeur initiale de 50, ce qui le rend idéal pour une intégration rapide dans n'importe quelle application. Pour des cas d'utilisation plus spécifiques, le `Slider` peut être personnalisé avec des propriétés telles que l'orientation, les marques de graduation, les étiquettes et les infobulles.

Voici un exemple de `Slider` qui permet aux utilisateurs d'ajuster les niveaux de volume dans une plage prédéfinie :

<ComponentDemo
path='/webforj/slider'
files={['src/main/java/com/webforj/samples/views/slider/SliderView.java']}
height='100px'
/>

## `Slider` value {#slider-value}

La valeur du `Slider` représente la position actuelle du bouton sur le curseur et est définie comme un entier dans la plage du `Slider`. Cette valeur se met à jour dynamiquement lorsque l'utilisateur interagit avec le curseur, ce qui en fait une propriété essentielle pour suivre l'entrée utilisateur.

:::tip Valeur par défaut
Par défaut, le `Slider` commence avec une valeur de 50, supposant la plage par défaut de 0 à 100.
:::

### Définir et obtenir la valeur {#setting-and-getting-the-value}

Vous pouvez définir la valeur du `Slider` lors de l'initialisation ou la mettre à jour ultérieurement en utilisant la méthode `setValue()`. Pour récupérer la valeur actuelle, utilisez la méthode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Définit le curseur à 25

Integer value = slider.getValue();  
System.out.println("Valeur actuelle du Slider : " + value);
```

## Valeurs minimales et maximales {#minimum-and-maximum-values}

Les valeurs minimale et maximale définissent la plage autorisée du `Slider`, déterminant les limites dans lesquelles le bouton du `Slider` peut se déplacer. Par défaut, la plage est définie de 0 à 100, mais vous pouvez personnaliser ces valeurs selon vos besoins.

Les intervalles sur le `Slider` ont une étape par défaut de 1, ce qui signifie que le nombre d'intervalles est déterminé par la plage. Par exemple :
- Un Slider avec une plage de 0 à 10 aura 10 intervalles.
- Un Slider avec une plage de 0 à 100 aura 100 intervalles.

Ces intervalles sont répartis uniformément le long de la piste du curseur, leur espacement dépendant des dimensions du `Slider`.

Voici un exemple de création d'un `Slider` avec une plage personnalisée :

<ComponentDemo
path='/webforj/donationslider'
files={['src/main/java/com/webforj/samples/views/slider/DonationSliderView.java']}
height='200px'
/>

## Configuration des marques de graduation {#tick-configuration}

Le composant `Slider` offre une configuration flexible des marques de graduation, vous permettant de personnaliser la façon dont les marques de graduation sont affichées et comment le bouton du curseur interagit avec elles. Cela inclut l'ajustement de l'espacement des marques de graduation majeures et mineures, l'affichage/cachage des marques de graduation, et l'activation du « snapping » aux marques de graduation pour une entrée précise de l'utilisateur.

### Espacement des marques de graduation majeures et mineures {#major-and-minor-tick-spacing}

Vous pouvez définir l'espacement des marques de graduation majeures et mineures, ce qui détermine la fréquence à laquelle elles apparaissent sur la piste du `Slider` :

- Les marques majeures sont plus grandes et souvent étiquetées pour représenter des valeurs clés.
- Les marques mineures sont plus petites et apparaissent entre les marques majeures pour offrir des intervalles plus fins.

Définissez l'espacement des marques en utilisant les méthodes `setMajorTickSpacing()` et `setMinorTickSpacing()` :
```java
slider.setMajorTickSpacing(10); // Marques majeures tous les 10 unités
slider.setMinorTickSpacing(2);  // Marques mineures toutes les 2 unités
```

### Afficher ou cacher les marques de graduation {#show-or-hide-ticks}

Vous pouvez basculer la visibilité des marques de graduation en utilisant la méthode `setTicksVisible()`. Par défaut, les marques de graduation sont cachées.

```java
slider.setTicksVisible(true); // Afficher les marques de graduation
slider.setTicksVisible(false); // Cacher les marques de graduation
```

### Snapping {#snapping}

Pour garantir que le bouton du `Slider` s'aligne avec la marque de graduation la plus proche lors de l'interaction de l'utilisateur, activez le snapping en utilisant la méthode `setSnapToTicks()` :

```java
slider.setSnapToTicks(true); // Activer le snapping
```

Voici un exemple d'un `Slider` entièrement configuré montrant les paramètres de marques de graduation majeures et mineures, ainsi que la capacité de snapping pour des ajustements précis :

<ComponentDemo
path='/webforj/slidertickspacing'
files={['src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java']}
height='350px'
/>

## Orientation et inversion {#orientation-and-inversion}

Le composant `Slider` supporte deux orientations : horizontale (par défaut) et verticale. Vous pouvez changer l'orientation pour s'adapter à la mise en page de votre UI et aux exigences de votre application.

En plus de l'orientation, le `Slider` peut également être inversé. Par défaut :

- Un `Slider` horizontal va de minimum (gauche) à maximum (droite).
- Un `Slider` vertical va de minimum (bas) à maximum (haut).

Lorsqu'il est inversé, cette direction est inversée. Utilisez la méthode `setInverted(true)` pour activer l'inversion.

<ComponentDemo
path='/webforj/sliderorientation'
files={['src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java']}
height='440px'
/>

## Étiquettes {#labels}

Le composant `Slider` prend en charge les étiquettes sur les marques de graduation pour aider les utilisateurs à interpréter plus facilement les valeurs. Vous pouvez utiliser les étiquettes numériques par défaut ou en fournir des personnalisées, et vous pouvez basculer leur visibilité si nécessaire.

### Étiquettes par défaut {#default-labels}

Par défaut, le curseur peut afficher des étiquettes numériques aux marques de graduation majeures. Ces valeurs sont déterminées par le réglage de `setMajorTickSpacing()`. Pour activer les étiquettes par défaut, utilisez :

```java
slider.setLabelsVisible(true);
```

### Étiquettes personnalisées {#custom-labels}

Vous pouvez remplacer les étiquettes numériques par défaut par du texte personnalisé en utilisant la méthode `setLabels()`. Cela est utile lorsque vous souhaitez afficher des valeurs plus significatives (par exemple, température, devise ou catégories).

```java
Map<Integer, String> customLabels = Map.of(
  0, "Froid",
  30, "Frais",
  50, "Modéré",
  80, "Chaud",
  100, "Très chaud"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Basculement de la visibilité des étiquettes {#toggling-label-visibility}

Que vous utilisiez des étiquettes par défaut ou personnalisées, vous pouvez contrôler leur visibilité avec `setLabelsVisible(true)` ou les cacher avec `setLabelsVisible(false)`.

<ComponentDemo
path='/webforj/sliderlabels'
files={['src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java']}
height='150px'
/>

## Infobulles {#tooltips}

Les infobulles améliorent l'utilisabilité en affichant la valeur du `Slider` directement au-dessus ou en dessous du bouton, aidant les utilisateurs à effectuer des ajustements plus précis. Vous pouvez configurer le comportement, la visibilité et le format de l'infobulle selon vos besoins.

Pour activer les infobulles, utilisez la méthode `setTooltipVisible()`. Par défaut, les infobulles sont désactivées :

```java
slider.setTooltipVisible(true); // Activer les infobulles
slider.setTooltipVisible(false); // Désactiver les infobulles
```

Les infobulles peuvent également être configurées pour n'apparaître que lorsque l'utilisateur interagit avec le `Slider`. Utilisez la méthode `setTooltipVisibleOnSlideOnly()` pour activer ce comportement. Cela est particulièrement utile pour réduire l'encombrement visuel tout en fournissant un retour utile lors de l'interaction.

Voici un exemple d'un `Slider` entièrement configuré avec des infobulles :

### Personnalisation des infobulles {#tooltip-customization}

Par défaut, le `Slider` affiche une infobulle avec sa valeur actuelle. Si vous souhaitez personnaliser ce texte, utilisez la méthode `setTooltipText()`. Cela est utile lorsque vous souhaitez que l'infobulle affiche un texte statique ou descriptif au lieu de la valeur en direct.

Vous pouvez également utiliser une expression JavaScript pour formater dynamiquement l'infobulle. Si votre expression inclut le mot-clé `return`, elle est utilisée telle quelle. Sinon, elle est automatiquement enveloppée avec `return` et `;` pour former une fonction valide. Par exemple :

```java
// Affiche la valeur suivie d'un signe dollar
slider.setTooltipText("return x + '$'"); 
```

Ou simplement :

```java
// Interprété comme : return x + ' unités';
slider.setTooltipText("x + ' unités'"); 
```

## Styling {#styling}

### Thèmes {#themes}

Le `Slider` est livré avec 6 thèmes intégrés pour un stylage rapide sans utiliser de CSS. Le thème est pris en charge par l'utilisation d'une classe enum intégrée.
Ci-dessous des curseurs avec chacun des thèmes supportés appliqués :

<ComponentDemo
path='/webforj/sliderthemes'
files={['src/main/java/com/webforj/samples/views/slider/SliderThemesView.java']}
height='460px'
/>

<TableBuilder name="Slider" />
