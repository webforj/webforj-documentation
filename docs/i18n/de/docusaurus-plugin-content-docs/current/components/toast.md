---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: 07365e349ec9393e79a13969504861bd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Ein `Toast` ist eine kleine, temporäre Benachrichtigung, die erscheint, um den Benutzern Feedback zu einer Aktion oder einem Ereignis zu geben. Toasts zeigen Nachrichten wie Erfolgsmeldungen, Warnungen oder Fehler an, ohne den aktuellen Arbeitsablauf zu unterbrechen, und verschwinden automatisch nach einer festgelegten Dauer.

<!-- INTRO_END -->

## Grundlagen {#basics}

webforJ bietet eine schnelle und einfache Möglichkeit, eine `Toast`-Komponente in einer einzigen Codezeile mit der Methode `Toast.show()` zu erstellen, die eine `Toast`-Komponente erstellt, sie zum `Frame` hinzufügt und anzeigt. Sie können Parameter an die Methode `show` übergeben, um den angezeigten `Toast` zu konfigurieren:

```java
Toast.show("Operation erfolgreich abgeschlossen!", Theme.SUCCESS);
```

Wenn Sie mehr Kontrolle über die Komponente haben möchten, können Sie auch einen `Toast` mit einem Standardkonstruktor erstellen und die Methode `open()` verwenden, um ihn anzuzeigen.

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Standardverhalten
Im Gegensatz zu anderen Komponenten muss ein `Toast` nicht explizit zu einem Container wie einem `Frame` hinzugefügt werden. Wenn Sie die Methode `open()` aufrufen, wird der `Toast` automatisch dem ersten Anwendungs-`Frame` angehängt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Feedback. Zum Beispiel:

- **Echtzeit-Feedback** für Aktionen wie Formularübermittlungen, Datenspeicherungen oder Fehler.
- **Anpassbare Themen** zur Unterscheidung zwischen Erfolgs-, Fehler-, Warn- oder Informationsmeldungen.
- **Flexibel platzierbare** Optionen, um Benachrichtigungen in verschiedenen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsablauf des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer festgelegten Dauer verschwinden oder auf dem Bildschirm verbleiben, bis sie geschlossen werden, je nach Bedarf. Sie können die Dauer mit der Methode `setDuration()` anpassen oder einfach einen Dauerparameter an den Konstruktor oder die Methode `show()` übergeben.

:::info Standarddauer
Standardmäßig schließt sich ein `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Permanente Toasts {#persistent-toasts}

Sie können einen permanenten `Toast` erstellen, indem Sie eine negative Dauer festlegen. Permanente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was nützlich sein kann für kritische Warnungen oder in Fällen, in denen eine Interaktion oder Bestätigung vom Benutzer erforderlich ist.

:::caution
Seien Sie vorsichtig mit permanenten `Toast`-Benachrichtigungen, und stellen Sie sicher, dass Sie dem Benutzer eine Möglichkeit geben, die Benachrichtigung abzulehnen. Verwenden Sie die Methode `close()`, um den `Toast` auszublenden, sobald der Benutzer ihn anerkannt oder eine erforderliche Interaktion abgeschlossen hat.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit der `Toast`-Komponente von webforJ können Sie auswählen, wo die Benachrichtigung auf dem Bildschirm erscheint, um den Design- und Benutzeranforderungen Ihrer App zu entsprechen. Standardmäßig erscheinen `Toast`-Benachrichtigungen in der unteren Mitte des Bildschirms.

Sie können die `platzierung` einer Toast-Benachrichtigung mit der Methode `setPlacement` unter Verwendung des Enums `Toast.Placement` mit einem der folgenden Werte festlegen:

- **UNTEN**: Platziert die Benachrichtigung in der unteren Mitte des Bildschirms.
- **UNTEN_LINKS**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **UNTEN_RECHTS**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **OBEN**: Platziert die Benachrichtigung in der oberen Mitte des Bildschirms.
- **OBEN_LINKS**: Platziert die Benachrichtigung in der oberen linken Ecke des Bildschirms.
- **OBEN_RECHTS**: Platziert die Benachrichtigung in der oberen rechten Ecke des Bildschirms.

Diese Optionen ermöglichen es Ihnen, die Platzierung der `Toast`-Benachrichtigung basierend auf dem Design und den Benutzeranforderungen Ihrer App zu steuern.

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

Durch die Anpassung der Platzierung Ihrer `Toast`-Benachrichtigungen können Sie sicherstellen, dass Benutzer Informationen in einer Weise erhalten, die für eine gegebene App, Bildschirmgestaltung und Kontext angemessen ist.

## Stapelung {#stacking}

Die `Toast`-Komponente kann mehrere Benachrichtigungen gleichzeitig anzeigen, die vertikal gestapelt werden, basierend auf ihrer Platzierung. Neuere Benachrichtigungen erscheinen näher am Platzierungsrand und schieben ältere Benachrichtigungen weiter weg. Dies stellt sicher, dass Benutzer wichtige Informationen nicht verpassen, selbst wenn viel los ist.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, ermöglicht es webforJ, Schaltflächen oder andere interaktive Elemente hinzuzufügen, um sie nützlicher als einfache Benachrichtigungen zu gestalten.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Durch das Hinzufügen dieser Art von Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu erledigen und Aktionen auszuführen, ohne ihren aktuellen Bildschirm zu verlassen, und verwandeln eine `Toast`-Benachrichtigung in einen wertvollen Kanal für Interaktion und Engagement.

## Stilgestaltung {#styling}

Sie können `Toast`-Benachrichtigungen mit Themen gestalten, genau wie andere webforJ-Komponenten, und den Benutzern wertvollen Kontext über die Art der angezeigten Informationen bieten und einen konsistenten Stil in Ihrer App schaffen. Sie können entweder das Thema festlegen, wenn Sie den Toast erstellen, oder die Methode `setTheme()` verwenden.

```java
Toast toast = new Toast("Beispielbenachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setTheme(Theme.INFO);
```

### Benutzerdefinierte Themen {#custom-themes}

Neben der Verwendung integrierter Themen können Sie auch Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht ein personalisierteres und markenbezogenes Benutzererlebnis und gibt Ihnen die vollständige Kontrolle über das gesamte Styling des `Toast`.

Um ein benutzerdefiniertes Thema zu einem `Toast` hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild der Komponente ändern. Das folgende Beispiel zeigt, wie man einen `Toast` mit einem benutzerdefinierten Thema in webforJ erstellt.

:::info `Toast` Zielausrichtung
Da sich der `Toast` nicht an einer bestimmten Position im DOM befindet, können Sie ihn mit CSS-Variablen anvisieren. Diese Variablen erleichtern es, konsistente benutzerdefinierte Stile über alle Toast-Benachrichtigungen hinweg anzuwenden.
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
