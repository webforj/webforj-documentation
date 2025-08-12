---
sidebar_position: 10
title: ComboBox
slug: combobox
_i18n_hash: ec3f88523477bf08e92fe9153b014b91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-combobox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" top='true'/>

<ParentLink parent="Liste" />

Le composant `ComboBox` est un élément d'interface utilisateur conçu pour présenter aux utilisateurs une liste d'options ou de choix, ainsi qu'un champ pour saisir leurs propres valeurs personnalisées. Les utilisateurs peuvent sélectionner une seule option dans cette liste, généralement en cliquant sur le `ComboBox`, ce qui déclenche l'affichage d'une liste déroulante contenant les choix disponibles, ou saisir une valeur personnalisée. Les utilisateurs peuvent également interagir avec le `ComboBox` à l'aide des flèches du clavier. Lorsque l'utilisateur effectue une sélection, l'option choisie est ensuite affichée dans le `ComboBox`. 

## Usages {#usages}

Le composant ComboBox est un élément de saisie polyvalent qui combine les caractéristiques d'une liste déroulante et d'un champ de saisie de texte. Il permet aux utilisateurs de sélectionner des éléments à partir d'une liste prédéfinie ou d'entrer des valeurs personnalisées au besoin. Cette section explore les usages courants du composant ComboBox dans divers scénarios :

1. **Recherche et saisie de produits** : Utilisez un ComboBox pour mettre en œuvre une fonction de recherche et de saisie de produits. Les utilisateurs peuvent soit sélectionner un produit dans une liste prédéfinie, soit taper un nom de produit personnalisé. Cela est utile pour des applications comme les sites de commerce électronique où les produits sont vastes et diversifiés.

2. **Sélection et saisie de tags** : Dans les applications impliquant le marquage de contenu, un ComboBox peut être un excellent choix. Les utilisateurs peuvent sélectionner parmi une liste de tags existants ou ajouter des tags personnalisés en les tapant. Cela est utile pour organiser et catégoriser le contenu. Des exemples de tels tags comprennent :
    >- Étiquettes de projet : Dans un outil de gestion de projet, les utilisateurs peuvent sélectionner des étiquettes ou des tags (par exemple, "Urgent", "En cours", "Terminé") pour catégoriser les tâches ou les projets, et ils peuvent créer des étiquettes personnalisées si nécessaire.
    >- Ingrédients de recette : Dans une application de cuisine ou de recettes, les utilisateurs peuvent sélectionner des ingrédients dans une liste (par exemple, "Tomates", "Oignons", "Poulet") ou ajouter leurs propres ingrédients pour des recettes personnalisées.
    >- Tags de localisation : Dans une application de cartographie ou de géolocalisation, les utilisateurs peuvent choisir des tags de localisation prédéfinis (par exemple, "Plage", "Ville", "Parc") ou créer des tags personnalisés pour marquer des endroits spécifiques sur une carte.

3. **Saisie de données avec valeurs suggérées** : Dans les formulaires de saisie de données, un ComboBox peut être utilisé pour accélérer la saisie en fournissant une liste de valeurs suggérées basées sur l'entrée de l'utilisateur. Cela aide les utilisateurs à saisir des données de manière précise et efficace.

    :::tip
    Le `ComboBox` doit être utilisé lorsque les utilisateurs sont autorisés à entrer des valeurs personnalisées. Si seules des valeurs prédéfinies sont souhaitées, utilisez un [`ChoiceBox`](./choice-box.md) à la place.
    :::

## Valeur personnalisée {#custom-value}

Changer la propriété de valeur personnalisée permet de contrôler si un utilisateur est capable ou non de changer la valeur dans le champ de saisie du composant `ComboBox`. Si `true`, ce qui est la valeur par défaut, alors un utilisateur peut changer la valeur. Si défini sur `false`, l'utilisateur ne pourra pas changer la valeur. Cela peut être défini à l'aide de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setAllowCustomValue(boolean)'>setAllowCustomValue()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxcustomvalue?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxCustomValueView.java'
height = '200px'
/>

