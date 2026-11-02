---
title: Theme
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: c7dd349ebf7570b2e34953fce990173d
---
L'onglet Thème vous permet de modifier l'apparence de votre application pendant son exécution. Il fonctionne avec les [tokens de conception DWC](/docs/styling/css-variables) que votre application utilise déjà, de sorte qu'un seul changement affecte tous les composants qui lisent ce token, au lieu de mettre à jour une règle à la fois.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Ajustement d'un thème {#adjusting-a-theme}

Les contrôles sont regroupés par ce qu'ils affectent, couvrant la palette à partir de laquelle l'application est construite, les surfaces derrière elle, la forme de ses bords et coins, sa typographie et son espacement. Chaque contrôle explique ce qu'il fait, car certains d'entre eux modifient la lisibilité de l'application plutôt que seulement son apparence.

Un thème a un côté clair et un côté sombre. Vous pouvez appliquer une modification aux deux côtés ou à un seul, et faire basculer l'application entre les deux pour voir le côté sur lequel vous travaillez. Un aperçu montre la palette, les surfaces, un spécimen de typographie et les couleurs d'état ensemble, afin que vous puissiez repérer une combinaison qui fonctionne sur un écran mais pas sur un autre avant de l'enregistrer.

![Les contrôles de thème à côté de l'aperçu](/img/craftforj/theme/knob-rail.png#rounded-border)

## Enregistrement d'un thème {#saving-a-theme}

Un thème sur lequel vous travaillez est appliqué à l'application mais ne fait pas encore partie de votre projet, et recharger la page le supprime. L'enregistrement l'écrit dans la feuille de style de votre application, où il survit aux redémarrages, apparaît dans votre diff et est expédié avec votre application.

craftforJ écrit dans une seule feuille de style, qu'il détecte automatiquement ou que vous nommez dans les paramètres de craftforJ. Si ce fichier contient déjà un thème, l'enregistrement le remplace entièrement plutôt que de superposer un second, et craftforJ vous demande de confirmer d'abord. Si le fichier a changé après que craftforJ l'ait lu, rien n'est écrit et craftforJ vous demande de sauvegarder à nouveau.

Vous pouvez rétablir un thème à son dernier état enregistré, ou le supprimer complètement de la feuille de style sans affecter quoi que ce soit d'autre dans le fichier.

## Thèmes prédéfinis {#preset-themes}

Au-delà de l'apparence par défaut, craftforJ dispose de plusieurs thèmes prédéfinis parmi lesquels choisir. Ce qui suit montre une comparaison entre les thèmes App Default et Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Application avec le thème App Default](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Application avec le thème Portico](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Désactivation {#turning-it-off}

Vous pouvez désactiver l'enregistrement des styles pour une application dans les paramètres de craftforJ, ou le supprimer entièrement avec la propriété [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags). Avec l'une ou l'autre désactivée, l'onglet fonctionne toujours et repeint l'application en cours d'exécution, mais vous ne pouvez pas enregistrer le résultat.
