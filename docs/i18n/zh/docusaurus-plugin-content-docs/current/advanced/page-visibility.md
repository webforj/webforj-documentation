---
title: Page Visibility
sidebar_position: 32
sidebar_class_name: new-content
description: >-
  Detect when the tab hosting your app moves between the foreground and the
  background, and react in Java.
_i18n_hash: 8382d0314f6143663c03e11409de08d5
---
<DocChip chip='since' label='26.01' />
<JavadocLink type="foundation" location="com/webforj/Page" top='true'/>

`Page` 类可以在用户切换离开承载您应用的标签、最小化窗口或返回时进行识别。利用它在无人观看时暂停轮询和动画、控制通知，或在标签重新获得焦点时刷新过时数据。

API包含两个部分：

- 一个类型化查询，`getVisibilityState()`，返回当前状态。
- 一个监听器，`addVisibilityChangeListener(...)`，每次状态变化时触发。

## 可见性状态 {#visibility-states}

`PageVisibilityState` 有两个值：

| 状态 | 说明 |
| --- | --- |
| `VISIBLE` | 页面内容至少部分可见。标签在未最小化窗口的前景中。 |
| `HIDDEN` | 页面内容对用户不可见。标签在后台，窗口已最小化，屏幕被锁定，或者操作系统显示屏幕保护程序。 |

## 读取当前状态 {#reading-the-current-state}

`Page.getVisibilityState()` 返回一个 `PendingResult<PageVisibilityState>`，其解析为当前状态。

```java
Page.getCurrent().getVisibilityState().thenAccept(state -> {
  if (state == PageVisibilityState.VISIBLE) {
    // 用户正在查看标签
  }
});
```

在您需要一次性回答时调用它，例如当计划任务唤醒时。对于持续反应，请注册一个监听器。

## 监听变化 {#listening-for-changes}

`addVisibilityChangeListener(...)` 注册一个监听器，每次可见性状态变化时都会通知。匹配的别名是 `onVisibilityChange(...)`。

```java
ListenerRegistration<PageVisibilityChangeEvent> registration =
    Page.getCurrent().onVisibilityChange(event -> {
      if (event.isHidden()) {
        pauseRendering();
      } else {
        resumeRendering();
      }
    });
```

事件携带新状态和一些便利的访问器：

| 方法 | 返回值 |
| --- | --- |
| `getState()` | 新的 `PageVisibilityState`。 |
| `isVisible()` | 当新状态为 `VISIBLE` 时返回 `true`。 |
| `isHidden()` | 当新状态为 `HIDDEN` 时返回 `true`。 |
| `getPage()` | 产生该事件的 `Page`。 |

使用返回的 `ListenerRegistration` 移除单个监听器。

## 示例：仅在标签被隐藏时通知 {#example-notify-when-hidden}

一个常见的用例是根据用户是否当前查看标签来选择交付渠道。下面的代码片段在五秒后调度一个通知。如果计时器触发时标签被隐藏，它将生成一个桌面通知，并在 favicon 上绘制一个徽章。如果标签可见，则显示一个应用内吐司。

```java
@Route("/")
public class NotifyView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Button notifyButton = new Button("5秒后通知");
  private final Debouncer schedule = new Debouncer(5.0f);

  private ListenerRegistration<PageVisibilityChangeEvent> visibilityRegistration;
  private DesktopNotification activeNotification;

  public NotifyView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER);

    H1 title = new H1("页面可见性演示");
    Paragraph hint = new Paragraph(
        "点击按钮，然后在计时器结束之前切换到另一个标签。");

    notifyButton.setPrefixComponent(FeatherIcon.BELL.create())
        .setTheme(ButtonTheme.PRIMARY)
        .onClick(e -> schedule.run(this::deliver));

    self.add(title, hint, notifyButton);

    visibilityRegistration = Page.getCurrent().onVisibilityChange(this::onVisibility);
  }

  private void deliver() {
    Page page = Page.getCurrent();
    page.getVisibilityState().thenAccept(state -> {
      if (state == PageVisibilityState.HIDDEN) {
        page.setIconBadge(1);
        activeNotification = new DesktopNotification("页面可见性演示",
            "计时器触发时标签被隐藏。");
        activeNotification.open();
      } else {
        Toast.show("计时器在标签可见时触发。", Theme.SUCCESS);
      }
    });
  }

  private void onVisibility(PageVisibilityChangeEvent event) {
    if (event.isVisible() && activeNotification != null) {
      Page.getCurrent().setIconBadge(0);
      activeNotification.close();
      activeNotification = null;
    }
  }

  @Override
  protected void onDidDestroy() {
    schedule.cancel();
    if (visibilityRegistration != null) {
      visibilityRegistration.remove();
    }
  }
}
```

可见性监听器在用户返回标签时清除图标徽章并关闭桌面通知。

## 何时使用 {#when-to-use-it}

- **暂停后台工作。** 当页面被隐藏时停止轮询、定时任务和动画以节省带宽和CPU。再次变为可见时恢复它们。
- **控制通知。** 当用户可以看到标签时显示 `Toast`，而当他们看不到时显示 `DesktopNotification`。
- **在返回时刷新过时数据。** 当页面从 `HIDDEN` 返回时，决定是否已经足够时间重新获取数据。
- **跟踪参与度。** 当标签被隐藏时将会话标记为非活跃。
