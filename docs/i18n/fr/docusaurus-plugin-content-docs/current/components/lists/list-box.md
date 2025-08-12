---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 7bd48c55ca5607255c1d6503c500a25d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

<ParentLink parent="Liste" />

Le composant `ListBox` est un élément d'interface utilisateur conçu pour afficher une liste déroulante d'objets et permet aux utilisateurs de sélectionner un ou plusieurs éléments de la liste. Les utilisateurs peuvent également interagir avec le `ListBox` à l'aide des flèches du clavier.

## Usages {#usages}

1. **Attribution des Rôles Utilisateurs** : Dans les applications avec contrôle d'accès utilisateur, les administrateurs peuvent utiliser un `ListBox` pour attribuer des rôles et des permissions aux utilisateurs. Les utilisateurs sont sélectionnés dans une liste, et les rôles ou permissions sont attribués en fonction de leur sélection. Cela garantit un accès précis et contrôlé à différentes fonctionnalités et données au sein de l'application.

2. **Attribution des Tâches de Projet** : Dans les logiciels de gestion de projet, les composants `ListBox` sont utiles pour attribuer des tâches aux membres de l'équipe. Les utilisateurs peuvent sélectionner des tâches dans une liste et les attribuer à différents membres de l'équipe. Cela simplifie la délégation des tâches et garantit que les responsabilités sont clairement définies au sein de l'équipe.

3. **Filtrage Multi-Catégorie** : Dans une application de recherche, les utilisateurs ont souvent besoin de filtrer les résultats de recherche en fonction de plusieurs critères. Un `ListBox` peut afficher diverses options de filtrage, telles que 
>- Caractéristiques des produits
>- Plages de prix
>- Marques.

  Les utilisateurs peuvent sélectionner des éléments dans chaque catégorie de filtre, leur permettant de préciser les résultats de recherche et de trouver exactement ce qu'ils recherchent.

4. **Catégorisation de Contenu** : Dans les systèmes de gestion de contenu, les composants `ListBox` aident à catégoriser des articles, des images ou des fichiers. Les utilisateurs peuvent sélectionner une ou plusieurs catégories à associer à leur contenu, facilitant ainsi l'organisation et la recherche d'éléments de contenu dans le système.

## Options de Sélection {#selection-options}

Par défaut, la liste déroulante est configurée pour permettre la sélection d'un seul élément à la fois. Cependant, le `ListBox` implémente l'interface <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>, qui peut être configurée avec une méthode intégrée permettant aux utilisateurs de sélectionner plusieurs éléments ***en utilisant la touche `Shift`*** pour une sélection d'entrée contiguë et ***la touche `Control` (Windows) ou `Command` (Mac)*** pour une sélection séparée de plusieurs éléments.

Utilisez la fonction <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> pour modifier cette propriété. Cette méthode accepte soit `SelectionMode.SINGLE`, soit `SelectionMode.MULTIPLE`.

:::info Comportement des dispositifs tactiles
Sur les dispositifs tactiles, lorsque la sélection multiple est activée, les utilisateurs peuvent sélectionner plusieurs éléments sans maintenir la touche shift.
:::

De plus, les flèches du clavier peuvent être utilisées pour naviguer dans le `ListBox`, et taper une lettre alors que le `ListBox` a le focus sélectionnera l'option qui commence par cette lettre, ou fera défiler les options commençant par cette lettre si plusieurs options existent.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Style {#styling}

<TableBuilder name="ListBox" />

## Bonnes Pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, considérez les bonnes pratiques suivantes :

1. **Prioriser la Hiérarchie de l'Information** : Lors de l'utilisation d'un `ListBox`, assurez-vous que les éléments sont organisés de manière logique et hiérarchique. Placez les options les plus importantes et les plus couramment utilisées en haut de la liste. Cela facilite la recherche des utilisateurs sans défilement excessif.

2. **Limitez la Longueur de la Liste** : Évitez de submerger les utilisateurs avec un `ListBox` excessivement long. S'il y a un grand nombre d'éléments à afficher, envisagez de mettre en œuvre la pagination, la recherche ou des options de filtrage pour aider les utilisateurs à localiser rapidement des éléments. Alternativement, vous pouvez regrouper les éléments en catégories pour réduire la longueur de la liste.

3. **Étiquettes Claires et Descriptives** : Fournissez des étiquettes claires et descriptives pour chaque élément du `ListBox`. Les utilisateurs devraient être en mesure de comprendre l'objectif de chaque option sans ambiguïté. Utilisez des étiquettes d'éléments concises et significatives.

4. **Feedback de Multi-Sélection** : Si votre `ListBox` permet des sélections multiples, fournissez des feedbacks visuels ou textuels indiquant que plusieurs éléments peuvent être sélectionnés dans la liste.

5. **Sélection par Défaut** : Envisagez de définir une sélection par défaut pour le `ListBox`, surtout si une option est plus couramment utilisée que d'autres. Cela peut simplifier l'expérience utilisateur en présélectionnant le choix le plus probable.
