---
sidebar_position: 30
title: Bearbeiten und Aktualisieren
slug: refreshing
description: >-
  Edit Table rows by mutating the bound Repository and call commit to refresh
  the UI through RepositoryCommitEvent.
_i18n_hash: 84dec48293e6fa794713881310b6f303
---
Das Bearbeiten von Daten in der `Table` erfolgt durch Interaktion mit dem `Repository`, das die Daten für die `Table` enthält. Das `Repository` dient als Schnittstelle zwischen der `Table` und dem zugrunde liegenden Datensatz und stellt Methoden zum Abrufen, Ändern und Aktualisieren von Daten bereit. Das folgende Beispiel implementiert das Bearbeiten des „Titels“ einer gewünschten Zeile.

<ComponentDemo
path='/webforj/tableeditdata'
files={[
  'src/main/java/com/webforj/samples/views/table/TableEditDataView.java',
  'src/main/java/com/webforj/samples/views/table/TitleEditorComponent.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Im obigen Beispiel ermöglicht die Klasse `TitleEditorComponent` das Bearbeiten des Felds „Titel“ eines ausgewählten `MusicRecord`. Die Komponente enthält ein Eingabefeld für den neuen Titel sowie die Schaltflächen „Speichern“ und „Abbrechen“.

Um die Bearbeitungskomponente mit der `Table` zu verbinden, wird der `Table` über einen `VoidElementRenderer` eine Schaltfläche „Bearbeiten“ hinzugefügt. Ein Klick auf diese Schaltfläche löst die Methode `edit()` von `TitleEditorComponent` aus, sodass Benutzer den Wert für „Titel“ ändern können.

## Commit-Methode {#commit-method}

Sobald der Benutzer den Titel ändert und auf die Schaltfläche „Speichern“ klickt, löst `TitleEditorComponent` die Methode `save()` aus. Diese Methode aktualisiert den Titel des entsprechenden `MusicRecord` und löst ein benutzerdefiniertes `SaveEvent` aus.

Die Daten im Repository werden mithilfe der Methode `commit()` in Echtzeit aktualisiert. Diese Methode wird im `onSave`-Ereignis-Listener aufgerufen, damit Änderungen, die über die Bearbeitungskomponente vorgenommen wurden, im zugrunde liegenden Datensatz übernommen werden.

Die Methode `commit()` wird aufgerufen, um alle interessierten Komponenten darüber zu informieren, dass sich die Daten geändert haben. Die `Table` fängt das `RepositoryCommitEvent` ab und aktualisiert sich anhand der neuen Daten.

:::tip Einträge aktualisieren und erstellen
Der Aufruf der Methode `commit()` aktualisiert sowohl vorhandene Einträge als auch **fügt neu erstellte Einträge ein**.
:::
