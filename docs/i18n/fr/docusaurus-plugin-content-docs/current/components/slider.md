---
title: Slider
sidebar_position: 101
_i18n_hash: 47e9254faad15097b580eb4099968fbc
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Le composant `Slider` dans webforJ fournit un contrôle interactif permettant aux utilisateurs de sélectionner une valeur dans une plage spécifique en déplaçant un bouton. Cette fonctionnalité est particulièrement utile pour les applications nécessitant une saisie précise ou intuitive, comme la sélection de volumes, de pourcentages ou d'autres valeurs ajustables.

## Bases {#basics}

Le `Slider` est conçu pour fonctionner immédiatement sans configuration supplémentaire. Par défaut, il s'étend sur une plage de 0 à 100 avec une valeur de départ de 50, ce qui le rend idéal pour une intégration rapide dans n'importe quelle application. Pour des cas d'utilisation plus spécifiques, le `Slider` peut être personnalisé avec des propriétés telles que l'orientation, les graduations, les étiquettes et les info-bulles.

Voici un exemple de `Slider` permettant aux utilisateurs d'ajuster les niveaux de volume dans une plage prédéfinie :

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## Valeur du `Slider` {#slider-value}

La valeur du `Slider` représente la position actuelle du bouton sur le curseur et est définie comme un entier dans la plage du `Slider`. Cette valeur se met à jour dynamiquement lorsque l'utilisateur interagit avec le curseur, ce qui en fait une propriété essentielle pour suivre les saisies des utilisateurs.

:::tip Valeur par défaut
Par défaut, le `Slider` commence avec une valeur de 50, supposant la plage par défaut de 0 à 100.
:::

### Définir et obtenir la valeur {#setting-and-getting-the-value}

Vous pouvez définir la valeur du `Slider` lors de l'initialisation ou la mettre à jour plus tard en utilisant la méthode `setValue()`. Pour récupérer la valeur actuelle, utilisez la méthode `getValue()`.

```java
Slider slider = new Slider();  
slider.setValue(25); // Définit le slider à 25

Integer value = slider.getValue();  
System.out.println("Valeur actuelle du Slider : " + value);
```

## Valeurs minimales et maximales {#minimum-and-maximum-values}

Les valeurs minimales et maximales définissent la plage autorisée du `Slider`, déterminant les limites dans lesquelles le bouton du `Slider` peut se déplacer. Par défaut, la plage est définie de 0 à 100, mais vous pouvez personnaliser ces valeurs en fonction de vos besoins.

Les intervalles sur le `Slider` ont un pas par défaut de 1, ce qui signifie que le nombre d'intervalles est déterminé par la plage. Par exemple :
- Un Slider avec une plage de 0 à 10 aura 10 intervalles.
- Un Slider avec une plage de 0 à 100 aura 100 intervalles.

Ces intervalles sont répartis uniformément le long de la piste du curseur, leur espacement dépendant des dimensions du `Slider`.

