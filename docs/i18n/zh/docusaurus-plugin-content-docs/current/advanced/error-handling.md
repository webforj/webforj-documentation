---
title: Error Handling
sidebar_position: 5
description: >-
  Register custom ErrorHandler implementations through the Java Service Provider
  Interface to respond to specific exceptions in webforJ apps.
_i18n_hash: 55731ba6ae9454377d363fa461c817bc
---
错误处理是开发可靠网页应用程序的关键方面。在webforJ中，错误处理被设计得灵活且可定制，允许开发者以最适合其应用需求的方式处理异常。

## 概述 {#overview}

在webforJ中，错误处理围绕`ErrorHandler`接口展开。该接口允许开发者定义其应用在执行过程中遇到异常时的响应方式。默认情况下，webforJ提供一个`GlobalErrorHandler`，以通用方式处理所有异常。然而，开发者可以为特定异常创建自定义错误处理程序，以提供更有针对性的响应。

## 发现并使用错误处理程序 {#discovering-and-using-error-handlers}

webforJ使用Java的服务提供者接口（SPI）来发现和加载错误处理程序。

### 发现过程 {#discovery-process}

1. **服务注册**：错误处理程序通过`META-INF/services`机制进行注册。
2. **服务加载**：在应用启动时，webforJ加载`META-INF/services/com.webforj.error.ErrorHandler`中列出的所有类。
3. **错误处理**：当发生异常时，webforJ检查是否存在针对该特定异常的错误处理程序。

### 处理程序选择 {#handler-selection}

- 如果存在该异常的特定处理程序，则使用该处理程序。
- 如果找不到特定处理程序，但定义了自定义全局错误处理程序`WebforjGlobalErrorHandler`，则使用该处理程序。
- 如果都没有找到，则使用默认的`GlobalErrorHandler`。

## `ErrorHandler`接口 {#the-errorhandler-interface}

`ErrorHandler`接口旨在处理在webforJ应用执行期间发生的错误。希望管理特定异常的应用必须实现该接口。

### 方法 {#methods}

- **`onError(Throwable throwable, boolean debug)`**：当发生错误时调用。此方法应包含处理异常的逻辑。
- **`showErrorPage(String title, String content)`**：一个默认方法，使用给定的标题和内容显示错误页面。

### 命名约定 {#naming-convention}

实现类的名称必须与其处理的异常相对应，并以`ErrorHandler`作为后缀。例如，为处理`NullPointerException`，类应命名为`NullPointerExceptionErrorHandler`。

### 注册 {#registration}

自定义错误处理程序必须在`META-INF/services/com.webforj.error.ErrorHandler`文件中注册，以便webforJ能够发现并使用它。

## 实现自定义错误处理程序 {#implementing-a-custom-error-handler}

以下步骤详细说明了为特定异常实现自定义错误处理程序的过程：

### 步骤1：创建错误处理程序类 {#step-1-create-the-error-handler-class}

创建一个新类，实现`ErrorHandler`接口，并以其处理的异常命名。

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // 针对NullPointerException的自定义处理逻辑
    String title = "空指针异常";
    String content = "在需要对象的地方遇到了空值。";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` 方法
`showErrorPage`方法是一个实用方法，使用webforJ API将提供的HTML内容和页面标题发送到浏览器，显示错误页面。当发生异常且应用无法恢复时，无法使用webforJ组件构建自定义错误页面。然而，`Page` API仍然可用，允许开发者重定向或显示错误页面作为最后的尝试。
:::

### 步骤2：注册错误处理程序 {#step-2-register-the-error-handler}

在您的应用的`META-INF/services`目录中创建一个名为`com.webforj.error.ErrorHandler`的文件。将您的错误处理程序类的完全限定名称添加到此文件中。

**文件**：`META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

现在，每当抛出`NullPointerException`时，webforJ将选择您注册的处理程序并执行其逻辑来处理错误。

## 使用`AutoService`简化注册 {#using-autoservice-to-simplify-registration}

开发者很容易忘记更新或正确指定服务描述符。通过使用Google的`AutoService`，您可以自动生成`META-INF/services/com.webforj.error.ErrorHandler`文件。您只需使用`AutoService`注解标记错误处理程序即可。您可以在这里了解更多关于[AutoService的信息](https://github.com/google/auto/blob/main/service/README.md)。

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // 针对NullPointerException的自定义处理逻辑
    String title = "空指针异常";
    String content = "在需要对象的地方遇到了空值。";

    showErrorPage(title, content);
  }
}
```

## `GlobalErrorHandler`类 {#the-globalerrorhandler-class}

`GlobalErrorHandler`是webforJ提供的默认错误处理程序。它实现了`ErrorHandler`接口，并提供了通用的错误处理。

### 行为 {#behavior}

- **日志记录**：错误被记录到服务器和浏览器控制台。
- **错误页面显示**：根据调试模式，错误页面显示堆栈跟踪或通用错误消息。

### 定义自定义全局错误处理程序 {#defining-a-custom-global-error-handler}

要定义全局错误处理程序，您需要创建一个名为`WebforjGlobalErrorHandler`的新错误处理程序，然后按照之前解释的[注册错误处理程序的步骤](#step-2-register-the-error-handler)。在这种情况下，webforJ首先会查找任何自定义错误处理程序来管理异常。如果没有找到，webforJ将回退到自定义全局错误处理程序。

:::info
如果注册了多个`WebforjGlobalErrorHandler`，则webforJ会选择第一个。
:::
