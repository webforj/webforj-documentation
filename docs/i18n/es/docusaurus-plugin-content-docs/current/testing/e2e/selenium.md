---
sidebar_position: 3
title: Testing with Selenium
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 865e29cc6d656fb60a0a3332a805c5f4
---
Esta documentación describe el proceso para probar aplicaciones webforJ utilizando Selenium, centrándose específicamente en el `HelloWorldView` de
el `webforj-archetype-hello-world`.

:::info Fundamentos de la Aplicación
Para obtener más información sobre el `webforj-archetype-hello-world`, consulta la sección [Introducción a los Fundamentos de la Aplicación](../../introduction/basics).
:::

## Requisitos previos {#prerequisites}

Antes de ejecutar las pruebas de Selenium, asegúrate de lo siguiente:
- La aplicación webforJ está correctamente configurada y funcionando en tu servidor local.
- Has instalado:
  - Selenium Java bindings.
  - Un WebDriver compatible para tu navegador.
  - Maven para las dependencias del proyecto.

## Configuración de Maven {#maven-configuration}

Agrega las dependencias necesarias en tu `pom.xml` para Selenium y otras bibliotecas de pruebas:

```xml title="pom.xml"
<dependencies>
  <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.27.0</version>
  </dependency>
  <dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.9.2</version>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Ejemplo de prueba: `HelloWorldView` {#testing-example-helloworldview}

El siguiente código demuestra una prueba basada en Selenium para el componente `HelloWorldView`.

```java title="HelloWorldViewTest.java"
package com.example.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import io.github.bonigarcia.wdm.WebDriverManager;

class HelloWorldViewTest {

  private WebDriver driver;
  private static final String PORT = System.getProperty("server.port", "8080");

  @BeforeAll
  static void setupAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  void setup() {
    driver = new ChromeDriver();
    driver.get("http://localhost:" + PORT + "/");
    new WebDriverWait(driver, ofSeconds(30))
            .until(titleIs("webforJ Hello World"));
  }

  @AfterEach
  void teardown() {
    if (driver != null) {
        driver.quit();
    }
  }

  @Test
  void shouldClickButton() {
    WebElement button = driver.findElement(By.tagName("dwc-button"));
    assertEquals("Say Hello", button.getText(), "¡Desajuste en el texto del botón!");
  }
}
```

### Pasos clave {#key-steps}

1. **Inicializar WebDriver**:
   - Usa [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) para gestionar automáticamente el ejecutable del controlador para el navegador.

2. **Configurar el Entorno de Pruebas**:
   - Inicia el servidor de pruebas en `http://localhost:<port>/`.
   - Espera hasta que el título de la página coincida con el esperado `webforJ Hello World`.

3. **Interactuar con Elementos**:
   - Ubica elementos utilizando `By.tagName`, `By.id` u otros localizadores de Selenium.
   - Verifica comportamientos esperados como clics en botones o cambios de texto.

  :::info
  Debido a que webforJ produce una aplicación web de una sola página, Selenium no es consciente de la manipulación del DOM después de que se ha cargado la página inicial. Puedes usar la [API WebDriverWait](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) de Selenium para esperar hasta que el DOM se haya compilado.
  :::

4. **Limpieza**:
   - Cierra la sesión de WebDriver para liberar recursos.

### Ejecutar pruebas {#running-tests}

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
- El elemento `dwc-button` con el texto `Say Hello` debería estar presente.
