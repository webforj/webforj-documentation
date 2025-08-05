---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: cf4917e0ffb74122b24c210ec7502cbf
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Dieser Abschnitt beschreibt gemeinsame Funktionen aller Listenkomponenten und ist keine Klasse, die instanziiert oder direkt verwendet werden kann.
:::

Es stehen drei Arten von Listen zur Verfügung, die in Ihren Apps verwendet werden können: [`ListBox`](listbox), [`ChoiceBox`](choicebox) und [`ComboBox`](combobox). Diese Komponenten zeigen alle eine Liste von Schlüssel-Wert-Elementen an und bieten Methoden zum Hinzufügen, Entfernen, Auswählen und Verwalten der Elemente in der Liste.

Diese Seite umreißt die gemeinsamen Merkmale und das Verhalten aller Listenkomponenten, während spezifische Details für jede auf ihren jeweiligen Seiten behandelt werden.

## Verwendung von `ListItem` {#using-listitem}

Listenkomponenten bestehen aus <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Objekten, die einzelne Elemente innerhalb einer Liste darstellen. Jedes <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> ist mit einem eindeutigen Schlüssel und anzuzeigendem Text verknüpft. Wichtige Merkmale der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Klasse umfassen:

- Ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapselt einen eindeutigen Schlüssel `Object` und einen Text `String`, der innerhalb der Listenkomponente angezeigt wird. 
- Sie können ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> erstellen, indem Sie einen Schlüssel und Text bereitstellen oder indem Sie nur den Text angeben, sodass ein zufälliger Schlüssel generiert wird.

## Verwalten von `ListItem`-Objekten über die API {#managing-listitem-objects-with-the-api}

Die verschiedenen Listenkomponenten bieten mehrere Methoden zum Verwalten der Liste von Elementen und zur Aufrechterhaltung eines konsistenten Zustands zwischen der Liste und dem Client. Durch die Verwendung dieser Methoden können Sie die Elemente innerhalb der Liste effektiv verwalten. Die API ermöglicht es Ihnen, mit der Liste zu interagieren und sie so zu manipulieren, dass sie den Anforderungen Ihrer App entspricht.

### Hinzufügen von Elementen {#adding-items}

- **Hinzufügen eines Elements**:

   - Um ein `ListItem` zur Liste hinzuzufügen, können Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink>-Methode verwenden.
   - Sie können auch ein neues `ListItem` hinzufügen, indem Sie den Schlüssel und den Text über die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink>-Methode angeben.

- **Einfügen eines Elements an einem bestimmten Index:**

   - Um ein Element an einem bestimmten Index einzufügen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink>-Methode.
   - Sie können ein Element mit Schlüssel und Text über die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink>-Methode einfügen.

- **Mehrere Elemente einfügen:** 

   - Sie können mehrere Elemente an einem bestimmten Index mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink>-Methode einfügen.

:::tip
Um die Leistung zu optimieren, ist es effizienter, anstatt bei jedem Aufruf der `add()`-Methode eine Nachricht vom Server an den Client auszulösen, zuerst eine Liste von <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Objekten zu erstellen. Sobald Sie diese Liste haben, können Sie sie alle auf einmal mit der `insert(int index, List<ListItem> items)`-Methode hinzufügen. Dieser Ansatz reduziert die Kommunikation zwischen Server und Client und erhöht die Gesamt-effizienz. Für detaillierte Richtlinien zu diesem und anderen Best Practices in der webforJ-Architektur siehe [Client/Server-Interaktion](/docs/architecture/client-server).
:::

### Entfernen von Elementen {#removing-items}

- **Ein Element entfernen:**

   - Um ein Element aus der Liste zu entfernen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink>- oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink>-Methode.

- **Alle Elemente entfernen:**
   - Sie können alle Elemente aus der Liste mithilfe von <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> entfernen.

### Auswählen von Elementen {#selecting-items}

Alle Listentypen implementieren das `SelectableList`-Interface. Dieses Interface ermöglicht verschiedene Möglichkeiten zur Auswahl des aktuellen `ListItem`.

#### Mit einem gegebenen `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> nimmt ein `ListItem` als Parameter entgegen, um es auszuwählen.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo-Element");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Mit einem gegebenen Schlüssel eines `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> nimmt einen Schlüssel zu einem `ListItem` als Parameter, um es auszuwählen.

