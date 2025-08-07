---
sidebar_position: 2
title: Testing with Playwright
_i18n_hash: 43cec2eab876a8dc170f4fb69aaa8214
---
该文档概述了使用 Playwright 测试 webforJ 应用程序的过程，特别关注来自 `webforj-archetype-hello-world` 的 `HelloWorldView`。

:::info 应用基础
要了解更多关于 `webforj-archetype-hello-world` 的信息，请参考 [应用基础介绍](../../introduction/basics) 部分。
:::

## 前提条件 {#prerequisites}

在编写和运行 Playwright 测试之前，请确保以下几点：
- webforJ 应用程序在您的本地服务器上正确设置并运行。
- 您已安装：
  - Playwright Java 绑定。
  - 兼容的浏览器（Playwright 可以在设置期间自动安装浏览器）。
  - 用于项目依赖的 Maven。

## Maven 配置 {#maven-configuration}

在您的 `pom.xml` 中添加 Playwright 所需的依赖项：

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

## 测试示例：`HelloWorldView` {#testing-example-helloworldview}

以下代码演示了一个基于 Playwright 的 `HelloWorldView` 组件的测试。

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

### 关键步骤 {#key-steps}

1. **初始化 Playwright**：
   - 创建一个 `Playwright` 实例。
   - 使用 `playwright.chromium().launch()` 启动一个浏览器实例。

2. **设置测试环境**：
   - 使用 `browser.newPage()` 打开一个新的浏览器页面。
   - 使用 `navigate` 方法导航到 `HelloWorldView` 页面。

3. **与元素交互**：
   - 使用 [Playwright 的定位器](https://playwright.dev/java/docs/api/class-locator) 与 DOM 元素交互。
   - 使用 `locator("input").fill()` 填充输入字段，并使用 `getByText("Say Hello").click()` 触发操作。

4. **断言**：
   - 使用 `PlaywrightAssertions.assertThat()` 验证显示的 toast 消息。

5. **清理**：
   - Playwright 在测试完成时自动处理浏览器清理。如需手动清理，您可以使用 `browser.close()` 关闭浏览器。

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

- 在访问 `http://localhost:<port>/` 时，`HelloWorldView` 页面加载。
- 在文本字段中输入 webforJ 并点击 `Say Hello` 按钮。
- 应出现一条 toast 消息，文本为：`Welcome to webforJ Starter webforJ!`。
