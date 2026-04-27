---
title: Avatar
sidebar_position: 7
_i18n_hash: a09e8f3e668c6818045ca93f0747f100
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar` 组件提供用户或实体的视觉表示。它可以显示图像、自动计算的首字母、用户自定义的首字母或图标。头像通常用于在评论区、导航菜单、聊天应用程序和联系人列表中识别用户。

<!-- INTRO_END -->

## 创建头像 {#creating-avatars}

要创建 `Avatar`，传递一个用作可访问名称的标签。该组件自动通过提取标签中每个单词的首字母来计算首字母。

```java
// 从标签创建一个显示 "JD" 的头像
Avatar avatar = new Avatar("John Doe");
```

如果您希望对显示的内容有更多控制，您还可以提供明确的首字母：

```java
// 创建一个自定义首字母的头像
Avatar avatar = new Avatar("John Doe", "J");
```

下面的示例展示了在团队面板上下文中的头像。每个 `Avatar` 显示用户的个人资料图像或基于用户名称自动生成的首字母。单击 `Avatar` 会打开一个对话框，显示放大的视图。

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## 显示图像 {#displaying-images}

`Avatar` 组件可以通过将 `Img` 组件作为子组件来显示图像，而不是显示首字母。当提供图像时，图像优先于首字母。

```java
import com.webforj.component.html.elements.Img;

// 带有图像的头像
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip 图像大小
图像会自动缩放以适应头像的维度，这取决于当前的扩展设置。
:::

## 显示图标 {#displaying-icons}

您可以通过将 `Icon` 组件作为子组件添加到 `Avatar` 中来显示图标：

```java
import com.webforj.component.icons.TablerIcon;

// 带有图标的头像
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## 标签和首字母 {#label-and-initials}

`Avatar` 组件使用标签进行可访问性和工具提示生成。`setLabel()` 和 `setText()` 方法是别名，两个方法都设置 `Avatar` 的可访问标签。

:::info 自动计算的首字母
当您仅用标签创建 `Avatar` 时，首字母会通过获取每个单词的第一个字符进行自动计算。例如，标签为 "John Doe" 的 `Avatar` 在 UI 中自动显示 "JD"。
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // 设置标签并自动生成工具提示
avatar.setInitials("JS");       // 覆盖自动计算的首字母
```

:::tip 自动工具提示
该组件会自动从标签生成工具提示，使您可以轻松在悬停时查看完整名称。当使用默认标签 `"Avatar"` 时，此行为将被禁用。
:::

## 点击事件 {#click-events}

`Avatar` 组件实现了 `HasElementClickListener`，允许您响应用户点击。这对于触发诸如打开用户个人资料或显示菜单等操作非常有用。

```java
avatar.onClick(event -> {
  // 处理头像点击 - 例如，打开用户个人资料
  System.out.println("头像被点击了！");
});
```

## 形状 {#shapes}

头像可以显示为圆形或方形。默认形状为 `CIRCLE`，这是用户头像的标准形状。对于团队、公司或应用程序等实体，请使用 `SQUARE`。

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## 主题 {#themes}

主题传达意义或状态；您可以使用它们来指示可用性、突出显示重要用户或匹配应用的设计。

可用的主题包括：

- `DEFAULT`：标准外观
- `GRAY`：中性、低调的外观
- `PRIMARY`：强调主要操作或用户
- `SUCCESS`：指示正面状态（例如，在线）
- `WARNING`：指示谨慎状态（例如，离开）
- `DANGER`：指示错误或忙碌状态
- `INFO`：提供信息上下文

每个主题还有一个轮廓变体，提供更轻的视觉效果：

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## 扩展 {#expanses}

使用 `setExpanse()` 方法控制头像大小。该组件支持从 `XXXSMALL` 到 `XXXLARGE` 的九种大小选项。

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>

## 样式 {#styling}

<TableBuilder name="Avatar" />
