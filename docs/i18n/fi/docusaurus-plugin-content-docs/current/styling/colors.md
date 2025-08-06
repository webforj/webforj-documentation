---
sidebar_position: 3
title: Colors
_i18n_hash: 257b725aa7a7992bc6cedd91de9c9ca5
---

webforJ tarjoaa värijärjestelmän, joka perustuu CSS:n mukautettaviin ominaisuuksiin. Nämä väri muuttujat pitävät visuaalisen tyylin johdonmukaisena sovelluksessasi, kun samalla antavat sinulle täydellisen hallinnan mukauttaa väriportaita suunnittelu tarpeidesi mukaan.

Voit viitata mihin tahansa väriin käyttämällä `--dwc-color-{palette}-{shade}` syntaksia, jossa `{palette}` on väriryhmän nimi (esim. `primary`, `danger`, ..) ja `{shade}` on numero välillä `5` ja `95` viiden välein, joka edustaa värin vaaleutta.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Väriarvojen skaala
Väriarvot vaihtelevat `5` (tummin) ja `95` (vaalein) välillä, lisääntyen viiden askelein.
:::

## Väripaletit {#color-palettes}

Saatavilla on useita sisäänrakennettuja väripaletteja, jotka on suunniteltu semanttisiin käyttötarkoituksiin, kuten brändäykseen, onnistumisteksteihin, varoituksiin ja muuhun. Kukin paletti koostuu dynaamisesti generoituista sävyistä ja väreistä kolmen keskeisen ominaisuuden perusteella: `hue`, `saturation` ja `contrast-threshold`.

### Saatavilla olevat paletit {#available-palettes}

- **default**: Neutraali harmaapohjainen paletti, joka on sävytetty ensisijaisella värillä, käytetään useimmissa komponenteissa.
- **primary**: Edustaa tyypillisesti brändisi ensisijaista väriä.
- **success**, **warning**, **danger**: Semanttiset paletit, joita käytetään asianmukaisissa tilan indikoijissa.
- **info**: Valinnainen lisäpaletti toissijaisen korostuksen tueksi.
- **gray**: Aito harmaasävy paletti, ilman sävyjä.
- **black/white**: Staattiset väriarvot

<ColorPalette></ColorPalette>

<br/>

