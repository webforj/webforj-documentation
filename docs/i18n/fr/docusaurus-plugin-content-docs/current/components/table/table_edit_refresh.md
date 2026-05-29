---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 86d395b36fe0cdec90b5a29497a8b0d3
---
Modifier les données dans le `Table` se fait via l'interaction avec le `Repository` contenant les données pour le `Table`. Le `Repository` sert de pont entre le `Table` et le jeu de données sous-jacent, offrant des méthodes pour la récupération, la modification et le rafraîchissement des données. Ci-dessous, un exemple qui implémente le comportement pour éditer le "Titre" d'une ligne souhaitée basée.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

Dans l'exemple ci-dessus, la classe `TitleEditorComponent` facilite l'édition du champ "Titre" pour un `MusicRecord` sélectionné. Le composant inclut un champ de saisie pour le nouveau titre, ainsi que des boutons "Enregistrer" et "Annuler".

Pour connecter le composant d'édition avec le `Table`, un bouton "Éditer" est ajouté au `Table` via un `VoidElementRenderer`. Cliquer sur ce bouton déclenche la méthode `edit()` du `TitleEditorComponent`, permettant aux utilisateurs de modifier la valeur "Titre".

## Méthode commit {#commit-method}

Une fois que l'utilisateur modifie le titre et clique sur le bouton "Enregistrer", le `TitleEditorComponent` déclenche la méthode `save()`. Cette méthode met à jour le titre du `MusicRecord` correspondant et déclenche un `SaveEvent` personnalisé.

La mise à jour en temps réel des données dans le repository est réalisée par la méthode `commit()`. Cette méthode est employée dans le listener d'événement `onSave`, garantissant que les modifications apportées via le composant d'édition sont reflétées dans le jeu de données sous-jacent.

La méthode `commit()` est appelée pour notifier tous les composants intéressés que les données ont été modifiées. Le `Table` intercepte le `RepositoryCommitEvent` et se met à jour en fonction des nouvelles données.

:::tip Mise à jour et création d'entrées
Appeler la méthode `commit()` met à jour les entrées existantes et **insère toute nouvelle entrée qui a été créée**.
:::
