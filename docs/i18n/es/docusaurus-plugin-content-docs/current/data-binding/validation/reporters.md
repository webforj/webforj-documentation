---
sidebar_position: 3
title: Reporters
_i18n_hash: 0cb57295142e37eff340531d120b3566
---
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

Validation reporters are used for providing feedback about the validation process to the user interface. This feature is essential for informing users about the results of their input validation, particularly in complex forms or data-intensive applications.

## ¿Qué es un validador de informes? {#whats-a-validation-reporter}

Un validador de informes es un componente que procesa y muestra los resultados de las validaciones a los usuarios. Actúa como un puente entre la lógica de validación y la interfaz de usuario, asegurando que los resultados de la validación se comuniquen de manera efectiva y clara.

:::tip Componentes básicos Reportero predeterminado
webforJ incluye el `DefaultBindingReporter`, un reportero de uniones predeterminado diseñado para funcionar sin problemas con todos los componentes principales de webforJ. Este reportero integrado muestra automáticamente errores de validación, eliminando la necesidad de una implementación personalizada en muchos casos. Dependiendo de la configuración del componente, el `DefaultBindingReporter` muestra los errores de validación directamente como un popover o en línea, justo debajo del componente. Esta característica simplifica significativamente el proceso de informes de errores, asegurando una comunicación clara y directa de los errores de validación, y mejora la experiencia del usuario al proporcionar una retroalimentación inmediata y específica sobre la validación de entrada.
:::

## Configurando reporteros de validación {#configuring-validation-reporters}

Puedes configurar reporteros de validación dentro del contexto de unión para personalizar cómo se presentan los mensajes. Típicamente, implementarías un reportero de validación para agregar resultados de validación y luego mostrarlos de una manera amigable para el usuario, como resaltando campos incorrectos, mostrando mensajes de error o actualizando indicadores de estado.

Aquí tienes un ejemplo de cómo configurar un reportero de validación para un campo

<Tabs>
<TabItem value="UserRegistration" label="UserRegistration.java">

```java showLineNumbers
@StyleSheet("ws://css/styles.css")
public class UserRegistration extends App {
  Div errors = new Div();
  TextField emailField = new TextField("Dirección de correo electrónico");

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

En el código anterior, la unión de correo electrónico incorpora un reportero personalizado que muestra directamente los mensajes de validación debajo del campo de entrada. Esta configuración utiliza el método `useReporter`, que configura cómo la unión maneja y presenta los resultados de validación. Este método vincula efectivamente la lógica de validación a la interfaz de usuario, asegurando que cualquier problema de validación sea inmediatamente visible para el usuario, mejorando la interactividad del formulario y la experiencia del usuario.
