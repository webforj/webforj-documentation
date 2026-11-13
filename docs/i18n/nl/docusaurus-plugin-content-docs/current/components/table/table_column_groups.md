---
title: Kolomgroepen
sidebar_position: 7
description: >-
  Group Table columns under shared, nestable multi-row headers using
  ColumnGroup.of and setColumnGroups for complex layouts.
_i18n_hash: 4babef21ef20728da6aa7eec5829e4d1
---
<!-- vale off -->
# Kolomgroepen <DocChip chip='since' label='25.12' />
<!-- vale on -->

Met kolomgroepen kun je gerelateerde kolommen ordenen onder gedeelde koppen die meerdere rijen beslaan. Een groepslabel strekt zich uit over de onderliggende kolommen, zodat gebruikers de structuur van complexe tabellen gemakkelijker kunnen scannen en begrijpen. Groepen kunnen op elk gewenst niveau worden genest, en de `Table` geeft automatisch het juiste aantal koprijen weer.

## Kolomgroepen maken {#creating-column-groups}

Maak een groep met de factorymethode `ColumnGroup.of()` en voeg vervolgens met `add()`-aanroepen kolomverwijzingen, andere groepen of een combinatie van beide toe. Pas de groepen toe op een `Table` met `setColumnGroups()`.

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

Wanneer groepen zijn ingesteld, geeft de `Table` een kop met meerdere rijen weer, waarbij elk groepslabel zich uitstrekt over de onderliggende kolommen. De nestdiepte bepaalt hoeveel koprijen worden weergegeven. Een vlakke groep voegt één extra rij toe, twee niveaus nesting voegen er twee toe, enzovoort.

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
Groepen kunnen op elk moment worden ingesteld of gewijzigd, vóór of nadat de `Table` is weergegeven. Geef `null` of een lege lijst door aan `setColumnGroups()` om alle groepering te verwijderen en terug te keren naar een kop met één rij.
<!-- vale Google.OxfordComma = YES -->

```java
// Verwijder alle kolomgroepen
table.setColumnGroups(null);
```

## Kolomvolgorde {#column-ordering}

Wanneer groepen actief zijn, wordt de visuele kolomvolgorde bepaald door de groepstructuur en niet door de volgorde waarin kolommen aan de `Table` zijn toegevoegd. De structuur wordt diepte-eerst van links naar rechts doorlopen.

```
Kolommen toegevoegd:  [A, B, C, D, E]
Groepen:              Groep "G1" [C, A], Groep "G2" [E, D]
Visuele volgorde:     C, A, E, D, B
```

Niet-gegroepeerde kolommen, die niet in een groep worden genoemd, worden niet verborgen. Ze verschijnen op hun natuurlijke positie ten opzichte van gegroepeerde kolommen, gebaseerd op de volgorde waarin ze oorspronkelijk aan de `Table` zijn toegevoegd.

In dit voorbeeld verschijnt `Number` als eerste omdat het vóór `Title` is toegevoegd. `Label` verschijnt tussen `Genre` en `Cost` omdat het in de oorspronkelijke kolomvolgorde tussen deze kolommen is toegevoegd:

```
Kolommen toegevoegd:  [Number, Title, Artist, Genre, Label, Cost]
Groepen:              Groep "Music" [Title, Artist, Genre], Groep "Pricing" [Cost]
Visuele volgorde:     Number, Title, Artist, Genre, Label, Cost
```

De volgende demo illustreert dit gedrag. `Number` en `Label` worden in geen enkele groep genoemd, maar behouden hun natuurlijke positie op basis van de volgorde waarin ze aan de `Table` zijn toegevoegd.

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

:::tip De plaatsing van niet-gegroepeerde kolommen bepalen
Als je de plaatsing van niet-gegroepeerde kolommen expliciet wilt bepalen, neem je ze op als kolomverwijzingen op het hoogste niveau in de groepstructuur.
:::

## Kolommen verplaatsen binnen groepen {#column-moving-within-groups}

Wanneer groepen actief zijn, gelden er beperkingen voor het verslepen van kolommen, zodat de groepsintegriteit behouden blijft:

