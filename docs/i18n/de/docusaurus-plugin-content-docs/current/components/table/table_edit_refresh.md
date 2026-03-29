---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
_i18n_hash: 1a8c3b1ab877d759906737431d8601e1
---
Die Bearbeitung von Daten innerhalb der `Table` erfolgt über die Interaktion mit dem `Repository`, das die Daten für die `Table` enthält. Das `Repository` dient als Verbindung zwischen der `Table` und dem zugrunde liegenden Datensatz und bietet Methoden zum Abrufen, Ändern und Aktualisieren von Daten. Unten finden Sie ein Beispiel, das das Verhalten zur Bearbeitung des „Titels“ einer gewünschten Zeile implementiert.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Im obigen Beispiel erleichtert die Klasse `TitleEditorComponent` die Bearbeitung des „Titel“-Felds für einen ausgewählten `MusicRecord`. Die Komponente umfasst ein Eingabefeld für den neuen Titel sowie „Speichern“ und „Abbrechen“-Schaltflächen.

Um die Bearbeitungskomponente mit der `Table` zu verbinden, wird eine „Bearbeiten“-Schaltfläche zur `Table` über einen `VoidElementRenderer` hinzugefügt. Ein Klick auf diese Schaltfläche ruft die Methode `edit()` der `TitleEditorComponent` auf, die es den Benutzern ermöglicht, den „Titel“-Wert zu ändern.

## Commit-Methode {#commit-method}

Sobald der Benutzer den Titel ändert und auf die Schaltfläche „Speichern“ klickt, löst die `TitleEditorComponent` die Methode `save()` aus. Diese Methode aktualisiert den Titel des entsprechenden `MusicRecord` und dispatcht ein benutzerdefiniertes `SaveEvent`.

Die Echtzeitaktualisierung der Daten im Repository erfolgt über die Methode `commit()`. Diese Methode wird innerhalb des `onSave`-Ereignislisteners verwendet, um sicherzustellen, dass Änderungen, die über die Bearbeitungskomponente vorgenommen werden, im zugrunde liegenden Datensatz widergespiegelt werden.

Die Methode `commit()` wird aufgerufen, um alle interessierten Komponenten darüber zu informieren, dass die Daten geändert wurden. Die `Table` empfängt das `RepositoryCommitEvent` und aktualisiert sich basierend auf den neuen Daten.

:::tip Aktualisierung und Erstellung von Einträgen
Das Aufrufen der Methode `commit()` aktualisiert sowohl vorhandene Einträge als auch **fügt alle neuen Einträge ein, die erstellt wurden**.
:::
