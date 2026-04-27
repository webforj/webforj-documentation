---
title: Toast
sidebar_position: 140
_i18n_hash: c98ff64ae02ebe46d84c803492685d05
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Ein `Toast` ist eine kleine, temporäre Benachrichtigung, die erscheint, um den Benutzern Rückmeldungen zu einer Aktion oder einem Ereignis zu geben. Toasts zeigen Nachrichten wie Erfolgsbestätigungen, Warnungen oder Fehler an, ohne den aktuellen Arbeitsablauf zu unterbrechen, und verschwinden automatisch nach einer festgelegten Dauer.

<!-- INTRO_END -->

## Grundlagen {#basics}

webforJ bietet eine schnelle und einfache Möglichkeit, einen `Toast`-Komponente in einer einzigen Codezeile mit der Methode `Toast.show()` zu erstellen, die eine `Toast`-Komponente erstellt, sie zum `Frame` hinzufügt und anzeigt. Sie können Parameter an die `show`-Methode übergeben, um den angezeigten `Toast` zu konfigurieren:

```java
Toast.show("Operation erfolgreich abgeschlossen!", Theme.SUCCESS);
```

Wenn Sie eine detailliertere Kontrolle über die Komponente wünschen, können Sie auch einen `Toast` mit einem Standardkonstruktor erstellen und die Methode `open()` verwenden, um ihn anzuzeigen.

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

:::info Standardverhalten
Im Gegensatz zu anderen Komponenten muss ein `Toast` nicht explizit zu einem Container wie einem `Frame` hinzugefügt werden. Wenn Sie die Methode `open()` aufrufen, wird der `Toast` automatisch am ersten App `Frame` angehängt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Rückmeldungen. Zum Beispiel:

- **Echtzeit-Rückmeldungen** für Aktionen wie Formularübermittlungen, Datenspeicherungen oder Fehler.
- **Anpassbare Themen**, um zwischen Erfolgs-, Fehler-, Warn- oder Informationsnachrichten zu unterscheiden.
- **Flexible Platzierungsoptionen**, um Benachrichtigungen in verschiedenen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsablauf des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer festgelegten Dauer verschwinden oder auf dem Bildschirm bleiben, bis sie geschlossen werden, je nach Ihren Bedürfnissen. Sie können die Dauer mit der Methode `setDuration()` anpassen oder einfach einen Dauerparameter an den Konstruktor oder die `show()`-Methode übergeben.

:::info Standarddauer
Standardmäßig schließt ein `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Persistente Toasts {#persistent-toasts}

Sie können einen persistenten `Toast` erstellen, indem Sie eine negative Dauer einstellen. Persistente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was nützlich sein kann für kritische Warnungen oder in Fällen, in denen eine Interaktion oder Bestätigung vom Benutzer erforderlich ist.

:::caution
Seien Sie vorsichtig mit persistenten `Toast`-Benachrichtigungen und stellen Sie sicher, dass Sie eine Möglichkeit bieten, die Benachrichtigung zu schließen. Verwenden Sie die Methode `close()`, um den `Toast` auszublenden, sobald der Benutzer ihn anerkannt hat oder eine erforderliche Interaktion abgeschlossen ist.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit der `Toast`-Komponente von webforJ können Sie auswählen, wo die Benachrichtigung auf dem Bildschirm erscheint, um den Design- und Benutzeranforderungen Ihrer App gerecht zu werden. Standardmäßig erscheinen `Toast`-Benachrichtigungen in der unteren Mitte des Bildschirms.

Sie können die `platzierung` einer Toast-Benachrichtigung mit der Methode `setPlacement` unter Verwendung der `Toast.Placement`-Enum mit einem der folgenden Werte festlegen:

- **UNTEN**: Platziert die Benachrichtigung in der unteren Mitte des Bildschirms.
- **UNTEN_LINKS**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **UNTEN_RECHTS**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **OBEN**: Platziert die Benachrichtigung in der oberen Mitte des Bildschirms.
- **OBEN_LINKS**: Platziert die Benachrichtigung in der oberen linken Ecke des Bildschirms.
- **OBEN_RECHTS**: Platziert die Benachrichtigung in der oberen rechten Ecke des Bildschirms.

Diese Optionen ermöglichen es Ihnen, die Platzierung der `Toast`-Benachrichtigung basierend auf den Design- und Benutzerbedürfnissen Ihrer App zu steuern.

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='600px'
/>

Durch die Anpassung der Platzierung Ihrer `Toast`-Benachrichtigungen können Sie sicherstellen, dass Benutzer Informationen auf eine Weise erhalten, die für jede gegebene App, Bildschirmgestaltung und Kontext angemessen ist.

## Stapeln {#stacking}

Die `Toast`-Komponente kann mehrere Benachrichtigungen gleichzeitig anzeigen, die vertikal basierend auf ihrer Platzierung gestapelt werden. Neuere Benachrichtigungen erscheinen näher an der Platzierungskante, wodurch ältere Benachrichtigungen weiter entfernt gedrängt werden. Dies stellt sicher, dass die Benutzer keine wichtigen Informationen verpassen, auch wenn viel los ist.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, erlaubt es webforJ, Schaltflächen oder andere interaktive Elemente hinzuzufügen, um sie nützlicher zu machen als einfache Benachrichtigungen.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Durch das Hinzufügen dieser Art von Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu erledigen und Aktionen durchzuführen, ohne von ihrem aktuellen Bildschirm navigieren zu müssen, wodurch eine `Toast`-Benachrichtigung zu einem wertvollen Kanal für Interaktion und Engagement wird. 

## Styling {#styling}

Sie können `Toast`-Benachrichtigungen mit Themen gestalten, genau wie andere webforJ-Komponenten, was den Benutzern wertvollen Kontext über die Art der angezeigten Informationen bietet und einen konsistenten Stil in Ihrer App schafft. Sie können entweder das Thema festlegen, wenn Sie den Toast erstellen, oder die Methode `setTheme()` verwenden.

```java
Toast toast = new Toast("Beispielbenachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setTheme(Theme.INFO);
```

### Benutzerdefinierte Themen {#custom-themes}

Zusätzlich zur Verwendung integrierter Themen können Sie Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht ein persönlicheres und markenbezogenes Benutzererlebnis, bei dem Sie die volle Kontrolle über das gesamte Styling des `Toast` haben.

Um ein benutzerdefiniertes Thema zu einem `Toast` hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild der Komponente ändern. Das folgende Beispiel zeigt, wie man einen `Toast` mit einem benutzerdefinierten Thema unter Verwendung von webforJ erstellt.

:::info `Toast` Zielgerichtet
Da sich der `Toast` nicht an einer bestimmten Stelle im DOM befindet, können Sie ihn mit CSS-Variablen anvisieren. Diese Variablen erleichtern es, konsistente benutzerdefinierte Stile über alle Toast-Benachrichtigungen hinweg anzuwenden.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
