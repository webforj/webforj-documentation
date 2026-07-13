---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: 1fa51afef9c5af46944d4d74a6b5ec61
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Dieser Abschnitt beschreibt gemeinsame Funktionen aller Listenkomponenten und ist keine Klasse, die instanziiert oder direkt verwendet werden kann.
:::

Es stehen drei Arten von Listen zur Verwendung innerhalb Ihrer Apps zur Verfügung: [`ListBox`](listbox), [`ChoiceBox`](choicebox) und [`ComboBox`](combobox). Diese Komponenten zeigen alle eine Liste von Schlüssel-Wert-Elementen an und bieten Methoden zum Hinzufügen, Entfernen, Auswählen und Verwalten der Elemente innerhalb der Liste.

Diese Seite skizziert die gemeinsamen Funktionen und das Verhalten aller Listenkomponenten, während spezifische Details für jede von ihnen in den jeweiligen Seiten behandelt werden.

## Verwendung von `ListItem` {#using-listitem}

Listenkomponenten bestehen aus <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Objekten, die einzelne Elemente innerhalb einer Liste repräsentieren. Jedes <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink> ist mit einem eindeutigen Schlüssel und einem Anzeigetext verbunden. Wichtige Merkmale der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Klasse sind:

- Ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink> kapselt einen eindeutigen Schlüssel `Object` und einen Text `String`, der innerhalb der Listenkomponente angezeigt wird.
- Sie können ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink> erstellen, indem Sie einen Schlüssel und einen Text angeben oder nur den Text angeben, sodass ein zufälliger Schlüssel generiert wird.

## Verwaltung von `ListItem`-Objekten mit der API {#managing-listitem-objects-with-the-api}

Die verschiedenen Listenkomponenten bieten mehrere Methoden zur Verwaltung der Liste von Elementen und zur Aufrechterhaltung eines konsistenten Zustands zwischen der Liste und dem Client. Durch die Verwendung dieser Methoden können Sie die Elemente innerhalb der Liste effektiv verwalten. Die API erlaubt es Ihnen, mit der Liste zu interagieren und sie zu manipulieren, um die Anforderungen Ihrer App zu erfüllen.

### Hinzufügen von Elementen {#adding-items}

- **Ein Element hinzufügen**:

   - Um ein `ListItem` zur Liste hinzuzufügen, können Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink>-Methode verwenden.
   - Sie können auch ein neues `ListItem` hinzufügen, indem Sie den Schlüssel und den Text mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink>- oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink>-Methode angeben.

- **Ein Element an einem bestimmten Index einfügen**:

   - Um ein Element an einem bestimmten Index einzufügen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink>-Methode.
   - Sie können ein Element mit Schlüssel und Text mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink>- oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink>-Methode einfügen.

- **Mehrere Elemente einfügen**:

   - Sie können mehrere Elemente an einem bestimmten Index mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink>-Methode einfügen.

:::tip
Um die Leistung zu optimieren, ist es effizienter, anstatt bei jedem Gebrauch der `add()`-Methode eine Server-zu-Client-Nachricht auszulösen, zunächst eine Liste von <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Objekten zu erstellen. Haben Sie diese Liste erstellt, können Sie sie alle auf einmal mit der `insert(int index, List<ListItem> items)`-Methode hinzufügen. Dieser Ansatz reduziert die Kommunikation zwischen Server und Client und verbessert die Gesamtleistung. Für detaillierte Richtlinien zu diesem und anderen bewährten Verfahren in der webforJ-Architektur siehe [Client/Server-Interaktion](/docs/architecture/client-server).
:::

### Entfernen von Elementen {#removing-items}

- **Ein Element entfernen**:

   - Um ein Element aus der Liste zu entfernen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink>- oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink>-Methode.

- **Alle Elemente entfernen**:
   - Sie können alle Elemente aus der Liste mit <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> entfernen.

### Auswählen von Elementen {#selecting-items}

Alle Listentypen implementieren das `SelectableList`-Interface. Dieses Interface ermöglicht mehrere verschiedene Methoden zur Auswahl des aktuellen `ListItem`.

