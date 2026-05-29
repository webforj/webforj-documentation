---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: d3dcb4b1ded50923232cb33225364239
---
# 区域管理 <DocChip chip='since' label='25.10' />

webforJ 提供内置支持来管理应用程序的区域设置。区域设置决定了整个应用使用的语言和区域格式。组件可以通过 `LocaleObserver` 接口对区域设置的变化做出反应，从而在用户切换语言时立即更新 UI。

## 设置默认区域 {#setting-the-default-locale}

应用程序的区域设置可以使用 `webforj.locale` 属性进行配置。这设置了应用程序从启动时使用的区域设置，影响所有与区域设置相关的格式和文本。当 `webforj.locale` 未配置时，应用程序将回退到服务器的 JVM 默认区域设置。您可以随时使用 `App.getLocale()` 读取当前区域设置。

请参见 [配置](/docs/configuration/properties) 部分以了解如何为不同环境设置属性。

## 更改区域设置 {#changing-the-locale}

要在运行时更改区域设置，请调用 `App.setLocale()`。这会更新整个应用程序的区域设置，并通知所有实现 `LocaleObserver` 的组件，允许 UI 在不重新加载页面的情况下更新。

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## 浏览器区域检测 <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

当启用自动检测时，webforJ 会在启动时读取浏览器的首选语言，并将应用程序的区域设置设置为配置的支持区域设置中最匹配的。如未找到匹配项，则使用第一个支持的区域设置作为默认。

通过将 `webforj.i18n.auto-detect` 设置为 `true`，并使用应用程序支持的区域设置配置 `webforj.i18n.supported-locales` 来启用自动检测。请参见 [配置](/docs/configuration/properties) 部分以了解如何为不同环境设置属性。

:::info 需要支持的区域设置
自动检测需要配置 `supported-locales`。如果列表为空，则自动检测无效，应用程序将使用 `webforj.locale` 中的默认区域设置。
:::

## `LocaleObserver` 接口 {#the-localeobserver-interface}

需要在区域设置更改时更新其内容的组件应实现 `LocaleObserver` 接口。webforJ 会在组件创建和销毁时自动注册和注销观察者。

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
  void onLocaleChange(LocaleEvent event);
}
```

当区域设置更改时，会调用 `onLocaleChange`，并传入新的区域设置。在此方法内部，更新任何与区域设置相关的文本或格式：

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
从版本 25.12 开始，webforJ 提供内置的 [翻译系统](/docs/advanced/i18n-localization)，支持资源包、自定义解析器、自动浏览器区域检测和区域感知的数据绑定。
:::

### `LocaleEvent` {#localeevent}

传递给 `onLocaleChange()` 的 `LocaleEvent` 提供了新的区域设置和接收事件的组件：

| 方法 | 返回 | 描述 |
|--------|---------|-------------|
| `getLocale()` | `Locale` | 设置的新区域设置 |
| `getSource()` | `Object` | 接收事件的组件 |

## 手动更新区域设置 {#manual-locale-updates}

并非所有内容都能自动对区域设置更改作出反应。一些组件，如 [Masked Fields](/docs/components/fields/masked/overview)，在创建期间读取 `App.getLocale()` 一次以配置与区域设置相关的格式，但不实现 `LocaleObserver`。当运行时区域设置变化时，这些组件需要在您的 `onLocaleChange()` 处理程序中显式更新：

```java
public class OrderForm extends Composite<FlexLayout> implements LocaleObserver {
  private MaskedDateField dateField = new MaskedDateField("Date");
  private MaskedTimeField timeField = new MaskedTimeField("Time");

  @Override
  public void onLocaleChange(LocaleEvent event) {
    Locale newLocale = event.getLocale();
    dateField.setLocale(newLocale);
    timeField.setLocale(newLocale);
  }
}
```

:::tip 数据绑定
`BindingContext` 支持与区域感知的验证和转换消息。请参见 [动态验证消息](/docs/data-binding/validation/validators#dynamic-validation-messages) 和 [区域感知的 Jakarta 验证](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages)。
:::
