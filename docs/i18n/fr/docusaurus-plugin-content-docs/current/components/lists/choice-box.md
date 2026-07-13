---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
description: >-
  Pick a single value from a fixed set with the ChoiceBox dropdown, including
  dropdown type styling, max row count, and keyboard navigation.
_i18n_hash: f897ac9d3f5c252ac323762080e1edcf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Le composant `ChoiceBox` présente une liste déroulante à partir de laquelle les utilisateurs sélectionnent une seule option. Lorsqu'une sélection est faite, la valeur choisie est affichée dans le bouton. Il est particulièrement adapté lorsque les utilisateurs doivent choisir parmi un ensemble fixe de choix prédéfinis, et les touches fléchées peuvent être utilisées pour naviguer dans la liste.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Les composants `ChoiceBox` sont utilisés pour divers objectifs, tels que la sélection d'éléments dans un menu, le choix parmi une liste de catégories ou la sélection d'options parmi des ensembles prédéfinis. Ils offrent une manière organisée et visuellement attrayante pour les utilisateurs de faire des sélections, en particulier lorsqu'il y a plusieurs options disponibles. Les usages courants incluent :

1. **Sélection d'options par les utilisateurs** : Le but principal d'un `ChoiceBox` est de permettre aux utilisateurs de sélectionner une seule option dans une liste. Cela est précieux dans les applications qui nécessitent que les utilisateurs fassent des choix, comme :
    - Choisir dans une liste de catégories
    - Sélectionner des options à partir d'ensembles prédéfinis

2. **Entrées de formulaire** : Lors de la conception de formulaires nécessitant que les utilisateurs saisissent des options spécifiques, le `ChoiceBox` simplifie le processus de sélection. Que ce soit pour choisir un pays, un état ou toute autre option dans une liste prédéfinie, le `ChoiceBox` rationalise le processus de saisie.

3. **Filtrage et tri** : Le `ChoiceBox` peut être utilisé pour des tâches de filtrage et de tri dans les applications. Les utilisateurs peuvent choisir des critères de filtrage ou des préférences de tri à partir d'une liste, facilitant l'organisation et la navigation des données.

4. **Configuration et paramètres** : Lorsque votre application inclut des paramètres ou des options de configuration, le `ChoiceBox` offre un moyen intuitif pour les utilisateurs d'ajuster leurs préférences. Les utilisateurs peuvent choisir des paramètres dans une liste, facilitant ainsi l'adaptation de l'application à leurs besoins.

:::tip
Le `ChoiceBox` est destiné à être utilisé lorsqu'un nombre prédéfini d'options est disponible, et les options personnalisées ne doivent pas être autorisées ou incluses. Si vous souhaitez permettre aux utilisateurs d'entrer des valeurs personnalisées, utilisez un [`ComboBox`](./combo-box.md) à la place.
:::

## Type de menu déroulant {#dropdown-type}

Utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> assignera une valeur à l'attribut `type` d'un `ChoiceBox`, ainsi qu'une valeur correspondante pour l'attribut `data-dropdown-for` dans le menu déroulant du `ChoiceBox`. Cela est utile pour le style, car le menu déroulant est retiré de sa position actuelle dans le DOM et déplacé à la fin du corps de la page lorsqu'il est ouvert.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Cette séparation crée une situation où cibler directement le
menu déroulant à l'aide de sélecteurs CSS ou de parties ombragées depuis le composant parent devient difficile, à moins que vous n'utilisiez l'attribut de type de menu déroulant.

Dans la démo ci-dessous, le type de menu déroulant est défini et utilisé dans le fichier CSS pour agrandir une option lorsque vous passez la souris dessus.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/frontend/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Nombre maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le menu déroulant d'un `ChoiceBox` sera augmenté pour s'adapter au contenu. Cependant, en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>, il est possible de contrôler combien d'éléments sont affichés.

:::tip
Utiliser un nombre inférieur ou égal à 0 entraînera l'annulation de cette propriété.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options d'un `ChoiceBox` peut être contrôlée par programme avec les méthodes `open()` et `close()`. 
Ces méthodes permettent d'afficher la liste d'options pour la sélection ou de la masquer selon les besoins, offrant ainsi plus de flexibilité dans la gestion du comportement d'un `ChoiceBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsque le `ChoiceBox` est fermé et lorsqu'il est ouvert, vous donnant plus de contrôle pour déclencher des actions spécifiques.

```Java
//Concentrez-vous ou ouvrez le prochain composant dans un formulaire
ChoiceBox university = new ChoiceBox("University");
ChoiceBox major = new ChoiceBox("Major");
Button submit = new Button("Submit");

//... Ajoutez des listes d'universités et de majors

university.onClose( e ->{
  major.focus();
});

major.onClose( e ->{
  submit.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ChoiceBox` a des méthodes qui permettent de manipuler les dimensions du menu déroulant. La **hauteur maximale** et la **largeur minimale** du menu déroulant peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink> respectivement.

:::tip
Passer une valeur `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), comme des pixels, des dimensions de vue ou d'autres règles valides. Passer un `int` fixera la valeur passée en pixels.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les emplacements offrent des options flexibles pour améliorer la capacité d'un `ChoiceBox`. Vous pouvez avoir des icônes, des étiquettes, des indicateurs de chargement, des capacités de suppression/réinitialisation, des images d'avatar/profil et d'autres composants bénéfiques imbriqués dans un `ChoiceBox` pour clarifier davantage la signification intended to users.
Le `ChoiceBox` a deux emplacements : les emplacements `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Style {#styling}

<TableBuilder name="ChoiceBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale avec le composant `ChoiceBox`, considérez les meilleures pratiques suivantes :

1. **Options claires et limitées** : Gardez la liste des choix concise lorsque cela est possible, et pertinente pour la tâche de l'utilisateur. Un `ChoiceBox` est idéal pour présenter une liste claire d'options.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Assurez-vous que les utilisateurs peuvent facilement comprendre l'objectif de chaque choix.

3. **Sélection par défaut** : Définissez une sélection par défaut lorsque le `ChoiceBox` est initialement affiché. Cela garantit une option pré-sélectionnée, réduisant le nombre d'interactions nécessaires pour faire un choix.

4. **ChoiceBox vs. Autres composants de liste** : Un `ChoiceBox` est le meilleur choix si vous devez restreindre la saisie de l'utilisateur à un seul choix parmi une liste d'options prédéterminées. Un autre composant de liste peut être préférable si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments à la fois : [`ListBox`](./list-box.md)
    - Permettre une saisie personnalisée : [`ComboBox`](./combo-box.md)
