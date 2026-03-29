---
title: Button
sidebar_position: 15
_i18n_hash: 7df385d72b74249e5689c31575568ae8
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-button" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

Un `Button` est un élément cliquable qui déclenche une action lorsqu'il est pressé. Il peut afficher du texte, des icônes ou une combinaison des deux. Les boutons prennent en charge plusieurs thèmes visuels et tailles, et peuvent être désactivés pour éviter une interaction pendant des opérations de longue durée ou lorsqucertaines conditions ne sont pas remplies.

<!-- INTRO_END -->

## Usages {#usages}

La classe `Button` est un composant polyvalent couramment utilisé dans diverses situations où des interactions utilisateur et des actions doivent être déclenchées. Voici quelques scénarios typiques où vous pourriez avoir besoin d'un bouton dans votre application :

1. **Soumission de Formulaire** : Les boutons sont souvent utilisés pour soumettre les données d'un formulaire. Par exemple, dans une application, vous pouvez utiliser :

  > - Un bouton "Soumettre" pour envoyer des données au serveur
  > - Un bouton "Effacer" pour supprimer toute information déjà présente dans le formulaire


2. **Actions Utilisateur** : Les boutons sont utilisés pour permettre aux utilisateurs d'effectuer des actions spécifiques au sein de l'application. Par exemple, vous pouvez avoir un bouton étiqueté :

  > - "Supprimer" pour initier la suppression d'un élément sélectionné
  > - "Enregistrer" pour sauvegarder les modifications apportées à un document ou une page.

3. **Dialogues de Confirmation** : Les boutons sont souvent inclus dans des composants [`Dialog`](../components/dialog) conçus pour divers objectifs afin de fournir des options aux utilisateurs pour confirmer ou annuler une action, ou toute autre fonctionnalité intégrée dans le [`Dialog`](../components/dialog) que vous utilisez.

4. **Déclencheurs d'Interaction** : Les boutons peuvent servir de déclencheurs pour les interactions ou les événements au sein de l'application. En cliquant sur un bouton, les utilisateurs peuvent initier des actions complexes ou déclencher des animations, rafraîchir du contenu ou mettre à jour l'affichage.

5. **Navigation** : Les boutons peuvent être utilisés à des fins de navigation, comme passer entre différentes sections ou pages au sein d'une application. Les boutons de navigation pourraient inclure :

  > - "Suivant" - Amène l'utilisateur à la page ou section suivante de l'application ou de la page actuelle.
  > - "Précédent" - Ramène l'utilisateur à la page précédente de l'application ou à la section dans laquelle il se trouve.
  > - "Retour" - Ramène l'utilisateur à la première partie de l'application ou de la page dans laquelle il se trouve.
  
L'exemple suivant illustre des boutons utilisés pour la soumission de formulaires et l'effacement d'entrées :

<ComponentDemo 
path='/webforj/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

## Ajout d'icônes aux boutons <DocChip chip='since' label='24.11' /> {#adding-icons-to-buttons-docchip-chipsince-label2411-}

Incorporer une icône dans un bouton peut considérablement améliorer le design de votre application, permettant aux utilisateurs d'identifier rapidement les éléments actionnables à l'écran. Le composant [`Icon`](./icon.md) offre un large choix d'icônes parmi lesquelles choisir.

En utilisant les méthodes `setPrefixComponent()` et `setSuffixComponent()`, vous avez la flexibilité de déterminer si une `Icon` doit apparaître avant ou après le texte sur un bouton. Alternativement, la méthode `setIcon()` peut être utilisée pour ajouter une `Icon` après le texte, mais avant le slot `suffix` du bouton.

<!-- Add this back in once Icon has been merged -->
<!-- Référez-vous à la page [composant Icon](../components/icon) pour plus d'informations sur la configuration et la personnalisation des icônes. -->

:::tip
Par défaut, une `Icon` hérite du thème et de l'expansion du bouton.
:::

Ci-dessous des exemples de boutons avec du texte à gauche et à droite, ainsi qu'un bouton avec uniquement une icône :

<ComponentDemo 
path='/webforj/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
height="200px"
/>

### Noms {#names}

Le composant `Button` utilise la nomination, qui est utilisée pour l'accessibilité. Lorsqu'un nom n'est pas explicitement défini, l'étiquette du `Button` sera utilisée à la place. Cependant, certaines icônes n'ont pas d'étiquettes et affichent uniquement des éléments non textuels, tels que des icônes. Dans ce cas, il est utile d'utiliser la méthode `setName()` pour s'assurer que le composant `Button` créé respecte les normes d'accessibilité.

## Désactivation d'un bouton {#disabling-a-button}

Les composants de bouton, comme beaucoup d'autres, peuvent être désactivés pour indiquer à un utilisateur qu'une certaine action n'est pas encore ou n'est plus disponible. Un bouton désactivé diminuera l'opacité du bouton et est disponible pour tous les thèmes et expansions de boutons.

<ComponentDemo 
path='/webforj/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
/>

Désactiver un bouton peut se faire à tout moment dans le code en utilisant la fonction <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink>. Pour plus de commodité, un bouton peut également être désactivé lorsqu'il est cliqué en utilisant la fonction intégrée <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink>.

Dans certains cas, cliquer sur un bouton déclenche une action de longue durée. Désactiver le bouton jusqu'à ce que votre application traite l'action empêche l'utilisateur de cliquer plusieurs fois sur le bouton, notamment dans des environnements à haute latence.

