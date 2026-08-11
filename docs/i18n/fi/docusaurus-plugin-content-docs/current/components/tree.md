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

`Tree`-komponentti järjestää tiedot solmuhierarkiana. Jokainen solmu sisältää ainutlaatuisen avaimen ja etiketin. Solmut yhdistyvät muodostaen vanhempi-lapsi-suhteita. Voit laajentaa tai romahtaa solmuja näyttämään tai piilottamaan niiden lapset. Ikonit selventävät, minkä tyyppisestä solmusta on kyse ja onko se valittu. Valinta tukee yhden tai useamman solmun valitsemista kerrallaan.

<!-- INTRO_END -->

## Solmumalli ja puurakenne {#node-model-and-tree-structure}

### `TreeNode`-objektin rooli {#the-role-of-treenode}

Jokainen puun tietoelementti on kääritty `TreeNode`-objektiin. Tämä objekti sisältää avaimen, tekstietiketin sekä linkit sen vanhempaansa ja lapsisolmuihin. Juurisolmu on erityinen: se esiintyy jokaisessa puussa, mutta ei ole näkyvissä. Se toimii säilönä kaikille ylimmällä tasolla oleville solmuille, mikä helpottaa puun rakenteen hallintaa sisäisesti.

Koska solmut säilyttävät viittauksia vanhempiinsa ja lapsiinsa, puussa navigointi on suoraviivaista. Riippumatta siitä, haluatko siirtyä ylös, alas tai löytää tietyn solmun avaimen mukaan, yhteydet ovat aina käytettävissä.

### Solmujen luominen ja hallinta {#node-creation-and-management}

Solmut luodaan yksinkertaisilla tehdasmetodeilla antamalla avain ja teksti tai pelkkä teksti (joka toimii myös avaimena). Tämä varmistaa, että jokainen solmu on voimassa ja ainutlaatuisesti tunnistettavissa.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhempasolmulle. Nämä metodit hoitavat vanhemman viittauksen määrittämisen ja ilmoittavat puulle sen käyttöliittymän päivittämisestä.

Esimerkki:

```java
TreeNode parent = Tree.node("Vanhempi");
TreeNode child = Tree.node("Lapsi");
parent.add(child);
tree.add(parent);
```

