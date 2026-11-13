---
sidebar_position: 2
title: 使用 Playwright 进行测试
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: 3f815af0ad7916fdd5a53d6178846c6d
---
本文档介绍如何使用 Playwright 测试 webforJ 应用，重点介绍 `webforj-archetype-hello-world` 中的 `HelloWorldView`。

:::info 应用基础
如需进一步了解 `webforj-archetype-hello-world`，请参阅[应用基础介绍](../../introduction/basics)部分。
:::

## 前提条件 {#prerequisites}

编写并运行 Playwright 测试之前，请确保满足以下条件：
- webforJ 应用已正确设置，并在本地服务器上运行。
- 已安装以下项目：
  - Playwright Java 绑定。
  - 兼容的浏览器（Playwright 可在设置期间自动安装浏览器）。
  - 用于管理项目依赖项的 Maven。

## Maven 配置 {#maven-configuration}

在 `pom.xml` 中添加 Playwright 所需的依赖项：

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

以下代码演示了一个针对 `HelloWorldView` 组件的 Playwright 测试。

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
   - 使用 `playwright.chromium().launch()` 启动浏览器实例。

2. **设置测试环境**：
   - 使用 `browser.newPage()` 打开一个新的浏览器页面。
   - 使用 `navigate` 方法导航到 `HelloWorldView` 页面。

3. **与元素交互**：
   - 使用 [Playwright 定位器](https://playwright.dev/java/docs/api/class-locator)与 DOM 元素交互。
   - 使用 `locator("input").fill()` 填写输入字段，并使用 `getByText("Say Hello").click()` 触发操作。

4. **断言**：
   - 使用 `PlaywrightAssertions.assertThat()` 验证显示的 toast 消息。

5. **清理**：
   - 测试结束时，Playwright 会自动清理浏览器。如需手动清理，可以使用 `browser.close()` 关闭浏览器。

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

- 访问 `http://localhost:<port>/` 时，`HelloWorldView` 页面会加载。
- 在文本字段中输入 webforJ，然后点击 `Say Hello` 按钮。
- 页面应显示一条 toast 消息，文本为：`Welcome to webforJ Starter webforJ!`。
