---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 99f4482faa552334ce209b3f9296f4f5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Die Komponente `Accordion` bietet ein vertikal gestapeltes Set von zusammenklappbaren Panels. Jedes Panel hat einen klickbaren Header, der die Sichtbarkeit seines Inhalts toggelt. Ein `AccordionPanel` kann als eigenständiger Enthüllungsabschnitt verwendet werden oder innerhalb eines `Accordion` gruppiert werden, um das Verhalten beim Erweitern und Zusammensetzen über mehrere Panels hinweg zu koordinieren.

<!-- INTRO_END -->

:::tip Wann man ein Accordion verwenden sollte
Accordions eignen sich gut für FAQs, Einstellungsseiten und Schritt-für-Schritt-Flows, bei denen das gleichzeitige Anzeigen aller Inhalte ein überwältigendes Layout schaffen würde. Wenn Abschnitte gleich wichtig sind und die Benutzer davon profitieren, sie gleichzeitig zu sehen, sollten stattdessen [Tabs](/docs/components/tabbedpane) in Betracht gezogen werden.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` ist der zentrale Baustein des Accordion-Systems. Übergeben Sie eine Label-String an den Konstruktor, um den Text des Headers festzulegen, und fügen Sie dann Kindkomponenten hinzu, um den Körper zu füllen. Ein Panel funktioniert eigenständig ohne eine umgebende `Accordion`-Gruppe und macht es zu einem nützlichen leichten Enthüllungs-Widget, wenn Sie nur einen einzelnen zusammenklappbaren Abschnitt benötigen. Der Konstruktor ohne Argumente ist ebenfalls verfügbar, wenn Sie das Panel vollständig nach dem Erstellen konfigurieren möchten.

```java
// Nur Label - Inhalt des Körpers separat hinzufügen
AccordionPanel panel = new AccordionPanel("Abschnittsüberschrift");
panel.add(new Paragraph("Inhalt des Körpers geht hierhin."));

// Label und Inhalt des Körpers direkt im Konstruktor übergeben
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Inhalt des Körpers."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Öffnen und Schließen {#opening-and-closing}

Steuern Sie den offenen/geschlossenen Zustand programmgesteuert zu jedem Zeitpunkt. `isOpened()` ist nützlich, wenn Sie den aktuellen Zustand lesen müssen, bevor Sie entscheiden, was zu tun ist. Zum Beispiel könnten Sie ein Panel in den gegenteiligen Zustand umschalten oder bedingt andere Teile der Benutzeroberfläche anzeigen oder ausblenden.

```java
// Panel öffnen
panel.open();

// Panel schließen
panel.close();                    

// Gibt true zurück, wenn es derzeit erweitert ist
boolean isOpen = panel.isOpened();
```

Verwenden Sie `setLabel()`, um den Header-Text nach der Erstellung zu aktualisieren. `setText()` ist ein Alias für denselben Vorgang, sodass das Label mit dynamischen Daten synchronisiert werden kann:

```java
panel.setLabel("Aktualisiertes Label");
```

## Accordion-Gruppen {#accordion-groups}

Das Einwickeln mehrerer `AccordionPanel`-Instanzen innerhalb eines `Accordion` schafft eine koordinierte Gruppe. Standardmäßig verwendet die Gruppe **Einzelmodus**: Das Öffnen eines Panels lässt automatisch alle anderen zusammenklappen und hält nur einen Abschnitt sichtbar. Dieser Standard ist absichtlich, da er den Benutzer auf ein Stück Inhalt konzentriert und verhindert, dass die Seite visuell überladen wird, wenn die Panels umfassenden Inhalt haben.

Panels werden unabhängig konstruiert und an das `Accordion` übergeben, sodass Sie jedes einzeln konfigurieren können, bevor Sie sie gruppieren. Mehrere separate `Accordion`-Instanzen können ebenfalls auf derselben Seite existieren – jede Gruppe verwaltet ihren eigenen Zustand unabhängig, sodass das Erweitern eines Panels in einer Gruppe keine Auswirkungen auf eine andere hat.

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

Im Mehrfachmodus können beliebig viele Panels gleichzeitig geöffnet bleiben. Dies ist nützlich, wenn Benutzer den Inhalt mehrerer Abschnitte gleichzeitig vergleichen müssen oder wenn jedes Panel kurz genug ist, dass das gleichzeitige Erweitern mehrerer keinen überladenen Layout erzeugt.

```java
accordion.setMultiple(true);
```

Mit aktivem Mehrfachmodus können alle Panels in der Gruppe gleichzeitig mithilfe der Bulk-Methoden erweitert oder geschlossen werden:

