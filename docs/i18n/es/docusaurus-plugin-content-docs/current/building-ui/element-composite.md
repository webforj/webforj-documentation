---
sidebar_position: 6
title: Element Composite
sidebar_class_name: has-new-content
slug: element_composite
_i18n_hash: c64ec386d273ab7facb974f5577afecf
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` sirve como una base versátil para gestionar elementos compuestos en aplicaciones webforJ. Su propósito principal es facilitar la interacción con elementos HTML, representados por la clase `Element`, al proporcionar un enfoque estructurado para manejar propiedades, atributos y oyentes de eventos. Permite la implementación y reutilización de elementos en una aplicación. Utiliza la clase `ElementComposite` al implementar Web Components para su uso en aplicaciones webforJ.

Mientras usas la clase `ElementComposite`, el método `getElement()` te da acceso al componente `Element` subyacente. De manera similar, el método `getNodeName()` te proporciona el nombre de ese nodo en el DOM.

:::tip
Es posible hacer todo con la clase `Element` por sí misma, sin usar la clase `ElementComposite`. Sin embargo, los métodos en `ElementComposite` te ofrecen una forma de reutilizar tu trabajo.
:::

Los ejemplos en esta página demuestran cómo implementar el [componente web QR de Shoelace](https://shoelace.style/components/qr-code) utilizando la clase `ElementComposite`.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Descriptores de propiedades y atributos {#property-and-attribute-descriptors}

Las propiedades y atributos en los web components representan el estado del componente. A menudo se utilizan para gestionar datos o configuraciones. La clase `ElementComposite` proporciona una manera conveniente de trabajar con propiedades y atributos.

Las propiedades y atributos pueden ser declarados e inicializados como miembros `PropertyDescriptor` de la clase `ElementComposite` que se está escribiendo, y luego ser utilizados en el código. Para definir propiedades y atributos, usa el método `set()` para establecer el valor de una propiedad. Por ejemplo, `set(PropertyDescriptor<V> property, V value)` establece una propiedad a un valor especificado.

:::info
Las propiedades se acceden y manipulan internamente dentro del código del componente y no se reflejan en el DOM. Los atributos, por otro lado, son parte de la interfaz externa del componente y pueden ser utilizados para pasar información a un componente desde el exterior, proporcionando una forma para que elementos o scripts externos configuren el componente.
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

Usa el método `get()` en la clase `ElementComposite` para acceder y leer propiedades. El método `get()` puede aceptar un valor `booleano` opcional, que por defecto es falso, para indicar si el método debe hacer una solicitud al cliente para recuperar el valor. Esto impacta en el rendimiento, pero puede ser necesario si la propiedad puede ser modificada puramente en el cliente.

Un `Type` también se puede pasar al método, que dicta a qué convertir el resultado recuperado.

:::tip
Este `Type` no es estrictamente necesario y añade una capa extra de especificación a medida que se recuperan los datos.
:::

```java
// Ejemplo de propiedad llamada TITLE en una clase ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

