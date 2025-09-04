---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: 15effbe238b9ea86c975499ed2faa30b
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Dieser Abschnitt beschreibt gemeinsame Funktionen aller Listenkomponenten und ist keine Klasse, die instanziiert oder direkt verwendet werden kann.
:::

Es gibt drei Arten von Listen, die in Ihren Apps verwendet werden können: [`ListBox`](listbox), [`ChoiceBox`](choicebox) und [`ComboBox`](combobox). Diese Komponenten zeigen alle eine Liste von Schlüssel-Wert-Elementen an und bieten Methoden zum Hinzufügen, Entfernen, Auswählen und Verwalten der Elemente innerhalb der Liste.

Diese Seite beschreibt die gemeinsamen Eigenschaften und das Verhalten aller Listenkomponenten, während spezifische Details zu jeder Komponente auf ihren jeweiligen Seiten behandelt werden.

## Verwendung von `ListItem` {#using-listitem}

Listenkomponenten bestehen aus <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Objekten, die einzelne Elemente innerhalb einer Liste repräsentieren. Jedes <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> ist mit einem eindeutigen Schlüssel und einem Anzeigetext verbunden. Wichtige Merkmale der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Klasse sind:

- Ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapselt einen eindeutigen Schlüssel `Object` und einen Text `String`, der innerhalb der Listenkomponente angezeigt wird. 
- Sie können ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> erstellen, indem Sie einen Schlüssel und einen Text angeben oder nur den Text angeben, sodass ein zufällig generierter Schlüssel erzeugt wird.

## Verwaltende `ListItem`-Objekte mit der API {#managing-listitem-objects-with-the-api}

Die verschiedenen Listenkomponenten bieten mehrere Methoden zum Verwalten der Liste von Elementen und zur Beibehaltung eines konsistenten Zustands zwischen der Liste und dem Client. Durch die Verwendung dieser Methoden können Sie die Elemente innerhalb der Liste effektiv verwalten. Die API ermöglicht es Ihnen, mit der Liste zu interagieren und sie zu manipulieren, um den Anforderungen Ihrer App gerecht zu werden.

### Hinzufügen von Elementen {#adding-items}

- **Ein Element hinzufügen**:

   - Um ein `ListItem` zur Liste hinzuzufügen, können Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink>-Methode verwenden.
   - Sie können auch ein neues `ListItem` hinzufügen, indem Sie den Schlüssel und den Text mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink>-Methode angeben.

- **Ein Element an einem bestimmten Index einfügen:**

   - Um ein Element an einem bestimmten Index einzufügen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink>-Methode.
   - Sie können ein Element mit Schlüssel und Text mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink>-Methode einfügen.

- **Mehrere Elemente einfügen:** 

   - Sie können mehrere Elemente an einem angegebenen Index mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink>-Methode einfügen.

:::tip
Um die Leistung zu optimieren, ist es effizienter, eine Liste von <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Objekten zuerst zu erstellen, statt bei jedem Aufruf der `add()`-Methode eine Nachricht vom Server an den Client auszulösen. Sobald Sie diese Liste haben, können Sie sie alle auf einmal mit der `insert(int index, List<ListItem> items)`-Methode hinzufügen. Dieser Ansatz reduziert die Kommunikation zwischen Server und Client und verbessert die Gesamteffizienz. Für detaillierte Richtlinien zu diesem und anderen Best Practices in der WebforJ-Architektur verweisen Sie bitte auf [Client/Server Interaktion](/docs/architecture/client-server).
:::

### Elemente entfernen {#removing-items}

- **Ein Element entfernen:**

   - Um ein Element aus der Liste zu entfernen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink>-Methode.

- **Alle Elemente entfernen:**
   - Sie können alle Elemente aus der Liste mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>-Methode entfernen.

### Elemente auswählen {#selecting-items}

Alle Listenarten implementieren das `SelectableList`-Interface. Dieses Interface ermöglicht verschiedene Möglichkeiten zur Auswahl des aktuellen `ListItem`.

