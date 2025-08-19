---
title: Toast
sidebar_position: 140
_i18n_hash: 2027a7fa9671b2b8eb47a3f173ca6f41
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

Eine `Toast`-Benachrichtigung ist eine subtile und unaufdringliche Pop-up-Benachrichtigung, die dazu dient, den Benutzern in Echtzeit Feedback und Informationen zu geben. Diese Benachrichtigungen werden typischerweise verwendet, um die Benutzer über Vorgänge wie erfolgreiche Aktionen, Warnungen oder Fehler zu informieren, ohne deren Arbeitsablauf zu unterbrechen. `Toast`-Benachrichtigungen verschwinden normalerweise nach einer festgelegten Zeit und erfordern keine Benutzerreaktion.

Mit dem `Toast`-Komponent von webforJ können Sie diese Benachrichtigungen ganz einfach implementieren, um die Benutzererfahrung zu verbessern, indem Sie relevante Informationen auf eine vertraute, nicht aufdringliche und nahtlose Weise bereitstellen.

## Grundlagen {#basics}

webforJ bietet einen schnellen und einfachen Weg, um einen `Toast`-Komponent in einer einzigen Codezeile mit der Methode `Toast.show()` zu erstellen, die ein `Toast`-Komponent erstellt, es zum `Frame` hinzufügt und es anzeigt. Sie können Parameter an die Methode `show` übergeben, um den angezeigten `Toast` zu konfigurieren:

```java
Toast.show("Operation erfolgreich abgeschlossen!", Theme.SUCCESS);
```

Wenn Sie mehr Kontrolle über das Komponent wünschen, können Sie auch einen `Toast` mit einem Standardkonstruktor erstellen und die Methode `open()` verwenden, um ihn anzuzeigen.

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
Im Gegensatz zu anderen Komponenten muss ein `Toast` nicht explizit zu einem Container wie einem `Frame` hinzugefügt werden. Wenn Sie die Methode `open()` aufrufen, wird der `Toast` automatisch am ersten App-`Frame` angehängt.
:::

Toasts sind vielseitig und bieten subtile Benachrichtigungen für Echtzeit-Feedback. Beispielsweise:

- **Echtzeit-Feedback** für Aktionen wie Formularübermittlungen, Daten Sicherungen oder Fehler.
- **Anpassbare Themen** zum Unterscheiden zwischen Success-, Error-, Warning- oder Informationsnachrichten.
- **Flexible Platzierungs**optionen, um Benachrichtigungen in unterschiedlichen Bereichen des Bildschirms anzuzeigen, ohne den Arbeitsablauf des Benutzers zu unterbrechen.

## Dauer {#duration}

Sie können `Toast`-Benachrichtigungen so konfigurieren, dass sie nach einer bestimmten Dauer verschwinden oder auf dem Bildschirm bleiben, bis sie abgelehnt werden, je nach Ihren Bedürfnissen. Sie können die Dauer mit der Methode `setDuration()` anpassen oder einfach einen Dauerparameter an den Konstruktor oder die Methode `show()` übergeben.

