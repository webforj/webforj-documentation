---
title: 热替换
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 97e2fbe0bd74ada89fb19fae84fc4102
---
# 热替换 <DocChip chip='since' label='26.02' />

热替换工具会将已编译的类更改应用到正在运行的应用，无需重启。应用在更新期间会保留其状态。该工具在 [webforJ 构建插件](/docs/configuration/build-plugin)配置中指定，并在构建启动应用时附加到应用。运行命令保持不变，项目也无需为此声明依赖项。

支持两种工具：

- **HotswapAgent** 是开源工具。构建插件会在首次运行时下载该代理并缓存。
- **JRebel** 是商业产品。你需要自行安装并获取许可证。

只能配置其中一种。如果构建同时指定了两种工具，就会失败，并显示同时列出两者的错误。

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

空元素就是完整配置：

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<plugin>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-maven-plugin</artifactId>
  <extensions>true</extensions>
  <configuration>
    <hotswap>
      <hotswapAgent/>
    </hotswap>
  </configuration>
</plugin>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {}
  }
}
```

</TabItem>
</Tabs>

有两个选项可进一步配置代理的附加方式：

| 选项 | 说明 |
|--------|-------------|
| `version` | 指定代理版本，而不是使用插件选择的版本。 |
| `path` | 磁盘上的代理 jar 文件路径，使用该文件时不会下载。适用于无法访问网络的计算机或自定义构建的代理。 |

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <hotswapAgent>
    <path>/path/to/hotswap-agent.jar</path>
  </hotswapAgent>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    hotswapAgent {
      path = file('/path/to/hotswap-agent.jar')
    }
  }
}
```

</TabItem>
</Tabs>

### 类结构更改 {#class-structure-changes}

方法体的编辑可在任何 Java 虚拟机上应用。类结构的更改（例如新增字段或方法）则需要虚拟机支持 `-XX:+AllowEnhancedClassRedefinition` 选项，[JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) 提供了此功能。构建会检测虚拟机是否支持该功能，并启用它。有关安装 JetBrains Runtime 的信息，请参阅[先决条件](/docs/introduction/prerequisites#java-development-kit-jdk-21)。

如果虚拟机不支持此功能，方法体的编辑仍可应用，但类结构更改要等到重启后才会影响正在运行的应用。构建日志会显示一条警告，说明所需条件；浏览器也会显示一次提示。

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) 是由其供应商授权的商业产品。webforJ 不会随附或下载该产品，也不参与其授权流程。构建会读取配置的路径，检查文件是否存在，然后直接附加该文件，不作任何更改。

将配置指向 JRebel 安装目录中的代理，可以是本机库或 jar 文件：

<Tabs>
<TabItem value="maven" label="Maven">

```xml title="pom.xml"
<hotswap>
  <jrebel>
    <path>/path/to/libjrebel64.dylib</path>
  </jrebel>
</hotswap>
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```groovy title="build.gradle"
webforj {
  hotswap {
    jrebel {
      path = file('/path/to/libjrebel64.dylib')
    }
  }
}
```

</TabItem>
</Tabs>

必须指定路径。如果构建选择了 JRebel 但未指定路径，就会失败，并显示指出缺少该设置的错误。

使用 JRebel 时，所有类更改（包括结构更改）都可以在任何 Java 运行时上应用。

## 命令行选择 {#command-line-selection}

`webforj.hotswap` 属性可在单次运行时覆盖构建文件中的设置。可接受的值为 `hotswapAgent`、`jrebel` 和 `off`。其他任何值都会导致构建失败，并显示列出有效值的错误。选择 `jrebel` 时，仍需在配置中指定代理路径。

<Tabs>
<TabItem value="maven" label="Maven">

```bash
mvn -Dwebforj.hotswap=off
mvn -Dwebforj.hotswap=hotswapAgent
```

</TabItem>
<TabItem value="gradle" label="Gradle">

```bash
./gradlew bootRun -Pwebforj.hotswap=off
./gradlew bootRun -Pwebforj.hotswap=hotswapAgent
```

</TabItem>
</Tabs>

## 应用更改 {#applying-a-change}

编译更改后，它就会应用到正在运行的应用。你可以在保存时会自动编译的 IDE 中保存文件，也可以在另一个终端中运行编译命令。

如果所有已更改的类都属于当前页面所呈现的内容，受影响的部分会原位重建，应用状态也会保留。否则，页面会完整重新加载；出现这种情况的场景包括：应用没有路由、更改的类不属于当前呈现的路由，或无法执行重建。每次编译产生的更改都会在浏览器中触发一次更新。

## 加快 IntelliJ IDEA 中的重新加载速度 {#speeding-up-reloads-in-intellij-idea}

只有在启用了自动构建时，IntelliJ IDEA 才会在保存时编译；而且每个步骤开始前都会等待一段时间。缩短等待时间，让更改更快应用到正在运行的应用。

### 启用自动构建 {#enable-automatic-builds}

打开设置并启用以下选项：

1. 在**构建、执行、部署** > **编译器**中，启用**自动构建项目**。
<!-- vale off -->
2. 在**高级设置** > **编译器**中，启用**即使正在运行开发中的应用程序，也允许启动自动构建**。
<!-- vale on -->
3. 在**外观与行为** > **系统设置**的**自动保存**部分，启用**如果 IDE 空闲，则保存文件**，并将时间设为 `1` 秒。

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

按 <kbd>Ctrl+ALt+S</kbd>，或选择**文件** > **设置**。

</TabItem>
<TabItem value="macos" label="macOS">

按 <kbd>Cmd+,</kbd>，或选择**IntelliJ IDEA** > **设置**。

</TabItem>
</Tabs>

### 缩短构建等待时间 {#shorten-the-build-delays}

将以下 Registry 键的值设为 `0`：

| 键 | 说明 |
|-----|-------------|
| `compiler.automake.trigger.delay` | 文件更改后到构建开始前的等待时间。 |
| `compiler.automake.build.while.idle.timeout` | 构建开始前的空闲时间。 |
| `compiler.automake.postpone.when.idle.less.than` | IntelliJ IDEA 会推迟构建的空闲时间下限。 |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. 按 `Ctrl+Shift+A` 打开**查找操作**。
2. 输入 `Registry...` 并选择该项。

</TabItem>
<TabItem value="macos" label="macOS">

1. 按 `Cmd+Shift+A` 打开**查找操作**。
2. 输入 `Registry...` 并选择该项。

</TabItem>
</Tabs>

你也可以连按两次 `Shift` 打开**随处搜索**，切换到**操作**选项卡，然后输入 `Registry`。

在**Registry**对话框中，输入键名以筛选列表，然后将其值设为 `0`。

:::warning
Registry 条目属于高级 IntelliJ IDEA 设置，不受官方支持。要重置这些条目，请在**Registry**对话框中选择**恢复默认值**。
:::
