---
sidebar_position: 5
title: Automatic Binding
description: >-
  Bind UI fields to bean properties automatically with BindingContext.of using
  UseProperty, BindingExclude, and UseValidator annotations.
_i18n_hash: 412c446b42788eae1b7f7e16194afda9
---
webforJ ofrece varias características que simplifican el proceso de configuración y vinculación automática para los desarrolladores. Esta sección demuestra cómo utilizar estas características de manera efectiva.

## Usando `BindingContext.of` {#using-bindingcontextof}

El método `BindingContext.of` vincula automáticamente componentes de la interfaz de usuario a las propiedades de una clase bean especificada, simplificando el proceso de vinculación y reduciendo la configuración manual. Alinea componentes vinculables, declarados como campos dentro de un formulario o aplicación, con propiedades de bean según sus nombres.

```java
public class HeroRegistration extends App {
  // Componentes vinculables
  TextField name = new TextField("Text Field");
  ComboBox power = new ComboBox("Power");

  // ...

  @Override
  public void run() throws WebforjException {
    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    // ...
  }
}
```

```java
public class Hero {
  private String name;
  private String power;

  // Setters y getters
}
```

### Anotación `UseProperty` {#useproperty-annotation}

Cuando deseas vincular una propiedad de bean a un componente de UI que tiene un nombre diferente, utiliza la anotación `UseProperty`. Esta anotación proporciona mayor precisión al vincular propiedades de bean a componentes de UI, especialmente cuando se trata de [propiedades de bean anidadas](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Se vincula a la propiedad name
  @UseProperty("name")
  TextField nameField = new TextField("Nombre");
  
  // Se vincula a la propiedad anidada address.street
  @UseProperty("address.street")
  TextField streetField = new TextField("Calle");

  // Se vincula a la propiedad power
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### Anotación `BindingExclude` {#bindingexclude-annotation}

Utiliza la anotación `BindingExclude` para excluir un componente de las configuraciones de vinculación automática cuando prefieras vincularlo manualmente o excluirlo por completo.

```java
public class HeroRegistration extends App {
  // Componentes vinculables
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### Anotación `UseValidator` {#usevalidator-annotation}

Utiliza la anotación `UseValidator` para declarar validadores que hagan cumplir reglas de validación adicionales durante la vinculación. Los validadores se aplican en el orden en que los especificas.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Dirección de Correo Electrónico");
}
```

### Anotación `UseTransformer` {#usetransformer-annotation}

Utiliza la anotación `UseTransformer` para declarar una clase transformadora directamente en un campo de UI. El `BindingContext` aplica automáticamente el transformador especificado.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Campo de Fecha");
}
```

### Anotación `BindingReadOnly` {#bindingreadonly-annotation}

Utiliza `BindingReadOnly` para [marcar una vinculación como de solo lectura](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("ID de Usuario");
}
```

### Anotación `BindingRequired` {#bindingrequired-annotation}

Utiliza `BindingRequired` para marcar una vinculación como requerida. Consulta también [detecciones de vinculación requeridas](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Correo Electrónico del Usuario");
}
```

## Escritura de datos automáticamente {#writing-data-automatically}

Para mejorar la capacidad de respuesta y dinamismo de las aplicaciones, puedes utilizar el método `observe`. Este método garantiza que los cambios en los componentes de UI se propaguen inmediatamente al modelo de datos. Es particularmente útil cuando necesitas una sincronización continua entre el modelo de datos y la interfaz de usuario.

El método `observe` registra un listener de `ValueChangeEvent` en todas las vinculaciones en el contexto para monitorear los cambios realizados por el usuario, luego escribe instantáneamente estos cambios en las propiedades vinculadas del modelo si son válidos. Cuando invocas este método por primera vez, refleja las propiedades del bean en los componentes de UI.

Aquí hay un ejemplo de cómo utilizar `observe`:

```java
Hero bean = new Hero("Superman", "Fly");
BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
context.observe(bean);
context.onValidate(e -> {
  submit.setEnabled(e.isValid());
});

submit.onClick(e -> {
  ValidationResult results = context.validate();
  if (results.isValid()) {
    // Toma acción con el bean.
  }
});
```

:::info Dirección de Actualización
Esta vinculación automática es unidireccional; las actualizaciones se reflejan en el modelo cuando actualizas componentes de UI, pero los cambios en el modelo solo se reflejan en los componentes de UI una vez, cuando invocas el método por primera vez.
:::

:::tip Consideraciones
Mientras que `observe` aumenta la interactividad de las aplicaciones, es importante usarlo con moderación:

- **Impacto en el Rendimiento**: Actualizaciones frecuentes pueden afectar el rendimiento, especialmente con modelos complejos o servicios de backend lentos.
- **Experiencia del Usuario**: Las actualizaciones automáticas no deberían interrumpir la capacidad del usuario para ingresar datos cómodamente.
:::


## Detecciones de vinculación requeridas {#required-binding-detections}

Cuando marcas una vinculación como requerida, marca el componente como requerido, siempre que el componente soporte este estado a través de la interfaz `RequiredAware`. La vinculación no hace cumplir este estado por sí misma, sino que lo establece en el componente cuando es aplicable.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 Al utilizar [anotaciones de Jakarta](./validation/jakarta-validation.md), la vinculación puede detectar automáticamente el estado requerido en función de la presencia de cualquiera de las siguientes anotaciones en las propiedades del bean:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
