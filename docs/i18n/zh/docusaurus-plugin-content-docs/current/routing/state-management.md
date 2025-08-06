---
sidebar_position: 7
title: State Management
_i18n_hash: cba905abd01a780dea1f459ec4397cda
---
创建无缝的动态用户体验通常需要将您的Web应用程序的状态反映在URL中，并在浏览器导航事件中保留该状态。您可以通过利用URL参数更新和浏览器历史状态管理来实现这一点，而无需重新加载页面。这确保用户可以共享、书签或返回特定视图，而应用程序能够完全了解他们之前的交互。

## 更新URL {#updating-the-url}

当网页的状态发生变化时，例如过滤产品列表或在不同视图之间导航，您通常需要URL反映这些变化。您可以使用`replaceState`或`pushState`方法，这些方法由`BrowserHistory`类提供，用于在不重新加载页面的情况下操作URL：

- **`pushState`**：在不重新加载页面的情况下向浏览器的历史记录中添加新条目。这对于在不同视图或动态内容之间导航非常有用。
- **`replaceState`**：在不添加新条目的情况下更新浏览器历史中的当前条目。这对于在同一视图内更新状态非常理想。

### 示例：使用查询参数更新URL {#example-updating-the-url-with-query-parameters}

在此示例中，当单击“更新URL”按钮时，UI将更新以显示所选类别和排序，并且URL将更新为新的查询参数`category`和`sort`：

```java
@Route(value = "products")
public class ProductView extends Composite<Div> {
  Paragraph paragraph = new Paragraph();
  Random random = new Random();

  public ProductView() {
    Button update = new Button("Update URL", ButtonTheme.PRIMARY);
    update.onClick(ev -> {
      filter("electronics", String.valueOf(random.nextInt(3) - 1));
    });

    Div div = getBoundComponent();
    div.add(update);
    div.add(paragraph);
  }

  public void filter(String category, String sort) {
    // 更新UI
    updateUI(category, sort);

    // 更新URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("当前类别： " + category + "，排序方式： " + sort);
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // 无需重新加载页面更新URL
        .replaceState(null, newLocation);
  }
}
```

### 说明： {#explanation}

- **`filter` 方法**：该方法根据选定的`category`和`sort`来处理UI和URL的更新。
- **`updateUrl` 方法**：该方法为查询参数创建一个新的`ParametersBag`，构造一个新的URL，然后使用`replaceState`更新浏览器的URL，而无需重新加载页面。
- **`replaceState`**：该方法将URL更改为新位置，同时保持当前状态，而不会导致页面重新加载。

## 在浏览器历史中保存和恢复状态 {#saving-and-restoring-state-in-browser-history}

除了更新URL，还可以在浏览器的历史中保存任意状态对象。这意味着您可以存储与当前视图相关的额外数据（例如：表单输入、过滤器等），而无需将其直接嵌入到URL中。

### 示例：保存选择状态 {#example-saving-selection-state}

在以下示例中，`ProfileView`由几个选项卡（个人资料、订单和设置）组成。当用户在选项卡之间切换时，选定选项卡的状态通过`replaceState`保存到浏览器的历史中。这允许应用程序在用户返回此视图或刷新页面时记住最后一个活动的选项卡。

```java
@Route(value = "profile")
public class ProfileView extends Composite<Div> implements DidEnterObserver {
  TabbedPane sections = new TabbedPane();
  int currentSection = 0;

  public ProfileView() {
    sections.addTab("Profile");
    sections.addTab("Orders");
    sections.addTab("Settings");

    sections.onSelect(ev -> {
      currentSection = ev.getTabIndex();
      // 使用replaceState保存状态
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // 尝试从浏览器历史状态中检索最后保存的部分
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // 如果保存了部分，则恢复选项卡选择
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // 使用选定部分更新当前状态
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### 说明： {#explanation-1}

1. **TabbedPane 组件**：视图由一个`TabbedPane`组件组成，其中有三个选项卡：个人资料、订单和设置。
2. **选项卡更改时的状态保存**：每当选择一个选项卡时，当前部分索引会通过`replaceState`方法保存在浏览器的历史中。
3. **导航时恢复状态**：当用户返回`ProfileView`时，应用程序使用`event.getState()`从历史中检索保存的部分，并恢复正确的选项卡选择。
