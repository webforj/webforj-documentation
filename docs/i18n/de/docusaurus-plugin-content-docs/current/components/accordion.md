---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 2bf90130b3a767840e2604045504ee91
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Die `Accordion`-Komponente bietet eine vertikal gestapelte Reihe von aufklappbaren Panels. Jedes Panel hat einen klickbaren Header, der die Sichtbarkeit seines Inhalts umschaltet. Ein `AccordionPanel` kann als eigenständiger Offenbarungsabschnitt verwendet werden oder in einem `Accordion` gruppiert werden, um das Verhalten des Erweiterns und Zusammenklappens über mehrere Panels hinweg zu koordinieren.

<!-- INTRO_END -->

:::tip Wann ein Accordion verwenden
Accordions eignen sich gut für FAQs, Einstellungsseiten und Schritt-für-Schritt-Flows, bei denen das gleichzeitige Offenlegen aller Inhalte ein überwältigendes Layout erzeugen würde. Wenn Abschnitte gleich wichtig sind und die Benutzer davon profitieren, sie gleichzeitig zu sehen, ziehen Sie stattdessen [Tabs](/docs/components/tabbedpane) in Betracht.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` ist der zentrale Baustein des Accordion-Systems. Übergeben Sie einen Label-String an den Konstruktor, um den Header-Text festzulegen, und fügen Sie dann untergeordnete Komponenten hinzu, um den Inhalt des Körpers zu füllen. Ein Panel funktioniert eigenständig ohne eine umgebende `Accordion`-Gruppe, wodurch es ein nützliches leichtgewichtiges Offenbarungs-Widget ist, wenn Sie nur ein einzelnes aufklappbares Element benötigen. Der Konstruktor ohne Argumente ist ebenfalls verfügbar, wenn Sie das Panel vollständig nach der Konstruktion konfigurieren möchten.

```java
// Nur Label - den Körperinhalt separat hinzufügen
AccordionPanel panel = new AccordionPanel("Abschnitt Titel");
panel.add(new Paragraph("Inhalt des Körpers kommt hierhin."));

// Label und Körperinhalt werden direkt im Konstruktor übergeben
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Inhalt des Körpers."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='550px'
/>
<!-- vale on -->

### Öffnen und Schließen {#opening-and-closing}

Steuern Sie den offenen/geschlossenen Zustand programmgesteuert zu jeder Zeit. `isOpened()` ist nützlich, wenn Sie den aktuellen Zustand lesen müssen, bevor Sie entscheiden, was zu tun ist. Zum Beispiel könnten Sie ein Panel auf den entgegengesetzten Zustand umschalten oder konditional andere Teile der Benutzeroberfläche anzeigen oder ausblenden.

```java
// Panel öffnen
panel.open();

// Panel schließen
panel.close();                    

// Gibt true zurück, wenn es derzeit geöffnet ist
boolean isOpen = panel.isOpened();
```

Verwenden Sie `setLabel()`, um den Header-Text nach der Konstruktion zu aktualisieren. `setText()` ist ein Alias für denselben Vorgang, sodass das Label mit dynamischen Daten synchron gehalten werden kann:

```java
panel.setLabel("Aktualisiertes Label");
```

## Accordion-Gruppen {#accordion-groups}

Das Einpacken mehrerer `AccordionPanel`-Instanzen in einem `Accordion` schafft eine koordinierte Gruppe. Standardmäßig verwendet die Gruppe den **Einzelmodus**: Das Öffnen eines Panels kollabiert automatisch alle anderen, sodass jeweils nur ein Abschnitt sichtbar ist. Diese Standardoption ist absichtlich, sie hält den Benutzer auf ein Stück Inhalt fokussiert und verhindert, dass die Seite mit umfangreichen Inhalten der Panels visuell überwältigend wird.

Panels werden unabhängig konstruiert und an das `Accordion` übergeben, sodass Sie jedes einzeln konfigurieren können, bevor Sie sie gruppieren. Mehrere separate `Accordion`-Instanzen können auch auf derselben Seite existieren – jede Gruppe verwaltet ihren eigenen Zustand unabhängig, sodass das Erweitern eines Panels in einer Gruppe keine Auswirkungen auf eine andere hat.

```java
AccordionPanel panel1 = new AccordionPanel("Was ist webforJ?");
AccordionPanel panel2 = new AccordionPanel("Wie funktionieren gruppierte Panels?");
AccordionPanel panel3 = new AccordionPanel("Kann ich mehrere Gruppen haben?");

Accordion accordion = new Accordion(panel1, panel2, panel3);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiongroup'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionGroupView.java'
height='400px'
/>
<!-- vale on -->

### Mehrfachmodus {#multiple-mode}

Der Mehrfachmodus erlaubt es, dass beliebig viele Panels gleichzeitig geöffnet bleiben. Dies ist nützlich, wenn Benutzer den Inhalt mehrerer Abschnitte gleichzeitig vergleichen müssen oder wenn jedes Panel kurz genug ist, dass das gleichzeitige Erweitern mehrerer Panels kein überladenes Layout erzeugt.

```java
accordion.setMultiple(true);
```

Mit aktivem Mehrfachmodus können alle Panels in der Gruppe gleichzeitig mit den Bulk-Methoden erweitert oder geschlossen werden:

```java
// Jedes Panel in der Gruppe erweitern
accordion.openAll();

// Jedes Panel in der Gruppe schließen
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='575px'
/>
<!-- vale on -->

:::info Einschränkung im Einzelmodus
`openAll()` ist nur verfügbar, wenn der Mehrfachmodus aktiviert ist. Aufrufen im Einzelmodus hat keine Auswirkungen. `closeAll()` funktioniert in beiden Modi.
:::

