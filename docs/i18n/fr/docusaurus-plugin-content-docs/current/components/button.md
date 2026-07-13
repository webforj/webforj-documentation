---
title: Button
sidebar_position: 15
description: >-
  Trigger click actions in webforJ with the Button component, including themes,
  expanses, prefix and suffix icons, and disabled state.
_i18n_hash: 31fa93b60126cba6b26198da5a5c15b5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` est un élément cliquable qui déclenche une action lorsqu'il est pressé. Il peut afficher du texte, des icônes, ou une combinaison des deux. Les boutons supportent plusieurs thèmes visuels et tailles, et peuvent être désactivés pour empêcher l'interaction pendant les opérations longues ou lorsque certaines conditions ne sont pas remplies.

<!-- INTRO_END -->

## Usages {#usages}

La classe `Button` est un composant polyvalent couramment utilisé dans diverses situations où des interactions et actions des utilisateurs doivent être déclenchées. Voici quelques scénarios typiques où vous pourriez avoir besoin d'un bouton dans votre application :

1. **Soumission de Formulaire** : Les boutons sont souvent utilisés pour soumettre des données de formulaire. Par exemple, dans une application, vous pouvez utiliser :

  > - Un bouton "Soumettre" pour envoyer des données au serveur
  > - Un bouton "Effacer" pour retirer toute information déjà présente dans le formulaire


2. **Actions Utilisateur** : Les boutons sont utilisés pour permettre aux utilisateurs d'effectuer des actions spécifiques au sein de l'application. Par exemple, vous pouvez avoir un bouton étiqueté :

  > - "Supprimer" pour initier la suppression d'un élément sélectionné
  > - "Enregistrer" pour sauvegarder les modifications apportées à un document ou une page.

3. **Dialogues de Confirmation** : Les boutons sont souvent inclus dans des composants [`Dialog`](../components/dialog) conçus pour divers usages afin de fournir des options aux utilisateurs pour confirmer ou annuler une action, ou toute autre fonctionnalité intégrée au [`Dialog`](../components/dialog) que vous utilisez.

4. **Déclencheurs d'Interaction** : Les boutons peuvent servir de déclencheurs pour des interactions ou des événements au sein de l'application. En cliquant sur un bouton, les utilisateurs peuvent initier des actions complexes ou déclencher des animations, actualiser du contenu ou mettre à jour l'affichage.

5. **Navigation** : Les boutons peuvent être utilisés à des fins de navigation, comme passer d'une section ou page à une autre au sein d'une application. Les boutons de navigation pourraient inclure :

  > - "Suivant" - Amène l'utilisateur à la page ou section suivante de l'application ou de la page actuelle.
  > - "Précédent" - Retourne l'utilisateur à la page précédente de l'application ou section dans laquelle il se trouve.
  > - "Retour" - Ramène l'utilisateur à la première partie de l'application ou de la page dans laquelle il se trouve.

L'exemple suivant démontre des boutons utilisés pour la soumission de formulaire et l'effacement des saisies :

<ComponentDemo
path='/webforj/button'
files={['src/main/java/com/webforj/samples/views/button/ButtonView.java']}
height='300px'
/>

## Ajout d'icônes aux boutons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporer une icône dans un bouton peut grandement améliorer le design de votre application, permettant aux utilisateurs d'identifier rapidement les éléments actionnables à l'écran. Le composant [`Icon`](./icon.md) fournit une large sélection d'icônes parmi lesquelles choisir.

En utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`, vous avez la flexibilité de déterminer si une `Icon` doit apparaître avant ou après le texte sur un bouton. Alternativement, la méthode `setIcon()` peut être utilisée pour ajouter une `Icon` après le texte, mais avant l'emplacement `suffix` du bouton.

<!-- Ajoutez cela une fois que l'icône a été fusionnée -->
<!-- Consultez la page [composant Icon](../components/icon) pour plus d'informations sur la configuration et la personnalisation des icônes. -->

:::tip
Par défaut, une `Icon` hérite du thème et de l'expansion du bouton.
:::

Voici des exemples de boutons avec du texte à gauche et à droite, ainsi qu'un bouton avec uniquement une icône :

<ComponentDemo
path='/webforj/buttonicon'
files={['src/main/java/com/webforj/samples/views/button/ButtonIconView.java']}
height='200px'
/>

### Noms {#names}

Le composant `Button` utilise des noms, qui sont utilisés pour l'accessibilité. Lorsqu'un nom n'est pas explicitement défini, l'étiquette du `Button` sera utilisée à la place. Cependant, certaines icônes n'ont pas d'étiquettes et n'affichent que des éléments non textuels, tels que des icônes. Dans ce cas, il est judicieux d'utiliser la méthode `setName()` pour garantir que le composant `Button` créé se conforme aux normes d'accessibilité.

## Désactiver un bouton {#disabling-a-button}

Les composants de bouton, comme beaucoup d'autres, peuvent être désactivés pour indiquer à un utilisateur qu'une certaine action n'est pas encore ou n'est plus disponible. Un bouton désactivé diminuera l'opacité du bouton, et est disponible pour tous les thèmes et expansions de boutons.

