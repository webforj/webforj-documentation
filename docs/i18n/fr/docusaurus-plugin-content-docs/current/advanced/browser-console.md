---
sidebar_position: 15
title: Browser Console
_i18n_hash: 843587956991faa037138ce8e8563e7a
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Utiliser la console du navigateur pour imprimer des informations sur le programme est une partie intégrante du processus de développement. La classe utilitaire <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> fournit des fonctionnalités qui améliorent les capacités de journalisation à travers des types de journaux et du style.

## Instance {#instance}

Obtenez une instance de `BrowserConsole` en utilisant la méthode `App.console()`. Imprimez tout `Object` souhaité comme l'un des cinq types de journaux : log, info, warn, error ou debug.

```java
import static com.webforj.App.console;
// Types
console().log("Message de log");
console().info("Message d'information");
console().warn("Message d'avertissement");
console().error("Message d'erreur");
console().debug("Message de débogage");
```

## Styling {#styling}

Utilisez les méthodes de construction pour définir l'apparence du message de journal. Chaque constructeur a des options pour changer une propriété spécifique. Il est également possible de [mélanger plusieurs styles](#mixing-styles).
Une fois qu'un message de console est imprimé, tout style appliqué ne se transmettra pas aux messages suivants à moins d'être *explicitement* redéfini.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Utilisez la méthode `setStyle` pour changer les propriétés du journal `BrowserConsole` non spécifiées par les constructeurs.
:::

### Background color {#background-color}

Définissez la couleur d'arrière-plan avec la méthode `background()`, qui renvoie le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink>.
Utilisez des méthodes nommées par couleur, comme `blue()`, ou choisissez une valeur spécifique avec `colored(String color)`.

```java
// Exemples d'arrière-plan
console().background().blue().log("Arrière-plan bleu");
console().background().colored("#031f8f").log("Arrière-plan bleu personnalisé");
```

### Text color {#text-color}

Définissez la couleur du texte avec la méthode `color()`, qui renvoie le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink>.
Utilisez des méthodes nommées par couleur, comme `red()`, ou choisissez une valeur spécifique avec `colored(String color)`.

```java
// Exemples de couleur
console().background().red().log("Texte rouge");
console().color().colored("#becad2").log("Texte personnalisé gris-bleu clair");
```

### Font size {#font-size}

Définissez la taille de la police avec la méthode `size()`, qui renvoie le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink>.
Utilisez des méthodes nommées par une taille, comme `small()`, ou choisissez une valeur spécifique avec `from(String value)`.

```java
// Exemples de taille
console().size().small().log("Police petite");
console().size().from("30px").log("Police 30px");
```
:::tip
La méthode `from(String value)` peut prendre d'autres valeurs de taille de police, telles que rem et vw.
:::

### Font style {#font-style}

Définissez le style de la police avec la méthode `style()`, qui renvoie le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink>.
Par exemple, utilisez la méthode `italic()` pour rendre le journal de la console en italique.

```java
// Exemples de style
console().style().italic().log("Police italique");
console().style().normal().log("Police normale");
```

### Text transformation {#text-transformation}

Contrôlez la capitalisation des caractères dans un message avec la méthode `transform()`, qui renvoie le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink>.
Par exemple, utilisez la méthode `capitalize()` pour transformer la première lettre de chaque mot en majuscule.

```java
// Exemples de transformation
// Transformation de texte en capitales
console().transform().capitalize().log("Transformation de texte en capitales");
// TRANSFORMATION DE TEXTE EN MAJUSCULES 
console().transform().uppercase().log("Transformation de texte en MAJUSCULES");
```

### Font weight {#font-weight}

Définissez l'épaisseur du texte avec la méthode `weight()`, qui renvoie le <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink>.
Par exemple, utilisez la méthode `lighter()` pour rendre la police plus légère que la normale.

```java
// Exemples de poids
console().weight().bold().log("Police gras");
console().weight().lighter().log("Police plus légère");
```

## Mixing styles {#mixing-styles}
Il est possible de mélanger et d'associer des méthodes pour un affichage personnalisé des journaux.

```java
// Une variété d'options pour un affichage personnalisé des journaux
console()
  .weight().bolder()
  .size().larger()
  .color().gray()
  .style().italic()
  .transform().uppercase()
  .background().blue()
  .warn("Mélange de styles");
```
