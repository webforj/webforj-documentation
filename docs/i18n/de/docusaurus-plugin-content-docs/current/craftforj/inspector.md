---
title: Komponenten untersuchen
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 585425968807283ea392911908fbd030
---
Der Inspektor zeigt den Komponentenbaum, den Ihr Java-Code erstellt hat. Ein `Composite` erscheint als die von Ihnen geschriebene Klasse und enthält die untergeordneten Komponenten in der Reihenfolge, in der webforJ sie verwaltet. So entspricht die Struktur in craftforJ der Struktur in Ihrem Quellcode.

![Der Komponentenbaum mit einer ausgewählten und in der laufenden App hervorgehobenen Komponente](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Eine Komponente auswählen {#selecting-a-component}

Um eine Komponente auf der Seite auszuwählen, drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> und klicken Sie auf die Komponente. craftforJ wählt den entsprechenden Knoten im Baum aus. Wenn Sie den Mauszeiger über einen Knoten im Baum bewegen, wird umgekehrt die entsprechende Komponente auf der Seite hervorgehoben. So können Sie sich in beide Richtungen zwischen der Ansicht und dem Baum bewegen.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/pick-mode.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

Um den Baum zu durchsuchen, drücken Sie <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Wenn Sie einen Suchbegriff in Schrägstriche einschließen, wird er als regulärer Ausdruck behandelt. Ein Rechtsklick auf einen Knoten öffnet die dafür verfügbaren Aktionen. Sie können den Quellcode öffnen oder die Komponente an den [Assistenten](/docs/craftforj/ai) übergeben.

## Eigenschaften anzeigen und ändern {#reading-and-changing-properties}

Wenn Sie eine Komponente auswählen, werden ihre Eigenschaften in der Seitenleiste nach ihrer Funktion gruppiert angezeigt. Welche Eigenschaften eine Komponente bietet, hängt von der jeweiligen Komponente ab; manche Eigenschaften sind schreibgeschützt. Für Eigenschaften, die sich als einfacher Text nicht gut darstellen lassen, wird stattdessen ein Editor verwendet, der zum jeweiligen Wert passt. Änderungen an einem Wert werden sofort in der laufenden App wirksam.

:::info Live-Bearbeitungen ändern Ihre Dateien nicht
Eine Änderung an einer Eigenschaft wirkt sich nur auf die App aus, die Sie gerade vor sich sehen. Damit die Änderung in Ihren Quellcode übernommen wird, ist ein separater, bewusster Schritt erforderlich, der unter [Änderungen in den Quellcode schreiben](/docs/craftforj/source-changes) beschrieben wird.
:::

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/property-edit.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Den Quellcode einer Komponente anzeigen {#viewing-the-source-of-a-component}

Sie können jede Komponente bis zu dem Java-Code zurückverfolgen, mit dem sie erstellt wurde. Standardmäßig wird der Quellcode in craftforJ schreibgeschützt und an der Zeile geöffnet, in der die Komponente erstellt wurde. Sie können craftforJ so konfigurieren, dass der Quellcode stattdessen an derselben Zeile in Ihrem Editor geöffnet wird. Wenn sich eine Komponente keiner Zeile zuordnen lässt, meldet craftforJ dies, anstatt eine leere Ansicht zu öffnen.

![Der Quellcode-Viewer an der Zeile, in der die ausgewählte Komponente erstellt wurde](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Eine Komponente entfernen <DocChip chip='since' label='26.03' /> {#removing-a-component}

Wenn Sie im Baum mit der rechten Maustaste auf eine Komponente klicken und **Komponente entfernen** auswählen, haben Sie zwei Möglichkeiten, sie zu entfernen. Mit einer wird sie aus der App entfernt, die Sie gerade vor sich sehen, mit der anderen aus Ihrem Projekt.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/delete-components.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

### Nur aus dem Browser entfernen {#removing-from-the-browser-only}

**Nur aus dem Browser entfernen** entfernt die Komponente aus der laufenden App und lässt Ihren Java-Quellcode unverändert. Nutzen Sie diese Option, um zu prüfen, wie ein Layout ohne eine bestimmte Komponente aussieht, bevor Sie sich festlegen.

Die Entfernung ist vorübergehend. Die Komponente wird wieder angezeigt, sobald die App diese Ansicht erneut aus Ihrem Quellcode erstellt, zum Beispiel nach dem Neuladen der Seite.

### Aus dem Quellcode entfernen {#removing-from-source}

**Aus dem Quellcode entfernen** bearbeitet die Java-Datei, in der die Komponente erstellt wurde. Dadurch bleibt sie auch nach einem erneuten Erstellen der App entfernt. Die Änderung wird sofort geschrieben und nicht zu den [ausstehenden Änderungen](/docs/craftforj/source-changes#pending-changes) hinzugefügt. Daher gibt es keinen Prüfschritt, bevor sie in Ihrer Datei landet.

Das Schreiben von Java-Code führt dazu, dass Ihre App neu erstellt und neu gestartet wird – genauso wie beim [Übernehmen von Änderungen](/docs/craftforj/source-changes#after-you-apply). Wenn [Live Reload](/docs/configuration/deploy-reload/overview) konfiguriert ist, geschieht das automatisch. Andernfalls müssen Sie die App selbst neu starten.

Angenommen, Sie haben ein Formular mit den Schaltflächen **Speichern** und **Abbrechen**, wobei **Speichern** ein Theme und einen Click-Listener hat:

```java
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button save = new Button("Save");
    save.setTheme(ButtonTheme.PRIMARY);
    save.onClick(event -> {
      save.setEnabled(false);
    });
    Button cancel = new Button("Cancel");
    layout.add(save, cancel);
  }
}
```

Wenn Sie **Speichern** auswählen und **Aus dem Quellcode entfernen** wählen, bleibt Folgendes übrig. Die Deklaration, das Theme, der Listener und der Platz der Schaltfläche im Aufruf von `add()` werden entfernt. Auch der Import von `ButtonTheme` entfällt, da er sonst nirgends verwendet wird. Nach dem erneuten Erstellen der App ist **Abbrechen** weiterhin vorhanden:

```java
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexLayout;

public class ContactFormView {
  public ContactFormView(FlexLayout layout) {
    layout.setSpacing("1em");
    Button cancel = new Button("Cancel");
    layout.add(cancel);
  }
}
```

Beim Entfernen wird nur die Komponente selbst und nichts darüber hinaus berücksichtigt:

- Wenn Sie einen Container entfernen, werden auch die darin enthaltenen Komponenten entfernt.
- Code, der nur zum Erstellen, Konfigurieren und Hinzufügen der Komponente vorhanden war, wird bereinigt. Dazu gehören Felder und Importe, die sonst nirgends verwendet werden.
- Code, der an anderer Stelle weiterhin benötigt wird, bleibt erhalten. Wenn ein anderer Teil der Klasse die Komponente noch verwendet, etwa der Listener einer anderen Komponente, trennt craftforJ sie lediglich ab und lässt den Code, der sie erstellt und konfiguriert, bestehen.
- Wenn Sie eine Instanz einer wiederverwendbaren Komponente entfernen, zum Beispiel eines von Ihnen geschriebenen `Composite`, wird nur diese eine Verwendung entfernt. Die Klasse, in der sie definiert ist, wird weder gelöscht noch geändert.

craftforJ schreibt die Datei nur, wenn das Ergebnis weiterhin kompiliert. Dateien werden niemals gelöscht.

### Wenn das Entfernen nicht verfügbar ist {#when-removal-isnt-available}

**Aus dem Quellcode entfernen** wird nur angeboten, wenn das Schreiben von Java-Code sowohl in der [Konfiguration](/docs/craftforj/configuration#feature-flags) der App als auch in der Einstellung **Java-Quellcodeänderungen** in craftforJ zulässig ist. Diese Option wird nicht für Komponenten angeboten, die in Kotlin erstellt wurden, oder für Komponenten, die craftforJ keiner Quellcodezeile zuordnen kann.

Manche Komponenten können gar nicht aus dem Quellcode entfernt werden:

- Routen, die der Router anhand ihrer `@Route`-Annotation platziert
- Der Frame
- Komponenten, die eine andere Komponente als Teil ihrer selbst erstellt. Entfernen Sie stattdessen die äußere Komponente.

craftforJ kann eine Entfernung auch ablehnen, wenn der Quellcode der Komponente nicht gefunden wird oder sich nicht eindeutig feststellen lässt, welcher Code entfernt werden soll. Wenn beispielsweise eine Zeile mehrere Komponenten desselben Typs erstellt, lässt sich allein anhand dieser Zeile nicht erkennen, welche davon Sie ausgewählt haben.

Bei einer abgelehnten Entfernung bleiben Ihre Dateien unverändert und der Grund wird angezeigt:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Eine Entfernung rückgängig machen {#undoing-a-removal}

Um eine aus dem Quellcode entfernte Komponente wiederherzustellen, drücken Sie in craftforJ <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>. Dadurch wird die Datei auf den Stand vor dem Entfernen zurückgesetzt. Wenn Sie die Datei seitdem in Ihrem eigenen Editor bearbeitet haben, wird das Rückgängigmachen blockiert, anstatt Ihre Änderungen zu überschreiben. Unter [Rückgängig machen und wiederholen](/docs/craftforj/source-changes#undoing-and-redoing) erfahren Sie, wie das Rückgängigmachen funktioniert und was bei einer Blockierung zu tun ist.
