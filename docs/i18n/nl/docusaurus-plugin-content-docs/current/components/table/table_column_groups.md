---
title: Column Groups
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 7a0ad76ea48fafddc0aa9965309b48b8
---
<DocChip chip='since' label='25.12' />

Kolomgroepen stellen je in staat om gerelateerde kolommen te organiseren onder gedeelde, meerrijige koppen. Een groepslabel strekt zich uit over zijn kindkolommen, waardoor het voor gebruikers gemakkelijker wordt om de structuur van complexe tabellen te scannen en te begrijpen. Groepen kunnen tot elke diepte genest worden, en de `Table` rendert automatisch het juiste aantal koprijen.

## Kolomgroepen maken {#creating-column-groups}

Maak een groep met de `ColumnGroup.of()` fabrieksmethode en keten vervolgens `add()` aanroepen om deze te vullen met kolomreferenties, andere groepen of een mix van beide. Pas de groepen toe op een `Table` met `setColumnGroups()`.

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

Wanneer groepen zijn ingesteld, rendert de `Table` een meerrijige kop waarbij elk groepslabel zich uitstrekt over zijn kindkolommen. De diepte van de nesting bepaalt hoeveel koprijen verschijnen. Een platte groep voegt één extra rij toe, terwijl een twee-niveaus nesting er twee toevoegt, enzovoort.

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
Groepen kunnen op elk moment worden ingesteld of gewijzigd, vóór of nadat de `Table` is gerenderd. Geef `null` of een lege lijst door aan `setColumnGroups()` om alle groepering te verwijderen en terug te keren naar een kop met één rij.
<!-- vale Google.OxfordComma = YES -->

```java
// Verwijder alle kolomgroepen
table.setColumnGroups(null);
```

## Kolomvolgorde {#column-ordering}

Wanneer groepen actief zijn, wordt de visuele kolomvolgorde bepaald door de groepstructuur in plaats van de volgorde waarin kolommen aan de `Table` zijn toegevoegd. De boom wordt van links naar rechts diepte-eerst doorlopen.

```
Toegevoegde kolommen:  [A, B, C, D, E]
Groepen:              Groep "G1" [C, A], Groep "G2" [E, D]
Visuele volgorde:     C, A, E, D, B
```

Ongroeperde kolommen, die niet in een groep worden vermeld, zijn niet verborgen. Ze verschijnen op hun natuurlijke positie ten opzichte van gegroepeerde kolommen, gebaseerd op de volgorde waarin ze oorspronkelijk aan de `Table` zijn toegevoegd.

In dit voorbeeld verschijnt `Nummer` eerst omdat het vóór `Titel` is toegevoegd. `Label` verschijnt tussen `Genre` en `Kosten` omdat het tussen hen is toegevoegd in de oorspronkelijke kolomvolgorde:

```
Toegevoegde kolommen:  [Nummer, Titel, Artiest, Genre, Label, Kosten]
Groepen:              Groep "Muziek" [Titel, Artiest, Genre], Groep "Prijzen" [Kosten]
Visuele volgorde:     Nummer, Titel, Artiest, Genre, Label, Kosten
```

De volgende demo illustreert dit gedrag. `Nummer` en `Label` worden niet in een groep vermeld, maar behouden hun natuurlijke posities op basis van de volgorde waarin ze aan de `Table` zijn toegevoegd.

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

:::tip Beheersen van de plaatsing van ongroeperde kolommen
Om de plaatsing van ongroeperde kolommen expliciet te beheersen, neem ze op als kolomreferenties op het hoogste niveau in de groepstructuur.
:::

## Kolom verplaatsen binnen groepen {#column-moving-within-groups}

Wanneer groepen actief zijn, is het slepen en neerzetten van kolommen beperkt om de integriteit van de groep te behouden:

- **Binnen een groep**: een kolom binnen een groep kan alleen binnen zijn directe bovenliggende groep worden verplaatst. Het naar buiten slepen van de groep wordt geweigerd en de kolom springt terug naar zijn oorspronkelijke positie.
- **Ongroeperde kolommen**: een ongroeperde kolom kan alleen worden verplaatst naar posities die bezet zijn door andere ongroeperde kolommen. Het kan niet in het midden van een groep worden neergezet.
- **Herschikken van groepen**: een hele groep kan worden gesleept om deze opnieuw te ordenen tussen zijn broers en zussen op hetzelfde niveau van nesting.

```
Groepen:  Groep "G1" [A, B, C], Groep "G2" [D, E]

Verplaats A naar positie 2 -> OK (binnen G1, resultaat: [B, C, A])
Verplaats A naar positie 3 -> Weigerd (positie 3 is binnen G2)
Verplaats D naar positie 4 -> OK (binnen G2, resultaat: [E, D])
Verplaats D naar positie 1 -> Weigerd (positie 1 is binnen G1)
```

## Groepen vastzetten {#pinning-groups}

Een groep kan links of rechts worden vastgezet met `setPinDirection()`. Alle kolommen binnen de groep erven de vastzetrichting van de groep en individuele vastzetinstellingen van kolommen worden overschreven door de groep.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Zowel "nummer" als "titel" zijn links vastgezet,
// ongeacht hun eigen vastzetinstellingen
```

Ongroeperde kolommen behouden hun eigen vastzetrichtingen vanuit hun kolomdefinitie.

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

## Hoogte van groepskoppen {#group-header-height}

De hoogte van de groepskoprij kan onafhankelijk worden beheerd van reguliere kolomkoppen met `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Groepsrijen zijn 32px hoog
table.setHeaderHeight(48);      // De rij van de kolomkop blijft op 48px
```

De standaardhoogte van de groepskop komt overeen met de standaardhoogte van de kop.

## Groepen stijlen met CSS-onderdelen {#styling-groups-with-css-parts}

Groepskoppen en kolommen stellen CSS-onderdelen ter beschikking voor styling via `::part()`. De volgende onderdelen zijn beschikbaar:

| Onderdeel | Beschrijving |
| --- | --- |
| `cell-group-{ID}` | Groepskopcel, gericht op groeps-ID |
| `cell-group-depth-{N}` | Groepskopcel, gericht op diepte (`0` = bovenste niveau, `1` = tweede niveau, enz.) |
| `cell-column-{ID}` | Alle cellen (kop en lichaam) voor een gegeven kolom-ID |
| `cell-content-group-{ID}` | Inhoudomslag binnen een groepskop |
| `cell-label-group-{ID}` | Label binnen een groepskop |

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

### Stylen op groeps-ID {#styling-by-group-id}

Gebruik de groeps-ID om specifieke groepen met unieke kleuren of typografie te targeten.

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

### Stylen op diepte {#styling-by-depth}

Diepte-gebaseerde onderdelen zijn nuttig wanneer je consistente styling wilt toepassen op alle groepen op een bepaald niveau van nesting zonder elke groep afzonderlijk te targeten.

```css
/* Style alle bovenste groepen */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Style alle tweede niveau groepen */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Verborgen kolommen {#hidden-columns}

Verborgen kolommen worden uitgesloten van de visuele volgorde en de lay-out van de kop. Als een groep een mix van zichtbare en verborgen kolommen bevat, verschijnen alleen de zichtbare en de groeps `colspan` past zich dienovereenkomstig aan. Als elke kolom in een groep verborgen is, wordt de groepskop helemaal niet gerenderd.

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
