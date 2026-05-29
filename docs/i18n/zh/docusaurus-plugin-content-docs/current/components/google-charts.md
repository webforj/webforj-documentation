---
title: Google Charts
sidebar_position: 50
_i18n_hash: 31a5912850ae78f116c6738b99910d25
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

`GoogleChart`组件将[Google Charts](https://developers.google.com/chart)库集成到webforJ中，使您可以访问柱状图、折线图、饼图、地理图等多种图表类型。图表通过Java进行配置，使用类型、数据集和控制外观和行为的选项映射。

<!-- INTRO_END -->

## 创建图表 {#creating-a-chart}

:::info 导入 Google Charts
要在您的应用中使用`GoogleChart`类，请在您的POM文件中使用以下XML：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

要创建图表，请指定图表类型，配置其视觉选项，并提供要显示的数据。

此示例创建一个地理图表，将不同国家的收入数据进行映射，并具有自定义颜色、图例位置和图表区域大小：

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/resources/static/css/googlecharts/chart.css',
]}
height='300px'
/>

## 图表类型 {#chart-types}

`GoogleChart`扩展提供了一系列全面的图表类型，以满足各种数据可视化需求。选择适当的图表类型对有效传达数据的故事至关重要。请参见下面的画廊，查看可以在webforJ应用中使用的常见图表示例。

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/resources/static/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## 选项 {#options}

`GoogleChart`扩展通过多种选项启用广泛的自定义。这些选项使您能够根据应用的需求量身定制图表的外观和功能。选项作为`Map<String, Object>`传递给图表的`setOptions()`方法。

以下是设置图表选项的示例：

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "每月收入");
options.put("backgroundColor", "#EFEFEF");

// 将选项应用于图表
chart.setOptions(options);
```

有关特定图表可用选项的更多信息，请参阅[Google可视化API参考（图表画廊）](https://developers.google.com/chart/interactive/docs/gallery)。

## 设置数据 {#setting-data}

使用`GoogleChart`可视化数据需要正确构造和设置数据。本指南将指导您准备数据并将其应用于图表。

### 基本数据设置 {#basic-data-setup}

定义数据的最简单方法是使用`List<Object>`，其中每一行都是一个值的列表。

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

一旦数据准备好，就可以使用setData方法将其应用于GoogleChart。

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### 从JSON加载数据和选项 {#loading-data-and-options-from-json}

您也可以使用Gson从JSON文件加载数据和选项，以便更轻松管理。这种方法有助于保持数据和选项的组织和易于更新。

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

在响应数据变化、用户交互或视觉选项调整时，刷新或更新图表的外观是通过`redraw()`方法轻松实现的。该方法确保您的图表始终准确并与底层数据或任何设置修改视觉对齐。

在以下场景中调用`redraw()`：

- **在数据修改后**：确保图表反映对其数据源的任何更新。
- **在更改选项时**：将新的样式或配置变化应用于图表。
- **在响应调整时**：在容器的尺寸更改时调整图表的布局或大小，确保在各种设备上的最佳显示。

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/resources/static/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## 导出图表为图像 {#exporting-charts-as-images}

`getImageUri()`方法提供了一种将您的Google Charts导出为base64编码的PNG图像的方法。此方法对于在网络环境之外共享图表、将其嵌入电子邮件或文档中，或仅仅用于存档目的特别有用。

在图表完全渲染后，请在图表实例上调用`getImageUri()`。通常，这个方法在一个"ready"事件监听器中使用，以确保图表准备好导出：

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // 现在您可以使用imageUri，例如，作为img标签的src属性
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

每当用户选择Google Chart组件中的数据点或部分时，将触发`GoogleChartSelectedEvent`。此事件启用与所选图表数据的交互，提供有关所选内容的详细信息。可以通过在`GoogleChart`实例上使用`addSelectedListener()`方法来监听该事件。

`GoogleChartSelectedEvent`在需要用户与图表互动的应用中非常有用。

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// 将选定监听器添加到图表
chart.addSelectedListener(event -> {
  // 获取选择内容
  List<Object> selection = chart.getSelection();
  
  // 处理选中的事件
  if (!selection.isEmpty()) {
    System.out.println("选中的行: " + selection.get(0));
    // 根据选择的行/列进行进一步处理
  }
});
```

### 有效负载 {#payload}
`GoogleChartSelectedEvent`提供对选择数据的访问，可以使用图表对象上的`getSelection()`方法进行检索。此方法返回一个对象列表，其中每个对象包含以下属性：

- **row**：在图表的数据表中被选中的行的索引。
- **column**：在数据表中的列的索引，这是可选的，适用于允许选择单元格的图表，例如表格图表。
  
对于饼图或条形图等图表，通常仅提供`row`，表示所选数据点。

以下是有效负载示例：
```java
[
  {
    "row": 3,  // 数据中选中的行索引
    "column": 2  // （可选）选中的列索引
  }
]
```

:::info 选择多个数据点
如果用户选择多个数据点，`getSelection()`方法将返回一个对象数组，每个对象代表一个选定元素。有效负载可能会根据图表类型和用户执行的交互而有所不同。
:::
