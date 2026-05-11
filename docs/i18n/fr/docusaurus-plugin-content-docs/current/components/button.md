---
title: Button
sidebar_position: 15
_i18n_hash: 5e0b4998a50b6c7d935c53c9c11009d6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` est un élément cliquable qui déclenche une action lorsqu'il est pressé. Il peut afficher du texte, des icônes, ou une combinaison des deux. Les boutons prennent en charge plusieurs thèmes visuels et tailles, et peuvent être désactivés pour empêcher l'interaction lors d'opérations prolongées ou lorsque certaines conditions ne sont pas remplies.

<!-- INTRO_END -->

## Usages {#usages}

La classe `Button` est un composant polyvalent couramment utilisé dans diverses situations où des interactions et des actions des utilisateurs doivent être déclenchées. Voici quelques scénarios typiques où vous pourriez avoir besoin d'un bouton dans votre application :

1. **Soumission de formulaire** : Les boutons sont souvent utilisés pour soumettre des données de formulaire. Par exemple, dans une application, vous pouvez utiliser :

  > - Un bouton "Soumettre" pour envoyer des données au serveur
  > - Un bouton "Effacer" pour supprimer toute information déjà présente dans le formulaire

2. **Actions des utilisateurs** : Les boutons sont utilisés pour permettre aux utilisateurs d'effectuer des actions spécifiques au sein de l'application. Par exemple, vous pouvez avoir un bouton étiqueté :

  > - "Supprimer" pour initier la suppression d'un élément sélectionné
  > - "Sauvegarder" pour sauvegarder les modifications apportées à un document ou à une page.

3. **Dialogues de confirmation** : Les boutons sont souvent inclus dans les composants [`Dialog`](../components/dialog) conçus à diverses fins pour offrir aux utilisateurs des options pour confirmer ou annuler une action, ou toute autre fonctionnalité intégrée au [`Dialog`](../components/dialog) que vous utilisez.

4. **Déclencheurs d'interaction** : Les boutons peuvent servir de déclencheurs pour des interactions ou des événements au sein de l'application. En cliquant sur un bouton, les utilisateurs peuvent initier des actions complexes ou déclencher des animations, rafraîchir le contenu, ou mettre à jour l'affichage.

5. **Navigation** : Les boutons peuvent être utilisés à des fins de navigation, comme se déplacer entre différentes sections ou pages au sein d'une application. Les boutons de navigation pourraient inclure :

  > - "Suivant" - Amène l'utilisateur à la page ou section suivante de l'application ou de la page actuelle.
  > - "Précédent" - Renvoie l'utilisateur à la page précédente de l'application ou à la section dans laquelle il se trouve.
  > - "Retour" - Renvoie l'utilisateur à la première partie de l'application ou de la page dans laquelle il se trouve.

L'exemple suivant montre des boutons utilisés pour la soumission de formulaires et le nettoyage des entrées :

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Ajouter des icônes aux boutons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporer une icône dans un bouton peut grandement améliorer le design de votre application, permettant aux utilisateurs d'identifier rapidement les éléments actionnables à l'écran. Le composant [`Icon`](./icon.md) offre un large choix d'icônes à choisir.

En utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`, vous avez la flexibilité de déterminer si une `Icon` doit apparaître avant ou après le texte sur un bouton. Alternativement, la méthode `setIcon()` peut être utilisée pour ajouter une `Icon` après le texte, mais avant le slot `suffix` du bouton.

<!-- Ajoutez ceci une fois que l'icône a été fusionnée -->
<!-- Consultez la page [Composant Icon](../components/icon) pour plus d'informations sur la configuration et la personnalisation des icônes. -->

:::tip
Par défaut, une `Icon` hérite du thème et de l'expanse du bouton.
:::

