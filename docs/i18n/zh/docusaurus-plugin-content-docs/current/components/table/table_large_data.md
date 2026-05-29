---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 9431d33c6fea2dd9d4ff4b165877e7d5
---
## 虚拟滚动 {#virtual-scrolling}

`Table` 组件旨在通过利用虚拟滚动来有效处理大型数据集，虚拟滚动是一种用于网页应用程序的技术，通过仅渲染屏幕上可见的项来优化大型列表或表格的渲染和性能。

### 初始渲染 {#initial-render}

虚拟滚动是一种设计模式，其中最初仅渲染适合可滚动容器可见区域的小部分项。这最小化了创建的 DOM 元素数量，并加快了初始渲染过程。

### 动态加载 {#dynamic-loading}
当用户向下或向上滚动时，新的项目会动态加载到视图中。这些项目通常基于当前滚动位置从数据源获取。

### 项目回收 {#item-recycling}
虚拟滚动通常不会为每个项目创建新的 DOM 元素，而是重用现有的 DOM 元素。当一个项目移出可见区域时，它的 DOM 元素被回收并被重新用于一个进入可见区域的新项目。这个回收过程有助于减少内存使用并提高性能。

### 性能优势: {#performance-benefits}

虚拟滚动的主要优势是性能提升，尤其是在处理大量项时。它减少了 DOM 操作的数量，增强了用户界面的整体响应性。

下面的 `Table` 显示了所有奥运赢家 - 一个可以从表的虚拟滚动功能中获益匪浅的大型数据集：

<ComponentDemo
path='/webforj/tableolympicwinners'
files={[
  'src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## 超扫描 {#overscan}

设置表的 `Overscan` 属性决定了在可见区域之外渲染多少行。该设置可以使用 `setOverscan(double value)` 方法进行配置。

较高的超扫描值可以减少滚动时的渲染频率，但代价是渲染的行数超过任何时刻可见的行数。这可能是在渲染性能和滚动流畅性之间的权衡。
