---
title: Tree
sidebar_position: 150
_i18n_hash: 6d2decdf16e3054012a22aca28980ccf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten als Hierarchie von Knoten. Jeder Knoten enthält einen eindeutigen Schlüssel und ein Etikett. Knoten verbinden sich, um Eltern-Kind-Beziehungen zu bilden. Sie können Knoten erweitern oder Collapsen, um deren Kinder anzuzeigen oder zu verbergen. Icons verdeutlichen, welche Art von Knoten Sie betrachten und ob er ausgewählt ist. Die Auswahl unterstützt die Wahl eines oder mehrerer Knoten gleichzeitig.

<!-- INTRO_END -->

## Knotenmodell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` {#the-role-of-treenode}

Jedes Datenstück im Baum ist in einem `TreeNode` verpackt. Dieses Objekt hält den Schlüssel, das Textetikett und Links zu seinen Eltern- und Kindknoten. Der Wurzelknoten ist besonders: Er existiert in jedem Baum, ist jedoch nicht sichtbar. Er dient als Container für alle obersten Knoten und erleichtert die interne Verwaltung der Baumstruktur.

Da Knoten Referenzen zu ihren Eltern und Kindern behalten, ist das Durchlaufen des Baums unkompliziert. Egal, ob Sie nach oben, unten oder nach einem bestimmten Knoten anhand des Schlüssels suchen möchten, die Verbindungen sind immer zugänglich.

### Knoten erstellen und verwalten {#node-creation-and-management}

Knoten werden mit einfachen Fabrikmethoden erstellt, entweder indem ein Schlüssel und ein Text bereitgestellt oder nur der Text (der gleichzeitig als Schlüssel fungiert) angegeben wird. Dies stellt sicher, dass jeder Knoten gültig und eindeutig identifizierbar ist.

Das Hinzufügen von Knoten zum Baum erfolgt durch den Aufruf von `add()` oder `insert()` auf einem Elternknoten. Diese Methoden kümmern sich um die Zuweisung der Elternreferenz und benachrichtigen den Baum, um seine Benutzeroberfläche zu aktualisieren.

