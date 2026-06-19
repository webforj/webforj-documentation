---
sidebar_position: 2
title: Themes
sidebar_class_name: updated-content
description: >-
  Apply built-in light, dark, and dark-pure themes with @AppTheme or define
  custom themes through data-app-theme selectors.
_i18n_hash: 91e1a18f11aadea66df804dbaa4917d9
---
Teema webforJ:ssä on nimetty joukko CSS-käyttäjäominaisuuksia (design tokens), jotka hallitsevat, miltä jokainen komponentti näyttää. Teemojen vaihtaminen laskee värit, varjot, pinnat ja reunat koko sovelluksessa välittömästi ilman uudelleenrakennusta.

## Sisäänrakennetut teemat {#built-in-themes}

webforJ sisältää kolme sovellusaihetta suoraan laatikosta:

| Teema       | Tausta         | Sävy                        |
|-------------|----------------|-----------------------------|
| `light`     | Vaalea (oletus)| Hieman ensisijaisen värin sävy |
| `dark`      | Tumma         | Hieman ensisijaisen värin sävy |
| `dark-pure` | Tumma         | Ei mitään (puhtaat neutraalit harmaat) |

Mikä tahansa sovellus voi vaihtaa niiden välillä ajon aikana, ja lisätyt mukautetut teemat voidaan määrittää yhdessä sisäänrakennettujen kanssa.

## Teeman soveltaminen {#applying-a-theme}

Aseta aktiivinen teema deklaratiivisesti `@AppTheme`-annotaatiolla tai ohjelmallisesti `App.setTheme()`-kutsulla. Teeman nimen on oltava yksi seuraavista: `system`, `light`, `dark`, `dark-pure` tai mukautetun teeman nimi.

```java
@AppTheme("dark-pure")
class MyApp extends App {
  // sovelluskoodi
}

// tai ohjelmallisesti
App.setTheme("dark-pure");
```

Kutsuminen `App.setTheme()` uudelleen milloin tahansa vaihtaa sovelluksen eri teemaan.

## Väriteema {#color-scheme}

`color-scheme` CSS -lausunto kertoo selaimelle, kuinka renderöidä sen sisäänrakennetut pinnat, kuten natviit vierityspalkit, lomakeohjainwidgetit, automaattitäytön kohokohdat ja oletussivun tausta ennen CSS:n lataamista. Sisäänrakennetut `dark` ja `dark-pure` -teemat asettavat jo `color-scheme: dark` automaattisesti, joten selaimen mukautetut osat sulautuvat tummiin pintoihin automaattisesti.

Sinun tarvitsee ajatella tätä vain määrittäessäsi omaa mukautettua tummaa teemaa. Tässä tapauksessa sisällytä `color-scheme: dark` teeman valitsijaan:

```css
html[data-app-theme="brand-dark"] {
  --dwc-dark-mode: 1;
  color-scheme: dark;
}
```

Jos jätät sen huomiotta, vierityspalkkiasetukset ja automaattitäyttöruudut pysyvät oletusarvoisesti vaaleassa tilassa ja näyttävät epäsopivilta tummaa pintaasi vasten. Vaaleat teemat eivät tarvitse tätä lausuntoa, selaimet oletusarvoisesti toimivat vaaleana.

## Käyttäjän mieltymyksen noudattaminen {#following-the-users-preference}

Useimmat käyttöjärjestelmät antavat käyttäjille mahdollisuuden valita vaalean tai tumman ulkoasun koko järjestelmän laajuudella. webforJ voi kunnioittaa tätä mieltymystä ja valita oikean teeman automaattisesti.

Rekisteröi, mikä teema tulee soveltaa jokaiselle ulkoasun tilalle `@AppLightTheme` ja `@AppDarkTheme` (tai `App.setLightTheme()` ja `App.setDarkTheme()`), ja sitten siirrä varattu avainsana `"system"` `App.setTheme()`-kutsuun (tai `@AppTheme("system")`), jotta webforJ voi valita niiden välillä käyttäjän käyttöjärjestelmän mieltymyksen mukaan.

