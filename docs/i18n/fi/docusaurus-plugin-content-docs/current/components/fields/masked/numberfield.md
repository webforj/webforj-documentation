---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 00d399f2bcfb22c884608aa8a0975573
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

`MaskedNumberField` on tekstikenttä, joka on suunniteltu rakenteellista numeerista syöttöä varten. Se varmistaa, että numerot on muotoiltu johdonmukaisesti määritellyn maskin perusteella, mikä tekee siitä erityisen hyödyllisen talouslomakkeissa, hinnoittelukentissä tai kaikissa syötteissä, joissa tarkkuus ja luettavuus ovat tärkeitä.

Tämä komponentti tukee numeroiden muotoilua, desimaali/ryhmittelymerkkejä sekä valinnaisia arvorajoja, kuten minimimäärä tai maksimi.

## Perusteet {#basics}

`MaskedNumberField` voidaan alustaa parametreilla tai ilman. Se tukee alkuarvon, etiketin, paikkamerkin asettamista sekä tapahtumakuuntelijaa, joka reagoi arvojen muutoksiin.

Tämä demo esittelee **Vinkkilaskurin**, joka käyttää `MaskedNumberField` intuitiivista numeerista syöttöä varten. Yksi kenttä on määritetty hyväksymään muotoiltu laskun määrä, kun taas toinen tallentaa kokonaisnumeron juomarahaprosentin. Molemmat kentät soveltavat numeerisia maskeja varmistaakseen johdonmukaisen ja ennakoitavan muotoilun.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Maskisäännöt {#mask-rules}

`MaskedNumberField` käyttää maskimerkkijonoa hallitakseen, miten numeerinen syöttö muotoillaan ja näytetään. 
Jokainen maskin merkki määrittää tietyn muotoilukäyttäytymisen, mikä mahdollistaa tarkan hallinnan siitä, miten numerot näkyvät.

### Maskimerkit {#mask-characters}

| Merkki | Kuvaus |
|--------|--------|
| `0`    | Aina korvataan numerolla (0–9). |
| `#`    | Estää etunollat. Korvataan täyttömerkillä desimaalipisteen vasemmalla puolella. Perässä olevat numerot korvataan tyhjällä tilalla tai nollalla. Muuten korvataan numerolla. |
| `,`    | Käytetään ryhmittelyerottimena (esim. tuhannet). Korvataan täyttömerkillä, jos sen edessä ei ole numeroita. Muussa tapauksessa näytetään pilkku. |
| `-`    | Näyttää miinusmerkin (`-`), jos numero on negatiivinen. Korvataan täyttömerkillä, jos positiivinen. |
| `+`    | Näyttää `+` positiivisille tai `-` negatiivisille numeroille. |
| `$`    | Aina tuloksena dollari. |
| `(`    | Lisätään vasen sulku `(` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `)`    | Lisätään oikea sulku `)` negatiivisille arvoille. Korvataan täyttömerkillä, jos positiivinen. |
| `CR`   | Näyttää `CR` negatiivisille numeroille. Näyttää kaksi tyhjää tilaa, jos numero on positiivinen. |
| `DR`   | Näyttää `CR` negatiivisille numeroille. Näyttää `DR` positiivisille numeroille. |
| `*`    | Lisää tähden `*`. |
| `.`    | Merkitsee desimaalipistettä. Jos ulostulossa ei ole numeroita, korvataan täyttömerkillä. Desimaalin jälkeen täyttömerkit käsitellään tyhjinä tiloina. |
| `B`    | Aina muuttuu tyhjäksi. Muut kirjaimelliset merkit näytetään sellaisinaan. |

Osa yllä olevista merkeistä voi esiintyä useammin kuin kerran maskissa muotoilua varten. Näitä ovat `-`, `+`, `$` ja `(`. Jos jokin näistä merkeistä on läsnä maskissa, ensimmäinen kohdattu siirretään viimeiseen paikkaan, jossa `#` tai `,` korvattiin täyttömerkillä. Jos sellaista paikkaa ei ole, kaksinkertainen merkki jää paikalleen.

:::info Ei automaattista pyöristystä
Maski kentässä ei **PYÖRISTÄ**. Esimerkiksi, kun asetat arvon kuten `12.34567` kenttään, joka on maskattu `###0.00`, saat `12.34`.
:::

## Ryhmittely- ja desimaalierottimet {#group-and-decimal-separators}

`MaskedNumberField` tukee **ryhmittely**- ja **desimaalimerkkien** mukauttamista, mikä tekee numeroiden muotoilusta helppoa eri paikallisten tai liiketoimintakäytäntöjen mukaan.

- **Ryhmäerotin** käytetään visuaalisesti erottamaan tuhannet (esim. `1.000.000`).
- **Desimaalierotin** osoittaa numeron murtoluvun osan (esim. `123.45`).

Tämä on hyödyllistä kansainvälisissä sovelluksissa, joissa eri alueilla käytetään erilaisia merkkejä (esim. `.` vs `,`).

```java
field.setGroupCharacter(".");   // esim. 1.000.000
field.setDecimalCharacter(","); // esim. 123,45
```

:::tip Oletuskäyttäytyminen
Oletuksena `MaskedNumberField` soveltaa ryhmittely- ja desimaalierottimia sovelluksen nykyisen kielen mukaan. Voit ohittaa ne milloin tahansa käyttämällä annettuja asettajia.
:::

## Negoitava {#negateable}

`MaskedNumberField` tukee mahdollisuutta hallita, sallitaanko negatiivisia numeroita.

Oletuksena negatiiviset arvot, kuten `-123.45`, ovat sallittuja. Estä tämä käyttämällä `setNegateable(false)` rajoittaaksesi syötteen vain positiivisiin arvoihin.

Tämä on hyödyllistä liiketoimintaskenaarioissa, joissa arvot, kuten määrät, kokonaissummat tai prosentit, on aina oltava ei-negatiivisia.

```java
field.setNegateable(false);
```

Kun `negatable` on asetettu `false`:ksi, kenttä estää kaikki yritykset syöttää miinusmerkki tai muuten syöttää negatiivisia arvoja.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Minimi- ja maksimimäärät {#min-and-max-values}

`MaskedNumberField` tukee numeeristen rajojen asettamista käyttämällä `setMin()` ja `setMax()`. 
Nämä rajoitukset auttavat varmistamaan, että käyttäjän syöte pysyy voimassa olevassa, odotetussa alueessa.

- **Minimiarvo**  
  Käytä `setMin()` määritelläksesi alhaisin hyväksyttävä luku:

  ```java
  field.setMin(10.0); // Minimiarvo: 10
  ```

  Jos käyttäjä syöttää numeron, joka on tämän kynnyksen alapuolella, sitä pidetään virheellisenä.

- **Maksimiarvo**  
  Käytä `setMax()` määritelläksesi korkein hyväksyttävä luku:

  ```java
  field.setMax(100.0); // Maksimiarvo: 100
  ```

  Arvot tämän rajan yläpuolella merkitään virheellisiksi.

## Arvon palauttaminen {#restoring-the-value}

`MaskedNumberField` tukee palautustoimintoa, joka nollaa kentän arvon ennalta määriteltyyn tilaan. 
Tämä voi olla hyödyllistä, kun käyttäjät tarvitsevat kumota muutoksia, palauttaa vahingossa tehdyt muokkaukset tai palata tunnettuun oletusarvoon.

Tämä käyttäytyminen otetaan käyttöön määrittämällä kohdearvo `setRestoreValue()` -menetelmällä. 
Kun tarpeen, kenttä voidaan nollata ohjelmallisesti käyttämällä `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Tapoja palauttaa arvo {#ways-to-restore-the-value}

- **Ohjelmallisesti** käyttämällä `restoreValue()`
- **Näppäimistön kautta**, painamalla <kbd>ESC</kbd> (tämä on oletuspalautuspainike, ellei sitä ole ylitetty)

Palautusarvo on määritettävä nimenomaisesti. Jos sitä ei ole määritelty, ominaisuus ei palauta kenttää.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

`MaskedNumberFieldSpinner` laajentaa [`MaskedNumberField`](#basics) lisäämällä spinnereitä, joiden avulla käyttäjät voivat lisätä tai vähentää arvoa askeleen painikkeiden tai nuolinäppäinten avulla. 
Tämä on ihanteellinen syötteille, kuten määrille, hinnoittelun säätämiselle, arviointikontrolleille tai kaikissa tilanteissa, joissa käyttäjät tekevät vähittäismuutoksia.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Avainominaisuudet {#key-features}

- **Askelvaikutukset**  
  Käytä `setStep()` määritelläksesi, kuinka paljon arvon tulisi muuttua jokaisella käännöllä:

  ```java
  spinner.setStep(5.0); // Jokainen käännös lisää tai vähentää 5
  ```

- **Interaktiiviset ohjaimet**  
  Käyttäjät voivat napsauttaa spinneripainikkeita tai käyttää näppäimistösisäänsyöttöä arvon säätämiseen.

- **Kaikki ominaisuudet `MaskedNumberField` -komponentista**  
  Täydellisesti tukee maskeja, muotoilua, ryhmittely-/desimaalimerkkejä, minimi-/maksimi-rajoituksia ja palautuslogiikkaa.

## Tyylittely {#styling}

<TableBuilder name="MaskedNumberField" />
