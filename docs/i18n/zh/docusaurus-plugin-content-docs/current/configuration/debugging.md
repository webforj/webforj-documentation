---
title: 调试
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
_i18n_hash: 6e761f399c4a1f8d562acf85f92af615
---
调试是 Java 开发中不可或缺的一部分，可以帮助开发者高效地发现并修复问题。本指南介绍如何在 Visual Studio Code、IntelliJ IDEA 和 Eclipse 中配置 webforJ 调试。

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<VideoPlayer video="debugging/vscode" width={1920} height={1080} title="VS Code" />

1. 在 VS Code 中打开你的 webforJ 项目。
2. 按 <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>（Mac 上按 <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>）打开“运行和调试”面板。
3. 点击“创建 launch.json 文件”。
4. 选择 Java 作为环境。
5. 修改 `launch.json`，使其内容如下：

```json title="launch.json"
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Attach to Jetty",
      "request": "attach",
      "hostName": "localhost",
      "port": 8000
    }
  ]
}
```

6. 保存文件，然后点击“开始调试”。

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<VideoPlayer video="debugging/intellij" width={1920} height={1080} title="IntelliJ" />

1. 在 IntelliJ IDEA 中打开你的项目。
2. 转到“运行 → 编辑配置”。
3. 点击 <kbd>+</kbd> 按钮，然后选择“远程 JVM 调试”。
4. 将主机设置为 `localhost`，端口设置为 `8000`。
5. 保存配置，然后点击“调试”以连接到正在运行的应用。

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<VideoPlayer video="debugging/eclipse" width={1920} height={1080} title="Eclipse" />

1. 在 Eclipse 中打开你的项目。
2. 转到“运行 → 编辑配置”。
3. 选择“远程 Java 应用程序”。
4. 点击“新建配置”，并设置：
   - 主机：`localhost`
   - 端口：`8000`
5. 保存并启动调试器。

</TabItem>
</Tabs>

## 运行调试器 {#running-the-debugger}

配置好 IDE 后：

1. 使用 `mvnDebug` 启动 webforJ 应用。
2. 在 IDE 中运行调试配置。
3. 设置断点并开始调试。

:::tip 调试提示
1. 确保端口 8000 可用，且未被任何防火墙阻止。
2. 如果你使用的是任何 webforJ 原型，并且已更改 pom.xml 文件中的端口号，请确保调试所用的端口与更新后的端口号一致。
:::

## 检查正在运行的应用 {#inspecting-the-running-app}

调试器可以显示代码的运行情况。[craftforJ](/docs/craftforj) 则可以展示代码生成的应用，包括 webforJ 构建的组件树、各组件的属性、当前激活的路由，以及哪些人有权访问该路由。你可以更改属性、在运行中的应用里查看结果，并将更改写回对应的 Java 代码。

craftforJ 随 webforJ 一起提供，使用你已启用的相同调试模式，另外还需要设置一个属性：

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

请参阅 [craftforJ 入门](/docs/craftforj/getting-started)。
