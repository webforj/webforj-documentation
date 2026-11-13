---
title: Sarakeryhmät
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 4babef21ef20728da6aa7eec5829e4d1
---
<!-- vale off -->
# Sarakeryhmät <DocChip chip='since' label='25.12' />
<!-- vale on -->

Sarakeryhmien avulla voit järjestää toisiinsa liittyvät sarakkeet yhteisten, usealle riville ulottuvien otsikoiden alle. Ryhmän otsikko ulottuu sen alisarakkeiden yli, mikä helpottaa monimutkaisten taulukoiden rakenteen hahmottamista. Ryhmiä voi sisäkkäistää rajattomasti, ja `Table` luo automaattisesti oikean määrän otsikkorivejä.

## Sarakeryhmien luominen {#creating-column-groups}

Luo ryhmä `ColumnGroup.of()`-tehdasmetodilla ja lisää siihen sarakeviittauksia, muita ryhmiä tai molempia ketjuttamalla `add()`-kutsuja. Aseta ryhmät `Table`-komponenttiin käyttämällä `setColumnGroups()`-metodia.

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

Kun ryhmät on asetettu, `Table` luo monirivisen otsikon, jossa kunkin ryhmän otsikko ulottuu sen alisarakkeiden yli. Sisäkkäisyyden syvyys määrittää otsikkorivien määrän. Tasainen ryhmä lisää yhden rivin, kaksitasoinen sisäkkäisyys kaksi riviä ja niin edelleen.

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
Ryhmät voi asettaa tai muuttaa milloin tahansa, ennen `Table`-komponentin renderöintiä tai sen jälkeen. Poista kaikki ryhmittelyt ja palauta yksirivinen otsikko välittämällä `setColumnGroups()`-metodille `null` tai tyhjä lista.
<!-- vale Google.OxfordComma = YES -->

```java
// Poista kaikki sarakeryhmät
table.setColumnGroups(null);
```

## Sarakkeiden järjestys {#column-ordering}

Kun ryhmittely on käytössä, sarakkeiden visuaalinen järjestys määräytyy ryhmäpuun eikä sen perusteella, missä järjestyksessä sarakkeet lisättiin `Table`-komponenttiin. Puu käydään läpi syvyyssuuntaisesti vasemmalta oikealle.

```
Lisätyt sarakkeet:  [A, B, C, D, E]
Ryhmät:             Ryhmä "G1" [C, A], Ryhmä "G2" [E, D]
Visuaalinen järjestys: C, A, E, D, B
```

Ryhmittelemättömiä sarakkeita eli sellaisia, joihin ei viitata missään ryhmässä, ei piiloteta. Ne näkyvät luonnollisilla paikoillaan ryhmiteltyihin sarakkeisiin nähden sen mukaan, missä järjestyksessä ne alun perin lisättiin `Table`-komponenttiin.

Tässä esimerkissä `Number` näkyy ensin, koska se lisättiin ennen `Title`-saraketta. `Label` näkyy sarakkeiden `Genre` ja `Cost` välissä, koska se lisättiin niiden väliin alkuperäisessä sarakejärjestyksessä:

```
Lisätyt sarakkeet:  [Number, Title, Artist, Genre, Label, Cost]
Ryhmät:             Ryhmä "Music" [Title, Artist, Genre], Ryhmä "Pricing" [Cost]
Visuaalinen järjestys: Number, Title, Artist, Genre, Label, Cost
```

Seuraava esimerkki havainnollistaa tätä toimintaa. `Number`- ja `Label`-sarakkeisiin ei viitata missään ryhmässä, mutta ne pysyvät luonnollisilla paikoillaan sen perusteella, missä järjestyksessä ne lisättiin `Table`-komponenttiin.

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

:::tip Ryhmitttelemättömien sarakkeiden sijainnin hallinta
Voit määrittää ryhmittelemättömien sarakkeiden sijainnin erikseen lisäämällä ne ylimmän tason sarakeviittauksina ryhmäpuuhun.
:::

## Sarakkeiden siirtäminen ryhmien sisällä {#column-moving-within-groups}

Kun ryhmittely on käytössä, sarakkeiden siirtämistä vetämällä ja pudottamalla rajoitetaan ryhmien rakenteen säilyttämiseksi:

