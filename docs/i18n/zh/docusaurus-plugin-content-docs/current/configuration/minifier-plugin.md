---
sidebar_position: 40
title: Minifier Plugin
description: >-
  Discover and minify CSS and JavaScript assets referenced by webforJ
  annotations during the Maven or Gradle production build.
_i18n_hash: a570df6cd0876073e88e7b38b6357b10
---
# Minifier 插件 <DocChip chip='since' label='25.11' />

webforJ Minifier 插件自动 [压缩](https://en.wikipedia.org/wiki/Minification_(programming)) 和优化 CSS 和 JavaScript 资源，在构建过程中。该插件通过 webforJ [资源注解](/docs/managing-resources/importing-assets) 发现资产，并在构建输出中压缩它们，从而减少文件大小并提高加载速度，而不会修改原始源文件。

:::info 压缩已内置于打包工具中
[前端打包工具](/docs/managing-resources/bundler/overview) 在其生产构建中压缩 CSS 和 JavaScript，因此使用打包工具的项目不需要该插件。该插件仍然对通过资源注解加载资产而不使用打包工具的项目可用并得到支持。现有设置可按之前方式正常工作，无需更改。
:::

## 设置 {#setup}

如果您使用 [startforJ](https://docs.webforj.com/startforj) 或 webforJ [原型](/docs/building-ui/archetypes/overview) 创建了项目，minifier 插件已经配置好并在您使用 `mvn package -Pprod` 构建时自动运行。

对于手动设置，minifier 需要两个配置：一个注解处理器用于在编译期间发现资源，以及一个插件用于执行压缩。

<Tabs>
<TabItem value="maven" label="Maven">

将以下内容添加到您的 `pom.xml`：

```xml
<build>
  <plugins>
    <!-- 注解处理器配置 -->
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

    <!-- Minifier 插件配置 -->
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
        <!-- CSS 压缩 -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript 压缩 -->
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

将以下内容添加到您的 `build.gradle`：

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
  // 注解处理器，用于在编译期间发现资产
  annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

  // Minifier 实现
  add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
  add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

`minify` 任务会在 `jar` 或 `war` 任务之前自动运行。您也可以通过 `./gradlew minify` 手动运行它。

</TabItem>
</Tabs>

## 使用插件 {#using-the-plugin}

配置好后，插件会自动工作。只需在代码中使用 webforJ 资产注解：

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // 你的应用程序代码
}
```

构建项目时，插件将自动：

1. 在编译期间发现注解中引用的资产
2. 压缩发现的 CSS 和 JavaScript 文件
3. 报告大小减少和处理时间

### URL 协议解析 {#url-protocol-resolution}

该插件理解 webforJ [URL 协议](/docs/managing-resources/assets-protocols) 并将其解析为文件系统路径：

| 协议 | 解析到 | 示例 |
|----------|-------------|---------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

不支持没有协议的 URL，且将被跳过。

## 内置压缩器 {#built-in-minifiers}

webforJ 包含两个生产就绪的 CSS 和 JavaScript 压缩器。

| 压缩器 | 特性 | 跳过 |
|----------|----------|-------|
| CSS | 删除空白、注释，并优化属性值 | `.min.css` |
| JavaScript | 变量重命名、死代码消除、语法优化 | `.min.js`, `.min.mjs` |

## 配置选项 {#configuration-options}

插件提供选项以禁用压缩、定制 JavaScript 优化和处理额外文件。

### 禁用压缩 {#disabling-minification}

您可能希望在开发期间或出于调试目的关闭压缩。

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

JavaScript 压缩器提供多个配置选项以控制优化行为。

:::note Gradle 支持
从 v25.12 开始，Gradle 插件支持传递 JavaScript 压缩器选项。
:::

| 选项 | 默认 | 描述 |
|--------|---------|-------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - 仅删除空白和注释</li><li>`SIMPLE_OPTIMIZATIONS` - 变量重命名和死代码移除</li><li>`ADVANCED_OPTIMIZATIONS` - 通过函数/属性重命名进行激进优化</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | 输入 JavaScript 版本: `ECMASCRIPT3`, `ECMASCRIPT5`, `ECMASCRIPT_2015` 到 `ECMASCRIPT_2021`, `ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | 输出 JavaScript 版本: 与 `languageIn` 相同，外加 `NO_TRANSPILE` |
| `prettyPrint` | `false` | 设置为 `true` 以保留调试格式 |

在配置部分配置这些选项：

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
    skip = false  // 设置为 true 以跳过压缩
    minifierConfigurations.put("closureJs", [
      compilationLevel: "SIMPLE_OPTIMIZATIONS",
      languageIn: "ECMASCRIPT_NEXT",
      languageOut: "ECMASCRIPT5"
    ])
}
```

</TabItem>
</Tabs>

### 压缩额外文件 {#minifying-additional-files}

要压缩未通过注解发现的文件，创建一个配置文件，指定 glob 模式：

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# 包含模式
**/*.css
**/*.js

# 排除模式（前缀加 !）
!**/*.min.css
!**/*.min.js
```

## 自定义压缩器 {#custom-minifiers}

该插件通过 Java 的服务提供者接口 (SPI) 支持自定义压缩器，允许您为额外的文件类型或替代压缩库添加支持。

### 创建自定义压缩器 {#creating-a-custom-minifier}

实现 `AssetMinifier` 接口以创建自己的压缩器。以下示例显示一个使用 Gson 删除空格的 JSON 压缩器：

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
    // 跳过配置文件和已压缩的文件
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
    // 如果需要，配置选项
  }
}
```

### 注册您的压缩器 {#registering-your-minifier}

创建服务提供者配置文件：

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### 使用您的自定义压缩器 {#using-your-custom-minifier}

将您的压缩器打包为单独的 JAR 并将其添加为插件依赖：

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
    <!-- 标准压缩器（可选） -->
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
    <p>未注册压缩器</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      将压缩器模块依赖添加到插件配置。对于 CSS，添加 `webforj-minify-phcss-css`。对于 JavaScript，添加 `webforj-minify-closure-js`。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>没有处理文件</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      如果插件报告 `Processed 0 files`，请确认：

      1. 注解处理器在 `maven-compiler-plugin` 中配置，`annotationProcessorPaths` 中包含 `webforj-minify-foundation`
      2. 资源注解在您的源代码中存在
      3. 编译后 `target/classes/META-INF/webforj-resources.json` 存在
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>未找到文件</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      验证文件是否存在于 `src/main/resources/static` 下的正确路径，并且 URL 协议与目录结构匹配。
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

      插件会发出警告，但在失败构建的情况下继续。压缩失败时，原始内容被保留。要修复语法错误，请使用 linter 验证 CSS 或 JavaScript。
    </div>
  </AccordionDetails>
</Accordion>
<br />
