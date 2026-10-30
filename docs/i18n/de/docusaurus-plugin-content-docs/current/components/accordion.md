---
sidebar_position: 1
title: Accordion
description: >-
  Group collapsible panels with the Accordion and AccordionPanel components to
  toggle visibility and coordinate expand or collapse behavior.
_i18n_hash: 6537c2d24bfc99dd866f27277ed4f197
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Die `Accordion`-Komponente bietet eine vertikal gestapelte Gruppe von collapsible Panels. Jedes Panel hat einen anklickbaren Header, der die Sichtbarkeit seines Inhalts toggelt. Ein `AccordionPanel` kann als eigenständiger Offenlegungsbereich verwendet oder innerhalb eines `Accordion` gruppiert werden, um das Expandieren und Kollabieren über mehrere Panels zu koordinieren.

<!-- INTRO_END -->

:::tip Wann man ein Accordion verwenden sollte
Accordions eignen sich gut für FAQs, Einstellungsseiten und schrittweise Abläufe, bei denen das gleichzeitige Anzeigen aller Inhalte ein überwältigendes Layout erzeugen würde. Wenn Abschnitte gleich wichtig sind und Nutzer davon profitieren, sie gleichzeitig zu sehen, ziehen Sie stattdessen [Tabs](/docs/components/tabbedpane) in Betracht.
:::

## `AccordionPanel` <DocChip chip='name' label="dwc-accordion-panel" /> {#accordion-panel}

`AccordionPanel` ist der grundlegende Baustein des Accordion-Systems. Übergeben Sie einen Label-String an den Konstruktor, um den Header-Text festzulegen, und fügen Sie dann untergeordnete Komponenten hinzu, um den Inhalt des Körpers zu füllen. Ein Panel funktioniert eigenständig, ohne dass eine umgebende `Accordion`-Gruppe erforderlich ist, was es zu einem nützlichen, leichten Offenlegungs-Widget macht, wenn Sie nur einen einzelnen collapsible Abschnitt benötigen. Der Konstruktor ohne Argumente ist ebenfalls verfügbar, wenn Sie das Panel vollständig nach der Konstruktion konfigurieren möchten.

```java
// Nur Label - fügen Sie den Körperinhalt separat hinzu
AccordionPanel panel = new AccordionPanel("Abschnitts-Titel");
panel.add(new Paragraph("Inhalt des Körpers geht hierhin."));

// Label und Körperinhalt werden direkt im Konstruktor übergeben
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Inhalt des Körpers."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Öffnen und Schließen {#opening-and-closing}

Steuern Sie den offenen/geschlossenen Zustand programmgesteuert zu jeder Zeit. `isOpened()` ist nützlich, wenn Sie den aktuellen Zustand lesen müssen, bevor Sie entscheiden, was zu tun ist. Zum Beispiel können Sie ein Panel auf den entgegengesetzten Zustand umschalten oder bedingt andere Teile der UI ein- oder ausblenden.

```java
// Panel erweitern
panel.open();

// Panel einklappen
panel.close();

// Gibt true zurück, wenn es aktuell erweitert ist
boolean isOpen = panel.isOpened();
```

Verwenden Sie `setLabel()`, um den Header-Text nach der Konstruktion zu aktualisieren. `setText()` ist ein Alias für dieselbe Operation, sodass das Label mit dynamischen Daten synchron gehalten werden kann:

```java
panel.setLabel("Aktualisiertes Label");
```

## Accordion-Gruppen {#accordion-groups}

Das Einwickeln mehrerer `AccordionPanel`-Instanzen innerhalb eines `Accordion` erstellt eine koordinierte Gruppe. Standardmäßig verwendet die Gruppe den **einzelnen Modus**: Das Öffnen eines Panels reduziert automatisch alle anderen, sodass immer nur ein Abschnitt sichtbar ist. Dieses Standardverhalten ist beabsichtigt, da es den Benutzer auf ein Stück Inhalt fokussiert und verhindert, dass die Seite visuell überwältigend wird, wenn Panels umfangreiche Körperinhalte haben.

Panels werden unabhängig konstruiert und an das `Accordion` übergeben, sodass Sie jedes einzelne konfigurieren können, bevor Sie sie gruppieren. Mehrere separate `Accordion`-Instanzen können auch auf derselben Seite existieren – jede Gruppe verwaltet ihren eigenen Zustand unabhängig, sodass das Erweitern eines Panels in einer Gruppe keine Auswirkungen auf eine andere hat.

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

Der Mehrfachmodus erlaubt es, dass beliebig viele Panels gleichzeitig geöffnet bleiben. Dies ist nützlich, wenn Benutzer den Inhalt mehrerer Abschnitte gleichzeitig vergleichen müssen oder wenn jedes Panel kurz genug ist, dass das gleichzeitige Öffnen mehrerer kein überladenes Layout erzeugt.

```java
accordion.setMultiple(true);
```

Bei aktivem Mehrfachmodus können alle Panels in der Gruppe gleichzeitig mit Hilfe der Bulk-Methoden erweitert oder eingeklapt werden:

```java
// Jedes Panel in der Gruppe erweitern
accordion.openAll();

// Jedes Panel in der Gruppe einklappen
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
`openAll()` ist nur verfügbar, wenn der Mehrfachmodus aktiviert ist. Sein Aufruf im Einzelmodus hat keine Auswirkungen. `closeAll()` funktioniert in beiden Modi.
:::

