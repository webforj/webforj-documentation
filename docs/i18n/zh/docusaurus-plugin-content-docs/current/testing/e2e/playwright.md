---
sidebar_position: 2
title: Testing with Playwright
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: d0b58780be88b22c15eef134bbd4755a
---
本 документация описывает процесс тестирования приложений webforJ с использованием Playwright, с акцентом на `HelloWorldView` из `webforj-archetype-hello-world`.

:::info Основы приложения
Чтобы узнать больше о `webforj-archetype-hello-world`, обратитесь к разделу [Основы приложения](../../introduction/basics).
:::

## Предварительные требования {#prerequisites}

Перед написанием и запуском тестов Playwright, убедитесь в следующем:
- Приложение webforJ правильно настроено и работает на вашем локальном сервере.
- Вы установили:
  - Связывание Playwright Java.
  - Совместимый браузер (Playwright может автоматически устанавливать браузеры во время настройки).
  - Maven для зависимостей проекта.

## Конфигурация Maven {#maven-configuration}

Добавьте необходимые зависимости в ваш `pom.xml` для Playwright:

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

## Пример тестирования: `HelloWorldView` {#testing-example-helloworldview}

Следующий код демонстрирует тест на основе Playwright для компонента `HelloWorldView`.

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
        .containsText("Добро пожаловать в webforJ Starter webforJ!");
  }
}
```

### Основные шаги {#key-steps}

1. **Инициализация Playwright**:
   - Создайте экземпляр `Playwright`.
   - Запустите экземпляр браузера с помощью `playwright.chromium().launch()`.

2. **Настройка тестовой среды**:
   - Откройте новую страницу браузера с помощью `browser.newPage()`.
   - Перейдите на страницу `HelloWorldView`, используя метод `navigate`.

3. **Взаимодействие с элементами**:
   - Используйте [локаторы Playwright](https://playwright.dev/java/docs/api/class-locator) для взаимодействия с элементами DOM.
   - Заполните поля ввода, используя `locator("input").fill()` и выполните действия, используя `getByText("Say Hello").click()`.

4. **Утверждения**:
   - Проверьте отображаемое сообщение с помощью `PlaywrightAssertions.assertThat()`.

5. **Завершение**:
   - Playwright автоматически обрабатывает очистку браузера, когда тест завершен. Для ручной очистки вы можете закрыть браузер, используя `browser.close()`.

### Запуск тестов {#running-tests}

1. Запустите сервер webforJ:
   ```bash
   mvn jetty:run
   ```

2. Выполните тестовые случаи:
   ```bash
   mvn test
   ```

## Ожидаемое поведение {#expected-behavior}

- При посещении `http://localhost:<port>/` загружается страница `HelloWorldView`.
- Введите webforJ в текстовое поле и нажмите кнопку `Say Hello`.
- Сообщение должно появиться с текстом: `Добро пожаловать в webforJ Starter webforJ!`.
