---
sidebar_position: 10
title: File Chooser
---

# File Chooser Dialog

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/FileChooserDialog" top='true'/>

`FileChooserDialog` is a modal dialog designed to allow the user to select a file or a directory from the server file system. The dialog blocks app execution until the user makes a selection or closes the dialog.

```java
OptionDialog.showFileChooserDialog("Select a file");
```

## Usages {#usages}

The `FileChooserDialog` provides a way to select files or directories from the file system, enabling users to choose directories for saving data, or perform file operations.

<ComponentDemo 
path='/webforj/filechooserdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogBasicView.java'
height = '600px'
/>

## Result {#result}

The `FileChooserDialog` returns the selected file or directory as a string. If the user closes the dialog without making a selection, the result will be `null`.

:::info
The resulting string will be returned from the `show()` method, or the equivalent `OptionDialog` method as shown below. 
:::

```java showLineNumbers
String result = OptionDialog.showFileChooserDialog(
    "Select a file", "/home/user", FileChooserDialog.SelectionMode.FILES);

if (result != null) {
    OptionDialog.showMessageDialog("You selected: " + result, "Selection Made");
} else {
    OptionDialog.showMessageDialog("No selection made", "Selection Canceled");
}
```

## Selection mode {#selection-mode}

The `FileChooserDialog` supports different selection modes, allowing you to tailor the selection method to your specific needs:

1. **FILES**: Allows the selection of files only.
2. **DIRECTORIES**: Allows the selection of directories only.
3. **FILES_AND_DIRECTORIES**: Allows the selection of both files and directories.

## Initial path {#initial-path}

The `FileChooserDialog` allows you to specify an initial path that the dialog will open to when displayed. This can provide users with a starting point for their file selection.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Select a file", "/home/user");
String result = dialog.show();
```

## Restriction {#restriction}

You can restrict the dialog to a specific directory, preventing users from navigating outside of it using the `setRestricted(boolean restricted)` method.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Select a file", "/home/user");
dialog.setRestricted(true);
dialog.show();
```

## Filters {#filters}

When the seletion mode is `FILES`, The `FileChooserDialog` allows you to set filters to limit the types of files that listed. You can configure filters using the `setFilters(List<FileChooserFilter> filters)` method.

<ComponentDemo 
path='/webforj/filechooserdialogfilters?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/filechooser/FileChooserDialogFiltersView.java'
height = '600px'
/>

### Custom filters {#custom-filters}

You can allow users to add custom filters by enabling the custom filters feature using the `setCustomFilters(boolean customFilters)` method.
Custom filters will be saved in the browser's local storage by default and restored when the dialog is shown again.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Select a file", "/home/user");
dialog.setCustomFilters(true);
String result = dialog.show();
```

## Internationalization (i18n) {#internationalization-i18n}

The titles, descriptions, labels, and messages within the component are fully customizable using the `FileChooserI18n` class. This flexibility allows you to tailor the dialog interface to meet specific localization requirements or personalization preferences.

```java showLineNumbers
FileChooserDialog dialog = new FileChooserDialog("Wählen Sie eine Datei aus", "/Users/habof/bbx");
FileChooserI18n i18n = new FileChooserI18n();
i18n.setChoose("Wählen");
i18n.setCancel("Stornieren");
dialog.setI18n(i18n);
```

## Best practices {#best-practices}

1. **Clear and Concise Prompts**: Ensure the prompt message clearly explains what the user is being asked to select.
2. **Appropriate Selection Modes**: Choose selection modes that match the required user action to ensure accurate and relevant selections.
3. **Logical Initial Paths**: Set initial paths that provide users with a useful starting point for their selection.
4. **Restrict Directory Navigation**: Restrict the dialog to a specific directory when necessary to prevent users from navigating to unauthorized areas.