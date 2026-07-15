---
title: ProgressBar
sidebar_position: 90
description: >-
  Visualize task completion with the ProgressBar component, supporting
  determinate and indeterminate modes, orientation, and themes.
_i18n_hash: 47c51276d2b1da6c6bef337f76403515
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Die `ProgressBar`-Komponente stellt visuell den Abschlussstatus eines Vorgangs dar. Während die Arbeit fortschreitet, füllt sich ein Rechteck allmählich, um den aktuellen Prozentsatz widerzuspiegeln. Die Leiste kann auch eine textliche Darstellung ihres Wertes anzeigen und unterstützt sowohl determinate als auch indeterminate Zustände für Aufgaben mit bekannter oder unbekannter Dauer.

<!-- INTRO_END -->

## Usos {#usages}

Die `ProgressBar`-Komponente ist nützlich, um den Abschlussstatus von Aufgaben zu visualisieren. Sie unterstützt:

- Konfigurierbare Mindest- und Höchstwerte.
- Indeterminate Modus für laufende Aufgaben ohne definitives Ende.
- Optionen für Textsichtbarkeit, Animation und gestreifte Designs für bessere visuelle Rückmeldungen.

Das folgende Beispiel zeigt eine gestreifte, animierte Fortschrittsleiste mit Start-, Pause- und Rücksetzsteuerungen:

<ComponentDemo
path='/webforj/progressbarbasic'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java']}
height='150px'
/>

## Werte setzen {#setting-values}

Die ProgressBar-Komponente ermöglicht das Setzen und Abrufen ihres aktuellen Wertes, Mindest- und Höchstgrenzen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Ausrichtung {#orientation}

Die `ProgressBar` kann horizontal oder vertikal ausgerichtet sein.

<ComponentDemo
path='/webforj/progressbarorientation'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java']}
height='175px'
/>

## Indeterminate Zustand {#indeterminate-state}

Die `ProgressBar` unterstützt einen indeterminierten Zustand für Aufgaben mit unbekannter Abschlusszeit.

<ComponentDemo
path='/webforj/progressbardeterminate'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java']}
height='25px'
/>

## Text und Textsichtbarkeit {#text-and-text-visibility}

Im Standardformat zeigt die Fortschrittsleiste beim Erstellen den abgeschlossenen Prozentsatz im Format `XX%` an. Mit der `setText()`-Methode können Sie den Platzhalter `{{x}}` verwenden, um den aktuellen Wert als Prozentsatz zu erhalten. Darüber hinaus können Sie den Platzhalter
`{{value}}` verwenden, um den aktuellen Rohwert zu erhalten.

```java
ProgressBar bar = new ProgressBar(15, "Herunterladen: {{x}}%");
```

## Styling {#styling}

### Themen {#themes}

Die `ProgressBar`-Komponente kommt mit <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, die für eine schnelle Gestaltung ohne Verwendung von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. 
Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von Fortschrittsleisten in einer Anwendung anzupassen.

<ComponentDemo
path='/webforj/progressbarthemes'
files={['src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java']}
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best Practices {#best-practices}

- **Verwenden Sie angemessene Mindest- und Höchstwerte**: Setzen Sie die Mindest- und Höchstwerte, um den Bereich der Aufgabe genau widerzuspiegeln.
- **Aktualisieren Sie den Fortschritt regelmäßig**: Aktualisieren Sie den Fortschrittswert kontinuierlich, um den Benutzern Echtzeit-Feedback zu geben.
- **Nutzen Sie den indeterminierten Zustand für unbekannte Dauer**: Verwenden Sie den indeterminierten Zustand für Aufgaben mit unvorhersehbaren Dauern, um andauernden Fortschritt anzuzeigen.
- **Zeigen Sie Text für besseres Benutzerfeedback an**: Stellen Sie Text auf der Fortschrittsleiste dar, um weiteren Kontext zum Fortschritt der Aufgabe zu bieten.
