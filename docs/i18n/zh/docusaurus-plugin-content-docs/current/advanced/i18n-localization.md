---
sidebar_position: 11
title: 本地化
sidebar_class_name: new-content
_i18n_hash: 91f5af285113e5e76d50a201a2fbf88f
---
# 本地化 <DocChip chip='since' label='25.10' />

实现 `LocaleObserver` 接口的组件会在语言环境变化时自动接收通知。这使得 UI 元素能够更新其文本、格式和其他特定于语言环境的内容，而无需手动协调。

## `LocaleObserver` 接口 {#the-localeobserver-interface}

```java title="LocaleObserver.java"
@FunctionalInterface
public interface LocaleObserver extends Serializable {
    void onLocaleChange(LocaleEvent event);
}
```

当组件实现此接口时，webforJ 会自动：
- 在创建时注册组件以接收语言环境变化事件
- 在销毁时注销组件
- 每当语言环境变化时调用 `onLocaleChange()`

此注册过程在组件生命周期中发生。

## 处理翻译 {#handling-translations}

当调用 `onLocaleChange()` 时，组件会接收到新的语言环境。如何加载和应用翻译取决于开发者。常见的方法包括：

- 使用 Java `ResourceBundle` 和属性文件
- 数据库查询翻译
- 自定义翻译提供者
- 简单情况的硬编码映射

此示例使用 `ResourceBundle`，它将翻译存储在属性文件中：

```
messages.properties        # 后备/默认
messages_en.properties     # 英语
messages_de.properties     # 德语
```

属性文件包含键值对：

```properties title="messages_en.properties"
app.title=Mailbox
menu.inbox=Inbox
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
```
## 更改语言环境 {#changing-the-locale}

使用 `App.setLocale()` 来更改应用程序语言环境。这会触发对所有注册观察者的通知：

```java
App.setLocale(Locale.GERMAN);
App.setLocale(Locale.forLanguageTag("fr"));
```

一种典型的实现可能使用下拉菜单或选择组件：

```java
ChoiceBox languageSelector = new ChoiceBox();
languageSelector.add("en", "English");
languageSelector.add("de", "Deutsch");
languageSelector.add("fr", "Français");

languageSelector.onSelect(e -> {
  String lang = (String) e.getSelectedItem().getKey();
  Locale newLocale = Locale.forLanguageTag(lang);

  App.setLocale(newLocale);
});
```

当用户选择一种语言时，`App.setLocale()` 会触发，所有实现了 `LocaleObserver` 的组件都会接收到更新。

## 实现观察者 {#implementing-observers}

当组件实现 `LocaleObserver` 时，需要处理两种情况：使用当前语言环境的初始渲染和在语言环境变化时的更新。以下示例通过一个显示本地化文本和链接的组件演示此模式。

该组件存储需要翻译更新的元素的引用。构造时，它加载当前语言环境的翻译。当语言环境变化时，`onLocaleChange()` 被触发，允许组件重新加载翻译并更新其显示的文本。

```java title="TranslationService.java"
import com.webforj.App;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {
  private final MessageSource messageSource;

  public TranslationService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String get(String key) {
    return messageSource.getMessage(key, null, App.getLocale());
  }
}
```

```java title="Explore.java"
public class Explore extends Composite<FlexLayout> implements LocaleObserver {
  private final TranslationService i18n;
  private FlexLayout self = getBoundComponent();
  private H3 titleElement;
  private Anchor anchor;
  private String titleKey;

  public Explore(TranslationService i18n, String titleKey) {
    this.i18n = i18n;
    this.titleKey = titleKey;

    self.addClassName("explore-component");
    self.setStyle("margin", "1em auto");
    self.setDirection(FlexDirection.COLUMN);
    self.setAlignment(FlexAlignment.CENTER);
    self.setMaxWidth(300);
    self.setSpacing(".3em");

    Img img = new Img(String.format("ws://explore/%s.svg", titleKey), "mailbox");
    img.setMaxWidth(250);

    String translatedTitle = i18n.get("menu." + titleKey.toLowerCase());
    titleElement = new H3(translatedTitle);

    anchor = new Anchor("https://docs.webforj.com/docs/components/overview", i18n.get("explore.link"));
    anchor.setTarget("_blank");

    self.add(img, titleElement, anchor);
  }

  @Override
  public void onLocaleChange(LocaleEvent event) {
    titleElement.setText(i18n.get("menu." + titleKey.toLowerCase()));
    anchor.setText(i18n.get("explore.link"));
  }
}
```

该组件存储显示翻译内容的元素引用（`titleElement` 和 `anchor`）。翻译在构造函数中使用当前语言环境加载。当语言环境变化时，`onLocaleChange()` 仅更新需要翻译的文本。

## 生命周期管理 {#lifecycle-management}

该框架通过组件生命周期钩子自动处理观察者注册：

- **创建时**：实现 `LocaleObserver` 的组件会在 `LocaleObserverRegistry` 中注册
- **销毁时**：组件会注销以防止内存泄漏

每个应用实例维护其自己的观察者注册表。此自动管理意味着：

- 无需手动调用注册/注销
- 不会因销毁的组件而导致内存泄漏
- 线程安全的并发通知

:::info 每个应用的注册表
每个应用实例维护其自己的观察者注册表。在一个应用中注册的观察者不会接收到同一 JVM 中其他应用的通知。
:::

## `LocaleEvent` {#localeevent}

传递给 `onLocaleChange()` 的 `LocaleEvent` 提供：

| 方法 | 返回 | 描述 |
|--------|---------|-------------|
| `getLocale()` | `Locale` | 设置的新语言环境 |
| `getSource()` | `Object` | 接收到事件的组件 |

```java
@Override
public void onLocaleChange(LocaleEvent event) {
  Locale newLocale = event.getLocale();
  Object source = event.getSource();

  // 使用新语言环境更新组件
  ResourceBundle bundle = ResourceBundle.getBundle("messages", newLocale);
  updateUI(bundle);
}
```
