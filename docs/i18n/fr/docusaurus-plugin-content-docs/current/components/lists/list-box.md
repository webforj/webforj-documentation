---
sidebar_position: 15
title: ListBox
slug: listbox
_i18n_hash: 9bf0e23b101252295342c62ce6a0dee9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

Le composant `ListBox` affiche une liste défilante d'éléments qui reste visible sans avoir besoin d'ouvrir un menu déroulant. Il prend en charge la sélection unique et multiple, et fonctionne bien lorsque les utilisateurs ont besoin de voir toutes les options disponibles en une seule fois.

<!-- INTRO_END -->

## Usages {#usages}

<ParentLink parent="List" />

1. **Attribution de rôles utilisateurs** : Dans les applications avec contrôle d'accès utilisateur, les administrateurs peuvent utiliser un `ListBox` pour attribuer des rôles et des autorisations aux utilisateurs. Les utilisateurs sont sélectionnés à partir d'une liste, et les rôles ou autorisations sont attribués en fonction de leur sélection. Cela garantit un accès précis et contrôlé à différentes fonctionnalités et données au sein de l'application.

2. **Attribution de tâches de projet** : Dans les logiciels de gestion de projet, les composants `ListBox` sont utiles pour assigner des tâches aux membres de l'équipe. Les utilisateurs peuvent sélectionner des tâches à partir d'une liste et les attribuer à différents membres de l'équipe. Cela simplifie la délégation des tâches et garantit que les responsabilités sont clairement définies au sein de l'équipe.

3. **Filtrage multi-catégories** : Dans une application de recherche, les utilisateurs ont souvent besoin de filtrer les résultats de recherche en fonction de plusieurs critères. Un `ListBox` peut afficher diverses options de filtrage, telles que 
>- Caractéristiques du produit
>- Gammes de prix
>- Marques.

  Les utilisateurs peuvent sélectionner des éléments dans chaque catégorie de filtre, leur permettant d'affiner les résultats de recherche et de trouver exactement ce qu'ils recherchent.

4. **Catégorisation de contenu** : Dans les systèmes de gestion de contenu, les composants `ListBox` aident à catégoriser des articles, des images ou des fichiers. Les utilisateurs peuvent sélectionner une ou plusieurs catégories à associer à leur contenu, facilitant ainsi l'organisation et la recherche des éléments de contenu dans le système.

## Options de sélection {#selection-options}

Par défaut, la liste est configurée pour permettre la sélection d'un seul élément à la fois. Cependant, le `ListBox` implémente l'interface <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>, qui peut être configurée avec une méthode intégrée permettant aux utilisateurs de sélectionner plusieurs éléments ***en utilisant la touche `Shift`*** pour la sélection contiguë et ***la touche `Control` (Windows) ou `Command` (Mac)*** pour la sélection séparée de plusieurs éléments.

Utilisez la fonction <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> pour changer cette propriété. Cette méthode accepte soit `SelectionMode.SINGLE` soit `SelectionMode.MULTIPLE`.

:::info Comportement des appareils tactiles
Sur les appareils tactiles, lorsque la sélection multiple est activée, les utilisateurs peuvent sélectionner plusieurs éléments sans maintenir la touche shift.
:::

De plus, les touches fléchées peuvent être utilisées pour naviguer dans le `ListBox`, et taper une lettre alors que le `ListBox` a le focus sélectionnera l'option qui commence par cette lettre, ou fera défiler les options commençant par cette lettre si plusieurs options existent.

<ComponentDemo 
path='/webforj/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
height = '250px'
/>

## Style {#styling}

<TableBuilder name="ListBox" />

## Meilleures pratiques {#best-practices}

Pour garantir une expérience utilisateur optimale lors de l'utilisation du composant `ChoiceBox`, considérez les meilleures pratiques suivantes :

1. **Prioriser la hiérarchie de l'information** : Lors de l'utilisation d'un `ListBox`, assurez-vous que les éléments sont organisés dans un ordre logique et hiérarchique. Placez les options les plus importantes et les plus couramment utilisées en haut de la liste. Cela facilite la recherche d'informations pour les utilisateurs sans défilement excessif.

2. **Limiter la longueur de la liste** : Évitez de submerger les utilisateurs avec un `ListBox` excessivement long. S'il y a un grand nombre d'éléments à afficher, envisagez d'implémenter des options de pagination, de recherche ou de filtrage pour aider les utilisateurs à localiser rapidement les éléments. Alternativement, vous pouvez regrouper les éléments en catégories pour réduire la longueur de la liste.

3. **Étiquettes claires et descriptives** : Fournissez des étiquettes claires et descriptives pour chaque élément du `ListBox`. Les utilisateurs doivent pouvoir comprendre l'objectif de chaque option sans ambiguïté. Utilisez des étiquettes d'éléments concises et significatives.

4. **Retour d'information de sélection multiple** : Si votre `ListBox` permet des sélections multiples, fournissez un retour d'information visuel ou textuel indiquant que plusieurs éléments peuvent être sélectionnés à partir de la liste.

5. **Sélection par défaut** : Envisagez de définir une sélection par défaut pour le `ListBox`, surtout si une option est plus souvent utilisée que d'autres. Cela peut rationaliser l'expérience utilisateur en pré-sélectionnant le choix le plus probable.
