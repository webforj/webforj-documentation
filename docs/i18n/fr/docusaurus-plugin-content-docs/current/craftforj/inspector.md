---
title: Inspecter les composants
sidebar_position: 3
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 5dd1df77df56d81dd4e54c1998289e71
---
L'Inspecteur montre l'arbre des composants que votre code Java a créé. Un `Composite` apparaît comme la classe que vous avez écrite, contenant les enfants que vous lui avez donnés dans l'ordre dans lequel webforJ les détient, de sorte que la structure dans craftforJ correspond à la structure dans votre source.

![L'arbre des composants avec un composant sélectionné et mis en surbrillance dans l'application en cours](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Sélectionner un composant {#selecting-a-component}

Pour sélectionner un composant de la page, appuyez sur <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> et cliquez dessus. craftforJ sélectionne le nœud correspondant dans l'arbre. Survoler un nœud dans l'arbre fait l'inverse et met en surbrillance ce composant sur la page, vous permettant de naviguer entre l'écran et l'arbre dans les deux sens.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4" type="video/mp4" />
  </video>
</div>

Pour rechercher dans l'arbre, appuyez sur <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Enveloppant un terme dans des barres obliques le considère comme une expression régulière. Clic droit sur un nœud ouvre les actions disponibles pour celui-ci. Vous pouvez ouvrir sa source ou la confier à l'[assistant](/docs/craftforj/ai).

## Lire et changer les propriétés {#reading-and-changing-properties}

Sélectionner un composant remplit la barre latérale avec ses propriétés, regroupées par ce qu'elles affectent. Les propriétés qu'un composant offre dépendent du composant, et certaines d'entre elles sont en lecture seule. Les propriétés qui ne se lisent pas bien comme texte brut obtiennent un éditeur adapté à leur valeur à la place. Changer une valeur prend effet dans l'application en cours immédiatement.

:::info Les modifications en direct ne modifient pas vos fichiers
Une modification de propriété change l'application devant vous et rien d'autre. L'intégration dans votre source est une étape distincte que vous réalisez délibérément, décrite dans [Écriture des modifications dans la source](/docs/craftforj/source-changes).
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4" type="video/mp4" />
  </video>
</div>

## Voir la source d'un composant {#viewing-the-source-of-a-component}

Vous pouvez retracer n'importe quel composant jusqu'au Java qui l'a construit. Par défaut, la source s'ouvre dans craftforJ en mode lecture seule, positionnée à la ligne qui a créé le composant. Vous pouvez configurer craftforJ pour l'ouvrir dans votre éditeur à la même ligne. Lorsqu'un composant ne peut pas être retracé jusqu'à une ligne, craftforJ signale cela plutôt que d'ouvrir un visualiseur vide.

![Le visualiseur de source positionné à la ligne qui a créé le composant sélectionné](/img/craftforj/inspector/source-viewer.png#rounded-border)
