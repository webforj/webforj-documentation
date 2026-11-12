---
sidebar_position: 10
title: Architecture des composants
description: >-
  See how webforJ components are built on the DwcComponent base class, why
  component classes are final, and why composition replaces inheritance.
_i18n_hash: 13c29b780cb00a918fe6e0a98c5e783a
---
webforJ fournit une API Java pour une bibliothèque de composants web et gère chacun d’eux depuis le serveur. Cette page explique la structure des composants et comment les étendre.

## Structure des composants {#how-components-are-structured}
Chaque composant webforJ fourni avec le produit est une classe Java associée un à un à un composant web côté client de la bibliothèque de composants DWC. La classe Java contient l’état et l’API du composant sur le serveur, et le framework maintient la synchronisation avec le composant web dans le navigateur.

Vous travaillez uniquement avec la classe Java. Les propriétés, les événements et le rendu dans le navigateur sont gérés par le framework : vous pouvez donc créer des applications web uniquement en Java.

## La classe de base `DwcComponent` {#the-dwccomponent-base-class}
Au cœur de l’architecture des composants de webforJ se trouve la classe de base DWCComponent. Tous les composants webforJ héritent de cette classe. Elle gère le lien entre le composant Java et son homologue côté client, notamment sa création, la mise à jour de ses propriétés et la gestion des événements.

Il est toutefois important de noter que les développeurs ne sont pas autorisés à étendre davantage la classe DWCComponent. Toute tentative en ce sens entraînera une exception à l’exécution qui interdira ces extensions. Cette restriction vise à préserver l’intégrité de ce lien et à éviter que les développeurs ne le manipulent involontairement d’une manière susceptible d’entraîner des conséquences imprévues.

### Classes finales et restrictions d’extension {#final-classes-and-extension-restrictions}
Dans webforJ, la plupart des classes de composants, à l’exception des éléments HTML intégrés et des classes qui en héritent, sont déclarées `final`. Cela signifie qu’elles ne peuvent pas être étendues ni sous-classées. Ce choix de conception est intentionnel et répond à plusieurs objectifs :

1. **Contrôle du lien côté client** : comme indiqué précédemment, l’extension des classes de composants webforJ exposerait le lien interne entre le composant Java et son homologue côté client. Pour préserver la cohérence et la prévisibilité du comportement des composants, ce niveau de contrôle est restreint.

2. **Prévention des modifications involontaires** : déclarer les classes de composants `final` évite les modifications involontaires des composants essentiels et réduit le risque d’introduire des comportements inattendus ou des vulnérabilités.

3. **Encouragement à utiliser des composites** : pour étendre les fonctionnalités des composants, le framework webforJ encourage les développeurs à adopter une approche composite. Les composants composites sont des classes Java qui contiennent d’autres composants webforJ ou des éléments HTML standard. Bien que l’héritage traditionnel soit déconseillé, les composants composites permettent de créer de nouveaux composants personnalisés qui encapsulent des composants existants.

<!-- ## Ajout de composants au DOM
TODO : expliquer comment webforJ détermine l’homologue côté client d’un composant -->

## Composants composites : extension par composition {#composite-components-extending-through-composition}
Dans le framework webforJ, les composants composites jouent un rôle essentiel dans l’extension des fonctionnalités des composants. Les composants composites sont des classes Java qui ne sont pas soumises au mot-clé `final`. Ils permettent aux développeurs de créer de nouveaux composants en étendant le comportement d’un seul composant ou en combinant plusieurs composants, par composition de composants existants. Des classes facilitant cette approche ont été créées pour les développeurs. Consultez les sections `Composite` et `ElementComposite` pour découvrir comment créer correctement des composants composites.

Cette approche encourage un style de développement plus modulaire et flexible, permettant aux développeurs de créer des composants sur mesure qui répondent à des besoins spécifiques.

<!--
## Hiérarchie des composants {#component-hierarchy}
TODO : créer et afficher un graphique -->
