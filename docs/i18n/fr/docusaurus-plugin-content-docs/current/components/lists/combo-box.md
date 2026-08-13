---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 9e5c0f54f07f604ee91a84210189ca30
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Le composant `ComboBox` combine une liste déroulante avec un champ de saisie texte, permettant aux utilisateurs de sélectionner parmi des options prédéfinies ou de taper une valeur personnalisée. Lorsqu'il est nécessaire de permettre des entrées personnalisées en plus d'un ensemble d'options suggérées, il remplit le vide que `ChoiceBox` ne couvre pas.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Le composant ComboBox est un élément d'entrée polyvalent qui combine les fonctionnalités d'une liste déroulante et d'un champ de saisie texte. Il permet aux utilisateurs de sélectionner des éléments dans une liste prédéfinie ou d'entrer des valeurs personnalisées si nécessaire. Cette section explore les usages courants du composant ComboBox dans divers scénarios :

1. **Recherche et saisie de produit** : Utilisez un ComboBox pour implémenter une fonctionnalité de recherche et de saisie de produit. Les utilisateurs peuvent soit sélectionner un produit dans une liste prédéfinie, soit taper un nom de produit personnalisé. Cela est utile pour des applications comme les sites de commerce électronique où les produits sont vastes et diversifiés.

2. **Sélection et saisie de tags** : Dans les applications impliquant le tagging de contenu, un ComboBox peut être un excellent choix. Les utilisateurs peuvent sélectionner parmi une liste de tags existants ou ajouter des tags personnalisés en les tapant. Cela est utile pour organiser et catégoriser le contenu. Des exemples de tels tags incluent :
    >- Étiquettes de projet : Dans un outil de gestion de projet, les utilisateurs peuvent sélectionner des étiquettes ou des tags (par exemple, "Urgent", "En cours", "Terminée") pour catégoriser des tâches ou des projets, et ils peuvent créer des étiquettes personnalisées si nécessaire.
    >- Ingrédients de recette : Dans une application de cuisine ou de recettes, les utilisateurs peuvent sélectionner des ingrédients dans une liste (par exemple, "Tomates", "Oignons", "Poulet") ou ajouter leurs propres ingrédients pour des recettes personnalisées.
    >- Étiquettes de localisation : Dans une application de cartographie ou de géotagging, les utilisateurs peuvent choisir des étiquettes de localisation prédéfinies (par exemple, "Plage", "Ville", "Parc") ou créer des tags personnalisés pour marquer des endroits spécifiques sur une carte.

3. **Saisie de données avec valeurs suggérées** : Dans les formulaires de saisie de données, un ComboBox peut être utilisé pour accélérer la saisie en fournissant une liste de valeurs suggérées en fonction de l'entrée de l'utilisateur. Cela aide les utilisateurs à entrer des données de manière précise et efficace.

    :::tip
    Le `ComboBox` doit être utilisé lorsque les utilisateurs sont autorisés à saisir des valeurs personnalisées. Si seules des valeurs prédéfinies sont désirées, utilisez un [`ChoiceBox`](./choice-box.md) à la place.
    :::

## Valeur personnalisée {#custom-value}

