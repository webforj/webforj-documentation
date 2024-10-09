---
title: Google Charts
---

import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="google-chart" clickable={false} iconName='code'/>

<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Brief overview of the component and what it is/does -->

:::info Importing Google Charts
To use the `GoogleChart` class in your app, use the following XML in your POM file:

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-googlecharts</artifactId>
    <version>${webforj.version}</version>
</dependency>
```
:::

The `GoogleChart` class is a comprehensive solution for embedding rich, interactive charts within web applications. This class acts as a bridge to the [Google Charts](https://developers.google.com/chart) library, offering a wide variety of chart types suitable for any data visualization task.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
height='300px'
/>


## Chart types

The `GoogleChart` addon offers a comprehensive array of chart types to suit various data visualization requirements. Selecting the appropriate chart type is essential for effectively communicating the data's story. See the gallery below for examples of common charts that can be used in a webforJ app.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/chartstyles/chartgallery_styles.css'
height='600px'
/>

## Options

The `GoogleChart` addon enables extensive customization through a variety of options. These options allow you to tailor the look and functionality of your charts to fit your app's needs. Options are passed as a `Map<String, Object>` to the chart's `setOptions()` method. 

Here's an example for setting a chart's options:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monthly Revenue");
options.put("backgroundColor", "#EFEFEF");

// Apply the options to the chart
chart.setOptions(options);
```

For more information on the options available for specific charts, see the [Google Visualization API reference (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Setting data

Visualizing data with `GoogleChart` requires properly structuring and setting the data. This guide will walk you through preparing your data and applying it to your charts.

### Basic data setup

The most straightforward way to define the data is by using `List<Object>`, where each row is a list of values.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Task", "Hours per Day"));
data.add(Arrays.asList("Work", 11));
data.add(Arrays.asList("Eat", 2));
data.add(Arrays.asList("Commute", 2));
data.add(Arrays.asList("Watch TV", 2));
data.add(Arrays.asList("Sleep", 7));
chart.setData(data);
```

### Using maps for more complex structures

For more complex data structures, you can use maps to represent rows and then convert them into the required format.

```java
List<Object> data = new ArrayList<>();

// Header row
data.add(Arrays.asList("Country", "Revenue"));

// Data rows
Map<String, Object> row1 = Map.of("Country", "Germany", "Revenue", 1000);
Map<String, Object> row2 = Map.of("Country", "United States", "Revenue", 1170);
Map<String, Object> row3 = Map.of("Country", "Brazil", "Revenue", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Once the data is prepared, it can be applied to the GoogleChart using the setData method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Loading data and options from JSON

You can also load data and options from JSON files using Gson for easier management. This approach helps keep your data and options organized and easy to update.


```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Year", "Sales", "Expenses"));
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

## Updating chart visuals

Refreshing or updating the appearance of your charts in response to data changes, user interactions, or visual option adjustments is straightforward with the `redraw()` method. This method ensures that your charts remain accurate and visually aligned with the underlying data or any modifications to their settings.

Invoke `redraw()` in scenarios such as:

- **After Data Modifications**: Ensures the chart reflects any updates to its data source.
- **Upon Changing Options**: Applies new styling or configuration changes to the chart.
- **For Responsive Adjustments**: Adjusts the chart's layout or size when the container's dimensions change, ensuring optimal display across devices.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/chartstyles/redrawchart_styles.css'
height='500px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Exporting charts as images

The `getImageUri()` method provides a way to export your Google Charts as base64-encoded PNG images. This method is particularly useful for sharing charts outside the web environment, embedding them into emails or documents, or simply for archival purposes.

Call `getImageUri()` on your chart instance after the chart has been fully rendered. Typically, this method is used within a "ready" event listener to ensure the chart is ready for export:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Now you can use the imageUri, for example, as the src attribute of an img tag
});
```