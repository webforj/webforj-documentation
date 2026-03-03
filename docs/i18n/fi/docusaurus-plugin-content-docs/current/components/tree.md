---
title: Tree
sidebar_position: 150
_i18n_hash: 280fb07f73ba1172b33bd0617ded7876
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree`-komponentti järjestää tiedot solmujen hierarkiana. Jokainen solmu pitää sisällään ainutlaatuisen avaimen ja etiketti. Solmut yhdistyvät muodostaen vanhempi-lapsi-suhteita. Voit laajentaa tai kutistaa solmuja lapsensa näyttämiseksi tai piilottamiseksi. Kuvakkeet selventävät, minkä tyyppisestä solmusta on kyse ja onko se valittuna. Valinta tukee yhden solmun tai useiden kerralla valitsemista.

<!-- INTRO_END -->

## Solmumalli ja puurakenne {#node-model-and-tree-structure}

### `TreeNode`-rooli {#the-role-of-treenode}

Jokainen tietoelementti puussa on kääritty `TreeNode`-objektiin. Tämä objekti sisältää avaimen, tekstietiketin sekä linkit sen vanhempiin ja lapsisolmuihin. Juurisolmu on erityinen: se on läsnä jokaisessa puussa, mutta ei ole näkyvissä. Se toimii säilönä kaikille ylimmille solmuille, mikä helpottaa puurakenteen hallintaa sisäisesti.

Koska solmut pitävät viittauksia vanhempiinsa ja lapsiinsa, puussa liikkuminen on vaivatonta. Olitpa sitten liikkumassa ylös, alas tai etsimässä tiettyä solmua avaimen mukaan, yhteydet ovat aina käytettävissä.

### Solmujen luominen ja hallinta {#node-creation-and-management}

Solmuja luodaan yksinkertaisilla tehdasmenetelmillä, joko antamalla avain ja teksti tai pelkkä teksti (joka toimii myös avaimena). Tämä takaa, että jokainen solmu on kelvollinen ja yksilöllisesti tunnistettava.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhempasolmulle. Nämä menetelmät käsittelevät vanhemman viittauksen määrittämistä ja ilmoittavat puulle, että sen käyttöliittymä on päivitettävä.

Esimerkki:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Vain yksi vanhempi
Yrittäminen määrittää sama solmu useammalle vanhemmalle johtaa poikkeuksen heittämiseen. Tämä turvatoimi varmistaa, että puu ylläpitää oikeaa hierarkiaa estämällä solmuja saamasta useita vanhempia, mikä rikkoisi rakenteen eheyttä ja aiheuttaisi odottamatonta käyttäytymistä.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Solmujen muokkaaminen {#modifying-nodes}

Voit päivittää solmun etikettiä kutsumalla `setText(String text)`. Tämä menetelmä muuttuu tekstin, joka näytetään solmulle puussa.

Poistaaksesi tietyn lapsisolmun, käytä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmasta ja poistaa sen puurakenteesta. Se myös tyhjentää vanhemman viittauksen.

Jos haluat tyhjentää kaikki lapset solmusta, kutsu `removeAll()`. Tämä poistaa jokaisen lapsisolmun, tyhjentää niiden vanhemmat viittaukset ja tyhjentää lasten listan.

Jokainen solmu tukee lisätietojen tallentamista palvelinpuolelle käyttämällä `setUserData(Object key, Object data)`. Tämä antaa sinun liittää mielivaltaista metatietoa tai viittauksia solmuun, paljastamatta näitä tietoja asiakkaalle tai käyttöliittymälle.

:::tip Demoa käyttäen solmun tekstin muokkaaminen
Demossa voit kaksoisklikata solmua avatakseen editorin sen tekstille. Syötä uusi teksti ja tallenna se päivittääksesi solmun etiketti puussa.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Kuvakkeet {#icons}

Kuvakkeet tarjoavat visuaalisia vihjeitä solmujen edustuksesta ja niiden tilasta. Ne parantavat luettavuutta erottamalla solmujen tyypit ja valintatilat yhdellä silmäyksellä. `Tree`-komponentti tukee oletuskuvakkeiden määrittämistä globaalisti, yksittäisten solmujen kuvakkeiden mukauttamista ja kuvakkeiden näkyvyyden kytkemistä.

### Globaalit kuvakkeet {#global-icons}

Puu antaa sinun määrittää oletuskuvakkeet kutistuneille ryhmille, laajennetuillle ryhmille, lehtisolmuille ja valituille lehdille.

Esimerkki:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Kuvake lähteet
Kuvake voi olla mikä tahansa kelvollinen webforJ [kuvake](./icon) määritelmä tai resurssitiedosto, joka on ladattu webforJ [tuettujen resurssiprotokollien](../managing-resources/assets-protocols) kautta.
:::

### Solmukohtaiset kuvakkeet {#per-node-icons}

Voit ylikirjoittaa globaaleja oletuksia määrittämällä kuvakkeita yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat eri käsitteitä, kuten "projekti" kansioita tai erityisiä tiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Kuvakkeiden näkyvyys {#icon-visibility}

Joskus haluat ehkä piilottaa kuvakkeet ryhmille tai lehdille vähentääksesi häiriötekijöitä. Komponentti antaa sinun kytkeä näkyvyyden globaalisti näille kategorioille, jolloin voit yksinkertaistaa puun ulkoasua menettämättä rakennetta.

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

Solmuja voidaan laajentaa tai kutistaa, jotta hallitaan, mitkä osat puusta ovat näkyvissä. Tämä mahdollistaa keskittymisen relevantteihin osiin ja tukee tilanteita, kuten laiska lataus tai dynaamiset tietopäivitykset.

### Laajentamis- ja kutistamistoiminnot {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja kutistamista joko niiden avaimen tai suoran viittauksen avulla. Voit myös laajentaa tai kutistaa kaikkia solmun jälkeläisiä kerralla.

Nämä toiminnot antavat sinulle hallintaa siitä, kuinka paljon puusta on näkyvissä, ja tukevat tietojen laiskaa latausta tai keskittymistä kiinnostaviin alueisiin.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// kutista alapuuta
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Juurisolmun kutistaminen
Juurisolmu kiinnittää puun, mutta pysyy piilossa. Juurisolmun kutistaminen piilottaisi normaalisti kaiken, mikä saisi puun näyttämään tyhjältä. Tämän välttämiseksi juurisolmun kutistaminen kutistaa itse asiassa kaikki sen lapset, mutta pitää juurisolmun laajennettuna sisäisesti, varmistaen, että puu näyttää edelleen sisältönsä oikein.
:::

### Laiska lataus solmuissa {#lazy-loading-nodes}

Puu tukee solmun lasten laiskaa latausta reagoimalla laajentamistapahtumiin. Kun käyttäjä laajentaa solmua, sovelluksesi voi ladata tai luoda kyseisen solmun lapsia dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain näkyviä osia puusta tarpeen mukaan.

Käytä `onExpand`-tapahtumaa havaitaksesi, kun solmu laajennetaan. Käsittelijän sisällä tarkista, ovatko solmun lapset paikkamerkkejä (esimerkiksi pyörivä latauskuvake tai tyhjät solmut) ja korvata ne oikeilla tiedoilla lataamisen jälkeen.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Valinta {#selection}

Valinta hallitsee, mitkä solmut käyttäjä valitsee. `Tree`-komponentti tukee joustavaa tilaa ja sovellusrajapintoja solmujen valitsemiseksi, kumoamiseksi ja kysymiseksi.

### Valintatilat {#selection-modes}

Voit valita, sallitaanko puun valita vain yksi solmu kerralla vai useita solmuja samanaikaisesti. Siirtyminen useasta valinnasta yksittäiseen valintaan poistaa automaattisesti kaikki muut kuin ensimmäisen valitun solmun.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Monivalintakäyttäytyminen
Kun puu on asetettu useaan valintatilaan, käyttäjät voivat valita useamman kuin yhden solmun kerralla. Tämä toimii laitteesta riippuen:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:llä) ja napsauttavat solmuja lisätäkseen tai poistakseen ne nykyisestä valinnasta. Tämä mahdollistaa useiden yksittäisten solmujen valitsemisen ilman muiden kumoamista.
* **Mobiili- ja kosketuslaitteet:** Koska muokkausnäppäimiä ei ole saatavilla, käyttäjät yksinkertaisesti napauttavat solmuja valitakseen tai kumotakseen niitä. Jokainen napautus vaihtaa kyseisen solmun valintatilaa, mahdollistaen helpon monivalinnan yksinkertaisilla napautuksilla.
:::

### Solmujen valitseminen ja kumoaminen {#selecting-and-deselecting}

Solmuja voidaan valita tai kumota viittauksen, avaimen, yksittäin tai joukoittain. Voit myös valita tai kumota kaikki lapset yhdellä kutsulla.

Esimerkki:

```java
// valitse solmu viittauksella tai avaimella
tree.select(node);
tree.selectKey(key);

// kumoa solmu viittauksella tai avaimella
tree.deselect(node);
tree.deselectAll();

// valitseminen tai kumoaminen solmun lapsista
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Valintatilanteen hakeminen {#selection-state-retrieval}

Voit saada nykyisen valinnan käyttämällä alla olevaa koodia:

```java
// hae valitun solmun viittaus
TreeNode selected = tree.getSelected();
List<TreeNode> selectedItems = tree.getSelectedItems();

// hae valitun solmun avain
Object selectedKey = tree.getSelectedKey();
List<Object> selectedKeys = tree.getSelectedKeys();
```

<ComponentDemo 
path='/webforj/treeselection?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeSelectionView.java'
height='400px'
/>

## Tyylitys {#styling}

<TableBuilder name="Tree" />
