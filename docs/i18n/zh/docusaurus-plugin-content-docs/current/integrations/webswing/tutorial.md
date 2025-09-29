---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: 32805132a2cf7b320864275fbbae7889
---
本教程介绍了如何通过将现有的 Java Swing 应用集成到 webforJ 中，通过 `WebswingConnector` 现代化。您将学习如何使传统桌面应用程序可通过网络访问，并逐步添加现代网页功能，比如基于网页的对话框和交互式表单，使用 webforJ 组件。

:::tip 源代码
本教程的完整源代码可在 GitHub 上获得：[webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

## 场景

设想您有一个使用 Swing 构建的客户管理应用程序，它已投入生产多年。它运行良好，但用户现在期望能够通过网络访问并拥有现代界面。与其从头开始重写，不如直接使用 Webswing 使其立即可通过网络访问，然后逐步添加现代网页功能，如基于网页的对话框和表单，使用 webforJ 组件。

## 起点：Swing 应用程序

示例 Swing 应用程序是一个客户表格，具有典型的 CRUD 操作。像许多企业 Swing 应用程序一样，它遵循标准模式：

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
          // 处理双击以进行编辑
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

此应用程序作为桌面应用完全正常工作，但缺乏网络可访问性。用户必须安装 Java 并在本地运行 JAR 文件。

## 步骤 1：使其具备 Webswing 感知能力

第一步是使 Swing 应用能够检测它是否在 Webswing 下运行。这使得它能够在不破坏桌面兼容性的情况下调整其行为。

### 检测 Webswing 环境

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

这里的关键点是 `WebswingUtil.getWebswingApi()` 在作为常规桌面应用运行时返回 `null`，从而使您能够保持双模式兼容性。

### 为网络部署调整行为

有了检测机制后，您现在可以调整应用的行为。最重要的变化是如何处理用户交互：

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

## 步骤 2：创建 webforJ 包装器

现在 Swing 应用可以通过事件进行通信，创建一个 webforJ 应用，它嵌入 Swing 应用并添加现代网页功能，如基于网页的对话框和表单。

### 设置连接器

`WebswingConnector` 组件将在 webforJ 视图中嵌入您托管在 Webswing 中的应用：

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

### 处理来自 Swing 的事件

当 Swing 应用发送事件（如用户双击一行时），连接器会接收它们：

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

现在，用户看到的是使用 webforJ 组件构建的现代网页表单，而不是 Swing 对话框。

## 步骤 3：双向通信

当通信双向流动时，集成变得强大。webforJ 应用可以将更新发送回 Swing 应用，保持两个用户界面的同步。

### 向 Swing 发送更新

在用户在 webforJ 对话框中编辑客户后：

```java
dialog.onSave(() -> {
  // 将更新后的客户发送回 Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### 在 Swing 中处理更新

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

## 架构优势

这种方法相对于完全重写提供了几项优点：

### 立即的网络部署

您的 Swing 应用立即变得可以通过网络访问，无需代码更改。用户可以通过浏览器访问它，而您工作在增强功能上。

### 渐进增强

首先替换仅编辑对话框，然后逐步替换更多组件：

1. **第 1 阶段**：嵌入整个 Swing 应用，仅替换编辑对话框
2. **第 2 阶段**：在嵌入的应用周围添加 webforJ 导航和菜单
3. **第 3 阶段**：用 webforJ 表替换表格，保留 Swing 以处理不可替代的功能
4. **第 4 阶段**：最终替换所有 Swing 组件

### 风险缓解

由于原始的 Swing 应用保持功能，您可以：

- 如有需要可退回到桌面部署
- 测试新功能与现有功能并行
- 逐步迁移用户
- 维护相同的业务逻辑
