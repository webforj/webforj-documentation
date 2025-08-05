---
title: Tree
sidebar_position: 150
_i18n_hash: b161d0d5855f65cb593cf23bc2695d5b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree` component organiseert gegevens als een hiërarchie van knooppunten. Elk knooppunt heeft een unieke sleutel en een label. Knooppunten verbinden om ouder-kindrelaties te vormen. Je kunt knooppunten uitbreiden of samenvouwen om hun kinderen al dan niet te tonen. Pictogrammen verduidelijken welk type knooppunt je bekijkt en of het geselecteerd is. Selectie ondersteunt het kiezen van één of meerdere knooppunten tegelijk.

## Node model en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` {#the-role-of-treenode}

Elk stuk gegevens in de boom is ingepakt in een `TreeNode`. Dit object bevat de sleutel, het tekstlabel en koppelingen naar zijn ouder- en kindknopen. De wortelknoop is speciaal: hij bestaat in elke boom maar is niet zichtbaar. Het dient als de container voor alle bovenliggende knooppunten, waardoor de boomstructuur intern gemakkelijker te beheersen is.

Omdat knooppunten verwijzingen naar hun ouders en kinderen bijhouden, is het doorbladeren van de boom eenvoudig. Of je nu omhoog of omlaag wilt bewegen, of een specifiek knooppunt op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Node creatie en beheer {#node-creation-and-management}

Knooppunten worden gemaakt met behulp van eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te bieden, hetzij gewoon tekst (die ook als sleutel fungeert). Dit garandeert dat elk knooppunt geldig en uniek identificeerbaar is.

Het toevoegen van knooppunten aan de boom houdt in dat je `add()` of `insert()` oproept op een ouderknoop. Deze methoden regelen het toewijzen van de ouderreferentie en informeren de boom om zijn UI bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Alleen enkele ouder
Proberen om dezelfde knoop aan meer dan één ouder toe te wijzen, resulteert in een exceptie. Deze waarborg zorgt ervoor dat de boom een correcte hiërarchie handhaaft door te voorkomen dat knooppunten meerdere ouders hebben, wat de integriteit van de structuur zou schenden en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Het wijzigen van knooppunten {#modifying-nodes}

Je werkt het label van een knoop bij door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor de knoop in de boom wordt weergegeven.

Om een specifiek kindknop te verwijderen, gebruik je `remove(TreeNode child)`. Dit detacheren het kind van zijn ouder en verwijdert het uit de boomstructuur. Het wist ook de ouderreferentie.

Als je alle kinderen van een knoop wilt wissen, noem je `removeAll()`. Dit verwijdert elk kindknoop, wist hun ouderreferenties en leegt de kinderenlijst.

Elk knooppunt ondersteunt het opslaan van aanvullende informatie aan de serverzijde met behulp van `setUserData(Object key, Object data)`. Hiermee kun je willekeurige metadata of referenties aan de knoop koppelen, zonder deze gegevens aan de client of de UI bloot te stellen.

:::tip Gebruik de Demo om de tekst van een knoop te bewerken
In de demo, dubbelklik op een knoop om een editor voor zijn tekst te openen. Voer de nieuwe tekst in en sla deze op om het label van de knoop in de boom bij te werken.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Pictogrammen {#icons}

Pictogrammen bieden visuele aanwijzingen over wat knopen vertegenwoordigen en hun status. Ze verbeteren de leesbaarheid door knooppunttypen en selectiestatus in één oogopslag te onderscheiden. De `Tree` component ondersteunt het instellen van standaard pictogrammen globaal, het aanpassen van pictogrammen per knoop, en het toggelen van pictogramzichtbaarheid.

### Globale pictogrammen {#global-icons}

De boom laat je standaard pictogrammen instellen voor samengevouwen groepen, uitgebreide groepen, bladknopen en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Pictogram bronnen
Een pictogram kan elke geldige webforJ [pictogram](./icon) definitie zijn of een resourcebestand dat is geladen via een webforJ [ondersteunde assets-protocollen](../managing-resources/assets-protocols).
:::

### Pictogrammen per knoop {#per-node-icons}

Je kunt globale standaardinstellingen overschrijven door pictogrammen aan individuele knopen toe te wijzen. Dit is nuttig wanneer bepaalde knooppunten verschillende concepten vertegenwoordigen, zoals “project” mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Pictogram zichtbaarheid {#icon-visibility}

Soms wil je pictogrammen voor groepen of bladeren verbergen om rommel te verminderen. De component laat je de zichtbaarheid globaal voor deze categorieën toggelen, waardoor je de uitstraling van de boom kunt vereenvoudigen zonder de structuur te verliezen.

Voorbeeld:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## Node uitbreiding en samenvouwen {#node-expansion-and-collapse}

Knooppunten kunnen worden uitgebreid of samengevouwen om te bepalen welke delen van de boom zichtbaar zijn. Dit maakt het mogelijk om je te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitbreidings- en samenvouwoperaties {#expand-and-collapse-operations}

De boom ondersteunt het uitbreiden en samenvouwen van individuele knopen op basis van hun sleutel of directe referentie. Je kunt ook alle afstammelingen van een knoop in één keer uitbreiden of samenvouwen.

Deze operaties stellen je in staat om te bepalen hoeveel van de boom zichtbaar is en ondersteunen lazy-loading van gegevens of focussen op gebieden van belang.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// samenvouwen van subbomen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Samenvouwen van de wortel
De wortelknoop verankert de boom maar blijft verborgen. Het samenvouwen van de wortel zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te voorkomen, leidt het samenvouwen van de wortel er eigenlijk toe dat al zijn kinderen worden samengevouwen, maar houdt de wortel intern uitgebreid, waardoor de boom nog steeds zijn inhoud correct toont.
:::

### Lazy loading knooppunten {#lazy-loading-nodes}

De boom ondersteunt lazy loading van kindknopen door te reageren op uitbreidevenementen. Wanneer een gebruiker een knoop uitbreidt, kan je app de kinderen van die knoop dynamisch laden of genereren. Dit verbetert de prestaties door alleen zichtbare delen van de boom op aanvraag te laden.

Gebruik het `onExpand` evenement om te detecteren wanneer een knoop is uitgebouwd. Controleer in de handler of de kinderen van de knoop plaatsaanduidingen zijn (bijvoorbeeld een draaiknop of lege knoop) en vervang ze door daadwerkelijke gegevens zodra ze zijn geladen.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selectie {#selection}

Selectie controleert welke knopen door de gebruiker zijn gekozen. De `Tree` component ondersteunt flexibele modi en API's om knopen te selecteren, deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

Je kunt kiezen of de boom het selecteren van één knoop tegelijk of meerdere knopen tegelijkertijd toestaat. Overstappen van meerdere naar enkele selectie deselecteert automatisch alle knopen behalve de eerste geselecteerde knoop.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie-interactie
Wanneer de boom is ingesteld op de modus voor meerdere selecties, kunnen gebruikers meer dan één knoop tegelijk selecteren. Hoe dit werkt, hangt af van het apparaat:

* **Desktop (muis en toetsenbord):** Gebruikers houden de **Ctrl**-toets (of **Cmd**-toets op macOS) ingedrukt en klikken op knopen om ze aan de huidige selectie toe te voegen of te verwijderen. Dit maakt het mogelijk om meerdere individuele knopen te selecteren zonder andere te deselecteren.
* **Mobiele en touchapparaten:** Aangezien modifier-toetsen niet beschikbaar zijn, tappen gebruikers gewoon op knopen om ze te selecteren of te deselecteren. Elke tik toggelt de selectiestatus van die knoop, wat eenvoudig meerdere selectie door eenvoudige tikken mogelijk maakt.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knooppunten kunnen worden geselecteerd of gedeselecteerd op basis van referentie, sleutel, individueel of in batches. Je kunt ook alle kinderen van een knoop in één oproep selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knoop op basis van referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knoop op basis van referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// selecteren of deselecteren van kinderen van knopen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Oproep van selectiestatus {#selection-state-retrieval}

Je kunt de huidige selectie verkrijgen door gebruik te maken van de onderstaande code:

```java
// krijg de referentie van geselecteerde knoop
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// krijg de sleutel van geselecteerde knoop
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Stijl {#styling}

<TableBuilder name="Tree" />
