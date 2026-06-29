---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: fb67c93e2165a651245a703c772d3bcb
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Komponenten sind die Bausteine der webforJ-Anwendungen. Ob Sie integrierte Komponenten wie `Button` und `TextField` verwenden oder mit benutzerdefinierten Komponenten arbeiten, die von Ihrem Team bereitgestellt werden, die Art und Weise, wie Sie mit ihnen interagieren, folgt demselben konsistenten Modell: Sie konfigurieren Eigenschaften, verwalten den Zustand und komponieren Komponenten zu Layouts.

Dieser Leitfaden konzentriert sich auf diese alltäglichen Operationen: nicht auf die internen Abläufe, wie Komponenten funktionieren, sondern darauf, wie man mit ihnen in der Praxis arbeitet.

## Komponenteneigenschaften {#component-properties}

Jede Komponente bietet Eigenschaften, die ihren Inhalt, ihr Aussehen und ihr Verhalten steuern. Die meisten davon haben spezifische, typisierte Java-Methoden (`setText()`, `setTheme()`, `setExpanse()`, usw.), was die primäre Methode ist, wie Sie Komponenten in webforJ konfigurieren. In den folgenden Abschnitten werden die Eigenschaften und Methoden behandelt, die allgemein auf Komponententypen anwendbar sind.

### Textinhalt {#text-content}

Die Methode `setText()` setzt den sichtbaren Text einer Komponente als literale Zeichen, wie die Beschriftung auf einem `Button` oder den Inhalt eines `Label`. Für Eingabekomponenten wie `TextField` verwenden Sie stattdessen `setValue()`, um den aktuellen Wert des Feldes festzulegen.

```java
Button button = new Button();
button.setText("Klick mich");

Label label = new Label();
label.setText("Status: bereit");

TextField field = new TextField();
field.setValue("Anfangswert");
```

Markup, das mit `setText()` geschrieben wird, erscheint als diese Zeichen und wird niemals ausgeführt, wodurch verhindert wird, dass Text, der von Benutzereingaben oder externen Daten stammt, als lebendes Markup interpretiert wird.

```java
// Wird als die literalen Zeichen "<b>Status: ready</b>" angezeigt
component.setText("<b>Status: ready</b>");
```

:::note Verwendung des `<html>`-Tags
Frühere Versionen von webforJ behandelten einen mit `<html>` umschlossenen Wert, der an `setText()` übergeben wurde, als HTML. Dieses Verhalten ist veraltet und wird in webforJ 27.00 entfernt.

Beim ersten Mal, wenn ein mit `<html>` umschlossener Wert `setText()` erreicht, wird eine Warnung protokolliert, die den Komponenten- und den Aufrufort nennt, damit der Aufruf zu `setHtml()` verschoben werden kann.

Um die Standardwerte von webforJ 27.00 im Voraus zu übernehmen, setzen Sie `webforj.legacyHtmlInText` auf `false`. In einer Spring-Anwendung wird derselbe Wert über `webforj.legacy-html-in-text` gesetzt.

```java
// webforj.legacyHtmlInText = true (Standard)
component.setText("<html><b>Status: ready</b></html>"); // rendert fett

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: ready</b></html>"); // zeigt die Zeichen <b>Status: ready</b>
```
:::

### HTML rendern {#rendering-html}

Einige Komponenten unterstützen auch `setHtml()`, wenn Sie inline HTML-Markup im Inhalt rendern müssen:

```java
Div container = new Div();
container.setHtml("<strong>Fetter Text</strong> und <em>kursiver Text</em>");
```