:::tip
Désactiver au clic aide non seulement à optimiser le traitement des actions, mais empêche également le développeur de devoir mettre en œuvre ce comportement par lui-même, car cette méthode a été optimisée pour réduire les communications aller-retour.
:::

## Styliser {#styling}

### Thèmes {#themes}

Les composants `Button` sont livrés avec <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 thèmes discrets</JavadocLink> intégrés pour un stylisme rapide sans l'utilisation de CSS. Ces thèmes sont des styles prédéfinis qui peuvent être appliqués aux boutons pour changer leur apparence et leur présentation visuelle. Ils offrent un moyen rapide et cohérent de personnaliser l'aspect des boutons dans toute une application. 

Bien qu'il existe de nombreux cas d'utilisation pour chacun des différents thèmes, voici quelques exemples :

  - **Danger** : Meilleure pour des actions avec des conséquences graves, comme effacer des informations remplies ou supprimer définitivement un compte/données.
  - **Default** : Approprié pour des actions au sein d'une application qui ne nécessitent pas d'attention particulière et sont génériques, comme basculer un paramètre.
  - **Primary** : Approprié en tant que "appel à l'action" principal sur une page, comme s'inscrire, enregistrer des modifications ou continuer vers une autre page.
  - **Success** : Excellent pour visualiser l'achèvement réussi d'un élément dans une application, comme la soumission d'un formulaire ou l'achèvement d'un processus d'inscription. Le thème de succès peut être appliqué par programmation une fois qu'une action réussie a été accomplie.
  - **Warning** : Utile pour indiquer qu'un utilisateur est sur le point d'effectuer une action potentiellement risquée, comme naviguer loin d'une page avec des modifications non enregistrées. Ces actions sont souvent moins impactantes que celles qui utiliseraient le thème Danger.
  - **Gray** : Bon pour des actions subtiles, comme des paramètres mineurs ou des actions qui sont plus complémentaires à une page, et non pas partie de la fonctionnalité principale.
  - **Info** : Bon pour fournir des informations supplémentaires et clarificatrices à un utilisateur.

Ci-dessous des exemples de boutons avec chacun des thèmes pris en charge appliqués : <br/>

<ComponentDemo 
path='/webforj/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
cssURL='/css/button/buttonThemes.css'
height='175px'
/>

### Expanses {#expanses}
Les valeurs <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses</JavadocLink> suivantes permettent un stylisme rapide sans utiliser CSS. Cela permet de manipuler les dimensions du bouton sans avoir à les définir explicitement en utilisant un style. En plus de simplifier le style, cela aide également à créer et à maintenir une uniformité dans votre application. L'expansion `Button` par défaut est `Expanse.MEDIUM`.

Différentes tailles sont souvent appropriées pour différents usages :
  - Des valeurs d'expansion **Plus Grandes** conviennent aux boutons qui doivent attirer l'attention, souligner la fonctionnalité ou être intégrales à la fonctionnalité de base d'une application ou d'une page.
  - Les boutons d'expansion **Moyenne**, la taille par défaut, devraient être la taille standard des boutons. Les fonctions de ces boutons ne devraient pas être plus ni moins critiques que celles des composants similaires.
  - Des valeurs d'expansion **Plus Petites** devraient être utilisées pour des boutons qui n'ont pas de comportements intégraux dans l'application, et qui remplissent un rôle plus complémentaire ou utilitaire, plutôt que de jouer un rôle important dans l'interaction utilisateur. Cela inclut des composants `Button` utilisés uniquement avec des icônes à des fins utilitaires.

Ci-dessous se trouvent les différentes expansions prises en charge pour le composant `Button` : <br/>

<ComponentDemo 
path='/webforj/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
height='200px'
/>

<TableBuilder name="Button" />

## Meilleures pratiques {#best-practices}

Pour assurer une expérience utilisateur optimale lors de l'utilisation du composant `Button`, considérez les meilleures pratiques suivantes :

1. **Texte Approprié** : Utilisez un texte clair et concis pour le texte intérieur de votre composant `Button` afin de fournir une indication claire de son objectif.

2. **Styling Visuel Approprié** : Considérez le style visuel et le thème du `Button` pour assurer la cohérence avec le design de votre application. Par exemple :
  > - Un composant `Button` "Annuler" devrait être stylé avec le thème ou le style CSS approprié pour s'assurer que les utilisateurs sont sûrs de vouloir annuler une action.
  > - Un `Button` "Confirmer" aurait un style différent d'un bouton "Annuler", mais se démarquerait également pour s'assurer que les utilisateurs savent qu'il s'agit d'une action spéciale.

3. **Gestion Efficace des Événements** : Gérez efficacement les événements du `Button` et fournissez un retour d'information approprié aux utilisateurs. Référez-vous à [Événements](../building-ui/events) pour passer en revue des comportements d'ajout d'événements efficaces.

4. **Tests et Accessibilité** : Testez le comportement du bouton dans différents scénarios, comme lorsqu'il est désactivé ou reçoit du focus, pour garantir une expérience utilisateur fluide. Suivez les directives d'accessibilité pour rendre le `Button` utilisable pour tous les utilisateurs, y compris ceux qui s'appuient sur des technologies d'assistance.
