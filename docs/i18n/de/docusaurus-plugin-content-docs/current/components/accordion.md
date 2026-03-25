---
sidebar_position: 1
title: Accordion
sidebar_class_name: new-content
_i18n_hash: 560172f4743427476d9ecaadebd1d61d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Die `Accordion`-Komponente bietet eine vertikal gestapelte Reihe von klappbaren Panels. Jedes Panel hat einen klickbaren Header, der die Sichtbarkeit seines Inhalts umschaltet. Ein `AccordionPanel` kann als eigenständiger Offenbarungsteil verwendet oder in ein `Accordion` gruppiert werden, um das Expandieren und Schließen über mehrere Panels hinweg zu koordinieren.

<!-- INTRO_END -->

:::tip Wann man ein Accordion verwenden sollte
Accordions eignen sich gut für FAQs, Einstellungsseiten und Schritt-für-Schritt-Flows, bei denen das gleichzeitige Enthüllen aller Inhalte eine überwältigende Anordnung schaffen würde. Wenn die Abschnitte gleich wichtig sind und die Benutzer davon profitieren, sie gleichzeitig sehen zu können, ziehen Sie stattdessen [Tabs](/docs/components/tabbedpane) in Betracht.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` ist der zentrale Baustein des Accordion-Systems. Übergeben Sie einen Label-String an den Konstruktor, um den Header-Text festzulegen, und fügen Sie dann Kindkomponenten hinzu, um den Inhalt des Panels auszufüllen. Ein Panel funktioniert eigenständig ohne eine umgebende `Accordion`-Gruppe, was es zu einem nützlichen leichten Offenbarungselement macht, wenn Sie nur einen einzelnen klappbaren Abschnitt benötigen. Der Konstruktor ohne Argumente ist ebenfalls verfügbar, wenn Sie das Panel vollständig nach dem Bau konfigurieren möchten.

```java
// Nur Label - fügen Sie den Inhaltsbereich separat hinzu
AccordionPanel panel = new AccordionPanel("Abschnittsüberschrift");
panel.add(new Paragraph("Inhaltsbereich kommt hierhin."));

// Label und Inhaltsbereich direkt im Konstruktor übergeben
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Inhaltsbereich."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java'
height='500px'
/>
<!-- vale on -->

### Öffnen und Schließen {#opening-and-closing}

Steuern Sie den offenen/geschlossenen Zustand programmgesteuert zu jeder Zeit. `isOpened()` ist nützlich, wenn Sie den aktuellen Zustand lesen müssen, bevor Sie entscheiden, was zu tun ist. Zum Beispiel könnten Sie ein Panel in den entgegengesetzten Zustand umschalten oder andere Teile der Benutzeroberfläche bedingt anzeigen oder ausblenden.

```java
// Panel erweitern
panel.open();

// Panel schließen
panel.close();                    

// Gibt true zurück, wenn es derzeit erweitert ist
boolean isOpen = panel.isOpened();
```

Verwenden Sie `setLabel()`, um den Header-Text nach der Erstellung zu aktualisieren. `setText()` ist ein Synonym für dieselbe Operation, sodass das Label mit dynamischen Daten synchronisiert werden kann:

```java
panel.setLabel("Aktualisiertes Label");
```

## Accordion-Gruppen {#accordion-groups}

Das Einrahmen mehrerer `AccordionPanel`-Instanzen innerhalb eines `Accordion` schafft eine koordinierte Gruppe. Standardmäßig verwendet die Gruppe den **Einzelmodus**: Das Öffnen eines Panels lässt automatisch alle anderen einklappen, sodass immer nur ein Abschnitt gleichzeitig sichtbar ist. Dieses Standardverhalten ist absichtlich, es hält den Benutzer auf ein Stück Inhalt fokussiert und verhindert, dass die Seite visuell überwältigend wird, wenn Panels umfangreiche Inhalte haben.

Panels werden unabhängig konstruiert und an das `Accordion` übergeben, sodass Sie jedes Panel konfigurieren können, bevor Sie sie gruppieren. Mehrere separate `Accordion`-Instanzen können auch auf derselben Seite existieren—jede Gruppe verwaltet ihren eigenen Zustand unabhängig, sodass das Erweitern eines Panels in einer Gruppe keine Auswirkungen auf eine andere hat.

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

Der Mehrfachmodus erlaubt es, dass beliebig viele Panels gleichzeitig erweitert bleiben. Dies ist nützlich, wenn Benutzer den Inhalt mehrerer Abschnitte gleichzeitig vergleichen müssen oder wenn jedes Panel kurz genug ist, sodass das gleichzeitige Erweitern mehrerer kein überladenes Layout schafft.

```java
accordion.setMultiple(true);
```

Mit aktivem Mehrfachmodus können alle Panels in der Gruppe gleichzeitig mit den Sammelmethoden erweitert oder eingeklappt werden:

