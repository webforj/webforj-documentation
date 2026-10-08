---
title: Thème
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: 98545075c2ac2777380812af08d71345
---
L'onglet Thème vous permet de modifier l'apparence de votre application pendant son exécution. Il fonctionne avec les [tokens de design DWC](/docs/styling/css-variables) que votre application utilise déjà, ainsi un changement unique atteint chaque composant qui lit ce token plutôt qu'une règle à la fois.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/theme-knobs.mp4" type="video/mp4" />
  </video>
</div>

## Ajustement d'un thème {#adjusting-a-theme}

Les contrôles sont regroupés par ce qu'ils affectent, couvrant la palette à partir de laquelle l'application est construite, les surfaces derrière elle, la forme de ses bords et coins, sa typographie et son espacement. Chaque contrôle explique ce qu'il fait, car certains d'entre eux changent la lisibilité de l'application plutôt que seulement son apparence.

Un thème a un côté clair et un côté sombre. Vous pouvez appliquer une modification aux deux ou à un seul, et faire basculer l'application entre eux pour voir le côté sur lequel vous travaillez. Un aperçu montre la palette, les surfaces, un spécimen de typographie et les couleurs d'état ensemble, vous permettant d'identifier une combinaison qui fonctionne sur un écran mais pas sur un autre avant de l'enregistrer.

![Les contrôles de thème à côté de l'aperçu](/img/craftforj/theme/knob-rail.png#rounded-border)

## Enregistrement d'un thème {#saving-a-theme}

Un thème sur lequel vous travaillez est appliqué à l'application mais ne fait pas encore partie de votre projet, et recharger la page le supprime. L'enregistrement l'écrit dans la feuille de style de votre application, où il survit aux redémarrages, apparaît dans votre différence et est expédié avec votre application.

craftforJ écrit dans une seule feuille de style, qu'il détecte par lui-même ou que vous nommez dans les paramètres de craftforJ. Si ce fichier contient déjà un thème, l'enregistrement le remplace dans son ensemble plutôt que de superposer un second dessus, et craftforJ vous demande de confirmer d'abord. Si le fichier a changé après que craftforJ l'a lu, rien n'est écrit et craftforJ vous demande de sauvegarder à nouveau.

Vous pouvez revenir à un thème dans son dernier état enregistré, ou le supprimer complètement de la feuille de style sans affecter quoi que ce soit d'autre dans le fichier.

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

Vous pouvez désactiver l'enregistrement des styles pour une application dans les paramètres de craftforJ, ou le supprimer complètement avec la propriété [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags). Avec l'un ou l'autre désactivé, l'onglet fonctionne toujours et peint à nouveau l'application en cours d'exécution, mais vous ne pouvez pas enregistrer le résultat.