```java
// Jedes Panel in der Gruppe öffnen
accordion.openAll();

// Jedes Panel in der Gruppe schließen
accordion.closeAll();   
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionmultiple'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionMultipleView.java'
height='500px'
/>
<!-- vale on -->

:::info Einschränkung des Einzelmodus
`openAll()` ist nur verfügbar, wenn der Mehrfachmodus aktiviert ist. Der Aufruf in Einzelmodus hat keine Auswirkungen. `closeAll()` funktioniert in beiden Modi.
:::

<!-- vale off -->
## Deaktivierter Zustand {#disabled-state}
<!-- vale on -->

Einzelne Panels können deaktiviert werden, um die Benutzerinteraktion zu verhindern, während sie weiterhin sichtbar bleiben. Dies ist praktisch während Ladezuständen oder wenn bestimmte Abschnitte bedingt nicht verfügbar sind und die Panelstruktur angezeigt wird, ohne eine vorzeitige Interaktion zuzulassen. Ein deaktiviertes Panel, das bereits geöffnet war, bleibt erweitert, aber sein Header kann nicht mehr angeklickt werden, um es zu schließen. Das Deaktivieren der `Accordion`-Gruppe wendet den deaktivierten Zustand auf alle enthaltenen Panels gleichzeitig an, sodass Sie nicht jedes Panel einzeln durchlaufen müssen.

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
height='600px'
/>
<!-- vale on -->

## Anpassen von Panels {#customizing-panels}

Über Labels und grundlegendes Öffnen/Schließen hinaus unterstützt jedes `AccordionPanel` eine reichhaltige Anpassung sowohl seines Header-Inhalts als auch des Symbols für Erweitern/Zusammenklappen.

### Benutzerdefinierte Header {#custom-header}

Der Header eines Panels rendert sein Label standardmäßig als normalen Text. Verwenden Sie `addToHeader()`, um diesen Text durch jede Komponente oder Kombination von Komponenten zu ersetzen, und es ist einfach, Symbole, Abzeichen, Statusanzeigen oder andere reichhaltige Markups neben dem Panel-Label einzufügen. Dies ist besonders nützlich in Dashboards oder Einstellungs-Panels, in denen jeder Abschnittsheader zusätzlichen Kontext auf einen Blick vermitteln muss, wie beispielsweise eine Elementanzahl, ein Warnabzeichen oder ein Abschlussstatus, ohne dass der Benutzer das Panel zuerst erweitern muss.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Benutzerdefinierter Header mit Symbol"));
panel.addToHeader(headerContent);
```

:::info Ersetzungslabel
Inhalt, der über `addToHeader()` hinzugefügt wird, ersetzt den Standard-Labeltext vollständig. `setLabel()` und `setText()` funktionieren weiterhin neben `addToHeader()`, aber da der Header-Bereich visuell Vorrang hat, wird der Labeltext nicht angezeigt, es sei denn, Sie fügen ihn explizit in Ihren geschachtelten Inhalten hinzu.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Benutzerdefiniertes Symbol {#custom-icon}

Der Indikator für Erweitern/Zusammenklappen ist standardmäßig ein Chevron, das sowohl im offenen als auch im geschlossenen Zustand sichtbar ist. `setIcon()` ersetzt ihn durch jede [`Icon`](/docs/components/icon) Komponente, die nützlich für Marken-Ikonografie ist oder wenn eine andere visuelle Metapher besser zum Inhalt passt. Durch das Übergeben von `null` wird das Standard-Chevron wiederhergestellt. `getIcon()` gibt das derzeit eingestellte Symbol zurück oder `null`, wenn das Standard-Chevron verwendet wird.

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

Accordions können in anderen Accordion-Panels verschachtelt werden, was nützlich ist, um hierarchische Inhalte wie kategorisierte Einstellungen oder mehrstufige Navigation darzustellen. Fügen Sie ein inneres `Accordion` zu einem äußeren `AccordionPanel` wie jede andere Kindkomponente hinzu. Halten Sie die Verschachtelung flach. Eine oder zwei Ebenen sind normalerweise ausreichend. Tiefere Hierarchien sind oft schwerer zu navigieren und signalisiert häufig, dass die Inhaltsstruktur selbst überdacht werden muss.

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

`AccordionPanel` löst Ereignisse in jeder Phase des Toggle-Lebenszyklus aus. Die drei Ereignistypen decken verschiedene Momente ab, sodass Sie basierend auf dem Zeitpunkt, zu dem Ihre Logik ausgeführt werden muss, auswählen können:

| Ereignis | Wird ausgelöst |
|----------|----------------|
| `AccordionPanelToggleEvent` | Bevor der Zustand sich ändert |
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
    // Wird ausgelöst, nachdem das Panel vollständig geschlossen ist – gut für Aufräum- oder Zusammenfassungsaktualisierungen.
});
```

## Styling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
