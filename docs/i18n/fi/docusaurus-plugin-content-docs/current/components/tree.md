---
title: Tree
sidebar_position: 150
_i18n_hash: 2302136423928266f164623de9a234b4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-tree" />
<DocChip chip='since' label='25.01' />
<JavadocLink type="foundation" location="com/webforj/component/tree/Tree" top='true'/>

`Tree`-komponentti järjestää tiedot solmujen hierarkiana. Jokaisella solmulla on ainutlaatuinen avain ja etiketti. Solmut yhdistyvät muodostaen vanhempi-lapsi-suhteita. Voit laajentaa tai purkaa solmuja näyttämään tai piilottamaan niiden lapsia. Kuvakkeet selventävät, minkä tyyppisestä solmusta on kyse ja onko se valittu. Valinta tukee yhden tai useamman solmun valitsemista kerralla.

<!-- INTRO_END -->

## Solumalli ja puurakenne {#node-model-and-tree-structure}

### `TreeNode`-komponentin rooli {#the-role-of-treenode}

Jokainen puun tietoaineisto on kääritty `TreeNode`:iin. Tämä objekti pitää sisällään avaimen, tekstietiketin ja linkit vanhempiin ja lapsisolmuihin. Juurisolmu on erityinen: se on olemassa jokaisessa puussa, mutta ei ole näkyvissä. Se toimii containerina kaikille ylimmän tason solmuille, mikä tekee puurakenteen hallinnasta helpompaa sisäisesti.

Koska solmut pitävät viittauksia vanhempiinsa ja lapsiinsa, puussa liikkuminen on suoraviivaista. Halusitpa siirtyä ylös, alas tai löytää tietyn solmun avaimen perusteella, yhteydet ovat aina saavutettavissa.

### Solmujen luominen ja hallinta {#node-creation-and-management}

Solmut luodaan yksinkertaisilla tehdasmenetelmillä, joko antamalla avain ja teksti tai pelkkä teksti (joka toimii myös avaimena). Tämä takaa, että jokainen solmu on voimassa ja ainutlaatuisesti tunnistettavissa.

Solmujen lisääminen puuhun tapahtuu kutsumalla `add()` tai `insert()` vanhempisolmulle. Nämä menetelmät käsittelevät vanhemman viittauksen asettamista ja ilmoittavat puulle, että sen käyttöliittymää on päivitettävä.

Esimerkki:

```java
TreeNode parent = Tree.node("Parent");
TreeNode child = Tree.node("Child");
parent.add(child);
tree.add(parent);
```

:::info Yksittäinen vanhempi vain
Yrittäminen määrätä sama solmu useammalle vanhemmalle johtaa poikkeuksen heittämiseen. Tämä turvatoimi varmistaa, että puu ylläpitää oikeaa hierarkiaa estämällä solmuja saamasta useampaa vanhempaa, mikä rikkoisi rakenteen eheyttä ja aiheuttaisi odottamatonta käyttäytymistä.
:::

<ComponentDemo 
path='/webforj/tree?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeView.java'
height='300px'
/>

### Solmujen muokkaaminen {#modifying-nodes}

Voit päivittää solmun etiketin kutsumalla `setText(String text)`. Tämä menetelmä muuttaa solmun puussa näkyvän tekstin.

Jos haluat poistaa tietyn lapsisolmun, käytä `remove(TreeNode child)`. Tämä irrottaa lapsen sen vanhemmasta ja poistaa sen puun rakenteesta. Se myös tyhjentää vanhemman viittauksen.

Jos haluat poistaa kaikki lapset solmusta, kutsu `removeAll()`. Tämä poistaa kaikki lapsisolmut, tyhjentää niiden vanhempi viittaukset ja tyhjentää lasten luettelon.