#### Mit einem bestimmten `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> akzeptiert ein `ListItem` als Parameter zur Auswahl.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo-Element");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Mit einem bestimmten Schlüssel eines `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> akzeptiert einen Schlüssel zu einem `ListItem` als Parameter zur Auswahl.

```java {3}
List demoList = new List();
demoList.add("demo","Demo-Element");
demoList.selectKey("demo");
```

#### Mit einem bestimmten Index eines `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> akzeptiert einen Index zu einem `ListItem` als Parameter zur Auswahl.

```java {3}
List demoList = new List();
demoList.add("demo","Demo-Element");
demoList.selectKey(0);
```

### Andere Listenoperationen {#other-list-operations}

- **Zugriff und Aktualisierung von Elementen:**

   - Um auf Elemente nach Schlüssel oder Index zuzugreifen, verwenden Sie <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Sie können den Text eines Elements mit der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink>-Methode innerhalb der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Klasse aktualisieren.

- **Informationen über die Liste abrufen:**
   - Sie können die Größe der Liste mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink>-Methode abrufen.
   - Um zu überprüfen, ob die Liste leer ist, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink>-Methode.

### Durchlisten von Listen {#iterating-over-lists}

Alle Listenkomponenten implementieren das Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html)-Interface, das eine effiziente und intuitive Möglichkeit bietet, durch den Inhalt einer Liste zu iterieren. Mit diesem Interface können Sie einfach durch jedes `ListItem` schleifen, was es einfach macht, auf jedes Element zuzugreifen, es zu ändern oder Aktionen mit minimalem Aufwand durchzuführen. Das `Iterable`-Interface ist ein Standardmuster der Java-Sprache, das sicherstellt, dass Ihr Code für jeden Java-Entwickler vertraut und wartbar ist.

Der folgende Codeausschnitt demonstriert zwei einfache Möglichkeiten, durch eine Liste zu iterieren:

```java
list.forEach(item -> {
   item.setText("Modifiziert: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modifiziert2: " + item.getText());
}
```

## Gemeinsame Listenmerkmale {#shared-list-properties}

### Label {#label}

Alle Listenkomponenten können ein Label zugewiesen bekommen, das ein beschreibender Text oder Titel ist, der mit der Komponente verbunden ist. Labels bieten eine kurze Erklärung oder Aufforderung, um den Benutzern zu helfen, den Zweck oder die erwartete Auswahl für diese spezielle Liste zu verstehen. Neben ihrer Bedeutung für die Benutzerfreundlichkeit spielen Listenlabels auch eine wichtige Rolle in der Barrierefreiheit, da sie Screenreadern und Hilfstechnologien ermöglichen, genaue Informationen bereitzustellen und die Tastaturnavigation zu erleichtern.

### Hilfetext {#helper-text}

Jede Listenkomponente kann Hilfetext unter der Liste mit der Methode `setHelperText()` anzeigen. Dieser Hilfetext bietet zusätzlichen Kontext oder Erklärungen zu den verfügbaren Optionen und stellt sicher, dass die Benutzer die notwendigen Informationen haben, um informierte Auswahlen zu treffen.

### Horizontale Ausrichtung {#horizontal-alignment}

Alle Listenkomponenten implementieren das <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>-Interface, das Ihnen die Kontrolle über die Ausrichtung von Text und Inhalt innerhalb der Komponente gibt.

Verwenden Sie die Methode `setHorizontalAlignment()`, um die Ausrichtung festzulegen:

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

Alle Listenkomponenten in webforJ implementieren auch das <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink>-Interface, das es Ihnen ermöglicht, die Gesamtgröße und das visuelle Gewicht der Komponente anzupassen. Dies ist nützlich, um die Komponente an verschiedene UI-Kontexte anzupassen, wie z. B. Formulare, Dialoge, Seitenleisten usw.

Verwenden Sie die Methode `setExpanse()`, um die Expansestufe festzulegen. Optionen sind:

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
