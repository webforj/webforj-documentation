---
sidebar_position: 1
title: Accordion
_i18n_hash: 207c70347cc18d88661a8a9279988417
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-accordion" />
<DocChip chip='name' label="dwc-accordion-panel" />
<DocChip chip='since' label='25.12' />
<JavadocLink type="accordion" location="com/webforj/component/accordion/Accordion" top='true'/>

Die `Accordion`-Komponente bietet einen vertikal gestapelten Satz von einziehbaren Panels. Jedes Panel hat einen klickbaren Header, der die Sichtbarkeit seines Inhalts umschaltet. Ein `AccordionPanel` kann als eigenständiger Offenlegungsteil verwendet werden oder innerhalb eines `Accordion` gruppiert werden, um das Expandieren und Zusammenklappen mehrerer Panels zu koordinieren.

<!-- INTRO_END -->

:::tip Wann man ein Accordion verwenden sollte
Accordions eignen sich gut für FAQs, Einstellungsseiten und Schritt-für-Schritt-Abläufe, bei denen das gleichzeitige Offenlegen aller Inhalte ein überwältigendes Layout schaffen würde. Wenn Abschnitte gleich wichtig sind und die Benutzer davon profitieren, sie gleichzeitig zu sehen, ziehen Sie stattdessen [Tabs](/docs/components/tabbedpane) in Betracht.
:::

## `AccordionPanel` {#accordion-panel}

`AccordionPanel` ist der zentrale Baustein des Accordion-Systems. Übergeben Sie einen Label-String an den Konstruktor, um den Header-Text festzulegen, und fügen Sie dann Kindkomponenten hinzu, um den Body zu befüllen. Ein Panel funktioniert auch ohne eine umgebende `Accordion`-Gruppe, was es zu einem nützlichen, leichten Offenlegungs-Widget macht, wenn Sie nur einen einzelnen einziehbaren Abschnitt benötigen. Der Konstruktor ohne Argumente ist ebenfalls verfügbar, wenn Sie das Panel vollständig nach der Konstruktion konfigurieren möchten.

```java
// Nur Label - fügen Sie den Body-Inhalt separat hinzu
AccordionPanel panel = new AccordionPanel("Abschnittsüberschrift");
panel.add(new Paragraph("Body-Inhalt kommt hierhin."));

// Label und Body-Inhalt werden direkt im Konstruktor übergeben
AccordionPanel panel = new AccordionPanel("Titel", new Paragraph("Body-Inhalt."));
```

<!-- vale off -->
<ComponentDemo
path='/webforj/accordionbasic'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionBasicView.java']}
height='550px'
/>
<!-- vale on -->

### Öffnen und Schließen {#opening-and-closing}

Steuern Sie den geöffneten/geschlossenen Status jederzeit programmatisch. `isOpened()` ist nützlich, wenn Sie den aktuellen Status lesen müssen, bevor Sie entscheiden, was zu tun ist. Zum Beispiel könnten Sie ein Panel in den gegenteiligen Status umschalten oder andere Teile der Benutzeroberfläche bedingt anzeigen oder ausblenden.

```java
// Panel erweitern
panel.open();

// Panel schließen
panel.close();                    

// Gibt true zurück, wenn derzeit erweitert
boolean isOpen = panel.isOpened();
```

Verwenden Sie `setLabel()`, um den Header-Text nach der Konstruktion zu aktualisieren. `setText()` ist ein Alias für dieselbe Operation, sodass das Label mit dynamischen Daten synchron gehalten werden kann:

```java
panel.setLabel("Aktualisiertes Label");
```

## Accordion-Gruppen {#accordion-groups}

Das Gruppieren mehrerer `AccordionPanel`-Instanzen innerhalb eines `Accordion` schafft eine koordinierte Gruppe. Standardmäßig verwendet die Gruppe den **Einzelmodus**: Das Öffnen eines Panels kollabiert automatisch alle anderen, sodass immer nur ein Abschnitt sichtbar bleibt. Dieses Standardverhalten ist absichtlich, da es den Benutzer auf ein Stück Inhalt fokussiert und verhindert, dass die Seite visuell überwältigend wird, wenn Panels umfangreiche Body-Inhalte haben.

Panels werden unabhängig erstellt und an das `Accordion` übergeben, sodass Sie jedes einzeln konfigurieren können, bevor Sie sie gruppieren. Mehrere separate `Accordion`-Instanzen können ebenfalls auf derselben Seite existieren – jede Gruppe verwaltet ihren eigenen Status unabhängig, sodass das Erweitern eines Panels in einer Gruppe keine Auswirkungen auf eine andere hat.

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

Der Mehrfachmodus ermöglicht es, dass beliebig viele Panels gleichzeitig geöffnet bleiben. Dies ist nützlich, wenn Benutzer den Inhalt mehrere Abschnitte gleichzeitig vergleichen müssen oder wenn jedes Panel kurz genug ist, dass das gleichzeitige Erweitern mehrerer Panels kein unübersichtliches Layout schafft.

```java
accordion.setMultiple(true);
```

Mit aktiviertem Mehrfachmodus können alle Panels in der Gruppe gleichzeitig mit den Sammelmöglichkeiten erweitert oder geschlossen werden:

