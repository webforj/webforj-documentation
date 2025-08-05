---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: d2e1c4ceeb6346a98d03075f19f5ee1c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="Liste" />

Le composant `ChoiceBox` est un élément d'interface utilisateur conçu pour présenter aux utilisateurs une liste d'options ou de choix. Les utilisateurs peuvent sélectionner une seule option dans cette liste, généralement en cliquant sur le `ChoiceBox`, ce qui déclenche l'affichage d'une liste déroulante contenant les choix disponibles. Les utilisateurs peuvent également interagir avec le `ChoiceBox` à l'aide des touches fléchées. Lorsqu'un utilisateur effectue une sélection, l'option choisie est alors affichée dans le bouton `ChoiceBox`.

## Usages {#usages}
Les composants `ChoiceBox` sont utilisés à diverses fins, telles que la sélection d'éléments dans un menu, le choix parmi une liste de catégories ou la sélection d'options parmi des ensembles prédéfinis. Ils fournissent un moyen organisé et visuellement agréable pour les utilisateurs de faire des sélections, en particulier lorsqu'il y a plusieurs options disponibles. Les usages courants incluent :

1. **Sélection d'options par l'utilisateur** : Le but principal d'un `ChoiceBox` est de permettre aux utilisateurs de sélectionner une option unique parmi une liste. Ceci est précieux dans les applications qui nécessitent que les utilisateurs fassent des choix, tels que :
    - Choisir parmi une liste de catégories
    - Sélectionner des options parmi des ensembles prédéfinis

2. **Entrées de formulaires** : Lors de la conception de formulaires nécessitant que les utilisateurs saisissent des options spécifiques, le `ChoiceBox` simplifie le processus de sélection. Que ce soit pour choisir un pays, un état, ou toute autre option d'une liste prédéfinie, le `ChoiceBox` rationalise le processus d'entrée.

3. **Filtrage et tri** : Le `ChoiceBox` peut être utilisé pour des tâches de filtrage et de tri dans les applications. Les utilisateurs peuvent choisir des critères de filtrage ou des préférences de tri à partir d'une liste, facilitant ainsi l'organisation et la navigation dans les données.

4. **Configuration et paramètres** : Lorsque votre application inclut des paramètres ou des options de configuration, le `ChoiceBox` offre un moyen intuitif pour les utilisateurs d'ajuster leurs préférences. Les utilisateurs peuvent choisir des paramètres dans une liste, ce qui facilite l'adaptation de l'application à leurs besoins.

:::tip
Le `ChoiceBox` est destiné à être utilisé lorsque un nombre prédéfini d'options est disponible, et les options personnalisées ne devraient pas être autorisées ou incluses. Si vous souhaitez permettre aux utilisateurs de saisir des valeurs personnalisées, utilisez un [`ComboBox`](./combo-box.md) à la place.
:::

## Type de dropdown {#dropdown-type}

L'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> affectera une valeur à l'attribut `type` d'un `ChoiceBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans le dropdown du `ChoiceBox`. Cela est utile pour le stylisme, car le dropdown est retiré de sa position actuelle dans le DOM et déplacé à la fin du corps de la page lorsqu'il est ouvert.

<!-- ![exemple type](/img/components/_images/choicebox/type.png)
![exemple type](/img/components/_images/choicebox/type_zoomed.png) -->

Ce détachement crée une situation où le ciblage direct du dropdown à l'aide de CSS ou de sélecteurs de parties d'ombre à partir du composant parent devient difficile, à moins que vous n'utilisiez l'attribut de type de dropdown.

Dans la démo ci-dessous, le type de Dropdown est défini et utilisé dans le fichier CSS pour sélectionner le dropdown et changer la couleur d'arrière-plan.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Compte maximum de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le dropdown d'un `ChoiceBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler combien d'éléments sont affichés.

:::tip
Utiliser un nombre inférieur ou égal à 0 aura pour effet de désactiver cette propriété.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ChoiceBox` peut être contrôlée de manière programmatique avec les méthodes `open()` et `close()`. Ces méthodes permettent d'afficher la liste des options pour sélection ou de la cacher au besoin, offrant ainsi une plus grande flexibilité dans la gestion du comportement d'un `ChoiceBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsque le `ChoiceBox` est fermé et lorsqu'il est ouvert, vous donnant plus de contrôle pour déclencher des actions spécifiques.

```Java
//Mettre le focus ou ouvrir le prochain composant dans un formulaire
ChoiceBox université = new ChoiceBox("Université");
ChoiceBox spécialité = new ChoiceBox("Spécialité");
Button soumettre = new Button("Soumettre");

//... Ajouter des listes d'universités et de spécialités

université.onClose( e ->{
  spécialité.focus();
});

spécialité.onClose( e ->{
  soumettre.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ChoiceBox` possède des méthodes qui permettent de manipuler les dimensions du dropdown. La **hauteur maximale** et la **largeur minimale** du dropdown peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Passer une valeur `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), telles que des pixels, des dimensions de viewport ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les emplacements fournissent des options flexibles pour améliorer la capacité d'un `ChoiceBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, une capacité de nettoyage/réinitialisation, des photos de profil/avatar et d'autres composants bénéfiques imbriqués dans un `ChoiceBox` pour clarifier davantage le sens prévu pour les utilisateurs. Le `ChoiceBox` dispose de deux emplacements : les emplacements `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stylisation {#styling}

<TableBuilder name="ChoiceBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, considérez les meilleures pratiques suivantes :

1. **Options claires et limitées** : Gardez la liste des choix concise autant que possible, et pertinente pour la tâche de l'utilisateur. Un `ChoiceBox` est idéal pour présenter une liste d'options claire.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Assurez-vous que les utilisateurs peuvent facilement comprendre le but de chaque choix.

3. **Sélection par défaut** : Définissez une sélection par défaut lorsque le ChoiceBox est affiché pour la première fois. Cela garantit une option présélectionnée, réduisant le nombre d'interactions nécessaires pour faire un choix.

4. **ChoiceBox vs. Autres composants de liste** : Un `ChoiceBox` est le meilleur choix si vous devez restreindre l'entrée utilisateur à un seul choix parmi une liste d'options prédéterminées. Un autre composant de liste pourrait être mieux si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments à la fois : [`ListBox`](./list-box.md)
    - Autoriser une entrée personnalisée : [`ComboBox`](./combo-box.md)
