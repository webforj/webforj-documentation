---
title: Tailwind
sidebar_position: 60
sidebar_class_name: new-content
description: >-
  Turn on the webforj-tailwind extension, apply utility classes from a view, and
  understand how it generates and scans its own stylesheet.
_i18n_hash: f588624ebd738977bb8be4e9887141d1
---
[Tailwind CSS](https://tailwindcss.com/) est un framework CSS utilitaire dont les noms de classes correspondent chacun à un petit ensemble de déclarations CSS. C'est la seule extension sélectionnée qui est livrée, car la plupart des projets ne l'utilisent pas. Vous l'activez par identifiant, de la même manière que vous activez n'importe quelle extension, voir [Activé par id](/docs/managing-resources/bundler/extensions/overview#enabled-by-id). Lorsqu'elle est active, elle fait quelque chose que les autres ne font pas : elle génère sa propre entrée.

## Comment ça fonctionne {#how-it-works}

Plutôt que de compiler un fichier que vous avez écrit, l'extension Tailwind analyse les sources de votre application pour les noms de classes utilitaires qu'elles utilisent, génère une feuille de style qui contient uniquement ces utilitaires et la charge pour chaque vue. Une vue applique alors des utilitaires comme noms de classes sans rien importer :

```java title="TailwindView.java"
@Route("/tailwind")
public class TailwindView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public TailwindView() {
    Div card = new Div("Stylisé par les utilitaires compilés de Tailwind");
    card.addClassName("flex", "items-center", "gap-4", "p-8", "rounded-lg",
        "bg-blue-500", "text-white");
    self.add(card);
  }
}
```

La feuille de style générée importe le thème et les utilitaires de Tailwind mais pas sa réinitialisation de base. La réinitialisation, le préflight de Tailwind, restaure chaque élément nu sur la page, ce qui remplacerait le style que webforJ applique déjà à ses composants. En l'absence de cette réinitialisation, cela permet aux classes utilitaires que vous ajoutez de fonctionner sans perturber les composants que vous n'avez pas modifiés.

Parce que les utilitaires viennent des noms de classes utilisées par vos vues, le [frontend watch](/docs/configuration/deploy-reload/frontend-watch) suit vos sources d'application ainsi que `src/main/frontend`. Ajoutez ou supprimez une classe utilitaire dans une vue et enregistrez, et la feuille de style se régénère et se patch dans la page à sa place, de la même manière que l'édition d'une feuille de style que vous avez écrite.

## Où les classes utilitaires atteignent {#where-utility-classes-reach}

:::warning Une classe utilitaire stylise l'élément, pas l'intérieur d'un composant
Les composants webforJ rendent avec un DOM shadow qui garde leur structure interne privée. Une classe utilitaire ajoutée à un composant ne stylise que sa boîte extérieure, son espacement, sa taille et son emplacement dans une mise en page, et n'atteint jamais les éléments dessinés à l'intérieur. Les utilitaires s'appliquent comme leurs noms de classe se lisent sur un conteneur de mise en page ou un `Div` ordinaire que vous construisez, où il n'y a pas de frontière à franchir, mais pas sur l'intérieur d'un composant construit.

Pour styliser ce qui se trouve à l'intérieur d'un composant, utilisez le style que le composant expose à la place : [parties d'ombre](/docs/styling/shadow-parts) via `::part()` et les [propriétés CSS personnalisées](/docs/styling/css-variables) du composant, toutes listées dans la référence de style de chaque composant. Gardez les utilitaires pour la mise en page et pour vos propres éléments, et utilisez le style propre d'un composant pour régler le composant.
:::

La feuille de style contient les noms de classes utilitaires que l'analyse trouve dans vos sources, et uniquement ceux-ci. Une classe que vous tapez dans l'inspecteur de navigateur pour essayer une idée ne s'appliquera pas, car elle n'a jamais été compilée. Mettez la classe dans une vue et enregistrez, et le watch régénérera la feuille de style avec elle.

Lorsque le même groupe d'utilitaires se répète dans de nombreuses vues, nommez-le : définissez une classe CSS une fois et ajoutez-la à la place. Quelques utilitaires en ligne restent lisibles, une longue chaîne répétée à la main dérive au fur et à mesure que vous éditez.

## Options {#options}

L'extension Tailwind ne prend aucune option depuis `bun.config.ts`. Elle génère et analyse sa propre feuille de style, et Tailwind lui-même est configuré via cette feuille de style plutôt que par le biais de l'extension.
