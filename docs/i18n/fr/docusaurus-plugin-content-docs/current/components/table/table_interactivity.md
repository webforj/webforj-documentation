---
sidebar_position: 40
title: Interactivité
slug: Interactivity
_i18n_hash: 8ec1ec3ef58f8bfcde31ee8dc9891579
---
Le composant `Table` réagira à diverses entrées au clavier des utilisateurs pour permettre une interaction plus précise. La liste suivante décrit les différents comportements lorsque la touche désignée est enfoncée :

## Interactions Clavier {#keyboard-interations}

|Touche|Comportement|
|:-:|-|
|<kbd>Espace</kbd>|Sélectionne la ligne actuelle.|
|<kbd>Entrée</kbd>|Focalise la cellule, ou l'élément focalisable si la cellule a un renderer.|
|<kbd>Accueil</kbd>|Focalise la première cellule de la première colonne et de la première ligne.|
|<kbd>Fin</kbd>|Focalise la dernière cellule de la dernière colonne et de la dernière ligne.|
|<kbd>Shift</kbd> + <kbd>Tab</kbd>|Si la cellule se trouve dans le corps du tableau et que Shift + Tab est enfoncé, cela focalise la première cellule focalisable dans l'en-tête.|
|<kbd>&#8594;</kbd>|Si la touche Ctrl est enfoncée, cela focalise la dernière cellule de la ligne actuelle. Sinon, cela focalise la cellule suivante horizontalement.|
|<kbd>&#8592;</kbd>|Si la touche Ctrl est enfoncée, cela focalise la première cellule de la ligne actuelle. Sinon, cela focalise la cellule précédente horizontalement.|
|<kbd>&#8593;</kbd>|Si la touche Ctrl est enfoncée, cela focalise la première cellule de la colonne actuelle. Sinon, cela focalise la cellule précédente verticalement.|
|<kbd>&#8595;</kbd>|Si la touche Ctrl est enfoncée, cela focalise la dernière cellule de la colonne actuelle. Sinon, cela focalise la cellule suivante verticalement.|
