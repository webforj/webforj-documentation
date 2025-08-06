---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 0c57bd3fd3a9adb9cb7275e23efa725f
---
Das Bearbeiten von Daten innerhalb der `Table` erfolgt durch Interaktion mit dem `Repository`, das die Daten für die `Table` enthält. Das `Repository` dient als Brücke zwischen der `Table` und dem zugrunde liegenden Datensatz und bietet Methoden zum Abrufen, Ändern und Aktualisieren von Daten. Im Folgenden finden Sie ein Beispiel, das das Verhalten implementiert, um den "Titel" einer gewünschten Zeile zu bearbeiten.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Im obigen Beispiel erleichtert die `TitleEditorComponent`-Klasse das Bearbeiten des "Titel"-Felds für einen ausgewählten `MusicRecord`. Die Komponente enthält ein Eingabefeld für den neuen Titel sowie "Speichern"- und "Abbrechen"-Schaltflächen.

Um die Bearbeitungskomponente mit der `Table` zu verbinden, wird eine "Bearbeiten"-Schaltfläche zur `Table` über einen `VoidElementRenderer` hinzugefügt. Das Klicken auf diese Schaltfläche löst die Methode `edit()` der `TitleEditorComponent` aus, die es den Benutzern ermöglicht, den "Titel" zu ändern.

## Commit-Methode {#commit-method}

Sobald der Benutzer den Titel ändert und auf die Schaltfläche "Speichern" klickt, löst die `TitleEditorComponent` die Methode `save()` aus. Diese Methode aktualisiert den Titel des entsprechenden `MusicRecord` und löst ein benutzerdefiniertes `SaveEvent` aus.

Die Aktualisierung der Daten im Repository erfolgt über die Methode `commit()`. Diese Methode wird innerhalb des `onSave`-Ereignislisteners verwendet und stellt sicher, dass Änderungen, die über die Bearbeitungskomponente vorgenommen werden, im zugrunde liegenden Datensatz widergespiegelt werden.

Die Methode `commit()` wird aufgerufen, um alle interessierten Komponenten darüber zu informieren, dass sich die Daten geändert haben. Die `Table` fängt das `RepositoryCommitEvent` ab und aktualisiert sich basierend auf den neuen Daten.

:::tip Aktualisieren und Erstellen von Einträgen
Der Aufruf der Methode `commit()` aktualisiert sowohl bestehende Einträge als auch **fügt alle neuen Einträge hinzu, die erstellt wurden**.
:::
