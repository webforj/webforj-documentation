---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: e0312bf77de08272221f84c9c231c2df
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Ein `Toast` ist eine kleine, temporäre Benachrichtigung, die erscheint, um den Benutzern Feedback zu einer Aktion oder einem Ereignis zu geben. Toasts zeigen Nachrichten wie Erfolgsbestätigungen, Warnungen oder Fehler an, ohne den aktuellen Arbeitsablauf zu unterbrechen, und verschwinden automatisch nach einer bestimmten Dauer.

<!-- INTRO_END -->

Die Methode `Toast.show()` erstellt einen `Toast`, fügt ihn dem `Frame` hinzu und zeigt ihn in einer einzigen Codezeile an. Übergeben Sie Parameter an `show()`, um den angezeigten `Toast` zu konfigurieren:

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
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info Standardverhalten
Im Gegensatz zu anderen Komponenten muss ein `Toast` nicht explizit zu einem Container wie einem `Frame` hinzugefügt werden. Wenn Sie die Methode `open()` aufrufen, wird der `Toast` automatisch dem ersten App `Frame` angehängt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Feedback. Zum Beispiel:

- **Echtzeit-Feedback** für Aktionen wie Formularübermittlungen, Datenspeicherungen oder Fehler.
- **Anpassbare Themen** zur Unterscheidung zwischen Erfolgs-, Fehler-, Warn- oder Informationsnachrichten.
- **Flexible Platzierungsoptionen**, um Benachrichtigungen in verschiedenen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsablauf des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer bestimmten Dauer verschwinden oder solange auf dem Bildschirm bleiben, bis sie abgelehnt werden, je nach Ihren Bedürfnissen. Sie können die Dauer mit der Methode `setDuration()` anpassen oder einfach einen Dauerparameter an den Konstruktor oder die Methode `show()` übergeben.

:::info Standardeinstellung für die Dauer
Standardmäßig schließt ein `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Beispi Benachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Persistente Toasts {#persistent-toasts}

Sie können einen persistenten `Toast` erstellen, indem Sie eine negative Dauer festlegen. Persistente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was nützlich sein kann für kritische Warnungen oder wenn eine Interaktion oder Bestätigung vom Benutzer erforderlich ist.

:::caution
Seien Sie vorsichtig mit persistenten `Toast`-Benachrichtigungen und stellen Sie sicher, dass es eine Möglichkeit gibt, die Benachrichtigung für den Benutzer anzunehmen. Verwenden Sie die Methode `close()`, um den `Toast` auszublenden, sobald der Benutzer ihn bestätigt hat oder eine erforderliche Interaktion abgeschlossen ist.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit der `Toast`-Komponente von webforJ können Sie wählen, wo die Benachrichtigung auf dem Bildschirm erscheint, um den Design- und Benutzerfreundlichkeitsanforderungen Ihrer App gerecht zu werden. Standardmäßig erscheinen `Toast`-Benachrichtigungen in der Mitte unten auf dem Bildschirm.

Sie können die `placement` eines Toast-Benachrichtigung mit der Methode `setPlacement` unter Verwendung des `Toast.Placement`-Enums mit einem der folgenden Werte festlegen:

- **BOTTOM**: Platziert die Benachrichtigung in der Mitte unten auf dem Bildschirm.
- **BOTTOM_LEFT**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **BOTTOM_RIGHT**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **TOP**: Platziert die Benachrichtigung in der Mitte oben auf dem Bildschirm.
- **TOP_LEFT**: Platziert die Benachrichtigung in der oberen linken Ecke des Bildschirms.
- **TOP_RIGHT**: Platziert die Benachrichtigung in der oberen rechten Ecke des Bildschirms.

Diese Optionen ermöglichen es Ihnen, die Platzierung der `Toast`-Benachrichtigung basierend auf den Anforderungen Ihres App-Designs und der Benutzerfreundlichkeit zu steuern.

```java
Toast toast = new Toast("Beispi Benachrichtigung");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

Durch die Anpassung der Platzierung Ihrer `Toast`-Benachrichtigungen können Sie sicherstellen, dass die Benutzer Informationen auf eine Weise erhalten, die für eine bestimmte App, Bildschirmgestaltung und Kontext angemessen ist.

## Stapelung {#stacking}

Die `Toast`-Komponente kann mehrere Benachrichtigungen gleichzeitig anzeigen, die vertikal gestapelt werden, abhängig von ihrer Platzierung. Neuere Benachrichtigungen erscheinen näher am Platzierungsrand und schieben ältere Benachrichtigungen weiter weg. Dies stellt sicher, dass Benutzer wichtige Informationen nicht verpassen, selbst wenn viel los ist.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, ermöglicht webforJ das Hinzufügen von Schaltflächen oder anderen interaktiven Elementen, um sie nützlicher als einfache Benachrichtigungen zu machen.

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

Durch das Hinzufügen dieser Art von Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu erledigen und Aktionen auszuführen, ohne ihren aktuellen Bildschirm zu verlassen, wodurch eine `Toast`-Benachrichtigung in einen wertvollen Kanal für Interaktion und Engagement verwandelt wird.

## Gestaltung {#styling}

Sie können `Toast`-Benachrichtigungen mit Themen gestalten, genau wie andere webforJ-Komponenten, um den Benutzern wertvollen Kontext über die Art der angezeigten Informationen zu bieten und einen konsistenten Stil in Ihrer App zu schaffen. Sie können das Thema entweder festlegen, wenn Sie den Toast erstellen, oder die Methode `setTheme()` verwenden.

```java
Toast toast = new Toast("Beispi Benachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Beispi Benachrichtigung");
toast.setTheme(Theme.INFO);
```

### Benutzerdefinierte Themen {#custom-themes}

Neben der Verwendung von integrierten Themen können Sie auch Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht ein personalisiertes und markenspezifisches Benutzererlebnis und gibt Ihnen die volle Kontrolle über die gesamte Gestaltung des `Toast`.

Um ein benutzerdefiniertes Thema zu einem `Toast` hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild der Komponente ändern. Im folgenden Beispiel wird gezeigt, wie man einen `Toast` mit einem benutzerdefinierten Thema unter Verwendung von webforJ erstellt.

:::info `Toast`-Ziel
Da sich der `Toast` nicht an einer bestimmten Position im DOM befindet, können Sie ihn mit CSS-Variablen ansprechen. Diese Variablen erleichtern es, konsistente benutzerdefinierte Stile über alle Toast-Benachrichtigungen hinweg anzuwenden.
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
