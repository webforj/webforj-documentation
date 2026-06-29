---
sidebar_position: 2
title: Bindings
sidebar_class_name: updated-content
description: >-
  Link Java Bean properties to webforJ UI components through BindingContext to
  synchronize reads and writes between model and view.
_i18n_hash: 047676a64833283bcc160d7a8d226559
---
Un enlace en webforJ vincula una propiedad específica de un Java Bean a un componente de la interfaz de usuario (UI). Esta vinculación permite actualizaciones automáticas entre la UI y el modelo del backend. Cada enlace puede manejar la sincronización de datos, validación, transformación y gestión de eventos.

Puedes iniciar enlaces únicamente a través del `BindingContext`. Este gestiona una colección de instancias de enlace, cada una vinculando un componente de la UI a una propiedad de un bean. Facilita operaciones grupales en los enlaces, como validación y sincronización entre los componentes de la UI y las propiedades del bean. Actúa como un agregador, permitiendo acciones colectivas sobre múltiples enlaces, simplificando así la gestión del flujo de datos dentro de las aplicaciones.

:::tip Vinculación Automática
Esta sección introduce los conceptos básicos de la configuración manual de enlaces. Además, puedes crear enlaces automáticamente en función de los componentes de la interfaz de usuario en tu formulario. Una vez que comprendas los fundamentos, aprende más leyendo la sección de [Vinculación Automática](/docs/data-binding/automatic-binding).
:::

## Configurar enlaces {#configure-bindings}

Comienza creando una nueva instancia de `BindingContext` que gestiona todos los enlaces para un modelo particular. Este contexto valida y actualiza todos los enlaces de forma colectiva.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Cada formulario debe tener solo una instancia de `BindingContext`, y debes usar esta instancia para todos los componentes en el formulario.
:::

### La propiedad vinculada {#the-bound-property}

Una propiedad vinculada es un campo o atributo específico de un Java Bean que puede vincularse a un componente de la UI en tu aplicación. 
Esta vinculación permite que los cambios en la UI afecten directamente a la propiedad correspondiente del modelo de datos, y viceversa, 
de modo que la UI y el modelo de datos se mantengan sincronizados.

Al configurar un enlace, debes proporcionar el nombre de la propiedad como una cadena. Este nombre debe coincidir con el nombre del campo en la clase Java Bean. Aquí hay un ejemplo simple:

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);
context
  .bind(textField, "power")
  .add();
```

```java
public class Hero  {
  private String name;
  private String power;

