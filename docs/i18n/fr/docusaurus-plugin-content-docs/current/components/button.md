---
title: Button
sidebar_position: 15
_i18n_hash: 6c3425f6d7138e710c5222d2baf84644
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` est un élément cliquable qui déclenche une action lorsqu'il est pressé. Il peut afficher du texte, des icônes, ou une combinaison des deux. Les boutons prennent en charge plusieurs thèmes visuels et tailles, et peuvent être désactivés pour empêcher l'interaction pendant des opérations longues ou lorsque certaines conditions ne sont pas remplies.

<!-- INTRO_END -->

## Usages {#usages}

La classe `Button` est un composant polyvalent couramment utilisé dans diverses situations où des interactions et des actions des utilisateurs doivent être déclenchées. Voici quelques scénarios typiques où vous pourriez avoir besoin d'un bouton dans votre application :

1. **Soumission de formulaire** : Les boutons sont souvent utilisés pour soumettre des données de formulaire. Par exemple, dans une application, vous pouvez utiliser :

  > - Un bouton "Soumettre" pour envoyer des données au serveur
  > - Un bouton "Effacer" pour supprimer toute information déjà présente dans le formulaire


2. **Actions utilisateur** : Les boutons sont utilisés pour permettre aux utilisateurs d'effectuer des actions spécifiques dans l'application. Par exemple, vous pouvez avoir un bouton étiqueté :

  > - "Supprimer" pour initier la suppression d'un élément sélectionné
  > - "Enregistrer" pour enregistrer les modifications apportées à un document ou une page.

3. **Dialogs de confirmation** : Les boutons sont souvent inclus dans les composants [`Dialog`](../components/dialog) construits pour diverses raisons afin de fournir des options à l'utilisateur pour confirmer ou annuler une action, ou toute autre fonctionnalité intégrée dans le [`Dialog`](../components/dialog) que vous utilisez.

4. **Déclencheurs d'interaction** : Les boutons peuvent servir de déclencheurs pour des interactions ou des événements dans l'application. En cliquant sur un bouton, les utilisateurs peuvent initier des actions complexes ou déclencher des animations, rafraîchir le contenu, ou mettre à jour l'affichage.

5. **Navigation** : Les boutons peuvent être utilisés à des fins de navigation, comme passer d'une section ou page à une autre au sein d'une application. Les boutons de navigation pourraient inclure :

  > - "Suivant" - Amène l'utilisateur à la page ou section suivante de l'application ou de la page actuelle.
  > - "Précédent" - Ramène l'utilisateur à la page précédente de l'application ou à la section où il se trouve.
  > - "Retour" - Ramène l'utilisateur à la première partie de l'application ou de la page où il se trouve.
  
L'exemple suivant démontre des boutons utilisés pour la soumission de formulaire et l'effacement des entrées :

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Ajout d'icônes aux boutons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporer une icône dans un bouton peut grandement améliorer le design de votre application, permettant aux utilisateurs d'identifier rapidement les éléments actionnables à l'écran. Le composant [`Icon`](./icon.md) offre une large sélection d'icônes parmi lesquelles choisir.

En utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`, vous avez la flexibilité de déterminer si une `Icon` doit apparaître avant ou après le texte sur un bouton. Alternativement, la méthode `setIcon()` peut être utilisée pour ajouter une `Icon` après le texte, mais avant l'emplacement `suffix` du bouton.

<!-- Ajoutez ceci une fois que l'icône a été fusionnée -->
<!-- Consultez la page du [composant Icon](../components/icon) pour plus d'informations sur la configuration et la personnalisation des icônes. -->

:::tip
Par défaut, une `Icon` hérite du thème et de l'expansion du bouton.
:::

