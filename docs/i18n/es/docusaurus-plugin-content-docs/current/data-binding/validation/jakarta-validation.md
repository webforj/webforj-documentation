---
sidebar_position: 6
title: Jakarta Validation
_i18n_hash: eec00fac283efce49d856b1d40a48252
---
[Java Bean Validation](https://beanvalidation.org/) es ampliamente reconocido como el estándar para integrar la lógica de validación en aplicaciones Java. Utiliza un enfoque uniforme para la validación al permitir que los desarrolladores anoten las propiedades del modelo de dominio con restricciones de validación declarativas. Estas restricciones se aplican en tiempo de ejecución, con opciones para reglas tanto integradas como definidas por el usuario.

webforJ se integra perfectamente con Bean Validation a través del adaptador `JakartaValidator`, proporcionando un soporte robusto de forma predeterminada.

## Instalación {#installation}

Es necesario incluir una implementación compatible, como [Hibernate Validator](https://hibernate.org/validator/), en su classpath. Si su entorno no incluye esta implementación de forma predeterminada, puede agregarla manualmente utilizando las siguientes dependencias de Maven:

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

La clase `JakartaValidator` funciona como un adaptador, conectando el contexto de enlace de webforJ con Jakarta Validation. Esta integración permite el uso de reglas de validación complejas directamente a través de anotaciones en la clase bean.

### Activando `JakartaValidator` {#activating-jakartavalidator}

Para activar el `JakartaValidator` en todo el contexto, generalmente se usa el parámetro `useJakartaValidator` al construir el `BindingContext`.

```java
BindingContext<User> context = new BindingContext<>(User.class, true);
```

### Definiendo restricciones para propiedades del bean {#defining-constraints-for-bean-properties}

Las restricciones basadas en anotaciones se aplican directamente dentro de la clase bean para especificar las condiciones de validación, como se ilustra en el siguiente ejemplo:

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

Tales restricciones son tan efectivas como aquellas establecidas programáticamente durante la inicialización del enlace, asegurando resultados de validación consistentes.

:::warning
Actualmente, el `JakartaValidator` solo reconoce restricciones que se asignan directamente a las propiedades e ignora cualquier validación que no esté directamente asociada con propiedades.
:::