```java
// Jedes Panel in der Gruppe erweitern
accordion.openAll();

// Jedes Panel in der Gruppe schließen
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
`openAll()` ist nur verfügbar, wenn der Mehrfachmodus aktiviert ist. Es hat keine Auswirkungen, wenn es im Einzelmodus aufgerufen wird. `closeAll()` funktioniert in beiden Modi.
:::

<!-- vale off -->
## Deaktivierter Zustand {#disabled-state}
<!-- vale on -->

Individuelle Panels können deaktiviert werden, um die Benutzerinteraktion zu verhindern, während sie weiterhin sichtbar bleiben. Dies ist nützlich während Ladezuständen oder wenn bestimmte Abschnitte bedingt nicht verfügbar sind, da die Panelstruktur angezeigt wird, ohne premature Interaktionen zu ermöglichen. Ein deaktiviertes Panel, das bereits geöffnet war, bleibt erweitert, aber sein Header kann nicht mehr angeklickt werden, um es zu schließen. Das Deaktivieren der `Accordion`-Gruppe wendet den deaktivierten Zustand gleichzeitig auf alle enthaltenen Panels an, sodass Sie nicht jedes Panel einzeln durchlaufen müssen.

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

## Individualisierung von Panels {#customizing-panels}

Über Labels und das grundlegende Öffnen/Schließen-Verhalten hinaus unterstützt jedes `AccordionPanel` eine reichhaltigere Anpassung sowohl seines Header-Inhalts als auch des Expandieren-/Zuklappen-Icons.

### Benutzerdefinierter Header {#custom-header}

Der Header eines Panels gibt standardmäßig sein Label als normalen Text wieder. Verwenden Sie `addToHeader()`, um diesen Text durch beliebige Komponenten oder Kombinationen von Komponenten zu ersetzen, was das einfache Einfügen von Icons, Abzeichen, Statusanzeigen oder anderem reichhaltigen Markup neben dem Panel-Label erleichtert. Dies ist besonders nützlich in Dashboards oder Einstellungs-Panels, in denen jeder Abschnittsheader zusätzlichen Kontext auf einen Blick vermitteln muss, wie z.B. eine Elementanzahl, ein Warnabzeichen oder einen Abschlussstatus, ohne dass der Benutzer das Panel zuerst erweitern muss.

```java
FlexLayout headerContent = FlexLayout.create()
    .horizontal()
    .build()
    .setSpacing("var(--dwc-space-s)");

headerContent.add(FeatherIcon.SETTINGS.create(), new Span("Benutzerdefinierter Header mit Icon"));
panel.addToHeader(headerContent);
```

:::info Label-Ersatz
Inhalt, der über `addToHeader()` hinzugefügt wird, ersetzt vollständig den Standard-Label-Text. `setLabel()` und `setText()` funktionieren weiterhin zusammen mit `addToHeader()`, aber da der Header-Bereich visuelle Priorität hat, wird der Label-Text nicht angezeigt, es sei denn, Sie fügen ihn ausdrücklich in Ihrem slotierten Inhalt hinzu.
:::

<!-- vale off -->
<ComponentDemo
path='/webforj/accordioncustomheader'
files={['src/main/java/com/webforj/samples/views/accordion/AccordionCustomHeaderView.java']}
height='300px'
/>
<!-- vale on -->

### Benutzerdefiniertes Icon {#custom-icon}

Der Expand-/Zuklappen-Indikator wird standardmäßig durch ein Chevron dargestellt, das sowohl im geöffneten als auch im geschlossenen Zustand sichtbar ist. `setIcon()` ersetzt es durch eine beliebige [`Icon`](/docs/components/icon) Komponente, die nützlich für gebrandete Ikonografie ist oder wenn ein anderes visuelles Symbol besser zum Inhalt passt. Das Übergeben von `null` stellt das Standard-Chevron wieder her. `getIcon()` gibt das derzeit eingestellte Icon zurück oder `null`, wenn das Standard-Chevron verwendet wird.

```java
// Ersetzen Sie das Standard-Chevron durch ein Plus-Icon
panel.setIcon(FeatherIcon.PLUS.create());

// Stellen Sie das Standard-Chevron wieder her
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

Accordions können in andere Accordion-Panels eingebettet werden, was nützlich ist, um hierarchische Inhalte wie kategorisierte Einstellungen oder mehrstufige Navigation darzustellen. Fügen Sie ein inneres `Accordion` zu einem äußeren `AccordionPanel` als jede andere Kindkomponente hinzu. Halten Sie die Verschachtelung flach. Eins oder zwei Ebenen sind normalerweise ausreichend. Tiefere Hierarchien sind oft schwieriger zu navigieren und deuten häufig darauf hin, dass die Inhaltsstruktur selbst überdacht werden muss.

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

`AccordionPanel` löst Ereignisse in jeder Phase des Umschaltlebenszyklus aus. Die drei Ereignistypen decken verschiedene Zeitpunkte ab, wählen Sie also basierend darauf, wann Ihre Logik ausgeführt werden muss:

| Ereignis | Tritt ein |
|----------|-----------|
| `AccordionPanelToggleEvent` | Bevor sich der Status ändert |
| `AccordionPanelOpenEvent` | Nachdem das Panel vollständig geöffnet ist |
| `AccordionPanelCloseEvent` | Nachdem das Panel vollständig geschlossen ist |

```java
panel.onToggle(e -> {
    // Tritt ein, bevor sich das Panel in den Status ändert.
    // e.isOpened() spiegelt den Status wider, in den gewechselt wird, nicht den aktuellen Status.
    String direction = e.isOpened() ? "öffnen" : "schließen";
});

panel.onOpen(e -> {
    // Tritt ein, nachdem das Panel vollständig geöffnet ist — gut für das Lazy-Loading von Inhalten.
});

panel.onClose(e -> {
    // Tritt ein, nachdem das Panel vollständig geschlossen ist — gut für Aufräum- oder Zusammenfassungsaktualisierungen.
});
```

## Styling {#styling}

<TableBuilder name={['Accordion', 'AccordionPanel']} />
