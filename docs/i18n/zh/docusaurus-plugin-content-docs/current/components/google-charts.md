---
title: Google Charts
sidebar_position: 50
_i18n_hash: 7421699c19919de6aab7db8a36123524
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

`GoogleChart` 组件将 [Google Charts](https://developers.google.com/chart) 库集成到 webforJ 中，让你可以访问条形图、折线图、饼图、地理图等图表类型。图表使用 Java 配置，使用类型、数据集和控制外观和行为的选项映射进行设置。

<!-- INTRO_END -->

## 创建图表 {#creating-a-chart}

:::info 导入 Google Charts
要在你的应用中使用 `GoogleChart` 类，请在 POM 文件中使用以下 XML：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

要创建图表，请指定图表类型、配置其视觉选项，并提供要显示的数据。

此示例创建了一个地理图，显示不同国家的收入数据，具有自定义颜色、图例位置和图表区域大小：

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>

## 图表类型 {#chart-types}

`GoogleChart` 附加组件提供了各种图表类型，以满足各种数据可视化需求。选择合适的图表类型对于有效传达数据的故事至关重要。请查看以下画廊，了解可用于 webforJ 应用的常见图表示例。

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## 选项 {#options}

`GoogleChart` 附加组件通过多种选项实现广泛的自定义。这些选项允许你根据应用的需要调整图表的外观和功能。选项作为 `Map<String, Object>` 传递给图表的 `setOptions()` 方法。

以下是设置图表选项的示例：

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "每月收入");
options.put("backgroundColor", "#EFEFEF");

// 将选项应用于图表
chart.setOptions(options);
```

有关特定图表可用选项的更多信息，请参见 [Google Visualization API 参考 (图表画廊)](https://developers.google.com/chart/interactive/docs/gallery)。

## 设置数据 {#setting-data}

使用 `GoogleChart` 可视化数据需要正确构造和设置数据。本指南将指导您准备数据并将其应用于图表。

### 基本数据设置 {#basic-data-setup}

定义数据的最简单方法是使用 `List<Object>`，每一行都是值的列表。

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("任务", "每日日数"));
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

// 标头行
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

一旦准备好数据，就可以使用 setData 方法将其应用于 GoogleChart。

<ComponentDemo 
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### 从 JSON 加载数据和选项 {#loading-data-and-options-from-json}

你还可以使用 Gson 从 JSON 文件加载数据和选项，以便于管理。这种方法有助于保持数据和选项的组织性，并且易于更新。

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("年份", "销售", "支出"));
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

在响应数据更改、用户交互或视觉选项调整时，刷新或更新图表外观非常简单，可以使用 `redraw()` 方法。此方法可确保图表在数据或设置的任何修改后保持准确和视觉对齐。

在以下场景中调用 `redraw()`：

- **数据修改后**：确保图表反映其数据源的任何更新。
- **更改选项时**：将新样式或配置更改应用于图表。
- **进行响应调整**：当容器的尺寸发生变化时，调整图表的布局或大小，确保在各种设备上的最佳显示。

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## 将图表导出为图像 {#exporting-charts-as-images}

`getImageUri()` 方法提供了将 Google Charts 导出为 base64 编码的 PNG 图像的方法。此方法在将图表共享到网络环境外部、嵌入到电子邮件或文档中，或仅用于存档时特别有用。

在图表完全渲染后调用 `getImageUri()`。通常，此方法在 "ready" 事件监听器中使用，以确保图表准备好进行导出：

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // 现在可以使用 imageUri，例如作为 img 标签的 src 属性
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

当用户在 Google Chart 组件中选择数据点或段时，会触发 `GoogleChartSelectedEvent`。此事件允许与所选图表数据进行交互，并提供有关所选内容的详细信息。可以通过在 `GoogleChart` 实例上使用 `addSelectedListener()` 方法进行监听。

`GoogleChartSelectedEvent` 在用户与图表进行交互时非常有用。

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// 为图表添加选择监听器
chart.addSelectedListener(event -> {
  // 获取选择
  List<Object> selection = chart.getSelection();
  
  // 处理选择事件
  if (!selection.isEmpty()) {
    System.out.println("选择的行: " + selection.get(0));
    // 根据选择的行/列进行进一步处理
  }
});
```

### 负载 {#payload}
`GoogleChartSelectedEvent` 提供对选择数据的访问，可以通过图表对象上的 `getSelection()` 方法检索该数据。此方法返回一个对象列表，每个对象包含以下属性：

- **row**：在图表的数据表中所选行的索引。
- **column**：在数据表中的列索引，可选且适用于允许选择单个单元格的图表，如表格图。

对于饼图或条形图类图表，通常只提供 `row`，指示所选数据点。

以下是负载示例：
```java
[
  {
    "row": 3,  // 数据中的所选行索引
    "column": 2  // （可选）所选列的索引
  }
]
```

:::info 选择多个数据点
如果用户选择多个数据点，`getSelection()` 方法将返回一个对象数组，每个对象表示一个所选元素。负载可能会根据图表类型和用户执行的交互而有所不同。
:::
