---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 9a4b6da2f5a3bd524a0b3cf6a1eb86e1
---
Un enlace en webforJ conecta una propiedad específica de un Java Bean a un componente de UI. Este vínculo permite actualizaciones automáticas entre la UI y el modelo del backend. Cada enlace puede manejar la sincronización de datos, validación, transformación y gestión de eventos.

Puedes iniciar enlaces solo a través del `BindingContext`. Este gestiona una colección de instancias de enlace, cada una vinculando un componente de UI a una propiedad de un bean. Facilita operaciones grupales sobre enlaces, como validación y sincronización entre los componentes de la UI y las propiedades del bean. Actúa como un agregador, permitiendo acciones colectivas sobre múltiples enlaces, simplificando así la gestión del flujo de datos dentro de las aplicaciones.

:::tip Enlace Automático
Esta sección presenta los conceptos básicos de la configuración manual de enlaces. Además, puedes crear enlaces automáticamente basados en los componentes de la UI en tu formulario. Una vez que comprendas los fundamentos, aprende más leyendo la sección sobre [Enlace Automático](/docs/data-binding/automatic-binding).
:::

## Configurar enlaces {#configure-bindings}

Comienza creando una nueva instancia de `BindingContext` que gestiona todos los enlaces para un modelo particular. Este contexto valida y actualiza todos los enlaces de manera colectiva.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Cada formulario debe tener solo una instancia de `BindingContext`, y debes usar esta instancia para todos los componentes en el formulario.
:::

### La propiedad vinculada {#the-bound-property}

Una propiedad de enlace es un campo o atributo específico de un Java Bean que puede vincularse a un componente de UI en tu aplicación. Este vínculo permite que los cambios en la UI afecten directamente la propiedad correspondiente del modelo de datos, y viceversa, por lo que la UI y el modelo de datos permanecen en sincronía.

Al configurar un enlace, debes proporcionar el nombre de la propiedad como una cadena. Este nombre debe coincidir con el nombre del campo en la clase Java Bean. Aquí hay un ejemplo simple:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add()
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters y getters
}
```

El método `bind` devuelve un `BindingBuilder` que crea el objeto `Binding` y se puede usar para configurar el enlace con varios ajustes. El método `add` es el que realmente agrega el enlace al contexto.

### El componente vinculado {#the-bound-component}

El otro lado del enlace es el componente vinculado, que se refiere al componente de UI que interactúa con la propiedad del Java Bean. El componente vinculado puede ser cualquier componente de UI que soporte la interacción del usuario y la visualización, como campos de texto, cuadros combinados, casillas de verificación, o cualquier componente personalizado que implemente la interfaz `ValueAware`.

El componente vinculado sirve como el punto de interacción del usuario con el modelo de datos subyacente. Muestra datos al usuario y también captura las entradas del usuario que luego se regresan al modelo.

```java
TextField nameTextField = new TextField("Nombre");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Lectura y escritura de datos {#reading-and-writing-data}

### Lectura de datos {#reading-data}

Leer datos implica llenar los componentes de UI con valores del modelo de datos. Esto se hace típicamente cuando un formulario se muestra por primera vez o cuando necesitas recargar los datos debido a cambios en el modelo subyacente. El método `read` proporcionado por `BindingContext` hace que este proceso sea sencillo.

```java
// Supongamos que el objeto Hero se ha instanciado e inicializado
Hero hero = new Hero("Clark Kent", "Volando");

// BindingContext ya está configurado con enlaces
context.read(hero);
```

En este ejemplo, el método `read` toma una instancia de `Hero` y actualiza todos los componentes de UI vinculados para reflejar las propiedades del héroe. Si el nombre o poder del héroe cambia, los componentes de UI correspondientes (como un `TextField` para el nombre y un `ComboBox` para los poderes) mostrarán estos nuevos valores.

### Escritura de datos {#writing-data}

Escribir datos implica recopilar valores de los componentes de UI y actualizar el modelo de datos. Esto ocurre comúnmente cuando un usuario envía un formulario. El método `write` maneja la validación y la actualización del modelo en un solo paso.

```java
// Esto podría ser desencadenado por un evento de envío de formulario
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Los datos son válidos, y el objeto héroe ha sido actualizado
    // repository.save(hero);
  } else {
    // Manejar errores de validación
    // results.getMessages();
  }
});
```

En el código anterior, cuando el usuario hace clic en el botón de enviar, se llama al método `write`. Este realiza todas las validaciones configuradas y, si los datos pasan todas las comprobaciones, actualiza el objeto `Hero` con nuevos valores de los componentes vinculados. Si los datos son válidos, puedes guardar en una base de datos o procesarlos más. Si hay errores de validación, debes manejarlos apropiadamente, normalmente mostrando mensajes de error al usuario.

:::tip Reporte de Errores de Validación
Todos los componentes principales de webforJ tienen configuraciones predeterminadas para informar automáticamente errores de validación, ya sea en línea o a través de un popover. Puedes personalizar este comportamiento utilizando [Reporteros](./validation/reporters.md).
:::

## Propiedades de bean anidadas <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Una propiedad de enlace puede ser una ruta con puntos que apunta a una propiedad dentro de un bean anidado. Cada segmento en la ruta sigue las convenciones estándar de getter y setter de JavaBean, así que `address.street` se lee a través de `getAddress().getStreet()` y se escribe a través de `getAddress().setStreet()`.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getters y setters
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getters y setters
}
```

Al leer, una ruta se resuelve de manera segura incluso cuando un bean intermedio es `null`. Si un `Hero` no tiene `Address`, los componentes vinculados a `address.street` y `address.city` se leen como vacíos en lugar de lanzar una excepción, por lo que el formulario aún se llena.

Al escribir, el contexto crea cualquier bean intermedio que falte a través de su constructor sin argumentos, así que escribir el formulario en un `Hero` sin `Address` produce un nuevo `Address` poblado. Se reutiliza un `Address` existente.

Las anotaciones de validación de [Jakarta validation](/docs/data-binding/validation/jakarta-validation) en una propiedad anidada se detectan de la misma manera que en una propiedad de nivel superior. Una anotación como `@NotNull` en `Address.street` marca el enlace `address.street` como [requerido](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Las rutas se validan de manera anticipada
La ruta completa se valida cuando llamas a `bind`. Un error tipográfico en cualquier segmento, en el nivel superior o más profundo en la ruta, lanzará una `IllegalArgumentException`, así que los errores de enlace se presentan de inmediato en lugar de en el tiempo de lectura o escritura.
:::

<!-- vale off -->
## Datos de solo lectura {#readonly-data}
<!-- vale on -->

En ciertos escenarios, es posible que desees que tu aplicación muestre datos sin permitir que el usuario final los modifique directamente a través de la UI. Los enlaces de datos de solo lectura abordan esto. webforJ admite la configuración de enlaces como de solo lectura, por lo que puedes mostrar datos, pero no editarlos a través de componentes de UI vinculados.

### Configuración de enlaces de solo lectura {#configuring-readonly-bindings}

Para configurar un enlace de solo lectura, puedes configurar el enlace para desactivar o ignorar la entrada del componente de UI. Los datos permanecerán sin cambios desde la perspectiva de la UI, mientras que aún se actualizan programáticamente cuando sea necesario.

```java
// Configurando un campo de texto como de solo lectura en el contexto de enlace
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

