---
title: Tree
sidebar_position: 150
_i18n_hash: 2302136423928266f164623de9a234b4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten als Hierarchie von Knoten. Jeder Knoten hat einen eindeutigen Schlüssel und ein Label. Knoten verbinden sich zu eltern-kind Beziehungen. Sie können Knoten erweitern oder zusammenklappen, um deren Kinder anzuzeigen oder zu verbergen. Icons verdeutlichen, um welche Art von Knoten es sich handelt und ob dieser ausgewählt ist. Die Auswahl unterstützt die gleichzeitige Auswahl eines oder mehrerer Knoten.

<!-- INTRO_END -->

## Node-Modell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` {#the-role-of-treenode}

Jedes Datenstück im Baum ist in einem `TreeNode` eingewickelt. Dieses Objekt enthält den Schlüssel, das Textlabel und Verweise auf seine Eltern- und Kindknoten. Der Wurzelknoten ist besonders: Er existiert in jedem Baum, ist aber nicht sichtbar. Er dient als Container für alle Objekte der obersten Ebene und erleichtert die interne Verwaltung der Baumstruktur.

Da Knoten Referenzen zu ihren Eltern und Kindern behalten, ist das Durchqueren des Baums unkompliziert. Egal, ob Sie nach oben oder unten gehen oder einen bestimmten Knoten nach Schlüssel finden möchten, die Verbindungen sind immer zugänglich.

### Knoten erstellen und verwalten {#node-creation-and-management}

Knoten werden mit einfachen Fabrikmethoden erstellt, entweder indem ein Schlüssel und Text oder nur Text (der auch als Schlüssel dient) bereitgestellt werden. Dies garantiert, dass jeder Knoten gültig und eindeutig identifizierbar ist.

Knoten zum Baum hinzuzufügen, geschieht durch Aufruf von `add()` oder `insert()` auf einem Elternknoten. Diese Methoden übernehmen die Zuordnung der Elternreferenz und benachrichtigen den Baum, dass seine Benutzeroberfläche aktualisiert werden muss.

