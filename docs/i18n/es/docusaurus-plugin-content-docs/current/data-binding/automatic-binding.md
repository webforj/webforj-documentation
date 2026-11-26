---
sidebar_position: 5
title: Enlace automático
description: >-
  Vincula automáticamente los campos de la interfaz con las propiedades del bean
  mediante BindingContext.of y las anotaciones UseProperty, BindingExclude y
  UseValidator.
_i18n_title_hash: 666e71f372d1b611262598ca5cd23499
_i18n_description_hash: 985dfb18ded910f005bf2a2e040a8f9d
_i18n_hash: 55ac69466d077caf09fa5b765b76074a
---
webforJ ofrece varias funciones que agilizan la configuración y el proceso de vinculación automática para los desarrolladores. En esta sección se muestra cómo usar estas funciones de manera eficaz.

## Uso de `BindingContext.of` {#using-bindingcontextof}

El método `BindingContext.of` vincula automáticamente los componentes de la interfaz de usuario con las propiedades de una clase bean especificada, lo que simplifica el proceso de vinculación y reduce la configuración manual. Alinea los componentes vinculables, declarados como campos dentro de un formulario o una aplicación, con las propiedades del bean según sus nombres.

```java
public class HeroRegistration extends App {
  // Bindable components
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

  // Setters and getters
}
```

### Anotación `UseProperty` {#useproperty-annotation}

Cuando quieras vincular una propiedad de un bean con un componente de la interfaz de usuario que tenga un nombre diferente, usa la anotación `UseProperty`.
Esta anotación permite vincular con mayor precisión las propiedades de los beans a los componentes de la interfaz de usuario, especialmente al trabajar con [propiedades anidadas de beans](/docs/data-binding/bindings#nested-bean-properties).

```java
public class HeroRegistration extends App {
  // Binds to the name property
  @UseProperty("name")
  TextField nameField = new TextField("Name");

  // Binds to the nested address.street property
  @UseProperty("address.street")
  TextField streetField = new TextField("Street");

  // Binds to the power property
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### Anotación `BindingExclude` {#bindingexclude-annotation}

Usa la anotación `BindingExclude` para excluir un componente de las configuraciones de vinculación automática cuando prefieras vincularlo manualmente o excluirlo por completo.

```java
public class HeroRegistration extends App {
  // Bindable components
  @UseProperty("name")
  TextField nameField = new TextField("Text Field");

  @BindingExclude
  ComboBox power = new ComboBox("Power");

  // ...
}
```

### Anotación `UseValidator` {#usevalidator-annotation}

Usa la anotación `UseValidator` para declarar validadores que apliquen reglas de validación adicionales durante la vinculación. Los validadores se aplican en el orden en que los especifiques.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Email Address");
}
```

### Anotación `UseTransformer` {#usetransformer-annotation}

Usa la anotación `UseTransformer` para declarar una clase de transformación directamente en un campo de la interfaz de usuario. `BindingContext` aplica automáticamente el transformador especificado.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Date Field");
}
```

### Anotación `BindingReadOnly` {#bindingreadonly-annotation}

Usa `BindingReadOnly` para [marcar una vinculación como de solo lectura](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("User ID");
}
```

### Anotación `BindingRequired` {#bindingrequired-annotation}

Usa `BindingReadOnly` para marcar una vinculación como obligatoria. Consulta también [detección de vinculaciones obligatorias](#required-binding-detections).


```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("User Email");
}
```

## Escritura automática de datos {#writing-data-automatically}

Para aumentar la capacidad de respuesta y el dinamismo de las aplicaciones, puedes usar el método `observe`. Este método garantiza que los cambios en los componentes de la interfaz de usuario se propaguen inmediatamente al modelo de datos. Es especialmente útil cuando necesitas una sincronización continua entre el modelo de datos y la interfaz de usuario.

El método `observe` registra un listener de `ValueChangeEvent` en todas las vinculaciones del contexto para supervisar los cambios realizados por el usuario y, luego, escribe de inmediato esos cambios en las propiedades vinculadas del modelo si son válidos. La primera vez que invocas este método, refleja las propiedades del bean en los componentes de la interfaz de usuario.

Aquí tienes un ejemplo de cómo usar `observe`:

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
    // Take action with the bean.
  }
});
```

:::info Dirección de actualización
Esta vinculación automática es unidireccional: las actualizaciones se reflejan en el modelo cuando actualizas los componentes de la interfaz de usuario, pero los cambios en el modelo solo se reflejan en los componentes de la interfaz de usuario una vez, cuando invocas el método por primera vez.
:::

:::tip Consideraciones
Aunque `observe` aumenta la interactividad de las aplicaciones, es importante usarlo con criterio:

- **Impacto en el rendimiento**: las actualizaciones frecuentes pueden afectar al rendimiento, especialmente con modelos complejos o servicios de backend lentos.
- **Experiencia del usuario**: las actualizaciones automáticas no deberían impedir que el usuario introduzca datos cómodamente.
:::


## Detección de vinculaciones obligatorias {#required-binding-detections}

Al marcar una vinculación como obligatoria, también se marca el componente como obligatorio, siempre que el componente admita este estado mediante la interfaz `RequiredAware`. La vinculación no impone este estado por sí misma, sino que lo establece en el componente cuando corresponde.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 Al usar [anotaciones de Jakarta](./validation/jakarta-validation.md), la vinculación puede detectar automáticamente el estado obligatorio según la presencia de cualquiera de las siguientes anotaciones en las propiedades del bean:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`*

:::note Uso de `@Size` para una vinculación obligatoria <DocChip chip='since' label='26.10' />
`@Size` solo marca el componente vinculado como obligatorio si tiene definido un valor `min`.
En versiones anteriores, `@Size` siempre marca el componente vinculado como obligatorio.
:::
