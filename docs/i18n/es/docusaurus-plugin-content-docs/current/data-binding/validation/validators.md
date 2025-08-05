---
sidebar_position: 2
title: Validators
_i18n_hash: 98f40d70b15464d8c7ee48710b07d8fc
---
Los validadores revisan los datos dentro de tus componentes de UI contra las restricciones definidas antes de comprometer estos datos al modelo de datos. Puedes aplicar validadores para asegurarte de que los datos cumplan con ciertos criterios, como estar dentro de un rango especificado, coincidir con un patrón o no estar vacíos.

Las validaciones se configuran por enlace, permitiendo que reglas específicas se apliquen a cada punto de datos individualmente. Esta configuración asegura que cada pieza de datos pase por la validación de acuerdo con sus propios requisitos.

## Agregar validadores {#adding-validators}

Agrega validadores a un enlace utilizando el método `useValidator` en el `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "El nombre no puede estar vacío")
    .useValidator(value -> value.length() >= 3, "El nombre debe tener al menos 3 caracteres")
    .add();
```

En el ejemplo anterior, dos validadores verifican que el nombre no esté vacío y que contenga al menos tres caracteres.

:::tip Procesamiento de validadores
No hay límite en el número de validadores que puedes agregar por enlace. El enlace aplica los validadores en el orden de inserción y se detiene en la primera violación.
:::

## Implementación de validadores {#implementing-validators}

Puedes crear validadores reutilizables personalizados implementando la interfaz `Validator<T>`, donde `T` es el tipo de dato que deseas validar. Esta configuración implica definir el método validate, que revisa los datos y retorna un `ValidationResult`.

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

## Uso de validadores en enlaces {#using-validators-in-bindings}

Una vez que has definido un validador, puedes aplicarlo fácilmente a cualquier enlace relevante dentro de tu aplicación. Esto es particularmente útil para componentes que requieren reglas de validación comunes en diferentes partes de tu aplicación, como direcciones de correo electrónico de usuarios o la fortaleza de contraseñas.

Para aplicar el `EmailValidator` a un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Sobrescribiendo mensajes de validadores {#overriding-validator-messages}

Puedes personalizar los mensajes de error de los validadores en el punto de enlace a un componente de UI específico. Esto te permite proporcionar información más detallada o contextual al usuario si la validación falla. Los mensajes personalizados son especialmente útiles cuando el mismo validador se aplica a múltiples componentes, pero requiere diferentes orientaciones al usuario basadas en el contexto en el que se utiliza.

Aquí se muestra cómo sobrescribir el mensaje predeterminado de un validador reutilizable en un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mensaje personalizado para dirección de correo electrónico inválida"))
    .add();
```

En el ejemplo anterior, el código aplica el `EmailValidator` a un campo de correo electrónico con un mensaje de error personalizado específicamente diseñado para ese campo. Esto permite una experiencia de usuario más dirigida y útil si la validación falla.

:::tip Entendiendo `Validator.from`
El método `Validator.from` envuelve un validador pasado con uno nuevo, permitiéndote especificar un mensaje de error personalizado en caso de que el validador no soporte mensajes personalizados. Esta técnica es particularmente útil cuando necesitas aplicar la misma lógica de validación en múltiples componentes, pero con mensajes de error distintos y específicos del contexto para cada instancia.
:::
