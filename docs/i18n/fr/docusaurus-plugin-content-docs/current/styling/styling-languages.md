---
title: Styling languages
sidebar_position: 11
sidebar_class_name: new-content
description: >-
  Author your styles in CSS, compile them from Sass or Less, or generate them
  with Tailwind, and load the result into a webforJ app.
_i18n_hash: 98eca77023e33bac367a1a250da900d7
---
Vos styles atteignent la page sous forme de CSS, mais vous n'avez pas à les écrire comme du CSS. webforJ charge une feuille de style que vous rédigez, compile une à partir d'un préprocesseur tel que Sass ou Less, ou en génère une à partir de Tailwind, et le résultat stylise vos vues de la même manière, peu importe la provenance. Les tokens DWC, [propriétés CSS personnalisées](/docs/styling/css-variables), et [parties d'ombre](/docs/styling/shadow-parts) couvertes dans le reste de cette section s'appliquent à l'intérieur de chacun d'eux.

## CSS pur {#plain-css}

Une feuille de style que vous écrivez n'a pas besoin de compilation. Attachez-la à un composant ou à l'application avec [`@StyleSheet`](/docs/managing-resources/importing-assets#importing-css-files). Lorsque vous exécutez déjà le [bundler frontend](/docs/managing-resources/bundler/overview), vous pouvez plutôt lier un fichier `.css` à une classe avec `@BundleEntry`, ce qui le charge comme styles pour cette vue.

## Sass et Less {#sass-and-less}

Pour écrire vos styles en [Sass](https://sass-lang.com/) ou [Less](https://lesscss.org/), avec des variables, du nesting, et des fonctions, rédigez la source et laissez le [bundler frontend](/docs/managing-resources/bundler/overview) le compiler en CSS. Le compilateur est une [extension](/docs/managing-resources/bundler/extensions/overview) qui s'active lorsque source de son type est présente, donc rédiger un fichier `.scss`, `.sass`, ou `.less` est le seul signal dont il a besoin. Liez la source à une classe de la même manière que vous liez une feuille de style :

```java title="StyledView.java"
@Route("/styled")
@BundleEntry("styles/view.scss")
public class StyledView extends Composite<FlexLayout> {
  // construire la vue
}
```

L'extension compile `view.scss` en CSS et le charge pour la vue. Voir [SCSS et Sass](/docs/managing-resources/bundler/extensions/scss) et [Less](/docs/managing-resources/bundler/extensions/less) pour la structure des fichiers, les chemins de chargement, et les options que chacune accepte.

## Tailwind {#tailwind}

[Tailwind](https://tailwindcss.com/) génère une feuille de style à partir des noms de classes utilitaires que vos vues utilisent, plutôt qu'à partir d'un fichier que vous rédigez. Activez l'extension, puis ajoutez des utilitaires en tant que noms de classes sans rien importer. webforJ omet la réinitialisation de base de Tailwind pour ne pas contrecarrer le style que vos composants portent déjà, et un utilitaire atteint l'élément sur lequel vous l'appliquez, pas l'intérieur d'un composant. Voir [Extension Tailwind](/docs/managing-resources/bundler/extensions/tailwind) pour savoir comment elle génère et scope sa feuille de style, et où les classes utilitaires s'appliquent ou non.

## Une autre langue {#another-language}

Le compilateur pour chaque langue est une extension de bundler, et le modèle est ouvert. Pour rédiger vos styles dans une langue pour laquelle webforJ n'expédie pas de compilateur, écrivez une petite extension qui contribue ce compilateur, selon le même contrat que Sass et Less utilisent. Voir [Écriture de votre propre extension](/docs/managing-resources/bundler/extensions/writing-your-own).
