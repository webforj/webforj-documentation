---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2f1ddb4b3375c89dc29d9dbc9cee7303
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` envuelve un elemento HTML personalizado o un [componente web](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Vincula tu clase de Java al `Element` subyacente y te permite trabajar con las propiedades, atributos y eventos de ese elemento a través de Java. Úsalo al integrar componentes web en una aplicación webforJ.

:::tip Cuándo usar `ElementComposite`
Utiliza `ElementComposite` al envolver un componente web de terceros que webforJ no proporciona ya. Si un componente webforJ incorporado cubre el caso de uso (`TextField`, `ColorField`, `Button`, etc.), utiliza ese en su lugar. Para trabajos DOM únicos que no necesitan ser reutilizados, la clase `Element` se puede utilizar directamente sin un envoltorio.
:::

Esta guía demuestra cómo implementar el [componente web de tiempo relativo Web Awesome](https://webawesome.com/docs/components/relative-time/) utilizando la clase `ElementComposite`.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Anotaciones de clase {#class-annotations}

Tres anotaciones aparecen comúnmente en la parte superior de una subclase de `ElementComposite`: `@NodeName` declara la etiqueta HTML que el componente envuelve, y `@JavaScript` y `@StyleSheet` cargan cualquier activo del lado del cliente del que depende el componente web subyacente. `@NodeName` es obligatoria y específica para `ElementComposite`. `@JavaScript` y `@StyleSheet` son anotaciones generales de activos de webforJ y funcionan en cualquier clase, incluyendo vistas, componentes, o la clase `App`.

### `@NodeName` {#nodename}

La anotación `@NodeName` declara la etiqueta HTML que el componente envuelve. webforJ utiliza este nombre al crear el elemento subyacente en el DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

El nombre de la etiqueta debe coincidir con el elemento personalizado registrado en el cliente. Sin esta anotación, el marco no puede determinar qué elemento crear.

Dentro de una subclase, `getNodeName()` lee la etiqueta declarada, y `getElement()` devuelve el `Element` subyacente para que puedas llamar a métodos de nivel DOM en él directamente.

### `@JavaScript` {#javascript}

La anotación `@JavaScript` carga el script que define o registra el componente web subyacente. Colócala en la clase para que el script se cargue solo cuando se utiliza el componente.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Se permiten múltiples anotaciones `@JavaScript`, y webforJ elimina automáticamente las cargas duplicadas. El mismo script no se cargará dos veces si varios componentes dependen de él.

Consulta [Importando archivos JavaScript](../managing-resources/importing-assets#importing-javascript-files) para el conjunto completo de opciones, incluyendo `top`, `attributes`, y el tiempo de carga.

### `@StyleSheet` {#stylesheet}

La anotación `@StyleSheet` carga un archivo CSS del que depende el componente. Es útil para componentes de terceros que envían una hoja de estilos separada, o para agrupar estilos específicos del componente junto al envoltorio.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Para activos empaquetados localmente, utiliza el prefijo `ws://` para referenciar archivos en `resources/static`:

```java
@StyleSheet("ws://components/relative-time.css")
```

Consulta [Importando archivos CSS](../managing-resources/importing-assets#importing-css-files) para el conjunto completo de opciones.

## Descriptores de propiedad y atributo {#property-and-attribute-descriptors}

Las propiedades y atributos representan el estado de un componente web, normalmente conteniendo datos o configuraciones. `ElementComposite` expone ambos a través de `PropertyDescriptor`.

Dos métodos de fábrica en `PropertyDescriptor` producen el descriptor mismo, uno por objetivo de enlace:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` se vincula a una propiedad de JavaScript en el nodo DOM. `PropertyDescriptor.attribute()` se vincula a un atributo HTML. El primer argumento es el nombre que el componente web espera. El segundo es un valor por defecto, que también fija el tipo de Java del descriptor.

Declara el descriptor como un campo privado en el componente, luego lee y escribe a través de él con `set(PropertyDescriptor<V> property, V value)` y `get(PropertyDescriptor<V> property)`.

:::info
Las propiedades son el estado interno en el nodo DOM y no se reflejan en el marcado. Los atributos son el marcado HTML, visibles para scripts y CSS externos.
:::

```java
// Ejemplo de propiedad llamada "title" en una clase ElementComposite
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Ejemplo de atributo llamado "value" en una clase ElementComposite
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "My Title");
set(value, "My Value");
```

Las llamadas anteriores utilizan `set()` directamente para mostrar la forma primitiva. En la práctica, `set()` y `get()` son métodos `protected` en `ElementComposite`. Son la capa primitiva que sincroniza valores de Java con el elemento subyacente, no la API pública que los consumidores llaman. El patrón previsto es mantener el `PropertyDescriptor` privado y escribir métodos públicos `setX()` y `getX()` que deleguen en los primitivos.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // primitivo protegido
    return this;
  }

  public String getHeading() {
    return get(heading);     // primitivo protegido
  }
}
```

