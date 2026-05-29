---
sidebar_position: 2
title: Bindings
_i18n_hash: c567705312942e83f5e83a77f1d510a4
---
Un enlace en webforJ conecta una propiedad específica de un Java Bean a un componente de la interfaz de usuario (UI). Esta vinculación permite actualizaciones automáticas entre la UI y el modelo del backend. Cada enlace puede manejar la sincronización de datos, validación, transformación y gestión de eventos.

Puedes iniciar enlaces solo a través del `BindingContext`. Este gestiona una colección de instancias de enlace, cada una vinculando un componente de la UI a una propiedad de un bean. Facilita operaciones grupales en los enlaces, como la validación y la sincronización entre los componentes de la UI y las propiedades del bean. Actúa como un agregador, permitiendo acciones colectivas sobre múltiples enlaces, agilizando así la gestión del flujo de datos dentro de las aplicaciones.

:::tip Vinculación Automática
Esta sección introduce los conceptos básicos de la configuración manual de enlaces. Además, puedes crear enlaces automáticamente basados en los componentes de la UI en tu formulario. Una vez que comprendas los fundamentos, aprende más leyendo la sección de [Vinculación Automática](./automatic-binding).
:::

## Configurar enlaces {#configure-bindings}

Comienza creando una nueva instancia de `BindingContext` que gestione todos los enlaces para un modelo particular. Este contexto asegura que todos los enlaces puedan ser validados y actualizados de manera colectiva.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Cada formulario debe tener solo una instancia de `BindingContext`, y deberías usar esta instancia para todos los componentes en el formulario.
:::

### La propiedad vinculada {#the-bound-property}

Una propiedad de enlace es un campo o atributo específico de un Java Bean que puede ser vinculado a un componente de la UI en tu aplicación. Esta vinculación permite que los cambios en la UI afecten directamente la propiedad correspondiente del modelo de datos, y viceversa, facilitando una experiencia de usuario reactiva.