Jokainen solmu tukee ylimääräisen tiedon tallentamista palvelinpuolella käyttämällä `setUserData(Object key, Object data)`. Tämä mahdollistaa mielivaltaisen metatiedon tai viitteiden liittämisen solmuun, ilman että tämä tieto altistuu asiakkaalle tai käyttöliittymälle.

:::tip Demosovelluksen käyttäminen solmun tekstin muokkaamiseen
Demosovelluksessa voit kaksoisklikata solmua avataksesi editorin sen tekstille. Syötä uusi teksti ja tallenna se päivittääksesi solmun etiketin puussa.
:::

<ComponentDemo 
path='/webforj/treemodify?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeModifyView.java'
height='320px'
/>

## Kuvakkeet {#icons}

Kuvakkeet antavat visuaalisia vihjeitä siitä, mitä solmut edustavat ja niiden tilasta. Ne parantavat luettavuutta erottamalla solmutyyppiä ja valintatilaa yhdellä silmäyksellä. `Tree`-komponentti tukee oletuskuvakkeiden asettamista globaalisti, kuvakkeiden mukauttamista yksittäisille solmuille ja kuvakkeiden näkyvyyden vaihtamista.

### Globaali kuvakkeet {#global-icons}

Puu mahdollistaa oletuskuvakkeiden asettamisen puretuille ryhmille, laajennettuille ryhmille, lehden solmuille ja valituille lehdille.

Esimerkki:

```java
tree.setCollapsedIcon(TablerIcon.create("folder"));
tree.setExpandedIcon(TablerIcon.create("folder-opened"));
tree.setLeafIcon(TablerIcon.create("file"));
tree.setLeafSelectedIcon(TablerIcon.create("file-checked"));
```

:::tip Kuvake Lähteet
Kuvake voi olla mikä tahansa voimassa oleva webforJ [kuvake](./icon) määritelmä tai resurssitiedosto, joka on ladattu webforJ [tuettujen resurssiprotokollien](../managing-resources/assets-protocols) kautta.
:::

### Yksittäisen solmun kuvakkeet {#per-node-icons}

Voit ohittaa globaalit oletukset määrittämällä kuvakkeita yksittäisille solmuille. Tämä on hyödyllistä, kun tietyt solmut edustavat erilaisia käsitteitä, kuten “projekti” kansioita tai erityisiä tiedostoja.

Esimerkki:

```java
node.setIcon(TablerIcon.create("project"));
node.setSelectedIcon(TablerIcon.create("project-selected"));
```

### Kuvakkeiden näkyvyys {#icon-visibility}

Joskus saatat haluta piilottaa kuvakkeet ryhmille tai lehdille, jotta häiriötekijöitä vähennetään. Komponentti mahdollistaa näkyvyyden vaihtamisen globaalisti näille kategorioille, jolloin voit yksinkertaistaa puun ulkoasua ilman rakenteen menettämistä.

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

## Solmujen laajentaminen ja purkaminen {#node-expansion-and-collapse}

Solmuja voidaan laajentaa tai purkaa valvoaksesi, mitkä osat puusta ovat näkyvissä. Tämä mahdollistaa keskittymisen olennaisiin osiin ja tukee skenaarioita, kuten laiskalatausta tai dynaamisia tietopäivityksiä.

### Laajennus- ja purkutoiminnot {#expand-and-collapse-operations}

Puu tukee yksittäisten solmujen laajentamista ja purkamista joko niiden avaimen tai suoran viittauksen perusteella. Voit myös laajentaa tai purkaa kaikkia solmun jälkeläisiä kerralla.

Nämä toiminnot auttavat sinua hallitsemaan, kuinka paljon puusta on näkyvissä, ja tukevat laiskalatausta tai keskittymistä kiinnostaviin alueisiin.

Esimerkki:

```java
tree.expand(node);
tree.collapse(key);

// puretaan alipuuhat
tree.expandFrom(node);
tree.collapseFrom(node);
```