Una sola llamada a `set(descriptor, value)` hace tres cosas a la vez. Empuja el valor al cliente a través de `setProperty()` para propiedades, o `setAttribute()` para atributos. Almacena el valor en una caché local del lado del servidor, un mapa por cada instancia de componente. Y registra el tipo en tiempo de ejecución junto con el valor, de modo que las llamadas posteriores a `get()` sepan cómo deserializar.

Esa caché local es la razón por la cual `get()` puede ser barato por defecto. `get(descriptor)` devuelve el valor en caché de la tienda del lado del servidor sin llamada de red, porque cada `set()` mantiene la caché sincronizada con el cliente. El segundo argumento opcional `boolean` controla si se debe omitir la caché y leer del navegador en su lugar.

```java
String cached = get(heading);            // lee de la caché del lado del servidor
String live = get(heading, true);        // obliga a una lectura del navegador
```

Establece `fromClient` en verdadero cuando el valor puede cambiar en el cliente sin el conocimiento del servidor, como un valor tipeado en `<input>`. Para propiedades controladas por el servidor, el valor por defecto evita un viaje de ida y vuelta.

El tercer argumento opcional es un `java.lang.reflect.Type` y controla cómo se deserializa el resultado. webforJ resuelve el tipo en este orden: el argumento explícito `Type` si se pasa, luego el tipo en tiempo de ejecución registrado por un `set()` previo en el mismo descriptor, luego `Object.class`. En la práctica, el tipo registrado por un `set()` anterior es suficiente, por lo que el tercer argumento generalmente puede omitirse. Se necesita cuando la clase registrada pierde información de la que el deserializador depende, como un tipo parametrizado como `List<String>` cuya clase en tiempo de ejecución es solo `ArrayList`.