:::info Standarddauer
Standardmäßig schließt sich ein `Toast` automatisch nach 5000 Millisekunden.
:::

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setDuration(10000);
toast.open();
```

### Persistente Toasts {#persistent-toasts}

Sie können einen persistenten `Toast` erstellen, indem Sie eine negative Dauer festlegen. Persistente `Toast`-Benachrichtigungen schließen sich nicht automatisch, was für kritische Warnungen oder Situationen nützlich sein kann, in denen eine Interaktion oder Bestätigung vom Benutzer erforderlich ist.

:::caution
Seien Sie vorsichtig mit persistenten `Toast`-Benachrichtigungen und stellen Sie sicher, dass Sie eine Möglichkeit bieten, die Benachrichtigung durch den Benutzer zu schließen. Verwenden Sie die Methode `close()`, um den `Toast` auszublenden, sobald der Benutzer ihn zur Kenntnis genommen hat oder erforderliche Interaktionen abgeschlossen hat.
:::

```java
Toast toast = new Toast("Operation erfolgreich abgeschlossen!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Platzierung {#placement}

Mit dem `Toast`-Komponent von webforJ können Sie wählen, wo die Benachrichtigung auf dem Bildschirm erscheinen soll, um den Design- und Usability-Anforderungen Ihrer App gerecht zu werden. Standardmäßig erscheinen `Toast`-Benachrichtigungen am unteren Bildschirmrand in der Mitte.

Sie können die `Platzierung` einer Toast-Benachrichtigung mit der Methode `setPlacement` festlegen, wobei Sie das `Toast.Placement`-Enum mit einem der folgenden Werte verwenden:

- **UNTEN**: Platziert die Benachrichtigung am unteren Bildschirmrand in der Mitte.
- **UNTEN_LINKS**: Platziert die Benachrichtigung in der unteren linken Ecke des Bildschirms.
- **UNTEN_RECHTS**: Platziert die Benachrichtigung in der unteren rechten Ecke des Bildschirms.
- **OBEN**: Platziert die Benachrichtigung am oberen Bildschirmrand in der Mitte.
- **OBEN_LINKS**: Platziert die Benachrichtigung in der oberen linken Ecke des Bildschirms.
- **OBEN_RECHTS**: Platziert die Benachrichtigung in der oberen rechten Ecke des Bildschirms.

Diese Optionen ermöglichen es Ihnen, die Platzierung der `Toast`-Benachrichtigung basierend auf dem Design Ihrer App und den Usability-Anforderungen zu steuern.

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

Durch die Anpassung der Platzierung Ihrer `Toast`-Benachrichtigungen können Sie sicherstellen, dass Benutzer Informationen auf eine Weise erhalten, die für jede bestimmte App, Bildschirmgestaltung und Kontext angemessen ist.

## Stapelung {#stacking}

Das `Toast`-Komponent kann mehrere Benachrichtigungen gleichzeitig anzeigen, die vertikal basierend auf ihrer Platzierung gestapelt sind. Neuere Benachrichtigungen erscheinen näher an der Platzierungskante, wodurch ältere Benachrichtigungen weiter entfernt geschoben werden. Dies stellt sicher, dass Benutzer wichtige Informationen nicht verpassen, selbst wenn viel auf einmal passiert.

## Aktionen und Interaktivität {#actions-and-interactivity}

Obwohl `Toast`-Benachrichtigungen standardmäßig keine Benutzerinteraktion erfordern, erlaubt webforJ Ihnen, Schaltflächen oder andere interaktive Elemente hinzuzufügen, um sie nützlicher zu machen als einfache Benachrichtigungen.

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

Durch Hinzufügen dieser Art von Interaktivität können Sie den Benutzern die Möglichkeit geben, Aufgaben zu erledigen und Aktionen durchzuführen, ohne den aktuellen Bildschirm zu verlassen, wodurch eine `Toast`-Benachrichtigung in einen wertvollen Kanal der Interaktion und des Engagements verwandelt wird.

## Styling {#styling}

Sie können `Toast`-Benachrichtigungen mit Themen stylen, genauso wie andere webforJ-Komponenten, und den Benutzern wertvollen Kontext über die Art der angezeigten Informationen bieten und einen konsistenten Stil in Ihrer App schaffen. Sie können entweder das Thema beim Erstellen des Toasts festlegen oder die Methode `setTheme()` verwenden.

```java
Toast toast = new Toast("Beispielbenachrichtigung", Theme.INFO);
```

```java
Toast toast = new Toast("Beispielbenachrichtigung");
toast.setTheme(Theme.INFO);
```

### Benutzerdefinierte Themen {#custom-themes}

Neben der Verwendung integrierter Themen können Sie auch Ihre eigenen benutzerdefinierten Themen für `Toast`-Benachrichtigungen erstellen. Dies ermöglicht ein persönlicheres und markenspezifisches Benutzererlebnis, das Ihnen die volle Kontrolle über das gesamte Styling des `Toast` gibt.

Um einem `Toast` ein benutzerdefiniertes Thema hinzuzufügen, können Sie benutzerdefinierte CSS-Variablen definieren, die das Erscheinungsbild des Komponents modifizieren. Das folgende Beispiel zeigt, wie Sie einen `Toast` mit einem benutzerdefinierten Thema unter Verwendung von webforJ erstellen.

:::info `Toast`-Zielsetzung
Da der `Toast` nicht an einem bestimmten Ort im DOM platziert ist, können Sie ihn mit CSS-Variablen ansprechen. Diese Variablen erleichtern die Anwendung konsistenter benutzerdefinierter Stile auf alle Toast-Benachrichtigungen.
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
