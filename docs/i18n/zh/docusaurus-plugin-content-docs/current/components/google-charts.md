---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: 8e1916e526ef45c55ca75c6cd32a4e7f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

`GoogleChart` 组件将 [Google Charts](https://developers.google.com/chart) 库整合到 webforJ 中，使您可以访问条形图、折线图、饼图、地理图等图表类型。图表使用 Java 进行配置，涉及一个类型、一个数据集和一个控制外观和行为的选项映射。

<!-- INTRO_END -->

## 创建图表 {#creating-a-chart}

:::info 导入 Google Charts
要在您的应用中使用 `GoogleChart` 类，请在 POM 文件中使用以下 XML：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

要创建图表，请指定图表类型、配置其视觉选项，并提供要显示的数据。

此示例创建一个映射不同国家收入数据的地理图，具有自定义颜色、图例位置和图表区域大小：

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## 图表类型 {#chart-types}

`GoogleChart` 插件提供了一系列全面的图表类型，以满足各种数据可视化需求。选择适当的图表类型对于有效传达数据的故事至关重要。请参阅下面的画廊以获取可以在 webforJ 应用中使用的常见图表示例。

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## 选项 {#options}

`GoogleChart` 插件通过多种选项实现广泛的自定义。这些选项允许您根据应用的需求量身定制图表的外观和功能。选项作为 `Map<String, Object>` 传递给图表的 `setOptions()` 方法。

以下是设置图表选项的示例：

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "每月收入");
options.put("backgroundColor", "#EFEFEF");

// 将选项应用于图表
chart.setOptions(options);
```

有关特定图表可用选项的更多信息，请参阅 [Google Visualization API 参考 (图表画廊)](https://developers.google.com/chart/interactive/docs/gallery)。

## 设置数据 {#setting-data}

使用 `GoogleChart` 可视化数据需要正确结构化和设置数据。本指南将引导您准备数据并将其应用于图表。

### 基本数据设置 {#basic-data-setup}

定义数据的最简单方法是使用 `List<Object>`，其中每行是一个值列表。

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

### 使用映射进行更复杂的结构 {#using-maps-for-more-complex-structures}

对于更复杂的数据结构，您可以使用映射来表示行，然后将其转换为所需格式。

```java
List<Object> data = new ArrayList<>();

// 表头行
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

准备好数据后，可以使用 setData 方法将其应用于 GoogleChart。

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### 从 JSON 加载数据和选项 {#loading-data-and-options-from-json}

您还可以使用 Gson 从 JSON 文件加载数据和选项，以便更轻松地管理。这种方法有助于保持数据和选项的组织和易于更新。

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("年份", "销售额", "费用"));
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

`GoogleChart` 会在数据、选项、类型或选择更改时自动更新。您无需在使用 `setData()`、`setOptions()`、`setType()` 或 `setSelection()` 后调用 `redraw()`。

该组件还会监听浏览器窗口调整大小事件，并自动重新绘制以适应新尺寸。

对于没有触发窗口调整大小事件的容器大小更改，请调用 `redraw()`。例如，扩展面板和调整侧边栏大小。请在容器的新尺寸应用后调用它。

以下演示使用 `setData()` 更新图表的数据。当应用新数据时，图表会自动重新绘制。

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## 导出图表为图像 {#exporting-charts-as-images}

`getImageUri()` 方法提供了一种将您的 Google Charts 导出为 base64 编码 PNG 图像的方式。此方法在将图表分享给网络环境外部、嵌入到电子邮件或文档中，或仅仅用于存档时特别有用。

在图表完全渲染后，调用图表实例上的 `getImageUri()`。通常，此方法在 "ready" 事件监听器中使用，以确保图表准备好导出：

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // 现在您可以使用 imageUri，例如，将其作为 img 标签的 src 属性
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

每当用户在 Google Chart 组件中选择数据点或片段时，都会触发 `GoogleChartSelectedEvent`。此事件使与所选图表数据的交互成为可能，提供有关所选内容的详细信息。可以使用 `GoogleChart` 实例上的 `addSelectedListener()` 方法侦听该事件。

`GoogleChartSelectedEvent` 在需要用户与图表交互的应用中非常有用。

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// 将选择监听器添加到图表
chart.addSelectedListener(event -> {
  // 获取选择内容
  List<Object> selection = chart.getSelection();

  // 处理选定事件
  if (!selection.isEmpty()) {
    System.out.println("选定行: " + selection.get(0));
    // 根据选择的行/列进行进一步处理
  }
});
```

### 有效负载 {#payload}
`GoogleChartSelectedEvent` 提供访问选择数据的能力，可以通过图表对象上的 `getSelection()` 方法进行检索。该方法返回一个对象列表，其中每个对象包含以下属性：

- **row**: 在图表数据表中被选中的行的索引。
- **column**: 数据表中列的索引，这个是可选的，适用于允许单元格选择的图表，如表格图。

对于像饼图或条形图这样的图表，通常只提供 `row` ，表示所选的数据点。

以下是有效负载的示例：
```java
[
  {
    "row": 3,  // 数据中选定的行的索引
    "column": 2  // （可选）选定的列的索引
  }
]
```

:::info 选择多个数据点
如果用户选择多个数据点，`getSelection()` 方法将返回一个对象数组，每个对象代表一个所选元素。有效负载可能会根据图表类型和用户执行的交互而有所不同。
:::
