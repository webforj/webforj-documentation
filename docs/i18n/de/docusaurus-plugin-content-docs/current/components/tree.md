---
title: Tree
sidebar_position: 150
_i18n_hash: 280fb07f73ba1172b33bd0617ded7876
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten als Hierarchie von Knoten. Jeder Knoten hat einen einzigartigen Schlüssel und ein Label. Knoten verbinden sich, um Eltern-Kind-Beziehungen zu bilden. Sie können Knoten erweitern oder reduzieren, um deren Kinder anzuzeigen oder auszublenden. Icons verdeutlichen, um welchen Knotentyp es sich handelt und ob er ausgewählt ist. Die Auswahl unterstützt die Auswahl eines Knotens oder vieler auf einmal.

<!-- INTRO_END -->

## Knotenmodell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` {#the-role-of-treenode}

Jedes Datenstück im Baum ist in einem `TreeNode` eingekapselt. Dieses Objekt enthält den Schlüssel, das Textlabel und Links zu seinen Eltern- und Kindknoten. Der Stammknoten ist etwas Besonderes: Er existiert in jedem Baum, ist aber nicht sichtbar. Er dient als Container für alle Knoten der obersten Ebene und erleichtert die Verwaltung der Baumstruktur intern.

Da Knoten Referenzen zu ihren Eltern und Kindern aufbewahren, ist das Durchqueren des Baums unkompliziert. Egal, ob Sie nach oben, unten oder einen bestimmten Knoten nach Schlüssel suchen möchten, die Verbindungen sind immer zugänglich.

### Knoten erstellen und verwalten {#node-creation-and-management}

Knoten werden mit einfachen Fabrikmethoden erstellt, entweder durch Bereitstellung eines Schlüssels und Textes oder nur Text (der auch als Schlüssel dient). Dies garantiert, dass jeder Knoten gültig und eindeutig identifizierbar ist.

Um Knoten zum Baum hinzuzufügen, rufen Sie `add()` oder `insert()` auf einem Elternknoten auf. Diese Methoden handhaben das Zuweisen der Elternreferenz und benachrichtigen den Baum, dass er seine Benutzeroberfläche aktualisieren soll.

