---
title: Column Groups
sidebar_position: 7
_i18n_hash: f4ab153f6d1e8c4d029e16c3abc41762
---
<DocChip chip='since' label='25.12' />

Sarake-ryhmät antavat sinun järjestää samankaltaiset sarakkeet jaettujen, monirivisten otsikoiden alle. Ryhmän otsikko ulottuu sen lapsisarakkeiden yli, mikä helpottaa käyttäjiä skannaamaan ja ymmärtämään monimutkaisten taulukoiden rakennetta. Ryhmiä voidaan sisällyttää toisiinsa minkä tahansa syvyyden verran, ja `Table` renderoi automaattisesti oikean määrän otsikkorivejä.

## Sarake-ryhmien luominen {#creating-column-groups}

Luo ryhmä `ColumnGroup.of()` tehdasmenetelmällä, ja yhdistä `add()` -kutsuja täyttääksesi sen sarakeresvointien, muiden ryhmien tai molempien sekoituksella. Käytä ryhmiä `Table`ssa `setColumnGroups()` -kutsun avulla.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

Kun ryhmät on asetettu, `Table` renderoi monirivisen otsikon, jossa jokaisen ryhmän otsikko ulottuu sen lapsisarakkeiden yli. Sisäkkäisyyden syvyys määrää, kuinka monta otsikkoriviä näkyy. Tasainen ryhmä lisää yhden ylimääräisen rivin, kun taas kahden tason sisäkkäisyys lisää kaksi, ja niin edelleen.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablenestedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableNestedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

<!-- vale Google.OxfordComma = NO -->
Ryhmät voidaan asettaa tai muuttaa milloin tahansa, ennen tai jälkeen `Table`n renderöinnin. Siirrä `null` tai tyhjää listaa `setColumnGroups()` -menetelmään poistaaksesi kaikki ryhmät ja palataksesi yksiriviseen otsikkoon.
<!-- vale Google.OxfordComma = YES -->

```java
// Poista kaikki sarake-ryhmät
table.setColumnGroups(null);
```

## Sarakejärjestys {#column-ordering}

Kun ryhmät ovat aktiivisia, visuaalinen sarakejärjestys määräytyy ryhmäpuun perusteella, eikä sen mukaan, missä järjestyksessä sarakkeet lisättiin `Table`-sovellukseen. Puu kävellään syvyyssuunnassa, vasemmalta oikealle.

```
Lisätyt sarakkeet:  [A, B, C, D, E]
Ryhmät:          Ryhmä "G1" [C, A], Ryhmä "G2" [E, D]
Visuaalinen järjestys:   C, A, E, D, B
```

Ryhmittämättömät sarakkeet, joita ei ole viitattu mihinkään ryhmään, eivät ole piilossa. Ne näkyvät luonnollisessa asemassaan suhteessa ryhmitettyihin sarakkeisiin, sen mukaan missä järjestyksessä ne alunperin lisättiin `Table`-sovellukseen.

Tässä esimerkissä `Number` esiintyy ensin, koska se lisättiin ennen `Title`-saraketta. `Label` esiintyy `Genre`- ja `Cost`-sarakeiden välissä, koska se lisättiin niiden väliin alkuperäisessä sarakejärjestyksessä:

```
Lisätyt sarakkeet:  [Number, Title, Artist, Genre, Label, Cost]
Ryhmät:          Ryhmä "Music" [Title, Artist, Genre], Ryhmä "Pricing" [Cost]
Visuaalinen järjestys:   Number, Title, Artist, Genre, Label, Cost
```

Seuraava demo havainnollistaa tätä käyttäytymistä. `Number` ja `Label` eivät ole viitattu mihinkään ryhmään, mutta ne säilyttävät luonnolliset asemansa sen perusteella, missä järjestyksessä ne lisättiin `Table`-sovellukseen.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablecolumngroupordering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnGroupOrderingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::tip Ryhmittämättömien sarakkeiden sijoittamisen hallinta
Ryhmittämättömien sarakkeiden sijoittamisen hallitsemiseksi sisällytä ne huipputason sarakeviittauksina ryhmäpuuhun.
:::

## Sarakkeiden siirtäminen ryhmien sisällä {#column-moving-within-groups}

Kun ryhmät ovat aktiivisia, vedä ja pudota -sarakeen siirtoa rajoitetaan ryhmän eheyden ylläpitämiseksi:

