---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 97722c8e3bf6c3129c078d8ae23cf2a4
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Komponenten sind die Bausteine von webforJ-Anwendungen. Ob Sie nun integrierte Komponenten wie `Button` und `TextField` verwenden oder mit benutzerdefinierten Komponenten arbeiten, die von Ihrem Team bereitgestellt werden, die Interaktion folgt demselben konsistenten Modell: Sie konfigurieren Eigenschaften, verwalten den Zustand und fügen Komponenten in Layouts ein.

Dieser Leitfaden konzentriert sich auf diese alltäglichen Vorgänge: nicht auf die Interna, wie Komponenten funktionieren, sondern darauf, wie man sie in der Praxis effektiv nutzen kann.

## Komponenten Eigenschaften {#component-properties}

Jede Komponente bietet Eigenschaften, die ihren Inhalt, ihr Aussehen und ihr Verhalten steuern. Die meisten davon verfügen über dedizierte, typisierte Java-Methoden (`setText()`, `setTheme()`, `setExpanse()` usw.), die die primäre Art und Weise sind, wie Sie Komponenten in webforJ konfigurieren. In den folgenden Abschnitten werden die Eigenschaften und Methoden behandelt, die allgemein auf verschiedene Komponententypen anwendbar sind.

### Textinhalt {#text-content}

Die Methode `setText()` setzt den sichtbaren Text einer Komponente als literale Zeichen, wie die Beschriftung eines `Button` oder den Inhalt eines `Label`. Für Eingabekomponenten wie `TextField` verwenden Sie stattdessen `setValue()`, um den aktuellen Wert des Feldes festzulegen.

```java
Button button = new Button();
button.setText("Klick mich");

Label label = new Label();
label.setText("Status: bereit");

TextField field = new TextField();
field.setValue("Anfangswert");
```

Markup, das mit `setText()` geschrieben wird, erscheint als diese Zeichen und wird niemals ausgeführt, was sicherstellt, dass Text, der von Benutzereingaben oder externen Daten stammt, nicht als lebendes Markup interpretiert wird.

```java
// Wird als die literalen Zeichen "<b>Status: ready</b>" angezeigt
component.setText("<b>Status: ready</b>");
```

:::note Verwendung des `<html>`-Tags
Frühere Versionen von webforJ behandelten einen in `<html>` gehüllten Wert, der an `setText()` übergeben wurde, als HTML. Dieses Verhalten ist veraltet und wird in webforJ 27.00 entfernt.

Beim ersten Mal, dass ein in `<html>` gehüllter Wert an `setText()` übergeben wird, wird eine Warnung protokolliert, die die Komponente und den Aufrufort benennt, sodass der Aufruf in `setHtml()` verschoben werden kann.

Um im Vorfeld den Standard von webforJ 27.00 zu übernehmen, setzen Sie `webforj.legacyHtmlInText` auf `false`. In einer Spring-Anwendung wird der gleiche Wert über `webforj.legacy-html-in-text` festgelegt.

```java
// webforj.legacyHtmlInText = true (Standard)
component.setText("<html><b>Status: ready</b></html>"); // rendert fett

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: ready</b></html>"); // zeigt die Zeichen <b>Status: ready</b>
```
:::

### HTML rendern {#rendering-html}

Einige Komponenten unterstützen auch `setHtml()`, wenn Sie Inline-HTML-Markup im Inhalt rendern müssen:

```java
Div container = new Div();
container.setHtml("<strong>Fetter Text</strong> und <em>kursiver Text</em>");
```

