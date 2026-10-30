---
title: Tree
sidebar_position: 150
description: >-
  Display hierarchical data with the Tree component, using TreeNode parent-child
  links, expand or collapse, icons, and selection.
_i18n_hash: cacef36e8323b6349fa209a7c057c88b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree` 组件以节点的层级结构组织数据。每个节点都有一个唯一的键和标签。节点通过连接形成父子关系。您可以展开或折叠节点以显示或隐藏其子节点。图标可以明确您正在查看的节点类型以及其是否被选中。选择支持一次选择一个节点或多个节点。

<!-- INTRO_END -->

## 节点模型和树结构 {#node-model-and-tree-structure}

### `TreeNode` 的角色 <DocChip chip="name" label="dwc-tree-node" /> {#the-role-of-treenode}

树中的每一条数据都被封装在一个 `TreeNode` 中。该对象包含了键、文本标签，并链接到其父节点和子节点。根节点是特殊的：它存在于每棵树中，但不可见。它充当所有顶级节点的容器，使树结构在内部管理上更容易。

由于节点保留对其父节点和子节点的引用，遍历树变得简单。无论您想向上、向下移动，还是根据键查找特定节点，连接始终是可访问的。

### 节点创建和管理 {#node-creation-and-management}

通过提供键和文本或仅提供文本（文本也作为键）可以使用简单的工厂方法创建节点。这确保每个节点都是有效的并具有唯一标识。

将节点添加到树中涉及在父节点上调用 `add()` 或 `insert()`。这些方法处理分配父引用并通知树更新其 UI。

示例：

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info 仅允许单一父节点
尝试将同一个节点分配给多个父节点将导致抛出异常。此保护措施确保树保持正确的层级结构，防止节点具有多个父节点，这将破坏结构的完整性并导致意外行为。
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### 修改节点 {#modifying-nodes}

通过调用 `setText(String text)` 可以更新节点的标签。此方法更改在树中显示的节点文本。

要移除特定的子节点，请使用 `remove(TreeNode child)`。这将子节点从其父节点中分离并从树结构中移除。它还清除了父引用。

如果要清除节点的所有子节点，请调用 `removeAll()`。这将移除每个子节点，清除它们的父引用，并清空子节点列表。

每个节点支持在服务器端存储附加信息，使用 `setUserData(Object key, Object data)`。这使您可以将任意元数据或引用与节点关联，而不会将这些数据暴露给客户端或 UI。

:::tip 使用演示编辑节点文本
在演示中，双击节点以打开其文本的编辑器。输入新文本并保存以更新树中的节点标签。
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

图标提供了关于节点表示和状态的视觉提示。它们通过一眼区分节点类型和选择状态来提高可读性。`Tree` 组件支持全局设置默认图标，为每个节点自定义图标，并切换图标的可见性。

### 全局图标 {#global-icons}

树允许您设置折叠组、展开组、叶子节点和选定叶子的默认图标。

示例：

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip 图标来源
图标可以是任何有效的 webforJ [图标](./icon) 定义，或是通过 webforJ [支持的资源协议](../managing-resources/assets-protocols) 加载的资源文件。
:::

### 每节点图标 {#per-node-icons}

您可以通过将图标分配给单个节点来覆盖全局默认值。这在某些节点代表不同概念时非常有用，如“项目”文件夹或特殊文件。

示例：

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### 图标可见性 {#icon-visibility}

有时，您可能希望隐藏组或叶子的图标，以减少杂乱。该组件允许您全局切换这些类别的可见性，从而简化树的外观而不失去结构。

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

## 节点展开和折叠 {#node-expansion-and-collapse}

节点可以被展开或折叠，以控制树的哪些部分是可见的。这允许集中注意相关部分，并支持懒加载或动态数据更新的场景。

### 展开和折叠操作 {#expand-and-collapse-operations}

树支持通过其键或直接引用展开和折叠单个节点。您还可以一次性展开或折叠节点的所有子节点。

这些操作让您控制树的可见部分，并支持数据的懒加载或专注于感兴趣的区域。

示例：

```java
tree.expand(node);
tree.collapse(key);

// 折叠子树
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info 折叠根节点
根节点锚定树，但保持隐藏。折叠根节点通常会隐藏所有内容，使树看起来为空。为避免这种情况，折叠根节点实际上会折叠它的所有子节点，但内部保持根节点展开，确保树仍然正确显示其内容。
:::

### 懒加载节点 {#lazy-loading-nodes}

树支持通过响应展开事件进行节点子项的懒加载。当用户展开节点时，您的应用可以动态加载或生成该节点的子项。这通过在需要时加载可见的树部分来提高性能。

使用 `onExpand` 事件来检测节点何时被展开。在处理程序中，检查节点的子项是否为占位符（例如，加载指示器或空节点），并在加载后用实际数据替换它们。

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## 选择 {#selection}

选择控制用户选择了哪些节点。`Tree` 组件支持灵活的模式和 API，以选择、取消选择和查询节点。

### 选择模式 {#selection-modes}

您可以选择树是否允许同时选择一个节点或多个节点。从多个选择切换到单一选择时，自动取消选择除第一个选中的节点之外的所有节点。

示例：

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip 多选择交互
当树设置为多选模式时，用户可以一次选择多个节点。具体操作取决于设备：

* **桌面（鼠标和键盘）：** 用户按住 **Ctrl** 键（在 macOS 上为 **Cmd** 键）并单击节点以将其添加或从当前选择中移除。这允许在不取消选择其他节点的情况下选择多个单独的节点。
* **移动设备和触摸设备：** 由于没有修饰键，用户只需轻触节点以选择或取消选择它们。每次点击都会切换该节点的选择状态，从而通过简单的点击启用轻松的多选择。
:::

### 选择和取消选择 {#selecting-and-deselecting}

节点可以通过引用、键、单独或批量进行选择或取消选择。您还可以在一次调用中选择或取消选择节点的所有子节点。

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
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## 样式 {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
