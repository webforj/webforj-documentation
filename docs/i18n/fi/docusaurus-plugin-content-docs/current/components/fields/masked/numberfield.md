---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: bba6de4e793a65cc887af236d206bb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstinsuli, joka on suunniteltu muotoilemaan numeerisia syötteitä johdonmukaisesti määritellyn maskin perusteella. Se on hyödyllinen taloudellisissa lomakkeissa, hinnoittelukentissä tai missä tahansa syötteessä, jossa tarkkuus ja luettavuus ovat tärkeitä.

Tätä komponenttia voidaan instansioida parametreilla tai ilman. Se tukee numeromuotoilua, desimaalierottimien ja ryhmittelymerkkien lokalisointia sekä valinnaisia arvorajoituksia, kuten minimirajoja tai maksimirajoja. Se tukee myös alkualkua, etikettiä, paikkamerkkiä ja tapahtumakuuntelijaa, joka reagoi arvojen muutoksiin.

<!-- INTRO_END -->

Esimerkki alla esittelee **Vinkkilaskurin**, joka käyttää `MaskedNumberField`-komponenttia intuitiiviseen numeeriseen syöttöön. Yksi kenttä on konfiguroitu hyväksymään muotoiltu laskumäärä, kun taas toinen tallentaa kokonaislukuvinkin prosenttiosuuden.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskimerkkijonoa ohjaamaan, kuinka numeerinen syötteet muotoillaan ja esitetään. Jokainen merkki maskissa määrittelee tietyn muotoilutoiminnon, mikä mahdollistaa tarkan hallinnan siitä, miten numerot näkyvät.

:::tip Maskien soveltaminen ohjelmallisesti
Jotta numerot voidaan muotoilla aivan samansuuntaisella maskisyntaksilla kentän ulkopuolella, esimerkiksi kun renderöidään tietoja `[`Table`](/docs/components/table/overview)`, käytä [`MaskDecorator`](/docs/advanced/mask-decorator) -työkaluluokkaa.
:::

### Maskimerkit {#mask-characters}