Changer la propriété de valeur personnalisée permet de contrôler si un utilisateur peut ou non changer la valeur dans le champ d'entrée du composant `ComboBox`. Si `true`, ce qui est par défaut, alors un utilisateur peut changer la valeur. Si défini sur `false`, l'utilisateur ne pourra pas changer la valeur. Cela peut être défini à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Espace réservé {#placeholder}

Un espace réservé peut être défini pour un `ComboBox`, qui s'affichera dans le champ de texte du composant lorsqu'il est vide pour inviter les utilisateurs à entrer la valeur souhaitée dans le champ. Cela peut être fait en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Type de liste déroulante {#dropdown-type}

L'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> assignera une valeur à l'attribut `type` d'un `ComboBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans le menu déroulant du `ComboBox`. Cela est utile pour le stylage, car le menu déroulant est retiré de sa position actuelle dans le DOM et relocalisé à la fin du corps de la page lorsqu'il est ouvert.

Ce détachement crée une situation où le ciblage direct du menu déroulant à l'aide de CSS ou des sélecteurs de partie d'ombre depuis le composant parent devient difficile, à moins de tirer parti de l'attribut de type de menu déroulant.

Dans la démo ci-dessous, le type de liste déroulante est défini et utilisé dans le fichier CSS pour agrandir une option lorsque vous la survolez.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Nombre maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le menu déroulant d'un `ComboBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler combien d'éléments sont affichés.

:::caution
Utiliser un nombre qui est inférieur ou égal à 0 aura pour résultat de désactiver cette propriété.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ComboBox` peut être contrôlée programmaticalement avec les méthodes `open()` et `close()`. Ces méthodes permettent d'afficher la liste des options à sélectionner ou de la masquer si nécessaire, offrant une plus grande flexibilité dans la gestion du comportement d'un `ComboBox`.

De plus, webforJ propose des écouteurs d'événements pour quand le `ComboBox` est fermé et quand il est ouvert, vous offrant plus de contrôle pour déclencher des actions spécifiques.

```Java
// Se concentrer ou ouvrir le prochain composant dans un formulaire
ComboBox university = new ComboBox("Université");
ComboBox major = new ComboBox("Majeure");
Button submit = new Button("Soumettre");

//... Ajouter des listes d'universités et de majeures

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ComboBox` possède des méthodes qui permettent de manipuler les dimensions du menu déroulant. La **hauteur maximale** et la **largeur minimale** du menu déroulant peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Passer une valeur `String` à l'une ou l'autre de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), comme des pixels, des dimensions de viewport ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Surlignage {#highlighting}

Lors de l'utilisation d'un `ComboBox`, vous pouvez personnaliser quand le texte est surligné en fonction de la manière dont le composant obtient le focus. Cette fonctionnalité peut réduire les erreurs de saisie lorsque les utilisateurs remplissent des formulaires et peut améliorer l'expérience de navigation globale. Changez le comportement de surlignage à l'aide de la méthode `setHighlightOnFocus()` avec l'un des énumérations intégrées `HasHighlightOnFocus.Behavior` :

- `ALL`
Le contenu du composant est toujours automatiquement surligné lorsque le composant reçoit le focus.
- `FOCUS`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus sous contrôle programmatique.
- `FOCUS_OR_KEY`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus sous contrôle programmatique ou en y accédant via la tabulation.
- `FOCUS_OR_MOUSE`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus sous contrôle programmatique ou en y accédant avec la souris.
- `KEY`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus en y accédant par tabulation.
- `KEY_MOUSE`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus en y accédant par tabulation ou en y accédant avec la souris.
- `MOUSE`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus en y accédant avec la souris.
- `NONE`
Le contenu du composant n'est jamais automatiquement surligné lorsque le composant reçoit le focus.

:::note
Si le contenu était surligné lors de la perte de focus, il sera de nouveau surligné lors du regain de focus, quelle que soit la behavior définie.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les emplacements fournissent des options flexibles pour améliorer la capacité d'un `ComboBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, la capacité de réinitialiser/effacer, des photos de profil/avatar et d'autres composants bénéfiques imbriqués dans un `ComboBox` pour clarifier davantage le sens prévu pour les utilisateurs. Le `ComboBox` a deux emplacements : les emplacements `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après les options à l'intérieur d'un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stylisation {#styling}

<TableBuilder name="ComboBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ComboBox`, considérez les meilleures pratiques suivantes :

1. **Précharger les valeurs courantes** : S'il y a des éléments courants ou fréquemment utilisés, préchargez-les dans la liste `ComboBox`. Cela accélère la sélection des éléments couramment choisis et encourage la cohérence.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Veillez à ce que les utilisateurs puissent comprendre facilement le but de chaque choix.

3. **Validation** : Mettez en œuvre une validation des entrées pour gérer les entrées personnalisées. Vérifiez l'exactitude et la cohérence des données. Vous pouvez vouloir suggérer des corrections ou des confirmations pour les entrées ambiguës.

4. **Sélection par défaut** : Définissez une sélection par défaut, surtout s'il existe un choix courant ou recommandé. Cela améliore l'expérience utilisateur en réduisant le besoin de clics supplémentaires.

5. **ComboBox vs. autres composants de liste** : Un `ComboBox` est le meilleur choix si vous avez besoin d'une seule entrée de la part de l'utilisateur et que vous souhaitez leur fournir des choix prédéterminés ainsi que la capacité de personnaliser leur saisie. Un autre composant de liste peut être mieux si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments à la fois : [ListBox](./list-box.md)
    - Prévenir les saisies personnalisées : [ChoiceBox](./choice-box.md)