:::danger Cross-Site-Scripting (XSS)
Zur Vorsicht gegen [Cross-Site-Scripting (XSS)-Angriffe](/docs/security/application-security/common-threats#cross-site-scripting-xss) verwenden Sie `setHtml()` nur mit Inhalten, die Sie direkt kontrollieren.
:::

### HTML-Attribute {#html-attributes}

Die meisten Konfigurationen in webforJ erfolgen über typisierte Java-Methoden und nicht über rohe HTML-Attribute. Dennoch ist `setAttribute()` nützlich für das Übergeben von Zugänglichkeitsattributen, für die es keine dedizierte API gibt:

```java
Button button = new Button("Absenden");
button.setAttribute("aria-label", "Das Formular absenden");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Komponentenunterstützung überprüfen
Nicht alle Komponenten unterstützen arbiträre Attribute. Dies hängt von der zugrunde liegenden Implementierung der Komponente ab.
:::

### Komponenten-IDs {#component-ids}

Sie können einer HTML-Element-ID einer Komponente mit `setAttribute()` zuweisen:

```java
Button submitButton = new Button("Absenden");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-Mail");
emailField.setAttribute("id", "email-input");
```

DOM-IDs werden häufig für Testauswahlen und CSS-Targeting in Ihren Stylesheets verwendet.

:::tip Bevorzugen Sie Klassen für das Ziel von mehreren Komponenten
Im Gegensatz zu CSS-Klassen sollten IDs innerhalb Ihrer Anwendung einzigartig sein. Wenn Sie mehrere Komponenten anvisieren müssen, verwenden Sie stattdessen `addClassName()`.
:::

:::info Vom Framework verwaltete IDs
webforJ weist auch automatisch Identifikatoren für Komponenten intern zu. Die serverseitige ID (erreichbar über `getComponentId()`) wird für das Framework-Tracking verwendet, während die clientseitige ID (erreichbar über `getClientComponentId()`) für die Kommunikation zwischen Client und Server genutzt wird. Diese sind unabhängig von der DOM-`id`-Eigenschaft, die Sie mit `setAttribute()` festlegen.
:::

### Styling {#styling}

Drei Methoden decken die meisten Styling-Bedürfnisse ab: `setStyle()` für einzelne CSS-Property-Werte sowie `addClassName()` und `removeClassName()`, um CSS-Klassen anzuwenden oder zu entfernen, die in Ihren Stylesheets definiert sind. Verwenden Sie `setStyle()` für kleinere oder einmalige Styling-Anpassungen und nutzen Sie CSS-Klassen für größere oder wiederverwendbare Stilgebung.

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
[`@InlineStyleSheet`](/docs/managing-resources/importing-assets#injecting-css) ist ein veralteter Ansatz und wird im Allgemeinen nicht für neue Projekte empfohlen. In den meisten Fällen sollten Sie Ihre Stile in separaten CSS-Dateien halten.
:::

## Komponentenstatus {#component-state}

Neben Inhalt und Aussehen haben Komponenten Status-Eigenschaften, die bestimmen, ob sie sichtbar sind und ob sie auf Benutzereingaben reagieren. Die zwei am häufigsten verwendeten sind `setVisible()` und `setEnabled()`.

`setVisible()` steuert, ob die Komponente überhaupt in der Benutzeroberfläche gerendert wird. `setEnabled()` steuert, ob sie Eingaben oder Interaktionen annimmt, während sie sichtbar bleibt. In den meisten Fällen ist es besser, eine Komponente zu deaktivieren, als sie auszublenden: Ein deaktivierter Button kommuniziert weiterhin, dass eine Aktion existiert, aber noch nicht verfügbar ist, was weniger desorientierend ist, als wenn er erscheint und verschwindet.

```java
// Ein zusätzliches Feld anzeigen, wenn eine Checkbox aktiviert ist
TextField advancedField = new TextField("Erweiterte Einstellung");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Erweiterte Einstellungen anzeigen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Einen Button nur aktivieren, wenn das erforderliche Feld einen Wert hat
Button submitButton = new Button("Absenden");
submitButton.setEnabled(false);

TextField nameField = new TextField("Name");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

Das folgende Anmeldeformular demonstriert `setEnabled()` in der Praxis. Der Anmeldebutton bleibt deaktiviert, bis beide Felder Inhalte haben, was dem Benutzer klar macht, dass eine Eingabe erforderlich ist, bevor er fortfahren kann:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='400px'
/>

## Arbeiten mit Containern {#working-with-containers}

In webforJ wird das Layout von Containern behandelt, das sind Komponenten, die andere Komponenten halten und steuern, wie sie angeordnet sind. Sie positionieren die untergeordneten Komponenten nicht manuell; stattdessen fügen Sie sie einem Container hinzu und konfigurieren die Layout-Eigenschaften dieses Containers.

### Komponenten hinzufügen {#adding-components}

Alle Container bieten eine `add()`-Methode. Sie können die Komponenten einzeln oder alle auf einmal übergeben:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klick mich"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("E-Mail");
Button submitButton = new Button("Absenden");

container.add(nameField, emailField, submitButton);
```

### Layout-Optionen {#layout-options}

`FlexLayout` ist der primäre Layout-Container in webforJ und deckt die meisten Anwendungsfälle ab: Reihen, Spalten, Ausrichtung, Abstände und Wickeln. Für komplexere Anordnungen wie CSS Grid oder benutzerdefinierte Positionierungen können Sie CSS direkt über `setStyle()` oder `addClassName()` auf beliebigen Container-Komponenten anwenden. Weitere Informationen finden Sie in der [FlexLayout](/docs/components/flex-layout)-Dokumentation zu den vollständigen Layout-Optionen.

### Abschnitte anzeigen und ausblenden {#showing-hiding-sections}

Eine häufige Verwendung von `setVisible()` in Containern besteht darin, zusätzliche Benutzeroberflächen nur dann anzuzeigen, wenn sie relevant sind. Dies hält die Benutzeroberfläche fokussiert und reduziert visuelle Unordnung. Anstatt zu einer neuen Ansicht zu navigieren, können Sie einen Abschnitt des aktuellen Layouts als direkte Reaktion auf Benutzereingaben anzeigen.

Das folgende Einstellungsfeld demonstriert dies: Grundlegende Benachrichtigungseinstellungen sind immer sichtbar, und ein Abschnitt mit erweiterten Optionen erscheint nur, wenn der Benutzer danach fragt. Der Speichern-Button aktiviert sich, sobald eine Einstellung geändert wird:

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

Dies ist nützlich, wenn Sie den Inhalt vollständig ersetzen müssen, z. B. um einen Ladevorgang gegen die geladenen Daten auszutauschen.

## Formularvalidierung {#form-validation}

Die Koordination mehrerer Komponenten, um eine Abschlussaktion zu steuern, ist eines der häufigsten Muster in webforJ-UIs. Die Grundidee ist einfach: jedes Eingabefeld registriert einen Listener, und wann immer sich ein Wert ändert, überprüft das Formular erneut, ob alle Kriterien erfüllt sind, und aktualisiert den Absenden-Button entsprechend.

Dies ist besser, als Validierungsfehler nur anzuzeigen, nachdem der Benutzer auf Absenden geklickt hat, da es kontinuierliches Feedback gibt und unnötige Einsendungen verhindert. Der Absenden-Button dient als Indikator: deaktiviert bedeutet, dass das Formular nicht bereit ist, aktiviert bedeutet, dass es bereit ist.

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

Komponenten müssen nach ihrer Erstellung nicht in einem festen Zustand bleiben. Sie können den Text aktualisieren, CSS-Klassen austauschen und den aktivierten Zustand jederzeit als Reaktion auf App-Ereignisse umschalten. Ein häufiges Beispiel ist das Bereitstellen von Feedback während einer langanhaltenden Aufgabe:

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

Das Deaktivieren des Buttons während der Ausführung der Aufgabe verhindert doppelte Einsendungen, und die Aktualisierung des Labels hält den Benutzer darüber informiert, was passiert.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

Das `ComponentLifecycleObserver`-Interface ermöglicht es Ihnen, Ereignisse des Komponentenlebenszyklus von außerhalb der Komponente selbst zu beobachten. Dies ist nützlich, wenn Sie auf die Erstellung oder Zerstörung einer Komponente reagieren müssen, ohne deren Implementierung zu ändern. Zum Beispiel könnten Sie es verwenden, um ein Register aktiver Komponenten zu führen oder externe Ressourcen freizugeben, wenn eine Komponente entfernt wird.

### Grundlegende Verwendung {#basic-usage}

Rufen Sie `addLifecycleObserver()` auf jeder Komponente auf, um ein Callback zu registrieren. Das Callback erhält die Komponente und das Lebenszyklusereignis:

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

### Muster: Ressourcenregister {#pattern-resource-registry}

Das DESTROY-Ereignis ist besonders nützlich dafür, um ein Register automatisch synchron zu halten. Anstatt Komponenten manuell zu entfernen, wenn sie nicht mehr benötigt werden, lassen Sie die Komponente das Register selbst benachrichtigen:

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

Eine Koordinator-Klasse, die eine Sammlung verwandter Komponenten verwaltet, kann denselben Ansatz verwenden, um ihre interne Liste genau zu halten:

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
- Implementierung von Protokollierung oder Überwachung
- Koordination mehrerer Komponenten
- Bereinigung von externen Ressourcen

Um Code auszuführen, nachdem eine Komponente dem DOM hinzugefügt wurde, siehe [`whenAttached()`](/docs/building-ui/composite-components) im Leitfaden für Composite Components.

## Benutzerdaten {#user-data}

Komponenten können beliebige serverseitige Daten über `setUserData()` und `getUserData()` tragen. Beide Methoden nehmen einen Schlüssel zur Identifizierung der Daten an. Dies ist nützlich, wenn Sie Domainobjekte oder Kontexte mit einer Komponente assoziieren müssen, ohne eine separate Nachschlage-Struktur zu verwalten.

```java
Button button = new Button("Verarbeiten");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Da Benutzerdaten niemals an den Client gesendet werden, können Sie sensiblen Informationen oder großen Objekten sicher speichern, ohne den Netzwerkverkehr zu beeinträchtigen.
