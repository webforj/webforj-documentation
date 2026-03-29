---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 996b617e97e439660bbe69f15d6355b9
---
Los validadores validan datos dentro de tus componentes de interfaz de usuario contra restricciones definidas antes de comprometer esos datos al modelo de datos. Puedes aplicar validadores para verificar que los datos cumplen con ciertos criterios, como estar dentro de un rango especificado, coincidir con un patrón o no estar vacíos.

Las validaciones se configuran por enlace, lo que permite que reglas específicas se apliquen a cada punto de datos individualmente. Cada pieza de datos pasa por validación de acuerdo con sus propios requisitos.

## Agregar validadores {#adding-validators}

Agrega validadores a un enlace usando el método `useValidator` en el `BindingBuilder`.

```java
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), "El nombre no puede estar vacío")
  .useValidator(value -> value.length() >= 3, "El nombre debe tener al menos 3 caracteres")
  .add();
```

En el ejemplo anterior, dos validadores verifican que el nombre no esté vacío y que contenga al menos tres caracteres.

:::tip Procesamiento de validadores
No hay límite en el número de validadores que puedes agregar por enlace. El enlace aplica los validadores según el orden de inserción y se detiene en la primera violación.
:::

## Implementación de validadores {#implementing-validators}

Puedes crear validadores reutilizables personalizados implementando la interfaz `Validator<T>`, donde `T` es el tipo de dato que deseas validar. Esta configuración implica definir el método validate, que verifica los datos y devuelve un `ValidationResult`.

Aquí hay un ejemplo de un validador reutilizable que verifica si el correo electrónico de un usuario es válido.

```java
import com.webforj.data.validation.server.ValidationResult;
import com.webforj.data.validation.server.validator.Validator;

public class EmailValidator implements Validator<String> {
  private static final String PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

  @Override
  public ValidationResult validate(String value) {
    if (value.matches(PATTERN)) {
        return ValidationResult.valid();
    } else {
        return ValidationResult.invalid("Dirección de correo electrónico inválida");
    }
  }
}
```

### Uso de validadores en enlaces {#using-validators-in-bindings}

Una vez que has definido un validador, puedes aplicarlo fácilmente a cualquier enlace relevante dentro de tu aplicación. Esto es particularmente útil para componentes que requieren reglas de validación comunes en diferentes partes de tu aplicación, como direcciones de correo electrónico de usuario o la solidez de las contraseñas.

Para aplicar el `EmailValidator` a un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
  .useValidator(new EmailValidator())
  .add();
```

### Sobrescribiendo mensajes de validadores {#overriding-validator-messages}

Puedes personalizar los mensajes de error de los validadores en el punto de enlace a un componente de interfaz de usuario específico. Esto te permite proporcionar información más detallada o contextual al usuario si la validación falla. Los mensajes personalizados son particularmente útiles cuando el mismo validador se aplica a múltiples componentes pero requiere diferentes orientaciones según el contexto en el que se usa.

Aquí te mostramos cómo sobrescribir el mensaje predeterminado de un validador reutilizable en un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
  .useValidator(
    Validator.from(new EmailValidator(), "Mensaje personalizado para dirección de correo electrónico inválida"))
  .add();
```

En el ejemplo anterior, el código aplica el `EmailValidator` a un campo de correo electrónico con un mensaje de error personalizado específicamente adaptado para ese campo.

:::tip Entendiendo `Validator.from`
El método `Validator.from` envuelve un validador pasado con uno nuevo, permitiendo especificar un mensaje de error personalizado en caso de que el validador no soporte mensajes personalizados. Esta técnica es particularmente útil cuando necesitas aplicar la misma lógica de validación a través de múltiples componentes pero con mensajes de error distintos y específicos para cada instancia.
:::

### Mensajes de validación dinámicos <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Por defecto, los mensajes de validación son cadenas estáticas establecidas una vez en el momento del enlace. En aplicaciones que soportan múltiples idiomas, estos mensajes estáticos no se actualizarán cuando el usuario cambie de locales. Para resolver esto, tanto `useValidator` como los métodos de fábrica de `Validator` aceptan un `Supplier<String>` que se invoca cada vez que la validación falla, permitiendo que el mensaje se resuelva dinámicamente.

#### Validadores en línea con mensajes dinámicos {#inline-validators-with-dynamic-messages}

Pasa un `Supplier<String>` en lugar de una cadena simple a `useValidator`:

```java {2,3}
context.bind(nameTextField, "name")
  .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
  .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
  .add();
```

Cada vez que se ejecuta la validación y el predicado falla, el proveedor llama a `t()`, que resuelve el mensaje para el locale actual.

#### Métodos de fábrica con mensajes dinámicos {#factory-methods-with-dynamic-messages}

Los métodos de fábrica `Validator.of` y `Validator.from` también aceptan proveedores:

```java {4,10}
// Crea un validador basado en un predicado con un mensaje dinámico
Validator<String> required = Validator.of(
  value -> !value.isEmpty(),
  () -> t("validation.required")
);

// Envuelve un validador existente con un mensaje de anulación dinámico
Validator<String> email = Validator.from(
  new EmailValidator(),
  () -> t("validation.email.invalid")
);
```

#### Validadores personalizados sensibles a la configuración regional {#locale-aware-custom-validators}

Para validadores reutilizables que necesitan producir mensajes sensibles a la configuración regional internamente, implementa la interfaz `LocaleAware`. Cuando la configuración regional cambia a través de `BindingContext.setLocale()`, el contexto propaga automáticamente la nueva configuración regional a todos los validadores que implementen esta interfaz. La siguiente ejecución de validación producirá mensajes en la configuración regional actualizada.
