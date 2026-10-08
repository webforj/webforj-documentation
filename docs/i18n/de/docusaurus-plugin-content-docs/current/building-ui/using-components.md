---
sidebar_position: 3
title: Verwendung von Komponenten
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
_i18n_hash: df0f3d5a956eda1abd755f646899a7cc
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponenten sind die Bausteine von webforJ-Anwendungen. Ob Sie integrierte Komponenten wie `Button` und `TextField` verwenden oder mit benutzerdefinierten Komponenten arbeiten, die von Ihrem Team bereitgestellt werden, die Interaktion mit ihnen folgt demselben konsistenten Modell: Sie konfigurieren Eigenschaften, verwalten den Zustand und setzen Komponenten in Layouts zusammen.

Dieser Leitfaden konzentriert sich auf die täglichen Operationen: nicht auf die Interna, wie Komponenten funktionieren, sondern darauf, wie man in der Praxis damit arbeitet.

## Komponenteneigenschaften {#component-properties}

Jede Komponente bietet Eigenschaften, die ihren Inhalt, ihr Aussehen und ihr Verhalten steuern. Die meisten davon haben spezielle, typisierte Java-Methoden (`setText()`, `setTheme()`, `setExpanse()` usw.), die die primäre Möglichkeit darstellen, Komponenten in webforJ zu konfigurieren. Die folgenden Abschnitte behandeln die Eigenschaften und Methoden, die allgemein auf Komponententypen anwendbar sind.

### Textinhalt {#text-content}

Die Methode `setText()` setzt den sichtbaren Text einer Komponente als literale Zeichen, wie z.B. die Beschriftung auf einem `Button` oder den Inhalt eines `Label`. Für Eingabekomponenten wie `TextField` verwenden Sie stattdessen `setValue()`, um den aktuellen Wert des Feldes festzulegen.

```java
Button button = new Button();
button.setText("Klick mich");

Label label = new Label();
label.setText("Status: bereit");

TextField field = new TextField();
field.setValue("Anfangswert");
```

Markup, das mit `setText()` geschrieben wurde, wird als diese Zeichen angezeigt und nie ausgeführt, was verhindert, dass Texte, die von Benutzereingaben oder externen Daten stammen, als lebendiges Markup interpretiert werden.

```java
// Wird als die literalen Zeichen "<b>Status: bereit</b>" angezeigt
component.setText("<b>Status: bereit</b>");
```

:::note Verwendung des `<html>`-Tags
Frühere Versionen von webforJ behandelten einen in `<html>` eingeschlossenen Wert, der an `setText()` übergeben wurde, als HTML. Dieses Verhalten ist veraltet und wird in webforJ 27.00 entfernt.

Beim ersten Mal, dass ein `<html>`-umwickelter Wert `setText()` erreicht, wird eine Warnung protokolliert, die die Komponente und die Aufrufstelle benennt, sodass der Aufruf nach `setHtml()` verschoben werden kann.

Um die Standardkonfiguration von webforJ 27.00 vorab anzunehmen, setzen Sie `webforj.legacyHtmlInText` auf `false`. In einer Spring-Anwendung wird derselbe Wert über `webforj.legacy-html-in-text` festgelegt.

```java
// webforj.legacyHtmlInText = true (Standard)
component.setText("<html><b>Status: bereit</b></html>"); // rendert fett

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: bereit</b></html>"); // zeigt die Zeichen <b>Status: bereit</b>
```
:::

### HTML rendern {#rendering-html}

Einige Komponenten unterstützen auch `setHtml()`, wenn Sie strukturiertes HTML-Markup im Inhalt anzeigen müssen:

```java
Div container = new Div();
container.setHtml("<strong>Fettgedruckter Text</strong> und <em>kursiver Text</em>");
```

