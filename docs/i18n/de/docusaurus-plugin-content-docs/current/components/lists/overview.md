---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: f75147986adfbf756ebf603caa663134
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Dieser Abschnitt beschreibt die gemeinsamen Funktionen aller Listenkomponenten und ist keine Klasse, die instanziiert oder direkt verwendet werden kann.
:::

Es stehen drei Typen von Listen zur Verwendung in Ihren Apps zur Verfügung: [`ListBox`](listbox), [`ChoiceBox`](choicebox) und [`ComboBox`](combobox). Diese Komponenten zeigen alle eine Liste von Schlüssel-Wert-Elementen an und bieten Methoden zum Hinzufügen, Entfernen, Auswählen und Verwalten der Elemente innerhalb der Liste.

Diese Seite skizziert die geteilten Merkmale und das Verhalten aller Listenkomponenten, während spezifische Details für jede Komponente in ihren jeweiligen Seiten behandelt werden.

## Verwendung von `ListItem` {#using-listitem}

Listenkomponenten bestehen aus <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Objekten, die einzelne Elemente innerhalb einer Liste repräsentieren. Jedes <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> ist mit einem einzigartigen Schlüssel und einem Anzeigetext verbunden. Wichtige Merkmale der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Klasse umfassen:

- Ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> kapselt einen einzigartigen Schlüssel `Object` und einen Text `String`, der innerhalb der Listenkomponente angezeigt wird.
- Sie können ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> erstellen, indem Sie einen Schlüssel und Text angeben oder indem Sie nur den Text angeben, sodass ein zufälliger Schlüssel generiert wird.

## Verwalten von `ListItem`-Objekten mit der API {#managing-listitem-objects-with-the-api}

Die verschiedenen Listenkomponenten bieten mehrere Methoden zum Verwalten der Liste von Elementen und zur Beibehaltung eines konsistenten Zustands zwischen der Liste und dem Client. Durch die Verwendung dieser Methoden können Sie die Elemente innerhalb der Liste effektiv verwalten. Die API ermöglicht es Ihnen, mit der Liste zu interagieren und diese so zu manipulieren, dass sie den Anforderungen Ihrer App entspricht.

### Hinzufügen von Elementen {#adding-items}

- **Hinzufügen eines Elements**:

   - Um ein `ListItem` zur Liste hinzuzufügen, können Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink>-Methode verwenden.
   - Sie können auch ein neues `ListItem` hinzufügen, indem Sie den Schlüssel und den Text über die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink>- oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink>-Methode angeben.

- **Einfügen eines Elements an einem bestimmten Index:**

   - Um ein Element an einem bestimmten Index einzufügen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink>-Methode.
   - Sie können ein Element mit Schlüssel und Text einfügen, indem Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink>- oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink>-Methode verwenden.

- **Mehrere Elemente einfügen:**

   - Sie können mehrere Elemente an einem bestimmten Index mithilfe der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink>-Methode einfügen.

:::tip
Um die Leistung zu optimieren, ist es effizienter, eine Liste von <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Objekten zuerst zu erstellen, anstatt bei jedem Aufruf der `add()`-Methode eine Server-zu-Client-Nachricht auszulösen. Sobald Sie diese Liste haben, können Sie sie alle auf einmal mit der Methode `insert(int index, List<ListItem> items)` hinzufügen. Dieser Ansatz reduziert die Kommunikation zwischen Server und Client und verbessert die Gesamteffizienz. Für detaillierte Richtlinien zu diesem und anderen Best Practices in der webforJ-Architektur verweisen Sie bitte auf [Client/Server-Interaktion](/docs/architecture/client-server).
:::

### Entfernen von Elementen {#removing-items}

- **Ein Element entfernen:**

   - Um ein Element aus der Liste zu entfernen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink>- oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink>-Methode.

- **Alle Elemente entfernen:**
   - Sie können alle Elemente aus der Liste mit <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> entfernen.

### Auswählen von Elementen {#selecting-items}

Alle Listentypen implementieren die `SelectableList`-Schnittstelle. Diese Schnittstelle ermöglicht verschiedene Möglichkeiten zur Auswahl des aktuellen `ListItem`.

#### Mit einem gegebenen `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> benötigt ein `ListItem` als Parameter zur Auswahl.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Mit einem gegebenen Schlüssel eines `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> benötigt einen Schlüssel zu einem `ListItem` als Parameter zur Auswahl.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Mit einem gegebenen Index eines `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> benötigt einen Index zu einem `ListItem` als Parameter zur Auswahl.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Andere Listenoperationen {#other-list-operations}

- **Zugriff und Aktualisierung von Elementen:**

   - Um auf Elemente anhand von Schlüssel oder Index zuzugreifen, verwenden Sie <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Sie können den Text eines Elements mithilfe der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink>-Methode innerhalb der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink>-Klasse aktualisieren.

- **Abrufen von Informationen über die Liste:**
   - Sie können die Größe der Liste mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink>-Methode erhalten.
   - Um zu überprüfen, ob die Liste leer ist, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink>-Methode.

### Über Listen iterieren {#iterating-over-lists}

