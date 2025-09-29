---
title: Communication
sidebar_position: 3
_i18n_hash: 4a12006d21bb2a0bd6e82f2f0ff8fa78
---
El `WebswingConnector` proporciona comunicación bidireccional entre tu aplicación webforJ y la aplicación Swing incrustada. Esto te permite enviar comandos a la aplicación Swing y recibir notificaciones cuando ocurren eventos dentro de ella.

## Enviando acciones a Swing

El método `performAction()` permite a tu aplicación webforJ activar funcionalidades en la aplicación Swing. Esto es útil para sincronizar el estado, activar actualizaciones o controlar el comportamiento de la aplicación Swing desde la interfaz web.

Por ejemplo, si tu aplicación Swing tiene un manejador de acciones personalizado para refrescar datos:

```java
// Activar un refresh en la aplicación Swing desde webforJ
connector.performAction("refresh");
```

También puedes enviar datos junto con la acción. La aplicación Swing recibe esto a través de su integración con la API de Webswing:

```java
// Enviar un comando con datos desde webforJ
connector.performAction("selectRecord", "12345");

// Enviar datos binarios
byte[] fileContent = Files.readAllBytes(path);
connector.performAction("uploadDocument", "invoice.pdf", new String(fileContent));
```

Los nombres de las acciones y los formatos de datos esperados están definidos por la implementación de tu aplicación Swing.

## Recibiendo eventos desde Swing

El conector dispara tres tipos de eventos que notifican a tu aplicación webforJ sobre el estado y las acciones de la aplicación Swing.

### Eventos de ciclo de vida

El **evento de inicialización** se dispara cuando se establece la conexión de Webswing y está lista para la comunicación:

```java
connector.onInitialize(event -> {
  // Conexión establecida
  connector.getInstanceId().ifPresent(id ->
      console.log("Conectado a la instancia de Webswing: " + id)
  );
});
```

El **evento de inicio** se dispara cuando la aplicación Swing se ha cargado completamente y está en ejecución:

```java
connector.onStart(event -> {
  // La aplicación Swing ahora es visible e interactiva
  console.log("Aplicación lista para la interacción del usuario");
});
```

### Eventos de acciones personalizadas

Cuando tu aplicación Swing envía acciones personalizadas de vuelta a la interfaz web utilizando la [API Java de Webswing](https://www.webswing.org/docs/25.1/integrate/api), estas se reciben como eventos de acción:

```java
connector.onAction(event -> {
  String actionName = event.getActionName();

  switch(actionName) {
      case "dataUpdated":
        event.getActionData().ifPresent(data -> {
            // Manejar la notificación de actualización
            updateWebInterface(data);
        });
        break;

      case "fileReady":
        event.getActionBinaryData().ifPresent(data -> {
            // Datos binarios
            saveFile(fileData);
        });
        break;
  }
});
```
