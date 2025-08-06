---
sidebar_position: 40
title: Interactivity
slug: Interactivity
_i18n_hash: 64d7e4b5603b5335b498c05566de1784
---
Le composant `Table` répondra à diverses entrées clavier des utilisateurs pour permettre une interaction plus fine. La liste suivante décrit les divers comportements lorsque la touche désignée est enfoncée :


## Interactions Clavier {#keyboard-interations}

|Touche|Comportement|
|:-:|-|
|<kbd>Barre d'espace</kbd>|Sélectionne la ligne actuelle.|
|<kbd>Entrée</kbd>|Met le focus sur la cellule, ou sur l'élément focalisable si la cellule a un rendu.| 
|<kbd>Home</kbd>|Met le focus sur la première cellule de la première colonne et ligne.| 
|<kbd>Fin</kbd>|Met le focus sur la dernière cellule de la dernière colonne et ligne.| 
|<kbd>Maj</kbd> + <kbd>Tab</kbd>|Si la cellule se trouve dans le corps du tableau et que Maj + Tab est enfoncé, il met le focus sur la première cellule focalisable dans l'en-tête.| 
|<kbd>&#8594;</kbd>|Si la touche Ctrl est enfoncée, il met le focus sur la dernière cellule de la ligne actuelle. Sinon, il met le focus sur la cellule suivante horizontalement.| 
|<kbd>&#8592;</kbd>|Si la touche Ctrl est enfoncée, il met le focus sur la première cellule de la ligne actuelle. Sinon, il met le focus sur la cellule précédente horizontalement.| 
|<kbd>&#8593;</kbd>|Si la touche Ctrl est enfoncée, il met le focus sur la première cellule de la colonne actuelle. Sinon, il met le focus sur la cellule précédente verticalement.| 
|<kbd>&#8595;</kbd>|Si la touche Ctrl est enfoncée, il met le focus sur la dernière cellule de la colonne actuelle. Sinon, il met le focus sur la cellule suivante verticalement.|
