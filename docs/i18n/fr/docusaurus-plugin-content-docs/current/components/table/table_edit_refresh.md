---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 6ecf362668be7d0633c3c13e7da068ec
---
Éditer des données au sein de la `Table` fonctionne via l'interaction avec le `Repository` contenant les données pour la `Table`. Le `Repository` sert de pont entre la `Table` et le dataset sous-jacent, offrant des méthodes pour la récupération, la modification et le rafraîchissement des données. Ci-dessous un exemple qui implémente un comportement pour éditer le "Titre" d'une ligne désirée.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

Dans l'exemple ci-dessus, la classe `TitleEditorComponent` facilite l'édition du champ "Titre" pour un `MusicRecord` sélectionné. Le composant inclut un champ de saisie pour le nouveau titre, ainsi que des boutons "Sauvegarder" et "Annuler".

Pour connecter le composant d'édition avec la `Table`, un bouton "Modifier" est ajouté à la `Table` via un `VoidElementRenderer`. Cliquer sur ce bouton déclenche la méthode `edit()` de la `TitleEditorComponent`, permettant aux utilisateurs de modifier la valeur "Titre".

## Méthode de validation {#commit-method}

Une fois l'utilisateur a modifié le titre et cliqué sur le bouton "Sauvegarder", la `TitleEditorComponent` déclenche la méthode `save()`. Cette méthode met à jour le titre du `MusicRecord` correspondant et émet un `SaveEvent` personnalisé.

La mise à jour en temps réel des données dans le repository est réalisée via la méthode `commit()`. Cette méthode est employée dans l'écouteur d'événements `onSave`, assurant que les modifications apportées par le biais du composant d'édition se répercutent dans le dataset sous-jacent.

La méthode `commit()` est appelée pour notifier tous les composants intéressés que les données ont été modifiées. La `Table` capture le `RepositoryCommitEvent` et se met à jour en fonction des nouvelles données.

:::tip Mise à jour et création d'entrées
Appeler la méthode `commit()` met à la fois à jour les entrées existantes et **insère toutes les nouvelles entrées qui ont été créées**.
:::
