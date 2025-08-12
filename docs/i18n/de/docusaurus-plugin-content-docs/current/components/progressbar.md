---
title: ProgressBar
sidebar_position: 90
_i18n_hash: 9b2f9ec23124d60ab5f8fca18e561acb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-progressbar" />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/progressbar/ProgressBar" top='true'/>

Der ProgressBar ist eine Komponente, die visuell den Fortschritt einer Aufgabe anzeigt. Während die Aufgabe dem Abschluss näher kommt, zeigt die Fortschrittsanzeige den Prozentsatz des Abschlusses der Aufgabe an. Dieser Prozentsatz wird visuell durch ein Rechteck dargestellt, das zu Beginn leer ist und allmählich gefüllt wird, während die Aufgabe voranschreitet. Zusätzlich kann die Fortschrittsanzeige eine textuelle Darstellung dieses Prozentsatzes anzeigen.

<ComponentDemo 
path='/webforj/progressbarbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarBasicView.java'
height='150px'
/>

## Verwendungen {#usages}

Die `ProgressBar`-Komponente ist nützlich, um den Fertigstellungsstatus von Aufgaben zu visualisieren. Sie unterstützt:

- Konfigurierbare Mindest- und Höchstwerte.
- Unbestimmten Modus für laufende Aufgaben ohne definitives Ende.
- Optionen für die Textsichtbarkeit, Animation und gestreifte Designs für ein besseres visuelles Feedback.

## Werte festlegen {#setting-values}

Die ProgressBar-Komponente ermöglicht das Setzen und Abrufen ihres aktuellen Wertes, Mindest- und Höchstwerte.

```java showLineNumbers
ProgressBar bar = new ProgressBar();
bar.setMin(0);
bar.setMax(100);
bar.setValue(50);
```

## Ausrichtung {#orientation}

Die `ProgressBar` kann horizontal oder vertikal ausgerichtet werden.

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

## Styling {#styling}

### Themen {#themes}

Die `ProgressBar`-Komponente verfügt über <JavadocLink type="foundation" location="com/webforj/component/Theme"> Themen </JavadocLink>, die für eine schnelle Gestaltung ohne CSS eingebaut sind. Diese Themen sind vordefinierte Stile, die auf Schaltflächen angewendet werden können, um ihr Aussehen und ihre visuelle Präsentation zu ändern. Sie bieten eine schnelle und konsistente Möglichkeit, das Erscheinungsbild von ProgressBars in einer App anzupassen.

<ComponentDemo 
path='/webforj/progressbarthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/progressbar/ProgressBarThemesView.java'
height='320px'
/>

<TableBuilder name="ProgressBar" />

## Best Practices {#best-practices}

- **Verwenden Sie geeignete Mindest- und Höchstwerte**: Setzen Sie die Mindest- und Höchstwerte, um den Aufgabenbereich genau widerzuspiegeln.
- **Aktualisieren Sie den Fortschritt regelmäßig**: Aktualisieren Sie den Fortschrittswert kontinuierlich, um den Benutzern zeitnahe Rückmeldungen zu geben.
- **Nutzen Sie den unbestimmten Zustand für unbekannte Dauer**: Verwenden Sie den unbestimmten Zustand für Aufgaben mit unvorhersehbaren Dauern, um fortlaufenden Fortschritt anzuzeigen.
- **Zeigen Sie Text für besseres Benutzerfeedback an**: Zeigen Sie Text auf der Fortschrittsanzeige an, um zusätzlichen Kontext zum Fortschritt der Aufgabe zu bieten.
