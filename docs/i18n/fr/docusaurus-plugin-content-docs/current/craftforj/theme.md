---
title: Thème
sidebar_position: 6
description: >-
  Adjust the DWC design tokens of a running webforJ app, preview the result
  immediately, and save it into your stylesheet.
_i18n_hash: fe089afe4f44e457a98622707bf995e5
---
L’onglet Theme vous permet de modifier l’apparence de votre application pendant son exécution. Il utilise les [jetons de conception DWC](/docs/styling/css-variables) dont votre application se sert déjà : une seule modification s’applique à chaque composant qui utilise ce jeton, plutôt que de devoir modifier les règles une par une.

<VideoPlayer video="craftforj/theme-knobs" width={1920} height={992} title="Theme knobs" />

## Ajuster un thème {#adjusting-a-theme}

Les commandes sont regroupées selon leur effet : elles couvrent la palette qui sert de base à l’application, les surfaces d’arrière-plan, la forme des bords et des coins, la typographie et l’espacement. Chaque commande explique son effet, car certaines modifient la lisibilité de l’application, et pas seulement son apparence.

Un thème comporte une variante claire et une variante sombre. Vous pouvez appliquer une modification aux deux ou à une seule, et basculer entre les deux variantes dans l’application pour voir celle sur laquelle vous travaillez. Un aperçu rassemble la palette, les surfaces, un spécimen typographique et les couleurs d’état : vous pouvez ainsi repérer une combinaison qui fonctionne sur un écran, mais pas sur un autre, avant de l’enregistrer.

![Les commandes du thème à côté de l’aperçu](/img/craftforj/theme/knob-rail.png#rounded-border)

## Enregistrer un thème {#saving-a-theme}

Le thème sur lequel vous travaillez est appliqué à l’application, mais ne fait pas encore partie de votre projet. Si vous rechargez la page, il est perdu. L’enregistrer l’ajoute à la feuille de style de votre application : il est alors conservé après les redémarrages, apparaît dans votre diff et est inclus dans votre application.

craftforJ écrit dans une seule feuille de style, qu’il détecte automatiquement ou que vous indiquez dans les paramètres de craftforJ. Si ce fichier contient déjà un thème, l’enregistrement le remplace entièrement au lieu d’en superposer un autre, et craftforJ vous demande d’abord de confirmer. Si le fichier a été modifié depuis que craftforJ l’a lu, aucune modification n’est écrite et craftforJ vous demande de réessayer l’enregistrement.

Vous pouvez rétablir la dernière version enregistrée d’un thème ou le supprimer entièrement de la feuille de style sans toucher au reste du fichier.

## Thèmes prédéfinis {#preset-themes}

En plus de l’apparence par défaut, craftforJ propose plusieurs thèmes prédéfinis. Voici une comparaison entre les thèmes App Default et Portico.

<Tabs>
  <TabItem value="app-default" label="App Default" default>
    ![Application avec le thème App Default](/img/craftforj/theme/theme-app-default.png#rounded-border)
  </TabItem>
  <TabItem value="portico" label="Portico">
    ![Application avec le thème Portico](/img/craftforj/theme/theme-portico.png#rounded-border)
  </TabItem>
</Tabs>

## Désactiver cette fonctionnalité {#turning-it-off}

Vous pouvez désactiver l’enregistrement des styles d’une application dans les paramètres de craftforJ, ou le supprimer entièrement à l’aide de la propriété [`stylesheet-changes`](/docs/craftforj/configuration#feature-flags). Dans les deux cas, l’onglet reste fonctionnel et actualise toujours l’application en cours d’exécution, mais vous ne pouvez pas enregistrer le résultat.
