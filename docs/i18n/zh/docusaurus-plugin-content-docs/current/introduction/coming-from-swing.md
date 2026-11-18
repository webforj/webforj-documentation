---
title: 从 Swing 转来
sidebar_position: 5
description: >-
  将 Java Swing 概念（如 JFrame、布局管理器、监听器、事件分发线程、表格模型、JOptionPane 和 CardLayout）映射到
  webforJ 中的对应实现。
_i18n_title_hash: 4334c9ef532372ad4ec7040646346991
_i18n_description_hash: bb61f39a493a0bd89b7e48af68c639d2
_i18n_hash: a55f88307cad0553b509d1cbb9c3a3f3
---
本页将 Java Swing 桌面应用的概念与其在 webforJ 中的对应概念进行映射。在这两种框架中，UI 都是纯 Java：组件是 Java 对象，事件由监听器处理，布局通过代码组合而成。webforJ 在服务器上运行，框架会让浏览器与组件状态保持同步。有关其工作原理，请参阅[组件架构](/docs/architecture/controls-components)。

webforJ API 与 Swing 类似，但两者并不兼容。每个屏幕都需要针对 webforJ API 重新编写，而不依赖 `javax.swing` 或 `java.awt` 的类（例如服务、实体和验证规则）则可以原样用于新视图。

## 窗口和框架 {#windows-and-frames}

Swing 应用会创建一个 `JFrame`，添加内容，然后显示它。在 webforJ 中，浏览器页面就是窗口。应用类继承 `App`，框架会提供一个用于容纳可见内容的 `Frame`。启用 `@Routify` 路由后，每个屏幕都是一个带有 `@Route` 注解的类，由路由器将其渲染到框架中。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| `JFrame` | `App` 和 `Frame`，屏幕使用 `@Route` 类 | [可路由应用](/docs/routing/routable-apps)、[路由](/docs/routing/overview) |
| `JFrame.setTitle` | `@FrameTitle` 或 `HasFrameTitle` | [导航框架标题](/docs/routing/navigational-frame-titles) |
| 用于构建 UI 的主方法 | 带有 `@Routify` 的 `App` 子类 | [App 基础知识](/docs/introduction/basics) |

## 面板和布局管理器 {#panels-and-layout-managers}

带有布局管理器的 `JPanel` 可以对应为绑定到布局组件的 `Composite`。`FlexLayout` 可沿行或列排列子组件，并支持间距、对齐和换行，适用于大多数使用 `BoxLayout` 或 `FlowLayout` 构建的屏幕。使用 `BorderLayout` 的窗口外框对应 `AppLayout`，其他 `BorderLayout` 面板则对应嵌套的 `FlexLayout` 容器。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| `JPanel` 子类 | 绑定到 `FlexLayout` 或 `Div` 等容器的 `Composite<T>` | [组合组件](/docs/building-ui/composing-components) |
| `BoxLayout`、`FlowLayout` | `FlexLayout` | [FlexLayout](/docs/components/flex-layout) |
| `BorderLayout` | 窗口外框使用 `AppLayout`，其他面板使用嵌套的 `FlexLayout` 容器 | [AppLayout](/docs/components/app-layout)、[FlexLayout](/docs/components/flex-layout) |
| `GridLayout`、`GridBagLayout` 表单 | `ColumnsLayout` | [ColumnsLayout](/docs/components/columns-layout) |
| `JSplitPane` | `Splitter` | [Splitter](/docs/components/splitter) |
| `JTabbedPane` | `TabbedPane` | [TabbedPane](/docs/components/tabbedpane) |
| `JToolBar` | `Toolbar` | [Toolbar](/docs/components/toolbar) |
| 带有页眉、侧边菜单和内容区域的主窗口 | `AppLayout` | [AppLayout](/docs/components/app-layout) |

Swing 中包含文本字段和点击响应按钮的面板，在 webforJ 中可实现为以下视图：

```java
@Route("customers/new")
@FrameTitle("New customer")
public class NewCustomerView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final Button save = new Button("Save", ButtonTheme.PRIMARY);

  public NewCustomerView() {
    self.setDirection(FlexDirection.COLUMN).setSpacing("1em");
    save.onClick(e -> OptionDialog.showMessageDialog("Saved " + name.getValue(), "Customer"));
    self.add(name, save);
  }
}
```

