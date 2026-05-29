---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 86d395b36fe0cdec90b5a29497a8b0d3
---
Editing data within the `Table` works via interaction with the `Repository` containing the data for the `Table`. The `Repository` serves as a bridge between the `Table` and the underlying dataset, offering methods for data retrieval, modification, and refreshing. Below is an example which implements behavior to edit the "Title" of a desired row based.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

In the above example, the `TitleEditorComponent` class facilitates the editing of the "Title" field for a selected `MusicRecord`. The component includes an input field for the new title, along with "Save" and "Cancel" buttons.

To connect the editing component with the `Table`, an "Edit" button is added to the `Table` via a `VoidElementRenderer`. Clicking this button triggers the `edit()` method of the `TitleEditorComponent`, allowing users to modify the "Title" value.

## Método commit {#commit-method}

Una vez que el usuario modifica el título y hace clic en el botón "Guardar", el `TitleEditorComponent` activa el método `save()`. Este método actualiza el título del `MusicRecord` correspondiente y despacha un `SaveEvent` personalizado.

La actualización en tiempo real de los datos en el repositorio se logra a través del método `commit()`. Este método se emplea dentro del listener del evento `onSave`, asegurando que los cambios realizados a través del componente de edición se reflejen en el conjunto de datos subyacente.

El método `commit()` se llama para notificar a todos los componentes interesados que los datos han sido modificados. El `Table` captura el `RepositoryCommitEvent` y se actualiza basado en los nuevos datos.

:::tip Actualización y creación de entradas
Llamar al método `commit()` actualiza tanto las entradas existentes como **inserta cualquier nueva entrada que se haya creado**.
:::
