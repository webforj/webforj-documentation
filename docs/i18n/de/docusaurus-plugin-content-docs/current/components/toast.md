---
title: Toast
sidebar_position: 140
_i18n_hash: 0ac4df1a045e2706f2e9309327ba4683
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Ein `Toast` ist eine kleine, temporäre Benachrichtigung, die erscheint, um den Benutzern Feedback zu einer Aktion oder einem Ereignis zu geben. Toasts zeigen Nachrichten wie Bestätigungen für erfolgreiche Aktionen, Warnungen oder Fehler, ohne den aktuellen Arbeitsablauf zu unterbrechen, und verschwinden automatisch nach einer festgelegten Dauer.

<!-- INTRO_END -->

## Grundlagen {#basics}

webforJ bietet eine schnelle und einfache Möglichkeit, eine `Toast`-Komponente in einer einzigen Codezeile mit der `Toast.show()`-Methode zu erstellen, die eine `Toast`-Komponente erstellt, sie zum `Frame` hinzufügt und anzeigt. Sie können Parameter an die `show`-Methode übergeben, um den angezeigten `Toast` zu konfigurieren:

```java
Toast.show("Operation erfolgreich abgeschlossen!", Theme.SUCCESS);
```

Wenn Sie mehr Kontrolle über die Komponente wünschen, können Sie auch einen `Toast` mit einem Standardkonstruktor erstellen und die `open()`-Methode verwenden, um ihn anzuzeigen.

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
height='200px'
/>

:::info Standardverhalten
Im Gegensatz zu anderen Komponenten muss ein `Toast` nicht ausdrücklich zu einem Container wie einem `Frame` hinzugefügt werden. Wenn Sie die `open()`-Methode aufrufen, wird der `Toast` automatisch dem ersten App-`Frame` hinzugefügt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Feedback. Zum Beispiel:

- **Echtzeit-Feedback** für Aktionen wie Formularübermittlungen, Datenspeicherungen oder Fehler.
- **Anpassbare Themen**, um zwischen Erfolgs-, Fehler-, Warn- oder Informationsnachrichten zu unterscheiden.
- **Flexible Platzierungs**-Optionen, um Benachrichtigungen in verschiedenen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsablauf des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer festgelegten Dauer verschwinden oder auf dem Bildschirm bleiben, bis sie abgelehnt werden, je nach Ihren Bedürfnissen. Sie können die Dauer mit der `setDuration()`-Methode anpassen oder einfach einen Dauerparameter an den Konstruktor oder die `show()`-Methode übergeben.

:::info Standarddauer
Standardmäßig schließt ein `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Persistente Toasts {#persistent-toasts}

Sie können einen persistenten `Toast` erstellen, indem Sie eine negative Dauer festlegen. Persistente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was nützlich sein kann für kritische Warnungen oder in Fällen, in denen eine Interaktion oder Bestätigung vom Benutzer erforderlich ist.

:::caution
Seien Sie vorsichtig mit persistierenden `Toast`-Benachrichtigungen und stellen Sie sicher, dass Sie eine Möglichkeit bieten, die Benachrichtigung für den Benutzer abzulehnen. Verwenden Sie die `close()`-Methode, um den `Toast` auszublenden, sobald der Benutzer ihn anerkannt oder eine erforderliche Interaktion abgeschlossen hat.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit der `Toast`-Komponente von webforJ können Sie auswählen, wo die Benachrichtigung auf dem Bildschirm erscheinen soll, um den Design- und Benutzerfreundlichkeitsanforderungen Ihrer App gerecht zu werden. Standardmäßig erscheinen `Toast`-Benachrichtigungen in der Mitte unten auf dem Bildschirm.

Sie können die `Platzierung` einer Toast-Benachrichtigung mit der `setPlacement`-Methode unter Verwendung des `Toast.Placement`-Enums mit einem der folgenden Werte festlegen:

- **UNTEN**: Platziert die Benachrichtigung in der Mitte unten auf dem Bildschirm.
- **UNTEN_LINKS**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **UNTEN_RECHTS**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **OBEN**: Platziert die Benachrichtigung in der Mitte oben auf dem Bildschirm.
- **OBEN_LINKS**: Platziert die Benachrichtigung in der oberen linken Ecke des Bildschirms.
- **OBEN_RECHTS**: Platziert die Benachrichtigung in der oberen rechten Ecke des Bildschirms.

Diese Optionen ermöglichen es Ihnen, die Platzierung der `Toast`-Benachrichtigung basierend auf dem Design und den Benutzerbedürfnissen Ihrer App zu steuern.

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

Indem Sie die Platzierung Ihrer `Toast`-Benachrichtigungen anpassen, können Sie sicherstellen, dass Benutzer Informationen auf eine Weise erhalten, die für jede App, Bildschirmgestaltung und Kontext angemessen ist.

## Stapeln {#stacking}

Die `Toast`-Komponente kann mehrere Benachrichtigungen gleichzeitig anzeigen und sie basierend auf ihrer Platzierung vertikal stapeln. Neuere Benachrichtigungen erscheinen näher am Platzierungsrand und drücken ältere Benachrichtigungen weiter weg. Dies stellt sicher, dass die Benutzer wichtige Informationen nicht verpassen, selbst wenn viel los ist.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, ermöglicht es webforJ, Schaltflächen oder andere interaktive Elemente hinzuzufügen, um sie nützlicher als einfache Benachrichtigungen zu machen.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Durch das Hinzufügen dieser Art von Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu erledigen und Aktionen auszuführen, ohne ihren aktuellen Bildschirm zu verlassen, und verwandeln eine `Toast`-Benachrichtigung in einen wertvollen Kanal für Interaktion und Engagement.

## Styling {#styling}

Sie können `Toast`-Benachrichtigungen mit Themen stylen, genau wie andere webforJ-Komponenten, und den Benutzern wertvollen Kontext über die Art der angezeigten Informationen bieten und einen konsistenten Stil in Ihrer App schaffen. Sie können das Thema entweder beim Erstellen des Toasts festlegen oder die `setTheme()`-Methode verwenden.

```java
Toast toast = new Toast("Beispielbenachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setTheme(Theme.INFO);
```

### Benutzerdefinierte Themen {#custom-themes}

Neben der Verwendung von integrierten Themen können Sie auch Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht eine persönlichere und markengerechte Benutzererfahrung, wodurch Sie die vollständige Kontrolle über das Gesamterscheinungsbild des `Toast` haben.

Um einem `Toast` ein benutzerdefiniertes Thema hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild der Komponente ändern. Das folgende Beispiel zeigt, wie Sie einen `Toast` mit einem benutzerdefinierten Thema in webforJ erstellen.

:::info `Toast` Targeting
Da sich der `Toast` nicht an einem bestimmten Ort im DOM befindet, können Sie ihn mit CSS-Variablen anvisieren. Diese Variablen erleichtern es, konsistente benutzerdefinierte Stile über alle Toast-Benachrichtigungen hinweg anzuwenden.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
