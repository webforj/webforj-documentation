---
sidebar_position: 3
title: Colors
_i18n_hash: cc233e97e4b7333262eb47b14bfe572a
---
webforJ tarjoaa värijärjestelmän, joka perustuu CSS:n mukautettaviin ominaisuuksiin. Nämä väri muuttujat säilyttävät johdonmukaisen visuaalisen tyylin sovelluksessasi, samalla kun annat täyden hallinnan mukauttaa väripaletteja suunnittelutarpeidesi mukaan.

Voit viitata mihin tahansa väriin käyttäen syntaksia `--dwc-color-{palette}-{step}`, jossa `{palette}` on väriryhmän nimi (esim. `primary`, `danger`, ..) ja `{step}` on luku `5` - `95` välein `5`, mikä edustaa värin vaaleutta.

```css
.element {
  background-color: var(--dwc-color-primary-50);
  color: var(--dwc-color-on-primary-text-50);
}
```

:::tip Shade Step Scale
Vaiheiden arvot vaihtelevat `5` (tummimmat) ja `95` (vaaleimmat) välillä, kasvavat viiden välein. Vaihe 5 on aina tummimmassa tilassa ja vaihe 95 on aina vaaleimmassa, riippumatta vaaleasta tai tummasta tilasta.
:::

## Väripaletit {#color-palettes}

DWC määrittää seitsemän palettia sekä `black/white` paletin, jossa jokainen paletti on joukkue variations (sävyjä ja sävyjä) semanttisesta väristä.

### Saatavilla olevat paletit {#available-palettes}

- **default**: Neutraali paletti, joka on sävytys päävärillä, käytetään useimpien komponenttien taustoissa, rajoissa ja neutraaleissa käyttöliittymäelementeissä.
- **primary**: Edustaa tyypillisesti brändisi pääväriä.
- **success**, **warning**, **danger**: Semanttiset paletit, joita käytetään asianmukaisiin tilan indikaattoreihin.
- **info**: Täydentävä paletti toissijaiselle korostukselle.
- **gray**: Puhdas harmaan sävyjen paletti, ilman sävyjä.
- **black/white**: Dynaamisesti teemoja tunnistavat värit, jotka mukautuvat nykyiseen teemaan. Lähes musta vaaleassa tilassa muuttuu lähes valkoiseksi tummassa tilassa ja päinvastoin.

<dwc-doc-palettes></dwc-doc-palettes>

### Palettisiemenet {#palette-seeds}

Jokainen paletti generoidaan kahdesta siemen muuttujasta: `hue` ja `saturation`. Asettamalla nämä kaksi arvoa, kaikki 19 vaihetta generoituu automaattisesti.

| Siemen muuttuja | Kuvaus |
|---|---|
| `--dwc-color-{name}-h` | Siemenvärin sävykulma (0-360). |
| `--dwc-color-{name}-s` | Saturaatio prosentteina. `100%` on täysin kyllästetty, `0%` on täysin kyllästämätön (harmaa). |

Voit säätää palettia määrittelemällä nämä muuttujat juuristyyleissäsi. Esimerkiksi, muuttaaksesi pääpalettia:

```css
:root {
  --dwc-color-primary-h: 225;
  --dwc-color-primary-s: 100%;
}
```

<Tabs>

<TabItem value="Primary">

| Muuttuja | Oletusarvo |
|---|---|
| `--dwc-color-primary-h` | 223 |
| `--dwc-color-primary-s` | 91% |

</TabItem>

<TabItem value="Success">

| Muuttuja | Oletusarvo |
|---|---|
| `--dwc-color-success-h` | 153 |
| `--dwc-color-success-s` | 60% |

</TabItem>

<TabItem value="Warning">

| Muuttuja | Oletusarvo |
|---|---|
| `--dwc-color-warning-h` | 35 |
| `--dwc-color-warning-s` | 90% |

</TabItem>

<TabItem value="Danger">

| Muuttuja | Oletusarvo |
|---|---|
| `--dwc-color-danger-h` | 4 |
| `--dwc-color-danger-s` | 90% |

</TabItem>

<TabItem value="Info">

| Muuttuja | Oletusarvo |
|---|---|
| `--dwc-color-info-h` | 262 |
| `--dwc-color-info-s` | 65% |

</TabItem>

<TabItem value="Gray">

| Muuttuja | Oletusarvo |
|---|---|
| `--dwc-color-gray-h` | 0 |
| `--dwc-color-gray-s` | 0% |

