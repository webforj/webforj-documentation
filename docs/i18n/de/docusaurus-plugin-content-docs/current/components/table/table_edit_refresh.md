---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 86d395b36fe0cdec90b5a29497a8b0d3
---
Das Bearbeiten von Daten innerhalb der `Table` erfolgt über die Interaktion mit dem `Repository`, das die Daten für die `Table` enthält. Das `Repository` fungiert als Brücke zwischen der `Table` und dem zugrunde liegenden Datensatz und bietet Methoden zum Abrufen, Ändern und Aktualisieren von Daten. Unten steht ein Beispiel, das das Verhalten implementiert, um den "Titel" einer gewünschten Zeile zu bearbeiten.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

Im obigen Beispiel erleichtert die Klasse `TitleEditorComponent` das Bearbeiten des "Titel"-Feldes für einen ausgewählten `MusicRecord`. Die Komponente umfasst ein Eingabefeld für den neuen Titel sowie "Speichern"- und "Abbrechen"-Schaltflächen.

Um die Bearbeitungskomponente mit der `Table` zu verbinden, wird über einen `VoidElementRenderer` eine "Editieren"-Schaltfläche zur `Table` hinzugefügt. Ein Klick auf diese Schaltfläche löst die Methode `edit()` der `TitleEditorComponent` aus, sodass Benutzer den "Titel"-Wert ändern können.

## Commit-Methode {#commit-method}

Sobald der Benutzer den Titel ändert und auf die Schaltfläche "Speichern" klickt, löst die `TitleEditorComponent` die Methode `save()` aus. Diese Methode aktualisiert den Titel des entsprechenden `MusicRecord` und dispatcht ein benutzerdefiniertes `SaveEvent`.

Das Echtzeit-Update der Daten im Repository wird durch die Methode `commit()` erreicht. Diese Methode wird innerhalb des `onSave`-Ereignislisteners verwendet, um sicherzustellen, dass die durch die Bearbeitungskomponente vorgenommenen Änderungen im zugrunde liegenden Datensatz widergespiegelt werden.

Die `commit()`-Methode wird aufgerufen, um alle interessierten Komponenten zu benachrichtigen, dass die Daten geändert wurden. Die `Table` erfasst das `RepositoryCommitEvent` und aktualisiert sich basierend auf den neuen Daten.

:::tip Aktualisieren und Erstellen von Einträgen
Der Aufruf der Methode `commit()` aktualisiert sowohl vorhandene Einträge als auch **fügt alle neuen Einträge hinzu, die erstellt wurden**.
:::
