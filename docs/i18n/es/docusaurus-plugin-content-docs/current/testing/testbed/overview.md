---
sidebar_position: 0
title: TestBed
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  The TestBed is webforJ's browserless testing, which runs views and components
  in plain JUnit tests without a browser or a server.
sidebar_class_name: new-content
_i18n_hash: a97354d7621ab9d89fc8b88c7592847f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# TestBed <DocChip chip='since' label='26.03' />
<!-- vale Google.Headings = YES -->

Probar una vista de webforJ suele implicar iniciar la aplicación, abrir un navegador y controlarlo con Selenium o Playwright. Así se detecta lo que solo se ve en un navegador, pero cada prueba tiene el coste del navegador, el servidor y las esperas entre ambos.

TestBed permite probar webforJ sin navegador. Una vista se ejecuta dentro de una prueba JUnit normal. No se abre ningún navegador ni se inicia ningún servidor. La prueba añade la vista, interactúa con ella como lo haría un usuario y comprueba el resultado desde Java. Los componentes se conectan, disparan sus eventos y navegan exactamente igual que en una aplicación en ejecución. Además, cada interacción termina antes de que se ejecute la siguiente línea de la prueba, así que no hay esperas ni pruebas inestables.

## Cómo funciona una prueba {#how-a-test-works}

Una prueba con TestBed sigue los pasos de un usuario. Busca un componente en la página, interactúa con él y comprueba qué muestra la página después. El siguiente formulario suscribe una dirección de correo electrónico y mantiene desactivado el botón **Subscribe** hasta que el campo contiene una dirección:

```java title="NewsletterForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class NewsletterForm extends Composite<Div> {

  public NewsletterForm() {
    TextField email = new TextField("Email");
    Paragraph status = new Paragraph();
    Button subscribe =
        new Button(
            "Subscribe", e -> status.setText("Subscribed " + email.getValue()));
    subscribe.setEnabled(false);
    email.onModify(e -> subscribe.setEnabled(!email.getValue().isBlank()));

    getBoundComponent().add(email, subscribe, status);
  }
}
```

La prueba busca el campo y el botón, escribe una dirección, hace clic y comprueba la confirmación:

```java title="NewsletterFormTest.java" showLineNumbers
@Test
void shouldSubscribeTheEmailTheUserTyped(WebforjTestbed context) {
  context.add(new NewsletterForm());

  var email = ComponentLocator.of(TextField.class).byLabel("Email").locate();
  var subscribe = ComponentLocator.of(Button.class)
      .byText("Subscribe")
      .locate();

  UserInteraction.type(email, "ada@example.com");
  UserInteraction.click(subscribe);

  assertTrue(
      ComponentLocator.of(Paragraph.class)
          .byText("Subscribed ada@example.com")
          .isPresent());
}
```

Cada paso tiene su propia parte de TestBed:

- **Búsqueda.** `ComponentLocator` describe un componente como lo identificaría un usuario, por su tipo y por lo que muestra; por ejemplo, un campo por su etiqueta. Consulta [Buscar componentes](./finding-components).
- **Interacción.** `UserInteraction` realiza las acciones de un usuario, y solo las que este podría realizar. Si se hace clic en un botón desactivado, la prueba falla en lugar de ejecutar su listener. Consulta [Interacción del usuario](./user-interaction).
- **Comprobación.** `verifyThat` comprueba lo que el usuario vería en un componente, y una instantánea compara la vista completa con una descripción almacenada de ella. Consulta [Aserciones](./assertions) e [Instantáneas](./snapshots).

La prueba nunca accede directamente a los campos de la vista. Se basa en lo que muestra la página, por lo que sigue pasando aunque la vista se construya de otra manera, y falla cuando el usuario vería algo distinto.

Además de probar vistas individuales, TestBed navega por rutas, prueba vistas protegidas, responde a diálogos y ejecuta temporizadores, como se muestra en [Probar aplicaciones](./testing-apps). También se ejecuta dentro de una aplicación Spring Boot con sus beans y su seguridad.

Las pruebas en navegador siguen siendo útiles para comprobar lo que solo se puede ver en un navegador, como los estilos, la maquetación y el comportamiento del cliente. TestBed cubre lo demás: cómo se comportan las vistas cuando un usuario interactúa con ellas.

Empieza con [Primeros pasos](./getting-started) para añadir la dependencia y escribir una primera prueba.

## Temas {#topics}

<DocCardList className="topics-section" />