En la demostración a continuación, se han añadido propiedades para el código QR basadas en la documentación para el web component. Luego se han implementado métodos que permiten a los usuarios obtener y establecer las diversas propiedades que se han implementado.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Registro de eventos {#event-registration}

Los eventos permiten la comunicación entre diferentes partes de tu aplicación webforJ. La clase `ElementComposite` proporciona manejo de eventos con soporte para debouncing, throttling, filtrado y recolección de datos de eventos personalizados.

Registra oyentes de eventos usando el método `addEventListener()`:

```java
// Ejemplo: Añadiendo un oyente de eventos de clic
addEventListener(ElementClickEvent.class, event -> {
  // Manejar el evento de clic
});
```

:::info
Los eventos de `ElementComposite` son diferentes a los eventos de `Element`, ya que no permiten cualquier clase, sino sólo las clases de `Event` especificadas.
:::

### Clases de eventos integradas {#built-in-event-classes}

webforJ proporciona clases de eventos preconstruidas con acceso a datos tipados:

- **ElementClickEvent**: Eventos de clic del mouse con coordenadas (`getClientX()`, `getClientY()`), información del botón (`getButton()`) y teclas modificadoras (`isCtrlKey()`, `isShiftKey()`, etc.)
- **ElementDefinedEvent**: Se activa cuando un elemento personalizado está definido en el DOM y listo para usarse.
- **ElementEvent**: Clase base de eventos que proporciona acceso a datos de eventos en bruto, tipo de evento (`getType()`) e ID de evento (`getId()`)

### Payloads de eventos {#event-payloads}

Los eventos llevan datos del cliente a tu código Java. Accede a estos datos a través de `getData()` para datos de eventos en bruto o utiliza métodos tipados cuando estén disponibles en las clases de eventos integradas. Para más detalles sobre cómo usar eficientemente los payloads de eventos, consulta la [guía de Eventos](../building-ui/events).

## Clases de eventos personalizadas {#custom-event-classes}

Para un manejo especializado de eventos, crea clases de eventos personalizadas con payloads configurados usando las anotaciones `@EventName` y `@EventOptions`.

En el ejemplo a continuación, se ha creado un evento de clic y luego se ha añadido al componente de código QR. Este evento, cuando se activa, mostrará la coordenada "X" del mouse en el momento de hacer clic en el componente, que se proporciona al evento Java como datos. Luego se implementa un método para permitir al usuario acceder a estos datos, que es cómo se muestran en la aplicación.

<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## `ElementEventOptions` {#elementeventoptions}

`ElementEventOptions` permite personalizar el comportamiento de los eventos configurando qué datos recopilar, cuándo se activan los eventos y cómo se procesan. Aquí hay un fragmento de código completo que muestra todas las opciones de configuración:

```java
ElementEventOptions options = new ElementEventOptions()
  // Recopilar datos personalizados del cliente
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Ejecutar JavaScript antes de que se active el evento
  .setCode("component.classList.add('processing');")
  
  // Solo activar si se cumplen las condiciones
  .setFilter("component.value.length >= 2")
  
  // Retrasar la ejecución hasta que el usuario deje de escribir (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

addEventListener("input", this::handleSearch, options);
```

### Control de rendimiento {#performance-control}

Controla cuándo y con qué frecuencia se activan los eventos:

**Debouncing** retrasa la ejecución hasta que la actividad se detiene:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Espera 300ms después del último evento
```

**Throttling** limita la frecuencia de ejecución:

```java
options.setThrottle(100); // Se activa como máximo una vez cada 100ms
```

Fases de debouncing disponibles:

- `LEADING`: Se activa de inmediato, luego se espera
- `TRAILING`: Espera un periodo de silencio, luego se activa (por defecto)
- `BOTH`: Se activa de inmediato y después del periodo de silencio

## Fusión de opciones {#options-merging}

Combina configuraciones de eventos de diferentes fuentes usando `mergeWith()`. Las opciones base proporcionan datos comunes para todos los eventos, mientras que las opciones específicas añaden configuraciones especializadas. Las opciones posteriores anulan configuraciones en conflicto.

```java
ElementEventOptions merged = baseOptions.mergeWith(specificOptions);
```

## Interacción con slots {#interacting-with-slots}

Los web components a menudo utilizan slots para permitir que los desarrolladores definan la estructura de un componente desde el exterior. Un slot es un marcador de posición dentro de un web component que puede ser llenado con contenido al usar el componente. En el contexto de la clase `ElementComposite`, los slots proporcionan una forma de personalizar el contenido dentro de un componente. Se proporcionan los siguientes métodos para permitir a los desarrolladores interactuar y manipular slots:

1. **`findComponentSlot()`**: Este método se utiliza para buscar un componente específico en todos los slots en un sistema de componentes. Devuelve el nombre del slot donde se encuentra el componente. Si no se encuentra el componente en ningún slot, se devuelve una cadena vacía.

2. **`getComponentsInSlot()`**: Este método recupera la lista de componentes asignados a un slot dado en un sistema de componentes. Opcionalmente, pasa un tipo de clase específico para filtrar los resultados del método.

3. **`getFirstComponentInSlot()`**: Este método está diseñado para obtener el primer componente asignado al slot. Opcionalmente, pasa un tipo de clase específico para filtrar los resultados de este método.

También es posible utilizar el método `add()` con un parámetro de tipo `String` para especificar el slot deseado en el que añadir el componente pasado.

Estas interacciones permiten a los desarrolladores aprovechar el poder de los web components al proporcionar una API limpia y sencilla para manipular slots, propiedades y manejar eventos dentro de la clase `ElementComposite`.
