---
sidebar_position: 2
title: Testing with Playwright
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: d0b58780be88b22c15eef134bbd4755a
---
Esta documentación describe el proceso para probar aplicaciones webforJ utilizando Playwright, centrándose específicamente en el `HelloWorldView` del `webforj-archetype-hello-world`.

:::info Básicos de la Aplicación
Para obtener más información sobre el `webforj-archetype-hello-world`, consulte la sección [Introducción a los Básicos de la Aplicación](../../introduction/basics).
:::

## Requisitos Previos {#prerequisites}

Antes de escribir y ejecutar las pruebas de Playwright, asegúrese de lo siguiente:
- La aplicación webforJ está correctamente configurada y en funcionamiento en su servidor local.
- Ha instalado:
  - Las vinculaciones de Java de Playwright.
  - Un navegador compatible (Playwright puede instalar automáticamente los navegadores durante la configuración).
  - Maven para las dependencias del proyecto.

## Configuración de Maven {#maven-configuration}

Agregue las dependencias necesarias en su `pom.xml` para Playwright:

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
        .containsText("¡Bienvenido al iniciador webforJ webforJ!");
  }
}
```

### Pasos clave {#key-steps}

1. **Inicializar Playwright**:
   - Crear una instancia de `Playwright`.
   - Lanzar una instancia del navegador utilizando `playwright.chromium().launch()`.

2. **Configurar el Entorno de Prueba**:
   - Abrir una nueva página del navegador con `browser.newPage()`.
   - Navegar a la página `HelloWorldView` utilizando el método `navigate`.

3. **Interactuar con Elementos**:
   - Use [localizadores de Playwright](https://playwright.dev/java/docs/api/class-locator) para interactuar con elementos del DOM.
   - Llenar campos de entrada utilizando `locator("input").fill()` y desencadenar acciones utilizando `getByText("Say Hello").click()`.

4. **Afirmaciones**:
   - Verificar el mensaje de tostada mostrado con `PlaywrightAssertions.assertThat()`.

5. **Teardown**:
   - Playwright maneja automáticamente la limpieza del navegador cuando la prueba finaliza. Para la limpieza manual, puede cerrar el navegador utilizando `browser.close()`.

### Ejecución de pruebas {#running-tests}

1. Inicie el servidor webforJ:
   ```bash
   mvn jetty:run
   ```

2. Ejecute los casos de prueba:
   ```bash
   mvn test
   ```

## Comportamiento esperado {#expected-behavior}

- Al visitar `http://localhost:<port>/`, se carga la página `HelloWorldView`.
- Ingrese webforJ en el campo de texto y haga clic en el botón `Say Hello`.
- Debe aparecer un mensaje de tostada con el texto: `¡Bienvenido al iniciador webforJ webforJ!`.
