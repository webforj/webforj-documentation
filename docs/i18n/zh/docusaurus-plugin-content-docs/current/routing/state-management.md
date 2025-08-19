---
sidebar_position: 7
title: State Management
_i18n_hash: e10d155e02722ea38419a79813a2f5af
---
创建无缝且动态的用户体验通常要求将您的网络应用的状态反映在 URL 中，并在浏览器导航事件之间保留这些状态。您可以通过利用 URL 参数更新和浏览器历史状态管理来实现这一点，而无需重新加载页面。这确保用户可以分享、书签或返回特定视图，应用程序完全了解他们之前的交互。

## 更新 URL {#updating-the-url}

当网页的状态发生变化时，例如过滤产品列表或在不同视图之间导航，您通常需要 URL 反映这些变化。您可以使用 `BrowserHistory` 类提供的 `replaceState` 或 `pushState` 方法来在不重新加载页面的情况下操作 URL：

- **`pushState`**：在不重新加载页面的情况下向浏览器的历史记录堆栈添加一个新条目。这对于在不同视图或动态内容之间导航很有用。
- **`replaceState`**：在不添加新条目的情况下更新浏览器历史记录中的当前条目。这对于在同一视图内更新状态非常理想。

### 示例：使用查询参数更新 URL {#example-updating-the-url-with-query-parameters}

在此示例中，当点击“更新 URL”按钮时，UI 会更新以显示所选类别和排序，URL 则会使用新的查询参数 `category` 和 `sort` 进行更新：

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
    // 更新 UI
    updateUI(category, sort);

    // 更新 URL
    updateUrl(category, sort);
  }

  private void updateUI(String category, String sort) {
    paragraph.setText("当前查看类别: " + category + "，并按: " + sort + " 排序");
  }

  private void updateUrl(String category, String sort) {
    ParametersBag queryParameters = new ParametersBag();
    queryParameters.put("category", category);
    queryParameters.put("sort", sort);

    Location newLocation = new Location("/products?" + queryParameters.getQueryString());
    Router.getCurrent().getHistory()
        // 在不重新加载页面的情况下更新 URL
        .replaceState(null, newLocation);
  }
}
```

### 说明: {#explanation}

- **`filter` 方法**：该方法处理根据选定的 `category` 和 `sort` 更新 UI 和 URL。
- **`updateUrl` 方法**：此方法为查询参数创建一个新的 `ParametersBag`，构造一个新的 URL，然后使用 `replaceState` 更新浏览器的 URL，而无需重新加载页面。
- **`replaceState`**：此方法在不导致页面重新加载的情况下，将 URL 更改为新位置，同时保持当前状态。

## 在浏览器历史中保存和恢复状态 {#saving-and-restoring-state-in-browser-history}

除了更新 URL 外，还可以在浏览器的历史中保存任意状态对象。这意味着您可以存储与当前视图相关的额外数据（例如: 表单输入、过滤器等），而无需将它们直接嵌入到 URL 中。

### 示例：保存选择状态 {#example-saving-selection-state}

在以下示例中，`ProfileView` 包含几个选项卡（个人资料、订单和设置）。当用户在选项卡之间切换时，所选选项卡的状态将使用 `replaceState` 保存在浏览器的历史中。这使得当用户返回到此视图或刷新页面时，应用可以记住最后活动的选项卡。

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
      // 使用 replaceState 保存状态
      updateState(currentSection);
    });

    getBoundComponent().add(sections);
  }

  @Override
  public void onDidEnter(DidEnterEvent event, ParametersBag parameters) {
    // 尝试从浏览器历史状态中检索最后保存的部分
    Optional<Integer> lastSavedSection = event.getState(Integer.class);

    // 如果部分已保存，则恢复选项卡选择
    lastSavedSection.ifPresent(section -> sections.select(section));
  }

  private void updateState(int section) {
    Router router = Router.getCurrent();
    Location currentLocation = router.getHistory().getLocation().get();

    // 使用所选部分更新当前状态
    Router.getCurrent().getHistory()
        .replaceState(section, currentLocation);
  }
}
```

### 说明: {#explanation-1}

1. **TabbedPane 组件**：该视图由一个 `TabbedPane` 组件组成，其中有三个选项卡：个人资料、订单和设置。
2. **选项卡更改时状态保存**：每当选择一个选项卡时，当前部分索引会使用 `replaceState` 方法保存在浏览器的历史中。
3. **导航时恢复状态**：当用户返回到 `ProfileView` 时，应用会使用 `event.getState()` 从历史中检索保存的部分，并恢复正确的选项卡选择。
