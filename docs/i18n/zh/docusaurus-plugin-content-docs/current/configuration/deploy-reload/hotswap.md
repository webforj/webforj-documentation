---
title: Hotswap
sidebar_position: 10
sidebar_class_name: new-content
description: >-
  Apply compiled class changes to a running webforJ app without a restart,
  through HotswapAgent or JRebel configured in the webforJ build plugin.
_i18n_hash: 0943bf726abb55f753a0149ca3744ad7
---
# Hotswap <DocChip chip='since' label='26.02' />

一个热替换工具可以将编译后的类更改应用于运行中的应用程序，而无需重新启动。应用程序在更新之间保持其状态。该工具在[webforJ构建插件](/docs/configuration/build-plugin)配置中命名，并在构建启动应用程序时附加。运行命令保持不变，项目声明不需要依赖此工具。

支持两种工具：

- **HotswapAgent** 是开源的。构建插件在第一次运行时下载代理并进行缓存。
- **JRebel** 是一款商业产品。它需要您自己的安装和许可证。

仅配置一个。命名两个的构建会失败并显示错误。

<!-- vale off -->
## HotswapAgent {#hotswapagent}
<!-- vale on -->

一个空元素是完整的配置：

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

两个选项细化附加：

| 选项 | 描述 |
|--------|-------------|
| `version` | 用于指定代理版本，而不是插件选择的版本。 |
| `path` | 磁盘上的代理jar，直接使用，无需下载。适用于没有网络访问权限的机器或自定义代理构建。 |

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

方法体的编辑可以在任何Java虚拟机上应用。类结构的更改，如新字段或新方法，需要一个支持`-XX:+AllowEnhancedClassRedefinition`选项的虚拟机，该选项由[JetBrains Runtime](https://github.com/JetBrains/JetBrainsRuntime/releases)提供。构建检测此能力并启用它。有关安装JetBrains Runtime的详细信息，请参见[先决条件](/docs/introduction/prerequisites#java-development-kit-jdk-21)。

如果没有此能力，方法体的编辑仍然会应用，但类结构更改在重新启动之前不会到达运行中的应用程序。构建日志会打印一个警告，说明该要求，浏览器会显示一次通知。

<!-- vale off -->
## JRebel {#jrebel}
<!-- vale on -->

[JRebel](https://www.jrebel.com/) 是一款商业产品，由其供应商授权。webforJ不提供它，不下载它，并且不参与其许可。构建读取配置的路径，检查文件是否存在，并保持不变地附加它。

将配置指向您JRebel安装中的代理，一个本地库或一个jar：

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

路径是必需的。选择没有路径的JRebel的构建会因缺少设置而失败。

使用JRebel，所有类更改，包括结构更改，都可以在任何Java运行时上应用。

## 命令行选择 {#command-line-selection}

`webforj.hotswap`属性会覆盖构建文件，以便进行单次运行。接受的值为`hotswapAgent`，`jrebel`和`off`。任何其他值都会导致构建失败，并列出有效的值。选择`jrebel`仍然需要在配置中指定代理路径。

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

编译更改并使其达到运行中的应用程序。使用会在保存时编译的IDE进行保存，或在第二个终端中运行编译。

当每个更改的类属于当前页面呈现的内容时，受影响的部分会就地重建，应用程序状态保持不变。否则，页面会全重新加载：对于没有路由的应用程序，对于呈现路由之外的类，或当重建无法进行时。一个已编译的更改会在浏览器中产生一个更新。
