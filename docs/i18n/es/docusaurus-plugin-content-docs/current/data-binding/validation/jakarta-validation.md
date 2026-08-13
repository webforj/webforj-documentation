---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
description: >-
  Apply Jakarta Bean Validation annotations to bean properties and activate
  JakartaValidator on a BindingContext with locale-aware messages.
_i18n_hash: e5b90cd31ee5ca5eab453a1c087967da
---
[Java Bean Validation](https://beanvalidation.org/) es ampliamente reconocido como el estándar para integrar lógica de validación en aplicaciones Java. Utiliza un enfoque uniforme para la validación al permitir que los desarrolladores anoten las propiedades del modelo de dominio con restricciones de validación declarativa. Estas restricciones se hacen cumplir en tiempo de ejecución, con opciones tanto para reglas incorporadas como para reglas definidas por el usuario.

webforJ se integra con Bean Validation a través del adaptador `JakartaValidator`, proporcionando un soporte completo desde el principio.

## Instalación {#installation}

Es necesario incluir una implementación compatible, como [Hibernate Validator](https://hibernate.org/validator/), en tu classpath. Si tu entorno no incluye esta implementación por defecto, puedes agregarla manualmente utilizando las siguientes dependencias de Maven:

```xml
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>8.0.1.Final</version>
</dependency>
<dependency>
  <groupId>org.glassfish.expressly</groupId>
  <artifactId>expressly</artifactId>
  <version>5.0.0</version>
</dependency>
```

## El `JakartaValidator` {#the-jakartavalidator}

La clase `JakartaValidator` actúa como un adaptador, conectando el contexto de enlace de webforJ con Jakarta Validation. Esta integración permite el uso de reglas de validación complejas directamente a través de anotaciones en la clase bean.

### Activando `JakartaValidator` {#activating-jakartavalidator}

Para activar el `JakartaValidator` en todo el contexto, generalmente se utiliza el parámetro `useJakartaValidator` al construir el `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Definiendo restricciones para las propiedades del bean {#defining-constraints-for-bean-properties}

Las restricciones basadas en anotaciones se aplican directamente dentro de la clase bean para especificar condiciones de validación, como se ilustra en el siguiente ejemplo:

```java
public class Hero {
  @NotEmpty(message = "El nombre no puede estar vacío")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Poder no especificado")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Poder inválido")
  private String power;

  // getters y setters
}
```

Tales restricciones son tan efectivas como las establecidas programáticamente durante la inicialización de enlace y producen resultados de validación consistentes.

:::warning
Actualmente, el `JakartaValidator` solo reconoce restricciones que se asignan directamente a las propiedades e ignora cualquier validación no directamente asociada a propiedades.
:::

### Validando beans anidados <DocChip chip='since' label='26.01' /> {#validating-nested-beans}

Declara las restricciones directamente en los propios campos del bean anidado. Cuando enlazas uno de esos campos a través de una [ruta de propiedad con puntos](/docs/data-binding/bindings#nested-bean-properties), la restricción sobre esa propiedad se aplica al enlace de la misma manera que lo hace para una propiedad de nivel superior.

```java
public class Address {
  @NotBlank(message = "Se requiere calle")
  @Size(max = 80, message = "La calle es demasiado larga")
  private String street;

  // getters y setters
}
```

```java {6-7}
public class Hero {
  @NotEmpty(message = "El nombre no puede estar vacío")
  @Length(min = 3, max = 20)
  private String name;

  // Un bean anidado con las restricciones para address.street
  private Address address;

  // getters y setters
}
```

El enlace `address.street` valida contra `@NotBlank` en `Address.street`. Cada enlace valida la propiedad al final de su ruta.

El [ejemplo de beans anidados](https://github.com/webforj/built-with-webforj) enlaza un `Employee` con beans anidados `Address` y `EmergencyContact` a través de un solo `BindingContext` utilizando este patrón.

### Mensajes de validación conscientes de la localidad <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation admite mensajes de restricción localizados a través de la interpolación de mensajes estándar. Cuando cambias la localidad de la aplicación, el `JakartaValidator` necesita conocer la nueva localidad para poder resolver los mensajes en el idioma correcto.

`JakartaValidator` implementa la interfaz `LocaleAware`, lo que significa que `BindingContext.setLocale()` propaga automáticamente la localidad a todos los validadores de Jakarta en el contexto. No necesitas actualizar cada validador manualmente.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Cuando la localidad cambia, los validadores de Jakarta automáticamente
// producen mensajes en la nueva localidad
context.setLocale(Locale.GERMAN);
```

En un componente que implementa `LocaleObserver`, llama a `context.setLocale()` dentro de `onLocaleChange()` para mantener los mensajes de validación sincronizados con el idioma de la UI:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Consulta [mensajes de validación dinámicos](/docs/data-binding/validation/validators#dynamic-validation-messages) para más información sobre validadores conscientes de la localidad.
