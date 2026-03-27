---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: b1ed30653bdca5af11b2f138a491baef
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Le composant `ComboBox` combine une liste déroulante avec un champ de saisie, permettant aux utilisateurs de sélectionner parmi des options prédéfinies ou de taper une valeur personnalisée. Lorsque des entrées personnalisées doivent être autorisées en plus d'un ensemble d'options suggérées, il comble le vide que le `ChoiceBox` ne couvre pas.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="Liste" />

Le composant ComboBox est un élément d'entrée polyvalent qui combine les fonctionnalités d'une liste déroulante et d'un champ de saisie. Il permet aux utilisateurs de sélectionner des éléments à partir d'une liste prédéfinie ou d'entrer des valeurs personnalisées selon les besoins. Cette section explore les usages courants du composant ComboBox dans divers scénarios :

1. **Recherche et saisie de produits** : Utilisez un ComboBox pour implémenter une fonctionnalité de recherche et de saisie de produits. Les utilisateurs peuvent soit sélectionner un produit dans une liste prédéfinie, soit taper un nom de produit personnalisé. Cela est utile pour des applications comme les sites de commerce électronique où les produits sont nombreux et diversifiés.

2. **Sélection et saisie de balises** : Dans les applications impliquant le marquage de contenu, un ComboBox peut être un excellent choix. Les utilisateurs peuvent sélectionner à partir d'une liste de balises existantes ou ajouter des balises personnalisées en les tapant. Cela est utile pour organiser et catégoriser le contenu. Des exemples de telles balises incluent :
    >- Labels de projet : Dans un outil de gestion de projet, les utilisateurs peuvent sélectionner des labels ou balises (par exemple, "Urgent", "En Cours", "Terminé") pour catégoriser les tâches ou les projets, et ils peuvent créer des labels personnalisés au besoin.
    >- Ingrédients de recettes : Dans une application de cuisine ou de recettes, les utilisateurs peuvent sélectionner des ingrédients dans une liste (par exemple, "Tomates", "Oignons", "Poulet") ou ajouter leurs propres ingrédients pour des recettes personnalisées.
    >- Balises de localisation : Dans une application de cartographie ou de géomarquage, les utilisateurs peuvent choisir des balises de localisation prédéfinies (par exemple, "Plage", "Ville", "Parc") ou créer des balises personnalisées pour marquer des lieux spécifiques sur une carte.

3. **Saisie de données avec valeurs suggérées** : Dans les formulaires de saisie de données, un ComboBox peut être utilisé pour accélérer la saisie en fournissant une liste de valeurs suggérées basées sur l'entrée de l'utilisateur. Cela aide les utilisateurs à saisir des données de manière précise et efficace.

    :::tip
    Le `ComboBox` doit être utilisé lorsque les utilisateurs sont autorisés à saisir des valeurs personnalisées. Si seules des valeurs prédéfinies sont désirées, utilisez un [`ChoiceBox`](./choice-box.md) à la place.
    :::

## Valeur personnalisée {#custom-value}

Changer la propriété de valeur personnalisée permet de contrôler si un utilisateur est capable ou non de changer la valeur dans le champ de saisie du composant `ComboBox`. Si `true`, ce qui est par défaut, alors un utilisateur peut changer la valeur. Si défini sur `false`, l'utilisateur ne pourra pas changer la valeur. Cela peut être défini à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Espace réservé {#placeholder}

Un espace réservé peut être défini pour un `ComboBox`, qui s'affichera dans le champ de texte du composant lorsqu'il est vide pour inciter les utilisateurs à entrer ce qu'ils souhaitent dans le champ. Cela peut être fait à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Type de liste déroulante {#dropdown-type}

Utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> attribuera une valeur à l'attribut `type` d'un `ComboBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans la liste déroulante du `ComboBox`. Cela est utile pour le stylage, car la liste déroulante est retirée de sa position actuelle dans le DOM et déplacée à la fin du corps de la page lorsqu'elle est ouverte.

Cette séparation crée une situation où cibler directement la
liste déroulante à l'aide de CSS ou de sélecteurs de parties ombres du composant parent devient difficile, sauf si vous utilisez l'attribut de type de liste déroulante.

Dans la démo ci-dessous, le type de liste déroulante est défini et utilisé dans le fichier CSS pour sélectionner la liste déroulante et changer la couleur d'arrière-plan.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Nombre maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans la liste déroulante d'un `ComboBox` sera augmenté pour s'adapter au contenu. Cependant, en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink>, il est possible de contrôler combien d'éléments sont affichés.

:::caution
Utiliser un nombre inférieur ou égal à 0 remettra ce paramètre à zéro.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options pour un `ComboBox` peut être contrôlée par programmation avec les méthodes `open()` et `close()`. Ces méthodes vous permettent d'afficher la liste des options à sélectionner ou de la cacher si nécessaire, offrant une plus grande flexibilité dans la gestion du comportement d'un `ComboBox`.

De plus, webforJ a des écouteurs d'événements pour lorsque le `ComboBox` est fermé et lorsqu'il est ouvert, vous donnant un meilleur contrôle pour déclencher des actions spécifiques.

```Java
//Focaliser ou ouvrir le prochain composant dans un formulaire
ComboBox université = new ComboBox("Université");
ComboBox majeure = new ComboBox("Majeure");
Button soumettre = new Button("Soumettre");

