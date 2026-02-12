---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: new-content
_i18n_hash: e6a8ce3ff1ae6ca3636dc7284f48a110
---
# Minifier 插件 <DocChip chip='since' label='25.11' />

webforJ Minifier 插件会自动 [最小化](https://en.wikipedia.org/wiki/Minification_(programming)) 和优化 CSS 和 JavaScript 资产，在构建过程中进行。此插件会通过 webforJ [资产注解](/docs/managing-resources/importing-assets) 发现引用的资产，并在构建输出中进行最小化，从而减小文件大小并改善加载时间，而无需修改原始源文件。

## 设置 {#setup}

如果你使用 [startforJ](https://docs.webforj.com/startforj) 或 webforJ [原型](/docs/building-ui/archetypes/overview) 创建了项目，那么最小化插件已经配置好，并且在使用 `mvn package -Pprod` 以 `prod` 配置文件构建时会自动运行。

对于手动设置，最小化插件需要两个配置：一个注解处理器用于在编译过程中发现资产，以及一个插件来执行最小化。

<Tabs>
<TabItem value="maven" label="Maven">

将以下内容添加到你的 `pom.xml` 文件中：

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

    <!-- 最小化插件配置 -->
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
        <!-- CSS 最小化 -->
        <dependency>
          <groupId>com.webforj</groupId>
          <artifactId>webforj-minify-phcss-css</artifactId>
          <version>${webforj.version}</version>
        </dependency>
        <!-- JavaScript 最小化 -->
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

将以下内容添加到你的 `build.gradle` 文件中：

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
    // 用于在编译过程中发现资产的注解处理器
    annotationProcessor "com.webforj:webforj-minify-foundation:${webforjVersion}"

    // 最小化实现
    add "webforjMinifier", "com.webforj:webforj-minify-phcss-css:${webforjVersion}"
    add "webforjMinifier", "com.webforj:webforj-minify-closure-js:${webforjVersion}"
}
```

`minify` 任务会在 `jar` 或 `war` 任务之前自动运行。你也可以通过 `./gradlew minify` 手动运行它。

</TabItem>
</Tabs>

## 使用插件 {#using-the-plugin}

一旦配置好，插件会自动工作。只需在你的代码中使用 webforJ 资产注解即可：

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

当你构建项目时，插件会自动：

1. 在编译过程中发现注解中引用的资产
2. 最小化发现的 CSS 和 JavaScript 文件
3. 报告文件大小的减少和处理时间

### URL 协议解析 {#url-protocol-resolution}

插件理解 webforJ [URL 协议](/docs/managing-resources/assets-protocols) 并将其解析为文件系统路径：

| 协议      | 解析为                      | 示例                        |
|-----------|-----------------------------|-------------------------------|
| `ws://`   | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/`       | `context://styles/app.css` → `styles/app.css` |

没有协议的 URL 不被最小化插件支持，并且将被跳过。

## 内置最小化工具 {#built-in-minifiers}

webforJ 包含两个用于 CSS 和 JavaScript 的生产就绪最小化工具。

| 最小化工具 | 特性                               | 跳过                       |
|------------|------------------------------------|----------------------------|
| CSS        | 移除空格、注释，并优化属性值      | `.min.css`                 |
| JavaScript | 变量重命名、死代码消除、语法优化  | `.min.js`, `.min.mjs`      |

## 配置选项 {#configuration-options}

插件提供禁用最小化、自定义 JavaScript 优化和处理额外文件的选项。

### 禁用最小化 {#disabling-minification}

你可能希望在开发期间或调试目的下关闭最小化。

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

### JavaScript 最小化工具选项 {#javascript-minifier-options}

JavaScript 最小化工具提供多个配置选项，以控制优化行为。

:::info 仅限 Maven
JavaScript 最小化工具选项目前仅针对 Maven 可用。Gradle 支持使用默认设置。
:::

| 选项               | 默认                       | 描述                                                                                           |
|--------------------|---------------------------|------------------------------------------------------------------------------------------------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS`    | <ul><li>`WHITESPACE_ONLY` - 仅移除空格和注释</li><li>`SIMPLE_OPTIMIZATIONS` - 变量重命名和死代码移除</li><li>`ADVANCED_OPTIMIZATIONS` - 具有函数/属性重命名的激进优化</li></ul> |
| `languageIn`       | `ECMASCRIPT_NEXT`         | 输入 JavaScript 版本：`ECMASCRIPT3`，`ECMASCRIPT5`，`ECMASCRIPT_2015` 到 `ECMASCRIPT_2021`，`ECMASCRIPT_NEXT` |
| `languageOut`      | `ECMASCRIPT5`             | 输出 JavaScript 版本：与 `languageIn` 相同，且增加 `NO_TRANSPILE`                         |
| `prettyPrint`      | `false`                   | 设置为 `true` 以保留调试格式                                                                  |

在 `minifierConfigurations` 部分中配置这些选项：

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

### 最小化额外文件 {#minifying-additional-files}

要最小化通过注解未发现的文件，请创建一个包含全局模式的配置文件：

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# 包括模式
**/*.css
**/*.js

# 排除模式（前缀使用 !）
!**/*.min.css
!**/*.min.js
```

## 自定义最小化工具 {#custom-minifiers}

插件通过 Java 的服务提供者接口 (SPI) 支持自定义最小化工具，允许你添加对其他文件类型或替代最小化库的支持。

### 创建自定义最小化工具 {#creating-a-custom-minifier}

实现 `AssetMinifier` 接口以创建自己的最小化工具。以下示例展示了一个使用 Gson 移除空格的 JSON 最小化工具：

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
    // 跳过配置文件和已经最小化的文件
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
    // 如果需要配置选项
  }
}
```

### 注册你的最小化工具 {#registering-your-minifier}

创建服务提供者配置文件：

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### 使用你的自定义最小化工具 {#using-your-custom-minifier}

将你的最小化工具作为单独的 JAR 包，并将其作为插件依赖项添加：

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
    <!-- 标准最小化工具（可选） -->
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
    <p>没有注册最小化工具</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] No minifiers registered via SPI. Skipping minification.
      [WARN] Ensure ph-css and/or closure-compiler are on the classpath.
      ```

      将最小化模块的依赖项添加到插件配置中。对于 CSS，请添加 `webforj-minify-phcss-css`。对于 JavaScript，请添加 `webforj-minify-closure-js`。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>没有处理文件</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      如果插件报告 `Processed 0 files`，请验证：

      1. 注解处理器在 `maven-compiler-plugin` 中已配置，并且 `annotationProcessorPaths` 包含 `webforj-minify-foundation`
      2. webforJ 资产注解存在于你的源代码中
      3. 编译后 `target/classes/META-INF/webforj-resources.json` 文件存在
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>文件未找到</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] File not found: /path/to/static/css/app.css (referenced as 'ws://css/app.css')
      ```

      验证该文件是否存在于 `src/main/resources/static` 目录下的正确路径，并确保 URL 协议与目录结构相匹配。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>最小化错误</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] Error minifying file /path/to/app.css: parse error at line 42
      ```

      插件会发出警告，但在不失败构建的情况下继续处理。如果最小化失败，则保留原始内容。要修复语法错误，请使用代码检查工具验证 CSS 或 JavaScript。
    </div>
  </AccordionDetails>
</Accordion>
<br />
