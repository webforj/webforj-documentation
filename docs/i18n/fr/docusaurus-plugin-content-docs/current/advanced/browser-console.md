---
sidebar_position: 5
title: Browser Console
_i18n_hash: 340e3d6f1d09c67ecc3d2d93bcd23b28
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Utiliser la console du navigateur pour imprimer des informations précieuses sur le programme est une partie intégrante du processus de développement. La classe utilitaire <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> offre de nombreuses fonctionnalités pour améliorer les capacités de journalisation.

<!-- :::info
Avant `24.10`, les méthodes `App.consoleLog()` et `App.consoleError()` permettaient ce comportement, mais elles ont depuis été marquées pour dépréciation.
::: -->

## Instance {#instance}

Obtenez une instance de `BrowserConsole` en utilisant la méthode `App.console()`. Imprimez n'importe quel `Object` souhaité comme l'un des cinq types de journal : log, info, warn, error ou debug.

```java
import static com.webforj.App.console;
// Types
console().log("Message de journal");
console().info("Message d'information");
console().warn("Message d'avertissement");
console().error("Message d'erreur");
console().debug("Message de débogage");
```

## Styling {#styling}

Utilisez les méthodes de constructeur pour définir l'apparence du message de journal. Chaque constructeur a des options pour changer une propriété spécifique. Il est également possible de [mélanger plusieurs styles](#mixing-styles).
Une fois qu'un message de console est imprimé, tout style appliqué ne sera pas transféré aux messages suivants à moins d'être *explicitement* redéfini.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Utilisez la méthode `setStyle` pour changer les propriétés du journal `BrowserConsole` qui ne sont pas spécifiées par les constructeurs.
:::

### Couleur de fond {#background-color}

Définissez la couleur de fond avec la méthode `background()`, qui retourne le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>.
Utilisez des méthodes nommées par couleur, comme `blue()`, ou choisissez une valeur spécifique avec `colored(String color)`.

```java
// Exemples de fond
console().background().blue().log("Fond bleu");
console().background().colored("#031f8f").log("Fond bleu personnalisé");
```

### Couleur du texte {#text-color}

Définissez la couleur du texte avec la méthode `color()`, qui retourne le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>.
Utilisez des méthodes nommées par couleur, comme `red()`, ou choisissez une valeur spécifique avec `colored(String color)`.

```java
// Exemples de couleur
console().background().red().log("Texte rouge");
console().color().colored("#becad2").log("Texte personnalisé de gris clair-bleu");
```

### Taille de police {#font-size}

Définissez la taille de la police avec la méthode `size()`, qui retourne le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>.
Utilisez des méthodes nommées par taille, comme `small()`, ou choisissez une valeur spécifique avec `from(String value)`.

```java
// Exemples de taille
console().size().small().log("Petite police");
console().size().from("30px").log("Police de 30px");
```
:::tip
La méthode `from(String value)` peut prendre d'autres valeurs de taille de police, telles que rem et vw.
:::

### Style de police {#font-style}

Définissez le style de la police avec la méthode `style()`, qui retourne le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>.
Par exemple, utilisez la méthode `italic()` pour rendre le journal de la console en italique.

```java
// Exemples de style
console().style().italic().log("Police en italique");
console().style().normal().log("Police normale");
```

### Transformation du texte {#text-transformation}

Contrôlez la capitalisation des caractères dans un message avec la méthode `transform()`, qui retourne le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>.
Par exemple, utilisez la méthode `capitalize()` pour transformer la première lettre de chaque mot en majuscule.

```java
// Exemples de transformation
// Transformation de texte en capitalisant
console().transform().capitalize().log("Transformation de texte en capitalisant");
// TRANSFORMATION DE TEXTE EN MAJUSCULES 
console().transform().uppercase().log("Transformation de texte en majuscules");
```

### Poids de la police {#font-weight}

Définissez l'épaisseur du texte avec la méthode `weight()`, qui retourne le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>.
Par exemple, utilisez la méthode `ligther()` pour rendre la police plus légère que la normale.

```java
// Exemples de poids
console().weight().bold().log("Police en gras");
console().weight().lighter().log("Police plus légère");
```

## Mélanger les styles {#mixing-styles}
Il est possible de mélanger et d'associer des méthodes pour un affichage de journal personnalisé.

```java
// Une variété d'options pour un affichage de journal personnalisé
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Mélange de styles");
```