| Merkki | Kuvaus |
|-----------|-------------|
| `0`       | Korvataan aina numerolla (0–9). |
| `#`       | Estää etunollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Viimeisten numeroiden kohdalla korvataan tyhjällä tai nollalla. Muuten korvataan numerolla. |
| `,`       | Käytetään ryhmittelyerottimena (esim. tuhansia). Korvataan täyttömerkillä, jos sen edessä ei ole numeroita. Muussa tapauksessa näytetään pilkku. |
| `-`       | Näyttää miinusmerkin (`-`), jos numero on negatiivinen. Korvataan täyttömerkillä, jos se on positiivinen. |
| `+`       | Näyttää `+` positiivisille tai `-` negatiivisille numeroille. |
| `$`       | Aina tuloksena dollari-merkki. |
| `(`       | Lisää vasemman sulkumerkin `(` negatiivisille arvoille. Korvataan täyttömerkillä, jos se on positiivinen. |
| `)`       | Lisää oikean sulkumerkin `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos se on positiivinen. |
| `CR`      | Näyttää `CR` negatiivisille numeroille. Näyttää kaksi väliä positiiviselle. |
| `DR`      | Näyttää `CR` negatiivisille numeroille. Näyttää `DR` positiivisille numeroille. |
| `*`       | Lisää tähden `*`. |
| `.`       | Merkitsee desimaalipistettä. Jos tulostuksessa ei ole numeroita, se korvataan täyttömerkillä. Desimaalin jälkeen täyttömerkit käsitellään väleinä. |
| `B`       | Muuttuu aina väliksi. Kaikki muut kirjaimelliset merkit näytetään sellaisinaan. |

Suurin osa yllä olevista merkeistä voi esiintyä useamman kerran maskissa muotoilua varten. Näitä ovat `-`, `+`, `$` ja `(`. Jos jokin näistä merkit on läsnä maskissa, ensimmäinen kohteeseen liittyvä siirretään viimeiseen kohtaan, jossa `#` tai `,` korvattiin täyttömerkillä. Jos tällaista paikkaa ei ole, kaksoismerkki jää paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä ei **PYÖRISTÄ**. Esimerkiksi, kun arvoksi asetetaan `12.34567`, joka on maskattu `###0.00`-maskilla, saadaan tulokseksi `12.34`.
:::

## Ryhmittely- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee **ryhmittely**- ja **desimaal**merkkien mukauttamista, mikä tekee numeromuotoilusta helppoa erilaisille kulttuurille tai liiketoimintakäytännöille.

- **Ryhmittelyerotin** on tarkoitettu visuaaliseen erottamiseen tuhansista (esim. `1,000,000`).
- **Desimaalierotin** merkitsee numeron murtoluvun osaa (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueet käyttävät erilaisia merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // esim. 1.000.000
field.setDecimalCharacter(","); // esim. 123,45
```

:::tip Oletuskäytös
Oletuksena `MaskedNumberField` käyttää ryhmittely- ja desimaalierottimia sovelluksen nykyisen kulttuurin mukaan. Voit ohittaa ne milloin tahansa käyttämällä annettuja asettajia.
:::

## Negatoitava {#negateable}

`MaskedNumberField` tukee asetusta, joka määrittää, sallitaanko negatiivisia numeroita.

Oletuksena negatiiviset arvot kuten `-123.45` ovat sallittuja. Estääksesi tämän, käytä `setNegateable(false)` rajoittaaksesi syötteen vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintakäytännöissä, joissa arvot kuten määrät, yhteensä tai prosentit on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` on asetettu `false`, kenttä estää kaikki yritykset syöttää miinusmerkkiä tai muuten syöttää negatiivisia arvoja.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Minimi- ja maksimiarvot {#min-and-max-values}

`MaskedNumberField` tukee numeeristen raja-arvojen asettamista `setMin()` ja `setMax()` avulla. Nämä rajoitukset auttavat varmistamaan, että käyttäjän syöte pysyy voimassa olevassa ja odotetussa alueessa.

- **Minimiarvo**
  Käytä `setMin()` määrittääksesi alhaisin hyväksyttävä luku:

  ```java
  field.setMin(10.0); // Minimiarvo: 10
  ```

  Jos käyttäjä syöttää arvon, joka on tämän kynnysarvon alapuolella, se katsotaan virheelliseksi.

- **Maksimiarvo**
  Käytä `setMax()` määrittääksesi korkeimman hyväksyttävän luvun:

  ```java
  field.setMax(100.0); // Maksimiarvo: 100
  ```

  Arvot, jotka ylittävät tämän rajan, merkitään virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautusominaisuutta, joka nollaa kentän arvon ennalta määriteltyyn tilaan. Tämä voi olla hyödyllistä, kun käyttäjät tarvitsevat muutosten kumoamista, vahingossa tehtyjen muokkausten palauttamista tai paluuta tunnettuun oletusarvoon.

Ota tämä toiminta käyttöön määrittelemällä kohdearvo `setRestoreValue()` avulla. Kun tarpeen, kenttä voidaan nollata ohjelmallisesti käyttämällä `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tavat palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttäen `restoreValue()`
- **Näppäimistön kautta** painamalla <kbd>ESC</kbd> (tämä on oletus palautusnäppäin ellei sitä ole ohitettu)

Palautusarvo on määriteltävä nimenomaisesti. Jos sitä ei ole määritelty, ominaisuus ei palauta kenttää.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa `MaskedNumberField`-komponenttia lisäämällä säätöohjaimia, jotka mahdollistavat käyttäjien arvon lisäämisen tai vähentämisen käyttäen askelpainikkeita tai nuolinäppäimiä. Tämä on ihanteellinen syötteille, kuten määrät, hinnoittelusäätö, arviointikontrollit tai minkä tahansa tilanteen, jossa käyttäjät tekevät vähittäismuutoksia.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Avainominaisuudet {#key-features}

- **Askelintegraalit**
  Käytä `setStep()` määrittääksesi, kuinka paljon arvon pitäisi muuttua jokaisella pyörityksellä:

  ```java
  spinner.setStep(5.0); // Jokainen pyöritys lisää tai vähentää 5
  ```

- **Interaktiiviset ohjaimet**
  Käyttäjät voivat napsauttaa säätöpainikkeita tai käyttää näppäimistösisääntuloa muuttaakseen arvoa.

- **Kaikki ominaisuudet MaskedNumberFieldistä**
  Täydellisesti tukee maskeja, muotoilua, ryhmittely- /desimaalimerkkejä, minimi/max rajoituksia ja palautuslogiikkaa.

## Tyylittäminen {#styling}

<TableBuilder name="MaskedNumberField" />
