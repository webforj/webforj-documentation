---
title: Google Charts
sidebar_position: 50
_i18n_hash: b477c90cfb24a59329f3047d7ae7d24c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- 组件的简要概述及其功能 -->

:::info 导入 Google Charts
要在应用中使用 `GoogleChart` 类，请在您的 POM 文件中使用以下 XML：

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

`GoogleChart` 类是一种全面的解决方案，用于在 Web 应用程序中嵌入丰富的互动图表。该类充当 [Google Charts](https://developers.google.com/chart) 库的桥梁，提供适合任何数据可视化任务的多种图表类型。

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>


## 图表类型 {#chart-types}

`GoogleChart` 附加组件提供多种图表类型，以满足各种数据可视化需求。选择合适的图表类型对于有效传达数据故事至关重要。请查看下面的图库，了解可以在 webforJ 应用中使用的常见图表示例。

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## 选项 {#options}

`GoogleChart` 附加组件通过多种选项实现广泛的自定义。这些选项允许您根据应用的需求调整图表的外观和功能。选项作为 `Map<String, Object>` 传递给图表的 `setOptions()` 方法。

以下是设置图表选项的示例：

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "月收入");
options.put("backgroundColor", "#EFEFEF");

// 将选项应用于图表
chart.setOptions(options);
```

有关特定图表可用选项的更多信息，请参阅 [Google Visualization API 参考 (图表库)](https://developers.google.com/chart/interactive/docs/gallery)。

## 设置数据 {#setting-data}

使用 `GoogleChart` 可视化数据需要正确构建和设置数据。本文将指导您准备数据并将其应用于图表。

### 基本数据设置 {#basic-data-setup}

定义数据的最简单方法是使用 `List<Object>`，其中每一行都是一个值的列表。

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("任务", "每日小时"));
data.add(Arrays.asList("工作", 11));
data.add(Arrays.asList("吃饭", 2));
data.add(Arrays.asList("通勤", 2));
data.add(Arrays.asList("看电视", 2));
data.add(Arrays.asList("睡觉", 7));
chart.setData(data);
```

### 使用映射处理更复杂的结构 {#using-maps-for-more-complex-structures}

对于更复杂的数据结构，您可以使用映射表示行，然后将其转换为所需格式。

```java
List<Object> data = new ArrayList<>();

// 头部行
data.add(Arrays.asList("国家", "收入"));

// 数据行
Map<String, Object> row1 = Map.of("国家", "德国", "收入", 1000);
Map<String, Object> row2 = Map.of("国家", "美国", "收入", 1170);
Map<String, Object> row3 = Map.of("国家", "巴西", "收入", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

一旦数据准备好，它可以通过 `setData` 方法应用于 GoogleChart。

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### 从 JSON 加载数据和选项 {#loading-data-and-options-from-json}

您还可以使用 Gson 从 JSON 文件加载数据和选项，以便于管理。这种方法有助于保持数据和选项的组织性，便于更新。

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("年份", "销售额", "支出"));
data.add(Arrays.asList("2013", 1000, 400));
data.add(Arrays.asList("2014", 1170, 460));
data.add(Arrays.asList("2015", 660, null)); 
data.add(Arrays.asList("2016", 1030, 540));
chart.setData(data);

Map<String, Object> options = new Gson().fromJson(
    Assets.contentOf("options.json"),
    new TypeToken<Map<String, Object>>() {}.getType()
);
chart.setOptions(options);
```

## 更新图表视觉效果 {#updating-chart-visuals}

在数据更改、用户交互或视觉选项调整后，刷新或更新图表的外观，使用 `redraw()` 方法即可。此方法确保图表准确且与基础数据或任何设置的修改在视觉上保持一致。

在以下情况下调用 `redraw()`：

- **数据修改后**：确保图表反映任何对其数据源的更新。
- **选项更改时**：对图表应用新的样式或配置更改。
- **响应性调整**：在容器尺寸更改时，调整图表的布局或大小，以确保在设备间的最佳显示。

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## 将图表导出为图像 {#exporting-charts-as-images}

`getImageUri()` 方法提供了一种将 Google Charts 导出为 base64 编码的 PNG 图像的方法。此方法对于在 Web 环境外共享图表、将其嵌入电子邮件或文档中，或仅仅用于归档目的非常有用。

在图表完全渲染后，调用 `getImageUri()`。通常，此方法在 "ready" 事件监听器中使用，以确保图表准备好导出：

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // 现在您可以使用 imageUri，例如，作为 img 标签的 src 属性
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

`GoogleChartSelectedEvent` 在用户选择 Google Chart 组件中的数据点或分段时触发。此事件允许与选定的图表数据进行交互，提供有关所选内容的详细信息。可以通过在 `GoogleChart` 实例上使用 `addSelectedListener()` 方法来监听该事件。

`GoogleChartSelectedEvent` 在用户与图表互动时非常有用。

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// 向图表添加选择监听器
chart.addSelectedListener(event -> {
    // 获取选择
    List<Object> selection = chart.getSelection();
    
    // 处理选定事件
    if (!selection.isEmpty()) {
        System.out.println("选定行: " + selection.get(0));
        // 基于选择的行/列进行进一步处理
    }
});
```

### 负载 {#payload}
`GoogleChartSelectedEvent` 提供对选择数据的访问，可以使用图表对象上的 `getSelection()` 方法检索。该方法返回一个对象列表，其中每个对象包含以下属性：

- **row**：图表数据表中被选中行的索引。
- **column**：数据表中列的索引，这是可选的，并适用于允许选择单个单元格的图表，例如表格图。

对于饼图或条形图等图表，通常只提供 `row`，指示所选数据点。

以下是负载的示例：
```java
[
  {
    "row": 3,  // 数据中选定的行索引
    "column": 2  // （可选）选定的列索引
  }
]
```

:::info 选择多个数据点
如果用户选择多个数据点，`getSelection()` 方法将返回一个对象数组，每个对象代表一个选择的元素。负载可能会根据图表类型和用户执行的交互而有所变化。
:::