- **Ryhmässä**: sarake ryhmässä voidaan siirtää vain sen välittömässä vanhemmassa ryhmässä. Sen vetäminen ryhmän ulkopuolelle hylätään, ja sarake palaa alkuperäiseen asemaansa.
- **Ryhmittämättömät sarakkeet**: ryhmittämätön sarake voi siirtyä vain paikkoihin, jotka ovat muiden ryhmittämättömien sarakkeiden käytössä. Sitä ei voi pudottaa ryhmän sisään.
- **Ryhmien uudelleenjärjestäminen**: koko ryhmää voidaan vetää uudelleenjärjestämään sitä samassa sisäkkäisyystasossa sisarustensa kesken.

```
Ryhmät:  Ryhmä "G1" [A, B, C], Ryhmä "G2" [D, E]

Siirrä A asemaan 2 -> OK (G1:ssä, tulos: [B, C, A])
Siirrä A asemaan 3 -> Hylätty (asema 3 on G2:ssä)
Siirrä D asemaan 4 -> OK (G2:ssä, tulos: [E, D])
Siirrä D asemaan 1 -> Hylätty (asema 1 on G1:ssä)
```

## Ryhmien kiinnittäminen {#pinning-groups}

Ryhmä voidaan kiinnittää vasemmalle tai oikealle käyttämällä `setPinDirection()`. Kaikki sarakkeet ryhmässä perivät ryhmän kiinnitys suunnan, ja yksittäisten sarakkeiden kiinnityssäännöt ylikirjoitetaan ryhmän toimesta.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sekä "number" että "title" on kiinnitetty vasemmalle,
// riippumatta omista kiinnityksistään
```

Ryhmittämättömät sarakkeet säilyttävät oman kiinnityssuuntansa sarake määrittelystään.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablepinnedcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TablePinnedColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

## Ryhmän otsikon korkeus {#group-header-height}

Ryhmän otsikkorivin korkeutta voidaan hallita riippumatta tavallisista sarakeotsikoista käyttäen `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Ryhmän rivit ovat 32px korkeat
table.setHeaderHeight(48);      // Sarakeotsikkorivi pysyy 48px korkeana
```

Oletusryhmän otsikkokorkeus vastaa oletusotsikkokorkeutta.

## Ryhmien tyylittely CSS-osien avulla {#styling-groups-with-css-parts}

Ryhmäotsikot ja sarakkeet altistavat CSS-osia tyylittelyä varten `::part()`-menetelmällä. Seuraavat osat ovat saatavilla:

| Osa | Kuvaus |
| --- | --- |
| `cell-group-{ID}` | Ryhmäotsikon solu, kohdistettu ryhmän ID:n perusteella |
| `cell-group-depth-{N}` | Ryhmäotsikon solu, kohdistettu syvyyden perusteella (`0` = ykköstaso, `1` = kakkostaso, jne.) |
| `cell-column-{ID}` | Kaikki solut (otsikko ja runko) tietyllä sarake-ID:llä |
| `cell-content-group-{ID}` | Sisällön kehys ryhmäotsikossa |
| `cell-label-group-{ID}` | Etiketti ryhmäotsikossa |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/resources/static/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Tyylittely ryhmän ID:n mukaan {#styling-by-group-id}

Käytä ryhmän ID:tä kohdentamaan erityisiä ryhmiä ainutlaatuisilla väreillä tai typografialla.

```css
dwc-table::part(cell-group-catalog) {
  background: var(--dwc-color-primary-30);
  color: var(--dwc-color-primary-text-30);
  font-weight: 600;
}

dwc-table::part(cell-group-bio) {
  background: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}

dwc-table::part(cell-column-title) {
  font-weight: 600;
}
```

### Tyylittely syvyyden mukaan {#styling-by-depth}

Syvyyspohjaiset osat ovat hyödyllisiä, kun haluat soveltaa johdonmukaista tyyliä kaikkiin ryhmiin tietyllä sisäkkäisyystasolla ilman, että kohdistat jokaista ryhmää erikseen.

```css
/* Tyylittele kaikki ykköstason ryhmät */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Tyylittele kaikki kakkostason ryhmät */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Piilotetut sarakkeet {#hidden-columns}

Piilotetut sarakkeet jätetään pois visuaalisesta järjestyksestä ja otsikkosuunnitelmasta. Jos ryhmässä on sekoitus näkyviä ja piilotettuja sarakkeita, vain näkyvät näyttäytyvät ja ryhmän `colspan` säätyy vastaavasti. Jos jokainen sarake ryhmässä on piilotettu, ryhmäotsikkoa ei renderoida lainkaan.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablehiddencolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableHiddenColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->
