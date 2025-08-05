---
title: Spinner
sidebar_position: 110
_i18n_hash: 8ab95efdcfcc1e42df56c372da27cc81
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet einen visuellen Indikator, der anzeigt, dass im Hintergrund ein Prozess oder Ladevorgang stattfindet. Sie wird häufig verwendet, um zu zeigen, dass das System Daten abruft oder wenn ein Prozess Zeit in Anspruch nimmt, um abgeschlossen zu werden. Der Spinner bietet Rückmeldung für den Benutzer und signalisiert, dass das System aktiv arbeitet.

## Grundlagen {#basics}

Um einen `Spinner` zu erstellen, können Sie das Design und die Größe angeben. Die grundlegende Syntax beinhaltet das Erstellen einer `Spinner`-Instanz und das Definieren ihres Aussehens und Verhaltens über Methoden wie `setTheme()` und `setExpanse()`.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Geschwindigkeit und Pausierung verwalten {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit in Millisekunden für den `Spinner` einzustellen und die Animation problemlos zu pausieren oder fortzusetzen.

Verwendungszwecke für die Geschwindigkeitsanpassung umfassen die Unterscheidung zwischen Ladeprozessen. Schnellere `Spinner` sind für kleinere Aufgaben geeignet, während langsamere `Spinner` besser für größere Aufgaben geeignet sind. Eine Pause ist nützlich, wenn eine Benutzeraktion oder Bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeit anpassen {#adjusting-speed}

Sie können steuern, wie schnell sich der `Spinner` dreht, indem Sie seine Geschwindigkeit in Millisekunden mit der Methode `setSpeed()` anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller drehen, während höhere Werte ihn verlangsamen.

```java
spinner.setSpeed(500); // Dreht schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden, um eine vollständige Umdrehung abzuschließen.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinner` ist nützlich, wenn ein Programm vorübergehend angehalten wird oder auf Benutzereingaben wartet. Es informiert die Benutzer, dass das Programm angehalten ist, anstatt aktiv zu arbeiten, was die Klarheit bei mehrstufigen Prozessen erhöht.

Um den Spinner zu pausieren und fortzusetzen, verwenden Sie die Methode `setPaused()`. Dies ist besonders hilfreich, wenn Sie die Drehanimation vorübergehend stoppen müssen.

```java
spinner.setPaused(true);  // Spinner pausieren
spinner.setPaused(false); // Spinner fortsetzen
```

Dieses Beispiel zeigt, wie man die Geschwindigkeit einstellt und wie man den `Spinner` pausiert/fortsetzt:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Drehrichtung {#spin-direction}

Die Drehrichtung des `Spinner` kann gesteuert werden, um **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** zu rotieren. Sie können dieses Verhalten mit der Methode `setClockwise()` festlegen.

```java
spinner.setClockwise(false);  // Dreht gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Dreht im Uhrzeigersinn
```

Diese Option zeigt visuell einen speziellen Zustand an oder dient als eine einzigartige Designwahl. Die Änderung der Drehrichtung kann helfen, zwischen verschiedenen Prozessarten zu unterscheiden, wie Fortschritt gegenüber Rückschritt, oder ein eindeutiges visuelles Signal in bestimmten Kontexten zu geben.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Styling {#styling}

### Themen {#themes}

Die `Spinner`-Komponente verfügt über mehrere integrierte Themen, die es Ihnen ermöglichen, Stile schnell anzuwenden, ohne benutzerdefiniertes CSS erstellen zu müssen. Diese Themen verändern das visuelle Erscheinungsbild des Spinners, sodass er für verschiedene Anwendungsfälle und Kontexte geeignet ist. Die Verwendung dieser vordefinierten Themen sorgt für Konsistenz im Styling Ihrer App.

Während Spinner für verschiedene Situationen dienen, hier einige Beispiele für die unterschiedlichen Themen:

- **Primär**: Ideal, um einen Ladevorgang hervorzuheben, der ein wesentlicher Bestandteil des Benutzerflusses ist, wie beim Absenden eines Formulars oder beim Verarbeiten einer wichtigen Aktion.
  
- **Erfolg**: Nützlich, um erfolgreiche Hintergrundprozesse darzustellen, wie wenn ein Benutzer ein Formular absendet und die App die letzten Schritte des Prozesses durchführt.
  
- **Gefahr**: Verwenden Sie dies für riskante oder kritische Operationen, wie das Löschen wichtiger Daten oder das Vornehmen unwiderruflicher Änderungen, bei denen ein visueller Indikator für Dringlichkeit oder Vorsicht erforderlich ist.
  
- **Warnung**: Verwenden Sie dies, um einen cautionarischen oder weniger dringenden Prozess anzuzeigen, wie wenn der Benutzer auf eine Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Funktioniert gut für subtile Hintergrundprozesse, wie weniger wichtige oder passive Ladevorgänge, wie das Abrufen von ergänzenden Daten, die die Benutzererfahrung nicht direkt beeinflussen.
  
- **Info**: Geeignet für Ladeszenarien, bei denen Sie zusätzliche Informationen oder Klarstellungen an den Benutzer bereitstellen, wie das Anzeigen eines Spinners zusammen mit einer Nachricht, die den laufenden Prozess erklärt.

Sie können diese Themen programmgesteuert auf den Spinner anwenden und visuelle Hinweise geben, die mit dem Kontext und der Wichtigkeit des Vorgangs übereinstimmen.

Sie können dieses Verhalten mit der Methode `setTheme()` festlegen.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners, bekannt als **Größe**, anpassen, um den visuellen Raum zu erfüllen, den Sie benötigen. Der Spinner unterstützt verschiedene Größen, einschließlich `Expanse.SMALL`, `Expanse.MEDIUM` und `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
