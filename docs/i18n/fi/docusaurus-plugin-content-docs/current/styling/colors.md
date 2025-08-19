---
sidebar_position: 3
title: Colors
_i18n_hash: c0e3dc5c992621c0c9cb3da24ef3964f
---
import ColorPalette from '@site/src/components/DWCTheme/ColorPalette/ColorPalette';

webforJ tarjoaa värijärjestelmän, joka perustuu CSS:n mukautettuihin ominaisuuksiin. Nämä väri muuttujat säilyttävät johdonmukaisen visuaalisen tyylin sovelluksessasi samalla kun ne antavat sinulle täyden valvonnan mukauttaa palettia suunnittelutarpeidesi mukaan.

Voit viitata mihin tahansa väriin käyttäen `--dwc-color-{palette}-{shade}` syntaksia, jossa `{palette}` on väriryhmän nimi (esim. `primary`, `danger`, jne.) ja `{shade}` on numero `5` ja `95` välillä viiden välein, mikä edustaa värin vaaleutta.

```css
.element {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

:::tip Sävyarvojen asteikko
Sävyarvot vaihtelevat välillä `5` (tummimmat) ja `95` (vaaleimmat), kasvavat viiden välein.
:::

## Väripaletit {#color-palettes}

On useita käyttövalmiita väripaletteja, jotka on suunniteltu semanttisia käyttötapauksia varten, kuten brändäys, menestysviestit, varoitukset jne. Kukin paletti koostuu dynaamisesti luoduista sävyistä ja vivahteista, jotka perustuvat kolmeen keskeiseen ominaisuuteen: `hue`, `saturation` ja `contrast-threshold`.

### Saatavilla olevat paletit {#available-palettes}

- **default**: Neutraali harmaapohjainen paletti, joka on sävytetty päävärillä, käytetään useimmissa komponenteissa.
- **primary**: Edustaa yleensä brändisi pääväriä.
- **success**, **warning**, **danger**: Semanttiset paletit käytettäväksi asianmukaisina tilan indikaattoreina.
- **info**: Valinnainen täydentävä paletti toissijaiselle painotukselle.
- **gray**: Todellinen harmaan sävyinen paletti, ilman sävyä.
- **black/white**: Staattiset väriarvot.

<ColorPalette></ColorPalette>


<br/>

:::tip DWC HueCraft
Helpottaaksesi WCAG-yhteensopivien palettien luomisprosessia webforJ-sovelluksillesi, voit käyttää [DWC HueCraft](https://webforj.github.io/huecraft/) työkalua. Se tukee palettien luomista brändivärien tai logojen perusteella ja mahdollistaa nopean CSS-viennin.
:::


### Tumma tila {#dark-mode-behavior}

webforJ tukee käänteistä väristrategiaa tummassa tilassa. Sen sijaan, että käyttäisit täysin erillisiä väripaletteja, se kääntää huomioväriarvoja.

Tämä tarkoittaa, että **tummissa teemoissa** alhaiset sävyarvot (esim. `--dwc-color-primary-5`) muuttuvat vaaleiksi ja korkeammat arvot (esim. `--dwc-color-primary-95`) tummiksi. Logiikka kääntyy varmistamaan optimaalisen kontrastin ja luettavuuden taustojen yli.

Komponenttikoodisi pysyy samana riippumatta teemasta. Esimerkiksi:

```css
.button {
  background-color: var(--dwc-color-primary-40);
  color: var(--dwc-color-primary-text-40);
}
```

Vaalea tila antaa keskiväri taustan. Tumma tila antaa silti keskivärin, mutta käänteisenä visuaalisesti suljettujen pintojen päällä. Tämä lähestymistapa välttää päällekkäisyyksiä, pitää tyylisi johdonmukaisina ja tekee visuaalisista siirtymisistä sujuvia kun vaihdetaan vaaleista tummiin teemoihin.

### Palettien konfigurointi muuttujat {#palette-configuration-variables}

Jokainen paletti luodaan seuraavien muuttujien perusteella:

| Muuttuja               | Kuvaus |
|------------------------|-------------|
| `hue`                  | Kulma (asteina) väripyörällä. Yksikköinä arvoja käsitellään asteina. |
| `saturation`           | Prosentti, joka indikoi värin voimakkuutta. `100%` on täysin kyllästetty; `0%` on harmaasävy. |
| `contrast-threshold`   | Arvo välillä `0` ja `100`, joka määrittää, onko teksti vaaleaa vai tummaa taustavärin vaaleuden perusteella. |

Voit säätää palettia määrittelemällä nämä muuttujat juurisijoituksissasi. Esimerkiksi, muuttaaksesi pääpalettia:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
  --dwc-color-primary-c: 60;
}
```

## Komponenttien teemoittaminen abstrakteilla muuttujilla {#theming-components-with-abstract-variables}

Helpottaaksesi tyylittämistä ja johdonmukaisuutta komponenttien kesken, webforJ esittelee abstraktiokerroksen perusväripalettejensa päälle. Tämä kerros perustuu **abstrakteihin teema muuttujille** - CSS:n mukautetut ominaisuudet, jotka viittaavat tiettyihin sävyihin väripaletissa.

