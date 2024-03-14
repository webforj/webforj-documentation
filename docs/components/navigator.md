---
sidebar_position: 55 
title: Navigator
draft: true
---

The `Navigator` component is a customizable pagination component designed to navigate through data sets, supporting multiple layouts. It can be configured to display various navigation controls such as first, last, next, and previous buttons, along with page numbers or a quick jump field depending on the layout setting. 

It supports automatic disabling of navigation buttons based on the current page and total items, and offers customization options for text and tooltips for different parts of the navigator. Additionally, it can be bound to a `Paginator` instance to manage the data set's pagination logic and reflect changes in the navigation controls.


## Pagination 

The `Navigator` component is closely linked with the `Paginator` model class. While not overly necessary, utilizing the `Paginator` enables the logic behind navigation. 

This class calculates pagination metadata such as total number of pages, start and end indices of items on the current page, and an array of page numbers for navigation.

### Items

In the context of the `Paginator`, "items" refer to the individual elements or data entries that are being paginated. These could be records, entries, or any discrete units within a dataset. The `Paginator` facilitates the navigation and organization of these items across multiple pages.

To interact with the items, the `Paginator` provides the following methods to access item position within a page:

**Start Index**:
The index of the first item on the current page can be obtained using `getStartIndex()`.

```java
int startIndex = paginator.getStartIndex();
```


Similarly, the index of the last item on the current page can be retrieved with `getEndIndex()`.

```java
int endIndex = paginator.getEndIndex();
```
Now, developers can use these indices to fetch and display the relevant items within the current page.

### Pages

The concept of "pages" in the `Paginator` refers to the logical divisions of the entire dataset. Each page contains a specific number of items, and users navigate through these pages to explore the complete dataset.

To enhance the navigation experience, the Paginator provides an array of page numbers. Developers can use this array to create a navigation menu or display available pages for the user.

### Start and End Indices

