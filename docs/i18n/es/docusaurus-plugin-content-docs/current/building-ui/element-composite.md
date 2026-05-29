---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: de075e855ba84ee82ec08c2bef771ea8
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` sirve como una base versátil para gestionar elementos compuestos en aplicaciones webforJ. Su propósito principal es facilitar la interacción con elementos HTML, representados por la clase `Element`, proporcionando un enfoque estructurado para manejar propiedades, atributos y escuchadores de eventos. Permite la implementación y reutilización de elementos en una aplicación. Usa la clase `ElementComposite` al implementar Web Components para su uso en aplicaciones webforJ.

Al usar la clase `ElementComposite`, el método `getElement()` te da acceso al componente `Element` subyacente. De manera similar, el método `getNodeName()` te proporciona el nombre de ese nodo en el DOM.

:::tip
Es posible hacer todo con la clase `Element` en sí, sin usar la clase `ElementComposite`. Sin embargo, los métodos en `ElementComposite` te brindan una manera de reutilizar tu trabajo.
:::

Los ejemplos en esta página demuestran cómo implementar el [componente web de código QR de Shoelace](https://shoelace.style/components/qr-code) usando la clase `ElementComposite`.

<ComponentDemo
path='/webforj/qrdemo'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java']}
height='175px'
/>

## Descriptores de propiedades y atributos {#property-and-attribute-descriptors}

Las propiedades y atributos en los componentes web representan el estado del componente. A menudo se utilizan para gestionar datos o configuración. La clase `ElementComposite` proporciona una forma conveniente de trabajar con propiedades y atributos.

Las propiedades y atributos pueden ser declarados e inicializados como miembros `PropertyDescriptor` de la clase `ElementComposite` que se está escribiendo, y luego usarse en el código. Para definir propiedades y atributos, usa el método `set()` para establecer el valor de una propiedad. Por ejemplo, `set(PropertyDescriptor<V> property, V value)` establece una propiedad a un valor especificado.

:::info
Las propiedades se acceden y manipulan internamente dentro del código del componente y no se reflejan en el DOM. Los atributos, por otro lado, son parte de la interfaz externa del componente y se pueden usar para pasar información a un componente desde el exterior, proporcionando una forma para que elementos externos o scripts configuren el componente.
:::

```java
// Ejemplo de propiedad llamada TITLE en una clase ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
// Ejemplo de atributo llamado VALUE en una clase ElementComposite
private final PropertyDescriptor<String> VALUE = PropertyDescriptor.attribute("value", "");
//...
set(TITLE, "Mi Título");
set(VALUE, "Mi Valor");
```

Usa el método `get()` en la clase `ElementComposite` para acceder y leer propiedades. El método `get()` puede aceptar un valor `booleano` opcional, que es falso por defecto, para dictar si el método debe hacer un viaje al cliente para recuperar el valor. Esto impacta en el rendimiento, pero podría ser necesario si la propiedad puede ser modificada puramente en el cliente.

También se puede pasar un `Type` al método, que dicta cómo se debe convertir el resultado recuperado.

:::tip
Este `Type` no es estrictamente necesario y añade una capa extra de especificación al recuperar los datos.
:::

```java
// Ejemplo de propiedad llamada TITLE en una clase ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

En la demostración a continuación, se han agregado propiedades para el código QR basadas en la documentación para el componente web. Luego se han implementado métodos que permiten a los usuarios obtener y establecer las diversas propiedades que se han implementado.

<ComponentDemo
path='/webforj/qrproperties'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java']}
height='250px'
/>

## Registro de eventos {#event-registration}

Los eventos permiten la comunicación entre diferentes partes de tu aplicación webforJ. La clase `ElementComposite` proporciona manejo de eventos con soporte para debouncing, throttling, filtrado y recolección de datos de eventos personalizados.

Registra escuchadores de eventos usando el método `addEventListener()`:

```java
// Ejemplo: Agregando un escuchador de eventos de clic
addEventListener(ElementClickEvent.class, event -> {
  // Manejar el evento de clic
});
```

:::info
Los eventos de `ElementComposite` son diferentes de los eventos de `Element`, ya que no permiten cualquier clase, sino solo clases `Event` especificadas.
:::

### Clases de eventos integradas {#built-in-event-classes}

webforJ proporciona clases de eventos preconstruidas con acceso a datos tipados:

