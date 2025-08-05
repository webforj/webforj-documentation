---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 3c76010e8bda96d8694bffa5a260b851
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Der ProgressBar ist eine Komponente, die den Fortschritt einer Aufgabe visuell darstellt. Während die Aufgabe auf die Fertigstellung hin fortschreitet, zeigt die Fortschrittsanzeige den prozentualen Abschluss der Aufgabe an. Dieser Prozentsatz wird visuell durch ein Rechteck dargestellt, das zu Beginn leer ist und allmählich gefüllt wird, während die Aufgabe voranschreitet. Darüber hinaus kann die Fortschrittsanzeige eine textuelle Darstellung dieses Prozentsatzes anzeigen.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Anwendungen {#usages}

Die `ProgressBar`-Komponente ist nützlich zur Visualisierung des Abschlussstatus von Aufgaben. Sie unterstützt:

- Konfigurierbare Mindest- und Höchstwerte.
- Unbestimmten Modus für laufende Aufgaben ohne definitives Ende.
- Optionen für Textsichtbarkeit, Animation und gestreifte Designs für besseres visuelles Feedback.

## Werte setzen {#setting-values}

Die ProgressBar-Komponente ermöglicht das Setzen und Abrufen ihres aktuellen Wertes, der Mindest- und Höchstgrenzen.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Ausrichtung {#orientation}

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

Standardmäßig zeigt die Fortschrittsanzeige beim Erstellen den Prozentsatz des Abschlusses im Format `XX%` an. Mit der Methode `setText()` können Sie den Platzhalter `{{x}}` verwenden, um den aktuellen Wert als Prozentsatz zu erhalten. Darüber hinaus können Sie den Platzhalter `{{value}}` verwenden, um den aktuellen Rohwert zu erhalten.

```java
ProgressBar bar = new ProgressBar(15, "Herunterladen: {{x}}%");
```

## Stil {#styling}

### Themen {#themes}

Die `ProgressBar`-Komponente verfügt über <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, die für eine schnelle Styling-Option ohne die Verwendung von CSS integriert sind. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Erscheinungsbild und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von ProgressBars in der gesamten Anwendung anzupassen.

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best Practices {#best-practices}

- **Verwenden Sie angemessene Mindest- und Höchstwerte**: Setzen Sie die Mindest- und Höchstwerte, um den Bereich der Aufgabe genau widerzuspiegeln.
- **Aktualisieren Sie den Fortschritt regelmäßig**: Aktualisieren Sie den Fortschrittswert kontinuierlich, um den Benutzern Echtzeit-Feedback zu geben.
- **Nutzen Sie den unbestimmten Zustand für unbekannte Dauer**: Verwenden Sie den unbestimmten Zustand für Aufgaben mit unvorhersehbaren Dauern, um den laufenden Fortschritt anzuzeigen.
- **Zeigen Sie Text für besseres Benutzerfeedback an**: Blenden Sie Text auf der Fortschrittsanzeige ein, um zusätzlichen Kontext zum Fortschritt der Aufgabe zu bieten.
