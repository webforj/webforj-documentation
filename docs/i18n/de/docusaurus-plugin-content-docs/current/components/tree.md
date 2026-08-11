---
title: Tree
sidebar_position: 150
description: >-
  Display hierarchical data with the Tree component, using TreeNode parent-child
  links, expand or collapse, icons, and selection.
_i18n_hash: 0d536028b5d1148a59b52128c41278a5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten als Hierarchie von Knoten. Jeder Knoten hat einen eindeutigen Schlüssel und ein Label. Knoten verbinden sich, um Eltern-Kind-Beziehungen zu bilden. Sie können Knoten erweitern oder collapsen, um ihre Kinder anzuzeigen oder zu verbergen. Icons verdeutlichen, um welchen Knotentyp es sich handelt und ob er ausgewählt ist. Die Auswahl unterstützt das gleichzeitige Wählen eines oder mehrerer Knoten.

<!-- INTRO_END -->

## Knotenmodell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` {#the-role-of-treenode}

Jedes Stück Daten im Baum ist in einem `TreeNode` eingekapselt. Dieses Objekt hält den Schlüssel, das Textlabel und Verweisen auf die übergeordneten und untergeordneten Knoten. Der Wurzelknoten ist besonders: Er existiert in jedem Baum, ist aber nicht sichtbar. Er dient als Container für alle obersten Knoten und erleichtert die interne Verwaltung der Baumstruktur.

Da Knoten Referenzen auf ihre Eltern und Kinder behalten, ist das Durchqueren des Baums einfach. Egal, ob Sie nach oben, unten oder einen bestimmten Knoten nach Schlüssel suchen möchten, die Verbindungen sind immer zugänglich.

### Erstellung und Verwaltung von Knoten {#node-creation-and-management}

Knoten werden mit einfachen Fabrikmethoden erstellt, entweder durch Angabe eines Schlüssels und Textes oder nur des Textes (was gleichzeitig als Schlüssel fungiert). Dies gewährleistet, dass jeder Knoten gültig und eindeutig identifizierbar ist.

Das Hinzufügen von Knoten zum Baum erfolgt durch Aufrufen von `add()` oder `insert()` auf einem übergeordneten Knoten. Diese Methoden kümmern sich um die Zuordnung der Elternreferenz und benachrichtigen den Baum, dass seine UI aktualisiert werden muss.

