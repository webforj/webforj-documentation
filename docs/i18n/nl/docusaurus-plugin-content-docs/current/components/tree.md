---
title: Tree
sidebar_position: 150
_i18n_hash: 8f653af18f5e041d09896794f560d30a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree` component organiseert gegevens als een hiërarchie van knooppunten. Elk knooppunt heeft een unieke sleutel en een label. Knooppunten verbinden zich om ouder-kindrelaties te vormen. Je kunt knooppunten uitbreiden of inklappen om hun kinderen weer te geven of te verbergen. Pictogrammen verduidelijken welk type knooppunt je bekijkt en of het is geselecteerd. Selectie ondersteunt het kiezen van één knooppunt of meerdere tegelijk.

## Node model en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` {#the-role-of-treenode}

Elke gegevensstuk in de boom is verpakt in een `TreeNode`. Dit object bevat de sleutel, het tekstlabel en koppelingen naar zijn ouder- en kindknopen. De rootknop is speciaal: deze bestaat in elke boom maar is niet zichtbaar. Het dient als de container voor alle top-level knooppunten, waardoor de boomstructuur gemakkelijker intern te beheren is.

Omdat knooppunten verwijzingen naar hun ouders en kinderen behouden, is het doorlopen van de boom eenvoudig. Of je nu omhoog, omlaag wilt bewegen of een specifiek knooppunt op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Knooppunt creatie en beheer {#node-creation-and-management}

Knooppunten worden gemaakt met eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te bieden of alleen tekst (die ook als de sleutel fungeert). Dit garandeert dat elk knooppunt geldig en uniek identificeerbaar is.

Knooppunten aan de boom toevoegen gebeurt door `add()` of `insert()` aan te roepen op een ouderknoop. Deze methoden zorgen voor het toewijzen van de ouderreferentie en stellen de boom in kennis om zijn UI bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Enkelvoudige ouder alleen
Proberen om hetzelfde knooppunt aan meer dan één ouder toe te wijzen, zal resulteren in het genereren van een uitzondering. Deze waarborg zorgt ervoor dat de boom een goede hiërarchie behoudt door te voorkomen dat knooppunten meerdere ouders hebben, wat de integriteit van de structuur zou verstoren en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knopen aanpassen {#modifying-nodes}

Je kunt het label van een knooppunt bijwerken door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor het knooppunt in de boom wordt weergegeven.

Om een specifiek kindknop te verwijderen, gebruik je `remove(TreeNode child)`. Dit ontkoppelt het kind van zijn ouder en verwijdert het uit de boomstructuur. Het ruimt ook de ouderreferentie op.

Als je alle kinderen van een knooppunt wilt wissen, roep je `removeAll()` aan. Dit verwijdert elk kindknooppunt, ruimt hun ouderreferenties op en leegt de lijst met kinderen.

Elk knooppunt ondersteunt het opslaan van aanvullende informatie aan de serverzijde met behulp van `setUserData(Object key, Object data)`. Hiermee kun je willekeurige metadata of verwijzingen aan het knooppunt koppelen, zonder deze gegevens bloot te stellen aan de client of de UI.

:::tip Gebruik de demo om knooppunttekst te bewerken
In de demo, dubbelklik op een knooppunt om een editor voor de tekst te openen. Voer de nieuwe tekst in en sla deze op om het label van het knooppunt in de boom bij te werken.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Pictogrammen {#icons}

Pictogrammen bieden visuele aanwijzingen over wat knooppunten vertegenwoordigen en hun status. Ze verbeteren de leesbaarheid door knooppunttypen en selectie status in één oogopslag te onderscheiden. De `Tree` component ondersteunt het instellen van standaardpictogrammen, het aanpassen van pictogrammen per knooppunt en het omzetten van de zichtbaarheid van pictogrammen.

### Standaardpictogrammen {#global-icons}

De boom stelt je in staat om standaardpictogrammen in te stellen voor ingeklapte groepen, uitgeklapte groepen, bladknooppunten en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Pictogrambronnen
Een pictogram kan elke geldige webforJ [pictogram](./icon) definitie zijn of een bronbestand dat is geladen via een webforJ [ondersteunde middelenprotocollen](../managing-resources/assets-protocols).
:::

### Pictogrammen per knooppunt {#per-node-icons}

Je kunt standaardinstellingen overschrijven door pictogrammen aan individuele knooppunten toe te wijzen. Dit is nuttig wanneer bepaalde knooppunten verschillende concepten vertegenwoordigen, zoals "project" mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Zichtbaarheid van pictogrammen {#icon-visibility}

Soms wil je misschien pictogrammen voor groepen of bladeren verbergen om rommel te verminderen. De component stelt je in staat om de zichtbaarheid globaal voor deze categorieën te schakelen, zodat je het uiterlijk van de boom kunt vereenvoudigen zonder de structuur te verliezen.

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

## Uitbreiden en inklappen van knooppunten {#node-expansion-and-collapse}

Knooppunten kunnen worden uitgebreid of ingeklapt om te regelen welke delen van de boom zichtbaar zijn. Dit maakt het mogelijk je te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitbreiden en inklappen operaties {#expand-and-collapse-operations}

De boom ondersteunt het uitbreiden en inklappen van individuele knooppunten op basis van hun sleutel of directe referentie. Je kunt ook alle afstammelingen van een knooppunt in één keer uitbreiden of inklappen.

Deze operaties stellen je in staat te controleren hoeveel van de boom zichtbaar is en ondersteunen lazy-loading van gegevens of het focussen op gebieden van belang.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// inklappen van subbomen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Inklappen van de root
De rootknoop verankert de boom maar blijft verborgen. Inklappen van de root zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te voorkomen, verkleint het inklappen van de root eigenlijk alle kinderen, maar houdt de root intern uitgebreid, zodat de boom nog steeds zijn inhoud correct toont.
:::

### Lazy loading van knooppunten {#lazy-loading-nodes}

De boom ondersteunt lazy loading van kinderen van knooppunten door te reageren op uitbreidevenementen. Wanneer een gebruiker een knooppunt uitbreidt, kan je app de kinderen van dat knooppunt dynamisch laden of genereren. Dit verbetert de prestaties door alleen zichtbare delen van de boom op aanvraag te laden.

Gebruik het `onExpand` evenement om te detecteren wanneer een knooppunt is uitgebreid. Controleer binnen de handler of de kinderen van het knooppunt tijdelijke aanduidingen zijn (bijvoorbeeld een spinnende of lege knoop) en vervang ze door daadwerkelijke gegevens zodra deze zijn geladen.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selectie {#selection}

Selectie beheert welke knooppunten door de gebruiker zijn gekozen. De `Tree` component ondersteunt flexibele modi en API's om knooppunten te selecteren, deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

Je kunt kiezen of de boom het selecteren van een enkel knooppunt tegelijk of meerdere knooppunten tegelijkertijd toestaat. Overschakelen van meerdere naar enkele selectie deselecteert automatisch alle behalve het eerste geselecteerde knooppunt.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie interactie
Wanneer de boom is ingesteld op meerdere selectiemodus, kunnen gebruikers meer dan één knooppunt tegelijk selecteren. Hoe dit werkt, hangt af van het apparaat:

* **Desktop (muis en toetsenbord):** Gebruikers houden de **Ctrl** toets (of **Cmd** toets op macOS) ingedrukt en klikken op knooppunten om ze aan de huidige selectie toe te voegen of te verwijderen. Dit maakt het mogelijk om meerdere individuele knooppunten te selecteren zonder andere te deselecteren.
* **Mobiele en aanraakapparaten:** Aangezien modifier-toetsen niet beschikbaar zijn, tikken gebruikers simpelweg op knooppunten om ze te selecteren of te deselecteren. Elke tik schakelt de selectiestatus van dat knooppunt om, waardoor eenvoudige multi-selectie via eenvoudige tikken mogelijk is.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knooppunten kunnen worden geselecteerd of gedeselecteerd op basis van referentie, sleutel, individueel of in batches. Je kunt ook alle kinderen van een knooppunt in één keer selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knooppunt op basis van referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knooppunt op basis van referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// selecteren of deselecteren van kinderen van knooppunten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Opvragen van selectiestatus {#selection-state-retrieval}

Je kunt de huidige selectie krijgen door gebruik te maken van de onderstaande code:

```java
// krijg de referentie van het geselecteerde knooppunt
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// krijg de sleutel van het geselecteerde knooppunt
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Stijling {#styling}

<TableBuilder name="Tree" />
