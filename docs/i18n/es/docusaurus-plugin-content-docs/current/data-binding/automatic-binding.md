---
sidebar_position: 5
title: Automatic Binding
_i18n_hash: 170c308c3b93a933f5fb85c0f0ec4f15
---
webforJ ofrece varias características que agilizan el proceso de configuración y vinculación automática para los desarrolladores. Esta sección demuestra cómo usar estas características de manera efectiva.

## Usando `BindingContext.of` {#using-bindingcontextof}

El método `BindingContext.of` vincula automáticamente los componentes de la interfaz de usuario a las propiedades de una clase bean especificada, simplificando el proceso de vinculación y reduciendo la configuración manual. Alínea los componentes vinculables, declarados como campos dentro de un formulario o aplicación, con las propiedades del bean según sus nombres.

```java
public class HeroRegistration extends App {
  // Componentes vinculables
  TextField name = new TextField("Campo de texto");
  ComboBox power = new ComboBox("Poder");

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

Usa la anotación `UseProperty` para especificar el nombre de la propiedad del bean cuando el nombre del campo de la interfaz de usuario no coincide con el nombre de la propiedad del bean.

```java
public class HeroRegistration extends App {
  // Componentes vinculables
  @UseProperty("name")
  TextField nameField = new TextField("Campo de texto");
  ComboBox power = new ComboBox("Poder");

  // ...
}
```

En el ejemplo anterior, el nombre del campo de la interfaz de usuario es `nameField`, pero la propiedad del bean es `name`. Puedes anotar el campo de la interfaz de usuario con el nombre de la propiedad del bean para asegurar una vinculación adecuada.

### Anotación `BindingExclude` {#bindingexclude-annotation}

Usa la anotación `BindingExclude` para excluir un componente de las configuraciones de vinculación automática cuando prefieres vincularlo manualmente o excluirlo por completo.

```java
public class HeroRegistration extends App {
  // Componentes vinculables
  @UseProperty("name")
  TextField nameField = new TextField("Campo de texto");

  @BindingExclude
  ComboBox power = new ComboBox("Poder");

  // ...
}
```

### Anotación `UseValidator` {#usevalidator-annotation}

Usa la anotación `UseValidator` para declarar validadores que imponen reglas de validación adicionales durante la vinculación. Los validadores se aplican en el orden en que los specifies.

```java
public class UserRegistration extends App {

  @UseValidator(EmailValidator.class)
  TextField email = new TextField("Dirección de correo electrónico");
}
```

### Anotación `UseTransformer` {#usetransformer-annotation}

Usa la anotación `UseTransformer` para declarar una clase transformadora directamente en un campo de la interfaz de usuario. El `BindingContext` aplica automáticamente la transformadora especificada.

```java
public class UserRegistration extends App {

  @UseProperty("date")
  @UseTransformer(DateTransformer.class)
  DateField dateField = new DateField("Campo de fecha");
}
```

### Anotación `BindingReadOnly` {#bindingreadonly-annotation}

Usa `BindingReadOnly` para [marcar un vínculo como de solo lectura](./bindings/#configuring-readonly-bindings).

```java
public class UserRegistration extends App {

  @BindingReadOnly
  TextField IDField = new TextField("ID de usuario");
}
```

### Anotación `BindingRequired` {#bindingrequired-annotation}

Usa `BindingReadOnly` para marcar un vínculo como requerido. Ver también [detecciones de vinculaciones requeridas](#required-binding-detections).

```java
public class UserRegistration extends App {

  @BindingRequired
  TextField emailField = new TextField("Correo electrónico del usuario");
}
```

## Escritura de datos automáticamente {#writing-data-automatically}

Para mejorar la capacidad de respuesta y la dinámica de las aplicaciones, puedes usar el método `observe`. Este método asegura que los cambios en los componentes de la interfaz de usuario se propaguen inmediatamente al modelo de datos. Es particularmente útil cuando necesitas una sincronización continua entre el modelo de datos y la interfaz de usuario.

El método `observe` registra un oyente de `ValueChangeEvent` en todos los vínculos en el contexto para monitorear los cambios realizados por el usuario y luego escribe instantáneamente estos cambios en las propiedades vinculadas del modelo si son válidos. Cuando invocas este método por primera vez, refleja las propiedades del bean en los componentes de la interfaz de usuario.

Aquí hay un ejemplo de cómo usar `observe`:

```java
Hero bean = new Hero("Superman", "Volador");
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

:::info Dirección de actualización
Esta vinculación automática es unidireccional; las actualizaciones se reflejan en el modelo cuando actualizas los componentes de la interfaz de usuario, pero los cambios en el modelo solo se reflejan en los componentes de la interfaz de usuario una vez, cuando invocas el método por primera vez.
:::

:::tip Consideraciones
Si bien `observe` aumenta la interactividad de las aplicaciones, es importante utilizarlo con prudencia:

- **Impacto en el rendimiento**: Actualizaciones frecuentes pueden afectar el rendimiento, especialmente con modelos complejos o servicios backend lentos.
- **Experiencia del usuario**: Las actualizaciones automáticas no deben interrumpir la capacidad del usuario para ingresar datos cómodamente.
:::


## Detecciones de vinculaciones requeridas {#required-binding-detections}

Cuando marcas un vínculo como requerido, marca el componente como requerido, siempre que el componente soporte este estado a través de la interfaz `RequiredAware`. La vinculación no aplica este estado por sí misma, sino que lo establece en el componente cuando sea aplicable.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
context
  .bind(emailField, "email")
    .required()
    .add()
```

 Al utilizar [anotaciones de Jakarta](./validation/jakarta-validation.md), la vinculación puede detectar automáticamente el estado requerido basado en la presencia de alguna de las siguientes anotaciones en las propiedades del bean:

1. `@NotNull`
2. `@NotEmpty`
3. `@NotBlank`
4. `@Size`
