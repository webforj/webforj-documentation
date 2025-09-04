---
title: Tree
sidebar_position: 150
_i18n_hash: 8f653af18f5e041d09896794f560d30a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree`-komponentti organisoi tietoa solmujen hierarkiana. Jokainen solmu pitää sisällään ainutlaatuisen avaimen ja merkinnän. Solmut yhdistyvät muodostaen vanhempi-lapsi-suhteita. Voit laajentaa tai kutistaa solmuja näyttämään tai piilottamaan niiden lapsia. Ikonit selkeyttävät, minkä tyyppisestä solmusta on kyse ja onko se valittu. Valinta mahdollistaa yhden tai useamman solmun valitsemisen kerralla.

## Solmumalli ja puurakenne {#node-model-and-tree-structure}

### `TreeNode`:n rooli {#the-role-of-treenode}

Jokainen tietokappale puussa on kääritty `TreeNode`:n sisään. Tämä objekti säilyttää avaimen, tekstimerkinnän ja linkit sen vanhempiin ja lapsisolmuihin. Juuresolmu on erityinen: se on olemassa jokaisessa puussa, mutta ei ole näkyvissä. Se toimii kaikkien ylimmän tason solmujen astiana, mikä helpottaa puurakenteen hallintaa sisäisesti.

Koska solmut pitävät viittauksia vanhempiinsa ja lapsiinsa, puun tutkiminen on suoraviivaista. Olitpa haluamassa siirtyä ylöspäin, alaspäin tai löytää tietyn solmun avaimen mukaan, yhteydet ovat aina saavutettavissa.

### Solmujen luominen ja hallinta {#node-creation-and-management}

Solmut luodaan yksinkertaisilla tehdasmenetelmillä, joko antamalla avain ja teksti tai pelkkä teksti (joka toimii myös avaimena). Tämä takaa, että jokainen solmu on voimassa ja ainutlaatuisesti tunnistettavissa.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhempisolmulle. Nämä menetelmät käsittelevät vanhemman viittauksen määrittämistä ja ilmoittavat puulle käyttöliittymän päivittämisestä.

Esimerkki:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Vain yksi vanhempi
Yrittäminen määrittää sama solmu useammalle vanhemmalle johtaa poikkeuksen heittämiseen. Tämä suojatoimi takaa, että puu säilyttää oikean hierarkian estämällä solmuja saamasta useita vanhempia, mikä rikkoisi rakenteen eheyttä ja aiheuttaisi odottamatonta käyttäytymistä.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Solmujen muokkaaminen {#modifying-nodes}

Voit päivittää solmun merkintää kutsumalla `setText(String text)`. Tämä menetelmä muuttaa tekstiä, joka näkyy solmulle puussa.

Poistaaksesi tietyn lapsisolmun, käytä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmasta ja poistaa sen puurakenteesta. Se myös tyhjentää vanhemman viittauksen.

Jos haluat tyhjentää kaikki lapset solmusta, kutsu `removeAll()`. Tämä poistaa jokaisen lapsisolmun, tyhjentää niiden vanhemman viittaukset ja tyhjentää lasten listan.

Jokainen solmu tukee lisätietojen tallentamista palvelinpuolella käyttämällä `setUserData(Object key, Object data)`. Tämä antaa sinun liittää satunnaista metadataa tai viittauksia solmuun, ilman että nämä tiedot altistuvat asiakkaalle tai käyttöliittymälle.

:::tip Demo käytön avustaminen solmun tekstin muokkaamiseen
Demossa kaksoisnapsauta solmua avataksesi muokkaustyökalun sen tekstille. Syötä uusi teksti ja tallenna se päivittääksesi solmun merkintä puussa.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Ikonit {#icons}

Ikonit antavat visuaalisia vihjeitä siitä, mitä solmut esittävät ja mikä niiden tila on. Ne parantavat luettavuutta erottamalla solmunt tyypit ja valintatilat heti yhdellä silmäyksellä. `Tree`-komponentti tukee oletusikonien määrittämistä globaalisti, ikoneiden mukauttamista per solmu ja ikonien näkyvyyden kytkemistä.

### Globaalit ikonit {#global-icons}

Puu antaa sinun asettaa oletusikonit kutistuneille ryhmille, laajentuneille ryhmille, lehtisolmuille ja valituille lehdille.

Esimerkki:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Ikonilähteet
Ikoni voi olla mikä tahansa voimassa oleva webforJ [ikoni](./icon) määritelmä tai resurssitiedosto, joka on ladattu webforJ:n [tuettujen resurssiprotokollien](../managing-resources/assets-protocols) kautta.
:::

### Per-solmu ikonit {#per-node-icons}

Voit ohittaa globaalit oletukset määrittämällä ikoneja yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat erilaisia käsitteitä, kuten “projekti” kansioita tai erityisiä tiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Ikonien näkyvyys {#icon-visibility}

Joskus saatat haluta piilottaa ikonit ryhmiltä tai lehdiltä vähentääksesi sotkua. Komponentti antaa sinun kytkeä näkyvyyden globaalisti näille kategoriolle, mikä mahdollistaa puun ulkoasun yksinkertaistamisen ilman rakenteen menettämistä.

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

## Solmujen laajentaminen ja kutistaminen {#node-expansion-and-collapse}

Solmuja voidaan laajentaa tai kutistaa valitsemalla, mitkä osat puusta ovat näkyvissä. Tämä mahdollistaa keskittymisen olennaisiin osiin ja tukee skenaarioita, kuten laiskaa lataamista tai dynaamisia tietopäivityksiä.

### Laajentamis- ja kutistamistoimet {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja kutistamista joko niiden avaimilla tai suora viittauksella. Voit myös laajentaa tai kutistaa kaikki solmun jälkeläiset kerralla.

Nämä toimet antavat sinulle hallita, kuinka suuri osa puusta on näkyvissä ja tukevat datan laiskalatausta tai kiinnostavien alueiden korostamista.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// kutista alapuolista puuta
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Juureksien kutistaminen
Juuresolmu ankkuroi puun, mutta pysyy piilossa. Juureksen kutistaminen piilottaisi normaalisti kaiken, jolloin puu näyttäisi tyhjältä. Tämän välttämiseksi juureksen kutistaminen kutistaa itse asiassa kaikki sen lapset, mutta pitää juureksen laajennettuna sisäisesti, varmistaen, että puu näyttää silti sisältönsä oikein.
:::

### Laiska lataus solmuista {#lazy-loading-nodes}

Puu tukee solmun lasten laiskaa lataamista reagoimalla laajentamistapahtumiin. Kun käyttäjä laajentaa solmua, sovelluksesi voi ladata tai luoda kyseisen solmun lapsia dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain näkyvät osat puusta tarpeen mukaan.

Käytä `onExpand`-tapahtumaa havaitaksesi, kun solmu laajenee. Käsittelijän sisällä tarkista, ovatko solmun lapset paikkamerkkejä (esimerkiksi latauspyörä tai tyhjä solmu) ja vaihda ne todellisiin tietoihin, kun ne on ladattu.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Valinta {#selection}

Valinta kontrolloi, mitkä solmut käyttäjä valitsee. `Tree`-komponentti tukee joustavia tiloja ja API:ita solmujen valitsemiseksi, kumoamiseksi ja kysymiseksi.

### Valintatilat {#selection-modes}

Voit valita, sallitaanko puun valitseminen vain yksi solmu kerrallaan tai useita solmuja samanaikaisesti. Siirtyminen monivalinnasta yksittäiseen valintaan kumoaa automaattisesti kaikki paitsi ensimmäisen valitun solmun.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Monivalinta vuorovaikutus
Kun puu on asetettu monivalintatilaan, käyttäjät voivat valita useamman kuin yhden solmun kerralla. Tämä toimii riippuen laitteesta:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:ssä) ja napsauttavat solmuja lisätäkseen tai poistaakseen ne nykyisestä valinnasta. Tämä mahdollistaa useiden yksittäisten solmujen valitsemisen ilman muiden kumoamista.
* **Mobiililaitteet ja kosketuslaitteet:** Koska muokkausnäppäimiä ei ole käytettävissä, käyttäjät yksinkertaisesti napauttavat solmuja valitakseen tai kumotakseen ne. Jokainen napautus vaihtaa kyseisen solmun valintatilaa, mahdollistaen helpon monivalinnan yksinkertaisilla napautuksilla.
:::

### Solmujen valitseminen ja kumoaminen {#selecting-and-deselecting}

Solmuja voidaan valita tai kumota viittauksen, avaimen, yksittäin tai erissä. Voit myös valita tai kumota kaikki lapsisolmut yhdellä kutsulla.

Esimerkki:

```java
// valitse solmu viittauksen tai avaimen avulla
tree.select(node);
tree.selectKey(key);

// kumoa solmu viittauksen tai avaimen avulla
tree.deselect(node);
tree.deselectAll();

// valitseminen tai kumoaminen lasten solmuille
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Valintatilanteen haku {#selection-state-retrieval}

Voit saada nykyisen valinnan hyödyntämällä alla olevaa koodia:

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

## Tyylit {#styling}

<TableBuilder name="Tree" />
