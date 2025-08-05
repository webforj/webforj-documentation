---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 0c57bd3fd3a9adb9cb7275e23efa725f
---
Modifier les données au sein de la `Table` se fait via l'interaction avec le `Repository` contenant les données pour la `Table`. Le `Repository` sert de pont entre la `Table` et l'ensemble de données sous-jacent, offrant des méthodes pour la récupération, la modification et le rafraîchissement des données. Voici un exemple qui met en œuvre un comportement pour modifier le "Titre" d'une ligne souhaitée basée.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Dans l'exemple ci-dessus, la classe `TitleEditorComponent` facilite la modification du champ "Titre" pour un `MusicRecord` sélectionné. Le composant comprend un champ de saisie pour le nouveau titre, ainsi que des boutons "Enregistrer" et "Annuler".

Pour connecter le composant d'édition avec la `Table`, un bouton "Modifier" est ajouté à la `Table` via un `VoidElementRenderer`. Cliquer sur ce bouton déclenche la méthode `edit()` du `TitleEditorComponent`, permettant aux utilisateurs de modifier le "Titre".

## Méthode de validation {#commit-method}

Une fois que l'utilisateur modifie le titre et clique sur le bouton "Enregistrer", le `TitleEditorComponent` déclenche la méthode `save()`. Cette méthode met à jour le titre du `MusicRecord` correspondant et déclenche un `SaveEvent` personnalisé.

La mise à jour en temps réel des données dans le repository est réalisée grâce à la méthode `commit()`. Cette méthode est utilisée dans l'écouteur d'événements `onSave`, garantissant que les modifications apportées par le composant d'édition soient reflétées dans l'ensemble des données sous-jacent.

La méthode `commit()` est appelée pour notifier tous les composants intéressés que les données ont été modifiées. La `Table` intercepte le `RepositoryCommitEvent` et se met à jour en fonction des nouvelles données.

:::tip Mise à jour et création d'entrées
Appeler la méthode `commit()` met à jour à la fois les entrées existantes et **insère toutes les nouvelles entrées qui ont été créées**.
:::
