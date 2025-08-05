---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 0c57bd3fd3a9adb9cb7275e23efa725f
---
Editar datos dentro de la `Table` funciona a través de la interacción con el `Repository` que contiene los datos para la `Table`. El `Repository` sirve como un puente entre la `Table` y el conjunto de datos subyacente, ofreciendo métodos para la recuperación, modificación y actualización de datos. A continuación, se muestra un ejemplo que implementa el comportamiento para editar el "Título" de una fila deseada.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

En el ejemplo anterior, la clase `TitleEditorComponent` facilita la edición del campo "Título" para un `MusicRecord` seleccionado. El componente incluye un campo de entrada para el nuevo título, junto con botones de "Guardar" y "Cancelar".

Para conectar el componente de edición con la `Table`, se agrega un botón de "Editar" a la `Table` a través de un `VoidElementRenderer`. Hacer clic en este botón activa el método `edit()` del `TitleEditorComponent`, permitiendo a los usuarios modificar el "Título".

## Método commit {#commit-method}

Una vez que el usuario modifica el título y hace clic en el botón "Guardar", el `TitleEditorComponent` activa el método `save()`. Este método actualiza el título del `MusicRecord` correspondiente y despacha un `SaveEvent` personalizado.

La actualización en tiempo real de los datos en el repositorio se logra a través del método `commit()`. Este método se emplea dentro del listener del evento `onSave`, asegurando que los cambios realizados a través del componente de edición se reflejen en el conjunto de datos subyacente.

Se llama al método `commit()` para notificar a todos los componentes interesados que los datos han sido cambiados. La `Table` captura el `RepositoryCommitEvent` y se actualiza en función de los nuevos datos.

:::tip Actualizando y creando entradas
Llamar al método `commit()` tanto actualiza las entradas existentes, como **inserta cualquier nueva entrada que se haya creado**.
:::
