---
sidebar_position: 1
title: Data Binding
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: b05f45d2f2725defb3d5fba7cb0fb622
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

webforJ 包含一个数据绑定特性，将用户界面组件与 Java 应用程序中的后端数据模型集成。此特性填补了用户界面与数据层之间的空白，使得用户界面中的变化能够反映到数据模型中，反之亦然，从而减少事件处理和数据同步的复杂性。

<AISkillTip skill="webforj-building-forms" />

## 概念 {#concept}

下面的演示展示了一个简单的 webforJ 应用程序，用于注册超级英雄，使用 webforJ 数据绑定。该应用程序由两部分组成：`HeroRegistration.java` 和 `Hero.java`。

在 `HeroRegistration.java` 中，代码配置了用户界面，包括一个用于输入英雄名称的 `TextField`，一个用于选择超能力的 `ComboBox`，以及一个用于提交注册的 `Button`。

`Hero` 类定义了数据模型，并对英雄的名称和能力施加了验证约束。条目必须有效，并遵循指定的标准，例如长度和模式。

该应用程序使用 `BindingContext` 将用户界面组件绑定到 `Hero` 对象的属性。当用户点击提交按钮时，如果条目有效，应用程序会将输入的表单数据写回 `Hero` bean。

<Tabs>
<TabItem value="HeroRegistration" label="HeroRegistration.java">

```java showLineNumbers
public class HeroRegistration extends App {
    
  private TextField name = new TextField("文本字段");
  private ComboBox power = new ComboBox("超能力");
  private Button submit = new Button("提交申请");
  private FlexLayout layout = FlexLayout.create(name, power, submit).vertical().build()
      .setStyle("margin", "20px auto").setMaxWidth("400px");

  @Override
  public void run() throws WebforjException {
    power.insert("飞行", "隐形", "激光视觉", "速度", "瞬移");

    BindingContext<Hero> context = BindingContext.of(this, Hero.class, true);
    Hero bean = new Hero("超人", "飞行");

    // 在表单中反映 bean 数据
    context.read(bean);

    submit.onClick(e -> {
      // 将表单数据写回 bean
      ValidationResult results = context.write(bean);

      if (results.isValid()) {
        // 对 bean 执行某些操作
        // repository.persist(bean)
      }
    });

    Frame frame = new Frame();
    frame.add(layout);
  }
}
```

</TabItem>
<TabItem value="Hero" label="Hero.java">

```java showLineNumbers
public class Hero {

  @NotEmpty(message = "名称不能为空")
  @Length(min = 3, max = 20)
  private String name;

  @NotEmpty(message = "未指定超能力")
  @Pattern(regexp = "飞行|隐形|激光视觉|速度|瞬移", message = "无效的超能力")
  private String power;

  public Hero(String name, String power) {
    this.name = name;
    this.power = power;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPower() {
    return power;
  }

  public void setPower(String power) {
    this.power = power;
  }

  public String toString() {
    return "名称: " + name + ", 超能力: " + power;
  }
}
```

</TabItem>
</Tabs>

## 主要特性 {#key-features}

- **双向绑定：** 支持双向数据绑定，允许数据模型中的变化更新用户界面，用户界面中的用户交互更新数据模型。

- **验证支持：** 集成了全面的验证机制，可以自定义和扩展。开发人员可以实现自己的验证规则，或使用现有的验证框架，如 Jakarta Validation，在更新模型之前验证数据完整性。

- **可扩展性：** 可以轻松扩展以支持不同类型的用户界面组件、数据转换和复杂的验证场景。

- **注释驱动的配置：** 使用注释来最小化样板代码，使用户界面组件与数据模型之间的绑定声明式并易于管理。

## 主题 {#topics}

<DocCardList className="topics-section" />