Beispiel:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Wenn Sie denselben Knoten mehreren Eltern zuweisen, wird eine Ausnahme ausgelöst. Dieser Schutzmechanismus stellt sicher, dass der Baum eine ordnungsgemäße Hierarchie aufrechterhält, indem verhindert wird, dass Knoten mehrere Eltern haben, was die Integrität der Struktur gefährden und unerwartetes Verhalten verursachen würde.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Knoten ändern {#modifying-nodes}

Sie aktualisieren das Label eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den Text, der für den Knoten im Baum angezeigt wird.

Um einen bestimmten untergeordneten Knoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dies trennt das Kind von seinem Elternteil und entfernt es aus der Baumstruktur. Es löscht auch die Elternreferenz.

Wenn Sie alle Kinder eines Knotens leeren möchten, rufen Sie `removeAll()` auf. Dies entfernt jeden untergeordneten Knoten, leert deren Elternreferenzen und leert die Kinderliste.

Jeder Knoten unterstützt das Speichern zusätzlicher Informationen auf der Serverseite mit `setUserData(Object key, Object data)`. Dadurch können Sie beliebige Metadaten oder Verweise mit dem Knoten verknüpfen, ohne diese Daten dem Client oder der UI preiszugeben.

:::tip Verwendung der Demo zur Änderung des Knotentexts
In der Demo doppelklicken Sie auf einen Knoten, um einen Editor für dessen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um das Label des Knotens im Baum zu aktualisieren.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/frontend/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Icons {#icons}

Icons bieten visuelle Hinweise darauf, was Knoten repräsentieren und in welchem Zustand sie sich befinden. Sie verbessern die Lesbarkeit, indem sie Knotentypen und Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das globale Setzen von Standardicons, das Anpassen von Icons pro Knoten und das Umlegen der Sichtbarkeit von Icons.

### Globale Icons {#global-icons}

Der Baum ermöglicht das Setzen von Standardicons für kollabierte Gruppen, erweiterte Gruppen, Blattknoten und ausgewählte Blätter.

Beispiel:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Icon-Quellen
Ein Icon kann jede gültige webforJ [Icon](./icon)-Definition oder eine Ressourcendatei sein, die über einen von webforJ [unterstützten Assets-Protokollen](../managing-resources/assets-protocols) geladen wird.
:::

### Icons pro Knoten {#per-node-icons}

Sie können globale Standards überschreiben, indem Sie Icons einzelnen Knoten zuweisen. Dies ist nützlich, wenn bestimmte Knoten unterschiedliche Konzepte darstellen, wie „Projekt“-Ordner oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Sichtbarkeit von Icons {#icon-visibility}

Manchmal möchten Sie vielleicht Icons für Gruppen oder Blätter ausblenden, um Unordnung zu reduzieren. Die Komponente ermöglicht es Ihnen, die Sichtbarkeit für diese Kategorien global zu steuern, sodass Sie das Aussehen des Baums vereinfachen können, ohne die Struktur zu verlieren.

Beispiel:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo
path='/webforj/treeicons'
files={['src/main/java/com/webforj/samples/views/tree/TreeIconsView.java']}
height='320px'
/>

## Knoten erweitern und collapsen {#node-expansion-and-collapse}

Knoten können erweitert oder collapsen werden, um zu steuern, welche Teile des Baums sichtbar sind. Dies ermöglicht es, sich auf relevante Abschnitte zu konzentrieren und Szenarien wie Lazy Loading oder dynamische Datenaktualisierungen zu unterstützen.

### Erweiterungs- und Collapsen-Operationen {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Collapsen einzelner Knoten entweder durch ihren Schlüssel oder durch direkte Referenz. Sie können auch alle Nachkommen eines Knotens gleichzeitig erweitern oder collapsen.

Diese Operationen ermöglichen es Ihnen, zu steuern, wie viel vom Baum sichtbar ist, und unterstützen Lazy Loading von Daten oder die Konzentration auf interessante Bereiche.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// collapsen von Unterbäumen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Kollabieren der Wurzel
Der Wurzelknoten verankert den Baum, bleibt aber verborgen. Das Kollabieren der Wurzel würde normalerweise alles verstecken, wodurch der Baum leer zu erscheinen scheint. Um dies zu vermeiden, kollabiert das Kollabieren der Wurzel tatsächlich alle ihre Kinder, lässt jedoch die Wurzel intern erweitert, sodass der Baum seinen Inhalt weiterhin korrekt anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt lazy loading von Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre App die Kinder dieses Knotens dynamisch laden oder generieren. Dies verbessert die Leistung, indem nur die sichtbaren Teile des Baums nach Bedarf geladen werden.

Verwenden Sie das `onExpand`-Ereignis, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie im Handler, ob die Kinder des Knotens Platzhalter sind (zum Beispiel ein Spinner oder ein leerer Knoten), und ersetzen Sie diese durch tatsächliche Daten, sobald sie geladen wurden.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt werden. Die `Tree`-Komponente unterstützt flexible Modi und APIs zum Auswählen, Abwählen und Abfragen von Knoten.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum die Auswahl eines einzelnen Knotens zur gleichen Zeit oder mehrere Knoten gleichzeitig zulässt. Der Wechsel von mehrfachem zu einfacher Auswahl wählt automatisch alle außer dem ersten ausgewählten Knoten ab.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-Selection-Interaktion
Wenn der Baum im Mehrfachauswahlmodus eingestellt ist, können Benutzer mehr als einen Knoten gleichzeitig auswählen. Die Art und Weise, wie dies funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer halten die **Ctrl**-Taste (oder die **Cmd**-Taste auf macOS) gedrückt und klicken auf Knoten, um sie zur aktuellen Auswahl hinzuzufügen oder zu entfernen. Dadurch können mehrere einzelne Knoten ausgewählt werden, ohne andere abzuwählen.
* **Mobile und Touch-Geräte:** Da Modifikatortasten nicht verfügbar sind, tippen Benutzer einfach auf Knoten, um sie auszuwählen oder abzuwählen. Jeder Tap wechselt den Auswahlstatus dieses Knotens, was eine einfache Mehrfachauswahl durch einfache Taps ermöglicht.
:::

### Auswählen und Abwählen {#selecting-and-deselecting}

Knoten können durch Referenz, Schlüssel, einzeln oder in Gruppen ausgewählt oder abgewählt werden. Sie können auch alle Kinder eines Knotens in einem Aufruf auswählen oder abwählen.

Beispiel:

```java
// Knoten nach Referenz oder Schlüssel auswählen
tree.select(node);
tree.selectKey(key);

// Knoten nach Referenz oder Schlüssel abwählen
tree.deselect(node);
tree.deselectAll();

// Auswählen oder Abwählen von Kindern von Knoten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Abfrage des Auswahlstatus {#selection-state-retrieval}

Sie können die aktuelle Auswahl mit dem unten angezeigten Code abrufen:

```java
// Referenz des ausgewählten Knotens abrufen
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// Schlüssel des ausgewählten Knotens abrufen
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## Stil {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
