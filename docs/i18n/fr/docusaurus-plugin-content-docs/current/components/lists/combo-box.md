---
sidebar_position: 10
title: ComboBox
slug: combobox
description: >-
  Combine a dropdown list with a text input in the ComboBox to let users select
  preset items or type custom values with placeholder support.
_i18n_hash: 4ef8ce7040bed877e314790f155f728a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Le composant `ComboBox` combine une liste déroulante avec un champ de texte, permettant aux utilisateurs de sélectionner parmi des options prédéfinies ou de taper une valeur personnalisée. Lorsqu'il est nécessaire d'autoriser des entrées personnalisées en plus d'un ensemble d'options suggérées, il comble le vide que `ChoiceBox` ne couvre pas.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Le composant ComboBox est un élément d'entrée polyvalent qui combine les fonctionnalités d'une liste déroulante et d'un champ de texte. Il permet aux utilisateurs de sélectionner des éléments d'une liste prédéfinie ou d'entrer des valeurs personnalisées si nécessaire. Cette section explore les usages courants du composant ComboBox dans divers scénarios :

1. **Recherche et saisie de produits** : Utilisez un ComboBox pour implémenter une fonctionnalité de recherche et de saisie de produits. Les utilisateurs peuvent soit sélectionner un produit dans une liste prédéfinie, soit taper un nom de produit personnalisé. Cela est utile pour des applications comme les sites de commerce électronique où les produits sont vastes et diversifiés.

2. **Sélection et saisie de balises** : Dans les applications impliquant le balisage de contenu, un ComboBox peut être un excellent choix. Les utilisateurs peuvent sélectionner dans une liste de balises existantes ou ajouter des balises personnalisées en les tapant. Cela est utile pour organiser et catégoriser le contenu. Des exemples de telles balises incluent :
    >- Étiquettes de projet : Dans un outil de gestion de projet, les utilisateurs peuvent sélectionner des étiquettes ou des balises (par exemple, "Urgent", "En cours", "Complété") pour catégoriser des tâches ou des projets, et ils peuvent créer des étiquettes personnalisées si nécessaire.
    >- Ingrédients de recettes : Dans une application de cuisine ou de recette, les utilisateurs peuvent sélectionner des ingrédients dans une liste (par exemple, "Tomates", "Oignons", "Poulet") ou ajouter leurs propres ingrédients pour des recettes personnalisées.
    >- Balises de localisation : Dans une application de cartographie ou de géolocalisation, les utilisateurs peuvent choisir des balises de localisation prédéfinies (par exemple, "Plage", "Ville", "Parc") ou créer des balises personnalisées pour marquer des lieux spécifiques sur une carte.

3. **Saisie de données avec valeurs suggérées** : Dans les formulaires de saisie de données, un ComboBox peut être utilisé pour accélérer l'entrée en fournissant une liste de valeurs suggérées en fonction de la saisie de l'utilisateur. Cela aide les utilisateurs à entrer des données de manière précise et efficace.

    :::tip
    Le `ComboBox` doit être utilisé lorsque les utilisateurs sont autorisés à entrer des valeurs personnalisées. Si seules des valeurs prédéfinies sont souhaitées, utilisez un [`ChoiceBox`](./choice-box.md) à la place.
    :::

## Valeur personnalisée {#custom-value}

Modifier la propriété de valeur personnalisée permet de contrôler si un utilisateur peut ou non modifier la valeur dans le champ de saisie du composant `ComboBox`. Si `true`, ce qui est la valeur par défaut, alors un utilisateur peut modifier la valeur. Si défini sur `false`, l'utilisateur ne pourra pas modifier la valeur. Cela peut être réglé à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Placeholder {#placeholder}

Un placeholder peut être défini pour un `ComboBox` qui s'affichera dans le champ de texte du composant lorsqu'il est vide, afin d'inciter les utilisateurs à entrer la valeur souhaitée dans le champ. Cela peut être fait à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Type de déroulant {#dropdown-type}

L'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> attribuera une valeur à l'attribut `type` d'un `ComboBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans le déroulant du `ComboBox`. Cela est utile pour le style, car le déroulant est retiré de sa position actuelle dans le DOM et déplacé à la fin du corps de la page lorsqu'il est ouvert.

Cette détachement crée une situation où le ciblage direct du déroulant à l'aide de CSS ou de sélecteurs de parties ombragées du composant parent devient difficile, à moins d'utiliser l'attribut de type de déroulant.

