---
title: 热替换
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 5c17c1ef76de9c15b7177aee4b2b8419
---
# 热替换 <DocChip chip='since' label='26.02' />

热替换工具会将编译后的类更改应用到正在运行的应用中，而无需重启。应用会在更新之间保留其状态。此工具在 [webforJ 构建插件](/docs/configuration/build-plugin)配置中命名，并在构建启动应用时附加。运行命令保持不变，项目也无需声明该工具的依赖项。

支持以下两种工具：

- **HotswapAgent** 是一款开源工具。构建插件会在首次运行时下载该代理并缓存。
- **JRebel** 是一款商业产品，需要自行安装并获取许可证。

只能配置其中一种。如果构建同时指定了两种工具，就会报错并列出两者的名称。

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

空元素即表示完整配置：

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

以下两个选项可进一步配置代理的附加方式：

| 选项 | 说明 |
|--------|-------------|
| `version` | 指定代理版本，而不是使用插件选择的版本。 |
| `path` | 磁盘上的代理 jar 文件路径，直接使用该文件而不下载。适用于无法访问网络的机器，或自定义构建的代理。 |

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

任何 Java 虚拟机都支持应用方法体编辑。类结构更改（例如新增字段或方法）则需要虚拟机支持 `-XX:+AllowEnhancedClassRedefinition` 选项；[JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases) 提供了此功能。构建过程会检测该功能并启用它。有关如何安装 JetBrains Runtime，请参阅[先决条件](/docs/introduction/prerequisites#java-development-kit-jdk-21)。

如果虚拟机不支持此功能，方法体编辑仍会生效，但类结构更改要等到重启后才会应用到正在运行的应用。构建日志会打印警告，指出所需的功能；浏览器也会显示一次提示。

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) 是一款商业产品，由其供应商授权。webforJ 不会随附或下载该产品，也不参与其授权事宜。构建过程会读取配置的路径、检查文件是否存在，并直接附加该文件，不作任何更改。

将配置指向 JRebel 安装目录中的代理文件，可以是本机库或 jar 文件：

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

必须指定路径。如果构建选择了 JRebel 却未配置路径，就会报错并指出缺少的设置。

使用 JRebel 时，任何 Java 运行时都可以应用所有类更改，包括类结构更改。

## 命令行选择 {#command-line-selection}

`webforj.hotswap` 属性可在单次运行时覆盖构建文件中的设置。可接受的值为 `hotswapAgent`、`jrebel` 和 `off`。其他任何值都会导致构建失败，并列出有效值。选择 `jrebel` 时，仍须在配置中指定代理路径。

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

编译更改后，更改就会应用到正在运行的应用中。你可以在支持保存时编译的 IDE 中保存文件，也可以在另一个终端中运行编译命令。

如果所有已更改的类都属于当前页面渲染的内容，受影响的部分会原地重建，并保留应用状态。否则，页面会完整重新加载：例如，应用没有路由、类不属于当前渲染的路由，或无法执行重建时。每次编译产生的更改都会在浏览器中触发一次更新。

## 在 IntelliJ IDEA 中加快重新加载 {#speeding-up-reloads-in-intellij-idea}

只有启用了自动构建时，IntelliJ IDEA 才会在保存时编译，而且每个步骤之前都会等待。缩短等待时间，让更改更快应用到正在运行的应用中。

### 启用自动构建 {#enable-automatic-builds}

打开设置并启用以下选项：

1. 在 **Build, Execution, Deployment** > **Compiler** 中，启用 **Build project automatically**。
<!-- vale off -->
2. 在 **Advanced Settings** > **Compiler** 中，启用 **Allow auto-make to start even if developed application is currently running**。
<!-- vale on -->
3. 在 **Appearance & Behavior** > **System Settings** 中，在 **Autosave** 下启用 **Save files if the IDE is idle for**，并将其设为 `1` 秒。

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

按 <kbd>Ctrl</kbd>+<kbd>ALt</kbd>+<kbd>S</kbd>，或选择 **File** > **Settings**。

</TabItem>
<TabItem value="macos" label="macOS">

按 <kbd>Cmd</kbd>+<kbd>+</kbd>，或选择 **IntelliJ IDEA** > **Settings**。

</TabItem>
</Tabs>

### 缩短构建延迟 {#shorten-the-build-delays}

将以下 Registry 键的值设为 `0`：

| 键 | 说明 |
|-----|-------------|
| `compiler.automake.trigger.delay` | 文件更改后到构建开始前的延迟时间。 |
| `compiler.automake.build.while.idle.timeout` | 构建开始前的空闲时间。 |
| `compiler.automake.postpone.when.idle.less.than` | IntelliJ IDEA 延后构建所依据的最短空闲时间。 |

<Tabs>
<TabItem value="windows-linux" label="Windows and Linux">

1. 按 <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> 打开 **Find Action**。
2. 输入 `Registry...` 并选择该项。

</TabItem>
<TabItem value="macos" label="macOS">

1. 按 <kbd>Cmd</kbd>+<kbd>Shift</kbd>+<kbd>A</kbd> 打开 **Find Action**。
2. 输入 `Registry...` 并选择该项。

</TabItem>
</Tabs>

也可以按两次 <kbd>Shift</kbd> 打开 **Search Everywhere**，切换到 **Actions** 标签页，然后输入 `Registry`。

在 **Registry** 对话框中，输入键名筛选列表，然后将其值设为 `0`。

:::warning
Registry 条目属于高级 IntelliJ IDEA 设置，不受支持。要重置这些设置，请在 **Registry** 对话框中选择 **Restore Defaults**。
:::
