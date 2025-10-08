---
sidebar_position: 1
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: 6e201040e3dfd4be12037094eb9e978e
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` sirve como una base versátil para gestionar elementos compuestos en aplicaciones webforJ. Su propósito principal es facilitar la interacción con los elementos HTML, representados por la clase `Element`, proporcionando un enfoque estructurado para manejar propiedades, atributos y oyentes de eventos. Permite la implementación y reutilización de elementos en una aplicación. Utiliza la clase `ElementComposite` al implementar Web Components para su uso en aplicaciones webforJ.

Al usar la clase `ElementComposite`, utilizar el método `getElement()` te dará acceso al componente `Element` subyacente. De manera similar, el método `getNodeName()` te dará el nombre de ese nodo en el DOM.

:::tip
Es posible hacer todo con la clase `Element` por sí misma, sin usar la clase `ElementComposite`. Sin embargo, los métodos proporcionados en la `ElementComposite` ofrecen a los usuarios una manera de reutilizar el trabajo que se está realizando. 
:::

Esta guía demuestra cómo implementar el [componente web de código QR de Shoelace](https://shoelace.style/components/qr-code) utilizando la clase `ElementComposite`.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Descriptores de propiedades y atributos {#property-and-attribute-descriptors}

Las propiedades y atributos en los componentes web representan el estado del componente. Se utilizan a menudo para gestionar datos o configuraciones. La clase `ElementComposite` proporciona una forma conveniente de trabajar con propiedades y atributos.

Las propiedades y atributos se pueden declarar e inicializar como miembros `PropertyDescriptor` de la clase `ElementComposite` que se está escribiendo, y luego usarse en el código. Para definir propiedades y atributos, utiliza el método `set()` para establecer el valor de una propiedad. Por ejemplo, `set(PropertyDescriptor<V> property, V value)` establece una propiedad a un valor especificado.

:::info
Las propiedades se acceden y manipulan internamente dentro del código del componente y no se reflejan en el DOM. Los atributos, en cambio, son parte de la interfaz externa del componente y pueden usarse para pasar información a un componente desde el exterior, proporcionando una manera para que elementos o scripts externos configuren el componente.
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

Además de establecer una propiedad, utiliza el método `get()` en la clase `ElementComposite` para acceder y leer propiedades. El método `get()` puede recibir un valor `booleano` opcional, que es falso por defecto, para dictar si el método debe hacer un viaje al cliente para recuperar el valor. Esto impacta en el rendimiento, pero puede ser necesario si la propiedad puede ser modificada únicamente en el cliente.

También se puede pasar un `Type` al método, que dicta a qué se debe convertir el resultado recuperado.

:::tip
Este `Type` no es necesariamente obvio y añade una capa adicional de especificación a medida que se recupera la información.
:::

```java
// Ejemplo de propiedad llamada TITLE en una clase ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

En la demostración a continuación, se han agregado propiedades para el código QR basado en la documentación del componente web. Luego se han implementado métodos que permiten a los usuarios obtener y establecer las diversas propiedades que se han implementado.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Registro de eventos {#event-registration}

Los eventos permiten la comunicación entre diferentes partes de tu aplicación webforJ. La clase `ElementComposite` proporciona manejo de eventos con soporte para debouncing, throttling, filtrado y recopilación de datos de eventos personalizados.

Registra oyentes de eventos utilizando el método `addEventListener()`:

```java
// Ejemplo: Agregando un oyente de eventos de clic
addEventListener(ElementClickEvent.class, event -> {
    // Manejar el evento de clic
});
```

:::info
Los eventos de `ElementComposite` son diferentes a los eventos de `Element`, ya que esto no permite cualquier clase, sino solo clases de `Event` especificadas.
:::

### Clases de eventos integradas {#built-in-event-classes}

webforJ proporciona clases de eventos preconstruidas con acceso a datos tipados:

- **ElementClickEvent**: Eventos de clic con coordenadas (`getClientX()`, `getClientY()`), información del botón (`getButton()`) y teclas modificadoras (`isCtrlKey()`, `isShiftKey()`, etc.)
- **ElementDefinedEvent**: Se dispara cuando un elemento personalizado se define en el DOM y está listo para usarse
- **ElementEvent**: Clase base de eventos que proporciona acceso a datos de eventos en bruto, tipo de evento (`getType()`) y ID de evento (`getId()`)

### Cargas de eventos {#event-payloads}

Los eventos llevan datos del cliente a tu código Java. Accede a estos datos a través de `getData()` para datos de eventos en bruto o utiliza métodos tipados cuando estén disponibles en las clases de eventos integradas. Para más detalles sobre el uso eficiente de las cargas de eventos, consulta la [guía de Eventos](../building-ui/events).

## Clases de eventos personalizadas {#custom-event-classes}

Para un manejo especializado de eventos, crea clases de eventos personalizadas con cargas configuradas utilizando las anotaciones `@EventName` y `@EventOptions`.

En el ejemplo a continuación, se ha creado un evento de clic y luego se ha añadido al componente de código QR. Este evento, cuando se activa, mostrará la coordenada "X" del mouse en el momento de hacer clic en el componente, que se proporciona al evento de Java como datos. Luego se implementa un método para permitir al usuario acceder a estos datos, que es cómo se muestra en la aplicación.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` te permite personalizar el comportamiento de los eventos configurando qué datos recopilar, cuándo se disparan los eventos y cómo se procesan. Aquí hay un fragmento de código completo que muestra todas las opciones de configuración:

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

Fases de debouncing disponibles:

- `LEADING`: Disparar inmediatamente, luego esperar
- `TRAILING`: Esperar un período de silencio, luego disparar (predeterminado)
- `BOTH`: Disparar inmediatamente y después del período de silencio

## Combinación de opciones {#options-merging}

Combina configuraciones de eventos de diferentes fuentes utilizando `mergeWith()`. Las opciones base proporcionan datos comunes para todos los eventos, mientras que las opciones específicas añaden configuraciones especializadas. Las opciones posteriores anulan configuraciones conflictivas.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interacción con slots {#interacting-with-slots}

Los componentes web suelen utilizar slots para permitir a los desarrolladores definir la estructura de un componente desde el exterior. Un slot es un marcador de posición dentro de un componente web que puede ser llenado con contenido al usar el componente. En el contexto de la clase `ElementComposite`, los slots proporcionan una forma de personalizar el contenido dentro de un componente. Se proporcionan los siguientes métodos para permitir a los desarrolladores interactuar y manipular los slots:

1. **`findComponentSlot()`**: Este método se usa para buscar un componente específico en todos los slots de un sistema de componentes. Devuelve el nombre del slot donde se encuentra el componente. Si el componente no se encuentra en ningún slot, se devuelve una cadena vacía.

2. **`getComponentsInSlot()`**: Este método recupera la lista de componentes asignados a un slot dado en un sistema de componentes. Opcionalmente, pasa un tipo de clase específico para filtrar los resultados del método.

3. **`getFirstComponentInSlot()`**: Este método está diseñado para obtener el primer componente asignado al slot. Opcionalmente pasa un tipo de clase específico para filtrar los resultados de este método.

También es posible utilizar el método `add()` con un parámetro `String` para especificar el slot deseado en el que agregar el componente pasado.

Estas interacciones permiten a los desarrolladores aprovechar el poder de los componentes web al proporcionar una API limpia y sencilla para manipular slots, propiedades y manejar eventos dentro de la clase `ElementComposite`.
