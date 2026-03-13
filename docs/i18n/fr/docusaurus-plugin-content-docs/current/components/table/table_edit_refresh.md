---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 1a8c3b1ab877d759906737431d8601e1
---
Modifier des données dans la `Table` fonctionne via l'interaction avec le `Repository` contenant les données de la `Table`. Le `Repository` sert de pont entre la `Table` et le jeu de données sous-jacent, offrant des méthodes pour la récupération, la modification et le rafraîchissement des données. Ci-dessous, un exemple qui implémente un comportement pour modifier le "Titre" d'une ligne souhaitée.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Dans l'exemple ci-dessus, la classe `TitleEditorComponent` facilite la modification du champ "Titre" pour un `MusicRecord` sélectionné. Le composant comprend un champ de saisie pour le nouveau titre, ainsi que des boutons "Enregistrer" et "Annuler".

Pour connecter le composant de modification avec la `Table`, un bouton "Modifier" est ajouté à la `Table` via un `VoidElementRenderer`. Cliquer sur ce bouton déclenche la méthode `edit()` du `TitleEditorComponent`, permettant aux utilisateurs de modifier la valeur du "Titre".

## Méthode de validation {#commit-method}

Une fois que l'utilisateur modifie le titre et clique sur le bouton "Enregistrer", le `TitleEditorComponent` déclenche la méthode `save()`. Cette méthode met à jour le titre du `MusicRecord` correspondant et dispatch un `SaveEvent` personnalisé.

La mise à jour en temps réel des données dans le repository est réalisée grâce à la méthode `commit()`. Cette méthode est utilisée dans l'écouteur d'événements `onSave`, garantissant que les modifications apportées par le biais du composant de modification sont reflétées dans le jeu de données sous-jacent.

La méthode `commit()` est appelée pour notifier tous les composants intéressés que les données ont été changées. La `Table` intercepte le `RepositoryCommitEvent` et se met à jour en fonction des nouvelles données.

:::tip Mise à jour et création d'entrées
Appeler la méthode `commit()` met à la fois à jour les entrées existantes et **insère toute nouvelle entrée qui a été créée**.
:::
