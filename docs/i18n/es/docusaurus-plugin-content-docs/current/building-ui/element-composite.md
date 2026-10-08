---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 2a742b2589b096aff73a1fcb67e041c1
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` envuelve un elemento HTML personalizado o un [web component](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Vincula tu clase Java al `Element` subyacente y te permite trabajar con las propiedades, atributos y eventos de ese elemento a través de Java. Utilízalo al integrar web components en una aplicación webforJ.

:::tip Cuándo usar `ElementComposite`
Utiliza `ElementComposite` al envolver un web component de terceros que webforJ no proporciona. Si un componente incorporado de webforJ cubre el caso de uso (`TextField`, `ColorField`, `Button`, etc.), usa ese en su lugar. Para trabajos únicos en el DOM que no necesitan ser reutilizados, la clase `Element` se puede usar directamente sin un envoltorio.
:::

Esta guía demuestra cómo implementar el [web component de tiempo relativo Web Awesome](https://webawesome.com/docs/components/relative-time/) usando la clase `ElementComposite`.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Anotaciones de clase {#class-annotations}

Tres anotaciones comúnmente aparecen en la parte superior de un subclase de `ElementComposite`: `@NodeName` declara la etiqueta HTML que el componente envuelve, y `@JavaScript` y `@StyleSheet` cargan los activos del lado del cliente de los que depende el web component subyacente. `@NodeName` es obligatoria y específica de `ElementComposite`. `@JavaScript` y `@StyleSheet` son anotaciones generales de activos de webforJ y funcionan en cualquier clase, incluidas vistas, componentes o la clase `App`.

### `@NodeName` {#nodename}

La anotación `@NodeName` declara la etiqueta HTML que el componente envuelve. webforJ utiliza este nombre al crear el elemento subyacente en el DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

El nombre de la etiqueta debe coincidir con el elemento personalizado registrado en el cliente. Sin esta anotación, el marco no puede determinar qué elemento crear.

Dentro de una subclase, `getNodeName()` lee la etiqueta declarada, y `getElement()` devuelve el `Element` subyacente para que puedas llamar a los métodos del nivel DOM directamente sobre él.

### `@JavaScript` {#javascript}

La anotación `@JavaScript` carga el script que define o registra el web component subyacente. Colócala en la clase para que el script se cargue solo cuando se utiliza el componente.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Se permiten múltiples anotaciones `@JavaScript`, y webforJ deduplica las cargas automáticamente. El mismo script no se cargará dos veces si varios componentes dependen de él.

Consulta [Importando archivos JavaScript](../managing-resources/importing-assets#importing-javascript-files) para conocer el conjunto completo de opciones, incluidos `top`, `attributes` y el momento de carga.

### `@StyleSheet` {#stylesheet}

La anotación `@StyleSheet` carga un archivo CSS del que depende el componente. Es útil para componentes de terceros que envían una hoja de estilo separada, o para agrupar estilos específicos del componente junto al envoltorio.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Para activos empaquetados localmente, utiliza el prefijo `ws://` para hacer referencia a archivos en `resources/static`:

```java
@StyleSheet("ws://components/relative-time.css")
```