:::info Juuren purkaminen
Juuri solmu ankkuroi puun, mutta pysyy piilossa. Juuren purkaminen piilottaisi normaalisti kaiken, jolloin puu näyttäisi tyhjältä. Tämän välttämiseksi juuren purkaminen itse asiassa puree kaikki sen lapset, mutta pitää juuren laajennettuna sisäisesti, varmistaen, että puu näyttää silti sisältönsä oikein.
:::

### Laiska lataus solmuista {#lazy-loading-nodes}

Puu tukee solmun lasten laiskaa latausta reagoimalla laajennustapahtumiin. Kun käyttäjä laajentaa solmua, sovelluksesi voi ladata tai luoda kyseisen solmun lapsia dynaamisesti. Tämä parantaa suorituskykyä lataamalla vain näkyvät osat puusta tarvittaessa.

Käytä `onExpand`-tapahtumaa havaitaksesi, kun solmu laajennetaan. Handlerin sisällä tarkista, ovatko solmun lapset paikkamerkkejä (esimerkiksi latauskierroksia tai tyhjiä solmuja) ja korvaa ne todellisilla tiedoilla, kun ne on ladattu.

<ComponentDemo 
path='/webforj/treelazyload?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/tree/TreeLazyLoadView.java'
height='250px'
/>

## Valinta {#selection}

Valinta hallitsee, mitkä solmut käyttäjä valitsee. `Tree`-komponentti tukee joustavia tiloja ja API:ita solmujen valitsemiseksi, poistamiseksi valinnasta ja kyselyksi.

### Valintatilat {#selection-modes}

Voit valita, salliiko puu valita vain yhden solmun kerrallaan vai useita solmuja samanaikaisesti. Siirtyminen useasta valinnasta yksinkertaiseen valintaan poistaa automaattisesti valinnasta kaikki paitsi ensimmäisen valitun solmun.

Esimerkki:

```java
tree.setSelectionMode(Tree.SelectionMode.SINGLE);
```

:::tip Monivalinta vuorovaikutus
Kun puu on asetettu monivalintatilaan, käyttäjät voivat valita useamman solmun kerralla. Tämä toimii eri tavalla riippuen laitteesta:

* **Työpöytä (hiiri ja näppäimistö):** Käyttäjät pitävät **Ctrl**-näppäintä (tai **Cmd**-näppäintä macOS:llä) alhaalla ja napsauttavat solmuja lisätäksesi tai poistaaksesi ne nykyisestä valinnasta. Tämä mahdollistaa useiden yksittäisten solmujen valitsemisen ilman muiden poistamista valinnasta.
* **Mobiili- ja kosketuslaitteet:** Koska muokkaavan avaimet eivät ole käytettävissä, käyttäjät yksinkertaisesti napauttavat solmuja valitakseen tai poistaakseen ne valinnasta. Jokainen napautus vaihtaa sen solmun valintatilaa, mahdollistaen helpon monivalinnan yksinkertaisilla napautuksilla.
:::

### Valitseminen ja poistaminen valinnasta {#selecting-and-deselecting}

Solmuja voidaan valita tai poistaa valinnasta viittauksen, avaimen, yksittäin tai ryhmässä. Voit myös valita tai poistaa valinnasta kaikki lasten solmut yhdellä kutsulla.

Esimerkki:

```java
// valitse solmu viittauksen tai avaimen perusteella
tree.select(node);
tree.selectKey(key);

// poista solmu valinnasta viittauksen tai avaimen perusteella
tree.deselect(node);
tree.deselectAll();

// valitsemalla tai poistamalla valinnasta solmujen lapsia
tree.selectChildren(parentNode);
tree.deselectChildren(parentNode);
```

### Valintatilan hakeminen {#selection-state-retrieval}

Voit saada nykyisen valinnan hyödyntämällä alla esitettyä koodia:

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

## Tyylittely {#styling}

<TableBuilder name={['Tree', 'TreeNode']} />