<ComponentDemo
path='/webforj/buttondisable'
files={['src/main/java/com/webforj/samples/views/button/ButtonDisableView.java']}
/>

Désactiver un bouton peut se faire à tout moment dans le code en utilisant la fonction <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Pour plus de commodité, un bouton peut également être désactivé lorsque l'utilisateur clique dessus en utilisant la fonction intégrée <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

Dans certains cas, cliquer sur un bouton déclenche une action longue. Désactiver le bouton tant que votre application traite l'action empêche l'utilisateur de cliquer plusieurs fois sur le bouton, en particulier dans des environnements à latence élevée.

:::tip
Désactiver au clic non seulement aide à optimiser le traitement des actions, mais empêche également le développeur de devoir implémenter ce comportement par lui-même, car cette méthode a été optimisée pour réduire les communications de retour.
:::

## Style {#styling}

### Thèmes {#themes}

Les composants `Button` sont fournis avec <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 thèmes discrets </JavadocLink> intégrés pour un stylage rapide sans utilisation de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour modifier leur apparence et leur présentation visuelle. Ils offrent une manière rapide et cohérente de personnaliser l'aspect des boutons dans toute une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples d'utilisation :

  - **Danger** : Idéal pour les actions ayant des conséquences graves, comme effacer des informations remplies ou supprimer définitivement un compte/données.
  - **Default** : Approprié pour les actions dans toute une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
  - **Primary** : Approprié en tant qu'"appel à l'action" principal sur une page, comme s'inscrire, enregistrer des modifications, ou continuer vers une autre page.
  - **Success** : Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou la finalisation d'un processus d'inscription. Le thème de succès peut être appliqué de manière programmatique une fois qu'une action réussie a été complétée.
  - **Warning** : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, comme des réglages mineurs ou des actions qui sont plus complémentaires à une page et non pas partie intégrante de la fonctionnalité principale.
  - **Info** : Bon pour fournir des informations supplémentaires aux utilisateurs.

Voici ci-dessous des exemples de boutons avec chacun des thèmes pris en charge appliqués : <br/>

<ComponentDemo
path='/webforj/buttonthemes'
files={['src/main/java/com/webforj/samples/views/button/ButtonThemesView.java']}
height='175px'
/>

### Expanses {#expanses}
Les valeurs <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses </JavadocLink> suivantes permettent un stylage rapide sans utiliser de CSS. Cela permet de manipuler les dimensions du bouton sans avoir à les définir explicitement à l'aide de styles. En plus de simplifier le stylage, cela aide également à créer et maintenir une uniformité dans votre application. L'expansion par défaut du `Button` est `Expanse.MEDIUM`.

Différentes tailles sont souvent appropriées pour différentes utilisations :
  - Des valeurs d'expansion **plus grandes** conviennent aux boutons qui devraient attirer l'attention, souligner la fonctionnalité ou être intégrales à la fonctionnalité principale d'une application ou d'une page.
  - Les boutons de taille **moyenne**, la taille par défaut, devraient être la taille standard des boutons. Les fonctions de ces boutons ne devraient être ni plus ni moins critiques que celles des composants similaires.
  - Les valeurs d'expansion **plus petites** devraient être utilisées pour des boutons qui n'ont pas de comportements intégrals dans l'application, et qui jouent un rôle plus complémentaire ou utilitaire, plutôt que de jouer un rôle important dans l'interaction utilisateur. Cela inclut les composants `Button` utilisés uniquement avec des icônes à des fins utilitaires.

Voici les différentes expanses prises en charge pour le composant `Button` : <br/>

<ComponentDemo
path='/webforj/buttonexpanses'
files={['src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java']}
height='200px'
/>

<TableBuilder name="Button" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Button`, considérez les meilleures pratiques suivantes :

1. **Texte Approprié** : Utilisez un texte clair et concis pour le texte au sein de votre composant `Button` afin de fournir une indication claire de son but.

2. **Style Visuel Approprié** : Tenez compte du style visuel et du thème du `Button` pour assurer la cohérence avec le design de votre application. Par exemple :
  > - Un composant `Button` "Annuler" devrait être stylisé avec le thème approprié ou le style CSS pour s'assurer que les utilisateurs sont sûrs de vouloir annuler une action
  > - Un `Button` "Confirmer" aurait un style différent d'un bouton "Annuler", mais se démarquerait de manière similaire pour s'assurer que les utilisateurs savent qu'il s'agit d'une action spéciale.

3. **Gestion des Événements Efficace** : Gérez les événements des `Button` de manière efficace et fournissez un retour d'information approprié aux utilisateurs. Consultez [Événements](../building-ui/events) pour examiner les comportements d'ajout d'événements efficaces.

4. **Tests et Accessibilité** : Testez le comportement du bouton dans différents scénarios, comme lorsqu'il est désactivé ou reçoit le focus, pour garantir une expérience utilisateur fluide.
Suivez les directives d'accessibilité pour rendre le `Button` utilisable par tous les utilisateurs, y compris ceux qui dépendent de technologies d'assistance.