La demostración a continuación agrega propiedades para el tiempo relativo basándose en la documentación del componente web y las expone a través de getters y setters. Cada fila en el feed de actividad utiliza diferentes valores `format` y `numeric` para mostrar cómo el mismo componente se renderiza bajo configuraciones variadas.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Propiedades versus atributos {#properties-versus-attributes}

Aunque `PropertyDescriptor.property()` y `PropertyDescriptor.attribute()` parecen intercambiables, apuntan a diferentes partes del elemento subyacente. Elegir el incorrecto resulta en valores que silenciosamente fallan en aplicarse.

Las propiedades son propiedades de objeto de JavaScript en el nodo DOM. Pueden contener cualquier tipo, incluyendo cadenas, booleanos, números, objetos y arreglos, y representan el estado actual en tiempo de ejecución del elemento. Establecer una propiedad es una asignación directa de JavaScript.

Los atributos son marcado HTML. Viven en la etiqueta de apertura del elemento, son siempre cadenas, y representan la configuración inicial del elemento. Establecer un atributo desencadena una mutación DOM y una conversión de cadena.

Para algunos casos, los dos permanecen en sincronía. Para otros divergen. El `value` de un `<input>` es el ejemplo clásico: el atributo `value` es el valor inicial, mientras que la propiedad `value` es el valor actual que el usuario ha tipeado. Leer el atributo después de que el usuario tipea devuelve el marcado original, pero leer la propiedad devuelve el contenido actual del campo.

Usa **propiedades** para:

- **Estado en tiempo de ejecución que cambia frecuentemente**: contadores, selecciones actuales, valores tipeados
- **Tipos no cadena**: booleanos, números, objetos, arreglos
- **Actualizaciones sensibles al rendimiento**: las propiedades omiten la conversión de cadena requerida para atributos

Usa **atributos** para:

- **Configuración inicial**: configuraciones que el componente lee una vez cuando se conecta
- **Selectores CSS**: valores que deseas dirigir con selectores como `[disabled]` o `[variant="danger"]`
- **Ganchos de accesibilidad**: `aria-label`, `role`, y otros atributos ARIA
- **Configuraciones similares a cadenas que raramente cambian**

Al envolver un componente web de terceros, consulta la documentación del componente para confirmar qué nombre mapea a una propiedad y cuál a un atributo. Usar `PropertyDescriptor.attribute()` para algo que el componente expone solo como propiedad no funcionará, y lo mismo es cierto al revés. El componente ignorará silenciosamente el valor.

### Tipado de propiedades {#typing-properties}

Un descriptor está parametrizado por el tipo de Java de su valor. La sintaxis completa de declaración es:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

El parámetro genérico `<T>` declara el tipo del valor. El tipo en tiempo de ejecución del valor por defecto también fija `T`, por lo que raramente es necesario especificar explícitamente el argumento genérico. webforJ utiliza `T` para serializar y deserializar valores al comunicarse con el cliente.

```java
private final PropertyDescriptor<String> label =
    PropertyDescriptor.property("label", "");

private final PropertyDescriptor<Boolean> disabled =
    PropertyDescriptor.property("disabled", false);

private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

private final PropertyDescriptor<Double> step =
    PropertyDescriptor.property("step", 1.0);
```

La serialización es automática para primitivos, sus equivalentes encapsulados, y `String`. Para tipos complejos, el valor se serializa como JSON antes de asignarlo a la propiedad en el cliente.

### Validando valores {#validating-values}

Valida los valores en el setter antes de llamar a `set()`. El setter es el punto natural de enforcement porque cada mutación fluye a través de él.

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max must be non-negative");
  }
  set(max, value);
  return this;
}
```

Para referencias anulables, utiliza `Objects.requireNonNull()` para que la falla surja en la frontera en lugar de más tarde en la canalización de renderizado.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Evita validar en `get()`. Las lecturas deben mantenerse baratas y consistentes.

### Propiedades de estilo enum {#enum-style-properties}

La mayoría de los componentes web esperan valores de cadena en minúsculas o kebab-case para propiedades similares a enum (`theme="primary"`, `expanse="xs"`). webforJ utiliza Gson para serializar enums, pero la representación predeterminada de Gson es el nombre constante en mayúsculas. Anota cada constante con `@SerializedName` para que el valor serializado coincida con lo que el componente web espera.

```java
import com.google.gson.annotations.SerializedName;

public enum Variant {
  @SerializedName("primary")
  PRIMARY,

  @SerializedName("secondary")
  SECONDARY,

  @SerializedName("danger")
  DANGER
}
```

Declara el descriptor con el tipo enum y utiliza el enum directamente en el setter y getter.

```java
private final PropertyDescriptor<Variant> variant =
    PropertyDescriptor.property("variant", Variant.PRIMARY);

public MyButton setVariant(Variant value) {
  set(variant, value);
  return this;
}

public Variant getVariant() {
  return get(variant);
}
```

Este es el mismo patrón que utilizan los componentes incorporados de webforJ para `Theme`, `Expanse`, y enums similares. La API pública de Java se mantiene segura en tipos, y el valor que recibe el componente web es la cadena de `@SerializedName`.

### Probando propiedades {#testing-properties}

`PropertyDescriptorTester` valida que cada `PropertyDescriptor` en un componente esté correctamente conectado. Escanea la clase para campos de descriptor, llama a cada setter con el valor por defecto, y compara el resultado con lo que devuelve el getter. El tester captura errores de integración antes de que lleguen a una aplicación en ejecución: un setter que escribe en el descriptor incorrecto, un getter que lee una propiedad diferente, un valor por defecto que no hace round-trip, o un accesor faltante para un descriptor declarado.

Una prueba base para un componente se ve así:

```java
import com.webforj.component.element.PropertyDescriptorTester;
import org.junit.jupiter.api.Test;

