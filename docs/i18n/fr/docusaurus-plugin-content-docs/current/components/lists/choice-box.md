---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 1da4824585c11423d72c2b75b451a6db
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Le composant `ChoiceBox` présente une liste déroulante à partir de laquelle les utilisateurs sélectionnent une option unique. Lorsqu'une sélection est effectuée, la valeur choisie est affichée dans le bouton. C'est un bon choix lorsque les utilisateurs doivent choisir parmi un ensemble fixe de choix prédéfinis, et les touches fléchées peuvent être utilisées pour naviguer dans la liste.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Liste" />

Les composants `ChoiceBox` sont utilisés à diverses fins, telles que la sélection d'éléments dans un menu, le choix parmi une liste de catégories ou la sélection d'options parmi des ensembles prédéfinis. Ils offrent un moyen organisé et visuellement attrayant pour les utilisateurs de faire des sélections, en particulier lorsqu'il existe plusieurs options disponibles. Les usages courants incluent :

1. **Sélection d'options par l'utilisateur** : L'objectif principal d'un `ChoiceBox` est de permettre aux utilisateurs de sélectionner une seule option parmi une liste. Cela est précieux dans les applications qui nécessitent que les utilisateurs fassent des choix, tels que :
    - Choisir parmi une liste de catégories
    - Sélectionner des options parmi des ensembles prédéfinis

2. **Saisies de formulaire** : Lors de la conception de formulaires qui exigent que les utilisateurs saisissent des options spécifiques, le `ChoiceBox` simplifie le processus de sélection. Qu'il s'agisse de sélectionner un pays, un état ou toute autre option à partir d'une liste prédéfinie, le `ChoiceBox` rationalise le processus de saisie.

3. **Filtrage et tri** : Le `ChoiceBox` peut être utilisé pour des tâches de filtrage et de tri dans les applications. Les utilisateurs peuvent choisir des critères de filtre ou des préférences de tri à partir d'une liste, facilitant ainsi l'organisation et la navigation dans les données.

4. **Configuration et paramètres** : Lorsque votre application comprend des paramètres ou des options de configuration, le `ChoiceBox` offre un moyen intuitif pour les utilisateurs d'ajuster les préférences. Les utilisateurs peuvent sélectionner des paramètres à partir d'une liste, ce qui facilite l'adaptation de l'application à leurs besoins.

:::tip
Le `ChoiceBox` est destiné à être utilisé lorsque un nombre prédéfini d'options est disponible, et les options personnalisées ne doivent pas être autorisées ou incluses. Si vous souhaitez permettre aux utilisateurs de saisir des valeurs personnalisées, utilisez un [`ComboBox`](./combo-box.md) à la place.
:::

## Type de menu déroulant {#dropdown-type}

L'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> attribuera une valeur à l'attribut `type` d'un `ChoiceBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans le menu déroulant du `ChoiceBox`. Cela est utile pour le style, car le menu déroulant est retiré de sa position actuelle dans le DOM et déplacé à la fin du corps de la page lorsqu'il est ouvert.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Ce détachement crée une situation où cibler directement le menu déroulant à l'aide de sélecteurs CSS ou de parties ombragées à partir du composant parent devient délicat, sauf si vous utilisez l'attribut de type de menu déroulant.

Dans la démonstration ci-dessous, le type de menu déroulant est défini et utilisé dans le fichier CSS pour sélectionner le menu déroulant et changer la couleur de fond.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Nombre maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le menu déroulant d'un `ChoiceBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler combien d'éléments sont affichés.

:::tip
Utiliser un nombre inférieur ou égal à 0 entraînera la désactivation de cette propriété.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ChoiceBox` peut être contrôlée par programme avec les méthodes `open()` et `close()`. Ces méthodes vous permettent d'afficher la liste des options à sélectionner ou de la cacher si nécessaire, offrant une plus grande flexibilité dans la gestion du comportement d'un `ChoiceBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsqu'un `ChoiceBox` est fermé et lorsqu'il est ouvert, vous donnant plus de contrôle pour déclencher des actions spécifiques.

```Java
//Se concentrer ou ouvrir le prochain composant dans un formulaire
ChoiceBox university = new ChoiceBox("Université");
ChoiceBox major = new ChoiceBox("Majeure");
Button submit = new Button("Envoyer");

//... Ajouter des listes d'universités et de majeures

university.onClose(e -> {
  major.focus();
});

major.onClose(e -> {
  submit.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ChoiceBox` comprend des méthodes permettant de manipuler les dimensions du menu déroulant. La **hauteur maximale** et la **largeur minimale** du menu déroulant peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Le passage d'une valeur `String` à l'une ou l'autre de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), comme des pixels, des dimensions de viewport ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les slots offrent des options flexibles pour améliorer les capacités d'un `ChoiceBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, des capacités de nettoyage/réinitialisation, des images de profil/avatar, et d'autres composants bénéfiques intégrés dans un `ChoiceBox` pour clarifier davantage la signification prévue pour les utilisateurs. 
Le `ChoiceBox` a deux slots : les slots `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Style {#styling}

<TableBuilder name="ChoiceBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, considérez les meilleures pratiques suivantes :

1. **Options claires et limitées** : Gardez la liste des choix concise dans la mesure du possible, et pertinente pour la tâche de l'utilisateur. Un `ChoiceBox` est idéal pour présenter une liste claire d'options.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option soient conviviales et explicites. Veillez à ce que les utilisateurs puissent facilement comprendre le but de chaque choix.

3. **Sélection par défaut** : Définissez une sélection par défaut lorsque le `ChoiceBox` est initialement affiché. Cela garantit une option présélectionnée, réduisant le nombre d'interactions nécessaires pour faire un choix.

4. **ChoiceBox vs. Autres composants de liste** : Un `ChoiceBox` est le meilleur choix si vous devez restreindre la saisie de l'utilisateur à un seul choix parmi une liste d'options prédéterminées. Un autre composant de liste peut être mieux si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments à la fois : [`ListBox`](./list-box.md)
    - Autoriser la saisie personnalisée : [`ComboBox`](./combo-box.md)
