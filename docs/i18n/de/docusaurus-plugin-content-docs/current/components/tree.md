---
title: Tree
sidebar_position: 150
_i18n_hash: 8f653af18f5e041d09896794f560d30a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten als eine Hierarchie von Knoten. Jeder Knoten hat einen einzigartigen Schlüssel und ein Label. Knoten verbinden sich, um Eltern-Kind-Beziehungen zu bilden. Sie können Knoten erweitern oder reduzieren, um deren Kinder anzuzeigen oder auszublenden. Icons verdeutlichen, um welchen Knoten es sich handelt und ob er ausgewählt ist. Die Auswahl unterstützt die Wahl eines einzelnen Knotens oder mehrerer gleichzeitig.

## Node-Modell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` {#the-role-of-treenode}

Jedes Datenelement im Baum ist in einem `TreeNode` verpackt. Dieses Objekt speichert den Schlüssel, das Textlabel und Links zu seinen Eltern- und Kindknoten. Der Wurzelknoten ist besonders: Er existiert in jedem Baum, ist jedoch nicht sichtbar. Er dient als Container für alle obersten Knoten und erleichtert die interne Verwaltung der Baumstruktur.

Da Knoten Referenzen zu ihren Eltern und Kindern speichern, ist das Traversieren des Baums unkompliziert. Egal, ob Sie nach oben, unten oder einen bestimmten Knoten nach Schlüssel suchen möchten, die Verbindungen sind immer zugänglich.

### Knoten erstellen und verwalten {#node-creation-and-management}

Knoten werden mithilfe einfacher Fabrikmethoden erstellt, entweder durch die Bereitstellung eines Schlüssels und Textes oder nur durch Text (der auch als Schlüssel dient). Dies gewährleistet, dass jeder Knoten gültig und eindeutig identifizierbar ist.

Das Hinzufügen von Knoten zum Baum erfolgt durch den Aufruf von `add()` oder `insert()` auf einem Elternknoten. Diese Methoden verwalten die Zuordnung der Elternreferenz und benachrichtigen den Baum, seine Benutzeroberfläche zu aktualisieren.

