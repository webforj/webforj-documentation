---
sidebar_position: 3
title: Using Components
sidebar_class_name: new-content
_i18n_hash: 3ffe2e3b31ea278e434f7319f8019637
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Komponenten sind die Fundamentsteine von webforJ-Anwendungen. Ob Sie integrierte Komponenten wie `Button` und `TextField` verwenden oder mit benutzerdefinierten Komponenten arbeiten, die von Ihrem Team bereitgestellt werden, die Interaktion erfolgt nach dem gleichen konsistenten Modell: Sie konfigurieren Eigenschaften, verwalten den Zustand und setzen Komponenten in Layouts zusammen.

Dieser Leitfaden konzentriert sich auf die täglichen Abläufe: nicht auf die internen Abläufe, wie Komponenten funktionieren, sondern darauf, wie Sie mit ihnen in der Praxis Fortschritte erzielen.

## Komponenten-Eigenschaften {#component-properties}

Jede Komponente stellt Eigenschaften zur Verfügung, die ihren Inhalt, ihr Aussehen und ihr Verhalten steuern. Die meisten von ihnen haben dedizierte, typisierte Java-Methoden (`setText()`, `setTheme()`, `setExpanse()` usw.), die die primäre Methode sind, mit der Sie Komponenten in webforJ konfigurieren. Die nachfolgenden Abschnitte behandeln die Eigenschaften und Methoden, die allgemein für Komponententypen gelten.

### Textinhalt {#text-content}

Die Methode `setText()` legt den sichtbaren Text einer Komponente fest, wie z.B. die Beschriftung auf einem `Button` oder den Inhalt eines `Label`. Für Eingabekomponenten wie `TextField` verwenden Sie stattdessen `setValue()`, um den aktuellen Wert des Feldes festzulegen.

```java
Button button = new Button();
button.setText("Klicken Sie mich");

Label label = new Label();
label.setText("Status: bereit");

TextField field = new TextField();
field.setValue("Anfangswert");
```

Einige Komponenten unterstützen auch `setHtml()` für Fälle, in denen Sie Inline-HTML-Markup im Inhalt benötigen:

```java
Div container = new Div();
container.setHtml("<strong>Fetter Text</strong> und <em>kursiver Text</em>");
```

### HTML-Attribute {#html-attributes}

Die meiste Konfiguration in webforJ erfolgt über typisierte Java-Methoden und nicht über rohe HTML-Attribute. Dennoch ist `setAttribute()` nützlich, um Zugänglichkeitsattribute zu übergeben, die keine dedizierte API haben:

```java
Button button = new Button("Absenden");
button.setAttribute("aria-label", "Formular absenden");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Überprüfen Sie die Unterstützung von Komponenten
Nicht alle Komponenten unterstützen beliebige Attribute. Das hängt von der zugrunde liegenden Implementierung der Komponente ab.
:::

### Komponenten-IDs {#component-ids}

Sie können einer HTML-Element-ID einer Komponente mit `setAttribute()` eine ID zuweisen:

```java
Button submitButton = new Button("Absenden");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("Email");
emailField.setAttribute("id", "email-input");
```

DOM-IDs werden häufig für Testauswahlen und CSS-Anpassungen in Ihren Stylesheets verwendet.

:::tip Bevorzugen Sie Klassen für mehrere Komponenten
Im Gegensatz zu CSS-Klassen sollten IDs innerhalb Ihrer App eindeutig sein. Wenn Sie mehrere Komponenten anvisieren müssen, verwenden Sie stattdessen `addClassName()`.
:::

:::info Vom Framework verwaltete IDs
webforJ weist auch automatisch intern Identifikatoren für Komponenten zu. Die serverseitige ID (über `getComponentId()` aufgerufen) wird für das Framework-Tracking verwendet, während die clientseitige ID (über `getClientComponentId()` aufgerufen) für die Kommunikation zwischen Client und Server verwendet wird. Diese sind getrennt von dem DOM-`id`-Attribut, das Sie mit `setAttribute()` festgelegt haben.
:::

### Styling {#styling}

Drei Methoden decken die meisten Styling-Bedürfnisse ab: `setStyle()` für individuelle CSS-Property-Werte und `addClassName()` sowie `removeClassName()` zum Anwenden oder Entfernen von in Ihren Stylesheets definierten CSS-Klassen. Verwenden Sie `setStyle()` für kleinere oder einmalige Styling-Anpassungen und verwenden Sie CSS-Klassen, um größere oder wiederverwendbare Styles anzuwenden.

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
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) ist ein veralteter Ansatz und wird für neue Projekte allgemein nicht empfohlen. In den meisten Fällen sollten Sie Ihre Styles in separaten CSS-Dateien behalten.
:::

## Komponenten-Zustand {#component-state}

Über Inhalt und Aussehen hinaus haben Komponenten Zustands-Eigenschaften, die bestimmen, ob sie sichtbar sind und ob sie auf Benutzerinteraktion reagieren. Die beiden am häufigsten verwendeten sind `setVisible()` und `setEnabled()`.

`setVisible()` steuert, ob die Komponente überhaupt in der Benutzeroberfläche gerendert wird. `setEnabled()` steuert, ob sie Eingaben oder Interaktionen akzeptiert, während sie sichtbar bleibt. In den meisten Fällen ist das Deaktivieren besser als das Verbergen: Ein deaktivierter Button zeigt immer noch an, dass eine Aktion vorhanden ist, aber noch nicht verfügbar ist, was weniger verwirrend ist, als wenn er erscheint und verschwindet.

```java
// Ein zusätzliches Feld anzeigen, wenn ein Kontrollkästchen aktiviert ist
TextField advancedField = new TextField("Erweiterte Einstellung");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Erweiterte Einstellungen anzeigen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Ein Button nur aktivieren, wenn das erforderliche Feld einen Wert hat
Button submitButton = new Button("Absenden");
submitButton.setEnabled(false);

