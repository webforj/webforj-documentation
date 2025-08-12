---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: e90d77e503b1c8f7fc20109633b1e7be
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

<ParentLink parent="Liste" />

Le composant `ChoiceBox` est un élément d'interface utilisateur conçu pour présenter aux utilisateurs une liste d'options ou de choix. Les utilisateurs peuvent sélectionner une seule option dans cette liste, généralement en cliquant sur le `ChoiceBox`, ce qui déclenche l'affichage d'une liste déroulante contenant les choix disponibles. Les utilisateurs peuvent également interagir avec le `ChoiceBox` en utilisant les touches fléchées. Lorsqu'un utilisateur fait une sélection, l'option choisie est alors affichée dans le bouton `ChoiceBox`.

## Usages {#usages}
Les composants `ChoiceBox` sont utilisés à diverses fins, telles que la sélection d'articles dans un menu, le choix parmi une liste de catégories, ou le choix d'options parmi des ensembles prédéfinis. Ils fournissent un moyen organisé et visuellement agréable pour les utilisateurs de faire des sélections, en particulier lorsqu'il y a plusieurs options disponibles. Les usages courants incluent :

1. **Sélection d'options par l'utilisateur** : Le but principal d'un `ChoiceBox` est de permettre aux utilisateurs de sélectionner une seule option dans une liste. Cela est précieux dans les applications qui nécessitent que les utilisateurs fassent des choix, tels que :
    - Choisir parmi une liste de catégories
    - Choisir des options parmi des ensembles prédéfinis

2. **Inputs de formulaire** : Lors de la conception de formulaires nécessitant que les utilisateurs saisissent des options spécifiques, le `ChoiceBox` simplifie le processus de sélection. Que ce soit pour sélectionner un pays, un état ou toute autre option d'une liste prédéfinie, le `ChoiceBox` facilite le processus de saisie.

3. **Filtrage et tri** : Le `ChoiceBox` peut être utilisé pour des tâches de filtrage et de tri dans les applications. Les utilisateurs peuvent choisir des critères de filtre ou des préférences de tri à partir d'une liste, facilitant l'organisation et la navigation des données.

4. **Configuration et paramètres** : Lorsque votre application comprend des paramètres ou des options de configuration, le `ChoiceBox` offre un moyen intuitif pour les utilisateurs d'ajuster leurs préférences. Les utilisateurs peuvent choisir des paramètres à partir d'une liste, facilitant ainsi l'adaptation de l'application à leurs besoins.

:::tip
Le `ChoiceBox` est destiné à être utilisé lorsque un nombre prédéfini d'options est disponible, et des options personnalisées ne doivent pas être autorisées ou incluses. Si vous souhaitez permettre aux utilisateurs d'entrer des valeurs personnalisées, utilisez un [`ComboBox`](./combo-box.md) à la place.
:::

## Type de dropdown {#dropdown-type}

Utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> assignera une valeur à l'attribut `type` d'un `ChoiceBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans la liste déroulante du `ChoiceBox`. Cela est utile pour le style, car la liste déroulante est retirée de sa position actuelle dans le DOM et déplacée à la fin du corps de la page lorsqu'elle est ouverte.

Ce détachement crée une situation où cibler directement la liste déroulante à l'aide de CSS ou de sélecteurs de partie d'ombre depuis le composant parent devient difficile, à moins d'utiliser l'attribut de type de liste déroulante.

Dans la démonstration ci-dessous, le type de dropdown est défini et utilisé dans le fichier CSS pour sélectionner la liste déroulante et changer la couleur de fond.

<ComponentDemo 
path='/webforj/choiceboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Nombre maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans la liste déroulante d'un `ChoiceBox` sera augmenté pour s'adapter au contenu. Cependant, en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>, vous pouvez contrôler combien d'éléments sont affichés.

:::tip
Utiliser un nombre qui est inférieur ou égal à 0 aura pour effet de désactiver cette propriété.
:::

<ComponentDemo 
path='/webforj/choiceboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java'
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options d'un `ChoiceBox` peut être contrôlée programmatique avec les méthodes `open()` et `close()`. Ces méthodes vous permettent d'afficher la liste des options à sélectionner ou de la cacher selon vos besoins, offrant une plus grande flexibilité dans la gestion du comportement d'un `ChoiceBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsque le `ChoiceBox` est fermé et lorsque celui-ci est ouvert, vous donnant un plus grand contrôle pour déclencher des actions spécifiques.

```Java
//Se concentrer ou ouvrir le composant suivant dans un formulaire
ChoiceBox université = new ChoiceBox("Université");
ChoiceBox majeur = new ChoiceBox("Majeur");
Button soumettre = new Button("Soumettre");

//... Ajouter des listes d'universités et de majeurs

université.onClose( e ->{
  majeur.focus();
});

majeur.onClose( e ->{
  soumettre.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ChoiceBox` a des méthodes qui permettent de manipuler les dimensions de la liste déroulante. La **hauteur maximale** et la **largeur minimale** de la liste déroulante peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Passer une valeur `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), comme des pixels, des dimensions de vue, ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les slots offrent des options flexibles pour améliorer la capacité d'un `ChoiceBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, une capacité de réinitialisation/effacement, des images de profil/avatar, et d'autres composants bénéfiques imbriqués dans un `ChoiceBox` pour clarifier davantage le sens voulu pour les utilisateurs.
Le `ChoiceBox` a deux slots : les slots `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Style {#styling}

<TableBuilder name="ChoiceBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, prenez en compte les meilleures pratiques suivantes :

1. **Options claires et limitées** : Gardez la liste des choix concise dans la mesure du possible et pertinente par rapport à la tâche de l'utilisateur. Un `ChoiceBox` est idéal pour présenter une liste claire d'options.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Veillez à ce que les utilisateurs puissent facilement comprendre le but de chaque choix.

3. **Sélection par défaut** : Définissez une sélection par défaut lorsque le `ChoiceBox` est affiché initialement. Cela garantit qu'une option est pré-sélectionnée, réduisant le nombre d'interactions nécessaires pour faire un choix.

4. **ChoiceBox vs. autres composants de liste** : Un `ChoiceBox` est le meilleur choix si vous devez restreindre l'entrée de l'utilisateur à un seul choix parmi une liste d'options prédéterminées. Un autre composant de liste peut être mieux si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments en même temps : [`ListBox`](./list-box.md)
    - Autoriser l'entrée personnalisée : [`ComboBox`](./combo-box.md)
