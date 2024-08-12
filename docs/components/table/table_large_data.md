---
sidebar_position: 6
title: Large Data Sets
slug: data
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';

## Virtual Scrolling

The `Table` component is built to efficiently handle large datasets by utilizing virtual scrolling, which  is a technique used in web applications to optimize the rendering and performance of large lists or tables by rendering only the visible items on the screen.

#### Initial Render

Virtual scrolling is a design pattern in which, initially, only a small subset of items that fit within the visible area of the scrollable container is rendered. This minimizes the amount of DOM elements created and speeds up the initial rendering process.

#### Dynamic Loading
As the user scrolls down or up, new items are dynamically loaded into the view. These items are typically fetched from the data source based on the current scroll position.

#### Item Recycling
Instead of creating a new DOM element for each item, virtual scrolling often reuses existing DOM elements. As an item moves out of the visible area, its DOM element is recycled and repurposed for a new item entering the visible area. This recycling process helps to reduce memory usage and improves performance.

#### Performance Benefits:

The main advantage of virtual scrolling is improved performance, especially when dealing with a large number of items. It reduces the amount of DOM manipulation and enhances the overall responsiveness of the user interface.

The below `Table` shows all olympic winners - a large dataset that benefits greatly from the table's virtual scrolling functionality:

<ComponentDemo
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.tabledemos.TableOlympicWinners' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/TableOlympicWinners.java'
urls={['https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/tabledemos/Service.java']}
height='600px'
/>

### Overscan

Setting the table's `Overscan` property determines how many rows to render outside of the visible area. This setting can be configured with the `setOverscan(double value)` method.

A higher overscan value can reduce the frequency of rendering when scrolling, but at the cost of rendering more rows than are visible at any one time. This can be a trade-off between rendering performance and scroll smoothness.