:::info Vain yksi vanhempi
Yritys määrittää sama solmu useammalle vanhemmalle johtaa poikkeukseen. Tämä suojaus varmistaa, että puu ylläpitää oikeaa hierarkiaa estämällä solmuja saamasta useita vanhempia, mikä rikkoisi rakenteen eheyttä ja aiheuttaisi odottamatonta käyttäytymistä.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Solmujen muokkaaminen {#modifying-nodes}

Voit päivittää solmun etikettiä kutsumalla `setText(String text)`. Tämä metodi muuttaa puussa näkyvää tekstiä solmulle.

Poistaaksesi tietyn lapsisolmun, käytä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmastaan ja poistaa sen puurakenteesta. Se myös tyhjentää vanhemman viittauksen.

Jos haluat tyhjentää kaikki lapset solmulta, kutsu `removeAll()`. Tämä poistaa jokaisen lapsisolmun, tyhjentää niiden vanhempien viittaukset ja tyhjentää lasten luettelon.

Jokainen solmu tukee lisätietojen tallentamista palvelinpuolelle käyttäen `setUserData(Object key, Object data)`. Tämä mahdollistaa mielivaltaisen metadatan tai viitteiden yhdistämisen solmuun ilman, että tämä tieto on näkyvissä asiakkaalle tai käyttöliittymälle.

:::tip Käytä Demoa Solmun Tekstin Muokkaamiseen
Demon avulla voit kaksoisnapsauttamalla solmua avata sen tekstieditorin. Syötä uusi teksti ja tallenna se päivittääksesi solmun etiketin puussa.
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

Ikonit tarjoavat visuaalisia vihjeitä siitä, mitä solmut edustavat ja mikä niiden tila on. Ne parantavat luettavuutta erottamalla solmutyypit ja valintatilat yhdellä silmäyksellä. `Tree`-komponentti tukee oletusikonien asettamista globaalisti, ikonien mukauttamista solmukohtaisesti ja ikonien näkyvyyden vaihtamista.

### Globaali ikoni {#global-icons}

Puu antaa sinun asettaa oletusikonit romahtaneille ryhmille, laajennettuille ryhmille, lehtisolmuille ja valituilla lehdille.

Esimerkki:

```java
tree.setCollapsedIcon(TablerIcon.create("kansio"));
tree.setExpandedIcon(TablerIcon.create("kansio-avattu"));
tree.setLeafIcon(TablerIcon.create("tiedosto"));
tree.setLeafSelectedIcon(TablerIcon.create("tiedosto-valittu"));
```

:::tip Ikonilähteet
Ikoni voi olla mikä tahansa voimassa oleva webforJ [ikoni](./icon) määrittely tai resurssitiedosto, joka on ladattu webforJ [tuettujen resurssiprotokollien](../managing-resources/assets-protocols) kautta.
:::

### Solmukohtaiset ikonit {#per-node-icons}

Voit ohittaa globaalit oletusarvot määrittämällä ikoneita yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat eri käsitteitä, kuten "projekti" kansioita tai erityisiä tiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("projekti"));
node.setSelectedIcon(TablerIcon.create("projekti-valittu"));
```

### Ikonin näkyvyys {#icon-visibility}

Joskus saatat haluta piilottaa ikonit ryhmiltä tai lehdiltä häiriötekijöiden vähentämiseksi. Komponentti antaa sinun vaihtaa näkyvyyttä globaalisti näille kategorioille, mikä mahdollistaa puun ilmeen yksinkertaistamisen ilman rakenteen menettämistä.

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

## Solmujen laajentaminen ja romahtaminen {#node-expansion-and-collapse}

Solmuja voidaan laajentaa tai romahtaa hallitaakseen, mitkä osat puusta ovat näkyvissä. Tämä mahdollistaa keskittymisen relevantteihin osiin ja tukee skenaarioita, kuten laiskaa lataamista tai dynaamisia tietopäivityksiä.

### Laajennus- ja romahtamistoiminnot {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja romahtamista joko niiden avaimen tai suoran viitteen avulla. Voit myös laajentaa tai romahtaa kaikkia solmun jälkeläisiä kerralla.

Nämä toiminnot antavat sinun hallita, kuinka suuri osa puusta on näkyvissä, ja tukevat dynaamista tietojen lataamista tai keskittymistä kiinnostaviin alueisiin.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// romahtaa alapuoli
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Juuren romahtaminen
Juurisolmu ankkuroi puun, mutta pysyy piilossa. Juuren romahtaminen piilottaisi normaalisti kaiken, jolloin puu näyttäisi tyhjältä. Tämän estämiseksi juuren romahtaminen itse asiassa romahtaa kaikki sen lapset, mutta pitää juuren laajennettuna sisäisesti, varmistamalla, että puu näyttää edelleen sisältönsä oikein.
:::

### Laiska lataaminen solmuista {#lazy-loading-nodes}

Puu tukee solmun lasten laiskaa lataamista reagoimalla laajennustapahtumiin. Kun käyttäjä laajentaa solmua, sovelluksesi voi ladata tai generoida sen lasten dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain näkyvät osat puusta tarvittaessa.

Käytä `onExpand`-tapahtumaa havaitaksesi, kun solmu laajennetaan. Handlerissa tarkista, ovatko solmun lapset paikalla (esimerkiksi pyörivä latauskierros tai tyhjät solmut) ja korvata ne varsinaisilla tiedoilla, kun ne on ladattu.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Valinta {#selection}

Valinta hallitsee, mitkä solmut käyttäjä valitsee. `Tree`-komponentti tukee joustavia tiloja ja API:ita solmujen valitsemiin, peruuttamiseen ja kysymiseen.

### Valintatilat {#selection-modes}

Voit valita, salliiko puu valita kerralla yhden solmun tai useita solmuja samanaikaisesti. Siirtyminen monivalintatilasta yksittäiseen valintaan poistaa automaattisesti valinnan kaikilta paitsi ensimmäiseltä valitulta solmulta.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Monivalinnan vuorovaikutus
Kun puu on asetettu usean valinnan tilaan, käyttäjät voivat valita useita solmuja kerralla. Tämä toiminto riippuu laitteesta:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:ssa) ja napsauttavat solmuja lisätäkseen tai poistaakseen ne nykyisestä valinnasta. Tämä mahdollistaa useiden yksittäisten solmujen valintaan ilman muiden poistamista.
* **Mobiili- ja kosketuslaitteet:** Koska modifier-näppäimiä ei ole saatavilla, käyttäjät vain napauttavat solmuja valitakseen tai poistaakseen ne valinnasta. Jokainen napautus vaihtaa valitun solmun valintatilaa, mahdollistaen helpon monivalinnan yksinkertaisilla napautuksilla.
:::

### Valitsemisen ja peruutuksen {#selecting-and-deselecting}

Solmuja voidaan valita tai peruuttaa viitteen, avaimen, yksitellen tai sarjoittain. Voit myös valita tai peruuttaa kaikkia solmun lapsia yhdellä kutsulla.

Esimerkki:

```java
// valitse solmu viitteen tai avaimen avulla
tree.select(node);
tree.selectKey(key);

// peruuta solmu viitteen tai avaimen avulla
tree.deselect(node);
tree.deselectAll();

// valitaan tai peruutetaan solmujen lapsia
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Valinnan tilan hakeminen {#selection-state-retrieval}

Voit saada nykyisen valinnan käyttämällä alla olevaa koodia:

```java
// hanki valitun solmun viite
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// hanki valitun solmun avain
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo
path='/webforj/treeselection'
files={['src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java']}
height='400px'
/>

## Tyylit {#styling}

<TableBuilder name={['Puu', 'TreeNode']} />
