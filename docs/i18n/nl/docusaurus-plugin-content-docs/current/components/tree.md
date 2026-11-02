---
title: Tree
sidebar_position: 150
description: >-
  Display hierarchical data with the Tree component, using TreeNode parent-child
  links, expand or collapse, icons, and selection.
_i18n_hash: cacef36e8323b6349fa209a7c057c88b
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree` component organiseert gegevens als een hiërarchie van knooppunten. Elk knooppunt bevat een unieke sleutel en een label. Knopen zijn met elkaar verbonden om ouder-kind relaties te vormen. Je kunt knooppunten uitbreiden of inkrimpen om hun kinderen te tonen of te verbergen. Iconen verduidelijken welk type knooppunt je bekijkt en of het is geselecteerd. Selectie ondersteunt het kiezen van één knooppunt of meerdere tegelijk.

<!-- INTRO_END -->

## Knooppuntmodel en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` <DocChip chip="name" label="dwc-tree-node" /> {#the-role-of-treenode}

Elk gegevenselement in de boom is verpakt in een `TreeNode`. Dit object bevat de sleutel, het tekstlabel en links naar de ouder- en kindknopen. De wortelknoop is speciaal: hij bestaat in elke boom maar is niet zichtbaar. Hij dient als de container voor alle knooppunten op het hoogste niveau, waardoor de boomstructuur intern gemakkelijker te beheren is.

Omdat knopen referenties naar hun ouders en kinderen behouden, is het doorlopen van de boom eenvoudig. Of je nu naar boven, naar beneden wilt bewegen of een specifiek knooppunt op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Het maken en beheren van knopen {#node-creation-and-management}

Knooppunten worden gemaakt met eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te bieden, of alleen tekst (die ook als sleutel dient). Dit garandeert dat elk knooppunt geldig en uniek identificeerbaar is.

Knooppunten aan de boom toevoegen houdt in dat je `add()` of `insert()` op een ouderknoop aanroept. Deze methoden zorgen voor het toewijzen van de ouderreferentie en informeren de boom om zijn UI bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Alleen enkele ouder
Proberen dezelfde knoop aan meer dan één ouder toe te wijzen, resulteert in een uitzondering. Deze beveiliging zorgt ervoor dat de boom een goede hiërarchie behoudt door te voorkomen dat knopen meerdere ouders hebben, wat de integriteit van de structuur zou breken en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Het wijzigen van knopen {#modifying-nodes}

Je kunt het label van een knoop bijwerken door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor de knoop in de boom wordt weergegeven.

Om een specifiek kindknoop te verwijderen, gebruik je `remove(TreeNode child)`. Dit ontkoppelt het kind van zijn ouder en verwijdert het uit de boomstructuur. Het wist ook de ouderreferentie.

Als je alle kinderen van een knoop wilt wissen, roep je `removeAll()` aan. Dit verwijdert elk kindknoop, wist hun ouderreferenties en leeg de lijst met kinderen.

Elke knoop ondersteunt het opslaan van aanvullende informatie aan de serverzijde met `setUserData(Object key, Object data)`. Dit stelt je in staat om willekeurige metadata of referenties aan de knoop te associëren, zonder deze gegevens aan de client of de UI bloot te stellen.

:::tip Gebruik de demo om de tekst van de knoop te bewerken
In de demo, dubbelklik op een knoop om een editor te openen voor de tekst. Voer de nieuwe tekst in en sla op om het label van de knoop in de boom bij te werken.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/frontend/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Iconen {#icons}

Iconen bieden visuele aanwijzingen over wat knopen vertegenwoordigen en hun status. Ze verbeteren de leesbaarheid door knooptypen en selectiestatus in één oogopslag te onderscheiden. De `Tree` component ondersteunt het globaal instellen van standaardiconen, het aanpassen van iconen per knoop en het wisselen van iconzichtbaarheid.

### Globale iconen {#global-icons}

De boom staat je toe standaardiconen in te stellen voor ingeklapte groepen, uitgebreide groepen, bladknopen en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Iconbronnen
Een icoon kan elke geldige webforJ [icoon](./icon) definitie zijn of een bronbestand dat is geladen via een webforJ [ondersteunde middelenprotocollen](../managing-resources/assets-protocols).
:::

### Per-knoop iconen {#per-node-icons}

Je kunt globale standaardinstellingen overschrijven door iconen aan individuele knopen toe te wijzen. Dit is nuttig wanneer bepaalde knopen verschillende concepten vertegenwoordigen, zoals “project” mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Iconzichtbaarheid {#icon-visibility}

Soms wil je misschien iconen voor groepen of bladeren verbergen om rommel te verminderen. De component laat je toe om de zichtbaarheid globaal voor deze categorieën te schakelen, waardoor je de weergave van de boom kunt vereenvoudigen zonder de structuur te verliezen.

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

## Knooppuntuitbreiding en -samenklap {#node-expansion-and-collapse}

Knooppunten kunnen worden uitgebreid of samengeklapt om te beheersen welke delen van de boom zichtbaar zijn. Dit stelt je in staat om je te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitbreidings- en samenvouwoperaties {#expand-and-collapse-operations}

De boom ondersteunt het uitbreiden en samenvouwen van individuele knopen, hetzij via hun sleutel of directe referentie. Je kunt ook alle nakomelingen van een knoop in één keer uitbreiden of samenvouwen.

Deze operaties stellen je in staat om te controleren hoeveel van de boom zichtbaar is en ondersteunen lazy loading van gegevens of focus op gebieden van belang.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// samenvouwen subbomen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Samenvouwen van de wortel
De wortelknoop verankert de boom maar blijft verborgen. Het samenvouwen van de wortel zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te voorkomen, verkleint het samenvouwen van de wortel eigenlijk alle kinderen maar houdt de wortel intern uitgebreid, zodat de boom zijn inhoud correct blijft tonen.
:::

### Lazy loading van knopen {#lazy-loading-nodes}

De boom ondersteunt lazy loading van kindknopen door te reageren op uitbreidingsgebeurtenissen. Wanneer een gebruiker een knoop uitbreidt, kan je app die knoopkinderen dynamisch laden of genereren. Dit verbetert de prestaties door alleen zichtbare delen van de boom op aanvraag te laden.

Gebruik het `onExpand` evenement om te detecteren wanneer een knoop wordt uitgebreid. Controleer binnen de handler of de kinderen van de knoop tijdelijke aanduidingen zijn (bijvoorbeeld een spinner of lege knoop) en vervang deze door daadwerkelijke gegevens zodra ze zijn geladen.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Selectie {#selection}

Selectie controleert welke knopen door de gebruiker zijn gekozen. De `Tree` component ondersteunt flexibele modi en API's om knopen te selecteren, te deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

Je kunt kiezen of de boom het selecteren van één knoop tegelijk of meerdere knopen tegelijkertijd toestaat. Overschakelen van meerdere naar enkele selectie deselecteert automatisch alles behalve de eerste geselecteerde knoop.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie interactie
Wanneer de boom is ingesteld op de modus voor meerdere selecties, kunnen gebruikers meer dan één knoop tegelijkertijd selecteren. De manier waarop dit werkt, hangt af van het apparaat:

* **Desktop (muis en toetsenbord):** Gebruikers houden de **Ctrl** toets (of **Cmd** toets op macOS) ingedrukt en klikken op knopen om ze toe te voegen of te verwijderen van de huidige selectie. Hierdoor kunnen gebruikers meerdere individuele knopen selecteren zonder andere te deselecteren.
* **Mobiele en touch-apparaten:** Aangezien modifier-toetsen niet beschikbaar zijn, tikken gebruikers eenvoudig op knopen om ze te selecteren of deselecteren. Elke tik schakelt de selectiestatus van die knoop om, waardoor eenvoudige multi-selectie mogelijk is via eenvoudige tikken.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knooppunten kunnen worden geselecteerd of gedeselecteerd op referentie, sleutel, individueel of in batches. Je kunt ook alle kinderen van een knoop in één keer selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knoop op referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knoop op referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// geselecteerde of gedeselecteerde kinderen van knopen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Opvragen van de selectiestatus {#selection-state-retrieval}

Je kunt de huidige selectie krijgen door de onderstaande code te gebruiken:

```java
// krijg de referentie van de geselecteerde knoop
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// krijg de sleutel van de geselecteerde knoop
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## Stijlen {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