- **Ryhmän sisällä**: ryhmään kuuluvaa saraketta voi siirtää vain sen välittömässä yläryhmässä. Sarakkeen siirtäminen ryhmän ulkopuolelle hylätään, ja sarake palautuu alkuperäiselle paikalleen.
- **Ryhmittelemättömät sarakkeet**: ryhmittelemätöntä saraketta voi siirtää vain paikkoihin, joissa on muita ryhmittelemättömiä sarakkeita. Sitä ei voi pudottaa ryhmän keskelle.
- **Ryhmien järjestäminen uudelleen**: koko ryhmän voi vetää uuteen järjestykseen sisarustensa joukossa samalla sisäkkäisyystasolla.

```
Ryhmät: Ryhmä "G1" [A, B, C], Ryhmä "G2" [D, E]

Siirrä A paikkaan 2 -> OK (G1-ryhmän sisällä, tulos: [B, C, A])
Siirrä A paikkaan 3 -> Hylätty (paikka 3 on G2-ryhmän sisällä)
Siirrä D paikkaan 4 -> OK (G2-ryhmän sisällä, tulos: [E, D])
Siirrä D paikkaan 1 -> Hylätty (paikka 1 on G1-ryhmän sisällä)
```

## Ryhmien kiinnittäminen {#pinning-groups}

Ryhmän voi kiinnittää vasemmalle tai oikealle käyttämällä `setPinDirection()`-metodia. Kaikki ryhmän sarakkeet perivät ryhmän kiinnityssuunnan, ja ryhmän asetus ohittaa yksittäisten sarakkeiden kiinnitysasetukset.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID-tiedot")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Sekä "number" että "title" kiinnitetään vasemmalle
// niiden omista kiinnitysasetuksista riippumatta
```

Ryhmittelemättömät sarakkeet säilyttävät sarakemäärittelyssään asetetun kiinnityssuunnan.

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

## Ryhmän otsikkorivin korkeus {#group-header-height}

Ryhmän otsikkorivin korkeutta voi hallita erikseen tavallisten sarakeotsikoiden korkeudesta käyttämällä `setGroupHeaderHeight()`-metodia.

```java
table.setGroupHeaderHeight(32); // Ryhmärivien korkeus on 32px
table.setHeaderHeight(48);      // Sarakeotsikkorivin korkeus pysyy 48px
```

Ryhmän otsikkorivin oletuskorkeus vastaa otsikkorivin oletuskorkeutta.

## Ryhmien tyylittely CSS-osien avulla {#styling-groups-with-css-parts}

Ryhmien otsikot ja sarakkeet tarjoavat CSS-osia, joita voi tyylitellä `::part()`-valitsimella. Käytettävissä ovat seuraavat osat:

| Osa | Kuvaus |
| --- | --- |
| `cell-group-{ID}` | Ryhmän otsikkosolu, kohdistetaan ryhmän tunnuksella |
| `cell-group-depth-{N}` | Ryhmän otsikkosolu, kohdistetaan sisäkkäisyyssyvyyden mukaan (`0` = ylin taso, `1` = toinen taso jne.) |
| `cell-column-{ID}` | Tietyn sarakkeen kaikki solut (otsikko ja sisältö) |
| `cell-content-group-{ID}` | Ryhmän otsikon sisällön kääresäiliö |
| `cell-label-group-{ID}` | Ryhmän otsikon nimike |

<!-- vale off -->
<ComponentDemo
path='/webforj/tablestyledcolumngroups'
files={[
  'src/main/java/com/webforj/samples/views/table/TableStyledColumnGroupsView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
  'src/main/frontend/css/table/tablestyledcolumngroups.css',
]}
height='600px'
/>
<!-- vale on -->

### Tyylittely ryhmän tunnuksen mukaan {#styling-by-group-id}

Kohdista tiettyihin ryhmiin ryhmän tunnuksen avulla, kun haluat käyttää niissä yksilöllisiä värejä tai typografiaa.

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

### Tyylittely sisäkkäisyyssyvyyden mukaan {#styling-by-depth}

Syvyyteen perustuvat osat ovat hyödyllisiä, kun haluat käyttää yhdenmukaista tyyliä kaikissa tietyn sisäkkäisyystason ryhmissä kohdistamatta tyyliä kuhunkin ryhmään erikseen.

```css
/* Tyylittele kaikki ylimmän tason ryhmät */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Tyylittele kaikki toisen tason ryhmät */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Piilotetut sarakkeet {#hidden-columns}

Piilotetut sarakkeet jätetään pois visuaalisesta järjestyksestä ja otsikkoasettelusta. Jos ryhmässä on sekä näkyviä että piilotettuja sarakkeita, näkyviin tulevat vain näkyvät sarakkeet, ja ryhmän `colspan` mukautuu niiden määrän mukaan. Jos kaikki ryhmän sarakkeet on piilotettu, ryhmän otsikkoa ei renderöidä lainkaan.

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
