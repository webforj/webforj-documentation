---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: 3e783061967931c25ff55499a3139122
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) 是一个工具，允许您将 Java 代码作为脚本运行，而无需构建文件、项目设置或手动编译。webforJ JBang 集成使您能够快速创建 webforJ 应用程序，最适合用于快速原型制作、学习和快速演示，而无需全面 Java 程序的传统依赖和基础设施。

## 为什么将 JBang 与 webforJ 一起使用 {#why-use-jbang}

传统的 webforJ 项目使用 Maven 或 Gradle，配有多个配置文件和标准项目结构。这种设置对于生产应用是标准的，但对于简单的实验或演示来说可能显得繁重。

使用 JBang，您可以：

- **立即启动**：编写一个 `.java` 文件并立即运行
- **跳过项目设置**：无需 `pom.xml`，无需 `build.gradle`，也无需目录结构
- **轻松分享**：发送一个文件，收件人只需通过一个命令即可运行
- **更快学习**：关注 webforJ 概念，而无需处理构建工具的复杂性

该集成在您关闭浏览器标签时会自动关闭服务器，保持您的开发工作流程干净整洁。

## 前提条件 {#prerequisites}

### 安装 JBang {#install-jbang}

选择您偏好的安装方法：

```bash
# 通用 (Linux/macOS/Windows 使用 bash)
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
当您第一次运行 JBang 而没有安装 JDK 时，JBang 会自动下载一个。您可以在运行 JBang 之前设置 JDK 版本和供应商：

```bash
export JBANG_DEFAULT_JAVA_VERSION=21
export JBANG_JDK_VENDOR=temurin
```
:::

:::tip[了解更多 JBang]
有关全面的 JBang 文档，请参见：
- [JBang 快速入门](https://www.jbang.dev/documentation/jbang/latest/index.html) - 安装和基础知识
- [脚本指令参考](https://www.jbang.dev/documentation/jbang/latest/script-directives.html) - 所有可用指令
- [依赖关系](https://www.jbang.dev/documentation/jbang/latest/dependencies.html) - 高级依赖管理
:::

## 创建 webforJ 脚本 {#creating-a-script}

创建一个名为 `HelloWorld.java` 的文件，内容如下：

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
        .addClickListener(e -> Toast.show("欢迎使用 webforJ JBang Starter " + hello.getValue() + "!", Theme.GRAY));

    self.add(hello, btn);
  }
}

```

### 理解脚本结构 {#script-structure}

| 行   | 目的         |
|------|--------------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | Shebang 行，允许脚本在 Unix 系统上直接执行 |
| `//JAVA 21` | 指定所需的最低 Java 版本；如果需要，JBang 自动下载它 |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | 使用 Maven 坐标声明 webforJ JBang 启动程序为依赖 |
| `@SpringBootApplication` | 启用 Spring Boot 自动配置 |
| `extends App` | 使此类成为 webforJ 应用 |

`webforj-jbang-starter` 依赖项包含运行 webforJ 应用所需的所有内容：Spring Boot 启动器、开发工具和自动打开浏览器。

:::note[版本]
将 `25.11` 替换为最新的 webforJ 版本。请查看 [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) 以获取最新版本。
:::

### 添加依赖项 {#adding-dependencies}

您可以使用多个 `//DEPS` 行添加更多 Maven 依赖项：

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

依赖项使用标准的 Maven 坐标 (`groupId:artifactId:version`)。JBang 在首次运行时会自动从 Maven Central 获取它们。

## 运行您的脚本 {#running-your-script}

使用 JBang 运行脚本：

```bash
jbang HelloWorld.java
```

JBang 将：

1. 下载依赖项（仅首次运行）
2. 编译脚本
3. 在随机可用端口上启动嵌入式服务器
4. 打开默认浏览器以访问应用程序

### 使脚本可执行 {#executable-script}

在 Unix 系统上，您可以使脚本直接可执行：

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

这是可行的，因为文件顶部有 shebang 行。

## IDE 支持 {#ide-support}

JBang 与流行的 Java IDE（包括 VS Code、IntelliJ IDEA、Eclipse 等）集成。这些集成提供了如指令自动补全、自动依赖解析以及从 IDE 直接运行和调试脚本的功能。

有关设置说明和支持的编辑器，请参见 [JBang IDE 集成文档](https://www.jbang.dev/documentation/jbang/latest/editing.html)。

## 配置 {#configuration}

webforJ JBang 启动器包括优化针对脚本的合理默认设置。您可以使用系统属性自定义行为。

### 自动关机 {#auto-shutdown}

默认情况下，当所有连接到应用的浏览器标签关闭时，服务器会自动关闭。这通过不留下孤立的服务器运行来保持您的开发工作流程干净。

| 属性                        | 默认值        | 描述                                 |
|---------------------------|-------------|------------------------------------|
| `webforj.jbang.auto-shutdown` | `true`      | 开启或关闭自动关机                       |
| `webforj.jbang.idle-timeout`  | `5`         | 最后一个浏览器断开后等待的秒数，之后将关闭 |

要关闭自动关机：

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

要更改空闲超时时间：

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### 默认设置 {#default-settings}

JBang 启动器配置以下默认设置：

| 设置                       | 值       | 描述                                     |
|--------------------------|---------|----------------------------------------|
| `server.port`            | `0`     | 随机端口分配，以避免运行多个脚本时的冲突          |
| `server.shutdown`        | `immediate` | 快速关闭以快速终止脚本                       |
| `spring.main.banner-mode`| `off`   | 隐藏 Spring Boot 横幅，以获得更清晰的输出           |
| `logging.level.root`     | `ERROR` | 最小日志以保持控制台输出整洁                  |
| `logging.level.com.webforj` | `WARN`  | 仅显示来自 webforJ 的警告和错误               |
| `webforj.devtools.browser.open` | `true`  | 应用启动时自动打开浏览器                     |

### 重新部署和实时重载 {#development-workflow}

JBang 脚本不支持实时重载。要查看更改：

1. 停止正在运行的脚本（关闭浏览器标签或按 `Ctrl+C`）
2. 编辑您的代码
3. 再次运行 `jbang HelloWorld.java`

要在开发期间实现自动重新部署，请考虑使用 [全 Maven 项目与 Spring DevTools](/docs/integrations/spring/spring-boot)。有关更多详细信息，请参见 [实时重载文档](/docs/configuration/deploy-reload/overview)。

## 过渡到完整项目 {#transitioning}

当您的原型超出单个文件时，请使用 [startforJ](https://docs.webforj.com/startforj) 或 [Maven 原型](./spring/spring-boot#option-2-using-the-command-line) 创建一个适当的项目。您可以将脚本逻辑直接复制到生成的项目结构中。