:::danger Cross-Site-Scripting (XSS)
Als Vorsichtsmaßnahme gegen [Cross-Site-Scripting (XSS)-Angriffe](/docs/security/application-security/common-threats#cross-site-scripting-xss) verwenden Sie `setHtml()` nur mit Inhalten, die Sie direkt kontrollieren.
:::

### HTML-Attribute {#html-attributes}

Die meiste Konfiguration in webforJ erfolgt über typisierte Java-Methoden anstelle von rohen HTML-Attributen. Dennoch ist `setAttribute()` nützlich, um Zugänglichkeitsattribute zu übergeben, die keine spezielle API haben:

```java
Button button = new Button("Einreichen");
button.setAttribute("aria-label", "Das Formular einreichen");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Überprüfen Sie die Unterstützung von Komponenten
Nicht alle Komponenten unterstützen beliebige Attribute. Dies hängt von der Implementierung der zugrunde liegenden Komponente ab.
:::

### Komponentene IDs {#component-ids}

Sie können einer HTML-Element-ID einer Komponente mit `setAttribute()` zuweisen:

```java
Button submitButton = new Button("Einreichen");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-Mail");
emailField.setAttribute("id", "email-input");
```

DOM-IDs werden häufig für Test-Selektoren und CSS-Zielsetzungen in Ihren Stylesheets verwendet.

:::tip Bevorzugen Sie Klassen für das Ziel von mehreren Komponenten
Im Gegensatz zu CSS-Klassen sollten IDs innerhalb Ihrer App einzigartig sein. Wenn Sie mehrere Komponenten anvisieren müssen, verwenden Sie stattdessen `addClassName()`.
:::

:::info Framework-gesteuerte IDs
webforJ weist auch automatisch Identifikatoren an Komponenten intern zu. Die serverseitige ID (erreichbar über `getComponentId()`) wird zur Verfolgung im Framework verwendet, während die clientseitige ID (erreichbar über `getClientComponentId()`) für die Kommunikation zwischen Client und Server verwendet wird. Diese sind unabhängig von dem DOM-`id`-Attribut, das Sie mit `setAttribute()` setzen.
:::

### Styling {#styling}

Drei Methoden decken die meisten Styling-Bedürfnisse ab: `setStyle()` für individuelle CSS-Properties, sowie `addClassName()` und `removeClassName()` zum Anwenden oder Entfernen von in Ihren Stylesheets definierten CSS-Klassen. Verwenden Sie `setStyle()` für kleinere oder einmalige Styling-Anpassungen und CSS-Klassen, um größere oder wiederverwendbare Stile anzuwenden.

```java
Div container = new Div();
container.setStyle("padding", "20px");

if (isHighPriority) {
    container.setStyle("border-left", "4px solid rot");
}

Button button = new Button("Umschalten");
button.addClassName("primary", "large");

if (isLoading) {
    button.addClassName("loading");
}
```

## Komponentenstatus {#component-state}

Neben Inhalt und Aussehen haben Komponenten Status-Eigenschaften, die bestimmen, ob sie sichtbar sind und ob sie auf Benutzereingaben reagieren. Die zwei am häufigsten verwendeten sind `setVisible()` und `setEnabled()`.

`setVisible()` steuert, ob die Komponente überhaupt in der UI gerendert wird. `setEnabled()` steuert, ob sie Eingaben oder Interaktionen akzeptiert, während sie sichtbar bleibt. In den meisten Fällen ist Deaktivieren vorzuziehen als Ausblenden: Ein deaktivierter Button kommuniziert weiterhin, dass eine Aktion existiert, aber noch nicht verfügbar ist, was weniger verwirrend ist, als wenn er erscheint und verschwindet.

```java
// Ein zusätzliches Feld anzeigen, wenn ein Kontrollkästchen aktiviert ist
TextField advancedField = new TextField("Erweiterte Einstellung");
advancedField.setVisible(false);

CheckBox enableAdvanced = new CheckBox("Erweiterte Einstellungen anzeigen");
enableAdvanced.addValueChangeListener(e -> advancedField.setVisible(e.getValue()));

// Einen Button nur aktivieren, wenn das erforderliche Feld einen Wert hat
Button submitButton = new Button("Einreichen");
submitButton.setEnabled(false);

TextField nameField = new TextField("Name");
nameField.addValueChangeListener(e -> submitButton.setEnabled(!e.getValue().isBlank()));
```

:::warning Deaktiviert und verborgen sind kein Sicherheitsmerkmal
`setVisible(false)` und `setEnabled(false)` beeinflussen nur die UI. Sie verhindern nicht, dass ein entschlossener Benutzer die zugrunde liegende Aktion über den Browser oder eine gestaltete Anfrage aufruft, also verlassen Sie sich niemals darauf, um sensible Operationen zu schützen. Überprüfen Sie immer den Zugriff auf Serverseite. Siehe [Deaktiviert und verborgen sind kein Sicherheitsmerkmal](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) für weitere Details.
:::

Die folgende Anmeldemaske demonstriert `setEnabled()` in der Praxis. Der Anmelde-Button bleibt deaktiviert, bis beide Felder Inhalte haben, was dem Benutzer klar macht, dass Eingaben erforderlich sind, bevor er fortfahren kann:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
]}
height='450px'
/>

## Arbeiten mit Containern {#working-with-containers}

In webforJ wird das Layout von Containern gehandhabt, die Komponenten enthalten und steuern, wie sie angeordnet sind. Sie positionieren untergeordnete Komponenten nicht manuell; stattdessen fügen Sie sie einem Container hinzu und konfigurieren die Layout-Eigenschaften dieses Containers.

### Komponenten hinzufügen {#adding-components}

Alle Container bieten eine Methode `add()`. Sie können Komponenten einzeln oder alle auf einmal übergeben:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klick mich"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("E-Mail");
Button submitButton = new Button("Einreichen");

container.add(nameField, emailField, submitButton);
```

### Layout-Optionen {#layout-options}

`FlexLayout` ist der primäre Layout-Container in webforJ und deckt die meisten Anwendungsfälle ab: Reihen, Spalten, Ausrichtung, Abstand und Umbruch. Für komplexere Anordnungen wie CSS Grid oder benutzerdefinierte Positionierungen können Sie CSS direkt über `setStyle()` oder `addClassName()` auf jede Containerkomponente anwenden. Weitere Informationen finden Sie in der [FlexLayout](/docs/components/flex-layout)-Dokumentation über die gesamte Palette an Layout-Optionen.

### Abschnitte ein- und ausblenden {#showing-hiding-sections}

Ein häufiger Einsatz von `setVisible()` in Containern ist das Anzeigen zusätzlicher UI, nur wenn dies relevant ist. Dies hält die Benutzeroberfläche fokussiert und reduziert visuelles Durcheinander. Anstatt zu einer neuen Ansicht zu navigieren, können Sie einen Abschnitt des aktuellen Layouts direkt als Reaktion auf Benutzereingaben anzeigen.

Das folgende Einstellungsfeld demonstriert dies: Grundlegende Benachrichtigungseinstellungen sind immer sichtbar, und ein Abschnitt erweiterter Optionen erscheint nur, wenn der Benutzer danach fragt. Der Speichern-Button wird aktiv, sobald eine Einstellung geändert wird:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
]}
height='450px'
/>

