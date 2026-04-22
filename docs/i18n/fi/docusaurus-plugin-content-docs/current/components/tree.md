---
title: Tree
sidebar_position: 150
_i18n_hash: 6d2decdf16e3054012a22aca28980ccf
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree` komponentti järjestää tietoa solmujen hierarkiana. Jokaisella solmulla on ainutlaatuinen avain ja merkintä. Solmut yhdistyvät mu形成vanvan vanhempi-lapsisuhteita. Voit laajentaa tai supistaa solmuja näyttääkseen tai piilottaakseen niiden lapsia. Ikonit selventävät, minkä tyyppisestä solmusta on kyse ja onko se valittu. Valinta tukee yhden tai useamman solmun valitsemista samanaikaisesti.

<!-- INTRO_END -->

## Node model and tree structure {#node-model-and-tree-structure}

### The role of `TreeNode` {#the-role-of-treenode}

Jokainen tietopala puussa on kääritty `TreeNode`-objektiin. Tämä objekti pitää sisällään avaimen, tekstimerkin ja linkit sen vanhempiin ja lapsisolmuihin. Juurisolmu on erityinen: se esiintyy jokaisessa puussa, mutta ei ole näkyvissä. Se toimii kaikkien ylimmän tason solmujen säiliönä, mikä tekee puurakenteen hallinnasta helpompaa sisäisesti.

Koska solmut pitävät viittauksia vanhempiinsa ja lapsiinsa, puun läpikäyminen on suoraviivaista. Riippumatta siitä, haluatko siirtää ylös, alas tai löytää tietyn solmun sen avaimen perusteella, yhteydet ovat aina käytettävissä.

### Node creation and management {#node-creation-and-management}

Solmut luodaan yksinkertaisilla tehdasmenetelmillä, joko antamalla avain ja teksti tai vain teksti (joka toimii myös avaimena). Tämä takaa, että jokainen solmu on voimassa ja yksilöitävissä.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhempi-solmulle. Nämä menetelmät hoitavat vanhempi viittauksen määrittämisen ja ilmoittavat puulle käyttöliittymän päivittämisestä.

Esimerkki:

```java
TreeNode parent = Tree.node("Vanhempi");
TreeNode child = Tree.node("Lapsi");
parent.add(child);
tree.add(parent);
```

:::info Single Parent Only
Yrittäessäsi määrittää samaa solmua useammalle vanhemmalle, heittää poikkeuksen. Tämä suojatoimi varmistaa, että puu säilyttää oikean hierarkian estämällä solmuja saamasta useampaa vanhempaa, mikä rikkoisi rakenteen eheyttä ja aiheuttaisi odottamatonta käyttäytymistä.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Modifying nodes {#modifying-nodes}

Voit päivittää solmun merkinnän kutsumalla `setText(String text)`. Tämä menetelmä muuttaa puussa näkyvän solmun tekstin.

Poistaaksesi tietyn lapsisolmun, käytä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmasta ja poistaa sen puurakenteesta. Se myös tyhjentää vanhempi viittauksen.

Jos haluat tyhjentää kaikki lapset solmusta, kutsu `removeAll()`. Tämä poistaa jokaisen lapsisolmun, tyhjentää niiden vanhempi viittaukset ja tyhjentää lapsiluettelon.

Jokainen solmu tukee ylimääräisten tietojen tallentamista palvelinpuolella käyttäen `setUserData(Object key, Object data)`. Tämä antaa sinun liittää mielivaltaista metadataa tai viittauksia solmuun ilman, että tämä data paljastuu asiakkaalle tai käyttöliittymälle.

:::tip Using the Demo to Edit Node Text
Demoissa voit kaksinkertaisesti napsauttaa solmua avataksesi tekstieditorin sille. Syötä uusi teksti ja tallenna se päivittääksesi solmun merkintää puussa.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
cssURL='/css/tree/tree-modify-view.css'
height='320px'
/>

## Icons {#icons}

Ikonit tarjoavat visuaalisia vihjeitä siitä, mitä solmut edustavat ja niiden tila. Ne parantavat luettavuutta erottamalla solmutyypit ja valintatilat yhdellä silmäyksellä. `Tree` komponentti tukee oletusikonien asettamista globaalisti, mukauttamista solmukohtaisesti ja ikonien näkyvyyden vaihtamista.

### Global icons {#global-icons}

Puu antaa sinun asettaa oletusikonit suljetuille ryhmille, avatuile ryhmille, lehtisolmuille ja valituille lehtisolmuille.

Esimerkki:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Icon Sources
Ikoni voi olla mikä tahansa voimassa oleva webforJ [ikoni](./icon) määrittely tai resurssitiedosto, joka on ladattu webforJ [tuettujen resurssiprotokollien](../managing-resources/assets-protocols) kautta.
:::

### Per-node icons {#per-node-icons}

Voit ohittaa globaalit oletukset määrittämällä ikoneja yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat erilaisia konsepteja, kuten "projekti" kansioita tai erikoistiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Icon visibility {#icon-visibility}

Joskus saatat haluta piilottaa ikoneita ryhmiltä tai lehdiltä vähentääksesi kaaosta. Komponentti antaa sinun vaihtaa näkyvyyttä globaalisti näille kategorioille, mikä antaa sinun yksinkertaistaa puun ulkoasua ilman, että rakenne kärsii.

Esimerkki:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo 
path='/webforj/treeicons?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeIconsView.java'
height='320px'
/>

## Node expansion and collapse {#node-expansion-and-collapse}

Solmut voidaan laajentaa tai supistaa hallitsemaan, mitkä osat puustaa ovat näkyviä. Tämä mahdollistaa keskittymisen olennaisiin osiin ja tukee skenaarioita, kuten laiskakulutusta tai dynaamisia tietojen päivityksiä.

### Expand and collapse operations {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja supistamista joko niiden avaimen tai suoran viittauksen avulla. Voit myös laajentaa tai supistaa kaikkia lapsia solmusta kerralla.

Nämä toiminnot antavat sinulle mahdollisuuden hallita, kuinka paljon puusta on näkyvissä ja tukevat laiskalatausta tai keskittymistä kiinnostaviin alueisiin.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// supista alapuuleet
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Collapsing root
Juurisolmu kiinnittää puun mutta pysyy piilossa. Juuren supistaminen piilottaisi normaalisti kaiken, jolloin puu näyttäisi tyhjältä. Välttääksesi tätä, juuren supistaminen supistaa oikeastaan kaikkia sen lapsia mutta pitää juuren laajennettuna sisäisesti, varmistaen että puu näyttää silti sisältönsä oikein.
:::

### Lazy loading nodes {#lazy-loading-nodes}

Puu tukee solmujen lasten laiskakulutusta reagoimalla laajentumistapahtumiin. Kun käyttäjä laajentaa solmun, sovelluksesi voi ladata tai luoda sen lapsia dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain näkyviä puun osia tarpeen mukaan.

Käytä `onExpand` tapahtumaa havaitaksesi, kun solmu on laajennettu. Käsittelijän sisällä, tarkista onko solmun lapset paikkamerkkejä (esimerkiksi pyörivä latauskierros tai tyhjät solmut) ja korvata ne todellisilla tiedoilla, kun ne on ladattu.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Selection {#selection}

Valinta hallitsee, mitkä solmut käyttäjä valitsee. `Tree` komponentti tukee joustavia tiloja ja API:ita solmujen valintaan, poistamiseen ja kysymiseen.

### Selection modes {#selection-modes}

Voit valita, salliiko puu valita vain yhden solmun kerrallaan vai useita solmuja samanaikaisesti. Siirtyminen useasta valinnasta yksittäiseen valintaan poistaa automaattisesti valinnasta kaikki paitsi ensimmäisen valitun solmun.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Multi-selection interaction
Kun puu on asetettu usean valinnan tilaan, käyttäjät voivat valita useamman kuin yhden solmun kerrallaan. Tämä toimii laitteesta riippuen:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:llä) ja napsauttavat solmuja lisätäkseen tai poistaakseen ne nykyisestä valinnasta. Tämä mahdollistaa useiden erillisten solmujen valitsemisen ilman muiden poistamista.
* **Mobiili ja kosketuslaitteet:** Koska modifikaatiota ei ole saatavilla, käyttäjät napsauttavat solmuja valitakseen tai poistaakseen ne valinnasta. Jokainen napsautus vaihtaa kyseisen solmun valintatilaa, mikä mahdollistaa helpon monivalinnan yksinkertaisilla napsautuksilla.
:::

### Selecting and deselecting {#selecting-and-deselecting}

Solmut voidaan valita tai poistaa viittauksen, avaimen, yksilöllisesti tai erissä. Voit myös valita tai poistaa jokaisen lapsen solmusta yhdellä kutsulla.

Esimerkki:

```java
// valitse solmu viittauksen tai avaimen perusteella
tree.select(node);
tree.selectKey(key);

// poista valinta solmusta viittauksen tai avaimen perusteella
tree.deselect(node);
tree.deselectAll();

// solmujen lasten valinta tai poistaminen
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Selection state retrieval {#selection-state-retrieval}

Voit saada nykyisen valinnan hyödyntämällä alla näkyvää koodia:

```java
// saa valitun solmun viittauksen
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// saa valitun solmun avaimen
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