TextField nameField = new TextField("Name");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Das folgende Anmeldeformular demonstriert `setEnabled()` in der Praxis. Der Anmeldebutton bleibt deaktiviert, bis beide Felder Inhalt haben, was dem Benutzer klar macht, dass Eingaben erforderlich sind, bevor sie fortfahren.

<ComponentDemo 
path='/webforj/conditionalstate?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/conditionalstate.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java'
height='400px'
/>

## Arbeiten mit Containern {#working-with-containers}

In webforJ wird das Layout von Containern behandelt, die Komponenten enthalten und steuern, wie sie angeordnet sind. Sie positionieren untergeordnete Komponenten nicht manuell; stattdessen fügen Sie sie einem Container hinzu und konfigurieren die Layout-Eigenschaften dieses Containers.

### Komponenten hinzufügen {#adding-components}

Alle Container bieten eine `add()`-Methode. Sie können Komponenten einzeln oder alle auf einmal übergeben:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klicken Sie mich"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("Email");
Button submitButton = new Button("Absenden");

container.add(nameField, emailField, submitButton);
```

### Layout-Optionen {#layout-options}

`FlexLayout` ist der primäre Layout-Container in webforJ und deckt die Mehrheit der Anwendungsfälle ab: Reihen, Spalten, Ausrichtung, Abstand und Umbruch. Für komplexere Anordnungen wie CSS Grid oder benutzerdefinierte Positionierung können Sie CSS direkt über `setStyle()` oder `addClassName()` auf jeden Container anwenden. Siehe die [FlexLayout](/docs/components/flex-layout) Dokumentation für die vollständige Palette von Layout-Optionen.

### Abschnitte anzeigen und ausblenden {#showing-hiding-sections}

Eine häufige Verwendung von `setVisible()` in Containern besteht darin, eine zusätzliche Benutzeroberfläche erst dann anzuzeigen, wenn sie relevant ist. Dadurch bleibt die Benutzeroberfläche fokussiert und reduziert visuelles Durcheinander. Anstatt zu einer neuen Ansicht zu navigieren, können Sie einen Abschnitt des aktuellen Layouts direkt als Reaktion auf Benutzereingaben anzeigen.

Das folgende Einstellungsfeld demonstriert dies: Grundlegende Benachrichtigungseinstellungen sind immer sichtbar, und ein Abschnitt mit erweiterten Optionen erscheint nur, wenn der Benutzer danach fragt. Der Speichern-Button wird aktiviert, sobald eine Einstellung geändert wird:

<ComponentDemo 
path='/webforj/progressivedisclosure?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/progressivedisclosure.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java'
height='450px'
/>

### Container-Verwaltung {#container-management}

Verwenden Sie `remove()` und `removeAll()`, um Komponenten zur Laufzeit aus einem Container zu entfernen:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporär");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dies ist nützlich, wenn Sie den Inhalt vollständig ersetzen müssen, beispielsweise um einen Ladeindikator durch die geladenen Daten zu ersetzen.

## Formularvalidierung {#form-validation}

Die Koordination mehrerer Komponenten, um eine Absendeaktion zu steuern, ist eines der häufigsten Muster in webforJ-UIs. Die Kernidee ist einfach: jedes Eingabefeld registriert einen Listener, und wann immer sich ein Wert ändert, bewertet das Formular erneut, ob alle Kriterien erfüllt sind, und aktualisiert den Absende-Button entsprechend.

Dies ist vorzuziehen, um Validierungsfehler erst nach dem Klicken auf Absenden anzuzeigen, da dies kontinuierliches Feedback gibt und unnötige Einsendungen verhindert. Der Absende-Button dient als Indikator: Deaktiviert bedeutet, das Formular ist nicht bereit, aktiviert bedeutet, es ist bereit.

In diesem Kontaktformular darf das Namensfeld nicht leer sein, die E-Mail muss ein `@`-Symbol enthalten und die Nachricht muss mindestens 10 Zeichen lang sein:

<ComponentDemo 
path='/webforj/formvalidation?' 
cssURL='https://raw.githubusercontent.com/webforj/webforj-documentation/main/src/main/resources/static/usingcomponents/formvalidation.css'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java'
height='500px'
/>

## Dynamische Inhaltsaktualisierungen {#dynamic-content-updates}

Komponenten müssen nach der Erstellung nicht in einem festen Zustand bleiben. Sie können jederzeit Text aktualisieren, CSS-Klassen wechseln und den aktivierten Zustand als Reaktion auf Ereignisse in der App umschalten. Ein häufiges Beispiel ist das Bereitstellen von Feedback während einer langwierigen Aufgabe:

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

Das Deaktivieren des Buttons während der Ausführung der Aufgabe verhindert doppelte Einsendungen und die Aktualisierung des Labels hält den Benutzer darüber informiert, was passiert.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

Das `ComponentLifecycleObserver`-Interface ermöglicht es Ihnen, Ereignisse im Lebenszyklus von Komponenten von außerhalb der Komponente selbst zu beobachten. Dies ist nützlich, wenn Sie auf die Erstellung oder Zerstörung einer Komponente reagieren möchten, ohne deren Implementierung zu ändern. Beispielsweise können Sie es verwenden, um ein Verzeichnis aktiver Komponenten zu pflegen oder externe Ressourcen freizugeben, wenn eine Komponente entfernt wird.

### Grundlegende Verwendung {#basic-usage}

Rufen Sie `addLifecycleObserver()` für jede Komponente auf, um einen Callback zu registrieren. Der Callback erhält die Komponente und das Lebenszyklusereignis:

```java
Button button = new Button("Beobachte mich");

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