  // setters and getters
}
```

el método `bind` devuelve un `BindingBuilder` que crea el objeto `Binding` y puedes usar para configurar el enlace con varias configuraciones, el método `add` que es lo que realmente agrega el enlace al contexto.

### El componente vinculado {#the-bound-component}

El otro lado del enlace es el componente vinculado, que se refiere al componente de la UI que interactúa con la propiedad del Java Bean. 
El componente vinculado puede ser cualquier componente de la UI que soporte interacción del usuario y visualización, como campos de texto, cuadros combinados, casillas de verificación o 
cualquier componente personalizado que implemente la interfaz `ValueAware`.

El componente vinculado sirve como el punto de interacción del usuario con el modelo de datos subyacente. 
Muestra datos al usuario y también captura las entradas del usuario que luego se propagan de nuevo al modelo.

```java
TextField nameTextField = new TextField("Nombre");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Leer y escribir datos {#reading-and-writing-data}

### Leer datos {#reading-data}

Leer datos implica poblar componentes de la UI con valores del modelo de datos. 
Esto se realiza típicamente cuando un formulario se muestra inicialmente, o cuando necesitas recargar los datos debido a cambios en el modelo subyacente. 
El método `read` proporcionado por `BindingContext` hace que este proceso sea sencillo.

```java
// Suponiendo que el objeto Hero ha sido instanciado e inicializado
Hero hero = new Hero("Clark Kent", "Volando");

// BindingContext ya está configurado con enlaces
context.read(hero);
```

En este ejemplo, el método `read` toma una instancia de `Hero` y actualiza todos los componentes de la UI vinculados para reflejar las propiedades del héroe. 
Si el nombre o el poder del héroe cambian, los componentes de la UI correspondientes (como un `TextField` para el nombre y un `ComboBox` para poderes) 
muestran estos nuevos valores.

### Escribir datos {#writing-data}

Escribir datos implica recolectar valores de los componentes de la UI y actualizar el modelo de datos. 
Esto ocurre típicamente cuando un usuario envía un formulario. El método `write` maneja la validación y la actualización del modelo en un solo paso.

```java
// Esto podría ser desencadenado por un evento de envío de formulario
submit.onClick(event -> {
  ValidationResult results = context.write(hero);
  if (results.isValid()) {
    // Los datos son válidos, y el objeto hero ha sido actualizado
    // repository.save(hero); 
  } else {
    // Manejar errores de validación
    // results.getMessages();
  }
});
```

En el código anterior, cuando el usuario hace clic en el botón de enviar, se llama al método `write`. 
Realiza todas las validaciones configuradas y, si los datos pasan todas las comprobaciones, actualiza el objeto `Hero` 
con nuevos valores de los componentes vinculados. 
Si los datos son válidos, podrías guardar en una base de datos o procesar más. Si hay errores de validación, 
deberías manejarlos adecuadamente, típicamente mostrando mensajes de error al usuario.


:::tip Informe de Errores de Validación
Todos los componentes centrales de webforJ tienen configuraciones predeterminadas para informar automáticamente errores de validación, ya sea en línea o a través de un popover. Puedes personalizar este comportamiento utilizando [Reporters](./validation/reporters.md).
:::

## Propiedades de bean anidadas <DocChip chip='since' label='26.01' /> {#nested-bean-properties}

Una propiedad de enlace puede ser una ruta con puntos que apunta a una propiedad dentro de un bean anidado. Cada segmento de la ruta sigue las convenciones estándar de getters y setters de JavaBean, por lo que `address.street` se lee a través de `getAddress().getStreet()` y se escribe a través de `getAddress().setStreet()`.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(streetField, "address.street").add();
context.bind(cityField, "address.city").add();
```

```java
public class Hero {
  private String name;
  private Address address;

  // getters and setters
}

public class Address {
  private String street;
  private String city;
  private String zip;

  // getters and setters
}
```

Al leer, una ruta se resuelve de manera segura incluso cuando un bean intermedio es `null`. Si un `Hero` no tiene `Address`, los componentes vinculados a `address.street` y `address.city` se leen como vacíos en lugar de lanzar una excepción, de modo que el formulario aún se pobla.

Al escribir, el contexto crea cualquier bean intermedio que falte a través de su constructor sin argumentos, por lo que escribir el formulario en un `Hero` sin `Address` produce un nuevo `Address` poblado. Un `Address` existente se reutiliza.

Las anotaciones de [validación de Jakarta](/docs/data-binding/validation/jakarta-validation) sobre una propiedad anidada se detectan de la misma manera que en una propiedad de nivel superior. Una anotación como `@NotNull` en `Address.street` marca el enlace `address.street` como [requerido](/docs/data-binding/automatic-binding#bindingrequired-annotation).

:::info Las rutas se validan de antemano
La ruta completa se valida cuando llamas a `bind`. Un error tipográfico en cualquier segmento, a nivel superior o más profundo en la ruta, lanza un `IllegalArgumentException`, por lo que los errores de vinculación emergen inmediatamente en lugar de en el momento de lectura o escritura.
:::

<!-- vale off -->
## Datos de solo lectura {#readonly-data}
<!-- vale on -->

En ciertas situaciones, es posible que desees que tu aplicación muestre datos sin permitir que el usuario final los modifique directamente a través de la UI. 
Los enlaces de datos de solo lectura abordan esto. webforJ admite la configuración de enlaces como de solo lectura, por lo que 
puedes mostrar datos, pero no editarlos a través de los componentes de UI vinculados.

### Configurar enlaces de solo lectura {#configuring-readonly-bindings}

Para establecer un enlace de solo lectura, puedes configurar el enlace para desactivar o ignorar la entrada del componente de la UI. 
Los datos permanecen sin cambios desde la perspectiva de la UI, mientras se actualizan programáticamente cuando sea necesario.

```java
// Configurando un campo de texto para que sea de solo lectura en el contexto de enlace
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

En esta configuración, `readOnly` detiene el `nameTextField` de aceptar entradas del usuario, por lo que el campo de texto muestra 
los datos sin permitir modificaciones.

:::info
El enlace puede marcar el componente como de solo lectura solo si los componentes de la UI implementan la interfaz `ReadOnlyAware`.
:::

:::tip Componentes de Solo Lectura vs Enlaces de Solo Lectura
Es importante diferenciar entre los enlaces que configuras como de solo lectura y los componentes de la UI que configuras para mostrarse como de solo lectura. 
Cuando marcas un enlace como de solo lectura, esto afecta cómo el enlace gestiona los datos durante el proceso de escritura, no solo el comportamiento de la UI.

Cuando marcas un enlace como de solo lectura, el sistema omite las actualizaciones de datos. Cualquier cambio en el componente de la UI no se transmitirá de vuelta al modelo de datos. 
Como resultado, incluso si el componente de la UI recibe entradas del usuario de alguna manera, no actualizará el modelo de datos subyacente. 
Mantener esta separación protege la integridad de los datos en escenarios donde las acciones del usuario no deberían alterar los datos.

Por otro lado, configurar un componente de la UI como de solo lectura, sin configurar el enlace como de solo lectura, simplemente detiene al usuario de hacer cambios 
en el componente de la UI, pero no impide que el enlace actualice el modelo de datos si los cambios ocurren programáticamente o a través de otros medios.
:::

## Métodos getters y setters en enlaces {#binding-getters-and-setters}

Los setters y getters son métodos en Java que establecen y obtienen los valores de las propiedades, respectivamente. 
En el contexto del enlace de datos, se utilizan para definir cómo se actualizan y recuperan las propiedades dentro del marco de enlace.

### Personalizando los setters y getters {#customizing-setters-and-getters}

Aunque webforJ puede utilizar automáticamente convenciones de nomenclatura estándar de JavaBean
(por ejemplo, `getName()`, `setName()` para una propiedad `name`), es posible que debas definir un comportamiento personalizado. 
Esto es necesario cuando la propiedad no sigue la nomenclatura convencional o cuando el manejo de datos requiere lógica adicional.

### Usando getters personalizados {#using-custom-getters}

Los getters personalizados se utilizan cuando el proceso de recuperación de valores implica más que simplemente devolver una propiedad. 
Por ejemplo, podrías querer formatear la cadena, calcular un valor o registrar ciertas acciones cuando se accede a una propiedad.

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

Los setters personalizados entran en juego cuando establecer una propiedad implica operaciones adicionales como validación, transformación o efectos secundarios 
como registrar o notificar a otras partes de tu aplicación.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Actualizando el nombre de " + hero.getName() + " a " + name);
    hero.setName(name); // Operación adicional: registro
  });
```
