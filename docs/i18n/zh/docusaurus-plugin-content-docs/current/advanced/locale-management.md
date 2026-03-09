---
sidebar_position: 11
title: Locale Management
sidebar_class_name: new-content
_i18n_hash: 066d555fcf006756c6ae0e542d409d77
---
# 语言环境管理 <DocChip chip='since' label='25.10' />

webforJ 提供内置支持用于管理应用的语言环境。语言环境决定了整个应用使用的语言和区域格式。组件可以通过 `LocaleObserver` 接口对语言环境的变化做出反应，允许在用户切换语言时立即更新 UI。

## 设置默认语言环境 {#setting-the-default-locale}

应用的语言环境可以通过 `webforj.locale` 属性进行配置。这将设置应用在启动时使用的语言环境，影响所有与语言环境相关的格式和文本。当 `webforj.locale` 未配置时，应用会退回到服务器的 JVM 默认语言环境。您可以随时使用 `App.getLocale()` 查看当前语言环境。

请参阅 [Configuration](/docs/configuration/properties) 部分了解如何为不同环境设置属性。

## 更改语言环境 {#changing-the-locale}

要在运行时更改语言环境，请调用 `App.setLocale()`。这将更新整个应用的语言环境，并通知所有实现了 `LocaleObserver` 的组件，允许在不刷新页面的情况下更新 UI。

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

## 浏览器语言环境检测 <DocChip chip='since' label='25.12' /> {#browser-locale-detection}

当启用自动检测时，webforJ 会在启动时读取浏览器的首选语言，并将应用的语言环境设置为与配置的支持语言中最匹配的一种。如果没有找到合适的匹配，则使用第一个支持的语言环境作为默认值。

通过将 `webforj.i18n.auto-detect` 设置为 `true` 并配置 `webforj.i18n.supported-locales` 以包括您的应用支持的语言环境，来启用自动检测。请参阅 [Configuration](/docs/configuration/properties) 部分了解如何为不同环境设置属性。

:::info 需要支持的语言环境
自动检测需要配置 `supported-locales`。如果列表为空，自动检测将无效，应用将使用 `webforj.locale` 中的默认语言环境。
:::

## `LocaleObserver` 接口 {#the-localeobserver-interface}

需要在语言环境更改时更新其内容的组件应实现 `LocaleObserver` 接口。webforJ 会在组件创建和销毁时自动注册和注销观察者。

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver {
    void onLocaleChange(LocaleEvent event);
}
```

当语言环境发生变化时，将调用 `onLocaleChange` 方法并传入新的语言环境。在此方法内，更新任何与语言环境相关的文本或格式：

```java title="MainLayout.java"
@Route
public class MainLayout extends Composite<AppLayout>
    implements HasTranslation, LocaleObserver {

  private AppLayout self = getBoundComponent();
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
从版本 25.12 开始，webforJ 提供内置的 [翻译系统](/docs/advanced/i18n-localization)，支持资源包、自定义解析器、自动浏览器语言环境检测和语言环境感知的数据绑定。
:::

### `LocaleEvent` {#localeevent}

传递给 `onLocaleChange()` 的 `LocaleEvent` 提供了新的语言环境和接收事件的组件：

| 方法 | 返回值 | 描述 |
|------|--------|------|
| `getLocale()` | `Locale` | 被设置的新语言环境 |
| `getSource()` | `Object` | 接收事件的组件 |

## 手动更新语言环境 {#manual-locale-updates}

并非所有组件都会自动对语言环境变化做出反应。一些组件，如 [Masked Fields](/docs/components/fields/masked/overview)，在创建时读取一次 `App.getLocale()` 以配置与语言环境相关的格式，但不实现 `LocaleObserver`。当运行时语言环境发生变化时，这些组件需要在 `onLocaleChange()` 处理程序中显式进行更新：

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
`BindingContext` 支持与语言环境相关的验证和转换消息。请参阅 [动态验证消息](/docs/data-binding/validation/validators#dynamic-validation-messages) 和 [与语言环境相关的 Jakarta 验证](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages)。
:::