```java
@AppTheme("system")
@AppLightTheme("light")
@AppDarkTheme("dark")
class MyApp extends App {
  // sovelluskoodi
}
```

Vastaava ohjelmallinen muoto:

```java
App.setLightTheme("light");
App.setDarkTheme("dark");
App.setTheme("system");
```

`"system"` on varattu avainsana. webforJ ratkaisee sen ajon aikana joko rekisteröityyn vaaleaan tai tummaan teemaan ja ratkaisee sen automaattisesti aina, kun käyttöjärjestelmän mieltymys muuttuu. Kun se on ratkaistu, sivun `data-app-theme` -attribuutti on `light` tai `dark`, joten kaikki CSS-yliuudistukset tulisi kohdistaa näihin nimiin sen sijaan, että käytettäisiin `"system"`.

:::info Käyttöjärjestelmän tason ulkoasuasetukset
Missä käyttäjät aktivoivat järjestelmän laajuisen ulkoasuasetuksen vaihtelee alustan mukaan:

- **Windows 10/11**: Asetukset > Mukauttaminen > Värit > Valitse värisi
- **macOS**: Järjestelmäasetukset > Ulkoasu
- **iOS**: Asetukset > Näyttö & kirkkaus > Ulkoasu
- **Android**: Asetukset > Näyttö > Tumma teema
:::

## Olemassa olevien teemojen ylikirjoittaminen {#overriding-default-themes}

Suurin osa brändäystyöstä tehdään **ylikirjoittamalla olemassa olevat teemat** sen sijaan, että luotaisiin uusia. Säädä siemenvärit (tai mikä tahansa muu token) sisäänrakennettuille `light`, `dark` ja `dark-pure` -teemille, niin jokainen komponentti ottaa automaattisesti uuden ilmeen.

Voit ylikirjoittaa **light**-teeman määrittämällä CSS-käyttäjäominaisuudet `:root`-valitsijassa.

:::info `:root` pseudo-luokka
`:root` CSS -pseudo-luokka kohdistaa asiakirjan juurielementtiin. HTML:ssä se edustaa `<html>`-elementtiä ja sillä on korkeampi spesifisyys kuin tavallisella `html`-valitsijalla.
:::

```css
:root {
  --dwc-color-primary-h: 215;
  --dwc-color-primary-s: 100%;
  --dwc-font-size: var(--dwc-font-size-l);
}
```

Ylikirjoittamiseen **dark** tai **dark-pure** -teemoissa käytä attribuuttivalitsijoita `<html>`-elementissä:

```css
html[data-app-theme="dark"] {
  --dwc-color-primary-seed: #a855f7;
}

html[data-app-theme="dark-pure"] {
  --dwc-color-primary-seed: #a855f7;
}
```

Teeman vaihtaminen kutsulla `App.setTheme("dark")` aktivoi brändätyn tumman teeman, eikä uusi teemanimi ole tarpeen.

## Mukautettujen teemojen luominen {#creating-custom-themes}

Luo täysin uusi teema vain silloin, kun tarvitset sellaista, joka elää sisäänrakennettujen rinnalla (esimerkiksi korkean kontrastin variantti tai asiakaskohtainen ulkoasu). Valitse ainutlaatuinen nimi ja määritä se omassa `html[data-app-theme='TEEMA_NIMI']` -valitsijassa:

```css
html[data-app-theme="new-theme"] {
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
}
```

Muuttaaksesi mukautetun teeman tummaksi, aseta `--dwc-dark-mode: 1` ja `color-scheme: dark`:

```css
html[data-app-theme="new-dark-theme"] {
  --dwc-dark-mode: 1;
  --dwc-color-primary-h: 280;
  --dwc-color-primary-s: 100%;
  color-scheme: dark;
}
```

Sitten sovelluksessasi:

