---
sidebar_position: 2
title: Bindings
_i18n_hash: fa6155c6e1eb2724d684d042f561c8a3
---
Un enlace en webforJ vincula una propiedad específica de un Java Bean a un componente de UI. Este enlace permite actualizaciones automáticas entre la UI y el modelo de backend. Cada enlace puede manejar la sincronización de datos, validación, transformación y gestión de eventos.

Puedes iniciar enlaces únicamente a través del `BindingContext`. Este gestiona una colección de instancias de enlace, cada una vincula un componente de UI a una propiedad de un bean. Facilita operaciones grupales sobre los enlaces, como validación y sincronización entre los componentes de UI y las propiedades del bean. Actúa como un agregador, permitiendo acciones colectivas sobre múltiples enlaces, simplificando así la gestión del flujo de datos dentro de las aplicaciones.

:::tip Enlazado Automático
Esta sección introduce los conceptos básicos de la configuración manual de enlaces. Además, puedes crear enlaces automáticamente basados en los componentes de UI en tu formulario. Una vez que comprendas los fundamentos, aprende más leyendo la sección [Enlazado Automático](./automatic-binding).
:::

## Configurar enlaces {#configure-bindings}

Comienza creando una nueva instancia de `BindingContext` que gestiona todos los enlaces para un modelo particular. Este contexto asegura que todos los enlaces puedan ser validados y actualizados colectivamente.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
```

:::info
Cada formulario debe tener solo una instancia de `BindingContext`, y debes utilizar esta instancia para todos los componentes en el formulario.
:::

### La propiedad vinculada {#the-bound-property}

Una propiedad de enlace es un campo o atributo específico de un Java Bean que se puede vincular a un componente de UI en tu aplicación. Este enlace permite que los cambios en la UI afecten directamente la propiedad correspondiente del modelo de datos, y viceversa, facilitando una experiencia de usuario reactiva.

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

  // setters y getters
}
```

el método `bind` devuelve un `BindingBuilder` que crea el objeto `Binding`, y puedes usarlo para configurar varias configuraciones del enlace, el método `add` que es lo que realmente agrega el enlace al contexto.

### El componente vinculado {#the-bound-component}

El otro lado del enlace es el componente vinculado, que se refiere al componente de UI que interactúa con la propiedad del Java Bean. El componente vinculado puede ser cualquier componente de UI que soporte interacción del usuario y visualización, como campos de texto, cuadros combinados, casillas de verificación, o cualquier componente personalizado que implemente la interfaz `ValueAware`.

El componente vinculado sirve como el punto de interacción del usuario con el modelo de datos subyacente. Muestra datos al usuario y también captura las entradas del usuario que luego se propagan de nuevo al modelo.

```java
TextField nameTextField = new TextField("Nombre");
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name").add();
```

## Leer y escribir datos {#reading-and-writing-data}

### Leer datos {#reading-data}

Leer datos implica poblar componentes de UI con valores del modelo de datos. Esto se hace típicamente cuando un formulario se muestra inicialmente, o cuando necesitas recargar los datos debido a cambios en el modelo subyacente. El método `read` proporcionado por `BindingContext` hace que este proceso sea sencillo.

```java
// Supongamos que el objeto Hero ha sido instanciado e inicializado
Hero hero = new Hero("Clark Kent", "Volador");

// BindingContext ya está configurado con enlaces
context.read(hero);
```

En este ejemplo, el método `read` toma una instancia de `Hero` y actualiza todos los componentes de UI vinculados para reflejar las propiedades del héroe. Si el nombre o poder del héroe cambia, los componentes de UI correspondientes (como un `TextField` para el nombre y un `ComboBox` para los poderes) mostrarán estos nuevos valores.

### Escribir datos {#writing-data}

Escribir datos implica recopilar valores de los componentes de UI y actualizar el modelo de datos. Esto ocurre típicamente cuando un usuario envía un formulario. El método `write` maneja la validación y actualización del modelo en un solo paso.