<!-- vale off -->
## Deaktivierter Zustand {#disabled-state}
<!-- vale on -->

Einzelne Panels können deaktiviert werden, um die Benutzerinteraktion zu verhindern, während sie weiterhin sichtbar bleiben. Dies ist während Ladezuständen oder wenn bestimmte Abschnitte bedingt nicht verfügbar sind, praktisch, da es die Panelstruktur anzeigt, ohne eine vorzeitige Interaktion zuzulassen. Ein deaktiviertes Panel, das bereits geöffnet war, bleibt erweitert, aber sein Header kann nicht mehr angeklickt werden, um es zu schließen. Das Deaktivieren der `Accordion`-Gruppe gilt für alle enthaltenen Panels auf einmal, sodass Sie die Panels nicht einzeln durchlaufen müssen.

```java
// Ein einzelnes Panel deaktivieren
panel.setEnabled(false);

// Alle Panels in der Gruppe deaktivieren
accordion.setEnabled(false);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordiondisabled'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionDisabledView.java'
height='650px'
/>
<!-- vale on -->

## Anpassen von Panels {#customizing-panels}

Über Labels und grundlegendes Öffnen/Schließen hinaus unterstützt jedes `AccordionPanel` eine reichhaltigere Anpassung sowohl des Header-Inhalts als auch des Symbols zum Erweitern/Zusammenklappen.

### Benutzerdefinierter Header {#custom-header}

Der Header eines Panels rendert sein Label standardmäßig als Klartext. Verwenden Sie `addToHeader()`, um diesen Text durch eine beliebige Komponente oder Kombination von Komponenten zu ersetzen, was es einfach macht, Icons, Abzeichen, Statusindikatoren oder andere reichhaltige Markups neben dem Panel-Label einzufügen. Dies ist besonders nützlich in Dashboards oder Einstellungs-Panels, wo jeder Abschnittsheader zusätzlichen Kontext auf einen Blick vermitteln muss, wie z.B. eine Anzahl von Elementen, ein Warnabzeichen oder einen Abschlussstatus, ohne dass der Benutzer das Panel zuerst erweitern muss.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Benutzerdefinierter Header mit Icon"));
panel.addToHeader(headerContent);
```

:::info Ersetzen des Labels
Inhalt, der über `addToHeader()` hinzugefügt wird, ersetzt den Standardtext des Labels vollständig. `setLabel()` und `setText()` funktionieren weiterhin neben `addToHeader()`, aber da der Header-Slot visuelle Priorität hat, wird der Label-Text nicht angezeigt, es sei denn, Sie fügen ihn explizit in Ihren zugewiesenen Inhalt ein.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Benutzerdefiniertes Symbol {#custom-icon}

Das Symbol für das Erweitern/Zusammenklappen ist standardmäßig ein Chevron, das sowohl im offenen als auch im geschlossenen Zustand sichtbar ist. `setIcon()` ersetzt es durch jede [`Icon`](/docs/components/icon)-Komponente, die nützlich für gebrandete Symbolik ist oder wenn eine andere visuelle Metapher besser zum Inhalt passt. Das Übergeben von `null` stellt das Standard-Chevron wieder her. `getIcon()` gibt das derzeit eingestellte Symbol zurück oder `null`, wenn das Standard-Chevron verwendet wird.

```java
// Ersetzen Sie das Standard-Chevron durch ein Plus-Symbol
panel.setIcon(FeatherIcon.PLUS.create());

// Stellen Sie das Standard-Chevron wieder her
panel.setIcon(null);
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomicon'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomIconView.java'
height='200px'
/>
<!-- vale on -->

## Verschachtelte Accordions {#nested-accordions}

Accordions können innerhalb anderer Accordion-Panels verschachtelt werden, was nützlich ist, um hierarchische Inhalte wie kategorisierte Einstellungen oder mehrstufige Navigation darzustellen. Fügen Sie ein inneres `Accordion` zu einem äußeren `AccordionPanel` als jede andere untergeordnete Komponente hinzu. Halten Sie die Verschachtelung flach. Eine oder zwei Ebenen sind normalerweise ausreichend. Tiefere Hierarchien sind oft schwer zu navigieren und signalisieren häufig, dass die Inhaltsstruktur selbst überdacht werden muss.

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
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionNestedView.java'
height='550px'
/>
<!-- vale on -->

## Ereignisse {#events}

`AccordionPanel` generiert Ereignisse in jeder Phase des Umschaltzyklus. Die drei Ereignistypen decken unterschiedliche Momente ab, wählen Sie also basierend darauf, wann Ihre Logik ausgeführt werden muss:

| Ereignis | Wird ausgelöst |
|-------|-------|
| `AccordionPanelToggleEvent` | Bevor der Zustand sich ändert |
| `AccordionPanelOpenEvent` | Nachdem das Panel vollständig geöffnet ist |
| `AccordionPanelCloseEvent` | Nachdem das Panel vollständig geschlossen ist |

```java
panel.onToggle(e -> {
    // Wird ausgelöst, bevor sich das Panelzustand ändert.
    // e.isOpened() spiegelt den Zustand wider, in den gewechselt wird, nicht den aktuellen Zustand.
    String direction = e.isOpened() ? "öffnen" : "schließen";
});

panel.onOpen(e -> {
    // Wird ausgelöst, nachdem das Panel vollständig geöffnet ist – gut zum Lazy-Loading von Inhalten.
});

panel.onClose(e -> {
    // Wird ausgelöst, nachdem das Panel vollständig geschlossen ist – gut für Aufräumarbeiten oder Aktualisierungen der Zusammenfassung.
});
```

## Styling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
