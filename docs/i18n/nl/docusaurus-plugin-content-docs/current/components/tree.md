---
title: Tree
sidebar_position: 150
description: >-
  Display hierarchical data with the Tree component, using TreeNode parent-child
  links, expand or collapse, icons, and selection.
_i18n_hash: 0d536028b5d1148a59b52128c41278a5
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree` component organiseert gegevens als een hiëarchie van knooppunten. Elk knooppunt heeft een unieke sleutel en een label. Knooppunten verbinden om ouder-kindrelaties te vormen. Je kunt knooppunten uitbreiden of inklappen om hun kinderen te tonen of te verbergen. Pictogrammen verduidelijken wat voor soort knooppunt je bekijkt en of het geselecteerd is. Selectie ondersteunt het kiezen van één knooppunt of meerdere tegelijk.

<!-- INTRO_END -->

## Node model en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` {#the-role-of-treenode}

Elk stuk gegevens in de boom is verpakt in een `TreeNode`. Dit object houdt de sleutel, het tekstlabel en koppelingen naar zijn ouder- en kindknooppunten. Het wortelknooppunt is speciaal: het bestaat in elke boom maar is niet zichtbaar. Het dient als de container voor alle top-level knooppunten, waardoor de boomstructuur gemakkelijker intern te beheren is.

Omdat knooppunten referenties naar hun ouders en kinderen behouden, is het doorlopen van de boom eenvoudig. Of je nu omhoog, omlaag wilt bewegen of een specifiek knooppunt op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Knooppunt creatie en beheer {#node-creation-and-management}

Knooppunten worden gemaakt met behulp van eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te verstrekken of alleen tekst (die fungeert als de sleutel). Dit garandeert dat elk knooppunt geldig en uniek identificeerbaar is.

Knooppunten aan de boom toevoegen houdt in dat je `add()` of `insert()` op een ouderknooppunt aanroept. Deze methoden regelen het toewijzen van de ouderreferentie en informeren de boom om zijn gebruikersinterface bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Slechts één ouder
Pogingen om hetzelfde knooppunt aan meer dan één ouder toe te wijzen, leiden tot een uitzondering. Deze waarborg zorgt ervoor dat de boom een juiste hiërarchie behoudt door te voorkomen dat knooppunten meerdere ouders hebben, wat de integriteit van de structuur zou schaden en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Knooppunten aanpassen {#modifying-nodes}

Je werkt het label van een knooppunt bij door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor het knooppunt in de boom wordt weergegeven.

Om een specifiek kindknooppunt te verwijderen, gebruik je `remove(TreeNode child)`. Dit haalt het kind bij zijn ouder weg en verwijdert het uit de boomstructuur. Het maakt ook de ouderreferentie leeg.

Als je alle kinderen van een knooppunt wilt wissen, roep je `removeAll()` aan. Dit verwijdert elk kindknooppunt, maakt hun ouderreferenties leeg en legt de kinderenlijst leeg.

Elk knooppunt ondersteunt het opslaan van extra informatie aan de serverzijde met behulp van `setUserData(Object key, Object data)`. Dit stelt je in staat om willekeurige metadata of referenties aan het knooppunt te koppelen, zonder deze gegevens aan de klant of de gebruikersinterface bloot te stellen.

:::tip De Demo gebruiken om Knoptekst te Bewerken
In de demo, dubbelklik op een knooppunt om een editor voor de tekst te openen. Voer de nieuwe tekst in en sla deze op om het label van het knooppunt in de boom bij te werken.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/frontend/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Pictogrammen {#icons}

Pictogrammen bieden visuele aanwijzingen over wat knooppunten vertegenwoordigen en hun status. Ze verbeteren de leesbaarheid door knooptiketten en selectie-status in een oogopslag te onderscheiden. De `Tree` component ondersteunt het instellen van standaardpictogrammen globaal, het aanpassen van pictogrammen per knooppunt en het in- en uitschakelen van pictogram zichtbaarheid.

### Globale pictogrammen {#global-icons}

De boom laat je standaardpictogrammen instellen voor ingeklapte groepen, uitgebreide groepen, bladeren en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Pictogrambronnen
Een pictogram kan een geldige webforJ [pictogram](./icon) definitie zijn of een bronbestand dat is geladen via een webforJ [ondersteunde middelenprotocol](../managing-resources/assets-protocols).
:::

### Pictogrammen per knooppunt {#per-node-icons}

Je kunt globale standaardinstellingen overschrijven door pictogrammen aan individuele knooppunten toe te wijzen. Dit is nuttig wanneer bepaalde knooppunten verschillende concepten vertegenwoordigen, zoals “project” mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Pictogram zichtbaarheid {#icon-visibility}

Soms wil je misschien pictogrammen voor groepen of bladeren verbergen om rommel te verminderen. De component laat je toe om de zichtbaarheid globaal voor deze categorieën in te schakelen, zodat je de uitstraling van de boom kunt vereenvoudigen zonder de structuur te verliezen.

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

## Knooppunt uitbreiding en inklappen {#node-expansion-and-collapse}

Knooppunten kunnen worden uitgebreid of ingeklapt om te bepalen welke delen van de boom zichtbaar zijn. Dit maakt het mogelijk om je te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitbreid- en inklapbewerkingen {#expand-and-collapse-operations}

De boom ondersteunt het uitbreiden en inklappen van individuele knooppunten zowel op hun sleutel als op directe referentie. Je kunt ook alle afstammelingen van een knooppunt in één keer uitbreiden of inklappen.

Deze bewerkingen stellen je in staat om te controleren hoeveel van de boom zichtbaar is en ondersteunen lazy-loading van gegevens of focus op interessante gebieden.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// inklappen van sub bomen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Het inklappen van de root
Het wortelknooppunt verankert de boom maar blijft verborgen. Het inklappen van de root zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te vermijden, wordt het inklappen van de root eigenlijk het inklappen van al zijn kinderen, maar houdt de root intern uitgebreid, zodat de boom zijn inhoud correct blijft tonen.
:::

### Lazy loading knooppunten {#lazy-loading-nodes}

De boom ondersteunt lazy loading van kindknopen door te reageren op uitbreidevenementen. Wanneer een gebruiker een knooppunt uitbreidt, kan je applicatie de kinderen van dat knooppunt dynamisch laden of genereren. Dit verbetert de prestaties door alleen zichtbare delen van de boom op aanvraag te laden.

Gebruik het `onExpand` evenement om te detecteren wanneer een knooppunt is uitgebreid. Controleer binnen de handler of de kinderen van het knooppunt plaatsaanduidingen zijn (bijvoorbeeld een spinner of leeg knooppunt) en vervang ze met daadwerkelijke gegevens zodra deze zijn geladen.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Selectie {#selection}

Selectie bepaalt welke knooppunten door de gebruiker zijn gekozen. De `Tree` component ondersteunt flexibele modi en API's om knooppunten te selecteren, deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

Je kunt kiezen of de boom het selecteren van een enkel knooppunt tegelijk of meerdere knooppunten tegelijkertijd toestaat. Overschakelen van meerdere naar enkele selectie deselecteert automatisch alle behalve het eerste geselecteerde knooppunt.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie interactie
Wanneer de boom is ingesteld op meerdere selectiemodus, kunnen gebruikers meer dan één knooppunt tegelijk selecteren. De manier waarop dit werkt, hangt af van het apparaat:

* **Desktop (muis en toetsenbord):** Gebruikers houden de **Ctrl** toets (of **Cmd** toets op macOS) ingedrukt en klikken op knooppunten om deze aan de huidige selectie toe te voegen of te verwijderen. Dit maakt het mogelijk om meerdere individuele knooppunten te selecteren zonder andere te deselecteren.
* **Mobiele en aanraakapparaten:** Aangezien er geen modifier-toetsen beschikbaar zijn, tikken gebruikers eenvoudig op knooppunten om deze te selecteren of te deselecteren. Elke tik schakelt de selectiestatus van dat knooppunt om, waardoor eenvoudige multi-selectie door middel van eenvoudige tikken mogelijk is.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knooppunten kunnen worden geselecteerd of gedeselecteerd door referentie, sleutel, individueel of in batches. Je kunt ook alle kinderen van een knooppunt in één oproep selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knooppunt op referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knooppunt op referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// selecteren of deselecteren van kinderen van knooppunten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Selectiestaat ophalen {#selection-state-retrieval}

Je kunt de huidige selectie verkrijgen door de onderstaande code te gebruiken:

```java
// haal de referentie van het geselecteerde knooppunt op
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// haal de sleutel van het geselecteerde knooppunt op
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## Styling {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
