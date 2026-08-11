---
sidebar_position: 3
title: Using Components
description: >-
  Configure webforJ components in Java by setting text, attributes, IDs, inline
  styles, and CSS classes that drive appearance and behavior.
sidebar_class_name: new-content
_i18n_hash: 046749107d0e78ccfaab4017d4e374d1
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/>

Komponenten sind die Bausteine von webforJ-Anwendungen. Egal, ob Sie integrierte Komponenten wie `Button` und `TextField` verwenden oder mit benutzerdefinierten Komponenten arbeiten, die von Ihrem Team bereitgestellt werden, die Interaktion mit ihnen folgt demselben konsistenten Modell: Sie konfigurieren Eigenschaften, verwalten den Zustand und setzen Komponenten in Layouts zusammen.

Dieser Leitfaden konzentriert sich auf die alltäglichen Vorgänge: nicht auf die Interna, wie Komponenten funktionieren, sondern darauf, wie man sie praktisch einsetzt.

## Komponenteneigenschaften {#component-properties}

Jede Komponente bietet Eigenschaften, die ihren Inhalt, ihr Erscheinungsbild und ihr Verhalten steuern. Die meisten davon haben spezifische, typisierte Java-Methoden (`setText()`, `setTheme()`, `setExpanse()` usw.), was die primäre Möglichkeit darstellt, wie Sie Komponenten in webforJ konfigurieren. Die folgenden Abschnitte behandeln die Eigenschaften und Methoden, die allgemein für verschiedene Komponententypen gelten.

### Textinhalt {#text-content}

Die Methode `setText()` legt den sichtbaren Text einer Komponente als wörtliche Zeichen fest, wie z.B. die Beschriftung eines `Button` oder den Inhalt eines `Label`. Bei Eingabekomponenten wie `TextField` verwenden Sie stattdessen `setValue()`, um den aktuellen Wert des Feldes festzulegen.

```java
Button button = new Button();
button.setText("Klick mich");

Label label = new Label();
label.setText("Status: bereit");

TextField field = new TextField();
field.setValue("Anfangswert");
```

Markup, das mit `setText()` geschrieben wird, erscheint als diese Zeichen und wird nie ausgeführt, was verhindert, dass Texte, die von Benutzereingaben oder externen Daten stammen, als aktives Markup interpretiert werden.

```java
// Wird als wörtliche Zeichen "<b>Status: bereit</b>" angezeigt
component.setText("<b>Status: bereit</b>");
```

:::note Verwendung des `<html>`-Tags
Frühere Versionen von webforJ behandelten einen in `<html>` eingekapselten Wert, der an `setText()` übergeben wurde, als HTML. Dieses Verhalten ist veraltet und wird in webforJ 27.00 entfernt.

Beim ersten Erreichen eines `<html>`-verpackten Werts in `setText()` wird eine Warnung protokolliert, die die Komponente und den Aufrufort benennt, sodass der Aufruf zu `setHtml()` verschoben werden kann.

Um den Standard von webforJ 27.00 vorzeitig zu übernehmen, setzen Sie `webforj.legacyHtmlInText` auf `false`. In einer Spring-Anwendung wird der gleiche Wert über `webforj.legacy-html-in-text` festgelegt.

```java
// webforj.legacyHtmlInText = true (Standard)
component.setText("<html><b>Status: bereit</b></html>"); // rendert fett

// webforj.legacyHtmlInText = false
component.setText("<html><b>Status: bereit</b></html>"); // zeigt die Zeichen <b>Status: bereit</b>
```
:::

### HTML-rendering {#rendering-html}

Einige Komponenten unterstützen ebenfalls `setHtml()`, wenn Sie Inline-HTML-Markup im Inhalt rendern müssen:

```java
Div container = new Div();
container.setHtml("<strong>Fetter Text</strong> und <em>kursiver Text</em>");
```

