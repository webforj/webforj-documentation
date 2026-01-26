---
title: JBang
sidebar_position: 15
sidebar_class_name: new-content
_i18n_hash: a8ffb21c2834adc74528dc39cb6d0497
---
# JBang <DocChip chip='since' label='25.11' />

[JBang](https://www.jbang.dev/) 是一款允许您以脚本形式运行 Java 代码的工具，无需构建文件、项目设置或手动编译。webforJ JBang 集成使您能够快速创建 webforJ 应用程序，特别适合快速原型开发、学习和快速演示，而无需传统 Java 程序的依赖项和基础设施。

## 为什么在 webforJ 中使用 JBang {#why-use-jbang}

传统的 webforJ 项目使用 Maven 或 Gradle，包含多个配置文件和标准项目结构。虽然这种设置适用于生产应用，但对于简单实验或演示来说可能显得笨重。

使用 JBang，您可以：

- **立即开始**：编写一个 `.java` 文件并立即运行
- **跳过项目设置**：无需 `pom.xml`，无需 `build.gradle`，也无需目录结构
- **轻松分享**：发送给别人一个他们可以用一个命令运行的单个文件
- **更快学习**：专注于 webforJ 概念，而不必面对构建工具的复杂性

集成还包括在您关闭浏览器标签页时自动关闭服务器，保持您的开发工作流清洁。

## 必要条件 {#prerequisites}

### 安装 JBang {#install-jbang}

选择您喜欢的安装方法：

```bash
# Universal (Linux/macOS/Windows with bash)
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

:::tip[了解更多关于 JBang 的信息]
有关全面的 JBang 文档，请参见：
- [JBang 入门](https://www.jbang.dev/documentation/jbang/latest/index.html) - 安装与基础知识
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

  private FlexLayout self = getBoundComponent();
  private TextField hello = new TextField("你叫什么名字?");
  private Button btn = new Button("说你好");

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

| 行 | 目的 |
|------|---------|
| `///usr/bin/env jbang "$0" "$@" ; exit $?` | 允许在 Unix 系统上直接执行脚本的 Shebang 行 |
| `//JAVA 21` | 指定所需的最低 Java 版本；如果需要，JBang 会自动下载 |
| `//DEPS com.webforj:webforj-jbang-starter:25.11` | 使用 Maven 坐标声明 webforJ JBang starter 作为依赖 |
| `@SpringBootApplication` | 启用 Spring Boot 自动配置 |
| `extends App` | 使此类成为 webforJ 应用 |

`webforj-jbang-starter` 依赖项包含运行 webforJ 应用所需的一切：Spring Boot starter、开发工具和自动打开浏览器的功能。

:::note[版本]
将 `25.11` 替换为最新的 webforJ 版本。请查看 [Maven Central](https://central.sonatype.com/artifact/com.webforj/webforj-jbang-starter) 获取最新的发布信息。
:::
### 添加依赖 {#adding-dependencies}

您可以使用多个 `//DEPS` 行添加其他 Maven 依赖：

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 21
//DEPS com.webforj:webforj-jbang-starter:25.11
//DEPS com.google.code.gson:gson:2.11.0
//DEPS org.apache.commons:commons-lang3:3.14.0
```

依赖项使用标准 Maven 坐标（`groupId:artifactId:version`）。JBang 在首次运行时会自动从 Maven Central 获取它们。

## 运行您的脚本 {#running-your-script}

使用 JBang 运行脚本：

```bash
jbang HelloWorld.java
```

JBang 将会：

1. 下载依赖（仅首次运行）
2. 编译脚本
3. 启动嵌入式服务器并分配一个随机可用的端口
4. 打开您的默认浏览器以访问应用程序

### 使脚本可执行 {#executable-script}

在 Unix 系统上，您可以使脚本直接可执行：

```bash
chmod +x HelloWorld.java
./HelloWorld.java
```

这适用于文件顶部的 Shebang 行。

## IDE 支持 {#ide-support}

JBang 与常用的 Java IDE（包括 VS Code、IntelliJ IDEA、Eclipse 等）集成。这些集成提供了诸如指令自动完成功能、自动解析依赖关系以及能够直接从 IDE 运行和调试脚本等功能。

有关设置说明和支持的编辑器，请参见 [JBang IDE 集成文档](https://www.jbang.dev/documentation/jbang/latest/editing.html)。

## 配置 {#configuration}

webforJ JBang starter 包含优化的脚本默认设置。您可以使用系统属性自定义行为。

### 自动关闭 {#auto-shutdown}

默认情况下，当与应用程序连接的所有浏览器标签页关闭时，服务器会自动关闭。这可以通过不让孤立的服务器运行来保持开发工作流的清洁。

| 属性 | 默认值 | 描述 |
|----------|---------|-------------|
| `webforj.jbang.auto-shutdown` | `true` | 打开或关闭自动关闭 |
| `webforj.jbang.idle-timeout` | `5` | 在最后一个浏览器断开连接后等待的秒数，才会关闭 |

要关闭自动关闭：

```bash
jbang -Dwebforj.jbang.auto-shutdown=false HelloWorld.java
```

要更改闲置超时：

```bash
jbang -Dwebforj.jbang.idle-timeout=30 HelloWorld.java
```

### 默认设置 {#default-settings}

JBang starter 配置了以下默认值：

| 设置 | 值 | 描述 |
|---------|-------|-------------|
| `server.port` | `0` | 随机端口分配，以避免在运行多个脚本时发生冲突 |
| `server.shutdown` | `immediate` | 快速关闭，用于快速终止脚本 |
| `spring.main.banner-mode` | `off` | 隐藏 Spring Boot 横幅，以获得更清洁的输出 |
| `logging.level.root` | `ERROR` | 最小化日志记录，以保持控制台输出的整洁 |
| `logging.level.com.webforj` | `WARN` | 仅显示来自 webforJ 的警告和错误 |
| `webforj.devtools.browser.open` | `true` | 应用程序启动时自动打开浏览器 |

### redeployment 和动态重载 {#development-workflow}

JBang 脚本不支持动态重载。要查看更改：

1. 停止运行脚本（关闭浏览器标签页或按 `Ctrl+C`）
2. 编辑您的代码
3. 再次运行 `jbang HelloWorld.java`

要在开发过程中自动重新部署，请考虑使用 [完整的 Maven 项目与 Spring DevTools](/docs/integrations/spring/spring-boot)。有关更多详细信息，请查看 [动态重载文档](/docs/configuration/deploy-reload/overview)。

## 转移到完整项目 {#transitioning}

当您的原型超出单一文件时，请使用 [startforJ](https://docs.webforj.com/startforj) 或 [Maven 原型](./spring/spring-boot#option-2-using-the-command-line) 创建一个适当的项目。您可以直接将脚本逻辑复制到生成的项目结构中。