Alle Listenkomponenten implementieren die Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html)-Schnittstelle, die einen effizienten und intuitiven Weg bietet, durch den Inhalt einer Liste zu iterieren. Mit dieser Schnittstelle können Sie mühelos durch jedes `ListItem` schleifen und jedes Element mit minimalem Aufwand zugreifen, ändern oder Aktionen durchführen. Die `Iterable`-Schnittstelle ist ein standardmäßiges Muster der Programmiersprache Java, das sicherstellt, dass Ihr Code vertraut und wartbar für jeden Java-Entwickler ist.

Der folgende Code zeigt zwei einfache Möglichkeiten, um durch eine Liste zu iterieren:

```java
list.forEach(item -> {
   item.setText("Modifiziert: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modifiziert2: " + item.getText());
}
```

## Suchen <DocChip chip='since' label='26.02' /> {#searching}

Alle Listenkomponenten verfügen über ein eingebettetes Suchfeld, das die Elemente anhand ihres Textes filtert. Das Feld ist standardmäßig deaktiviert. Verwenden Sie `getSearch()`, um auf die Suchkonfiguration zuzugreifen, und dann `setFieldVisible(true)`, um das Feld oben in der Liste der Komponente anzuzeigen.

```java
ComboBox comboBox = new ComboBox("Frucht");
comboBox.insert("Apfel", "Banane", "Kirsche", "Aprikose", "Ananas");

comboBox.getSearch()
  .setFieldVisible(true)
  .setPlaceholder("Früchte suchen")
  .setEmptyMessage("Keine Früchte gefunden");
```

<ComponentDemo
path='/webforj/listsearch'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListSearchView.java']}
height='450px'
/>

Das Filtern blendet nur die Elemente aus, die nicht übereinstimmen. Die Elementeindizes und die aktuelle Auswahl bleiben unberührt, sodass `getSelectedIndex()` weiterhin auf die vollständige Liste verweist und nicht auf die aktuell sichtbaren Elemente.

Das Suchfeld kann wieder mit `setFieldVisible(false)` ausgeblendet werden.

### Konfigurieren des Feldes {#configuring-the-field}

- `setPlaceholder()` legt den Platzhaltertext des Suchfelds fest. Der Standardwert ist `Suchen`.

-  `setEmptyMessage()` legt die Nachricht fest, die angezeigt wird, wenn eine Suche keine Ergebnisse zurückgibt. Der Standardwert ist `Keine Daten zum Anzeigen`.

Jede Einstellung hat einen passenden Getter: `isFieldVisible()`, `getPlaceholder()`, `getEmptyMessage()`, und `getTerm()`.

### Filtern aus dem Code heraus {#filtering-from-code}

`setTerm()` legt den Suchbegriff fest und filtert die Liste. Es funktioniert unabhängig davon, ob das Feld sichtbar ist oder nicht, sodass eine Liste gefiltert werden kann, ohne dass eine Suchoberfläche angezeigt wird.

```java
listBox.getSearch().setTerm("apfel");
```

:::warning `getTerm()` und das Suchfeld
Das Tippen in das Suchfeld schreibt den Begriff nicht in die Konfiguration zurück. `getTerm()` gibt den letzten Wert zurück, der an `setTerm()` übergeben wurde, und nicht das, was der Benutzer eingegeben hat.
:::

## Gemeinsame Listenproperties {#shared-list-properties}

### Etikett {#label}

Alle Listenkomponenten können ein Etikett zugewiesen werden, das ein beschreibender Text oder Titel ist, der mit der Komponente verbunden ist. Etiketten bieten eine kurze Erklärung oder Aufforderung, um den Benutzern zu helfen, den Zweck oder die erwartete Auswahl für diese bestimmte Liste zu verstehen. Neben ihrer Wichtigkeit für die Benutzerfreundlichkeit spielen Listenetiketten auch eine entscheidende Rolle in der Barrierefreiheit, da sie Bildschirmlesegeräten und unterstützenden Technologien ermöglichen, genaue Informationen bereitzustellen und die Tastaturnavigation zu erleichtern.

### Hilfetext {#helper-text}

Jede Listenkomponente kann einen Hilfetext unter der Liste mit der Methode `setHelperText()` anzeigen. Dieser Hilfetext bietet zusätzlichen Kontext oder Erklärungen zu den verfügbaren Optionen und stellt sicher, dass die Benutzer die notwendigen Informationen haben, um informierte Entscheidungen zu treffen.

### Horizontale Ausrichtung {#horizontal-alignment}

Alle Listenkomponenten implementieren die <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink>-Schnittstelle, die Ihnen die Kontrolle darüber gibt, wie Text und Inhalte innerhalb der Komponente ausgerichtet sind.

Verwenden Sie die Methode `setHorizontalAlignment()`, um die Ausrichtung festzulegen:

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

### Expanses {#expanses}

Alle Listenkomponenten in webforJ implementieren auch die <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink>-Schnittstelle, die es Ihnen ermöglicht, die Gesamtgröße und das visuelle Gewicht der Komponente anzupassen. Dies ist nützlich, um die Komponente an verschiedene UI-Kontexte anzupassen, wie Formulare, Dialoge, Seitenleisten usw.

Verwenden Sie die Methode `setExpanse()`, um die Expansionsstufe festzulegen. Optionen sind:

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

Sie können die aktuelle Einstellung mit folgender Methode abrufen:
```java
Expanse current = listBox.getExpanse();
```

## Themen {#topics}

<DocCardList className="topics-section" />
