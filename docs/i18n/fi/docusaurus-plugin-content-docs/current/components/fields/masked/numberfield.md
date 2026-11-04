---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: f247d28107239e0db4a24823c8a1ef60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstikenttä, joka on suunniteltu muotoilemaan numeeriset syötteet johdonmukaisesti määritellyn maskin perusteella. Se on hyödyllinen talouslomakkeissa, hinnoittelukentissä tai kaikissa syötteissä, joissa tarkkuus ja luettavuus ovat tärkeitä.

Tätä komponenttia voidaan instanssia parametreilla tai ilman. Se tukee numeroiden muotoilua, desimaali- ja ryhmittelymerkkien lokalisoimista sekä valinnaisia arvorajoituksia, kuten vähimmäis- ja enimmäisarvoja. Se tukee myös alkuarvon, otsikon, paikkamerkin asettamista sekä tapahtumakuuntelijaa, joka reagoi arvojen muutoksiin.

<!-- INTRO_END -->

Alla oleva esimerkki esittelee **Vinkkilaskurin**, joka käyttää `MaskedNumberField` -komponenttia intuitiiviseen numeeriseen syöttöön. Yksi kenttä on määritetty hyväksymään muotoiltu laskun summa, kun taas toinen sieppaa kokonaissumman tippi prosentteina.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskimerkkijonoa hallitsemaan, kuinka numeerinen syöte muotoillaan ja näytetään. Jokainen merkki maskissa määrittää tietyn muotoilukäyttäytymisen, mikä mahdollistaa tarkan hallinnan numeroiden esittämisessä.

:::tip Maskien soveltaminen ohjelmallisesti
Jos haluat muotoilla numeroita samalla maskin syntaksilla kentän ulkopuolella, esimerkiksi kun renderöit dataa [`Taulukossa`](/docs/components/table/overview), käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -apuluokkaa.
:::

### Maskimerkit {#mask-characters}

