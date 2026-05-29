---
sidebar_position: 5
title: ChoiceBox
slug: choicebox
_i18n_hash: 6e04ceea1fadc5f159b8d4dd9645e014
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-choicebox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ChoiceBox" top='true'/>

Le composant `ChoiceBox` présente une liste déroulante à partir de laquelle les utilisateurs sélectionnent une seule option. Lorsqu'une sélection est faite, la valeur choisie est affichée dans le bouton. Il est bien adapté lorsque les utilisateurs doivent choisir parmi un ensemble fixe de choix prédéfinis, et les touches fléchées peuvent être utilisées pour naviguer dans la liste.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Les composants `ChoiceBox` sont utilisés à diverses fins, comme sélectionner des éléments dans un menu, choisir parmi une liste de catégories, ou choisir des options parmi des ensembles prédéfinis. Ils offrent un moyen organisé et visuellement agréable pour les utilisateurs de faire des sélections, en particulier lorsqu'il existe plusieurs options disponibles. Les utilisations courantes incluent :

1. **Sélection d'options par l'utilisateur** : Le principal objectif d'un `ChoiceBox` est de permettre aux utilisateurs de sélectionner une seule option dans une liste. Cela est précieux dans les applications qui nécessitent que les utilisateurs fassent des choix, tels que :
    - Choisir parmi une liste de catégories
    - Prendre des options parmi des ensembles prédéfinis

2. **Saisies de formulaire** : Lors de la conception de formulaires nécessitant que les utilisateurs saisissent des options spécifiques, le `ChoiceBox` simplifie le processus de sélection. Que ce soit pour sélectionner un pays, un état ou toute autre option d'une liste prédéfinie, le `ChoiceBox` rationalise le processus de saisie.

3. **Filtrage et tri** : Le `ChoiceBox` peut être utilisé pour des tâches de filtrage et de tri dans les applications. Les utilisateurs peuvent choisir des critères de filtrage ou des préférences de tri dans une liste, facilitant l'organisation et la navigation des données.

4. **Configuration et paramètres** : Lorsque votre application inclut des paramètres ou des options de configuration, le `ChoiceBox` fournit un moyen intuitif pour les utilisateurs d'ajuster leurs préférences. Les utilisateurs peuvent choisir des paramètres dans une liste, ce qui facilite l'adaptation de l'application à leurs besoins.

:::tip
Le `ChoiceBox` est conçu pour être utilisé lorsqu'un nombre prédéfini d'options est disponible, et les options personnalisées ne doivent pas être autorisées ou incluses. Si vous souhaitez permettre aux utilisateurs de saisir des valeurs personnalisées, utilisez un [`ComboBox`](./combo-box.md) à la place.
:::

## Type de dropdown {#dropdown-type}

L'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> attribuera une valeur à l'attribut `type` d'un `ChoiceBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans le dropdown du `ChoiceBox`. Cela est utile pour le style, car le dropdown est déplacé de sa position actuelle dans le DOM et relocalisé à la fin du corps de la page lorsqu'il est ouvert.

<!-- ![example type](/img/components/_images/choicebox/type.png)
![example type](/img/components/_images/choicebox/type_zoomed.png) -->

Cette détachement crée une situation où cibler directement le dropdown à l'aide de sélecteurs CSS ou de parties d'ombre du composant parent devient difficile, à moins que vous n'utilisiez l'attribut de type dropdown.

Dans la démo ci-dessous, le type de dropdown est défini et utilisé dans le fichier CSS pour sélectionner le dropdown et changer la couleur de fond.

<ComponentDemo
path='/webforj/choiceboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Compte maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans le dropdown d'un `ChoiceBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler combien d'articles sont affichés.

:::tip
Utiliser un nombre inférieur ou égal à 0 entraînera la désactivation de cette propriété.
:::

<ComponentDemo
path='/webforj/choiceboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/choicebox/ChoiceboxMaxRowView.java']}
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ChoiceBox` peut être contrôlée par programme avec les méthodes `open()` et `close()`. Ces méthodes vous permettent d'afficher la liste des options pour sélection ou de la masquer selon vos besoins, offrant une plus grande flexibilité dans la gestion du comportement d'un `ChoiceBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsque le `ChoiceBox` est fermé et lorsqu'il est ouvert, vous donnant plus de contrôle pour déclencher des actions spécifiques.

```Java
//Focaliser ou ouvrir le composant suivant dans un formulaire
ChoiceBox university = new ChoiceBox("Université");
ChoiceBox major = new ChoiceBox("Majeure");
Button submit = new Button("Soumettre");

//... Ajouter des listes d'universités et de majeures

university.onClose(e -> {
  major.focus();
});

major.onClose(e -> {
  submit.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ChoiceBox` dispose de méthodes qui permettent de manipuler les dimensions du dropdown. La **hauteur maximale** et la **largeur minimale** du dropdown peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Passer une valeur `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), comme des pixels, des dimensions de viewport, ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les emplacements offrent des options flexibles pour améliorer la capacité d'un `ChoiceBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, des capacités de nettoyage/réinitialisation, des images de profil/avatar, et d'autres composants bénéfiques imbriqués dans un `ChoiceBox` pour clarifier encore mieux la signification souhaitée pour les utilisateurs. Le `ChoiceBox` a deux emplacements : les emplacements `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après l'option affichée dans un `ChoiceBox`.

```java
ComboBox choiceBox = new ChoiceBox());
  choiceBox.setPrefixComponent(TablerIcon.create("box"));
  choiceBox.setSuffixComponent(TablerIcon.create("box"));
```

## Style {#styling}

<TableBuilder name="ChoiceBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, considérez les meilleures pratiques suivantes :

1. **Options claires et limitées** : Gardez la liste des choix concise dans la mesure du possible et pertinente pour la tâche de l'utilisateur. Un `ChoiceBox` est idéal pour présenter une liste claire d'options.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Veillez à ce que les utilisateurs puissent facilement comprendre l'objectif de chaque choix.

3. **Sélection par défaut** : Définissez une sélection par défaut lorsque le `ChoiceBox` est affiché pour la première fois. Cela garantit une option présélectionnée, réduisant le nombre d'interactions nécessaires pour faire un choix.

4. **ChoiceBox contre d'autres composants de liste** : Un `ChoiceBox` est le meilleur choix si vous devez restreindre la saisie utilisateur à un seul choix parmi une liste d'options prédéterminées. Un autre composant de liste peut être préférable si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments à la fois : [`ListBox`](./list-box.md)
    - Permettre une saisie personnalisée : [`ComboBox`](./combo-box.md)
