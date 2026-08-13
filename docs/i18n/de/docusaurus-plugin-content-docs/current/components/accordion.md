---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: b11e2d2ef8854f757454635c984da1d6
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Die `Accordion`-Komponente bietet eine vertikal gestapelte Menge von zusammenklappbaren Panels. Jedes Panel hat einen klickbaren Header, der die Sichtbarkeit seines Inhalts umschaltet. Ein `AccordionPanel` kann als eigenständiger Offenlegungsteil verwendet oder innerhalb eines `Accordion` gruppiert werden, um das Erweiterungs- und Zusammenklappverhalten mehrerer Panels zu koordinieren.

<!-- INTRO_END -->

:::tip Wann man ein Accordion verwenden sollte
Accordions sind geeignet für FAQs, Einstellungsseiten und Schritt-für-Schritt-Übersichten, bei denen das gleichzeitige Anzeigen aller Inhalte ein überwältigendes Layout erzeugen würde. Wenn Abschnitte gleich wichtig sind und die Benutzer davon profitieren, sie gleichzeitig zu sehen, sollten Sie stattdessen [Tabs](/docs/components/tabbedpane) in Betracht ziehen.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` ist der zentrale Baustein des Accordion-Systems. Übergeben Sie einen Label-String an den Konstruktor, um den Header-Text festzulegen, und fügen Sie dann Kind-Komponenten hinzu, um den Inhalt des Panels zu füllen. Ein Panel funktioniert eigenständig, ohne eine umgebende `Accordion`-Gruppe, was es zu einem nützlichen, leichten Offenlegungswidget macht, wenn Sie nur einen einzigen zusammenklappbaren Abschnitt benötigen. Der Konstruktor ohne Argumente ist ebenfalls verfügbar, wenn Sie das Panel ganz nach der Erstellung konfigurieren möchten.

```java
// Nur Label - Inhalt separat hinzufügen
AccordionPanel panel = new AccordionPanel("Abschnittsüberschrift");
panel.add(new Paragraph("Inhaltsbereich kommt hierhin."));

// Label und Inhaltsbereich direkt im Konstruktor übergeben
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Inhaltsbereich."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Öffnen und Schließen {#opening-and-closing}

Steuern Sie den geöffneten/geschlossenen Zustand programmgesteuert zu jeder Zeit. `isOpened()` ist nützlich, wenn Sie den aktuellen Zustand lesen müssen, bevor Sie entscheiden, was zu tun ist. Zum Beispiel könnten Sie ein Panel in den entgegengesetzten Zustand umschalten oder bedingt andere Teile der Benutzeroberfläche anzeigen oder verbergen.

```java
// Panel erweitern
panel.open();

// Panel zusammenklappen
panel.close();

// Gibt true zurück, wenn es derzeit erweitert ist
boolean isOpen = panel.isOpened();
```

Verwenden Sie `setLabel()`, um den Header-Text nach der Erstellung zu aktualisieren. `setText()` ist ein Alias für dieselbe Operation, sodass das Label mit dynamischen Daten synchronisiert werden kann:

```java
panel.setLabel("Aktualisiertes Label");
```

## Accordion-Gruppen {#accordion-groups}

Das Einwickeln mehrerer `AccordionPanel`-Instanzen in ein `Accordion` erzeugt eine koordinierte Gruppe. Standardmäßig verwendet die Gruppe den **Einzelmodus**: Das Öffnen eines Panels klappt automatisch alle anderen zusammen und lässt jeweils nur ein Abschnitt sichtbar. Dieses Standardverhalten ist absichtlich, da es den Benutzer auf ein einzelnes Element fokussiert und verhindert, dass die Seite visuell überwältigend wird, wenn Panels erheblichen Inhalt haben.

Panels werden unabhängig konstruiert und dem `Accordion` übergeben, sodass Sie jedes einzelne vor der Gruppierung konfigurieren können. Mehrere separate `Accordion`-Instanzen können ebenfalls auf derselben Seite vorhanden sein – jede Gruppe verwaltet ihren eigenen Zustand unabhängig, sodass das Erweitern eines Panels in einer Gruppe keine Auswirkungen auf eine andere hat.

```java
AccordionPanel panel1 = new AccordionPanel("Was ist webforJ?");
AccordionPanel panel2 = new AccordionPanel("Wie funktionieren gruppierte Panels?");
AccordionPanel panel3 = new AccordionPanel("Kann ich mehrere Gruppen haben?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java']}
height='400px'
/>
<!-- vale on -->

### Mehrfachmodus {#multiple-mode}

Der Mehrfachmodus ermöglicht es, dass eine beliebige Anzahl von Panels gleichzeitig geöffnet bleibt. Dies ist nützlich, wenn Benutzer den Inhalt mehrerer Abschnitte gleichzeitig vergleichen müssen oder wenn jedes Panel kurz genug ist, dass das gleichzeitige Erweitern mehrerer Panels kein überladenes Layout erzeugt.

```java
accordion.setMultiple(true);
```

Mit aktivem Mehrfachmodus können alle Panels in der Gruppe gleichzeitig mit den Massenmethoden erweitert oder zusammengeklappt werden:

```java
// Alle Panels in der Gruppe erweitern
accordion.openAll();

// Alle Panels in der Gruppe zusammenklappen
accordion.closeAll();
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java']}
height='575px'
/>
<!-- vale on -->

:::info Einschränkung im Einzelmodus
`openAll()` ist nur verfügbar, wenn der Mehrfachmodus aktiviert ist. Der Aufruf hat im Einzelmodus keine Auswirkungen. `closeAll()` funktioniert in beiden Modi.
:::

<!-- vale off -->
## Deaktivierter Zustand {#disabled-state}
<!-- vale on -->