:::tip DWC HueCraft
Helpottaaksesi WCAG-yhteensopivien palettien luomisen prosessia webforJ-sovelluksille, voit käyttää [DWC HueCraft](https://webforj.github.io/huecraft/) työkalua. Se tukee palettien luomista brändiväreistä tai logoista ja mahdollistaa nopean CSS-viennin.
:::

### Tumma tila käyttäytyminen {#dark-mode-behavior}

webforJ tukee käänteistä väri strategiaa tummassa tilassa. Sen sijaan, että käytetään täysin erillisiä väripaletteja, se kääntää tapaa, jolla vaaleusarvoja tulkitaan.

Tämä tarkoittaa, että **tummissa teemoissa** alhaiset sävyarvot (esim. `--dwc-color-primary-5`) muuttuvat vaaleiksi ja korkeammat arvot (esim. `--dwc-color-primary-95`) muuttuvat tummiksi. Logiikka on käänteinen varmistaakseen optimaalisen kontrastin ja luettavuuden taustojen yli.

Komponenttisi koodi pysyy samana riippumatta teemasta. Esimerkiksi:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

Vaaleassa tilassa tämä antaa keskitason taustavärin. Tummassa tilassa se antaa myös keskitason, mutta visuaalisesti käännettynä toimimaan tummilla pinnoilla. Tämä lähestymistapa välttää duplication, pitää tyylisi johdonmukaisina ja varmistaa sujuvat visuaaliset siirtymät, kun vaihdetaan vaaleiden ja tummien teemojen välillä.

### Palettien konfigurointimuuttujat {#palette-configuration-variables}

Jokainen paletti luodaan seuraavien muuttujien perusteella:

| Muuttuja               | Kuvaus |
|------------------------|-------------|
| `hue`                  | Kulma (asteina) väripyörällä. Yksikköedelliset arvot tulkitaan asteina. |
| `saturation`           | Prosentti, joka osoittaa värin intensiivisyyden. `100%` on täysin kylläinen; `0%` on harmaa. |
| `contrast-threshold`   | Arvo, joka vaihtelee `0` ja `100` välillä ja määrittää, tulisiko tekstin olla vaalea tai tumma taustan vaaleuden perusteella. |

Voit säätää palettia määrittelemällä nämä muuttujat juurityylistesi. Esimerkiksi, muokataksesi ensisijaista palettia:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Komponenttien teemoitus abstrakteilla muuttujilla {#theming-components-with-abstract-variables}

Helpottaaksesi tyylittelyä ja johdonmukaisuutta komponenttien välillä, webforJ esittelee abstraktiokerroksen perusväripalettejen päälle. Tämä kerros perustuu **abstrakteihin teemamuuttujiin** - CSS:n mukautettaviin ominaisuuksiin, jotka viittaavat tiettyihin sävyihin väripaleteissa.

Nämä muuttujat helpottavat teemojen soveltamista kaikkiin komponentteihin ilman, että viitataan suoraan raakaväriarvoihin tai näytteenottoihin. Voit ajatella niitä *semanttisten tyylittelytietojen oikoteinä*, jotka heijastavat sovelluksesi tarkoitusta enemmän kuin sen toteutustietoja.

### Muuttujaryhmät {#variable-groups}

Abstraktit teemamuuttujat on järjestetty neljään ryhmään:

1. [Normaali](#normal-state) Käytetään oletusulkonäössä, kuten taustoissa ja tekstissä inaktiivisille komponenteille.
2. [Tumma](#darker-variant) Käytetään aktiivisille tai valituille tiloille.
3. [Vaalea](#lighter-variant) Käytetään hover- ja fokus tiloille.
4. [Alt](#alt-variant) Käytetään toissijaisille korostuksille, kuten näppäimistön fokukselle tai hienovaraisille UI-aksenteille.

Jokainen ryhmä määrittelee:

- Taustaväri
- Etu (teksti) väri
- Rajoituksen väri (fokusoituneissa/hallinnoiduissa/aktiivisissa tiloissa)
- Fokussäde (käytetään, kun komponentti saa näkyvän fokusin)

Jokainen välilehti alla näyttää abstrakti muuttujat, jotka on määritelty tietyssä paletissa (`primary`, `success`, `danger`, jne.). Nämä muuttujat ottavat arvot taustalla olevasta paletista (esim. `--dwc-color-primary-40`) ja tekevät niistä käyttökelpoisia sovelluksessasi.

Esimerkiksi, sen sijaan, että käyttäisit suoraan `--dwc-color-primary-40`, voit soveltaa `--dwc-color-primary`, joka abstrahoi sen värin roolin *oletustaustana* ensisijaistyyliselle komponentille.

Palettiarvojen muuttaminen yhdessä paikassa päivittää kaikkien komponenttien ulkoasun, jotka riippuvat näistä abstrakteista muuttujista.

### Normaali tila {#normal-state}

Käytetään komponentin perustason, neutraalin ulkoasun—kun se on toimettomana eikä sen kanssa vuorovaikuta.

| Muuttuja                           | Kuvaus                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | Oletustaustaväri. Käytetään myös monien komponenttien rajoissa. |
| `--dwc-color-on-${name}-text`      | Tekstiväri, joka näkyy oletustaustan päällä.                 |
| `--dwc-color-${name}-text`         | Tekstiväri, kun komponentti on sijoitettu pinnan taustalle. |
| `--dwc-border-color-${name}`       | Rajoituksen väri, käytetään pääasiassa hover-, focus- ja aktiivisissa tiloissa.         |
| `--dwc-focus-ring-${name}`         | Fokussäde varjo, kun komponentti saa näkyvän fokus-tyylin.   |

---

### Tummempi variantti {#darker-variant}

Käytetään valituissa tai aktiivisissa tiloissa—yleensä syvempää sävyä vahvemmalla kontrastilla ja korostuksella.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Tummempi versio perusväristä. Käytetään usein painetuissa tai valituissa tiloissa. |
| `--dwc-color-on-${name}-text-dark`      | Tekstiväri käytettäessä tummalla taustalla.                               |
| `--dwc-color-${name}-text-dark`         | Hiukan tummempi tekstivaihtoehto, kun näytetään pinnalla.            |

---

### Vaaleampi variantti {#lighter-variant}

Käytetään hover-, focus- ja vähemmän hallitsevissa visuaalisissa vihjeissä. Nämä ovat pehmeitä sävyjä, jotka on suunniteltu hienovaraisiin vuorovaikutuspalautteisiin.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Vaaleampi versio perusväristä. Käytetään tyypillisesti hover/focus taustoissa. |
| `--dwc-color-on-${name}-text-light`     | Tekstiväri, kun näytetään vaalealla taustalla.                             |
| `--dwc-color-${name}-text-light`        | Vaaleampi tekstiväri vähemmän korostetuissa tiloissa.                    |

---

### Alt variantti {#alt-variant}

Käytetään toissijaisiin korostuksiin tai UI-korostuksiin—kuten näppäimistön navigointifokusrivit tai apuvälineet.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Erittäin vaalea versio väristä, käytetään pääasiassa korostuksiin tai taustan heijastuksiin. |
| `--dwc-color-on-${name}-text-alt`       | Tekstiväri, kun tausta on vaihtoehtoinen (`alt`) väri.           |

<Tabs>

<TabItem value="Default / Tone">

```css
--dwc-color-default-dark: var(--dwc-color-default-85);
--dwc-color-on-default-text-dark: var(--dwc-color-default-text-85);
--dwc-color-default-text-dark: var(--dwc-color-default-35);

--dwc-color-default: var(--dwc-color-default-90);
--dwc-color-on-default-text: var(--dwc-color-default-text-90);
--dwc-color-default-text: var(--dwc-color-default-40);

--dwc-color-default-light: var(--dwc-color-default-95);
--dwc-color-on-default-text-light: var(--dwc-color-default-text-95);
--dwc-color-default-text-light: var(--dwc-color-default-45);

--dwc-color-default-alt: var(--dwc-color-primary-alt);
--dwc-color-on-default-text-alt: var(--dwc-color-on-primary-text-alt);

--dwc-border-color-default: var(--dwc-border-color-primary);
--dwc-focus-ring-default: var(--dwc-focus-ring-primary);
```

</TabItem>

<TabItem value="Primary">

```css
--dwc-color-primary-dark: var(--dwc-color-primary-35);
--dwc-color-on-primary-text-dark: var(--dwc-color-primary-text-35);
--dwc-color-primary-text-dark: var(--dwc-color-primary-30);

--dwc-color-primary: var(--dwc-color-primary-40);
--dwc-color-on-primary-text: var(--dwc-color-primary-text-40);
--dwc-color-primary-text: var(--dwc-color-primary-35);

--dwc-color-primary-light: var(--dwc-color-primary-45);
--dwc-color-on-primary-text-light: var(--dwc-color-primary-text-45);
--dwc-color-primary-text-light: var(--dwc-color-primary-40);

--dwc-color-primary-alt: var(--dwc-color-primary-95);
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text-95);

--dwc-border-color-primary: var(--dwc-color-primary);
--dwc-focus-ring-primary: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-primary-h),
    var(--dwc-color-primary-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Success">

```css
--dwc-color-success-dark: var(--dwc-color-success-20);
--dwc-color-on-success-text-dark: var(--dwc-color-success-text-20);
--dwc-color-success-text-dark: var(--dwc-color-success-15);

--dwc-color-success: var(--dwc-color-success-25);
--dwc-color-on-success-text: var(--dwc-color-success-text-25);
--dwc-color-success-text: var(--dwc-color-success-20);

--dwc-color-success-light: var(--dwc-color-success-30);
--dwc-color-on-success-text-light: var(--dwc-color-success-text-30);
--dwc-color-success-text-light: var(--dwc-color-success-25);

--dwc-color-success-alt: var(--dwc-color-success-95);
--dwc-color-on-success-text-alt: var(--dwc-color-success-text-95);

--dwc-border-color-success: var(--dwc-color-success);
--dwc-focus-ring-success: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-success-h),
    var(--dwc-color-success-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Warning">

```css
--dwc-color-warning-dark: var(--dwc-color-warning-35);
--dwc-color-on-warning-text-dark: var(--dwc-color-warning-text-35);
--dwc-color-warning-text-dark: var(--dwc-color-warning-15);

--dwc-color-warning: var(--dwc-color-warning-40);
--dwc-color-on-warning-text: var(--dwc-color-warning-text-40);
--dwc-color-warning-text: var(--dwc-color-warning-20);

--dwc-color-warning-light: var(--dwc-color-warning-45);
--dwc-color-on-warning-text-light: var(--dwc-color-warning-text-45);
--dwc-color-warning-text-light: var(--dwc-color-warning-25);

--dwc-color-warning-alt: var(--dwc-color-warning-95);
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text-95);

--dwc-border-color-warning: var(--dwc-color-warning);
--dwc-focus-ring-warning: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-warning-h),
    var(--dwc-color-warning-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-warning-a)
  );
```

</TabItem>

<TabItem value="Danger">

```css
--dwc-color-danger-dark: var(--dwc-color-danger-35);
--dwc-color-on-danger-text-dark: var(--dwc-color-danger-text-35);
--dwc-color-danger-text-dark: var(--dwc-color-danger-30);

--dwc-color-danger: var(--dwc-color-danger-40);
--dwc-color-on-danger-text: var(--dwc-color-danger-text-40);
--dwc-color-danger-text: var(--dwc-color-danger-35);

--dwc-color-danger-light: var(--dwc-color-danger-45);
--dwc-color-on-danger-text-light: var(--dwc-color-danger-text-45);
--dwc-color-danger-text-light: var(--dwc-color-danger-40);

--dwc-color-danger-alt: var(--dwc-color-danger-95);
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text-95);

--dwc-border-color-danger: var(--dwc-color-danger);
--dwc-focus-ring-danger: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-danger-h),
    var(--dwc-color-danger-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Info">

```css
--dwc-color-info-dark: var(--dwc-color-info-35);
--dwc-color-on-info-text-dark: var(--dwc-color-info-text-35);
--dwc-color-info-text-dark: var(--dwc-color-info-35);

--dwc-color-info: var(--dwc-color-info-40);
--dwc-color-on-info-text: var(--dwc-color-info-text-40);
--dwc-color-info-text: var(--dwc-color-info-40);

--dwc-color-info-light: var(--dwc-color-info-45);
--dwc-color-on-info-text-light: var(--dwc-color-info-text-45);
--dwc-color-info-text-light: var(--dwc-color-info-45);

--dwc-color-info-alt: var(--dwc-color-info-95);
--dwc-color-on-info-text-alt: var(--dwc-color-info-text-95);

--dwc-border-color-info: var(--dwc-color-info);
--dwc-focus-ring-info: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-info-h),
    var(--dwc-color-info-s),
    var(--dwc-focus-ring-l),
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Gray">

```css
--dwc-color-gray-dark: var(--dwc-color-gray-10);
--dwc-color-on-gray-text-dark: var(--dwc-color-gray-text-10);
--dwc-color-gray-text-dark: var(--dwc-color-gray-15);

--dwc-color-gray: var(--dwc-color-gray-15);
--dwc-color-on-gray-text: var(--dwc-color-gray-text-15);
--dwc-color-gray-text: var(--dwc-color-gray-20);

--dwc-color-gray-light: var(--dwc-color-gray-20);
--dwc-color-on-gray-text-light: var(--dwc-color-gray-text-20);
--dwc-color-gray-text-light: var(--dwc-color-gray-25);

--dwc-color-gray-alt: var(--dwc-color-gray-95);
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text-95);

--dwc-border-color-gray: var(--dwc-color-gray);
--dwc-focus-ring-gray: 0 0 0 var(--dwc-focus-ring-width) hsla(
    var(--dwc-color-gray-h),
    var(--dwc-color-gray-s),
    var(--dwc-focus-ring-l),
    var(--dwc-color-gray-a)
  );
```
</TabItem>

</Tabs>
```
