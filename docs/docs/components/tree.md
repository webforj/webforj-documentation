---
title: Tree
sidebar_position: 150
sidebar_class_name: new-content
---

<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<JavadocLink type="tree" location="com/webforj/component/layout/tree/Tree" top='true'/>

The `Tree` component organizes data as a hierarchy of nodes. Each node holds a unique key and a label. Nodes connect to form parent-child relationships. You can expand or collapse nodes to show or hide their children. Icons clarify what kind of node you’re looking at and whether it’s selected. Selection supports choosing one node or many at once.

## Node model and tree structure

### The role of `TreeNode`

Every piece of data in the tree is wrapped in a `TreeNode`. This object holds the key, the text label, and links to its parent and children nodes. The root node is special: it exists in every tree but isn’t visible. It serves as the container for all top-level nodes, making the tree structure easier to manage internally.

Because nodes keep references to their parents and children, traversing the tree is straightforward. Whether you want to move up, down, or find a specific node by key, the connections are always accessible.

### Node creation and management

Nodes are created using simple factory methods, either by providing a key and text or just text (which doubles as the key). This guarantees each node is valid and uniquely identifiable.

Adding nodes to the tree involves calling `add()` or `insert()` on a parent node. These methods handle assigning the parent reference and notifying the tree to update its UI.

Example:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Single Parent Only
Attempting to assign the same node to more than one parent will result in an exception being thrown. This safeguard ensures the tree maintains a proper hierarchy by preventing nodes from having multiple parents, which would break the integrity of the structure and cause unexpected behavior.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Modifying nodes

You update a node’s label by calling `setText(String text)`. This method changes the text shown for the node in the tree.

To remove a specific child node, use `remove(TreeNode child)`. This detaches the child from its parent and removes it from the tree structure. It also clears the parent reference.

If you want to clear all children from a node, call `removeAll()`. This removes every child node, clears their parent references, and empties the children list.

Each node supports storing additional information on the server side using `setUserData(Object key, Object data)`. This lets you associate arbitrary metadata or references with the node, without exposing this data to the client or the UI.

:::tip Using the Demo to Edit Node Text
In the demo, double-click a node to open an editor for its text. Enter the new text and save it to update the node’s label in the tree.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Icons

Icons provide visual cues about what nodes represent and their state. They improve readability by distinguishing node types and selection status at a glance. The `Tree` component supports setting default icons globally, customizing icons per node, and toggling icon visibility.

### Global icons

The tree lets you set default icons for collapsed groups, expanded groups, leaf nodes, and selected leaves.

Example:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Icon Sources
An icon can be any valid webforJ [icon](./icon) definition or a resource file loaded via a webforJ [supported assets protocols](../managing-resources/assets-protocols).
:::

### Per-node icons

You can override global defaults by assigning icons to individual nodes. This is useful when certain nodes represent different concepts, like “project” folders or special files.

Example:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Icon visibility

Sometimes, you might want to hide icons for groups or leaves to reduce clutter. The component lets you toggle visibility globally for these categories, letting you simplify the tree’s appearance without losing structure.

Example:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## Node expansion and collapse

Nodes can be expanded or collapsed to control which parts of the tree are visible. This allows focusing on relevant sections and supports scenarios like lazy loading or dynamic data updates.

### Expand and collapse operations

The tree supports expanding and collapsing individual nodes by either their key or direct reference. You can also expand or collapse all descendants of a node at once.

These operations let you control how much of the tree is visible and support lazy-loading of data or focus on areas of interest.

Example:

```java
tree.expand(node);
tree.collapse(key);

// collapse sub trees
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Collapsing root
The root node anchors the tree but remains hidden. Collapsing the root would normally hide everything, making the tree appear empty. To avoid this, collapsing the root actually collapses all its children but keeps the root expanded internally, ensuring the tree still shows its content correctly.
:::

### Lazy loading nodes

The tree supports lazy loading of node children by reacting to expand events. When a user expands a node, your app can load or generate that node’s children dynamically. This improves performance by loading only visible parts of the tree on demand.

Use the `onExpand` event to detect when a node is expanded. Inside the handler, check if the node’s children are placeholders (for example, a spinner or empty node) and replace them with actual data once loaded.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selection

Selection controls which nodes are chosen by the user. The `Tree` component supports flexible modes and APIs to select, deselect, and query nodes.

### Selection modes

You can choose whether the tree allows selecting a single node at a time or multiple nodes simultaneously. Switching from multiple to single selection automatically deselects all but the first selected node.

Example:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selection interaction
When the tree is set to multiple selection mode, users can select more than one node at a time. The way this works depends on the device:

* **Desktop (mouse and keyboard):** Users hold the **Ctrl** key (or **Cmd** key on macOS) and click nodes to add or remove them from the current selection. This allows selecting multiple individual nodes without deselecting others.
* **Mobile and touch devices:** Since modifier keys aren’t available, users simply tap nodes to select or deselect them. Each tap toggles the selection state of that node, enabling easy multi-selection through simple taps.
:::

### Selecting and deselecting

Nodes can be selected or deselected by reference, key, individually, or in batches. You can also select or deselect all children of a node in one call.

Example:

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

### Selection state retrieval

You can get the current selection:

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

## Styling

### Shadow parts

These are the parts of the [shadow DOM](../glossary#shadow-dom) that can be targeted via CSS:

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Tree} table='parts'/>

### Reflected attributes

These are attributes visible in the DOM, allowing styling via attribute selectors:

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Tree} table="reflects"/>

