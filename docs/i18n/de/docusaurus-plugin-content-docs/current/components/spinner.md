---
title: Spinner
sidebar_position: 110
_i18n_hash: bb61c6f2d3cf7073ca2d1c6fc6e1c0c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-spinner" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

Die `Spinner`-Komponente bietet einen visuellen Indikator, der anzeigt, dass im Hintergrund ein Prozess oder eine Ladeoperation stattfindet. Sie wird häufig verwendet, um zu zeigen, dass das System Daten abruft oder wenn ein Prozess Zeit benötigt, um abgeschlossen zu werden. Der `Spinner` bietet Benutzerfeedback und signalisiert, dass das System aktiv arbeitet.

<!-- INTRO_END -->

## Grundlagen {#basics}

Um einen `Spinner` zu erstellen, können Sie das Thema und die Größe angeben. Die grundlegende Syntax besteht darin, eine `Spinner`-Instanz zu erstellen und ihr Erscheinungsbild und Verhalten über Methoden wie `setTheme()` und `setExpanse()` zu definieren.

<ComponentDemo 
path='/webforj/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
height = '225px'
/>

## Geschwindigkeitsregelung und Pausierung {#managing-speed-and-pausing}

Es ist möglich, die Geschwindigkeit des `Spinner` in Millisekunden einzustellen und die Animation einfach zu pausieren oder fortzusetzen.

Anwendungsfälle für das Einstellen von Geschwindigkeiten umfassen die Unterscheidung zwischen Ladeprozessen. Beispielsweise eignen sich schnellere `Spinner` für kleinere Aufgaben, während langsamere `Spinner` besser für größere Aufgaben geeignet sind. Das Pausieren ist nützlich, wenn eine Benutzeraktion oder Bestätigung erforderlich ist, bevor der Prozess fortgesetzt wird.

### Geschwindigkeitsanpassung {#adjusting-speed}

Sie können steuern, wie schnell sich der `Spinner` dreht, indem Sie seine Geschwindigkeit in Millisekunden mit der Methode `setSpeed()` anpassen. Ein niedrigerer Wert lässt den `Spinner` schneller drehen, während höhere Werte ihn verlangsamen.

```java
spinner.setSpeed(500); // Dreht schneller
```

:::info Standardgeschwindigkeit
Standardmäßig benötigt der `Spinner` 1000 Millisekunden, um eine vollständige Umdrehung abzuschließen.
:::

### Pausieren und Fortsetzen {#pausing-and-resuming}

Das Pausieren des `Spinner` ist nützlich, wenn ein Programm vorübergehend gestoppt wird oder auf Benutzereingaben wartet. Es lässt die Benutzer wissen, dass das Programm pausiert ist, anstatt aktiv zu laufen, was die Klarheit bei mehrstufigen Prozessen verbessert.

Um den Spinner zu pausieren und fortzusetzen, verwenden Sie die Methode `setPaused()`. Dies ist besonders hilfreich, wenn Sie die drehende Animation vorübergehend stoppen müssen.      

```java
spinner.setPaused(true);  // Pausiert den Spinner
spinner.setPaused(false); // Setzt den Spinner fort
```

Dieses Beispiel zeigt, wie man die Geschwindigkeit einstellt und wie man den `Spinner` pausiert/fortsetzt:

<ComponentDemo 
path='/webforj/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
height = '150px'
/>

## Drehrichtung {#spin-direction}

Die Richtung des `Spinner` kann so gesteuert werden, dass sie **im Uhrzeigersinn** oder **gegen den Uhrzeigersinn** rotiert. Sie können dieses Verhalten mit der Methode `setClockwise()` angeben.

```java
spinner.setClockwise(false);  // Dreht gegen den Uhrzeigersinn
spinner.setClockwise(true);   // Dreht im Uhrzeigersinn
```

Diese Option zeigt visuell einen speziellen Zustand an oder dient als besondere Designwahl. Das Ändern der Drehrichtung kann helfen, zwischen verschiedenen Prozessarten zu unterscheiden, wie Fortschritt gegen Umkehrung, oder einen bestimmten visuellen Hinweis in spezifischen Kontexten zu geben.

<ComponentDemo 
path='/webforj/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Styling {#styling}

### Themen {#themes}

Die `Spinner`-Komponente wird mit mehreren integrierten Themen geliefert, die es Ihnen ermöglichen, Stile schnell anzuwenden, ohne benutzerdefiniertes CSS zu benötigen. Diese Themen ändern das visuelle Erscheinungsbild des Spinners und machen ihn für verschiedene Anwendungsfälle und Kontexte geeignet. Die Verwendung dieser vordefinierten Themen gewährleistet Konsistenz im Styling Ihrer Anwendung.

Während Spinner in verschiedenen Situationen eingesetzt werden, sind hier einige Beispielanwendungsfälle für die unterschiedlichen Themen:

- **Primär**: Ideal zur Betonung eines Ladezustands, der ein zentraler Bestandteil des Benutzerflusses ist, z. B. beim Einreichen eines Formulars oder beim Verarbeiten einer wichtigen Aktion.
  
- **Erfolg**: Nützlich, um erfolgreiche Hintergrundprozesse darzustellen, wie wenn ein Benutzer ein Formular einreicht und die App die letzten Schritte des Prozesses durchführt.
  
- **Gefahr**: Verwenden Sie dies für riskante oder kritische Vorgänge, wie das Löschen wichtiger Daten oder das Vornehmen unwiderruflicher Änderungen, bei denen ein visueller Hinweis auf Dringlichkeit oder Vorsicht erforderlich ist.
  
- **Warnung**: Verwenden Sie dies, um einen vorsichtigen oder weniger dringenden Prozess anzuzeigen, wie wenn der Benutzer auf die Datenvalidierung wartet, aber keine sofortige Aktion erforderlich ist.

- **Grau**: Eignet sich gut für subtile Hintergrundprozesse, wie niedrigpriorisierte oder passive Ladeaufgaben, z. B. beim Abrufen von ergänzenden Daten, die sich nicht direkt auf die Benutzererfahrung auswirken.
  
- **Info**: Geeignet für Ladeszenarien, in denen Sie zusätzliche Informationen oder Klarstellungen für den Benutzer bereitstellen, z. B. beim Anzeigen eines Spinners zusammen mit einer Nachricht, die den laufenden Prozess erklärt.

Sie können diese Themen programmatisch auf den Spinner anwenden und visuelle Hinweise bereitstellen, die mit dem Kontext und der Wichtigkeit des Vorgangs übereinstimmen.

Sie können dieses Verhalten mit der Methode `setTheme()` angeben.

<ComponentDemo 
path='/webforj/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
height = '100px'
/>

### Größen {#expanses}

Sie können die Größe des Spinners, die als **Größe** bekannt ist, anpassen, um den visuellen Raum zu berücksichtigen, den Sie benötigen. Der Spinner unterstützt verschiedene Größen einschließlich `Expanse.SMALL`, `Expanse.MEDIUM` und `Expanse.LARGE`.

<ComponentDemo 
path= '/webforj/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
height = '100px'
/>

<TableBuilder name="Spinner" />
