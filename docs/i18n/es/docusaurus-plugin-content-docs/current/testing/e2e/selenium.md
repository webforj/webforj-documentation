---
sidebar_position: 3
title: Testing with Selenium
_i18n_hash: fe85942b4638ef9828b334ef986b4436
---
Esta documentación describe el proceso para probar aplicaciones webforJ utilizando Selenium, centrándose específicamente en el `HelloWorldView` del `webforj-archetype-hello-world`.

:::info Información Básica de la App
Para obtener más información sobre el `webforj-archetype-hello-world`, consulte la sección [Introducción a la Información Básica de la App](../../introduction/basics).
:::

## Requisitos Previos {#prerequisites}

Antes de ejecutar las pruebas de Selenium, asegúrese de lo siguiente:
- La aplicación webforJ está correctamente configurada y en funcionamiento en su servidor local.
- Ha instalado:
  - Bindings de Java para Selenium.
  - Un WebDriver compatible con su navegador.
  - Maven para las dependencias del proyecto.

## Configuración de Maven {#maven-configuration}

Agregue las dependencias necesarias en su `pom.xml` para Selenium y otras bibliotecas de prueba:

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
    assertEquals("Say Hello", button.getText(), "¡Texto del botón no coincide!");
  }
}
```

### Pasos clave {#key-steps}

1. **Inicializar WebDriver**:
   - Use [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) para gestionar automáticamente el ejecutable del controlador para el navegador.

2. **Configurar el Entorno de Prueba**:
   - Inicie el servidor de pruebas en `http://localhost:<port>/`.
   - Espere hasta que el título de la página coincida con el esperado `webforJ Hello World`.

3. **Interactuar con Elementos**:
   - Ubique elementos utilizando `By.tagName`, `By.id`, u otros localizadores de Selenium.
   - Verifique los comportamientos esperados, como clics en botones o cambios de texto.
    
  :::info
  Debido a que webforJ produce una aplicación web de una sola página, Selenium no es consciente de la manipulación del DOM después de que se ha cargado la página inicial. Puede utilizar la [API WebDriverWait de Selenium](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) para esperar hasta que se haya compilado el DOM.
  :::

4. **Teardown**:
   - Cierre la sesión de WebDriver para liberar recursos.

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
- El elemento `dwc-button` con el texto `Say Hello` debe estar presente.
