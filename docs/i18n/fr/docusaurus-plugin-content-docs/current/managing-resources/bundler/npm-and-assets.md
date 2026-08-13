---
title: Packages and assets
sidebar_position: 30
sidebar_class_name: new-content
description: >-
  Declare npm packages, load a module straight from one, install build-only
  dependencies, rely on tree shaking, and import CSS and assets from a
  component's entry.
_i18n_hash: 3b538e3785ee74397f156dd38ef8506a
---
Une entrée s'appuie sur plus que sa propre source. Elle importe des packages depuis npm, charge un module directement à partir de l'un d'eux, intègre une feuille de style ou une image, et peut provenir d'une classe que vous étendez ou d'une bibliothèque dont vous dépendez. Cette page traite de la manière dont ces éléments atteignent la compilation.

## Déclaration d'un package {#declaring-a-package}

`@BundlePackage` déclare une dépendance npm qu'une entrée importe. La compilation recueille chaque déclaration sur le classpath et l'ajoute au [`package.json`](https://docs.npmjs.com/cli/v11/configuring-npm/package-json) de votre projet, puis [Bun](https://bun.sh/) l'installe avant de compiler, de sorte qu'un package soit présent au moment de la compilation de son entrée. Vos propres modifications de ce fichier sont préservées, et un projet qui ne déclare aucun package et n'a pas de `package.json` propre n'en garde aucun, donc npm reste à l'écart d'une compilation qui n'en a pas besoin.

Une bibliothèque de composants Web est le cas courant. Déclarez le package, puis pointez `@BundleEntry` vers les modules de composants que vous souhaitez :

```java title="Ui5View.java"
@Route("/ui5")
@BundlePackage(value = "@ui5/webcomponents", version = "^2.0.0")
@BundleEntry("@ui5/webcomponents/dist/Button.js")
@BundleEntry("@ui5/webcomponents/dist/Input.js")
public class Ui5View extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public Ui5View() {
    self.setAlignment(FlexAlignment.CENTER)
      .setStyle("margin", "1em");

    Element input = new Element("ui5-input");
    input.setProperty("placeholder", "Tapez quelque chose");

    Element button = new Element("ui5-button");
    button.setProperty("design", "Emphasized");
    button.setText("Dites bonjour");
    button.addEventListener("click", e ->
      Toast.show("Bonjour " + input.getProperty("value"))
    );

    self.add(input, button);
  }
}
```

La `version` suit la syntaxe de version npm, donc `^2.0.0` accepte les versions compatibles 2.x. À la fois `@BundlePackage` et `@BundleEntry` sont répétables, donc une classe déclare autant de packages et charge autant de modules qu'elle en a besoin.

### Une entrée de fichier ou un module npm {#a-file-entry-or-an-npm-module}

La valeur de `@BundleEntry` est l'une de deux choses : un chemin vers un fichier que vous avez créé sous `src/main/frontend`, ou un chemin vers un module dans un package npm. La vue ci-dessus nomme les chemins des modules à l'intérieur de `@ui5/webcomponents`, elle ne contient donc aucun fichier source propre. Chacun de ces modules enregistre son propre élément personnalisé lorsqu'il se charge, c'est pourquoi la vue consomme `ui5-input` et `ui5-button` sans wrapper. Une entrée de fichier pointe plutôt vers un fichier `.ts`, `.js` ou `.css` que vous avez écrit, compilé de la même manière.

### Dépendances de compilation {#build-dependencies}

Un package requis uniquement pour compiler les sources, pas à l'exécution, est une dépendance de compilation. Mettez `dev = true` sur `@BundlePackage`, et la compilation l'installera en tant que `devDependency` :

```java
@BundlePackage(value = "typescript", version = "^5.0.0", dev = true)
```

Les extensions soigneusement choisies utilisent cela pour les packages nécessaires à leurs compilateurs, c'est pourquoi une source SCSS intègre `sass` en tant que dépendance de compilation et rien à l'exécution.

## Seulement ce que vous importez {#tree-shaking}

Le compilateur inclut uniquement le code qu'une entrée importe réellement. Nommer `@ui5/webcomponents/dist/Button.js` intègre le composant Button et ce dont il a besoin, pas le reste de la bibliothèque. Une bibliothèque large ne coûte que les parties que vous réclamez, donc il n'y a pas de pénalité à déclarer un grand package et à en charger un module.

### Code partagé {#shared-code}

Lorsque deux entrées importent le même package, la compilation factorise le code partagé dans un morceau que les deux chargent, plutôt que de le copier dans chacune. Plusieurs composants construits sur la même bibliothèque, un ensemble d'éléments Lit par exemple, partagent le code de cette bibliothèque sur une page au lieu de payer pour elle une fois par élément.

## Comment les entrées se chargent {#how-entries-load}

Une entrée produit un script, une feuille de style ou les deux, et le runtime charge cette sortie la première fois qu'un composant de sa classe est créé, peu importe où ce composant est utilisé et à quel point il est profondément imbriqué. Une vue routée et une disposition sont des composants comme les autres, donc une entrée se lie à la création de composants, pas au routage. Deux détails suivent de l'annotation vivant sur la classe :

- **Héritage.** `@BundleEntry` et `@BundlePackage` sont hérités. Une classe de base déclare l'entrée, et une sous-classe qui n'ajoute rien de son propre charge quand même.
- **Entrées de débogage.** Une entrée déclarée `@BundleEntry(value = "...", debug = true)` ne se charge que lorsque l'application s'exécute en mode débogage, ce qui convient à un diagnostic uniquement en développement.

## Importation de CSS et d'actifs {#importing-css-and-assets}

Une entrée de composant gère les feuilles de style et d'autres actifs via des imports, sans annotation et sans extension. Bun les résout au moment de la compilation.

Importez une feuille de style pour son effet secondaire, et le bundler l'inclut dans les styles de l'entrée. Importez un actif non code, et l'importation vous donne une URL à utiliser :

```ts title="card/card.ts"
import './card.css';
import logoPath from './logo.svg';

const logo = new URL(logoPath, import.meta.url).href;
// utilisez logo comme source d'image à l'intérieur de l'élément
```

Résolvez une URL d'actif contre `import.meta.url`, pas le document, afin qu'elle pointe vers l'actif compilé où qu'elle soit servie.

Importez une feuille de style comme texte à la place, et appliquez-la à l'intérieur d'une racine shadow pour limiter les styles à un seul élément :

```ts title="swatch/swatch.ts"
import sheet from './swatch.css' with { type: 'text' };

class ColorSwatch extends HTMLElement {
  connectedCallback() {
    const root = this.attachShadow({ mode: 'open' });
    const style = document.createElement('style');
    style.textContent = sheet;
    root.append(style);
  }
}
```

Une entrée peut également être un simple fichier `.css` sans script, lié à une classe de la même manière qu'une entrée de script. Le runtime le charge comme styles pour la vue :

```java title="ThemeView.java"
@Route("/theme")
@BundleEntry("theme/theme.css")
public class ThemeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ThemeView() {
    self.add(new Div("Stylé par une entrée de bundle uniquement CSS")
                 .addClassName("themed-label"));
  }
}
```
