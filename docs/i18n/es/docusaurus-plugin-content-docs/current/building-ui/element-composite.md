---
sidebar_position: 4
title: Element Composite
slug: element_composite
_i18n_hash: 78629dd08e77cbd5f111aabb094f8db8
---
<DocChip chip='since' label='23.06' />
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` sirve como una base versátil para gestionar elementos compuestos en aplicaciones webforJ. Su propósito principal es facilitar la interacción con los elementos HTML, representados por la clase `Element`, al proporcionar un enfoque estructurado para manejar propiedades, atributos y oyentes de eventos. Permite la implementación y reutilización de elementos en una aplicación. Use la clase `ElementComposite` al implementar Web Components para su uso en aplicaciones webforJ.

Al usar la clase `ElementComposite`, el método `getElement()` le dará acceso al componente `Element` subyacente. De manera similar, el método `getNodeName()` le dará el nombre de ese nodo en el DOM.

:::tip
Es posible hacer todo con la clase `Element` por sí sola, sin usar la clase `ElementComposite`. Sin embargo, los métodos proporcionados en el `ElementComposite` dan a los usuarios una manera de reutilizar el trabajo que se está realizando.
:::

A lo largo de esta guía, implementaremos el [componente web de código QR Shoelace](https://shoelace.style/components/qr-code) utilizando la clase `ElementComposite`.

<ComponentDemo 
path='/webforj/qrdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRDemoView.java'
height='175px'
/>

## Descriptores de propiedades y atributos {#property-and-attribute-descriptors}

Las propiedades y atributos en los componentes web representan el estado del componente. A menudo se utilizan para gestionar datos o configuración. La clase `ElementComposite` proporciona una forma conveniente de trabajar con propiedades y atributos.

Las propiedades y atributos pueden declararse e inicializarse como miembros `PropertyDescriptor` de la clase `ElementComposite` que se está escribiendo, y luego usarse en el código. Para definir propiedades y atributos, use el método `set()` para establecer el valor de una propiedad. Por ejemplo, `set(PropertyDescriptor<V> property, V value)` establece una propiedad a un valor especificado.

:::info
Las propiedades se acceden y manipulan internamente dentro del código del componente y no se reflejan en el DOM. Los atributos, por otro lado, son parte de la interfaz externa del componente y pueden utilizarse para pasar información a un componente desde el exterior, proporcionando una forma para que elementos o scripts externos configuren el componente.
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

Además de establecer una propiedad, utilice el método `get()` en la clase `ElementComposite` para acceder y leer propiedades. El método `get()` puede recibir un valor `boolean` opcional, que es falso por defecto, para dictar si el método debe hacer un viaje al cliente para recuperar el valor. Esto impacta el rendimiento, pero puede ser necesario si la propiedad puede ser modificada puramente en el cliente.

También se puede pasar un `Type` al método, que dicta a qué se debe convertir el resultado recuperado.

:::tip
Este `Type` no es estrictamente necesario, y añade una capa extra de especificación a medida que se recuperan los datos.
:::

```java
// Ejemplo de propiedad llamada TITLE en una clase ElementComposite
private final PropertyDescriptor<String> TITLE = PropertyDescriptor.property("title", "");
//...
String title = get(TITLE, false, String);
```

En la demostración a continuación, se han añadido propiedades para el código QR basado en la documentación del componente web. Luego se han implementado métodos que permiten a los usuarios obtener y establecer las diversas propiedades que se han implementado.

<ComponentDemo 
path='/webforj/qrproperties?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QRPropertiesView.java'
height='250px'
/>

## Registro de eventos {#event-registration}

Los eventos son una parte crucial de los componentes web, permitiendo la comunicación entre diferentes partes de una aplicación. La clase `ElementComposite` simplifica el registro y manejo de eventos. Para registrar un oyente de eventos, use el método `addEventListener()` para registrar oyentes de eventos para tipos de eventos específicos. Especifique la clase de evento, el oyente y opciones de evento opcionales.

```java
// Ejemplo: Agregar un oyente de evento de clic
addEventListener(ClickEvent.class, event -> {
    // Manejar el evento de clic
});
```

:::info
Los eventos de `ElementComposite` son diferentes a los eventos de `Element`, en que este no permite cualquier clase, sino solo clases de `Event` especificadas.
:::

En la demostración a continuación, se ha creado un evento de clic y luego se ha añadido al componente de código QR. Este evento, cuando se activa, mostrará la coordenada "X" del mouse en el momento de hacer clic en el componente, que se proporciona a través de datos al evento de Java. Luego se implementa un método para permitir al usuario acceder a estos datos, que es cómo se muestra en la aplicación.
<ComponentDemo 
path='/webforj/qrevent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/elementcomposite/QREventView.java'
height='300px'
/>

## Interactuando con Slots {#interacting-with-slots}

Los componentes web a menudo utilizan slots para permitir a los desarrolladores definir la estructura de un componente desde el exterior. Un slot es un marcador de posición dentro de un componente web que puede llenarse con contenido al usar el componente. En el contexto de la clase `ElementComposite`, los slots proporcionan una forma de personalizar el contenido dentro de un componente. Los siguientes métodos se proporcionan para permitir a los desarrolladores interactuar y manipular slots:

1. **`findComponentSlot()`**: Este método se utiliza para buscar un componente específico en todos los slots de un sistema de componentes. Devuelve el nombre del slot donde se encuentra el componente. Si el componente no se encuentra en ningún slot, se devuelve una cadena vacía.

2. **`getComponentsInSlot()`**: Este método recupera la lista de componentes asignados a un slot dado en un sistema de componentes. Opcionalmente, pase un tipo de clase específico para filtrar los resultados del método.

3. **`getFirstComponentInSlot()`**: Este método está diseñado para obtener el primer componente asignado al slot. Opcionalmente, pase un tipo de clase específico para filtrar los resultados de este método.

También es posible utilizar el método `add()` con un parámetro `String` para especificar el slot deseado en el que agregar el componente pasado.

Estas interacciones permiten a los desarrolladores aprovechar el poder de los componentes web al proporcionar una API limpia y sencilla para manipular slots, propiedades y manejar eventos dentro de la clase `ElementComposite`.