```java
// Esto podría ser activado por un evento de envío de formulario
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

En el código anterior, cuando el usuario hace clic en el botón de enviar, se llama al método `write`. Realiza todas las validaciones configuradas y, si los datos pasan todas las comprobaciones, actualiza el objeto `Hero` con nuevos valores de los componentes vinculados. Si los datos son válidos, podrías guardar en una base de datos o procesarlos más. Si hay errores de validación, deberías manejarlos apropiadamente, generalmente mostrando mensajes de error al usuario.

:::tip Informe de Errores de Validación
Todos los componentes básicos de webforJ tienen configuraciones predeterminadas para informar automáticamente errores de validación, ya sea en línea o a través de un popover. Puedes personalizar este comportamiento utilizando [Reporters](./validation/reporters.md).
:::

<!-- vale off -->
## Datos Solo Lectura {#readonly-data}
<!-- vale on -->

En ciertas situaciones, es posible que desees que tu aplicación muestre datos sin permitir que el usuario final los modifique directamente a través de la UI. Aquí es donde los enlaces de datos solo lectura se vuelven cruciales. webforJ admite la configuración de enlaces para que sean de solo lectura, asegurando que puedes mostrar datos, pero no editarlos a través de componentes de UI vinculados.

### Configurando enlaces de solo lectura {#configuring-readonly-bindings}

Para configurar un enlace de solo lectura, puedes configurar el enlace para desactivar o ignorar la entrada del componente de UI. Esto asegura que los datos permanezcan sin cambios desde la perspectiva de la UI, mientras que aún se pueden actualizar programáticamente si es necesario.

```java
// Configurando un campo de texto para ser de solo lectura en el contexto de enlace
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context.bind(nameTextField, "name")
    .readOnly()
    .add();
```

En esta configuración, `readOnly` asegura que el `nameTextField` no acepte la entrada del usuario, haciendo que el campo de texto muestre los datos sin permitir modificaciones.

:::info
El enlace puede marcar el componente como de solo lectura solo si los componentes de UI implementan la interfaz `ReadOnlyAware`.
:::

:::tip Componente Solo Lectura vs Enlace Solo Lectura
Es importante diferenciar entre los enlaces que configuras como de solo lectura y los componentes de UI que estableces para mostrar como de solo lectura. Cuando marcas un enlace como de solo lectura, impacta cómo el enlace gestiona los datos durante el proceso de escritura, no solo el comportamiento de la UI.

Cuando marcas un enlace como de solo lectura, el sistema omite las actualizaciones de datos. Cualquier cambio en el componente de UI no se transmitirá de nuevo al modelo de datos. Esto asegura que, incluso si el componente de UI recibe entrada del usuario de alguna manera, no actualizará el modelo de datos subyacente. Mantener esta separación es crucial para preservar la integridad de los datos en escenarios donde las acciones del usuario no deberían alterar los datos.

En contraste, establecer un componente de UI como de solo lectura, sin configurar el enlace como de solo lectura, simplemente impide al usuario realizar cambios en el componente de UI pero no detiene al enlace de actualizar el modelo de datos si los cambios ocurren programáticamente o a través de otros medios.
:::

## Enlazando getters y setters {#binding-getters-and-setters}

Los setters y getters son métodos en Java que establecen y obtienen los valores de las propiedades, respectivamente. En el contexto del enlace de datos, se utilizan para definir cómo se actualizan y recuperan las propiedades dentro del marco de enlace.

### Personalizando setters y getters {#customizing-setters-and-getters}

Aunque webforJ puede utilizar automáticamente las convenciones de nombrado estándar de JavaBean (por ejemplo, `getName()`, `setName()` para una propiedad `name`), es posible que necesites definir un comportamiento personalizado. Esto es necesario cuando la propiedad no sigue la nomenclatura convencional o cuando el manejo de datos requiere lógica adicional.

### Usando getters personalizados {#using-custom-getters}

Los getters personalizados se utilizan cuando el proceso de recuperación de valores implica más que simplemente devolver una propiedad. Por ejemplo, es posible que quieras formatear la cadena, calcular un valor o registrar ciertas acciones cuando se accede a una propiedad.

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

Los setters personalizados entran en juego cuando establecer una propiedad implica operaciones adicionales, como validación, transformación o efectos secundarios como registro o notificación a otras partes de tu aplicación.

```java
BindingContext<Hero> context = new BindingContext<>(Hero.class);
context
    .bind(textField, "power")
    .useSetter((hero, name) -> {
        System.out.println("Actualizando nombre de " + hero.getName() + " a " + name);
        hero.setName(name); // Operación adicional: registro
    });
```