//... Ajouter des listes d'universités et de majeures

université.onClose( e ->{
  majeure.open();
});

majeure.onClose( e ->{
  soumettre.focus();
});
```

## Dimensions d'ouverture {#opening-dimensions}

Le composant `ComboBox` possède des méthodes qui permettent de manipuler les dimensions de la liste déroulante. La **hauteur maximale** et la **largeur minimale** de la liste déroulante peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Passer une valeur de `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), comme des pixels, des dimensions de fenêtre ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Mise en évidence {#highlighting}

Lorsque vous travaillez avec un `ComboBox`, vous pouvez personnaliser le moment où le texte est mis en surbrillance en fonction de la manière dont le composant acquiert le focus. Cette fonctionnalité peut réduire les erreurs de saisie lorsque les utilisateurs complètent des formulaires et peut améliorer l'expérience globale de navigation. Changez le comportement de mise en évidence en utilisant la méthode `setHighlightOnFocus()` avec l'un des énumérations intégrées `HasHighlightOnFocus.Behavior` :

- `ALL`
Le contenu du composant est toujours automatiquement mis en surbrillance lorsque le composant obtient le focus.
- `FOCUS`
Le contenu du composant est automatiquement mis en surbrillance lorsque le composant obtient le focus sous contrôle de programme.
- `FOCUS_OR_KEY`
Le contenu du composant est automatiquement mis en surbrillance lorsque le composant obtient le focus sous contrôle de programme ou par tabulation.
- `FOCUS_OR_MOUSE`
Le contenu du composant est automatiquement mis en surbrillance lorsque le composant obtient le focus sous contrôle de programme ou en cliquant dessus avec la souris.
- `KEY`
Le contenu du composant est automatiquement mis en surbrillance lorsque le composant obtient le focus par tabulation.
- `KEY_MOUSE`
Le contenu du composant est automatiquement mis en surbrillance lorsque le composant obtient le focus par tabulation ou en cliquant dessus avec la souris.
- `MOUSE`
Le contenu du composant est automatiquement mis en surbrillance lorsque le composant obtient le focus en cliquant dessus avec la souris.
- `NONE`
Le contenu du composant n'est jamais automatiquement mis en surbrillance lorsque le composant obtient le focus.

:::note
Si le contenu a été mis en surbrillance lors de la perte de focus, il sera à nouveau mis en surbrillance lors du regain de focus, quelle que soit le comportement défini.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les emplacements offrent des options flexibles pour améliorer la capacité d'un `ComboBox`. Vous pouvez avoir des icônes, des étiquettes, des indicateurs de chargement, des capacités de nettoyage/réinitialisation, des photos de profil/avatar, et d'autres composants bénéfiques imbriqués dans un `ComboBox` pour clarifier davantage le sens prévu pour les utilisateurs.
Le `ComboBox` a deux emplacements : les emplacements `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après les options dans un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stylisation {#styling}

<TableBuilder name="ComboBox" />

## Bonnes pratiques {#best-practices}

Pour assurer une expérience utilisateur optimale lors de l'utilisation du composant `ComboBox`, considérez les bonnes pratiques suivantes :

1. **Précharger les valeurs courantes** : Si des éléments courants ou fréquemment utilisés existent, préchargez-les dans la liste du `ComboBox`. Cela accélère la sélection d'éléments souvent choisis et encourage la cohérence.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Veillez à ce que les utilisateurs comprennent facilement le but de chaque choix.

3. **Validation** : Mettez en œuvre la validation des entrées pour gérer les entrées personnalisées. Vérifiez la précision et la cohérence des données. Vous voudrez peut-être suggérer des corrections ou des confirmations pour les entrées ambiguës.

4. **Sélection par défaut** : Définissez une sélection par défaut, surtout s'il existe un choix courant ou recommandé. Cela améliore l'expérience utilisateur en réduisant le besoin de clics supplémentaires.

5. **ComboBox vs autres composants de liste** : Un `ComboBox` est le meilleur choix si vous avez besoin d'une seule entrée de l'utilisateur et que vous souhaitez lui fournir des choix prédéterminés ainsi que la possibilité de personnaliser son entrée. Un autre composant de liste peut être préférable si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments à la fois : [ListBox](./list-box.md)
    - Prévenir l'entrée personnalisée : [ChoiceBox](./choice-box.md)