:::danger Cross-Site-Scripting (XSS)
Als Vorsichtsmaßnahme gegen [Cross-Site-Scripting (XSS)-Angriffe](/docs/security/application-security/common-threats#cross-site-scripting-xss) sollten Sie `setHtml()` nur mit Inhalten verwenden, die Sie direkt kontrollieren.
:::

### HTML-Attribute {#html-attributes}

Die meisten Konfigurationen in webforJ erfolgen über typisierte Java-Methoden und nicht über rohe HTML-Attribute. Dennoch ist `setAttribute()` nützlich, um Zugänglichkeitsattribute zu übergeben, die keine spezifische API haben:

```java
Button button = new Button("Einreichen");
button.setAttribute("aria-label", "Das Formular einreichen");
button.setAttribute("aria-describedby", "form-hint");
```

:::note Überprüfen Sie die Unterstützung von Komponenten
Nicht alle Komponenten unterstützen willkürliche Attribute. Dies hängt von der zugrunde liegenden Komponentenimplementierung ab.
:::

### Komponenten-IDs {#component-ids}

Sie können einer HTML-Element-ID einer Komponente eine ID zuweisen, indem Sie `setAttribute()` verwenden:

```java
Button submitButton = new Button("Einreichen");
submitButton.setAttribute("id", "submit-btn");

TextField emailField = new TextField("E-Mail");
emailField.setAttribute("id", "email-input");
```

DOM-IDs werden häufig für Testauswahlen und CSS-Zielsetzungen in Ihren Stylesheets verwendet.

:::tip Bevorzugen Sie Klassen für Mehr-Komponenten-Zielsetzungen
Im Gegensatz zu CSS-Klassen sollten IDs innerhalb Ihrer Anwendung eindeutig sein. Wenn Sie mehrere Komponenten ansprechen müssen, verwenden Sie stattdessen `addClassName()`.
:::

:::info Von Framework verwaltete IDs
webforJ weist auch automatisch Identifikatoren für Komponenten intern zu. Die serverseitige ID (erreichbar über `getComponentId()`) wird für die Framework-Nachverfolgung verwendet, während die clientseitige ID (erreichbar über `getClientComponentId()`) für die Kommunikation zwischen Client und Server verwendet wird. Diese sind getrennt von dem DOM-`id`-Attribut, das Sie mit `setAttribute()` festlegen.
:::

### Styling {#styling}

Drei Methoden decken die meisten Stylingbedürfnisse ab: `setStyle()` für individuelle CSS-Eigenschaftswerte sowie `addClassName()` und `removeClassName()` zum Anwenden oder Entfernen von CSS-Klassen, die in Ihren Stylesheets definiert sind. Verwenden Sie `setStyle()` für geringfügige oder einmalige Stylinganpassungen und CSS-Klassen für größere oder wiederverwendbare Styles.

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

Über den Inhalt und das Aussehen hinaus haben Komponenten Zustandsattribute, die bestimmen, ob sie sichtbar sind und ob sie auf Benutzerinteraktionen reagieren. Die beiden am häufigsten verwendeten sind `setVisible()` und `setEnabled()`.

`setVisible()` steuert, ob die Komponente überhaupt in der Benutzeroberfläche angezeigt wird. `setEnabled()` steuert, ob sie Eingaben oder Interaktionen akzeptiert, während sie sichtbar bleibt. In den meisten Fällen ist das Deaktivieren vorzuziehen als das Ausblenden: Ein deaktivierter Button kommuniziert immer noch, dass eine Aktion existiert, aber momentan nicht verfügbar ist, was weniger verwirrend ist, als wenn er erscheint und wieder verschwindet.

```java
// Eine zusätzliche Eingabe anzeigen, wenn eine Checkbox aktiviert wird
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

:::warning Deaktiviert und verborgen sind keine Sicherheit
`setVisible(false)` und `setEnabled(false)` beeinflussen nur die Benutzeroberfläche. Sie verhindern nicht, dass ein entschlossener Benutzer die zugrunde liegende Aktion über den Browser oder eine angepasste Anfrage aufruft, daher sollten Sie sich niemals darauf verlassen, um sensible Operationen zu schützen. Enforce always access control on the server. See [Disabled and hidden aren't security](/docs/security/application-security/production-hardening#disabled-and-hidden-arent-security) for more details.
:::

Das folgende Anmeldeformular zeigt `setEnabled()` in der Praxis. Der Anmelde-Button bleibt deaktiviert, bis beide Felder Inhalt haben, was dem Benutzer klar macht, dass Eingaben erforderlich sind, bevor er fortfahren kann:

<ComponentDemo
path='/webforj/conditionalstate'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ConditionalStateView.java',
  'src/main/frontend/usingcomponents/conditionalstate.css',
]}
height='450px'
/>

## Arbeiten mit Containern {#working-with-containers}

In webforJ wird das Layout von Containern verwaltet, die Komponenten halten und steuern, wie sie angeordnet sind. Sie positionieren untergeordnete Komponenten nicht manuell; stattdessen fügen Sie sie einem Container hinzu und konfigurieren die Layout-Eigenschaften dieses Containers.

### Komponenten hinzufügen {#adding-components}

Alle Container bieten eine `add()`-Methode. Sie können Komponenten einzeln oder alle auf einmal übergeben:

```java
FlexLayout container = new FlexLayout();

container.add(new Button("Klick mich"));

TextField nameField = new TextField("Name");
TextField emailField = new TextField("E-Mail");
Button submitButton = new Button("Einreichen");

container.add(nameField, emailField, submitButton);
```

### Layout-Optionen {#layout-options}

`FlexLayout` ist der primäre Layout-Container in webforJ und deckt die Mehrheit der Anwendungsfälle ab: Reihen, Spalten, Ausrichtung, Abstände und Zeilenumbrüche. Für komplexere Anordnungen wie CSS Grid oder benutzerdefinierte Positionierungen können Sie CSS direkt über `setStyle()` oder `addClassName()` auf jedem Container anwenden. Siehe die [FlexLayout](/docs/components/flex-layout) Dokumentation für die vollständige Palette von Layout-Optionen.

### Abschnitte anzeigen und ausblenden {#showing-hiding-sections}

Eine häufige Verwendung von `setVisible()` in Containern ist das Anzeigen zusätzlicher Benutzeroberflächen, nur wenn sie relevant sind. Dies hält die Benutzeroberfläche fokussiert und reduziert visuelles Durcheinander. Anstatt zu einer neuen Ansicht zu navigieren, können Sie einen Abschnitt des aktuellen Layouts direkt als Reaktion auf Benutzereingaben anzeigen.

Das folgende Einstellungen-Panel zeigt dies: grundlegende Benachrichtigungseinstellungen sind immer sichtbar, und ein Abschnitt mit erweiterten Optionen erscheint nur, wenn der Benutzer danach fragt. Der Speichern-Button wird sofort aktiviert, sobald eine Einstellung geändert wird:

<ComponentDemo
path='/webforj/progressivedisclosure'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/ProgressiveDisclosureView.java',
  'src/main/frontend/usingcomponents/progressivedisclosure.css',
]}
height='450px'
/>

### Container-Management {#container-management}

Verwenden Sie `remove()` und `removeAll()`, um Komponenten zur Laufzeit aus einem Container zu entfernen:

```java
FlexLayout container = new FlexLayout();
Button tempButton = new Button("Temporär");

container.add(tempButton);
container.remove(tempButton);

container.removeAll();
```

Dies ist nützlich, wenn Sie den Inhalt vollständig ersetzen müssen, z. B. beim Ersetzen eines Ladeindikators durch die geladenen Daten.

## Formularvalidierung {#form-validation}

Die Koordination mehrerer Komponenten, um eine Sendeaktion zu steuern, ist ein häufiges Muster in webforJ-UIs. Die grundlegende Idee ist, dass jedes Eingabefeld einen Listener registriert, und wann immer sich ein Wert ändert, bewertet das Formular erneut, ob alle Kriterien erfüllt sind und aktualisiert den Sende-Button entsprechend.

Das folgende Beispiel verkabelt dies manuell, damit Sie sehen können, wie der Komponentenstatus und Ereignis-Listener zusammenarbeiten. Es ist nicht der empfohlene Ansatz für echte Formulare: die manuelle Listener-Logik wird schwer zu warten, während Formulare wachsen, und sie verbindet Ihre Komponenten nicht mit einem zugrunde liegenden Datenmodell.

:::tip Verwenden Sie Datenbindung für die Formularvalidierung
Für Produktionsformulare verwenden Sie [Datenbindung](/docs/data-binding/overview). Es umfasst Validierung, bidirektionale Synchronisierung zwischen Komponenten und Ihrem Modell und Werttransformationen durch `BindingContext`. Das hier gezeigte manuelle Muster dient nur zur Veranschaulichung.
:::

In diesem Kontaktformular darf das Namensfeld nicht leer sein, die E-Mail muss ein `@`-Symbol enthalten und die Nachricht muss mindestens 10 Zeichen lang sein:

<ComponentDemo
path='/webforj/formvalidation'
files={[
  'src/main/java/com/webforj/samples/views/usingcomponents/FormValidationView.java',
  'src/main/frontend/usingcomponents/formvalidation.css',
]}
height='500px'
/>

## Dynamische Inhaltsaktualisierungen {#dynamic-content-updates}

Komponenten müssen nach ihrer Erstellung nicht in einem festen Zustand bleiben. Sie können Text aktualisieren, CSS-Klassen ändern und den Status aktivieren oder deaktivieren, sobald App-Ereignisse eintreten. Ein häufiges Beispiel ist das Bereitstellen von Feedback während einer lang laufenden Aufgabe:

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

Das Deaktivieren des Buttons, während die Aufgabe läuft, verhindert doppelte Einreichungen, und das Aktualisieren des Labels informiert den Benutzer darüber, was passiert.

## `ComponentLifecycleObserver` {#componentlifecycleobserver}

Das `ComponentLifecycleObserver`-Interface ermöglicht es Ihnen, Ereignisse im Lebenszyklus von Komponenten von außerhalb der Komponente selbst zu beobachten. Dies ist nützlich, wenn Sie auf die Erstellung oder Zerstörung einer Komponente reagieren müssen, ohne ihre Implementierung zu ändern. Zum Beispiel könnten Sie es verwenden, um ein Register aktiver Komponenten zu führen oder externe Ressourcen freizugeben, wenn eine Komponente entfernt wird.

### Grundlegende Verwendung {#basic-usage}

Rufen Sie `addLifecycleObserver()` für jede Komponente auf, um eine Rückruffunktion zu registrieren. Der Rückruf erhält die Komponente und das Lebenszyklusereignis:

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

Eine Koordinator-Klasse, die eine Gruppe verwandter Komponenten verwaltet, kann denselben Ansatz verwenden, um ihre interne Liste aktuell zu halten:

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

Für die Ausführung von Code, nachdem eine Komponente an den DOM angefügt wurde, siehe `whenAttached()` im [Komponenten Composing](/docs/building-ui/composing-components) Leitfaden.

## Benutzerdaten {#user-data}

Komponenten können beliebige serverseitige Daten über `setUserData()` und `getUserData()` übertragen. Beide Methoden benötigen einen Schlüssel, um die Daten zu identifizieren. Dies ist nützlich, wenn Sie Domainobjekte oder Kontexte mit einer Komponente verknüpfen müssen, ohne eine separate Lookup-Struktur zu verwalten.

```java
Button button = new Button("Verarbeiten");
button.setUserData("context", new ProcessingContext(userId, taskId));

button.onClick(event -> {
    ProcessingContext context = (ProcessingContext) button.getUserData("context");
    processTask(context.getUserId(), context.getTaskId());
});
```

Da Benutzerdaten niemals an den Client gesendet werden, können Sie sensible Informationen oder große Objekte sicher speichern, ohne den Netzwerkverkehr zu beeinträchtigen.
