---
sidebar_position: 2
title: Pruebas con Playwright
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: 3f815af0ad7916fdd5a53d6178846c6d
---
Esta documentación describe el proceso para probar aplicaciones webforJ con Playwright, centrándose específicamente en `HelloWorldView` de `webforj-archetype-hello-world`.

:::info Conceptos básicos de la aplicación
Para obtener más información sobre `webforj-archetype-hello-world`, consulta la sección [Introducción a los conceptos básicos de la aplicación](../../introduction/basics).
:::

## Requisitos previos {#prerequisites}

Antes de escribir y ejecutar las pruebas de Playwright, asegúrate de lo siguiente:
- La aplicación webforJ está configurada correctamente y se está ejecutando en tu servidor local.
- Has instalado:
  - Los enlaces de Java de Playwright.
  - Un navegador compatible (Playwright puede instalar los navegadores automáticamente durante la configuración).
  - Maven para las dependencias del proyecto.

## Configuración de Maven {#maven-configuration}

Añade las dependencias necesarias en tu `pom.xml` para Playwright:

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

El siguiente código muestra una prueba basada en Playwright para el componente `HelloWorldView`.

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

### Pasos principales {#key-steps}

1. **Inicializar Playwright**:
   - Crea una instancia de `Playwright`.
   - Inicia una instancia del navegador con `playwright.chromium().launch()`.

2. **Configurar el entorno de prueba**:
   - Abre una nueva página del navegador con `browser.newPage()`.
   - Navega a la página `HelloWorldView` usando el método `navigate`.

3. **Interactuar con los elementos**:
   - Usa [los localizadores de Playwright](https://playwright.dev/java/docs/api/class-locator) para interactuar con los elementos del DOM.
   - Rellena los campos de entrada con `locator("input").fill()` y activa acciones con `getByText("Say Hello").click()`.

4. **Aserciones**:
   - Verifica el mensaje de notificación mostrado con `PlaywrightAssertions.assertThat()`.

5. **Limpieza**:
   - Playwright gestiona automáticamente la limpieza del navegador cuando finaliza la prueba. Para limpiarlo manualmente, puedes cerrar el navegador con `browser.close()`.

### Ejecutar pruebas {#running-tests}

1. Inicia el servidor webforJ:
   ```bash
   mvn
   ```

2. Ejecuta los casos de prueba:
   ```bash
   mvn test
   ```

## Comportamiento esperado {#expected-behavior}

- Al visitar `http://localhost:<port>/`, se carga la página `HelloWorldView`.
- Introduce webforJ en el campo de texto y haz clic en el botón `Say Hello`.
- Debería aparecer un mensaje de notificación con el texto: `Welcome to webforJ Starter webforJ!`.
