---
sidebar_position: 9
title: State
_i18n_hash: 3dc9896bce3e0577b2407f8ae4c863d0
---
State tokens määrittävät, kuinka komponentit visuaalisesti reagoivat käyttäjän vuorovaikutukseen, esimerkiksi kun ne ovat poistettu käytöstä tai fokusoitu. Nämä muuttujat auttavat varmistamaan johdonmukaisen käyttäytymisen ja tyylin kaikissa käyttöliittymäelementeissä, ja niitä voidaan helposti mukauttaa vastaamaan suunnittelujärjestelmääsi.

<!-- vale off -->
## Poistettu käytöstä {#disabled-state}
<!-- vale on -->
Poistettu käytöstä -tilan ominaisuuksia käytetään elementin visuaalisen näkyvyyden ja ei-vuorovaikutteisuuden osoittamiseen.

Opacity mukautuu nykyiseen teeman tilanteeseen optimaalisen näkyvyyden saavuttamiseksi sekä vaaleissa että tummissa tiloissa.

### Esimerkki {#example}

```css
input:disabled {
  opacity: var(--dwc-disabled-opacity);
  cursor: var(--dwc-disabled-cursor);
}
```

### Muuttujat {#variables}

| **Muuttuja**               | **Oletusarvo**          | **Kuvaus** |
|----------------------------|-------------------------|------------|
| `--dwc-disabled-opacity`   | Mukautuu vaaleaan/tummaiseen tilaan | Vähennetty opacity poistetuissa käytöstä olevissa elementeissä |
| `--dwc-disabled-cursor`    | var(--dwc-cursor-disabled) | |

---

## Fokustila {#focus-state}

Kun komponentti saa fokuksen, sen ympärille näytetään fokuskehys, joka osoittaa sen aktiivisen tilan. Fokuskehys käyttää gap-ring -kuviota, jossa on pinta-värinen sisäkatko ja värillinen ulkokehys.

### Muuttujat {#variables-1}

| **Muuttuja**               | **Oletusarvo** | **Kuvaus** |
|----------------------------|-----------------|------------|
| `--dwc-focus-ring-a`       | 0.75            | Fokuskehyksen alfa-opasiteetti |
| `--dwc-focus-ring-width`   | 2px             | Fokuskehyksen paksuus |
| `--dwc-focus-ring-gap`     | 2px             | Kuopan väli komponentin reunan ja kehyksen välillä |

Jokainen väri-paletista tuottaa oman fokuskehyksen muuttujan:

| Muuttujamalli             | Kuvaus |
|---------------------------|--------|
| `--dwc-focus-ring-{name}` | Fokuskehyksen varjo, joka on sävytetty paletinvärillä. |

Missä `{name}` on yksi seuraavista: `primary`, `success`, `warning`, `danger`, `info`, `gray`, `default`. Katso [Komponenttiteemat](./colors#theming-components-with-abstract-variables) lisätietoja varten.

<dwc-doc-focus-rings></dwc-doc-focus-rings>

---

## Skaalat {#scales}

Skaala-Transformaatioita käytetään painallus/klikkaus-palautteena vuorovaikutteisille elementeille.

| **Muuttuja**              | **Oletusarvo** | **Kuvaus** |
|---------------------------|-----------------|------------|
| `--dwc-scale-press`       | 0.97            | Vakio painallusskaala (3% kutistus) |
| `--dwc-scale-press-deep`  | 0.93            | Syvä painallusskaala (7% kutistus) |

<dwc-doc-scales></dwc-doc-scales>
