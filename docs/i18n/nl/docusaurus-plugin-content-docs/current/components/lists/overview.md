---
sidebar_position: 20
title: Lijsten
hide_giscus_comments: true
sidebar_class_name: new-content
description: >-
  Manage shared list features across ChoiceBox, ComboBox, and ListBox, including
  ListItem objects, adding, removing, and selection APIs.
_i18n_hash: f75147986adfbf756ebf603caa663134
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Deze sectie beschrijft gemeenschappelijke eigenschappen van alle lijstcomponenten en is geen klasse die kan worden geïnstantieerd of direct gebruikt.
:::

Er zijn drie soorten lijsten beschikbaar voor gebruik binnen je apps: [`ListBox`](listbox), [`ChoiceBox`](choicebox) en [`ComboBox`](combobox). Deze componenten tonen allemaal een lijst van sleutel-waarde-items en bieden methoden om de items in de lijst toe te voegen, te verwijderen, te selecteren en te beheren.

Deze pagina beschrijft de gedeelde functies en het gedrag van alle lijstcomponenten, terwijl specifieke details voor elk worden behandeld op hun eigen pagina's.

## Gebruik van `ListItem` {#using-listitem}

Lijstcomponenten bestaan uit <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objecten, die individuele items binnen een lijst vertegenwoordigen. Elk <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> is gekoppeld aan een unieke sleutel en weertekst. Belangrijke kenmerken van de <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> klasse zijn:

- Een <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> bevat een unieke sleutel `Object` en een tekst `String` om binnen de lijstcomponent weer te geven.
- Je kunt een <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> construeren door een sleutel en tekst op te geven, of alleen de tekst op te geven zodat er een willekeurige sleutel wordt gegenereerd.

## Beheren van `ListItem` objecten met de API {#managing-listitem-objects-with-the-api}

De verschillende Lijstcomponenten bieden verschillende methoden voor het beheren van de lijst met items en het handhaven van een consistente toestand tussen de lijst en de client. Door deze methoden te gebruiken, kun je de items binnen de lijst effectief beheren. De API stelt je in staat om te communiceren met en de lijst te manipuleren om aan de vereisten van je app te voldoen.

### Items toevoegen {#adding-items}

- **Een item toevoegen:**

   - Om een `ListItem` aan de lijst toe te voegen, kun je de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> methode gebruiken.
   - Je kunt ook een nieuw `ListItem` toevoegen door de sleutel en tekst op te geven met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> methode.

- **Een item op een specifieke index invoegen:**

   - Om een item op een specifieke index in te voegen, gebruik je de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> methode.
   - Je kunt een item met sleutel en tekst invoegen met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> methode.

- **Meerdere items invoegen:**

   - Je kunt meerdere items op een opgegeven index invoegen met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> methode.

:::tip
Om de prestaties te optimaliseren, is het efficiënter om eerst een lijst van <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" code="true">ListItem</JavadocLink> objecten te maken in plaats van elke keer dat je de `add()` methode gebruikt, een server-naar-client bericht te sturen. Zodra je deze lijst hebt, kun je ze allemaal tegelijk toevoegen met de `insert(int index, List<ListItem> items)` methode. Deze benadering vermindert de communicatie tussen server en client, wat de algehele efficiëntie verbetert. Voor gedetailleerde richtlijnen over dit en andere best practices in de webforJ-architectuur, raadpleeg [Client/Server Interaction](/docs/architecture/client-server).
:::

### Items verwijderen {#removing-items}

- **Een item verwijderen:**

   - Om een item uit de lijst te verwijderen, gebruik je de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> methode.