class CardTest {

  @Test
  void validateProperties() {
    Card component = new Card();
    PropertyDescriptorTester.run(Card.class, component);
  }
}
```

#### Excluyendo propiedades {#excluding-properties}

Algunos descriptores no siguen convenciones estándar de getter y setter, o dependen de estado externo que la prueba no puede satisfacer. Anótalos con `@PropertyExclude` para omitirlos.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Nombres de getter y setter personalizados {#custom-getter-and-setter-names}

Si un descriptor utiliza nombres de accesor no estándar, decláralos con `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

El parámetro `target` acepta una clase cuando los accesores viven en algún lugar que no sea el componente en sí.

Para más detalles sobre la superficie de prueba, consulta [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de preocupación {#concern-interfaces}

Las interfaces de preocupación otorgan a un componente de subclase de `ElementComposite` capacidades sin escribir la implementación tú mismo. Las interfaces reenvían llamadas al elemento subyacente. Implementa las que el componente debe soportar, parametrizadas con el tipo de subclase para que el encadenado devuelva el componente:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // No se necesita implementación.
}

MyBadge badge = new MyBadge()
    .setText("Nuevo")
    .addClassName("highlight")
    .setStyle("color", "var(--dwc-color-primary)");
```

Las tres interfaces anteriores cubren todo lo que `MyBadge` necesita sin cuerpos de método en la clase. `HasText` expone `setText()` y escribe en el contenido de texto del elemento. `HasClassName` expone `addClassName()`, que permite que la insignia sea dirigida desde CSS. `HasStyle` expone `setStyle()` para el estilo en línea.

Para el conjunto completo de interfaces disponibles y lo que cada una proporciona, consulta [Interfaces de preocupación](./component-fundamentals#concern-interfaces) en el artículo Comprendiendo los Componentes. Si un reenvío predeterminado no coincide con lo que expone el elemento envuelto, anula el método en la subclase.

## Eventos {#events}

### Registro de eventos {#event-registration}

Los componentes web despachan eventos DOM cuando ocurre algo en el navegador. Para reaccionar desde Java, escucha esos eventos con `addEventListener()`. El conjunto de eventos que un componente despacha varía, así que consulta la documentación del componente para conocer los nombres y cargas disponibles.

`ElementComposite` admite debouncing, throttling, filtrado y datos de eventos personalizados en los oyentes registrados.

Registra oyentes de eventos utilizando el método `addEventListener()`:

```java
// Ejemplo: Agregar un oyente de eventos de clic
addEventListener(ElementClickEvent.class, event -> {
  // Manejar el evento de clic
});
```

:::info
`ElementComposite` solo acepta clases de eventos anotadas con `@EventName`, a diferencia de `Element`, que acepta cualquier nombre de evento en cadena.
:::

### Clases de eventos incorporadas {#built-in-event-classes}

`ElementClickEvent` es la única clase de evento incorporada con la que se envía `ElementComposite`. Superficia eventos de clic del mouse en el elemento subyacente con accesores tipados para coordenadas (`getClientX()`, `getClientY()`), información del botón (`getButton()`) y teclas modificadoras (`isCtrlKey()`, `isShiftKey()`, y así sucesivamente).

Para exponer el manejo de clics en la API pública de una subclase, implementa la interfaz de preocupación `HasElementClickListener<T>`. Esta proporciona métodos predeterminados `onClick()` y `addClickListener()` que delegan en el primitivo protegido `addEventListener()`.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() y addClickListener() ahora están disponibles en MyBadge
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Para cualquier otro evento que despache el componente web subyacente, define una clase de evento personalizada. Consulta [Clases de eventos personalizados](#custom-event-classes).

### Cargas de eventos {#event-payloads}

Los eventos transportan datos desde el cliente a tu código Java. Accede a estos datos a través de `getData()` para obtener datos de eventos crudos o utiliza métodos tipados cuando estén disponibles en las clases de eventos incorporadas. Consulta la [guía de eventos](../building-ui/events) para obtener más información sobre el manejo eficiente de cargas.

### Clases de eventos personalizadas {#custom-event-classes}

Define clases de eventos personalizadas con `@EventName` y `@EventOptions` para capturar datos del lado del cliente en un evento Java tipado. Utiliza esto cuando el controlador de Java necesite valores del navegador.

`@EventName` vincula la clase Java al evento que el componente despacha en el navegador, por lo que una clase anotada con `@EventName("change")` se activa cada vez que el elemento subyacente emite `change`. `@EventOptions` controla lo que viaja de vuelta con ese evento. Cada `@EventData` dentro de él empareja una clave con una expresión de JavaScript evaluada contra el evento DOM. El resultado está disponible en la clase de evento Java a través de `getData().get(key)`.

El formulario de revisión de producto a continuación utiliza este patrón con [`wa-rating`](https://webawesome.com/docs/components/rating/). El `ChangeEvent` personalizado transporta el valor de la calificación como un `double` tipado, y el oyente lo utiliza para habilitar el botón de envío:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Opciones de evento {#event-options}

`ElementEventOptions` configura la carga del evento, temporización de debounce o throttle, expresiones de filtrado, y código de pre-ejecución. El fragmento a continuación muestra las opciones:

```java
ElementEventOptions options = new ElementEventOptions()
  // Colectar datos personalizados del cliente
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Ejecutar JavaScript antes de que se dispare el evento
  .setCode("component.classList.add('processing');")

  // Solo disparar si se cumplen las condiciones
  .setFilter("component.value.length >= 2")

  // Retrasar la ejecución hasta que el usuario deje de escribir (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Aplica estas opciones al registrar un oyente para una clase de evento personalizada
// (consulta la sección Clases de eventos personalizados arriba para saber cómo definir una):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` expone solo la forma basada en clases `addEventListener(Class, listener, options)`. Úsalo con una clase de evento anotada con `@EventName`. Para registrar un nombre de evento de cadena directamente, llama a `getElement().addEventListener("input", listener, options)`.
:::

#### Control de rendimiento {#performance-control}

**Debouncing** retrasa la ejecución hasta que la actividad se detiene:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Espera 300ms después del último evento
```

Fases de debouncing disponibles:

- `LEADING`: Dispara inmediatamente, luego espera
- `TRAILING`: Espera un período de quietud, luego dispara (predeterminado)
- `BOTH`: Dispara inmediatamente y después del período de quietud

**Throttling** limita la frecuencia de ejecución:

```java
options.setThrottle(100); // Dispara como máximo una vez por cada 100ms
```

## Interactuando con slots {#interacting-with-slots}

Los slots son marcadores de posición dentro de un componente web que los usuarios llenan con contenido. El componente web declara sus slots en su plantilla con `<slot>` o `<slot name="...">`, y el envoltorio expone métodos que colocan componentes Java en esos slots.

Para agregar contenido a los slots, extiende `ElementCompositeContainer` en lugar de `ElementComposite`. El contenedor lleva la misma maquinaria de propiedades y atributos más los métodos necesarios para agregar hijos. Los hijos añadidos a través de `add()` van al slot por defecto. Los hijos añadidos a través de `getElement().add(slotName, components)` van al slot nombrado.

```java
@NodeName("my-dialog")
public class Dialog extends ElementCompositeContainer {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Dialog setHeading(String value) {
    set(heading, value);
    return this;
  }

  public Dialog addToFooter(Component... components) {
    getElement().add("footer", components);
    return this;
  }
}
```

La demostración a continuación muestra dos tarjetas de precios construidas con [`wa-card`](https://webawesome.com/docs/components/card/), poblando los slots de `header`, por defecto y `footer` desde Java:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspeccionando el contenido de los slots {#inspecting-slot-contents}

El `Element` subyacente (accedido a través de `getElement()`) proporciona métodos para leer lo que actualmente está asignado a los slots:

- **`findComponentSlot()`**: busca en todos los slots un componente específico y devuelve el nombre del slot que lo contiene, o una cadena vacía si el componente no está en ningún slot.
- **`getComponentsInSlot()`**: devuelve la lista de componentes asignados a un slot dado. Opcionalmente toma un tipo de clase para filtrar los resultados.
- **`getFirstComponentInSlot()`**: devuelve el primer componente asignado a un slot. Opcionalmente toma un tipo de clase para filtrar.
