---
sidebar_position: 7
title: Eventos
description: >-
  Listen for component events, read the event payload, configure element events,
  and dispatch your own custom events with the EventDispatcher.
slug: events
sidebar_class_name: new-content
_i18n_hash: 5ceda90a316ff6a1528a686565011f88
---
Los componentes, ya sean personalizados o parte del marco, admiten el manejo de eventos. Puedes agregar oyentes de eventos para capturar varios tipos de eventos, como interacciones del usuario, cambios en el estado o eventos que definas tú mismo. Estos oyentes te permiten activar un comportamiento específico en respuesta a lo que sucede en tu aplicación.

## Agregando eventos {#adding-events}

Agrega un oyente con el método específico del evento en el componente. Cada componente expone un par: un método `addXxxListener` y, en la mayoría de los casos, un alias más corto `on` que hace lo mismo. Un `Button`, por ejemplo, expone tanto `addClickListener` como `onClick`.

Puedes pasar el oyente como una lambda:

```java
Button button = new Button("Guardar");
button.onClick(event -> {
  // Manejar el clic
});
```

o como una referencia de método:

```java
button.onClick(this::handleSave);
```

No todos los eventos tienen un alias `on`. Los cambios de valor, por ejemplo, se agregan solo con `addValueChangeListener`:

```java
TextField name = new TextField("Nombre");
name.addValueChangeListener(event -> {
  String value = event.getValue();
  // Manejar el nuevo valor
});
```

## Eliminando un evento {#removing-an-event}

Agregar un oyente devuelve un `ListenerRegistration`. Mantenlo para eliminar el oyente más tarde.

```java
ListenerRegistration<ButtonClickEvent> registration =
    button.onClick(event -> {
      // Manejar el clic
    });

// Más tarde, cuando el oyente ya no sea necesario
registration.remove();
```

## Usando la carga útil del evento {#using-event-payload}

Los eventos llevan una carga útil con información sobre lo que ha ocurrido. Leer esa carga útil en el manejador te da los datos relevantes sin necesidad de ir de vuelta al cliente.

Por ejemplo, un `ModifyEvent` de un `TextField` lleva el texto actual del campo. Puedes consultarlo desde el componente:

```java
TextField field = new TextField("Buscar");
field.onModify(event -> {
  String text = field.getText();
  // Usar texto
});
```

El mismo valor ya está en el evento, por lo que leerlo de la carga útil evita volver al componente:

```java
field.onModify(event -> {
  String text = event.getText();
  // Usar texto
});
```

Lee de la carga útil siempre que un evento exponga los datos que necesitas. Para más información sobre por qué esto es importante, consulta [Interacción Cliente/Servidor](../architecture/client-server).

## Configurando eventos de elementos {#configuring-element-events}

Cuando trabajas directamente con un <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink>, sus eventos se configuran con <JavadocLink type="foundation" location="com/webforj/component/element/event/ElementEventOptions" code='true'>ElementEventOptions</JavadocLink>. Esto controla qué datos lleva el evento, si se dispara o no, y con qué frecuencia, todo evaluado en el cliente antes de que el evento llegue al servidor.

### Datos de eventos {#event-data}

Los datos del evento adjuntan valores del cliente al evento, por lo que la información está disponible en el servidor sin una solicitud adicional. Los agregas con `addData()`, dando a cada entrada una clave y una expresión de JavaScript que produce el valor.

Dos variables están disponibles dentro de estas expresiones: `event`, el objeto de evento del cliente, y `component`, el elemento al que está adjunto el oyente.

```java
ElementEventOptions options = new ElementEventOptions()
    .addData("value", "component.value")
    .addData("key", "event.key");
```

En el servidor, cada valor se lee del evento por su clave.

### Ejecutando JavaScript {#executing-javascript}

`setCode()` ejecuta un fragmento de JavaScript en el cliente antes de que se dispare el evento. Esto es útil para preparar los datos del evento o reaccionar en el cliente sin una vuelta al servidor.

```java
ElementEventOptions options = new ElementEventOptions()
    .setCode("event.target.value = event.target.value.trim();");
```

### Filtrando eventos {#filtering-events}

`setFilter()` establece una expresión de JavaScript que decide si se dispara el evento. Si evalúa a falso, el evento nunca llega al servidor. Esto es útil cuando solo te interesa un evento bajo ciertas condiciones, como que una entrada pase una longitud mínima.

```java
ElementEventOptions options = new ElementEventOptions()
    .setFilter("event.target.value.length > 2");
```

### Debouncing y throttling {#debouncing-and-throttling}

Debouncing y throttling limitan con qué frecuencia un evento llega al servidor, lo que es útil para eventos rápidos como teclear o desplazar.

Debouncing espera hasta que la actividad se asiente antes de disparar. `setDebounce()` toma un tiempo de espera en milisegundos y una <JavadocLink type="foundation" location="com/webforj/component/element/event/DebouncePhase" code='true'>DebouncePhase</JavadocLink> opcional: `LEADING` se dispara al inicio del estallido, `TRAILING` se dispara después de que termina, y `BOTH` se dispara en cada borde. Cuando omites la fase, se establece por defecto en `TRAILING`.

```java
ElementEventOptions options = new ElementEventOptions()
    .setDebounce(300, DebouncePhase.TRAILING);
```

