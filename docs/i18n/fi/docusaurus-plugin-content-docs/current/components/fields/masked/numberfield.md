---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: 1ce8783919180d45f2f7321c559fc26a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstikenttä, joka on suunniteltu jäsenneltyyn numeron syöttöön. Se varmistaa, että numerot muotoillaan johdonmukaisesti määritetyn maskin perusteella, mikä tekee siitä erityisen hyödyllisen talouslomakkeissa, hinnoittelukentissä tai missä tahansa syötteessä, jossa tarkkuus ja luettavuus ovat tärkeitä.

Tämä komponentti tukee numeron muotoilua, desimaalien/ryhmittelymerkkien lokaalisointia ja valinnaisia arvon rajoituksia, kuten minimimäärä tai enimmäismäärä.

<!-- INTRO_END -->

## Perusteet {#basics}

`MaskedNumberField` voidaan instansioida parametreilla tai ilman niitä. Se tukee aloitusarvon, etiketin, paikkamerkin ja tapahtumakuuntelijan asettamista, jotta voidaan reagoida arvojen muutoksiin.

Tämä demo esittelee **Vinkkilaskurin**, joka käyttää `MaskedNumberField`-komponenttia intuitiiviseen numeeriseen syöttöön. Yksi kenttä on määritetty hyväksymään muotoillun laskun määrän, kun taas toinen kerää kokonaisluku tipin prosenttiosuuden. Molemmat kentät soveltavat numeromaskia varmistaakseen johdonmukaisen ja ennakoitavan muotoilun.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskimerkkiä hallinnan vuoksi, miten numerosyöttö muotoillaan ja näytetään. Jokainen merkki maskissa määrittää erityisen muotoilukäyttäytymisen, mikä mahdollistaa tarkan hallinnan siitä, miten numerot näkyvät.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla numeroita, joissa käytetään samaa maskisyntaksia kentän ulkopuolella, esimerkiksi datan renderoimisessa [`Table`](/docs/components/table/overview)-komponentissa, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuohjelmakäyttäjää.
:::

### Maskimerkit {#mask-characters}

