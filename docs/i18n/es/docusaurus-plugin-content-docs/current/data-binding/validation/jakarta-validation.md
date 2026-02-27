---
sidebar_position: 6
title: Jakarta Validation
sidebar_class_name: updated-content
_i18n_hash: fa09682ac85db8e2c53ff9eea2d0633e
---
[Java Bean Validation](https://beanvalidation.org/) es ampliamente reconocido como el estándar para integrar la lógica de validación en aplicaciones Java. Utiliza un enfoque uniforme para la validación al permitir que los desarrolladores anoten las propiedades del modelo de dominio con restricciones de validación declarativas. Estas restricciones se aplican en tiempo de ejecución, con opciones tanto para reglas integradas como para reglas definidas por el usuario.

webforJ se integra con Bean Validation a través del adaptador `JakartaValidator`, proporcionando soporte completo desde el primer momento.

## Instalación {#installation}

Es necesario incluir una implementación compatible, como [Hibernate Validator](https://hibernate.org/validator/), en tu classpath. Si tu entorno no incluye esta implementación por defecto, puedes añadirla manualmente utilizando las siguientes dependencias de Maven:

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

La clase `JakartaValidator` sirve como un adaptador, conectando el contexto de enlace de webforJ con Jakarta Validation. Esta integración permite el uso de reglas de validación complejas directamente a través de anotaciones en la clase bean.

### Activando `JakartaValidator` {#activating-jakartavalidator}

Para activar el `JakartaValidator` en todo el contexto, normalmente utilizas el parámetro `useJakartaValidator` al construir el `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Definiendo restricciones para propiedades del bean {#defining-constraints-for-bean-properties}

Las restricciones basadas en anotaciones se aplican directamente dentro de la clase bean para especificar condiciones de validación, como se ilustra en el siguiente ejemplo:

```java
public class Hero {
  @NotEmpty(message = "El nombre no puede estar vacío")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "Poder no especificado")
  @Pattern(regexp = "Fly|Invisible|LaserVision|Speed|Teleportation", message = "Poder no válido")
  private String power;

  // getters y setters
}
```

Tales restricciones son tan efectivas como las establecidas programáticamente durante la inicialización del enlace y producen resultados de validación consistentes.

:::warning
Actualmente, el `JakartaValidator` solo reconoce restricciones que se asignan directamente a las propiedades e ignora cualquier validación no asociada directamente con propiedades.
:::

### Mensajes de validación sensibles a la configuración regional <DocChip chip='since' label='25.12' /> {#locale-aware-validation-messages}

Jakarta Validation soporta mensajes de restricción localizados a través de la interpolación de mensajes estándar. Cuando cambias la configuración regional de la aplicación, el `JakartaValidator` necesita conocer la nueva configuración regional para poder resolver los mensajes en el idioma correcto.

`JakartaValidator` implementa la interfaz `LocaleAware`, lo que significa que `BindingContext.setLocale()` propaga automáticamente la configuración regional a todos los validadores de Jakarta en el contexto. No necesitas actualizar manualmente cada validador.

```java {5}
BindingContext<Hero> context = new BindingContext<>(Hero.class, true);

// Cuando la configuración regional cambia, los validadores de Jakarta automáticamente
// producen mensajes en la nueva configuración regional
context.setLocale(Locale.GERMAN);
```

En un componente que implementa `LocaleObserver`, llama a `context.setLocale()` dentro de `onLocaleChange()` para mantener los mensajes de validación sincronizados con el idioma de la interfaz:

```java {3}
@Override
public void onLocaleChange(LocaleEvent event) {
  context.setLocale(event.getLocale());
}
```

Consulta [dynamic validation messages](/docs/data-binding/validation/validators#dynamic-validation-messages) para más información sobre validadores sensibles a la configuración regional.
