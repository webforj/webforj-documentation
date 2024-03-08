---
sidebar_position: 1
title: Table
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import ComponentDemoMultiple from '@site/src/components/DocsTools/ComponentDemoMultiple';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import EventTable from '@site/src/components/DocsTools/EventTable';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-table" clickable={false} iconName='code'/>

<JavadocLink type="engine" location="org/dwcj/component/button/Button" top='true'/>

:::warning EXPERIMENTAL
The table component is currently under heavy development and has not reached a stable status. However, any assistance testing would be appreciated.
:::

:::info
To use the `Table` in your project, ensure snapshots are enabled, and include the following tag in your POM's dependencies:

```xml
<dependency>
    <groupId>org.dwcj</groupId>
    <artifactId>dwcj-table</artifactId>
    <version>24.00-SNAPSHOT</version>
</dependency>
```
:::

The `Table` class is a versatile component designed for presenting tabular information in a structured and easily understandable manner. Optimized for handling large datasets with high performance, this component offers advanced visualization and a comprehensive suite of events for dynamic user engagement.

<ComponentDemo 
path='https://eu.bbx.kitchen/webapp/controlsamples/DataTable?' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/DataTable.java'
height='600px'
/>


## Creating a `Table` 

In order to create and populate a `Table` in an application, the following steps can be taken:

#### 1. Creating an Entity Class

Define a class to represent the entities (data) you want to display in the table. In the example, this class is MusicRecord.

```java
public class MusicRecord {
    // Fields and methods to represent the attributes of each record
}
```

#### 2. Create a Repository

Once an entity class has been created, use this to fill a collection of these entities with the desired data.

From this data, a `Repository` needs to be created for use within the `Table`. The `CollectionRepository` class is provided to turn any valid Java collection into a usable `Repository`, forgoing the need to implement your own `Repository` class.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

#### 3. Instantiate `Table` and Add Columns

Instantiate a new `Table` object, and use one of the provided factory methods to add the desired columns to a newly created `Table`:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Number", MusicRecord::getNumber);
table.addColumn("Title", MusicRecord::getTitle);
table.addColumn("Artist", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Cost", MusicRecord::getCost);
```

#### 4. Set the `Table` data

Finally, set the `Repository` for the `Table` created in the previous step:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternatively, the `setItems()` method can be passed any valid Java collection, which will create a `CollectionRepository` under the hood for you. 
:::

Below is an example of the above steps implemented to create a basic `Table` component:


<ComponentDemoMultiple 
path='https://eu.bbx.kitchen/webapp/controlsamples?class=addondemos.tabledemos.TableBasic' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/TableBasic.java'
urls={['https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/MusicRecord.java', 
'https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/addondemos/tabledemos/Service.java']}
height='600px'
/>

<!-- 
## Column Configuration

Various column configurations and tools exist to allow for customizations of a `Table` at the individual `Column` level. For more information on the `Column`, see **[this article](./table_columns.md)**.

## Data Rendering

The `Table` component provides various tools to further customize the display of various data. **[This section](./table_rendering.md)** of the guide goes over the options available when using the `Table` within an application.


## Selection

The `Table` component provides various selection capabilities, including methods for the configuration of selection type, programmatically managing selections, and events.

The details of `Table` selection are outlined in **[this article](./table_selection.md)**. 

## Sorting

Sorting is supported out of the box by the `Table` component, allowing for more efficient visualization of data for users.

The details of `Table` sorting are outlined in **[this article](./table_sorting.md)**. 

## Large Datasets

The `Table` component handles large datasets efficiently by utilizing virtual scrolling to optimize performance. For more information on virtual scrolling and settings related to this functionality, see **[this article](./table_large_data.md)**.


## Editing and Refreshing

The `Repository` class contains the data for the `Table`, and is responsible for refreshing it when the data changes. For a more in-depth description, see **[this section](./table_edit_refresh.md)**.

## Filtering

While the `Table` component itself does not provide filtering, this functionality can be implemented using various methods outlined in **[this section](./table_filtering.md)**. -->