<!-- vale off -->
## Deaktivierter Zustand {#disabled-state}
<!-- vale on -->

Einzelne Panels können deaktiviert werden, um die Benutzerinteraktion zu verhindern, während sie weiterhin sichtbar bleiben. Dies ist während Ladezuständen oder wenn bestimmte Abschnitte bedingt nicht verfügbar sind, hilfreich, da die Panelstruktur angezeigt wird, ohne eine vorzeitige Interaktion zuzulassen. Ein deaktiviertes Panel, das bereits geöffnet war, bleibt erweitert, aber sein Header kann nicht mehr angeklickt werden, um es einzuklappen. Das Deaktivieren der `Accordion`-Gruppe wendet den deaktivierten Zustand auf alle enthaltenen Panels gleichzeitig an, sodass Sie nicht jedes Panel individuell durchlaufen müssen.

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

## Anpassen von Panels {#customizing-panels}

Über Labels und grundlegendes Öffnen/Schließen hinaus unterstützt jedes `AccordionPanel` eine reichhaltigere Anpassung sowohl seines Header-Inhalts als auch des Expand-/Kollapsicons.

### Benutzerdefinierter Header {#custom-header}

Der Header eines Panels zeigt standardmäßig sein Label als einfachen Text an. Verwenden Sie `addToHeader()`, um diesen Text durch eine beliebige Komponente oder eine Kombination von Komponenten zu ersetzen, sodass es einfach wird, Icons, Abzeichen, Statusanzeigen oder andere reichhaltige Markup neben dem Panel-Label einzufügen. Dies ist besonders nützlich in Dashboards oder Einstellungs-Panels, wo jeder Abschnittsheader zusätzlichen Kontext auf einen Blick vermitteln muss, wie etwa eine Artikelanzahl, ein Warnabzeichen oder einen Abschlussstatus, ohne dass der Benutzer das Panel zuerst erweitern muss.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Benutzerdefinierter Header mit Icon"));
panel.addToHeader(headerContent);
```

:::info Label-Ersetzung
Inhalt, der über `addToHeader()` hinzugefügt wird, ersetzt den standardmäßigen Label-Text vollständig. `setLabel()` und `setText()` funktionieren weiterhin zusammen mit `addToHeader()`, aber da der Header-Slot visuelle Priorität hat, wird der Label-Text nicht angezeigt, es sei denn, Sie fügen ihn ausdrücklich in Ihren geslotet Inhalt ein.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Benutzerdefiniertes Icon {#custom-icon}

Der Expand-/Kollaps-Indikator ist standardmäßig ein Chevron, das sowohl im geöffneten als auch im geschlossenen Zustand sichtbar ist. `setIcon()` ersetzt es durch jede [`Icon`](/docs/components/icon)-Komponente, die nützlich für gebrandete Icons oder wenn eine andere visuelle Metapher besser zum Inhalt passt. Das Übergeben von `null` stellt das Standard-Chevron wieder her. `getIcon()` gibt das aktuell gesetzte Icon zurück oder `null`, wenn das Standard-Chevron verwendet wird.

```java
// Ersetzen des standardmäßigen Chevrons durch ein Plus-Icon
panel.setIcon(FeatherIcon.PLUS.create());

// Wiederherstellen des standardmäßigen Chevrons
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java']}
height='200px'
/>
<!-- vale on -->

## Verschachtelte Accordions {#nested-accordions}

Accordions können in anderen Accordion-Panels verschachtelt werden, was nützlich ist, um hierarchische Inhalte wie kategorisierte Einstellungen oder mehrstufige Navigation darzustellen. Fügen Sie ein inneres `Accordion` zu einem äußeren `AccordionPanel` als jede andere untergeordnete Komponente hinzu. Halten Sie die Verschachtelung flach. Eine oder zwei Ebenen sind in der Regel ausreichend. Tiefere Hierarchien sind oft schwerer zu navigieren und signalisieren häufig, dass die Inhaltsstruktur selbst überdacht werden muss.

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

`AccordionPanel` löst Ereignisse in jeder Phase des Toggle-Lebenszyklus aus. Die drei Ereignistypen decken verschiedene Momente ab, wählen Sie also basierend darauf, wann Ihre Logik ausgeführt werden muss:

| Ereignis | Wird ausgelöst |
|-------|-------|
| `AccordionPanelToggleEvent` | Bevor sich der Zustand ändert |
| `AccordionPanelOpenEvent` | Nachdem das Panel vollständig geöffnet ist |
| `AccordionPanelCloseEvent` | Nachdem das Panel vollständig geschlossen ist |

```java
panel.onToggle(e -> {
    // Wird ausgelöst, bevor das Panel den Zustand ändert.
    // e.isOpened() spiegelt den Zustand wider, in den gewechselt wird, nicht den aktuellen Zustand.
    String direction = e.isOpened() ? "öffnen" : "schließen";
});

panel.onOpen(e -> {
    // Wird ausgelöst, nachdem das Panel vollständig geöffnet ist – gut für das Lazy-Loading von Inhalten.
});

panel.onClose(e -> {
    // Wird ausgelöst, nachdem das Panel vollständig geschlossen ist – gut für Bereinigungen oder Zusammenfassungsaktualisierungen.
});
```

## Styling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
