---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: 79a006a2c6a0435414d931e5bfb161bd
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Dieser Abschnitt beschreibt gemeinsame Funktionen aller Listenkomponenten und ist keine Klasse, die instanziiert oder direkt verwendet werden kann.
:::

Es stehen drei Typen von Listen zur Verwendung in Ihren Apps zur Verfügung: [`ListBox`](listbox), [`ChoiceBox`](choicebox) und [`ComboBox`](combobox). Diese Komponenten zeigen alle eine Liste von Schlüssel-Wert-Elementen an und bieten Methoden zum Hinzufügen, Entfernen, Auswählen und Verwalten der Elemente innerhalb der Liste.

Diese Seite beschreibt die gemeinsamen Merkmale und das Verhalten aller Listenkomponenten, während spezifische Details für jede auf ihren jeweiligen Seiten behandelt werden.

## Verwendung von `ListItem` {#using-listitem}

Listenkomponenten bestehen aus <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Objekten, die individuelle Elemente innerhalb einer Liste darstellen. Jedes <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink> ist mit einem einzigartigen Schlüssel und einem Anzeige-Text verknüpft. Wichtige Merkmale der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Klasse umfassen:

- Ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink> kapselt einen einzigartigen Schlüssel `Object` und einen Text `String`, der innerhalb der Listenkomponente angezeigt wird.
- Sie können ein <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink> erstellen, indem Sie einen Schlüssel und einen Text angeben oder nur den Text angeben, der auch als Schlüssel verwendet wird.

Schlüssel müssen innerhalb einer Liste einzigartig sein. Das Hinzufügen eines zweiten, nur textbasierten Elements mit demselben Text löst eine `IllegalArgumentException` aus. Um doppelte Beschriftungen anzuzeigen, geben Sie unterschiedliche Schlüssel für diese Elemente an.

## Verwaltung von `ListItem`-Objekten mit der API {#managing-listitem-objects-with-the-api}

Die verschiedenen Listenkomponenten bieten mehrere Methoden zur Verwaltung der Liste von Elementen und zur Aufrechterhaltung eines konsistenten Zustands zwischen der Liste und dem Client. Durch die Verwendung dieser Methoden können Sie die Elemente innerhalb der Liste effektiv verwalten. Die API ermöglicht es Ihnen, mit der Liste zu interagieren und sie zu manipulieren, um die Anforderungen Ihrer App zu erfüllen.

### Hinzufügen von Elementen {#adding-items}

- **Hinzufügen eines Elements**:

   - Um ein `ListItem` zur Liste hinzuzufügen, können Sie die Methode <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> verwenden.
   - Sie können auch ein neues `ListItem` erstellen, indem Sie den Schlüssel und den Text mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> Methode angeben.

- **Einfügen eines Elements an einem bestimmten Index:**

   - Um ein Element an einem bestimmten Index einzufügen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> Methode.
   - Sie können ein Element mit Schlüssel und Text mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> Methode einfügen.

- **Einfügen mehrerer Elemente:**

   - Sie können mehrere Elemente an einem angegebenen Index einfügen, indem Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> Methode verwenden.

:::tip
Um die Leistung zu optimieren, ist es anstelle der Auslösung einer Server-zu-Client-Nachricht bei jedem Aufruf der `add()`-Methode effizienter, zunächst eine Liste von <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Objekten zu erstellen. Sobald Sie diese Liste haben, können Sie alle auf einmal mit der Methode `insert(int index, List<ListItem> items)` hinzufügen. Dieser Ansatz reduziert die Kommunikation zwischen Server und Client und verbessert die Gesamteffizienz. Für detaillierte Richtlinien zu diesem und anderen Best Practices in der webforJ-Architektur verweisen Sie auf [Client/Server-Interaktion](/docs/architecture/client-server).
:::

### Entfernen von Elementen {#removing-items}

- **Entfernen eines Elements:**

   - Um ein Element aus der Liste zu entfernen, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> Methode.

- **Entfernen aller Elemente:**
   - Sie können alle Elemente aus der Liste mit <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink> entfernen.

### Auswählen von Elementen {#selecting-items}

Alle Listentypen implementieren das `SelectableList`-Interface. Dieses Interface ermöglicht mehrere verschiedene Arten, das aktuelle `ListItem` auszuwählen.

#### Mit einem bestimmten `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> nimmt ein `ListItem` als Parameter zur Auswahl.

```java {4}
ListBox demoList = new ListBox();
ListItem demoItem = new ListItem("demo","Demo Element");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Mit einem bestimmten Schlüssel eines `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> nimmt einen Schlüssel zu einem `ListItem` als Parameter zur Auswahl.

```java {3}
ListBox demoList = new ListBox();
demoList.add("demo","Demo Element");
demoList.selectKey("demo");
```

#### Mit einem bestimmten Index eines `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> nimmt einen Index zu einem `ListItem` als Parameter zur Auswahl.

```java {3}
ListBox demoList = new ListBox();
demoList.add("demo","Demo Element");
demoList.selectIndex(0);
```

### Andere Listenoperationen {#other-list-operations}

- **Zugriff auf und Aktualisierung von Elementen:**

   - Um auf Elemente nach Schlüssel oder Index zuzugreifen, verwenden Sie <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> oder <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Sie können den Text eines Elements mit der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> Methode innerhalb der <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink>-Klasse aktualisieren.

- **Informationen über die Liste abrufen:**
   - Sie können die Größe der Liste mit der <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> Methode abrufen.
   - Um zu überprüfen, ob die Liste leer ist, verwenden Sie die <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> Methode.

### Über Listen iterieren {#iterating-over-lists}

