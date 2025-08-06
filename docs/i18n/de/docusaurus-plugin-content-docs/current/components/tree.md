---
title: Tree
sidebar_position: 150
_i18n_hash: b161d0d5855f65cb593cf23bc2695d5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

Die `Tree`-Komponente organisiert Daten als eine Hierarchie von Knoten. Jeder Knoten hält einen eindeutigen Schlüssel und ein Label. Knoten verbinden sich, um Eltern-Kind-Beziehungen zu bilden. Sie können Knoten erweitern oder reduzieren, um deren Kinder anzuzeigen oder auszublenden. Icons verdeutlichen, um welchen Knotentyp es sich handelt und ob er ausgewählt ist. Die Auswahl unterstützt das gleichzeitige Wählen eines oder mehrerer Knoten.

## Node-Modell und Baumstruktur {#node-model-and-tree-structure}

### Die Rolle von `TreeNode` {#the-role-of-treenode}

Jedes Datenelement im Baum ist in einem `TreeNode` eingeschlossen. Dieses Objekt hält den Schlüssel, das Textlabel und Verweise auf seine Eltern- und Kindknoten. Der Wurzelknoten ist besonders: Er existiert in jedem Baum, ist aber nicht sichtbar. Er dient als Container für alle obersten Knoten und macht die Baumstruktur interner leichter zu verwalten.

Da Knoten Verweise auf ihre Eltern und Kinder halten, ist das Durchlaufen des Baums unkompliziert. Egal, ob Sie nach oben, unten oder einen bestimmten Knoten nach Schlüssel suchen möchten, die Verbindungen sind immer zugänglich.

### Knoten erstellen und verwalten {#node-creation-and-management}

Knoten werden mit einfachen Fabrikmethoden erstellt, entweder durch Bereitstellung eines Schlüssels und Textes oder nur eines Textes (der als Schlüssel genutzt wird). Dies garantiert, dass jeder Knoten gültig und eindeutig identifizierbar ist.

Das Hinzufügen von Knoten zum Baum erfolgt durch den Aufruf von `add()` oder `insert()` auf einem Elternknoten. Diese Methoden kümmern sich um die Zuordnung des Elternverweises und benachrichtigen den Baum, seine Benutzeroberfläche zu aktualisieren.