Dans la démo ci-dessous, le type de déroulant est défini et utilisé dans le fichier CSS pour agrandir une option lorsque vous survolez.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Nombre maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le déroulant d'un `ComboBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler combien d'éléments sont affichés.

:::caution
Utiliser un nombre inférieur ou égal à 0 entraînera la réinitialisation de cette propriété.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ComboBox` peut être contrôlée par programmation à l'aide des méthodes `open()` et `close()`. Ces méthodes permettent d'afficher la liste des options à sélectionner ou de la masquer si nécessaire, offrant une plus grande flexibilité dans la gestion du comportement d'un `ComboBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsque le `ComboBox` est fermé et lorsque celui-ci est ouvert, vous permettant de déclencher des actions spécifiques.

```Java
//Mettre au point ou ouvrir le composant suivant dans un formulaire
ComboBox university = new ComboBox("University");
ComboBox major = new ComboBox("Major");
Button submit = new Button("Submit");

//... Ajouter des listes d'universités et de majeures

university.onClose( e ->{
  major.open();
});

major.onClose( e ->{
  submit.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ComboBox` dispose de méthodes permettant de manipuler les dimensions du déroulant. La **hauteur maximale** et la **largeur minimale** du déroulant peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement. 

:::tip
Passer une valeur `String` à l'une ou l'autre de ces méthodes permettra d'appliquer [toute unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), telle que les pixels, les dimensions de la fenêtre, ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Surlignage {#highlighting}

Lorsque vous travaillez avec un `ComboBox`, vous pouvez personnaliser le moment où le texte est surligné en fonction de la façon dont le composant obtient le focus. Cette fonctionnalité peut réduire les erreurs de saisie lorsque les utilisateurs complètent des formulaires et peut améliorer l'expérience de navigation globale. Modifiez le comportement de surlignage à l'aide de la méthode `setHighlightOnFocus()` avec l'un des énumérations `HasHighlightOnFocus.Behavior` intégrées :

- `ALL`
Les contenus du composant sont toujours automatiquement surlignés lorsque le composant reçoit le focus.
- `FOCUS`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus sous contrôle du programme.
- `FOCUS_OR_KEY`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus sous contrôle du programme ou en y accédant par tabulation.
- `FOCUS_OR_MOUSE`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus sous contrôle du programme ou en cliquant dedans avec la souris.
- `KEY`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus en y accédant par tabulation.
- `KEY_MOUSE`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus en y accédant par tabulation ou en cliquant dedans avec la souris.
- `MOUSE`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus en cliquant dedans avec la souris.
- `NONE`
Les contenus du composant ne sont jamais automatiquement surlignés lorsque le composant obtient le focus.

:::note
Si le contenu a été surligné lors de la perte de focus, il sera à nouveau surligné lors du regain de focus, quelle que soit la méthode définie.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les slots offrent des options flexibles pour améliorer la capacité d'un `ComboBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, une capacité de nettoyage/réinitialisation, des images d'avatar/profil, et d'autres composants bénéfiques imbriqués dans un `ComboBox` pour clarifier encore plus la signification voulue pour les utilisateurs. Le `ComboBox` a deux slots : les slots `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après les options dans un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stylisation {#styling}

<TableBuilder name="ComboBox" />

## Bonnes pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ComboBox`, considérez les bonnes pratiques suivantes :

1. **Précharger des valeurs communes** : S'il existe des éléments courants ou fréquemment utilisés, préchargez-les dans la liste du `ComboBox`. Cela accélère la sélection des éléments souvent choisis et encourage la constance.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Veillez à ce que les utilisateurs puissent facilement comprendre l'objectif de chaque choix.

3. **Validation** : Implémentez une validation des entrées pour gérer les entrées personnalisées. Vérifiez l'exactitude et la cohérence des données. Vous voudrez peut-être suggérer des corrections ou des confirmations pour les entrées ambiguës.

4. **Sélection par défaut** : Définissez une sélection par défaut, surtout s'il existe un choix commun ou recommandé. Cela améliore l'expérience utilisateur en réduisant le besoin de clics supplémentaires.

5. **ComboBox vs autres composants de liste** : Un `ComboBox` est le meilleur choix si vous avez besoin d'une seule entrée de la part de l'utilisateur et que vous souhaitez leur fournir des choix prédéterminés et la possibilité de personnaliser leur entrée. Un autre composant de liste peut être mieux si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments à la fois : [ListBox](./list-box.md)
    - Empêcher les entrées personnalisées : [ChoiceBox](./choice-box.md)
