---
title: JBang
sidebar_position: 15
description: >-
  Run webforJ apps as single-file JBang scripts with no Maven, Gradle, or
  project structure for rapid prototyping and demos.
_i18n_hash: a7af803fa3e58cb86e0c18289f8fbb40
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) 是一款可将 Java 代码作为脚本运行的工具，无需构建文件、项目设置或手动编译。webforJ 的 JBang 集成让你能够快速创建 webforJ 应用，非常适合快速原型开发、学习和快速演示，无需完整 Java 程序所需的传统依赖项和基础设施。

## 为什么将 JBang 与 webforJ 搭配使用 {#why-use-jbang}

传统的 webforJ 项目使用 Maven 或 Gradle，包含多个配置文件和标准项目结构。这种设置适用于生产应用，但对于简单的实验或演示来说可能显得过于繁重。

使用 JBang，你可以：

- **立即开始**：编写单个 `.java` 文件并立即运行
- **跳过项目设置**：无需 `pom.xml`、`build.gradle` 或目录结构
- **轻松分享**：发送单个文件，对方只需一条命令即可运行
- **更快学习**：专注于 webforJ 概念，无需处理构建工具的复杂性

此集成还包含在关闭浏览器标签页时自动关闭服务器的功能，让开发工作流程保持简洁。

## 前提条件 {#prerequisites}

### 安装 JBang {#install-jbang}

选择你偏好的安装方式：

```bash
# 通用方式（Linux/macOS/Windows with bash）
curl -Ls https://sh.jbang.dev | bash -s - app setup

# SDKMan
sdk install jbang

# Homebrew (macOS)
brew install jbangdev/tap/jbang

# Chocolatey (Windows)
choco install jbang

# Scoop (Windows)
scoop install jbang
```

验证安装：

```bash
jbang --version
```

:::info[默认 Java 版本]
如果首次运行 JBang 时未安装 JDK，JBang 会自动下载一个。你可以在运行 JBang 之前设置 JDK 版本和供应商：

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[了解有关 JBang 的更多信息]
如需完整的 JBang 文档，请参阅：
- [JBang 入门](https://www.jbang.dev/documentation/jbang/latest/index.html) - 安装和基础知识
- [脚本指令参考](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - 所有可用指令
- [依赖项](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - 高级依赖项管理
:::

## 创建 webforJ 脚本 {#creating-a-script}

创建名为 `HelloWorld.java` 的文件，并填入以下内容：

```java title="HelloWorld.java"
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.webforj:webforj-jbang-starter:25.11
//JAVA 21

package bang;

import com.webforj.App;
import com.webforj.annotation.Routify;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.router.annotation.Route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Routify
public class HelloWorld extends App {
  public static void main(String[] args) {
    SpringApplication.run(HelloWorld.class, args);
  }
}

@Route("/")
class MainView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("What is your name?");
  private Button btn = new Button("Say Hello");

  public MainView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(300);
    self.setStyle("margin", "1em auto");

    btn.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .addClickListener(e -> Toast.show("Welcome to webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### 了解脚本结构 {#script-structure}

| 行 | 用途 |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang 行，可在 Unix 系统上直接执行脚本 |
| `//JAVA 21` | 指定所需的最低 Java 版本；如果需要，JBang 会自动下载 |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | 使用 Maven 坐标声明 webforJ JBang starter 依赖项 |
| `@SpringBootApplication` | 启用 Spring Boot 自动配置 |
| `extends App` | 将此类定义为 webforJ 应用 |

`webforj-jbang-starter` 依赖项包含运行 webforJ 应用所需的一切：Spring Boot starter、开发工具以及自动打开浏览器的功能。

:::note[版本]
将 `25.11` 替换为最新的 webforJ 版本。请查看 [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) 获取最新发布版本。
:::
### 添加依赖项 {#adding-dependencies}

你可以使用多条 `//DEPS` 行添加其他 Maven 依赖项：

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

依赖项使用标准 Maven 坐标（`groupId:artifactId:version`）。首次运行时，JBang 会自动从 Maven Central 获取这些依赖项。

## 运行脚本 {#running-your-script}

使用 JBang 运行脚本：

```bash
jbang HelloWorld.java
```

JBang 将会：

1. 下载依赖项（仅首次运行时）
2. 编译脚本
3. 在随机可用端口上启动嵌入式服务器
4. 在默认浏览器中打开应用

### 使脚本可执行 {#executable-script}

在 Unix 系统上，你可以将脚本设为可直接执行：

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

这是因为文件顶部有 Shebang 行。

## IDE 支持 {#ide-support}

JBang 可与常见的 Java IDE 集成，包括 VS Code、IntelliJ IDEA、Eclipse 等。这些集成提供指令自动补全、自动解析依赖项，以及直接从 IDE 运行和调试脚本等功能。

请参阅 [JBang IDE 集成文档](https://www.jbang.dev/documentation/jbang/latest/editing.html)，了解设置说明和受支持的编辑器。

## 配置 {#configuration}

webforJ JBang starter 包含适合脚本运行的默认设置。你可以使用系统属性自定义其行为。

### 自动关闭 {#auto-shutdown}

默认情况下，当所有已连接应用的浏览器标签页都关闭时，服务器会自动关闭。这样可以避免服务器在后台持续运行，让开发工作流程保持简洁。

| 属性 | 默认值 | 描述 |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | 开启或关闭自动关闭功能 |
| `webforj.jbang.idle-timeout` | `5` | 最后一个浏览器断开连接后，等待多少秒再关闭服务器 |

关闭自动关闭功能：

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

更改空闲超时时间：

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### 默认设置 {#default-settings}

JBang starter 配置了以下默认值：

| 设置 | 值 | 描述 |
|---------|-------|-------------|
| `server.port` | `0` | 随机分配端口，避免同时运行多个脚本时发生冲突 |
| `server.shutdown` | `immediate` | 快速关闭，以便迅速终止脚本 |
| `spring.main.banner-mode` | `off` | 隐藏 Spring Boot banner，让输出更简洁 |
| `logging.level.root` | `ERROR` | 尽量减少日志，让控制台输出保持简洁 |
| `logging.level.com.webforj` | `WARN` | 仅显示 webforJ 的警告和错误 |
| `webforj.devtools.browser.open` | `true` | 应用启动时自动打开浏览器 |

### 重新部署和实时重新加载 {#development-workflow}

JBang 脚本不支持实时重新加载。如需查看更改：

1. 停止正在运行的脚本（关闭浏览器标签页或按 <kbd>Ctrl</kbd>+<kbd>C</kbd>）
2. 编辑代码
3. 再次运行 `jbang HelloWorld.java`

如需在开发期间自动重新部署，请考虑使用[完整的 Maven 项目和 Spring DevTools](/docs/integrations/spring/spring-boot)。更多详情请参阅[实时重新加载文档](/docs/configuration/deploy-reload/overview)。

## 迁移到完整项目 {#transitioning}

当原型不再适合单个文件时，请使用 [startforJ](https://docs.webforj.com/startforj) 或 [Maven 原型](./spring/spring-boot#option-2-using-the-command-line)创建正式项目。你可以将脚本逻辑直接复制到生成的项目结构中。
