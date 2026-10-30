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

`Tree`-komponentti järjestää tiedot solmujen hierarkiana. Jokainen solmu pitää sisällään ainutlaatuisen avaimen ja etiketin. Solmut yhdistyvät muodostaen vanhempi-lapsi-suhteita. Voit laajentaa tai kutistaa solmuja näyttämään tai piilottamaan niiden lapsia. Ikonit selkeyttävät, minkä tyyppisestä solmusta on kyse ja onko se valittu. Valinta sallii yhden tai useamman solmun valitsemisen samanaikaisesti.

<!-- INTRO_END -->

## Solmumalli ja puurakenne {#node-model-and-tree-structure}

### `TreeNode`-rooli <DocChip chip="name" label="dwc-tree-node" /> {#the-role-of-treenode}

Jokainen tietopala puussa on kääritty `TreeNode`-objektiin. Tämä objekti pitää sisällään avaimen, tekstietiketin sekä linkit sen vanhempi- ja lapsisolmuihin. Juurisolmu on erityinen: se on läsnä jokaisessa puussa, mutta ei ole näkyvissä. Se toimii kaikkien ylimmän tason solmujen konttina, mikä helpottaa puurakenteen hallintaa sisäisesti.

Koska solmut pitävät viittauksia vanhempiinsa ja lapsiinsa, puussa kulkeminen on suoraviivaista. Halusitpa sitten siirtyä ylös, alas tai löytää tietyn solmun avaimen perusteella, yhteydet ovat aina käytettävissä.

### Solmujen luominen ja hallinta {#node-creation-and-management}

Solmut luodaan yksinkertaisilla tehdasmenetelmillä joko antamalla avain ja teksti tai pelkkä teksti (joka toimii myös avaimena). Tämä takaa, että jokainen solmu on voimassa ja sen voi tunnistaa ainutlaatuisesti.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhemmalle solmulle. Nämä menetelmät hoitavat vanhemman viittauksen asettamisen ja ilmoittavat puulle sen käyttöliittymän päivittämisestä.

Esimerkki:

```java
TreeNode parent = Tree.node("Vanhempi");
TreeNode child = Tree.node("Lapsi");
parent.add(child);
tree.add(parent);
```