Al configurar un enlace, deberías proporcionar el nombre de la propiedad como una cadena. Este nombre debe coincidir con el nombre del campo en la clase Java Bean. Aquí tienes un ejemplo simple:

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

  // setters y getters
}
```

el método `bind` devuelve un `BindingBuilder` que crea el objeto `Binding` y puedes usar para configurar el enlace con varias configuraciones, el método `add` que es lo que realmente agrega el enlace al contexto.

### El componente vinculado {#the-bound-component}

El otro lado del enlace es el componente vinculado, que se refiere al componente de la UI que interactúa con la propiedad del Java Bean. El componente vinculado puede ser cualquier componente de la UI que soporte interacción y visualización del usuario, como campos de texto, cuadros combinados, casillas de verificación, o cualquier componente personalizado que implemente la interfaz `ValueAware`.

El componente vinculado sirve como el punto de interacción del usuario con el modelo de datos subyacente. Muestra datos al usuario y también captura las entradas del usuario, que luego se propagan de vuelta al modelo.

```java
TextField nameTextField = new TextField("Nombre");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Leer y escribir datos {#reading-and-writing-data}

### Leer datos {#reading-data}

Leer datos implica poblar los componentes de la UI con valores del modelo de datos. Esto se realiza típicamente cuando un formulario se muestra inicialmente, o cuando necesitas recargar los datos debido a cambios en el modelo subyacente. El método `read` proporcionado por `BindingContext` hace que este proceso sea sencillo.

```java
// Supongamos que el objeto Hero ha sido instanciado e inicializado
Hero hero = new Hero("Clark Kent", "Volador");

// El BindingContext ya está configurado con enlaces
context.read(hero);
```

En este ejemplo, el método `read` toma una instancia de `Hero` y actualiza todos los componentes de la UI vinculados para reflejar las propiedades del héroe. Si el nombre o poder del héroe cambia, los componentes de la UI correspondientes (como un `TextField` para el nombre y un `ComboBox` para los poderes) mostrarán estos nuevos valores.

### Escribir datos {#writing-data}

Escribir datos implica recopilar los valores de los componentes de la UI y actualizar el modelo de datos. Esto ocurre típicamente cuando un usuario envía un formulario. El método `write` maneja la validación y la actualización del modelo en un solo paso.

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

En el código anterior, cuando el usuario hace clic en el botón de enviar, se llama al método `write`. Realiza todas las validaciones configuradas y, si los datos pasan todas las verificaciones, actualiza el objeto `Hero` con nuevos valores de los componentes vinculados. Si los datos son válidos, puedes guardarlos en una base de datos o procesarlos más. Si hay errores de validación, deberías manejarlos apropiadamente, normalmente mostrando mensajes de error al usuario.

:::tip Informe de Errores de Validación
Todos los componentes centrales de webforJ tienen configuraciones predeterminadas para informar automáticamente errores de validación, ya sea en línea o a través de un popover. Puedes personalizar este comportamiento usando [Reporteros](./validation/reporters.md).
:::

<!-- vale off -->
## Datos de Solo Lectura {#readonly-data}
<!-- vale on -->

En ciertos escenarios, puede que desees que tu aplicación muestre datos sin permitir que el usuario final los modifique directamente a través de la UI. Aquí es donde las vinculaciones de datos de solo lectura se vuelven cruciales. webforJ soporta la configuración de enlaces para que sean de solo lectura, asegurando que puedas mostrar datos, pero no editarlos a través de componentes de UI vinculados.

### Configurando enlaces de solo lectura {#configuring-readonly-bindings}

Para configurar un enlace de solo lectura, puedes configurar el enlace para desactivar o ignorar la entrada del componente de la UI. Esto asegura que los datos permanezcan sin cambios desde la perspectiva de la UI, mientras que aún se actualizan programáticamente si es necesario.

```java
// Configurando un campo de texto para que sea de solo lectura en el contexto de enlace
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
  .readOnly()
  .add();
```

En esta configuración, `readOnly` asegura que el `nameTextField` no acepte entradas del usuario, haciendo que el campo de texto muestre los datos sin permitir modificaciones.

:::info
El enlace puede marcar al componente como de solo lectura solo si los componentes de la UI implementan la interfaz `ReadOnlyAware`.
:::

:::tip Componente de Solo Lectura vs Enlace de Solo Lectura
Es importante diferenciar entre los enlaces que configuras como de solo lectura y los componentes de UI que configuras para mostrar como de solo lectura. Cuando marcas un enlace como de solo lectura, impacta en cómo el enlace gestiona los datos durante el proceso de escritura, no solo en el comportamiento de la UI.

Cuando marcas un enlace como de solo lectura, el sistema omite las actualizaciones de datos. Cualquier cambio en el componente de la UI no se transmitirá de vuelta al modelo de datos. Esto asegura que incluso si el componente de la UI de alguna manera recibe una entrada del usuario, no actualizará el modelo de datos subyacente. Mantener esta separación es crucial para preservar la integridad de los datos en escenarios donde las acciones del usuario no deberían alterar los datos.

En contraste, establecer un componente de la UI como de solo lectura, sin configurar el enlace como de solo lectura, simplemente impide que el usuario realice cambios en el componente de la UI, pero no detiene el enlace de actualizar el modelo de datos si los cambios ocurren programáticamente o por otros medios.
:::

## Métodos de Obtención y Establecimiento de Enlaces {#binding-getters-and-setters}

Los métodos de establecimiento y obtención son métodos en Java que establecen y obtienen los valores de propiedades, respectivamente. En el contexto de la vinculación de datos, se utilizan para definir cómo se actualizan y recuperan las propiedades dentro del marco de conexión.

### Personalizando los métodos de obtención y establecimiento {#customizing-setters-and-getters}

Aunque webforJ puede usar automáticamente convenciones de nomenclatura estándar de JavaBean (por ejemplo, `getName()`, `setName()` para una propiedad `name`), puede que necesites definir un comportamiento personalizado. Esto es necesario cuando la propiedad no sigue la nomenclatura convencional o cuando el manejo de datos requiere lógica adicional.

### Usando métodos de obtención personalizados {#using-custom-getters}

Los métodos de obtención personalizados se utilizan cuando el proceso de recuperación del valor implica más que simplemente devolver una propiedad. Por ejemplo, podrías querer formatear la cadena, calcular un valor o registrar ciertas acciones cuando se accede a una propiedad.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useGetter(hero -> {
    String name = hero.getName();
    return name.toUpperCase(); // Lógica personalizada: convertir el nombre a mayúsculas
  });
```

### Usando métodos de establecimiento personalizados {#using-custom-setters}

Los métodos de establecimiento personalizados entran en juego cuando establecer una propiedad implica operaciones adicionales como validación, transformación o efectos secundarios como registro o notificación a otras partes de tu aplicación.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
  .bind(textField, "power")
  .useSetter((hero, name) -> {
    System.out.println("Actualizando el nombre de " + hero.getName() + " a " + name);
    hero.setName(name); // Operación adicional: registro
  });
```
