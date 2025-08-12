---
title: Error Handling
sidebar_position: 25
_i18n_hash: 15106dd9fa7ccf0d4f722ca675f0d362
---
错误处理是开发稳健的 web 应用程序的关键方面。在 webforJ 中，错误处理被设计为灵活且可定制，允许开发人员以最适合其应用程序需求的方式处理异常。

## 概述 {#overview}

在 webforJ 中，错误处理围绕 `ErrorHandler` 接口展开。该接口允许开发人员定义应用程序在执行过程中遇到异常时应如何响应。默认情况下，webforJ 提供了一个以通用方式处理所有异常的 `GlobalErrorHandler`。但是，开发人员可以为特定异常创建自定义错误处理程序，以提供更针对性的响应。

## 发现和使用错误处理程序 {#discovering-and-using-error-handlers}

webforJ 使用 Java 的服务提供者接口（SPI）来发现和加载错误处理程序。

### 发现过程 {#discovery-process}

1. **服务注册**：通过 `META-INF/services` 机制注册错误处理程序。
2. **服务加载**：在应用程序启动时，webforJ 加载 `META-INF/services/com.webforj.error.ErrorHandler` 中列出的所有类。
3. **错误处理**：当发生异常时，webforJ 检查是否存在该特定异常的错误处理程序。

### 处理程序选择 {#handler-selection}

- 如果存在该异常的特定处理程序，则使用该处理程序。
- 如果没有找到特定处理程序，但定义了自定义全局错误处理程序 `WebforjGlobalErrorHandler`，则使用它。
- 如果都没有找到，则使用默认的 `GlobalErrorHandler`。

## `ErrorHandler` 接口 {#the-errorhandler-interface}

`ErrorHandler` 接口旨在处理在 webforJ 应用程序执行期间发生的错误。希望管理特定异常的应用程序应实现此接口。

### 方法 {#methods}

- **`onError(Throwable throwable, boolean debug)`**：当发生错误时调用。此方法应包含处理异常的逻辑。
- **`showErrorPage(String title, String content)`**：一个默认方法，用给定的标题和内容显示错误页面。

### 命名约定 {#naming-convention}

实现类必须以其处理的异常命名，并添加后缀 `ErrorHandler`。例如，要处理 `NullPointerException`，类应命名为 `NullPointerExceptionErrorHandler`。

### 注册 {#registration}

自定义错误处理程序必须在 `META-INF/services/com.webforj.error.ErrorHandler` 文件中注册，以便 webforJ 能够发现并利用它。

## 实现自定义错误处理程序 {#implementing-a-custom-error-handler}

以下步骤详细说明了特定异常的自定义错误处理程序的实现：

### 步骤 1：创建错误处理程序类 {#step-1-create-the-error-handler-class}

创建一个新类，实现 `ErrorHandler` 接口，并以其处理的异常命名。

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // NullPointerException 的自定义处理逻辑
    String title = "空指针异常";
    String content = "在需要对象的地方遇到了一个空值。";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` 方法
`showErrorPage` 方法是一个实用方法，利用 webforJ API 将提供的 HTML 内容和页面标题发送到浏览器，显示错误页面。当发生异常且应用程序无法恢复时，使用 webforJ 组件构建自定义错误页面就变得不可能。但 `Page` API 仍然可访问，允许开发人员重定向或显示错误页面作为最后的尝试。
:::

### 步骤 2：注册错误处理程序 {#step-2-register-the-error-handler}

在应用程序的 `META-INF/services` 目录中创建一个名为 `com.webforj.error.ErrorHandler` 的文件。将错误处理程序类的全限定名添加到此文件中。

**文件**：`META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

现在，每当抛出 `NullPointerException` 时，webforJ 会选择注册的处理程序并执行其逻辑以处理错误。

## 使用 `AutoService` 来简化注册 {#using-autoservice-to-simplify-registration}

开发人员很容易忘记更新或正确指定服务描述符。通过使用 Google 的 `AutoService`，可以自动生成 `META-INF/services/com.webforj.error.ErrorHandler` 文件。您只需用 `AutoService` 注解错误处理程序即可。了解更多关于 [AutoService 的信息](https://github.com/google/auto/blob/main/service/README.md)。

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // NullPointerException 的自定义处理逻辑
    String title = "空指针异常";
    String content = "在需要对象的地方遇到了一个空值。";

    showErrorPage(title, content);
  }
}
```

## `GlobalErrorHandler` 类 {#the-globalerrorhandler-class}

`GlobalErrorHandler` 是 webforJ 提供的默认错误处理程序。它实现了 `ErrorHandler` 接口并提供通用错误处理。

### 行为 {#behavior}

- **日志记录**：错误记录到服务器和浏览器控制台。
- **错误页面显示**：根据调试模式，错误页面显示堆栈跟踪或通用错误消息。

### 定义自定义全局错误处理程序 {#defining-a-custom-global-error-handler}

要定义全局错误处理程序，您需要创建一个新的错误处理程序，命名为 `WebforjGlobalErrorHandler`。然后按照 [注册错误处理程序的步骤](#step-2-register-the-error-handler) 进行操作。在这种情况下，webforJ 首先会查找任何自定义错误处理程序来管理异常。如果未找到，webforJ 退回到自定义全局错误处理程序。

:::info
如果注册了多个 `WebforjGlobalErrorHandler`，则 webforJ 选择第一个。
:::
