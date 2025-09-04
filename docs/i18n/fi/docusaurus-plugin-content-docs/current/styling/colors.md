---
sidebar_position: 3
title: Colors
_i18n_hash: d82a6a563267933d08c081faeddf2cc0
---
webforJ tarjoaa värijärjestelmän, joka perustuu CSS:n mukautettuihin ominaisuuksiin. Nämä värimuuttujat säilyttävät johdonmukaisen visuaalisen tyylin sovelluksessasi, samalla kun ne antavat sinulle täyden hallinnan mukauttaa värejä suunnittelutarpeidesi mukaan.

Voit viitata mihin tahansa väriin käyttämällä syntaksia `--dwc-color-{palette}-{shade}`, jossa `{palette}` on väriryhmän nimi (esim. `primary`, `danger`, ..) ja `{shade}` on numero `5` ja `95` välillä viiden numeron välein, joka edustaa värin vaaleutta.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Varshade-arvoasteikko
Varshade-arvot vaihtelevat `5` (tummimmat) ja `95` (vaaleimmat) välillä, lisääntyvät viiden välein.
:::

## Väriteemat {#color-palettes}

Saatavilla on useita valmiita väriteemoja, jotka on suunniteltu semanttisiin käyttötarkoituksiin, kuten brändäykseen, onnistumisviesteihin, varoituksiin ja muuhun. Jokainen väripaletista koostuu dynaamisesti luoduista sävyistä ja vaaleuksista, jotka perustuvat kolmeen keskeiseen ominaisuuteen: `hue`, `saturation` ja `contrast-threshold`.

### Saatavilla olevat paletit {#available-palettes}

- **default**: Puhtaasti harmaaseen perustuva neutraali paletti, jota on sävytetty ensisijaisella värillä, käytetään useimmissa komponenteissa.
- **primary**: Edustaa tyypillisesti brändisi ensisijaista väriä.
- **success**, **warning**, **danger**: Semanttiset paletit, joita käytetään sopivien tilan ilmaisten esittämiseen.
- **info**: Valinnainen täydentävä paletti toissijaisen korostamisen varten.
- **gray**: Todellinen harmaasävyjen paletti, ilman sävyä.
- **black/white**: Staattiset värimaailmat.

<ColorPalette></ColorPalette>

<br/>

