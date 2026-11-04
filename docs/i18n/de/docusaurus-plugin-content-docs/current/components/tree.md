---
title: Tree
sidebar_position: 150
description: >-
  Display hierarchical data with the Tree component, using TreeNode parent-child
  links, expand or collapse, icons, and selection.
_i18n_hash: cacef36e8323b6349fa209a7c057c88b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten in einer Hierarchie von Knoten. Jeder Knoten hat einen eindeutigen Schlüssel und ein Label. Knoten sind so miteinander verbunden, dass sie Eltern-Kind-Beziehungen bilden. Sie können Knoten erweitern oder reduzieren, um deren Kinder anzuzeigen oder zu verbergen. Icons verdeutlichen, um welchen Knotentyp es sich handelt und ob dieser ausgewählt ist. Die Auswahl unterstützt die gleichzeitige Wahl eines oder mehrerer Knoten.

<!-- INTRO_END -->

## Knotenmodell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` <DocChip chip="name" label="dwc-tree-node" /> {#the-role-of-treenode}

Jedes Datenstück im Baum ist in einem `TreeNode` eingebettet. Dieses Objekt speichert den Schlüssel, das Textlabel und Verbindungen zu seinen Eltern- und Kindknoten. Der Wurzelknoten ist besonders: Er existiert in jedem Baum, ist jedoch nicht sichtbar. Er dient als Container für alle obersten Knoten, was die interne Verwaltung der Baumstruktur erleichtert.

Da Knoten Referenzen auf ihre Eltern und Kinder halten, ist das Durchlaufen des Baumes unkompliziert. Egal, ob Sie nach oben, unten oder einen bestimmten Knoten nach Schlüssel suchen möchten, die Verbindungen sind immer zugänglich.

### Knoten erstellen und verwalten {#node-creation-and-management}

Knoten werden mithilfe einfacher Fabrikmethoden erstellt, entweder indem ein Schlüssel und ein Text oder nur Text (der auch als Schlüssel dient) bereitgestellt wird. Dies gewährleistet, dass jeder Knoten gültig und einzigartig identifizierbar ist.

Das Hinzufügen von Knoten zum Baum erfolgt durch den Aufruf von `add()` oder `insert()` auf einem Elternknoten. Diese Methoden übernehmen die Zuweisung der Elternreferenz und benachrichtigen den Baum, dass seine Benutzeroberfläche aktualisiert werden muss.

Beispiel:

