---
sidebar_position: 2
title: Validators
sidebar_class_name: updated-content
_i18n_hash: 4af002debda2abb59282b5c6a1bf01d7
---
Los validadores validan datos dentro de sus componentes de interfaz de usuario contra restricciones definidas antes de comprometer estos datos al modelo de datos. Puede aplicar validadores para verificar que los datos cumplan ciertos criterios, como estar dentro de un rango específico, coincidir con un patrón o no estar vacíos.

Las validaciones se configuran por enlace, lo que permite que reglas específicas se apliquen a cada punto de datos individualmente. Cada pieza de datos se somete a validación de acuerdo con sus propios requisitos.

## Agregar validadores {#adding-validators}

Agregue validadores a un enlace utilizando el método `useValidator` en el `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "El nombre no puede estar vacío")
    .useValidator(value -> value.length() >= 3, "El nombre debe tener al menos 3 caracteres")
    .add();
```

En el ejemplo anterior, dos validadores verifican que el nombre no esté vacío y que contenga al menos tres caracteres.

:::tip Procesamiento de validadores
No hay límite en la cantidad de validadores que puede agregar por enlace. El enlace aplica los validadores en el orden de inserción y se detiene en la primera violación.
:::

## Implementación de validadores {#implementing-validators}

Puede crear validadores reutilizables personalizados implementando la interfaz `Validator<T>`, donde `T` es el tipo de datos que desea validar. Esta configuración implica definir el método de validación, que verifica los datos y devuelve un `ValidationResult`.

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
        return ValidationResult.invalid("Dirección de correo electrónico no válida");
    }
  }
}
```

### Uso de validadores en enlaces {#using-validators-in-bindings}

Una vez que haya definido un validador, puede aplicarlo fácilmente a cualquier enlace relevante dentro de su aplicación. Esto es particularmente útil para componentes que requieren reglas de validación comunes en diferentes partes de su aplicación, como direcciones de correo electrónico de usuarios o la fuerza de una contraseña.

Para aplicar el `EmailValidator` a un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

### Sobrescribiendo mensajes de validador {#overriding-validator-messages}

Puede personalizar los mensajes de error de los validadores en el momento de enlazarlos a un componente de interfaz de usuario específico. Esto le permite proporcionar información más detallada o contextual al usuario si la validación falla. Los mensajes personalizados son particularmente útiles cuando el mismo validador se aplica a múltiples componentes pero requiere diferentes orientaciones según el contexto en el que se utiliza.

Así es como se sobrescribe el mensaje predeterminado de un validador reutilizable en un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mensaje personalizado para dirección de correo electrónico no válida"))
    .add();
```

En el ejemplo anterior, el código aplica el `EmailValidator` a un campo de correo electrónico con un mensaje de error personalizado específicamente adaptado para ese campo.

:::tip Entendiendo `Validator.from`
El método `Validator.from` envuelve un validador pasado con uno nuevo, lo que le permite especificar un mensaje de error personalizado en caso de que el validador no soporte mensajes personalizados. Esta técnica es particularmente útil cuando necesita aplicar la misma lógica de validación en múltiples componentes pero con mensajes de error distintos y específicos para cada instancia.
:::

### Mensajes de validación dinámicos <DocChip chip='since' label='25.12' /> {#dynamic-validation-messages}

Por defecto, los mensajes de validación son cadenas estáticas configuradas una vez en el momento de enlace. En aplicaciones que admiten múltiples idiomas, estos mensajes estáticos no se actualizarán cuando el usuario cambie de localidad. Para resolver esto, tanto `useValidator` como los métodos de fábrica de `Validator` aceptan un `Supplier<String>` que se invoca cada vez que la validación falla, lo que permite que el mensaje se resuelva dinámicamente.

#### Validadores en línea con mensajes dinámicos {#inline-validators-with-dynamic-messages}

Pase un `Supplier<String>` en lugar de una cadena simple a `useValidator`:

```java {2,3}
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), () -> t("validation.name.required"))
    .useValidator(value -> value.length() >= 3, () -> t("validation.name.minLength"))
    .add();
```

Cada vez que se ejecuta la validación y el predicado falla, el proveedor llama a `t()` que resuelve el mensaje para la localidad actual.

#### Métodos de fábrica con mensajes dinámicos {#factory-methods-with-dynamic-messages}

Los métodos de fábrica `Validator.of` y `Validator.from` también aceptan proveedores:

```java {4,10}
// Crear un validador basado en un predicado con un mensaje dinámico
Validator<String> required = Validator.of(
    value -> !value.isEmpty(),
    () -> t("validation.required")
);

// Envolver un validador existente con un mensaje de anulación dinámica
Validator<String> email = Validator.from(
    new EmailValidator(),
    () -> t("validation.email.invalid")
);
```

#### Validadores personalizados conscientes de la localidad {#locale-aware-custom-validators}

Para validadores reutilizables que necesitan generar mensajes sensibles a la localidad internamente, implemente la interfaz `LocaleAware`. Cuando la localidad cambia a través de `BindingContext.setLocale()`, el contexto propaga automáticamente la nueva localidad a todos los validadores que implementan esta interfaz. La próxima ejecución de validación producirá mensajes en la localidad actualizada.
