---
title: Avatar
sidebar_position: 7
_i18n_hash: 7974a5de785a24d8b83507dd8c81d03d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-avatar" />
<DocChip chip='since' label='25.11' />
<JavadocLink type="avatar" location="com/webforj/component/avatar/Avatar" top='true'/>

`Avatar` 组件提供用户或实体的视觉表示。它可以显示图像、自动计算的首字母、用户自定义的首字母或图标。头像通常用于识别评论区、导航菜单、聊天应用程序和联系人列表中的用户。

<!-- INTRO_END -->

## 创建头像 {#creating-avatars}

要创建一个 `Avatar`，传递一个作为可访问名称的标签。该组件通过提取标签中每个单词的首字母自动计算首字母。

```java
// 创建一个显示 "JD" 的头像
Avatar avatar = new Avatar("John Doe");
```

如果您希望更好地控制显示的内容，也可以提供自定义的首字母：

```java
// 创建一个带有自定义首字母的头像
Avatar avatar = new Avatar("John Doe", "J");
```

下面的示例展示了团队面板中的头像。每个 `Avatar` 根据用户的姓名显示个人资料图像或自动生成的首字母。点击 `Avatar` 会打开一个对话框，显示放大的视图。

<ComponentDemo 
path='/webforj/avatar?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarView.java'
cssURL='/css/avatar/avatar.css'
height = '500px'
/>

## 显示图像 {#displaying-images}

`Avatar` 组件可以通过将 `Img` 组件作为子组件来显示图像，而不是首字母。当提供图像时，它会优先于首字母。

```java
import com.webforj.component.html.elements.Img;

// 带有图像的头像
Avatar avatar = new Avatar("John Doe", new Img("path/to/profile.png"));
```

:::tip 图像大小
图像会根据当前的扩展设置自动缩放，以适应头像的尺寸。
:::

## 显示图标 {#displaying-icons}

您可以通过将 `Icon` 组件作为子组件添加来在 `Avatar` 中显示图标：

```java
import com.webforj.component.icons.TablerIcon;

// 带有图标的头像
Avatar avatar = new Avatar("Guest User", TablerIcon.create("user"));
```

## 标签和首字母 {#label-and-initials}

`Avatar` 组件使用标签进行可访问性和工具提示生成。`setLabel()` 和 `setText()` 方法是同义词，都用于设置 `Avatar` 的可访问标签。

:::info 自动计算的首字母
当您仅用标签创建 `Avatar` 时，首字母会自动通过提取每个单词的第一个字符进行计算。例如，标签为 "John Doe" 的 `Avatar` 将在 UI 中自动显示 "JD"。
:::

```java
Avatar avatar = new Avatar();
avatar.setLabel("Jane Smith");  // 设置标签并自动生成工具提示
avatar.setInitials("JS");       // 覆盖自动计算的首字母
```

:::tip 自动工具提示
该组件会根据标签自动生成工具提示，方便在悬停时查看完整名称。当使用默认标签 `"Avatar"` 时，此行为将被禁用。
:::

## 点击事件 {#click-events}

`Avatar` 组件实现了 `HasElementClickListener`，允许您对用户点击作出响应。这对于触发操作如打开用户资料或显示菜单非常有用。

```java
avatar.onClick(event -> {
  // 处理头像点击 - 例如，打开用户资料
  System.out.println("Avatar clicked!");
});
```

## 形状 {#shapes}

头像可以显示为圆形或方形。默认形状为 `CIRCLE`，这是用户头像的标准形状。对于团队、公司或应用程序等实体，用 `SQUARE`。

<ComponentDemo
path='/webforj/avatarshapes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarShapesView.java'
height='100px'
/>

## 主题 {#themes}

主题传达意义或状态；您可以使用它们来指示可用性、突出重要用户或匹配应用程序的设计。

可用的主题包括：

- `DEFAULT`：标准外观
- `GRAY`：中性、低调的外观
- `PRIMARY`：强调主要操作或用户
- `SUCCESS`：指示积极状态（例如，在线）
- `WARNING`：指示谨慎（例如，离开）
- `DANGER`：指示错误或繁忙状态
- `INFO`：提供信息上下文

每个主题也有一个轮廓变体，以提供更轻的视觉效果：

<ComponentDemo
path='/webforj/avatarthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarThemesView.java'
height='120px'
/>

## 扩展 {#expanses}

使用 `setExpanse()` 方法控制头像的大小。该组件支持九种大小选项，从 `XXXSMALL` 到 `XXXLARGE`。

<ComponentDemo
path='/webforj/avatarexpanses?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/avatar/AvatarExpansesView.java'
height='100px'
/>


## 样式 {#styling}

<TableBuilder name="Avatar" />
