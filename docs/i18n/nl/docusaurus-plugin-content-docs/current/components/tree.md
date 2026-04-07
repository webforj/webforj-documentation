---
title: Tree
sidebar_position: 150
_i18n_hash: 2302136423928266f164623de9a234b4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

De `Tree`-component organiseert gegevens als een hiërarchie van knopen. Elke knoop heeft een unieke sleutel en een label. Knopen verbinden om ouders-kindrelaties te vormen. Je kunt knopen uitvouwen of samentrekken om hun kinderen te tonen of te verbergen. Pictogrammen verduidelijken met welk type knoop je te maken hebt en of deze is geselecteerd. Selectie ondersteunt het kiezen van één knoop of meerdere tegelijk.

<!-- INTRO_END -->

## Node model en boomstructuur {#node-model-and-tree-structure}

### De rol van `TreeNode` {#the-role-of-treenode}

Elke gegevensstuk in de boom is verpakt in een `TreeNode`. Dit object bevat de sleutel, het tekstlabel en links naar de ouder- en kindknopen. De wortelknoop is speciaal: deze bestaat in elke boom maar is niet zichtbaar. Het dient als de container voor alle knopen op het hoogste niveau, waardoor de boomstructuur gemakkelijker intern te beheren is.

Omdat knopen referenties naar hun ouders en kinderen behouden, is het doorlopen van de boom eenvoudig. Of je nu omhoog, omlaag wilt bewegen of een specifieke knoop op sleutel wilt vinden, de verbindingen zijn altijd toegankelijk.

### Het maken en beheren van knopen {#node-creation-and-management}

Knopen worden gemaakt met behulp van eenvoudige fabrieksmethoden, hetzij door een sleutel en tekst te geven of alleen tekst (die ook de sleutel is). Dit garandeert dat elke knoop geldig en uniek identificeerbaar is.

Knopen aan de boom toevoegen omvat het aanroepen van `add()` of `insert()` op een ouderknoop. Deze methoden regelen het toewijzen van de ouderreferentie en informeren de boom om de UI bij te werken.