- **Binnen een groep**: een kolom binnen een groep kan alleen binnen de directe bovenliggende groep worden verplaatst. Als je de kolom buiten de groep sleept, wordt de verplaatsing geweigerd en springt de kolom terug naar de oorspronkelijke positie.
- **Niet-gegroepeerde kolommen**: een niet-gegroepeerde kolom kan alleen naar posities worden verplaatst die door andere niet-gegroepeerde kolommen worden ingenomen. De kolom kan niet midden in een groep worden neergezet.
- **Groepen opnieuw ordenen**: een volledige groep kan worden versleept om deze opnieuw te ordenen tussen groepen op hetzelfde nestniveau.

```
Groepen:  Groep "G1" [A, B, C], Groep "G2" [D, E]

Verplaats A naar positie 2 -> OK (binnen G1, resultaat: [B, C, A])
Verplaats A naar positie 3 -> Geweigerd (positie 3 ligt binnen G2)
Verplaats D naar positie 4 -> OK (binnen G2, resultaat: [E, D])
Verplaats D naar positie 1 -> Geweigerd (positie 1 ligt binnen G1)
```

## Groepen vastzetten {#pinning-groups}

Een groep kan links of rechts worden vastgezet met `setPinDirection()`. Alle kolommen binnen de groep nemen de vastzettingsrichting van de groep over, en individuele instellingen voor het vastzetten van kolommen worden overschreven door de groep.

```java
ColumnGroup idInfo = ColumnGroup.of("id-info", "ID Info")
  .setPinDirection(PinDirection.LEFT)
  .add("number")
  .add("title");

// Zowel "number" als "title" worden links vastgezet,
// ongeacht hun eigen instellingen voor vastzetten
```

Niet-gegroepeerde kolommen behouden hun eigen vastzettingsrichting uit de kolomdefinitie.

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

De hoogte van rijen met groepskoppen kan onafhankelijk van gewone kolomkoppen worden ingesteld met `setGroupHeaderHeight()`.

```java
table.setGroupHeaderHeight(32); // Groepsrijen zijn 32px hoog
table.setHeaderHeight(48);      // De rij met kolomkoppen blijft 48px hoog
```

De standaardhoogte van groepskoppen komt overeen met de standaardhoogte van koppen.

## Groepen opmaken met CSS-parts {#styling-groups-with-css-parts}

Groepskoppen en kolommen bieden CSS-parts voor opmaak via `::part()`. De volgende parts zijn beschikbaar:

| Part | Beschrijving |
| --- | --- |
| `cell-group-{ID}` | Groepskopcel, geselecteerd op groeps-ID |
| `cell-group-depth-{N}` | Groepskopcel, geselecteerd op diepte (`0` = hoogste niveau, `1` = tweede niveau, enz.) |
| `cell-column-{ID}` | Alle cellen (kop en inhoud) voor een bepaalde kolom-ID |
| `cell-content-group-{ID}` | Inhoudswrapper binnen een groepskop |
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

### Opmaak op basis van groeps-ID {#styling-by-group-id}

Gebruik de groeps-ID om specifieke groepen een unieke kleur of typografie te geven.

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

### Opmaak op basis van diepte {#styling-by-depth}

Parts op basis van diepte zijn handig wanneer je alle groepen op een bepaald nestniveau op dezelfde manier wilt opmaken, zonder elke groep afzonderlijk te selecteren.

```css
/* Alle groepen op het hoogste niveau opmaken */
dwc-table::part(cell-group-depth-0) {
  background: var(--dwc-color-primary-30);
  font-weight: 700;
}

/* Alle groepen op het tweede niveau opmaken */
dwc-table::part(cell-group-depth-1) {
  background: var(--dwc-color-primary-40);
}
```

## Verborgen kolommen {#hidden-columns}

Verborgen kolommen worden uitgesloten van de visuele volgorde en de kopindeling. Als een groep zowel zichtbare als verborgen kolommen bevat, worden alleen de zichtbare kolommen weergegeven en wordt de `colspan` van de groep hierop aangepast. Als alle kolommen in een groep verborgen zijn, wordt de groepskop niet weergegeven.

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