## Espace réservé {#placeholder}

Un espace réservé peut être défini pour un `ComboBox`, qui s'affichera dans le champ de texte du composant lorsqu'il est vide pour inciter les utilisateurs à entrer la valeur souhaitée dans le champ. Cela peut être fait en utilisant la méthode <JavadocLink type="foundation" location="com/webforj/component/list/ComboBox" code='true' suffix='#setPlaceholder(java.lang.String)'>setPlaceholder()</JavadocLink>.

<ComponentDemo 
path='/webforj/comboboxplaceholder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxPlaceholderView.java'
height = '200px'
/>

## Type de liste déroulante {#dropdown-type}

Utiliser la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setDropdownType(java.lang.String)'>setDropdownType()</JavadocLink> assignera une valeur à l'attribut `type` d'un `ComboBox`, et une valeur correspondante pour l'attribut `data-dropdown-for` dans la liste déroulante du `ComboBox`. Cela est utile pour le stylisme, car la liste déroulante est détachée de sa position actuelle dans le DOM et relocalisée à la fin du corps de la page lorsqu'elle est ouverte.

Ce détachement crée une situation où cibler directement la liste déroulante à l'aide de sélecteurs CSS ou de parties ombragées du composant parent devient difficile, à moins que vous n'utilisiez l'attribut du type de liste déroulante.

Dans la démo ci-dessous, le type de liste déroulante est défini et utilisé dans le fichier CSS pour sélectionner la liste déroulante et changer la couleur de fond.

<ComponentDemo 
path='/webforj/comboboxdropdowntype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxDropdownTypeView.java'
cssURL='/css/lists/combobox/comboBoxDropDownType.css'
height='250px'
/>

## Compte maximum de lignes {#max-row-count}

Par défaut, le nombre de lignes affichées dans la liste déroulante d'un `ComboBox` sera augmenté pour s'adapter au contenu. Cependant, l'utilisation de la méthode <JavadocLink type="foundation" location="com/webforj/component/list/DwcSelectDropdown" code='true' suffix='#setMaxRowCount(int)'>setMaxRowCount()</JavadocLink> permet de contrôler combien d'éléments sont affichés.

:::caution
Utiliser un nombre inférieur ou égal à 0 annulera cette propriété.
:::

<ComponentDemo 
path='/webforj/comboboxmaxrow?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/combobox/ComboBoxMaxRowView.java'
height='450px'
/>

## Ouverture et fermeture {#opening-and-closing}

La visibilité des options d'un `ComboBox` peut être contrôlée par programme avec les méthodes `open()` et `close()`. Ces méthodes vous permettent d'afficher la liste des options pour sélection ou de la masquer selon vos besoins, offrant ainsi plus de flexibilité dans la gestion du comportement d'un `ComboBox`.

De plus, webforJ dispose d'écouteurs d'événements pour lorsque le `ComboBox` est fermé et lorsqu'il est ouvert, vous donnant plus de contrôle pour déclencher des actions spécifiques.