Beispiel:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Ein Versuch, denselben Knoten mehreren Eltern zuzuweisen, führt zu einer Ausnahme. Dieser Schutz stellt sicher, dass der Baum eine ordnungsgemäße Hierarchie aufrechterhält, indem verhindert wird, dass Knoten mehrere Eltern haben, was die Integrität der Struktur brechen und zu unerwartetem Verhalten führen würde.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knoten bearbeiten {#modifying-nodes}

Sie aktualisieren das Label eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den Text, der für den Knoten im Baum angezeigt wird.

Um einen bestimmten untergeordneten Knoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dadurch wird das Kind von seinem Elternteil abgekoppelt und aus der Baumstruktur entfernt. Es wird auch die Elternreferenz gelöscht.

Wenn Sie alle Kinder von einem Knoten löschen möchten, rufen Sie `removeAll()` auf. Dies entfernt jeden untergeordneten Knoten, löscht deren Elternreferenzen und leert die Liste der Kinder.

Jeder Knoten unterstützt das Speichern zusätzlicher Informationen auf der Serverseite mit `setUserData(Object key, Object data)`. Dies ermöglicht es Ihnen, willkürliche Metadaten oder Referenzen mit dem Knoten zu verknüpfen, ohne diese Daten für den Client oder die Benutzeroberfläche zugänglich zu machen.

:::tip Verwendung der Demo zum Bearbeiten des Knotentexts
In der Demo doppelklicken Sie auf einen Knoten, um einen Editor für seinen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um das Label des Knotens im Baum zu aktualisieren.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Icons {#icons}

Icons bieten visuelle Hinweise darauf, was Knoten darstellen und welchen Zustand sie haben. Sie verbessern die Lesbarkeit, indem sie Knotentypen und den Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das globale Setzen von Standardicons, das Anpassen von Icons pro Knoten und das Umschalten der Iconsichtbarkeit.

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
Ein Icon kann jede gültige webforJ [Icon](./icon)-Definition oder eine Ressourcen-Datei sein, die über einen von webforJ [unterstützten Assets-Protokollen](../managing-resources/assets-protocols) geladen wird.
:::

### Pro-Knoten-Icons {#per-node-icons}

Sie können globale Standards überschreiben, indem Sie Icons einzelnen Knoten zuweisen. Dies ist nützlich, wenn bestimmte Knoten unterschiedliche Konzepte darstellen, wie "Projektdokumente" oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Iconsichtbarkeit {#icon-visibility}

Manchmal möchten Sie möglicherweise Icons für Gruppen oder Blätter ausblenden, um Unordnung zu reduzieren. Die Komponente ermöglicht es Ihnen, die Sichtbarkeit für diese Kategorien global umzuschalten, sodass Sie das Erscheinungsbild des Baums vereinfachen können, ohne die Struktur zu verlieren.

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

Knoten können erweitert oder reduziert werden, um zu steuern, welche Teile des Baums sichtbar sind. Dies ermöglicht es, sich auf relevante Abschnitte zu konzentrieren und unterstützt Szenarien wie Lazy Loading oder dynamische Datenaktualisierungen.

### Erweiterungs- und Reduktionsoperationen {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Reduzieren einzelner Knoten entweder nach ihrem Schlüssel oder direkter Referenz. Sie können auch alle Nachfahren eines Knotens auf einmal erweitern oder reduzieren.

Diese Operationen ermöglichen es Ihnen zu steuern, wie viel vom Baum sichtbar ist, und unterstützen das Lazy Loading von Daten oder die Fokussierung auf interessante Bereiche.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// reduzieren von Unterbäumen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Reduzierung der Wurzel
Der Wurzelknoten verankert den Baum, bleibt jedoch verborgen. Das Reduzieren der Wurzel würde normalerweise alles ausblenden, sodass der Baum leer erscheint. Um dies zu vermeiden, reduziert das Reduzieren der Wurzel tatsächlich alle ihre Kinder, behält jedoch die Wurzel intern erweitert bei, sodass der Baum seinen Inhalt korrekt anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt das Lazy Loading von Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre Anwendung die Kinder dieses Knotens dynamisch laden oder generieren. Dadurch wird die Leistung verbessert, da nur sichtbare Teile des Baums nach Bedarf geladen werden.

Verwenden Sie das `onExpand`-Ereignis, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie innerhalb des Handlers, ob die Kinder des Knotens Platzhalter sind (zum Beispiel ein Spinner oder ein leerer Knoten), und ersetzen Sie sie durch tatsächliche Daten, sobald sie geladen sind.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt werden. Die `Tree`-Komponente unterstützt flexible Modi und APIs, um Knoten auszuwählen, abzuwählen und abzufragen.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum die Auswahl eines einzelnen Knotens oder mehrerer Knoten gleichzeitig erlaubt. Der Wechsel von mehreren zur Einzelauswahl hebt automatisch die Auswahl aller bis auf den ersten ausgewählten Knoten auf.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-Selection-Interaktion
Wenn der Baum im Mehrfachauswahlmodus ist, können Benutzer mehr als einen Knoten gleichzeitig auswählen. Wie das funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer halten die **Strg**-Taste (oder die **Cmd**-Taste auf macOS) gedrückt und klicken Knoten an, um sie zur aktuellen Auswahl hinzuzufügen oder zu entfernen. Dies ermöglicht das Auswählen mehrerer einzelner Knoten, ohne andere abzuwählen.
* **Mobile und Touch-Geräte:** Da Modifikatortasten nicht verfügbar sind, tippen Benutzer einfach auf Knoten, um sie auszuwählen oder abzuwählen. Jeder Tap schaltet den Auswahlstatus dieses Knotens um, sodass eine einfache Mehrfachauswahl durch einfache Taps ermöglicht wird.
:::

### Auswählen und Abwählen {#selecting-and-deselecting}

Knoten können durch Referenz, Schlüssel, einzeln oder in Chargen ausgewählt oder abgewählt werden. Sie können auch alle Kinder eines Knotens in einem Aufruf auswählen oder abwählen.

Beispiel:

```java
// Knoten durch Referenz oder Schlüssel auswählen
tree.select(node);
tree.selectKey(key);

// Knoten durch Referenz oder Schlüssel abwählen
tree.deselect(node);
tree.deselectAll();

// Auswählen oder Abwählen von Kinderknoten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Abrufen des Auswahlstatus {#selection-state-retrieval}

Sie können die aktuelle Auswahl erhalten, indem Sie den unten angegebenen Code verwenden:

```java
// die Referenz des ausgewählten Knotens abrufen
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// den Schlüssel des ausgewählten Knotens abrufen
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