```java
TreeNode parent = Tree.node("Eltern");
TreeNode child = Tree.node("Kind");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Wenn Sie versuchen, denselben Knoten mehreren Eltern zuzuweisen, wird eine Ausnahme ausgelöst. Diese Schutzmaßnahme stellt sicher, dass der Baum seine richtige Hierarchie beibehält, indem verhindert wird, dass Knoten mehrere Eltern haben, was die Integrität der Struktur gefährden und unerwartetes Verhalten verursachen würde.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Knoten modifizieren {#modifying-nodes}

Sie aktualisieren das Label eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den Text, der für den Knoten im Baum angezeigt wird.

Um einen bestimmten Kindknoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dadurch wird das Kind von seinem Elternteil getrennt und aus der Baumstruktur entfernt. Es wird auch die Elternreferenz gelöscht.

Wenn Sie alle Kinder von einem Knoten löschen möchten, rufen Sie `removeAll()` auf. Dies entfernt jeden Kindknoten, löscht deren Elternreferenzen und leert die Kindliste.

Jeder Knoten unterstützt die Speicherung zusätzlicher Informationen auf der Serverseite mit `setUserData(Object key, Object data)`. Damit können Sie beliebige Metadaten oder Referenzen mit dem Knoten verknüpfen, ohne diese Daten dem Client oder der Benutzeroberfläche preiszugeben.

:::tip Nutzung der Demo zur Bearbeitung des Knotentextes
In der Demo doppelklicken Sie auf einen Knoten, um einen Editor für seinen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um das Label des Knotens im Baum zu aktualisieren.
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

Icons bieten visuelle Hinweise darauf, welche Art von Knoten dargestellt wird und welchen Zustand sie haben. Sie verbessern die Lesbarkeit, indem sie Knotentypen und Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das globale Setzen von Standard-Icons, die Anpassung von Icons pro Knoten und das Umschalten der Sichtbarkeit von Icons.

### Globale Icons {#global-icons}

Der Baum lässt Sie Standard-Icons für reduzierte Gruppen, erweiterte Gruppen, Blattknoten und ausgewählte Blätter festlegen.

Beispiel:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Icon-Quellen
Ein Icon kann jede gültige webforJ [Icon](./icon)-Definition oder eine Ressourcendatei sein, die über unterstützte Assets-Protokolle von webforJ geladen wird.
:::

### Pro-Knoten-Icons {#per-node-icons}

Sie können globale Vorgaben überschreiben, indem Sie Icons einzelnen Knoten zuweisen. Dies ist nützlich, wenn bestimmte Knoten verschiedene Konzepte repräsentieren, wie „Projekt“-Ordner oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Icon-Sichtbarkeit {#icon-visibility}

Manchmal möchten Sie möglicherweise Icons für Gruppen oder Blätter ausblenden, um Unordnung zu reduzieren. Die Komponente ermöglicht es Ihnen, die Sichtbarkeit global für diese Kategorien umschalten, sodass Sie das Erscheinungsbild des Baums vereinfachen können, ohne die Struktur zu verlieren.

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

## Knoten erweitern und reduzieren {#node-expansion-and-collapse}

Knoten können erweitert oder reduziert werden, um zu steuern, welche Teile des Baums sichtbar sind. Dies ermöglicht es, sich auf relevante Abschnitte zu konzentrieren und unterstützt Szenarien wie Lazy Loading oder dynamische Datenaktualisierungen.

### Erweiterungs- und Reduzierungsoperationen {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Reduzieren einzelner Knoten entweder durch ihren Schlüssel oder durch direkte Referenz. Sie können auch alle Nachkommen eines Knotens auf einmal erweitern oder reduzieren.

Diese Operationen ermöglichen es Ihnen, zu steuern, wie viel vom Baum sichtbar ist, und unterstützen das Lazy Loading von Daten oder die Fokussierung auf interessante Bereiche.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// unterbäume reduzieren
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Wurzel zusammenklappen
Der Wurzelknoten verankert den Baum, bleibt jedoch verborgen. Das Zusammenklappen der Wurzel würde normalerweise alles verbergen und den Baum leer erscheinen lassen. Um dies zu vermeiden, reduziert das Zusammenklappen der Wurzel tatsächlich alle ihre Kinder, behält aber die Wurzel intern erweitert, sodass der Baum seinen Inhalt weiterhin korrekt anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt das Lazy Loading der Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre Anwendung die Kinder dieses Knotens dynamisch laden oder generieren. Dies verbessert die Leistung, indem nur sichtbare Teile des Baums bei Bedarf geladen werden.

Verwenden Sie das `onExpand`-Ereignis, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie im Handler, ob die Kinder des Knotens Platzhalter sind (z. B. ein Spinner oder ein leerer Knoten), und ersetzen Sie sie durch echte Daten, sobald sie geladen sind.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt werden. Die `Tree`-Komponente unterstützt flexible Modi und APIs zum Auswählen, Abwählen und Abfragen von Knoten.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum die Auswahl eines einzelnen Knotens oder mehrerer Knoten gleichzeitig erlaubt. Der Wechsel von der Mehrfach- zur Einzelauswahl hebt automatisch die Auswahl aller bis auf den ersten ausgewählten Knoten auf.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Mehrfachauswahl-Interaktion
Wenn der Baum auf den Mehrfachauswahlmodus eingestellt ist, können Benutzer mehr als einen Knoten gleichzeitig auswählen. Die Art und Weise, wie dies funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer halten die **Strg**-Taste (oder **Cmd**-Taste auf macOS) gedrückt und klicken auf Knoten, um sie zur aktuellen Auswahl hinzuzufügen oder sie davon zu entfernen. Dies ermöglicht es, mehrere einzelne Knoten auszuwählen, ohne andere abzuwählen.
* **Mobile und Touch-Geräte:** Da Modifikatortasten nicht verfügbar sind, tippen Benutzer einfach auf Knoten, um sie auszuwählen oder abzuwählen. Jedes Tippen wechselt den Auswahlstatus dieses Knotens und ermöglicht eine einfache Mehrfachauswahl durch einfache Tipps.
:::

### Auswählen und Abwählen {#selecting-and-deselecting}

Knoten können nach Referenz, Schlüssel, einzeln oder in Gruppen ausgewählt oder abgewählt werden. Sie können auch alle Kinder eines Knotens in einem Aufruf wählen oder abwählen.

Beispiel:

```java
// Knoten nach Referenz oder Schlüssel auswählen
tree.select(node);
tree.selectKey(key);

// Knoten nach Referenz oder Schlüssel abwählen
tree.deselect(node);
tree.deselectAll();

// Kinder von Knoten auswählen oder abwählen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Abrufen des Auswahlstatus {#selection-state-retrieval}

Sie können die aktuelle Auswahl mit dem folgenden Code erhalten:

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

## Styling {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
