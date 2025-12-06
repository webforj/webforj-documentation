---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 97df9e800c5792a1ff22fb6e0e9a33e9
---
本教程通过使用 `WebswingConnector` 将现有的 Java Swing 应用程序现代化，使其与 webforJ 集成。您将学习如何使传统的桌面应用程序可以通过网络访问，并逐步添加现代 web 功能，例如基于 web 的对话框和使用 webforJ 组件的交互表单。

:::note 先决条件
在开始本教程之前，请完成 [设置和配置](./setup) 步骤，以配置您的 Webswing 服务器和 CORS 设置。
:::

:::tip 源代码
本教程的完整源代码可在 GitHub 上获得：[webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## 场景 {#the-scenario}

想象一下，您有一个基于 Swing 的客户管理应用程序，已经投入生产多年。它运行良好，但用户现在期望能够通过网络访问并拥有现代界面。与其从头重写，不如立即使用 Webswing 使其可通过网络访问，然后逐步添加现代 web 功能，例如基于 web 的对话框和使用 webforJ 组件的表单。

## 起点：Swing 应用 {#starting-point-the-swing-app}

示例 Swing 应用是一个客户表格，具有典型的 CRUD 操作。就像许多企业 Swing 应用一样，它遵循标准模式：

```java
public class Application {
  private List<Customer> customers;
  private DefaultTableModel model;
  private JTable table;

  private void createTable() {
    String[] columnNames = { "姓名", "公司", "电子邮件" };
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
          // 处理双击以编辑
        }
      }
    });
  }

  private void showEditDialog(Customer customer) {
    JTextField nameField = new JTextField(customer.getName());
    JTextField companyField = new JTextField(customer.getCompany());
    JTextField emailField = new JTextField(customer.getEmail());

    Object[] fields = {
        "姓名:", nameField,
        "公司:", companyField,
        "电子邮件:", emailField
    };

    int result = JOptionPane.showConfirmDialog(null, fields, "编辑客户",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
  }
}
```

这个应用程序作为桌面应用程序运行良好，但缺乏网络可访问性。用户必须安装 Java 并在本地运行 JAR 文件。

## 第一步：使其支持 Webswing {#step-1-making-it-webswing-aware}

第一步是使 Swing 应用能够检测它是否在 Webswing 下运行。这使得它能够改变行为，而不破坏桌面兼容性。

### 检测 Webswing 环境 {#detecting-the-webswing-environment}

将 Webswing API 依赖项添加到您的 Swing 项目中：

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

然后修改您的应用以检测 Webswing 运行时：

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

这里的关键点是 `WebswingUtil.getWebswingApi()` 在作为普通桌面应用运行时返回 `null`，这使您能够保持双模式兼容性。

### 为 web 部署调整行为 {#adapting-behavior-for-web-deployment}

有了检测后，您可以调整应用的行为。最重要的改变是用户交互的处理方式：

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

通过根据 `isWebswing` 的值分支行为，代码库可以处理两种环境。

## 第二步：创建 webforJ 包装器 {#step-2-creating-the-webforj-wrapper}

现在 Swing 应用可以通过事件进行通信，创建一个 webforJ 应用，该应用嵌入 Swing 应用并添加现代 web 功能，例如基于 web 的对话框和表单。

### 设置连接器 {#setting-up-the-connector}

`WebswingConnector` 组件在 webforJ 视图中嵌入您的 Webswing 托管应用：

```java
@Route("/")
public class CustomerTableView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public CustomerTableView(@Value("${webswing.connector.url}") String webswingUrl) {
    WebswingConnector connector = new WebswingConnector(webswingUrl);
    connector.setSize("100vw", "100vh");

    self.add(connector);
  }
}
```

连接器连接到您的 Webswing 服务器，建立双向通信通道。

### 处理来自 Swing 的事件 {#handling-events-from-swing}

当 Swing 应用发送事件（例如，当用户双击一行时），连接器会接收这些事件：

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

现在，用户看到的不是 Swing 对话框，而是一个使用 webforJ 组件构建的现代 web 表单。

## 第三步：双向通信 {#step-3-bidirectional-communication}

当通信双向流动时，集成就变得强大。webforJ 应用可以将更新发送回 Swing 应用，保持两个用户界面的同步。

### 向 Swing 发送更新 {#sending-updates-to-swing}

在用户编辑 webforJ 对话框中的客户后：

```java
dialog.onSave(() -> {
  // 将更新的客户发送回 Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### 在 Swing 中处理更新 {#processing-updates-in-swing}

Swing 应用监听这些更新并刷新其显示：

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

这种方法相比完全重写提供了几种优势：

### 立即的 web 部署 {#immediate-web-deployment}

您的 Swing 应用立即变得可通过网络访问，而无需代码更改。用户可以通过浏览器访问它，同时您继续工作于增强功能。

### 渐进增强 {#progressive-enhancement}

开始时仅替换编辑对话框，然后逐步替换更多组件：

1. **第一阶段**：嵌入整个 Swing 应用，仅替换编辑对话框
2. **第二阶段**：在嵌入的应用周围添加 webforJ 导航和菜单
3. **第三阶段**：用 webforJ 表格替换 Swing 表格，保留 Swing 用于不可替换的功能
4. **第四阶段**：最终替换所有 Swing 组件

### 风险缓解 {#risk-mitigation}

由于原始 Swing 应用仍然可用，因此您可以：

- 如有需要，回退到桌面部署
- 并行测试新功能和现有功能
- 渐进式迁移用户
- 保持相同的业务逻辑
