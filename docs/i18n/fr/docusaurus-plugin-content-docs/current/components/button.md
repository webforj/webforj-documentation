---
title: Button
sidebar_position: 15
_i18n_hash: 186724659f1f66cdb6f85e7a1628def8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un composant `Button` est un élément fondamental de l'interface utilisateur utilisé dans le développement d'applications pour créer des éléments interactifs qui déclenchent des actions ou des événements lorsqu'ils sont cliqués ou activés. Il sert d'élément cliquable avec lequel les utilisateurs peuvent interagir pour effectuer diverses actions au sein d'une application ou d'un site web.

L'objectif principal du composant `Button` est de fournir un appel à l'action clair et intuitif pour les utilisateurs, les guidant à effectuer des tâches spécifiques telles que soumettre un formulaire, naviguer vers une autre page, déclencher une fonction ou initier un processus. Les boutons sont essentiels pour améliorer les interactions des utilisateurs, améliorer l'accessibilité et créer une expérience utilisateur plus engageante.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Usages {#usages}

La classe `Button` est un composant polyvalent couramment utilisé dans diverses situations où les interactions et actions des utilisateurs doivent être déclenchées. Voici quelques scénarios typiques où vous pourriez avoir besoin d'un bouton dans votre application :

1. **Soumission de Formulaire** : Les boutons sont souvent utilisés pour soumettre des données de formulaire. Par exemple, dans une application, vous pouvez utiliser :

  > - Un bouton "Soumettre" pour envoyer des données au serveur
  > - Un bouton "Effacer" pour supprimer toute information déjà présente dans le formulaire


2. **Actions Utilisateur** : Les boutons sont utilisés pour permettre aux utilisateurs d'exécuter des actions spécifiques au sein de l'application. Par exemple, vous pouvez avoir un bouton étiqueté :

  > - "Supprimer" pour initier la suppression d'un élément sélectionné
  > - "Sauvegarder" pour enregistrer les modifications apportées à un document ou une page.

3. **Dialogues de Confirmation** : Les boutons sont souvent inclus dans des composants [`Dialog`](../components/dialog) construits à diverses fins pour fournir des options aux utilisateurs afin de confirmer ou d'annuler une action, ou toute autre fonctionnalité intégrée dans le [`Dialog`](../components/dialog) que vous utilisez.

4. **Déclencheurs d'Interaction** : Les boutons peuvent servir de déclencheurs pour des interactions ou des événements au sein de l'application. En cliquant sur un bouton, les utilisateurs peuvent initier des actions complexes, déclencher des animations, actualiser du contenu ou mettre à jour l'affichage.

5. **Navigation** : Les boutons peuvent être utilisés à des fins de navigation, comme passer d'une section ou d'une page à l'autre au sein d'une application. Les boutons de navigation pourraient inclure :

  > - "Suivant" - Emène l'utilisateur à la prochaine page ou section de l'application ou page en cours.
  > - "Précédent" - Ramène l'utilisateur à la page précédente de l'application ou à la section dans laquelle il se trouve.
  > - "Retour" - Ramène l'utilisateur à la première partie de l'application ou de la page dans laquelle il se trouve.

## Ajouter des icônes aux boutons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporer une icône dans un bouton peut grandement améliorer le design de votre application, permettant aux utilisateurs d'identifier rapidement les éléments actionnables à l'écran. Le composant [`Icon`](./icon.md) offre une large sélection d'icônes parmi lesquelles choisir.

En utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`, vous avez la flexibilité de déterminer si une `Icon` doit apparaître avant ou après le texte sur un bouton. Alternativement, la méthode `setIcon()` peut être utilisée pour ajouter une `Icon` après le texte, mais avant l'emplacement du `suffix` du bouton.

:::tip
Par défaut, une `Icon` hérite du thème et de l'expansion du bouton.
:::

Voici des exemples de boutons avec du texte à gauche et à droite, ainsi qu'un bouton avec seulement une icône :

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Noms {#names}

Le composant `Button` utilise des noms, qui sont utilisés pour l'accessibilité. Lorsqu'un nom n'est pas explicitement défini, l'étiquette du `Button` sera utilisée à la place. Cependant, certaines icônes n'ont pas d'étiquettes et affichent uniquement des éléments non textuels, comme des icônes. Dans ce cas, il est opportun d'utiliser la méthode `setName()` pour garantir que le composant `Button` créé respecte les normes d'accessibilité.

## Désactiver un bouton {#disabling-a-button}

Les composants de bouton, comme beaucoup d'autres, peuvent être désactivés pour indiquer à un utilisateur qu'une certaine action n'est pas encore ou n'est plus disponible. Un bouton désactivé diminuera l'opacité du bouton et est disponible pour tous les thèmes et expansions de bouton.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Désactiver un bouton peut être effectué à tout moment dans le code en utilisant la fonction <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Pour plus de commodité, un bouton peut également être désactivé lorsqu'il est cliqué en utilisant la fonction intégrée <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

Dans certaines applications, cliquer sur un bouton déclenche une action longue. Dans la plupart des cas, l'application pourrait vouloir s'assurer qu'un seul clic soit traité. Cela peut poser un problème dans des environnements à forte latence lorsque l'utilisateur clique plusieurs fois sur le bouton avant que l'application ait eu le temps de commencer à traiter l'action résultante.

:::tip
Désactiver sur clic aide non seulement à optimiser le traitement des actions, mais empêche également le développeur d'avoir à mettre en œuvre ce comportement par lui-même, car cette méthode a été optimisée pour réduire les communications à double sens.
:::

## Style {#styling}

### Thèmes {#themes}

Les composants `Button` sont livrés avec <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 thèmes discrets</JavadocLink> intégrés pour un style rapide sans l'utilisation de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent une manière rapide et cohérente de personnaliser l'apparence des boutons tout au long d'une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples d'utilisation :

  - **Danger** : Idéal pour les actions ayant de graves conséquences, comme effacer des informations remplies ou supprimer définitivement un compte/données.
  - **Default** : Approprié pour les actions au sein d'une application qui ne nécessitent pas une attention particulière et sont génériques, comme basculer un paramètre.
  - **Primary** : Approprié en tant que principale "action à réaliser" sur une page, comme s'inscrire, sauvegarder des modifications ou continuer vers une autre page.
  - **Success** : Excellent pour visualiser l'achèvement réussi d'un élément dans une application, tel que la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué de manière programmatique une fois qu'une action réussie a été effectuée.
  - **Warning** : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non sauvegardées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, telles que des réglages mineurs ou des actions qui sont plus complémentaires à une page et ne font pas partie de la fonctionnalité principale.
  - **Info** : Bon pour fournir des informations supplémentaires clarifiantes à un utilisateur.

Voici ci-dessous des boutons d'exemple avec chacun des thèmes pris en charge appliqués : <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expanses {#expanses}
Les valeurs <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses </JavadocLink> suivantes permettent un style rapide sans utiliser de CSS. Cela permet de manipuler les dimensions du bouton sans avoir à le définir explicitement à l'aide d'un style. En plus de simplifier le style, cela aide également à créer et à maintenir une uniformité dans votre application. L'expansion `Button` par défaut est `Expanse.MEDIUM`.

Différentes tailles sont souvent appropriées pour différents usages :
  - Des valeurs d'expansion **plus grandes** conviennent aux boutons qui doivent capter l'attention, souligner une fonctionnalité ou être intégrales à la fonctionnalité principale d'une application ou d'une page.
  - Les boutons d'expansion **moyenne**, la taille par défaut, devraient être utilisés comme une "taille standard", lorsque le comportement d'un bouton n'est ni plus ni moins important que celui d'autres composants similaires.
  - Des valeurs d'expansion **plus petites** doivent être utilisées pour les boutons qui n'ont pas de comportements intégrants dans l'application, et servent un rôle plus complémentaire ou utilitaire, plutôt que de jouer un rôle important dans l'interaction utilisateur. Cela inclut des composants `Button` utilisés uniquement avec des icônes à des fins utilitaires.

Voici ci-dessous les différentes expansions prises en charge pour le composant `Button` : <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Button`, considérez les meilleures pratiques suivantes :

1. **Texte Correct** : Utilisez un texte clair et concis pour le texte à l'intérieur de votre composant `Button` afin de fournir une indication claire de son objectif.

2. **Style Visuel Approprié** : Prenez en compte le style visuel et le thème du `Button` pour garantir la cohérence avec le design de votre application. Par exemple :
  > - Un composant `Button` "Annuler" devrait être stylisé avec le thème ou le style CSS approprié pour s'assurer que les utilisateurs soient sûrs de vouloir annuler une action
  > - Un `Button` "Confirmer" aurait un style différent de celui d'un bouton "Annuler", mais se démarquerait de manière similaire pour garantir que les utilisateurs sachent qu'il s'agit d'une action spéciale.

3. **Gestion Efficace des Événements** : Gérez les événements du `Button` efficacement et fournissez un retour approprié aux utilisateurs. Consultez [Événements](../building-ui/events) pour examiner les comportements d'ajout d'événements efficaces.

4. **Tests et Accessibilité** : Testez le comportement du bouton dans différents scénarios, comme lorsqu'il est désactivé ou reçoit le focus, pour garantir une expérience utilisateur fluide. Suivez les directives d'accessibilité pour rendre le `Button` utilisable par tous les utilisateurs, y compris ceux qui dépendent des technologies d'assistance.