Einzelne Panels können deaktiviert werden, um Benutzerinteraktionen zu verhindern, während sie weiterhin sichtbar bleiben. Dies ist nützlich während Ladezuständen oder wenn bestimmte Abschnitte bedingt nicht verfügbar sind, indem die Panelstruktur gezeigt wird, ohne vorzeitige Interaktionen zuzulassen. Ein deaktiviertes Panel, das bereits geöffnet war, bleibt erweitert, aber sein Header kann nicht mehr angeklickt werden, um es zusammenzuklappen. Das Deaktivieren der `Accordion`-Gruppe wendet den deaktivierten Zustand auf alle enthaltenen Panels gleichzeitig an, sodass Sie nicht durch die Panels einzeln iterieren müssen.

```java
// Ein einzelnes Panel deaktivieren
panel.setEnabled(false);

// Alle Panels in der Gruppe deaktivieren
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java']}
height='650px'
/>
<!-- vale on -->

## Anpassung von Panels {#customizing-panels}

Über Labels und das grundlegende Öffnen/Schließen hinaus unterstützt jedes `AccordionPanel` eine reichhaltigere Anpassung sowohl seines Header-Inhalts als auch des Erweiterungs-/Zusammenklappsymbols.

### Benutzerdefinierter Header {#custom-header}

Der Header eines Panels zeigt standardmäßig sein Label als einfachen Text an. Verwenden Sie `addToHeader()`, um diesen Text durch jede Komponente oder eine Kombination von Komponenten zu ersetzen, was es einfach macht, Icons, Abzeichen, Statusanzeigen oder andere reichhaltige Markups zusammen mit dem Panel-Label einzufügen. Dies ist besonders nützlich in Dashboards oder Einstellungs-Panels, in denen jeder Abschnittsheader zusätzlichen Kontext auf einen Blick vermitteln muss, wie etwa eine Artikelanzahl, ein Warnabzeichen oder einen Abschlussstatus, ohne dass der Benutzer das Panel zuerst erweitern muss.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Benutzerdefinierter Header mit Icon"));
panel.addToHeader(headerContent);
```

:::info Label-Ersetzung
Inhalte, die über `addToHeader()` hinzugefügt werden, ersetzen den Standardlabel-Text vollständig. `setLabel()` und `setText()` funktionieren weiterhin neben `addToHeader()`, aber da der Header-Bereich visuelle Priorität hat, wird der Label-Text nicht angezeigt, es sei denn, Sie fügen ihn ausdrücklich in Ihren bereitgestellten Inhalten hinzu.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Benutzerdefiniertes Symbol {#custom-icon}

Der Indikator für das Erweitern/Zusammenklappen ist standardmäßig ein Chevron, der sowohl im geöffneten als auch geschlossenen Zustand sichtbar ist. `setIcon()` ersetzt ihn durch jede [Icon]-Komponente (/docs/components/icon), die nützlich für Marken-Ikonographie oder wenn eine andere visuelle Metapher besser zum Inhalt passt. Das Übergeben von `null` stellt das Standard-Chevron wieder her. `getIcon()` gibt das derzeit gesetzte Symbol zurück oder `null`, wenn das Standard-Chevron verwendet wird.

```java
// Das Standard-Chevron durch ein Plus-Symbol ersetzen
panel.setIcon(FeatherIcon.PLUS.create());

// Das Standard-Chevron wiederherstellen
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## NESTED ACCORDIONS {#nested-accordions}

Accordions können in andere Accordion-Panels eingebettet werden, was nützlich ist, um hierarchische Inhalte wie kategorisierte Einstellungen oder mehrstufige Navigation darzustellen. Fügen Sie ein inneres `Accordion` zu einem äußeren `AccordionPanel` wie jeder anderen Kindkomponente hinzu. Halten Sie die Verschachtelung flach. Eine oder zwei Ebenen sind normalerweise ausreichend. Tiefergehende Hierarchien tendieren dazu, schwieriger zu navigieren zu sein und signalisieren oft, dass die Inhaltsstruktur selbst überdacht werden muss.

```java
AccordionPanel innerA = new AccordionPanel("Inneres Panel A");
AccordionPanel innerB = new AccordionPanel("Inneres Panel B");
Accordion innerAccordion = new Accordion(innerA, innerB);

AccordionPanel outer = new AccordionPanel("Äußeres Panel");
outer.add(innerAccordion);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionnested'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java']}
height='550px'
/>
<!-- vale on -->

## Ereignisse {#events}

`AccordionPanel` löst Ereignisse in jeder Phase des Umschaltzyklus aus. Die drei Ereignistypen decken verschiedene Zeitpunkte ab, sodass Sie wählen können, basierend auf wann Ihre Logik ausgeführt werden muss:

| Ereignis | Tritt ein |
|----------|-----------|
| `AccordionPanelToggleEvent` | Bevor der Zustand sich ändert |
| `AccordionPanelOpenEvent` | Nachdem das Panel vollständig geöffnet ist |
| `AccordionPanelCloseEvent` | Nachdem das Panel vollständig geschlossen ist |

```java
panel.onToggle(e -> {
    // Tritt ein, bevor sich das Panelzustand ändert.
    // e.isOpened() spiegelt den Zustand wider, zu dem übergegangen wird, nicht den aktuellen Zustand.
    String direction = e.isOpened() ? "öffnen" : "schließen";
});

panel.onOpen(e -> {
    // Tritt ein, nachdem das Panel vollständig geöffnet ist – gut zum Lazy-Loading von Inhalten.
});

panel.onClose(e -> {
    // Tritt ein, nachdem das Panel vollständig geschlossen ist – gut für Aufräum- oder Zusammenfassungsaktualisierungen.
});
```

## Styling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