:::tip DWC HueCraft
Helpottaaksesi WCAG-yhteensopivien palettien luomista webforJ-sovelluksillesi, voit käyttää [DWC HueCraft](https://webforj.github.io/huecraft/) työkalua. Se tukee palettien luomista brändivärien tai logojen perusteella ja mahdollistaa nopean CSS-viennin.
:::

### Tumman tila käyttäytyminen {#dark-mode-behavior}

webforJ tukee käänteistä väri strategiaa tummassa tilassa. Sen sijaan, että käyttäisit täysin erillisiä väriteemoja, se kääntää vaaleusarvojen tulkintatapaa.

Tämä tarkoittaa, että **tummissa teemoissa** alhaiset shade-arvot (esim. `--dwc-color-primary-5`) muuttuvat vaaleiksi ja korkeammat arvot (esim. `--dwc-color-primary-95`) tummiksi. Logiikka käännetään optimaalisen kontrastin ja luettavuuden varmistamiseksi taustojen välillä.

Komponenttikoodisi pysyy samana riippumatta teemasta. Esimerkiksi:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

Valoisassa tilassa tämä antaa keskivärisen taustan. Tummassa tilassa se tarjoaa edelleen keskitason, mutta käännettynä visuaalisesti tummille pinnoille. Tämä lähestymistapa välttää päällekkäisyyksiä, pitää tyylit johdonmukaisina ja varmistaa visuaalisten siirtymien sujuvuuden vaaleista tummiin teemoihin siirryttäessä.

### Paletin konfigurointimuuttujat {#palette-configuration-variables}

Jokainen paletti johdetaan seuraavista muuttujista:

| Muuttuja               | Kuvaus |
|------------------------|-------------|
| `hue`                  | Kulma (asteina) värirenkaalla. Yksikköarvoja tulkitaan asteina. |
| `saturation`           | Prosentti, joka ilmoittaa värin intensiivisyyden. `100%` on täysin kylläinen; `0%` on harmaaaste. |
| `contrast-threshold`   | Arvo väliltä `0` ja `100`, joka määräytyy sen mukaan, tulisiko teksti olla vaalea vai tumma taustan vaaleuden perusteella. |

Voit säätää palettia määrittelemällä nämä muuttujat juurityylissäsi. Esimerkiksi, muuttaaksesi ensisijaista palettia:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Komponenttien teemoitus abstraktien muuttujien avulla {#theming-components-with-abstract-variables}

Tyylin ja johdonmukaisuuden helpottamiseksi komponenttien välillä, webforJ esittelee abstraktiokerroksen perustason väriteemojen päälle. Tämä kerros perustuu **abstrakteihin teemu muuttujiin** - CSS:n mukautettuihin ominaisuuksiin, jotka viittaavat tiettyihin sävyihin väripalettissa.

Nämä muuttujat helpottavat teemojen käyttöä kaikissa komponenteissa ilman, että sinun tarvitsee viitata suoraan raakoihin väriväwerteihin tai väriin. Voit ajatella niitä *semanttisina tyylityökaluna*, jotka heijastavat sovelluksesi aikomusta sen toteutuksen yksityiskohtien sijaan.

### Muuttujaryhmät {#variable-groups}

Abstraktit teemamuuttujat on järjestetty neljään ryhmään:

1. [Normaali](#normal-state) Käytetään oletusulkoasun, kuten taustojen ja tekstin dynaamisissa komponenteissa.
2. [Tumma](#darker-variant) Käytetään aktiivisissa tai valituissa tiloissa.
3. [Vaalea](#lighter-variant) Käytetään hover- ja fokus-tiloissa.
4. [Alt](#alt-variant) Käytetään toissijaisissa korostuksissa, kuten näppäimistön fokuksessa tai hienovaraisissa käyttöliittymän sävyissä.

Jokainen ryhmä määrittelee:

- Taustaväri
- Etualan (teksti) väri
- Rantaväri (fokusoiduissa/hypiävissä/aktiivisissa tiloissa)
- Fokus-renkaat (käytetään, kun komponentti saa näkyvän fokus-tyylin)

Jokainen välilehti alla osoittaa abstraktit muuttujat, jotka on määritelty tietylle paletille (`primary`, `success`, `danger`, jne.). Nämä muuttujat nappaavat arvot alustan paletista (esim. `--dwc-color-primary-40`) ja tekevät niistä käytettävissä sovelluksessasi.

Esimerkiksi, sen sijaan, että käyttäisit suoraan `--dwc-color-primary-40`, voit soveltaa `--dwc-color-primary`, joka abstrahoi sen roolin *oletustaustana* ensisijaisille tyyli komponenteille.

Muuttamalla palettin arvoja yhdessä paikassa päivittää kaikkien näiden abstraktien muuttujien varan saaneiden komponenttien ulkoasua.

### Normaali tila {#normal-state}

Käytetään komponentin perus-, neutraalia ulkoasua - kun se on lepotilassa eikä sillä ole vuorovaikutusta.

| Muuttuja                           | Kuvaus                                                             |
| ---------------------------------- | --------------------------------------------------------------------- |
| `--dwc-color-${name}`              | Oletus taustaväri. Käytetään myös useimmissa komponenttien rajoissa. |
| `--dwc-color-on-${name}-text`      | Tekstiväri, joka näkyy oletus taustan yllä.                        |
| `--dwc-color-${name}-text`         | Tekstiväri, kun komponentti on asetettu pinnan taustalle.         |
| `--dwc-border-color-${name}`       | Rantaväri, käytetään pääasiassa hover, fokus ja aktiivisissa tiloissa. |
| `--dwc-focus-ring-${name}`         | Fokusrenkaan varjo, kun komponentti saa näkyvän fokus-tyylin.     |

---

### Tummempi variantti {#darker-variant}

Käytetään valituissa tai aktiivisissa tiloissa - yleensä syvempi sävy voimakkaamman kontrastin ja korostuksen vuoksi.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Tummempi versio perusväristä. Käytetään usein painetuissa tai valituissa tiloissa. |
| `--dwc-color-on-${name}-text-dark`      | Tekstiväri, kun taustana on tumma väri.                              |
| `--dwc-color-${name}-text-dark`         | Aavistuksen tummempi tekstivaihtoehto pinnan päällä.            |

---

### Vaaleampi variantti {#lighter-variant}

Käytetään hover-, fokus- ja vähemmän hallitsevissa visuaalista varoitusviestintää. Nämä ovat pehmeitä sävyjä, jotka on suunniteltu hienovaraisiksi vuorovaikutuksen palautteiksi.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Vaaleampi versio perusväristä. Tyypillisesti käytetään hover/fokus taustoissa. |
| `--dwc-color-on-${name}-text-light`     | Tekstiväri, kun se näkyy vaalealla taustalla.                             |
| `--dwc-color-${name}-text-light`        | Vaaleampi tekstisävy vähemmän hallitsevissa tiloissa.                    |

---

### Alt-variantti {#alt-variant}

Käytetään toissijaiselle korostamiselle tai käyttöliittymän kohokohtiin - kuten näppäimistön navigoinnin fokusviivoille tai apuviesteille.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Erittäin vaalea versio väri, käytetään pääasiassa korostuksissa tai taustasäteilyssä. |
| `--dwc-color-on-${name}-text-alt`       | Tekstiväri, kun taustana on vaihtoehtoinen (`alt`) väri.           |

<Tabs>

<TabItem value="Oletus / sävy">

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

<TabItem value="Ensisijainen">

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

<TabItem value="Onnistuminen">

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
    var(--dwc-color-success-a)
  );
```

</TabItem>

<TabItem value="Varoitus">

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

<TabItem value="Vaarallisuus">

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
    var(--dwc-color-danger-l),
    var(--dwc-color-danger-a)
  );
```

</TabItem>

<TabItem value="Tietoa">

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
    var(--dwc-color-info-a)
  );
```

</TabItem>

<TabItem value="Harmaa">

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
