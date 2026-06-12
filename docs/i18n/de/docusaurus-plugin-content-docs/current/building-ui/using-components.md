---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: cf47b1c83e67cb4c4998c149a7696701
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Komponenten sind die Bausteine von webforJ-Anwendungen. Egal, ob Sie integrierte Komponenten wie `Button` und `TextField` verwenden oder mit benutzerdefinierten Komponenten arbeiten, die von Ihrem Team bereitgestellt werden, die Art und Weise, wie Sie mit ihnen interagieren, folgt demselben konsistenten Modell: Sie konfigurieren Eigenschaften, verwalten den Zustand und setzen Komponenten in Layouts zusammen.

Diese Anleitung konzentriert sich auf die täglichen Operationen: nicht auf die internen Abläufe, wie Komponenten funktionieren, sondern darauf, wie man sie in der Praxis einsetzen kann.

## Komponenten Eigenschaften {#component-properties}

Jede Komponente bietet Eigenschaften, die ihren Inhalt, ihr Erscheinungsbild und ihr Verhalten steuern. Die meisten davon haben dedizierte, typisierte Java-Methoden (`setText()`, `setTheme()`, `setExpanse()` usw.), die die primäre Möglichkeit sind, wie Sie Komponenten in webforJ konfigurieren. Die Abschnitte unten behandeln die Eigenschaften und Methoden, die allgemein für verschiedene Komponententypen gelten.

### Textinhalt {#text-content}

Die Methode `setText()` setzt den sichtbaren Text einer Komponente, beispielsweise die Beschriftung eines `Button` oder den Inhalt eines `Label`. Bei Eingabekomponenten wie `TextField` verwenden Sie stattdessen `setValue()`, um den aktuellen Wert des Feldes festzulegen.

```java
Button button = new Button();
button.setText("Klicken Sie hier");

Label label = new Label();
label.setText("Status: bereit");

TextField field = new TextField();
field.setValue("Anfangswert");
```

Einige Komponenten unterstützen auch `setHtml()`, wenn Sie Inline-HTML-Markup im Inhalt benötigen:

```java
Div container = new Div();
container.setHtml("<strong>Fettgedruckter Text</strong> und <em>kursiver Text</em>");
```

### HTML-Attribute {#html-attributes}

Die meiste Konfiguration in webforJ erfolgt über typisierte Java-Methoden und nicht über rohe HTML-Attribute. Allerdings ist `setAttribute()` nützlich, um Zugänglichkeitsattribute zu übergeben, die keine dedizierte API haben:

```java
Button button = new Button("Absenden");
button.setAttribute("aria-label", "Formular absenden");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Überprüfen Sie die Komponentensupport
Nicht alle Komponenten unterstützen beliebige Attribute. Dies hängt von der zugrunde liegenden Implementierung der Komponente ab.
:::

### Komponenten-IDs {#component-ids}

Sie können einer HTML-Element-ID einer Komponente mit `setAttribute()` zuweisen:

```java
Button submitButton = new Button("Absenden");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-Mail");
emailField.setAttribute("id", "email-input");
```

DOM-IDs werden häufig für Test-Selektoren und CSS-Zielen in Ihren Stylesheets verwendet.

:::tip Bevorzugen Sie Klassen für die Zielgruppenansprache mehrerer Komponenten
Im Gegensatz zu CSS-Klassen sollten IDs innerhalb Ihrer App einzigartig sein. Wenn Sie mehrere Komponenten ansprechen müssen, verwenden Sie stattdessen `addClassName()`.
:::

:::info Framework-verwaltete IDs
webforJ weist Komponenten auch intern automatische Identifikatoren zu. Die serverseitige ID (die über `getComponentId()` abgerufen wird) wird zur Verfolgung des Frameworks verwendet, während die clientseitige ID (die über `getClientComponentId()` abgerufen wird) für die Kommunikation zwischen Client und Server verwendet wird. Diese sind getrennt von dem DOM-Attribut `id`, das Sie mit `setAttribute()` festlegen.
:::

### Styling {#styling}

Drei Methoden decken die meisten Styling-Bedürfnisse ab: `setStyle()` für einzelne CSS-Eigenschaftswerte sowie `addClassName()` und `removeClassName()`, um CSS-Klassen anzuwenden oder zu entfernen, die in Ihren Stylesheets definiert sind. Verwenden Sie `setStyle()` für kleinere oder einmalige Styling-Anpassungen und CSS-Klassen, um größere oder wiederverwendbare Stile anzuwenden.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid red");
}

Button button = new Button("Umschalten");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

:::note Veralteter Ansatz
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) ist ein veralteter Ansatz und wird für neue Projekte im Allgemeinen nicht empfohlen. In den meisten Fällen sollten Sie Ihre Styles in separaten CSS-Dateien aufbewahren.
:::

## Komponentenstatus {#component-state}

Neben Inhalt und Erscheinungsbild haben Komponenten Statusattribute, die bestimmen, ob sie sichtbar sind und ob sie auf Benutzerinteraktionen reagieren. Die beiden am häufigsten verwendeten sind `setVisible()` und `setEnabled()`.

`setVisible()` steuert, ob die Komponente überhaupt in der Benutzeroberfläche gerendert wird. `setEnabled()` steuert, ob sie Eingaben oder Interaktionen akzeptiert, während sie sichtbar bleibt. In den meisten Fällen ist das Deaktivieren einer Komponente vorzuziehen zum Verstecken: Ein deaktivierter Button kommuniziert weiterhin, dass eine Aktion existiert, aber derzeit nicht verfügbar ist, was weniger verwirrend ist, als wenn er erscheint und verschwindet.

```java
// Ein zusätzliches Feld anzeigen, wenn ein Kontrollkästchen aktiviert ist
TextField advancedField = new TextField("Erweiterte Einstellung");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Erweiterte Einstellungen anzeigen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Aktivieren Sie einen Button nur, wenn das erforderliche Feld einen Wert hat
Button submitButton = new Button("Absenden");
submitButton.setEnabled(false);

