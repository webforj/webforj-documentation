---
sidebar_position: 15
title: ListBox
slug: listbox
description: >-
  Show a scrollable, always-visible list with the ListBox component, supporting
  single or multiple selection and keyboard navigation.
_i18n_hash: 0a9d307607ef33fdebb557f190abd7cf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-listbox" />
<DocChip chip='since' label='23.05' />
<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

Le composant `ListBox` affiche une liste déroulante d’éléments qui reste visible sans avoir à ouvrir un menu déroulant. Il prend en charge la sélection simple et multiple, et convient particulièrement aux situations où les utilisateurs doivent voir toutes les options disponibles à la fois.

<!-- INTRO_END -->

## Utilisations {#usages}

<ParentLink parent="List" />

1. **Attribution de rôles aux utilisateurs** : dans les applications qui contrôlent les accès des utilisateurs, les administrateurs peuvent utiliser un `ListBox` pour attribuer des rôles et des autorisations. Les utilisateurs sont sélectionnés dans une liste, puis les rôles ou les autorisations leur sont attribués selon leur sélection. Cela permet de contrôler précisément l’accès aux différentes fonctionnalités et données de l’application.

2. **Attribution de tâches à des projets** : dans les logiciels de gestion de projet, les composants `ListBox` sont utiles pour attribuer des tâches aux membres d’une équipe. Les utilisateurs peuvent sélectionner des tâches dans une liste et les attribuer à différents membres de l’équipe. Cela simplifie la délégation des tâches et garantit que les responsabilités sont clairement définies au sein de l’équipe.

3. **Filtrage par plusieurs catégories** : dans une application de recherche, les utilisateurs doivent souvent filtrer les résultats selon plusieurs critères. Un `ListBox` peut afficher différentes options de filtre, telles que :
>- Caractéristiques des produits
>- Gammes de prix
>- Marques.

  Les utilisateurs peuvent sélectionner des éléments dans chaque catégorie de filtre pour affiner les résultats et trouver exactement ce qu’ils recherchent.

4. **Catégorisation du contenu** : dans les systèmes de gestion de contenu, les composants `ListBox` facilitent la catégorisation des articles, des images ou des fichiers. Les utilisateurs peuvent sélectionner une ou plusieurs catégories à associer à leur contenu, ce qui simplifie son organisation et sa recherche dans le système.

## Options de sélection {#selection-options}

Par défaut, la liste permet de sélectionner un seul élément à la fois. Cependant, `ListBox` implémente l’interface <JavadocLink type="foundation" location="com/webforj/component/list/MultipleSelectableList" code='true'>MultipleSelectableList</JavadocLink>, qui peut être configurée à l’aide d’une méthode intégrée permettant aux utilisateurs de sélectionner plusieurs éléments ***avec la touche <kbd>Shift</kbd>*** pour sélectionner une suite d’éléments, et ***avec la touche <kbd>Ctrl</kbd> (Windows) ou <kbd>Cmd</kbd> (Mac)*** pour sélectionner plusieurs éléments distincts.

Utilisez la fonction <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> pour modifier cette propriété. Cette méthode accepte `SelectionMode.SINGLE` ou `SelectionMode.MULTIPLE`.

:::info Comportement sur les appareils tactiles
Sur les appareils tactiles, lorsque la sélection multiple est activée, les utilisateurs peuvent sélectionner plusieurs éléments sans maintenir la touche Shift enfoncée.
:::

Les touches fléchées permettent également de parcourir le `ListBox`. De plus, lorsqu’un `ListBox` a le focus, la saisie d’une lettre sélectionne l’option qui commence par cette lettre. S’il existe plusieurs options commençant par cette lettre, la sélection passe de l’une à l’autre.

<ComponentDemo
path='/webforj/listboxmultipleselection'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java']}
height='250px'
/>

## Style {#styling}

<TableBuilder name="ListBox" />

## Bonnes pratiques {#best-practices}

Pour offrir une expérience utilisateur optimale avec le composant `ChoiceBox`, tenez compte des bonnes pratiques suivantes :

1. **Privilégiez une hiérarchie claire de l’information** : lorsque vous utilisez un `ListBox`, veillez à organiser les éléments selon un ordre logique et hiérarchique. Placez les options les plus importantes et les plus utilisées en haut de la liste. Les utilisateurs trouveront ainsi plus facilement ce qu’ils cherchent, sans avoir à faire défiler la liste de manière excessive.

2. **Limitez la longueur de la liste** : évitez de submerger les utilisateurs avec un `ListBox` trop long. Si la liste contient un grand nombre d’éléments, envisagez d’ajouter une pagination, une recherche ou des options de filtrage pour aider les utilisateurs à trouver rapidement ce qu’ils cherchent. Vous pouvez également regrouper les éléments par catégories pour raccourcir la liste.

3. **Utilisez des libellés clairs et descriptifs** : donnez à chaque élément du `ListBox` un libellé clair et descriptif. Les utilisateurs doivent pouvoir comprendre sans ambiguïté la fonction de chaque option. Privilégiez des libellés concis et pertinents.

4. **Indiquez les sélections multiples** : si votre `ListBox` autorise la sélection de plusieurs éléments, fournissez un retour visuel ou textuel indiquant que plusieurs éléments de la liste peuvent être sélectionnés.

5. **Définissez une sélection par défaut** : envisagez de définir une sélection par défaut pour le `ListBox`, surtout si une option est plus couramment utilisée que les autres. La présélection de l’option la plus probable peut simplifier l’expérience utilisateur.
