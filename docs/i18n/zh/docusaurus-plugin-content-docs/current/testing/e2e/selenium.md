---
sidebar_position: 3
title: 使用 Selenium 进行测试
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 482f683ba0db2228dfbfb9fdbea794e7
---
本文档概述了使用 Selenium 测试 webforJ 应用的流程，重点介绍如何测试 `webforj-archetype-hello-world` 中的 `HelloWorldView`。

:::info 应用基础知识
如需进一步了解 `webforj-archetype-hello-world`，请参阅[应用基础知识简介](../../introduction/basics)部分。
:::

## 先决条件 {#prerequisites}

运行 Selenium 测试之前，请确保满足以下条件：
- webforJ 应用已正确设置，并在本地服务器上运行。
- 已安装：
  - Selenium Java 绑定。
  - 与浏览器兼容的 WebDriver。
  - 用于管理项目依赖项的 Maven。

## Maven 配置 {#maven-configuration}

在 `pom.xml` 中添加 Selenium 和其他测试库所需的依赖项：

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

## 测试示例：`HelloWorldView` {#testing-example-helloworldview}

以下代码展示了一个基于 Selenium 的 `HelloWorldView` 组件测试。

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

### 关键步骤 {#key-steps}

1. **初始化 WebDriver**：
   - 使用 [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) 自动管理浏览器的驱动程序可执行文件。

2. **设置测试环境**：
   - 在 `http://localhost:<port>/` 启动测试服务器。
   - 等待页面标题与预期的 `webforJ Hello World` 相符。

3. **与元素交互**：
   - 使用 `By.tagName`、`By.id` 或其他 Selenium 定位器查找元素。
   - 验证预期行为，例如按钮点击或文本更改。

  :::info
  由于 webforJ 会生成单页 Web 应用，页面初次加载后发生的 DOM 操作不会被 Selenium 自动识别。你可以使用 Selenium 的 [WebDriverWait API](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html)，等待 DOM 编译完成。
  :::

4. **清理**：
   - 退出 WebDriver 会话以释放资源。

### 运行测试 {#running-tests}

1. 启动 webforJ 服务器：
   ```bash
   mvn
   ```

2. 执行测试用例：
   ```bash
   mvn test
   ```

## 预期行为 {#expected-behavior}

- 访问 `http://localhost:<port>/` 时，会加载 `HelloWorldView` 页面。
- 页面上应存在文本为 `Say Hello` 的 `dwc-button` 元素。
