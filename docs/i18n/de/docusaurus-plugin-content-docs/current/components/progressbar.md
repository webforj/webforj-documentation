---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 6acac582ce905eb255ee09e499fd561f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Die `ProgressBar`-Komponente stellt den Fertigungsstatus eines Vorgangs visuell dar. Während die Arbeit voranschreitet, füllt sich ein Rechteck allmählich, um den aktuellen Prozentsatz widerzuspiegeln. Die Leiste kann auch eine textuelle Darstellung ihres Wertes anzeigen und unterstützt sowohl determinierte als auch indeterminierte Zustände für Aufgaben mit bekannten oder unbekannten Dauern.

<!-- INTRO_END -->

## Verwendungen {#usages}

Die `ProgressBar`-Komponente ist nützlich zur Visualisierung des Fertigungsstatus von Aufgaben. Sie unterstützt:

- Konfigurierbare Minimal- und Maximalwerte.
- Indeterminate Modus für andauernde Aufgaben ohne definitives Ende.
- Optionen für Textsichtbarkeit, Animation und gestreifte Designs für besseres visuelles Feedback.

Das folgende Beispiel zeigt eine gestreifte, animierte Fortschrittsleiste mit Start-, Pause- und Zurücksetzen-Steuerelementen:

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Werte festlegen {#setting-values}

Die ProgressBar-Komponente ermöglicht das Setzen und Abrufen ihres aktuellen Wertes sowie der minimalen und maximalen Grenzen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Ausrichtung {#orientation}

Die `ProgressBar` kann horizontal oder vertikal ausgerichtet werden.

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## Indeterminierter Zustand {#indeterminate-state}

Die `ProgressBar` unterstützt einen indeterminierten Zustand für Aufgaben mit unbekannter Fertigstellungszeit.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Text und Textsichtbarkeit {#text-and-text-visibility}

Standardmäßig zeigt die Fortschrittsleiste beim Erstellen den Prozentsatz der Fertigstellung im Format `XX%` an. Mit der Methode `setText()` können Sie den Platzhalter `{{x}}` verwenden, um den aktuellen Wert als Prozentsatz zu erhalten. Außerdem können Sie den Platzhalter `{{value}}` verwenden, um den aktuellen Rohwert zu erhalten.

```java
ProgressBar bar = new ProgressBar(15, "Herunterladen: {{x}}%");
```

## Stilgestaltung {#styling}

### Themen {#themes}

Die `ProgressBar`-Komponente kommt mit <JavadocLink type="foundation" location="com/webforj/component/Theme">Themen</JavadocLink>, die für eine schnelle Stilgestaltung ohne CSS integriert sind. Diese Themen sind vorgegebene Stile, die auf Schaltflächen angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. 
Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von ProgressBars in einer App anzupassen.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best Practices {#best-practices}

- **Verwenden Sie geeignete Minimal- und Maximalwerte**: Setzen Sie die Minimal- und Maximalwerte, um den Bereich der Aufgabe genau widerzuspiegeln.
- **Aktualisieren Sie den Fortschritt regelmäßig**: Aktualisieren Sie den Fortschrittswert kontinuierlich, um den Benutzern Echtzeit-Feedback zu geben.
- **Nutzen Sie den indeterminierten Zustand für unbekannte Dauer**: Verwenden Sie den indeterminierten Zustand für Aufgaben mit unvorhersehbaren Dauern, um den fortschreitenden Verlauf anzuzeigen.
- **Anzeige von Text für besseres Benutzerfeedback**: Zeigen Sie Text auf der Fortschrittsleiste an, um zusätzlichen Kontext über den Fortschritt der Aufgabe zu bieten.
