---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 7fd4306a016d3734d34336b8136c6e11
---
Le framework webforJ est conçu pour fournir une API Java autour du langage BBj DWC, offrant une architecture robuste pour la création et la gestion de composants.

## Mapping des contrôles BBj aux composants webforJ {#mapping-bbj-controls-to-webforj-components}
Un des principes fondamentaux de webforJ est le lien entre les contrôles BBj et les composants webforJ. Dans cette architecture, chaque composant webforJ fourni avec le produit a un mapping un à un avec un contrôle BBj sous-jacent. Ce mapping garantit que les composants Java reflètent le comportement et les propriétés de leurs homologues BBj de manière transparente.

Cette correspondance étroite entre les composants webforJ et les contrôles BBj simplifie le développement et permet aux développeurs Java de travailler avec des concepts familiers lors de la création d'applications web sans avoir besoin d'écrire du code BBj.

## La classe de base `DwcComponent` {#the-dwccomponent-base-class}
Au cœur de l'architecture des composants de webforJ se trouve la classe de base DWCComponent. Tous les composants webforJ héritent de cette classe. Cet héritage permet à chaque composant webforJ d'accéder à son contrôle BBj sous-jacent, fournissant un lien direct entre le composant Java et le contrôle BBj qu'il représente.

Cependant, il est important de noter que les développeurs ne sont pas autorisés à étendre la classe DWCComponent. Tenter de le faire entraînera une exception à l'exécution qui interdit de telles extensions. Cette restriction est en place pour maintenir l'intégrité du contrôle BBj sous-jacent et garantir que les développeurs ne le manipulent pas par inadvertance de manière à provoquer des conséquences indésirables.

### Classes finales et restrictions d'extension {#final-classes-and-extension-restrictions}
Dans webforJ, la plupart des classes de composants, à l'exception des éléments HTML intégrés et de toutes les classes qui les prolongent, sont déclarées comme `final`. Cela signifie qu'elles ne sont pas disponibles pour l'extension ou la sous-classification. Ce choix de conception est délibéré et sert plusieurs objectifs :

1. **Contrôle sur le contrôle BBj sous-jacent** : Comme mentionné précédemment, étendre les classes de composants webforJ donnerait aux développeurs le contrôle sur le contrôle BBj sous-jacent. Afin de maintenir la cohérence et la prévisibilité du comportement des composants, ce niveau de contrôle est restreint.

2. **Prévention des modifications non désirées** : Rendre les classes de composants `final` empêche les modifications involontaires des composants de base, réduisant le risque d'introduire des comportements inattendus ou des vulnérabilités.

3. **Promotion de l'utilisation des composites** : Pour étendre la fonctionnalité des composants, le framework webforJ encourage les développeurs à adopter une approche composite. Les composants composites sont des classes Java qui contiennent d'autres composants webforJ ou des éléments HTML standard. Bien que l'héritage traditionnel soit découragé, les composants composites offrent un moyen de créer de nouveaux composants personnalisés qui encapsulent ceux existants.

## Composants composites : étendre par la composition {#composite-components-extending-through-composition}
Dans le framework webforJ, le concept de composants composites joue un rôle essentiel dans l'extension des fonctionnalités des composants. Les composants composites sont des classes Java qui ne sont pas restreintes par le mot-clé final, permettant aux développeurs de créer de nouveaux composants qui étendent le comportement d'un seul composant ou combinent plusieurs composants en un, en composant des composants existants. Des classes qui facilitent ce comportement ont été créées pour l'utilisation des développeurs. Consultez les sections `Composite` et `ElementComposite` pour voir comment créer correctement des composants composites.

Cette approche encourage un style de développement plus modulaire et flexible, permettant aux développeurs de construire des composants sur mesure qui répondent à des exigences spécifiques.
