<!-- ---
sidebar_position: 1
--- -->

# BarChart

### Description

Use the barchart to help visualize data in your application or webpage.


### Methods

| Method Name | Description | Parameters  | Returns |
|-------------|-------------|:--------:|:-------:|
| is3D() | Returns true or false based on whether or not the chart has been created as a 3D chart or not. | N/A | `boolean` |
| getCategoryCount() | Returns the number of categories in the chart. Categories are groups of data. | N/A | `Integer` |
| getSeriesCount() | Returns the number of series in the chart. Series are the pieces of data, or bars, within a category. | N/A | `Integer` |
| getTitle() | Returns the title of the chart. | N/A | `String` |
| getXLabel() | Returns the X axis label of the chart. | N/A | `String` |
| getYLabel() | Returns the Y axis label of the chart. | N/A | `String` |
| isLegendShown() | Returns a true or false value based on whether the legend for the chart is currently visable. | N/A | `boolean` |
| setBarValue() | Sets the value of a single bar within the chart. Three integers must be given:<br/> 1) The desired series <br/> 2) The desired category <br/> 3) The value you wish the data to reflect. <br/>Function returns instance of the object to allow for method chaining. | `(int series, int category, int value)` | `BarChart` |
| setCategoryCount() | Sets the number of discrete categories, or groups, of data your chart will have. | `(int categoryNumber)` | `BarChart` |
| setCategoryName() | Sets the name of a group of data, or category, on the chart. Must specify category number first (zero based), then a name. | `(int category, String categoryName)` | `Barchart` |
| setSeriesCount() | Sets the number of series, or pieces of data within a category, the chart will have. | `(int seriesNumber)` | `BarChart` |
| setLegendShown() | Sets whether or not there will be a legend for the bar chart shown. | `(boolean legendShown)` | `BarChart` |
| setSeriesName() | Sets the name of a group of data, or category, on the chart. Must specify category number first (zero based), then a name. | `(int series, String name)` | `BarChart` |
| setTitle() | Sets the title of the chart. | `(String name)` | `BarChart` |
| setXLabel() | Sets the label or description of the X axis, will appear at the bottom of the chart. | `(String title)` | `BarChart` |
| setYLabel() | Sets the label or description of the Y axis, will appear at the bottom of the chart. | `(String title)` | `BarChart` |