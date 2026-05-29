---
title: Toast
sidebar_position: 140
_i18n_hash: 563743d9f91aff0002f8965cbf719d99
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Ein `Toast` ist eine kleine, temporäre Benachrichtigung, die erscheint, um den Benutzern Feedback zu einer Aktion oder einem Ereignis zu geben. Toasts zeigen Nachrichten wie Erfolgsbestätigungen, Warnungen oder Fehler an, ohne den aktuellen Arbeitsablauf zu unterbrechen, und verschwinden automatisch nach einer festgelegten Dauer.

<!-- INTRO_END -->

## Grundlagen {#basics}

webforJ bietet eine schnelle und einfache Möglichkeit, einen `Toast`-Komponenten in einer einzigen Codezeile mit der Methode `Toast.show()` zu erstellen, die eine `Toast`-Komponente erzeugt, sie zum `Frame` hinzufügt und sie anzeigt. Sie können Parameter an die Methode `show` übergeben, um den angezeigten `Toast` zu konfigurieren:

```java
Toast.show("Operation erfolgreich abgeschlossen!", Theme.SUCCESS);
```

Wenn Sie mehr Kontrolle über die Komponente wünschen, können Sie auch einen `Toast` mit einem Standardkonstruktor erstellen und die Methode `open()` verwenden, um ihn anzuzeigen.

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Standardverhalten
Im Gegensatz zu anderen Komponenten muss ein `Toast` nicht explizit zu einem Container wie einem `Frame` hinzugefügt werden. Wenn Sie die Methode `open()` aufrufen, wird der `Toast` automatisch dem ersten App-`Frame` angehängt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Feedback. Zum Beispiel:

- **Echtzeit-Feedback** für Aktionen wie Formularübermittlungen, Datenspeicherungen oder Fehler.
- **Anpassbare Themen** zur Unterscheidung zwischen Erfolgs-, Fehler-, Warn- oder Informationsnachrichten.
- **Flexible Platzierungs**optionen, um Benachrichtigungen in verschiedenen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsablauf des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer festgelegten Dauer verschwinden oder auf dem Bildschirm verbleiben, bis sie abgelehnt werden, je nach Ihren Bedürfnissen. Sie können die Dauer mit der Methode `setDuration()` anpassen oder einfach einen Dauerparameter an den Konstruktor oder die Methode `show()` übergeben.

:::info Standarddauer
Standardmäßig schließt sich ein `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Persistente Toasts {#persistent-toasts}

Sie können einen persistenten `Toast` erstellen, indem Sie eine negative Dauer einstellen. Persistente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was bei kritischen Warnungen nützlich sein kann oder wenn eine Interaktion oder Bestätigung vom Benutzer erforderlich ist.

:::caution
Seien Sie vorsichtig mit persistenten `Toast`-Benachrichtigungen und stellen Sie sicher, dass Sie dem Benutzer eine Möglichkeit bieten, die Benachrichtigung abzulehnen. Verwenden Sie die Methode `close()`, um den `Toast` zu verbergen, sobald der Benutzer ihn anerkannt hat oder eine erforderliche Interaktion abgeschlossen hat.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit der `Toast`-Komponente von webforJ können Sie wählen, wo die Benachrichtigung auf dem Bildschirm erscheint, um den Design- und Benutzerfreundlichkeitsanforderungen Ihrer App gerecht zu werden. Standardmäßig erscheinen `Toast`-Benachrichtigungen in der unteren Mitte des Bildschirms. 

Sie können die `placement` eines Toasts mit der Methode `setPlacement` unter Verwendung des `Toast.Placement`-Enums mit einem der folgenden Werte festlegen:

- **BOTTOM**: Platziert die Benachrichtigung in der unteren Mitte des Bildschirms.
- **BOTTOM_LEFT**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **BOTTOM_RIGHT**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **TOP**: Platziert die Benachrichtigung in der oberen Mitte des Bildschirms.
- **TOP_LEFT**: Platziert die Benachrichtigung in der oberen linken Ecke des Bildschirms.
- **TOP_RIGHT**: Platziert die Benachrichtigung in der oberen rechten Ecke des Bildschirms.

Diese Optionen ermöglichen es Ihnen, die Platzierung der `Toast`-Benachrichtigung basierend auf dem Design und den Benutzerfreundlichkeitsbedürfnissen Ihrer App zu steuern.

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Durch die Anpassung der Platzierung Ihrer `Toast`-Benachrichtigungen können Sie sicherstellen, dass Benutzer Informationen in einer für die jeweilige App, das Bildschirmlayout und den Kontext geeigneten Weise erhalten.

## Stapelung {#stacking}

Die `Toast`-Komponente kann mehrere Benachrichtigungen gleichzeitig anzeigen, die basierend auf ihrer Platzierung vertikal gestapelt sind. Neuere Benachrichtigungen erscheinen näher am Platzierungsrand und schieben ältere Benachrichtigungen weiter weg. Dies stellt sicher, dass Benutzer wichtige Informationen nicht verpassen, selbst wenn viel los ist.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, ermöglicht webforJ das Hinzufügen von Schaltflächen oder anderen interaktiven Elementen, um sie nützlicher zu machen als einfache Benachrichtigungen. 

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Durch das Hinzufügen dieser Art von Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu bearbeiten und Aktionen durchzuführen, ohne ihren aktuellen Bildschirm zu verlassen, wodurch eine `Toast`-Benachrichtigung zu einem wertvollen Kanal für Interaktion und Engagement wird.

## Styling {#styling}

Sie können `Toast`-Benachrichtigungen mit Themen stilisieren, genau wie andere webforJ-Komponenten, und den Benutzern wertvollen Kontext über die Art der angezeigten Informationen bieten und einen konsistenten Stil in Ihrer App erstellen. Sie können das Thema entweder beim Erstellen des Toasts festlegen oder die Methode `setTheme()` verwenden.

```java
Toast toast = new Toast("Beispielbenachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setTheme(Theme.INFO);
```

### Anpassbare Themen {#custom-themes}

Neben der Verwendung integrierter Themen können Sie auch Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht ein persönlicheres und markenbezogenes Benutzererlebnis und gibt Ihnen die volle Kontrolle über das gesamte Styling des `Toast`.

Um einem `Toast` ein benutzerdefiniertes Thema hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild der Komponente ändern. Das folgende Beispiel zeigt, wie Sie einen `Toast` mit einem benutzerdefinierten Thema mit webforJ erstellen.

:::info `Toast`-Ziel
Da der `Toast` nicht an einer bestimmten Position im DOM liegt, können Sie ihn mit CSS-Variablen ansprechen. Diese Variablen erleichtern es, konsistente benutzerdefinierte Stile in allen Toast-Benachrichtigungen anzuwenden.
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