Alle Listenkomponenten implementieren das Java [`Iterable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) Interface. Verwenden Sie `forEach()` oder eine `for`-Schleife, um auf jedes `ListItem` zuzugreifen oder es zu ändern.

Der folgende Codeausschnitt zeigt zwei einfache Möglichkeiten, durch eine Liste zu iterieren:

```java
list.forEach(item -> {
   item.setText("Modifiziert: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modifiziert2: " + item.getText());
}
```

## Suchen <DocChip chip='since' label='26.02' /> {#searching}

Alle Listenkomponenten haben ein eingebettetes Suchfeld, das die Elemente nach ihrem Text filtert. Das Feld ist standardmäßig ausgeschaltet. Verwenden Sie `getSearch()`, um auf die Suchkonfiguration zuzugreifen, und verwenden Sie dann `setFieldVisible(true)`, um das Feld oben in der Liste der Komponente anzuzeigen.

```java
ComboBox comboBox = new ComboBox("Obst");
comboBox.insert("Apfel", "Banane", "Kirsche", "Aprikose", "Ananas");

comboBox.getSearch()
  .setFieldVisible(true)
  .setPlaceholder("Obst suchen")
  .setEmptyMessage("Keine Früchte gefunden");
```

<ComponentDemo
path='/webforj/listsearch'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListSearchView.java']}
height='450px'
/>

Das Filtern blendet nur die Elemente aus, die nicht übereinstimmen. Die Indizes der Elemente und die aktuelle Auswahl bleiben unberührt, sodass `getSelectedIndex()` weiterhin auf die gesamte Liste verweist und nicht auf die derzeit sichtbaren Elemente.

Das Suchfeld kann wieder mit `setFieldVisible(false)` ausgeblendet werden.

### Konfigurieren des Feldes {#configuring-the-field}

- `setPlaceholder()` setzt den Platzhaltertext des Suchfelds. Der Standardwert ist `Suchen`.

-  `setEmptyMessage()` setzt die Nachricht, die angezeigt wird, wenn eine Suche keine Ergebnisse zurückgibt. Der Standardwert ist `Keine Daten anzuzeigen`.

Jede Einstellung hat einen passenden Getter: `isFieldVisible()`, `getPlaceholder()`, `getEmptyMessage()`, und `getTerm()`.

### Filtern aus dem Code heraus {#filtering-from-code}

`setTerm()` setzt den Suchbegriff und filtert die Liste. Es funktioniert unabhängig davon, ob das Feld sichtbar ist oder nicht, sodass eine Liste gefiltert werden kann, ohne dass eine Suchbenutzeroberfläche angezeigt wird.

```java
listBox.getSearch().setTerm("apfel");
```

:::warning `getTerm()` und das Suchfeld
Eingaben im Suchfeld werden nicht zurück in die Konfiguration geschrieben. `getTerm()` gibt den letzten Wert zurück, der an `setTerm()` übergeben wurde, nicht das, was der Benutzer eingegeben hat.
:::

## Gemeinsame Listenattribute {#shared-list-properties}

### Beschriftung {#label}

Alle Listenkomponenten können mit einer Beschriftung versehen werden, die einem beschreibenden Text oder Titel zugeordnet ist. Beschriftungen bieten eine kurze Erklärung oder Aufforderung, um den Benutzern zu helfen, den Zweck oder die erwartete Auswahl für diese bestimmte Liste zu verstehen. Neben ihrer Bedeutung für die Benutzerfreundlichkeit spielen Listenbeschriftungen auch eine entscheidende Rolle für die Barrierefreiheit, indem sie Screenreadern und unterstützenden Technologien ermöglichen, genaue Informationen bereitzustellen und die Navigation mit der Tastatur zu erleichtern.

### Hilfetext {#helper-text}

Jede Listenkomponente kann Hilfetext unter der Liste mithilfe der Methode `setHelperText()` anzeigen. Dieser Hilfetext bietet zusätzlichen Kontext oder Erklärungen zu den verfügbaren Optionen, um sicherzustellen, dass die Benutzer die notwendigen Informationen haben, um informierte Entscheidungen zu treffen.

### Horizontale Ausrichtung {#horizontal-alignment}

Alle Listenkomponenten implementieren das <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> Interface, das Ihnen die Kontrolle darüber gibt, wie Text und Inhalte innerhalb der Komponente ausgerichtet sind.

Verwenden Sie die Methode `setHorizontalAlignment()` mit dem `HasHorizontalAlignment.Alignment`-Enum, um die Ausrichtung festzulegen:

- `Alignment.LEFT` (Standard)
- `Alignment.MIDDLE`
- `Alignment.RIGHT`

```java
ListBox listBox = new ListBox();
listBox.setHorizontalAlignment(Alignment.LEFT);
```

Um die aktuelle Ausrichtung zu erhalten:
```java
Alignment alignment = listBox.getHorizontalAlignment();
```

### Größenanpassung {#expanses}

Alle Listenkomponenten in webforJ implementieren ebenfalls das <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> Interface, das es Ihnen ermöglicht, die Gesamtgröße und das visuelle Gewicht der Komponente anzupassen. Dies ist nützlich, um die Komponente an verschiedene UI-Kontexte wie Formulare, Dialoge, Seitenleisten usw. anzupassen.

Verwenden Sie die Methode `setExpanse()`, um das Ausmaß festzulegen. Optionen umfassen:

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` (Standard)
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox listBox = new ListBox();
listBox.setExpanse(Expanse.LARGE);
```

Sie können die aktuelle Einstellung mit folgendem Befehl abrufen:
```java
Expanse current = listBox.getExpanse();
```

## Themen {#topics}

<DocCardList className="topics-section" />