## 组件 {#components}

大多数 Swing 组件都有直接对应项。文本组件使用 `setText()` 设置标题，输入组件使用 `getValue()` 和 `setValue()` 获取和设置内容，详见[使用组件](/docs/building-ui/using-components)。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| `JButton` | `Button` | [Button](/docs/components/button) |
| `JLabel` | `Label` | [使用组件](/docs/building-ui/using-components) |
| `JTextField` | `TextField` | [TextField](/docs/components/fields/textfield) |
| `JPasswordField` | `PasswordField` | [PasswordField](/docs/components/fields/passwordfield) |
| `JFormattedTextField` | `MaskedTextField` 和其他掩码字段 | [MaskedTextField](/docs/components/fields/masked/textfield) |
| `JTextArea` | `TextArea` | [TextArea](/docs/components/textarea) |
| `JCheckBox` | `CheckBox` | [CheckBox](/docs/components/checkbox) |
| `JRadioButton` 和 `ButtonGroup` | `RadioButton` 和 `RadioButtonGroup` | [RadioButton](/docs/components/radiobutton)、[RadioButtonGroup](/docs/components/radiobuttongroup) |
| `JComboBox` | 固定选项集使用 `ChoiceBox`，允许用户输入值时使用 `ComboBox` | [ChoiceBox](/docs/components/lists/choicebox)、[ComboBox](/docs/components/lists/combobox) |
| `JList` | `ListBox` | [ListBox](/docs/components/lists/listbox) |
| `JTree` | `Tree` | [Tree](/docs/components/tree) |
| `JProgressBar` | `ProgressBar` | [ProgressBar](/docs/components/progressbar) |
| `JSlider` | `Slider` | [Slider](/docs/components/slider) |

## 监听器和事件 {#listeners-and-events}