### Containerverwaltung {#container-management}

Verwenden Sie `remove()` und `removeAll()`, um Komponenten zur Laufzeit aus einem Container zu entfernen:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporär");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dies ist nützlich, wenn Sie den Inhalt vollständig ersetzen müssen, z.B. um einen Ladeindikator gegen die geladenen Daten auszutauschen.

## Formularvalidierung {#form-validation}

Die Koordination mehrerer Komponenten, um eine Einreichaktion zu steuern, ist ein häufiges Muster in webforJ-UIs. Die grundlegende Idee ist, dass jedes Eingabefeld einen Listener registriert, und wann immer sich ein Wert ändert, wird das Formular neu bewertet, ob alle Kriterien erfüllt sind, und der Einreichen-Button wird entsprechend aktualisiert.

Das folgende Beispiel verbindet dies manuell, damit Sie sehen können, wie der Komponentenstatus und Ereignis-Listener zusammenarbeiten. Es ist nicht der empfohlene Ansatz für echte Formulare: Manuelle Listener-Logik wird schwer zu warten, wenn Formulare wachsen, und sie verbindet Ihre Komponenten nicht mit einem zugrunde liegenden Datenmodell.

:::tip Verwenden Sie Datenbindung zur Formularvalidierung
Für Produktionsformulare verwenden Sie [Datenbindung](/docs/data-binding/overview). Dabei werden Validierung, bidirektionale Synchronisierung zwischen Komponenten und Ihrem Modell sowie Werttransformation durch `BindingContext` abgedeckt. Das hier gezeigte manuelle Muster dient nur zu Illustrationszwecken.
:::

