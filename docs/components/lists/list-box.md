---
sidebar_position: 3
title: ListBox
slug: listbox
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-listbox" clickable={false} iconName='code'/>

<JavadocLink type="foundation" location="com/webforj/component/list/ListBox" top='true'/>

The `ListBox` component is a user interface element designed to display a scrollable list of objects and allows users to select single or multiple items from the list. Users can also interact with the `ListBox` with the arrow keys. 

## Usages

1. **User Role Assignment**: In applications with user access control, administrators can use a `ListBox` to assign roles and permissions to users. Users are selected from a list, and the roles or permissions are assigned based on their selection. This ensures precise and controlled access to different features and data within the application.

2. **Project Task Assignment**: In project management software, `ListBox` components are useful for assigning tasks to team members. Users can select tasks from a list and assign them to different team members. This simplifies task delegation and ensures that responsibilities are clearly defined within the team.

3. **Multi-Category Filtering**: In a search application, users often need to filter search results based on multiple criteria. A `ListBox` can display various filter options, such as 
>- Product features
>- Price ranges
>- Brands. 

  Users can select items from each filter category, allowing them to refine search results and find exactly what they're looking for.

4. **Content Categorization**: In content management systems, `ListBox` components assist in categorizing articles, images, or files. Users can select one or more categories to associate with their content, making it easier to organize and search for content items in the system.

## Selection Options

By default, the list box is configured to allow selection of a single item at a time. However, this can be easily configured with a built-in method which allows users to select multiple items ***using the `Shift` key*** for contiguous entry selection and ***`Control` (Windows) or `Command` (Mac) key*** for separate, multiple item selection. Use the <JavadocLink type="foundation" location="com/webforj/component/list/ListBox" code='true' suffix='#setSelectionMode(org.dwcj.component.list.MultipleSelectableList.SelectionMode)'>setSelectionMode()</JavadocLink> function to change this property. True will enable multiple selection, false disables it. 

Additionally, the arrow keys can be used to navigate the `ListBox`, and typing a letter key while the `ListBox` has focus will select the option that begins with that letter, or cycle through the options beginning with that letter should multiple options exist.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/listboxmultipleselection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/lists/listbox/ListboxMultipleSelectionView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/listbox/MultipleSelection.txt'
height = '250px'
/>

## Styling


### Shadow Parts

These are the various parts of the [shadow DOM](../../glossary#shadow-dom) for the `ListBox` component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ListBox} table='parts' exclusions='' />

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ListBox} exclusions='' table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').ListBox} table="reflects" exclusions=''/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-listbox' table="dependencies"/>

## Best Practices 

To ensure an optimal user experience when using the `ChoiceBox` component, consider the following best practices:

1. **Prioritize Information Hierarchy**: When using a `ListBox`, ensure that the items are organized in a logical and hierarchical order. Place the most important and commonly used options at the top of the list. This makes it easier for users to find what they need without excessive scrolling.

2. **Limit List Length**: Avoid overwhelming users with an excessively long `ListBox`. If there are a large number of items to display, consider implementing pagination, search, or filtering options to help users locate items quickly. Alternatively, you can group items into categories to reduce list length.

3. **Clear and Descriptive Labels**: Provide clear and descriptive labels for each item in the `ListBox`. Users should be able to understand the purpose of each option without ambiguity. Use concise and meaningful item labels.

4. **Multi-Selection Feedback**: If your `ListBox` allows for multiple selections, provide visual or textual feedback indicating that multiple items can be selected from the list.

5. **Default Selection**: Consider setting a default selection for the `ListBox`, especially if one option is more commonly used than others. This can streamline the user experience by pre-selecting the most likely choice.