---
sidebar_position: 21
title: Minifier Plugin
sidebar_class_name: updated-content
_i18n_hash: bbc598d57e4531fcd7f76fe117d2e49a
---
# Minifier 插件 <DocChip chip='since' label='25.11' />

webforJ Minifier 插件在构建过程中自动 [minifies](https://en.wikipedia.org/wiki/Minification_(programming)) 和优化 CSS 和 JavaScript 资产。该插件通过 webforJ [资产注释](/docs/managing-resources/importing-assets) 发现引用的资产，并在构建输出中进行压缩，从而减少文件大小并改善加载时间，而不修改原始源文件。

## 设置 {#setup}

如果您使用 [startforJ](https://docs.webforj.com/startforj) 或 webforJ [原型](/docs/building-ui/archetypes/overview) 创建了项目，则 minifier 插件已经配置，并在使用 `prod` 配置文件构建时自动运行，命令为 `mvn package -Pprod`。

对于手动设置，minifier 需要两个配置：一个用于在编译期间发现资产的注释处理器，以及一个用于执行压缩的插件。

<Tabs>
<TabItem value="maven" label="Maven">

将以下内容添加到您的 `pom.xml`：

```xml
<build>
  <plugins>
    <!-- 注释处理器配置 -->
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
  // 用于在编译期间发现资产的注释处理器
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

配置完成后，插件会自动工作。仅需在您的代码中使用 webforJ 资产注释：

```java
package com.example;

import com.webforj.annotation.StyleSheet;
import com.webforj.annotation.JavaScript;

@StyleSheet("ws://css/app.css")
@JavaScript("ws://js/app.js")
public class MyApp extends App {
  // 您的应用代码
}
```

当您构建项目时，插件会自动：

1. 在编译期间发现注释中引用的资产
2. 压缩发现的 CSS 和 JavaScript 文件
3. 报告大小减少和处理时间

### URL 协议解析 {#url-protocol-resolution}

插件理解 webforJ [URL 协议](/docs/managing-resources/assets-protocols) 并将其解析为文件系统路径：

| 协议 | 解析为 | 示例 |
|------|--------|------|
| `ws://` | `src/main/resources/static/` | `ws://css/app.css` → `static/css/app.css` |
| `context://` | `src/main/resources/` | `context://styles/app.css` → `styles/app.css` |

不带协议的 URL 不被 minifier 支持，将被跳过。

## 内置压缩器 {#built-in-minifiers}

webforJ 包含两个适用于生产的 CSS 和 JavaScript 压缩器。

| 压缩器 | 特性 | 跳过 |
|--------|------|------|
| CSS | 移除空格、注释，优化属性值 | `.min.css` |
| JavaScript | 变量重命名、死代码消除、语法优化 | `.min.js`, `.min.mjs` |

## 配置选项 {#configuration-options}

插件提供禁用压缩、自定义 JavaScript 优化和处理其他文件的选项。

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

### JavaScript 压缩选项 {#javascript-minifier-options}

JavaScript 压缩器提供几个配置选项以控制优化行为。

:::note Gradle 支持
从 v25.12 开始，Gradle 插件支持传递 JavaScript 压缩选项。
:::

| 选项 | 默认值 | 描述 |
|------|--------|------|
| `compilationLevel` | `SIMPLE_OPTIMIZATIONS` | <ul><li>`WHITESPACE_ONLY` - 仅移除空格和注释</li><li>`SIMPLE_OPTIMIZATIONS` - 变量重命名和死代码移除</li><li>`ADVANCED_OPTIMIZATIONS` - 函数/属性重命名的激进优化</li></ul> |
| `languageIn` | `ECMASCRIPT_NEXT` | 输入 JavaScript 版本：`ECMASCRIPT3`、`ECMASCRIPT5`、`ECMASCRIPT_2015` 到 `ECMASCRIPT_2021`、`ECMASCRIPT_NEXT` |
| `languageOut` | `ECMASCRIPT5` | 输出 JavaScript 版本：与 `languageIn` 相同，另加 `NO_TRANSPILE` |
| `prettyPrint` | `false` | 设置为 `true` 以保留用于调试的格式 |

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

### 压缩其他文件 {#minifying-additional-files}

要压缩未通过注释发现的文件，请创建一个配置文件，指定 glob 模式：

```hocon title="src/main/resources/META-INF/webforj-minify.txt"
# 包括模式
**/*.css
**/*.js

# 排除模式（以 ! 为前缀）
!**/*.min.css
!**/*.min.js
```

## 自定义压缩器 {#custom-minifiers}

该插件支持通过 Java 的服务提供者接口 (SPI) 添加自定义压缩器，允许您为其他文件类型或替代压缩库添加支持。

### 创建自定义压缩器 {#creating-a-custom-minifier}

实现 `AssetMinifier` 接口以创建您自己的压缩器。以下示例显示了一个使用 Gson 移除空格的 JSON 压缩器：

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
      logger.warning("在 " + sourceFile + " 中的 JSON 格式不正确，跳过: " + e.getMessage());
      return content;
    } catch (Exception e) {
      throw new MinificationException("压缩 JSON 文件失败: " + sourceFile, e);
    }
  }

  @Override
  public Set<String> getSupportedExtensions() {
    return Set.of("json");
  }

  @Override
  public boolean shouldMinify(Path filePath) {
    String filename = filePath.getFileName().toString();
    // 跳过配置文件和已经压缩的文件
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
    // 如果需要，进行配置选项
  }
}
```

### 注册您的压缩器 {#registering-your-minifier}

创建一个服务提供者配置文件：

```txt title="src/main/resources/META-INF/services/com.webforj.minify.common.AssetMinifier"
com.example.minifier.JsonMinifier
```

### 使用您的自定义压缩器 {#using-your-custom-minifier}

将您的压缩器打包为单独的 JAR 并将其添加为插件依赖项：

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
    <p>没有注册的压缩器</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      ```
      [WARN] 通过 SPI 未注册任何压缩器。跳过压缩。
      [WARN] 确保 ph-css 和/或 closure-compiler 在类路径上。
      ```

      将压缩器模块依赖项添加到插件配置中。对于 CSS，添加 `webforj-minify-phcss-css`。对于 JavaScript，添加 `webforj-minify-closure-js`。
    </div>
  </AccordionDetails>
</Accordion>

<Accordion disableGutters>
  <AccordionSummary expandIcon={<ExpandMoreIcon />}>
    <p>未处理任何文件</p>
  </AccordionSummary>
  <AccordionDetails>
    <div>
      如果插件报告 `Processed 0 files`，请验证以下内容：

      1. 在 `maven-compiler-plugin` 中配置的注释处理器包括 `webforj-minify-foundation` 在 `annotationProcessorPaths` 中
      2. 您的源代码中存在 webforJ 资产注释
      3. 在编译后 `target/classes/META-INF/webforj-resources.json` 存在
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
      [WARN] 未找到文件: /path/to/static/css/app.css (引用为 'ws://css/app.css')
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
      [WARN] 压缩文件 /path/to/app.css 时出错: 第 42 行的解析错误
      ```

      插件会发出警告，但在不导致构建失败的情况下继续。压缩失败时，原始内容会被保留。要修复语法错误，请使用 linter 验证 CSS 或 JavaScript。
    </div>
  </AccordionDetails>
</Accordion>
<br />
