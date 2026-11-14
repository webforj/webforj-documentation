---
title: Komponenten untersuchen
sidebar_position: 3
sidebar_class_name: new-content
description: >-
  Browse the component tree webforJ built, select components from the page, and
  change their properties while the app runs.
_i18n_hash: 91fbaf620dc8967a50c4a5ce6b455841
---
Der Inspector zeigt den Komponentenbaum, den Ihr Java-Code erstellt hat. Ein `Composite` erscheint als die von Ihnen geschriebene Klasse und enthält die untergeordneten Komponenten in der Reihenfolge, in der webforJ sie verwaltet. Dadurch entspricht die Struktur in craftforJ der Struktur in Ihrem Quellcode.

![Der Komponentenbaum mit einer ausgewählten und in der laufenden App hervorgehobenen Komponente](/img/craftforj/inspector/tree-selection.png#rounded-border)

## Eine Komponente auswählen {#selecting-a-component}

Um eine Komponente auf der Seite auszuwählen, drücken Sie <kbd>Alt</kbd> + <kbd>Shift</kbd> + <kbd>C</kbd> und klicken Sie auf die Komponente. craftforJ wählt den entsprechenden Knoten im Baum aus. Wenn Sie im Baum auf einen Knoten zeigen, wird umgekehrt die zugehörige Komponente auf der Seite hervorgehoben. So können Sie in beide Richtungen zwischen der Oberfläche und dem Baum wechseln.

<VideoPlayer video="craftforj/pick-mode" width={1920} height={992} title="Pick mode" />

Um den Baum zu durchsuchen, drücken Sie <kbd>Cmd/Ctrl</kbd> + <kbd>F</kbd>. Wenn Sie einen Suchbegriff in Schrägstriche einschließen, wird er als regulärer Ausdruck behandelt. Ein Rechtsklick auf einen Knoten öffnet die dafür verfügbaren Aktionen. Sie können den Quellcode öffnen oder die Komponente an den [Assistenten](/docs/craftforj/ai) übergeben.

## Eigenschaften anzeigen und ändern {#reading-and-changing-properties}

Wenn Sie eine Komponente auswählen, werden ihre Eigenschaften in der Seitenleiste angezeigt und nach ihrem Zweck gruppiert. Welche Eigenschaften eine Komponente bietet, hängt von der jeweiligen Komponente ab. Einige Eigenschaften sind schreibgeschützt. Für Eigenschaften, die sich als einfacher Text nicht gut bearbeiten lassen, steht stattdessen ein Editor zur Verfügung, der zum jeweiligen Wert passt. Änderungen werden sofort in der laufenden App übernommen.

:::info Live-Änderungen ändern Ihre Dateien nicht
Eine Änderung an einer Eigenschaft wirkt sich nur auf die App aus, die Sie gerade verwenden. Damit die Änderung in Ihren Quellcode übernommen wird, müssen Sie sie ausdrücklich übernehmen. Wie das geht, wird unter [Änderungen in den Quellcode schreiben](/docs/craftforj/source-changes) beschrieben.
:::

<VideoPlayer video="craftforj/property-edit" width={1920} height={1000} title="Property edit" />

## Den Quellcode einer Komponente anzeigen {#viewing-the-source-of-a-component}

Sie können jede Komponente bis zu dem Java-Code zurückverfolgen, der sie erstellt hat. Standardmäßig wird der Quellcode in craftforJ schreibgeschützt und an der Zeile geöffnet, in der die Komponente erstellt wurde. Sie können craftforJ so konfigurieren, dass der Quellcode stattdessen in Ihrem Editor an derselben Zeile geöffnet wird. Wenn eine Komponente keiner Quellcodezeile zugeordnet werden kann, meldet craftforJ dies, anstatt eine leere Ansicht zu öffnen.

![Die Quellcodeansicht an der Zeile, in der die ausgewählte Komponente erstellt wurde](/img/craftforj/inspector/source-viewer.png#rounded-border)

## Eine Komponente entfernen <DocChip chip='since' label='26.03' /> {#removing-a-component}

Wenn Sie im Baum mit der rechten Maustaste auf eine Komponente klicken und **Komponente entfernen** auswählen, stehen Ihnen zwei Möglichkeiten zum Entfernen zur Verfügung. Bei der einen wird die Komponente aus der App entfernt, die Sie gerade verwenden, bei der anderen aus Ihrem Projekt.

<VideoPlayer video="craftforj/delete-components" width={1872} height={1080} title="Delete components" />

### Nur aus dem Browser entfernen {#removing-from-the-browser-only}

**Nur aus dem Browser entfernen** nimmt die Komponente aus der laufenden App, ohne Ihren Java-Quellcode zu ändern. So können Sie ausprobieren, wie ein Layout ohne eine bestimmte Komponente aussieht, bevor Sie sich festlegen.

Das Entfernen ist vorübergehend. Die Komponente wird wieder angezeigt, sobald die App diese Ansicht erneut aus Ihrem Quellcode erstellt, zum Beispiel nach dem Neuladen der Seite.

### Aus dem Quellcode entfernen {#removing-from-source}

**Aus dem Quellcode entfernen** bearbeitet die Java-Datei, in der die Komponente erstellt wurde. Dadurch bleibt sie auch nach einem erneuten Erstellen der App entfernt. Die Änderung wird sofort geschrieben und nicht zu den [ausstehenden Änderungen](/docs/craftforj/source-changes#pending-changes) hinzugefügt. Daher ist keine Überprüfung erforderlich, bevor sie in Ihrer Datei landet.

Das Schreiben von Java-Code bewirkt, dass Ihre App neu erstellt und gestartet wird – genau wie beim [Übernehmen von Änderungen](/docs/craftforj/source-changes#after-you-apply). Wenn [Live Reload](/docs/configuration/deploy-reload/overview) konfiguriert ist, geschieht dies automatisch. Andernfalls müssen Sie die App selbst neu starten.

Betrachten Sie zum Beispiel ein Formular mit einer Schaltfläche **Save** und einer Schaltfläche **Cancel**, wobei **Save** ein Theme und einen Click-Listener hat:

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

Wenn Sie **Save** auswählen und **Aus dem Quellcode entfernen** wählen, bleibt Folgendes übrig. Die Deklaration, das Theme, der Listener und der Platz der Schaltfläche im Aufruf von `add()` werden entfernt. Auch der Import von `ButtonTheme`, der sonst nicht mehr verwendet wird, entfällt. Nach dem erneuten Erstellen der App ist **Cancel** weiterhin vorhanden:

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

Beim Entfernen wird nur die Komponente selbst berücksichtigt:

- Wenn Sie einen Container entfernen, werden auch die darin enthaltenen Komponenten entfernt.
- Code, der ausschließlich zum Erstellen, Konfigurieren und Einfügen der Komponente vorhanden war, wird bereinigt. Dazu gehören Felder und Imports, die sonst nicht mehr verwendet werden.
- Code, der weiterhin an anderer Stelle benötigt wird, bleibt erhalten. Wenn ein anderer Teil der Klasse noch auf die Komponente zugreift, zum Beispiel der Listener einer anderen Komponente, trennt craftforJ sie lediglich ab und lässt den Code zum Erstellen und Konfigurieren bestehen.
- Wenn Sie eine Instanz einer wiederverwendbaren Komponente entfernen, beispielsweise eines von Ihnen geschriebenen `Composite`, wird nur diese eine Verwendung entfernt. Die Klasse, die die Komponente definiert, wird weder gelöscht noch geändert.

craftforJ schreibt die Datei nur, wenn das Ergebnis weiterhin kompiliert. Dateien werden niemals gelöscht.

### Wenn das Entfernen nicht verfügbar ist {#when-removal-isnt-available}

**Aus dem Quellcode entfernen** wird nur angeboten, wenn das Schreiben von Java-Code sowohl in der [Konfiguration](/docs/craftforj/configuration#feature-flags) der App als auch in der Einstellung **Java-Quellcodeänderungen** in craftforJ zulässig ist. Die Option ist für Komponenten, die in Kotlin erstellt wurden, sowie für Komponenten, die craftforJ keiner Quellcodezeile zuordnen kann, nicht verfügbar.

Einige Komponenten können nicht aus dem Quellcode entfernt werden:

- Routen, die der Router entsprechend ihrer `@Route` platziert
- Der Frame
- Komponenten, die eine andere Komponente als Teil ihrer selbst erstellt. Entfernen Sie stattdessen die äußere Komponente.

craftforJ kann das Entfernen auch ablehnen, wenn es den Quellcode der Komponente nicht findet oder nicht feststellen kann, welcher Code entfernt werden soll. Wenn beispielsweise eine Zeile mehr als eine Komponente desselben Typs erstellt, lässt sich allein anhand dieser Zeile nicht erkennen, welche Komponente Sie ausgewählt haben.

Bei einer Ablehnung bleibt Ihr Quellcode unverändert, und der Grund wird angezeigt:

```
Could not remove FlexLayout from source: FlexLayout is a part ContactCard builds for itself, remove that instead
```

### Ein Entfernen rückgängig machen {#undoing-a-removal}

Um eine Komponente wiederherzustellen, die Sie aus dem Quellcode entfernt haben, drücken Sie in craftforJ <kbd>Cmd/Ctrl</kbd> + <kbd>Z</kbd>. Dadurch wird die Datei auf den Stand vor dem Entfernen zurückgesetzt. Wenn Sie die Datei seitdem in Ihrem eigenen Editor bearbeitet haben, wird das Rückgängigmachen blockiert, statt Ihre Änderungen zu überschreiben. Unter [Rückgängig machen und wiederholen](/docs/craftforj/source-changes#undoing-and-redoing) erfahren Sie, wie das Rückgängigmachen funktioniert und was Sie tun können, wenn es blockiert wird.
