---
title: Avatar
sidebar_position: 7
sidebar_class_name: new-content
_i18n_hash: 88c1ddd6113a8022a977f27413e2eacf
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar` 组件提供用户或实体的视觉表示。它可以显示图像、自动计算的首字母、定制的首字母或者图标。头像通常用于在评论区、导航菜单、聊天应用和联系人列表中标识用户。

<!-- INTRO_END -->

## 创建头像 {#creating-avatars}

要创建一个 `Avatar`，请传递一个作为可访问名称的标签。该组件通过提取标签中每个单词的第一个字母自动计算首字母。

```java
// 从标签中创建一个显示 "JD" 的头像
Avatar avatar = new Avatar("John Doe");
```

如果您希望更好地控制显示内容，也可以提供明确的首字母：

```java
// 创建一个带有自定义首字母的头像
Avatar avatar = new Avatar("John Doe", "J");
```

下面的示例展示了在团队面板上下文中使用头像。每个 `Avatar` 显示用户的个人资料图像或根据用户姓名自动生成的首字母。单击 `Avatar` 会打开一个对话框，显示放大的视图。

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '450px'
/>

## 显示图像 {#displaying-images}

`Avatar` 组件可以通过将 `Img` 组件作为子组件显示图像，而不是首字母。当提供图像时，它优先于首字母显示。

```java
import com.webforj.component.html.elements.Img;

// 带有图像的头像
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip 图像大小
图像会根据当前的扩展设置自动缩放，以适应头像的尺寸。
:::

## 显示图标 {#displaying-icons}

您可以通过将 `Icon` 组件添加为子组件，在 `Avatar` 中显示图标：

```java
import com.webforj.component.icons.TablerIcon;

// 带有图标的头像
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## 标签和首字母 {#label-and-initials}

`Avatar` 组件使用标签进行可访问性和工具提示生成。`setLabel()` 和 `setText()` 方法是别名，都设置 `Avatar` 的可访问标签。

:::info 自动计算的首字母
当您仅使用标签创建 `Avatar` 时，首字母会自动计算，取每个单词的第一个字符。例如，“John Doe”变为“JD”。
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // 设置标签并自动生成工具提示
avatar.setInitials("JS");       // 覆盖自动计算的首字母
```

:::tip 自动工具提示
该组件会自动从标签生成工具提示，使您在悬停时容易看到全名。当使用默认标签 `"Avatar"` 时，此行为会被禁用。
:::

## 点击事件 {#click-events}

`Avatar` 组件实现了 `HasElementClickListener`，允许您响应用户点击。这对于触发打开用户个人资料或显示菜单等操作非常有用。

```java
avatar.onClick(event -> {
  // 处理头像点击 - 例如，打开用户资料
  System.out.println("Avatar clicked!");
});
```

## 形状 {#shapes}

头像可以以圆形或方形显示。默认形状为 `CIRCLE`，这是用户头像的标准形状。对于团队、公司或应用等实体，请使用 `SQUARE`。

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## 主题 {#themes}

主题传达意义或状态；您可以使用它们来指示可用性、高亮重要用户或与应用的设计相匹配。

以下主题可用：

- `DEFAULT`：标准外观
- `GRAY`：中性、柔和的外观
- `PRIMARY`：强调主要操作或用户
- `SUCCESS`：表示积极状态（例如，在线）
- `WARNING`：表示注意（例如，离开）
- `DANGER`：表示错误或忙碌状态
- `INFO`：提供信息上下文

每个主题还有一个轮廓变体，以获得更轻的视觉效果：

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## 扩展 {#expanses}

使用 `setExpanse()` 方法控制头像的大小。该组件支持从 `XXXSMALL` 到 `XXXLARGE` 的九种尺寸选项。

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## 样式 {#styling}

<TableBuilder name="Avatar" />
