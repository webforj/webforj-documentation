---
sidebar_position: 10
title: Editing and Refreshing
slug: refreshing
---

Editing data within the `Table` works via interaction with the `Repository` containing the data for the `Table`. The `Repository` serves as a bridge between the `Table` and the underlying dataset, offering methods for data retrieval, modification, and refreshing. Below is an example which implements behavior to edit the "Title" of a desired row based.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

In the above example, the `TitleEditorComponent` class facilitates the editing of the "Title" field for a selected `MusicRecord`. The component includes an input field for the new title, along with "Save" and "Cancel" buttons.

To connect the editing component with the `Table`, an "Edit" button is added to the `Table` via a `VoidElementRenderer`. Clicking this button triggers the `edit()` method of the `TitleEditorComponent`, allowing users to modify the "Title".

## Commit method

Once the user modifies the title and clicks the "Save" button, the `TitleEditorComponent` triggers the `save()` method. This method updates the title of the corresponding `MusicRecord` and dispatches a custom `SaveEvent`.

The real-time update of data in the repository is achieved through the `commit()` method. This method is employed within the `onSave` event listener, ensuring that changes made through the editing component are reflected in the underlying dataset.

The `commit()` method is called to notify all interested components that the data has been changed. The `Table` catches the `RepositoryCommitEvent`, and updates based on the new data. 