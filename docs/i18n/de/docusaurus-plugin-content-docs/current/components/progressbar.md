---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 7612411ef90d5344a2bab79b7e221141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Die `ProgressBar`-Komponente stellt den Abschlussstatus eines Vorgangs visuell dar. Während der Arbeit wird ein Rechteck allmählich gefüllt, um den aktuellen Prozentsatz widerzuspiegeln. Die Leiste kann auch eine textliche Darstellung ihres Wertes anzeigen und unterstützt sowohl bestimmte als auch unbestimmte Zustände für Aufgaben mit bekanntem oder unbekanntem Zeitraum.

<!-- INTRO_END -->

## Verwendungen {#usages}

Die `ProgressBar`-Komponente ist nützlich, um den Abschlussstatus von Aufgaben zu visualisieren. Sie unterstützt:

- Konfigurierbare Mindest- und Höchstwerte.
- Unbestimmten Modus für laufende Aufgaben ohne definitives Ende.
- Optionen für die Sichtbarkeit von Text, Animation und gestreifte Designs für bessere visuelle Rückmeldung.

Das folgende Beispiel zeigt eine gestreifte, animierte Fortschrittsleiste mit Start-, Pause- und Zurücksetzen-Bedienelementen:

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Werte festlegen {#setting-values}

Die ProgressBar-Komponente ermöglicht das Festlegen und Abrufen ihres aktuellen Wertes, der Mindest- und Höchstgrenzen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Orientierung {#orientation}

Die `ProgressBar` kann horizontal oder vertikal ausgerichtet sein.

<ComponentDemo 
path='/webforj/progressbarorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarOrientationView.java'
height='175px'
/>

## Unbestimmter Zustand {#indeterminate-state}

Die `ProgressBar` unterstützt einen unbestimmten Zustand für Aufgaben mit unbekannter Abschlusszeit.

<ComponentDemo 
path='/webforj/progressbardeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarDeterminateView.java'
height='25px'
/>

## Text und Textsichtbarkeit {#text-and-text-visibility}

Standardmäßig zeigt die Fortschrittsleiste beim Erstellen den Prozentsatz des Abschlusses im Format `XX%` an. Mit der Methode `setText()` können Sie den Platzhalter `{{x}}` verwenden, um den aktuellen Wert als Prozentsatz abzurufen. Zusätzlich können Sie den Platzhalter `{{value}}` verwenden, um den aktuellen Wert zu erhalten.

```java
ProgressBar bar = new ProgressBar(15, "Herunterladen: {{x}}%");
```

## Styling {#styling}

### Themen {#themes}

Die `ProgressBar`-Komponente enthält <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, die für schnelles Styling ohne CSS eingebaut sind. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Erscheinungsbild und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Aussehen von ProgressBars in einer App anzupassen.

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best Practices {#best-practices}

- **Verwenden Sie geeignete Mindest- und Höchstwerte**: Setzen Sie die Mindest- und Höchstwerte, um den Bereich der Aufgabe genau widerzuspiegeln.
- **Aktualisieren Sie den Fortschritt regelmäßig**: Aktualisieren Sie den Fortschrittswert kontinuierlich, um den Benutzern Echtzeit-Feedback zu geben.
- **Nutzen Sie den unbestimmten Zustand für unbekannte Dauer**: Verwenden Sie den unbestimmten Zustand für Aufgaben mit unvorhersehbaren Dauer, um den laufenden Fortschritt anzuzeigen.
- **Zeigen Sie Text für bessere Benutzerfeedback an**: Stellen Sie Text auf der Fortschrittsleiste zur Verfügung, um zusätzlichen Kontext über den Fortschritt der Aufgabe zu bieten.