Beispiel:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Der Versuch, denselben Knoten mehr als einem Elternteil zuzuweisen, führt zu einer Ausnahme. Diese Sicherheitsmaßnahme stellt sicher, dass der Baum eine ordnungsgemäße Hierarchie aufrechterhält, indem verhindert wird, dass Knoten mehrere Eltern haben, was die Integrität der Struktur brechen und unerwartetes Verhalten verursachen würde.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knoten modifizieren {#modifying-nodes}

Sie aktualisieren das Etikett eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den Text, der für den Knoten im Baum angezeigt wird.

Um einen speziellen Kindknoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dies trennt das Kind von seinem Elternteil und entfernt es aus der Baumstruktur. Es wird auch die Elternreferenz gelöscht.

Wenn Sie alle Kinder von einem Knoten entfernen möchten, rufen Sie `removeAll()` auf. Dies entfernt jeden Kindknoten, löscht deren Elternreferenzen und leert die Liste der Kinder.

Jeder Knoten unterstützt das Speichern zusätzlicher Informationen auf der Serverseite mit `setUserData(Object key, Object data)`. Dies ermöglicht es Ihnen, beliebige Metadaten oder Referenzen mit dem Knoten zu verknüpfen, ohne diese Daten dem Client oder der Benutzeroberfläche offenzulegen.

:::tip Demo zur Bearbeitung von Knotentext
In der Demo doppelklicken Sie auf einen Knoten, um einen Editor für seinen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um das Etikett des Knotens im Baum zu aktualisieren.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
cssURL='/css/tree/tree-modify-view.css'
height='320px'
/>

## Icons {#icons}

Icons bieten visuelle Hinweise darauf, was Knoten darstellen und deren Status. Sie verbessern die Lesbarkeit, indem sie Knotenarten und Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das Festlegen von Standardicons global, das Anpassen von Icons pro Knoten und das Umschalten der Iconsichtbarkeit.

### Globale Icons {#global-icons}

Der Baum ermöglicht es Ihnen, Standardicons für zusammengeklappte Gruppen, erweiterte Gruppen, Blattknoten und ausgewählte Blätter festzulegen.

Beispiel:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Icon-Quellen
Ein Icon kann jede gültige webforJ [Icon](./icon)-Definition oder eine Ressourcen-Datei sein, die über die von webforJ unterstützten [Assets-Protokolle](../managing-resources/assets-protocols) geladen wird.
:::

### Icons pro Knoten {#per-node-icons}

Sie können globale Vorgaben überschreiben, indem Sie Icons einzelnen Knoten zuweisen. Dies ist nützlich, wenn bestimmte Knoten unterschiedliche Konzepte darstellen, wie „Projekt“-Ordner oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Iconsichtbarkeit {#icon-visibility}

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

## Knoten erweitern und zusammenklappen {#node-expansion-and-collapse}

Knoten können erweitert oder zusammengeklappt werden, um zu steuern, welche Teile des Baums sichtbar sind. Dies ermöglicht eine Fokussierung auf relevante Abschnitte und unterstützt Szenarien wie Lazy Loading oder dynamische Datenaktualisierungen.

### Erweiterungs- und Zusammenklappoperationen {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Zusammenklappen einzelner Knoten entweder anhand ihres Schlüssels oder durch direkte Referenz. Sie können auch alle Nachfahren eines Knotens gleichzeitig erweitern oder zusammenklappen.

Diese Operationen ermöglichen es Ihnen, zu steuern, wie viel des Baums sichtbar ist, und unterstützen das Lazy Loading von Daten oder die Fokussierung auf Interessensgebiete.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// Unterbäume zusammenklappen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Zusammenklappen der Wurzel
Der Wurzelknoten verankert den Baum, bleibt jedoch verborgen. Das Zusammenklappen der Wurzel würde normalerweise alles verbergen, wodurch der Baum leer erscheint. Um dies zu vermeiden, wird beim Zusammenklappen der Wurzel tatsächlich alle ihre Kinder zusammengeklappt, während die Wurzel intern expanded bleibt, sodass der Baum seinen Inhalt korrekt anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt das Lazy Loading von Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre App die Kinder dieses Knotens dynamisch laden oder generieren. Dies verbessert die Leistung, indem nur die sichtbaren Teile des Baums auf Anfrage geladen werden.

Verwenden Sie das `onExpand`-Ereignis, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie innerhalb des Handlers, ob die Kinder des Knotens Platzhalter sind (zum Beispiel ein Spinner oder leerer Knoten) und ersetzen Sie sie mit tatsächlichen Daten, sobald sie geladen sind.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt sind. Die `Tree`-Komponente unterstützt flexible Modi und APIs, um Knoten auszuwählen, abzuwählen und abzufragen.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum die Auswahl eines einzelnen Knotens gleichzeitig oder mehrerer Knoten unterstützt. Der Wechsel von mehreren zu einer einzelnen Auswahl hebt automatisch alle bis auf den ersten ausgewählten Knoten auf.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaktion mit Mehrfachauswahl
Wenn der Baum auf den Mehrfachauswahlmodus eingestellt ist, können Benutzer mehr als einen Knoten gleichzeitig auswählen. Wie dies funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer halten die **Strg**-Taste (oder die **Cmd**-Taste auf macOS) und klicken auf Knoten, um sie zur aktuellen Auswahl hinzuzufügen oder daraus zu entfernen. Dies ermöglicht die Auswahl mehrerer einzelner Knoten, ohne andere abzuwählen.
* **Mobile und Touch-Geräte:** Da Modifikatortasten nicht verfügbar sind, tippen Benutzer einfach auf Knoten, um sie auszuwählen oder abzuwählen. Jeder Tipp wechselt den Auswahlstatus dieses Knotens, wodurch eine einfache Mehrfachauswahl durch einfaches Tippen ermöglicht wird.
:::

### Auswählen und Abwählen {#selecting-and-deselecting}

Knoten können nach Referenz, Schlüssel, einzeln oder in Gruppen ausgewählt oder abgewählt werden. Sie können auch alle Kinder eines Knotens in einem Aufruf auswählen oder abwählen.

Beispiel:

```java
// Selektieren Sie den Knoten nach Referenz oder Schlüssel
tree.select(node);
tree.selectKey(key);

// Deselect Knoten nach Referenz oder Schlüssel
tree.deselect(node);
tree.deselectAll();

// Auswählen oder Abwählen von Kindern von Knoten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Abrufen des Auswahlstatus {#selection-state-retrieval}

Sie können die aktuelle Auswahl erhalten, indem Sie den unten angezeigten Code verwenden:

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

<TableBuilder name={['Tree', 'TreeNode']} />
