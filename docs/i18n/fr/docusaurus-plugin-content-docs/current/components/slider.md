---
title: Slider
sidebar_position: 101
_i18n_hash: 045c80d3d54048157d805ee64213f210
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-slider" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/slider/Slider" top='true'/>

Le composant `Slider` de webforJ fournit un contrôle interactif qui permet aux utilisateurs de sélectionner une valeur dans une plage spécifique en déplaçant un bouton. Cette fonctionnalité est particulièrement utile pour les applications nécessitant une entrée précise ou intuitive, telles que la sélection de volumes, pourcentages ou autres valeurs ajustables.

## Basics {#basics}

Le `Slider` est conçu pour fonctionner immédiatement, sans configuration supplémentaire pour être efficace. Par défaut, il s'étend sur une plage de 0 à 100 avec une valeur de départ de 50, ce qui le rend idéal pour une intégration rapide dans n'importe quelle application. Pour des cas d'utilisation plus spécifiques, le `Slider` peut être personnalisé avec des propriétés telles que l'orientation, les marques de graduation, les étiquettes et les infobulles.

Voici un exemple d'un `Slider` qui permet aux utilisateurs d'ajuster les niveaux de volume dans une plage prédéfinie :

<ComponentDemo 
path='/webforj/slider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderView.java'
height = '100px'
/>

## `Slider` value {#slider-value}

La valeur du `Slider` représente la position actuelle du bouton sur le slider et est définie comme un entier dans la plage du `Slider`. Cette valeur se met à jour dynamiquement à mesure que l'utilisateur interagit avec le slider, ce qui en fait une propriété essentielle pour suivre l'entrée de l'utilisateur.

:::tip Valeur par défaut
Par défaut, le `Slider` commence avec une valeur de 50, supposant que la plage par défaut est de 0 à 100.
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

Les valeurs minimales et maximales définissent la plage autorisée du `Slider`, déterminant les limites à l'intérieur desquelles le bouton du `Slider` peut se déplacer. Par défaut, la plage est définie de 0 à 100, mais vous pouvez personnaliser ces valeurs selon vos besoins.

Les intervalles sur le `Slider` ont une étape par défaut de 1, ce qui signifie que le nombre d'intervalles est déterminé par la plage. Par exemple :
- Un Slider avec une plage de 0 à 10 aura 10 intervalles.
- Un Slider avec une plage de 0 à 100 aura 100 intervalles.

Ces intervalles sont répartis uniformément le long de la piste du slider, leur espacement dépendant des dimensions du `Slider`.

Voici un exemple de création d'un `Slider` avec une plage personnalisée :

<ComponentDemo 
path='/webforj/donationslider?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/DonationSliderView.java'
height = '200px'
/>

## Configuration des marques de graduation {#tick-configuration}

Le composant `Slider` offre une configuration flexible des marques de graduation, vous permettant de personnaliser la façon dont les marques de graduation sont affichées et comment le bouton du slider interagit avec elles. Cela inclut l'ajustement de l'espacement des marques de graduation majeures et mineures, l'affichage/cachage des marques de graduation et l'activation de l'accrochage aux marques de graduation pour une entrée utilisateur précise.

### Espacement des marques de graduation majeures et mineures {#major-and-minor-tick-spacing}

Vous pouvez définir l'espacement des marques de graduation majeures et mineures, ce qui détermine leur fréquence d'apparition sur la piste du `Slider` :

- Les marques de graduation majeures sont plus grandes et souvent étiquetées pour représenter des valeurs clés.
- Les marques de graduation mineures sont plus petites et apparaissent entre les marques de graduation majeures pour offrir des intervalles plus fins.

Définissez l'espacement des marques de graduation en utilisant les méthodes suivantes `setMajorTickSpacing()` et `setMinorTickSpacing()` :
```java
slider.setMajorTickSpacing(10); // Marques majeures tous les 10 unités
slider.setMinorTickSpacing(2);  // Marques mineures toutes les 2 unités
```

### Afficher ou masquer les marques de graduation {#show-or-hide-ticks}

Vous pouvez basculer la visibilité des marques de graduation en utilisant la méthode `setTicksVisible()`. Par défaut, les marques de graduation sont cachées.

```java
slider.setTicksVisible(true); // Afficher les marques de graduation
slider.setTicksVisible(false); // Masquer les marques de graduation
```

### Accrochage {#snapping}

Pour garantir que le bouton du `Slider` s'aligne avec la marque de graduation la plus proche lors de l'interaction de l'utilisateur, activez l'accrochage en utilisant la méthode `setSnapToTicks()` :

```java
slider.setSnapToTicks(true); // Activer l'accrochage
```

