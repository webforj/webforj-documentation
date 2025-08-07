---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 83619419eb87c85aa5e309ee153af7fb
---
## 虚拟滚动 {#virtual-scrolling}

`Table` 组件旨在通过利用虚拟滚动有效处理大型数据集，虚拟滚动是一种在Web应用程序中优化大型列表或表格的渲染和性能的技术，仅渲染屏幕上可见的项目。

### 初始渲染 {#initial-render}

虚拟滚动是一种设计模式，在这种模式下，最初仅渲染适合滚动容器可见区域的小部分项目。这最小化了创建的DOM元素数量，并加快了初始渲染过程。

### 动态加载 {#dynamic-loading}
当用户向下或向上滚动时，新项目会动态加载到视图中。这些项目通常根据当前的滚动位置从数据源中获取。

### 项目回收 {#item-recycling}
虚拟滚动通常不会为每个项目创建新的DOM元素，而是重用现有的DOM元素。当一个项目移出可见区域时，其DOM元素被回收并重新用作进入可见区域的新项目。此回收过程有助于减少内存使用并提高性能。

### 性能优势: {#performance-benefits}

虚拟滚动的主要优点是提高性能，尤其是在处理大量项目时。它减少了DOM操作的数量，并增强了用户界面的整体响应性。

下方的 `Table` 显示所有奥运会获胜者 - 一个受益于表格虚拟滚动功能的大型数据集：

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## 超拉 {#overscan}

设置表格的 `Overscan` 属性决定了要在可见区域外渲染多少行。此设置可以通过 `setOverscan(double value)` 方法配置。

更高的超拉值可以减少滚动时渲染的频率，但代价是渲染的行数超过了任何时刻可见的行数。这可能是渲染性能与滚动流畅性之间的权衡。
