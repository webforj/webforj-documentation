---
title: 现代化教程
sidebar_position: 4
description: >-
  Modernize a Swing customer management app step by step by embedding it with
  WebswingConnector and layering in webforJ dialogs and forms.
_i18n_hash: ded3a5a3abe3905086532c58861652f8
---
本教程将介绍如何通过集成 `WebswingConnector`，将现有 Java Swing 应用现代化。你将学习如何让传统桌面应用可通过 Web 访问，并逐步添加现代 Web 功能，例如基于 Web 的对话框，以及使用 webforJ 组件构建的交互式表单。

:::note 前置条件
开始本教程前，请先完成[设置和配置](./setup)步骤，以配置 Webswing 服务器和 CORS 设置。
:::

:::tip 源代码
本教程的完整源代码托管在 GitHub 上：[webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<VideoPlayer video="webswing/modernization-tutorial" width={1920} height={1080} title="Modernization tutorial" />

## 场景 {#the-scenario}

假设你有一个使用 Swing 构建的客户管理应用，已经投入生产多年。它运行良好，但用户现在期望能够通过 Web 访问，并使用现代化界面。与其从头重写，不如使用 Webswing 立即让应用支持 Web 访问，然后逐步使用 webforJ 组件添加现代 Web 功能，例如基于 Web 的对话框和表单。

## 起点：Swing 应用 {#starting-point-the-swing-app}

示例 Swing 应用是一个带有典型 CRUD 操作的客户表格。与许多企业级 Swing 应用一样，它遵循标准模式：

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "Name", "Company", "Email" };
    model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(model);
    table.setRowHeight(30);
    table.setRowSelectionAllowed(true);

    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          // Handle double-click to edit
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "Name:", nameField,
        "Company:", companyField,
        "Email:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "Edit Customer",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

此应用作为桌面应用运行良好，但不支持 Web 访问。用户必须安装 Java，并在本地运行 JAR 文件。

## 步骤 1：让应用识别 Webswing {#step-1-making-it-webswing-aware}

第一步是让 Swing 应用检测自己是否运行在 Webswing 下。这样，应用就能调整自身行为，同时保持桌面环境下的兼容性。

### 检测 Webswing 环境 {#detecting-the-webswing-environment}

将 Webswing API 依赖项添加到 Swing 项目中：

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

然后修改应用以检测 Webswing 运行时：

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

这里的关键在于，当应用作为普通桌面应用运行时，`WebswingUtil.getWebswingApi()` 会返回 `null`，因此可以保持双模式兼容性。

### 调整应用在 Web 部署下的行为 {#adapting-behavior-for-web-deployment}

完成检测后，就可以调整应用的行为了。最重要的变化是处理用户交互的方式：

```java
private void handleDoubleClick(MouseEvent e) {
  int row = table.rowAtPoint(e.getPoint());
  if (row >= 0 && row < customers.size()) {
    Customer customer = customers.get(row);

    if (isWebswing) {
      api.sendActionEvent("select-customer", gson.toJson(customer), null);
    } else {
      showEditDialog(customer);
    }
  }
}
```

根据 `isWebswing` 的值分支处理后，代码就可以同时支持这两种环境。

## 步骤 2：创建 webforJ 封装应用 {#step-2-creating-the-webforj-wrapper}

现在，Swing 应用已经可以通过事件进行通信。接下来，创建一个 webforJ 应用来嵌入 Swing 应用，并添加现代 Web 功能，例如基于 Web 的对话框和表单。

### 设置连接器 {#setting-up-the-connector}

`WebswingConnector` 组件可将 Webswing 托管的应用嵌入 webforJ 视图中：

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

连接器会连接到 Webswing 服务器，并建立双向通信通道。

### 处理来自 Swing 的事件 {#handling-events-from-swing}

当 Swing 应用发送事件时（例如用户双击某一行），连接器会接收这些事件：

```java
connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        JsonObject customer = JsonParser.parseString(data).getAsJsonObject();
        CustomerForm dialog = new CustomerForm(customer);
        self.add(dialog);
        dialog.onSave(() -> {
          Gson gson = new Gson();
          connector.performAction("update-customer", gson.toJson(customer));
        });
      });
      break;
  }
});
```

这样，用户看到的就不再是 Swing 对话框，而是使用 webforJ 组件构建的现代 Web 表单。

## 步骤 3：双向通信 {#step-3-bidirectional-communication}

当通信能够双向流动时，这种集成才真正发挥威力。webforJ 应用可以向 Swing 应用发送更新，使两个 UI 保持同步。

### 向 Swing 发送更新 {#sending-updates-to-swing}

用户在 webforJ 对话框中编辑客户信息后：

```java
dialog.onSave(() -> {
  // Send updated customer back to Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### 在 Swing 中处理更新 {#processing-updates-in-swing}

Swing 应用会监听这些更新并刷新显示内容：

```java
private void setupWebswingListeners() {
  api.addBrowserActionListener(event -> {
    if ("update-customer".equals(event.getActionName())) {
      Customer updated = gson.fromJson(event.getData(), Customer.class);
      updateCustomer(updated);
    }
  });
}
```

## 架构优势 {#architecture-benefits}

与完全重写相比，这种方法有几个优势：

### 立即部署到 Web {#immediate-web-deployment}

无需修改代码，Swing 应用即可立即支持 Web 访问。你可以在进行功能增强的同时，让用户通过浏览器访问应用。

### 渐进式增强 {#progressive-enhancement}

可以先只替换编辑对话框，然后逐步替换更多组件：

1. **阶段 1**：嵌入整个 Swing 应用，只替换编辑对话框
2. **阶段 2**：在嵌入的应用周围添加 webforJ 导航和菜单
3. **阶段 3**：将表格替换为 webforJ 表格，同时保留 Swing 中无法替代的功能
4. **阶段 4**：最终替换所有 Swing 组件

### 降低风险 {#risk-mitigation}

由于原始 Swing 应用仍可正常运行，你可以：

- 必要时回退到桌面部署
- 在现有功能旁测试新功能
- 逐步迁移用户
- 保留相同的业务逻辑