Swing 使用 `addXxxListener` 方法注册监听器。webforJ 组件采用相同的模式：提供 `addXxxListener` 方法，而且大多数情况下还提供更简短的 `on` 别名。每次注册都会返回一个 `ListenerRegistration`，调用它的 `remove()` 方法即可替代 Swing 的 `removeXxxListener`。请参阅[事件](/docs/building-ui/events)。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| 按钮上的 `ActionListener` | `onClick` 或 `addClickListener` | [Button](/docs/components/button) |
| 文本字段上的 `DocumentListener` | `onModify` | [事件](/docs/building-ui/events) |
| `JCheckBox` 上的 `ItemListener` | `onToggle` | [CheckBox](/docs/components/checkbox) |
| 值上的 `ChangeListener` 和属性更改监听器 | `onValueChange` | [事件](/docs/building-ui/events) |
| `FocusListener` | `onFocus` 和 `onBlur` | [事件](/docs/building-ui/events) |
| 字段上的 `KeyListener` | `onKeypress` | [事件](/docs/building-ui/events) |
| `removeXxxListener` | `ListenerRegistration.remove()` | [移除事件](/docs/building-ui/events#removing-an-event) |

## 事件分发线程和 UI 更新 {#event-dispatch-thread-and-ui-updates}

Swing 要求所有 UI 操作都在事件分发线程上执行。webforJ 也有相同的规则：每个用户会话都有一个 `Environment` 线程，所有 UI 更新都必须在该线程上执行。构造函数、事件处理程序和生命周期方法本来就在该线程上运行。后台线程必须通过 `Environment.runLater(Environment, Runnable)` 将 UI 更改交回该线程。

在后台任务开始之前，应在 `Environment` 线程上使用 `Environment.getCurrent()` 获取目标线程。在后台线程上调用 `getCurrent()` 会返回 `null`。有关线程模型以及通过 `PendingResult` 取消任务的信息，请参阅[异步更新](/docs/advanced/asynchronous-updates)。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| 事件分发线程 | 会话的 `Environment` 线程 | [异步更新](/docs/advanced/asynchronous-updates#understanding-the-thread-model) |
| `SwingUtilities.invokeLater` | `Environment.runLater(Environment, Runnable)` | [异步更新](/docs/advanced/asynchronous-updates#passing-the-target) |
| `SwingWorker` | 后台任务，例如使用 `CompletableFuture`，然后调用 `Environment.runLater` | [异步更新](/docs/advanced/asynchronous-updates) |
| `javax.swing.Timer` | `Interval` | [Interval](/docs/advanced/interval) |
| 每次按键时重新启动计时器 | `Debouncer` | [防抖](/docs/advanced/debouncing) |

`SwingWorker` 的 `doInBackground` 方法体会变为后台任务，`done` 方法则会变为 `runLater` 代码块：一个处理结果，另一个处理失败：

```java
public class ReportView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final CustomerService service = new CustomerService();
  private final Button export = new Button("Export");
  private final Label status = new Label();

  public ReportView() {
    self.add(export, status);
    export.onClick(e -> {
      Environment env = Environment.getCurrent();
      CompletableFuture.supplyAsync(service::exportReport)
          .thenAccept(path -> Environment.runLater(env, () -> { status.setText("Saved to " + path); }))
          .exceptionally(error -> {
            Environment.runLater(env, () -> { status.setText("Export failed"); });
            return null;
          });
    });
  }
}
```

## 模型和数据绑定 {#models-and-data-binding}

Swing 使用 `TableModel` 和 `ListModel` 等模型接口将数据与视图分离，并需要手动编写代码，在字段和对象之间复制值。webforJ 使用 `Repository` 作为 `Table` 等组件的数据源，并使用 `BindingContext` 双向连接表单字段和 Bean 属性。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| `TableModel` | `Repository`，例如 `CollectionRepository` | [Repository](/docs/advanced/repository/overview) |
| `ListModel` | 添加到 `ListBox`、`ChoiceBox` 或 `ComboBox` 的 `ListItem` 条目 | [列表](/docs/components/lists/overview) |
| `fireTableDataChanged`、`fireTableRowsUpdated` | 在存储库上调用 `commit()`` 和 `commit(entity)` | [数据同步](/docs/advanced/repository/overview#data-synchronization) |
| 在字段与对象之间复制值 | 使用 `read(bean)` 和 `write(bean)` 的 `BindingContext` | [数据绑定](/docs/data-binding/overview)、[自动绑定](/docs/data-binding/automatic-binding) |
| `InputVerifier` | 绑定上的验证器，或 Jakarta Validation | [验证](/docs/data-binding/validation/overview)、[Jakarta Validation](/docs/data-binding/validation/jakarta-validation) |

`BindingContext.of` 会将组件字段绑定到同名的 Bean 属性：

```java
public class CustomerForm extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final TextField name = new TextField("Name");
  private final TextField email = new TextField("Email");
  private final BindingContext<Customer> context;

  public CustomerForm(Customer customer) {
    self.setDirection(FlexDirection.COLUMN).add(name, email);
    context = BindingContext.of(this, Customer.class);
    context.read(customer);
  }

  public boolean save(Customer customer) {
    return context.write(customer).isValid();
  }
}
```

## 对话框 {#dialogs}

选项对话框是模态的，会像 `JOptionPane` 一样阻塞执行，直到用户作出响应。调用之后的代码可以直接读取结果，无需使用回调。请参阅[选项对话框](/docs/components/option-dialogs/overview)。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| `JOptionPane.showMessageDialog` | `OptionDialog.showMessageDialog` | [消息对话框](/docs/components/option-dialogs/message) |
| `JOptionPane.showConfirmDialog` | `OptionDialog.showConfirmDialog`，返回 `ConfirmDialog.Result` | [确认对话框](/docs/components/option-dialogs/confirm) |
| `JOptionPane.showInputDialog` | `OptionDialog.showInputDialog` | [输入对话框](/docs/components/option-dialogs/input) |
| 用于打开用户设备上文件的 `JFileChooser` | `OptionDialog.showFileUploadDialog` | [文件上传对话框](/docs/components/option-dialogs/file-upload) |
| 用于浏览服务器文件的 `JFileChooser` | `OptionDialog.showFileChooserDialog` | [文件选择器对话框](/docs/components/option-dialogs/file-chooser) |
| 带自定义内容的 `JDialog` | `Dialog`，使用 `open()` 打开并使用 `close()` 关闭 | [Dialog](/docs/components/dialog) |

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Delete this customer?", "Delete customer",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);

if (result == ConfirmDialog.Result.YES) {
  service.delete(customer);
}
```

## 表格 {#tables}

`JTable` 从 `TableModel` 子类读取数据。webforJ 的 `Table<T>` 保存实体列表，并将每列绑定到一个 getter，因此无需编写模型类。`setItems` 会将集合封装到存储库中，而 `setRepository` 则接受由你的数据层支持的存储库。请参阅[Table](/docs/components/table/overview)。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| 带有 `TableModel` 的 `JTable` | 使用 `setItems` 或 `setRepository` 的 `Table<T>` | [Table](/docs/components/table/overview) |
| `TableColumn` 和 `getValueAt` | `addColumn(id, getter)` | [列](/docs/components/table/columns) |
| `ListSelectionModel` | `setSelectionMode`、`getSelectedItems`、`onItemSelect` | [选择](/docs/components/table/selection) |
| 检测双击的 `MouseListener` | `onItemDoubleClick` | <JavadocLink type="table" location="com/webforj/component/table/Table" code="true">Table</JavadocLink> |
| `TableRowSorter` | `Column.setSortable(true)` | [排序](/docs/components/table/sorting) |
| `RowFilter` | 在 `CollectionRepository` 上调用 `commit()` 时应用的 `setBaseFilter(Predicate)` | [筛选](/docs/components/table/filtering) |
| `TableCellRenderer` | 使用 `Column.setRenderer` 设置的 `Renderer` | [渲染](/docs/components/table/rendering) |

```java
Table<Customer> table = new Table<>();
table.addColumn("Name", Customer::getName);
table.addColumn("Email", Customer::getEmail);
table.setSelectionMode(Table.SelectionMode.SINGLE);
table.setItems(service.findAll());
table.onItemDoubleClick(e -> confirmDelete(e.getItem()));
```

## 导航 {#navigation}

Swing 应用会通过替换面板来切换屏幕，通常使用 `CardLayout`，也可以打开新窗口。在 webforJ 中，每个屏幕都是一个拥有独立 URL 的路由，因此浏览器历史记录、书签和深层链接都能正常使用。请参阅[路由](/docs/routing/overview)。

| Swing | webforJ | 参考 |
| --- | --- | --- |
| `CardLayout.show` 或替换内容面板 | `@Route` 视图和 `Router.getCurrent().navigate(View.class)` | [定义路由](/docs/routing/defining-routes)、[路由导航](/docs/routing/route-navigation) |
| `JMenu` 或屏幕侧边列表 | 带有指向路由的 `AppNavItem` 条目的 `AppNav` | [AppNav](/docs/components/appnav) |
| 带参数打开屏幕 | 路由参数和查询参数 | [路由模式](/docs/routing/route-patterns)、[查询参数](/docs/routing/query-parameters) |

```java
Router.getCurrent().navigate(NewCustomerView.class);
```

## 逐个迁移屏幕 {#moving-screen-by-screen}

无需一次性重写整个 Swing 应用。Webswing 可以在浏览器中运行现有的 Swing、JavaFX 或 SWT 应用，而无需修改其源代码；`WebswingConnector` 组件则可将其嵌入 webforJ 视图中。只有双向消息通信需要对 Swing 应用进行少量修改，具体说明如下。这样，新旧屏幕可以同时存在于同一个应用中，并逐个完成迁移。

连接器支持双向通信。`performAction` 会将操作从 webforJ 发送到 Swing 应用，而 `onAction` 会接收 Swing 应用通过 Webswing Java API 发送的操作。要将操作发送回 webforJ，需要将该 API 添加到 Swing 应用中。

在 Swing 端，Webswing 仓库中的 `webswing-api` 依赖提供了 `WebswingUtil.getWebswingApi()`。如果应用作为常规桌面应用运行，该方法会返回 `null`，因此相同代码在桌面环境中仍然可用。以下示例中，双击表格行会将选中的客户发送到 webforJ，而不是打开 Swing 对话框：

```java
WebswingApi api = WebswingUtil.getWebswingApi();

if (api != null) {
  api.sendActionEvent("select-customer", gson.toJson(customer), null);
} else {
  showEditDialog(customer);
}
```

- [Webswing](/docs/integrations/webswing/overview)介绍其架构和渐进式现代化。
- [安装和配置](/docs/integrations/webswing/setup)介绍 Webswing 服务器和 CORS。
- [通信](/docs/integrations/webswing/communication)介绍 `performAction` 和 `onAction`。
- [现代化教程](/docs/integrations/webswing/tutorial)演示如何将 Swing 编辑对话框替换为 webforJ 表单，同时保留 Swing 表格。