| Merkki  | Kuvaus |
|---------|--------|
| `0`     | Korvataan aina numerolla (0–9). |
| `#`     | Estää johdanna nollat. Korvataan täyttömerkillä, joka on desimaalipisteen vasemmalla puolella. Peräkkäisille numeroille korvataan tilalla tai nollalla. Muutoin korvataan numerolla. |
| `,`     | Käytetään ryhmittelyerottimena (esim. tuhannet). Korvataan täyttömerkillä, jos sen edessä ei ole numeroita. Muuten näytetään pilkkuna. |
| `-`     | Näyttää miinusmerkin (`-`), jos luku on negatiivinen. Korvataan täyttömerkillä, jos se on positiivinen. |
| `+`     | Näyttää `+` positiivisille tai `-` negatiivisille numeroille. |
| `$`     | Tuottaa aina dollarimerkin. |
| `(`     | Lisää vasemman sulkumerkin `(` negatiivisille arvoille. Korvataan täyttömerkillä, jos se on positiivinen. |
| `)`     | Lisää oikean sulkumerkin `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos se on positiivinen. |
| `CR`    | Näyttää `CR` negatiivisille numeroille. Näyttää kaksi välistä, jos luku on positiivinen. |
| `DR`    | Näyttää `CR` negatiivisille numeroille. Näyttää `DR` positiivisille numeroille. |
| `*`     | Lisää tähden `*`. |
| `.`     | Merkitsee desimaalipistettä. Jos tulostuksessa ei ole numeroita, korvataan täyttömerkillä. Desimaalin jälkeen täyttömerkit käsitellään väleinä. |
| `B`     | Muuttuu aina tilaksi. Kaikki muut kirjaimelliset merkit näytetään sellaisenaan. |

Osa yllä olevista merkeistä voi esiintyä useammin kuin kerran maskissa muotoilua varten. Näitä ovat `-`, `+`, `$`, ja `(`. Jos jokin näistä merkeistä on läsnä maskissa, ensimmäinen kohta joka kohdattiin siirretään viimeiseen sijaintiin, jossa `#` tai `,` oli korvattu täyttömerkillä. Jos tällaista sijaintia ei ole, tuplamerkki jätetään paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä **EI** pyöristä. Esimerkiksi, kun sijoitat arvon `12.34567` kenttään, joka on maskattu muodolla `###0.00`, saat `12.34`.
:::

## Ryhmittely- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee ryhmittely- ja desimaalimerkkien muokkausta, mikä helpottaa numeroiden muotoilua eri alueille tai liiketoimintakäytännöille.

- **Ryhmäerotin** käytetään visuaalisesti erottamaan tuhannet (esim. `1,000,000`).
- **Desimaalierotin** osoittaa numeroiden murtoluvun (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueet käyttävät erilaisia merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // esim. 1.000.000
field.setDecimalCharacter(","); // esim. 123,45
```

:::tip Oletuskäyttäytyminen
Oletuksena `MaskedNumberField` soveltaa ryhmittely- ja desimaalierottimia sovelluksen nykyisen alueen perusteella. Voit ylittää ne milloin tahansa käyttämällä tarjottuja asettimia.
:::

## Mahdollisuus negatiivisiin arvoihin {#negateable}

`MaskedNumberField` tukee vaihtoehtoa hallita, sallitaanko negatiiviset numerot.

Oletuksena negatiiviset arvot, kuten `-123.45`, ovat sallittuja. Estääksesi tämän, käytä `setNegateable(false)` rajoittaaksesi syötteet vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintaskenaarioissa, joissa arvot, kuten määrät, yhteenlasketut arvot tai prosentit, on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` on asetettu `false`, kenttä estää kaikki yritykset syöttää miinusmerkkiä tai muutoin negatiivisia arvoja.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Minimimäärät ja enimmäismäärät {#min-and-max-values}

`MaskedNumberField` tukee numeeristen rajojen asettamista käyttämällä `setMin()` ja `setMax()`. Nämä rajoitukset auttavat varmistamaan, että käyttäjän syötteet pysyvät voimassa olevissa, odotetuissa rajoissa.

- **Minimiarvo**
  Käytä `setMin()` määrittääksesi alhaisin hyväksyttävä numero:

  ```java
  field.setMin(10.0); // Minimiarvo: 10
  ```

  Jos käyttäjä syöttää numeron, joka on alle tämän kynnyksen, se tulee olemaan voimassa oleva.

- **Maksimiarvo**
  Käytä `setMax()` määrittääksesi korkeimman hyväksyttävän numeron:

  ```java
  field.setMax(100.0); // Maksimiarvo: 100
  ```

  Arvot, jotka ylittävät tämän rajan, merkitään voimattomaksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautusominaisuutta, joka nollaa kentän arvon esiasetettuun tilaan. Tämä voi olla hyödyllistä, kun käyttäjät tarvitsevat kumoamaan muutoksia, peruuttamaan vahingossa tehtyjä muokkauksia tai palaamaan tunnettuun oletusarvoon.

Ominaisuuden mahdollistamiseksi määritä kohdearvo käyttämällä `setRestoreValue()`. Tarvittaessa kenttä voidaan nollata ohjelmallisesti käyttäen `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttämällä `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusavain, ellei sitä ole ohitettu)

Palautusarvon on oltava nimenomaan asetettu. Jos sitä ei ole määritelty, ominaisuus ei palauta kenttää.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa [`MaskedNumberField`](#basics) lisäämällä spinnereitä, joiden avulla käyttäjät voivat lisätä tai vähentää arvoa askeleilla tai nuolinäppäimillä.
Tämä on ihanteellinen syötteille, kuten määriin, hintasäätöihin, arviointikontrolleihin tai mihin tahansa skenaarioon, jossa käyttäjät tekevät vähittäismuotoisia muutoksia.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Askelincrementit**
  Käytä `setStep()` määrittääksesi, kuinka paljon arvon tulisi muuttua jokaisella kierroksella:

  ```java
  spinner.setStep(5.0); // Jokainen kierros lisää tai vähentää 5
  ```

- **Interaktiiviset ohjaimet**
  Käyttäjät voivat napsauttaa spinner-painikkeita tai käyttää näppäimistösisältöä arvon säätämiseksi.

- **Kaikki ominaisuudet MaskedNumberFieldistä**
  Täysin tukee maskeja, muotoilua, ryhmittely/desimaalimerkkejä, minimi/maksimi-rajoituksia ja palautuslogiikkaa.

## Tyylittely {#styling}

<TableBuilder name="MaskedNumberField" />