</TabItem>

<TabItem value="Default / Tone">

| Muuttuja | Oletusarvo |
|---|---|
| `--dwc-color-default-h` | var(--dwc-color-primary-h) |
| `--dwc-color-default-s` | 3% |

</TabItem>

</Tabs>

### Suora siemenkorvaus {#direct-seed-override}

Jokainen paletti altistaa myös `--dwc-color-{name}-seed` muuttujan. Oletusarvoisesti tämä rakennetaan sävy- ja kyllästysarvoista, mutta voit ohittaa sen suoraan millä tahansa voimassa olevalla CSS-värillä kiertäen hue/kyllästysjärjestelmän kokonaan.

```css
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

### Sävykierto {#hue-rotation}

Palettigeneraattori soveltaa hienovaraista sävykiertoa vaiheiden yli luodakseen luonnollisemman näköisiä paletteja. Tummemmat sävyt siirtyvät hieman lämpimämmiksi, kun taas vaaleammat sävyt siirtyvät hieman viileämmiksi. Tämä jäljittelee, miten todelliset pigmentit käyttäytyvät ja estää paletteja näyttämästä litteiltä tai synteettisiltä.

| Muuttuja | Oletusarvo | Kuvaus |
|---|---|---|
| `--dwc-color-hue-rotate` | 3 | Pisteiden sävykierron asteet paletissa. Aseta 0 poistaaksesi käytöstä. |

<dwc-doc-hue-rotate name="primary"></dwc-doc-hue-rotate>

### Generoituja muuttujia jokaiselle vaiheelle {#generated-variables-per-step}

Jokainen paletti generoi 19 vaihetta (5 - 95). Jokaiselle vaiheelle tuotetaan seuraavat muuttujat:

| Muuttujamalli | Kuvaus |
|---|---|
| `--dwc-color-{name}-{step}` | Paletin sävy kyseisessä vaiheessa. |
| `--dwc-color-{name}-text-{step}` | Pintaturvallinen tekstiväri, joka on peräisin kyseisestä vaiheesta (WCAG AA -yhteensopiva). |
| `--dwc-color-on-{name}-text-{step}` | Tekstiväri käytettäväksi SABOTTOMINA värin päällä (automaattisesti vaihdattaa vaaleaa/tummaista). |

:::tip Esteettömyys
Kaikki generoituat tekstivärit täyttävät WCAG AA -kontrastivaatimukset automaattisesti. Sinun ei tarvitse laskea kontrastisuhteita itse.
:::

Ylärivillä näkyy sävy sen `on-text` värin kanssa (tekstille, joka on suoraan tätä sävyä vasten). Alarivillä näkyy `text` väri pinnan taustalla:

<dwc-doc-step-vars name="primary"></dwc-doc-step-vars>

### Lisägeneroidut muuttujat {#additional-generated-variables}

| Muuttujamalli | Kuvaus |
|---|---|
| `--dwc-color-{name}-tint` | Siemenväri 12% läpinäkyvyydellä hienovaraisille korostustaustoille. |
| `--dwc-border-color-{name}` | Moodin mukautuva reunan väri, jossa on paletin sävy. |
| `--dwc-border-color-{name}-emphasis` | Voimakkaampi moodin mukautuva reunan väri hiiren päällä, tarkennuksessa ja aktiivisissa tiloissa. |
| `--dwc-focus-ring-{name}` | Keskitysrenkaan varjo paletille. |

## Globaalit värit {#global-colors}

Nämä ovat moodin mukautuvia värejä, jotka mukautuvat automaattisesti vaalealle ja tummalle teemalle.

| Muuttuja | Kuvaus |
|---|---|
| `--dwc-color-black` | Lähes musta vaaleassa tilassa, lähes valkoinen tummassa tilassa. |
| `--dwc-color-white` | Lähes valkoinen vaaleassa tilassa, lähes musta tummassa tilassa. |
| `--dwc-color-body-text` | Oletustekstiväri (käyttää `--dwc-color-black`). |

## Komponenttiteemat {#theming-components-with-abstract-variables}

DWC abstrahoi käytön käytettävissä olevista paleteista korkeamman tason semanttisten variaatiomuuttujien avulla. Komponentit käyttävät näitä variaatioita raakojen vaiheiden numeroiden sijaan, koska variaatiot mukautuvat automaattisesti vaaleisiin ja tummiin teemoihin.

Variaatiot on jaettu kolmeen ryhmään: `normal`, `dark`, ja `light`.

1. `normal` muuttujat ovat perusväri, jota käytetään taustoihin ja ensisijaisiin käyttöliittymäelementteihin.
2. `dark` muuttujat käytetään pääasiassa `aktiivisissa/painetuissa` tiloissa.
3. `light` muuttujat käytetään pääasiassa `hover/focus` tiloissa.

<Tabs>

<TabItem value="Primary">

<dwc-doc-variations name="primary"></dwc-doc-variations>

```css
--dwc-color-primary-dark: var(--dwc-color-primary-45)
--dwc-color-primary: var(--dwc-color-primary-50)
--dwc-color-primary-light: var(--dwc-color-primary-55)
--dwc-color-primary-alt: var(--dwc-color-primary-tint)

