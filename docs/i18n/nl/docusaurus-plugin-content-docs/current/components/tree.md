---
title: Tree
sidebar_position: 150
_i18n_hash: 6d2decdf16e3054012a22aca28980ccf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree` component organiseert gegevens als een hiërarchie van knooppunten. Elk knooppunt bevat een unieke sleutel en een label. Knooppunten verbinden zich om ouder-kindrelaties te vormen. Je kunt knooppunten uitbreiden of samenvouwen om hun kinderen te tonen of te verbergen. Iconen verduidelijken wat voor soort knooppunt je bekijkt en of het geselecteerd is. Selectie ondersteunt het kiezen van één knooppunt of meerdere tegelijk.

<!-- INTRO_END -->

## Node model en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` {#the-role-of-treenode}

Elke gegevensstuk in de boom is verpakt in een `TreeNode`. Dit object bevat de sleutel, het tekstlabel en koppelingen naar de ouder- en kindknopen. De wortelknoop is speciaal: hij bestaat in elke boom maar is niet zichtbaar. Hij fungeert als de container voor alle top-level knooppunten, waardoor de boomstructuur intern gemakkelijker te beheren is.

Omdat knooppunten verwijzingen naar hun ouders en kinderen behouden, is het doorlopen van de boom eenvoudig. Of je nu omhoog, omlaag wilt bewegen of een specifiek knooppunt op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Knoop creatie en beheer {#node-creation-and-management}

Knooppunten worden gemaakt met behulp van eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te verstrekken, of alleen tekst (die ook als sleutel fungeert). Dit garandeert dat elk knooppunt geldig en uniek identificeerbaar is.

Het toevoegen van knooppunten aan de boom omvat het aanroepen van `add()` of `insert()` op een ouderknoop. Deze methoden zorgen voor de toewijzing van de ouderreferentie en informeren de boom om zijn UI bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Enkele ouders alleen
Proberen om dezelfde knoop aan meer dan één ouder toe te wijzen, resulteert in het gooien van een uitzondering. Deze beveiliging zorgt ervoor dat de boom een correcte hiërarchie behoudt door knooppunten te voorkomen die meerdere ouders hebben, wat de integriteit van de structuur zou verstoren en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Het wijzigen van knooppunten {#modifying-nodes}

Je kunt het label van een knoop bijwerken door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor de knoop in de boom wordt weergegeven.

Om een specifiek kindknoop te verwijderen, gebruik je `remove(TreeNode child)`. Dit ontkoppelt het kind van zijn ouder en verwijdert het uit de boomstructuur. Het wist ook de ouderreferentie.

Als je alle kinderen van een knoop wilt wissen, roep dan `removeAll()` aan. Dit verwijdert elk kindknoop, wist hun ouderreferenties en leegt de kinderlijst.

Elke knoop ondersteunt het opslaan van aanvullende informatie aan de serverzijde met `setUserData(Object key, Object data)`. Dit stelt je in staat om willekeurige metadata of referenties aan de knoop te koppelen, zonder deze gegevens bloot te stellen aan de client of de UI.

:::tip Gebruik de demo om de knooptekst te bewerken
In de demo, dubbelklik op een knoop om een editor voor de tekst te openen. Voer de nieuwe tekst in en sla deze op om het label van de knoop in de boom te bijgewerken.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
cssURL='/css/tree/tree-modify-view.css'
height='320px'
/>

## Iconen {#icons}

Iconen bieden visuele aanwijzingen over wat knooppunten vertegenwoordigen en hun status. Ze verbeteren de leesbaarheid door knoopt typen en selectie status in één oogopslag te onderscheiden. De `Tree` component ondersteunt het instellen van standaardiconen op globale schaal, het aanpassen van iconen per knoop en het in-/uitschakelen van de zichtbaarheid van iconen.

### Globale iconen {#global-icons}

De boom laat je standaardiconen instellen voor samengevouwen groepen, uitgebreide groepen, bladknopen en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Iconenbronnen
Een icoon kan elke geldige webforJ [icoon](./icon) definitie zijn of een resourcebestand dat is geladen via een webforJ [ondersteunde middelenprotocollen](../managing-resources/assets-protocols).
:::

### Per-knoop iconen {#per-node-icons}

Je kunt globale standaardinstellingen overschrijven door iconen aan individuele knooppunten toe te wijzen. Dit is nuttig wanneer bepaalde knooppunten verschillende concepten vertegenwoordigen, zoals “project” mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Iconen zichtbaarheid {#icon-visibility}

Soms wil je misschien iconen voor groepen of bladeren verbergen om rommel te verminderen. De component laat je de zichtbaarheid globaal voor deze categorieën in-/uitschakelen, zodat je de uitstraling van de boom kunt vereenvoudigen zonder de structuur te verliezen.

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

## Knoop uitbreiding en samenvouwen {#node-expansion-and-collapse}

Knooppunten kunnen worden uitgebreid of samengevouwen om te bepalen welke delen van de boom zichtbaar zijn. Dit maakt het mogelijk om je te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitbreiden en samenvouwen bewerkingen {#expand-and-collapse-operations}

De boom ondersteunt het uitbreiden en samenvouwen van individuele knooppunten op basis van hun sleutel of directe referentie. Je kunt ook alle afstammelingen van een knoop in één keer uitbreiden of samenvouwen.

Deze bewerkingen stellen je in staat om te controleren hoeveel van de boom zichtbaar is en ondersteunen lazy-loading van gegevens of focus op gebieden van interesse.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// samenvouwen van subbomen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Samenvouwen van de wortel
De wortelknoop verankert de boom maar blijft verborgen. Samenvouwen van de wortel zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te voorkomen, samenvouwen van de wortel verkleint in feite al zijn kinderen, maar houdt de wortel intern uitgebreid, zodat de boom nog steeds correct zijn inhoud toont.
:::

### Lazy loading knooppunten {#lazy-loading-nodes}

De boom ondersteunt lazy loading van kindknooppunten door te reageren op uitbreid evenementen. Wanneer een gebruiker een knoop uitbreidt, kan je app de kinderen van die knoop dynamisch laden of genereren. Dit verbetert de prestaties door alleen de zichtbare delen van de boom op aanvraag te laden.

Gebruik de `onExpand` gebeurtenis om te detecteren wanneer een knoop is uitgebreid. Controleer binnen de handler of de kinderen van de knoop tijdelijke aanduidingen zijn (bijvoorbeeld een spinner of lege knoop) en vervang ze door werkelijke gegevens zodra deze zijn geladen.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selectie {#selection}

Selectie controleert welke knooppunten door de gebruiker zijn gekozen. De `Tree` component ondersteunt flexibele modi en API's om knooppunten te selecteren, deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

Je kunt kiezen of de boom het selecteren van een enkel knoop tegelijk toestaat of meerdere knooppunten tegelijkertijd. Overschakelen van meerdere naar enkele selecties deselecteert automatisch alle behalve de eerste geselecteerde knoop.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie interactie
Wanneer de boom is ingesteld op meerdere selectiemodus, kunnen gebruikers meer dan één knoop tegelijk selecteren. De manier waarop dit werkt, is afhankelijk van het apparaat:

* **Desktop (muis en toetsenbord):** Gebruikers houden de **Ctrl** toets (of **Cmd** toets op macOS) ingedrukt en klikken op knopen om ze toe te voegen of te verwijderen uit de huidige selectie. Dit maakt het mogelijk om meerdere afzonderlijke knooppunten te selecteren zonder andere te deselecteren.
* **Mobiele en touch apparaten:** Aangezien modifier-toetsen niet beschikbaar zijn, tikken gebruikers simpelweg op knopen om ze te selecteren of te deselecteren. Elke tik schakelt de selectiestatus van dat knoop om, waardoor eenvoudige multi-selectie mogelijk is door eenvoudige tikken.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knooppunten kunnen worden geselecteerd of gedeselecteerd via referentie, sleutel, individueel of in batches. Je kunt ook alle kinderen van een knoop in één keer selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knoop via referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knoop via referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// selecteren of deselecteren van kinderen van knopen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Selectiestatus ophalen {#selection-state-retrieval}

Je kunt de huidige selectie ophalen door de onderstaande code te gebruiken:

```java
// krijg de referentie van het geselecteerde knoop
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// krijg de sleutel van het geselecteerde knoop
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Styling {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
