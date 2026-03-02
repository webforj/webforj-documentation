---
sidebar_position: 12
title: Translation
sidebar_class_name: new-content
_i18n_hash: 57626c2969592f2378a55eff0dd01d48
---
# Translation <DocChip chip='since' label='25.12' />

webforJ 包含一个内置的翻译系统，通过键查找本地化字符串。该系统由一个翻译解析器组成，该解析器将键映射到本地化文本，一个提供便捷 `t()` 方法的 `HasTranslation` concern 接口，`App.getTranslation()` 用于任何地方的直接访问，从浏览器自动检测语言环境，以及支持自定义翻译源，例如数据库。

## 翻译解析器 {#translation-resolver}

翻译解析器是查找给定键和语言环境的本地化字符串的系统。webforJ 提供一个默认解析器 `BundleTranslationResolver`，它从类路径中的 Java `ResourceBundle` 属性文件加载翻译。这无需额外的依赖即可开箱即用。

### 资源包文件

将翻译文件放置在 `src/main/resources` 目录中。默认解析器查找命名为 `messages` 的文件，后缀遵循标准的 Java `ResourceBundle` 命名约定：

```text
messages.properties        # 默认/回退翻译
messages_en.properties     # 英语
messages_de.properties     # 德语
messages_fr_CA.properties  # 法语（加拿大）
```

每个文件包含键值对。键是您在代码中使用的标识符，值是翻译后的字符串。您可以包括 [`MessageFormat`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/text/MessageFormat.html) 占位符，如 `{0}`、`{1}` 用于动态值：

```properties title="messages.properties"
app.title=邮箱
menu.inbox=收件箱
menu.outbox=发件箱
greeting=你好 {0}，您有 {1} 条新消息
```

```properties title="messages_de.properties"
app.title=Postfach
menu.inbox=Posteingang
menu.outbox=Postausgang
greeting=Hallo {0}, Sie haben {1} neue Nachrichten
```

解析器委托给 Java 的标准 [`ResourceBundle`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ResourceBundle.html) 解析链，自动处理语言环境匹配和回退。

### 配置支持的语言环境 {#configuring-supported-locales}

`supported-locales` 设置告诉 webforJ 您的应用程序支持哪些语言环境。此列表被自动检测用于将用户的浏览器语言环境与可用翻译进行匹配。列表中的第一个语言环境在没有找到更好匹配时用作默认回退。属性键是 `webforj.i18n.supported-locales`，接受一系列 [BCP 47](https://en.wikipedia.org/wiki/IETF_language_tag) 语言标签，例如 `en, de`。

:::info 更多信息
请参阅 [Configuration](/docs/configuration/properties) 部分以了解如何为不同环境设置属性。
:::

## `t()` 方法 {#the-t-method}

实现 `HasTranslation` concern 接口的组件可以使用 `t()` 方法进行文本翻译。该方法接受一个翻译键，并返回当前应用程序语言环境的本地化字符串：

```java
public class MainLayout extends Composite<AppLayout> implements HasTranslation {

  public MainLayout() {
    // 简单翻译
    String title = t("app.title");

    // 带有 MessageFormat 参数的翻译
    String greeting = t("greeting", userName, messageCount);

    // 特定语言环境的翻译
    String germanTitle = t(Locale.GERMAN, "app.title");
  }
}
```

您也可以直接使用 `App.getTranslation()`，而无需实现接口：

```java
String title = App.getTranslation("app.title");
```

:::info 优雅的回退
如果找不到翻译键，`t()` 会返回该键本身，而不是抛出异常。这意味着如果缺少翻译，您的应用程序不会中断。键将原样显示，并记录警告，以便您在开发期间跟踪缺失的翻译。
:::

## 实现翻译组件 {#implementing-translated-components}

翻译组件通常将 `HasTranslation` 与 [`LocaleObserver`](/docs/advanced/locale-management#the-localeobserver-interface) 结合使用。在创建 UI 元素时使用 `t()` 来设置初始翻译文本。要支持运行时语言切换，实现 `LocaleObserver` 并在 `onLocaleChange()` 中更新相同的文本。

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

:::tip 数据绑定
数据绑定系统支持使用 `Supplier<String>` 与 `t()` 一起翻译验证和转换消息。请参阅 [动态验证消息](/docs/data-binding/validation/validators#dynamic-validation-messages)、[动态转换器消息](/docs/data-binding/transformation#dynamic-transformer-error-messages) 和 [语言环境感知的 Jakarta 验证](/docs/data-binding/validation/jakarta-validation#locale-aware-validation-messages)。
:::

## 自定义翻译解析器 {#custom-translation-resolvers}

默认解析器从 Java `ResourceBundle` 属性文件加载翻译。要从不同的源（例如数据库或远程服务）加载翻译，实现 `TranslationResolver`：

```java title="DatabaseTranslationResolver.java"
public class DatabaseTranslationResolver implements TranslationResolver {
  private final TranslationRepository repository;
  private final List<Locale> supportedLocales;

  public DatabaseTranslationResolver(TranslationRepository repository,
      List<Locale> supportedLocales) {
    this.repository = repository;
    this.supportedLocales = List.copyOf(supportedLocales);
  }

  @Override
  public String resolve(String key, Locale locale, Object... args) {
    String value = repository
        .findByKeyAndLocale(key, locale.getLanguage())
        .map(Translation::getValue)
        .orElse(key);

    if (args != null && args.length > 0) {
      value = new MessageFormat(value, locale).format(args);
    }

    return value;
  }

  @Override
  public List<Locale> getSupportedLocales() {
    return supportedLocales;
  }
}
```

### 注册自定义解析器 {#registering-a-custom-resolver}

在普通的 webforJ 应用程序中，在应用程序启动之前设置解析器，例如使用 [应用程序生命周期监听器](/docs/advanced/lifecycle-listeners)：

```java
App.setTranslationResolver(new DatabaseTranslationResolver(repository, supportedLocales));
```

在 Spring Boot 应用程序中，将解析器作为 Bean 暴露：

```java title="MessageSourceConfig.java"
@Configuration
public class MessageSourceConfig {

  @Bean
  TranslationResolver translationResolver(TranslationRepository repository,
      SpringConfigurationProperties properties) {
    List<Locale> supportedLocales = properties.getI18n().getSupportedLocales().stream()
        .map(Locale::forLanguageTag)
        .toList();
    return new DatabaseTranslationResolver(repository, supportedLocales);
  }
}
```

:::info Spring Boot 中的默认解析器
当没有定义自定义 `TranslationResolver` Bean 时，Spring 自动配置提供一个默认的 `BundleTranslationResolver`，它根据 `application.properties` 中的支持语言环境进行配置。
:::