--dwc-color-primary-text-dark: var(--dwc-color-primary-text-40)
--dwc-color-primary-text: var(--dwc-color-primary-text-45)
--dwc-color-primary-text-light: var(--dwc-color-primary-text-50)

--dwc-color-on-primary-text-dark: var(--dwc-color-on-primary-text-45)
--dwc-color-on-primary-text: var(--dwc-color-on-primary-text-50)
--dwc-color-on-primary-text-light: var(--dwc-color-on-primary-text-55)
--dwc-color-on-primary-text-alt: var(--dwc-color-primary-text)
```

</TabItem>

<TabItem value="Success">

<dwc-doc-variations name="success"></dwc-doc-variations>

```css
--dwc-color-success-dark: var(--dwc-color-success-45)
--dwc-color-success: var(--dwc-color-success-50)
--dwc-color-success-light: var(--dwc-color-success-55)
--dwc-color-success-alt: var(--dwc-color-success-tint)

--dwc-color-success-text-dark: var(--dwc-color-success-text-40)
--dwc-color-success-text: var(--dwc-color-success-text-45)
--dwc-color-success-text-light: var(--dwc-color-success-text-50)

--dwc-color-on-success-text-dark: var(--dwc-color-on-success-text-45)
--dwc-color-on-success-text: var(--dwc-color-on-success-text-50)
--dwc-color-on-success-text-light: var(--dwc-color-on-success-text-55)
--dwc-color-on-success-text-alt: var(--dwc-color-success-text)
```

</TabItem>

<TabItem value="Warning">

<dwc-doc-variations name="warning"></dwc-doc-variations>

```css
--dwc-color-warning-dark: var(--dwc-color-warning-45)
--dwc-color-warning: var(--dwc-color-warning-50)
--dwc-color-warning-light: var(--dwc-color-warning-55)
--dwc-color-warning-alt: var(--dwc-color-warning-tint)

--dwc-color-warning-text-dark: var(--dwc-color-warning-text-40)
--dwc-color-warning-text: var(--dwc-color-warning-text-45)
--dwc-color-warning-text-light: var(--dwc-color-warning-text-50)

--dwc-color-on-warning-text-dark: var(--dwc-color-on-warning-text-45)
--dwc-color-on-warning-text: var(--dwc-color-on-warning-text-50)
--dwc-color-on-warning-text-light: var(--dwc-color-on-warning-text-55)
--dwc-color-on-warning-text-alt: var(--dwc-color-warning-text)
```

</TabItem>

<TabItem value="Danger">

<dwc-doc-variations name="danger"></dwc-doc-variations>

```css
--dwc-color-danger-dark: var(--dwc-color-danger-45)
--dwc-color-danger: var(--dwc-color-danger-50)
--dwc-color-danger-light: var(--dwc-color-danger-55)
--dwc-color-danger-alt: var(--dwc-color-danger-tint)

--dwc-color-danger-text-dark: var(--dwc-color-danger-text-40)
--dwc-color-danger-text: var(--dwc-color-danger-text-45)
--dwc-color-danger-text-light: var(--dwc-color-danger-text-50)

--dwc-color-on-danger-text-dark: var(--dwc-color-on-danger-text-45)
--dwc-color-on-danger-text: var(--dwc-color-on-danger-text-50)
--dwc-color-on-danger-text-light: var(--dwc-color-on-danger-text-55)
--dwc-color-on-danger-text-alt: var(--dwc-color-danger-text)
```

</TabItem>

<TabItem value="Info">

<dwc-doc-variations name="info"></dwc-doc-variations>

```css
--dwc-color-info-dark: var(--dwc-color-info-45)
--dwc-color-info: var(--dwc-color-info-50)
--dwc-color-info-light: var(--dwc-color-info-55)
--dwc-color-info-alt: var(--dwc-color-info-tint)

