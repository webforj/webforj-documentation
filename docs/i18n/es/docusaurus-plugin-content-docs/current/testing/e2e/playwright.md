---
sidebar_position: 2
title: Testing with Playwright
_i18n_hash: 43cec2eab876a8dc170f4fb69aaa8214
---
Esta documentación describe el proceso para probar aplicaciones webforJ utilizando Playwright, con un enfoque específico en el `HelloWorldView` del `webforj-archetype-hello-world`.

:::info Conceptos Básicos de la App
Para aprender más sobre el `webforj-archetype-hello-world`, consulta la sección de [Conceptos Básicos de la App](../../introduction/basics).
:::

## Requisitos Previos {#prerequisites}

Antes de escribir y ejecutar las pruebas de Playwright, asegúrate de lo siguiente:
- La aplicación webforJ está correctamente configurada y en funcionamiento en tu servidor local.
- Has instalado:
  - Las dependencias de Playwright para Java.
  - Un navegador compatible (Playwright puede instalar navegadores automáticamente durante la configuración).
  - Maven para las dependencias del proyecto.

## Configuración de Maven {#maven-configuration}

Agrega las dependencias necesarias en tu `pom.xml` para Playwright:

```xml title="pom.xml"
<dependencies>
  <dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.49.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Ejemplo de Prueba: `HelloWorldView` {#testing-example-helloworldview}

El siguiente código demuestra una prueba basada en Playwright para el componente `HelloWorldView`.

```java title="HelloWorldViewTest.java"
package com.example.views;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

class HelloWorldViewTest {

  static Playwright playwright = Playwright.create();
  Browser browser;
  Page page;
  String port = System.getProperty("server.port", "8080");

  @BeforeEach
  void setUp() {
    browser = playwright.chromium().launch(); 
    page = browser.newPage();
    page.navigate("http://localhost:" + port + "/");
  }

  @Test
  void shouldClickButton() {
    page.locator("input").fill("webforJ");
    page.getByText("Say Hello").click();

    assertThat(page.locator("dwc-toast").first())
        .containsText("Welcome to webforJ Starter webforJ!");
  }
}
```

### Pasos Clave {#key-steps}

1. **Inicializa Playwright**:
   - Crea una instancia de `Playwright`.
   - Inicia una instancia de navegador utilizando `playwright.chromium().launch()`.

2. **Configura el Entorno de Prueba**:
   - Abre una nueva página del navegador con `browser.newPage()`.
   - Navega a la página `HelloWorldView` utilizando el método `navigate`.

3. **Interactúa con los Elementos**:
   - Utiliza los [localizadores de Playwright](https://playwright.dev/java/docs/api/class-locator) para interactuar con elementos del DOM.
   - Rellena los campos de entrada usando `locator("input").fill()` y activa acciones usando `getByText("Say Hello").click()`.

4. **Afirmaciones**:
   - Verifica el mensaje de tostada mostrado con `PlaywrightAssertions.assertThat()`.

5. **Tarea de Limpieza**:
   - Playwright maneja automáticamente la limpieza del navegador cuando la prueba finaliza. Para una limpieza manual, puedes cerrar el navegador utilizando `browser.close()`.

### Ejecutando pruebas {#running-tests}

1. Inicia el servidor webforJ:
   ```bash
   mvn jetty:run
   ```

2. Ejecuta los casos de prueba:
   ```bash
   mvn test
   ```

## Comportamiento Esperado {#expected-behavior}

- Al visitar `http://localhost:<port>/`, se carga la página `HelloWorldView`.
- Ingresa webforJ en el campo de texto y haz clic en el botón `Say Hello`.
- Debería aparecer un mensaje de tostada con el texto: `Welcome to webforJ Starter webforJ!`.
