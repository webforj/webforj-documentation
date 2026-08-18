---
title: Work with the MCP client
sidebar_position: 25
description: Connect a rendered webforJ view to its MCP client.
_i18n_hash: 082797b568bd8f308b625306c524d7ef
---
Una aplicación MCP no tiene que mantener cada interacción dentro de su vista incrustada. Puede enviar información a la conversación, mantener al modelo informado a medida que el usuario cambia la interfaz de usuario o pedir al cliente que maneje algo fuera del marco.

La misma ruta también se puede abrir en un navegador normal. Comience cada interacción del cliente verificando si hay un host MCP presente.

## Continúe la conversación desde la vista {#send-a-message}

Considere una aplicación de inventario donde el usuario selecciona un almacén y luego pide a la IA que revise su stock. El botón puede enviar esa solicitud como el siguiente mensaje del usuario:

```java
Paragraph warehouse = new Paragraph("Almacén: BER");
Button review = new Button("Revisar stock");

review.addClickListener(event -> McpHost.ifPresent(host ->
    host.sendMessage("Revisa el stock actual de " + warehouse.getText())));
```

`McpHost.ifPresent` ejecuta el callback solo cuando la vista está conectada a un cliente MCP. En un navegador normal, el botón no tiene efecto del lado del host.

## Mantenga informado al modelo {#update-model-context}

No cada cambio de UI debe crear otro mensaje. Cuando se selecciona un almacén o cambian los filtros, la aplicación puede reemplazar el contexto que contribuye al modelo:

```java
McpHost host = McpHost.getCurrent();
if (host != null) {
  PendingResult<Void> result = host.updateModelContext(
      Map.of("warehouse", warehouse.getText(), "source", "inventory-app"));

  result.exceptionally(error -> {
    warehouse.setText("Error en el intercambio: " + error.getMessage());
    return null;
  });
}
```

El estado actualizado se vuelve disponible para las respuestas del modelo posteriores sin agregar un mensaje visible a la conversación. Las llamadas del host son asíncronas y devuelven un <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink>, así que maneje la finalización o el error sin bloquear el hilo de la interfaz de usuario de webforJ.

## Salir de la vista incrustada {#leave-the-view}

Algunos trabajos pertenecen fuera del marco de la aplicación. Use `openLink` cuando el usuario necesite continuar en una página externa. Use `requestDisplayMode` cuando el contenido actual necesite una presentación diferente, como pantalla completa para una tabla detallada. El cliente decide si puede satisfacer cualquiera de las solicitudes.

:::tip[Mantenga completa la experiencia del navegador]

Trate la integración del host como una mejora. La ruta debe seguir siendo útil cuando se ejecute en un navegador o cuando el cliente conectado no soporte una capacidad solicitada.
:::

## Siga los cambios de la conversación {#host-events}

El cliente puede continuar trabajando con la aplicación después de que se renderiza. Por ejemplo, la vista puede limpiar un estado de carga cuando se cancela una llamada a la herramienta y refrescar el texto explicativo cuando cambia el contexto de la conversación:

```java
McpHost.ifPresent(host -> {
  host.onToolCancelled(event ->
      warehouse.setText("La solicitud de inventario fue cancelada."));
  host.onHostContextChanged(event ->
      warehouse.setText("El contexto de la conversación ha cambiado."));
});
```

Registre solo los oyentes que la vista necesita y no asuma que cada cliente envía cada evento. Consulte la documentación de Javadoc de `McpHost` para las solicitudes, eventos, cargas útiles y firmas de métodos disponibles.
