---
sidebar_position: 30
title: Editar y Actualizar
slug: refreshing
_i18n_hash: 39816123675d62a6dda185187e8d13e2
---
Editar datos dentro de `Table` funciona a través de la interacción con el `Repository` que contiene los datos para el `Table`. El `Repository` sirve como un puente entre el `Table` y el conjunto de datos subyacente, ofreciendo métodos para la recuperación, modificación y actualización de datos. A continuación se muestra un ejemplo que implementa el comportamiento para editar el "Título" de una fila deseada.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

En el ejemplo anterior, la clase `TitleEditorComponent` facilita la edición del campo "Título" para un `MusicRecord` seleccionado. El componente incluye un campo de entrada para el nuevo título, junto con botones de "Guardar" y "Cancelar".

Para conectar el componente de edición con el `Table`, se añade un botón de "Editar" al `Table` a través de un `VoidElementRenderer`. Al hacer clic en este botón se activa el método `edit()` del `TitleEditorComponent`, permitiendo a los usuarios modificar el "Título".

## Método commit {#commit-method}

Una vez que el usuario modifica el título y hace clic en el botón "Guardar", el `TitleEditorComponent` activa el método `save()`. Este método actualiza el título del `MusicRecord` correspondiente y despacha un `SaveEvent` personalizado.

La actualización en tiempo real de los datos en el repositorio se logra a través del método `commit()`. Este método se emplea dentro del listener del evento `onSave`, asegurando que los cambios realizados a través del componente de edición se reflejen en el conjunto de datos subyacente.

El método `commit()` se llama para notificar a todos los componentes interesados que los datos han cambiado. El `Table` captura el `RepositoryCommitEvent` y se actualiza en función de los nuevos datos.

:::tip Actualización y creación de entradas
Llamar al método `commit()` tanto actualiza entradas existentes, como **inserta cualquier nueva entrada que se haya creado**.
:::