```Java
//Focus ou ouvrez le composant suivant dans un formulaire
ComboBox university = new ComboBox("Université");
ComboBox major = new ComboBox("Majeure");
Button submit = new Button("Soumettre");

//... Ajoutez des listes d'universités et de majeures

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
Passer une valeur de type `String` à l'une de ces méthodes permettra d'appliquer [n'importe quelle unité CSS valide](https://developer.mozilla.org/en-US/docs/Learn/CSS/Building_blocks/Values_and_units), comme les pixels, les dimensions de la fenêtre, ou d'autres règles valides. Passer un int définira la valeur passé en pixels.
:::

## Surlignage {#highlighting}

Lors de l'utilisation d'un `ComboBox`, vous pouvez personnaliser le moment où le texte est surligné en fonction de la manière dont le composant acquiert le focus. Cette fonctionnalité peut réduire les erreurs de saisie lorsque les utilisateurs remplissent des formulaires et peut améliorer l'expérience de navigation globale. Changez le comportement de surlignage en utilisant la méthode `setHighlightOnFocus()` avec l'un des énumérées `HasHighlightOnFocus.Behavior` intégrées :

- `TOUT`
Le contenu du composant est toujours automatiquement surligné lorsque le composant reçoit le focus.
- `FOCUS`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus sous contrôle programmatique.
- `FOCUS_OR_KEY`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus sous contrôle programmatique ou en y accédant par tabulation.
- `FOCUS_OR_MOUSE`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus sous contrôle programmatique ou en y accédant par clic de souris.
- `KEY`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus en y accédant par tabulation.
- `KEY_MOUSE`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus en y accédant par tabulation ou en y accédant par clic de souris.
- `MOUSE`
Le contenu du composant est automatiquement surligné lorsque le composant reçoit le focus en y accédant par clic de souris.
- `AUCUN`
Le contenu du composant n'est jamais automatiquement surligné lorsque le composant reçoit le focus.

:::note
Si le contenu était surligné lors de la perte de focus, il sera à nouveau surligné lors du regain de focus, quel que soit le comportement défini.
:::

## Préfixe et suffixe {#prefix-and-suffix}

Les emplacements offrent des options flexibles pour améliorer la capacité d'un `ComboBox`. Vous pouvez avoir des icônes, des étiquettes, des spinners de chargement, des capacités de nettoyage/réinitialisation, des photos de profil/avatar et d'autres composants bénéfiques imbriqués dans un `ComboBox` pour clarifier davantage le sens voulu pour les utilisateurs. Le `ComboBox` dispose de deux emplacements : le préfixe et le suffixe. Utilisez les méthodes `setPrefixComponent()` et `setSuffixComponent()` pour insérer divers composants avant et après les options dans un `ComboBox`.

```java
ComboBox comboBox = new ComboBox());
  comboBox.setPrefixComponent(TablerIcon.create("box"));
  comboBox.setSuffixComponent(TablerIcon.create("box"));
```

## Stylisation {#styling}

<TableBuilder name="ComboBox" />

## Meilleures pratiques {#best-practices}

Pour assurer une expérience utilisateur optimale lors de l'utilisation du composant `ComboBox`, envisagez les meilleures pratiques suivantes :

1. **Précharger des valeurs courantes** : Si des éléments communs ou fréquemment utilisés existent, préchargez-les dans la liste du `ComboBox`. Cela accélère la sélection des éléments souvent choisis et encourage la cohérence.

2. **Étiquettes conviviales** : Assurez-vous que les étiquettes affichées pour chaque option sont conviviales et explicites. Assurez-vous que les utilisateurs peuvent facilement comprendre l'objectif de chaque choix.

3. **Validation** : Mettez en œuvre une validation des entrées pour gérer les entrées personnalisées. Vérifiez l'exactitude et la cohérence des données. Vous préférerez peut-être suggérer des corrections ou des confirmations pour des saisies ambiguës.

4. **Sélection par défaut** : Définissez une sélection par défaut, surtout s'il existe un choix commun ou recommandé. Cela améliore l'expérience utilisateur en réduisant le besoin de clics supplémentaires.

5. **ComboBox vs. autres composants de liste** : Un `ComboBox` est le meilleur choix si vous avez besoin d'une seule entrée de l'utilisateur et que vous souhaitez leur fournir des choix prédéterminés et la possibilité de personnaliser leur saisie. Un autre composant de liste peut être préférable si vous avez besoin des comportements suivants :
    - Sélection multiple et affichage de tous les éléments en même temps : [ListBox](./list-box.md)
    - Empêcher les entrées personnalisées : [ChoiceBox](./choice-box.md)
