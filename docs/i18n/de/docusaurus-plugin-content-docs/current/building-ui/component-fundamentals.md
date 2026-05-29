---
sidebar_position: 2
title: Understanding Components
_i18n_hash: 9236dac850f1e56f91cbcada9b6d8921
---
<JavadocLink type="foundation" location="com/webforj/component/Component" top='true'/> 

Bevor Sie benutzerdefinierte Komponenten in webforJ erstellen, ist es wichtig, die zugrunde liegende Architektur zu verstehen, die bestimmt, wie Komponenten funktionieren. Dieser Artikel erklärt die Komponentenhierarchie, die Identität von Komponenten, Lebenszykluskonzepte und wie Concern-Schnittstellen Komponentenfähigkeiten bereitstellen.

## Verständnis der Komponentenhierarchie {#understanding-the-component-hierarchy}

webforJ organisiert die Komponenten in eine Hierarchie mit zwei Gruppen: internen Klassen des Frameworks, die Sie niemals erweitern sollten, und Klassen, die speziell für den Bau von benutzerdefinierten Komponenten entwickelt wurden. In diesem Abschnitt wird erklärt, warum webforJ Komposition über Vererbung verwendet und welche Funktionen jede Ebene der Hierarchie bereitstellt.

### Warum Komposition anstelle von Erweiterung? {#why-composition-instead-of-extension}

In webforJ sind integrierte Komponenten wie [`Button`](../components/button) und [`TextField`](../components/fields/textfield) finale Klassen – Sie können sie nicht erweitern:

```java
// Das wird nicht in webforJ funktionieren
public class MyButton extends Button {
  // Button ist final - kann nicht erweitert werden 
}
```

webforJ verwendet **Komposition über Vererbung**. Anstatt vorhandene Komponenten zu erweitern, erstellen Sie eine Klasse, die `Composite` erweitert und Komponenten darin kombiniert. `Composite` fungiert als Container, der eine einzelne Komponente (die gebundene Komponente) umschließt und es Ihnen ermöglicht, eigene Komponenten und Verhaltensweisen hinzuzufügen.

```java
public class SearchBar extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private TextField searchField;
  private Button searchButton;
  
  public SearchBar() {
    searchField = new TextField("Suche");
    searchButton = new Button("Los");
    
    self.setDirection(FlexDirection.ROW)
      .add(searchField, searchButton);
  }
}
```

### Warum Sie integrierte Komponenten nicht erweitern können {#why-you-cant-extend-built-in-components}

webforJ-Komponenten sind als final gekennzeichnet, um die Integrität der zugrunde liegenden clientseitigen Webkomponente aufrechtzuerhalten. Das Erweitern von webforJ-Komponentenklassen würde Kontrolle über die zugrunde liegende Webkomponente gewähren, was zu unbeabsichtigten Konsequenzen führen und die Konsistenz und Vorhersagbarkeit des Verhaltens der Komponenten beeinträchtigen könnte.

