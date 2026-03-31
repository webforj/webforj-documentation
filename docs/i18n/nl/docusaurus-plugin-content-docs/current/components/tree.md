---
title: Tree
sidebar_position: 150
_i18n_hash: 280fb07f73ba1172b33bd0617ded7876
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree` component organiseert gegevens als een hiërarchie van knooppunten. Elk knooppunt heeft een unieke sleutel en een label. Knooppunten verbinden zich om ouder-kindrelaties te vormen. Je kunt knooppunten uitbreiden of inkrimpen om hun kinderen weer te geven of te verbergen. Pictogrammen verduidelijken wat voor soort knooppunt je bekijkt en of het geselecteerd is. Selectie ondersteunt het kiezen van één knooppunt of meerdere tegelijk.

<!-- INTRO_END -->

## Node model en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` {#the-role-of-treenode}

Elk stuk gegevens in de boom is verpakt in een `TreeNode`. Dit object bevat de sleutel, het tekstlabel en links naar de ouder- en kindknooppunten. Het wortelknooppunt is speciaal: het bestaat in elke boom maar is niet zichtbaar. Het dient als de container voor alle top-level knooppunten, waardoor de boomstructuur gemakkelijker intern te beheren is.

Omdat knooppunten verwijzingen naar hun ouders en kinderen behouden, is het doorlopen van de boom eenvoudig. Of je nu omhoog, omlaag wilt bewegen of een specifiek knooppunt op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Knooppunt creatie en beheer {#node-creation-and-management}

Knooppunten worden gemaakt met behulp van eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te verstrekken of alleen tekst (die dubbel fungeert als de sleutel). Dit garandeert dat elk knooppunt geldig en uniek identificeerbaar is.

Knooppunten aan de boom toevoegen houdt in dat je `add()` of `insert()` op een ouderknooppunt aanroept. Deze methoden regelen het toewijzen van de ouderreferentie en melden de boom om zijn UI bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Enkelvoudige ouder alleen
Proberen om hetzelfde knooppunt aan meer dan één ouder toe te wijzen, zal resulteren in een uitzondering. Deze beveiliging zorgt ervoor dat de boom een goede hiërarchie behoudt door te voorkomen dat knooppunten meerdere ouders hebben, wat de integriteit van de structuur zou breken en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knooppunten wijzigen {#modifying-nodes}

Je kunt het label van een knooppunt bijwerken door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor het knooppunt in de boom wordt weergegeven.

Om een specifiek kindknooppunt te verwijderen, gebruik je `remove(TreeNode child)`. Dit koppelt het kind los van zijn ouder en verwijdert het uit de boomstructuur. Het wis ook de ouderreferentie.

Als je alle kinderen van een knooppunt wilt wissen, roep je `removeAll()` aan. Dit verwijdert elk kindknooppunt, wist hun ouderreferenties en leegt de lijst met kinderen.

Elk knooppunt ondersteunt het opslaan van extra informatie aan de serverside met `setUserData(Object key, Object data)`. Dit stelt je in staat om willekeurige metadata of referenties aan het knooppunt te koppelen, zonder deze gegevens bloot te stellen aan de client of de UI.

:::tip Gebruik de demo om de tekst van een knooppunt te bewerken
In de demo, dubbelklik op een knooppunt om een editor voor de tekst te openen. Voer de nieuwe tekst in en sla deze op om het label van het knooppunt in de boom bij te werken.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Pictogrammen {#icons}

Pictogrammen bieden visuele aanwijzingen over waar knooppunten voor staan en hun status. Ze verbeteren de leesbaarheid door knooppunttypes en selectie-status in één oogopslag te onderscheiden. De `Tree` component ondersteunt het instellen van standaardpictogrammen globaal, het aanpassen van pictogrammen per knooppunt, en het toggelen van de zichtbaarheid van pictogrammen.

### Globale pictogrammen {#global-icons}

De boom laat je standaardpictogrammen instellen voor ingeklapte groepen, uitgeklapte groepen, bladknopen en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Pictogrambronnen
Een pictogram kan elke geldige webforJ [pictogram](./icon) definitie zijn of een hulpbestand dat via een webforJ [ondersteunde middelenprotocollen](../managing-resources/assets-protocols) is geladen.
:::

### Per-knooppunt pictogrammen {#per-node-icons}

Je kunt globale standaarden overschrijven door pictogrammen aan individuele knooppunten toe te wijzen. Dit is nuttig wanneer bepaalde knooppunten verschillende concepten vertegenwoordigen, zoals "project" mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Pictogram zichtbaarheidsinstellingen {#icon-visibility}

Soms wil je misschien pictogrammen voor groepen of bladeren verbergen om rommel te verminderen. De component laat je de zichtbaarheid globaal voor deze categorieën toggelen, waardoor je de uitstraling van de boom kunt vereenvoudigen zonder de structuur te verliezen.

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

## Knooppuntuitbreiding en -inkrimping {#node-expansion-and-collapse}

Knooppunten kunnen worden uitgebreid of ingekrompen om te bepalen welke delen van de boom zichtbaar zijn. Dit maakt het mogelijk om je te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitbreidings- en inkrompingoperaties {#expand-and-collapse-operations}

De boom ondersteunt het uitbreiden en inkrompen van individuele knooppunten op basis van hun sleutel of directe referentie. Je kunt ook alle afgeleiden van een knooppunt in één keer uitbreiden of inkrompen.

Deze operaties stellen je in staat te bepalen hoeveel van de boom zichtbaar is en ondersteunen lazy-loading van data of focus op gebieden van belang.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// subbomen inkrompen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Het inkrompen van de wortel
Het wortelknooppunt verankert de boom maar blijft verborgen. Het inkrompen van de wortel zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te voorkomen, verkleint het inkrompen van de wortel eigenlijk al zijn kinderen, maar houdt de wortel intern uitgebreid, zodat de boom nog steeds zijn inhoud correct weergeeft.
:::

### Lazy loading van knooppunten {#lazy-loading-nodes}

De boom ondersteunt lazy loading van kindknopen door te reageren op uitbreidingsgebeurtenissen. Wanneer een gebruiker een knooppunt uitbreidt, kan je app de kinderen van dat knooppunt dynamisch laden of genereren. Dit verbetert de prestaties door alleen zichtbare delen van de boom op aanvraag te laden.

Gebruik de `onExpand` gebeurtenis om te detecteren wanneer een knooppunt wordt uitgebreid. Controleer in de handler of de kinderen van het knooppunt plaatsvervangers zijn (bijvoorbeeld een spinner of leeg knooppunt) en vervang ze door actuele gegevens zodra ze zijn geladen.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selectie {#selection}

Selectie regelt welke knooppunten door de gebruiker zijn gekozen. De `Tree` component ondersteunt flexibele modi en API's om knooppunten te selecteren, deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

Je kunt kiezen of de boom het selecteren van één knooppunt tegelijk of meerdere knooppunten tegelijkertijd toestaat. Overschakelen van meerdere selectie naar enkele selectie deselecteert automatisch alle, behalve het eerste geselecteerde knooppunt.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie-interactie
Wanneer de boom is ingesteld op meerdere selectiemodus, kunnen gebruikers meer dan één knooppunt tegelijk selecteren. De manier waarop dit werkt, hangt af van het apparaat:

* **Desktop (muisklik en toetsenbord):** Gebruikers houden de **Ctrl**-toets (of de **Cmd**-toets op macOS) ingedrukt en klikken op knooppunten om ze aan de huidige selectie toe te voegen of te verwijderen. Dit maakt het mogelijk om meerdere individuele knooppunten te selecteren zonder anderen te deselecteren.
* **Mobiele en aanraakapparaten:** Aangezien modifier-toetsen niet beschikbaar zijn, tikken gebruikers eenvoudig op knooppunten om ze te selecteren of deselecteren. Elke tik schakelt de selectiestatus van dat knooppunt om, waardoor eenvoudige multi-selectie door middel van simpele tikken mogelijk is.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knooppunten kunnen worden geselecteerd of gedeselecteerd op basis van referentie, sleutel, individueel of in batches. Je kunt ook alle kinderen van een knooppunt in één oproep selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knooppunt op referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knooppunt op referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// kiezen of deselecteren van kinderen van knooppunten
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Selectiestatus ophalen {#selection-state-retrieval}

Je kunt de huidige selectie verkrijgen door gebruik te maken van de onderstaande code:

```java
// haal de referentie van het geselecteerde knooppunt op
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// haal de sleutel van het geselecteerde knooppunt op
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Styling {#styling}

<TableBuilder name="Tree" />