Voorbeeld:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Enkelvoudige ouder alleen
Proberen om dezelfde knoop aan meer dan één ouder toe te wijzen resulteert in een uitzondering. Deze bescherming zorgt ervoor dat de boom een juiste hiërarchie behoudt door te voorkomen dat knopen meerdere ouders hebben, wat de integriteit van de structuur zou verbreken en onverwacht gedrag zou veroorzaken.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Knopen wijzigen {#modifying-nodes}

Je kunt het label van een knoop bijwerken door `setText(String text)` aan te roepen. Deze methode verandert de tekst die voor de knoop in de boom wordt getoond.

Om een specifieke kindknop te verwijderen, gebruik je `remove(TreeNode child)`. Dit ontkoppelt het kind van zijn ouder en verwijdert het uit de boomstructuur. Het wist ook de ouderreferentie.

Als je alle kinderen van een knoop wilt wissen, roep je `removeAll()` aan. Dit verwijdert elke kindknop, wist hun ouderreferenties en leegt de kinderlijst.

Elke knoop ondersteunt het opslaan van aanvullende informatie aan de serverzijde met `setUserData(Object key, Object data)`. Hiermee kun je willekeurige metadata of referenties aan de knoop koppelen, zonder deze gegevens aan de client of de UI bloot te stellen.

:::tip De demo gebruiken om knooptekst te bewerken
In de demo, dubbelklik op een knoop om een editor voor de tekst te openen. Voer de nieuwe tekst in en sla deze op om het label van de knoop in de boom bij te werken.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Pictogrammen {#icons}

Pictogrammen bieden visuele aanwijzingen over wat knopen vertegenwoordigen en hun status. Ze verbeteren de leesbaarheid door knoopsoorten en selectiestatus snel te onderscheiden. De `Tree`-component ondersteunt het wereldwijd instellen van standaardpictogrammen, het aanpassen van pictogrammen per knoop en het schakelen van pictogramzichtbaarheid.

### Standaardpictogrammen {#global-icons}

De boom laat je standaardpictogrammen instellen voor ingeklapte groepen, uitgeklapte groepen, bladknopen en geselecteerde bladeren.

Voorbeeld:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Pictogrambronnen
Een pictogram kan elke geldige webforJ [pictogram](./icon) definitie zijn of een hulpbronbestand dat is geladen via een webforJ [ondersteunde hulpbronnenprotocollen](../managing-resources/assets-protocols).
:::

### Per-knoop pictogrammen {#per-node-icons}

Je kunt wereldwijde standaardinstellingen overschrijven door pictogrammen toe te wijzen aan individuele knopen. Dit is nuttig wanneer bepaalde knopen verschillende concepten vertegenwoordigen, zoals “project” mappen of speciale bestanden.

Voorbeeld:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Pictogramzichtbaarheid {#icon-visibility}

Soms wil je pictogrammen voor groepen of bladeren verbergen om rommel te verminderen. De component laat je de zichtbaarheid wereldwijd voor deze categorieën schakelen, zodat je de uitstraling van de boom kunt vereenvoudigen zonder de structuur te verliezen.

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

## Knoopuitvouwen en -samenvouwen {#node-expansion-and-collapse}

Knopen kunnen worden uitgeklapt of samengevouwen om te bepalen welke delen van de boom zichtbaar zijn. Dit stelt je in staat om je te concentreren op relevante secties en ondersteunt scenario's zoals lazy loading of dynamische gegevensupdates.

### Uitklap- en samenvouwoperaties {#expand-and-collapse-operations}

De boom ondersteunt het uitvouwen en samenvouwen van individuele knopen op basis van hun sleutel of directe referentie. Je kunt ook alle afstammelingen van een knoop in één keer uitklappen of samenvouwen.

Deze operaties stellen je in staat om te controleren hoeveel van de boom zichtbaar is en ondersteunen het lazy-loading van gegevens of focus op gebieden van belang.

Voorbeeld:

```java
tree.expand(node);
tree.collapse(key);

// samenvouwen sub-bomen
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Het samenvouwen van de wortel
De wortelknoop verankert de boom maar blijft verborgen. Het samenvouwen van de wortel zou normaal gesproken alles verbergen, waardoor de boom leeg lijkt. Om dit te vermijden, zorgt het samenvouwen van de wortel ervoor dat alle kinderen worden samengevouwen maar de wortel intern wordt uitgeklapt, zodat de boom zijn inhoud correct blijft tonen.
:::

### Lazy loading van knopen {#lazy-loading-nodes}

De boom ondersteunt lazy loading van kindknopen door te reageren op uitvouwgebeurtenissen. Wanneer een gebruiker een knoop uitklapt, kan jouw app de kinderen van die knoop dynamisch laden of genereren. Dit verbetert de prestaties door alleen zichtbare delen van de boom op aanvraag te laden.

Gebruik de `onExpand`-gebeurtenis om te detecteren wanneer een knoop wordt uitgeklapt. Binnen de handler, controleer of de kinderen van de knoop plekhouders zijn (bijvoorbeeld een spinner of lege knoop) en vervang ze door daadwerkelijke gegevens zodra ze zijn geladen.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selectie {#selection}

Selectie bestuurt welke knopen door de gebruiker zijn gekozen. De `Tree`-component ondersteunt flexibele modi en API's om knopen te selecteren, deselecteren en op te vragen.

### Selectiemodi {#selection-modes}

Je kunt kiezen of de boom het selecteren van een enkele knoop tegelijk of meerdere knopen tegelijkertijd toestaat. Overstappen van meerdere naar enkele selectie deselecteert automatisch alle knopen behalve de eerste geselecteerde knoop.

Voorbeeld:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selectie interactie
Wanneer de boom is ingesteld op de modus voor meerdere selecties, kunnen gebruikers meer dan één knoop tegelijk selecteren. Hoe dit werkt, hangt af van het apparaat:

* **Desktop (muis en toetsenbord):** Gebruikers houden de **Ctrl**-toets (of **Cmd**-toets op macOS) ingedrukt en klikken op knopen om ze aan de huidige selectie toe te voegen of uit deze te verwijderen. Dit maakt het mogelijk om meerdere afzonderlijke knopen te selecteren zonder andere te deselecteren.
* **Mobiele apparaten en touch apparaten:** Aangezien modifier-toetsen niet beschikbaar zijn, tikken gebruikers eenvoudig op knopen om deze te selecteren of deselecteren. Elke tik schakelt de selectiestatus van die knoop om, waardoor gemakkelijke multi-selectie mogelijk is door middel van eenvoudige tikken.
:::

### Selecteren en deselecteren {#selecting-and-deselecting}

Knopen kunnen worden geselecteerd of gedeselecteerd op referentie, sleutel, individueel of in batches. Je kunt ook alle kinderen van een knoop in één oproep selecteren of deselecteren.

Voorbeeld:

```java
// selecteer knoop op referentie of sleutel
tree.select(node);
tree.selectKey(key);

// deselecteer knoop op referentie of sleutel
tree.deselect(node);
tree.deselectAll();

// selecteren of deselecteren van kinderen van knopen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Selectiestaat ophalen {#selection-state-retrieval}

Je kunt de huidige selectie krijgen door gebruik te maken van de onderstaande code:

```java
// verkrijg de referentie van de geselecteerde knoop
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// verkrijg de sleutel van de geselecteerde knoop
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Stijlen {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