Voici un exemple d'un `Slider` entièrement configuré montrant les paramètres des marques de graduation majeures et mineures ainsi que la capacité d'accrochage pour des ajustements précis :

<ComponentDemo 
path='/webforj/slidertickspacing?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderTickSpacingView.java'  
height = '350px'
/>

## Orientation et inversion {#orientation-and-inversion}

Le composant `Slider` prend en charge deux orientations : horizontale (par défaut) et verticale. Vous pouvez changer l'orientation pour convenir à votre mise en page UI et aux exigences de votre application.

En plus de l'orientation, le `Slider` peut également être inversé. Par défaut :

- Un `Slider` horizontal va de minime (gauche) à maximum (droite).
- Un `Slider` vertical va de minime (bas) à maximum (haut).

Lorsque le slider est inversé, cette direction est inversée. Utilisez la méthode `setInverted(true)` pour activer l'inversion.

<ComponentDemo 
path='/webforj/sliderorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderOrientationView.java'
height = '420px'
/>

## Étiquettes {#labels}

Le composant `Slider` prend en charge les étiquettes sur les marques de graduation pour aider les utilisateurs à interpréter plus facilement les valeurs. Vous pouvez utiliser des étiquettes numériques par défaut ou fournir des étiquettes personnalisées, et vous pouvez basculer leur visibilité au besoin.

### Étiquettes par défaut {#default-labels}

Par défaut, le slider peut afficher des étiquettes numériques aux marques de graduation majeures. Ces valeurs sont déterminées par le réglage de `setMajorTickSpacing()`. Pour activer les étiquettes par défaut, utilisez :

```java
slider.setLabelsVisible(true);
```

### Étiquettes personnalisées {#custom-labels}

Vous pouvez remplacer les étiquettes numériques par défaut par un texte personnalisé en utilisant la méthode `setLabels()`. Cela est utile lorsque vous souhaitez afficher des valeurs plus significatives (par exemple, température, devise ou catégories).

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

### Basculement de la visibilité des étiquettes {#toggling-label-visibility}

Que vous utilisiez des étiquettes par défaut ou personnalisées, vous pouvez contrôler leur visibilité avec `setLabelsVisible(true)` ou les masquer avec `setLabelsVisible(false)`.

<ComponentDemo 
path='/webforj/sliderlabels?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderLabelsView.java'
height = '150px'
/>

## Infobulles {#tooltips}

Les infobulles améliorent l'utilisabilité en affichant la valeur du `Slider` directement au-dessus ou en dessous du bouton, aidant les utilisateurs à effectuer des ajustements plus précis. Vous pouvez configurer le comportement, la visibilité et le format de l'infobulle selon vos besoins.

Pour activer les infobulles, utilisez la méthode `setTooltipVisible()`. Par défaut, les infobulles sont désactivées :

```java
slider.setTooltipVisible(true); // Activer les infobulles
slider.setTooltipVisible(false); // Désactiver les infobulles
```

Les infobulles peuvent également être configurées pour n'apparaître que lorsque l'utilisateur interagit avec le `Slider`. Utilisez la méthode `setTooltipVisibleOnSlideOnly()` pour activer ce comportement. Ceci est particulièrement utile pour réduire l'encombrement visuel tout en fournissant un retour d'information utile lors de l'interaction.

Voici un exemple d'un `Slider` entièrement configuré avec des infobulles :

### Personnalisation des infobulles {#tooltip-customization}

Par défaut, le `Slider` montre une infobulle avec sa valeur actuelle. Si vous souhaitez personnaliser ce texte, utilisez la méthode `setTooltipText()`. Cela est utile lorsque vous souhaitez que l'infobulle affiche du texte statique ou descriptif au lieu de la valeur en direct.

Vous pouvez également utiliser une expression JavaScript pour formater dynamiquement l'infobulle. Si votre expression inclut le mot-clé `return`, elle est utilisée telle quelle. Sinon, elle est automatiquement enroulée avec `return` et `;` pour former une fonction valide. Par exemple :

```java
// Montre la valeur suivie d'un signe dollar
slider.setTooltipText("return x + '$'"); 
```

Ou simplement :

```java
// Interprété comme : return x + ' unités';
slider.setTooltipText("x + ' unités'"); 
```

## Stylisation {#styling}

### Thèmes {#themes}

Le `Slider` est livré avec 6 thèmes intégrés pour un stylisme rapide sans l'utilisation de CSS. La thématisation est prise en charge par l'utilisation d'une classe d'énumération intégrée.
Ci-dessous sont montrés des sliders avec chacun des thèmes pris en charge appliqués :

<ComponentDemo 
path='/webforj/sliderthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/slider/SliderThemesView.java'
height = '460px'
/>

<TableBuilder name="Slider" />
