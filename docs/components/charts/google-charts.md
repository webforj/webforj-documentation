---
sidebar_position: 1
title: Google Charts
draft: true
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import EventTable from '@site/src/components/DocsTools/EventTable';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<!-- UPDATE THE NAME FOR THE CLIENT COMPONENT HERE (label="???") -->
<DocChip tooltipText="The name of the web component that will render in the DOM." label="google-chart" clickable={false} iconName='code'/>

<!-- Can't find Javadocs for GoogleChart? -->
<!-- <JavadocLink type="engine" location="org/dwcj/component/button/Button" top='true'/>  -->

<!-- Brief overview of the component and what it is/does -->
The `GoogleChart` class is a comprehensive solution for embedding rich, interactive charts within web applications. This class acts as a bridge to the [Google Charts](https://developers.google.com/chart) library, offering a wide variety of chart types suitable for any data visualization task.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.chartdemos.ChartDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/chartdemos/ChartDemo.java'
height='300px'
/>

<!-- tabs={['ChartDemo.java']} -->

## Chart Types

The `GoogleChart` addon offers a comprehensive array of chart types to suit various data visualization requirements. Selecting the appropriate chart type is essential for effectively communicating the data's story.

For more information on the different Chart Types available, see **[this article](./chart_types.md)**.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.chartdemos.ChartDemoRedraw' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/chartdemos/ChartGalleryDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/chartstyles/chartgallery_styles.css'
height='300px'
/>

<!-- tabs={['ChartGallerydemo.java', 'chartgallery_styles.css']} -->

<!-- Constructors -->

## Constructors

The `GoogleChart` class provides several constructors to create and initialize charts:

1. `GoogleChart()`: Creates a new `GoogleChart` instance with default settings. This constructor is typically used when you plan to set the chart type separately.

2. `GoogleChart(Type type)`: Instantiates a `GoogleChart` with a specific type provided by the `Type` enumeration. It allows you to define the type of chart right at the point of creation.

Here is an example of how to create a Bar Chart:

    ```java
    GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);
    ```
## Options

The `GoogleChart` addon enables extensive customization through a variety of options. These options allow you to tailor the look and functionality of your charts to fit your application's needs. Options are passed as a `Map<String, Object>` to the chart's `setOptions()` method. 

Here's an example for setting a chart's options:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Monthly Revenue");
options.put("backgroundColor", "#EFEFEF");

// Apply the options to the chart
chart.setOptions(options);
```

For more information on the options available for specific charts, see the [Google Visualization API reference (Chart Gallery)](https://developers.google.com/chart/interactive/docs/gallery).

## Setting Data

Visualizing data with `GoogleChart` requires properly structuring and setting the data. This guide will walk you through preparing your data and applying it to your charts.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.chartdemos.ChartDemoSettingData' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/chartdemos/ChartDemoSettingData.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

### Data Structure

Data for `GoogleChart` should be structured as a list of lists, where each inner list represents a row, and each row element represents a column value. Typically, the first row contains column labels. The `setData()` method is then used to apply the structured data to a chart. The method transforms this data into a format that the underlying Google Charts library can use to render the chart.

## Updating Chart Visuals

Refreshing or updating the appearance of your charts in response to data changes, user interactions, or visual option adjustments is straightforward with the `redraw()` method. This method ensures that your charts remain accurate and visually aligned with the underlying data or any modifications to their settings.

Invoke `redraw()` in scenarios such as:

- **After Data Modifications**: Ensures the chart reflects any updates to its data source.
- **Upon Changing Options**: Applies new styling or configuration changes to the chart.
- **For Responsive Adjustments**: Adjusts the chart's layout or size when the container's dimensions change, ensuring optimal display across devices.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=addondemos.chartdemos.ChartDemoRedraw' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/addondemos/chartdemos/ChartDemoRedraw.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/chartstyles/redrawchart_styles.css'
height='300px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Exporting Charts as Images

The `getImageUri()` method provides a way to export your Google Charts as base64-encoded PNG images. This method is particularly useful for sharing charts outside the web environment, embedding them into emails or documents, or simply for archival purposes.

Call `getImageUri()` on your chart instance after the chart has been fully rendered. Typically, this method is used within a "ready" event listener to ensure the chart is ready for export:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Now you can use the imageUri, for example, as the src attribute of an img tag
});
```