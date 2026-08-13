---
title: Tree
sidebar_position: 150
description: >-
  Display hierarchical data with the Tree component, using TreeNode parent-child
  links, expand or collapse, icons, and selection.
_i18n_hash: 0d536028b5d1148a59b52128c41278a5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree` 组件将数据组织为节点的层次结构。每个节点保留一个唯一的键和一个标签。节点通过连接形成父子关系。您可以展开或折叠节点以显示或隐藏其子节点。图标清晰地说明了您正在查看的节点类型以及它是否被选中。选择支持同时选择一个节点或多个节点。

<!-- INTRO_END -->

## 节点模型和树结构 {#node-model-and-tree-structure}

### `TreeNode` 的角色 {#the-role-of-treenode}

树中的每个数据项都被包装在一个 `TreeNode` 中。这个对象保存键、文本标签，并链接到其父节点和子节点。根节点是特殊的：它在每棵树中存在，但不可见。它充当所有顶级节点的容器，使树结构在内部更易于管理。

由于节点保留对其父节点和子节点的引用，遍历树是直接的。无论您想向上、向下移动，还是通过键查找特定节点，连接始终可访问。

### 节点的创建和管理 {#node-creation-and-management}

使用简单的工厂方法创建节点，可以通过提供键和文本或者仅提供文本（文本也作为键）来完成。这确保每个节点都是有效的并且可唯一识别。

将节点添加到树中涉及调用父节点上的 `add()` 或 `insert()`。这些方法处理分配父引用并通知树更新其 UI。

示例：

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info 单一父节点限制
尝试将同一节点分配给多个父节点将导致抛出异常。此保护措施确保树维持适当的层次结构，防止节点拥有多个父节点，这将破坏结构的完整性并导致意外行为。
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### 修改节点 {#modifying-nodes}

您可以通过调用 `setText(String text)` 来更新节点的标签。此方法更改树中节点显示的文本。

要删除特定的子节点，请使用 `remove(TreeNode child)`。这将从其父节点中分离子节点，并将其从树结构中移除。它还会清除父引用。

如果您想清除节点的所有子节点，请调用 `removeAll()`。这将移除每个子节点，清除它们的父引用，并清空子节点列表。

每个节点支持使用 `setUserData(Object key, Object data)` 在服务器端存储附加信息。这让您可以在不向客户端或 UI 暴露此数据的情况下，将任意元数据或引用与节点关联。

:::tip 使用演示编辑节点文本
在演示中，双击节点以打开其文本的编辑器。输入新文本并保存，以更新树中节点的标签。
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/frontend/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## 图标 {#icons}

图标提供关于节点表示及其状态的视觉提示。它们提高可读性，通过一眼区分节点类型和选择状态。`Tree` 组件支持全局设置默认图标、为每个节点自定义图标，并切换图标的可见性。

### 全局图标 {#global-icons}

树允许您为折叠组、展开组、叶节点和选定叶节点设置默认图标。

示例：

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip 图标源
图标可以是任何有效的 webforJ [图标](./icon) 定义或通过 webforJ [支持的资源协议](../managing-resources/assets-protocols) 加载的资源文件。
:::

### 每节点图标 {#per-node-icons}

您可以通过为单个节点分配图标来覆盖全局默认设置。这在某些节点表示不同概念时很有用，比如“项目”文件夹或特殊文件。

示例：

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### 图标可见性 {#icon-visibility}

有时，您可能希望隐藏某些组或叶节点的图标，以减少杂乱。该组件允许您全局切换这些类别的可见性，让您在不失去结构的情况下简化树的外观。

示例：

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo
path='/webforj/treeicons'
files={['src/main/java/com/webforj/samples/views/tree/TreeIconsView.java']}
height='320px'
/>

## 节点的展开与折叠 {#node-expansion-and-collapse}

节点可以展开或折叠，以控制树中哪些部分可见。这允许专注于相关部分，并支持诸如懒加载或动态数据更新等场景。

### 展开与折叠操作 {#expand-and-collapse-operations}

树支持通过其键或直接引用来展开和折叠单个节点。您还可以一次展开或折叠节点的所有后代。

这些操作让您控制树中可见的内容，并支持数据的懒加载或聚焦于感兴趣的区域。

示例：

```java
tree.expand(node);
tree.collapse(key);

// 折叠子树
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info 折叠根节点
根节点锚定树但保持隐藏。折叠根节点通常会隐藏所有内容，使树看起来为空。为了避免这种情况，折叠根节点实际上折叠了所有子节点，但内部保持根节点展开，确保树仍然正确显示其内容。
:::

### 懒加载节点 {#lazy-loading-nodes}

树通过响应展开事件支持节点子节点的懒加载。当用户展开一个节点时，您的应用可以动态加载或生成该节点的子节点。这通过仅按需加载可见的树部分来提高性能。

使用 `onExpand` 事件检测节点何时展开。在处理程序内，检查节点的子节点是否是占位符（例如，旋转器或空节点），一旦加载完成就用实际数据替换它们。

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## 选择 {#selection}

选择控制用户选择哪些节点。`Tree` 组件支持灵活的模式和 API 来选择、取消选择和查询节点。

### 选择模式 {#selection-modes}

您可以选择树是否允许一次选择一个节点或同时选择多个节点。从多个选择切换到单个选择会自动取消选择除第一个选中的节点之外的所有节点。

示例：

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip 多选交互
当树设置为多重选择模式时，用户可以一次选择多个节点。这种工作方式取决于设备：

* **桌面（鼠标和键盘）：** 用户按住 **Ctrl** 键（或在 macOS 上的 **Cmd** 键）并单击节点以将其添加到当前选择或从中删除。这允许选择多个单独的节点而不取消选择其他节点。
* **移动和触摸设备：** 由于没有修改键，用户只需轻点节点以选择或取消选择它们。每次轻点都会切换该节点的选择状态，通过简单的轻点实现轻松的多选。
:::

### 选择和取消选择 {#selecting-and-deselecting}

可以通过引用、键、单独或批量选择或取消选择节点。您也可以在一次调用中选择或取消选择节点的所有子节点。

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

您可以利用下面显示的代码获取当前选择：

```java
// 获取选中节点的引用
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// 获取选中节点的键
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## 样式 {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
