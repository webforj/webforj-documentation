---
sidebar_position: 1
title: Primeros pasos
description: >-
  Add the TestBed dependency, write a first test that adds a view and acts on
  it, and run the test.
_i18n_hash: 8a5fde986acda527dbefeadc4e0dc329
---
# Primeros pasos <DocChip chip='since' label='26.03' />

TestBed es la solución de pruebas de webforJ que no requiere navegador: ejecuta vistas y componentes dentro de pruebas JUnit normales, sin navegador ni servidor. En esta página se añade la dependencia, se escribe una primera prueba y se ejecuta.

La anotación `@WebforjTest` configura un contexto `WebforjTestbed` para cada prueba de una clase. Dentro de una prueba, `ComponentLocator` busca componentes en la página y `UserInteraction` realiza las acciones que haría un usuario.

<!-- INTRO_END -->

## Añadir la dependencia {#adding-the-dependency}

TestBed se proporciona mediante un módulo independiente. Añádelo a la aplicación con el ámbito de prueba:

```xml title="pom.xml"
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-testbed-junit</artifactId>
  <scope>test</scope>
</dependency>
```

La versión procede del Bill of Materials (BOM) de webforJ, por lo que la dependencia no declara ninguna. Los proyectos creados a partir de un [arquetipo](/docs/introduction/getting-started) ya importan el BOM. El módulo también incluye la API de JUnit Jupiter.

:::info Aplicaciones Spring Boot
Las aplicaciones Spring Boot usan el módulo `webforj-spring-testbed` en su lugar. Consulta [Pruebas con Spring](/docs/integrations/spring/testing).
:::

## Escribir una primera prueba {#writing-a-first-test}

El siguiente formulario saluda al usuario por el nombre que escribe:

```java title="GreetingForm.java" showLineNumbers
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Paragraph;

public class GreetingForm extends Composite<Div> {

  public GreetingForm() {
    TextField name = new TextField("Name");
    Paragraph greeting = new Paragraph();
    Button greet = new Button(
        "Greet", e -> greeting.setText("Hello, " + name.getValue() + "!"));

    getBoundComponent().add(name, greet, greeting);
  }
}
```

La prueba escribe un nombre, hace clic en **Greet** y comprueba el saludo:

```java title="GreetingFormTest.java" showLineNumbers
import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.samples.views.testbed.GreetingForm;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class GreetingFormTest {
  @Test
  void shouldGreetTheNameTheUserTyped(WebforjTestbed context) {
    context.add(new GreetingForm());

    var name = ComponentLocator.of(TextField.class).byLabel("Name").locate();
    var greet = ComponentLocator.of(Button.class).byText("Greet").locate();

    UserInteraction.type(name, "Ada");
    UserInteraction.click(greet);

    verifyThat(ComponentLocator.of(Paragraph.class).locate())
        .hasText("Hello, Ada!");
  }
}
```

La prueba sigue los pasos que realizaría un usuario:

1. `@WebforjTest` proporciona a cada método de prueba un `WebforjTestbed`, el contexto en el que se ejecuta la prueba.
2. `context.add()` coloca el formulario en la página y ejecuta todo su ciclo de vida, igual que cuando se añade a un frame en una aplicación en ejecución.
3. `ComponentLocator` busca el campo por su etiqueta y el botón por su texto. Consulta [Buscar componentes](./finding-components).
4. `UserInteraction` escribe y hace clic como lo haría un usuario, por lo que se ejecuta el listener del botón. Consulta [Interacción del usuario](./user-interaction).
5. `verifyThat` comprueba el texto que leería el usuario. Consulta [Aserciones](./assertions).

## Qué proporciona `@WebforjTest` {#what-webforjtest-provides}

Cada prueba de una clase anotada se ejecuta en su propio `WebforjTestbed`. El contexto se configura antes de cada prueba y se desmonta después, de modo que cada prueba empieza con una página vacía y no conserva ningún estado de la prueba anterior.

Una prueba puede acceder a su contexto de dos formas:

- Declarar un parámetro `WebforjTestbed` en el método de prueba, como en la primera prueba.
- Llamar a `WebforjTestbed.getCurrent()`, por ejemplo, desde un método compartido por las pruebas de una clase.

`context.add()` acepta uno o varios componentes y los añade al frame raíz del contexto, que se obtiene con `context.getFrame()`.

Las pruebas que navegan entre rutas en lugar de añadir componentes configuran los atributos `app`, `routeClasses` o `routePackages` de `@WebforjTest`. Consulta [Probar aplicaciones](./testing-apps).

## Ejecutar las pruebas {#running-the-tests}

Las pruebas de TestBed son pruebas JUnit normales. Ejecútalas con la herramienta de compilación o desde el IDE, como cualquier otra prueba JUnit:

```bash title="Terminal"
mvn test
```
