---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: a733a52b4d9ffb87eae039e9729b9cb9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

`GoogleChart` 组件将 [Google Charts](https://developers.google.com/chart) 库集成到 webforJ 中，让您可以访问柱状图、折线图、饼图、地理图等多种图表类型。图表使用 Java 进行配置，采用类型、数据集和控制外观和行为的选项映射。

<!-- INTRO_END -->

## 创建图表 {#creating-a-chart}

:::info 导入 Google Charts
要在您的应用程序中使用 `GoogleChart` 类，请在 POM 文件中使用以下 XML：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

要创建图表，请指定图表类型，配置其视觉选项，并提供要显示的数据。

以下示例创建一个地理图表，映射不同国家的收入数据，设置自定义颜色、图例位置和图表区域大小：

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## 图表类型 {#chart-types}

`GoogleChart` 附加组件提供了一系列全面的图表类型，以满足各种数据可视化需求。选择合适的图表类型对于有效传达数据的故事至关重要。请查看下面的画廊，以查看可以在 webforJ 应用程序中使用的常见图表示例。

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## 选项 {#options}

`GoogleChart` 附加组件通过多种选项实现广泛的自定义。这些选项允许您调整图表的外观和功能，以适应您的应用需求。选项作为 `Map<String, Object>` 传递给图表的 `setOptions()` 方法。

以下是设置图表选项的示例：

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "每月收入");
options.put("backgroundColor", "#EFEFEF");

// 将选项应用于图表
chart.setOptions(options);
```

有关特定图表可用选项的更多信息，请参见 [Google Visualization API 参考 (图表库)](https://developers.google.com/chart/interactive/docs/gallery)。

## 设置数据 {#setting-data}

使用 `GoogleChart` 可视化数据需要正确构建和设置数据。本指南将指导您准备数据并将其应用于图表。

### 基本数据设置 {#basic-data-setup}

定义数据的最简单方法是使用 `List<Object>`，每一行都是一个值列表。

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("任务", "每天小时数"));
data.add(Arrays.asList("工作", 11));
data.add(Arrays.asList("吃饭", 2));
data.add(Arrays.asList("通勤", 2));
data.add(Arrays.asList("看电视", 2));
data.add(Arrays.asList("睡觉", 7));
chart.setData(data);
```

### 使用映射表示更复杂的结构 {#using-maps-for-more-complex-structures}

对于更复杂的数据结构，可以使用映射来表示行，然后将其转换为所需的格式。

```java
List<Object> data = new ArrayList<>();

// 表头
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

一旦数据准备好，就可以使用 setData 方法将其应用到 GoogleChart。

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### 从 JSON 加载数据和选项 {#loading-data-and-options-from-json}

您还可以使用 Gson 从 JSON 文件加载数据和选项，以便于管理。这种方法有助于保持数据和选项的组织，使其易于更新。

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("年份", "销售", "费用"));
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

通过 `redraw()` 方法可以轻松刷新或更新图表的外观，以响应数据更改、用户交互或视觉选项调整。此方法确保图表保持准确，视觉上与基础数据或其设置的任何修改保持一致。

在以下情况下调用 `redraw()`：

- **数据修改后**：确保图表反映其数据源的任何更新。
- **更改选项时**：将新的样式或配置更改应用到图表。
- **进行响应式调整**：当容器的尺寸发生变化时调整图表的布局或大小，以确保在各种设备上获得最佳显示。

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## 导出图表为图像 {#exporting-charts-as-images}

`getImageUri()` 方法提供了一种将 Google Charts 导出为 base64 编码的 PNG 图像的方法。此方法对于在网络环境之外共享图表、将其嵌入电子邮件或文档，或仅用于归档目的特别有用。

在图表完全渲染后，在图表实例上调用 `getImageUri()`。通常，此方法在“准备”事件监听器中使用，以确保图表已准备好导出：

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // 现在可以将 imageUri 用作 img 标签的 src 属性
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

每当用户选择 Google Chart 组件中的数据点或区段时，会触发 `GoogleChartSelectedEvent`。该事件允许与选定的图表数据进行交互，提供有关所选内容的详细信息。可以通过在 `GoogleChart` 实例上使用 `addSelectedListener()` 方法来侦听该事件。

`GoogleChartSelectedEvent` 在用户需要与图表进行交互的应用程序中特别有用。

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// 向图表添加选择监听器
chart.addSelectedListener(event -> {
  // 获取选择的内容
  List<Object> selection = chart.getSelection();

  // 处理选择事件
  if (!selection.isEmpty()) {
    System.out.println("选定的行: " + selection.get(0));
    // 根据选中行/列进行进一步处理
  }
});
```

### 负载 {#payload}
`GoogleChartSelectedEvent` 提供对选择数据的访问，可以通过图表对象上的 `getSelection()` 方法检索该数据。该方法返回一个对象列表，其中每个对象包含以下属性：

- **row**：在图表数据表中所选行的索引。
- **column**：数据表中列的索引（可选），适用于允许选择单元格的图表，例如表格图表。

对于饼图或条形图等图表，通常只提供 `row`，表示所选数据点。

以下是负载示例：
```java
[
  {
    "row": 3,  // 数据中所选行的索引
    "column": 2  // （可选）所选列的索引
  }
]
```

:::info 选择多个数据点
如果用户选择多个数据点，`getSelection()` 方法将返回一个对象数组，每个对象表示一个选定的元素。负载可能因图表类型和用户执行的交互而异。
:::
