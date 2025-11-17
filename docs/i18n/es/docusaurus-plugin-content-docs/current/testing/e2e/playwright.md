---
sidebar_position: 2
title: Pruebas con Playwright
_i18n_hash: dffe640edd9d7918a3c8bace8cf0bbe8
---
Esta documentación describe el proceso para probar aplicaciones webforJ utilizando Playwright, enfocándose específicamente en el `HelloWorldView` del `webforj-archetype-hello-world`.

:::info Fundamentos de la App
Para obtener más información sobre el `webforj-archetype-hello-world`, consulta la sección de [Introducción a los Fundamentos de la App](../../introduction/basics).
:::

## Requisitos previos {#prerequisites}

Antes de escribir y ejecutar las pruebas de Playwright, asegúrate de lo siguiente:
- La aplicación webforJ está correctamente configurada y funcionando en tu servidor local.
- Has instalado:
  - Las bindings de Java de Playwright.
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

## Ejemplo de prueba: `HelloWorldView` {#testing-example-helloworldview}

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
        .containsText("¡Bienvenido a webforJ Starter webforJ!");
  }
}
```

### Pasos clave {#key-steps}

1. **Inicializar Playwright**:
   - Crea una instancia de `Playwright`.
   - Lanza una instancia del navegador utilizando `playwright.chromium().launch()`.

2. **Configurar el entorno de prueba**:
   - Abre una nueva página del navegador con `browser.newPage()`.
   - Navega a la página `HelloWorldView` utilizando el método `navigate`.

3. **Interactuar con elementos**:
   - Usa [los localizadores de Playwright](https://playwright.dev/java/docs/api/class-locator) para interactuar con elementos del DOM.
   - Llena los campos de entrada utilizando `locator("input").fill()` y activa acciones usando `getByText("Say Hello").click()`.

4. **Afirmaciones**:
   - Verifica el mensaje de toast mostrado con `PlaywrightAssertions.assertThat()`.

5. **Teardown**:
   - Playwright maneja automáticamente la limpieza del navegador cuando la prueba concluye. Para limpieza manual, puedes cerrar el navegador utilizando `browser.close()`.

### Ejecutando pruebas {#running-tests}

1. Inicia el servidor webforJ:
   ```bash
   mvn jetty:run
   ```

2. Ejecuta los casos de prueba:
   ```bash
   mvn test
   ```

## Comportamiento esperado {#expected-behavior}

- Al visitar `http://localhost:<port>/`, se carga la página `HelloWorldView`.
- Ingresa webforJ en el campo de texto y haz clic en el botón `Say Hello`.
- Debe aparecer un mensaje de toast con el texto: `¡Bienvenido a webforJ Starter webforJ!`.