Beispiel:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Nur ein Elternteil
Der Versuch, dasselbe Kind mehr als einem Elternteil zuzuweisen, führt zu einer Ausnahme. Diese Schutzmaßnahme stellt sicher, dass der Baum eine ordnungsgemäße Hierarchie beibehält, indem verhindert wird, dass Knoten mehrere Eltern haben, was die Integrität der Struktur beeinträchtigen und unerwartetes Verhalten verursachen würde.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knoten modifizieren {#modifying-nodes}

Sie aktualisieren das Label eines Knotens, indem Sie `setText(String text)` aufrufen. Diese Methode ändert den angezeigten Text für den Knoten im Baum.

Um einen bestimmten Kindknoten zu entfernen, verwenden Sie `remove(TreeNode child)`. Dies trennt das Kind von seinem Elternteil und entfernt es aus der Baumstruktur. Es wird auch der Elternverweis gelöscht.

Wenn Sie alle Kinder von einem Knoten entfernen möchten, rufen Sie `removeAll()` auf. Dadurch werden alle Kindknoten entfernt, ihre Elternverweise gelöscht und die Kinderliste geleert.

Jeder Knoten unterstützt das Speichern zusätzlicher Informationen auf der Serverseite mithilfe von `setUserData(Object key, Object data)`. Dies ermöglicht es Ihnen, beliebige Metadaten oder Verweise mit dem Knoten zu verknüpfen, ohne diese Daten dem Client oder der Benutzeroberfläche offenzulegen.

:::tip Nutzung der Demo zur Bearbeitung des Knotentexts
In der Demo doppelklicken Sie auf einen Knoten, um einen Editor für seinen Text zu öffnen. Geben Sie den neuen Text ein und speichern Sie ihn, um das Label des Knotens im Baum zu aktualisieren.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Icons {#icons}

Icons bieten visuelle Hinweise darauf, was Knoten darstellen und in welchem Zustand sie sich befinden. Sie verbessern die Lesbarkeit, indem sie Knotentypen und den Auswahlstatus auf einen Blick unterscheiden. Die `Tree`-Komponente unterstützt das Setzen von Standardicons global, die Anpassung von Icons pro Knoten und das Umschalten der Icon-Sichtbarkeit.

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
Ein Icon kann eine gültige webforJ [Icon](./icon)-Definition oder eine Ressourcen-Datei sein, die über webforJ [unterstützte Ressourcenprotokolle](../managing-resources/assets-protocols) geladen wird.
:::

### Icons pro Knoten {#per-node-icons}

Sie können die globalen Standards überschreiben, indem Sie Icons einzelnen Knoten zuordnen. Dies ist nützlich, wenn bestimmte Knoten unterschiedliche Konzepte darstellen, wie „Projekt“-Ordner oder spezielle Dateien.

Beispiel:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Icon-Sichtbarkeit {#icon-visibility}

Manchmal möchten Sie vielleicht Icons für Gruppen oder Blätter ausblenden, um Unordnung zu reduzieren. Die Komponente ermöglicht es Ihnen, die Sichtbarkeit global für diese Kategorien umzuschalten, sodass Sie das Erscheinungsbild des Baums vereinfachen können, ohne die Struktur zu verlieren.

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

Knoten können erweitert oder reduziert werden, um zu steuern, welche Teile des Baums sichtbar sind. Dies ermöglicht es, sich auf relevante Abschnitte zu konzentrieren und unterstützt Szenarien wie das Lazy Loading oder dynamische Datenaktualisierungen.

### Erweiterungs- und Reduktionsoperationen {#expand-and-collapse-operations}

Der Baum unterstützt das Erweitern und Reduzieren einzelner Knoten entweder durch ihren Schlüssel oder durch direkten Verweis. Sie können auch alle Nachkommen eines Knotens auf einmal erweitern oder reduzieren.

Diese Operationen ermöglichen eine Kontrolle darüber, wie viel des Baums sichtbar ist, und unterstützen das Lazy Loading von Daten oder das Fokussieren auf Interessensgebiete.

Beispiel:

```java
tree.expand(node);
tree.collapse(key);

// Unterbäume reduzieren
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Wurzel reduzieren
Der Wurzelknoten verankert den Baum, bleibt aber verborgen. Das Reduzieren der Wurzel würde normalerweise alles ausblenden, wodurch der Baum leer erscheinen würde. Um dies zu vermeiden, reduziert das Reduzieren der Wurzel tatsächlich alle ihre Kinder, hält jedoch die Wurzel intern erweitert, um sicherzustellen, dass der Baum seinen Inhalt weiterhin richtig anzeigt.
:::

### Lazy Loading von Knoten {#lazy-loading-nodes}

Der Baum unterstützt das Lazy Loading von Kindknoten, indem er auf Erweiterungsereignisse reagiert. Wenn ein Benutzer einen Knoten erweitert, kann Ihre Anwendung die Kinder dieses Knotens dynamisch laden oder generieren. Dies verbessert die Leistung, indem nur sichtbare Teile des Baums auf Anfrage geladen werden.

Verwenden Sie das `onExpand`-Ereignis, um zu erkennen, wann ein Knoten erweitert wird. Überprüfen Sie im Handler, ob die Kinder des Knotens Platzhalter (zum Beispiel einen Ladeindikator oder einen leeren Knoten) sind, und ersetzen Sie diese durch tatsächliche Daten, sobald sie geladen sind.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Auswahl {#selection}

Die Auswahl steuert, welche Knoten vom Benutzer ausgewählt werden. Die `Tree`-Komponente unterstützt flexible Modi und APIs, um Knoten auszuwählen, abzuwählen und abzufragen.

### Auswahlmodi {#selection-modes}

Sie können wählen, ob der Baum die Auswahl eines einzelnen Knotens zur gleichen Zeit oder mehrerer Knoten gleichzeitig zulässt. Der Wechsel von der Mehrfachauswahl zur Einfachauswahl hebt automatisch alle bis auf den ersten ausgewählten Knoten auf.

Beispiel:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Interaktion mit Mehrfachauswahl
Wenn der Baum auf den Mehrfachauswahlmodus eingestellt ist, können Benutzer mehr als einen Knoten gleichzeitig auswählen. Die Art und Weise, wie dies funktioniert, hängt vom Gerät ab:

* **Desktop (Maus und Tastatur):** Benutzer halten die **Strg**-Taste (oder die **Cmd**-Taste auf macOS) gedrückt und klicken auf Knoten, um diese zur aktuellen Auswahl hinzuzufügen oder daraus zu entfernen. Dadurch können mehrere einzelne Knoten ausgewählt werden, ohne andere abzuwählen.
* **Mobile und Touch-Geräte:** Da Modifikatortasten nicht verfügbar sind, tippen Benutzer einfach auf Knoten, um diese auszuwählen oder abzuwählen. Jeder Tipp schaltet den Auswahlstatus dieses Knotens um, sodass eine einfache Mehrfachauswahl durch einfache Tipps ermöglicht wird.
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

// Kinder von Knoten auswählen oder abwählen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Auswahlstatus abrufen {#selection-state-retrieval}

Sie können die aktuelle Auswahl abrufen, indem Sie den unten angezeigten Code verwenden:

```java
// den Verweis auf den ausgewählten Knoten abrufen
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
