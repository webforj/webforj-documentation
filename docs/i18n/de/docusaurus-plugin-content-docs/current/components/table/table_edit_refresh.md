---
sidebar_position: 30
title: Bearbeiten und Aktualisieren
slug: refreshing
_i18n_hash: 39816123675d62a6dda185187e8d13e2
---
Die Bearbeitung von Daten innerhalb der `Table` erfolgt durch die Interaktion mit dem `Repository`, das die Daten für die `Table` enthält. Das `Repository` dient als Brücke zwischen der `Table` und dem zugrunde liegenden Datensatz und bietet Methoden zum Abrufen, Ändern und Aktualisieren von Daten. Im Folgenden ist ein Beispiel aufgeführt, das das Verhalten zur Bearbeitung des "Titels" einer gewünschten Zeile implementiert.

<ComponentDemo 
path='/webforj/tableeditdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableEditDataView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java']}
height='600px'
/>

Im obigen Beispiel ermöglicht die Klasse `TitleEditorComponent` die Bearbeitung des "Titels" für einen ausgewählten `MusicRecord`. Die Komponente umfasst ein Eingabefeld für den neuen Titel sowie "Speichern"- und "Abbrechen"-Schaltflächen.

Um die Bearbeitungskomponente mit der `Table` zu verbinden, wird über einen `VoidElementRenderer` eine "Bearbeiten"-Schaltfläche zur `Table` hinzugefügt. Ein Klick auf diese Schaltfläche löst die Methode `edit()` der `TitleEditorComponent` aus, die es den Benutzern ermöglicht, den "Titel" zu ändern.

## Commit-Methode {#commit-method}

Sobald der Benutzer den Titel ändert und auf die Schaltfläche "Speichern" klickt, löst die `TitleEditorComponent` die Methode `save()` aus. Diese Methode aktualisiert den Titel des entsprechenden `MusicRecord` und löst ein benutzerdefiniertes `SaveEvent` aus.

Die Echtzeitaktualisierung der Daten im Repository erfolgt über die Methode `commit()`. Diese Methode wird innerhalb des `onSave`-Ereignislisteners verwendet, um sicherzustellen, dass Änderungen, die über die Bearbeitungskomponente vorgenommen wurden, im zugrunde liegenden Datensatz widergespiegelt werden.

Die Methode `commit()` wird aufgerufen, um alle interessierten Komponenten darüber zu informieren, dass sich die Daten geändert haben. Die `Table` empfängt das `RepositoryCommitEvent` und aktualisiert sich basierend auf den neuen Daten. 

:::tip Aktualisieren und Erstellen von Einträgen
Der Aufruf der Methode `commit()` aktualisiert sowohl vorhandene Einträge als auch **fügt alle neuen Einträge hinzu, die erstellt wurden**.
:::
