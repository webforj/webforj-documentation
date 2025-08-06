---
title: Tree
sidebar_position: 150
_i18n_hash: b161d0d5855f65cb593cf23bc2695d5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree` 组件将数据组织为节点的层次结构。每个节点包含一个唯一的键和一个标签。节点连接形成父子关系。您可以展开或折叠节点以显示或隐藏其子节点。图标可清楚地表明您正在查看的节点类型及其是否被选中。选择支持一次选择一个节点或多个节点。

## 节点模型和树结构 {#node-model-and-tree-structure}

### `TreeNode` 的角色 {#the-role-of-treenode}

树中的每一条数据都被包裹在一个 `TreeNode` 中。该对象保存键、文本标签，以及与其父节点和子节点的链接。根节点是特殊的：它存在于每棵树中但不可见。它作为所有顶层节点的容器，使树结构在内部管理更加容易。

因为节点保持对其父节点和子节点的引用，所以遍历树是直接的。无论您想向上、向下移动，还是根据键找到特定节点，连接始终可访问。

### 节点创建和管理 {#node-creation-and-management}

节点是使用简单的工厂方法创建的，可以通过提供键和文本或仅提供文本（文本也作为键）来完成。这确保每个节点都是有效且唯一可识别的。

将节点添加到树中涉及在父节点上调用 `add()` 或 `insert()`。这些方法处理分配父引用，并通知树更新其用户界面。

示例：

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info 仅单个父级
尝试将同一节点分配给多个父节点将导致抛出异常。此保护机制确保树保持适当的层次结构，防止节点有多个父节点，这将破坏结构的完整性并导致意外行为。
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### 修改节点 {#modifying-nodes}

您可以通过调用 `setText(String text)` 来更新节点的标签。此方法更改树中显示的节点文本。

要移除特定的子节点，请使用 `remove(TreeNode child)`。这会将子节点从其父节点中分离，并从树结构中移除它。此外，它还会清除父引用。

如果要清除节点的所有子节点，请调用 `removeAll()`。这会移除每个子节点，清除它们的父引用，并清空子节点列表。

每个节点支持使用 `setUserData(Object key, Object data)` 在服务器端存储附加信息。这样您可以将任意元数据或引用与节点关联，而无需将这些数据公开给客户端或用户界面。

:::tip 使用演示编辑节点文本
在演示中，双击节点以打开其文本的编辑器。输入新文本并保存以更新树中节点的标签。
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## 图标 {#icons}

图标提供有关节点代表的内容及其状态的视觉指示。通过一目了然地区分节点类型和选择状态，提升了可读性。`Tree` 组件支持全局设置默认图标、按节点自定义图标以及切换图标可见性。

### 全局图标 {#global-icons}

树允许您为折叠组、展开组、叶子节点和选定的叶子节点设置默认图标。

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

### 按节点图标 {#per-node-icons}

您可以通过为各个节点分配图标来覆盖全局默认设置。当某些节点代表不同概念时，例如“项目”文件夹或特殊文件，这非常有用。

示例：

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### 图标可见性 {#icon-visibility}

有时，您可能希望隐藏组或叶子的图标以减少混乱。该组件允许您在全局范围内切换这些类别的可见性，从而简化树的外观而不失去结构。

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

节点可以展开或折叠以控制树的哪些部分可见。这允许集中关注相关部分，并支持懒加载或动态数据更新的场景。

### 展开和折叠操作 {#expand-and-collapse-operations}

树支持通过其键或直接引用展开和折叠单个节点。您还可以一次性展开或折叠一个节点的所有后代。

这些操作让您控制树的可见性，并支持数据的懒加载或专注于感兴趣的区域。

示例：

```java
tree.expand(node);
tree.collapse(key);

// 折叠子树
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info 折叠根节点
根节点固定了树，但保持隐藏。折叠根节点通常会隐藏所有内容，使树看起来是空的。为了避免这种情况，折叠根实际上会折叠所有子节点，但内部保留根节点展开，确保树仍然可以正确显示其内容。
:::

### 懒加载节点 {#lazy-loading-nodes}

树支持通过对展开事件做出反应来懒加载节点子节点。当用户展开一个节点时，您的应用可以动态加载或生成该节点的子节点。这样可以通过按需加载树的可见部分来提高性能。

使用 `onExpand` 事件检测节点何时被展开。在处理程序内，检查节点的子节点是否为占位符（例如，加载中旋转器或空节点），一旦加载完成，用实际数据替换它们。

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## 选择 {#selection}

选择控制用户选择了哪些节点。`Tree` 组件支持灵活的模式和 API 来选择、取消选择和查询节点。

### 选择模式 {#selection-modes}

您可以选择树是否允许一次选择一个节点或同时选择多个节点。从多个选择切换到单个选择自动取消所有选择，只保留第一个选择的节点。

示例：

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip 多重选择交互
当树设置为多重选择模式时，用户可以一次选择多个节点。这种工作方式取决于设备：

* **桌面（鼠标和键盘）：** 用户按住 **Ctrl** 键（或在 macOS 上按 **Cmd** 键）并点击节点以将其添加到当前选择中或移除它。这允许在不取消选择其他节点的情况下选择多个单独的节点。
* **移动和触摸设备：** 由于没有可用的修饰键，用户只需轻触节点以选择或取消选择它们。每次轻触都会切换该节点的选择状态，通过简单的轻触实现轻松多重选择。
:::

### 选择和取消选择 {#selecting-and-deselecting}

可以通过引用、键、单独或批量选择或取消选择节点。您也可以在一次调用中选择或取消选择一个节点的所有子节点。

示例：

```java
// 通过引用或键选择节点
tree.select(node);
tree.selectKey(key);

// 通过引用或键取消选择节点
tree.deselect(node);
tree.deselectAll();

// 选择或取消选择节点的子节点
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### 选择状态检索 {#selection-state-retrieval}

您可以通过利用下面展示的代码获取当前选择：

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
