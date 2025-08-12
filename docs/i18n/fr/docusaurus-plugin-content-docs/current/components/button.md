---
title: Button
sidebar_position: 15
_i18n_hash: 0282098a1b80b4d494409d4f416caa5d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un composant `Button` est un élément fondamental de l'interface utilisateur utilisé dans le développement d'applications pour créer des éléments interactifs qui déclenchent des actions ou des événements lorsqu'ils sont cliqués ou activés. Il sert d'élément cliquable avec lequel les utilisateurs peuvent interagir pour effectuer diverses actions au sein d'une application ou d'un site Web.

Le but principal du composant `Button` est de fournir un appel à l'action clair et intuitif pour les utilisateurs, les guidant à accomplir des tâches spécifiques telles que soumettre un formulaire, naviguer vers une autre page, déclencher une fonction ou initier un processus. Les boutons sont essentiels pour améliorer les interactions des utilisateurs, améliorer l'accessibilité et créer une expérience utilisateur plus engageante.

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Usages {#usages}

La classe `Button` est un composant polyvalent qui est couramment utilisé dans diverses situations où des interactions et des actions des utilisateurs doivent être déclenchées. Voici quelques scénarios typiques où vous pourriez avoir besoin d'un bouton dans votre application :

1. **Soumission de formulaire** : Les boutons sont souvent utilisés pour soumettre des données de formulaire. Par exemple, dans une application, vous pouvez utiliser :

  > - Un bouton "Soumettre" pour envoyer des données au serveur
  > - Un bouton "Effacer" pour supprimer toute information déjà présente dans le formulaire

2. **Actions des utilisateurs** : Les boutons sont utilisés pour permettre aux utilisateurs d'effectuer des actions spécifiques au sein de l'application. Par exemple, vous pouvez avoir un bouton étiqueté :

  > - "Supprimer" pour initier la suppression d'un élément sélectionné
  > - "Enregistrer" pour enregistrer les modifications apportées à un document ou à une page.

3. **Dialogues de confirmation** : Les boutons sont souvent inclus dans des composants [`Dialog`](../components/dialog) construits pour diverses fins afin de fournir des options aux utilisateurs pour confirmer ou annuler une action, ou toute autre fonctionnalité intégrée dans le [`Dialog`](../components/dialog) que vous utilisez.

4. **Déclencheurs d'interaction** : Les boutons peuvent servir de déclencheurs pour des interactions ou des événements au sein de l'application. En cliquant sur un bouton, les utilisateurs peuvent initier des actions complexes ou déclencher des animations, actualiser le contenu ou mettre à jour l'affichage.

5. **Navigation** : Les boutons peuvent être utilisés à des fins de navigation, telles que le passage entre différentes sections ou pages au sein d'une application. Les boutons de navigation pourraient inclure :

  > - "Suivant" - Amène l'utilisateur à la prochaine page ou section de l'application ou de la page actuelle.
  > - "Précédent" - Ramène l'utilisateur à la page précédente de l'application ou à la section dans laquelle il se trouve.
  > - "Retour" Ramène l'utilisateur à la première partie de l'application ou de la page dans laquelle il se trouve.

## Ajout d'icônes aux boutons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporer une icône dans un bouton peut grandement améliorer le design de votre application, permettant aux utilisateurs d'identifier rapidement les éléments exploitables à l'écran. Le composant [`Icon`](./icon.md) fournit une large sélection d'icônes parmi lesquelles choisir.

En utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`, vous avez la flexibilité de déterminer si une `Icon` doit apparaître avant ou après le texte sur un bouton. Alternativement, la méthode `setIcon()` peut être utilisée pour ajouter une `Icon` après le texte, mais avant l'emplacement `suffix` du bouton.

<!-- Add this back in once Icon has been merged -->
<!-- Consultez la page sur le [composant Icon](../components/icon) pour plus d'informations sur la configuration et la personnalisation des icônes. -->

:::tip
Par défaut, une `Icon` hérite du thème et de l'expansion du bouton.
:::

Ci-dessous, vous trouverez des exemples de boutons avec du texte à gauche et à droite, ainsi qu'un bouton avec uniquement une icône :

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Noms {#names}

Le composant `Button` utilise la nomination, qui est utilisée pour l'accessibilité. Lorsqu'un nom n'est pas explicitement défini, l'étiquette du `Button` sera utilisée à la place. Cependant, certaines icônes n'ont pas d'étiquettes et affichent uniquement des éléments non textuels, comme des icônes. Dans ce cas, il est judicieux d'utiliser la méthode `setName()` pour s'assurer que le composant `Button` créé respecte les normes d'accessibilité.

## Désactiver un bouton {#disabling-a-button}

Les composants de bouton, comme beaucoup d'autres, peuvent être désactivés pour indiquer à un utilisateur qu'une certaine action n'est pas encore ou n'est plus disponible. Un bouton désactivé diminuera l'opacité du bouton et est disponible pour tous les thèmes et expansions de boutons.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

<br />

Désactiver un bouton peut être fait à tout moment dans le code en utilisant la fonction <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Pour plus de commodité, un bouton peut également être désactivé lorsqu'il est cliqué en utilisant la fonction intégrée <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

Dans certaines applications, cliquer sur un bouton déclenche une action de longue durée. Dans la plupart des cas, l'application pourrait vouloir s'assurer qu'un seul clic est traité. Cela peut être un problème dans des environnements à forte latence lorsque l'utilisateur clique plusieurs fois sur le bouton avant que l'application n'ait eu la chance de commencer à traiter l'action résultante.

:::tip
Désactiver au clic aide non seulement à optimiser le traitement des actions, mais empêche également le développeur de devoir mettre en œuvre ce comportement par ses propres moyens, car cette méthode a été optimisée pour réduire les communications de retour.
:::

## Style {#styling}

### Thèmes {#themes}

Les composants `Button` sont livrés avec <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 thèmes discrets</JavadocLink> intégrés pour un style rapide sans l'utilisation de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'apparence des boutons tout au long d'une application.

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples d'utilisations :

  - **Danger** : Idéal pour des actions ayant de graves conséquences, comme effacer des informations remplies ou supprimer définitivement un compte/données.
  - **Default** : Approprié pour des actions dans une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
  - **Primary** : Approprié comme principal "appel à l'action" sur une page, comme s'inscrire, enregistrer des modifications ou continuer vers une autre page.
  - **Success** : Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué de manière programmatique une fois qu'une action réussie a été complétée.
  - **Warning** : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, comme des paramètres mineurs ou des actions qui sont plus complémentaires à une page et ne font pas partie de la fonctionnalité principale.
  - **Info** : Bon pour fournir des informations supplémentaires à un utilisateur.

Ci-dessous, des exemples de boutons avec chacun des thèmes pris en charge appliqués : <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expanses {#expanses}
Les valeurs <JavadocLink type="foundation" location="com/webforj/component/Expanse">Expanses</JavadocLink> suivantes permettent un style rapide sans utiliser de CSS. Cela permet de manipuler les dimensions du Button sans avoir à les définir explicitement à l'aide de styles. En plus de simplifier le style, cela aide également à créer et à maintenir une uniformité dans votre application. L'expansion par défaut du `Button` est `Expanse.MEDIUM`.

Différentes tailles sont souvent appropriées pour différentes utilisations :
  - Les valeurs d'expansion **plus grandes** conviennent aux boutons qui doivent attirer l'attention, mettre en évidence la fonctionnalité ou être intégrales à la fonctionnalité de base d'une application ou d'une page.
  - Les boutons de taille **moyenne**, la taille par défaut, doivent être utilisés comme une "taille standard", lorsque le comportement d'un bouton n'est ni plus ni moins important que d'autres composants similaires.
  - Les valeurs d'expansion **plus petites** doivent être utilisées pour les boutons qui n'ont pas de comportements intégrés dans l'application et jouent un rôle plus complémentaire ou utilitaire, plutôt que de faire partie d'une interaction utilisateur importante. Cela inclut les composants `Button` utilisés uniquement avec des icônes à des fins utilitaires.

Voici les différentes expansions prises en charge pour le composant `Button` : <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `Button`, considérez les meilleures pratiques suivantes :

1. **Texte approprié** : Utilisez un texte clair et concis pour le texte au sein de votre composant `Button` afin de fournir une indication claire de son objectif.

2. **Style visuel approprié** : Considérez le style visuel et le thème du `Button` pour garantir une cohérence avec le design de votre application. Par exemple :
  > - Un composant `Button` "Annuler" doit être stylé avec le thème approprié ou le style CSS afin de s'assurer que les utilisateurs sont sûrs qu'ils souhaitent annuler une action
  > - Un `Button` "Confirmer" aurait un style différent de celui d'un bouton "Annuler", mais se démarquerait de la même manière pour aider les utilisateurs à comprendre qu'il s'agit d'une action spéciale.

3. **Gestion efficace des événements** : Gérez efficacement les événements `Button` et fournissez un retour approprié aux utilisateurs. Consultez [Événements](../building-ui/events) pour examiner les comportements d'ajout d'événements efficaces.

4. **Test et accessibilité** : Testez le comportement du bouton dans différents scénarios, tels que lorsqu'il est désactivé ou reçoit le focus, pour garantir une expérience utilisateur fluide. Suivez les directives d'accessibilité pour rendre le `Button` utilisable par tous les utilisateurs, y compris ceux qui dépendent des technologies d'assistance.
