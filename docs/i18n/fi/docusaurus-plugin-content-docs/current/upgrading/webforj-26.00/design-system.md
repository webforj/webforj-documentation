---
title: Upgrading to the v26 Design System
description: >-
  Reference for the design-system updates in DWC 26 - color engine, dark mode,
  surfaces, shadows, typography, radius, focus ring, and interaction feedback.
sidebar_position: 2
_i18n_hash: 8a36bc047ecfc90874412da4d39643fb
---
DWC 26 tuo mukanaan päivitetyn suunnittelujärjestelmän. Päivitys on asteittainen eikä täydellinen kirjoitus: useimmat v25 CSS-muuttujat ovat edelleen käytettävissä, teeman moottorin julkinen API on säilytetty ja olemassa olevat mukautukset toimivat edelleen ilman muutoksia.

Tässä oppaassa dokumentoidaan, mitä on muuttunut, missä visuaalinen ulkoasu poikkeaa ja mitkä päivitysaskeleet tarvitaan, kun sovellus riippuu tietystä v25-toiminnasta.

## Nopeasti arvioitu {#quick-verdict}

| Tilanne | Mitä odottaa |
| --- | --- |
| Käyttää oletustyyliä | Visuaalinen päivitys. Oletuspaletin sävyt on säädetty (esimerkiksi pääväri siirtyi `h: 211 / s: 100%` -> `h: 223 / s: 91%`), varjot näyttävät kerroksellisemmilta ja komponentit tuntuvat pyöreämmiltä. Koodimuutoksia ei tarvita, mutta brändin oletusvärit muuttuvat. |
| Yli kirjoittaa `--dwc-color-{name}-h` ja `-s` | Toimii edelleen. HSL-alkupolku on säilytetty. |
| Yli kirjoittaa yksittäiset palettiaskeleet (esimerkiksi `--dwc-color-primary-40`) | Numerot voivat olla eri värejä. Katso [Väri paletti](#color-palette-step-5-is-always-darkest). |
| Luottaa `--dwc-color-{name}-c` | Poista. Vaalean/tumman tekstin vaihto lasketaan nyt automaattisesti värin mukaan. |
| Viittaa nimettyihin fonttikoon tokeneihin (`--dwc-font-size-m`, `-l` ja niin edelleen) | Skaala siirtyi alaspäin yhdellä tasolla. `m` on nyt 14px sen sijaan, että se olisi 16px. Katso [Typografia](#typography). |
| Käyttää `--dwc-font-weight-semibold` saadakseen 500-painon | `semibold` on nyt 600. Vaihda uuteen `--dwc-font-weight-medium` saadaksesi 500. |
| Varataan tilaa keskitettävien elementtien ympärillä `--dwc-focus-ring-width` | Renkaassa on nyt väli. Lisää `--dwc-focus-ring-gap` siihen tilaan, muuten rengas ylittyy. Katso [Keskittymisrengas](#focus-ring). |
| Mukautettu painikkeiden hover / aaltoefektejä | Aallot on poistettu. Paina palautetta on nyt pieni koon vähennys. |

Jos mikään edellä mainituista ei koske sinua, voit lopettaa lukemisen tähän. Päivityksesi on valmis.

## Uudet ominaisuudet lyhyesti {#whats-new-at-a-glance}

- **Moderni väri moottori.** Paletit luodaan OKLCH-muodossa HSL:n sijaan. Vaaleus askeleet ovat havaintoyksikköisiä (joten vierekkäiset askeleet näyttävät vierekkäisiltä) ja tumma tila ei enää käännä palettia.
- **Tumma tila yhdellä muuttujalla.** `--dwc-dark-mode: 1` kääntää koko käyttöliittymän. Tilamuutos tapahtuu varianttikerroksessa, ei remappaamalla jokaista askelta.
- **Automaattiset `on-text` värit.** Jokaiselle paletti askeleelle saadaan `--dwc-color-on-{name}-text-{step}` kumppaniväri, joka on rajattu WCAG AA-contrast vaatimusten mukaiseksi. Sinun ei tarvitse laskea kontrastia manuaalisesti.
- **Suora siemenvaihto.** Anna mikä tahansa CSS-väri (hex, `rgb()`, `oklch()`, `lab()` jne.) `--dwc-color-{name}-seed` ja koko paletti luodaan sen perusteella.
- **Säädetyt varjot.** Samat kuusi tasoa (`xs` - `2xl`), nyt realistisella kerrosvähennyksellä ja automaattisella tumma-tilan vahvuuden nostolla `--dwc-shadow-strength` avulla.
- **Pinnat ja `default` käyttävät omaa vaaleus käyräänsä.** Molemmat sopeutuvat valolle/tummalle käytön avulla `--dwc-dark-mode` ja pienen päävärin tintin kautta, sen sijaan että määritettäisiin pintoja tummassa teemassa ja aliasoidaan `default` palettiaskeleiksi.
- **Skaalapainetuki.** Aallot on korvattu pienellä skaalavähennyksellä painatuksessa. Tokeneita: `--dwc-scale-press`, `--dwc-scale-press-deep`.
- **Keskittymisrengas välin kanssa.** Renkaassa on nyt pieni pinnanvärinen väli (`--dwc-focus-ring-gap`) ennen värillistä varjoa, jotta se pysyy näkyvänä kiinteissä painikkeissa ja tiiviissä asetteluissa.
- **Reunap radius on siemennetty.** Muuta `--dwc-border-radius-seed` ja `s` - `4xl` askeleet skaalaavat suhteellisesti (`2xs` ja `xs` pysyvät kiinteinä pikseli arvoina). Uudet `3xl` ja `4xl` askeleet.

## Väri järjestelmä {#the-color-system}

Tämä on suurin muutos taustalla. Käyttäytyminen, jonka näet, pitäisi olla tuttua, sisäiset asiat ovat erilaisia.

### Kaksi tapaa määrittää väri {#two-ways-to-define-a-color}

Voit jatkaa värin ja saturaatioon käyttämistä kuten ennenkin tai ylikirjoittaa siemenen suoraan millä tahansa CSS-värillä.

```css
/* Värin + saturaatio (yhä oletuspolku) */
:root {
  --dwc-color-primary-h: 223;
  --dwc-color-primary-s: 91%;
}

/* Suora siemen - mikä tahansa CSS-värimuoto */
:root {
  --dwc-color-primary-seed: #6366f1;
}
```

Jos olet jo käyttänyt `-h` ja `-s`, sinun ei tarvitse tehdä mitään. Siemenvaihto on uusi polku suoran brändivärin saamiseksi.

### Väri paletti: askel 5 on aina tummempi {#color-palette-step-5-is-always-darkest}

V25:ssä paletti vaihtui tummassa tilassa (askel 5 tummempi vaaleassa, vaalein tummassa). V26:ssa askel 5 on aina tummin sävy ja askel 95 on aina vaalein, riippumatta tilasta. Tilamuutos tapahtuu nyt yhdellä kerroksella, varianttitokeneissa:

```css
/* v26 - variantit osoittavat kiinteisiin askeleisiin */
--dwc-color-primary-dark:  var(--dwc-color-primary-45);
--dwc-color-primary:       var(--dwc-color-primary-50);
--dwc-color-primary-light: var(--dwc-color-primary-55);
```

| Tilanne | Mitä muuttuu |
| --- | --- |
| Käytä `--dwc-color-primary` (tai `-dark`, `-light`, `-text`) | Mikään. Variantit käyttäytyvät edelleen samalla tavalla eri tiloissa. |
| Kovakoodattu askel kuten `--dwc-color-primary-40` | Tuo askel ratkaisee saman OKLCH vaaleuden molemmissa tiloissa. Toinen askel, jonka näit tummassa tilassa, tuli eri askeleesta. Vaihda varianttitokeneeseen jos haluat tilatietoista käyttäytymistä. |
| Kirjoitettiin `hsl(var(--dwc-color-primary-h), ...)` suoraan | Toimii yhä. HSL siemen rakennetaan yhä h + s perusteella. |

### Värit ovat johdettuja, eivät luvattu {#colors-are-derived-not-promised}

:::info Huomio
Asetettu väri on **siemen**, ei kohde. Värin, jonka välität `--dwc-color-{name}-h` / `-s` (tai `-seed`) ei välttämättä näy askeleessa 50.
:::

Koska paletti käyttää absoluuttista OKLCH vaaleutta per askel, se mistä siemen päätyy riippuu sen luonnollisesta vaaleudesta. Kirkkaat sävyt (syan, keltainen) omaavat korkean OKLCH vaaleuden ja päätyvät askelille 80-85. Tummemmat sävyt (sininen) ovat sattumalta lähellä askelta 50.

Jos tarvitset tarkan värin tietyssä askeleessa, määritä askel selkeästi:

```css
:root {
  --dwc-color-primary-50: #1d4ed8;
}
```

### `--dwc-color-{name}-c` on poissa {#dwc-color-name-c-is-gone}

V25:ssä `-c` oli kontrastikynnys: taustan vaaleus, jossa kumppaniteksti vaihtui valkoisesta mustaksi. Arvo 50 tarkoitti, että teksti oli valkoinen taustalla, joka oli tummempi kuin 50% ja musta taustoilla, jotka olivat vaaleampia kuin 50%.

V26:ssa et valitse kääntöpistettä. Jokaiselle askeleelle saadaan sävytetty `on-text` väri, joka lasketaan automaattisesti sen sävyn perusteella. Tulos on aina WCAG AA-turvallinen ja säilyttää viitteitä paletin sävystä sen sijaan, että se menisi puhtaaseen mustaan tai valkoiseen.

Jos sinulla on jokin `--dwc-color-{name}-c` ylivarauksia, voit poistaa ne, niillä ei ole vaikutusta.

### Teksti ja `on-text` värit {#text-and-on-text-colors}

V25:ssä oli yksi per-askeleen tekst tokeni, `--dwc-color-{name}-text-{step}`, joka oli puhdas musta tai valkoinen väri, joka laskettiin `-c` kynnyksen mukaan ja tarkoitettu tekstille **tuolla** askelilla taustana.

V26 säilyttää saman tokenin nimen mutta muuttaa sen merkitystä ja lisää toisen per-askeleen tokenin:

| Token | v25 merkitys | v26 merkitys |
| --- | --- | --- |
| `--dwc-color-{name}-text-{step}` | Puhdas musta/valkoinen, tarkoitettu tekstille varjona | Sävyttävä, **pinnan turvallinen** teksti, luettavissa neutraaleilla sivupohjilla |
| `--dwc-color-on-{name}-text-{step}` | (ei ollut olemassa per-askeleen tokenina) | Sävyttävä teksti käytettäväksi **tuolla** askelilla taustana |

Molemmat v26 tokenit on rajattu WCAG AA-kontrastille niiden määrätystä taustasta. Jos käytit `--dwc-color-{name}-text-{step}` etualalla värillisellä taustalla, vaihda `--dwc-color-on-{name}-text-{step}` (uusille `on-text` tokenille) säilyttääksesi sen merkityksen.

### Sävyt ja rajat {#tints-and-borders}

Generaattori nyt tuottaa kolme tokenia per paletti, yksi todella uusi, yksi uusi muunnelma ja yksi, jonka lähde siirtyi:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-tint` | (ei ollut olemassa) | Siemen 12% opasiteetilla, vaihtoehtoisille taustoille |
| `--dwc-border-color-{name}-emphasis` | (ei ollut olemassa) | Voimakkaampi tilatietoinen reuna hover/focus/aktiivinen |
| `--dwc-border-color-{name}` | Asetettu per variaatio `var(--dwc-color-{name})` (pine sävy) | Lasketaan generaattorissa: tilatietoinen vaaleutettu sävy siemenestä |

Jos CSS:si luki `--dwc-border-color-primary` odottaen kyllästettyä pääväriä, visuaali on nyt hienovarainen separatorisävy. Jos haluat erityisesti kyllästetyn ilmeen, vaihda suoraan `--dwc-color-primary`.

## Tumma tila {#dark-mode}

Tumma tila on hallittu yhdellä muuttujalla, `--dwc-dark-mode`. Aseta se arvoon `1` tummalle, `0` vaalealle:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Se osallistuu `calc()` lausekkeisiin koko järjestelmässä, mikä on tapa, jolla tilamuutos siirtyy pintoihin, varjoihin, reunoihin ja tekstiväreihin.

V25:ssä sisäänrakennetut `dark` ja `dark-pure` teemat joutuivat määrittelemään pinnat, varjot ja monet palettivariaatiot manuaalisesti. V26:ssa kaikki se johdetaan `--dwc-dark-mode` ja siemenvärien perusteella. Tyypillinen mukautettu tumma teema, joka ennen oli 20-rivin ylivarauksessa, muuttuu:

```css
html[data-app-theme='my-dark-theme'] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  color-scheme: dark;
}
```

Jos sinulla on mukautettu tumma teema, joka on kopioitu v25 rakenteesta, voit yleensä poistaa suurimman osan sisäblokista ja säilyttää vain siemenen ja tumma-tilan lipun.

## Pinnat ja `default` {#surfaces-and-default}

V25:ssä pinnat määriteltiin kaksi kertaa, ensin `:root`-säännössä vaaleassa tilassa (`hsl(default-h, default-s, 96%)` jne.) ja sitten tummassa teemassa (`hsl(default-h, default-s, 8%)` jne.). `default` variaatio osoitti palettiaskeleisiin ja tarvitsi myös tumman teeman ylivarauksia.

V26:ssa molemmat lasketaan kerran `--dwc-dark-mode` termillä, joka on upotettu vaaleusskaalalaskentaan, mikä takaa:

- Pinnat istuvat aina hieman alle `default`, joten kortit näyttävät visuaalisesti leijuvan sivun ylle.
- Pieni päävärin tintti lisätään siemen chroman kautta molemmissa tiloissa.
- `dark-pure` teema asettaa `--dwc-color-default-s: 0%`, mikä pudottaa tintin automaattisesti nollaan.

Jos sovelluksesi ylittää `--dwc-surface-1` (tai muun pinnan) kiinteällä värillä, se toimii edelleen; valitset vain automaattisen tilan mukautuksen ulkopuolelle.

Myös `--dwc-color-{name}-alt` tokenin lähde muuttui:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-color-{name}-alt` | Palettiaskelta 95 (lähes valkoinen tausta) | Siemen 12% opasiteetilla (läpinäkyvä tintti) |

Jos käytit `-alt` kiinteänä lähes valkoisena taustana, se luetaan nyt läpinäkyvänä tinttinä. Valitse joko spesifinen askel (`--dwc-color-{name}-95`) tai suunnittele läpinäkyvää merkitystä ympärille.

## Varjot {#shadows}

Kuuden tason skaala (`xs`, `s`, `m`, `l`, `xl`, `2xl`) on nimeltään ja lukumäärältään muuttumaton, mutta kerros-eroja rakennettiin realistiseen vähennykseen ja tummassa tilassa varjot ovat nyt automaattisesti 5 kertaa vahvempia `--dwc-shadow-strength` ansiosta, koska tummat pinnat tarvitsevat enemmän kontrastia syvyyden ilmaisemiseen.

Jos käytät vain `var(--dwc-shadow)`, saat säädetyn keski varjon eikä mitään muuta muutu. `--dwc-shadow-color` muuttujan arvoformaatti on yhä käytössä:

| | v25 | v26 |
| --- | --- | --- |
| `--dwc-shadow-color` | HSL-triplet (`h, s%, l%`) | Täysi OKLCH väri |

Jos CSS:si käyttää perinteistä triplet-muotoa kuten `hsla(var(--dwc-shadow-color), 0.07)`, vaihda täysivarjotokeniin (`var(--dwc-shadow-m)`) tai kirjoita uudelleen `oklch(from var(--dwc-shadow-color) l c h / 0.07)`.

## Typografia {#typography}

Komponenttien koon tokeneita (`--dwc-size-*`) ei ole muutettu. Fonttiskaala on säädetty siten, että `m` taso ankkuroituu samaan kevyempään kehosi kokoon kuin muut DWC tokenit, joten kaaviot siirtyivät alaspäin yhdellä askeleella:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-size-3xs` | 10px | 10px |
| `--dwc-font-size-2xs` | 12px | 11px |
| `--dwc-font-size-xs` | 13px | 12px |
| `--dwc-font-size-s` | 14px | 13px |
| `--dwc-font-size-m` | 16px | 14px |
| `--dwc-font-size-l` | 18px | 16px |
| `--dwc-font-size-xl` | 22px | 20px |
| `--dwc-font-size-2xl` | 28px | 26px |
| `--dwc-font-size-3xl` | 36px | 34px |

Oletus `--dwc-font-size` ratkaisee edelleen **14px**, se vain päätyy tuohon kautta `--dwc-font-size-m` (v26) sen sijaan, että se päätyisi `--dwc-font-size-s`:n (v25) kautta.

Jos CSS:si viittaa fonttikokoken tokeneihin nimellä (esim. `font-size: var(--dwc-font-size-l)`), näkyvä tulos on pienempi v26:ssa. Siirry ylös yhdellä tasolla, jotta säilytät v25 koon.

Fonttipainot saivat kolme tokenia (`thin`, `medium`, `black`) ja yksi olemassa oleva token siirtyi:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-font-weight-semibold` | 500 | 600 |
| `--dwc-font-weight-medium` | (ei ollut olemassa) | 500 |

Jos käytit `--dwc-font-weight-semibold` saadaksesi 500-painon tekstiä, vaihda uuteen `--dwc-font-weight-medium`.

Rivivälin kaaviot siirtyivät samaan suuntaan kuin fonttikoot; oletus `--dwc-font-line-height` ratkaisee edelleen 1.25.

`--dwc-font-family-sans` ja `--dwc-font-family-mono` on modernisoitu käyttämään `system-ui` ja `ui-monospace` pinoja. Jos kohdistit tiettyyn nimettyyn fonttiin vanhasta pinosta (`Dank Mono`, `Operator Mono`, `Roboto`, jne.) ja haluat sen takaisin, määritä `--dwc-font-family` hallitsemaasi pinoon.

## Väli {#spacing}

Väli skaala on muuttumaton `xs`:sta eteenpäin. Vain kaksi pienintä tokenia pyöristettiin kokonaispikseliarvoihin:

| Token | v25 | v26 |
| --- | --- | --- |
| `--dwc-space-3xs` | 1.2px | 1px |
| `--dwc-space-2xs` | 2.4px | 2px |

Lähes kaikissa sovelluksissa ei tarvita toimenpiteitä.

## Reunat {#border-radius}

Reunat on nyt siemennetty. Muuta `--dwc-border-radius-seed` ja jokainen askel (`s`, `m`, `l`, `xl`, `2xl`, `3xl`, `4xl`) skaalaavat suhteellisesti. `2xs` ja `xs` askeleet pysyvät edelleen kiinteissä pikseliarvoissa (liian pieniä merkittävän määrän johdannaiseksi).

Kolme asiaa on muuttunut:

| | v25 | v26 |
| --- | --- | --- |
| Yksikkö | `em` (skaalautuu vanhemman fonttikoon mukaan) | `rem` (skaalautuu juuren fonttikoon mukaan) |
| Oletus `--dwc-border-radius` | `--dwc-border-radius-s` (4px) | `--dwc-border-radius-seed` (8px) |
| Saatavilla olevat askeleet | jopa `2xl` | lisää `3xl`, `4xl` |

Komponentit tuntuvat pyöreämmiltä suoraan laatikosta. Jos komponentti, joka on upotettu suurempaan tekstiin, periytyi suuremmasta säteestä `em` mukaan, tuota skaalausta ei enää tapahdu, säteet on nyt kiinnitetty juureen. Jos haluat takaisin v25 oletuskoko, puolita siemen:

```css
:root {
  --dwc-border-radius-seed: 0.25rem; /* 4px, puolittaa koko skaalan */
}
```

## Helposti {#easings}

Helpotuskatalogi on pääasiassa sama, uusilla pikavalintatokenilla yleisiin tapauksiin: `--dwc-ease`, `--dwc-ease-in`, `--dwc-ease-out`, `--dwc-ease-outGlide`. Katso [Siirtymät ja helpotukset](/docs/styling/transitions-easing) sivulta täydellinen lista.

## Siirtymät {#transitions}

Siirtymäajat on tasapainotettu nopeammaksi tunteeksi:

| Muuttuja | v25 | v26 |
| --- | --- | --- |
| `--dwc-transition-slow` | 500&nbsp;ms | 300&nbsp;ms |
| `--dwc-transition-medium` | 250&nbsp;ms | 250&nbsp;ms |
| `--dwc-transition-fast` | 150&nbsp;ms | 150&nbsp;ms |
| `--dwc-transition-x-fast` | 50&nbsp;ms | 100&nbsp;ms |

Jos riippuu tietystä ajasta, ylikirjoita se `:root`-säännössä.

## Keskittymisrengas {#focus-ring}

Keskittymisrengas käyttää nyt kaksoisrengaskuviota: pieni pinnanvärinen väli, sitten värillinen rengas. Tämä pitää renkaan luettavana kiinteillä painikkeilla ja tiiviillä asetteluilla.

| Muuttuja | v25 | v26 |
| --- | --- | --- |
| `--dwc-focus-ring-width` | 3px | 2px |
| `--dwc-focus-ring-a` | 0.4 | 0.75 |
| `--dwc-focus-ring-gap` | (ei ollut) | 2px |
| `--dwc-focus-ring-l` | 45% | (poistettu, vaaleus lasketaan per tila) |

Jos varaat tilaa keskitettävissä elementeissä `padding: var(--dwc-focus-ring-width)`, lisää väli siihen täytteeseen, jotta uusi rengas saa tilaa renderoida:

```css
/* v25 */
dwc-button { padding: var(--dwc-focus-ring-width); }

/* v26 */
dwc-button {
  padding: calc(var(--dwc-focus-ring-width) + var(--dwc-focus-ring-gap));
}
```

## Vuorovaikutuksen palaute {#interaction-feedback}

Materiaalityylisiä aaltoefektejä ei enää käytetä minkään DWC-komponentin avulla. Uusi palaute jokaiselle napsautettavalle elementille on pieni koon vähennys:

```css
--dwc-scale-press: 0.97;      /* Tavanomainen 3% kutistuminen */
--dwc-scale-press-deep: 0.93; /* Syvempi 7% kutistuminen painikkeille */
```

`ripple` SCSS-mixin ja `--dwc-ripple-color` CSS-muuttuja ovat edelleen käytössä, mutta mitään ei tuoda niihin oletusarvoisesti. Jos omat komponenttisi valitsivat mixinin, vaihda painotustokeniin vastataksesi uutta tuntumaa.

## Selaimen tuki {#browser-support}

Uusi järjestelmä käyttää kahta CSS-ominaisuutta, joiden selaimen yhteensopivuustiedot voit nähdä MDN:ssä:

- [OKLCH väriavaruus](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/oklch#browser_compatibility), sisältää suhteellisen värin syntaksin (`oklch(from ...)`)
- [`color-mix()`](https://developer.mozilla.org/en-US/docs/Web/CSS/color_value/color-mix#browser_compatibility)

Molemmat ovat mukana evergreen Chrome, Edge, Firefox ja Safari.

## Käytännöllinen päivitys tarkistuslista {#a-pragmatic-upgrade-checklist}

1. Etsi `--dwc-color-*-c` ja poista ne lausunnot.
2. Etsi `hsla(var(--dwc-shadow-color)` ja korvaa se varjotokenilla (`var(--dwc-shadow-m)`) tai kirjoita se uudelleen `oklch(from ...)`.
3. Etsi suorat palettiaskeleet (`--dwc-color-{name}-{number}`). Jos jokin ruokkii tumma-tilataitoja, vaihda varianttitokeneihin (`--dwc-color-{name}`, `-dark`, `-light`).
4. Etsi nimettyjä fonttikoon viittauksia (`--dwc-font-size-m`, `-l`, ja niin edelleen). Jos haluat v25 koon, siirry ylös yhdelle tasolle.
5. Etsi `--dwc-font-weight-semibold`. Jos halusit 500, vaihda `--dwc-font-weight-medium`.
6. Jos varaat tilaa keskitettävissä elementeissä `--dwc-focus-ring-width`, lisää `--dwc-focus-ring-gap` pehmusteeseen.
7. Avaa sovellus, napsauttele ympäriinsä. Useimmat sovellukset eivät tarvitse mitään muuta.
