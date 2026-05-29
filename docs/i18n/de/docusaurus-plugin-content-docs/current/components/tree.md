---
title: Tree
sidebar_position: 150
_i18n_hash: dacd1e2a128f112d2b7e4a4fd7836feb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten in einer Hierarchie von Knoten. Jeder Knoten hat einen eindeutigen Schlüssel und eine Bezeichnung. Knoten verbinden sich, um Eltern-Kind-Beziehungen zu bilden. Sie können Knoten erweitern oder reduzieren, um deren Kinder anzuzeigen oder zu verbergen. Symbole verdeutlichen, um welche Art von Knoten es sich handelt und ob er ausgewählt ist. Die Auswahl unterstützt die Auswahl eines Knotens oder mehrerer auf einmal.

<!-- INTRO_END -->

## Knotenmodell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` {#the-role-of-treenode}

Jedes Datenstück im Baum ist in einem `TreeNode` eingekapselt. Dieses Objekt enthält den Schlüssel, die Textbezeichnung und Verlinkungen zu seinen Eltern- und Kindknoten. Der Wurzelknoten ist etwas Besonderes: Er existiert in jedem Baum, ist aber nicht sichtbar. Er dient als Container für alle obersten Knoten und erleichtert das interne Management der Baumstruktur.

Da Knoten Referenzen zu ihren Eltern und Kindern behalten, ist das Durchqueren des Baumes unkompliziert. Egal, ob Sie nach oben, nach unten gehen oder einen bestimmten Knoten anhand des Schlüssels finden wollen, die Verbindungen sind stets zugänglich.

### Knoten erstellen und verwalten {#node-creation-and-management}

Knoten werden mithilfe einfacher Fabrikmethoden erstellt, entweder durch Angabe eines Schlüssels und Textes oder nur durch Text (der auch als Schlüssel dient). Dies gewährleistet, dass jeder Knoten gültig und eindeutig identifizierbar ist.

Das Hinzufügen von Knoten zum Baum erfolgt durch den Aufruf von `add()` oder `insert()` auf einem Elternknoten. Diese Methoden kümmern sich um die Zuordnung der Elternreferenz und benachrichtigen den Baum, um seine Benutzeroberfläche zu aktualisieren.