En esta configuración, `readOnly` evita que el `nameTextField` acepte la entrada del usuario, por lo que el campo de texto muestra los datos sin permitir modificaciones.

:::info
El enlace puede marcar el componente como de solo lectura solo si los componentes de UI implementan la interfaz `ReadOnlyAware`.
:::

:::tip Componente de Solo Lectura vs Enlace de Solo Lectura
Es importante diferenciar entre enlaces que configuras como de solo lectura y componentes de UI que estableces para mostrarse como de solo lectura. Cuando marcas un enlace como de solo lectura, impacta en cómo el enlace gestiona los datos durante el proceso de escritura, no solo en el comportamiento de la UI.

Cuando marcas un enlace como de solo lectura, el sistema omite las actualizaciones de datos. Cualquier cambio en el componente de UI no se transmitirá de vuelta al modelo de datos. Como resultado, incluso si el componente de UI recibe de alguna manera la entrada del usuario, no actualizará el modelo de datos subyacente. Mantener esta separación protege la integridad de los datos en escenarios donde las acciones del usuario no deberían alterar los datos.

En contraste, establecer un componente de UI como de solo lectura, sin configurar el enlace en sí como de solo lectura, simplemente detiene al usuario de hacer cambios en el componente de UI, pero no impide que el enlace actualice el modelo de datos si ocurren cambios programáticamente o a través de otros medios.
:::

## Getters y Setters de enlaces {#binding-getters-and-setters}

Los setters y getters son métodos en Java que establecen y obtienen los valores de las propiedades, respectivamente. En el contexto del enlace de datos, se utilizan para definir cómo se actualizan y obtienen las propiedades dentro del marco de enlaces.

### Personalizando setters y getters {#customizing-setters-and-getters}

Aunque webforJ puede utilizar automáticamente convenciones de nomenclatura estándar de JavaBean (por ejemplo, `getName()`, `setName()` para una propiedad `name`), es posible que necesites definir un comportamiento personalizado. Esto es necesario cuando la propiedad no sigue la nomenclatura convencional o cuando el manejo de datos requiere lógica adicional.

### Usando getters personalizados {#using-custom-getters}

Los getters personalizados se utilizan cuando el proceso de recuperación del valor implica más que simplemente devolver una propiedad. Por ejemplo, podrías querer dar formato a la cadena, calcular un valor o registrar ciertas acciones cuando se accede a una propiedad.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Lógica personalizada: convertir el nombre a mayúsculas
  });
```

### Usando setters personalizados {#using-custom-setters}

Los setters personalizados entran en juego cuando establecer una propiedad implica operaciones adicionales como validación, transformación o efectos secundarios como registro o notificación a otras partes de tu aplicación.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Actualizando el nombre de " + hero.getName() + " a " + name);
    hero.setName(name); // Operación adicional: registro
  });
```