TextField nameField = new TextField("Name");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Das folgende Anmeldeformular demonstriert `setEnabled()` in der Praxis. Der Anmelde-Button bleibt deaktiviert, bis beide Felder einen Inhalt haben, was dem Benutzer klar macht, dass Eingaben erforderlich sind, bevor er fortfahren kann:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Arbeiten mit Containern {#working-with-containers}

In webforJ wird das Layout von Containern gehandhabt, die Komponenten enthalten und steuern, wie sie angeordnet sind. Sie positionieren untergeordnete Komponenten nicht manuell; stattdessen fügen Sie sie einem Container hinzu und konfigurieren die Layout-Eigenschaften dieses Containers.

### Komponenten hinzufügen {#adding-components}

Alle Container bieten eine Methode `add()`. Sie können Komponenten einzeln oder alle auf einmal übergeben:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klicken Sie hier"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("E-Mail");
Button submitButton = new Button("Absenden");

container.add(nameField, emailField, submitButton);
```

### Layout-Optionen {#layout-options}

`FlexLayout` ist der primäre Layout-Container in webforJ und deckt die meisten Anwendungsfälle ab: Reihen, Spalten, Ausrichtung, Abstände und Umbrüche. Für komplexere Anordnungen wie CSS Grid oder benutzerdefinierte Positionierungen können Sie CSS direkt über `setStyle()` oder `addClassName()` auf jeden Container anwenden. Siehe die [FlexLayout](/docs/components/flex-layout) Dokumentation für die vollständige Palette von Layout-Optionen.

### Anzeigen und Ausblenden von Abschnitten {#showing-hiding-sections}

Ein häufiger Gebrauch von `setVisible()` in Containern besteht darin, zusätzliche Benutzeroberflächen nur anzuzeigen, wenn sie relevant sind. Dies hält die Benutzeroberfläche fokussiert und reduziert visuelles Durcheinander. Anstatt zu einer neuen Ansicht zu navigieren, können Sie einen Abschnitt des aktuellen Layouts als direkte Reaktion auf Benutzereingaben anzeigen.

Das folgende Einstellungsfeld demonstriert dies: Grundlegende Benachrichtigungseinstellungen sind immer sichtbar, während ein Abschnitt mit erweiterten Optionen nur erscheint, wenn der Benutzer danach fragt. Der Speichern-Button wird aktiviert, sobald eine Einstellung geändert wird:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/resources/static/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Containerverwaltung {#container-management}

Verwenden Sie `remove()` und `removeAll()`, um Komponenten zur Laufzeit aus einem Container zu entfernen:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Vorübergehend");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dies ist nützlich, wenn Sie den Inhalt vollständig ersetzen müssen, z. B. um einen Ladeindikator gegen die geladenen Daten auszutauschen.

## Formularvalidierung {#form-validation}

Die Koordination mehrerer Komponenten zur Steuerung einer Absendeaktion ist eines der häufigsten Muster in webforJ-Benutzeroberflächen. Die Grundidee ist einfach: Jedes Eingabefeld registriert einen Listener, und wann immer sich ein Wert ändert, bewertet das Formular erneut, ob alle Kriterien erfüllt sind, und aktualisiert den Absenden-Button entsprechend.

Dies ist vorzuziehen, als Validierungsfehler nur anzuzeigen, nachdem der Benutzer auf Absenden geklickt hat, da es kontinuierliches Feedback gibt und unnötige Einsendungen verhindert. Der Absenden-Button dient als Indikator: deaktiviert bedeutet, dass das Formular nicht bereit ist, aktiviert bedeutet, dass es bereit ist.

In diesem Kontaktformular darf das Namensfeld nicht leer sein, die E-Mail muss ein `@`-Symbol enthalten, und die Nachricht muss mindestens 10 Zeichen lang sein:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/resources/static/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamische Inhaltsaktualisierungen {#dynamic-content-updates}

Komponenten müssen nach ihrer Erstellung nicht in einem festen Zustand bleiben. Sie können zu irgendeinem Zeitpunkt Text aktualisieren, CSS-Klassen wechseln und den aktivierten Zustand als Reaktion auf App-Ereignisse umschalten. Ein häufiges Beispiel ist die Bereitstellung von Feedback während einer langwierigen Aufgabe:

```java
Label statusLabel = new Label("Bereit");
Button startButton = new Button("Prozess starten");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Verarbeitung...");
    statusLabel.addClassName("processing");
    
    performTask(() -> {
        statusLabel.setText("Abgeschlossen");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Das Deaktivieren des Buttons, während die Aufgabe läuft, verhindert doppelte Einsendungen, und die Aktualisierung des Labels informiert den Benutzer darüber, was gerade passiert.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

Das `ComponentLifecycleObserver`-Interface ermöglicht es Ihnen, Ereignisse im Lebenszyklus von Komponenten von außerhalb der Komponente selbst zu beobachten. Dies ist nützlich, wenn Sie auf eine Komponente reagieren möchten, die erstellt oder zerstört wird, ohne deren Implementierung zu ändern. Zum Beispiel könnten Sie es verwenden, um ein Register aktiver Komponenten zu führen oder externe Ressourcen freizugeben, wenn eine Komponente entfernt wird.

### Grundlegende Verwendung {#basic-usage}

Rufen Sie `addLifecycleObserver()` bei einer Komponente auf, um eine Callback-Funktion zu registrieren. Der Callback erhält die Komponente und das Lebenszyklusereignis:

```java
Button button = new Button("Beobachten Sie mich");

button.addLifecycleObserver((component, event) -> {
    switch (event) {
        case CREATE:
            System.out.println("Button wurde erstellt");
            break;
        case DESTROY:
            System.out.println("Button wurde zerstört");
            break;
    }
});
```

### Muster: Ressourcenregister {#pattern-resource-registry}

Das DESTROY-Ereignis ist besonders nützlich, um ein Register automatisch synchron zu halten. Anstatt Komponenten manuell zu entfernen, wenn sie nicht mehr benötigt werden, lassen Sie die Komponente das Register selbst benachrichtigen:

```java
public class ResourceRegistry {
    private final Map<String, Component> activeComponents = new ConcurrentHashMap<>();
    
    public void track(Component component, String name) {
        activeComponents.put(name, component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                activeComponents.remove(name);
            }
        });
    }
}
```

### Muster: Komponentenkoordination {#pattern-component-coordination}

Eine Koordinator-Klasse, die eine Gruppe verwandter Komponenten verwaltet, kann denselben Ansatz verwenden, um ihre interne Liste genau zu halten:

```java
public class FormCoordinator {
    private final List<DwcComponent<?>> managedComponents = new ArrayList<>();
    
    public void manage(DwcComponent<?> component) {
        managedComponents.add(component);
        
        component.addLifecycleObserver((comp, event) -> {
            if (event == ComponentLifecycleObserver.LifecycleEvent.DESTROY) {
                managedComponents.remove(comp);
            }
        });
    }
    
    public void disableAll() {
        managedComponents.forEach(c -> c.setEnabled(false));
    }
}
```

### Wann verwenden {#when-to-use}

Verwenden Sie `ComponentLifecycleObserver` für:
- Aufbau von Komponentenregistern
- Implementierung von Protokollen oder Monitoren
- Koordination mehrerer Komponenten
- Bereinigung externer Ressourcen

Für die Ausführung von Code, nachdem eine Komponente an das DOM angeheftet wurde, siehe [`whenAttached()`](/docs/building-ui/composite-components) im Leitfaden für zusammengesetzte Komponenten.

## Benutzerdaten {#user-data}

Komponenten können beliebige serverseitige Daten über `setUserData()` und `getUserData()` tragen. Beide Methoden verwenden einen Schlüssel, um die Daten zu identifizieren. Dies ist nützlich, wenn Sie Domänenobjekte oder Kontexte mit einer Komponente verknüpfen müssen, ohne eine separate Nachschlage-Struktur zu verwalten.

```java
Button button = new Button("Verarbeiten");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Da Benutzerdaten niemals an den Client gesendet werden, können Sie sensible Informationen oder große Objekte sicher speichern, ohne den Datenverkehr im Netzwerk zu beeinflussen.