Beispiel:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Versuche, denselben Knoten mehreren Eltern zuzuweisen, führen zu einer ausgelösten Ausnahme. Diese Schutzmaßnahme stellt sicher, dass der Baum eine korrekte Hierarchie beibehält, indem sie verhindert, dass Knoten mehrere Eltern haben, was die Integrität der Struktur brechen und unerwartetes Verhalten verursachen würde.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knoten modifizieren {#modifying-nodes}

Sie aktualisieren das Label eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den angezeigten Text für den Knoten im Baum.

Um einen bestimmten Kindknoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dies trennt das Kind von seinem Elternteil und entfernt es aus der Baumstruktur. Es wird auch die Elternreferenz gelöscht.

Wenn Sie alle Kinder von einem Knoten entfernen möchten, rufen Sie `removeAll()` auf. Dies entfernt jeden Kindknoten, löscht deren Elternreferenzen und leert die Liste der Kinder.

Jeder Knoten unterstützt das Speichern zusätzlicher Informationen auf der Serverseite, indem `setUserData(Object key, Object data)` verwendet wird. Dies ermöglicht es Ihnen, beliebige Metadaten oder Referenzen mit dem Knoten zu verknüpfen, ohne diese Daten dem Client oder der Benutzeroberfläche offenzulegen.

:::tip Demo verwenden, um Knotentext zu bearbeiten
In der Demo doppelklicken Sie auf einen Knoten, um einen Editor für dessen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um das Label des Knotens im Baum zu aktualisieren.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Icons {#icons}

Icons bieten visuelle Hinweise darauf, was Knoten darstellen und welchen Zustand sie haben. Sie verbessern die Lesbarkeit, indem sie Knotentypen und Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das globale Festlegen von Standardicons, das Anpassen von Icons pro Knoten und das Umschalten der Icon-Sichtbarkeit.

### Globale Icons {#global-icons}

Der Baum ermöglicht es Ihnen, Standardicons für reduzierte Gruppen, erweiterte Gruppen, Blattknoten und ausgewählte Blätter festzulegen.

Beispiel:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Icon-Quellen
Ein Icon kann jede gültige webforJ [Icon](./icon)-Definition oder eine Datei sein, die über unterstützte Assets-Protokolle von webforJ geladen wurde. 
:::

### Icons pro Knoten {#per-node-icons}

Sie können globale Standards überschreiben, indem Sie Icons zu einzelnen Knoten zuweisen. Dies ist nützlich, wenn bestimmte Knoten unterschiedliche Konzepte repräsentieren, wie „Projekt“-Ordner oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Icon-Sichtbarkeit {#icon-visibility}

Manchmal möchten Sie möglicherweise Icons für Gruppen oder Blätter ausblenden, um Unordnung zu reduzieren. Die Komponente ermöglicht es Ihnen, die Sichtbarkeit global für diese Kategorien umzuschalten, sodass Sie das Erscheinungsbild des Baums vereinfachen können, ohne die Struktur zu verlieren.

Beispiel:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## Knoten erweitern und reduzieren {#node-expansion-and-collapse}

Knoten können erweitert oder reduziert werden, um zu steuern, welche Teile des Baums sichtbar sind. Dies ermöglicht die Fokussierung auf relevante Abschnitte und unterstützt Szenarien wie Lazy Loading oder dynamische Datenupdates.

### Expandieren und Reduzieren {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Reduzieren einzelner Knoten entweder über deren Schlüssel oder direkte Referenz. Sie können auch alle Nachkommen eines Knotens auf einmal erweitern oder reduzieren.

Diese Operationen ermöglichen es Ihnen, zu steuern, wie viel des Baums sichtbar ist, und unterstützen das Lazy Loading von Daten oder die Fokussierung auf interessante Bereiche.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// reduzierte Teilbäume
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Stammreduzierung
Der Stammknoten verankert den Baum, bleibt jedoch verborgen. Das Reduzieren des Stammes würde normalerweise alles verbergen und den Baum leer erscheinen lassen. Um dies zu vermeiden, reduziert das Zusammenklappen des Stammes tatsächlich alle seine Kinder, lässt den Stamm intern jedoch erweitert, damit der Baum seinen Inhalt weiterhin korrekt anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt das Lazy Loading von Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre App die Kinder dieses Knotens dynamisch laden oder generieren. Dies verbessert die Leistung, indem nur die sichtbaren Teile des Baums nach Bedarf geladen werden.

Verwenden Sie das Ereignis `onExpand`, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie im Handler, ob die Kinder des Knotens Platzhalter sind (zum Beispiel ein Spinner oder leerer Knoten) und ersetzen Sie diese durch tatsächliche Daten, sobald sie geladen sind.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt werden. Die `Tree`-Komponente unterstützt flexible Modi und APIs, um Knoten auszuwählen, abzuwählen und abzufragen.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum die Auswahl eines einzelnen Knotens zu einem Zeitpunkt oder die gleichzeitige Auswahl mehrerer Knoten erlaubt. Der Wechsel von mehreren zu einer einzelnen Auswahl hebt automatisch die Auswahl aller bis auf den zuerst ausgewählten Knoten auf.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaktion mit Mehrfachauswahl
Wenn der Baum auf den Mehrfachauswahlmodus eingestellt ist, können Benutzer mehr als einen Knoten gleichzeitig auswählen. Die Art und Weise, wie dies funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer halten die **Ctrl**-Taste (oder die **Cmd**-Taste auf macOS) gedrückt und klicken auf Knoten, um sie zur aktuellen Auswahl hinzuzufügen oder daraus zu entfernen. Dies ermöglicht die Auswahl mehrerer einzelner Knoten, ohne andere abzuwählen.
* **Mobile und Touch-Geräte:** Da Modifier-Tasten nicht verfügbar sind, tippen Benutzer einfach auf Knoten, um sie auszuwählen oder abzuwählen. Jeder Tipp wechselt den Auswahlstatus dieses Knotens, sodass eine einfache Mehrfachauswahl durch einfache Taps ermöglicht wird.
:::

### Auswählen und Abwählen {#selecting-and-deselecting}

Knoten können über Referenz, Schlüssel, einzeln oder in Gruppen ausgewählt oder abgewählt werden. Sie können auch alle Kinder eines Knotens in einem Aufruf auswählen oder abwählen.

Beispiel:

```java
// Knoten nach Referenz oder Schlüssel auswählen
tree.select(node);
tree.selectKey(key);

// Knoten nach Referenz oder Schlüssel abwählen
tree.deselect(node);
tree.deselectAll();

// Auswahl oder Abwahl von Kindknoten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Auswahlstatus abfragen {#selection-state-retrieval}

Sie können die aktuelle Auswahl mithilfe des folgenden Codes abfragen:

```java
// Referenz des ausgewählten Knotens erhalten
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// Schlüssel des ausgewählten Knotens erhalten
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Styling {#styling}

<TableBuilder name="Tree" />