```java
// Jedes Panel in der Gruppe erweitern
accordion.openAll();

// Jedes Panel in der Gruppe einklappen
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
`openAll()` ist nur verfügbar, wenn der Mehrfachmodus aktiviert ist. Es hat keine Wirkung, wenn es im Einzelmodus aufgerufen wird. `closeAll()` funktioniert in beiden Modi.
:::

<!-- vale off -->
## Deaktivierter Zustand {#disabled-state}
<!-- vale on -->

Einzelne Panels können deaktiviert werden, um die Benutzerinteraktion zu verhindern, während sie weiterhin sichtbar bleiben. Dies ist nützlich während Ladezuständen oder wenn bestimmte Abschnitte bedingt nicht verfügbar sind, sodass die Panelstruktur angezeigt wird, ohne vorzeitige Interaktionen zuzulassen. Ein deaktiviertes Panel, das bereits geöffnet war, bleibt erweitert, kann jedoch nicht mehr angeklickt werden, um es einzuklappen. Das Deaktivieren der `Accordion`-Gruppe wendet den deaktivierten Zustand auf alle enthaltenen Panels gleichzeitig an, sodass Sie nicht durch einzelne Panels iterieren müssen.

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

Über Labels und grundlegendes Öffnen/Schließen hinaus unterstützt jedes `AccordionPanel` eine reichhaltigere Anpassung sowohl seines Header-Inhalts als auch des Expandieren/-Schließen-Symbols.

### Benutzerdefinierter Header {#custom-header}

Der Header eines Panels rendert sein Label standardmäßig als einfachen Text. Verwenden Sie `addToHeader()`, um diesen Text durch eine beliebige Komponente oder Kombination von Komponenten zu ersetzen, sodass es einfach ist, Symbole, Abzeichen, Statusanzeigen oder anderen reichen Markup neben dem Panel-Label einzuschließen. Dies ist besonders nützlich in Dashboards oder Einstellungspanels, wo jede Abschnittsüberschrift zusätzlichen Kontext auf einen Blick vermitteln muss, wie z.B. eine Elementanzahl, ein Warnabzeichen oder einen Abschlussstatus, ohne dass der Benutzer das Panel zuerst erweitern muss.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Benutzerdefinierter Header mit Symbol"));
panel.addToHeader(headerContent);
```

:::info Ersatz des Labels
Inhalt, der über `addToHeader()` hinzugefügt wird, ersetzt vollständig den Standardlabel-text. `setLabel()` und `setText()` funktionieren weiterhin neben `addToHeader()`, aber da der Header-Platz visuelle Vorrang hat, wird der Label-Text nicht angezeigt, es sei denn, Sie fügen ihn ausdrücklich in Ihren geschlitzten Inhalt ein.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java'
height='300px'
/>
<!-- vale on -->

### Benutzerdefiniertes Symbol {#custom-icon}

Der Indikator für Erweitern/Einklappen wird standardmäßig als Chevron angezeigt, der sowohl im offenen als auch im geschlossenen Zustand sichtbar ist. `setIcon()` ersetzt ihn durch eine beliebige [`Icon`](/docs/components/icon)-Komponente, die nützlich für gebrandete Ikonographie ist oder wenn eine andere visuelle Metapher besser zum Inhalt passt. Das Übergeben von `null` stellt das Standard-Chevron wieder her. `getIcon()` gibt das derzeit gesetzte Symbol zurück oder `null`, wenn das Standard-Chevron verwendet wird.

```java
// Ersetzen des Standard-Chevrons durch ein Plus-Symbol
panel.setIcon(FeatherIcon.PLUS.create());

// Das Standard-Chevron wiederherstellen
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

Accordions können in anderen Accordion-Panels verschachtelt werden, was nützlich ist, um hierarchische Inhalte wie kategorisierte Einstellungen oder mehrstufige Navigation darzustellen. Fügen Sie ein inneres `Accordion` zu einem äußeren `AccordionPanel` wie jede andere Kindkomponente hinzu. Halten Sie die Verschachtelung flach. Ein oder zwei Ebenen sind normalerweise ausreichend. Tiefergehende Hierarchien tendieren dazu, schwerer zu navigieren zu sein und signalisierten oft, dass die Inhaltsstruktur selbst überdacht werden muss.

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

`AccordionPanel` löst Ereignisse in jeder Phase des Umschaltzyklus aus. Die drei Ereignistypen decken unterschiedliche Momente ab, also wählen Sie basierend darauf, wann Ihre Logik ausgeführt werden muss:

| Ereignis | Feuer |
|-------|-------|
| `AccordionPanelToggleEvent` | Bevor sich der Zustand ändert |
| `AccordionPanelOpenEvent` | Nachdem das Panel vollständig geöffnet ist |
| `AccordionPanelCloseEvent` | Nachdem das Panel vollständig geschlossen ist |

```java
panel.onToggle(e -> {
    // Wird ausgelöst, bevor sich das Panel im Zustand ändert.
    // e.isOpened() spiegelt den Zustand wider, in den gewechselt wird, nicht den aktuellen Zustand.
    String direction = e.isOpened() ? "öffnen" : "schließen";
});

panel.onOpen(e -> {
    // Wird ausgelöst, nachdem das Panel vollständig geöffnet ist — gut zum Faul-Laden von Inhalten.
});

panel.onClose(e -> {
    // Wird ausgelöst, nachdem das Panel vollständig geschlossen ist — gut für Aufräum- oder Zusammenfassungsupdates.
});
```

## Styling {#styling}

<TableBuilder name="AccordionPanel" />
