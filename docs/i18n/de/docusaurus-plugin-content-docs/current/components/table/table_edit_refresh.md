---
sidebar_position: 30
title: Editing and Refreshing
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 6ecf362668be7d0633c3c13e7da068ec
---
Die Bearbeitung von Daten innerhalb der `Table` erfolgt durch die Interaktion mit dem `Repository`, das die Daten für die `Table` enthält. Das `Repository` dient als Brücke zwischen der `Table` und dem zugrunde liegenden Datensatz und bietet Methoden zum Abrufen, Modifizieren und Aktualisieren der Daten. Im Folgenden finden Sie ein Beispiel, das das Verhalten implementiert, um den "Titel" einer gewünschten Zeile zu bearbeiten.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
]}
height='600px'
/>

Im obigen Beispiel erleichtert die Klasse `TitleEditorComponent` die Bearbeitung des "Titel"-Feldes für einen ausgewählten `MusicRecord`. Die Komponente enthält ein Eingabefeld für den neuen Titel sowie "Speichern"- und "Abbrechen"-Schaltflächen.

Um die Bearbeitungskomponente mit der `Table` zu verbinden, wird über einen `VoidElementRenderer` eine "Bearbeiten"-Schaltfläche zur `Table` hinzugefügt. Das Klicken auf diese Schaltfläche löst die `edit()`-Methode der `TitleEditorComponent` aus, sodass die Benutzer den "Titel"-Wert ändern können.

## Commit-Methode {#commit-method}

Sobald der Benutzer den Titel ändert und auf die Schaltfläche "Speichern" klickt, löst die `TitleEditorComponent` die `save()`-Methode aus. Diese Methode aktualisiert den Titel des entsprechenden `MusicRecord` und löst ein benutzerdefiniertes `SaveEvent` aus.

Die Echtzeitaktualisierung der Daten im Repository erfolgt durch die `commit()`-Methode. Diese Methode wird im `onSave`-Ereignis-Listener verwendet und stellt sicher, dass Änderungen, die über die Bearbeitungskomponente vorgenommen wurden, im zugrunde liegenden Datensatz widergespiegelt werden.

Die `commit()`-Methode wird aufgerufen, um alle interessierten Komponenten zu benachrichtigen, dass die Daten geändert wurden. Die `Table` erfasst das `RepositoryCommitEvent` und aktualisiert sich basierend auf den neuen Daten.

:::tip Aktualisieren und Erstellen von Einträgen
Der Aufruf der `commit()`-Methode aktualisiert sowohl bestehende Einträge als auch **fügt alle neuen Einträge hinzu, die erstellt wurden**.
:::