Throttling se dispara a una tasa máxima constante mientras la actividad continúa. `setThrottle()` toma un tiempo de espera en milisegundos.

```java
ElementEventOptions options = new ElementEventOptions()
    .setThrottle(300);
```

Un evento utiliza uno u otro. Establecer un debounce borra cualquier throttle en las mismas opciones, y establecer un throttle borra cualquier debounce.

### Anotaciones {#annotations}

Las opciones de eventos de elementos también se pueden establecer con anotaciones, que es una forma más concisa de configurar un oyente. La anotación `@EventOptions` contiene las entradas de datos, junto con los filtros, debouncing y configuraciones de throttling.

```java
@EventOptions(
    data = {@EventData(key = "value", exp = "component.value")},
    debounce = @DebounceSettings(value = 200))
```

Cuando también pasas un `ElementEventOptions` en el sitio de llamada, sus datos se combinan con los datos de la anotación, y su código, filtro, debouncing y throttling anulan los de la anotación.

## Despachando tus propios eventos {#dispatching-your-own-events}

Los eventos cubiertos hasta ahora provienen del componente al que estás escuchando. Un componente que escribes puede publicar eventos propios de la misma manera, para que el código que lo utiliza pueda reaccionar sin acceder a los internos del componente.

:::tip Cuándo despachar un evento personalizado
Despacha un evento personalizado cuando tu componente decide que algo ha sucedido, como un formulario informando una presentación completada o un editor informando un registro guardado. Los eventos que se originan de una interacción del cliente en un `Element` se configuran con [opciones de eventos de elementos](#configuring-element-events) en su lugar.
:::

Los componentes no vienen con un despachador de eventos, así que un componente que publique sus propios eventos mantiene su propio <JavadocLink type="foundation" location="com/webforj/dispatcher/EventDispatcher" code='true'>EventDispatcher</JavadocLink> y publica a través de él.

### Definiendo el evento {#defining-the-event}

Define el evento como una clase que extiende `EventObject`. Pasa la fuente, el objeto que publica el evento, a la superclase y agrega accesores para los datos que los oyentes necesitan.

```java
public class OrderSubmittedEvent extends EventObject {
  private final String orderId;
  private final double total;

  public OrderSubmittedEvent(Object source, String orderId, double total) {
    super(source);
    this.orderId = orderId;
    this.total = total;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getTotal() {
    return total;
  }
}
```

Leer los datos del evento sigue el mismo razonamiento que [usar la carga útil del evento](#using-event-payload). Los oyentes obtienen lo que necesitan del evento en lugar de consultar a la fuente después.

### Registrando y despachando {#registering-and-dispatching}

Crea un despachador, registra oyentes para un tipo de evento y despacha una instancia de ese tipo cuando ocurre el evento. La registración devuelve un `ListenerRegistration`, que debes mantener para eliminar el oyente más tarde.

```java
EventDispatcher dispatcher = new EventDispatcher();

ListenerRegistration<OrderSubmittedEvent> registration =
    dispatcher.addListener(OrderSubmittedEvent.class, event -> {
      String id = event.getOrderId();
      // Manejar el evento
    });

dispatcher.dispatchEvent(new OrderSubmittedEvent(this, "ORD-1001", 49.99));
```

Cada oyente registrado para ese tipo de evento se ejecuta cuando se despacha el evento.

Un componente que publica un evento posee internamente el despachador y expone un método `onXxx` en lugar del despachador en sí, de modo que los llamadores se suscriben de la misma manera que lo harían para un evento incorporado:

```java
public ListenerRegistration<OrderSubmittedEvent> onSubmit(
    EventListener<OrderSubmittedEvent> listener) {
  return dispatcher.addListener(OrderSubmittedEvent.class, listener);
}
```

### Eliminando oyentes {#removing-listeners}

Elimina un oyente a través de su registro, o pasando el oyente de vuelta al despachador:

```java
registration.remove();

//O

dispatcher.removeListener(OrderSubmittedEvent.class, registration.getListener());
```

Para limpiar todos los oyentes registrados para un tipo de evento de una vez:

```java
dispatcher.removeAllListeners(OrderSubmittedEvent.class);
```

### Evitando fugas de memoria {#avoiding-memory-leaks}

Un despachador retiene sus oyentes, y cada oyente retiene lo que haya capturado. Una lambda o clase interna captura implícitamente `this` junto con cualquier variable local que utilice, por lo que los objetos detrás de un oyente permanecen accesibles mientras el despachador lo mantenga.

Esto se convierte en un problema cuando un oyente sobrevive a lo que referencia. Si un diálogo registra un oyente que lee su propio modelo y se cierra sin eliminarlo, el despachador aún mantiene el oyente, el oyente aún mantiene el diálogo y ninguno puede ser recolectado por el recolector de basura. En una aplicación que crea muchas vistas de corta duración, los oyentes retenidos se acumulan de esta manera.

Elimina un oyente cuando:

- El objeto que lo registró ha terminado, como un diálogo cerrado o una vista de la que se ha navegado fuera.
- La suscripción estaba vinculada a una tarea de corta duración o un flujo de una sola vez.

Mantén el `ListenerRegistration` devuelto donde puedas acceder a él durante la limpieza en lugar de registrar un oyente que no puedes eliminar más tarde. En un componente, `onDidDestroy()` es el punto de limpieza.
