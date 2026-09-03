---
title: 调试
sidebar_position: 15
description: >-
  Attach a remote Java debugger to a running webforJ app from Visual Studio
  Code, IntelliJ IDEA, or Eclipse using Jetty on port 8000.
sidebar_class_name: updated-content
_i18n_hash: c7b0a48745ef8f5793e38a3dd7691176
---
调试是Java开发的重要组成部分，帮助开发者高效地识别和修复问题。本指南解释了如何在Visual Studio Code、IntelliJ IDEA和Eclipse中为webforJ配置调试。

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. 在VS Code中打开你的webforJ项目。
2. 按下 <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>（或在Mac上按 <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>）打开运行和调试面板。
3. 点击“创建launch.json文件”
4. 选择Java作为环境。
5. 修改 `launch.json` 以匹配以下内容：

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

6. 保存文件并点击开始调试。

</TabItem>
<TabItem value="intellij" label="IntelliJ IDEA">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/intellij.mp4" type="video/mp4" />
      </video>
</div>

1. 在IntelliJ IDEA中打开你的项目。
2. 导航到运行 → 编辑配置。
3. 点击 <kbd>+</kbd> 按钮并选择远程JVM调试。
4. 将主机设置为 `localhost`，端口设置为 `8000`。
5. 保存配置并点击调试以连接到运行中的应用程序。

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. 在Eclipse中打开你的项目。
2. 前往运行 → 编辑配置。
3. 选择远程Java应用程序。
4. 点击新建配置并设置：
   - 主机：`localhost`
   - 端口：`8000`
5. 保存并启动调试器。

</TabItem>
</Tabs>

## 运行调试器 {#running-the-debugger}

配置好你的IDE后：

1. 使用相应的命令启动你的webforJ应用：
    - 对于Jetty，请使用 `mvnDebug jetty:run`
    - 对于Spring Boot，请使用 `mvnDebug spring-boot:run`
2. 在你的IDE中运行调试配置。
3. 设置断点并开始调试。

:::tip 调试提示
1. 确保端口8000可用并且未被任何防火墙阻塞。
2. 如果你使用了任何webforJ原型并且在pom.xml文件中更改了端口号，请确保用于调试的端口与更新后的值一致。
:::

## 检查运行中的应用 {#inspecting-the-running-app}

调试器可以显示你的代码在做什么。[craftforJ](/docs/craftforj)展示了代码生成的应用，包括webforJ构建的组件树、每个组件的属性、当前活动的路由以及谁可以访问它。你可以更改一个属性，查看运行中应用的结果，并将该更改写回原始Java代码。

craftforJ与webforJ一起发布，并使用你已经启用的相同调试模式，加上一个额外的属性：

```ini title="webforj.conf"
webforj.debug = true
webforj.devtools.craftforj.enabled = true
```

请参见 [开始使用craftforJ](/docs/craftforj/getting-started)。
