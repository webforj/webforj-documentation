---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: a8c510d518375e324ae1f1f0c95b5004
---
## 虚拟滚动 {#virtual-scrolling}

`Table` 组件旨在通过利用虚拟滚动来高效处理大型数据集，这是一种在Web应用程序中使用的技术，通过仅渲染屏幕上可见的项目来优化大型列表或表格的渲染和性能。

### 初始渲染 {#initial-render}

虚拟滚动是一种设计模式，其中最初仅渲染适合可滚动容器可见区域的小部分项目。这最小化了创建的DOM元素数量并加快了初始渲染过程。

### 动态加载 {#dynamic-loading}
当用户向下或向上滚动时，新项目会动态加载到视图中。这些项目通常根据当前的滚动位置从数据源中获取。

### 项目回收 {#item-recycling}
虚拟滚动通常不会为每个项目创建新的DOM元素，而是重用现有的DOM元素。当一个项目移出可见区域时，它的DOM元素会被回收并重新用于进入可见区域的新项目。这个回收过程有助于减少内存使用并提高性能。

### 性能优势: {#performance-benefits}

虚拟滚动的主要优点是提高了性能，尤其是在处理大量项目时。它减少了DOM操作的数量，增强了用户界面的整体响应性。

下面的 `Table` 显示了所有的奥林匹克获胜者 - 这是一个大型数据集，受益于表格的虚拟滚动功能：

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 超视图 {#overscan}

设置表格的 `Overscan` 属性决定了在可见区域外渲染多少行。此设置可以通过 `setOverscan(double value)` 方法进行配置。

较高的超视图值可以减少在滚动时的渲染频率，但代价是渲染的行数超过任何时间点可见的行数。这可能在渲染性能和滚动平滑性之间进行权衡。