:::info Vain yksi vanhempi
Yrittäminen liittää sama solmu useampaan vanhempaan johtaa poikkeukseen. Tämä suojaus varmistaa, että puu säilyttää oikean hierarkian estämällä solmuja saamasta useita vanhempia, mikä rikkoisi rakenteen eheyden ja aiheuttaisi odottamatonta käyttäytymistä.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Solmujen muokkaaminen {#modifying-nodes}

Voit päivittää solmun etikettiä kutsumalla `setText(String text)`. Tämä menetelmä muuttaa solmulle puussa näytettävän tekstin.

Poistaaksesi tietyn lapsisolmun, käytä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmasta ja poistaa sen puurakenteesta. Se myös tyhjentää vanhemman viittauksen.

Jos haluat tyhjentää kaikki lapset solmulta, kutsu `removeAll()`. Tämä poistaa jokaisen lapsisolmun, tyhjentää niiden vanhempien viittaukset ja tyhjentää lasten luettelon.

Jokainen solmu tukee lisätietojen tallentamista palvelinpuolelle käyttäen `setUserData(Object key, Object data)`. Tämä antaa mahdollisuuden liittää solmuun mielivaltaista meta-dataa tai viittauksia ilman, että tämä data altistuu asiakkaalle tai käyttöliittymälle.

:::tip Käytä demoja solmun tekstin muokkaamiseen
Deman aikana tuplaklikkaa solmua avataksesi sen tekstieditorin. Syötä uusi teksti ja tallenna se päivityksesi solmun etiketti puussa.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/frontend/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Ikonit {#icons}

Ikonit tarjoavat visuaalisia vihjeitä siitä, mitä solmut edustavat ja mikä niiden tila on. Ne parantavat luettavuutta erottamalla solmutyypit ja valintatilanteen yhdellä silmäyksellä. `Tree`-komponentti tukee oletusikonien asettamista globaalisti, muokattujen ikonien määrittämistä per solmu sekä ikonien näkyvyyden vaihtamista.

### Globaalit ikonit {#global-icons}

Puu antaa sinun asettaa oletusikonit kutistuneille ryhmille, laajennetuille ryhmille, lehtisolmuille ja valituille lehdille.

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

Voit ohittaa globaalit oletusasetukset määrittämällä ikoneja yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat erilaisia käsitteitä, kuten "projekti" kansioita tai erityisiä tiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Ikonien näkyvyys {#icon-visibility}

Joskus haluat ehkä piilottaa ikonit ryhmiltä tai lehdiltä, jotta häiriötekijöitä vähennetään. Komponentti antaa sinun vaihtaa näkyvyyttä globaalisti näille kategorioille, mikä helpottaa puun ulkoasun yksinkertaistamista ilman, että rakenne kärsii.

Esimerkki:

```java
tree.setGroupIconsVisible(false);
tree.setLeafIconsVisible(false);
```

<ComponentDemo
path='/webforj/treeicons'
files={['src/main/java/com/webforj/samples/views/tree/TreeIconsView.java']}
height='320px'
/>

## Solmujen laajentaminen ja kutistaminen {#node-expansion-and-collapse}

Solmut voidaan laajentaa tai kutistaa, jotta voidaan hallita, mitkä osat puusta ovat näkyvissä. Tämä mahdollistaa keskittymisen merkityksellisiin kohtiin ja tukee skenaarioita, kuten laiskaa latausta tai dynaamisia tietopäivityksiä.

### Laajentamis- ja kutistamisoperaatiot {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja kutistamista joko niiden avaimen tai suoran viittauksen avulla. Voit myös laajentaa tai kutistaa kaikki lapsisolmut kerralla.

Nämä operaatiot antavat sinulle kontrollin siitä, kuinka paljon puusta on näkyvissä ja tukevat laiskaa tietojen latausta tai keskittymistä kiinnostaviin alueisiin.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// kutista alapuuta
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Juuren kutistaminen
Juuri solmu ankkuroida puun, mutta se pysyy piilossa. Juuren kutistaminen piilottaisi normaalisti kaiken, mikä saisi puun näyttämään tyhjältä. Tämän estämiseksi juuren kutistaminen kutistaa itse asiassa kaikki sen lapset, mutta pitää juuren laajennettuna sisäisesti varmistaen, että puu näyttää sisällönsä oikein.
:::

### Laiska lapsisolmujen lataaminen {#lazy-loading-nodes}

Puu tukee lasten solmujen laiskaa lataamista reagoimalla laajentamistapahtumiin. Kun käyttäjä laajentaa solmua, sovelluksesi voi ladata tai luoda kyseisen solmun lapsia dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain näkyvät osat puusta pyynnöstä.

Käytä `onExpand`-tapahtumaa havaitaksesi, kun solmu laajennetaan. Handlerin sisällä tarkista, ovatko solmun lapset paikkamerkkejä (esimerkiksi pyörivä ikoni tai tyhjät solmut) ja korvaa ne todellisilla tiedoilla lataamisen jälkeen.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Valinta {#selection}

Valinta hallitsee, mitkä solmut käyttäjä valitsee. `Tree`-komponentti tukee joustavia tiloja ja API:ita solmujen valitsemiseen, peruuttamiseen ja kyselyyn.

### Valintatilat {#selection-modes}

Voit valita, sallitaanko puun valita yksi solmu kerrallaan tai useita solmuja samanaikaisesti. Siirtyminen useasta valinnasta yhteen valintaan poistaa automaattisesti kaikki muut kuin ensimmäisen valitun solmun.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Monivaihtoehtoisen valinnan vuorovaikutus
Kun puu on asetettu monivalintatilaan, käyttäjät voivat valita useamman kuin yhden solmun kerrallaan. Tämä tapahtuu riippuen laitteesta:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:ssä) alas ja napsauttavat solmuja lisätäkseen tai poistaakseen ne nykyisestä valinnasta. Tämä mahdollistaa yksittäisten solmujen valitsemisen ilman, että muita valintoja poistetaan.
* **Mobiili- ja kosketuslaitteet:** Koska muokkausnäppäimiä ei ole saatavilla, käyttäjät napauttavat yksinkertaisesti solmuja valitaakseen tai poistaakseen ne. Jokainen napautus vaihtaa kyseisen solmun valintatilaa, mahdollistaen helpon monivalinnan yksinkertaisilla napautuksilla.
:::

### Valitseminen ja peruuttaminen {#selecting-and-deselecting}

Solmut voidaan valita tai peruuttaa viittauksen, avaimen, yksittäin tai erissä. Voit myös valita tai peruuttaa kaikki lapsisolmut yhdellä kutsulla.

Esimerkki:

```java
// valitse solmu viittauksella tai avaimella
tree.select(node);
tree.selectKey(key);

// peruuta solmu viittauksella tai avaimella
tree.deselect(node);
tree.deselectAll();

// valitaan tai perutaan solmujen lapset
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Valintatilanteen hakeminen {#selection-state-retrieval}

Voit saada nykyisen valinnan käyttämällä alla esitettyä koodia:

```java
// hae valitun solmun viittaus
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// hae valitun solmun avain
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## Tyylittely {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
