---
title: Spinner
sidebar_position: 110
_i18n_hash: c60e7d3c3604a39de7f659f169d973a6
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet einen visuellen Indikator, der anzeigt, dass eine Verarbeitung oder ein Ladevorgang im Hintergrund läuft. Sie wird häufig verwendet, um zu zeigen, dass das System Daten abruft oder wenn ein Prozess Zeit in Anspruch nimmt. Der `Spinner` bietet dem Benutzer Rückmeldung und signalisiert, dass das System aktiv arbeitet.

<!-- INTRO_END -->

## Grundlagen {#basics}

Um einen `Spinner` zu erstellen, können Sie das Thema und die Größe angeben. Die grundlegende Syntax besteht darin, eine `Spinner`-Instanz zu erstellen und ihr Aussehen sowie Verhalten über Methoden wie `setTheme()` und `setExpanse()` zu definieren.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Geschwindigkeits- und Pausierungsverwaltung {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit in Millisekunden für den `Spinner` festzulegen und die Animation jederzeit zu pausieren oder fortzusetzen.

Anwendungsfälle für das Festlegen von Geschwindigkeiten beinhalten die Unterscheidung zwischen Ladevorgängen. Schnelle `Spinner` sind für kleinere Aufgaben geeignet, während langsamere `Spinner` besser für größere Aufgaben geeignet sind. Pausieren ist nützlich, wenn eine Benutzeraktion oder Bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeit anpassen {#adjusting-speed}

Sie können steuern, wie schnell sich der `Spinner` dreht, indem Sie seine Geschwindigkeit in Millisekunden mit der `setSpeed()`-Methode anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller rotieren, während höhere Werte ihn langsamer machen.

```java
spinner.setSpeed(500); // Rotiert schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden für eine vollständige Umdrehung.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinner` ist nützlich, wenn ein Programm vorübergehend angehalten wird oder auf Benutzereingaben wartet. Es informiert die Benutzer darüber, dass das Programm angehalten ist, anstatt aktiv zu laufen, was die Klarheit während mehrstufiger Prozesse erhöht.

Um den Spinner zu pausieren und fortzusetzen, verwenden Sie die `setPaused()`-Methode. Dies ist besonders hilfreich, wenn Sie die drehende Animation vorübergehend stoppen müssen.      

```java
spinner.setPaused(true);  // Pausiert den Spinner
spinner.setPaused(false); // Setzt den Spinner fort
```

Dieses Beispiel zeigt, wie Sie die Geschwindigkeit einstellen und wie Sie den `Spinner` pausieren/fortsetzen:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Drehrichtung {#spin-direction}

Die Richtung des `Spinner` kann so gesteuert werden, dass sie **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** rotiert. Sie können dieses Verhalten mit der `setClockwise()`-Methode angeben.

```java
spinner.setClockwise(false);  // Rotiert gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Rotiert im Uhrzeigersinn
```

Diese Option zeigt visuell einen speziellen Zustand an oder dient als einzigartige Designwahl. Die Änderung der Drehrichtung kann helfen, zwischen Arten von Prozessen zu unterscheiden, wie z. B. Fortschritt gegen Rückkehr oder in bestimmten Kontexten einen ausgeprägten visuellen Hinweis zu geben.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Styling {#styling}

### Themen {#themes}

Die `Spinner`-Komponente wird mit mehreren integrierten Themen geliefert, die es Ihnen ermöglichen, schnell Stile anzuwenden, ohne benutzerdefiniertes CSS zu benötigen. Diese Themen ändern das visuelle Erscheinungsbild des Spinners, sodass er für verschiedene Anwendungsfälle und Kontexte geeignet ist. Die Verwendung dieser vordefinierten Themen gewährleistet Konsistenz im Styling in Ihrer Anwendung.

Während Spinner in verschiedenen Situationen Verwendung finden, sind hier einige Anwendungsbeispiele für die verschiedenen Themen:

- **Primär**: Ideal, um einen Ladezustand zu betonen, der ein wesentlicher Bestandteil des Benutzerflusses ist, beispielsweise beim Absenden eines Formulars oder beim Verarbeiten einer wichtigen Aktion.
  
- **Erfolg**: Nützlich, um erfolgreiche Hintergrundprozesse darzustellen, z. B. wenn ein Benutzer ein Formular absendet und die App die letzten Schritte des Prozesses ausführt.
  
- **Gefahr**: Verwenden Sie dies für riskante oder kritische Operationen, wie das Löschen wichtiger Daten oder das Vornehmen irreversibler Änderungen, bei denen ein visueller Hinweis auf Dringlichkeit oder Vorsicht erforderlich ist.
  
- **Warnung**: Verwenden Sie dies, um einen vorsichtigen oder weniger dringenden Prozess anzuzeigen, z. B. wenn der Benutzer auf die Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Funktioniert gut für subtile Hintergrundprozesse, wie z. B. niedrigpriorisierte oder passive Ladeaufgaben, wie das Abrufen zusätzlicher Daten, die sich nicht direkt auf die Benutzererfahrung auswirken.
  
- **Info**: Geeignet für Ladeszenarien, bei denen Sie zusätzliche Informationen oder Klarstellungen für den Benutzer bereitstellen, z. B. das Anzeigen eines Spinners zusammen mit einer Nachricht, die den laufenden Prozess erklärt.

Sie können diese Themen programmatisch auf den Spinner anwenden und visuelle Hinweise geben, die mit dem Kontext und der Wichtigkeit der Operation übereinstimmen.

Sie können dieses Verhalten mit der Methode `setTheme()` angeben.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners anpassen, bekannt als **Expanse**, um den visuellen Raum anzupassen, den Sie benötigen. Der Spinner unterstützt verschiedene Größen, einschließlich `Expanse.SMALL`, `Expanse.MEDIUM` und `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

<TableBuilder name="Spinner" />
