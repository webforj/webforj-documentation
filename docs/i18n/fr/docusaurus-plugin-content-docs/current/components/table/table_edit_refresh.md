---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 39816123675d62a6dda185187e8d13e2
---
Modifier des données dans le `Table` fonctionne via l'interaction avec le `Repository` contenant les données pour le `Table`. Le `Repository` sert de pont entre le `Table` et le jeu de données sous-jacent, offrant des méthodes pour la récupération, la modification et le rafraîchissement des données. Voici un exemple qui implémente le comportement permettant de modifier le "Titre" d'une ligne désirée.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Dans l'exemple ci-dessus, la classe `TitleEditorComponent` facilite l'édition du champ "Titre" pour un `MusicRecord` sélectionné. Le composant comprend un champ de saisie pour le nouveau titre, ainsi que des boutons "Enregistrer" et "Annuler".

Pour connecter le composant d'édition avec le `Table`, un bouton "Éditer" est ajouté au `Table` via un `VoidElementRenderer`. Un clic sur ce bouton déclenche la méthode `edit()` du `TitleEditorComponent`, permettant aux utilisateurs de modifier le "Titre".

## Méthode de validation {#commit-method}

Une fois que l'utilisateur modifie le titre et clique sur le bouton "Enregistrer", le `TitleEditorComponent` déclenche la méthode `save()`. Cette méthode met à jour le titre du `MusicRecord` correspondant et déclenche un `SaveEvent` personnalisé.

La mise à jour en temps réel des données dans le repository est réalisée par la méthode `commit()`. Cette méthode est utilisée dans l'écouteur d'événements `onSave`, garantissant que les modifications apportées par le biais du composant d'édition se reflètent dans le jeu de données sous-jacent.

La méthode `commit()` est appelée pour notifier tous les composants intéressés que les données ont été modifiées. Le `Table` capte l'`RepositoryCommitEvent` et se met à jour en fonction des nouvelles données.

:::tip Mise à jour et création d'entrées
Appeler la méthode `commit()` met à la fois à jour les entrées existantes et **insère toute nouvelle entrée qui a été créée**.
:::