```java
@AppTheme("new-theme")
class MyApp extends App {
  // sovelluskoodi
}

// tai ohjelmallisesti
App.setTheme("new-theme");
```

## Työskentely DWC-tokeneiden kanssa {#working-with-dwc-tokens}

Muutama tapa pitää mukautettu CSS linjassa design-järjestelmän kanssa ja estää sen kulkeutumasta tummassa tilassa tai tulevissa versioissa.

### Viittaa aina tokeneihin `var(...)` avulla {#always-reference-tokens-with-var}

Kovakoodatut väriarvot (`#3b82f6`, `rgb(59 130 246)`, `oklch(0.6 0.18 250)`) eivät sopeudu tummaan tilaan eivätkä seuraa palettimuutoksia. Käytä sen sijaan tokenia.

```css
/* vältä */
.my-panel {
  background: #ffffff;
  color: #1f2937;
  border: 1px solid #e5e7eb;
}

/* preferoi */
.my-panel {
  background: var(--dwc-surface-3);
  color: var(--dwc-color-body-text);
  border: 1px solid var(--dwc-border-color);
}
```

### Suosi variaatiotokenia raakojen askelnumeroiden yli {#prefer-variation-tokens-over-raw-step-numbers}

Variaatiotokenit (`--dwc-color-primary`, `-dark`, `-light`, `-text`, `-alt`) ratkaistaan automaattisesti eri askeleiksi vaaleassa ja tummassa tilassa. Raakat askelnumeroita (`--dwc-color-primary-50`) ei.

```css
/* vältä - jäätyy askeleeseen 50 molemmissa tiloissa */
.badge {
  background: var(--dwc-color-primary-50);
}

/* preferoi - siirtyy askel tummassa tilassa */
.badge {
  background: var(--dwc-color-primary);
}
```

### Käytä päätettä, joka vastaa roolia {#use-the-suffix-that-matches-the-role}

| Päätteen tyyppi                     | Rooli                                                            |
|-------------------------------------|-----------------------------------------------------------------|
| `--dwc-color-{name}`                | Raskaampi täyttö täysillä voimalla (painikkeet, merkinnät, bannerit) |
| `--dwc-color-{name}-dark`           | Aktiivinen / painetilanne                                       |
| `--dwc-color-{name}-light`          | Hover / fokus-tausta                                           |
| `--dwc-color-{name}-alt`            | Hieman sävytetty tausta ilmoituksille ja ylimääräisille riveille|
| `--dwc-color-{name}-text`           | Värillinen teksti neutraalilla pinnalla                        |
| `--dwc-color-on-{name}-text`        | Teksti, joka sijaitsee **värillisen** sävyn päällä (automaattinen kontrasti) |
| `--dwc-border-color-{name}`         | Reunat ja jakajat                                             |

### Varaa pinnat ja reunat niiden rooleille {#reserve-surfaces-and-borders-for-their-roles}

Pinnat (`--dwc-surface-1` / `-2` / `-3`) rakentavat sivun hierarkian. Reunat (`--dwc-border-color`, `--dwc-border-color-*`) vetävät rajoja. Palettiaskeleiden uudelleenkäyttö näille rooleille toimii visuaalisesti, mutta menetetään automaattinen tilamuunnos, jota omat tokenit kantavat.

### Ylikirjoita siemen tason tasolla mukautetuissa teemoissa {#override-at-the-seed-level-in-custom-themes}

Kun rakennat mukautettua teemaa, aseta siemen (`--dwc-color-{name}-h`, `-s`, tai `-seed`) sen sijaan, että ylikirjoitat yksittäisiä askeleita. Generaattori uudelleenrakentaa täydet 19-askeleista paletin siemenen ympärille, pitäen koko sävyalueen johdonmukaisena. Yksittäisten askeleiden ylikirjoittaminen jättää loput paletista kulkemaan brändivärisi vastaisesti.

