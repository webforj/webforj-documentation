---
sidebar_position: 25
title: 大型数据集
slug: data
description: >-
  Handle large Table datasets efficiently with virtual scrolling, dynamic row
  loading, DOM recycling, and overscan tuning.
_i18n_hash: 63e45952d3d3f88d3aa435435c0905e6
---
## 虚拟滚动 {#virtual-scrolling}

`Table` 组件通过使用虚拟滚动，高效处理大型数据集。虚拟滚动是一种用于 Web 应用的技术，它通过仅渲染屏幕上可见的列表项或表格行，优化大型列表或表格的渲染效率和性能。

### 初始渲染 {#initial-render}

虚拟滚动是一种设计模式：初始时，只渲染适合滚动容器可视区域的一小部分内容。这样可以最大限度地减少创建的 DOM 元素数量，并加快初始渲染速度。

### 动态加载 {#dynamic-loading}
用户向下或向上滚动时，新内容会动态加载到视图中。通常，系统会根据当前滚动位置从数据源获取这些内容。

### 项目回收 {#item-recycling}
虚拟滚动通常会重复使用现有 DOM 元素，而不是为每个项目都创建新的 DOM 元素。当某个项目移出可视区域时，其 DOM 元素会被回收，并重新用于进入可视区域的新项目。此回收过程有助于减少内存使用并提升性能。

### 性能优势： {#performance-benefits}

虚拟滚动的主要优势是提升性能，尤其是在处理大量项目时。它减少了 DOM 操作，并增强了用户界面的整体响应速度。

下面的 `Table` 展示了所有奥运会获胜者——这是一个能充分受益于表格虚拟滚动功能的大型数据集：

<ComponentDemo
path='/webforj/tableolympicwinners'
files={['src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java']}
height='600px'
/>

## Overscan {#overscan}

设置表格的 `Overscan` 属性可决定在可视区域之外渲染多少行。可以使用 `setOverscan(double value)` 方法配置此设置。

较高的 overscan 值可以减少滚动时的渲染频率，但代价是渲染超出当前可视范围的行数。这需要在渲染性能和滚动流畅度之间进行权衡。
