---
sidebar_position: 30
title: Modification et actualisation
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 84dec48293e6fa794713881310b6f303
---
La modification des données dans le `Table` s’effectue en interagissant avec le `Repository` qui contient les données du `Table`. Le `Repository` sert de passerelle entre le `Table` et le jeu de données sous-jacent, en proposant des méthodes pour récupérer, modifier et actualiser les données. Voici un exemple qui permet de modifier le « Title » d’une ligne donnée.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Dans l’exemple ci-dessus, la classe `TitleEditorComponent` permet de modifier le champ « Title » d’un `MusicRecord` sélectionné. Le composant comprend un champ de saisie pour le nouveau titre, ainsi que des boutons « Save » et « Cancel ».

Pour relier le composant de modification au `Table`, un bouton « Edit » est ajouté au `Table` à l’aide d’un `VoidElementRenderer`. Lorsque l’utilisateur clique sur ce bouton, la méthode `edit()` de `TitleEditorComponent` est appelée, ce qui lui permet de modifier la valeur de « Title ».

## Méthode commit {#commit-method}

Lorsque l’utilisateur modifie le titre et clique sur le bouton « Save », `TitleEditorComponent` appelle la méthode `save()`. Celle-ci met à jour le titre du `MusicRecord` correspondant et déclenche un événement personnalisé `SaveEvent`.

La mise à jour des données du référentiel en temps réel s’effectue à l’aide de la méthode `commit()`. Cette méthode est utilisée dans l’écouteur d’événement `onSave`, afin que les modifications apportées dans le composant de modification soient répercutées dans le jeu de données sous-jacent.

La méthode `commit()` est appelée pour informer tous les composants concernés que les données ont été modifiées. Le `Table` intercepte l’événement `RepositoryCommitEvent` et se met à jour en fonction des nouvelles données.

:::tip Mise à jour et création d’entrées
L’appel de la méthode `commit()` met à jour les entrées existantes et **insère également les nouvelles entrées créées**.
:::