Voici un exemple de création d'un `Slider` avec une plage personnalisée :

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Configuration des graduations {#tick-configuration}

Le composant `Slider` offre une configuration flexible des graduations, vous permettant de personnaliser l'affichage des marques de graduation et la manière dont le bouton du curseur interagit avec elles. Cela inclut l'ajustement de l'espacement des graduations majeures et mineures, l'affichage/cachage des marques de graduation et l'activation de l'alignement sur les marques de graduation pour une saisie utilisateur précise.

### Espacement des graduations majeures et mineures {#major-and-minor-tick-spacing}

Vous pouvez définir l'espacement des marques de graduation majeures et mineures, ce qui détermine leur fréquence d'apparition sur la piste du `Slider` :

- Les graduations majeures sont plus grandes et souvent étiquetées pour représenter des valeurs clés.
- Les graduations mineures sont plus petites et apparaissent entre les graduations majeures pour offrir des intervalles plus fins.

Définissez l'espacement des graduations à l'aide des méthodes suivantes `setMajorTickSpacing()` et `setMinorTickSpacing()` :
```java
slider.setMajorTickSpacing(10); // Graduations majeures tous les 10 unités
slider.setMinorTickSpacing(2);  // Graduations mineures toutes les 2 unités
```

### Afficher ou cacher les graduations {#show-or-hide-ticks}

Vous pouvez activer ou désactiver la visibilité des marques de graduation à l'aide de la méthode `setTicksVisible()`. Par défaut, les marques de graduation sont cachées.

```java
slider.setTicksVisible(true); // Afficher les marques de graduation
slider.setTicksVisible(false); // Cacher les marques de graduation
```

### Alignement sur les graduations {#snapping}

Pour s'assurer que le bouton du `Slider` s'aligne sur la marque de graduation la plus proche pendant l'interaction de l'utilisateur, activez l'alignement à l'aide de la méthode `setSnapToTicks()` :

```java
slider.setSnapToTicks(true); // Activer l'alignement
```

Voici un exemple d'un `Slider` entièrement configuré montrant les réglages des graduations majeures et mineures ainsi que la capacité d'alignement pour des ajustements précis :

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientation et inversion {#orientation-and-inversion}

Le composant `Slider` prend en charge deux orientations : horizontale (par défaut) et verticale. Vous pouvez changer l'orientation en fonction de votre mise en page d'interface utilisateur et des exigences de votre application.

En plus de l'orientation, le `Slider` peut également être inversé. Par défaut :

- Un `Slider` horizontal va du minimum (gauche) au maximum (droite).
- Un `Slider` vertical va du minimum (bas) au maximum (haut).

Lorsqu'il est inversé, cette direction est inversée. Utilisez la méthode `setInverted(true)` pour activer l'inversion.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Étiquettes {#labels}

Le composant `Slider` prend en charge les étiquettes sur les marques de graduation pour aider les utilisateurs à interpréter les valeurs plus facilement. Vous pouvez utiliser des étiquettes numériques par défaut ou fournir des étiquettes personnalisées, et vous pouvez basculer leur visibilité si nécessaire.

### Étiquettes par défaut {#default-labels}

Par défaut, le curseur peut afficher des étiquettes numériques aux marques de graduation majeures. Ces valeurs sont déterminées par le paramètre `setMajorTickSpacing()`. Pour activer les étiquettes par défaut, utilisez :

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
    100, "Brûlant"
);

slider.setLabels(customLabels);
slider.setLabelsVisible(true);
```

### Contrôle de la visibilité des étiquettes {#toggling-label-visibility}

Que vous utilisiez des étiquettes par défaut ou personnalisées, vous pouvez contrôler leur visibilité avec `setLabelsVisible(true)` ou les cacher avec `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Info-bulles {#tooltips}

Les info-bulles améliorent l'utilisabilité en affichant la valeur du `Slider` directement au-dessus ou en dessous du bouton, aidant les utilisateurs à effectuer des ajustements plus précis. Vous pouvez configurer le comportement, la visibilité et le format de l'info-bulle en fonction de vos besoins.

Pour activer les info-bulles, utilisez la méthode `setTooltipVisible()`. Par défaut, les info-bulles sont désactivées :

```java
slider.setTooltipVisible(true); // Activer les info-bulles
slider.setTooltipVisible(false); // Désactiver les info-bulles
```

Les info-bulles peuvent également être configurées pour apparaître uniquement lorsque l'utilisateur interagit avec le `Slider`. Utilisez la méthode `setTooltipVisibleOnSlideOnly()` pour activer ce comportement. Ceci est particulièrement utile pour réduire l'encombrement visuel tout en fournissant des retours utiles pendant l'interaction.

Voici un exemple d'un `Slider` entièrement configuré avec des info-bulles :

### Personnalisation de l'info-bulle {#tooltip-customization}

Par défaut, le `Slider` affiche une info-bulle avec sa valeur actuelle. Si vous souhaitez personnaliser ce texte, utilisez la méthode `setTooltipText()`. Cela est utile lorsque vous souhaitez que l'info-bulle affiche un texte statique ou descriptif au lieu de la valeur en direct.

Vous pouvez également utiliser une expression JavaScript pour formater l'info-bulle dynamiquement. Si votre expression comprend le mot-clé `return`, elle est utilisée telle quelle. Sinon, elle est automatiquement enveloppée avec `return` et `;` pour former une fonction valide. Par exemple :

```java
// Affiche la valeur suivie d'un signe dollar
slider.setTooltipText("return x + '$'"); 
```

Ou simplement :

```java
// Interprété comme : return x + ' unités';
slider.setTooltipText("x + ' unités'"); 
```

## Style {#styling}

### Thèmes {#themes}

Le `Slider` est livré avec 6 thèmes intégrés pour un style rapide sans utiliser de CSS. La thématisation est prise en charge par une classe énumérée intégrée.
Ci-dessous, des curseurs avec chacun des thèmes pris en charge appliqués :

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