In diesem Kontaktformular darf das Namensfeld nicht leer sein, die E-Mail muss ein `@`-Symbol enthalten, und die Nachricht muss mindestens 10 Zeichen lang sein:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
]}
height='500px'
/>

## Dynamische Inhaltsaktualisierungen {#dynamic-content-updates}

Komponenten müssen nicht in einem festen Zustand bleiben, nachdem sie erstellt wurden. Sie können Text aktualisieren, CSS-Klassen austauschen und den aktivierten Zustand jederzeit als Reaktion auf App-Ereignisse umschalten. Ein gängiges Beispiel ist das Bereitstellen von Feedback während eines langwierigen Vorgangs:

```java
Label statusLabel = new Label("Bereit");
Button startButton = new Button("Prozess starten");

startButton.onClick(event -> {
    startButton.setEnabled(false);
    statusLabel.setText("Verarbeitung...");
    statusLabel.addClassName("processing");

    performTask(() -> {
        statusLabel.setText("Fertig");
        statusLabel.removeClassName("processing");
        statusLabel.addClassName("success");
        startButton.setEnabled(true);
    });
});
```

Das Deaktivieren des Buttons während des Vorgangs verhindert doppelte Einreichungen, und die Aktualisierung des Labels hält den Benutzer über das Geschehen informiert.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

Das Interface `ComponentLifecycleObserver` ermöglicht es Ihnen, Ereignisse des Komponentenlebenszyklus von außerhalb der Komponente selbst zu beobachten. Dies ist nützlich, wenn Sie auf eine Komponente reagieren müssen, die erstellt oder entfernt wird, ohne ihre Implementierung zu ändern. Beispielsweise könnten Sie es verwenden, um ein Register aktiver Komponenten zu führen oder externe Ressourcen freizugeben, wenn eine Komponente entfernt wird.

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
            System.out.println("Button wurde entfernt");
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
- Implementierung von Protokollierung oder Überwachung
- Koordination mehrerer Komponenten
- Aufräumen externer Ressourcen

Für die Ausführung von Code, nachdem eine Komponente an das DOM angefügt wurde, siehe `whenAttached()` im [Leitfaden Komponenten zusammensetzen](/docs/building-ui/composing-components).

## Benutzerdaten {#user-data}

Komponenten können beliebige serverseitige Daten über `setUserData()` und `getUserData()` tragen. Beide Methoden benötigen einen Schlüssel, um die Daten zu identifizieren. Dies ist nützlich, wenn Sie Domänenobjekte oder einen Kontext mit einer Komponente verknüpfen müssen, ohne eine separate Lookup-Struktur zu verwalten.

```java
Button button = new Button("Verarbeiten");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Da Benutzerdaten nie an den Client gesendet werden, können Sie sicher sensible Informationen oder große Objekte speichern, ohne den Netzwerkverkehr zu beeinträchtigen.
