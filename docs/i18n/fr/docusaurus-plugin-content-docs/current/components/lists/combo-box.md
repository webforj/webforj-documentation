---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: 199696bfbf6489520cec364f16226489
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

Le composant `ComboBox` combine une liste déroulante avec un champ de saisie, de sorte que les utilisateurs peuvent soit sélectionner parmi des options prédéfinies, soit taper une valeur personnalisée. Lorsque des entrées personnalisées doivent être autorisées aux côtés d'un ensemble d'options suggérées, il comble le vide que `ChoiceBox` ne couvre pas.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

Le composant ComboBox est un élément d'entrée polyvalent qui combine les fonctionnalités d'une liste déroulante et d'un champ de saisie. Il permet aux utilisateurs de sélectionner des éléments à partir d'une liste prédéfinie ou d'entrer des valeurs personnalisées si nécessaire. Cette section explore les usages courants du composant ComboBox dans divers scénarios :

1. **Recherche et Saisie de Produits** : Utilisez un ComboBox pour implémenter une fonctionnalité de recherche et de saisie de produit. Les utilisateurs peuvent soit sélectionner un produit dans une liste prédéfinie, soit taper un nom de produit personnalisé. Cela est utile pour des applications telles que les sites de commerce électronique où les produits sont vastes et diversifiés.

2. **Sélection et Saisie de Tags** : Dans les applications impliquant le marquage de contenu, un ComboBox peut constituer un excellent choix. Les utilisateurs peuvent sélectionner parmi une liste de tags existants ou ajouter des tags personnalisés en les tapant. Cela est utile pour organiser et catégoriser le contenu. Des exemples de tels tags incluent :
    >- Étiquettes de Projet : Dans un outil de gestion de projet, les utilisateurs peuvent sélectionner des étiquettes ou des tags (par exemple, "Urgent", "En Cours", "Terminé") pour catégoriser des tâches ou des projets, et ils peuvent créer des étiquettes personnalisées selon leurs besoins.
    >- Ingrédients de Recette : Dans une application de cuisine ou de recette, les utilisateurs peuvent sélectionner des ingrédients à partir d'une liste (par exemple, "Tomates", "Oignons", "Poulet") ou ajouter leurs propres ingrédients pour des recettes personnalisées.
    >- Tags de Localisation : Dans une application de cartographie ou de géolocalisation, les utilisateurs peuvent choisir des tags de localisation prédéfinis (par exemple, "Plage", "Ville", "Parc") ou créer des tags personnalisés pour marquer des endroits spécifiques sur une carte.

3. **Saisie de Données avec Valeurs Suggérées** : Dans les formulaires de saisie de données, un ComboBox peut être utilisé pour accélérer l'entrée en fournissant une liste de valeurs suggérées en fonction de l'entrée de l'utilisateur. Cela aide les utilisateurs à saisir des données avec précision et efficacité.

    :::tip
    Le `ComboBox` doit être utilisé lorsque les utilisateurs sont autorisés à entrer des valeurs personnalisées. Si seules des valeurs prédéfinies sont souhaitées, utilisez un [`ChoiceBox`](./choice-box.md) à la place.
    :::

## Valeur personnalisée {#custom-value}

Changer la propriété de valeur personnalisée permet de contrôler si un utilisateur est capable ou non de modifier la valeur dans le champ de saisie du composant `ComboBox`. Si `true`, ce qui est par défaut, alors un utilisateur peut changer la valeur. Si défini sur `false`, l'utilisateur ne pourra pas changer la valeur. Cela peut être défini à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxcustomvalue'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java']}
height='200px'
/>

## Espace réservé {#placeholder}

Un espace réservé peut être défini pour un `ComboBox`, qui s'affichera dans le champ de texte du composant lorsque celui-ci est vide pour inviter les utilisateurs à entrer la valeur souhaitée dans le champ. Cela peut être fait à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo
path='/webforj/comboboxplaceholder'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java']}
height='200px'
/>

## Type de liste déroulante {#dropdown-type}

L'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> permettra d'assigner une valeur à l'attribut `type` d'un `ComboBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans la liste déroulante du `ComboBox`. Cela est utile pour le style, car la liste déroulante est déplacée de sa position actuelle dans le DOM et relocalisée à la fin du corps de la page lorsqu'elle est ouverte.

Cette séparation crée une situation où cibler directement la liste déroulante à l'aide de sélecteurs CSS ou de parties d'ombre du composant parent devient difficile, sauf si vous utilisez l'attribut de type de liste déroulante.

Dans la démo ci-dessous, le type de liste déroulante est défini et utilisé dans le fichier CSS pour sélectionner la liste déroulante et changer la couleur de fond.

<ComponentDemo
path='/webforj/comboboxdropdowntype'
files={[
  'src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java',
  'src/main/resources/static/css/lists/combobox/comboBoxDropDownType.css',
]}
height='250px'
/>

## Nombre maximal de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans la liste déroulante d'un `ComboBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler le nombre d'éléments affichés.

