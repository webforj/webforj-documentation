---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 929625ea8b8335de7326ecb067dca773
---
Le framework webforJ est conçu pour fournir une API Java autour du langage BBj DWC, offrant une architecture robuste pour la construction et la gestion des composants.

## Mapping des contrôles BBj aux composants webforJ {#mapping-bbj-controls-to-webforj-components}
Un des principes fondamentaux de webforJ est le lien entre les contrôles BBj et les composants webforJ. Dans cette architecture, chaque composant webforJ fourni avec le produit a un mappage un à un avec un contrôle BBj sous-jacent. Ce mappage garantit que les composants Java reflètent le comportement et les propriétés de leurs homologues BBj sans couture.

Cette correspondance étroite entre les composants webforJ et les contrôles BBj simplifie le développement et permet aux développeurs Java de travailler avec des concepts familiers lors de la création d'applications web sans avoir à écrire de code BBj.

## La classe de base `DwcComponent` {#the-dwccomponent-base-class}
Au cœur de l'architecture des composants de webforJ se trouve la classe de base DWCComponent. Tous les composants webforJ héritent de cette classe. Cet héritage accorde à chaque composant webforJ l'accès à son contrôle BBj sous-jacent, fournissant un lien direct entre le composant Java et le contrôle BBj qu'il représente.

Cependant, il est important de noter que les développeurs ne peuvent pas étendre la classe DWCComponent. Tenter de le faire entraînera une exception d'exécution qui interdit de telles extensions. Cette restriction est en place pour maintenir l'intégrité du contrôle BBj sous-jacent et veiller à ce que les développeurs ne le manipulent pas de manière imprévue pouvant entraîner des conséquences indésirables.

### Classes finales et restrictions d'extension {#final-classes-and-extension-restrictions}
Dans webforJ, la plupart des classes de composants, à l'exception des éléments HTML intégrés et de toutes classes les étendant, sont déclarées comme `final`. Cela signifie qu'elles ne sont pas disponibles pour l'extension ou la sous-classe. Ce choix de conception est délibéré et sert plusieurs objectifs :

1. **Contrôle sur le contrôle BBj sous-jacent** : Comme mentionné précédemment, étendre les classes de composants webforJ donnerait aux développeurs un contrôle sur le contrôle BBj sous-jacent. Pour maintenir la cohérence et la prévisibilité du comportement des composants, ce niveau de contrôle est restreint.

2. **Prévention des modifications involontaires** : Rendre les classes de composants `final` empêche les modifications non intentionnelles des composants principaux, réduisant le risque d'introduction de comportements inattendus ou de vulnérabilités.

3. **Promotion de l'utilisation des composites** : Pour étendre la fonctionnalité des composants, le framework webforJ encourage les développeurs à adopter une approche composite. Les composants composites sont des classes Java qui contiennent d'autres composants webforJ ou des éléments HTML standard. Bien que l'héritage traditionnel soit découragé, les composants composites offrent un moyen de créer de nouveaux composants personnalisés qui encapsulent des éléments existants.

## Composants composites : étendre par la composition {#composite-components-extending-through-composition}
Dans le framework webforJ, le concept de composants composites joue un rôle essentiel dans l'extension de la fonctionnalité des composants. Les composants composites sont des classes Java qui ne sont pas restreintes par le mot-clé final, permettant aux développeurs de créer de nouveaux composants qui étendent le comportement d'un composant unique ou combinent plusieurs composants en un, en composant des composants existants. Des classes facilitant ce comportement ont été créées pour l'utilisation des développeurs. Consultez les sections `Composite` et `ElementComposite` pour voir comment créer correctement des composants composites.

Cette approche encourage un style de développement plus modulaire et flexible, permettant aux développeurs de construire des composants sur mesure qui répondent à des exigences spécifiques.