```css
/* vältä - jättää muut askeleet epätasa-arvoisiksi */
html[data-app-theme="brand"] {
  --dwc-color-primary-50: #6366f1;
}

/* preferoi - regeneroi koko paletin */
html[data-app-theme="brand"] {
  --dwc-color-primary-seed: #6366f1;
}
```

### Käytä tokeneita väljyydessä, koossa, säteissä ja siirtymissä {#use-tokens-for-spacing-sizing-radius-and-transitions}

Sama sääntö jatkuu koko muun designjärjestelmän alueella: viittaa tokeniin, älä koskaan taikanuotteihin.

```css
/* vältä */
.my-panel {
  padding: 16px;
  border-radius: 8px;
  transition: background-color 250ms;
}

/* preferoi */
.my-panel {
  padding: var(--dwc-space-m);
  border-radius: var(--dwc-border-radius);
  transition: background-color var(--dwc-transition);
}
```

Kovakoodatut arvot kiertävät käyttäjäkohtaiset fonttikoon skaalausasetukset, lukitsevat sinut tiettyyn muotokieleen ja ohittavat designjärjestelmän helpot ajoituskäyrät.

### Käytä `::part(...)` päästäksesi komponentteihin {#use-part-to-reach-into-components}

webforJ-komponentit ovat Shadow DOM:ia. Niiden sisäistä merkintää ei voida kohdistaa ulkoisilla valitsijoilla, joten sääntö kuten `.dwc-button-label { ... }` ei sovi mihinkään. Kohdistamalla näkyviin osiin, tyylit sisäisiä osia:

```css
/* tyylittele etiketti jokaisessa ensisijaisessa painikkeessa */
dwc-button[theme="primary"]::part(label) {
  letter-spacing: 0.02em;
}
```

Katso [Shadow Parts](./shadow-parts) koko mekanismista ja jokaisen komponentin **Tyylitys → Shadow Parts**-osasta sen osista, jotka se paljastaa.

### Rajoita tokenin ylikirjoitukset ympäröivän valitsijan avulla {#scope-token-overrides-with-a-wrapper-selector}

CSS-käyttäjäominaisuudet laskeutuvat. Tokenin asettaminen ympäröivälle elementille säätää kaikkea sen sisällä, vaikuttamatta sovelluksen muihin osiin.

```css
.danger-section {
  --dwc-color-primary-seed: #ef4444;
}
```

Jokainen komponentti `.danger-section`-osion sisällä (painikkeet, linkit, fokusrenkaat) käyttää nyt vaarallista punaista sävyä, samalla kun globaaliteema pysyy muuttumattomana.

### Testaa sekä vaaleassa että tummassa tilassa {#test-in-both-light-and-dark-mode}

Ennen kuin julkaiset mitään mukautettua CSS:ää, vaihda teema `dark` ja `dark-pure` ja kulje läpi näyttö. Yleisimmät regressiot ovat kovakoodatut väriarvot, jotka näyttivät hyvältä yhdessä tilassa ja lukivat epäselviltä tai paletista poikkeavilta toisessa.

### Älä tavoittele `!important` {#dont-reach-for-important}

Se pakenee laskeumaa ja tekee jokaisesta tulevasta ylikirjoituksesta vaikeampaa. Jos sääntö ei toimi, syy on lähes aina spesifisyysmismatch, puhtaammalla korjauksella: kohdista sama valitsija, jota kehys käyttää, tai lisää vanhempi kvalifikaattori. Varaa `!important` aidosti kolmannen osapuolen tyylittelyjen varalle, joita sinulla ei ole tapaa voittaa.

## Komponenttiteemat {#component-themes}

Sovellus- tason teemojen lisäksi webforJ-komponentit tukevat joukkoa **komponenttiteemoja**, jotka perustuvat oletusväripaletteihin: `default`, `primary`, `success`, `warning`, `danger`, `info` ja `gray`. Tämä on riippumaton aktiivisesta sovellusteemasta.

Jokainen komponentti asiakirjoittaa sen tukemat teemat **Tyylitys → Teemat** -osiossa.
