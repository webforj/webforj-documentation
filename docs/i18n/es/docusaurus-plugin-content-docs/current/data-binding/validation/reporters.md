---
sidebar_position: 3
title: Reporteros
_i18n_hash: c563479cec7e1fe29d483bcd121bb5fc
---
Los reporteros de validación se utilizan para proporcionar retroalimentación sobre el proceso de validación a la interfaz de usuario. Esta función es esencial para informar a los usuarios sobre los resultados de la validación de su entrada, particularmente en formularios complejos o aplicaciones intensivas en datos.

## ¿Qué es un reportero de validación? {#whats-a-validation-reporter}

Un reportero de validación es un componente que procesa y muestra los resultados de las validaciones a los usuarios. Actúa como un puente entre la lógica de validación y la interfaz de usuario, asegurando que los resultados de la validación se comuniquen de manera efectiva y clara.

:::tip Reportero predeterminado de componentes núcleo
webforJ incluye el `DefaultBindingReporter`, un reportero de enlaces predeterminado diseñado para funcionar sin problemas con todos los componentes núcleo de webforJ. Este reportero integrado muestra automáticamente los errores de validación, eliminando la necesidad de una implementación personalizada en muchos casos. Dependiendo de la configuración del componente, el `DefaultBindingReporter` muestra los errores de validación directamente como un popover o en línea, justo debajo del componente. Esta característica simplifica significativamente el proceso de reporte de errores, asegurando una comunicación clara y directa de los errores de validación, y mejora la experiencia del usuario al proporcionar retroalimentación inmediata y contextual sobre la validación de la entrada.
:::

## Configuración de los reporteros de validación {#configuring-validation-reporters}

Puedes configurar los reporteros de validación dentro del contexto de enlace para personalizar cómo se presentan los mensajes. Típicamente, implementarías un reportero de validación para agregar los resultados de la validación y luego mostrarlos de una manera amigable para el usuario, como resaltar campos incorrectos, mostrar mensajes de error o actualizar indicadores de estado.

Aquí hay un ejemplo de cómo configurar un reportero de validación para un campo

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@InlineStyleSheet("context://styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("Email Address");

  FlexLayout layout = FlexLayout.create(emailField, errors).vertical().build();

  @Override
  public void run() throws WebforjException {
    errors.addClassName("error");
    errors.setVisible(false);

    layout.addClassName("form");

    BindingContext<User> context = new BindingContext<>(User.class);
    context.bind(emailField, "email")
        .useValidator(
            Validator.from(new EmailValidator(), "Mensaje personalizado para dirección de correo electrónico inválida"))
        .useReporter((validationResult, binding) -> {
          errors.setVisible(!validationResult.isValid());

          if (!validationResult.isValid()) {
            errors.setText(validationResult.getMessages().stream().findFirst().orElse(""));
          }
        })
        .add();

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="User" label="User.java">

```java showLineNumbers
public class User {
  private String name;
  private String email;
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
}
```

</TabItem>
<TabItem value="styles" label="styles.css">

```css showLineNumbers
.error {
  border: 1px solid #f1aeb5;
  border-radius: 5px;
  background-color: #f8d7da;
  color: #58151c;
  padding: 5px;
}

.form {
  margin: 20px auto;
  max-width: 400px;
}
```

</TabItem>
</Tabs>

En el código anterior, el enlace de correo electrónico incorpora un reportero personalizado que muestra directamente los mensajes de validación debajo del campo de entrada. Esta configuración utiliza el método `useReporter`, que configura cómo el enlace maneja y presenta los resultados de la validación. Este método vincula efectivamente la lógica de validación a la interfaz de usuario, asegurando que cualquier problema de validación sea inmediatamente visible para el usuario, mejorando la interactividad del formulario y la experiencia del usuario.