:::danger Cross-Site-Scripting (XSS)
Als Vorsichtsmaßnahme gegen [Cross-Site-Scripting (XSS)-Angriffe](/docs/security/application-security/common-threats#cross-site-scripting-xss) verwenden Sie `setHtml()` nur mit Inhalten, die Sie direkt kontrollieren.
:::

### HTML-Attribute {#html-attributes}

Die meiste Konfiguration in webforJ erfolgt über typisierte Java-Methoden und nicht über rohe HTML-Attribute. Dennoch ist `setAttribute()` nützlich, um Zugänglichkeitsattribute zu übergeben, die keine spezielle API haben:

```java
Button button = new Button("Absenden");
button.setAttribute("aria-label", "Das Formular absenden");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Überprüfen Sie die Unterstützung der Komponente
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

DOM-IDs werden häufig für Testauswahlen und CSS-Zielverzeichnisse in Ihren Stylesheets verwendet.

:::tip Bevorzugen Sie Klassen für die Verwaltung mehrerer Komponenten
Im Gegensatz zu CSS-Klassen sollten IDs innerhalb Ihrer App einzigartig sein. Wenn Sie mehrere Komponenten anvisieren müssen, verwenden Sie stattdessen `addClassName()`.
:::

:::info Vom Framework verwaltete IDs
webforJ weist auch automatisch Identifikatoren an Komponenten intern zu. Die serverseitige ID (über `getComponentId()` zugänglich) wird zur Verfolgung des Frameworks verwendet, während die clientseitige ID (über `getClientComponentId()` zugänglich) für die client-server Kommunikation verwendet wird. Diese unterscheiden sich von dem DOM-Attribut `id`, das Sie mit `setAttribute()` festlegen.
:::

### Styling {#styling}

Drei Methoden decken die meisten Styling-Anforderungen ab: `setStyle()` für einzelne CSS-Property-Werte und `addClassName()` sowie `removeClassName()`, um CSS-Klassen anzuwenden oder zu entfernen, die in Ihren Stylesheets definiert sind. 
Verwenden Sie `setStyle()` für kleinere oder einmalige Styling-Anpassungen, und verwenden Sie CSS-Klassen, um größere oder wiederverwendbare Stile anzuwenden.

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

## Komponentenstatus {#component-state}

Über Inhalt und Aussehen hinaus haben Komponenten Zustandsattribute, die bestimmen, ob sie sichtbar sind und ob sie auf Interaktionen des Benutzers reagieren. Die beiden am häufigsten verwendeten sind `setVisible()` und `setEnabled()`.

`setVisible()` steuert, ob die Komponente überhaupt in der UI gerendert wird. `setEnabled()` steuert, ob sie Eingaben oder Interaktionen akzeptiert, während sie sichtbar bleibt. In den meisten Fällen ist es vorzuziehen, zu deaktivieren statt zu verstecken: Ein deaktivierter Button kommuniziert weiterhin, dass eine Aktion existiert, aber noch nicht verfügbar ist, was weniger verwirrend ist, als wenn er erscheint und verschwindet.

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

:::warning Deaktiviert und verborgen sind keine Sicherheit
`setVisible(false)` und `setEnabled(false)` betreffen nur die UI. Sie verhindern nicht, dass ein entschlossener Benutzer die zugrunde liegende Aktion über den Browser oder eine gestaltete Anfrage ausführt, daher sollten Sie sich niemals auf sie verlassen, um sensible Operationen zu schützen. Setzen Sie immer die Zugriffssteuerung auf dem Server durch. Siehe [Deaktiviert und verborgen sind keine Sicherheit](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) für weitere Details.
:::

Das folgende Anmeldeformular demonstriert `setEnabled()` in der Praxis. Der Anmelde-Button bleibt deaktiviert, bis beide Felder Inhalt haben, was dem Benutzer klar macht, dass Eingaben erforderlich sind, bevor er fortfahren kann:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/resources/static/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Arbeiten mit Containern {#working-with-containers}

In webforJ wird das Layout von Containern behandelt, die Komponenten enthalten und steuern, wie sie angeordnet werden. Sie positionieren die untergeordneten Komponenten nicht manuell; stattdessen fügen Sie sie einem Container hinzu und konfigurieren die Layout-Eigenschaften dieses Containers.

### Komponenten hinzufügen {#adding-components}

Alle Container bieten eine `add()`-Methode. Sie können Komponenten einzeln oder alle auf einmal übergeben:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klick mich"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("E-Mail");
Button submitButton = new Button("Absenden");

container.add(nameField, emailField, submitButton);
```

### Layout-Optionen {#layout-options}

`FlexLayout` ist der primäre Layout-Container in webforJ und deckt die meisten Anwendungsfälle ab: Reihen, Spalten, Ausrichtung, Abstände und Umbrüche. Für komplexere Anordnungen wie CSS-Grid oder benutzerdefinierte Positionierungen können Sie CSS direkt über `setStyle()` oder `addClassName()` auf jede Container-Komponente anwenden. Siehe die [FlexLayout](/docs/components/flex-layout) Dokumentation für die vollständige Palette von Layout-Optionen.

### Abschnitte anzeigen und ausblenden {#showing-hiding-sections}

Eine häufige Verwendung von `setVisible()` in Containern ist das Anzeigen zusätzlicher UI, nur wenn sie relevant ist. Dies hält die Benutzeroberfläche fokussiert und reduziert visuelle Unordnung. Anstatt zu einer neuen Ansicht zu navigieren, können Sie einen Abschnitt des aktuellen Layouts direkt als Reaktion auf Benutzereingaben anzeigen.

Das folgende Einstellungsfeld demonstriert dies: Grundlegende Benachrichtigungseinstellungen sind immer sichtbar, und ein Abschnitt mit erweiterten Optionen erscheint nur, wenn der Benutzer danach fragt. Der Speichern-Button wird aktiv, sobald eine Einstellung geändert wird:

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

Dies ist nützlich, wenn Sie den Inhalt vollständig ersetzen müssen, wie beim Austausch eines Ladeindikators gegen die geladenen Daten.

## Formularvalidierung {#form-validation}

Die Koordination mehrerer Komponenten zur Sperrung einer Absendaktion ist ein häufiges Muster in webforJ-Benutzeroberflächen. Die grundsätzliche Idee ist, dass jedes Eingabefeld einen Listener registriert, und wann immer ein Wert sich ändert, bewertet das Formular erneut, ob alle Kriterien erfüllt sind und aktualisiert den Absenden-Button entsprechend.

Das folgende Beispiel verbindet dies manuell, damit Sie sehen können, wie der Status der Komponente und Ereignis-Listener zusammenarbeiten. Dies ist jedoch nicht der empfohlene Ansatz für echte Formulare: Die manuelle Listener-Logik wird schwer zu pflegen, wenn Formulare wachsen, und sie verbindet Ihre Komponenten nicht mit einem zugrunde liegenden Datenmodell.

:::tip Verwenden Sie Datenbindung für die Formularvalidierung
Für Produktionsformulare verwenden Sie [Datenbindung](/docs/data-binding/overview). Sie umfasst Validierung, bidirektionale Synchronisierung zwischen Komponenten und Ihrem Modell sowie Wertetransformation durch `BindingContext`. Das hier gezeigte manuelle Muster dient nur zur Veranschaulichung.
:::

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

Komponenten müssen nach ihrer Erstellung nicht in einem festen Zustand bleiben. Sie können Text aktualisieren, CSS-Klassen austauschen und den Status aktivieren oder deaktivieren, um auf App-Ereignisse zu reagieren. Ein häufiges Beispiel ist, während eines langanhaltenden Vorgangs Feedback zu geben:

```java
Label statusLabel = new Label("Bereit");
Button startButton = new Button("Prozess starten");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Verarbeite...");
    statusLabel.addClassName("processing");
    
    performTask(() -> {
        statusLabel.setText("Fertig");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Das Deaktivieren des Buttons, während der Vorgang läuft, verhindert doppelte Absendungen, und die Aktualisierung des Labels informiert den Benutzer darüber, was geschieht.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

Das `ComponentLifecycleObserver`-Interface ermöglicht es Ihnen, Komponentenlebenszyklusereignisse von außerhalb der Komponente selbst zu beobachten. Dies ist nützlich, wenn Sie auf eine Komponente reagieren müssen, die erstellt oder zerstört wird, ohne ihre Implementierung zu ändern. Zum Beispiel könnten Sie es verwenden, um ein Register aktiver Komponenten zu führen oder externe Ressourcen freizugeben, wenn eine Komponente entfernt wird.

### Grundlegende Verwendung {#basic-usage}

Rufen Sie `addLifecycleObserver()` auf jeder Komponente auf, um einen Callback zu registrieren. Der Callback erhält die Komponente und das Lebenszyklusereignis:

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

### Wann zu verwenden {#when-to-use}

Verwenden Sie `ComponentLifecycleObserver` für:
- Aufbau von Komponentenregistern
- Implementierung von Protokollierung oder Überwachung
- Koordinierung mehrerer Komponenten
- Bereinigung externer Ressourcen

Um Code auszuführen, nachdem eine Komponente an das DOM angeheftet wurde, siehe `whenAttached()` im [Komponenten erstellen](/docs/building-ui/composing-components) Leitfaden.

## Benutzerdaten {#user-data}

Komponenten können beliebige serverseitige Daten über `setUserData()` und `getUserData()` tragen. Beide Methoden nehmen einen Schlüssel zur Identifizierung der Daten. Dies ist nützlich, wenn Sie Domainobjekte oder Kontexte mit einer Komponente verknüpfen müssen, ohne eine separate Lookup-Struktur verwalten zu müssen.

```java
Button button = new Button("Verarbeiten");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Da Benutzerdaten niemals an den Client gesendet werden, können Sie sicher sensible Informationen oder große Objekte speichern, ohne den Netzwerkverkehr zu beeinflussen.
