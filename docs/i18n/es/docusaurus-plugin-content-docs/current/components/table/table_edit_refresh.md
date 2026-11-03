---
sidebar_position: 30
title: Edición y actualización
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 84dec48293e6fa794713881310b6f303
---
La edición de datos en `Table` se realiza mediante la interacción con `Repository`, que contiene los datos de `Table`. `Repository` sirve de puente entre `Table` y el conjunto de datos subyacente, y ofrece métodos para recuperar, modificar y actualizar los datos. A continuación se muestra un ejemplo que implementa el comportamiento para editar el «Título» de una fila seleccionada.

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

En el ejemplo anterior, la clase `TitleEditorComponent` permite editar el campo «Título» de un `MusicRecord` seleccionado. El componente incluye un campo de entrada para el nuevo título, junto con los botones «Guardar» y «Cancelar».

Para conectar el componente de edición con `Table`, se añade un botón «Editar» a `Table` mediante un `VoidElementRenderer`. Al hacer clic en este botón, se activa el método `edit()` de `TitleEditorComponent`, lo que permite a los usuarios modificar el valor de «Título».

## Método commit {#commit-method}

Cuando el usuario modifica el título y hace clic en el botón «Guardar», `TitleEditorComponent` activa el método `save()`. Este método actualiza el título del `MusicRecord` correspondiente y despacha un evento `SaveEvent` personalizado.

La actualización de los datos del repositorio en tiempo real se realiza mediante el método `commit()`. Este método se utiliza dentro del listener del evento `onSave` para garantizar que los cambios realizados con el componente de edición se reflejen en el conjunto de datos subyacente.

Se llama al método `commit()` para notificar a todos los componentes interesados que los datos han cambiado. `Table` captura el evento `RepositoryCommitEvent` y se actualiza con los nuevos datos.

:::tip Actualizar y crear entradas
Al llamar al método `commit()`, se actualizan las entradas existentes y se **insertan las entradas nuevas que se hayan creado**.
:::
