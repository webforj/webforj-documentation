---
sidebar_position: 3
title: Testing with Selenium
_i18n_hash: 5d2e4b04f794236d9a8ea2a32d50579b
---
该文档概述了使用 Selenium 测试 webforJ 应用程序的过程，特别关注来自 `webforj-archetype-hello-world` 的 `HelloWorldView`。

:::info 应用基础
要了解更多有关 `webforj-archetype-hello-world` 的信息，请参阅 [应用基础介绍](../../introduction/basics) 部分。
:::

## 先决条件 {#prerequisites}

在运行 Selenium 测试之前，请确保以下内容：
- webforJ 应用程序已正确设置并在您的本地服务器上运行。
- 您已安装：
  - Selenium Java 绑定。
  - 与您浏览器兼容的 WebDriver。
  - 用于项目依赖关系的 Maven。

## Maven 配置 {#maven-configuration}

在您的 `pom.xml` 中添加 Selenium 和其他测试库所需的依赖项：

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

以下代码演示了一个基于 Selenium 的 `HelloWorldView` 组件测试。

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

### 主要步骤 {#key-steps}

1. **初始化 WebDriver**：
   - 使用 [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) 自动管理浏览器的驱动程序可执行文件。

2. **设置测试环境**：
   - 在 `http://localhost:<port>/` 启动测试服务器。
   - 等待页面标题与预期的 `webforJ Hello World` 匹配。

3. **与元素交互**：
   - 使用 `By.tagName`、`By.id` 或其他 Selenium 定位器定位元素。
   - 验证预期行为，如按钮点击或文本更改。

  :::info
  因为 webforJ 生成一个单页面 Web 应用程序，所以 Selenium 在初始页面加载后并不了解 DOM 操作。您可以使用 Selenium 的 [WebDriverWait API](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) 来等待 DOM 编译完成。
  :::

4. **拆解**：
   - 退出 WebDriver 会话以释放资源。

### 运行测试 {#running-tests}

1. 启动 webforJ 服务器：
   ```bash
   mvn jetty:run
   ```

2. 执行测试用例：
   ```bash
   mvn test
   ```

## 预期行为 {#expected-behavior}

- 访问 `http://localhost:<port>/` 时，`HelloWorldView` 页面加载。
- 文本为 `Say Hello` 的 `dwc-button` 元素应存在。
