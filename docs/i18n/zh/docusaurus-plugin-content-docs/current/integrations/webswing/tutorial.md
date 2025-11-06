---
title: Modernization Tutorial
sidebar_position: 4
_i18n_hash: d4f256ba28ac621f2280bbd31575f6f1
---
本教程将指导您通过使用`WebswingConnector`与webforJ集成来现代化现有的Java Swing应用程序。您将学习如何将传统桌面应用程序变为可通过网络访问的应用，并逐步添加现代网络功能，例如基于 web 的对话框和使用 webforJ 组件的交互表单。

:::tip 源代码
本教程的完整源代码可在GitHub上找到：[webforj/webforj-webswing-integration-tutorial](https://github.com/webforj/webforj-webswing-integration-tutorial)
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/tutorials/webswing/modernization-tutorial.mp4#t=5" type="video/mp4"/>
  </video>
</div>

## 场景

想象一下，您有一个使用Swing构建的客户管理应用程序，该应用程序已在生产中运行多年。它工作良好，但用户现在希望能够通过网络访问并享有现代接口。与其从头开始重写，不如利用Webswing立即使其可通过网络访问，然后逐步添加现代网络功能，例如基于web的对话框和使用webforJ组件的表单。

## 起始点：Swing应用程序

示例Swing应用程序是一个包含典型CRUD操作的客户表。像许多企业Swing应用程序一样，它遵循标准模式：

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

该应用程序作为桌面应用程序运行良好，但缺乏网络访问功能。用户必须安装Java并在本地运行JAR文件。

## 第一步：使其支持Webswing

第一步是使Swing应用程序检测是否在Webswing下运行。这使它能够在不破坏桌面兼容性的情况下调整其行为。

### 检测Webswing环境

将Webswing API依赖项添加到您的Swing项目中：

```xml
<dependency>
  <groupId>org.webswing</groupId>
  <artifactId>webswing-api</artifactId>
  <version>25.1</version>
</dependency>
```

然后修改您的应用程序以检测Webswing运行时：

```java
private void initWebswing() {
  api = WebswingUtil.getWebswingApi();
  isWebswing = api != null;

  if (isWebswing) {
    setupWebswingListeners();
  }
}
```

这里的关键见解是`WebswingUtil.getWebswingApi()`在作为常规桌面应用程序运行时返回`null`，从而允许您保持双模式兼容性。

### 为网络部署调整行为

有了检测机制，您现在可以调整应用程序的行为。最重要的变化是用户交互的处理方式：

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

通过根据`isWebswing`的值分支行为，代码可以处理两种环境。

## 第二步：创建webforJ包装器

现在Swing应用程序可以通过事件进行通信，创建一个webforJ应用程序，将Swing应用程序嵌入其中，并添加现代网页功能，例如基于web的对话框和表单。

### 设置连接器

`WebswingConnector`组件在webforJ视图中嵌入您托管在Webswing上的应用程序：

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

该连接器连接到您的Webswing服务器，建立双向通信通道。

### 处理Swing发送的事件

当Swing应用程序发送事件（例如，当用户双击一行时），连接器接收到这些事件：

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

现在，用户看到的是使用webforJ组件构建的现代网页表单，而不是Swing对话框。

## 第三步：双向通信

当通信双向流动时，集成变得强大。webforJ应用程序可以将更新发送回Swing应用程序，保持两个用户界面的同步。

### 将更新发送到Swing

用户在webforJ对话框中编辑客户后：

```java
dialog.onSave(() -> {
  // 将更新的客户发送回Swing
  connector.performAction("update-customer", gson.toJson(customer));
});
```

### 在Swing中处理更新

Swing应用程序监听这些更新并刷新其显示：

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

这种方法提供了比完全重写的多种优势：

### 立即网络部署

您的Swing应用程序可以立即通过网络访问，而无需代码更改。用户可以通过浏览器访问它，同时您进行增强。

### 渐进增强

首先替换只有编辑对话框，然后逐渐替换更多组件：

1. **阶段 1**：嵌入整个Swing应用程序，仅替换编辑对话框
2. **阶段 2**：在嵌入的应用程序周围添加webforJ导航和菜单
3. **阶段 3**：用webforJ表替换表格，保留Swing用于不可替代的功能
4. **阶段 4**：最终替换所有Swing组件

### 风险缓解

由于原始的Swing应用程序保持功能，您可以：

- 如有需要，回退到桌面部署
- 与现有功能一起测试新功能
- 渐进式迁移用户
- 保持相同的业务逻辑
