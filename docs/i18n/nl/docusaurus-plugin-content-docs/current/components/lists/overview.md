---
sidebar_position: 20
title: Lists
hide_giscus_comments: true
_i18n_hash: 15effbe238b9ea86c975499ed2faa30b
---
<JavadocLink type="foundation" location="com/webforj/component/list/DwcList"/>

:::info
Deze sectie beschrijft de gemeenschappelijke functies van alle lijstcomponenten en is geen klasse die rechtstreeks kan worden geïnstantieerd of gebruikt.
:::

Er zijn drie soorten lijsten beschikbaar voor gebruik binnen uw apps: [`ListBox`](listbox), [`ChoiceBox`](choicebox) en [`ComboBox`](combobox). Deze componenten tonen allemaal een lijst van sleutel-waarde-items en bieden methoden om items binnen de lijst toe te voegen, te verwijderen, te selecteren en te beheren.

Deze pagina beschrijft de gedeelde functies en het gedrag van alle lijstcomponenten, terwijl specifieke details voor elk op hun respectieve pagina's worden behandeld.

## Gebruik van `ListItem` {#using-listitem}

Lijstcomponenten zijn samengesteld uit <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objecten, die individuele items binnen een lijst vertegenwoordigen. Elke <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> is geassocieerd met een unieke sleutel en weergavetekst. Belangrijke functies van de <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> klasse zijn onder andere:

- Een <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> encapsuleert een unieke sleutel `Object` en een tekst `String` om weer te geven binnen de lijstcomponent. 
- U kunt een <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> construeren door een sleutel en tekst op te geven, of alleen de tekst op te geven zodat een willekeurige sleutel wordt gegenereerd.

## Beheren van `ListItem` objecten met de API {#managing-listitem-objects-with-the-api}

De verschillende Lijstcomponenten bieden verschillende methoden voor het beheren van de lijst met items en het behouden van een consistente status tussen de lijst en de client. Door deze methoden te gebruiken, kunt u de items binnen de lijst effectief beheren. De API stelt u in staat om met de lijst te communiceren en deze aan te passen om aan de vereisten van uw app te voldoen.

### Items toevoegen {#adding-items}

- **Een item toevoegen:**

   - Om een `ListItem` aan de lijst toe te voegen, kunt u de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(com.webforj.component.list.ListItem)' code="true">add(ListItem item)</JavadocLink> methode gebruiken.
   - U kunt ook een nieuwe `ListItem` toevoegen door de sleutel en tekst op te geven met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.Object,java.lang.String)' code="true">add(Object key, String text)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#add(java.lang.String)' code="true">add(String text)</JavadocLink> methode.

- **Een item op een specifieke index invoegen:**

   - Om een item op een specifieke index in te voegen, gebruikt u de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,com.webforj.component.list.ListItem)' code="true">insert(int index, ListItem item)</JavadocLink> methode.
   - U kunt een item met sleutel en tekst invoegen met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.Object,java.lang.String)' code="true">insert(int index, Object key, String text)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.lang.String)' code="true">insert(int index, String text)</JavadocLink> methode.

- **Meerdere items invoegen:** 

   - U kunt meerdere items op een opgegeven index invoegen met behulp van de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#insert(int,java.util.List)' code="true">insert(int index, List< ListItem > items)</JavadocLink> methode.

:::tip
Om de prestaties te optimaliseren, is het efficiënter om een Lijst van <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> objecten eerst te maken in plaats van elke keer dat u de `add()` methode gebruikt een server-naar-client bericht te triggers. Zodra u deze lijst heeft, kunt u ze allemaal tegelijk toevoegen met de `insert(int index, List<ListItem> items)` methode. Deze benadering vermindert de communicatie tussen server en client, waardoor de algehele efficiëntie wordt verhoogd. Voor gedetailleerde richtlijnen hierover en andere best practices in de webforJ-architectuur, raadpleegt u [Client/Server Interaction](/docs/architecture/client-server).
:::

### Items verwijderen {#removing-items}

- **Een item verwijderen:**

   - Om een item uit de lijst te verwijderen, gebruikt u de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(int)' code="true">remove(int index)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#remove(java.lang.Object)' code="true">remove(Object key)</JavadocLink> methode.

- **Alle items verwijderen:**
   - U kunt alle items uit de lijst verwijderen met <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#removeAll()' code="true">removeAll()</JavadocLink>.

### Items selecteren {#selecting-items}