- **Alle items verwijderen:**
   - Je kunt alle items uit de lijst verwijderen met <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Items selecteren {#selecting-items}

Alle lijsttypes implementeren de `SelectableList` interface. Deze interface biedt meerdere verschillende manieren om de huidige `ListItem` te selecteren.

#### Met een gegeven `ListItem` {#with-a-given-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#select(com.webforj.component.list.ListItem)' code="true">select(ListItem item)</JavadocLink> neemt een `ListItem` als parameter om te selecteren.

```java {4}
List demoList = new List();
ListItem demoItem = new ListItem("demo","Demo Item");
demoList.add(demoItem);
demoList.select(demoItem);
```

#### Met een gegeven sleutel van een `ListItem` {#with-a-given-key-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectKey(java.lang.Object)' code="true">selectKey(Object key)</JavadocLink> neemt een sleutel naar een `ListItem` als parameter om te selecteren.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey("demo");
```

#### Met een gegeven index van een `ListItem` {#with-a-given-index-of-a-listitem}

<JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#selectIndex(int)' code="true">selectIndex(int index)</JavadocLink> neemt een index naar een `ListItem` als parameter om te selecteren.

```java {3}
List demoList = new List();
demoList.add("demo","Demo Item");
demoList.selectKey(0);
```

### Andere lijstbewerkingen {#other-list-operations}

- **Toegang tot en bijwerken van items:**

   - Om items op te vragen op sleutel of index, gebruik je <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - Je kunt de tekst van een item bijwerken met de <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> methode binnen de <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> klasse.

- **Informatie over de lijst ophalen:**
   - Je kunt de grootte van de lijst krijgen met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> methode.
   - Om te controleren of de lijst leeg is, gebruik je de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> methode.

### Itereren over lijsten {#iterating-over-lists}

Alle Lijstcomponenten implementeren de Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) interface, die een efficiënte en intuïtieve manier biedt om door de inhoud van een lijst te itereren. Met deze interface kun je eenvoudig door elk `ListItem` lopen, zodat je gemakkelijk toegang hebt tot, wijzigingen kunt aanbrengen of acties kunt uitvoeren op elk item met minimale inspanning. De `Iterable` interface is een standaardpatroon van de Java-taal, wat ervoor zorgt dat je code bekend en onderhoudbaar is voor elke Java-ontwikkelaar.

De onderstaande codefragmenten tonen twee gemakkelijke manieren om door een lijst te itereren:

```java
list.forEach(item -> {
   item.setText("Gewijzigd: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Gewijzigd2: " + item.getText());
}
```

## Zoeken <DocChip chip='since' label='26.02' /> {#searching}

Alle Lijstcomponenten hebben een ingebouwd zoekveld dat de items filtert op basis van hun tekst. Het veld is standaard uitgeschakeld. Gebruik `getSearch()` om de zoekconfiguratie te bereiken, en gebruik vervolgens `setFieldVisible(true)` om het veld bovenaan de lijst van de component weer te geven.

```java
ComboBox comboBox = new ComboBox("Fruit");
comboBox.insert("Apple", "Banana", "Cherry", "Apricot", "Pineapple");

comboBox.getSearch()
  .setFieldVisible(true)
  .setPlaceholder("Zoek naar fruit")
  .setEmptyMessage("Geen fruit gevonden");
```

<ComponentDemo
path='/webforj/listsearch'
files={['src/main/java/com/webforj/samples/views/lists/listbox/ListSearchView.java']}
height='450px'
/>

Filtering verbergt alleen de items die niet overeenkomen. De itemindexen en de huidige selectie blijven onveranderd, zodat `getSelectedIndex()` blijft verwijzen naar de volledige lijst in plaats van naar de momenteel zichtbare items.

Het zoekveld kan opnieuw verborgen worden met `setFieldVisible(false)`.

### De velden configureren {#configuring-the-field}

- `setPlaceholder()` stelt de tijdelijke tekst van het zoekveld in. De standaardwaarde is `Zoeken`.

- `setEmptyMessage()` stelt de boodschap in die wordt weergegeven wanneer een zoekopdracht geen resultaten oplevert. De standaardwaarde is `Geen gegevens om weer te geven`.

Elke instelling heeft een bijbehorende getter: `isFieldVisible()`, `getPlaceholder()`, `getEmptyMessage()`, en `getTerm()`.

### Filteren vanuit de code {#filtering-from-code}

`setTerm()` stelt de zoekterm in en filtert de lijst. Het werkt ongeacht of het veld zichtbaar is, zodat een lijst kan worden gefilterd zonder dat er een zoekgebruikersinterface wordt weergegeven.

```java
listBox.getSearch().setTerm("apple");
```

:::warning `getTerm()` en het zoekveld
Typen in het zoekveld schrijft de term niet terug naar de configuratie. `getTerm()` retourneert de laatste waarde die aan `setTerm()` is doorgegeven, niet wat de gebruiker heeft getypt.
:::

## Gemeenschappelijke lijst eigenschappen {#shared-list-properties}

### Label {#label}

Alle Lijstcomponenten kunnen aan een label worden toegewezen, wat een beschrijvende tekst of titel is die aan de component is gekoppeld. Labels bieden een korte uitleg of aanwijzing om gebruikers te helpen de bedoeling of verwachte selectie voor die specifieke lijst te begrijpen. Naast hun belang voor gebruiksvriendelijkheid spelen lijstlabels ook een cruciale rol in toegankelijkheid, waardoor schermlezers en ondersteunende technologieën nauwkeurige informatie kunnen bieden en navigatie per toetsenbord kunnen vergemakkelijken.

### Helpertekst {#helper-text}

Elke Lijstcomponent kan helpertekst onder de lijst weergeven met behulp van de `setHelperText()` methode. Deze helpertekst biedt aanvullende context of uitleg over de beschikbare opties, zodat gebruikers de nodige informatie hebben om weloverwogen selecties te maken.

### Horizontale uitlijning {#horizontal-alignment}

Alle lijstcomponenten implementeren de <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> interface, waardoor je de controle hebt over hoe tekst en inhoud binnen de component zijn uitgelijnd.

Gebruik de `setHorizontalAlignment()` methode om de uitlijning in te stellen:

- `HorizontalAlignment.LEFT` (standaard)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Om de huidige uitlijning te krijgen:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Expansies {#expanses}

Alle lijstcomponenten in webforJ implementeren ook de <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> interface, waarmee je de algehele grootte en visuele gewicht van de component kunt aanpassen. Dit is nuttig voor het aanpassen van de component aan verschillende UI-contexten, zoals formulieren, dialoogvensters, zijbalken, enz.

Gebruik de `setExpanse()` methode om het expansieniveau in te stellen. Opties omvatten:

- `Expanse.NONE`
- `Expanse.XSMALL`
- `Expanse.SMALL`
- `Expanse.MEDIUM` (standaard)
- `Expanse.LARGE`
- `Expanse.XLARGE`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setExpanse(Expanse.LARGE);
```

Je kunt de huidige instelling ophalen met:
```java
Expanse current = listBox.getExpanse();
```

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
