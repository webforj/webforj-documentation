---
title: Debugging
sidebar_position: 1
_i18n_hash: fc63d32dc6c8e48192a28f100c29943e
---
调试是Java开发的重要组成部分，帮助开发者高效地识别和修复问题。本指南解释了如何在Visual Studio Code、IntelliJ IDEA和Eclipse中为webforJ配置调试。

<Tabs>
<TabItem value="vscode" label="Visual Studio Code">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/vscode.mp4" type="video/mp4" />
      </video>
</div>

1. 在VS Code中打开您的webforJ项目。
2. 按下 <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd> （在Mac上为 <kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>D</kbd>）打开运行和调试面板。
3. 点击“创建launch.json文件”。
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

1. 在IntelliJ IDEA中打开您的项目。
2. 导航到运行 → 编辑配置。
3. 单击 <kbd>+</kbd> 按钮并选择远程JVM调试。
4. 将主机设置为 `localhost`，端口设置为 `8000`。
5. 保存配置并单击调试以连接到运行中的应用程序。

</TabItem>
<TabItem value="eclipse" label="Eclipse">

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/debug/eclipse.mp4" type="video/mp4" />
      </video>
</div>

1. 在Eclipse中打开您的项目。
2. 转到运行 → 编辑配置。
3. 选择远程Java应用程序。
4. 单击新配置并设置：
   - 主机：`localhost`
   - 端口：`8000`
5. 保存并启动调试器。

</TabItem>
</Tabs>

## 运行调试器 {#running-the-debugger}

配置好您的IDE后：

1. 使用 `mvnDebug jetty:run` 启动您的webforJ应用程序。
2. 在您的IDE中运行调试配置。
3. 设置断点并开始调试。

:::tip 调试提示
1. 确保端口8000可用，并且没有被任何防火墙阻塞。
2. 如果您使用了任何webforJ原型，并且在pom.xml文件中更改了端口号，请确保用于调试的端口与更新的值匹配。
:::
