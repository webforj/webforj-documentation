---
sidebar_position: 40
title: 压缩插件
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: fc6e1dd15c6b453f014feb4cdf449953
---
# Minifier 插件 <DocChip chip='since' label='25.11' />

webforJ Minifier 插件会在构建过程中自动[压缩](https://en.wikipedia.org/wiki/Minification_(programming))并优化 CSS 和 JavaScript 资源。该插件会查找通过 webforJ [资源注解](/docs/managing-resources/importing-assets)引用的资源，并在构建输出中对其进行压缩，从而减小文件大小、缩短加载时间，同时不修改原始源文件。

:::info 压缩功能已内置于打包器中
[前端打包器](/docs/managing-resources/bundler/overview)会在生产构建过程中压缩 CSS 和 JavaScript，因此使用打包器的项目无需使用此插件。对于通过资源注解加载资源、但不使用打包器的项目，此插件仍然可用且受支持。现有配置会继续照常工作，无需更改。
:::

## 设置 {#setup}

设置 Minifier 时需要进行两项配置：一个用于在编译期间查找资源的注解处理器，以及一个用于执行压缩的插件。

<Tabs>
<TabItem value="maven" label="Maven">

将以下内容添加到 `pom.xml`：

```xml
<build>
  <plugins>
    <!-- Annotation Processor Configuration -->
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <configuration>
        <annotationProcessorPaths>
          <path>
            <groupId>com.webforj</groupId>
            <artifactId>webforj-minify-foundation</artifactId>
            <version>${webforj.version}</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>

    <!-- Minifier Plugin Configuration -->
    <plugin>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-maven-plugin</artifactId>
      <version>${webforj.version}</version>
      <executions>
        <execution>
          <goals>
            <goal>minify</goal>
          </goals>
        </execution>
      </executions>
      <dependencies>
        <!-- CSS minification -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript minification -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-closure-js</artifactId>
          <version>${webforj.version}</version>
        </dependency>
      </dependencies>
    </plugin>
  </plugins>
</build>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

将以下内容添加到 `build.gradle`：

```groovy
buildscript {
  repositories {
    mavenCentral()
  }
  dependencies {
    classpath "com.webforj:webforj-minify-gradle-plugin:${webforjVersion}"
  }
}

plugins {
  id 'java'
}

apply plugin: 'com.webforj.minify'

dependencies {
  // Annotation processor for discovering assets during compilation
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier implementations
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

`minify` 任务会在 `jar` 或 `war` 任务之前自动运行。你也可以使用 `./gradlew minify` 手动运行。

</TabItem>
</Tabs>

## 使用插件 {#using-the-plugin}

完成配置后，插件会自动运行。只需在代码中使用 webforJ 资源注解：

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // Your application code
}
```

构建项目时，插件会自动执行以下操作：

1. 在编译期间查找注解中引用的资源
2. 压缩查找到的 CSS 和 JavaScript 文件
3. 报告文件大小缩减情况和处理时间

### URL 协议解析 {#url-protocol-resolution}

插件能够识别 webforJ [URL 协议](/docs/managing-resources/assets-protocols)，并将其解析为文件系统路径：

| 协议 | 解析为 | 示例 |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

Minifier 不支持没有协议的 URL，因此会跳过这些 URL。

## 内置压缩器 {#built-in-minifiers}

webforJ 内置了两个适用于生产环境的 CSS 和 JavaScript 压缩器。

| 压缩器 | 功能 | 跳过 |
|----------|----------|-------|
| CSS | 删除空白字符和注释，并优化属性值 | `.min.css` |
| JavaScript | 变量重命名、死代码消除、语法优化 | `.min.js`、`.min.mjs` |

## 配置选项 {#configuration-options}

插件提供了用于禁用压缩、定制 JavaScript 优化以及处理其他文件的选项。

### 禁用压缩 {#disabling-minification}

在开发或调试时，你可能需要关闭压缩。

<Tabs>
<TabItem value="maven" label="Maven">

**通过命令行：**
```bash
mvn package -Dwebforj.minify.skip=true
```

**通过插件配置：**
```xml
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <configuration>
    <skip>true</skip>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

**通过构建配置：**
```groovy
webforjMinify {
  skip = true
}
```

</TabItem>
</Tabs>

### JavaScript 压缩器选项 {#javascript-minifier-options}

JavaScript 压缩器提供多个配置选项，用于控制优化行为。

:::note Gradle 支持
从 v25.12 开始，Gradle 插件支持传递 JavaScript 压缩器选项。
:::

| 选项 | 默认值 | 说明 |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - 仅删除空白字符和注释</li><li>`SIMPLE_OPTIMIZATIONS` - 重命名变量并删除死代码</li><li>`ADVANCED_OPTIMIZATIONS` - 进行激进优化，包括重命名函数和属性</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | 输入 JavaScript 版本：`ECMASCRIPT3`、`ECMASCRIPT5`、`ECMASCRIPT_2015` 至 `ECMASCRIPT_2021`、`ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | 输出 JavaScript 版本：与 `languageIn` 相同，另加 `NO_TRANSPILE` |
| `prettyPrint` | `false` | 设置为 `true` 可保留格式，以便调试 |

在配置部分设置这些选项：

<Tabs>
<TabItem value="maven" label="Maven">

```xml {7-12}
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <version>${webforj.version}</version>
  <configuration>
    <minifierConfigurations>
      <closureJs>
        <compilationLevel>SIMPLE_OPTIMIZATIONS</compilationLevel>
        <languageIn>ECMASCRIPT_2020</languageIn>
        <languageOut>ECMASCRIPT5</languageOut>
        <prettyPrint>false</prettyPrint>
      </closureJs>
    </minifierConfigurations>
  </configuration>
  <executions>
    <execution>
      <goals>
        <goal>minify</goal>
      </goals>
    </execution>
  </executions>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-closure-js</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy
webforjMinify {
    skip = false  // Set to true to skip minification
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### 压缩其他文件 {#minifying-additional-files}

若要压缩未通过注解查找到的文件，请创建一个指定 glob 模式的配置文件：

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# Include patterns
**/*.css
**/*.js

# Exclusion patterns (prefix with !)
!**/*.min.css
!**/*.min.js
```

## 自定义压缩器 {#custom-minifiers}

插件支持通过 Java 的 Service Provider Interface (SPI) 使用自定义压缩器，让你能够为其他文件类型或替代压缩库添加支持。

### 创建自定义压缩器 {#creating-a-custom-minifier}

实现 `AssetMinifier` 接口即可创建自己的压缩器。以下示例展示了一个使用 Gson 删除空白字符的 JSON 压缩器：

```java title="src/main/java/com/example/minifier/JsonMinifier.java"
package com.example.minifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.webforj.minify.common.AssetMinifier;
import com.webforj.minify.common.MinificationException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class JsonMinifier implements AssetMinifier {

  private static final Logger logger = Logger.getLogger(JsonMinifier.class.getName());
  private final Gson gson = new GsonBuilder().create();

  @Override
  public String minify(String content, Path sourceFile) throws MinificationException {
    try {
      JsonElement element = gson.fromJson(content, JsonElement.class);
      return gson.toJson(element);
    } catch (JsonSyntaxException e) {
      logger.warning("Malformed JSON in " + sourceFile + ", skipping: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("Failed to minify JSON file: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // Skip config files and already minified files
    if (filename.equals("package.json") || filename.equals("tsconfig.json")) {
      return false;
    }
    if (filename.endsWith("-lock.json") || filename.endsWith(".min.json")) {
      return false;
    }
    return true;
  }

  @Override
  public void configure(Map<String, Object> options) {
    // Configuration options if needed
  }
}
```

### 注册压缩器 {#registering-your-minifier}

创建服务提供者配置文件：

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### 使用自定义压缩器 {#using-your-custom-minifier}

将压缩器打包为单独的 JAR，并将其添加为插件依赖项：

```xml {5-9}
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-minify-maven-plugin</artifactId>
  <dependencies>
    <dependency>
      <groupId>com.example</groupId>
      <artifactId>json-minifier</artifactId>
      <version>1.0.0</version>
    </dependency>
    <!-- Standard minifiers (optional) -->
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-minify-phcss-css</artifactId>
      <version>${webforj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

## 常见问题 {#common-issues}

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>未注册任何压缩器</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      将压缩器模块依赖项添加到插件配置中。对于 CSS，请添加 `webforj-minify-phcss-css`。对于 JavaScript，请添加 `webforj-minify-closure-js`。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>未处理任何文件</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      如果插件报告 `Processed 0 files`，请确认：

      1. 已在 `maven-compiler-plugin` 中配置注解处理器，并在 `annotationProcessorPaths` 中添加了 `webforj-minify-foundation`
      2. 源代码中存在 webforJ 资源注解
      3. 编译后存在 `target/classes/META-INF/webforj-resources.json`
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>找不到文件</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      请确认文件位于 `src/main/resources/static` 下的正确路径，并且 URL 协议与目录结构相匹配。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>压缩错误</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      插件会发出警告，但会继续执行，不会导致构建失败。压缩失败时会保留原始内容。若要修复语法错误，请使用代码检查工具验证 CSS 或 JavaScript。
    </div>
  </AccordionDetails>
</Accordion>
<br />