Nämä muuttujat helpottavat teemojen soveltamista kaikissa komponenteissa ilman, että suoraan viitataan raakoihin väriarvoihin tai vahahiin. Voit ajatella niitä *semanttisina tyylitien oikoreitteinä*, jotka heijastavat sovelluksesi aikomusta sen toteutustiedon sijaan.

### Muuttujaryhmät {#variable-groups}

Abstraktit teema muuttujat on järjestetty neljään ryhmään:

1. [Normaali](#normal-state) Käytetään oletusulkonäössä, kuten taustoissa ja tekstissä inaktiivisissa komponenteissa.
2. [Tumma](#darker-variant) Käytetään aktiivisissa tai valituissa tiloissa.
3. [Vaalea](#lighter-variant) Käytetään hover- ja keskipisteissä.
4. [Alt](#alt-variant) Käytetään toissijaisissa korostuksissa, kuten näppäimistöfokuksessa tai hienovaraisissa UI-vivahteissa.

Jokainen ryhmä määrittelee:

- Taustaväri
- Etualan (teksti) väri
- Rajas väri (fokusoitu/hovered/aktiivisissa tiloissa)
- Fokusoituväri (käytetään, kun komponentti saa näkyväfokustyylit)

Jokainen välilehti alla näyttää abstraktit muuttujat, jotka on määritelty tietylle paletille (`primary`, `success`, `danger`, jne.). Nämä muuttujat haetaan arvoista taustalla olevasta paletista (esim. `--dwc-color-primary-40`) ja tekevät niistä uudelleenkäytettäviä sovelluksessasi.

Esimerkiksi, sen sijaan, että käyttäisit suoraan `--dwc-color-primary-40`, voit soveltaa `--dwc-color-primary`, joka abstrahoi sen roolin *oletustaustaksi* primääriselle komponentille.

Palettimuutosten tekeminen yhdessä paikassa päivittää kaikkien komponenttien ulkoasun, jotka tukeutuvat näihin abstrakteihin muuttujiin.

### Normaali tila {#normal-state}

Käytetään komponentin perustavaa, neutraalia ilmettä - kun se on käyttämättömänä eikä siihen ole vuorovaikutuksessa.

| Muuttuja                           | Kuvaus                                                             |
| ---------------------------------- | ----------------------------------------------------------------------- |
| `--dwc-color-${name}`              | Oletustaustaväri. Käytetään myös monien komponenttien rajoissa. |
| `--dwc-color-on-${name}-text`      | Tekstiväri, joka näkyy oletustaustalla.                 |
| `--dwc-color-${name}-text`         | Tekstiväri, kun komponentti on asetettu pintataustalle. |
| `--dwc-border-color-${name}`       | Raja väri, käytetään pääasiassa hover, focus ja aktiivisissa tiloissa.         |
| `--dwc-focus-ring-${name}`         | Fokusrengasvarjo, kun komponentti saa fokuksen näkyvän tyylin.   |

---

### Tummempi variantti {#darker-variant}

Käytetään valitsemisen tai aktiivisten tilojen yhteydessä - yleensä syvempi sävy voimakkaamman kontrastin ja korostuksen vuoksi.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-dark`              | Tummempi versio perusväri. Käytetään usein painettuina tai valittuina tiloina. |
| `--dwc-color-on-${name}-text-dark`      | Tekstiväri, kun sitä käytetään tummalla taustalla.                               |
| `--dwc-color-${name}-text-dark`         | Hiukan tummempi tekstivaihtoehto, kun sitä näytetään pinnalla.            |

---

### Vaaleampi variantti {#lighter-variant}

Käytetään hover, focus ja vähemmän hallitsevissa visuaalisissa vihjeissä. Nämä ovat pehmeitä sävyjä, jotka on suunniteltu hienovaraiseksi vuorovaikutuspalautteeksi.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-light`             | Vaaleampi versio perusväristä. Tyypillisesti käytetään hover/focus taustoissa. |
| `--dwc-color-on-${name}-text-light`     | Tekstiväri, kun se näkyy vaalealla taustalla.                             |
| `--dwc-color-${name}-text-light`        | Vaaleampi tekstiväri käytettäväksi vähemmän korostetuissa tiloissa.                    |

---

### Alt variantti {#alt-variant}

Käytetään toissijaiselle korostukselle tai UI-kohokohdille - kuten näppäimistön navigointifokusoutlineille tai apuvälineille.

| Muuttuja                                | Kuvaus                                                              |
| --------------------------------------- | ------------------------------------------------------------------------ |
| `--dwc-color-${name}-alt`               | Erittäin vaaleampi versio väristä, käytetään pääasiassa korostuksissa tai taustavaloissa. |
| `--dwc-color-on-${name}-text-alt`       | Tekstiväri, kun tausta on vaihtoehtoinen (`alt`) väri.           |

<Tabs>

<TabItem value="Oletus / Sävy">

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

<TabItem value="Pää">

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

<TabItem value="Menestys">

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
    var(--dwc-focus-ring-a)
  );
```

</TabItem>

<TabItem value="Vaara">

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
    var(--dwc-focus-ring-a)
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