#### Mit einem bestimmten `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> nimmt ein `ListItem` als Parameter zur Auswahl entgegen.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Mit einem bestimmten Schlüssel eines `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> akzeptiert einen Schlüssel zu einem `ListItem` als Parameter zur Auswahl.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Mit einem bestimmten Index eines `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> nimmt einen Index zu einem `ListItem` als Parameter zur Auswahl entgegen.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Weitere Listenoperationen {#other-list-operations}

- **Zugriff auf und Aktualisierung von Elementen:**

   - Um auf Elemente nach Schlüssel oder Index zuzugreifen, verwenden Sie <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Sie können den Text eines Elements mit der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink>-Methode innerhalb der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Klasse aktualisieren.

- **Abrufen von Informationen über die Liste:**
   - Sie können die Größe der Liste mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink>-Methode abrufen.
   - Um zu überprüfen, ob die Liste leer ist, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink>-Methode.

### Iterieren über Listen {#iterating-over-lists}

Alle Listenkomponenten implementieren das Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) Interface, das eine effiziente und intuitive Methode bietet, um durch den Inhalt einer Liste zu iterieren. Mit diesem Interface können Sie leicht jede `ListItem` durchlaufen, was es einfach macht, auf jedes Element zuzugreifen, es zu ändern oder Aktionen mit minimalem Aufwand auszuführen. Das `Iterable`-Interface ist ein Standardmuster der Java-Sprache, das sicherstellt, dass Ihr Code für jeden Java-Entwickler vertraut und wartbar ist.

Der folgende Code zeigt zwei einfache Möglichkeiten, um durch eine Liste zu iterieren:

```java
list.forEach(item -> {
   item.setText("Modifiziert: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modifiziert2: " + item.getText());
}
```

## Gemeinsame Listenattribute {#shared-list-properties}

### Label {#label}

Alle Listenkomponenten können ein Label zugewiesen werden, das ein beschreibender Text oder Titel ist, der mit der Komponente verbunden ist. Labels bieten eine kurze Erklärung oder Aufforderung, um den Benutzern zu helfen, den Zweck oder die erwartete Auswahl für diese spezifische Liste zu verstehen. Neben ihrer Bedeutung für die Benutzerfreundlichkeit spielen Listenlabels auch eine entscheidende Rolle für die Barrierefreiheit, indem sie Screenreader und unterstützende Technologien in die Lage versetzen, genaue Informationen bereitzustellen und die Tastaturnavigation zu erleichtern.

### Hilfetext {#helper-text}

Jede Listenkomponente kann Hilfetext unter der Liste mit der `setHelperText()`-Methode anzeigen. Dieser Hilfetext bietet zusätzlichen Kontext oder Erklärungen zu den verfügbaren Optionen und stellt sicher, dass die Benutzer die notwendigen Informationen haben, um informierte Entscheidungen zu treffen.

### Horizontale Ausrichtung {#horizontal-alignment}

Alle Listenkomponenten implementieren das <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>-Interface, das Ihnen die Kontrolle darüber gibt, wie Text und Inhalt innerhalb der Komponente ausgerichtet sind.

Verwenden Sie die `setHorizontalAlignment()`-Methode, um die Ausrichtung festzulegen:

- `HorizontalAlignment.LEFT` (Standard)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Um die aktuelle Ausrichtung abzurufen:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Expanses {#expanses}

Alle Listenkomponenten in webforJ implementieren ebenfalls das <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink>-Interface, sodass Sie die gesamte Größe und das visuelle Gewicht der Komponente anpassen können. Dies ist nützlich, um die Komponente an verschiedene UI-Kontexte wie Formulare, Dialoge, Seitenleisten usw. anzupassen.

Verwenden Sie die `setExpanse()`-Methode, um den Expansionsgrad festzulegen. Zu den Optionen gehören:

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

Sie können die aktuelle Einstellung mit folgendem Befehl abrufen:
```java
Expanse current = listBox.getExpanse();
```

## Themen {#topics}

<DocCardList className="topics-section" />
