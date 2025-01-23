---
sidebar_position: 7
title: File Save
---

# File Save Dialog

<DocChip chip='shadow' />

<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileSaveDialog" top='true'/>

`FileSaveDialog` is a modal dialog designed to allow users to save a file to a specified location on the server file system. The dialog blocks app execution until the user provides a filename and confirms the action or cancels the dialog.

```java
OptionDialog.showFileSaveDialog("Save your file");
```

## Usages

The `FileSaveDialog` provides a streamlined method for saving files to the file system, offering user-configurable options for file naming and handling existing files.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/filesavedialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogBasicView.java'
height = '800px'
/>

## Result

The `FileSaveDialog` returns the selected path as a string. If the user cancels the dialog, the result will be `null`.

:::important Dialog Purpose
This dialog doesn't actually cause any files to be saved but returns the filename the user has selected.
:::

:::info
The resulting string is returned from the `show()` method or the equivalent `OptionDialog` method as demonstrated below.
:::

```java showLineNumbers
String result = OptionDialog.showFileSaveDialog(
    "Save your file", "/home/user/documents", "report.xls");

if (result != null) {
  OptionDialog.showMessageDialog("Saved file to: " + path, "Path Selected");
} else {
  OptionDialog.showMessageDialog("No path is selected", "Path Selected",
      MessageDialog.MessageType.ERROR);
}
```

## Exists action

The `FileSaveDialog` provides configurable behavior when a file with the specified name already exists:

* **ACCEPT_WITHOUT_ACTION**: The selection is accepted with no additional user action.
* **ERROR_DIALOGUE**: The user is presented with an error dialog; the selection isn't allowed.
* **CONFIRMATION_DIALOGUE**: The user is presented with a dialog requesting confirmation. This is the default.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Save your file", "/home/user/documents", "report.xls");
dialog.setExistsAction(FileSaveDialog.ExistsAction.ERROR_DIALOGUE);
String result = dialog.show();
```

## Selection mode

The `FileSaveDialog` supports different selection modes, allowing you to tailor the selection method to your specific needs:

1. **FILES**: Allows the selection of files only.
2. **DIRECTORIES**: Allows the selection of directories only.
3. **FILES_AND_DIRECTORIES**: Allows the selection of both files and directories.

## Initial path

Specify the directory where the dialog will open using the initial path. This helps users start in a logical directory for their save operation.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Save your file", "/home/user/documents", "report.xls");
String result = dialog.show();
```

## Restriction

You can restrict the dialog to a specific directory, preventing users from navigating outside of it using the `setRestricted(boolean restricted)` method.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog(
  "Save your file", "/home/user/documents", "report.xls");
dialog.setRestricted(true);
dialog.show();
```

## Filename

Set a default filename for the save operation to guide users and minimize errors.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Save your file");
dialog.setName("report.xls");
String result = dialog.show();
```

## Internationalization (i18n)

The titles, descriptions, labels, and messages within the component are fully customizable using the `FileSaveI18n` class. This ensures the dialog can be tailored to various localization or personalization requirements.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Speichern Sie Ihre Datei");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Filters

The `FileSaveDialog` allows you to set filters to limit the types of files that can be saved using the `setFilters(List<FileSaveFilter> filters)` method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/filesavedialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filesave/FileSaveDialogFiltersView.java'
height = '800px'
/>

### Custom filters

You can enable custom filters to allow users to define their own file filters using the `setCustomFilters(boolean customFilters)` method. Filters are saved in local storage by default and restored on subsequent dialog invocations.

```java showLineNumbers
FileSaveDialog dialog = new FileSaveDialog("Save your file", "/home/user/documents");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Best practices

* **Predefined File Names**: Provide a logical default filename where applicable.
* **Confirm Overwrites**: Use `CONFIRMATION_DIALOGUE` for `ExistsAction` to prevent accidental overwrites.
* **Intuitive Initial Path**: Set an initial path that aligns with user expectations.
* **Internationalization**: Customize dialog text to enhance usability for international audiences.
* **File Type Filters**: Leverage filters to restrict file types and guide users towards valid file extensions.


