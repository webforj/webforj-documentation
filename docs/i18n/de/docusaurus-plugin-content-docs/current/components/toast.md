---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: d46665cad03749940ad45e5be8b3991d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Ein `Toast` ist eine kleine, temporäre Benachrichtigung, die erscheint, um den Benutzern ein Feedback zu einer Aktion oder einem Ereignis zu geben. Toasts zeigen Nachrichten wie Erfolgsmeldungen, Warnungen oder Fehler an, ohne den aktuellen Arbeitsablauf zu unterbrechen, und verschwinden automatisch nach einer festgelegten Dauer.

<!-- INTRO_END -->

Die Methode `Toast.show()` erstellt einen `Toast`, fügt ihn dem `Frame` hinzu und zeigt ihn in einer einzigen Codezeile an. Übergeben Sie Parameter an `show()`, um den angezeigten `Toast` zu konfigurieren:

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
Im Gegensatz zu anderen Komponenten muss ein `Toast` nicht ausdrücklich in einen Container wie einen `Frame` hinzugefügt werden. Wenn Sie die Methode `open()` aufrufen, wird der `Toast` automatisch am ersten App-`Frame` angehängt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Feedback. Zum Beispiel:

- **Echtzeit-Feedback** bei Aktionen wie Formularübermittlungen, Datenspeicherungen oder Fehlern.
- **Anpassbare Themen** zur Unterscheidung zwischen Erfolg, Fehler, Warnung oder Informationsmeldungen.
- **Flexible Platzierungsoptionen**, um Benachrichtigungen in verschiedenen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsablauf des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer festgelegten Dauer verschwinden oder auf dem Bildschirm verbleiben, bis sie geschlossen werden, je nach Ihren Bedürfnissen. Sie können die Dauer mit der Methode `setDuration()` anpassen oder einfach einen Dauerparameter an den Konstruktor oder die Methode `show()` übergeben.

:::info Standarddauer
Standardmäßig schließt sich ein `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Persistente Toasts {#persistent-toasts}

Sie können einen persistente `Toast` erstellen, indem Sie eine negative Dauer festlegen. Persistente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was nützlich sein kann für kritische Warnungen oder in Fällen, in denen eine Interaktion oder Bestätigung des Benutzers erforderlich ist.

:::caution
Seien Sie vorsichtig mit persistente `Toast`-Benachrichtigungen und stellen Sie sicher, dass Sie einen Weg bieten, wie der Benutzer die Benachrichtigung schließen kann. Verwenden Sie die Methode `close()`, um den `Toast` auszublenden, sobald der Benutzer ihn bestätigt oder die erforderliche Interaktion abgeschlossen hat.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit der `Toast`-Komponente von webforJ können Sie wählen, wo die Benachrichtigung auf dem Bildschirm erscheint, um den Design- und Benutzerfreundlichkeitsanforderungen Ihrer App gerecht zu werden. Standardmäßig erscheinen `Toast`-Benachrichtigungen in der unteren Mitte des Bildschirms.

Sie können die `placement` einer Toast-Benachrichtigung mit der Methode `setPlacement` unter Verwendung des Enums `Toast.Placement` mit einem der folgenden Werte festlegen:

- **BOTTOM**: Platziert die Benachrichtigung in der unteren Mitte des Bildschirms.
- **BOTTOM_LEFT**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **BOTTOM_RIGHT**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **CENTER**: Platziert die Benachrichtigung in der Mitte des Bildschirms.
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

Durch die Anpassung der Platzierung Ihrer `Toast`-Benachrichtigungen können Sie sicherstellen, dass die Benutzer Informationen auf eine für jede gegebene App, Bildschirmlayout und Kontext angemessene Weise erhalten.

## Stapeln {#stacking}

Die `Toast`-Komponente kann mehrere Benachrichtigungen gleichzeitig anzeigen und diese vertikal basierend auf ihrer Platzierung stapeln. Neuere Benachrichtigungen erscheinen näher an der Platzierungsgrenze und drücken ältere Benachrichtigungen weiter weg. Dies stellt sicher, dass Benutzer wichtige Informationen nicht verpassen, selbst wenn viel passiert.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, ermöglicht webforJ Ihnen, Schaltflächen oder andere interaktive Elemente hinzuzufügen, um sie nützlicher zu machen als einfache Benachrichtigungen.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Durch das Hinzufügen dieser Art von Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu erledigen und Aktionen auszuführen, ohne von ihrem aktuellen Bildschirm weg navigieren zu müssen, wodurch eine `Toast`-Benachrichtigung zu einem wertvollen Kanal für Interaktion und Engagement wird.

## Styling {#styling}

Sie können `Toast`-Benachrichtigungen mit Themen stylen, genau wie andere webforJ-Komponenten, um den Benutzern wertvollen Kontext über die Art der angezeigten Informationen zu geben und einen konsistenten Stil in Ihrer App zu schaffen. Sie können entweder das Thema festlegen, wenn Sie den Toast erstellen, oder die Methode `setTheme()` verwenden.

```java
Toast toast = new Toast("Beispielbenachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setTheme(Theme.INFO);
```

### Anpassbare Themen {#custom-themes}

Zusätzlich zur Verwendung integrierter Themen können Sie Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht ein personalisiertes und gebrandetes Benutzererlebnis und gibt Ihnen die volle Kontrolle über das gesamte Styling des `Toast`.

Um ein benutzerdefiniertes Thema zu einem `Toast` hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild der Komponente ändern. Das folgende Beispiel zeigt, wie Sie einen `Toast` mit einem benutzerdefinierten Thema mit webforJ erstellen.

:::info `Toast` Targeting
Da sich der `Toast` nicht an einer bestimmten Position im DOM befindet, können Sie ihn mithilfe von CSS-Variablen anvisieren. Diese Variablen erleichtern es, einheitliche benutzerdefinierte Stile über alle Toast-Benachrichtigungen hinweg anzuwenden.
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
