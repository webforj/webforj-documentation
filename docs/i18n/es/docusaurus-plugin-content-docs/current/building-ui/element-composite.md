---
sidebar_position: 6
title: Element Composite
sidebar_class_name: new-content
_i18n_hash: d626a230fe04d316c48e3cae7e292599
---
<JavadocLink type="foundation" location="com/webforj/component/element/ElementComposite" top='true'/>

La clase `ElementComposite` envuelve un elemento HTML personalizado o un [componente web](https://developer.mozilla.org/en-US/docs/Web/API/Web_components). Vincula tu clase Java al `Element` subyacente y te permite trabajar con las propiedades, atributos y eventos de ese elemento a través de Java. Úsalo al integrar componentes web en una aplicación webforJ.

:::tip Cuándo usar `ElementComposite`
Recurrir a `ElementComposite` al envolver un componente web de terceros que webforJ no proporciona. Si un componente webforJ incorporado cubre el caso de uso (`TextField`, `ColorField`, `Button`, etc.), usa ese en su lugar. Para trabajos DOM que no necesitan ser reutilizados, la clase `Element` puede ser utilizada directamente sin un envoltorio.
:::

Esta guía demuestra cómo implementar el [componente web de tiempo relativo de Shoelace](https://shoelace.style/components/relative-time) utilizando la clase `ElementComposite`.

<ComponentDemo 
path='/webforj/relativetime' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimeView.java']}
height='150px'
/>

## Anotaciones de clase {#class-annotations}

Tres anotaciones aparecen con frecuencia en la parte superior de una subclase de `ElementComposite`: `@NodeName` declara la etiqueta HTML que el componente envuelve, y `@JavaScript` y `@StyleSheet` cargan cualquier recurso del lado del cliente del que dependa el componente web subyacente. `@NodeName` es obligatoria y específica para `ElementComposite`. `@JavaScript` y `@StyleSheet` son anotaciones de recursos webforJ generales y funcionan en cualquier clase, incluyendo vistas, componentes o la clase `App`.

### `@NodeName` {#nodename}

La anotación `@NodeName` declara la etiqueta HTML que el componente envuelve. webforJ utiliza este nombre al crear el elemento subyacente en el DOM.

```java
@NodeName("sl-relative-time")
public class RelativeTime extends ElementComposite {
  // ...
}
```

El nombre de la etiqueta debe coincidir con el elemento personalizado registrado en el cliente. Sin esta anotación, el marco no puede determinar qué elemento crear.

Dentro de una subclase, `getNodeName()` lee de nuevo la etiqueta declarada, y `getElement()` devuelve el `Element` subyacente para que puedas llamar a métodos a nivel de DOM directamente.

### `@JavaScript` {#javascript}

La anotación `@JavaScript` carga el script que define o registra el componente web subyacente. Colócala en la clase para que el script se cargue solo cuando se utiliza el componente.

```java
@NodeName("sl-relative-time")
@JavaScript("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js")
public class RelativeTime extends ElementComposite {
  // ...
}
```

Se permiten múltiples anotaciones `@JavaScript`, y webforJ elimina automáticamente las cargas duplicadas. El mismo script no se cargará dos veces si varios componentes dependen de él.

Consulta [Importando archivos JavaScript](../managing-resources/importing-assets#importing-javascript-files) para el conjunto completo de opciones, incluyendo `top`, `attributes` y tiempo de carga.

### `@StyleSheet` {#stylesheet}

La anotación `@StyleSheet` carga un archivo CSS del que depende el componente. Es útil para componentes de terceros que envían una hoja de estilo separada o para agrupar estilos específicos del componente junto con el envoltorio.

```java
@StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
```

Para recursos empaquetados localmente, usa el prefijo `ws://` para hacer referencia a archivos en `resources/static`:

```java
@StyleSheet("ws://components/relative-time.css")
```

Consulta [Importando archivos CSS](../managing-resources/importing-assets#importing-css-files) para el conjunto completo de opciones.

## Descriptores de propiedades y atributos {#property-and-attribute-descriptors}

Las propiedades y atributos representan el estado de un componente web, generalmente conteniendo datos o configuración. `ElementComposite` expone ambos a través de `PropertyDescriptor`.

Dos métodos de fábrica en `PropertyDescriptor` producen el descriptor mismo, uno por objetivo de vinculación:

```java
PropertyDescriptor<T> property  = PropertyDescriptor.property(String name, T defaultValue);
PropertyDescriptor<T> attribute = PropertyDescriptor.attribute(String name, T defaultValue);
```

`PropertyDescriptor.property()` se conecta a una propiedad JavaScript en el nodo DOM. `PropertyDescriptor.attribute()` se conecta a un atributo HTML. El primer argumento es el nombre que espera el componente web. El segundo es un valor predeterminado, que también fija el tipo Java del descriptor.

Declara el descriptor como un campo privado en el componente, y luego lee y escribe a través de él con `set(PropertyDescriptor<V> property, V value)` y `get(PropertyDescriptor<V> property)`.

:::info
Las propiedades son el estado interno en el nodo DOM y no se reflejan en el marcado. Los atributos son el marcado HTML, visibles para scripts y CSS externos.
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

Una única llamada a `set(descriptor, value)` hace tres cosas a la vez. Empuja el valor al cliente a través de `setProperty()` para propiedades, o `setAttribute()` para atributos. Almacena el valor en una caché local en el servidor, un mapa por instancia de componente. Y registra el tipo de tiempo de ejecución junto con el valor, para que las llamadas `get()` posteriores sepan cómo deserializar.

Esa caché local es la razón por la que `get()` puede ser barato por defecto. `get(descriptor)` devuelve el valor en caché del almacenamiento del lado del servidor sin llamada de red, porque cada `set()` mantiene la caché sincronizada con el cliente. El segundo argumento booleano opcional controla si el acceso a la caché se evita y lee desde el navegador en su lugar.

```java
String cached = get(heading);            // lee de la caché del lado del servidor
String live = get(heading, true);        // fuerza una lectura desde el navegador
```

Establece `fromClient` en verdadero cuando el valor puede cambiar en el cliente sin el conocimiento del servidor, como un valor de `<input>` escrito. Para propiedades gestionadas por el servidor, la predeterminada evita un viaje adicional.

El tercer argumento opcional es un `java.lang.reflect.Type` y controla cómo se deserializa el resultado. webforJ resuelve el tipo en este orden: el argumento `Type` explícito si se pasa, luego el tipo de tiempo de ejecución registrado por un `set()` anterior en el mismo descriptor, luego `Object.class`. En la práctica, el tipo registrado por un `set()` previo es suficiente, por lo que el tercer argumento generalmente se puede omitir. Es necesario cuando la clase registrada pierde información de la cual el deserialization depende, como un tipo parametrizado como `List<String>` cuya clase de tiempo de ejecución es solo `ArrayList`.

La demostración a continuación agrega propiedades para tiempo relativo basadas en la documentación del componente web y las expone a través de getters y setters. Cada fila en el feed de actividad utiliza diferentes valores de `format` y `numeric` para mostrar cómo el mismo componente se renderiza bajo diversas configuraciones.

<ComponentDemo 
path='/webforj/relativetimeproperties' 
files={[
  'src/main/java/com/webforj/samples/views/elementcomposite/RelativeTimePropertiesView.java',
  'src/main/resources/static/css/elementcomposite/activity-feed.css',
]}
height='450px'
/>

### Propiedades versus atributos {#properties-versus-attributes}

Aunque `PropertyDescriptor.property()` y `PropertyDescriptor.attribute()` parecen intercambiables, apuntan a diferentes partes del elemento subyacente. Elegir el incorrecto resulta en valores que fallan silenciosamente en aplicarse.

Las propiedades son propiedades de objetos JavaScript en el nodo DOM. Pueden contener cualquier tipo, incluyendo cadenas, booleanos, números, objetos y arreglos, y representan el estado actual de ejecución del elemento. Establecer una propiedad es una asignación directa de JavaScript.

Los atributos son el marcado HTML. Viven en la etiqueta de apertura del elemento, siempre son cadenas y representan la configuración inicial del elemento. Establecer un atributo provoca una mutación del DOM y una conversión a cadena.

En algunos casos, ambos se mantienen sincronizados. En otros divergen. El `value` de un `<input>` es el ejemplo clásico: el atributo `value` es el valor inicial, mientras que la propiedad `value` es el valor actual que el usuario ha escrito. Leer el atributo después de que el usuario escribe devuelve el marcado original, pero leer la propiedad devuelve el contenido actual del campo.

Usa **propiedades** para:

- **Estado de ejecución que cambia con frecuencia**: contadores, selecciones actuales, valores escritos
- **Tipos no cadena**: booleanos, números, objetos, arreglos
- **Actualizaciones sensibles al rendimiento**: las propiedades evitan la conversión a cadena requerida para atributos

Usa **atributos** para:

- **Configuración inicial**: configuraciones que el componente lee una vez al conectarse
- **Selectores CSS**: valores que deseas dirigir con selectores como `[disabled]` o `[variant="danger"]`
- **Puntos de acceso para accesibilidad**: `aria-label`, `role` y otros atributos ARIA
- **Configuraciones tipo cadena que rara vez cambian**

Al envolver un componente web de terceros, consulta la documentación del componente para confirmar qué nombre corresponde a una propiedad y cuál a un atributo. Usar `PropertyDescriptor.attribute()` para algo que el componente expone solo como propiedad no funcionará, y lo mismo es cierto a la inversa. El componente ignorará silenciosamente el valor.

### Tipado de propiedades {#typing-properties}

Un descriptor está parametrizado por el tipo Java de su valor. La sintaxis completa de declaración es:

```java
private final PropertyDescriptor<T> name =
    PropertyDescriptor.property(String name, T defaultValue);
```

El parámetro genérico `<T>` declara el tipo del valor. El tipo de tiempo de ejecución del valor predeterminado también fija `T`, por lo que el argumento genérico rara vez necesita ser especificado explícitamente. webforJ utiliza `T` para serializar y deserializar valores al comunicarse con el cliente.

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

La serialización es automática para primitivos, sus equivalentes empaquetados y `String`. Para tipos complejos, el valor se serializa como JSON antes de ser asignado a la propiedad en el cliente.

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

Para referencias anulables, usa `Objects.requireNonNull()` para que la falla se presente en el límite en lugar de más tarde en el pipeline de renderizado.

```java
public Card setHeading(String value) {
  Objects.requireNonNull(value, "heading cannot be null");
  set(heading, value);
  return this;
}
```

Evita validar en `get()`. Las lecturas deben mantenerse baratas y consistentes.

### Propiedades estilo enumeración {#enum-style-properties}

La mayoría de los componentes web esperan valores de cadena en minúsculas o en kebab-case para propiedades estilo enumeración (`theme="primary"`, `expanse="xs"`). webforJ utiliza Gson para serializar enumeraciones, pero la representación predeterminada de Gson es el nombre de la constante en mayúsculas. Anota cada constante con `@SerializedName` para que el valor serializado coincida con lo que el componente web espera.

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

Declara el descriptor con el tipo de enumeración y usa la enumeración directamente en el setter y getter.

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

Este es el mismo patrón que utilizan los componentes incorporados de webforJ para `Theme`, `Expanse`, y enumeraciones similares. La API pública de Java se mantiene segura en tipos, y el valor que recibe el componente web es la cadena de `@SerializedName`.

### Probando propiedades {#testing-properties}

`PropertyDescriptorTester` valida que cada `PropertyDescriptor` en un componente esté conectado correctamente. Escanea la clase en busca de campos de descriptor, llama a cada setter con el valor predeterminado, y compara el resultado con lo que devuelve el getter. El tester detecta errores de integración antes de que lleguen a una aplicación en ejecución: un setter que escribe en el descriptor equivocado, un getter que lee una propiedad diferente, un valor predeterminado que no vuelve a pasar, o un accesor faltante para un descriptor declarado.

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

Algunos descriptores no siguen las convenciones estándar de getter y setter, o dependen de un estado externo que la prueba no puede satisfacer. Anótalos con `@PropertyExclude` para omitirlos.

```java
@PropertyExclude
private final PropertyDescriptor<String> internal =
    PropertyDescriptor.property("internal", "");
```

#### Nombres personalizados de getter y setter {#custom-getter-and-setter-names}

Si un descriptor utiliza nombres de acceso no estándar, decláralos con `@PropertyMethods`.

```java
@PropertyMethods(getter = "retrieveValue", setter = "updateValue")
private final PropertyDescriptor<String> custom =
    PropertyDescriptor.property("custom", "default");
```

El parámetro `target` acepta una clase cuando los accesores residen en algún lugar que no sea el componente en sí.

Para más detalles sobre la superficie de prueba, consulta [PropertyDescriptorTester](../testing/property-descriptor-tester).

## Interfaces de preocupación {#concern-interfaces}

Las interfaces de preocupación dan a un componente de subclase `ElementComposite` capacidades sin tener que escribir la implementación tú mismo. Las interfaces reenvían llamadas al elemento subyacente. Implementa las que el componente debe soportar, parametrizado con el tipo de subclase para que el encadenamiento devuelva el componente:

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

Las tres interfaces anteriores cubren todo lo que MyBadge necesita sin necesidad de cuerpos de método en la clase. `HasText` expone `setText()` y escribe en el contenido de texto del elemento. `HasClassName` expone `addClassName()`, lo que permite que la insignia sea dirigida desde CSS. `HasStyle` expone `setStyle()` para estilo en línea.

Para el conjunto completo de interfaces disponibles y lo que cada una proporciona, consulta [Interfaces de preocupación](./component-fundamentals#concern-interfaces) en el artículo Understanding Components. Si un reenvío predeterminado no coincide con lo que el elemento envuelto expone, anula el método en la subclase.

## Eventos {#events}

### Registro de eventos {#event-registration}

Los componentes web despachan eventos DOM cuando sucede algo en el navegador. Para reaccionar desde Java, escucha esos eventos con `addEventListener()`. El conjunto de eventos que un componente despacha varía, así que consulta la propia documentación del componente para los nombres y cargas útiles disponibles.

`ElementComposite` soporta debouncing, throttling, filtrado y datos de eventos personalizados en oyentes registrados.

Registra oyentes de eventos utilizando el método `addEventListener()`:

```java
// Ejemplo: Agregar un oyente de eventos de clic
addEventListener(ElementClickEvent.class, event -> {
  // Manejar el evento de clic
});
```

:::info
`ElementComposite` solo acepta clases de eventos anotadas con `@EventName`, a diferencia de `Element`, que acepta cualquier nombre de evento de cadena.
:::

### Clases de eventos incorporadas {#built-in-event-classes}

`ElementClickEvent` es la única clase de evento incorporada que `ElementComposite` proporciona. Supervisa eventos de clic del mouse en el elemento subyacente con accesores tipados para coordenadas (`getClientX()`, `getClientY()`), información sobre botones (`getButton()`) y teclas modificadoras (`isCtrlKey()`, `isShiftKey()`, y así sucesivamente).

Para exponer el manejo de clics en la API pública de una subclase, implementa la interfaz de preocupación `HasElementClickListener<T>`. Proporciona métodos predeterminados `onClick()` y `addClickListener()` que delegan al primitivo protegido `addEventListener()`.

```java
@NodeName("my-badge")
public class MyBadge extends ElementComposite
    implements HasElementClickListener<MyBadge> {
  // onClick() y addClickListener() están ahora disponibles en MyBadge
}

new MyBadge().onClick(event -> {
  if (event.isShiftKey()) {
    // ...
  }
});
```

Para cualquier otro evento que despache el componente web subyacente, define una clase de evento personalizada. Consulta [Clases de eventos personalizadas](#custom-event-classes).

### Cargas útiles de eventos {#event-payloads}

Los eventos transportan datos desde el cliente a tu código Java. Accede a estos datos a través de `getData()` para datos de evento en bruto o utiliza métodos tipados cuando estén disponibles en clases de eventos incorporadas. Consulta la [guía de eventos](../building-ui/events) para más información sobre el manejo eficiente de cargas útiles.

### Clases de eventos personalizadas {#custom-event-classes}

Define clases de eventos personalizadas con `@EventName` y `@EventOptions` para capturar datos del lado del cliente en un evento Java tipado. Usa esto cuando el controlador Java necesite valores del navegador.

`@EventName` vincula la clase Java al evento que el componente despacha en el navegador, por lo que una clase anotada con `@EventName("sl-change")` se activa cada vez que el elemento subyacente emite `sl-change`. `@EventOptions` controla qué viaja de regreso con ese evento. Cada `@EventData` dentro de él empareja una clave con una expresión JavaScript evaluada contra el evento DOM. El resultado está disponible en la clase de evento Java a través de `getData().get(key)`.

El formulario de revisión de productos a continuación utiliza este patrón con [`sl-rating`](https://shoelace.style/components/rating). El `ChangeEvent` personalizado lleva el valor de calificación como un `double` tipado, y el oyente lo utiliza para habilitar el botón de envío:

<ComponentDemo 
path='/webforj/rating' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/RatingView.java']}
height='220px'
/>

### Opciones de evento {#event-options}

`ElementEventOptions` configura la carga útil del evento, el tiempo de debounce o throttle, expresiones de filtro y código de preejecución. El fragmento a continuación muestra las opciones:

```java
ElementEventOptions options = new ElementEventOptions()
  // Recopila datos personalizados del cliente
  .addData("query", "component.value")
  .addData("timestamp", "Date.now()")
  .addData("isValid", "component.checkValidity()")
  
  // Ejecuta JavaScript antes de que se dispare el evento
  .setCode("component.classList.add('processing');")
  
  // Solo dispara si se cumplen las condiciones
  .setFilter("component.value.length >= 2")
  
  // Retrasa la ejecución hasta que el usuario deja de escribir (300ms)
  .setDebounce(300, DebouncePhase.TRAILING);

// Aplica estas opciones al registrar un oyente para una clase de evento personalizada
// (consulta la sección Clases de eventos personalizadas anterior para saber cómo definir una):
addEventListener(InputEvent.class, this::handleSearch, options);
```

:::info
`ElementComposite` expone solo la forma basada en clases `addEventListener(Class, listener, options)`. Úsala con una clase de evento anotada con `@EventName`. Para registrar directamente un nombre de evento de cadena, llama a `getElement().addEventListener("input", listener, options)`.
:::

#### Control de rendimiento {#performance-control}

**Debouncing** retrasa la ejecución hasta que la actividad se detiene:

```java
options.setDebounce(300, DebouncePhase.TRAILING); // Espera 300ms después del último evento
```

Fases de debounce disponibles:

- `LEADING`: Disparar inmediatamente, luego esperar
- `TRAILING`: Esperar un período de silencio, luego disparar (predeterminado)
- `BOTH`: Disparar inmediatamente y después del período de silencio

**Throttling** limita la frecuencia de ejecución:

```java
options.setThrottle(100); // Dispara como máximo una vez por cada 100ms
```

## Interactuando con slots {#interacting-with-slots}

Los slots son marcadores de posición dentro de un componente web que los usuarios llenan con contenido. El componente web declara sus slots en su plantilla con `<slot>` o `<slot name="...">`, y el envoltorio expone métodos que colocan componentes Java dentro de esos slots.

Para agregar contenido a los slots, extiende `ElementCompositeContainer` en lugar de `ElementComposite`. El contenedor lleva la misma maquinaria de propiedades y atributos más los métodos necesarios para agregar hijos. Los hijos añadidos a través de `add()` van al slot predeterminado. Los hijos añadidos a través de `getElement().add(slotName, components)` van al slot nombrado.

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

La demostración a continuación muestra dos tarjetas de precios construidas con [`sl-card`](https://shoelace.style/components/card), poblando los slots `header`, predeterminado y `footer` desde Java:

<ComponentDemo 
path='/webforj/card' 
files={['src/main/java/com/webforj/samples/views/elementcomposite/CardView.java']}
height='400px'
/>

### Inspeccionando el contenido de los slots {#inspecting-slot-contents}

El `Element` subyacente (accesible a través de `getElement()`) proporciona métodos para leer lo que actualmente está asignado a los slots:

- **`findComponentSlot()`**: busca todos los slots para un componente específico y devuelve el nombre del slot que lo contiene, o una cadena vacía si el componente no está en ningún slot.
- **`getComponentsInSlot()`**: devuelve la lista de componentes asignados a un slot dado. Opcionalmente, toma un tipo de clase para filtrar los resultados.
- **`getFirstComponentInSlot()`**: devuelve el primer componente asignado a un slot. Opcionalmente, toma un tipo de clase para filtrar.
