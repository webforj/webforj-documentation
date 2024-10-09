---
sidebar_position: 1
title: Table
---

import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-table" clickable={false} iconName='code'/>

<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

The `Table` class is a versatile component designed for presenting tabular information in a structured and easily understandable manner. Optimized for handling large datasets with high performance, this component offers advanced visualization and a comprehensive suite of events for dynamic user engagement.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
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


<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>