--dwc-color-info-text-dark: var(--dwc-color-info-text-40)
--dwc-color-info-text: var(--dwc-color-info-text-45)
--dwc-color-info-text-light: var(--dwc-color-info-text-50)

--dwc-color-on-info-text-dark: var(--dwc-color-on-info-text-45)
--dwc-color-on-info-text: var(--dwc-color-on-info-text-50)
--dwc-color-on-info-text-light: var(--dwc-color-on-info-text-55)
--dwc-color-on-info-text-alt: var(--dwc-color-info-text)
```

</TabItem>

<TabItem value="Default / Tone">

<dwc-doc-variations name="default"></dwc-doc-variations>

Oletusvaihtoehtoa käytetään neutraaleissa käyttöliittymäelementeissä, kuten komponenttien taustoissa ja rajoissa. Se perii sävyn pääpaletista hyvin alhaisella kyllästystasolla. Poiketen kromatisista paleteista, oletus käyttää omia OKLCH-vaaleuslaskelmiaan palettivaiheiden sijaan.

```css
--dwc-color-default-dark
--dwc-color-default
--dwc-color-default-light
--dwc-color-default-alt: var(--dwc-color-primary-alt)

--dwc-color-default-text-dark: var(--dwc-color-default-text-40)
--dwc-color-default-text: var(--dwc-color-default-text-45)
--dwc-color-default-text-light: var(--dwc-color-default-text-50)

--dwc-color-on-default-text-dark
--dwc-color-on-default-text
--dwc-color-on-default-text-light
--dwc-color-on-default-text-alt: var(--dwc-color-primary-text)

--dwc-focus-ring-default: var(--dwc-focus-ring-primary)
```

</TabItem>

<TabItem value="Gray">

<dwc-doc-variations name="gray"></dwc-doc-variations>

Harva vaihtoehto käyttää puhtaita harmaita sävyjä ja on moodista tietoinen, valiten tummia vaiheita vaaleassa tilassa ja vaaleita vaiheita tummassa tilassa.

```css
--dwc-color-gray-dark
--dwc-color-gray
--dwc-color-gray-light
--dwc-color-gray-alt: var(--dwc-color-gray-tint)

--dwc-color-gray-text-dark: var(--dwc-color-gray-text-40)
--dwc-color-gray-text: var(--dwc-color-gray-text-45)
--dwc-color-gray-text-light: var(--dwc-color-gray-text-50)

--dwc-color-on-gray-text-dark
--dwc-color-on-gray-text
--dwc-color-on-gray-text-light
--dwc-color-on-gray-text-alt: var(--dwc-color-gray-text)
```

</TabItem>

</Tabs>

### Variaatioviite {#variation-reference}

| Muuttuja | Kuvaus |
|---|---|
| `--dwc-color-{name}` | Perusväri. Käytetään taustoissa, täytöissä ja rajoissa. |
| `--dwc-color-{name}-dark` | Tummempi versio. Käytetään aktiivisissa/painetuissa tiloissa. |
| `--dwc-color-{name}-light` | Vaaleampi versio. Käytetään hover/focus -tiloissa. |
| `--dwc-color-{name}-alt` | Siemen 12% läpinäkyvyydellä. Käytetään hienovaraisissa korostustiloissa. |
| `--dwc-color-{name}-text` | Tekstiväri, joka on turvallinen sovelluspinnalla (WCAG AA). |
| `--dwc-color-{name}-text-dark` | Tummempi tekstivaihtoehto. |
| `--dwc-color-{name}-text-light` | Vaaleampi tekstivaihtoehto. |
| `--dwc-color-on-{name}-text` | Tekstiväri käytettäväksi `--dwc-color-{name}` päällä. |
| `--dwc-color-on-{name}-text-dark` | Tekstiväri käytettäväksi `--dwc-color-{name}-dark`. |
| `--dwc-color-on-{name}-text-light` | Tekstiväri käytettäväksi `--dwc-color-{name}-light`. |
| `--dwc-color-on-{name}-text-alt` | Tekstiväri käytettäväksi `--dwc-color-{name}-alt`. |
| `--dwc-border-color-{name}` | Moodista tietoinen reunan väri. |
| `--dwc-border-color-{name}-emphasis` | Voimakkaampi moodista tietoinen reunan väri. |
| `--dwc-focus-ring-{name}` | Keskitysrenkaan varjo. |
