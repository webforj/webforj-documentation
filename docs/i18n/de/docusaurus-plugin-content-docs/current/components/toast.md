---
title: Toast
sidebar_position: 140
_i18n_hash: 7350867dde3a34f2c5fe2e40c4d745c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Eine `Toast`-Benachrichtigung ist eine subtile und unauffällige Popup-Benachrichtigung, die entwickelt wurde, um Benutzern in Echtzeit Rückmeldungen und Informationen zu geben. Diese Benachrichtigungen werden typischerweise verwendet, um Benutzer über Vorgänge wie erfolgreiche Aktionen, Warnungen oder Fehler zu informieren, ohne ihren Arbeitsfluss zu unterbrechen. `Toast`-Benachrichtigungen verschwinden in der Regel nach einer bestimmten Zeit und erfordern keine Benutzerreaktion.

Mit dem `Toast`-Komponent von webforJ können Sie diese Benachrichtigungen ganz einfach implementieren, um die Benutzererfahrung zu verbessern, indem Sie relevante Informationen auf eine vertraute, unauffällige und nahtlose Weise bereitstellen.

## Grundlagen {#basics}

webforJ bietet eine schnelle und einfache Möglichkeit, eine `Toast`-Komponente mit einer einzigen Codezeile über die Methode `Toast.show()` zu erstellen, die eine `Toast`-Komponente erstellt, sie zum `Frame` hinzufügt und sie anzeigt. Sie können Parameter an die Methode `show` übergeben, um die angezeigte `Toast` zu konfigurieren:

```java
Toast.show("Operation erfolgreich abgeschlossen!", Theme.SUCCESS);
```

Wenn Sie mehr Kontrolle über die Komponente wünschen, können Sie auch eine `Toast` mit einem Standardkonstruktor erstellen und die Methode `open()` verwenden, um sie anzuzeigen.

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
Im Gegensatz zu anderen Komponenten muss eine `Toast`-Benachrichtigung nicht explizit zu einem Container wie einem `Frame` hinzugefügt werden. Wenn Sie die Methode `open()` aufrufen, wird die `Toast` automatisch dem ersten App-`Frame` angefügt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Feedback. Beispielsweise:

- **Echtzeit-Feedback** für Aktionen wie Formularübermittlungen, Datenspeicherungen oder Fehler.
- **Anpassbare Themen** zur Unterscheidung zwischen Erfolgen, Fehlern, Warnungen oder informativen Nachrichten.
- **Flexible Platzierungs**optionen, um Benachrichtigungen in verschiedenen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsfluss des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer bestimmten Dauer verschwinden oder auf dem Bildschirm bleiben, bis sie geschlossen werden, je nach Ihren Bedürfnissen. Sie können die Dauer mit der Methode `setDuration()` anpassen oder einfach einen Dauerparameter an den Konstruktor oder die Methode `show()` übergeben.

:::info Standarddauer
Standardmäßig schließt eine `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Stichprobenbenachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Persistente Toasts {#persistent-toasts}

Sie können eine persistente `Toast` erstellen, indem Sie eine negative Dauer festlegen. Persistente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was für kritische Warnungen oder in Fällen nützlich sein kann, in denen eine Interaktion oder Bestätigung des Benutzers erforderlich ist.

:::caution
Seien Sie vorsichtig mit persistenten `Toast`-Benachrichtigungen und stellen Sie sicher, dass Sie dem Benutzer eine Möglichkeit geben, die Benachrichtigung zu schließen. Verwenden Sie die Methode `close()`, um die `Toast` zu verbergen, sobald der Benutzer sie anerkannt hat oder erforderliche Interaktionen abgeschlossen sind.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit der `Toast`-Komponente von webforJ können Sie wählen, wo die Benachrichtigung auf dem Bildschirm erscheinen soll, um den Design- und Usability-Anforderungen Ihrer App gerecht zu werden. Standardmäßig erscheinen `Toast`-Benachrichtigungen am unteren Bildschirmrand in der Mitte.

Sie können die `platzierung` einer Toast-Benachrichtigung mit der Methode `setPlacement` unter Verwendung des Enums `Toast.Placement` mit einem der folgenden Werte festlegen:

- **UNTEN**: Platziert die Benachrichtigung am unteren Bildschirmrand in der Mitte.
- **UNTEN_LINKS**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **UNTEN_RECHTS**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **OBEN**: Platziert die Benachrichtigung am oberen Bildschirmrand in der Mitte.
- **OBEN_LINKS**: Platziert die Benachrichtigung in der oberen linken Ecke des Bildschirms.
- **OBEN_RECHTS**: Platziert die Benachrichtigung in der oberen rechten Ecke des Bildschirms.

Diese Optionen ermöglichen es Ihnen, die Platzierung der `Toast`-Benachrichtigung basierend auf den Design- und Usability-Anforderungen Ihrer App zu steuern.

```java
Toast toast = new Toast("Stichprobenbenachrichtigung");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

Durch die Anpassung der Platzierung Ihrer `Toast`-Benachrichtigungen können Sie sicherstellen, dass Benutzer Informationen auf eine Weise erhalten, die für jede gegebene App, Bildschirmlayout und Kontext geeignet ist.

## Stapelung {#stacking}

Die `Toast`-Komponente kann mehrere Benachrichtigungen gleichzeitig anzeigen und sie vertikal basierend auf ihrer Platzierung stapeln. Neuere Benachrichtigungen erscheinen näher am Platzierungsrand und schieben ältere Benachrichtigungen weiter weg. Dies stellt sicher, dass Benutzer wichtige Informationen nicht verpassen, auch wenn gleichzeitig viele Vorgänge stattfinden.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, ermöglicht es webforJ, Schaltflächen oder andere interaktive Elemente hinzuzufügen, um sie nützlicher als einfache Benachrichtigungen zu machen.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Durch das Hinzufügen solcher Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu erledigen und Aktionen auszuführen, ohne ihren aktuellen Bildschirm zu verlassen, und eine `Toast`-Benachrichtigung in einen wertvollen Kanal der Interaktion und des Engagements verwandeln.

## Styling {#styling}

Sie können `Toast`-Benachrichtigungen genauso wie andere webforJ-Komponenten mit Themen gestalten, wodurch den Benutzern wertvoller Kontext über die Art der angezeigten Informationen gegeben wird und ein konsistenter Stil in Ihrer App geschaffen wird. Sie können das Thema entweder festlegen, wenn Sie die Toast erstellen, oder die Methode `setTheme()` verwenden.

```java
Toast toast = new Toast("Stichprobenbenachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Stichprobenbenachrichtigung");
toast.setTheme(Theme.INFO);
```

### Benutzerdefinierte Themen {#custom-themes}

Neben der Verwendung von integrierten Themen können Sie Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht ein persönlicheres und markenspezifisches Benutzererlebnis und gibt Ihnen die volle Kontrolle über das gesamte Styling des `Toast`.

Um ein benutzerdefiniertes Thema zu einer `Toast` hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild der Komponente anpassen. Das folgende Beispiel zeigt, wie Sie eine `Toast` mit einem benutzerdefinierten Thema mithilfe von webforJ erstellen können.

:::info `Toast`-Ziel
Da sich die `Toast` nicht an einem bestimmten Ort im DOM befindet, können Sie sie mit CSS-Variablen ansprechen. Diese Variablen erleichtern das Anwenden konsistenter benutzerdefinierter Stile auf alle `Toast`-Benachrichtigungen.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
