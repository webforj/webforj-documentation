---
title: Tree
sidebar_position: 150
_i18n_hash: 8f653af18f5e041d09896794f560d30a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree` 组件将数据组织为节点的层次结构。每个节点都包含一个唯一的键和一个标签。节点相互连接形成父子关系。您可以展开或折叠节点以显示或隐藏它们的子节点。图标可以清晰地表明您正在查看哪种类型的节点以及它是否被选中。选择支持同时选择一个或多个节点。

## 节点模型和树结构 {#node-model-and-tree-structure}

### `TreeNode` 的角色 {#the-role-of-treenode}

树中的每个数据项都被封装在一个 `TreeNode` 中。这个对象保存键、文本标签，以及指向其父节点和子节点的链接。根节点是特殊的：它在每个树中存在但不可见。它作为所有顶级节点的容器，使树的内部结构更易于管理。

由于节点保持对其父节点和子节点的引用，因此遍历树是直接的。无论您想向上移动、向下移动，还是通过键查找特定节点，这些连接始终是可访问的。

### 节点创建和管理 {#node-creation-and-management}

节点使用简单的工厂方法创建，您可以提供键和文本，也可以只提供文本（文本本身作为键）。这确保每个节点都是有效的且唯一可识别的。

将节点添加到树中涉及在父节点上调用 `add()` 或 `insert()`。这些方法处理分配父引用并通知树更新其用户界面。

示例：

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info 仅单个父节点
尝试将同一节点分配给多个父节点将导致抛出异常。此 safeguard 确保树保持适当的层次结构，防止节点有多个父节点，这会破坏结构的完整性并导致意外行为。
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### 修改节点 {#modifying-nodes}

您可以通过调用 `setText(String text)` 来更新节点的标签。此方法更改树中显示的节点文本。

要移除特定的子节点，使用 `remove(TreeNode child)`。这将分离子节点与其父节点，并将其从树结构中移除。它也会清除父引用。

如果您希望清除节点的所有子节点，请调用 `removeAll()`。这将移除每个子节点，清除它们的父引用，并清空子节点列表。

每个节点支持使用 `setUserData(Object key, Object data)` 在服务器端存储附加信息。这使您可以将任意元数据或引用与节点关联，而不将这些数据暴露给客户端或用户界面。

:::tip 使用演示编辑节点文本
在演示中，双击一个节点以打开其文本编辑器。输入新文本并保存，以更新树中节点的标签。
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## 图标 {#icons}

图标提供有关节点代表的内容及其状态的视觉提示。它们通过一目了然地区分节点类型和选择状态来提高可读性。`Tree` 组件支持全局设置默认图标、自定义每个节点的图标以及切换图标的可见性。

### 全局图标 {#global-icons}

该树允许您为折叠组、展开组、叶节点和选定叶节点设置默认图标。

示例：

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip 图标来源
图标可以是任何有效的 webforJ [图标](./icon) 定义或通过 webforJ [支持的资产协议](../managing-resources/assets-protocols) 加载的资源文件。
:::

### 每个节点图标 {#per-node-icons}

您可以通过为单个节点分配图标来覆盖全局默认值。当某些节点代表不同概念时，这非常有用，例如“项目”文件夹或特殊文件。

示例：

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### 图标可见性 {#icon-visibility}

有时，您可能希望隐藏组或叶的图标以减少混乱。该组件允许您全局切换这些类别的可见性，让您简化树的外观而不失去结构。

示例：

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## 节点展开和折叠 {#node-expansion-and-collapse}

节点可以被展开或折叠，以控制树中哪些部分是可见的。这允许专注于相关部分，并支持懒加载或动态数据更新的场景。

### 展开和折叠操作 {#expand-and-collapse-operations}

该树支持通过其键或直接引用展开和折叠单独节点。您还可以一次展开或折叠一个节点的所有后代。

这些操作使您可以控制树中可见的内容，并支持数据的懒加载或关注感兴趣的区域。

示例：

```java
tree.expand(node);
tree.collapse(key);

// 折叠子树
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info 折叠根节点
根节点锚定树但保持隐藏。折叠根节点通常会隐藏所有内容，使树看起来是空的。为避免这种情况，折叠根节点实际上是折叠它的所有子节点，但保持根节点在内部展开，从而确保树仍然正确显示其内容。
:::

### 懒加载节点 {#lazy-loading-nodes}

树支持通过响应展开事件进行节点子节点的懒加载。当用户展开一个节点时，您的应用可以动态加载或生成该节点的子节点。这通过按需加载树的可见部分来提高性能。

使用 `onExpand` 事件来检测节点何时被展开。在处理程序内部，检查节点的子节点是否是占位符（例如，旋转器或空节点），并在实际数据加载完成后用实际数据替换它们。

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## 选择 {#selection}

选择控制由用户选择的节点。`Tree` 组件支持灵活的模式和 API 来选择、取消选择和查询节点。

### 选择模式 {#selection-modes}

您可以选择树是否允许一次选择一个节点或同时选择多个节点。从多个选择切换到单个选择时，将自动取消选择所有非第一个被选中的节点。

示例：

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip 多选交互
当树设置为多选模式时，用户可以一次选择多个节点。其工作原理取决于设备：

* **桌面（鼠标和键盘）：** 用户按住 **Ctrl** 键（或在 macOS 上按 **Cmd** 键）并点击节点以将其添加到或从当前选择中移除。这允许在不取消选择其他节点的情况下选择多个单独的节点。
* **移动和触摸设备：** 由于不可用的修改键，用户只需点击节点以选择或取消选择它们。每次点击会切换该节点的选择状态，从而通过简单的点击轻松实现多选择。
:::

### 选择和取消选择 {#selecting-and-deselecting}

节点可以通过引用、键、单独或批量进行选择或取消选择。您还可以在一次调用中选择或取消选择节点的所有子节点。

示例：

```java
// 按引用或键选择节点
tree.select(node);
tree.selectKey(key);

// 按引用或键取消选择节点
tree.deselect(node);
tree.deselectAll();

// 选择或取消选择节点的子节点
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### 选择状态检索 {#selection-state-retrieval}

您可以通过利用以下代码获取当前选择：

```java
// 获取选定节点的引用
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// 获取选定节点的键
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## 样式 {#styling}

<TableBuilder name="Tree" />
