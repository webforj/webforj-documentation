---
title: Tree
sidebar_position: 150
_i18n_hash: dacd1e2a128f112d2b7e4a4fd7836feb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree` component organiseert gegevens als een hiërarchie van knooppunten. Elk knooppunt heeft een unieke sleutel en een label. Knooppunten zijn met elkaar verbonden om ouder-kindrelaties te vormen. U kunt knooppunten uitbreiden of inkrimpen om hun kinderen te tonen of te verbergen. Pictogrammen verduidelijken met wat voor soort knooppunt u te maken heeft en of het geselecteerd is. Selectie ondersteunt het kiezen van één knooppunt of meerdere tegelijk.

<!-- INTRO_END -->

## Node model en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` {#the-role-of-treenode}

Elke gegevensstuk in de boom is verpakt in een `TreeNode`. Dit object bevat de sleutel, het tekstlabel, en links naar zijn ouder- en kindknopen. De wortelknoop is speciaal: hij bestaat in elke boom maar is niet zichtbaar. Hij dient als de container voor alle knooppunten op het hoogste niveau, waardoor de boomstructuur gemakkelijker intern te beheren is.

Omdat knooppunten verwijzingen naar hun ouders en kinderen behouden, is het doorlopen van de boom eenvoudig. Of u nu omhoog, omlaag wilt bewegen of een specifiek knooppunt op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Node creatie en beheer {#node-creation-and-management}

Knooppunten worden aangemaakt met behulp van eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te bieden of alleen tekst (die tevens als sleutel fungeert). Dit garandeert dat elk knooppunt geldig en uniek identificeerbaar is.

Knooppunten toevoegen aan de boom houdt in dat u `add()` of `insert()` op een ouderknoop aanroept. Deze methoden regelen het toewijzen van de ouderreferentie en het informeren van de boom om zijn gebruikersinterface bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Alleen een ouder
Proberen om hetzelfde knooppunt aan meer dan één ouder toe te wijzen, resulteert in het genereren van een uitzondering. Deze bescherming zorgt ervoor dat de boom een juiste hiërarchie behoudt door te voorkomen dat knooppunten meerdere ouders hebben, wat de integriteit van de structuur zou breken en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Het wijzigen van knooppunten {#modifying-nodes}

U kunt het label van een knooppunt bijwerken door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor het knooppunt in de boom wordt getoond.

Om een specifiek kindknooppunt te verwijderen, gebruikt u `remove(TreeNode child)`. Dit koppelt het kind los van zijn ouder en verwijdert het uit de boomstructuur. Het maakt ook de ouderreferentie leeg.

Als u alle kinderen van een knooppunt wilt wissen, roept u `removeAll()` aan. Dit verwijdert elk kindknooppunt, maakt hun ouderreferenties leeg en leegt de kinderenlijst.

Elk knooppunt ondersteunt het opslaan van aanvullende informatie aan de serverzijde met behulp van `setUserData(Object key, Object data)`. Dit stelt u in staat om willekeurige metadata of referenties met het knooppunt te associëren, zonder deze gegevens aan de client of de gebruikersinterface bloot te stellen.

:::tip De demo gebruiken om de tekst van knooppunten te bewerken
In de demo, dubbelklik op een knooppunt om een editor voor de tekst te openen. Voer de nieuwe tekst in en sla deze op om het label van het knooppunt in de boom bij te werken.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/resources/static/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Pictogrammen {#icons}

Pictogrammen bieden visuele aanwijzingen over wat knooppunten voorstellen en hun staat. Ze verbeteren de leesbaarheid door knooptype en selectiestatus in één oogopslag te onderscheiden. De `Tree` component ondersteunt het instellen van standaardpictogrammen op wereldwijde schaal, het aanpassen van pictogrammen per knooppunt en het wisselen van pictogramzichtbaarheid.

### Wereldwijde pictogrammen {#global-icons}

De boom laat u standaardpictogrammen instellen voor ingeklapte groepen, uitgeklapte groepen, bladknopen en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Pictogrambronnen
Een pictogram kan elke geldige webforJ [pictogram](./icon) definitie zijn of een hulpbronbestand dat is geladen via een webforJ [ondersteunde hulpbronprotocollen](../managing-resources/assets-protocols).
:::

### Pictogrammen per knooppunt {#per-node-icons}

U kunt wereldwijde standaardinstellingen overschrijven door pictogrammen aan individuele knooppunten toe te wijzen. Dit is nuttig wanneer bepaalde knooppunten verschillende concepten vertegenwoordigen, zoals “project” mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Pictogramzichtbaarheid {#icon-visibility}

Soms wilt u misschien pictogrammen voor groepen of bladeren verbergen om rommel te verminderen. De component laat u de zichtbaarheid op wereldwijde schaal voor deze categorieën toggelen, waardoor u het uiterlijk van de boom kunt vereenvoudigen zonder de structuur te verliezen.

Voorbeeld:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo
path='/webforj/treeicons'
files={['src/main/java/com/webforj/samples/views/tree/TreeIconsView.java']}
height='320px'
/>

## Uitbreiden en inkrimpen van knooppunten {#node-expansion-and-collapse}

Knooppunten kunnen worden uitgebreid of ingekrompen om te bepalen welke delen van de boom zichtbaar zijn. Dit maakt het mogelijk om zich te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitbreidings- en inkrimpbewerkingen {#expand-and-collapse-operations}

De boom ondersteunt het uitbreiden en inkorten van individuele knooppunten, zowel op basis van hun sleutel als op directe referentie. U kunt ook alle afstammelingen van een knooppunt tegelijk uitbreiden of inkorten.

Deze bewerkingen laten u controleren hoeveel van de boom zichtbaar is en ondersteunen lazy-loading van gegevens of concentratie op interessante gebieden.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// sub bomen inkorten
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Het inkorten van de wortel
De wortelknoop verankert de boom maar blijft verborgen. Het inkorten van de wortel zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te voorkomen, wordt het inkorten van de wortel eigenlijk de kinderen van de wortel ingekort, maar blijft de wortel intern uitgebreid, zodat de boom zijn inhoud correct blijft weergeven.
:::

### Lazy loading van knooppunten {#lazy-loading-nodes}

De boom ondersteunt lazy loading van de kinderen van knooppunten door te reageren op uitbreidevenementen. Wanneer een gebruiker een knooppunt uitbreidt, kan uw app de kinderen van dat knooppunt dynamisch laden of genereren. Dit verbetert de prestaties door alleen de zichtbare delen van de boom op verzoek te laden.

Gebruik het `onExpand` evenement om te detecteren wanneer een knooppunt wordt uitgebreid. In de handler, controleer of de kinderen van het knooppunt plaatsvervangers zijn (bijvoorbeeld een spinner of leeg knooppunt) en vervang ze door werkelijke gegevens zodra ze zijn geladen.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Selectie {#selection}

Selectie bepaalt welke knooppunten door de gebruiker zijn gekozen. De `Tree` component ondersteunt flexibele modi en API's om knooppunten te selecteren, deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

U kunt kiezen of de boom het selecteren van een enkel knooppunt tegelijk of meerdere knooppunten tegelijk toestaat. Het overschakelen van meerdere naar enkele selectie deselecteert automatisch alle behalve het eerste geselecteerde knooppunt.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie-interactie
Wanneer de boom is ingesteld op de modus voor meerdere selecties, kunnen gebruikers meer dan één knooppunt tegelijk selecteren. Hoe dit werkt, hangt af van het apparaat:

* **Desktop (muisklik en toetsenbord):** Gebruikers houden de **Ctrl** toets (of **Cmd** toets op macOS) ingedrukt en klikken op knooppunten om ze aan de huidige selectie toe te voegen of te verwijderen. Dit maakt het mogelijk om meerdere individuele knooppunten te selecteren zonder andere te deselecteren.
* **Mobiele en aanrakingsapparaten:** Aangezien modifier-toetsen niet beschikbaar zijn, tikken gebruikers simpelweg op knooppunten om ze te selecteren of te deselecteren. Elke tik wisselt de selectietoestand van dat knooppunt, waardoor eenvoudig multi-selectie mogelijk is via eenvoudige tikken.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knooppunten kunnen worden geselecteerd of gedeselecteerd op basis van referentie, sleutel, individueel of in batches. U kunt ook alle kinderen van een knooppunt in één keer selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knooppunt op basis van referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knooppunt op basis van referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// kinderen van knooppunten selecteren of deselecteren
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Selectietoestand ophalen {#selection-state-retrieval}

U kunt de huidige selectie ophalen door gebruik te maken van de onderstaande code:

```java
// verkrijg de referentie van het geselecteerde knooppunt
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// verkrijg de sleutel van het geselecteerde knooppunt
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## Stylen {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
