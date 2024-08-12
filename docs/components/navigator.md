---
title: Navigator
---

<!-- vale off -->

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import EventTable from '@site/src/components/DocsTools/EventTable';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';
import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<!-- vale on -->

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-navigator" href="https://basishub.github.io/basis-next/#/web-components/dwc-navigator" clickable={false} iconName='code'/>


<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>


The `Navigator` component is a customizable pagination component designed to navigate through data sets, supporting multiple layouts. You can configure it to display various navigation controls such as first, last, next, and previous buttons, along with page numbers or a quick jump field depending on the layout setting. 

It supports automatic disabling of navigation buttons based on the current page and total items, and offers customization options for text and tooltips for different parts of the navigator. Additionally, you can bind it to a `Paginator` instance to manage the data set's pagination logic and reflect changes in the navigation controls. 

## Binding to repositories

Often, a `Navigator` component displays information found in a bound `Repository`. This binding enables the `Navigator` to automatically paginate data managed by the repository and refresh other bindable components, such as tables, based on the navigated data.

To do this, simply pass the desired `Repository` object to an applicable `Navigator` object's constructor:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/NavigatorTable?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/navigatordemos/NavigatorTable.java'
height='475px'
/>

This example creates the `Navigator` and [`Table`](./table/) with the same `Repository` instance. This means that when navigating to a new page with the `Navigator`, the [`Table`](./table/) recognizes this change and rerenders.

## Pagination 

The `Navigator` component is closely linked with the `Paginator` model class, calculates pagination metadata such as total number of pages, start/end indices of items on the current page, and an array of page numbers for navigation. 

While not overtly necessary, utilizing the `Paginator` enables the logic behind navigation. When integrating with a `Paginator`, the navigator responds to any changes within the `Paginator`. `Navigator` objects have access to a built-in `Paginator` through use of the `getPaginator()` method. It can also accept a `Paginator` instance via the `setPaginator()` method, or utilization of one of the applicable constructors.

This section includes practical code snippets to illustrate how this integration works in practice.

### Items

The term "items" denotes the individual paginated elements or data entries. These could be records, entries, or any discrete units within a dataset. You can set the total number of items using the `setTotalItems()` method. 

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
A repository associated with the `Paginator` instance has the total number of items directly managed by the repository and can't be directly set.
:::

### Maximum pages

The `setMax()` method allows you to define the maximum number of page links to display in the pagination navigation. This is particularly useful when dealing with a large number of pages, as it controls the number of page links visible to the user at any given time.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/NavigatorPages?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/navigatordemos/NavigatorPages.java'
height='125px'
/>

This program shows a maximum of five pages on the `Navigator` at one time by using the `getPaginator()` method to retrieve the `Paginator` associated with the `Navigator` object, and then using the `setMax()` method to specify a desired number of maximum pages displayed.

### Page size

The `setSize()` method allows you to specify the number of items to display on each page of the pagination. When you call this method and provide a new page size, it adjusts the pagination accordingly. 

```java
navigator.getPaginator().setSize(pageSize);
```

## Customizing buttons, text and tooltips

The `Navigator` component provides extensive customization options for buttons, text, and tooltips. To change the displayed text on the `Navigator` component, use the `setText()` method. This method takes text, as well as the desired `Part` of the `Navigator`. 

In the following example, the `setText()` method displays a numeric value to the user. Clicking the buttons fires the `onChange` method of the `Navigator`, which comes with a `Direction` value the clicked button. 

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/NavigatorBasic?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/navigatordemos/NavigatorBasic.java'
height='100px'
/>

### Buttons and component text

The `setText()` method evaluates the text parameter as a JavaScript expression using following parameters:

- `page` - the current page number
- `current` - the currently selected page number
- `x` - an alias for the current page
- `startIndex` - The start index of the current page.
- `endIndex` - The end index of the current page.
- `totalItems` - The total number of items.
- `startPage` - The start page number.
- `endPage` - The end page number.
- `component` - The Navigator client component.

<!-- vale off -->
For example, to set the text of the last page button in a `Navigator` with 10 pages to "Go to page 10", use the following code snippet: 
<!-- vale on -->

```java
navigator.setText("'Go to page ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Tooltip text

You can customize tooltips for various parts of the `Navigator` component using the `setTooltipText()` method. Tooltips provide helpful hints to users when they hover over navigation elements.

:::info
Tooltip text doesn't evaluate to Javascript, unlike the text used by the `setText()` method
:::

<!-- vale off -->
For example, to set the tooltip text of the last page button in a `Navigator` to "Go to the last page", use the following code snippet:
<!-- vale on -->

```java
navigator.setTooltipText("Go to the last page", Navigator.Part.LAST_BUTTON);
```

## Layouts

Various layout options exist for the `Navigator` component  to provide flexibility in displaying pagination controls. To access these layouts, use the `Navigator.Layout` enum's values. The options are as follows:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/NavigatorLayout?' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/navigatordemos/NavigatorLayout.java'
height='200px'
/>

#### 1. None layout

The `NONE` layout renders no text within the `Navigator`, displaying only the navigation buttons without a default textual display. To activate this layout, use:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

#### 2. Numbered layout

The numbered layout displays numbered chips corresponding to each page within the display area of the `Navigator`. Using this layout is ideal for scenarios where users prefer direct navigation to specific pages. To activate this layout, use:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

#### 3. Preview layout

The preview layout shows the current page number and the total number of pages, and is suitable for compact pagination interfaces with limited space.

:::info
Preview is the default `Navigator` layout.
:::

To activate this layout, use:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

#### 4. Quick jump layout

The quick-jump layout provides a [NumberField](./fields/number-field.md) for users to enter a page number for quick navigation. This is useful when users need to navigate to a specific page quickly, especially for large datasets To activate this layout, use:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Styling

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, necessary for styling via CSS.

<!-- vale off -->
<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Navigator} table='parts'  />
<!-- vale on -->


### CSS properties

Here are the different CSS properties used in the component, along with a brief explanation of their purpose.

<!-- vale off -->
<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Navigator}  table='properties'/>
<!-- vale on -->


### Reflected attributes

The component's reflected attributes appear as attributes in the rendered HTML element within the DOM, allowing for styling based on these attributes.

<!-- vale off -->
<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Navigator} table="reflects" />
<!-- vale on -->

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-navigator' table="dependencies"/>

## Best practices 

To ensure an optimal user experience when using the `Navigator` component, consider the following best practices: 

- **Understand dataset**: Before integrating a `Navigator` component into your app, thoroughly understand the data browsing requirements of your users. Consider factors such as the size of the dataset, typical user interactions, and preferred navigation patterns.

- **Choose appropriate layout**: Select a layout for the `Navigator` component that aligns with the user experience goals and available screen real estate.

- **Customize text and tooltips**: Customize the text and tooltips of the `Navigator` component to match the language and terminology used in your app. Provide descriptive labels and helpful hints to assist users in navigating the dataset effectively.