Voici des exemples de boutons avec du texte à gauche et à droite, ainsi qu'un bouton avec uniquement une icône :

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Noms {#names}

Le composant `Button` utilise des noms, qui sont utilisés pour l'accessibilité. Lorsqu'un nom n'est pas explicitement défini, l'étiquette du `Button` sera utilisée à la place. Cependant, certaines icônes n'ont pas d'étiquettes, et n'affichent que des éléments non textuels, tels que des icônes. Dans ce cas, il est conseillé d'utiliser la méthode `setName()` pour garantir que le composant `Button` créé respecte les normes d'accessibilité.

## Désactivation d'un bouton {#disabling-a-button}

Les composants Button, comme beaucoup d'autres, peuvent être désactivés pour indiquer à l'utilisateur qu'une certaine action n'est pas encore ou n'est plus disponible. Un bouton désactivé diminuera l'opacité du bouton et est disponible pour tous les thèmes et expansions de bouton.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

La désactivation d'un bouton peut se faire à tout moment dans le code en utilisant la fonction <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Pour plus de commodité, un bouton peut également être désactivé lorsqu'il est cliqué en utilisant la fonction intégrée <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

Dans certaines applications, cliquer sur un bouton déclenche une action de longue durée. Dans la plupart des cas, l'application pourrait vouloir s'assurer qu'un seul clic soit traité. Cela peut poser problème dans des environnements à forte latence lorsque l'utilisateur clique sur le bouton plusieurs fois avant que l'application ait eu la chance de commencer à traiter l'action résultante. 

:::tip
Désactiver au clic aide non seulement à optimiser le traitement des actions, mais empêche également le développeur de devoir implémenter ce comportement par lui-même, car cette méthode est optimisée pour réduire les communications aller-retour.
:::

## Stylisation {#styling}

### Thèmes {#themes}

Les composants `Button` sont livrés avec <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 thèmes discrets </JavadocLink> intégrés pour un stylisme rapide sans l'utilisation de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des boutons dans toute l'application. 

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples d'utilisation :

  - **Danger** : Meilleur pour des actions ayant de graves conséquences, comme l'effacement d'informations remplies ou la suppression permanente d'un compte/données.
  - **Default** : Approprié pour des actions dans une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
  - **Primary** : Approprié comme un principal "appel à l'action" sur une page, comme s'inscrire, enregistrer des modifications, ou continuer vers une autre page.
  - **Success** : Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou la finalisation d'un processus d'inscription. Le thème de succès peut être appliqué par programmation une fois qu'une action réussie a été complétée.
  - **Warning** : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme quitter une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, comme des paramètres mineurs ou des actions plus complémentaires à une page, et qui ne font pas partie de la fonctionnalité principale.
  - **Info** : Bon pour fournir des informations complémentaires et clarificatrices à un utilisateur.

Voici des boutons d'exemple avec chacun des thèmes pris en charge : <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expansions {#expanses}
Les différentes valeurs <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses</JavadocLink> permettent un stylisme rapide sans utiliser CSS. Cela permet de manipuler les dimensions du bouton sans avoir à les définir explicitement en utilisant un style. En plus de simplifier le styling, cela aide également à créer et à maintenir une uniformité dans votre application. L'expansion par défaut du `Button` est `Expanse.MEDIUM`.

Différentes tailles sont souvent appropriées pour différents usages :
  - Les valeurs d'expansion **plus grandes** sont adaptées aux boutons qui devraient attirer l'attention, souligner la fonctionnalité ou être intégrales à la fonctionnalité principale d'une application ou d'une page.
  - Les boutons d'expansion **moyenne**, la taille par défaut, doivent être utilisés comme une "taille standard", lorsque le comportement d'un bouton n'est ni plus ni moins important que d'autres composants similaires.
  - Les valeurs d'expansion **plus petites** devraient être utilisées pour des boutons qui n'ont pas de comportements intégrants dans l'application, et qui jouent un rôle plus complémentaire ou utilitaire, plutôt que de jouer un rôle important dans l'interaction avec l'utilisateur. Cela inclut les composants `Button` utilisés uniquement avec des icônes à des fins utilitaires.

Voici les différentes expansions prises en charge par le composant `Button` : <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Button`, considérez les meilleures pratiques suivantes :

1. **Texte approprié** : Utilisez un texte clair et concis pour le texte de votre composant `Button` afin de fournir une indication claire de son objectif.

2. **Stylisation visuelle appropriée** : Considérez le style visuel et le thème du `Button` pour garantir la cohérence avec le design de votre application. Par exemple :
  > - Un composant `Button` "Annuler" devrait être stylisé avec le thème approprié ou un style CSS pour s'assurer que les utilisateurs sont sûrs qu'ils souhaitent annuler une action
  > - Un `Button` "Confirmer" aurait un style différent d'un bouton "Annuler", mais se démarquerait également pour s'assurer que les utilisateurs savent qu'il s'agit d'une action spéciale.

3. **Gestion efficace des événements** : Gérez les événements du `Button` de manière efficace et fournissez des retours appropriés aux utilisateurs. Consultez [Événements](../building-ui/events) pour examiner les comportements d'ajout d'événements efficaces.

4. **Tests et accessibilité** : Testez le comportement du bouton dans différents scénarios, tels que lorsqu'il est désactivé ou reçoit le focus, pour garantir une expérience utilisateur fluide. Suivez les lignes directrices en matière d'accessibilité pour rendre le `Button` utilisable par tous les utilisateurs, y compris ceux qui dépendent des technologies d'assistance.
