---
sidebar_position: 6
title: Element Composite
description: >-
  Wrap a custom HTML element or third-party web component in Java with
  ElementComposite, exposing its properties, attributes, and events through the
  Java API.
_i18n_hash: 277c6e7e84197ab515cae210cd8207b0
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` envuelve un elemento HTML personalizado o un [componente web](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Une tu clase Java al `Element` subyacente y te permite trabajar con las propiedades, atributos y eventos de ese elemento a través de Java. Úsalo cuando integres componentes web en una aplicación webforJ.

:::tip Cuándo usar `ElementComposite`
Utiliza `ElementComposite` cuando envuelvas un componente web de terceros que webforJ no proporciona. Si un componente webforJ integrado cubre el caso de uso (`TextField`, `ColorField`, `Button`, etc.), úsalo en su lugar. Para trabajo en DOM único que no necesita ser reutilizado, la clase `Element` se puede usar directamente sin un envoltorio.
:::

Esta guía demuestra cómo implementar el [componente web de tiempo relativo Web Awesome](https://webawesome.com/docs/components/relative-time/) utilizando la clase `ElementComposite`.

<ComponentDemo
path='/webforj/relativetime'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Anotaciones de clase {#class-annotations}

Tres anotaciones aparecen comúnmente en la parte superior de una subclase de `ElementComposite`: `@NodeName` declara la etiqueta HTML que envuelve el componente, y `@JavaScript` y `@StyleSheet` cargan cualquier recurso del lado del cliente del que dependa el componente web subyacente. `@NodeName` es obligatoria y específica de `ElementComposite`. `@JavaScript` y `@StyleSheet` son anotaciones generales de recursos de webforJ y funcionan en cualquier clase, incluidas vistas, componentes o la clase `App`.

### `@NodeName` {#nodename}

La anotación `@NodeName` declara la etiqueta HTML que envuelve el componente. webforJ utiliza este nombre al crear el elemento subyacente en el DOM.

```java
@NodeName("wa-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

El nombre de la etiqueta debe coincidir con el elemento personalizado registrado en el cliente. Sin esta anotación, el marco no puede determinar qué elemento crear.

Dentro de una subclase, `getNodeName()` lee nuevamente la etiqueta declarada, y `getElement()` devuelve el `Element` subyacente para que puedas llamar a métodos de nivel DOM directamente en él.

### `@JavaScript` {#javascript}

La anotación `@JavaScript` carga el script que define o registra el componente web subyacente. Colócala en la clase para que el script se cargue solo cuando se use el componente.

```java
@NodeName("wa-relative-time")
@JavaScript("https://ka-f.webawesome.com/webawesome@3.12.0/webawesome.loader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Se permiten múltiples anotaciones `@JavaScript`, y webforJ elimina cargas duplicadas automáticamente. El mismo script no se cargará dos veces si varios componentes dependen de él.

Consulta [Importación de archivos JavaScript](../managing-resources/importing-assets#importing-javascript-files) para obtener el conjunto completo de opciones, incluidos `top`, `attributes` y temporización de carga.

### `@StyleSheet` {#stylesheet}

La anotación `@StyleSheet` carga un archivo CSS del que depende el componente. Es útil para componentes de terceros que envían una hoja de estilo por separado, o para agrupar estilos específicos del componente junto con el envoltorio.

```java
@StyleSheet("https://ka-f.webawesome.com/webawesome@3.12.0/styles/themes/default.css")
```

Para activos empaquetados localmente, usa el prefijo `ws://` para hacer referencia a archivos en `resources/static`:

```java
@StyleSheet("ws://components/relative-time.css")
```

Consulta [Importación de archivos CSS](../managing-resources/importing-assets#importing-css-files) para el conjunto completo de opciones.

## Descriptores de propiedades y atributos {#property-and-attribute-descriptors}

Las propiedades y atributos representan el estado de un componente web, normalmente conteniendo datos o configuración. `ElementComposite` expone ambos a través de `PropertyDescriptor`.

Dos métodos de fábrica en `PropertyDescriptor` producen el descriptor en sí, uno para cada objetivo de enlace:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` se vincula a una propiedad de JavaScript en el nodo DOM. `PropertyDescriptor.attribute()` se vincula a un atributo HTML. El primer argumento es el nombre que el componente web espera. El segundo es un valor predeterminado, que también fija el tipo de Java del descriptor.

Declara el descriptor como un campo privado en el componente y luego léelo y escríbelo a través de `set(PropertyDescriptor<V> property, V value)` y `get(PropertyDescriptor<V> property)`.

:::info
Las propiedades son el estado interno en el nodo DOM y no se reflejan en el marcado. Los atributos son un marcado HTML, visibles para scripts y CSS externos.
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

Las llamadas anteriores utilizan `set()` directamente para mostrar la forma primitiva. En la práctica, `set()` y `get()` son métodos `protected` en `ElementComposite`. Son la capa primitiva que sincroniza los valores de Java con el elemento subyacente, no la API pública que los consumidores llaman. El patrón previsto es mantener el `PropertyDescriptor` privado y escribir métodos públicos `setX()` y `getX()` que deleguen en las primitivas.

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

Una sola llamada a `set(descriptor, value)` hace tres cosas a la vez. Envía el valor al cliente a través de `setProperty()` para propiedades, o `setAttribute()` para atributos. Almacena el valor en una caché local en el servidor, un mapa por instancia de componente. Y registra el tipo en tiempo de ejecución junto al valor, por lo que las llamadas posteriores a `get()` saben cómo deserializar.

Esa caché local es la razón por la que `get()` puede ser económico por defecto. `get(descriptor)` devuelve el valor en caché desde el almacén del lado del servidor sin llamada de red, porque cada `set()` mantiene la caché sincronizada con el cliente. El segundo argumento booleano opcional controla si se omite la caché y se lee desde el navegador en su lugar.

```java
String cached = get(heading);            // lee de la caché del lado del servidor
String live = get(heading, true);        // fuerza una lectura desde el navegador
```

Establece `fromClient` en true cuando el valor puede cambiar en el cliente sin el conocimiento del servidor, como un valor `<input>` tipeado. Para propiedades gestionadas por el servidor, el valor predeterminado evita un viaje doble.

El tercer argumento opcional es un `java.lang.reflect.Type` y controla cómo se deserializa el resultado. webforJ resuelve el tipo en este orden: el argumento `Type` explícito si se pasa, luego el tipo en tiempo de ejecución registrado por un `set()` anterior en el mismo descriptor, luego `Object.class`. En la práctica, el tipo registrado por un `set()` anterior es suficiente, así que el tercer argumento puede omitirse normalmente. Se necesita cuando la clase registrada pierde información de la que el deserializador depende, como un tipo parametrizado como `List<String>` cuya clase en tiempo de ejecución es solo `ArrayList`.

La demostración a continuación agrega propiedades para el tiempo relativo basadas en la documentación del componente web y las expone a través de getters y setters. Cada fila en el feed de actividad utiliza diferentes valores `format` y `numeric` para mostrar cómo el mismo componente se representa bajo configuraciones variadas.

<ComponentDemo
path='/webforj/relativetimeproperties'
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/frontend/element-composite/activityfeed.css',
]}
height='450px'
/>

### Propiedades versus atributos {#properties-versus-attributes}

Aunque `PropertyDescriptor.property()` y `PropertyDescriptor.attribute()` parecen intercambiables, apuntan a diferentes partes del elemento subyacente. Elegir el incorrecto resulta en valores que silenciosamente no se aplican.

Las propiedades son propiedades de objeto de JavaScript en el nodo DOM. Pueden contener cualquier tipo, incluidos cadenas, booleanos, números, objetos y arreglos, y representan el estado actual en tiempo de ejecución del elemento. Establecer una propiedad es una asignación directa de JavaScript.

Los atributos son un marcado HTML. Viven en la etiqueta de apertura del elemento, son siempre cadenas, y representan la configuración inicial del elemento. Establecer un atributo desencadena una mutación DOM y una conversión de cadena.

Para algunos casos, los dos permanecen en sincronía. Para otros, divergen. El `value` de un `<input>` es el ejemplo clásico: el atributo `value` es el valor inicial, mientras que la propiedad `value` es el valor actual que el usuario ha escrito. Leer el atributo después de que el usuario escriba devuelve el marcado original, pero leer la propiedad devuelve el contenido actual del campo.

Usa **propiedades** para:

- **Estado de ejecución frecuentemente cambiante**: contadores, selecciones actuales, valores tipeados
- **Tipos no cadena**: booleanos, números, objetos, arreglos
- **Actualizaciones sensibles al rendimiento**: las propiedades omiten la conversión de cadena requerida para los atributos

Usa **atributos** para:

- **Configuración inicial**: configuraciones que el componente lee una vez cuando se conecta
- **Selectores CSS**: valores que deseas dirigir con selectores como `[disabled]` o `[variant="danger"]`
- **Ganchos de accesibilidad**: `aria-label`, `role` y otros atributos ARIA
- **Configuraciones similares a cadenas que rara vez cambian**

Al envolver un componente web de terceros, consulta la documentación del componente para confirmar qué nombre mapea a una propiedad y cuál a un atributo. Usar `PropertyDescriptor.attribute()` para algo que el componente expone solo como propiedad no funcionará, y lo mismo es cierto al revés. El componente ignorará silenciosamente el valor.

### Tipado de propiedades {#typing-properties}

Un descriptor está parametrizado por el tipo de Java de su valor. La sintaxis completa de declaración es:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

El parámetro genérico `<T>` declara el tipo del valor. El tipo de tiempo de ejecución del valor predeterminado también fija `T`, por lo que el argumento genérico rara vez necesita especificarse explícitamente. webforJ usa `T` para serializar y deserializar valores al comunicarse con el cliente.

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

Valida los valores en el setter antes de llamar a `set()`. El setter es el punto natural de cumplimiento porque cada mutación fluye a través de él.

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

Para referencias anulables, utiliza `Objects.requireNonNull()` para que la falla se manifieste en el límite en lugar de más adelante en la canalización de renderizado.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading no puede ser nulo");
  set(heading, value);
  return this;
}
```

Evita validar en `get()`. Las lecturas deben ser económicas y consistentes.

### Propiedades al estilo enum {#enum-style-properties}

La mayoría de los componentes web esperan valores de cadena en minúscula o en formato kebab para propiedades similares a enums (`theme="primary"`, `expanse="xs"`). webforJ usa Gson para serializar enums, pero la representación predeterminada de Gson es el nombre constante en mayúsculas. Anota cada constante con `@SerializedName` para que el valor serializado coincida con lo que espera el componente web.

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

Declara el descriptor con el tipo enum y usa el enum directamente en el setter y el getter.

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

Este es el mismo patrón que utilizan los componentes integrados de webforJ para `Theme`, `Expanse` y enums similares. La API pública de Java permanece segura en tipos, y el valor que recibe el componente web es la cadena de `@SerializedName`.

### Pruebas de propiedades {#testing-properties}

`PropertyDescriptorTester` valida que cada `PropertyDescriptor` en un componente esté cableado correctamente. Escanea la clase en busca de campos descriptor, llama a cada setter con el valor predeterminado y compara el resultado con lo que devuelve el getter. El tester captura errores de integración antes de que lleguen a una aplicación en ejecución: un setter que escribe en el descriptor incorrecto, un getter que lee una propiedad diferente, un valor predeterminado que no retrocede, o un accesorio faltante para un descriptor declarado.

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

#### Exclusión de propiedades {#excluding-properties}

Algunos descriptores no siguen las convenciones estándar de getters y setters, o dependen de un estado externo que la prueba no puede satisfacer. Anótalos con `@PropertyExclude` para omitirlos.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Nombres de getter y setter personalizados {#custom-getter-and-setter-names}

Si un descriptor usa nombres de accesores no estándar, decláralos con `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

El parámetro `target` acepta una clase cuando los accesores viven en algún lugar que no sea el componente mismo.

Para más detalles sobre la superficie de pruebas, consulta [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de preocupación {#concern-interfaces}

Las interfaces de preocupación otorgan a un componente de subclase `ElementComposite` capacidades sin que tengas que escribir la implementación tú mismo. Las interfaces reenvían llamadas al elemento subyacente. Implementa las que el componente debe admitir, parametrizadas con el tipo de subclase para que el encadenamiento devuelva el componente:

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasText<MyBadge>, HasClassName<MyBadge>, HasStyle<MyBadge> {
  // No se necesita implementación.
}

MyBadge badge = new MyBadge()
    .setText("Nuevo")
    .addClassName("resaltado")
    .setStyle("color", "var(--dwc-color-primary)");
```

Las tres interfaces anteriores cubren todo lo que MyBadge necesita sin ningún cuerpo de método en la clase. `HasText` expone `setText()` y escribe en el contenido de texto del elemento. `HasClassName` expone `addClassName()`, lo que permite que la insignia sea dirigida desde CSS. `HasStyle` expone `setStyle()` para el estilo en línea.

Para el conjunto completo de interfaces disponibles y lo que cada una proporciona, consulta [Interfaces de preocupación](./component-fundamentals#concern-interfaces) en el artículo Comprender Componentes. Si un reenvío predeterminado no coincide con lo que expone el elemento envuelto, sobreescribe el método en la subclase.

## Eventos {#events}

### Registro de eventos {#event-registration}

Los componentes web despachan eventos DOM cuando ocurre algo en el navegador. Para reaccionar desde Java, escucha esos eventos con `addEventListener()`. El conjunto de eventos que un componente despacha varía, así que consulta la documentación del componente en sí para los nombres y cargas disponibles.

`ElementComposite` admite debounce, throttling, filtrado y datos de eventos personalizados en los oyentes registrados.

Registra los oyentes de eventos utilizando el método `addEventListener()`:

```java
// Ejemplo: Agregar un oyente de eventos de clic
addEventListener(ElementClickEvent.class, event -> {
  // Manejar el evento de clic
});
```

:::info
`ElementComposite` solo acepta clases de eventos anotadas con `@EventName`, a diferencia de `Element`, que acepta cualquier nombre de evento de cadena.
:::

### Clases de eventos integradas {#built-in-event-classes}

`ElementClickEvent` es la única clase de evento incorporada que `ElementComposite` ofrece. Supervisa los eventos de clic del mouse en el elemento subyacente con accesores tipados para coordenadas (`getClientX()`, `getClientY()`), información del botón (`getButton()`) y teclas modificadoras (`isCtrlKey()`, `isShiftKey()`, etc.).

Para exponer el manejo de clics en la API pública de una subclase, implementa la interfaz de preocupación `HasElementClickListener<T>`. Proporciona los métodos predeterminados `onClick()` y `addClickListener()` que delegan en la primitiva protegida `addEventListener()`.

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

Para cualquier otro evento que despache el componente web subyacente, define una clase de evento personalizada. Consulta [Clases de eventos personalizadas](#custom-event-classes).

### Cargas de eventos {#event-payloads}

Los eventos llevan datos del cliente a tu código Java. Accede a estos datos a través de `getData()` para datos de eventos en bruto o usa métodos tipados cuando estén disponibles en clases de eventos integradas. Consulta la [guía de Eventos](../building-ui/events) para más sobre el manejo eficiente de cargas.

### Clases de eventos personalizadas {#custom-event-classes}

Define clases de eventos personalizadas con `@EventName` y `@EventOptions` para capturar datos del lado del cliente en un evento Java tipado. Usa esto cuando el controlador de Java necesite valores del navegador.

`@EventName` vincula la clase Java al evento que el componente despacha en el navegador, por lo que una clase anotada con `@EventName("change")` se dispara cada vez que el elemento subyacente emite `change`. `@EventOptions` controla qué viaja de vuelta con ese evento. Cada `@EventData` dentro de ello empareja una clave con una expresión de JavaScript evaluada en contra del evento DOM. El resultado está disponible en la clase de evento Java a través de `getData().get(key)`.

El formulario de revisión del producto a continuación utiliza este patrón con [`wa-rating`](https://webawesome.com/docs/components/rating/). El `ChangeEvent` personalizado lleva el valor de calificación como un `double` tipado, y el oyente lo usa para habilitar el botón de envío:

<ComponentDemo
path='/webforj/rating'
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Opciones de eventos {#event-options}

`ElementEventOptions` configura la carga del evento, tiempos de debounce o throttling, expresiones de filtrado y código de preejecución. El fragmento a continuación muestra las opciones:

```java
ElementEventOptions options = new ElementEventOptions()
  // Recopilar datos personalizados del cliente
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")

  // Ejecutar JavaScript antes de que se dispare el evento
  .setCode("component.classList.add('processing');")

  // Solo disparar si se cumplen condiciones
  .setFilter("component.value.length >= 2")

  // Retrasar la ejecución hasta que el usuario deje de escribir (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Aplica estas opciones al registrar un oyente para una clase de evento personalizada
// (consulta la sección Clases de eventos personalizadas anterior para cómo definir una):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` expone solo la forma basada en clases `addEventListener(Class, listener, options)`. Úsalo con una clase de evento anotada con `@EventName`. Para registrarte directamente contra un nombre de evento de cadena, llama a `getElement().addEventListener("input", listener, options)`.
:::

#### Control de rendimiento {#performance-control}

**Debounce** retrasa la ejecución hasta que se detiene la actividad:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Espera 300ms después del último evento
```

Fases de debounce disponibles:

- `LEADING`: Disparar de inmediato, luego esperar
- `TRAILING`: Esperar un período de calma, luego disparar (predeterminado)
- `BOTH`: Disparar de inmediato y después del período de calma

**Throttling** limita la frecuencia de ejecución:

```java
options.setThrottle(100); // Disparar como máximo una vez por cada 100ms
```

## Interacción con slots {#interacting-with-slots}

Los slots son marcadores de posición dentro de un componente web que los usuarios llenan con contenido. El componente web declara sus slots en su plantilla con `<slot>` o `<slot name="...">`, y el envoltorio expone métodos que colocan componentes de Java en esos slots.

Para agregar contenido a los slots, extiende `ElementCompositeContainer` en lugar de `ElementComposite`. El contenedor lleva la misma maquinaria de propiedades y atributos más los métodos necesarios para agregar hijos. Los hijos agregados a través de `add()` van al slot predeterminado. Los hijos agregados a través de `getElement().add(slotName, components)` van al slot nombrado.

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

La demostración a continuación muestra dos tarjetas de precios construidas con [`wa-card`](https://webawesome.com/docs/components/card/), poblando los slots `header`, predeterminado y `footer` desde Java:

<ComponentDemo
path='/webforj/webawesomecard'
files={['src/main/java/com/webforj/samples/views/elementcomposite/WebAwesomeCardView.java']}
height='400px'
/>

### Inspección del contenido de los slots {#inspecting-slot-contents}

El `Element` subyacente (accedido a través de `getElement()`) proporciona métodos para leer qué está actualmente asignado a los slots:

- **`findComponentSlot()`**: busca todos los slots para un componente específico y devuelve el nombre del slot que lo contiene, o una cadena vacía si el componente no está en ningún slot.
- **`getComponentsInSlot()`**: devuelve la lista de componentes asignados a un slot dado. Opcionalmente toma un tipo de clase para filtrar los resultados.
- **`getFirstComponentInSlot()`**: devuelve el primer componente asignado a un slot. Opcionalmente toma un tipo de clase para filtrar.