Voici des exemples de boutons avec du texte à gauche et à droite, ainsi qu'un bouton avec uniquement une icône :

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Noms {#names}

Le composant `Button` utilise un nom, qui est utilisé pour l'accessibilité. Lorsqu'un nom n'est pas explicitement défini, l'étiquette du `Button` sera utilisée à la place. Cependant, certaines icônes n'ont pas d'étiquettes et affichent uniquement des éléments non textuels, tels que des icônes. Dans ce cas, il est judicieux d'utiliser la méthode `setName()` pour garantir que le composant `Button` créé soit conforme aux normes d'accessibilité.

## Désactivation d'un bouton {#disabling-a-button}

Les composants de bouton, comme beaucoup d'autres, peuvent être désactivés pour indiquer à un utilisateur qu'une certaine action n'est pas encore ou n'est plus disponible. Un bouton désactivé diminuera l'opacité du bouton, et est disponible pour tous les thèmes et expanse de boutons.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

La désactivation d'un bouton peut se faire à tout moment dans le code en utilisant la fonction <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Pour plus de commodité, un bouton peut également être désactivé lorsqu'il est cliqué à l'aide de la fonction intégrée <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

Dans certains cas, cliquer sur un bouton déclenche une action de longue durée. Désactiver le bouton jusqu'à ce que votre application traite l'action empêche l'utilisateur de cliquer plusieurs fois sur le bouton, en particulier dans des environnements à forte latence.

:::tip
La désactivation au clic permet non seulement d'optimiser le traitement des actions, mais empêche également le développeur de devoir mettre en œuvre ce comportement lui-même, car cette méthode a été optimisée pour réduire les communications à distance.
:::

## Style {#styling}

### Thèmes {#themes}

Les composants `Button` sont fournis avec <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 thèmes discrets</JavadocLink> intégrés pour un style rapide sans utiliser de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent une manière rapide et cohérente de personnaliser l'apparence des boutons dans toute une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, quelques exemples d'utilisation sont :

  - **Danger** : Idéal pour les actions ayant de graves conséquences, telles que supprimer des informations remplies ou supprimer définitivement un compte/données.
  - **Default** : Approprié pour les actions dans une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
  - **Primary** : Approprié en tant qu'"appel à l'action" principal sur une page, tel que s'inscrire, sauvegarder des modifications ou continuer vers une autre page.
  - **Success** : Excellent pour visualiser l'achèvement avec succès d'un élément dans une application, comme la soumission d'un formulaire ou la finalisation d'un processus d'inscription. Le thème de succès peut être appliqué programmatique une fois qu'une action réussie a été complétée.
  - **Warning** : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, telles que des paramètres mineurs ou des actions qui sont plus additionnelles à une page, et ne font pas partie de la fonctionnalité principale.
  - **Info** : Bon pour fournir des informations supplémentaires claires à un utilisateur.

Voici ci-dessous des exemples de boutons avec chacun des thèmes supportés appliqués : <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
height='175px'
/>

### Expanses {#expanses}

Les valeurs d'<JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses</JavadocLink> suivantes permettent un style rapide sans utiliser de CSS. Cela permet de manipuler les dimensions du bouton sans avoir à les définir explicitement à l'aide de styles. En plus de simplifier le stylage, cela aide également à créer et à maintenir une uniformité dans votre application. L'expanse par défaut du `Button` est `Expanse.MEDIUM`.

Différentes tailles sont souvent appropriées pour différentes utilisations :
  - Des valeurs d'expanse **plus grandes** sont adaptées aux boutons qui doivent attirer l'attention, souligner la fonctionnalité ou sont indispensables à la fonctionnalité principale d'une application ou d'une page.
  - Les boutons d'expanse **moyenne**, la taille par défaut, devraient être la taille standard des boutons. Les fonctions de ces boutons ne devraient être ni plus ni moins critiques que celles des composants similaires.
  - Les valeurs d'expanse **plus petites** devraient être utilisées pour les boutons qui n'ont pas de comportements essentiels dans l'application et servent un rôle plus auxiliaire ou utilitaire, plutôt que de jouer un rôle important dans l'interaction utilisateur. Cela inclut les composants `Button` utilisés uniquement avec des icônes à des fins utilitaires.

Voici ci-dessous les différentes expanses supportées pour le composant `Button` : <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Button`, considérez les meilleures pratiques suivantes :

1. **Texte approprié** : Utilisez un texte clair et concis pour le texte dans votre composant `Button` afin de fournir une indication claire de son objectif.

2. **Style visuel approprié** : Considérez le style visuel et le thème du `Button` pour garantir la cohérence avec le design de votre application. Par exemple :
  > - Un composant `Button` "Annuler" devrait être stylisé avec le thème approprié ou le style CSS pour s'assurer que les utilisateurs sont certains de vouloir annuler une action.
  > - Un `Button` "Confirmer" aurait un style différent d'un bouton "Annuler", mais ressortirait de manière similaire pour garantir que les utilisateurs sachent qu'il s'agit d'une action spéciale.

3. **Gestion des événements efficace** : Gérez efficacement les événements `Button` et fournissez des retours appropriés aux utilisateurs. Consultez [Événements](../building-ui/events) pour examiner les comportements d'ajout d'événements efficaces.

4. **Tests et accessibilité** : Testez le comportement du bouton dans différents scénarios, comme lorsqu'il est désactivé ou reçoit le focus, pour garantir une expérience utilisateur fluide.
Suivez les directives d'accessibilité pour rendre le `Button` utilisable par tous les utilisateurs, y compris ceux qui dépendent des technologies d'assistance.