Alle lijsttypen implementeren de `SelectableList` interface. Deze interface biedt verschillende manieren om de huidige `ListItem` te selecteren.

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

   - Om toegang te krijgen tot items op basis van sleutel of index, gebruikt u <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByKey(java.lang.Object)' code="true">getByKey(Object key)</JavadocLink> of <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#getByIndex(int)' code="true">getByIndex(int index)</JavadocLink>.
   - U kunt de tekst van een item bijwerken met de <JavadocLink type="foundation" location="com/webforj/component/list/ListItem" suffix='#setText(java.lang.String)' code="true">setText(String text)</JavadocLink> methode binnen de <JavadocLink type="foundation" location="com/webforj/component/list/ListItem"  code="true">ListItem</JavadocLink> klasse.

- **Informatie over de lijst ophalen:**
   - U kunt de grootte van de lijst ophalen met de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#size()' code="true">size()</JavadocLink> methode.
   - Om te controleren of de lijst leeg is, gebruikt u de <JavadocLink type="foundation" location="com/webforj/component/list/DwcList" suffix='#isEmpty()' code="true">isEmpty()</JavadocLink> methode.

### Lijsten doorlopen {#iterating-over-lists}

Alle Lijstcomponenten implementeren de Java [`Iteratable`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Iterable.html) interface, die een efficiënte en intuïtieve manier biedt om door de inhoud van een lijst te itereren. Met deze interface kunt u eenvoudig door elk `ListItem` lopen, waardoor het eenvoudig wordt om toegang te krijgen, te modificeren of acties uit te voeren op elk item met minimale inspanning. De `Iterable` interface is een standaard patroon van de Java-taal, waardoor uw code vertrouwd en onderhoudbaar is voor elke Java-ontwikkelaar.

De onderstaande code laat twee gemakkelijke manieren zien om door een lijst te itereren:

```java
list.forEach(item -> {
   item.setText("Modified: " + item.getText());
});

for (ListItem item : list) {
   item.setText("Modified2: " + item.getText());
}
```

## Gedeelde lijst eigenschappen {#shared-list-properties}

### Label {#label}

Alle Lijstcomponenten kunnen een label worden toegewezen, dat een beschrijvende tekst of titel is die aan de component is gekoppeld. Labels bieden een korte uitleg of aanwijzing om gebruikers te helpen het doel of de verwachte selectie voor die specifieke lijst te begrijpen. Naast hun belang voor de bruikbaarheid, spelen lijstlabels ook een cruciale rol in toegankelijkheid, waardoor schermlezers en ondersteunende technologieën nauwkeurige informatie kunnen geven en de navigatie met het toetsenbord kunnen vergemakkelijken.

### Helper tekst {#helper-text}

Elke Lijstcomponent kan helpertekst onder de lijst weergeven met behulp van de `setHelperText()` methode. Deze helpertekst biedt aanvullende context of uitleg over de beschikbare opties, zodat gebruikers de nodige informatie hebben om weloverwogen keuzes te maken.

### Horizontale uitlijning {#horizontal-alignment}

Alle lijstcomponenten implementeren de <JavadocLink type="foundation" location="com/webforj/concern/HasHorizontalAlignment" code='true'>HasHorizontalAlignment</JavadocLink> interface, waarmee u controle hebt over hoe tekst en inhoud binnen de component zijn uitgelijnd.

Gebruik de `setHorizontalAlignment()` methode om de uitlijning in te stellen:

- `HorizontalAlignment.LEFT` (standaard)
- `HorizontalAlignment.MIDDLE`
- `HorizontalAlignment.RIGHT`

```java
ListBox<String> listBox = new ListBox<>();
listBox.setHorizontalAlignment(HorizontalAlignment.LEFT);
```

Om de huidige uitlijning op te halen:
```java
HorizontalAlignment alignment = listBox.getHorizontalAlignment();
```

### Expansies {#expanses}

Alle lijstcomponenten in webforJ implementeren ook de <JavadocLink type="foundation" location="com/webforj/concern/HasExpanse" code='true'>HasExpanse</JavadocLink> interface, waarmee u de algehele grootte en visuele impact van de component kunt aanpassen. Dit is nuttig voor het aanpassen van de component aan verschillende UI-contexten, zoals formulieren, dialoogvensters, zijbalken, enz.

Gebruik de `setExpanse()` methode om het expansieniveau in te stellen. Opties zijn onder andere:

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

U kunt de huidige instelling ophalen met:
```java
Expanse current = listBox.getExpanse();
```

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