```java {3}
List demoList = new List();
demoList.add("demo","Demo-Element");
demoList.selectKey("demo");
```

#### Mit einem gegebenen Index eines `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> nimmt einen Index zu einem `ListItem` als Parameter, um es auszuwählen.

```java {3}
List demoList = new List();
demoList.add("demo","Demo-Element");
demoList.selectIndex(0);
```

### Weitere Listenoperationen {#other-list-operations}

- **Zugriff auf und Aktualisierung von Elementen:**

   - Um auf Elemente nach Schlüssel oder Index zuzugreifen, verwenden Sie <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Sie können den Text eines Elements mit der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink>-Methode innerhalb der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Klasse aktualisieren.

- **Informationen über die Liste abrufen:**
   - Sie können die Größe der Liste mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink>-Methode abrufen.
   - Um zu überprüfen, ob die Liste leer ist, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink>-Methode.

### Über Listen iterieren {#iterating-over-lists}

Alle Listenkomponenten implementieren das Java [`Iterable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html)-Interface, das eine effiziente und intuitive Möglichkeit bietet, durch den Inhalt einer Liste zu iterieren. Mit diesem Interface können Sie ganz einfach jede `ListItem` durchlaufen und auf jedes Element mit minimalem Aufwand zugreifen, es ändern oder Aktionen durchführen. Das `Iterable`-Interface ist ein Standardmuster der Java-Sprache und sorgt dafür, dass Ihr Code vertraut und wartbar für jeden Java-Entwickler ist.

Der folgende Code-Ausschnitt zeigt zwei einfache Möglichkeiten, durch eine Liste zu iterieren:

```java
list.forEach(item -> {
   item.setText("Modifiziert: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modifiziert2: " + item.getText());
}
```

## Gemeinsame Listen-Eigenschaften {#shared-list-properties}

### Beschriftung {#label}

Allen Listenkomponenten kann eine Beschriftung zugewiesen werden, die einen beschreibenden Text oder Titel darstellt, der mit der Komponente verbunden ist. Beschriftungen bieten eine kurze Erläuterung oder Aufforderung, um Benutzern zu helfen, den Zweck oder die erwartete Auswahl für diese bestimmte Liste zu verstehen. Neben ihrer Bedeutung für die Benutzerfreundlichkeit spielen Listenbeschriftungen auch eine entscheidende Rolle für die Barrierefreiheit, da sie es Screenreadern und unterstützenden Technologien ermöglichen, genaue Informationen bereitzustellen und die Navigation über die Tastatur zu erleichtern.

### Hilfetext {#helper-text}

Jede Listenkomponente kann Hilfetext unter der Liste mithilfe der `setHelperText()`-Methode anzeigen. Dieser Hilfetext bietet zusätzlichen Kontext oder Erklärungen zu den verfügbaren Optionen und stellt sicher, dass die Benutzer die notwendigen Informationen haben, um informierte Entscheidungen zu treffen.

### Horizontale Ausrichtung {#horizontal-alignment}

Alle Listenkomponenten implementieren das <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>-Interface, das Ihnen die Kontrolle darüber gibt, wie Text und Inhalte innerhalb der Komponente ausgerichtet sind.

Verwenden Sie die `setHorizontalAlignment()`-Methode, um die Ausrichtung festzulegen:

- `HorizontalAlignment.LEFT` (Standard)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Um die aktuelle Ausrichtung zu erhalten:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Erweiterungen {#expanses}

Alle Listenkomponenten in webforJ implementieren auch das <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink>-Interface, das es Ihnen ermöglicht, die gesamte Größe und das visuelle Gewicht der Komponente anzupassen. Dies ist nützlich, um die Komponente an verschiedene UI-Kontexte wie Formulare, Dialoge, Seitenleisten usw. anzupassen.

Verwenden Sie die `setExpanse()`-Methode, um das Expansionsniveau festzulegen. Optionen sind:

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` (Standard)
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setExpanse(Expanse.LARGE);
```

Sie können die aktuelle Einstellung wie folgt abrufen:
```java
Expanse current = listBox.getExpanse();
```

## Themen {#topics}

<DocCardList className="topics-section" />
