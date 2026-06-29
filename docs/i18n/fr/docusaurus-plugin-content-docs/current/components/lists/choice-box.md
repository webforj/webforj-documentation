---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: cf4d092418fcf1f593b8b8d00a47344b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Le composant `ChoiceBox` présente une liste déroulante à partir de laquelle les utilisateurs sélectionnent une seule option. Lorsqu'une sélection est effectuée, la valeur choisie est affichée dans le bouton. C'est un bon choix lorsque les utilisateurs doivent choisir parmi un ensemble fixe de choix prédéfinis, et les touches fléchées peuvent être utilisées pour naviguer dans la liste.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Les composants `ChoiceBox` sont utilisés à diverses fins, telles que la sélection d'éléments dans un menu, le choix parmi une liste de catégories ou la sélection d'options à partir d'ensembles prédéfinis. Ils offrent une manière organisée et visuellement agréable pour les utilisateurs de faire des sélections, en particulier lorsque plusieurs options sont disponibles. Les usages courants incluent :

1. **Sélection d'Options par l'Utilisateur** : Le principal objectif d'un `ChoiceBox` est de permettre aux utilisateurs de sélectionner une seule option dans une liste. Cela est précieux dans les applications nécessitant des choix de la part des utilisateurs, tels que :
    - Choisir parmi une liste de catégories
    - Sélectionner des options à partir d'ensembles prédéfinis

2. **Entrées de Formulaires** : Lors de la conception de formulaires nécessitant que les utilisateurs saisissent des options spécifiques, le `ChoiceBox` simplifie le processus de sélection. Que ce soit pour sélectionner un pays, un état ou toute autre option à partir d'une liste prédéfinie, le `ChoiceBox` facilite le processus d'entrée.

3. **Filtrage et Tri** : Le `ChoiceBox` peut être utilisé pour des tâches de filtrage et de tri dans les applications. Les utilisateurs peuvent choisir des critères de filtrage ou des préférences de tri à partir d'une liste, facilitant l'organisation et la navigation des données.

4. **Configuration et Paramètres** : Lorsque votre application comprend des paramètres ou des options de configuration, le `ChoiceBox` fournit un moyen intuitif pour les utilisateurs d'ajuster leurs préférences. Les utilisateurs peuvent sélectionner des paramètres à partir d'une liste, ce qui facilite l'adaptation de l'application à leurs besoins.

:::tip
Le `ChoiceBox` est destiné à être utilisé lorsque un nombre prédéterminé d'options est disponible, et les options personnalisées ne doivent pas être autorisées ou incluses. Si vous souhaitez permettre aux utilisateurs d'entrer des valeurs personnalisées, utilisez un [`ComboBox`](./combo-box.md) à la place.
:::

## Type de Dropdown {#dropdown-type}

Utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> assignera une valeur à l'attribut `type` d'un `ChoiceBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans le dropdown du `ChoiceBox`. Cela est utile pour le style, car le dropdown est retiré de sa position actuelle dans le DOM et relocalisé à la fin du corps de la page lorsqu'il est ouvert.

<!-- ![exemple type](/img/components/_images/choicebox/type.png)
![exemple type](/img/components/_images/choicebox/type_zoomed.png) -->

Cette séparation crée une situation où cibler directement le dropdown à l'aide de sélecteurs CSS ou de parties ombragées à partir du composant parent devient difficile, sauf si vous utilisez l'attribut de type de dropdown.

Dans la démo ci-dessous, le type de Dropdown est défini et utilisé dans le fichier CSS pour agrandir une option lorsque vous passez votre souris dessus.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Nombre maximum de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le dropdown d'un `ChoiceBox` sera augmenté pour s'adapter au contenu. Cependant, en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>, il est possible de contrôler le nombre d'éléments affichés. 

:::tip
Utiliser un nombre inférieur ou égal à 0 désactivera cette propriété.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ChoiceBox` peut être contrôlée de manière programmatique avec les méthodes `open()` et `close()`. Ces méthodes vous permettent d'afficher la liste des options pour sélection ou de la masquer si nécessaire, offrant une plus grande flexibilité dans la gestion du comportement d'un `ChoiceBox`.

De plus, webforJ dispose d'écouteurs d'événements pour quand le `ChoiceBox` est fermé et lorsqu'il est ouvert, vous donnant plus de contrôle pour déclencher des actions spécifiques.

```Java
//Concentrer ou ouvrir le prochain composant dans un formulaire
ChoiceBox university = new ChoiceBox("Université");
ChoiceBox major = new ChoiceBox("Majeure");
Button submit = new Button("Soumettre");

//... Ajouter des listes d'universités et de majeures

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ChoiceBox` dispose de méthodes qui permettent de manipuler les dimensions du dropdown. La **hauteur maximale** et **largeur minimale** du dropdown peuvent être définies en utilisant les méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement. 

:::tip
Passer une valeur `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), telles que des pixels, des dimensions de viewport, ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les slots offrent des options flexibles pour améliorer les capacités d'un `ChoiceBox`. Vous pouvez avoir des icônes, des étiquettes, des roues de chargement, des capacités de nettoyage/réinitialisation, des images de profil/avatar, et d'autres composants bénéfiques imbriqués dans un `ChoiceBox` pour clarifier l'intention pour les utilisateurs.
Le `ChoiceBox` a deux slots : le `prefix` et le `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Style {#styling}

<TableBuilder name="ChoiceBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, considérez les meilleures pratiques suivantes :

1. **Options Claires et Limitées** : Gardez la liste des choix concise lorsque cela est possible, et pertinente par rapport à la tâche de l'utilisateur. Un `ChoiceBox` est idéal pour présenter une liste claire d'options.

2. **Étiquettes Conviviaux** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Veillez à ce que les utilisateurs puissent facilement comprendre l'objectif de chaque choix.

3. **Sélection Par Défaut** : Définissez une sélection par défaut lorsque le ChoiceBox est affiché pour la première fois. Cela garantit une option pré-sélectionnée, réduisant le nombre d'interactions nécessaires pour faire un choix.

4. **ChoiceBox vs. Autres Composants de Liste** : Un `ChoiceBox` est le meilleur choix si vous avez besoin de restreindre la saisie utilisateur à un seul choix parmi une liste d'options prédéterminées. Un autre composant de liste peut être mieux si vous avez besoin des comportements suivants :
    - Sélection Multiple et affichage de tous les éléments à la fois : [`ListBox`](./list-box.md)
    - Autoriser l'entrée personnalisée : [`ComboBox`](./combo-box.md)