Beispiel:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Der Versuch, denselben Knoten mehreren Eltern zuzuweisen, führt zu einer Ausnahme. Diese Sicherheitsmaßnahme stellt sicher, dass der Baum eine ordnungsgemäße Hierarchie beibehält, indem verhindert wird, dass Knoten mehrere Eltern haben, was die Integrität der Struktur gefährden und unerwartetes Verhalten verursachen würde.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Knoten modifizieren {#modifying-nodes}

Sie aktualisieren die Bezeichnung eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den Text, der für den Knoten im Baum angezeigt wird.

Um einen bestimmten Kindknoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dies trennt das Kind von seinem Elternteil und entfernt es aus der Baumstruktur. Es löscht auch die Elternreferenz.

Wenn Sie alle Kinder von einem Knoten löschen möchten, rufen Sie `removeAll()` auf. Dies entfernt jeden Kindknoten, löscht deren Elternreferenzen und leert die Liste der Kinder.

Jeder Knoten unterstützt das Speichern zusätzlicher Informationen auf der Serverseite mit `setUserData(Object key, Object data)`. Dadurch können Sie beliebige Metadaten oder Verweise mit dem Knoten verknüpfen, ohne diese Daten dem Client oder der UI auszusetzen.

:::tip Demo verwenden, um Knotentext zu bearbeiten
Doppelklicken Sie in der Demo auf einen Knoten, um einen Editor für dessen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um die Bezeichnung des Knotens im Baum zu aktualisieren.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/resources/static/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Symbole {#icons}

Symbole bieten visuelle Hinweise darauf, was Knoten darstellen und welchen Status sie haben. Sie verbessern die Lesbarkeit, indem sie Knotenarten und Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das Festlegen von Standard-Symbolen global, das Anpassen von Symbolen pro Knoten und das Umschalten der Sichtbarkeit von Symbolen.

### Globale Symbole {#global-icons}

Der Baum ermöglicht es Ihnen, Standard-Symbole für reduzierte Gruppen, erweiterte Gruppen, Blattknoten und ausgewählte Blätter festzulegen.

Beispiel:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Symbolquellen
Ein Symbol kann jede gültige webforJ [Symbol](./icon)-Definition oder eine Ressourcendatei sein, die über ein von webforJ [unterstütztes Ressourcenprotokoll](../managing-resources/assets-protocols) geladen wird.
:::

### Symbole pro Knoten {#per-node-icons}

Sie können globale Standardwerte überschreiben, indem Sie Symbole für einzelne Knoten zuweisen. Dies ist nützlich, wenn bestimmte Knoten verschiedene Konzepte darstellen, wie etwa "Projekt"-Ordner oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Sichtbarkeit der Symbole {#icon-visibility}

Manchmal möchten Sie die Symbole für Gruppen oder Blätter ausblenden, um Unordnung zu reduzieren. Die Komponente lässt Sie die Sichtbarkeit global für diese Kategorien umschalten, sodass Sie das Erscheinungsbild des Baumes vereinfachen können, ohne die Struktur zu verlieren.

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

Knoten können erweitert oder reduziert werden, um zu steuern, welche Teile des Baumes sichtbar sind. Dies ermöglicht es, sich auf relevante Abschnitte zu konzentrieren und unterstützt Szenarien wie Lazy Loading oder dynamische Datenaktualisierungen.

### Expandieren und Reduzieren von Operationen {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Reduzieren einzelner Knoten entweder durch ihren Schlüssel oder durch direkte Referenz. Sie können auch alle Nachfahren eines Knotens auf einmal erweitern oder reduzieren.

Diese Operationen ermöglichen es Ihnen, zu steuern, wie viel des Baumes sichtbar ist, und unterstützen das Lazy Loading von Daten oder den Fokus auf interessante Bereiche.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// Unterbäume reduzieren
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Wurzel reduzieren
Der Wurzelknoten verankert den Baum, bleibt jedoch verborgen. Das Reduzieren der Wurzel würde normalerweise alles verbergen, wodurch der Baum leer erscheinen würde. Um dies zu vermeiden, reduziert das Reduzieren der Wurzel tatsächlich alle ihre Kinder, während die Wurzel intern erweitert bleibt, um sicherzustellen, dass der Baum seinen Inhalt weiterhin korrekt anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt das Lazy Loading von Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre App die Kinder dieses Knotens dynamisch laden oder generieren. Dies verbessert die Leistung, indem nur die sichtbaren Teile des Baumes bei Bedarf geladen werden.

Verwenden Sie das `onExpand`-Ereignis, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie im Handler, ob die Kinder des Knotens Platzhalter sind (zum Beispiel ein Spinner oder leerer Knoten), und ersetzen Sie sie durch tatsächliche Daten, sobald sie geladen sind.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt werden. Die `Tree`-Komponente unterstützt flexible Modi und APIs zum Auswählen, Abwählen und Abfragen von Knoten.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum das Auswählen eines einzelnen Knotens zur gleichen Zeit oder mehrerer Knoten gleichzeitig zulässt. Der Wechsel von mehreren zu einer einzelnen Auswahl hebt automatisch die Auswahl aller bis auf den ersten ausgewählten Knoten auf.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaktion bei Mehrfachauswahl
Wenn der Baum im Modus zur Mehrfachauswahl eingestellt ist, können Benutzer mehr als einen Knoten gleichzeitig auswählen. Wie dies funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer halten die **Strg**-Taste (oder **Cmd**-Taste auf macOS) gedrückt und klicken auf Knoten, um sie zur aktuellen Auswahl hinzuzufügen oder zu entfernen. Dadurch können mehrere einzelne Knoten ausgewählt werden, ohne andere abzuwählen.
* **Mobile und Touch-Geräte:** Da Modifikatortasten nicht verfügbar sind, tippen Benutzer einfach auf Knoten, um sie auszuwählen oder abzuwählen. Jeder Tap wechselt den Auswahlstatus dieses Knotens und ermöglicht eine einfache Mehrfachauswahl durch einfaches Tippen.
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

// Kinder von Knoten auswählen oder abwählen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Abfrage des Auswahlstatus {#selection-state-retrieval}

Sie können die aktuelle Auswahl mit folgendem Code abrufen:

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
