---
sidebar_position: 3
title: Pruebas con Selenium
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 482f683ba0db2228dfbfb9fdbea794e7
---
Esta documentación describe el proceso para probar aplicaciones webforJ con Selenium, centrándose específicamente en `HelloWorldView` de
`webforj-archetype-hello-world`.

:::info Conceptos básicos de la aplicación
Para obtener más información sobre `webforj-archetype-hello-world`, consulta la sección [Introducción a los conceptos básicos de la aplicación](../../introduction/basics).
:::

## Requisitos previos {#prerequisites}

Antes de ejecutar las pruebas de Selenium, asegúrate de lo siguiente:
- La aplicación webforJ está correctamente configurada y se está ejecutando en tu servidor local.
- Has instalado:
  - Los enlaces de Java de Selenium.
  - Un WebDriver compatible con tu navegador.
  - Maven para las dependencias del proyecto.

## Configuración de Maven {#maven-configuration}

Añade las dependencias necesarias para Selenium y otras bibliotecas de pruebas en tu `pom.xml`:

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

El siguiente código muestra una prueba basada en Selenium para el componente `HelloWorldView`.

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
    assertEquals("Say Hello", button.getText(), "Button text mismatch!");
  }
}
```

### Pasos clave {#key-steps}

1. **Inicializar WebDriver**:
   - Usa [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) para administrar automáticamente el ejecutable del controlador del navegador.

2. **Configurar el entorno de prueba**:
   - Inicia el servidor de prueba en `http://localhost:<port>/`.
   - Espera hasta que el título de la página coincida con el valor esperado `webforJ Hello World`.

3. **Interactuar con los elementos**:
   - Busca elementos usando `By.tagName`, `By.id` u otros localizadores de Selenium.
   - Comprueba los comportamientos esperados, como los clics en botones o los cambios de texto.

  :::info
  Como webforJ genera una aplicación web de una sola página, Selenium no detecta las manipulaciones del DOM después de que se haya cargado la página inicialmente. Puedes usar la [API WebDriverWait](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) de Selenium para esperar a que se compile el DOM.
  :::

4. **Limpieza**:
   - Cierra la sesión de WebDriver para liberar recursos.

### Ejecución de las pruebas {#running-tests}

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
- Debe estar presente el elemento `dwc-button` con el texto `Say Hello`.
