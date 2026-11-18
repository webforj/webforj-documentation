---
sidebar_position: 1000
title: Glossaire
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definitions for terms used across webforJ documentation, including DOM, Shadow
  DOM, and other web platform and framework concepts.
_i18n_hash: b8c6955dd483c1d5904668a163666015
---
## DOM {#dom}

Le DOM (Document Object Model, modèle objet de document) est une interface de programmation pour les documents web. Il représente la structure d’une page sous forme d’arbre d’objets, où chaque nœud correspond à un élément HTML. JavaScript et les frameworks web utilisent le DOM pour accéder dynamiquement au contenu, à la structure et au style des pages web, et les modifier.

## DOM fantôme {#shadow-dom}

Le DOM fantôme (Shadow DOM) est une norme web qui permet d’encapsuler le DOM (Document Object Model) et le CSS dans un élément spécifique, appelé arbre fantôme. Ce DOM et ce CSS isolés sont séparés du DOM du document principal, ce qui crée une frontière de portée pour le composant. Le DOM fantôme permet de créer des composants web autonomes et réutilisables, qui peuvent être ajoutés à une page web sans risque de conflit avec les autres styles et scripts de la page.

Il introduit également des fonctionnalités comme les parties fantômes et les slots, qui permettent aux développeurs d’exposer certaines parties de l’arbre fantôme afin que la page parente puisse les personnaliser. Cela offre un moyen flexible de transmettre du contenu au composant et d’en personnaliser l’apparence tout en préservant l’encapsulation.

## Composants à portée limitée {#scoped-components}

Composants dont les styles sont isolés afin d’éviter les conflits avec les styles extérieurs au composant. Cette isolation est obtenue en appliquant des attributs de données uniques aux styles du composant, plutôt qu’en s’appuyant sur le DOM fantôme natif.
