---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: 1c1224ca662a0e268606dc1cb6a0e96a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Le composant `ChoiceBox` présente une liste déroulante à partir de laquelle les utilisateurs sélectionnent une seule option. Lorsqu'une sélection est faite, la valeur choisie est affichée dans le bouton. C'est un bon choix lorsque les utilisateurs doivent choisir parmi un ensemble fixe de choix prédéfinis, et les touches fléchées peuvent être utilisées pour naviguer dans la liste.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Les composants `ChoiceBox` sont utilisés à diverses fins, telles que la sélection d'éléments dans un menu, le choix parmi une liste de catégories ou la sélection d'options parmi des ensembles prédéfinis. Ils offrent une manière organisée et visuellement agréable pour les utilisateurs de faire des sélections, en particulier lorsqu'il y a plusieurs options disponibles. Les usages courants incluent :

1. **Sélection d'options par l'utilisateur** : Le but principal d'un `ChoiceBox` est de permettre aux utilisateurs de sélectionner une seule option dans une liste. Cela est précieux dans les applications qui nécessitent que les utilisateurs prennent des décisions, telles que :
    - Choix d'une liste de catégories
    - Sélection d'options parmi des ensembles prédéfinis

2. **Entrées de formulaire** : Lors de la conception de formulaires nécessitant que les utilisateurs saisissent des options spécifiques, le `ChoiceBox` simplifie le processus de sélection. Qu'il s'agisse de sélectionner un pays, un état ou toute autre option dans une liste prédéfinie, le `ChoiceBox` rationalise le processus de saisie.

3. **Filtrage et tri** : Le `ChoiceBox` peut être utilisé pour des tâches de filtrage et de tri dans les applications. Les utilisateurs peuvent choisir des critères de filtrage ou des préférences de tri à partir d'une liste, facilitant ainsi l'organisation et la navigation des données.

4. **Configuration et paramètres** : Lorsque votre application comprend des paramètres ou des options de configuration, le `ChoiceBox` fournit une manière intuitive pour les utilisateurs d'ajuster leurs préférences. Les utilisateurs peuvent choisir des paramètres dans une liste, facilitant ainsi l'adaptation de l'application à leurs besoins.

:::tip
Le `ChoiceBox` est destiné à être utilisé lorsque le nombre d'options disponibles est fixe, et les options personnalisées ne doivent pas être autorisées ni incluses. Si vous souhaitez permettre aux utilisateurs d'entrer des valeurs personnalisées, utilisez un [`ComboBox`](./combo-box.md) à la place.
:::

## Type de déroulant {#dropdown-type}

L'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> attribuera une valeur à l'attribut `type` d'un `ChoiceBox`, et une valeur correspondante à l'attribut `data-dropdown-for` dans le déroulant du `ChoiceBox`. Cela est utile pour le stylisme, car le déroulant est sorti de sa position actuelle dans le DOM et déplacé à la fin du corps de la page lorsqu'il est ouvert.

Cette détachement crée une situation où cibler directement le déroulant à l'aide de sélecteurs CSS ou de parties d'ombre depuis le composant parent devient difficile, à moins d'utiliser l'attribut de type de déroulant.

Dans la démo ci-dessous, le type de déroulant est défini et utilisé dans le fichier CSS pour agrandir une option lorsque vous passez la souris dessus.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Nombre maximum de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le déroulant d'un `ChoiceBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler combien d'éléments sont affichés.

:::tip
Utiliser un nombre inférieur ou égal à 0 désactivera cette propriété.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ChoiceBox` peut être contrôlée programmatiquement avec les méthodes `open()` et `close()`. Ces méthodes vous permettent d'afficher la liste des options pour sélection ou de la cacher selon les besoins, offrant une plus grande flexibilité dans la gestion du comportement d'un `ChoiceBox`.

De plus, webforJ dispose d'écouteurs d'événements lorsque le `ChoiceBox` est fermé et lorsqu'il est ouvert, vous permettant de déclencher des actions spécifiques.

```Java
//Focaliser ou ouvrir le prochain composant dans un formulaire
ChoiceBox university = new ChoiceBox("University");
ChoiceBox major = new ChoiceBox("Major");
Button submit = new Button("Submit");

//... Ajouter des listes d'universités et de majeures

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ChoiceBox` dispose de méthodes permettant de manipuler les dimensions du déroulant. La **hauteur maximale** et la **largeur minimale** du déroulant peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Passer une valeur `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), telles que les pixels, les dimensions des fenêtres, ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les slots offrent des options flexibles pour améliorer la capacité d'un `ChoiceBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, des capacités de nettoyage/réinitialisation, des images de profil/avatar et d'autres composants utiles imbriqués dans un `ChoiceBox` pour clarifier davantage le sens pour les utilisateurs.
Le `ChoiceBox` dispose de deux slots : le slot `prefix` et le slot `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Styliser {#styling}

<TableBuilder name="ChoiceBox" />

## Bonnes pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, considérez les meilleures pratiques suivantes :

1. **Options claires et limitées** : Gardez la liste des choix concise lorsque cela est possible, et pertinente par rapport à la tâche de l'utilisateur. Un `ChoiceBox` est idéal pour présenter une liste claire d'options.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Veillez à ce que les utilisateurs puissent facilement comprendre l'objectif de chaque choix.

3. **Sélection par défaut** : Définissez une sélection par défaut lorsque le `ChoiceBox` est affiché initialement. Cela garantit une option pré-sélectionnée, réduisant le nombre d'interactions nécessaires pour faire un choix.

4. **ChoiceBox vs. Autres composants de liste** : Un `ChoiceBox` est le meilleur choix si vous devez restreindre l'entrée de l'utilisateur à un seul choix parmi une liste d'options prédéterminées. Un autre composant de liste peut être meilleur si vous avez besoin des comportements suivants :
    - Sélection multiple et afficher tous les éléments à la fois : [`ListBox`](./list-box.md)
    - Autoriser une saisie personnalisée : [`ComboBox`](./combo-box.md)
