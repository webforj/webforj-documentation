---
sidebar_position: 40
title: Interactivité
slug: Interactivity
description: >-
  Consultez les raccourcis clavier pour déplacer le focus, sélectionner des
  lignes et naviguer entre les cellules du composant Table.
_i18n_title_hash: fcbe6546766fcb51d1197a3d75a7a7f4
_i18n_description_hash: 84575804ecb073661d379d81f9143b66
_i18n_hash: 49377400524e9e1c3a25dd35cfb7750c
---
Le composant `Table` réagit à différentes saisies au clavier pour permettre une interaction plus précise. La liste suivante décrit le comportement associé à chaque touche :

## Interactions au clavier {#keyboard-interactions}

|Touche|Comportement|
|:-:|-|
|<kbd>Space</kbd>|Sélectionne la ligne actuelle.|
|<kbd>Enter</kbd>|Place le focus sur la cellule, ou sur l’élément pouvant recevoir le focus si la cellule a un moteur de rendu.|
|<kbd>Home</kbd>|Place le focus sur la première cellule de la première colonne et de la première ligne.|
|<kbd>End</kbd>|Place le focus sur la dernière cellule de la dernière colonne et de la dernière ligne.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Si la cellule se trouve dans le corps du tableau et que les touches Shift + Tab sont pressées, place le focus sur la première cellule de l’en-tête pouvant recevoir le focus.|
|<kbd>&#8594;</kbd>|Si la touche Ctrl est pressée, place le focus sur la dernière cellule de la ligne actuelle. Sinon, place le focus sur la cellule suivante horizontalement.|
|<kbd>&#8592;</kbd>|Si la touche Ctrl est pressée, place le focus sur la première cellule de la ligne actuelle. Sinon, place le focus sur la cellule précédente horizontalement.|
|<kbd>&#8593;</kbd>|Si la touche Ctrl est pressée, place le focus sur la première cellule de la colonne actuelle. Sinon, place le focus sur la cellule précédente verticalement.|
|<kbd>&#8595;</kbd>|Si la touche Ctrl est pressée, place le focus sur la dernière cellule de la colonne actuelle. Sinon, place le focus sur la cellule suivante verticalement.|
