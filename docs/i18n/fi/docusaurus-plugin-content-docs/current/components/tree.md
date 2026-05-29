---
title: Tree
sidebar_position: 150
_i18n_hash: dacd1e2a128f112d2b7e4a4fd7836feb
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree`-komponentti järjestää tiedot solmujen hierarkiana. Jokaisella solmulla on ainutlaatuinen avain ja etiketti. Solmut yhdistyvät muodostaen vanhempi-lapsi-suhteita. Voit laajentaa tai kutistaa solmuja näyttääkseen tai piilottaakseen niiden lapsia. Kuvakkeet selkeyttävät, minkä tyyppisestä solmusta on kyse ja onko se valittu. Valinta tukee yhden tai useamman solmun valitsemista kerralla.

<!-- INTRO_END -->

## Solmumalli ja puurakenne {#node-model-and-tree-structure}

### `TreeNode`-komponentin rooli {#the-role-of-treenode}

Jokainen puun tiedonpalanen on kiedottu `TreeNode`-objektiin. Tämä objekti sisältää avaimen, tekstietiketin sekä linkit sen vanhempaan ja lapsisolmuihin. Juurisolmu on erityinen: se esiintyy jokaisessa puussa, mutta ei ole näkyvissä. Se toimii säilönä kaikille ylimmille solmuille, mikä helpottaa puurakenteen hallintaa sisäisesti.

Koska solmut säilyttävät viittauksia vanhempiinsa ja lapsiinsa, puussa liikkuminen on suoraviivaista. Riippumatta siitä, haluatko siirtyä ylös, alas vai löytää tietyn solmun avaimen mukaan, yhteydet ovat aina käytettävissä.

### Solmujen luominen ja hallinta {#node-creation-and-management}

Solmuja luodaan yksinkertaisilla tehdasmenetelmillä, joko antamalla avain ja teksti tai pelkästään teksti (joka toimii myös avaimena). Tämä takaa, että jokainen solmu on voimassa oleva ja ainutlaatuisesti tunnistettava.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhempasolmulle. Nämä menetelmät käsittelevät vanhemman viittauksen määrittämistä ja ilmoittavat puulle, että sen UI:tä on päivitettävä.

Esimerkki:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Vain yksi vanhempi
Yrittäminen määrittää sama solmu useammalle vanhemmalle johtaa poikkeuksen heittämiseen. Tämä turvatoimi varmistaa, että puu pitää yllä oikeaa hierarkiaa estämällä solmuja saamasta useita vanhempia, mikä rikkoisi rakenteen eheyttä ja aiheuttaisi odottamattomia käyttäytymisiä.
:::

<ComponentDemo
path='/webforj/tree'
files={['src/main/java/com/webforj/samples/views/tree/TreeView.java']}
height='300px'
/>

### Solmujen muokkaaminen {#modifying-nodes}

Voit päivittää solmun etiketin kutsumalla `setText(String text)`. Tämä menetelmä muuttaa puussa näkyvän solmun tekstiä.

Poistaaksesi tietyn lapsisolmun, käytä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmasta ja poistaa sen puurakenteesta. Se myös tyhjentää vanhemman viittauksen.

Jos haluat tyhjentää kaikki lapset solmusta, kutsu `removeAll()`. Tämä poistaa jokaisen lapsisolmun, tyhjentää niiden vanhemman viittaukset ja tyhjentää lasten listan.

Jokainen solmu tukee lisätiedon tallentamista palvelinpuolella käyttäen `setUserData(Object key, Object data)`. Tämä antaa sinun liittää satunnaista metadataa tai viittauksia solmuun ilman, että tätä tietoa paljastetaan asiakkaalle tai käyttöliittymälle.

:::tip Käytä demoa solmun tekstin muokkaamiseen
Demossa kaksoisklikkaa solmua avataksesi muokkausikkunan sen tekstille. Syötä uusi teksti ja tallenna se päivittääksesi solmun etiketin puussa.
:::

<ComponentDemo
path='/webforj/treemodify'
files={[
  'src/main/java/com/webforj/samples/views/tree/TreeModifyView.java',
  'src/main/resources/static/css/tree/tree-modify-view.css',
]}
height='320px'
/>

## Kuvakkeet {#icons}

Kuvakkeet tarjoavat visuaalisia vihjeitä siitä, mitä solmut edustavat ja mikä niiden tila on. Ne parantavat luettavuutta erottamalla solmutyypit ja valintatilan yhdellä silmäyksellä. `Tree`-komponentti tukee oletuskuvakkeiden asettamista globaalisti, kuvakkeiden mukauttamista solmukohtaisesti ja kuvakkeiden näkyvyyden kytkemistä.

### Globaalit kuvakkeet {#global-icons}

Puu antaa sinun asettaa oletuskuvakkeet kutistuvalle ryhmälle, laajentuville ryhmille, lehtisolmuille ja valituille lehdille.

Esimerkki:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Kuvakesyöt
Kuvake voi olla mikä tahansa voimassa oleva webforJ [ikoni](./icon) -määrittely tai resurssitiedosto, joka on ladattu webforJ [tuetun resurssin protokollan kautta](../managing-resources/assets-protocols).
:::

### Solmukohtaiset kuvakkeet {#per-node-icons}

Voit korvata globaalit oletukset määrittämällä kuvakkeet yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat eri käsitteitä, kuten "projekti" kansioita tai erityisiä tiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Kuvakkeiden näkyvyys {#icon-visibility}

Joskus saatat haluta piilottaa kuvakkeet ryhmille tai lehdille vähentääksesi häiriötekijöitä. Komponentti antaa sinun kytkeä näkyvyyden globaaleissa kategorioissa, mikä helpottaa puun ulkoasun yksinkertaistamista ilman, että rakennetta menettää.

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

## Solmujen laajentaminen ja supistaminen {#node-expansion-and-collapse}

Solmuja voidaan laajentaa tai supistaa, jotta voidaan hallita, mitkä osat puusta ovat näkyvissä. Tämä mahdollistaa keskittymisen relevantiin osaan ja tukee tällaisia skenaarioita kuin laiska lataaminen tai dynaamiset tietopäivitykset.

### Laajentamis- ja supistamistoiminnot {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja supistamista joko niiden avaimen tai suoran viittauksen mukaan. Voit myös laajentaa tai supistaa kaikki solmun jälkeläiset yhdellä kertaa.

Nämä toiminnot antavat sinulle hallita, kuinka paljon puusta on näkyvissä ja tukevat tietojen laiskaa lataamista tai keskittymistä kiinnostaviin alueisiin.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// supista alapuut
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Juuren supistaminen
Juurisolmu ankkuroi puun, mutta pysyy piilotettuna. Juuren supistaminen piilottaisi normaalisti kaiken, jolloin puu näyttäisi tyhjältä. Tämän välttämiseksi juuren supistaminen supistaa kaikki sen lapset, mutta pitää juuren laajennettuna sisäisesti, varmistaen, että puu näyttää edelleen sisällön oikein.
:::

### Laiska lataaminen solmuista {#lazy-loading-nodes}

Puu tukee solmun lasten laiskaa lataamista reagoimalla laajentamistapahtumiin. Kun käyttäjä laajentaa solmua, sovelluksesi voi ladata tai luoda kyseisen solmun lapsia dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain puun näkyvät osat tarpeen mukaan.

Käytä `onExpand`-tapahtumaa havaitaksesi, kun solmu laajennetaan. Käsittelijässä tarkista, ovatko solmun lapset paikkamerkkejä (esimerkiksi latauspyörä tai tyhjät solmut) ja korvata ne varsinaisilla tiedoilla, kun ne on ladattu.

<ComponentDemo
path='/webforj/treelazyload'
files={['src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java']}
height='250px'
/>

## Valinta {#selection}

Valinta ohjaa, mitkä solmut käyttäjä valitsee. `Tree`-komponentti tukee joustavia tiloja ja API:ita solmujen valitsemiseen, poissulkemiseen ja kyselyyn.

### Valintatilat {#selection-modes}

Voit valita, salliiko puu yhden solmun valinnan kerrallaan vai useita solmuja samanaikaisesti. Siirtyminen useasta valinnasta yhteen valintaan poistaa automaattisesti kaikki valinnat paitsi ensimmäisen valitun solmun.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Monivalintatoiminta
Kun puu on asetettu monivalintatilaan, käyttäjät voivat valita useampia solmuja kerralla. Tämä toimii laitteesta riippuen:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:ssa) ja napsauttavat solmuja lisätäkseen tai poistaakseen ne nykyisestä valinnasta. Tämä mahdollistaa useiden yksittäisten solmujen valitsemisen ilman, että muita poistetaan valinnasta.
* **Mobiili- ja kosketuslaitteet:** Koska muokkausnäppäimiä ei ole saatavilla, käyttäjät vain napauttavat solmuja valitakseen tai poistaakseen ne valinnasta. Jokainen kosketus vaihtaa kyseisen solmun valintatilaa, mahdollistaen helpon monivalinnan yksinkertaisilla napautuksilla.
:::

### Valitseminen ja poistaminen valinnasta {#selecting-and-deselecting}

Solmuja voidaan valita tai poistaa valinnasta viittauksen, avaimen, yksilöllisesti tai erissä. Voit myös valita tai poistaa kaikilta solmun lapsilta kerralla.

Esimerkki:

```java
// valitse solmu viittauksen tai avaimen perusteella
tree.select(node);
tree.selectKey(key);

// poista valinta solmulta viittauksen tai avaimen perusteella
tree.deselect(node);
tree.deselectAll();

// valitse tai poista valinta solmujen lapsilta
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Valinnan tilan hakeminen {#selection-state-retrieval}

Voit saada nykyisen valinnan hyödyntämällä alla olevaa koodia:

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

## Tyylitys {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