- **ElementClickEvent**: Eventos de clic del mouse con coordenadas (`getClientX()`, `getClientY()`), información sobre el botón (`getButton()`), y teclas modificadoras (`isCtrlKey()`, `isShiftKey()`, etc.)
- **ElementDefinedEvent**: Se dispara cuando un elemento personalizado se define en el DOM y está listo para su uso
- **ElementEvent**: Clase base de evento que proporciona acceso a los datos crudos del evento, tipo de evento (`getType()`) y ID del evento (`getId()`)

### Cargas de eventos {#event-payloads}

Los eventos transportan datos desde el cliente a tu código Java. Accede a estos datos a través de `getData()` para datos crudos del evento o utiliza métodos tipados cuando estén disponibles en las clases de eventos integrados. Para más detalles sobre el uso eficiente de las cargas de eventos, consulta la [guía de Eventos](../building-ui/events).

## Clases de eventos personalizadas {#custom-event-classes}

Para el manejo de eventos especializado, crea clases de eventos personalizadas con cargas configuradas usando las anotaciones `@EventName` y `@EventOptions`.

En el ejemplo a continuación, se ha creado un evento de clic y luego se ha agregado al componente de código QR. Este evento, cuando se dispara, mostrará la coordenada "X" del mouse en el momento de hacer clic en el componente, que se proporciona al evento Java como datos. Luego se implementa un método para permitir al usuario acceder a estos datos, que es como se muestra en la aplicación.

<ComponentDemo
path='/webforj/qrevent'
files={['src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java']}
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` te permite personalizar el comportamiento del evento configurando qué datos recopilar, cuándo se disparan los eventos y cómo se procesan. Aquí hay un fragmento de código completo que muestra todas las opciones de configuración:

```java
ElementEventOptions options = new ElementEventOptions()
  // Recopilar datos personalizados del cliente
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Ejecutar JavaScript antes de que se dispare el evento
  .setCode("component.classList.add('processing');")
  
  // Solo disparar si se cumplen las condiciones
  .setFilter("component.value.length >= 2")
  
  // Retrasar la ejecución hasta que el usuario deje de escribir (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Control de rendimiento {#performance-control}

Controla cuándo y con qué frecuencia se disparan los eventos:

**Debouncing** retrasa la ejecución hasta que la actividad se detiene:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Esperar 300ms después del último evento
```

**Throttling** limita la frecuencia de ejecución:

```java
options.setThrottle(100); // Disparar como máximo una vez cada 100ms
```

Fases de debounce disponibles:

- `LEADING`: Disparar inmediatamente, luego esperar
- `TRAILING`: Esperar un período callado, luego disparar (por defecto)
- `BOTH`: Disparar inmediatamente y después del período callado

## Fusión de opciones {#options-merging}

Combina configuraciones de eventos de diferentes fuentes usando `mergeWith()`. Las opciones base proporcionan datos comunes para todos los eventos, mientras que las opciones específicas añaden configuraciones especializadas. Las opciones posteriores sobrescriben configuraciones conflictivas.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interacción con ranuras {#interacting-with-slots}

Los componentes web a menudo utilizan ranuras para permitir que los desarrolladores definan la estructura de un componente desde el exterior. Una ranura es un marcador de posición dentro de un componente web que se puede llenar con contenido al usar el componente. En el contexto de la clase `ElementComposite`, las ranuras proporcionan una forma de personalizar el contenido dentro de un componente. Se proporcionan los siguientes métodos para permitir a los desarrolladores interactuar y manipular ranuras:

1. **`findComponentSlot()`**: Este método se utiliza para buscar un componente específico en todas las ranuras en un sistema de componentes. Devuelve el nombre de la ranura donde se encuentra el componente. Si el componente no se encuentra en ninguna ranura, se devuelve una cadena vacía.

2. **`getComponentsInSlot()`**: Este método recupera la lista de componentes asignados a una ranura dada en un sistema de componentes. Opcionalmente, pasa un tipo de clase específico para filtrar los resultados del método.

3. **`getFirstComponentInSlot()`**: Este método está diseñado para obtener el primer componente asignado a la ranura. Opcionalmente, pasa un tipo de clase específico para filtrar los resultados de este método.

También es posible usar el método `add()` con un parámetro `String` para especificar la ranura deseada en la que agregar el componente pasado.

Estas interacciones permiten a los desarrolladores aprovechar el poder de los componentes web al proporcionar una API limpia y sencilla para manipular ranuras, propiedades y manejar eventos dentro de la clase `ElementComposite`.
