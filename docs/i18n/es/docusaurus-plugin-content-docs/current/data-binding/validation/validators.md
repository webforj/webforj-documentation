---
sidebar_position: 2
title: Validators
_i18n_hash: 3d41925977054029c22c2110455dd419
---
Los validadores verifican los datos dentro de sus componentes de interfaz de usuario contra restricciones definidas antes de comprometer estos datos al modelo de datos. Puede aplicar validadores para garantizar que los datos cumplan con ciertos criterios, como estar dentro de un rango específico, coincidir con un patrón o no estar vacíos.

Las validaciones se configuran por enlace, permitiendo que reglas específicas se apliquen a cada punto de datos de manera individual. Esta configuración asegura que cada pieza de datos sea sometida a validación de acuerdo a sus propios requisitos.

## Agregando validadores {#adding-validators}

Agregue validadores a un enlace utilizando el método `useValidator` en el `BindingBuilder`.

```java
context.bind(nameTextField, "name")
    .useValidator(value -> !value.isEmpty(), "El nombre no puede estar vacío")
    .useValidator(value -> value.length() >= 3, "El nombre debe tener al menos 3 caracteres")
    .add();
```

En el ejemplo anterior, dos validadores verifican que el nombre no esté vacío y que contenga al menos tres caracteres.

:::tip Procesamiento de validadores
No hay límite en el número de validadores que puede agregar por enlace. El enlace aplica los validadores en el orden de inserción y se detiene en la primera violación.
:::

## Implementando validadores {#implementing-validators}

Puede crear validadores reutilizables personalizados implementando la interfaz `Validator<T>`, donde `T` es el tipo de dato que desea validar. Esta configuración implica definir el método de validación, que verifica los datos y devuelve un `ValidationResult`.

Aquí hay un ejemplo de un validador reutilizable que verifica si la dirección de correo electrónico de un usuario es válida.

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

## Usando validadores en enlaces {#using-validators-in-bindings}

Una vez que haya definido un validador, puede aplicarlo fácilmente a cualquier enlace relevante dentro de su aplicación. Esto es particularmente útil para componentes que requieren reglas de validación comunes en diferentes partes de su aplicación, como direcciones de correo electrónico de usuario o la fortaleza de la contraseña.

Para aplicar el `EmailValidator` a un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(ageField, "age")
    .useValidator(new EmailValidator())
    .add();
```

## Sobrescribiendo mensajes de validador {#overriding-validator-messages}

Puede personalizar los mensajes de error de los validadores en el punto de enlace a un componente de interfaz de usuario específico. Esto le permite proporcionar información más detallada o contextual al usuario si la validación falla. Los mensajes personalizados son particularmente útiles cuando el mismo validador se aplica a múltiples componentes pero requiere diferentes orientaciones para el usuario según el contexto en el que se utiliza.

Aquí se muestra cómo sobrescribir el mensaje predeterminado de un validador reutilizable en un enlace:

```java
BindingContext<User> context = new BindingContext<>(User.class);
context.bind(emailField, "email")
    .useValidator(
        Validator.from(new EmailValidator(), "Mensaje personalizado para dirección de correo electrónico no válida"))
    .add();
```

En el ejemplo anterior, el código aplica el `EmailValidator` a un campo de correo electrónico con un mensaje de error personalizado específicamente adaptado para ese campo. Esto permite una experiencia de usuario más dirigida y útil si la validación falla.

:::tip Entendiendo `Validator.from`
El método `Validator.from` envuelve un validador pasado con uno nuevo, permitiéndole especificar un mensaje de error personalizado en caso de que el validador no soporte mensajes personalizados. Esta técnica es particularmente útil cuando necesita aplicar la misma lógica de validación a múltiples componentes pero con mensajes de error distintos y específicos al contexto para cada instancia.
:::
