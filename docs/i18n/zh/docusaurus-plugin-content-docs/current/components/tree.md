---
title: Tree
sidebar_position: 150
_i18n_hash: 280fb07f73ba1172b33bd0617ded7876
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree` 组件将数据组织为节点的层次结构。每个节点持有一个唯一的键和标签。节点连接形成父子关系。您可以展开或折叠节点，以显示或隐藏其子节点。图标有助于明确您正在查看的节点类型以及其是否被选中。选择支持一次选择一个节点或多个节点。

<!-- INTRO_END -->

## 节点模型和树结构 {#node-model-and-tree-structure}

### `TreeNode` 的角色 {#the-role-of-treenode}

树中的每个数据项都被封装在一个 `TreeNode` 中。这个对象包含键、文本标签，并链接到其父节点和子节点。根节点是特别的：它存在于每个树中但不可见。它作为所有顶级节点的容器，使树结构在内部更易于管理。

因为节点保持对其父节点和子节点的引用，所以遍历树是直接的。无论您想向上、向下移动，还是按键查找特定节点，连接始终可用。

### 节点创建和管理 {#node-creation-and-management}

节点是通过简单的工厂方法创建的，可以提供键和文本，或者仅提供文本（其本身也作为键）。这确保每个节点是有效且唯一可识别的。

将节点添加到树中涉及在父节点上调用 `add()` 或 `insert()`。这些方法处理分配父引用并通知树更新其用户界面。

示例：

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info 单一父级
尝试将同一节点分配给多个父节点将导致抛出异常。这种保护措施确保树保持适当的层次结构，防止节点拥有多个父节点，这会破坏结构的完整性并导致意外行为。
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### 修改节点 {#modifying-nodes}

您可以通过调用 `setText(String text)` 来更新节点的标签。这个方法更改树中显示的节点文本。

要移除特定子节点，使用 `remove(TreeNode child)`。这将子节点与其父节点分离，并将其从树结构中移除。它还会清除父引用。

如果您想清除节点的所有子节点，可以调用 `removeAll()`。这将移除每个子节点，清除它们的父引用，并清空子节点列表。

每个节点支持使用 `setUserData(Object key, Object data)` 在服务器端存储额外信息。这允许您与节点关联任意元数据或引用，而无需将这些数据暴露给客户端或用户界面。

:::tip 使用演示编辑节点文本
在演示中，双击一个节点以打开其文本的编辑器。输入新文本并保存以更新树中节点的标签。
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## 图标 {#icons}

图标提供了有关节点代表的内容和其状态的视觉线索。它们通过一目了然地区分节点类型和选择状态，提高了可读性。`Tree` 组件支持全球设置默认图标，为节点自定义图标，并切换图标可见性。

### 全局图标 {#global-icons}

树允许您为折叠组、展开组、叶子节点和选定叶子设置默认图标。

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

### 每个节点的图标 {#per-node-icons}

您可以通过为单个节点分配图标来覆盖全局默认设置。当某些节点表示不同概念时，例如“项目”文件夹或特殊文件，这很有用。

示例：

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### 图标可见性 {#icon-visibility}

有时，您可能希望隐藏组或叶子的图标以减少杂乱。该组件允许您全局切换这些类别的可见性，让您简化树的外观，而不失去结构。

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

节点可以被展开或折叠，以控制树的哪些部分可见。这允许用户关注相关部分，并支持懒加载或动态数据更新的场景。

### 展开和折叠操作 {#expand-and-collapse-operations}

树支持通过其键或直接引用来展开和折叠单个节点。您也可以一次展开或折叠一个节点的所有后代。

这些操作让您控制树的可见度，并支持数据的懒加载或关注感兴趣的区域。

示例：

```java
tree.expand(node);
tree.collapse(key);

// collapse sub trees
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info 折叠根节点
根节点固定树，但保持隐藏。折叠根节点通常会隐藏所有内容，使树看起来为空。为避免这种情况，折叠根节点实际上是折叠其所有子节点，但内部保持根节点展开，以确保树仍然正确显示其内容。
:::

### 懒加载节点 {#lazy-loading-nodes}

树支持通过响应展开事件进行节点子项的懒加载。当用户展开一个节点时，您的应用可以动态加载或生成该节点的子项。这提高了性能，通过按需加载可见部分的树。

使用 `onExpand` 事件来检测何时展开节点。在处理程序中，检查节点的子项是否为占位符（例如，旋转器或空节点），并在加载完成后用实际数据替换它们。

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## 选择 {#selection}

选择控制用户选择哪些节点。`Tree` 组件支持灵活的模式和 API 来选择、取消选择和查询节点。

### 选择模式 {#selection-modes}

您可以选择树是否允许一次选择一个节点或同时选择多个节点。从多个选择切换到单一选择将自动取消选择所有但第一个选定节点。

示例：

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip 多重选择交互
当树设置为多重选择模式时，用户可以一次选择多个节点。这种方式取决于设备：

* **桌面（鼠标和键盘）：** 用户按住 **Ctrl** 键（或 macOS 上的 **Cmd** 键）并单击节点以将其添加到当前选择或从中删除。这允许选择多个单个节点而不取消选择其他节点。
* **移动和触摸设备：** 由于没有修改键，用户只需点击节点以选择或取消选择它们。每次点击切换该节点的选择状态，通过简单的点击实现轻松的多重选择。
:::

### 选择和取消选择 {#selecting-and-deselecting}

节点可以通过引用、键、单独或批量进行选择或取消选择。您还可以通过一次调用选择或取消选择节点的所有子节点。

示例：

```java
// select node by reference or key
tree.select(node);
tree.selectKey(key);

// deselect node by reference or key
tree.deselect(node);
tree.deselectAll();

// selecting or deselecting children of nodes
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### 选择状态检索 {#selection-state-retrieval}

您可以通过利用以下代码获取当前选择：

```java
// get the reference of selected node
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// get the key of selected node
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