| Merkki    | Kuvaus |
|-----------|-------------|
| `0`       | Aina korvataan numerolla (0–9). |
| `#`       | Vaimentaa johtavat nollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Jälkimmäisille numeroille korvataan tilalla tyhjällä tai nollalla. Muussa tapauksessa korvataan numerolla. |
| `,`       | Käytetään ryhmittelyerottimena (esim. tuhanteen). Korvataan täyttömerkillä, jos sen edessä ei ole numeroita. Muussa tapauksessa näytetään pilkku. |
| `-`       | Näyttää miinusmerkin (`-`), jos numero on negatiivinen. Korvataan täyttömerkillä, jos positiivinen. |
| `+`       | Näyttää `+` positiivisille tai `-` negatiivisille numeroille. |
| `$`       | Aina tuloksena dollari-merkki. |
| `(`       | Lisää vasemman sulkumerkin `(` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `)`       | Lisää oikean sulkumerkin `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `CR`      | Näyttää `CR` negatiivisille numeroille. Näyttää kaksi tyhjää tilaa, jos numero on positiivinen. |
| `DR`      | Näyttää `CR` negatiivisille numeroille. Näyttää `DR` positiivisille numeroille. |
| `*`       | Lisää tähden `*`. |
| `.`       | Merkitsee desimaalipistettä. Jos tulostuksessa ei näy numeroita, korvataan täyttömerkillä. Desimaalin jälkeen täyttömerkkejä käsitellään tyhjinä. |
| `B`       | Aina muuttuu tyhjäksi. Muu kirjain näkyy sellaisenaan. |

Osa yllä olevista merkeistä voi esiintyä useita kertoja maskissa muotoilua varten. Näitä ovat `-`, `+`, `$` ja `(`. Jos joku näistä merkeistä on läsnä maskissa, ensimmäinen kohdattu siirretään viimeiseen paikkaan, jossa `#` tai `,` korvattiin täyttömerkillä. Jos sellaista paikkaa ei ole, kaksoismerkki jää paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä ei **pyöristä**. Esimerkiksi, kun sijoitat arvon `12.34567` kenttään, joka on maskattu muodolla `###0.00`, saat tulokseksi `12.34`.
:::

## Ryhmittely- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee **ryhmittely**- ja **desimaal**merkkien mukauttamista, mikä helpottaa numeroiden muotoilua eri paikallisten tai liiketoimintakäytäntöjen mukaan.

- **Ryhmittelyerotin** käytetään erottamaan tuhansia visuaalisesti (esim. `1,000,000`).
- **Desimaalierotin** osoittaa osan numerosta (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueet käyttävät eri merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // esimerkki: 1.000.000
field.setDecimalCharacter(","); // esimerkki: 123,45
```

:::tip Oletuskäyttäytyminen
Oletuksena `MaskedNumberField` soveltaa ryhmittely- ja desimaalierottimia sovelluksen nykyisen paikallisuuden mukaan. Voit ylikirjoittaa ne milloin tahansa käyttämällä annettuja asetinjatkoja.
:::

## Negoituva {#negateable}

`MaskedNumberField` tukee vaihtoehtoa hallita, sallitaanko negatiiviset numerot.

Oletuksena negatiiviset arvot, kuten `-123.45`, ovat sallittuja. Estääksesi tämän, käytä `setNegateable(false)` rajoittaaksesi syötteen vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintaskenaarioissa, joissa arvojen, kuten määrien, kokonaisten tai prosenttien, on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` on asetettu `false`, kenttä estää kaikki yritykset syöttää miinusmerkkiä tai muuten syöttää negatiivisia arvoja.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Minim ja maksimiarvot {#min-and-max-values}

`MaskedNumberField` tukee numeeristen rajojen asettamista `setMin()` ja `setMax()` avulla. Nämä rajoitukset auttavat varmistamaan, että käyttäjän syöte pysyy voimassa olevassa, odotetussa alueessa.

- **Minimiaarvo**
  Käytä `setMin()` määritelläksesi alhaisimman hyväksyttävän arvon:

  ```java
  field.setMin(10.0); // Minimaarvo: 10
  ```

  Jos käyttäjä syöttää arvon, joka on alle tämän rajan, sitä pidetään voimattomana.

- **Maksimiaarvo**
  Käytä `setMax()` määritelläksesi korkeimman hyväksyttävän arvon:

  ```java
  field.setMax(100.0); // Maksimiaarvo: 100
  ```

  Arvot tämän rajan yläpuolella merkitään voimattomiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautustoimintoa, joka nollaa kentän arvon ennalta määriteltyyn tilaan. Tämä voi olla hyödyllistä, kun käyttäjät tarvitsevat peruuttaa muutoksia, kumota vahingossa tehtyjä muutoksia tai palata tunnettuun oletusarvoon.

Voit palauttaa tietyn arvon määrittämällä sen `setRestoreValue()` -menetelmällä. Kun tarpeen, kenttä voidaan nollata ohjelmallisesti käyttämällä `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttäen `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletusarvoinen palautusnäppäin, ellei ylikirjoitettu)

Jos palautusarvoa ei ole asetettu, sekä `restoreValue()` että <kbd>ESC</kbd> asettavat kenttäarvon `null`:iksi.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa `MaskedNumberField` -komponenttia lisäämällä spinnereitä, jotka mahdollistavat käyttäjien arvon lisäämisen tai vähentämisen askelnappeilla tai nuolinäppäimillä. Tämä on ideaali syöttöön, kuten määrien, hinnoittelun säätöjen, arviointien tai minkä tahansa tilanteen, jossa käyttäjät tekevät vähittäisiä muutoksia.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Keskeiset ominaisuudet {#key-features}

- **Askelvähennykset**
  Käytä `setStep()` määrittääksesi, kuinka paljon arvoa pitäisi muuttaa jokaisella spinnauksella:

  ```java
  spinner.setStep(5.0); // Jokainen spinnaus lisää tai vähentää 5
  ```

- **Vuorovaikutteiset ohjaimet**
  Käyttäjät voivat napsauttaa spinnereitä tai käyttää näppäimistösyötettä arvon säätämiseen.

- **Kaikki ominaisuudet `MaskedNumberField` -komponentista**
  Täydellinen tuki maskeille, muotoilulle, ryhmittely-/desimaalimerkeille, min/max-rajoituksille ja palautuslogiikalle.

## Tyylit {#styling}

<TableBuilder name="MaskedNumberField" />