### Muster: Ressourcenverzeichnis {#pattern-resource-registry}

Das DESTROY-Ereignis ist besonders nützlich, um ein Verzeichnis automatisch synchron zu halten. Anstatt Komponenten manuell zu entfernen, wenn sie nicht mehr benötigt werden, lassen Sie die Komponente das Verzeichnis selbst benachrichtigen:

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
- Das Erstellen von Komponentenverzeichnissen
- Das Implementieren von Protokollierung oder Überwachung
- Das Koordinieren mehrerer Komponenten
- Das Bereinigen externer Ressourcen

Um Code auszuführen, nachdem eine Komponente an das DOM angehängt wurde, siehe [`whenAttached()`](/docs/building-ui/composite-components) im Leitfaden für Composite-Komponenten.

## Benutzerdaten {#user-data}

Komponenten können beliebige serverseitige Daten über `setUserData()` und `getUserData()` tragen. Beide Methoden verwenden einen Schlüssel, um die Daten zu identifizieren. Dies ist nützlich, wenn Sie Domänenobjekte oder Kontexte mit einer Komponente verbinden möchten, ohne eine separate Lookup-Struktur zu verwalten.

```java
Button button = new Button("Verarbeiten");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Da Benutzerdaten niemals an den Client gesendet werden, können Sie sensible Informationen oder große Objekte sicher speichern, ohne den Netzwerkverkehr zu beeinträchtigen.