Für eine detaillierte Erklärung siehe [Final Classes and Extension Restrictions](https://docs.webforj.com/docs/architecture/controls-components#final-classes-and-extension-restrictions) in der Architektur-Dokumentation.

### Die Komponentenhierarchie {#the-component-hierarchy}

<div style={{textAlign: 'center'}}>
```mermaid
graph TD
  A[Komponente<br/><small>Abstrakte Basis - Framework intern</small>]
  
  A --> B[DwcComponent<br/><small>Integrierte webforJ-Komponenten</small>]
  A --> C[Composite<br/><small>Kombinieren von webforJ-Komponenten</small>]
  
  B --> E[Button, TextField,<br/>DateField, ComboBox]
  
  C --> D[ElementComposite<br/><small>Umhüllen von Webkomponenten</small>]
  D --> F[ElementCompositeContainer<br/><small>Komponenten mit Slots</small>]

  classDef internal stroke-dasharray:6 4,stroke-width:1px
  classDef primary stroke-width:3px
  classDef secondary stroke-width:2px,stroke-dasharray:2 2
  class A,B,E internal
  class C primary
  class D,F secondary
```
</div>

Klassen für Entwickler (verwenden Sie diese):
- `Composite`
- `ElementComposite`
- `ElementCompositeContainer`

Interne Framework-Klassen (niemals direkt erweitern):
- `Component`
- `DwcComponent`

:::warning[Niemals `Component` oder `DwcComponent` direkt erweitern]
Erweitern Sie niemals `Component` oder `DwcComponent` direkt. Alle integrierten Komponenten sind final. Verwenden Sie immer Kompositionsmuster mit `Composite` oder `ElementComposite`.

Der Versuch, `DwcComponent` zu erweitern, führt zu einer Laufzeitausnahme.
:::

## Concern-Schnittstellen {#concern-interfaces}

Concern-Schnittstellen sind Java-Schnittstellen, die spezifische Fähigkeiten für Ihre Komponenten bereitstellen. Jede Schnittstelle fügt eine Reihe verwandter Methoden hinzu. Zum Beispiel fügt `HasSize` Methoden zur Steuerung von Breite und Höhe hinzu, während `HasFocus` Methoden zum Verwalten des Fokusstatus hinzufügt.

Wenn Sie eine Concern-Schnittstelle in Ihrer Komponente implementieren, erhalten Sie Zugriff auf diese Fähigkeiten, ohne Implementierungscode schreiben zu müssen. Die Schnittstelle bietet Standardimplementierungen, die automatisch funktionieren.

Die Implementierung von Concern-Schnittstellen gibt Ihren benutzerdefinierten Komponenten dieselben APIs wie integrierten webforJ-Komponenten:

```java
// Implementieren Sie HasSize, um Breiten- / Höhenmethoden automatisch zu erhalten
public class SizedCard extends Composite<Div> implements HasSize<SizedCard> {
  private final Div self = getBoundComponent();
  
  public SizedCard() {
    self.setText("Inhalt der Karte");
  }
  
  // Keine Notwendigkeit, diese zu implementieren - Sie erhalten sie kostenlos:
  // setWidth(), setHeight(), setSize()
}

// Verwenden Sie es wie jede webforJ-Komponente
SizedCard card = new SizedCard();
card.setWidth("300px")
  .setHeight("200px");
```

Die Composite leitet diese Aufrufe automatisch an das zugrunde liegende `Div` weiter. Kein zusätzlicher Code erforderlich.

### Erscheinungsbild {#concern-interfaces-appearance}

Diese Schnittstellen steuern die visuelle Präsentation einer Komponente, einschließlich ihrer Abmessungen, Sichtbarkeit, Stil und Thema.

| Schnittstelle | Beschreibung |
|---|---|
| `HasSize` | Steuert Breite und Höhe, einschließlich minimaler und maximaler Einschränkungen. Erweiterungen von `HasWidth`, `HasHeight` und ihren Min/Max-Varianten. |
| `HasVisibility` | Zeigt oder versteckt die Komponente, ohne sie aus dem Layout zu entfernen. |
| `HasClassName` | Verwaltet CSS-Klassennamen im Wurzelelement der Komponente. |
| `HasStyle` | Wendet Inline-CSS-Stile an und entfernt sie. |
| `HasHorizontalAlignment` | Steuert, wie Inhalte horizontal innerhalb der Komponente ausgerichtet sind. |
| `HasExpanse` | Setzt die Größenvariante der Komponente mithilfe der standardmäßigen Expansions-Tokens (`XSMALL` bis `XLARGE`). |
| `HasTheme` | Wendet eine Themenvariante wie `DEFAULT`, `PRIMARY` oder `DANGER` an. |
| `HasPrefixAndSuffix` | Fügt Komponenten zum Präfix- oder Suffixslot innerhalb der Komponente hinzu. |

### Inhalt {#concern-interfaces-content}

Diese Schnittstellen verwalten, was eine Komponente anzeigt, einschließlich Text, HTML, Labels, Hinweise und anderer beschreibender Inhalte.

| Schnittstelle | Beschreibung |
|---|---|
| `HasText` | Setzt und ruft den einfachen Textinhalt der Komponente ab. |
| `HasHtml` | Setzt und ruft das innere HTML der Komponente ab. |
| `HasLabel` | Fügt ein beschreibendes Label hinzu, das mit der Komponente verknüpft ist, das für die Barrierefreiheit verwendet wird. |
| `HasHelperText` | Zeigt sekundären Hinweistext unterhalb der Komponente an. |
| `HasPlaceholder` | Setzt Platzhaltertext, der angezeigt wird, wenn die Komponente keinen Wert hat. |
| `HasTooltip` | Hängt ein Tooltip an, das beim Hover erscheint. |

### Zustand {#concern-interfaces-state}

Diese Schnittstellen steuern den interaktiven Zustand einer Komponente, einschließlich ob sie aktiviert, bearbeitbar, erforderlich oder beim Laden fokussiert ist.

| Schnittstelle | Beschreibung |
|---|---|
| `HasEnablement` | Aktiviert oder deaktiviert die Komponente. |
| `HasReadOnly` | Versetzt die Komponente in einen schreibgeschützten Zustand, in dem der Wert sichtbar, aber nicht geändert werden kann. |
| `HasRequired` | Kennzeichnet die Komponente als erforderlich, typischerweise für die Formularvalidierung. |
| `HasAutoFocus` | Verschiebt den Fokus automatisch auf die Komponente, wenn die Seite geladen wird. |

### Fokus {#concern-interfaces-focus}

Diese Schnittstellen verwalten, wie eine Komponente den Fokus über die Tastatur erhält und darauf reagiert.

| Schnittstelle | Beschreibung |
|---|---|
| `HasFocus` | Verwalten des Fokusstatus und ob die Komponente den Fokus erhalten kann. |
| `HasFocusStatus` | Überprüft, ob die Komponente derzeit den Fokus hat. Erfordert einen Round-Trip zum Client. |
| `HasHighlightOnFocus` | Steuert, ob der Inhalt der Komponente hervorgehoben wird, wenn sie den Fokus erhält, und wie (`KEY`, `MOUSE`, `KEY_MOUSE`, `ALL` usw.). |

### Eingabebeschränkungen {#concern-interfaces-input-constraints}

Diese Schnittstellen definieren, welche Werte eine Komponente akzeptiert, einschließlich des aktuellen Wertes, erlaubter Bereiche, Längenlimits, Formatierungsmasken und lokal spezifischem Verhalten.

| Schnittstelle | Beschreibung |
|---|---|
| `HasValue` | Ruft den aktuellen Wert der Komponente ab und setzt ihn. |
| `HasMin` | Setzt einen minimal zulässigen Wert. |
| `HasMax` | Setzt einen maximal zulässigen Wert. |
| `HasStep` | Setzt die Schrittgröße für numerische oder Bereichseingaben. |
| `HasPattern` | Wendet ein reguläres Ausdrucksmuster an, um akzeptierte Eingaben einzuschränken. |
| `HasMinLength` | Setzt die minimale Anzahl von Zeichen, die im Wert der Komponente erforderlich sind. |
| `HasMaxLength` | Setzt die maximale Anzahl von Zeichen, die im Wert der Komponente zulässig sind. |
| `HasMask` | Wendet eine Formatmaske auf die Eingabe an. Wird von maskierten Feldkomponenten verwendet. |
| `HasTypingMode` | Steuert, ob eingegebene Zeichen eingefügt oder vorhandene Zeichen überschreiben (`INSERT` oder `OVERWRITE`). Wird von maskierten Feldern und `TextArea` verwendet. |
| `HasRestoreValue` | Definiert einen Wert, auf den die Komponente zurückgesetzt wird, wenn der Benutzer Escape drückt oder `restoreValue()` aufruft. Wird von maskierten Feldern verwendet. |
| `HasLocale` | Speichert eine komponentenspezifische Lokalisierung für lokalsensitive Formatierungen. Wird von maskierten Daten- und Zeitfeldern verwendet. |
| `HasPredictedText` | Setzt einen vorhergesagten oder automatischen Textwert. Wird von `TextArea` verwendet, um Inline-Vorschläge zu unterstützen. |

### Validierung {#concern-interfaces-validation}

Diese Schnittstellen fügen clientseitiges Validierungsverhalten hinzu, einschließlich der Kennzeichnung von Komponenten als ungültig, der Anzeige von Fehlermeldungen und der Steuerung, wann die Validierung ausgeführt wird.

| Schnittstelle | Beschreibung |
|---|---|
| `HasClientValidation` | Kennzeichnet eine Komponente als ungültig, setzt die Fehlermeldung und hängt einen clientseitigen Validator an. |
| `HasClientAutoValidation` | Steuert, ob die Komponente automatisch validiert, während der Benutzer eingibt. |
| `HasClientAutoValidationOnLoad` | Steuert, ob die Komponente beim ersten Laden validiert. |
| `HasClientValidationStyle` | Steuert, wie Validierungsnachrichten angezeigt werden: `INLINE` (unterhalb der Komponente) oder `POPOVER`. |

### DOM-Zugriff {#concern-interfaces-dom-access}

Diese Schnittstellen bieten Low-Level-Zugriff auf das zugrunde liegende HTML-Element der Komponente und clientseitige Eigenschaften.

| Schnittstelle | Beschreibung |
|---|---|
| `HasAttribute` | Liest und schreibt beliebige HTML-Attribute auf dem Element der Komponente. |
| `HasProperty` | Liest und schreibt DWC-Komponenten-Eigenschaften direkt auf dem Client-Element. |

### i18n {#concern-interfaces-i18n}

Diese Schnittstelle bietet Unterstützung für Übersetzungen für Komponenten, die lokalisierten Text anzeigen müssen.

| Schnittstelle | Beschreibung |
|---|---|
| `HasTranslation` | Bietet die Hilfsmethode `t()` zum Auflösen von Übersetzungsschlüsseln in lokalisierte Zeichenfolgen unter Verwendung der aktuellen App-Lokalisierung. |

:::warning
Wenn die zugrunde liegende Komponente die Schnittstellenerweiterung nicht unterstützt, erhalten Sie eine Laufzeitausnahme. Stellen Sie in diesem Fall Ihre eigene Implementierung bereit.
:::

Für eine vollständige Liste der verfügbaren Concern-Schnittstellen siehe die [webforJ JavaDoc](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/concern/package-summary.html).

## Komponentenidentifikatoren {#component-identifiers}

webforJ-Komponenten haben drei verschiedene Arten von Identifikatoren, die unterschiedlichen Zwecken dienen:

- **Serverseitige Komponenten-ID** (`getComponentId()`) - Automatisch vom Framework für die interne Verfolgung von Komponenten zugewiesen. Verwenden Sie dies, wenn Sie spezifische Komponenten abfragen oder benutzerdefinierte Komponentendatenbanken implementieren müssen.
- **Clientseitige Komponenten-ID** (`getClientComponentId()`) - Bietet Zugriff auf die zugrunde liegende Webkomponente von JavaScript aus. Verwenden Sie dies, wenn Sie native Methoden der Webkomponente aufrufen oder mit clientseitigen Bibliotheken integrieren müssen.
- **HTML `id`-Attribut** (`setAttribute("id", "...")`) - Standard DOM-Identifikator. Verwenden Sie dies zum CSS-Zielen, für Testautomatisierungs-Selektoren und zur Verknüpfung von Formularlabels mit Eingaben.

Das Verständnis dieser Unterschiede hilft Ihnen, den richtigen Identifikator für Ihren Anwendungsfall auszuwählen.

### Serverseitige Komponenten-ID {#server-side-component-id}

Jede Komponente wird automatisch mit einer serverseitigen Identifikation versehen, wenn sie erstellt wird. Diese Identifikation wird intern vom Framework zur Verfolgung der Komponenten verwendet. Rufen Sie sie mit `getComponentId()` ab:

```java
Button button = new Button("Klicken Sie mich");
String serverId = button.getComponentId();
```

Die serverseitige ID ist nützlich, wenn Sie nach spezifischen Komponenten innerhalb eines Containers abfragen oder benutzerdefinierte Logik zur Verfolgung von Komponenten implementieren müssen.

### Clientseitige Komponenten-ID {#client-side-component-id}

Die clientseitige Komponenten-ID bietet Zugriff auf die zugrunde liegende Webkomponente von JavaScript. Dadurch können Sie direkt mit der clientseitigen Komponente interagieren, wenn es erforderlich ist:

```java
Button btn = new Button("Klicken Sie mich");
btn.onClick(e -> {
  OptionDialog.showMessageDialog("Der Button wurde geklickt", "Ein Ereignis ist aufgetreten");
});

btn.whenAttached().thenAccept(e -> {
  Page.getCurrent().executeJs("objects.get('" + btn.getClientComponentId() + "').click()");
});
```

Verwenden Sie `getClientComponentId()` mit `objects.get()` in JavaScript, um auf die Instanz der Webkomponente zuzugreifen.

:::important
Die clientseitige Komponenten-ID ist nicht das HTML `id`-Attribut des DOM-Elements. Zum Festlegen von HTML-IDs für Tests oder CSS-Ziele siehe [Verwendung von Komponenten](using-components).
:::

## Überblick über den Komponentenlebenszyklus {#component-lifecycle-overview}

webforJ verwaltet den Lebenszyklus von Komponenten automatisch. Das Framework übernimmt die Erstellung, das Anhängen und das Zerstören von Komponenten, ohne dass eine manuelle Intervention erforderlich ist.

**Lebenszyklus-Hooks** sind verfügbar, wenn Sie sie benötigen:
- `onDidCreate(T container)` - Wird aufgerufen, nachdem die Komponente an das DOM angehängt wurde
- `onDidDestroy()` - Wird aufgerufen, wenn die Komponente zerstört wird

Diese Hooks sind **optional**. Verwenden Sie sie, wenn Sie:
- Ressourcen bereinigen müssen (Timer anhalten, Verbindungen schließen)
- Komponenten initialisieren müssen, die eine DOM-Anbindung erfordern
- Mit clientseitigem JavaScript integrieren müssen

In den meisten einfachen Fällen können Sie Komponenten direkt im Konstruktor initialisieren. Verwenden Sie Lebenszyklus-Hooks wie `onDidCreate()`, um Arbeiten nach Bedarf zu verzögern.
