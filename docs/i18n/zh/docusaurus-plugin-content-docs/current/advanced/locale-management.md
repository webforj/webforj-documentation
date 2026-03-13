---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: cfcad51aaedd77b781334fc048b0a4f1
---
# 本地化管理 <DocChip chip='since' label='25.10' />

webforJ 提供了内置支持来管理应用程序的语言环境。语言环境决定了整个应用程序中使用的语言和区域格式。组件可以通过 `LocaleObserver` 接口对语言环境的变化做出反应，从而在用户切换语言时立即更新 UI。

## 设置默认语言环境 {#setting-the-default-locale}

应用程序的语言环境可以通过 `webforj.locale` 属性进行配置。此属性设置应用程序启动时使用的语言环境，影响所有与语言环境相关的格式和文本。当未配置 `webforj.locale` 时，应用程序将回退到服务器的 JVM 默认语言环境。您可以随时通过 `App.getLocale()` 读取当前语言环境。

请参阅 [Configuration](/docs/configuration/properties) 部分了解如何为不同环境设置属性。

## 更改语言环境 {#changing-the-locale}

要在运行时更改语言环境，请调用 `App.setLocale()`。这将更新整个应用程序的语言环境，并通知所有实现了 `LocaleObserver` 的组件，允许 UI 在不重新加载页面的情况下更新。

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## 浏览器语言环境检测 <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

当启用自动检测时，webforJ 在启动时读取浏览器的首选语言，并将应用程序的语言环境设置为配置支持的语言环境中的最佳匹配。如果没有找到匹配项，则使用第一个支持的语言环境作为默认值。

通过将 `webforj.i18n.auto-detect` 设置为 `true` 并配置 `webforj.i18n.supported-locales` 為您的应用程序支持的语言环境来启用自动检测。请参阅 [Configuration](/docs/configuration/properties) 部分了解如何为不同环境设置属性。

:::info 需要支持的语言环境
自动检测需要配置 `supported-locales`。如果列表为空，则自动检测无效，应用程序使用 `webforj.locale` 的默认语言环境。
:::

## `LocaleObserver` 接口 {#the-localeobserver-interface}

需要在语言环境变化时更新内容的组件应实现 `LocaleObserver` 接口。webforJ 在组件创建和销毁时自动注册和注销观察者。

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

当语言环境变化时，`onLocaleChange` 会被调用并传入新的语言环境。在此方法内部，更新任何与语言环境相关的文本或格式：

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private final AppLayout self = getBoundComponent();
  private AppNavItem inboxItem;
  private AppNavItem outboxItem;

  public MainLayout() {
    inboxItem = new AppNavItem(t("menu.inbox"), InboxView.class, TablerIcon.create("inbox"));
    outboxItem = new AppNavItem(t("menu.outbox"), OutboxView.class, TablerIcon.create("send-2"));

    AppNav appNav = new AppNav();
    appNav.addItem(inboxItem);
    appNav.addItem(outboxItem);

    self.addToDrawer(appNav);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    inboxItem.setText(t("menu.inbox"));
    outboxItem.setText(t("menu.outbox"));
  }
}
```

:::tip 内置翻译支持
从版本 25.12 开始，webforJ 提供了内置的 [翻译系统](/docs/advanced/i18n-localization)，支持资源包、自定义解析器、自动浏览器语言环境检测和了解语言环境的数据绑定。
:::

### `LocaleEvent` {#localeevent}

传递给 `onLocaleChange()` 的 `LocaleEvent` 提供了新的语言环境和接收事件的组件：

| 方法 | 返回值 | 描述 |
|------|--------|------|
| `getLocale()` | `Locale` | 设置的新语言环境 |
| `getSource()` | `Object` | 接收事件的组件 |

## 手动语言环境更新 {#manual-locale-updates}

并非所有内容都会自动响应语言环境的变化。一些组件，如 [Masked Fields](/docs/components/fields/masked/overview)，在创建时读取一次 `App.getLocale()` 来配置与语言环境相关的格式，但不实现 `LocaleObserver`。当运行时语言环境变化时，这些组件需要在您的 `onLocaleChange()` 处理程序内部显式更新：

```java
public class OrderForm extends Composite<FlexLayout> implements LocaleObserver {
  private MaskedDateField dateField = new MaskedDateField("日期");
  private MaskedTimeField timeField = new MaskedTimeField("时间");

  @Override
  public void onLocaleChange(LocaleEvent event) {
    Locale newLocale = event.getLocale();
    dateField.setLocale(newLocale);
    timeField.setLocale(newLocale);
  }
}
```

:::tip 数据绑定
`BindingContext` 支持与语言环境相关的验证和转换消息。请参阅 [动态验证消息](/docs/data-binding/validation/validators#dynamic-validation-messages) 和 [与语言环境相关的 Jakarta 验证](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages)。
:::