Consulta [Importando archivos CSS](../managing-resources/importing-assets#importing-css-files) para obtener el conjunto completo de opciones.

## Descriptores de propiedades y atributos {#property-and-attribute-descriptors}

Las propiedades y atributos representan el estado de un web component, generalmente sosteniendo datos o configuración. `ElementComposite` expone ambos a través de `PropertyDescriptor`.

Dos métodos de fábrica en `PropertyDescriptor` producen el descriptor en sí, uno por objetivo de vinculación:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` se vincula a una propiedad JavaScript en el nodo DOM. `PropertyDescriptor.attribute()` se vincula a un atributo HTML. El primer argumento es el nombre que el web component espera. El segundo es un valor predeterminado, que también fija el tipo Java del descriptor.

Declara el descriptor como un campo privado en el componente, luego lee y escribe a través de él con `set(PropertyDescriptor<V> property, V value)` y `get(PropertyDescriptor<V> property)`.

:::info
Las propiedades son el estado interno en el nodo DOM y no se reflejan en el marcado. Los atributos son marcado HTML, visibles para scripts externos y CSS.
:::

```java
// Ejemplo de propiedad llamada "title" en una clase ElementComposite
private final PropertyDescriptor<String> title = PropertyDescriptor.property("title", "");
// Ejemplo de atributo llamado "value" en una clase ElementComposite
private final PropertyDescriptor<String> value = PropertyDescriptor.attribute("value", "");
//...
set(title, "Mi Título");
set(value, "Mi Valor");
```

Las llamadas anteriores utilizan `set()` directamente para mostrar la forma primitiva. En la práctica, `set()` y `get()` son métodos `protected` en `ElementComposite`. Son la capa primitiva que sincroniza los valores Java con el elemento subyacente, no la API pública a la que los consumidores llaman. El patrón previsto es mantener el `PropertyDescriptor` privado y escribir métodos públicos `setX()` y `getX()` que deleguen a las primitivas.

```java
@NodeName("my-card")
public class Card extends ElementComposite {

  private final PropertyDescriptor<String> heading =
      PropertyDescriptor.property("heading", "");

  public Card setHeading(String value) {
    set(heading, value);     // primitiva protegida
    return this;
  }

  public String getHeading() {
    return get(heading);     // primitiva protegida
  }
}
```

Una sola llamada a `set(descriptor, value)` hace tres cosas a la vez. Envía el valor al cliente a través de `setProperty()` para propiedades, o `setAttribute()` para atributos. Almacena el valor en un caché local del lado del servidor, un mapa por instancia de componente. Y registra el tipo en tiempo de ejecución junto al valor, para que las llamadas posteriores a `get()` sepan cómo deserializar.

Ese caché local es la razón por la que `get()` puede ser barato por defecto. `get(descriptor)` devuelve el valor en caché del almacén del lado del servidor sin llamada de red, porque cada `set()` mantiene el caché sincronizado con el cliente. El segundo argumento opcional `boolean` controla si se omite el caché y se lee desde el navegador en su lugar.

```java
String cached = get(heading);            // lee del caché del lado del servidor
String live = get(heading, true);        // fuerza una lectura desde el navegador
```

Establece `fromClient` en true cuando el valor puede cambiar en el cliente sin el conocimiento del servidor, como un valor de `<input>` escrito. Para propiedades impulsadas por el servidor, el valor predeterminado evita un viaje de ida y vuelta.

El tercer argumento opcional es un `java.lang.reflect.Type` y controla cómo se deserializa el resultado. webforJ resuelve el tipo en este orden: el argumento `Type` explícito si se pasa, luego el tipo en tiempo de ejecución registrado por un `set()` anterior en el mismo descriptor, luego `Object.class`. En la práctica, el tipo registrado por un `set()` anterior es suficiente, por lo que el tercer argumento generalmente se puede omitir. Es necesario cuando la clase registrada pierde información de la que el deserializador depende, como un tipo parametrizado como `List<String>` cuya clase de tiempo de ejecución es solo `ArrayList`.

La demostración a continuación agrega propiedades para tiempo relativo basadas en la documentación del web component y las expone a través de getters y setters. Cada fila en el feed de actividad utiliza diferentes valores de `format` y `numeric` para mostrar cómo el mismo componente se renderiza bajo configuraciones variadas.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Propiedades versus atributos {#properties-versus-attributes}

Aunque `PropertyDescriptor.property()` y `PropertyDescriptor.attribute()` parecen intercambiables, tienen como objetivo diferentes partes del elemento subyacente. Elegir el incorrecto resulta en valores que fallan silenciosamente en aplicarse.

Las propiedades son propiedades de objeto JavaScript en el nodo DOM. Pueden contener cualquier tipo, incluidos cadenas, booleanos, números, objetos y matrices, y representan el estado en tiempo de ejecución actual del elemento. Establecer una propiedad es una asignación directa de JavaScript.

Los atributos son marcado HTML. Viven en la etiqueta de apertura del elemento, siempre son cadenas y representan la configuración inicial del elemento. Establecer un atributo desencadena una mutación del DOM y una conversión de cadena.

Para algunos casos, los dos permanecen sincronizados. Para otros divergen. El `value` de un `<input>` es el ejemplo clásico: el atributo `value` es el valor inicial, mientras que la propiedad `value` es el valor actual que el usuario ha escrito. Leer el atributo después de que el usuario escriba devuelve el marcado original, pero leer la propiedad devuelve el contenido actual del campo.

Usa **propiedades** para:

- **Estado de ejecución que cambia con frecuencia**: contadores, selecciones actuales, valores escritos
- **Tipos no cadena**: booleanos, números, objetos, matrices
- **Actualizaciones sensibles al rendimiento**: las propiedades omiten la conversión de cadena requerida para los atributos

Usa **atributos** para:

- **Configuración inicial**: configuraciones que el componente lee una vez cuando se conecta
- **Selectores CSS**: valores que deseas dirigir con selectores como `[disabled]` o `[variant="danger"]`
- **Hooks de accesibilidad**: `aria-label`, `role` y otros atributos ARIA
- **Configuraciones similares a cadenas que rara vez cambian**

Al envolver un web component de terceros, consulta la documentación del componente para confirmar qué nombre corresponde a una propiedad y cuál a un atributo. Usar `PropertyDescriptor.attribute()` para algo que el componente expone solo como una propiedad no funcionará, y lo mismo es cierto al revés. El componente ignorará silenciosamente el valor.

### Tipificación de propiedades {#typing-properties}

Un descriptor está parametrizado por el tipo Java de su valor. La sintaxis completa de declaración es:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

El parámetro genérico `<T>` declara el tipo del valor. El tipo en tiempo de ejecución del valor predeterminado también fija `T`, por lo que el argumento genérico rara vez necesita ser especificado explícitamente. webforJ utiliza `T` para serializar y deserializar valores al comunicarse con el cliente.

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

La serialización es automática para primitivos, sus equivalentes empaquetados y `String`. Para tipos complejos, el valor se serializa como JSON antes de asignarse a la propiedad en el cliente.

### Validación de valores {#validating-values}

Valida los valores en el setter antes de llamar a `set()`. El setter es el punto natural de enforcement porque cada mutación pasa a través de él.

```java
private final PropertyDescriptor<Integer> max =
    PropertyDescriptor.property("max", 100);

public Slider setMax(int value) {
  if (value < 0) {
    throw new IllegalArgumentException("max debe ser no negativo");
  }
  set(max, value);
  return this;
}
```

Para referencias que pueden ser nulas, usa `Objects.requireNonNull()` para que el fallo surja en el límite en lugar de más tarde en el pipeline de renderización.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading no puede ser nulo");
  set(heading, value);
  return this;
}
```

Evita validar en `get()`. Las lecturas deben permanecer baratas y consistentes.

### Propiedades estilo Enum {#enum-style-properties}

La mayoría de los web components esperan valores de cadena en minúsculas o en kebab-case para propiedades de tipo enum (`theme="primary"`, `expanse="xs"`). webforJ utiliza Gson para serializar enums, pero la representación predeterminada de Gson es el nombre de constante en mayúsculas. Anota cada constante con `@SerializedName` para que el valor serializado coincida con lo que espera el web component.

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

Declara el descriptor con el tipo enum y usa directamente el enum en el setter y el getter.

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

Este es el mismo patrón que usan los componentes integrados de webforJ para `Theme`, `Expanse`, y enums similares. La API pública de Java se mantiene segura por tipo, y el valor que recibe el web component es la cadena de `@SerializedName`.

### Pruebas de propiedades {#testing-properties}

`PropertyDescriptorTester` valida que cada `PropertyDescriptor` en un componente esté conectado correctamente. Escanea la clase en busca de campos de descriptor, llama a cada setter con el valor predeterminado y compara el resultado contra lo que devuelve el getter. El probador captura errores de integración antes de que lleguen a una aplicación en ejecución: un setter que escribe en el descriptor incorrecto, un getter que lee una propiedad diferente, un valor predeterminado que no ronda el viaje, o un accessor faltante para un descriptor declarado.

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

#### Excluir propiedades {#excluding-properties}

Algunos descriptores no siguen las convenciones estándar de getter y setter, o dependen de un estado externo que la prueba no puede satisfacer. Anótalos con `@PropertyExclude` para omitirlos.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Nombres de getter y setter personalizados {#custom-getter-and-setter-names}

Si un descriptor utiliza nombres de accessor no estándar, decláralos con `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

El parámetro `target` acepta una clase cuando los accessors viven en otro lugar que no sea el componente en sí.

Para obtener más detalles sobre la superficie de prueba, consulta [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de preocupación {#concern-interfaces}

Las interfaces de preocupación dan a un componente subclase de `ElementComposite` capacidades sin escribir la implementación tú mismo. Las interfaces reenvían las llamadas al elemento subyacente. Implementa las que el componente debería soportar, parametrizadas con el tipo de subclase para que el encadenamiento devuelva el componente:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // No se necesita implementación.
}

MyBadge badge = new MyBadge()
    .setText("Nuevo")
    .addClassName("resaltar")
    .setStyle("color", "var(--dwc-color-primary)");
```

Las tres interfaces anteriores cubren todo lo que MyBadge necesita sin ningún cuerpo de método en la clase. `HasText` expone `setText()` y escribe en el contenido de texto del elemento. `HasClassName` expone `addClassName()`, lo que permite que la insignia sea dirigida desde CSS. `HasStyle` expone `setStyle()` para la estilización en línea.

Para el conjunto completo de interfaces disponibles y lo que cada una proporciona, consulta [Interfaces de preocupación](./component-fundamentals#concern-interfaces) en el artículo Entendiendo Componentes. Si un reenvío predeterminado no coincide con lo que expone el elemento envuelto, sobrescribe el método en la subclase.

## Eventos {#events}

### Registro de eventos {#event-registration}

Los web components despachan eventos DOM cuando algo sucede en el navegador. Para reaccionar desde Java, escucha esos eventos con `addEventListener()`. El conjunto de eventos que un componente despacha varía, así que verifica la documentación del componente para conocer los nombres y cargas disponibles.

`ElementComposite` soporta debouncing, throttling, filtrado y datos de eventos personalizados en los listeners registrados.

Registra listeners de eventos usando el método `addEventListener()`:

```java
// Ejemplo: Agregando un listener de eventos de clic
addEventListener(ElementClickEvent.class, event -> {
  // Manejar el evento de clic
});
```

:::info
`ElementComposite` solo acepta clases de eventos anotadas con `@EventName`, a diferencia de `Element`, que acepta cualquier nombre de evento de cadena.
:::

### Clases de eventos integradas {#built-in-event-classes}

`ElementClickEvent` es la única clase de evento integrada que `ElementComposite` ofrece. Supervisa los eventos de clic del mouse en el elemento subyacente con accesores tipados para coordenadas (`getClientX()`, `getClientY()`), información del botón (`getButton()`) y teclas modificadoras (`isCtrlKey()`, `isShiftKey()`, etc.).

Para exponer el manejo de clics en la API pública de una subclase, implementa la interfaz de preocupación `HasElementClickListener<T>`. Proporciona métodos predeterminados `onClick()` y `addClickListener()` que delegan en la primitiva protegida `addEventListener()`.

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

Para cualquier otro evento que el web component subyacente despache, define una clase de evento personalizada. Consulta [Clases de eventos personalizadas](#custom-event-classes).

### Cargas de eventos {#event-payloads}

Los eventos transportan datos del cliente a tu código Java. Accede a estos datos a través de `getData()` para datos de eventos en bruto o utiliza métodos tipados cuando estén disponibles en las clases de eventos integradas. Consulta la [guía de eventos](../building-ui/events) para obtener más información sobre el manejo eficiente de cargas.

### Clases de eventos personalizadas {#custom-event-classes}

Define clases de eventos personalizadas con `@EventName` y `@EventOptions` para capturar datos del lado del cliente en un evento Java tipado. Utiliza esto cuando el controlador de Java necesite valores del navegador.

`@EventName` vincula la clase Java al evento que el componente despacha en el navegador, por lo que una clase anotada con `@EventName("change")` se activa cada vez que el elemento subyacente emite `change`. `@EventOptions` controla lo que viaja de vuelta con ese evento. Cada `@EventData` dentro de él empareja una clave con una expresión de JavaScript evaluada contra el evento DOM. El resultado está disponible en la clase de evento Java a través de `getData().get(key)`.

El formulario de revisión de productos a continuación utiliza este patrón con [`wa-rating`](https://webawesome.com/docs/components/rating/). El `ChangeEvent` personalizado lleva el valor de calificación como un `double` tipado, y el listener lo utiliza para habilitar el botón de envío:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Opciones de eventos {#event-options}

`ElementEventOptions` configura la carga del evento, el tiempo de debounce o throttle, expresiones de filtro y código de pre-ejecución. El siguiente snippet muestra las opciones:

```java
ElementEventOptions options = new ElementEventOptions()
  // Recoger datos personalizados del cliente
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Ejecutar JavaScript antes de que se dispare el evento
  .setCode("component.classList.add('processing');")

  // Solo disparar si se cumplen las condiciones
  .setFilter("component.value.length >= 2")

  // Retrasar la ejecución hasta que el usuario deje de escribir (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Aplica estas opciones al registrar un listener para una clase de evento personalizada
// (consulta la sección Clases de eventos personalizadas anterior para ver cómo definir una):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` solo expone la forma basada en clases `addEventListener(Class, listener, options)`. Utilízala con una clase de evento anotada con `@EventName`. Para registrarte contra un nombre de evento de cadena directamente, llama a `getElement().addEventListener("input", listener, options)`.
:::

#### Control de rendimiento {#performance-control}

**Debouncing** retrasa la ejecución hasta que la actividad se detiene:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Espera 300ms después del último evento
```

Las fases de debounce disponibles:

- `LEADING`: Dispara inmediatamente, luego espera
- `TRAILING`: Espera un período de silencio, luego dispara (predeterminado)
- `BOTH`: Dispara inmediatamente y después del período de silencio

**Throttling** limita la frecuencia de ejecución:

```java
options.setThrottle(100); // Dispara como máximo una vez cada 100ms
```

## Interacción con slots {#interacting-with-slots}

Los slots son marcadores de posición dentro de un web component que los usuarios llenan con contenido. El web component declara sus slots en su plantilla con `<slot>` o `<slot name="...">`, y el envoltorio expone métodos que colocan componentes Java en esos slots.

Para agregar contenido a los slots, extiende `ElementCompositeContainer` en lugar de `ElementComposite`. El contenedor lleva la misma maquinaria de propiedad y atributo más los métodos necesarios para agregar hijos. Los hijos agregados a través de `add()` van al slot predeterminado. Los hijos agregados a través de `getElement().add(slotName, components)` van al slot nombrado.

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

La demostración a continuación muestra dos tarjetas de precios construidas con [`wa-card`](https://webawesome.com/docs/components/card/), poblando los slots de `header`, predeterminado y `footer` desde Java:

<ComponentDemo
path='/webforj/card'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Inspeccionando el contenido de los slots {#inspecting-slot-contents}

El `Element` subyacente (accedido a través de `getElement()`) proporciona métodos para leer lo que actualmente está asignado a los slots:

- **`findComponentSlot()`**: busca en todos los slots un componente específico y devuelve el nombre del slot que lo contiene, o una cadena vacía si el componente no está en ningún slot.
- **`getComponentsInSlot()`**: devuelve la lista de componentes asignados a un slot dado. Opcionalmente toma un tipo de clase para filtrar los resultados.
- **`getFirstComponentInSlot()`**: devuelve el primer componente asignado a un slot. Opcionalmente toma un tipo de clase para filtrar.