:::caution
L'utilisation d'un nombre inférieur ou égal à 0 entraînera la désactivation de cette propriété.
:::

<ComponentDemo
path='/webforj/comboboxmaxrow'
files={['src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java']}
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options d'un `ComboBox` peut être contrôlée programmétiquement avec les méthodes `open()` et `close()`. Ces méthodes permettent d'afficher la liste des options pour sélection ou de la cacher selon les besoins, offrant ainsi une plus grande flexibilité dans la gestion du comportement d'un `ComboBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsque le `ComboBox` est fermé et lorsqu'il est ouvert, vous donnant plus de contrôle pour déclencher des actions spécifiques.

```Java
//Se concentrer ou ouvrir le composant suivant dans un formulaire
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

Le composant `ComboBox` dispose de méthodes qui permettent de manipuler les dimensions de la liste déroulante. La **hauteur maximale** et la **largeur minimale** de la liste déroulante peuvent être définies à l'aide des méthodes <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenHeight(int)'>setOpenHeight()</JavadocLink> et <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setOpenWidth(int)'>setOpenWidth()</JavadocLink>, respectivement.

:::tip
Passer une valeur de `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), telle que des pixels, des dimensions de viewport ou d'autres règles valides. Passer un `int` définira la valeur passée en pixels.
:::

## Surlignement {#highlighting}

Lorsque vous travaillez avec un `ComboBox`, vous pouvez personnaliser le moment où le texte est surligné en fonction de la manière dont le composant obtient le focus. Cette fonction peut réduire les erreurs de saisie lorsque les utilisateurs remplissent des formulaires et peut améliorer l'expérience de navigation globale. Modifiez le comportement de surlignement en utilisant la méthode `setHighlightOnFocus()` avec l'un des énumérateurs `HasHighlightOnFocus.Behavior` intégrés :

- `ALL`
Les contenus du composant sont toujours automatiquement surlignés lorsque le composant obtient le focus.
- `FOCUS`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus sous contrôle programme.
- `FOCUS_OR_KEY`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus sous contrôle programme ou en utilisant la touche Tab pour y accéder.
- `FOCUS_OR_MOUSE`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus sous contrôle programme ou en cliquant dessus avec la souris.
- `KEY`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus en utilisant la touche Tab.
- `KEY_MOUSE`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus soit par tabulation, soit par clic de souris.
- `MOUSE`
Les contenus du composant sont automatiquement surlignés lorsque le composant obtient le focus en cliquant dessus avec la souris.
- `NONE`
Les contenus du composant ne sont jamais automatiquement surlignés lorsque le composant obtient le focus.

:::note
Si le contenu était surligné lors de la perte de focus, il sera de nouveau surligné lors du regain de focus, peu importe le comportement défini.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les slots offrent des options flexibles pour améliorer la capacité d'un `ComboBox`. Vous pouvez avoir des icônes, des étiquettes, des indicateurs de chargement, des capacités de réinitialisation/effacement, des photos de profil/avatar et d'autres composants bénéfiques imbriqués dans un `ComboBox` pour clarifier davantage la signification destinée aux utilisateurs. Le `ComboBox` dispose de deux slots : les slots `prefix` et `suffix`. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après les options dans un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Style {#styling}

<TableBuilder name="ComboBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ComboBox`, considérez les meilleures pratiques suivantes :

1. **Précharger des valeurs courantes** : Si des éléments sont courants ou souvent utilisés, préchargez-les dans la liste du `ComboBox`. Cela accélère la sélection pour des éléments couramment choisis et encourage la cohérence.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Assurez-vous que les utilisateurs peuvent facilement comprendre l'objectif de chaque choix.

3. **Validation** : Mettez en œuvre une validation des entrées pour gérer les entrées personnalisées. Vérifiez l'exactitude et la cohérence des données. Vous pourriez vouloir suggérer des corrections ou des confirmations pour des entrées ambiguës.

4. **Sélection par défaut** : Définissez une sélection par défaut, en particulier s'il existe un choix courant ou recommandé. Cela améliore l'expérience utilisateur en réduisant le besoin de clics supplémentaires.

5. **ComboBox vs Autres composants de liste** : Un `ComboBox` est le meilleur choix si vous avez besoin d'une seule entrée de l'utilisateur et que vous souhaitez lui fournir des choix prédéterminés ainsi que la possibilité de personnaliser son entrée. Un autre composant de liste peut être meilleur si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments en même temps : [ListBox](./list-box.md)
    - Prévenir l'entrée personnalisée : [ChoiceBox](./choice-box.md)