Beispiel:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Der Versuch, denselben Knoten mehreren Eltern zuzuweisen, führt zu einer Ausnahme. Diese Sicherheitsmaßnahme stellt sicher, dass der Baum eine korrekte Hierarchie beibehält, indem verhindert wird, dass Knoten mehrere Eltern haben, was die Integrität der Struktur brechen und unerwartetes Verhalten verursachen würde.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knoten modifizieren {#modifying-nodes}

Sie aktualisieren das Label eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den für den Knoten im Baum angezeigten Text.

Um einen bestimmten Kindknoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dies trennt das Kind von seinem Elternteil und entfernt es aus der Baumstruktur. Es räumt auch die Elternreferenz auf.

Wenn Sie alle Kinder von einem Knoten löschen möchten, rufen Sie `removeAll()` auf. Dies entfernt jeden Kindknoten, leert deren Elternreferenzen und leert die Kinderliste.

Jeder Knoten unterstützt das Speichern zusätzlicher Informationen auf der Serverseite mit `setUserData(Object key, Object data)`. Dies ermöglicht Ihnen, beliebige Metadaten oder Verweise mit dem Knoten zu verknüpfen, ohne diese Daten dem Client oder der Benutzeroberfläche zugänglich zu machen.

:::tip Verwenden der Demo zum Bearbeiten des Knotentexts
In der Demo doppelklicken Sie auf einen Knoten, um einen Editor für dessen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um das Label des Knotens im Baum zu aktualisieren.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Icons {#icons}

Icons bieten visuelle Hinweise darauf, was Knoten darstellen und ihren Status. Sie verbessern die Lesbarkeit, indem sie Knotentypen und Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das globales Festlegen von Standardicons, das Anpassen von Icons pro Knoten und das Umschalten der Icon-Sichtbarkeit.

### Globale Icons {#global-icons}

Der Baum ermöglicht es Ihnen, standardisierte Icons für zusammengeklappte Gruppen, erweiterte Gruppen, Blattknoten und ausgewählte Blätter festzulegen.

Beispiel:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Icon-Quellen
Ein Icon kann jede gültige webforJ [Icon](./icon)-Definition oder eine Ressourcen-Datei sein, die über unterstützte webforJ [Assets-Protokolle](../managing-resources/assets-protocols) geladen wird.
:::

### Per-Knoten-Icons {#per-node-icons}

Sie können globale Standards durch die Zuordnung von Icons zu einzelnen Knoten außer Kraft setzen. Dies ist nützlich, wenn bestimmte Knoten unterschiedliche Konzepte repräsentieren, wie z. B. "Projekt"-Ordner oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Icon-Sichtbarkeit {#icon-visibility}

Manchmal möchten Sie möglicherweise die Icons für Gruppen oder Blätter ausblenden, um die Übersichtlichkeit zu reduzieren. Die Komponente lässt Sie die Sichtbarkeit dieser Kategorien global umschalten, wodurch Sie das Erscheinungsbild des Baums vereinfachen können, ohne die Struktur zu verlieren.

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

## Knoten erweitern und zusammenklappen {#node-expansion-and-collapse}

Knoten können erweitert oder zusammengeklappt werden, um zu steuern, welche Teile des Baumes sichtbar sind. Dies ermöglicht es, sich auf relevante Abschnitte zu konzentrieren und unterstützt Szenarien wie das Lazy Loading oder dynamische Datenaktualisierungen.

### Erweitern- und Zusammenklappen-Operationen {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Zusammenklappen einzelner Knoten entweder über ihren Schlüssel oder direkten Verweis. Sie können auch alle Nachfahren eines Knotens auf einmal erweitern oder zusammenklappen.

Diese Operationen ermöglichen Ihnen, zu steuern, wie viel vom Baum sichtbar ist und unterstützen das Lazy Loading von Daten oder die Fokussierung auf interessante Bereiche.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// unterbäumen zusammenklappen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Wurzel zusammenklappen
Der Wurzelknoten verankert den Baum, bleibt jedoch verborgen. Das Zusammenklappen der Wurzel würde normalerweise alles verbergen und den Baum leer erscheinen lassen. Um dies zu vermeiden, klappt das Zusammenklappen der Wurzel tatsächlich alle ihre Kinder zusammen, hält die Wurzel jedoch intern erweitert, sodass der Baum seinen Inhalt weiterhin korrekt anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt das Lazy Loading von Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre Anwendung die Kinder dieses Knotens dynamisch laden oder generieren. Dies verbessert die Leistung, indem nur die sichtbaren Teile des Baums nach Bedarf geladen werden.

Verwenden Sie das `onExpand`-Ereignis, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie im Handler, ob die Kinder des Knotens Platzhalter (z. B. ein Lade-Symbol oder einen leeren Knoten) sind, und ersetzen Sie sie durch tatsächliche Daten, sobald sie geladen sind.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt werden. Die `Tree`-Komponente unterstützt flexible Modi und APIs, um Knoten auszuwählen, abzuwählen und abzufragen.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum die Auswahl von nur einem Knoten zurzeit oder mehreren Knoten gleichzeitig erlaubt. Der Wechsel von der Mehrfachauswahl zur Einzelauswahl hebt automatisch die Auswahl aller außer dem zuerst ausgewählten Knoten auf.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaktion mit Mehrfachauswahl
Wenn der Baum auf den Modus Mehrfachauswahl eingestellt ist, können die Benutzer mehr als einen Knoten gleichzeitig auswählen. Wie dies funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer drücken die **Strg**-Taste (oder **Cmd**-Taste bei macOS) und klicken auf Knoten, um diese zur aktuellen Auswahl hinzuzufügen oder zu entfernen. Auf diese Weise können mehrere einzelne Knoten ausgewählt werden, ohne andere Deselects.
* **Mobile und Touch-Geräte:** Da Modifikatortasten nicht verfügbar sind, tippen die Benutzer einfach auf Knoten, um diese auszuwählen oder abzuwählen. Jeder Tipp wechselt den Auswahlstatus dieses Knotens und ermöglicht eine einfache Mehrfachauswahl durch einfache Tipps.
:::

### Auswählen und Abwählen {#selecting-and-deselecting}

Knoten können durch Verweis, Schlüssel, einzeln oder in Gruppen ausgewählt oder abgewählt werden. Sie können auch alle Kinder eines Knotens in einem Aufruf auswählen oder abwählen.

Beispiel:

```java
// Knoten nach Verweis oder Schlüssel auswählen
tree.select(node);
tree.selectKey(key);

// Knoten nach Verweis oder Schlüssel abwählen
tree.deselect(node);
tree.deselectAll();

// Auswählen oder Abwählen von Kindknoten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Abfrage des Auswahlstatus {#selection-state-retrieval}

Sie können die aktuelle Auswahl mit dem folgenden Code abrufen:

```java
// Referenz des ausgewählten Knotens erhalten
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// Schlüssel des ausgewählten Knotens abrufen
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Styling {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
