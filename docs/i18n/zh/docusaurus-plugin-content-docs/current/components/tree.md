---
title: Tree
sidebar_position: 150
_i18n_hash: dacd1e2a128f112d2b7e4a4fd7836feb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree` 组件将数据组织为节点的层次结构。每个节点持有一个唯一的键和标签。节点连接形成父子关系。您可以展开或折叠节点以显示或隐藏其子节点。图标清晰地说明您正在查看的节点类型以及节点是否被选中。选择支持同时选择一个或多个节点。

<!-- INTRO_END -->

## 节点模型和树结构 {#node-model-and-tree-structure}

### `TreeNode` 的角色 {#the-role-of-treenode}

树中的每个数据片段都被封装在一个 `TreeNode` 中。该对象保存键、文本标签，并链接到其父节点和子节点。根节点是特别的：它存在于每棵树中，但不可见。它作为所有顶层节点的容器，使得树结构在内部更容易管理。

由于节点保留对其父节点和子节点的引用，遍历树变得简单。无论您想向上、向下移动或按键查找特定节点，连接始终是可访问的。

### 节点创建和管理 {#node-creation-and-management}

节点通过简单的工厂方法创建，提供键和文本或仅提供文本（文本也用作键）。这确保每个节点有效且唯一可识别。

将节点添加到树中涉及对父节点调用 `add()` 或 `insert()`。这些方法处理分配父引用并通知树更新其用户界面的工作。

示例：

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info 仅单一父节点
尝试将同一节点分配给多个父节点将导致抛出异常。此保护措施确保树保持适当的层次结构，防止节点具有多个父节点，这将破坏结构的完整性并导致意外行为。
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### 修改节点 {#modifying-nodes}

您可以通过调用 `setText(String text)` 更新节点的标签。此方法更改在树中显示的节点文本。

要移除特定的子节点，请使用 `remove(TreeNode child)`。这会将子节点从其父节点中分离，并将其从树结构中移除。它还会清除父引用。

如果您想清除节点所有子节点，可以调用 `removeAll()`。这会移除每个子节点，清除它们的父引用，并清空子节点列表。

每个节点支持使用 `setUserData(Object key, Object data)` 在服务器端存储附加信息。这使您可以将任意元数据或引用与节点关联，而无需将这些数据暴露给客户端或用户界面。

:::tip 使用演示来编辑节点文本
在演示中，双击一个节点以打开其文本的编辑器。输入新文本并保存以更新树中节点的标签。
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/resources/static/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## 图标 {#icons}

图标提供关于节点表示及其状态的视觉提示。它们通过区分节点类型和选择状态来提高可读性。`Tree` 组件支持全局设置默认图标、自定义每个节点的图标以及切换图标可见性。

### 全局图标 {#global-icons}

树允许您为折叠组、展开组、叶节点和选中叶设置默认图标。

示例：

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip 图标来源
图标可以是任何有效的 webforJ [图标](./icon) 定义或通过 webforJ [支持的资源协议](../managing-resources/assets-protocols) 加载的资源文件。
:::

### 每个节点的图标 {#per-node-icons}

您可以通过将图标分配给单个节点来覆盖全局默认值。这在某些节点代表不同概念时非常有用，例如“项目”文件夹或特殊文件。

示例：

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### 图标可见性 {#icon-visibility}

有时，您可能希望隐藏组或叶的图标以减少杂乱。此组件允许您在全球范围内切换这些类别的可见性，让您简化树的外观而不失去结构。

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

节点可以展开或折叠，以控制树中哪些部分可见。这允许专注于相关部分，并支持懒加载或动态数据更新等场景。

### 展开和折叠操作 {#expand-and-collapse-operations}

树支持通过节点的键或直接引用展开和折叠个别节点。您还可以一次展开或折叠节点的所有后代。

这些操作让您控制树的可见程度，并支持数据的懒加载或聚焦于感兴趣的区域。

示例：

```java
tree.expand(node);
tree.collapse(key);

// collapse sub trees
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info 折叠根节点
根节点锚定树，但保持隐藏。折叠根节点通常会隐藏所有内容，使树看起来为空。为了避免这种情况，折叠根节点实际上是折叠所有子节点，但保持根节点在内部展开，以确保树仍然正确显示其内容。
:::

### 懒加载节点 {#lazy-loading-nodes}

树支持通过响应展开事件进行节点子级的懒加载。当用户展开一个节点时，您的应用可以动态加载或生成该节点的子级。这通过按需加载树的可见部分来提高性能。

使用 `onExpand` 事件检测节点何时被展开。在处理程序内部，检查节点的子节点是否是占位符（例如，旋转器或空节点），并在加载完成后用实际数据替换它们。

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## 选择 {#selection}

选择控制用户选择哪些节点。`Tree` 组件支持灵活的模式和 API 来选择、取消选择和查询节点。

### 选择模式 {#selection-modes}

您可以选择树是允许一次选择单个节点还是同时选择多个节点。从多个选择切换到单个选择将自动取消选择除了第一个选定节点以外的所有节点。

示例：

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip 多选交互
当树设置为多选模式时，用户可以一次选择多个节点。其工作方式取决于设备：

* **桌面（鼠标和键盘）：** 用户按住 **Ctrl** 键（或 macOS 上的 **Cmd** 键）并单击节点以将其添加或移除当前选择。这允许选择多个单独节点而不取消选择其他节点。
* **移动和触摸设备：** 因为没有可用的修饰键，用户只需点击节点来选择或取消选择它们。每次点击切换该节点的选择状态，使得通过简单的点击轻松实现多选。
:::

### 选择和取消选择 {#selecting-and-deselecting}

可以通过引用、键、个别或批量方式选择或取消选择节点。您还可以一次选择或取消选择节点的所有子节点。

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

您可以利用下面显示的代码获取当前选择：

```java
// get the reference of selected node
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// get the key of selected node
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
